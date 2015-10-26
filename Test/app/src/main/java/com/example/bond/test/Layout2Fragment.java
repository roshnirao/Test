package com.example.bond.test;

/**
 * Created by Bond on 28-Feb-2015.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class Layout2Fragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public Spinner s1,s2;
    public Button b;
    public  String ss1="Select your branch";
    public String ss2="Select your sem";
    public static String spin1,spin2,URL,checks,result,ipadd;

    public static Layout2Fragment newInstance(int sectionNumber) {
        Layout2Fragment fragment = new Layout2Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public Layout2Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout2, container,
                false);
s1=(Spinner)rootView.findViewById(R.id.spinner4);
        s2=(Spinner)rootView.findViewById(R.id.spinner5);
        b=(Button)rootView.findViewById(R.id.button5);

        ipadd = getString(R.string.ip);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.branch, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s1.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.sem, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s2.setAdapter(adapter1);

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                spin1=s1.getSelectedItem().toString();
                spin2=s2.getSelectedItem().toString();

                if(spin1.equals(ss1)||spin2.equals(ss2)){

                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setIcon(R.drawable.up);
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage("Select the Branch, Sem and Test number..!!");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();


                }
                else{
                    URL="http://"+ipadd+":8080/noticeboard/exam?branch="+spin1+"&sem="+spin2+"";
                    new DownloadFileAsync().execute();
                }

            }
        });


        return rootView;
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {


        final Intent intent = new Intent(getActivity(), examdates.class);

        ProgressDialog progressDialog;
        //String result,checks;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "", "Getting the EXAM dates...");

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




            if(checks.equals("error")){
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Error");
                alertDialog.setIcon(R.drawable.up);
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Details not found for the selected values");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    } });
                alertDialog.show();
            }
            else{
                intent.putExtra("branch",spin1);
                intent.putExtra("sem",spin2);

                intent.putExtra("msg",checks);
                startActivity(intent);
            }
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(
                ARG_SECTION_NUMBER));
    }
}