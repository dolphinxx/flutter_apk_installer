import 'package:flutter/material.dart';
import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';
import 'package:path_provider/path_provider.dart';
import 'package:apk_installer/apk_installer.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  @override
  void initState() {
    super.initState();
  }

  Future<void> install(String path) async {
    await ApkInstaller.install(path);
  }

  Future<void> _copyAndInstall(Directory dir) async {
    ByteData bytes = await rootBundle.load('assets/example.apk');
    File file = File('${dir.path}/flutter_apk_installer_example.apk');
    await file.create();
    final buffer = bytes.buffer;
    file.writeAsBytesSync(buffer.asUint8List(bytes.offsetInBytes, bytes.lengthInBytes));
    install(file.path);
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body:Column(
          children: <Widget>[
            ElevatedButton(
              child: Text('Install From App Dir'),
              onPressed: () async {
                Directory dir = await getApplicationDocumentsDirectory();
                _copyAndInstall(dir);
              },
            ),
            ElevatedButton(
              child: Text('Install From External Dir'),
              onPressed: () async {
                Directory dir = await getExternalStorageDirectory();
                _copyAndInstall(dir);
              },
            ),
            ElevatedButton(
              child: Text('Install From Cache Dir'),
              onPressed: () async {
                Directory dir = await getTemporaryDirectory();
                _copyAndInstall(dir);
              },
            ),
          ],
        ),
      ),
    );
  }
}
