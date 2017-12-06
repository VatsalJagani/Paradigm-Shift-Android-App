package com.learnparadigmshift.vatsal.paradigmshift.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.learnparadigmshift.vatsal.paradigmshift.R;
import com.learnparadigmshift.vatsal.paradigmshift.adapters.AccountingListAdapter;
import com.learnparadigmshift.vatsal.paradigmshift.adapters.VMultiChoiceModeListener;
import com.learnparadigmshift.vatsal.paradigmshift.data.Accounting;
import com.learnparadigmshift.vatsal.paradigmshift.data.DatabaseHelper;
import com.learnparadigmshift.vatsal.paradigmshift.data.MainListModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryAccountingListFragment extends Fragment {

    public interface IHistoryAccountingListCommunication{
        void showMoreInformationOfAccounting(Accounting ac);
        void accountingsRemoved();
        MainListModel getCurrentActiveFriend();
    }

    View view;
    MainListModel currentActiveFriend;
    ArrayList<Accounting> items;
    AccountingListAdapter adapter;
    public HistoryAccountingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_list_accounting_history, container, false);

        currentActiveFriend=((IHistoryAccountingListCommunication) getActivity()).getCurrentActiveFriend();

        final ListView listView = (ListView) view.findViewById(R.id.listview1);
        DatabaseHelper db=new DatabaseHelper(getContext());
        items = db.getHistoryAccountings(currentActiveFriend.getFriendId());
        if(items==null){
            Toast.makeText(getContext(),"Items is null",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getContext(),items.size()+" Here",Toast.LENGTH_LONG).show();
        }
        adapter = new AccountingListAdapter(getContext(),items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((IHistoryAccountingListCommunication) getActivity()).showMoreInformationOfAccounting(items.get(position));
            }
        });
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new VMultiChoiceModeListener<Accounting>(adapter) {

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // Respond to clicks on the actions in the CAB
                switch (item.getItemId()) {
                    case R.id.deleteAction:
                        DatabaseHelper dbHelper=new DatabaseHelper(getContext());
                        for(Accounting ac:adapter.getSelectedItems()){
                            dbHelper.removeAccounting(ac.getAcId());
                        }
                        mode.finish(); // Action picked, so close the CAB
                        ((IHistoryAccountingListCommunication)getActivity()).accountingsRemoved();
                        return true;
                    default:
                        return false;
                }
            }
        });
        return view;
    }

}
