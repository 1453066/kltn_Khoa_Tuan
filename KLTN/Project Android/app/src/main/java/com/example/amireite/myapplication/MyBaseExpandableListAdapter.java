package com.example.amireite.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Amireite on 2/1/2018.
 */

public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> ListGroup;
    private HashMap<String, List<String>> ListChild;


    public MyBaseExpandableListAdapter(Context c, List<String> lg, HashMap<String, List<String>> lc) {
        context = c;
        ListGroup = lg;
        ListChild = lc;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ListChild.get(ListGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.main_page_expandable_list_item, null);
        }
        TextView txtItem = (TextView)convertView.findViewById(R.id.item);

        String text = (String)getChild(groupPosition, childPosition);

        txtItem.setText(text);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(groupPosition != 1)
            return 0;
        return ListChild.get(ListGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return ListGroup.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return ListGroup.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.main_page_expandable_list_group,null);
        }

        String textGroup = (String) getGroup(groupPosition);
        Resources contextResouces = context.getResources();
        Drawable groupDrawable;
        switch(groupPosition){
            case 0:
                groupDrawable = contextResouces.getDrawable(R.drawable.ic_account_box_white_24dp);
                break;
            case 1:
                groupDrawable = contextResouces.getDrawable(R.drawable.ic_shopping_cart_white_24dp);
                break;
            case 2:
                groupDrawable = contextResouces.getDrawable(R.drawable.ic_help_white_24dp);
                break;
            case 3:
                groupDrawable = contextResouces.getDrawable(R.drawable.ic_exit_to_app_white_24dp);
                break;
            default:
                groupDrawable = contextResouces.getDrawable(R.drawable.ic_launcher_background);
        }

        ImageView groupImage = (ImageView)convertView.findViewById(R.id.groupimage);
        groupImage.setImageDrawable(groupDrawable);

        TextView groupText = (TextView)convertView.findViewById(R.id.groupid);
        groupText.setText(textGroup);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
