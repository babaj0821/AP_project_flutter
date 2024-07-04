import 'dart:io';
import 'package:flutter/material.dart';
import 'package:ap_project/student.dart';

class TasksPage extends StatefulWidget {
  @override
  _TasksPageState createState() => _TasksPageState();
}

class _TasksPageState extends State<TasksPage> {
  final List<Map<String, String>> _tasks = [];
  final TextEditingController _taskController = TextEditingController();
  final TextEditingController _timeController = TextEditingController();

  @override
  void initState() {
    super.initState();
    _receiveTasksFromServer();
  }

  Future<void> _receiveTasksFromServer() async {
    try {
      final socket = await Socket.connect('192.168.43.66', 8888);
      print('Connected to: ${socket.remoteAddress.address}:${socket.remotePort}');
      socket.write('$globalUsername-giveTask\u0000');
      await socket.flush();
      print('Data sent to server: $globalUsername-giveTask\u0000');

      // Listen for responses from the server
      socket.listen((data) {
        final response = String.fromCharCodes(data).trim();
        print('Response from server: $response');

        // Assuming server sends tasks in format: "task1-time1,task2-time2,..."
        final tasks = response.split(',');
        setState(() {
          _tasks.clear();
          for (var task in tasks) {
            final taskDetails = task.split('/');
            if (taskDetails.length == 2) {
              _tasks.add({"title": taskDetails[0], "time": taskDetails[1]});
            }
          }
        });
      });
    } catch (e) {
      print('Error: $e');
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e')),
      );
    }
  }

  Future<void> _sendCompletedTaskToServer(String taskTitle) async {
    try {
      final socket = await Socket.connect('192.168.43.66', 8888);
      print('Connected to: ${socket.remoteAddress.address}:${socket.remotePort}');
      socket.write('$globalUsername-completedTask-$taskTitle\u0000');
      await socket.flush(); // Ensure data is sent
      print('Completed task sent to server: completed-$taskTitle\u0000');
      await socket.close();
    } catch (e) {
      print('Error: $e');
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e')),
      );
    }
  }

  void _addTask() {
    if (_taskController.text.isNotEmpty && _timeController.text.isNotEmpty) {
      setState(() {
        _tasks.add({"title": _taskController.text, "time": _timeController.text});
        _taskController.clear();
        _timeController.clear();
      });
      Navigator.of(context).pop();
    }
  }

  void _markTaskAsCompleted(int index) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text("Warning"),
          content: Text("Are you sure you want to delete this task?"),
          actions: [
            TextButton(
              child: Text("Cancel"),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            TextButton(
              child: Text("Yes"),
              onPressed: () {
                setState(() {
                  _sendCompletedTaskToServer(_tasks[index]["title"]!);
                  _tasks.removeAt(index);
                });
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  void _showAddTaskDialog() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text("Add New Task"),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                controller: _taskController,
                decoration: InputDecoration(hintText: "Enter Task Title"),
              ),
              TextField(
                controller: _timeController,
                decoration: InputDecoration(hintText: "Enter Task Time"),
              ),
            ],
          ),
          actions: [
            TextButton(
              child: Text("Cancel"),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            TextButton(
              child: Text("Add"),
              onPressed: _addTask,
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
        title: Text(
          "Tasks",
          style: TextStyle(fontSize: 18),
        ),
        centerTitle: true,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            ListView.builder(
              shrinkWrap: true,
              physics: NeverScrollableScrollPhysics(),
              itemCount: _tasks.length,
              itemBuilder: (context, index) {
                return Card(
                  child: ListTile(
                    leading: IconButton(
                      icon: Icon(Icons.check_circle, color: Colors.green),
                      onPressed: () => _markTaskAsCompleted(index),
                    ),
                    title: Text(_tasks[index]["title"]!),
                    subtitle: Text(_tasks[index]["time"]!),
                  ),
                );
              },
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _showAddTaskDialog,
        child: Icon(Icons.add),
      ),
    );
  }
}
