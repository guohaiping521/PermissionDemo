package com.example.permissiondemo.PermissionActivity;
/*
 * Created by ghp on 2018/3/14.
 */


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.example.permissiondemo.BaseApplication;
import com.example.permissiondemo.Helper.PermissionHelper;
import com.example.permissiondemo.PermissionFragment.PermissionFragmentHelper;

import java.util.List;

public class BaseActivity extends FragmentActivity {
    public final static int REQUEST_PERMISSION = 1;
    private static OnPermissionCallback mCallback;

    public static void requestPermission(OnPermissionCallback onPermissionCallback, String... permissions) {
        Activity topActivity = BaseApplication.getTopActivity();
        if (topActivity == null) {
            return;
        }
        mCallback = onPermissionCallback;
        if (PermissionHelper.selfPermissionGranted(topActivity, permissions)) {
            mCallback.onGrantedPermission();
            return;
        }
        ActivityCompat.requestPermissions(topActivity, permissions, REQUEST_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionFragmentHelper.REQUEST_PERMISSION) {
            List<String> deniedPermissions = PermissionHelper.verifyPermissions(permissions, grantResults);
            if (deniedPermissions != null && deniedPermissions.size() > 0) {
                mCallback.onDeniedPermission(deniedPermissions);
            } else {
                mCallback.onGrantedPermission();
            }
        }
    }

    public interface OnPermissionCallback {
        void onGrantedPermission();

        void onDeniedPermission(List<String> deniedPermissions);
    }
}
