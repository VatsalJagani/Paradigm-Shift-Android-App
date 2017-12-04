package com.learnparadigmshift.vatsal.paradigmshift.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.learnparadigmshift.vatsal.paradigmshift.R;
import com.learnparadigmshift.vatsal.paradigmshift.data.Accounting;
import com.learnparadigmshift.vatsal.paradigmshift.data.DatabaseHelper;
import com.learnparadigmshift.vatsal.paradigmshift.data.MainListModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAccountingFragment extends Fragment {

    public interface IAddAccountingCommunication{
        void newAccountingAdded(double amount);
        MainListModel getCurrentActiveFriend();
    }

    View view;
    java.util.Calendar myCal;
    EditText editDate,editAmount,editPurpose;
    ToggleButton toggleCreditDebit;
    public AddAccountingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_accounting_add, container, false);
        final MainListModel currentActiveFriend = ((IAddAccountingCommunication) getActivity()).getCurrentActiveFriend();
        String name=currentActiveFriend.getName();
        ((TextView)view.findViewById(R.id.t_name)).setText(name);
        myCal= Calendar.getInstance();
        editDate = (EditText) view.findViewById(R.id.t_date);
        editAmount = (EditText) view.findViewById(R.id.t_amount);
        editPurpose = (EditText) view.findViewById(R.id.t_purpose);
        toggleCreditDebit = (ToggleButton) view.findViewById(R.id.toggleCreditDebit);

        final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCal.set(Calendar.YEAR, year);
                myCal.set(Calendar.MONTH, monthOfYear);
                myCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(),0, dateListener,myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),myCal.get(Calendar.DATE)).show();
            }
        });

        ((Button)view.findViewById(R.id.addAccountingButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Accounting ac=new Accounting();
                ac.setFriendId(currentActiveFriend.getFriendId());
                try {
                    double amount = 0;
                    if (!editAmount.getText().toString().equals("")) {

                        amount = new Double(editAmount.getText().toString());
                        if (toggleCreditDebit.isChecked()) {
                            amount = amount * (-1);
                        }
                        ac.setAmount(amount);
                        if (!editPurpose.getText().toString().equals("")) {
                            ac.setPurpose(editPurpose.getText().toString());
                            ac.setDate(editDate.getText().toString());
                            ac.setComplete(0);
                            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                            dbHelper.insertAccounting(ac);

                            // Update data and diplay new list
                            ((IAddAccountingCommunication) getActivity()).newAccountingAdded(amount);
                        } else {
                            Dialog d = new Dialog(getContext());
                            d.setTitle("Please enter Purpose");
                            d.show();
                        }
                    } else {
                        Dialog d = new Dialog(getContext());
                        d.setTitle("Please enter amount");
                        d.show();
                    }
                }
                catch (Exception e){
                    Dialog d=new Dialog(getContext());
                    d.setTitle("Please enter correct values");
                    d.show();
                }

            }
        });
        updateLabel();

        return view;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        editDate.setText(sdf.format(myCal.getTime()));
    }
}
