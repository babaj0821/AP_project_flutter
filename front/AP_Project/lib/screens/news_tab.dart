import 'package:flutter/material.dart';
//import 'package:webview_flutter/webview_flutter.dart';
import 'dart:io';

class NewsTab extends StatelessWidget {
  final List<Map<String, String>> newsList = [
    {'title': 'News 1', 'url': 'https://www.tabnak.ir/fa/news/1246400'},
    {'title': 'News 2', 'url': 'https://www.tabnak.ir/fa/cultural'},
    {'title': 'News 3', 'url': 'https://www.tabnak.ir/fa/social'},
  ];

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: newsList.length,
      itemBuilder: (context, index) {
        return Card(
          child: ListTile(
            title: Text(newsList[index]['title']!),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => NewsDetailScreen(url: newsList[index]['url']!),
                ),
              );
            },
          ),
        );
      },
    );
  }
}

class NewsDetailScreen extends StatelessWidget {
  final String url;

  NewsDetailScreen({required this.url});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('News Detail'),
      ),
      // body: WebView(
      //   initialUrl: url,
      //   javascriptMode: JavascriptMode.unrestricted,
      // ),
    );
  }
}
