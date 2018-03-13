package com.example.permissiondemo.Permission;
/*
 * Created by ghp on 2018/3/12.
 */

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.PermissionChecker;

import java.lang.ref.WeakReference;

public class PermissionHelper {
    public final static int REQUEST_PERMISSION = 1;
    private PermissionsFragment mPermissionsFragment;
    private PermissionListener mPermissionListener;
    private WeakReference<FragmentActivity> mActivity;

    public PermissionHelper(FragmentActivity fragmentActivity) {
        if (fragmentActivity == null) {
            throw new RuntimeException("Activity不可为空");
        }
        mActivity = new WeakReference<>(fragmentActivity);
        mPermissionsFragment = PermissionsFragment.getPermissionsFragment(mActivity.get());
    }

    public void requestPermissions(String... permissions) {
        if (mActivity == null) {
            return;
        }
        if (mPermissionsFragment != null) {
            mPermissionsFragment.setPermissionListener(mPermissionListener);
        }
        if (selfPermissionGranted(mActivity.get(), permissions)) {
            mPermissionListener.grantedPermission();
            return;
        }
        mPermissionsFragment.requestPermissions(permissions, REQUEST_PERMISSION);
        //  Caused by: java.lang.IllegalStateException: Fragment PermissionsFragment{f75f9c6} not attached to Activity
         /*   mPermissionsFragment = new PermissionsFragment();
            mPermissionsFragment.requestPermissions(permissions, REQUEST_PERMISSION);*/
    }

    public boolean selfPermissionGranted(Context context, String... permissions) {
        boolean ret = true;
        int targetSdkVersion;
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            targetSdkVersion = 22;
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                ret = lacksPermissionsMoreM(permissions);
            } else {
                ret = lacksPermissions(permissions);
            }
        }
        return ret;
    }

    private boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (PermissionChecker.checkSelfPermission(mActivity.get(), permission) != PermissionChecker.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private boolean lacksPermissionsMoreM(String... permissions) {
        for (String permission : permissions) {
            if (PermissionChecker.checkSelfPermission(mActivity.get(), permission) != PermissionChecker.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public PermissionHelper setPermissionListener(PermissionListener permissionListener) {
        mPermissionListener = permissionListener;
        return this;
    }

    public interface PermissionListener {
        void grantedPermission();

        void deniedPermission();
    }
}
