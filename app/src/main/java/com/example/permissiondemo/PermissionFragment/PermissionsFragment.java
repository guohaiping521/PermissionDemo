package com.example.permissiondemo.PermissionFragment;
/*
 * Created by ghp on 2018/3/12.
 */

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.example.permissiondemo.Helper.PermissionHelper;
import com.example.permissiondemo.PermissionFragment.PermissionFragmentHelper.PermissionListener;

import java.util.List;

public class PermissionsFragment extends Fragment {

    public final static String FRAGMENT_TAG="fragment";

    private PermissionListener mPermissionListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("ghpppp", "PermissionsFragment onRequestPermissionsResult");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionFragmentHelper.REQUEST_PERMISSION) {
            List<String> deniedPermissions = PermissionHelper.verifyPermissions(permissions, grantResults);
            if (deniedPermissions != null && deniedPermissions.size() > 0) {
                mPermissionListener.deniedPermission(deniedPermissions);
            }else{
                mPermissionListener.grantedPermission();
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


    public void setPermissionListener(PermissionListener permissionListener) {
        mPermissionListener = permissionListener;
    }

}
