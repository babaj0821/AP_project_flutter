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
          title: Text('News App'),
          bottom: TabBar(
            tabs: [
              Tab(text: 'News'),
              Tab(text: 'Birthdays'),
              Tab(text: 'Rankings'),
            ],
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
