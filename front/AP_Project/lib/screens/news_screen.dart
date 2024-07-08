import 'package:flutter/material.dart';
import 'news_tab.dart';
import 'birthdays_tab.dart';
import 'rankings_tab.dart';

class NewsScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 3,
      child: Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.cyan[700],
          toolbarHeight: 5, // Set the desired height for the AppBar
          elevation: 50, // Remove the shadow if desired
            automaticallyImplyLeading: false,
          bottom: TabBar(
            tabs: [
              Tab(text: 'News',),
              Tab(text: 'Birthdays'),
              Tab(text: 'Rankings'),
            ],labelColor: Colors.white,
            unselectedLabelColor: Colors.white,
          ),
        ),
        body: TabBarView(
          children: [
            NewsTab(),
            BirthdaysTab(),
            RankingsTab(),
          ],
        ),
      ),
    );
  }
}
