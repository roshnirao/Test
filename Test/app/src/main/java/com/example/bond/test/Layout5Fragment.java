package com.example.bond.test;

/**
 * Created by Bond on 28-Feb-2015.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class Layout5Fragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public Button b;
    public Spinner s1,s2,s3;
    public  String ss1="Select your branch";
    public  String ss2="Select year";
    public  String ss3="Select category";
    public static String spin1,spin2,spin3,URL,checks,result,ipadd;

    public static Layout5Fragment newInstance(int sectionNumber) {
        Layout5Fragment fragment = new Layout5Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public Layout5Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout5, container,
                false);

        s1=(Spinner)rootView.findViewById(R.id.spinner8);
        s2=(Spinner)rootView.findViewById(R.id.spinner9);
        s3=(Spinner)rootView.findViewById(R.id.spinner10);

        b=(Button)rootView.findViewById(R.id.button10);

        ipadd = getString(R.string.ip);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.branch, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s1.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.year, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s2.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.tcat, android.R.layout.simple_spinner_item);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s3.setAdapter(adapter2);

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                spin1=s1.getSelectedItem().toString();
                spin2=s2.getSelectedItem().toString();
                spin3=s3.getSelectedItem().toString();

                if(spin1.equals(ss1)||spin2.equals(ss2)||spin3.equals(ss3)){

                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setIcon(R.drawable.up);
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage("Select the values other than default values..!!");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();


                }
                else{
                    URL="http://"+ipadd+":8080/noticeboard/listalltecheve?branch="+spin1+"&year="+spin2+"&cat="+spin3+"";
                    new DownloadFileAsync().execute();
                }

            }
        });

        return rootView;


    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {


        final Intent intent = new Intent(getActivity(), listtechevents.class);

        ProgressDialog progressDialog;
        //String result,checks;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "", "Getting the list of Technical events...");

        }

        @Override
        protected String doInBackground(String... aurl) {

            URL=URL.replace(" ","%20");
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet(URL);

            ResponseHandler<String> handler = new BasicResponseHandler();
            try {
                result = httpclient.execute(request, handler);
                checks=result.trim();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;

        }

        @Override
        protected void onPostExecute(String unused) {

            progressDialog.dismiss();
            intent.putExtra("branch",spin1);
            intent.putExtra("year",spin2);
            intent.putExtra("cat",spin3);


            intent.putExtra("msg",checks);
            startActivity(intent);

        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(
                ARG_SECTION_NUMBER));


    }
}