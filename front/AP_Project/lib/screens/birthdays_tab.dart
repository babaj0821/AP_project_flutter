import 'dart:io';
import 'dart:math';
import 'package:flutter/material.dart';
import 'package:confetti/confetti.dart';
import 'package:ap_project/student.dart';

class BirthdaysTab extends StatefulWidget {
  @override
  _BirthdaysTabState createState() => _BirthdaysTabState();
}

class _BirthdaysTabState extends State<BirthdaysTab> {
  final List<String> _birthdays = [];
  final ConfettiController _confettiController = ConfettiController(duration: const Duration(seconds: 10));

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      _confettiController.play();
    });
    _receiveBirthdaysFromServer();
  }

  @override
  void dispose() {
    _confettiController.dispose();
    super.dispose();
  }

  Future<void> _receiveBirthdaysFromServer() async {
    try {
      final socket = await Socket.connect('192.168.43.66', 8888);
      print('Connected to: ${socket.remoteAddress.address}:${socket.remotePort}');
      socket.write('$globalUsername-giveBirthdays\u0000');
      await socket.flush();
      print('Data sent to server: $globalUsername-giveBirthdays\u0000');
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
      appBar: AppBar(
        title: Text(
          'Today\'s Birthdays',
          style: TextStyle(color: Colors.white),
          textAlign: TextAlign.center,
        ),
        automaticallyImplyLeading: false,
        centerTitle: true,
        backgroundColor: Colors.pinkAccent,
      ),
      body: Stack(
        children: [
          SingleChildScrollView(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              children: [
                _birthdays.isEmpty
                    ? Center(
                  child: Text(
                    "No birthdays today",
                    style: TextStyle(fontSize: 18, color: Colors.grey),
                    textAlign: TextAlign.center,
                  ),
                )
                    : ListView.builder(
                  shrinkWrap: true,
                  physics: NeverScrollableScrollPhysics(),
                  itemCount: _birthdays.length,
                  itemBuilder: (context, index) {
                    return Card(
                      color: Colors.pink[50],
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(15.0),
                      ),
                      child: ListTile(
                        leading: Icon(Icons.cake, color: Colors.pink),
                        title: Center(
                          child: Text(
                            _birthdays[index],
                            style: TextStyle(
                              fontSize: 20,
                              color: Colors.pink[900],
                              fontWeight: FontWeight.bold,
                            ),
                            textAlign: TextAlign.center,
                          ),
                        ),
                      ),
                    );
                  },
                ),
              ],
            ),
          ),
          Align(
            alignment: Alignment.topCenter,
            child: ConfettiWidget(
              confettiController: _confettiController,
              blastDirection: pi / 2, // Shoot straight up
              emissionFrequency: 0.05, // How often it should emit
              numberOfParticles: 20, // Number of particles to emit
              gravity: 0.1, // fall speed
              shouldLoop: false, // Ensure it stops after the duration
              colors: const [Colors.blue, Colors.pink, Colors.orange, Colors.purple, Colors.yellow],
            ),
          ),
        ],
      ),
    );
  }
}
