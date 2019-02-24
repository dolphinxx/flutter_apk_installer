# apk_installer

A new Flutter plugin for android APK installation.

## Usage
First download the APK to the app dir/external dir/cache dir of the device, then call
`ApkInstaller.install(url)` to invoke installation.

## Note
add `<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>` to `AndroidManifest.xml` if the APK is allocated in external dir.