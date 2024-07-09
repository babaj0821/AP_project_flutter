import 'dart:io';
import 'package:flutter/material.dart';
import 'package:ap_project/screens/task_screen.dart';
import 'package:ap_project/screens/classes_screen.dart';
import 'package:ap_project/screens/news_screen.dart';
import 'package:ap_project/screens/user_profile_page.dart';
import 'package:ap_project/student.dart';
import 'assignment_screen.dart';
import 'signin_screen.dart';

class HomeScreen extends StatefulWidget {
  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int _selectedIndex = 0;

  final List<Widget> _pages = [
    HomePage(),
    TasksPage(),
    CoursePage(),
    NewsScreen(),
    WorkPage(),
    ProfileScreen(),
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
      print('Tapped item: $index');
    });
  }

  void _logout() {
    Navigator.pushAndRemoveUntil(
      context,
      MaterialPageRoute(builder: (context) => SignInScreen()),
          (Route<dynamic> route) => false,//remove all previous routs
    );
  }

  @override
  Widget build(BuildContext context) {
    print('Building HomeScreen');
    return Scaffold(
      appBar: AppBar(backgroundColor: Colors.cyan[500],
        automaticallyImplyLeading: false,
        title: Text(
          "دانشجویار",
          style: TextStyle(fontSize: 25, color: Colors.brown),
        ),
        centerTitle: true,
        actions: [
          IconButton(
            icon: Icon(Icons.logout),
            onPressed: _logout,
          ),
        ],
      ),
      body: _pages[_selectedIndex],
      bottomNavigationBar: BottomNavigationBar(
        items: [
          BottomNavigationBarItem(icon: Icon(Icons.home,color: Colors.cyan[800],), label: 'سرا',),
          BottomNavigationBarItem(icon: Icon(Icons.work,color: Colors.cyan[800]), label: 'تکالیف'),
          BottomNavigationBarItem(icon: Icon(Icons.school,color: Colors.cyan[800]), label: 'کلاسا'),
          BottomNavigationBarItem(icon: Icon(Icons.notifications,color: Colors.cyan[800]), label: 'خبرها'),
          BottomNavigationBarItem(icon: Icon(Icons.work,color: Colors.cyan[800]), label: 'برنامه'),
          BottomNavigationBarItem(icon: Icon(Icons.person,color: Colors.cyan[800]), label: 'پروفایل'),
        ],
        currentIndex: _selectedIndex,
        selectedItemColor: Colors.pink,
        unselectedItemColor: Colors.grey,
        showUnselectedLabels: true,
        type: BottomNavigationBarType.fixed,
        onTap: _onItemTapped,
      ),
    );
  }
}

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  String _summaryData = "";
  String _notDoneAssignments = "";
  String _doneAssignments = "";
  String _daysLeft = "";

  @override
  void initState() {
    super.initState();
    _fetchDataFromServer();
  }

  Future<void> _fetchDataFromServer() async {
    try {
      final socket = await Socket.connect('192.168.43.66', 8888);
      print('Connected to: ${socket.remoteAddress.address}:${socket.remotePort}');


      socket.write('$globalUsername-getSummary\u0000');
      await socket.flush();

      socket.write('$globalUsername-getNotDoneAssignments\u0000');
      await socket.flush();

      socket.write('$globalUsername-getDoneAssignments\u0000');
      await socket.flush();
      socket.write('$globalUsername-getDaysLeft\u0000');
      await socket.flush();


      socket.listen((data) {
        final response = String.fromCharCodes(data).trim();
        if (response.startsWith('summary:')) {
          setState(() {
            _summaryData = response.substring(8);
          });
        } else if (response.startsWith('notdone:')) {
          setState(() {
            _notDoneAssignments = response.substring(8);
          });
        } else if (response.startsWith('done:')) {
          setState(() {
            _doneAssignments = response.substring(5);
          });
        }else if (response.startsWith('daysLeft:')) {
          setState(() {
            _daysLeft = response.substring(9);
          });
        }
      });
    } catch (e) {
      print('Error: $e');
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    print('Building HomePage');
    return Scaffold(
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            _buildSummarySection(),
            SizedBox(height: 16.0),
            _buildDaysLeftRow(),
            SizedBox(height: 32.0),
            _buildNotDoneAssignmentsSection(),
            SizedBox(height: 32.0),
            _buildDoneAssignmentsSection(),
          ],
        ),
      ),
    );
  }

  Widget _buildSummarySection() {
    final data = _summaryData.split('-');
    if (data.length < 5) {
      return Text("Invalid summary data");
    }
    return Column(
      children: [
        Row(
          children: [
            Expanded(child: _buildSummaryCard("Assignments Done", data[0], Icons.check_circle)),
            Expanded(child: _buildSummaryCard("Assignments Not Done", data[1], Icons.cancel)),
            Expanded(child: _buildSummaryCard("Number of Exams", data[2], Icons.school)),
          ],
        ),
        SizedBox(height: 16.0),
        Row(
          children: [
            Expanded(child: _buildSummaryCard("Best Grade", data[3], Icons.trending_up)),
            Expanded(child: _buildSummaryCard("Worst Grade", data[4], Icons.trending_down)),
          ],
        ),
      ],
    );
  }

  Widget _buildDaysLeftRow() {
    final times = _daysLeft.split('-');
    if (times.length != 2) {
      return Text("Invalid days left data");
    }
    return Row(
      children: [
        Expanded(
          child: Text("soonest: "+
            times[0],
            style: TextStyle(color: Colors.red, fontSize: 16.0),
            textAlign: TextAlign.left,
          ),
        ),
        Text(
          '|',
          style: TextStyle(color: Colors.black, fontSize: 16.0),
        ),
        Expanded(
          child: Text("latest: "+
            times[1],
            style: TextStyle(color: Colors.green, fontSize: 16.0),
            textAlign: TextAlign.right,
          ),
        ),
      ],
    );
  }

  Widget _buildSummaryCard(String title, String value, IconData icon) {
    return Card(
      elevation: 4.0,
      child: Container(
        padding: EdgeInsets.all(8.0),
        height: 120.0,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(icon, size: 30.0),
            SizedBox(height: 8.0),
            Text(title, textAlign: TextAlign.center, style: TextStyle(fontSize: 14.0)),
            SizedBox(height: 4.0),
            Text(value, textAlign: TextAlign.center, style: TextStyle(fontSize: 14.0, fontWeight: FontWeight.bold)),
          ],
        ),
      ),
    );
  }

  Widget _buildNotDoneAssignmentsSection() {
    final assignments = _notDoneAssignments.split('-');
    if (assignments.isEmpty || assignments[0].isEmpty) {
      return Text("No not done assignments");
    }
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text("Not Done Assignments", style: TextStyle(fontSize: 18.0, fontWeight: FontWeight.bold)),
        ListView.builder(
          shrinkWrap: true,
          physics: NeverScrollableScrollPhysics(),
          itemCount: assignments.length,
          itemBuilder: (context, index) {
            return _buildAssignmentTile(assignments[index], false);
          },
        ),
      ],
    );
  }

  Widget _buildDoneAssignmentsSection() {
    final assignments = _doneAssignments.split('-');
    if (assignments.isEmpty || assignments[0].isEmpty) {
      return Text("No done assignments");
    }
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text("Done Assignments", style: TextStyle(fontSize: 18.0, fontWeight: FontWeight.bold)),
        ListView.builder(
          shrinkWrap: true,
          physics: NeverScrollableScrollPhysics(),
          itemCount: assignments.length,
          itemBuilder: (context, index) {
            return _buildAssignmentTile(assignments[index], true);
          },
        ),
      ],
    );
  }

  Widget _buildAssignmentTile(String assignment, bool isCompleted) {
    final parts = assignment.split(':');
    if (parts.length != 3) return Container();

    return ListTile(
      leading: Icon(isCompleted ? Icons.check_circle : Icons.cancel, color: isCompleted ? Colors.green : Colors.red),
      title: Text('${parts[0]}: ${parts[1]}'),
    );
  }
}
