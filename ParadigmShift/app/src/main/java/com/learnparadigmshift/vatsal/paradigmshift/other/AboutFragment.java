package com.learnparadigmshift.vatsal.paradigmshift.other;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learnparadigmshift.vatsal.paradigmshift.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    View view;
    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_about, container, false);
        view.findViewById(R.id.urlC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri=getResources().getString(R.string.webUrl);
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(i);
            }
        });
        view.findViewById(R.id.phoneC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri=getResources().getString(R.string.author_mobile_no_only);
                uri="tel:"+uri;
                Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse(uri));
                startActivity(i);
            }
        });
        view.findViewById(R.id.emailC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+
                        getResources().getString(R.string.author_email_only)));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                startActivity(Intent.createChooser(emailIntent, "Thanks for Feedback"));
            }
        });
        return view;
    }

}
