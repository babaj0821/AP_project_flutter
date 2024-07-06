import 'package:flutter/material.dart';

class AddWorkDialog extends StatefulWidget {
  @override
  _AddWorkDialogState createState() => _AddWorkDialogState();
}

class _AddWorkDialogState extends State<AddWorkDialog> {
  final TextEditingController _workController = TextEditingController();
  DateTime? _dueTime;

  void _pickDueTime() async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime.now(),
      lastDate: DateTime(2101),
    );
    if (picked != null && picked != _dueTime) {
      setState(() {
        _dueTime = picked;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text('Add Work'),
      content: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          TextField(
            controller: _workController,
            decoration: InputDecoration(labelText: 'Work'),
          ),
          SizedBox(height: 10),
          Row(
            children: [
              Text(_dueTime == null ? 'No due time' : 'Due: ${_dueTime.toString().split(' ')[0]}'),
              Spacer(),
              TextButton(
                child: Text('Pick Date'),
                onPressed: _pickDueTime,
              ),
            ],
          ),
        ],
      ),
      actions: [
        TextButton(
          child: Text('Cancel'),
          onPressed: () {
            Navigator.of(context).pop();
          },
        ),
        TextButton(
          child: Text('Add'),
          onPressed: () {
            Navigator.of(context).pop({
              'work': _workController.text,
              'dueTime': _dueTime?.toString().split(' ')[0],
            });
          },
        ),
      ],
    );
  }
}
