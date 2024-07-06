import 'dart:io';
import 'package:flutter/material.dart';
import 'package:ap_project/student.dart';

class BirthdaysTab extends StatefulWidget {
  @override
  _BirthdaysTabState createState() => _BirthdaysTabState();
}

class _BirthdaysTabState extends State<BirthdaysTab> {
  final List<String> _birthdays = [];

  @override
  void initState() {
    super.initState();
    _receiveBirthdaysFromServer();
  }

  Future<void> _receiveBirthdaysFromServer() async {
    try {
      final socket = await Socket.connect('192.168.43.66', 8888);
      print('Connected to: ${socket.remoteAddress.address}:${socket.remotePort}');
      socket.write('$globalUsername-giveBirthdays\u0000');
      await socket.flush();
      print('Data sent to server: $globalUsername-giveBirthdays\u0000');

      // Listen for responses from the server
      socket.listen((data) {
        final response = String.fromCharCodes(data).trim();
        print('Response from server: $response');

        setState(() {
          _birthdays.clear();
          if (response.isNotEmpty) {
            _birthdays.addAll(response.split(','));
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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            _birthdays.isEmpty
                ? Center(
              child: Text(
                "No birthdays today",
                style: TextStyle(fontSize: 18, color: Colors.grey),
              ),
            )
                : ListView.builder(
              shrinkWrap: true,
              physics: NeverScrollableScrollPhysics(),
              itemCount: _birthdays.length,
              itemBuilder: (context, index) {
                return Card(
                  child: ListTile(
                    title: Text(_birthdays[index]),
                  ),
                );
              },
            ),
          ],
        ),
      ),
    );
  }
}
