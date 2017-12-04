package com.learnparadigmshift.vatsal.paradigmshift.adapters;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;

import com.learnparadigmshift.vatsal.paradigmshift.R;
import com.learnparadigmshift.vatsal.paradigmshift.adapters.VListAdapter;

/**
 * Created by VATSAL on 02/12/2017.
 */

public abstract class VMultiChoiceModeListener<T> implements AbsListView.MultiChoiceModeListener {
    VListAdapter<T> adapter;
    public VMultiChoiceModeListener(VListAdapter<T> adapter){
        this.adapter=adapter;
    }
    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        if(checked){
            adapter.newItemSelected(position);
        }
        else{
            adapter.itemDeselected(position);
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.context, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public abstract boolean onActionItemClicked(ActionMode mode, MenuItem item);

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}
