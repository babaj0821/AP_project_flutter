import 'package:flutter/material.dart';

class NewsScreen extends StatefulWidget {
  @override
  _NewsScreenState createState() => _NewsScreenState();
}

class _NewsScreenState extends State<NewsScreen> with SingleTickerProviderStateMixin {
  late TabController _tabController;

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 5, vsync: this);
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('خبرها'),
        bottom: TabBar(
          controller: _tabController,
          isScrollable: true,
          tabs: [
            Tab(text: 'اخبار'),
            Tab(text: 'یادآوری‌ها'),
            Tab(text: 'تولدهای امروز'),
            Tab(text: 'رویدادها'),
            Tab(text: 'تمدیدها'),
          ],
        ),
      ),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Text('امروز (16 اردیبهشت)', style: TextStyle(fontSize: 18)),
          ),
          Expanded(
            child: TabBarView(
              controller: _tabController,
              children: [
                _buildNewsTab(),
                _buildPlaceholderTab('یادآوری‌ها'),
                _buildPlaceholderTab('تولدهای امروز'),
                _buildPlaceholderTab('رویدادها'),
                _buildPlaceholderTab('تمدیدها'),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildNewsTab() {
    return ListView(
      children: [
        _buildNewsCard(
          'اطلاعیه آموزشی',
          'قابل توجه دانشجویان دکترا ورودی 98 امکان حذف یک نیمسال بدون احتساب در سنوات...',
          'assets/images/university.png',
        ),
        _buildNewsCard(
          'اطلاعیه آموزشی',
          'قابل توجه دانشجویان دکترا ورودی 98 امکان حذف یک نیمسال بدون احتساب در سنوات...',
          'assets/images/university.png',
        ),
        // Add more cards as needed
      ],
    );
  }

  Widget _buildNewsCard(String title, String description, String imagePath) {
    return Card(
      margin: EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Image.asset(imagePath),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Text(
              title,
              style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold, color: Colors.pink),
            ),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 8.0),
            child: Text(description),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Text(
              'مطالعه بیشتر...',
              style: TextStyle(color: Colors.blue),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildPlaceholderTab(String title) {
    return Center(child: Text('$title tab content goes here'));
  }
}
