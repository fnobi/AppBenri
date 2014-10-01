package com.fnobi.appbenri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;

public class SearchAppActivityFragment extends Fragment implements TextWatcher, OnCheckedChangeListener {
    
    private List<AppActivityModel> mAppActivityList;
    private ListView mListView;
    private EditText mEditText;
    private SparseBooleanArray mVisiblityFlags;
    private AppListAdapter mAdapter; 
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.appbenri_fragment_search_app_activity, container, false);
        
        mListView = (ListView) rootView.findViewById(R.id.appbenri_listview);
        mEditText = (EditText) rootView.findViewById(R.id.appbenri_edittext_app_search);
        setupEditText();
        
        setupCheckBoxes(rootView);
        
        return rootView;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        
        setupListView();
    }
    
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        filterAppList(s.toString());
    }
    
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // do nothing
    }
    
    @Override
    public void afterTextChanged(Editable s) {
        // do nothing
    }
    
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mVisiblityFlags.put(buttonView.getId(), isChecked);
        if (mAdapter != null) {
            mAdapter.setVisibilityFlags(mVisiblityFlags);
            mAdapter.notifyDataSetChanged();
        }
    }
    
    private List<AppActivityModel> loadAppActivityList() {
        Activity activity = this.getActivity();
        PackageManager pm = activity.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
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
    
    private void setupListView() {
        mAppActivityList = loadAppActivityList();
        initAppList();
    }

    private void setupEditText() {
        assert(mEditText != null);
        mEditText.addTextChangedListener(this);
    }
    
    private void setupCheckBoxes(View rootView) {
        mVisiblityFlags = new SparseBooleanArray();
        int[] ids = {
            R.id.appbenri_check_app_install_date,
            R.id.appbenri_check_app_package_name
        };
        for (final int id : ids) {
            CheckBox checkBox = (CheckBox) rootView.findViewById(id);
            checkBox.setOnCheckedChangeListener(this);
            
            mVisiblityFlags.put(id, checkBox.isChecked());
        }
    }
    
    private void initAppList() {
        filterAppList(mEditText.getText().toString());
    }
    
    private void filterAppList(String searchText) {
        if (mAppActivityList == null) {
            return;
        }
        
        List<AppActivityModel> list = new ArrayList<AppActivityModel>();
        for (AppActivityModel model : mAppActivityList) {
            if (model.getPackageName().indexOf(searchText) == 0) {
                list.add(model);
            }
        }
        
        Activity activity = this.getActivity();
        mAdapter = new AppListAdapter(activity, 0, list);
        mListView.setAdapter(mAdapter);
    }
}
