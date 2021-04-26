package com.example.pruebasrecordatorios.expandableList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.pruebasrecordatorios.R;

import java.util.HashMap;
import java.util.List;

public class AdapterExpandable extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitulo;
    private HashMap<String,List<String>> expandableListDetalle;
    public AdapterExpandable(Context context, List<String> expandableListTitulo,HashMap<String,List<String>> expandableListDetalle){
        this.context = context;
        this.expandableListTitulo = expandableListTitulo;
        this.expandableListDetalle = expandableListDetalle;
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableListDetalle.get(this.expandableListTitulo.get(groupPosition))
                .get(childPosition);
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(groupPosition,childPosition);
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item,null);
        }
        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.expandidosItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.expandableListDetalle.get(this.expandableListTitulo.get(groupPosition)).size();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return this.expandableListTitulo.get(groupPosition);
    }
    @Override
    public int getGroupCount() {
        return this.expandableListTitulo.size();
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(groupPosition);
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group,null);
        }
        TextView lisTitleTextView = (TextView) convertView.findViewById(R.id.listTitulo);
        lisTitleTextView.setTypeface(null, Typeface.BOLD);
        lisTitleTextView.setText(listTitle);
        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
