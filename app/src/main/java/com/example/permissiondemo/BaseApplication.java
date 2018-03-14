package com.example.permissiondemo;
/*
 * Created by ghp on 2018/3/14.
 */

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private static List<Activity> mListActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        mListActivity = new ArrayList<Activity>();
        registerActivityLifecycleCallbacks(this);
    }

    public static Activity getTopActivity() {
        if (mListActivity.isEmpty()) {
            return null;
        }
        return mListActivity.get(mListActivity.size() - 1);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.i(MainActivity.TAG, "onActivityCreated");
        mListActivity.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.i(MainActivity.TAG, "onActivityDestroyed");
        activity.finish();
        mListActivity.remove(activity);
    }
}
