package com.example.bond.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class updateacc extends ActionBarActivity {
    public String ipadd,URL,checks,result,param,URL1,checks1,result1;
    public String det[];
    public EditText e1,e2,e3,e4;
    public String s1,s2,s3,s4;
    public Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateaccd);

        e1=(EditText)findViewById(R.id.editText7);
        e2=(EditText)findViewById(R.id.editText8);
        e3=(EditText)findViewById(R.id.editText9);
        e4=(EditText)findViewById(R.id.editText10);
        b1=(Button)findViewById(R.id.button16);


        ipadd = getString(R.string.ip);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            param=extras.getString("uid");
        }


        URL="http://"+ipadd+":8080/noticeboard/retstud?usn="+param+"";
        new DownloadFileAsync().execute();



        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                s1=e1.getText().toString();
                s2=e2.getText().toString();
                s3=e3.getText().toString();
                s4=e4.getText().toString();

                if(s1.equals("")||s2.equals("")||s3.equals("")||s4.equals("")){
                    AlertDialog alertDialog = new AlertDialog.Builder(updateacc.this).create();
                    alertDialog.setTitle("Fields cannot be blank");
                    alertDialog.setIcon(R.drawable.up);

                    alertDialog.setCancelable(false);
                    alertDialog.setMessage("Please enter all the details and then click Update button..!!");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        } });
                    alertDialog.show();
                }
                else
                {
                    URL1="http://"+ipadd+":8080/noticeboard/updatestud?usn="+param+"&pass="+s1+"&name="+s2+"&email="+s3+"&mob="+s4+"";
                    new DownloadFileAsyncf().execute();
                }


            }
        });

    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {


        ProgressDialog progressDialog;
        //String result,checks;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(updateacc.this, "", "Retrieving your account details..!!");

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
            det=checks.split("~");
            e1.setText(det[0]);
            e2.setText(det[1]);
            e3.setText(det[2]);
            e4.setText(det[3]);

        }
    }


    class DownloadFileAsyncf extends AsyncTask<String, String, String> {



        ProgressDialog progressDialog;
        //String result,checks;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(updateacc.this, "", "Updating your account details...");

        }

        @Override
        protected String doInBackground(String... aurl) {

            URL1=URL1.replace(" ","%20");
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet(URL1);

            ResponseHandler<String> handler = new BasicResponseHandler();
            try {
                result1 = httpclient.execute(request, handler);
                checks1=result1.trim();
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
            if(checks1.equals("done")){
                e1.setText("");
                e2.setText("");
                e3.setText("");
                e4.setText("");
                e1.requestFocus();

                AlertDialog alertDialog = new AlertDialog.Builder(updateacc.this).create();
                alertDialog.setTitle("Success");
                alertDialog.setIcon(R.drawable.up);

                alertDialog.setCancelable(false);
                alertDialog.setMessage("Your account details updated successfully..!!");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    } });
                alertDialog.show();
            }


        }
    }
}
