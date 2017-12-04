package com.learnparadigmshift.vatsal.paradigmshift.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.learnparadigmshift.vatsal.paradigmshift.R;
import com.learnparadigmshift.vatsal.paradigmshift.data.Friend;


/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFriendFragment extends Fragment {

    public interface IInformationFriendCommunication{
        Friend getCurrentFriend();
    }

    View view;
    Friend friend;
    TextView t_name,t_full_name,t_mobile,t_email;
    public InformationFriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_information_friend, container, false);
        friend=((IInformationFriendCommunication)getActivity()).getCurrentFriend();

        t_name=(TextView)view.findViewById(R.id.t_name);
        t_name.setText(friend.getName());

        t_full_name=(TextView)view.findViewById(R.id.t_full_name);
        t_full_name.setText(friend.getFullName());

        t_mobile=(TextView)view.findViewById(R.id.t_mobile);
        t_mobile.setText(friend.getMobileNo());

        t_email=(TextView)view.findViewById(R.id.t_email);
        t_email.setText(friend.getEmailAddress());

        ((Button)view.findViewById(R.id.b_doCall)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iCall=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+friend.getMobileNo()));
                startActivity(iCall);
            }
        });

        ((Button)view.findViewById(R.id.b_doEmail)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+friend.getEmailAddress()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Paradigm Shift Incomplete Accounting");
                startActivity(Intent.createChooser(emailIntent, "We have some accounting left in Paradigm Shift please contact me."));
            }
        });

        return view;
    }

}
