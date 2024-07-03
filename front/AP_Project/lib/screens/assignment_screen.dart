import 'package:flutter/material.dart';
class WorkPage extends StatefulWidget {
  @override
  _WorkPageState createState() => _WorkPageState();
}

class _WorkPageState extends State<WorkPage> {
  final List<Map<String, String>> _tasks = [
    {"title": "آز ریز - تمرین 1", "time": "09:00 صبح"},
    {"title": "تمرین الگوریتم", "time": "10:00 صبح"},
    {"title": "انتخاب واحد", "time": "11:00 صبح"},
    {"title": "تکمیل آز OS", "time": "12:00 ظهر"},
  ];

  final List<String> _completedTasks = [
    "تکمیل آز ریز - تمرین 0",
    "بررسی فایل‌ها تمرین"
  ];

  final TextEditingController _taskController = TextEditingController();
  final TextEditingController _timeController = TextEditingController();

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
          "کارها",
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
                    leading: Icon(Icons.check_circle, color: Colors.green),
                    title: Text(_tasks[index]["title"]!),
                    subtitle: Text(_tasks[index]["time"]!),
                  ),
                );
              },
            ),
            SizedBox(height: 32.0),
            Align(
              alignment: Alignment.centerRight,
              child: Text("کارهای انجام شده", style: TextStyle(fontSize: 18.0, fontWeight: FontWeight.bold)),
            ),
            ListView.builder(
              shrinkWrap: true,
              physics: NeverScrollableScrollPhysics(),
              itemCount: _completedTasks.length,
              itemBuilder: (context, index) {
                return Card(
                  child: ListTile(
                    title: Text(_completedTasks[index]),
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