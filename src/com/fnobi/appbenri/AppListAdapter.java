package com.fnobi.appbenri;

import java.util.List;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppListAdapter extends ArrayAdapter<AppActivityModel> {
    private LayoutInflater mInflater;
    private List<AppActivityModel> mItems;
    private SparseBooleanArray mVisibilityFlags;
    
    public AppListAdapter(Context context, int viewResourceId, List<AppActivityModel> items) {
        super(context, viewResourceId, items);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItems = items;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.appbenri_item_app_activity, parent, false);
        }
        
        final AppActivityModel client = mItems.get(position);
        ((TextView) convertView.findViewById(R.id.appbenri_text_app_label)).setText(client.getLabel());
        ((TextView) convertView.findViewById(R.id.appbenri_text_app_package)).setText(client.getPackageName());
        
        TextView textActivityName = (TextView) convertView.findViewById(R.id.appbenri_text_app_activity);
        if (mVisibilityFlags != null && mVisibilityFlags.get(R.id.appbenri_check_app_package_name)) {
            textActivityName.setVisibility(View.VISIBLE);
            textActivityName.setText(client.getActivityName());
        } else {
            textActivityName.setVisibility(View.GONE);
        }
        
        TextView textInstallDate = (TextView) convertView.findViewById(R.id.appbenri_text_app_install_date);
        if (mVisibilityFlags != null && mVisibilityFlags.get(R.id.appbenri_check_app_install_date)) {
            textInstallDate.setVisibility(View.VISIBLE);
            textInstallDate.setText(client.getFirstInstallTime().toString());
        } else {
            textInstallDate.setVisibility(View.GONE);
        }
        
        ImageView imageView = (ImageView) convertView.findViewById(R.id.appbenri_imageview_app_icon);
        imageView.setImageDrawable(client.getIcon());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.openActivity(v.getContext());
            }
        });
        
        ImageView storeButton = (ImageView) convertView.findViewById(R.id.appbenri_button_store);
        storeButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                client.openStore(v.getContext());
            }
        });
        
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.openAppDetail(v.getContext());
            }
        });
        
        return convertView;
    }
    
    public void setVisibilityFlags(SparseBooleanArray flags) {
        mVisibilityFlags = flags;
    }
}
