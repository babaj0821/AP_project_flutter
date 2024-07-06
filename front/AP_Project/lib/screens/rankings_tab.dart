import 'dart:io';
import 'package:flutter/material.dart';
import 'package:ap_project/student.dart';

class RankingsTab extends StatefulWidget {
  @override
  _RankingsTabState createState() => _RankingsTabState();
}

class _RankingsTabState extends State<RankingsTab> {
  final List<Map<String, String>> _rankings = [];

  @override
  void initState() {
    super.initState();
    _receiveRankingsFromServer();
  }

  Future<void> _receiveRankingsFromServer() async {
    try {
      final socket = await Socket.connect('192.168.43.66', 8888);
      print('Connected to: ${socket.remoteAddress.address}:${socket.remotePort}');
      socket.write('$globalUsername-giveRankings\u0000');
      await socket.flush();
      print('Data sent to server: $globalUsername-giveRankings\u0000');

      // Listen for responses from the server
      socket.listen((data) {
        final response = String.fromCharCodes(data).trim();
        print('Response from server: $response');

        setState(() {
          _rankings.clear();
          if (response.isNotEmpty) {
            final rankingsList = response.split(',');
            for (var ranking in rankingsList) {
              final parts = ranking.split(':');
              if (parts.length == 2) {
                _rankings.add({'name': parts[0], 'grade': parts[1]});
              }
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

  Color? _getRankColor(int rank) {
    switch (rank) {
      case 0:
        return Colors.amber[400]; // Gold
      case 1:
        return Colors.grey[600]; // Silver
      case 2:
        return Colors.brown[400]; // Bronze
      default:
        return null; // No color for other ranks
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            _rankings.isEmpty
                ? Center(
              child: Text(
                "No rankings available",
                style: TextStyle(fontSize: 18, color: Colors.grey),
              ),
            )
                : ListView.builder(
              shrinkWrap: true,
              physics: NeverScrollableScrollPhysics(),
              itemCount: _rankings.length,
              itemBuilder: (context, index) {
                return Card(
                  child: ListTile(
                    leading: CircleAvatar(
                      backgroundColor: _getRankColor(index),
                      child: Text((index + 1).toString()),
                    ),
                    title: Text('${_rankings[index]['name']}'),
                    trailing: Text('${_rankings[index]['grade']}'),
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
