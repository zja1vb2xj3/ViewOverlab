package com.androidg.beaconyx.testproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by beaconyx on 2017-10-06.
 */

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<String> listViewItems;

    public ListViewAdapter() {
        listViewItems = new ArrayList<String>();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Context context = parent.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_row, parent, false);
        }

        TextView textView = (TextView) view.findViewById(R.id.listText);

        String data = listViewItems.get(position);

        textView.setText(data);

        return view;
    }

    @Override
    public int getCount() {
        return listViewItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(String data) {
        listViewItems.add(data);

    }

    public void clearItem() {
        listViewItems.clear();
    }
}
