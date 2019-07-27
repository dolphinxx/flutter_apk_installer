import 'dart:async';

import 'package:flutter/services.dart';

class ApkInstaller {
  static const MethodChannel _channel =
      const MethodChannel('apk_installer');

  static Future<dynamic> install(String path, {String packageName: 'com.damnread.damnread.fileprovider'}) async {
//    print('apk path:$path');
    return _channel
        .invokeMethod('install', {'path': path, 'packageName': packageName});
  }
}
