import 'dart:async';

import 'package:flutter/services.dart';

class ApkInstaller {
  static const MethodChannel _channel =
      const MethodChannel('apk_installer');

  static Future<dynamic> install(String path) async {
    print('apk path:$path');
    return _channel
        .invokeMethod('install', {'path': path});
  }
}
