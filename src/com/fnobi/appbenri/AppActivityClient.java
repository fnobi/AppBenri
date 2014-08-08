package com.fnobi.appbenri;

public class AppActivityClient {
    final private String mLabel;
    final private String mPackageName;
    final private String mActivityName;
    
    public AppActivityClient(String label, String packageName, String activityName) {
        mLabel = label;
        mPackageName = packageName;
        mActivityName = activityName;
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
}
