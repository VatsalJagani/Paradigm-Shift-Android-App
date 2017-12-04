package com.learnparadigmshift.vatsal.paradigmshift.other;


import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.learnparadigmshift.vatsal.paradigmshift.R;
import com.learnparadigmshift.vatsal.paradigmshift.data.DatabaseHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class BackupFragment extends Fragment {


    public interface IBackupCommunication{
        void dataRestored();
    }

    View view;
    private final static String EXTERNAL_LOCATION= Environment.getExternalStorageDirectory() + "/database_paradigm.db";

    public BackupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_backup, container, false);
        (view.findViewById(R.id.backup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseBackup();
            }
        });
        (view.findViewById(R.id.restore)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseRestore();
            }
        });
        return view;
    }

    private boolean fileCopy(String from,String to){
        FileInputStream fis;
        OutputStream output;
        try {
            File dbFile = new File(from);
            fis = new FileInputStream(dbFile);

            String outFileName = to;

            // Open the empty db as the output stream
            output = new FileOutputStream(outFileName);
            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            // Close the streams
            output.flush();
            output.close();
            fis.close();
            return true;
        } catch (IOException e) {
            Dialog d=new Dialog(getContext());
            d.setTitle("There is some problem with External Storage");
            d.show();
        } finally {
            // on both the cases
        }
        return false;
    }

    private void databaseRestore(){
        databaseRestoreOnline();
        if(databaseRestoreOffline()){
           Toast.makeText(getContext(),"Restore Completed..!!",Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(getContext(),"Application restart is required..",Toast.LENGTH_LONG).show();
        ((IBackupCommunication)getActivity()).dataRestored();
    }
    private boolean databaseRestoreOnline(){
        return false;
    }
    private boolean databaseRestoreOffline(){
        String outFileName = getContext().getDatabasePath(DatabaseHelper.DATABASE_NAME).getPath();
        return fileCopy(EXTERNAL_LOCATION,outFileName);
    }

    private void databaseBackup(){
        if(databaseBackupOffline()) {
            Toast.makeText(getContext(), "Backup Completed..!!", Toast.LENGTH_SHORT).show();
        }
        databaseBackupOnline();
    }
    private boolean databaseBackupOffline() {
        String inFileName = getContext().getDatabasePath(DatabaseHelper.DATABASE_NAME).getPath();
        return fileCopy(inFileName,EXTERNAL_LOCATION);
    }
    private boolean databaseBackupOnline(){
        return false;
    }

}
