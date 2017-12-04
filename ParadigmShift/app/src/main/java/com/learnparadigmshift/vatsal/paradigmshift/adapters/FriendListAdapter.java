package com.learnparadigmshift.vatsal.paradigmshift.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learnparadigmshift.vatsal.paradigmshift.R;
import com.learnparadigmshift.vatsal.paradigmshift.data.Friend;

import java.util.ArrayList;

/**
 * Created by VATSAL on 03/12/2017.
 */

public class FriendListAdapter extends VListAdapter<Friend> {
    public FriendListAdapter(Context context, ArrayList<Friend> items) {
        super(context, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtName;
        TextView txtAmount;

        View itemView;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            itemView = inflater.inflate(R.layout.list_layout_friend, parent, false);
        else
            itemView = convertView;



        txtName = (TextView) itemView.findViewById(R.id.frd_name);
        txtName.setText(items.get(position).getName());


        return changeView(itemView,position);
    }
}

