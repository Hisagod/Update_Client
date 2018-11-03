package com.aib.update;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;

/**
 * 使用时记得把新app，旧app，放到对应的路径
 */
public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("update");
    }

    //旧版本
    String old = getsdpath() + "/old.apk";
    //新版本
    String newp = getsdpath() + "/new.apk";
    //差分包
    String patch = getsdpath() + "/patch.patch";
    //旧版apk和差分包合并生成的新版apk
    String tmp = getsdpath() + "/patchNew.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //注意：因为是在本地进行差分/合并，需要动态获取存储的权限
        PermissionUtils.permission(PermissionConstants.STORAGE).callback(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {
                diffBehavior();
                patchBehavior();
            }

            @Override
            public void onDenied() {
                finish();
            }
        }).request();
    }

    //差分按钮
    public void diffBehavior() {
        Button btn_diff = findViewById(R.id.diff);
        btn_diff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diff();
            }
        });
    }

    //查分
    private void diff() {
        long s = System.currentTimeMillis();
        diff(old, newp, patch);
        long s1 = System.currentTimeMillis();
        Toast.makeText(MainActivity.this, "生成差分包成功，用时:" + (s1 - s) + "ms", Toast.LENGTH_LONG).show();
    }

    //合并按钮
    public void patchBehavior() {
        Button btn_patch = findViewById(R.id.patch);
        btn_patch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patch();
            }
        });
    }

    //合并
    private void patch() {
        long s2 = System.currentTimeMillis();
        patch(old, tmp, patch);
        long s3 = System.currentTimeMillis();
        Toast.makeText(MainActivity.this, "差分包合并成功，用时:" + (s3 - s2) + "ms", Toast.LENGTH_LONG).show();
    }

    private String getsdpath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

    //生成差分包
    public native int diff(String oldpath, String newpath, String patch);

    //旧apk和差分包合并
    public native int patch(String oldpath, String newpath, String patch);
}