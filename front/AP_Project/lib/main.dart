import 'package:ap_project/screens/signin_screen.dart';
import 'package:flutter/material.dart';
import 'package:ap_project/screens/welcome_screen.dart';
import 'package:ap_project/theme/theme.dart';
import 'package:ap_project/screens/user_profile_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData.light().copyWith(
        primaryColor: Colors.lightBlue,
        appBarTheme: AppBarTheme(
          color: Colors.lightBlue,
        ),
      ),
      initialRoute: '/',
      routes: {
        '/': (context) => WelcomeScreen(),
        '/profile': (context) => UserProfilePage(),
      },
    );
  }
}
