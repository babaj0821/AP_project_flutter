import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';
import 'package:gif_view/gif_view.dart';
import 'add_work_dialog.dart';

class WorkPage extends StatefulWidget {
  @override
  _WorkPageState createState() => _WorkPageState();
}

class _WorkPageState extends State<WorkPage> {
  List<String> works = [];
  List<String> completedWorks = [];
  bool showGif = false;

  @override
  void initState() {
    super.initState();
    _loadWorks();
  }

  _loadWorks() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    setState(() {
      works = (prefs.getStringList('works') ?? []);
      completedWorks = (prefs.getStringList('completedWorks') ?? []);
    });
  }

  _saveWorks() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.setStringList('works', works);
    await prefs.setStringList('completedWorks', completedWorks);
  }

  void _addWork(String work) {
    setState(() {
      works.add(work);
      _saveWorks();
    });
  }

  void _completeWork(int index) {
    setState(() {
      String completedWork = works.removeAt(index);
      completedWorks.add(completedWork);
      showGif = true;
      _saveWorks();

      Future.delayed(Duration(seconds: 2), () {
        setState(() {
          showGif = false;
        });
      });
    });
  }

  void _clearCompletedWorks() {
    setState(() {
      completedWorks.clear();
      _saveWorks();
    });
  }

  void _openAddWorkDialog() async {
    final result = await showDialog<Map<String, dynamic>>(
      context: context,
      builder: (BuildContext context) {
        return AddWorkDialog();
      },
    );

    if (result != null && result.containsKey('work') && result.containsKey('dueTime')) {
      _addWork("${result['work']} (Due: ${result['dueTime']})");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          Column(
            children: [
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Text(
                      'To Do Works:',
                      style: TextStyle(color: Colors.red, fontSize: 20, fontWeight: FontWeight.bold),
                    ),
                    ClipOval(
                      child: Image.asset(
                        'assets/images/sad.jpg',
                        height: 80.0,
                        width: 80.0,
                        fit: BoxFit.cover,
                      ),
                    ),
                  ],
                ),
              ),
              Expanded(
                child: ListView.builder(
                  itemCount: works.length,
                  itemBuilder: (context, index) {
                    return ListTile(
                      title: Text(works[index]),
                      trailing: IconButton(
                        icon: Icon(Icons.check),
                        onPressed: () => _completeWork(index),
                      ),
                    );
                  },
                ),
              ),
              Divider(),
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Row(
                      children: [
                        Text(
                          'Completed Works:',
                          style: TextStyle(color: Colors.green, fontSize: 20, fontWeight: FontWeight.bold),
                        ),
                        IconButton(
                          icon: Icon(Icons.delete),
                          onPressed: _clearCompletedWorks,
                        ),
                      ],
                    ),
                    ClipOval(
                      child: Image.asset(
                        'assets/images/happy.jpg',
                        height: 80.0,
                        width: 80.0,
                        fit: BoxFit.cover,
                      ),
                    ),
                  ],
                ),
              ),
              Expanded(
                child: ListView.builder(
                  itemCount: completedWorks.length,
                  itemBuilder: (context, index) {
                    return ListTile(
                      title: Text(
                        completedWorks[index],
                        style: TextStyle(decoration: TextDecoration.lineThrough),
                      ),
                    );
                  },
                ),
              ),
            ],
          ),
          if (showGif)
            Center(
              child: GifView.asset(
                'assets/images/pooh.gif',
                height: 400.0,
                width: 400.0,
              ),
            ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _openAddWorkDialog,
        child: Icon(Icons.add),
        backgroundColor: Colors.pink[300],
        shape: CircleBorder(),
        foregroundColor: Colors.white,
      ),
    );
  }
}
