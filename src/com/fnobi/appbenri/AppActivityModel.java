package com.fnobi.appbenri;

import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;

public class AppActivityModel {
    final private String mLabel;
    final private Drawable mIcon;
    final private String mPackageName;
    final private String mActivityName;
    final private Date mFirstInstallTime;
    
    public AppActivityModel(String label, Drawable icon, ResolveInfo ri, PackageInfo pi) {
        mLabel = label;
        mIcon = icon;
        mPackageName = ri.activityInfo.packageName;
        mActivityName = ri.activityInfo.name;
        mFirstInstallTime = new Date(pi.firstInstallTime);
    }
    
    public String getLabel() {
        return mLabel;
    }
    
    public Drawable getIcon() {
        return mIcon;
    }
    
    public String getPackageName() {
        return mPackageName;
    }
    
    public String getActivityName() {
        return mActivityName;
    }
    
    public Date getFirstInstallTime() {
        return mFirstInstallTime;
    }
    
    public void openActivity(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.setClassName(mPackageName, mActivityName);
        context.startActivity(intent);
    }
    
    public void openAppDetail(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + mPackageName));
        context.startActivity(intent);        
    }
}
