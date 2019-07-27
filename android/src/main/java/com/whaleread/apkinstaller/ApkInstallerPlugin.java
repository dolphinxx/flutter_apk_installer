package com.whaleread.apkinstaller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.util.Map;

import androidx.core.content.FileProvider;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * ApkInstallerPlugin
 */
public class ApkInstallerPlugin implements MethodCallHandler {
    private Registrar registrar;

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "apk_installer");
        channel.setMethodCallHandler(new ApkInstallerPlugin(registrar));
    }

    public ApkInstallerPlugin(Registrar registrar) {
        this.registrar = registrar;
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
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
        Context context = registrar.context();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        } else {
            Uri fileUri = FileProvider.getUriForFile(context,
                    packageName,
                    file);
            intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
}
