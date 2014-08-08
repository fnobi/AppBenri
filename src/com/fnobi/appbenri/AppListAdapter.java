package com.fnobi.appbenri;

import java.util.List;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AppListAdapter extends ArrayAdapter<ResolveInfo> {
    private LayoutInflater mInflater;
    private List<ResolveInfo> mItems;
    
    public AppListAdapter(Context context, int viewResourceId, List<ResolveInfo> items) {
        super(context, viewResourceId, items);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItems = items;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.appbenri_layout_app_item, parent, false);
        }
        
        ResolveInfo ri = mItems.get(position);
        if (ri.activityInfo != null) {
            ((TextView) convertView.findViewById(R.id.appbenri_text_app_package)).setText(ri.activityInfo.packageName);
            ((TextView) convertView.findViewById(R.id.appbenri_text_app_activity)).setText(ri.activityInfo.name);
        }
        
        return convertView;
    }
}
