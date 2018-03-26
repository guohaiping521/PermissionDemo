package com.example.permissiondemo;

import android.Manifest;
import android.content.DialogInterface;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.permissiondemo.Helper.PermissionHelper;
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
                        PermissionDenied();
                    }
                }).requestPermissions(PERMISSIONS_CALENDAR);
                break;
            case R.id.apply_permission_activity_btn:
                BaseActivity.requestPermission(new OnPermissionCallback() {
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
                }, PERMISSIONS_CALENDAR);
                break;
        }
    }

    public void PermissionDenied() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.permission_explain))
                .setCancelable(false)
                .setPositiveButton(R.string.per_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionHelper.openApplicationSettings(getApplicationContext(), getPackageName());
                    }
                })
                .setNegativeButton(R.string.per_cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "点击了取消", Toast.LENGTH_SHORT).show();
                    }
                }).create();
        alertDialog.show();
    }

    /**
     * 返回true 表示可以使用  返回false表示不可以使用
     * 魅族手机/华为6.0以下手机，在拍照权限被拒绝后都会走异常处理
     */
    public boolean cameraIsCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            isCanUse = false;
            Log.i(TAG, "拍照权限已禁止");
        }

        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
                return isCanUse;
            }
        }
        return isCanUse;
    }
}
