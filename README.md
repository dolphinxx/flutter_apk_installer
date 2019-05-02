# apk_installer

A new Flutter plugin for android APK installation.

## Usage
First download the APK to the app dir/external dir/cache dir of the device, then call
`ApkInstaller.install(url)` to invoke installation.

## Note
add `<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>` to `AndroidManifest.xml` if the APK is allocated in external dir.

add following to AndroidManifest.xml(under application tag)
```
<provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="com.whaleread.apkinstaller.provider"
            android:exported="false"
            android:grantUriPermissions="true" >
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>
```

create a `file_paths.xml` in android/src/main/res/xml with following contents
```
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <root-path name="sdcard1" path="." />
    <external-path name="external" path="." />
    <external-files-path name="external_files" path="." />
    <files-path name="files" path="." />
    <cache-path name="cache" path="." />
</paths>
```