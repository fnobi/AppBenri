package com.fnobi.appbenri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class SearchAppActivityFragment extends Fragment implements OnItemSelectedListener {
    
    private static String[] sActionList = {
            Intent.ACTION_MAIN,
            Intent.ACTION_VIEW,
            Intent.ACTION_GET_CONTENT
    };
    
    private List<AppActivityModel> mAppActivityList;
    private ListView mListView;
    private Spinner mSpinner;
    private String mActionFilter;
    private AppListAdapter mAdapter;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.appbenri_fragment_search_app_activity, container, false);
        
        mListView = (ListView) rootView.findViewById(R.id.appbenri_listview);
        mSpinner = (Spinner) rootView.findViewById(R.id.appbenri_spinner_app_activity_search);
        
        setupSpinner();
        
        return rootView;
    }
    
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mActionFilter = sActionList[position];
        setupListView();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        
    }

    private void setupSpinner() {
        
        mSpinner.setAdapter(new SpinnerAdapter() {
            
            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void registerDataSetObserver(DataSetObserver observer) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public boolean isEmpty() {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public boolean hasStableIds() {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public int getViewTypeCount() {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Context context = parent.getContext();
                String label = (String) getItem(position);
                TextView textView = new TextView(context);
                textView.setText(label);
                return textView;
            }
            
            @Override
            public int getItemViewType(int position) {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public long getItemId(int position) {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public Object getItem(int position) {
                return sActionList[position];
            }
            
            @Override
            public int getCount() {
                return sActionList.length;
            }
            
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                return getView(position, convertView, parent);
            }
        });
        
        mSpinner.setOnItemSelectedListener(this);
    }
    
    private List<AppActivityModel> loadAppActivityList(String action) {
        Activity activity = this.getActivity();
        PackageManager pm = activity.getPackageManager();
        Intent intent = new Intent(action, null);
        List<ResolveInfo> activityInfoList = pm.queryIntentActivities(intent, 0);
        
        List<AppActivityModel> modelList = new ArrayList<AppActivityModel>();
        for (ResolveInfo ri : activityInfoList) {
            String label = (String) ri.loadLabel(pm);
            String packageName = ri.activityInfo.packageName;
            Drawable icon = ri.loadIcon(pm);
            
            PackageInfo pi = null;
            try {
                pi = pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
            } catch (NameNotFoundException e) { /* do nothing */ }
            if (pi == null) {
                continue;
            }
            
            modelList.add(new AppActivityModel(label, icon, ri, pi));
        }
        
        Collections.sort(modelList, new Comparator<AppActivityModel>() {
            @Override
            public int compare(AppActivityModel model1, AppActivityModel model2) {
                return -model1.getFirstInstallTime().compareTo(model2.getFirstInstallTime());
            }
        });
        
        return modelList;
    }
    
    public void setupListView() {
        if (mActionFilter == null) {
            return;
        }
        mAppActivityList = loadAppActivityList(mActionFilter);
        initAppList();
    }

    private void initAppList() {
        filterAppList(null);
    }
    
    private void filterAppList(String searchText) {
        if (mAppActivityList == null) {
            return;
        }
        
        List<AppActivityModel> list;
        if (searchText != null) {
            list = new ArrayList<AppActivityModel>();
            for (AppActivityModel model : mAppActivityList) {
                if (model.getPackageName().indexOf(searchText) == 0) {
                    list.add(model);
                }
            }
        } else {
            list = mAppActivityList;
        }
        
        Activity activity = this.getActivity();
        mAdapter = new AppListAdapter(activity, 0, list);
        mListView.setAdapter(mAdapter);
    }
}
