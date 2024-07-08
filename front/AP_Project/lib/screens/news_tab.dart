import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:http/http.dart' as http;
import 'package:html/parser.dart' as html_parser;

class NewsTab extends StatefulWidget {
  @override
  _NewsTabState createState() => _NewsTabState();
}

class _NewsTabState extends State<NewsTab> {
  final List<Map<String, String>> newsList = [
    {'url': 'https://www.tabnak.ir/fa/news/1246400'},
    {'url': 'https://www.tabnak.ir/fa/cultural'},
    {'url': 'https://www.tabnak.ir/fa/social'},
  ];

  @override
  void initState() {
    super.initState();
    _fetchTitles();
  }

  Future<void> _fetchTitles() async {
    for (var news in newsList) {
      final title = await _fetchTitle(news['url']!);
      setState(() {
        news['title'] = title;
      });
    }
  }

  Future<String> _fetchTitle(String url) async {
    try {
      final response = await http.get(Uri.parse(url));
      if (response.statusCode == 200) {
        var document = html_parser.parse(response.body);
        var title = document.getElementsByTagName('title').first.text;
        return title;
      } else {
        throw Exception('Failed to load web page');
      }
    } catch (e) {
      return 'Failed to fetch title';
    }
  }

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: newsList.length,
      itemBuilder: (context, index) {
        final news = newsList[index];
        return Card(margin:EdgeInsets.all(10),color: Colors.lightBlue[100],
          child: ListTile(hoverColor: Colors.cyan,iconColor: Colors.cyan,splashColor: Colors.cyan,selectedColor: Colors.cyan,
            title: Text(news['title'] ?? 'Loading...',textAlign: TextAlign.center,),
            onTap: () {
              _showNewsDialog(context, news['title']!, news['url']!);
            },
          ),
        );
      },
    );
  }

  void _showNewsDialog(BuildContext context, String title, String url) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text(title),
          content: Text('Do you want to read more?'),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop();
              },
              child: Text('Cancel'),
            ),
            TextButton(
              onPressed: () {
                Navigator.of(context).pop();
                _launchURL(url);
              },
              child: Text('Open'),
            ),
          ],
        );
      },
    );
  }

  void _launchURL(String url) async {
    if (await canLaunch(url)) {
      await launch(url);
    } else {
      throw 'Could not launch $url';
    }
  }
}
