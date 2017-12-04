package com.learnparadigmshift.vatsal.paradigmshift.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.learnparadigmshift.vatsal.paradigmshift.R;
import com.learnparadigmshift.vatsal.paradigmshift.data.DatabaseHelper;
import com.learnparadigmshift.vatsal.paradigmshift.data.Friend;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFriendFragment extends Fragment {

    public interface IAddFriendCommunication{
        void newFriendAdded();
    }

    View view;
    public AddFriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_add_friend, container, false);
        view.findViewById(R.id.buttonAddFriend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Friend fr=new Friend();
                EditText editName=(EditText)view.findViewById(R.id.editName);
                if(!editName.getText().toString().equals("")) {
                    fr.setName(editName.getText().toString());
                    fr.setFullName(((EditText) view.findViewById(R.id.editFullName)).getText().toString());
                    fr.setMobileNo(((EditText) view.findViewById(R.id.editPhone)).getText().toString());
                    fr.setEmailAddress(((EditText) view.findViewById(R.id.editEmail)).getText().toString());
                    DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                    dbHelper.insertFriend(fr);

                    // Update data and diplay new list
                    ((IAddFriendCommunication) getActivity()).newFriendAdded();
                }
                else{
                    Dialog d=new Dialog(getContext());
                    d.setTitle("Please Enter Name");
                    d.show();
                }
            }
        });
        return view;
    }

}
