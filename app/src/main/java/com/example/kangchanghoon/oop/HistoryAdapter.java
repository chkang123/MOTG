package com.example.kangchanghoon.oop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KangChanghoon on 2016. 12. 3..
 */

public class HistoryAdapter extends BaseExpandableListAdapter {
    private Context context;
    private int groupLayout = 0;
    private int chlidLayout = 0;
    private ArrayList<HistoryList> DataList;
    private LayoutInflater myinf = null;

    public HistoryAdapter(Context context,int groupLay,int chlidLay,ArrayList<HistoryList> DataList){
        this.DataList = DataList;
        this.groupLayout = groupLay;
        this.chlidLayout = chlidLay;
        this.context = context;
        this.myinf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView == null){
            convertView = myinf.inflate(this.groupLayout, parent, false);
        }
        TextView groupName = (TextView)convertView.findViewById(R.id.groupName);
        groupName.setText(DataList.get(groupPosition).year+"."+DataList.get(groupPosition).month+"."
                +DataList.get(groupPosition).day);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView == null){
            convertView = myinf.inflate(this.chlidLayout, parent, false);
        }
        TextView childName = (TextView)convertView.findViewById(R.id.hchildName);
        childName.setText(DataList.get(groupPosition).child.get(childPosition));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return DataList.get(groupPosition).child.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return DataList.get(groupPosition).child.size();
    }

    @Override
    public HistoryList getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return DataList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return DataList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }
}
