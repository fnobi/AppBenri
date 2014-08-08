package com.fnobi.appbenri;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appbenri_activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
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

}
