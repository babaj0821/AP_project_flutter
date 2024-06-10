import 'package:flutter/material.dart';
import 'package:ap_project/screens/task_screen.dart';
class HomeScreen extends StatefulWidget {
  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int _selectedIndex = 0;

  final List<Widget> _pages = [
    HomePage(),
    TasksPage(),
    ClassesPage(),
    NewsPage(),
    WorkPage(),
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          "خلاصه",
          style: TextStyle(fontSize: 18),
        ),
        centerTitle: true,
      ),
      body: _pages[_selectedIndex],
      bottomNavigationBar: BottomNavigationBar(
        items: [

          BottomNavigationBarItem(icon: Icon(Icons.home), label: 'سرا'),
          BottomNavigationBarItem(icon: Icon(Icons.work), label: 'تسک ها'),
          BottomNavigationBarItem(icon: Icon(Icons.school), label: 'کلاسا'),
          BottomNavigationBarItem(icon: Icon(Icons.notifications), label: 'خبرها'),
          BottomNavigationBarItem(icon: Icon(Icons.work), label: 'کارا'),
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

class HomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              _buildSummaryCard("بهترین نمره 100% ", Icons.flash_on),
              _buildSummaryCard("2 تا امتحان داری", Icons.favorite_border),
              _buildSummaryCard("3 تا تمرین داری", Icons.alarm),
            ],
          ),
          SizedBox(height: 16.0),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              _buildSummaryCard("بهترین نمره ماهه", Icons.trending_up),
              _buildSummaryCard("2 تا ددلاین پرری", Icons.timer),
            ],
          ),
          SizedBox(height: 32.0),
          Align(
            alignment: Alignment.centerRight,
            child: Text("کارهای جاری", style: TextStyle(fontSize: 18.0, fontWeight: FontWeight.bold)),
          ),
          _buildCurrentTask("آز ریز - تمرین 1", true),
          _buildCurrentTask("تست - تمرین 1", false),
          SizedBox(height: 32.0),
          Align(
            alignment: Alignment.centerRight,
            child: Text("تمرین‌های انجام شده", style: TextStyle(fontSize: 18.0, fontWeight: FontWeight.bold)),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              _buildCompletedTask("تمرین 1 معماری"),
              _buildCompletedTask("AP - تمرین 2"),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildSummaryCard(String text, IconData icon) {
    return Card(
      elevation: 4.0,
      child: Container(
        width: 100.0,
        height: 80.0,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(icon, size: 30.0),
            SizedBox(height: 8.0),
            Text(text, textAlign: TextAlign.center, style: TextStyle(fontSize: 14.0)),
          ],
        ),
      ),
    );
  }

  Widget _buildCurrentTask(String text, bool isCompleted) {
    return ListTile(
      leading: Icon(isCompleted ? Icons.check_circle : Icons.cancel, color: isCompleted ? Colors.green : Colors.red),
      title: Text(text),
    );
  }

  Widget _buildCompletedTask(String text) {
    return Card(
      elevation: 4.0,
      child: Container(
        width: 150.0,
        height: 50.0,
        child: Center(
          child: Text(text, style: TextStyle(fontSize: 14.0)),
        ),
      ),
    );
  }
}

class NewsPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(child: Text('خبرها Page'));
  }
}

class ClassesPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(child: Text('کلاسا Page'));
  }
}

class WorkPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(child: Text('works Page'));
  }
}