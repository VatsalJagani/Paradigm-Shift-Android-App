package com.learnparadigmshift.vatsal.paradigmshift.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.learnparadigmshift.vatsal.paradigmshift.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by VATSAL on 02/12/2017.
 */

public abstract class VListAdapter<T> extends BaseAdapter {
    protected Context context;
    protected ArrayList<T> items;
    protected HashMap<Integer,Boolean> selection;
    protected LayoutInflater inflater;

    public VListAdapter(Context context, ArrayList<T> items) {
        this.context = context;
        this.items = items;
        selection=new HashMap<>();
        for(int i=0;i<items.size();i++){
            selection.put(i,false);
        }
    }

    public void newItemSelected(int position){
        selection.put(position,true);
        notifyDataSetChanged();
    }
    public void itemDeselected(int position){
        selection.put(position,false);
        notifyDataSetChanged();
    }
    public void deselectAllItems(){
        for(int i=0;i<items.size();i++){
            selection.put(i,false);
        }
        notifyDataSetChanged();
    }
    public ArrayList<T> getSelectedItems(){
        ArrayList<T> arr=new ArrayList<>();
        for(Map.Entry<Integer,Boolean> entry:selection.entrySet()){
            if(entry.getValue()){
                arr.add(((T)items.get(entry.getKey())));
            }
        }
        return arr;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /* please override getView() method
    public View getView(int position, View convertView, ViewGroup parent) {*/

    protected View changeView(View itemView,int position){
        if(selection.get(position)){
            itemView.setBackgroundColor(ResourcesCompat.getColor(context.getResources(), R.color.colorSelectedItems,null));
        }
        else{
            itemView.setBackgroundColor(Color.WHITE);
        }
        return itemView;
    }
}
