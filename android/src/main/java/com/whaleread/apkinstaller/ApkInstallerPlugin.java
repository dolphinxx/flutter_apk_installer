package com.whaleread.apkinstaller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * ApkInstallerPlugin
 */
public class ApkInstallerPlugin implements FlutterPlugin, MethodCallHandler {
    private Context applicationContext;
    private MethodChannel methodChannel;

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final ApkInstallerPlugin instance = new ApkInstallerPlugin();
        instance.onAttachedToEngine(registrar.context(), registrar.messenger());
    }

    public ApkInstallerPlugin() {
    }

    private void onAttachedToEngine(Context applicationContext, BinaryMessenger messenger) {
        this.applicationContext = applicationContext;
        this.methodChannel = new MethodChannel(messenger, "apk_installer");
        this.methodChannel.setMethodCallHandler(this);
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        this.onAttachedToEngine(flutterPluginBinding.getApplicationContext(), flutterPluginBinding.getBinaryMessenger());
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        applicationContext = null;
        methodChannel.setMethodCallHandler(null);
        methodChannel = null;
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("install")) {
            installPackage(call.<String>argument("path"), call.<String>argument("packageName"));
            result.success(null);
        } else {
            result.notImplemented();
        }
    }

    private void installPackage(String path, String packageName) {
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException(path + " not found!");
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        } else {
            Uri fileUri = FileProvider.getUriForFile(applicationContext,
                    packageName,
                    file);
            intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        applicationContext.startActivity(intent);
    }
}
