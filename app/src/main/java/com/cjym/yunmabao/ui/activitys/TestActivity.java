package com.cjym.yunmabao.ui.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cjym.yunmabao.R;
import com.cjym.yunmabao.services.DownloadAndUpdateService;
import com.qihoo360.replugin.RePlugin;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        verifyStoragePermissions(this);
        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(TAG,"onclick");
                RePlugin.startActivity(TestActivity.this, RePlugin.createIntent("kejian",
                        "com.bdhs.bossplugone.MainActivity"));
            }
        });

        TextView tv2 = (TextView) findViewById(R.id.sample_text_2);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(TAG,"onclick");
                RePlugin.startActivity(TestActivity.this,
                        RePlugin.createIntent("image", "com.xq.imageplugindemo.MainActivity"));
            }
        });
    }

    public void onUpdateImagePlugin(View view) {
        // 插件下载地址
        String urlPath = "https://raw.githubusercontent.com/ZhangZeQiao/ImagePluginDemo/7c5866db83b57c455302fac12ea72af30d9a5364/app/src/main/assets/image.apk";
        // 插件下载后的存放路径
        String downloadDir = Environment.getExternalStorageDirectory().getAbsolutePath();

        Intent intent = new Intent(this, DownloadAndUpdateService.class);
        intent.putExtra("urlPath", urlPath);
        intent.putExtra("downloadDir", downloadDir);
        startService(intent);
    }
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
