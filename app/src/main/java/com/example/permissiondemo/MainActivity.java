package com.example.permissiondemo;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.permissiondemo.PermissionActivity.BaseActivity;
import com.example.permissiondemo.PermissionFragment.PermissionFragmentHelper;

import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "ghppp";
    private static String[] PERMISSIONS_CALENDAR = {Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.apply_permission_fragment_btn).setOnClickListener(this);
        findViewById(R.id.apply_permission_activity_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.apply_permission_fragment_btn:
                PermissionFragmentHelper permissionHelper = new PermissionFragmentHelper(MainActivity.this);
                permissionHelper.setPermissionListener(new PermissionFragmentHelper.PermissionListener() {
                    @Override
                    public void grantedPermission() {
                        Log.i(TAG, "权限success");
                        Toast.makeText(MainActivity.this, "权限申请成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void deniedPermission(List<String> deniedPermissionList) {
                        for (String deniedPermission : deniedPermissionList) {
                            Log.i(TAG, "权限fail===" + deniedPermission);
                        }
                        Toast.makeText(MainActivity.this, "权限申请失败", Toast.LENGTH_SHORT).show();
                    }
                }).requestPermissions(PERMISSIONS_CALENDAR);
                break;
            case R.id.apply_permission_activity_btn:
                BaseActivity.requestPermission(PERMISSIONS_CALENDAR, new OnPermissionCallback() {
                    @Override
                    public void onGrantedPermission() {
                        Log.i(TAG, "权限success");
                        Toast.makeText(MainActivity.this, "权限申请成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDeniedPermission(List<String> deniedPermissions) {
                        for (String deniedPermission : deniedPermissions) {
                            Log.i(TAG, "权限fail===" + deniedPermission);
                        }
                        Toast.makeText(MainActivity.this, "权限申请失败", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
