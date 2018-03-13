package com.example.permissiondemo;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.permissiondemo.Permission.PermissionHelper;

public class MainActivity extends FragmentActivity {

    private static String[] PERMISSIONS_CALENDAR = {Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.apply_permission_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionHelper permissionHelper=new PermissionHelper(MainActivity.this);
                permissionHelper.setPermissionListener(new PermissionHelper.PermissionListener() {
                    @Override
                    public void grantedPermission() {
                        Log.i("ghpppp", "权限success");
                        Toast.makeText(MainActivity.this,"权限申请成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void deniedPermission() {
                        Log.i("ghpppp", "权限fail");
                        Toast.makeText(MainActivity.this,"权限申请失败",Toast.LENGTH_SHORT).show();
                    }
                }).requestPermissions(PERMISSIONS_CALENDAR);
            }
        });
    }
}
