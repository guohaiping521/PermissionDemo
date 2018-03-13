package com.example.permissiondemo.Permission;
/*
 * Created by ghp on 2018/3/12.
 */

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.example.permissiondemo.Permission.PermissionHelper.PermissionListener;

public class PermissionsFragment extends Fragment {

    public final static String FRAGMENT_TAG="fragment";

    private PermissionListener mPermissionListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("ghpppp", "PermissionsFragment onRequestPermissionsResult");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionHelper.REQUEST_PERMISSION) {
            if (verifyPermissions(grantResults)) {
                mPermissionListener.grantedPermission();
            }else{
                mPermissionListener.deniedPermission();
            }
        }
    }

    public static PermissionsFragment getPermissionsFragment(FragmentActivity fragmentActivity){
        FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();
        PermissionsFragment fragment = (PermissionsFragment) supportFragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if(fragment==null){
            fragment=new PermissionsFragment();
            supportFragmentManager.beginTransaction().add(fragment,FRAGMENT_TAG).commitAllowingStateLoss();
            supportFragmentManager.executePendingTransactions();
        }
        return fragment;
    }

    public static boolean verifyPermissions(int[] grantResults) {
        if (grantResults.length < 1) {
            return false;
        }
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    public void setPermissionListener(PermissionListener permissionListener) {
        mPermissionListener = permissionListener;
    }

}
