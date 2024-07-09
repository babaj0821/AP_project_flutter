import 'package:flutter/material.dart';
import 'package:ap_project/student.dart';
import 'dart:io';

import 'signup_screen.dart';

class ProfileScreen extends StatefulWidget {
  @override
  _ProfileScreenState createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {
  String name = '';
  String role = '';
  String studentNumber = '';
  String currentTerm = '';
  String totalUnits = '';
  String totalAverage = '';
  String password = '';
  late TextEditingController _passwordController;
  bool _passwordVisible = false;

  @override
  void initState() {
    super.initState();
    _passwordController = TextEditingController();
    _fetchProfileData();
  }

  Future<void> _fetchProfileData() async {
    try {
      final socket = await Socket.connect('192.168.43.66', 8888);
      print('Connected to: ${socket.remoteAddress.address}:${socket.remotePort}');

      socket.write('$globalUsername-profile\u0000');
      await socket.flush();
      print('Data request sent to server: profile-data\u0000');

      socket.listen(
            (data) {
          final response = String.fromCharCodes(data).trim();
          print('Response from server: $response');

          // Assuming the server sends data in the format: name-role-studentNumber-currentTerm-totalUnits-totalAverage-password
          final profileData = response.split('-');
          if (profileData.length == 7) {
            setState(() {
              name = profileData[0];
              role = profileData[1];
              studentNumber = profileData[2];
              currentTerm = profileData[3];
              totalUnits = profileData[4];
              totalAverage = profileData[5];
              password = profileData[6];
              _passwordController.text = password;
            });
          } else {
            print('Unexpected data format from server');
          }
          socket.destroy();
        },
        onError: (error) {
          print('Error: $error');
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('Error: $error')),
          );
          socket.destroy();
        },
        onDone: () {
          print('Connection closed by server');
          socket.destroy();
        },
        cancelOnError: true,
      );
    } catch (e) {
      print('Error: $e');
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e')),
      );
    }
  }

  Future<void> _updatePassword(String newPassword) async {
    try {
      final socket = await Socket.connect('192.168.43.66', 8888);
      print('Connected to: ${socket.remoteAddress.address}:${socket.remotePort}');

      socket.write('$globalUsername-update_password-$newPassword\u0000');
      await socket.flush();
      print('Password update sent to server: update-password-$newPassword\u0000');


      socket.listen(
            (data) {
          final response = String.fromCharCodes(data).trim();
          print('Response from server: $response');
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text(response)),
          );
          socket.destroy();
        },
        onError: (error) {
          print('Error: $error');
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('Error: $error')),
          );
          socket.destroy();
        },
        onDone: () {
          print('Connection closed by server');
          socket.destroy();
        },
        cancelOnError: true,
      );
    } catch (e) {
      print('Error: $e');
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e')),
      );
    }
  }

  Future<void> _deleteAccount() async {
    bool? confirm = await showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Delete Account'),
          content: Text('Are you sure you want to delete your account? This action cannot be undone.'),
          actions: <Widget>[
            TextButton(
              child: Text('Cancel'),
              onPressed: () {
                Navigator.of(context).pop(false);
              },
            ),
            TextButton(
              child: Text('Delete'),
              onPressed: () {
                Navigator.of(context).pop(true);
              },
            ),
          ],
        );
      },
    );

    if (confirm == true) {
      try {
        final socket = await Socket.connect('192.168.43.66', 8888);
        print('Connected to: ${socket.remoteAddress.address}:${socket.remotePort}');
        socket.write('$globalUsername-delete_account\u0000');
        await socket.flush();
        print('Delete account request sent to server');

        socket.destroy();

        Navigator.pushReplacement(
          context,
          MaterialPageRoute(builder: (context) => SignUpScreen()),
        );
      } catch (e) {
        print('Error: $e');
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Error: $e')),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: SingleChildScrollView(
          child: Column(
            children: [
              SizedBox(height: 20),
              CircleAvatar(
                radius: 50,
                backgroundImage: AssetImage('assets/images/prof.png'), // Add your image asset here
              ),
              SizedBox(height: 10),
              Text(
                name,
                style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
              ),
              Text(role),
              SizedBox(height: 20),
              Card(
                margin: EdgeInsets.symmetric(horizontal: 20, vertical: 10),
                child: Padding(
                  padding: EdgeInsets.all(16),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      _buildInfoRow('شماره دانشجویی', studentNumber),
                      _buildInfoRow('ترم جاری', currentTerm),
                      _buildInfoRow('تعداد واحد', totalUnits),
                      _buildInfoRow('معدل کل', totalAverage),
                      _buildPasswordField(),
                    ],
                  ),
                ),
              ),
              SizedBox(height: 20),
              ElevatedButton(
                onPressed: () {
                  _updatePassword(_passwordController.text);
                },
                child: Text('Update Password',style: TextStyle(
                  color: Colors.black, // Set the desired color
                ),),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.green[300],
                ),
              ),
              SizedBox(height: 20),
              ElevatedButton(
                onPressed: _deleteAccount,
                child: Text('Delete Account',style: TextStyle(
                  color: Colors.black,
                ),),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.red[300],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildInfoRow(String label, String value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(
            label,
            style: TextStyle(fontSize: 16, fontWeight: FontWeight.w500),
          ),
          Text(
            value,
            style: TextStyle(fontSize: 16),
          ),
        ],
      ),
    );
  }

  Widget _buildPasswordField() {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8.0),
      child: TextField(
        controller: _passwordController,
        decoration: InputDecoration(
          labelText: 'Password',
          border: OutlineInputBorder(),
          suffixIcon: IconButton(
            icon: Icon(
              _passwordVisible ? Icons.visibility : Icons.visibility_off,
            ),
            onPressed: () {
              setState(() {
                _passwordVisible = !_passwordVisible;
              });
            },
          ),
        ),
        obscureText: !_passwordVisible,
      ),
    );
  }
}
