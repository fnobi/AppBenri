package com.fnobi.appbenri;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

public class AppActivityModel {
    final private String mLabel;
    final private String mPackageName;
    final private String mActivityName;
    
    public AppActivityModel(String label, ResolveInfo ri) {
        mLabel = label;
        mPackageName = ri.activityInfo.packageName;
        mActivityName = ri.activityInfo.name;
    }
    
    public String getLabel() {
        return mLabel;
    }
    
    public String getPackageName() {
        return mPackageName;
    }
    
    public String getActivityName() {
        return mActivityName;
    }
    
    public void startActivity(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.setClassName(mPackageName, mActivityName);
        context.startActivity(intent);
    }
}
