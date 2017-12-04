package com.learnparadigmshift.vatsal.paradigmshift.adapters;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learnparadigmshift.vatsal.paradigmshift.R;
import com.learnparadigmshift.vatsal.paradigmshift.data.MainListModel;

import java.util.ArrayList;


/**
 * Created by VATSAL on 02/12/2017.
 */

public class MainListAdapter extends VListAdapter<MainListModel> {
    public MainListAdapter(Context context, ArrayList<MainListModel> items) {
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
            itemView = inflater.inflate(R.layout.list_layout_main, parent, false);
        else
            itemView = convertView;



        txtName = (TextView) itemView.findViewById(R.id.frd_name);
        txtAmount = (TextView) itemView.findViewById(R.id.frd_total_amount);

        txtName.setText(items.get(position).getName());
        txtAmount.setText(""+(int)items.get(position).getAmount());

        if(items.get(position).getAmount()<0){
            txtAmount.setTextColor(ResourcesCompat.getColor(context.getResources(),R.color.colorDebit,null));
        }
        else{
            txtAmount.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.colorCredit,null));
        }

        // Capture position and set to the ImageView
        //imgIcon.setImageDrawable(items.get(position).getIcon());

        return changeView(itemView,position);
    }
}
