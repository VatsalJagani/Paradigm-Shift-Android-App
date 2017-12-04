package com.learnparadigmshift.vatsal.paradigmshift.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learnparadigmshift.vatsal.paradigmshift.R;
import com.learnparadigmshift.vatsal.paradigmshift.data.Accounting;
import com.learnparadigmshift.vatsal.paradigmshift.data.MainListModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationAccountingFragment extends Fragment {

    public interface IInformationAccountingCommunication{
        Accounting getCurrentAccounting();
        MainListModel getCurrentActiveFriend();
    }

    View view;
    Accounting ac;
    MainListModel fr;
    TextView t_name,t_credit_debit,t_amount,t_purpose,t_date;
    public InformationAccountingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_information_accounting, container, false);
        ac=((IInformationAccountingCommunication)getActivity()).getCurrentAccounting();
        fr=((IInformationAccountingCommunication)getActivity()).getCurrentActiveFriend();

        t_name=(TextView)view.findViewById(R.id.t_name);
        t_name.setText(fr.getName());

        t_credit_debit=(TextView)view.findViewById(R.id.t_credit_debit);
        if(ac.getAmount()>0) {
            t_credit_debit.setText("Credit");
        }
        else{
            t_credit_debit.setText("Debit");
        }

        t_amount=(TextView)view.findViewById(R.id.t_amount);
        t_amount.setText(ac.getAmount()+"");

        t_purpose=(TextView)view.findViewById(R.id.t_purpose);
        t_purpose.setText(ac.getPurpose());

        t_date=(TextView)view.findViewById(R.id.t_date);
        t_date.setText(ac.getDate());

        return view;
    }

}
