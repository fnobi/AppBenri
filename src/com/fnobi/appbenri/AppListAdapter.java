package com.fnobi.appbenri;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppListAdapter extends ArrayAdapter<AppActivityModel> {
    private LayoutInflater mInflater;
    private List<AppActivityModel> mItems;
    
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
        ((TextView) convertView.findViewById(R.id.appbenri_text_app_activity)).setText(client.getActivityName());
        
        ImageView imageView = (ImageView) convertView.findViewById(R.id.appbenri_imageview_app_icon);
        imageView.setImageDrawable(client.getIcon());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.openActivity(v.getContext());
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
}
