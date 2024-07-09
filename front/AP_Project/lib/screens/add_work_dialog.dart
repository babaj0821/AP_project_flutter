import 'package:flutter/material.dart';

class AddWorkDialog extends StatefulWidget {
  @override
  _AddWorkDialogState createState() => _AddWorkDialogState();
}

class _AddWorkDialogState extends State<AddWorkDialog> {
  final TextEditingController _workController = TextEditingController();
  DateTime? _dueTime;
  TimeOfDay? _dueHour;

  void _pickDueTime() async {
    final DateTime? pickedDate = await showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime.now(),
      lastDate: DateTime(2101),
    );
    if (pickedDate != null) {
      final TimeOfDay? pickedTime = await showTimePicker(
        context: context,
        initialTime: TimeOfDay.now(),
      );
      if (pickedTime != null) {
        setState(() {
          _dueTime = pickedDate;
          _dueHour = pickedTime;
        });
      }
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
              Flexible(
                child: Text(
                  _dueTime == null || _dueHour == null
                      ? 'No due time'
                      : 'Due: ${_dueTime!.toString().split(' ')[0]} ${_dueHour!.format(context)}',
                  overflow: TextOverflow.ellipsis,
                ),
              ),
              Spacer(),
              TextButton(
                child: Text('Pick Date & Time'),
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
              'dueTime': _dueTime != null && _dueHour != null
                  ? '${_dueTime!.toString().split(' ')[0]} ${_dueHour!.format(context)}'
                  : null,
            });
          },
        ),
      ],
    );
  }
}