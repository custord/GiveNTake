package com.roody.giventake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;


public class MyCustomBaseAdapter extends BaseAdapter {
    private static ArrayList<favResults> searchArrayList;
    private LayoutInflater mInflater;

    public MyCustomBaseAdapter(Context context, ArrayList<favResults> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_row_view, null);
            holder = new ViewHolder();
            holder.txtFavor = (TextView) convertView.findViewById(R.id.favor);
            holder.txtDate = (TextView) convertView.findViewById(R.id.date);
            holder.txtName = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtFavor.setText(searchArrayList.get(position).getFavor());
        holder.txtDate.setText(searchArrayList.get(position).getDate());
        holder.txtName.setText(searchArrayList.get(position).getName());

        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        TextView txtFavor;
        TextView txtDate;
    }


}