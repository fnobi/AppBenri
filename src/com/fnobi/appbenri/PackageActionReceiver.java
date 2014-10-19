package com.fnobi.appbenri;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PackageActionReceiver extends BroadcastReceiver {
    
    private final static String TAG = PackageActionReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_PACKAGE_ADDED.equals(action)) {
            android.util.Log.v(TAG, action);
            refreshAppList(context);
        } else if (Intent.ACTION_PACKAGE_REMOVED.equals(action)) {
            android.util.Log.v(TAG, action);
            refreshAppList(context);
        }
    }
    
    public void refreshAppList(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MainActivity.EXTRAS_REFRESH, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
