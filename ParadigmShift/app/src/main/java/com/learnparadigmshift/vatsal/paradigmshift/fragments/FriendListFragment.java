package com.learnparadigmshift.vatsal.paradigmshift.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.learnparadigmshift.vatsal.paradigmshift.R;
import com.learnparadigmshift.vatsal.paradigmshift.adapters.FriendListAdapter;
import com.learnparadigmshift.vatsal.paradigmshift.adapters.MainListAdapter;
import com.learnparadigmshift.vatsal.paradigmshift.adapters.VMultiChoiceModeListener;
import com.learnparadigmshift.vatsal.paradigmshift.data.DatabaseHelper;
import com.learnparadigmshift.vatsal.paradigmshift.data.Friend;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendListFragment extends Fragment {

    public interface IFriendListCommunication{
        void friendsRemoved();
        void showFriendDetails(Friend fr);
        void openAddNewFriend();
    }

    View view;
    ArrayList<Friend> items;
    FriendListAdapter adapter;
    public FriendListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_list_main, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_plus_friend);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((IFriendListCommunication) getActivity()).openAddNewFriend();
            }
        });

        final ListView listView = (ListView) view.findViewById(R.id.listview);

        DatabaseHelper db=new DatabaseHelper(getContext());
        items = db.getFriendList();
        adapter = new FriendListAdapter(getContext(),items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((IFriendListCommunication) getActivity()).showFriendDetails(items.get(position));
            }
        });
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new VMultiChoiceModeListener<Friend>(adapter) {

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // Respond to clicks on the actions in the CAB
                switch (item.getItemId()) {
                    case R.id.deleteAction:
                        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                        for (Friend model : adapter.getSelectedItems()) {
                            dbHelper.removeFriend(model.getFriendId());
                        }
                        mode.finish(); // Action picked, so close the CAB
                        ((IFriendListCommunication) getActivity()).friendsRemoved();
                        return true;
                    default:
                        return false;
                }
            }
        });
        return view;
    }

}
