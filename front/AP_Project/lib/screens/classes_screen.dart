import 'package:flutter/material.dart';
import 'dart:io';
import 'package:ap_project/screens/signin_screen.dart';
class CoursePage extends StatefulWidget {
  @override
  _CoursePageState createState() => _CoursePageState();
}

class _CoursePageState extends State<CoursePage> {
  List<Course> courses = [];

  void _addCourse() async {
    String? courseCode = await _showCourseCodeDialog();
    if (courseCode != null && courseCode.isNotEmpty) {
      try {
        final socket = await Socket.connect('192.168.43.66', 8888);
        print('Connected to: ${socket.remoteAddress.address}:${socket.remotePort}');

        // Send course code to the server
        socket.write('402243127-course-$courseCode\u0000');
        await socket.flush();
        print('Data sent to server:402243039-course-$courseCode\u0000');

        // Listen for responses from the server
        socket.listen((data) {
          final response = String.fromCharCodes(data).trim();
          print('Response from server: $response');
          if (response.startsWith('course_details-')) {
            final courseDetails = response.substring('course_details-'.length);
            final course = Course.fromDetailsString(courseDetails);
            setState(() {
              courses.add(course);
            });
          } else {
            ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(content: Text(response)),
            );
          }
        }, onError: (error) {
          print('Error: $error');
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('Error: $error')),
          );
        }, onDone: () {
          print('Socket closed');
        });

        // Close the socket after listening is complete
        await socket.done;
      } catch (e) {
        print('Error: $e');
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Error: $e')),
        );
      }
    }
  }

  Future<String?> _showCourseCodeDialog() async {
    TextEditingController courseCodeController = TextEditingController();
    return await showDialog<String>(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Enter Course Code'),
          content: TextField(
            controller: courseCodeController,
            decoration: InputDecoration(hintText: "Course Code"),
          ),
          actions: <Widget>[
            TextButton(
              child: Text('Cancel'),
              onPressed: () {
                Navigator.of(context).pop(null);
              },
            ),
            TextButton(
              child: Text('Submit'),
              onPressed: () {
                Navigator.of(context).pop(courseCodeController.text);
              },
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('کلاس‌ها'),
      ),
      body: ListView.builder(
        itemCount: courses.length,
        itemBuilder: (context, index) {
          return CourseCard(course: courses[index]);
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _addCourse,
        child: Icon(Icons.add),
      ),
    );
  }
}

class Course {
  final String title;
  final String instructor;
  final int units;
  final int assignmentsRemaining;
  final String topStudent;

  Course({
    required this.title,
    required this.instructor,
    required this.units,
    required this.assignmentsRemaining,
    required this.topStudent,
  });

  factory Course.fromDetailsString(String details) {
    final parts = details.split('-');
    return Course(
      title: parts[0],
      instructor: parts[1],
      units: int.tryParse(parts[2]) ?? 0,
      assignmentsRemaining: int.tryParse(parts[3]) ?? 0,
      topStudent: parts[4],
    );
  }
}

class CourseCard extends StatelessWidget {
  final Course course;

  CourseCard({required this.course});

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: EdgeInsets.all(10),
      child: Padding(
        padding: EdgeInsets.all(10),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              course.title,
              style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
            ),
            SizedBox(height: 5),
            Text('استاد: ${course.instructor}'),
            SizedBox(height: 5),
            Text('تعداد واحد: ${course.units}'),
            SizedBox(height: 5),
            Text('تکالیف باقی‌مانده: ${course.assignmentsRemaining}'),
            SizedBox(height: 5),
            Text('دانشجوی ممتاز: ${course.topStudent}'),
          ],
        ),
      ),
    );
  }
}
