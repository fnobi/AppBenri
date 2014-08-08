package com.fnobi.appbenri;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SearchAppActivityFragment extends Fragment {

    public SearchAppActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.appbenri_fragment_main, container, false);
        
        Activity activity = this.getActivity();
        PackageManager pm = activity.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        List<ResolveInfo> activityInfoList = pm.queryIntentActivities(intent, 0);
        
        List<AppActivityClient> clientList = new ArrayList<AppActivityClient>();
        for (ResolveInfo ri : activityInfoList) {
            String label = (String) ri.loadLabel(pm);
            String packageName = ri.activityInfo.packageName;
            String activityName = ri.activityInfo.name;
            clientList.add(new AppActivityClient(label, packageName, activityName));
        }
        
        ListView listView = (ListView) rootView.findViewById(R.id.appbenri_listview);
        AppListAdapter adapter = new AppListAdapter(activity, 0, clientList);
        listView.setAdapter(adapter);
        
        return rootView;
    }

}
