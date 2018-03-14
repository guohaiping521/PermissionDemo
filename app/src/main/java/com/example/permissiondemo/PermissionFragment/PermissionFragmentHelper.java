package com.example.permissiondemo.PermissionFragment;
/*
 * Created by ghp on 2018/3/12.
 */

import android.support.v4.app.FragmentActivity;

import com.example.permissiondemo.Helper.PermissionHelper;

import java.lang.ref.WeakReference;
import java.util.List;

public class PermissionFragmentHelper {
    public final static int REQUEST_PERMISSION = 1;
    private PermissionsFragment mPermissionsFragment;
    private PermissionListener mPermissionListener;
    private WeakReference<FragmentActivity> mActivity;

    public PermissionFragmentHelper(FragmentActivity fragmentActivity) {
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
        if (PermissionHelper.selfPermissionGranted(mActivity.get(), permissions)) {
            mPermissionListener.grantedPermission();
            return;
        }
        mPermissionsFragment.requestPermissions(permissions, REQUEST_PERMISSION);
        //  Caused by: java.lang.IllegalStateException: Fragment PermissionsFragment{f75f9c6} not attached to Activity
         /*   mPermissionsFragment = new PermissionsFragment();
            mPermissionsFragment.requestPermissions(permissions, REQUEST_PERMISSION);*/
    }

    public PermissionFragmentHelper setPermissionListener(PermissionListener permissionListener) {
        mPermissionListener = permissionListener;
        return this;
    }

    public interface PermissionListener {
        void grantedPermission();

        void deniedPermission(List<String> deniedPermissionList);
    }
}
