package com.learnparadigmshift.vatsal.paradigmshift.adapters;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learnparadigmshift.vatsal.paradigmshift.R;
import com.learnparadigmshift.vatsal.paradigmshift.data.Accounting;

import java.util.ArrayList;

/**
 * Created by VATSAL on 02/12/2017.
 */

public class AccountingListAdapter extends VListAdapter<Accounting> {
    public AccountingListAdapter(Context context, ArrayList items) {
        super(context, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtAmount;
        TextView txtPurpose;
        //ImageView imgIcon;

        View itemView;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            itemView = inflater.inflate(R.layout.list_layout_accounting, parent, false);
        else
            itemView = convertView;

        txtPurpose = (TextView) itemView.findViewById(R.id.purpose);
        txtAmount = (TextView) itemView.findViewById(R.id.amount);
        txtPurpose.setText(items.get(position).getPurpose());
        txtAmount.setText(""+(int)items.get(position).getAmount());

        if(items.get(position).getAmount()<0){
            txtAmount.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.colorDebit,null));
        }
        else{
            txtAmount.setTextColor(ResourcesCompat.getColor(context.getResources(),R.color.colorCredit,null));
        }
        return changeView(itemView,position);
    }
}
