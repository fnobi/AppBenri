package com.fnobi.appbenri;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AppListAdapter extends ArrayAdapter<AppActivityClient> {
    private LayoutInflater mInflater;
    private List<AppActivityClient> mItems;
    
    public AppListAdapter(Context context, int viewResourceId, List<AppActivityClient> items) {
        super(context, viewResourceId, items);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItems = items;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.appbenri_layout_app_item, parent, false);
        }
        
        final AppActivityClient ri = mItems.get(position);
        ((TextView) convertView.findViewById(R.id.appbenri_text_app_label)).setText(ri.getLabel());
        ((TextView) convertView.findViewById(R.id.appbenri_text_app_package)).setText(ri.getPackageName());
        ((TextView) convertView.findViewById(R.id.appbenri_text_app_activity)).setText(ri.getActivityName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ri.startActivity(v.getContext());
            }
        });
        
        return convertView;
    }
}
