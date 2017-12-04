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
public class AccountingListFragment extends Fragment {

    public interface IAccountingListCommunication{
        void openAddNewAccounting();
        void showMoreInformationOfAccounting(Accounting ac);
        void accountingsRemoved();
        void accountCleared();
        MainListModel getCurrentActiveFriend();
    }

    View view;
    MainListModel currentActiveFriend;
    ArrayList<Accounting> items;
    AccountingListAdapter adapter;

    public AccountingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_list_accounting, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_plus_accounting);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((IAccountingListCommunication)getActivity()).openAddNewAccounting();
            }
        });
        currentActiveFriend=((IAccountingListCommunication) getActivity()).getCurrentActiveFriend();

        TextView frdName= (TextView) view.findViewById(R.id.frd_name1);
        frdName.setText(currentActiveFriend.getName());
        TextView totalAmount= (TextView) view.findViewById(R.id.frd_total_amount1);
        totalAmount.setText(""+currentActiveFriend.getAmount());
        if(currentActiveFriend.getAmount()<0) {
            totalAmount.setTextColor(Color.RED);
        }

        final ListView listView = (ListView) view.findViewById(R.id.listview);
        DatabaseHelper db=new DatabaseHelper(getContext());
        items = db.getAccountings(currentActiveFriend.getFriendId());
        adapter = new AccountingListAdapter(getContext(),items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Display accountings in fragment by showIncompleAccountings()
                ((IAccountingListCommunication) getActivity()).showMoreInformationOfAccounting(items.get(position));
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
                        ((IAccountingListCommunication)getActivity()).accountingsRemoved();
                        return true;
                    default:
                        return false;
                }
            }
        });
        ((Button)view.findViewById(R.id.buttonClearAccount)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAccount();
            }
        });
        return view;
    }

    private void clearAccount() {
        DatabaseHelper dbHelper=new DatabaseHelper(getContext());
        dbHelper.clearAccount(currentActiveFriend.getFriendId());
        ((IAccountingListCommunication)getActivity()).accountCleared();
    }
}
