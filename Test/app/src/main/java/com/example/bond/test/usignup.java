package com.example.bond.test;

/**
 * Created by Bond on 28-Feb-2015.
 */
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class usignup extends ActionBarActivity {
    public EditText e1,e2,e3,e4,e5;
    public Spinner sp1;
    public Button b1;
    public String s1,s2,s3,s4,s5,s6,URL,result,checks,ipadd;
    public String def="Select your branch";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        ipadd = getString(R.string.ip);

        e1=(EditText)findViewById(R.id.editText1);
        e2=(EditText)findViewById(R.id.editText2);
        e3=(EditText)findViewById(R.id.editText3);
        e4=(EditText)findViewById(R.id.editText4);
        e5=(EditText)findViewById(R.id.editText5);
        b1=(Button)findViewById(R.id.button1);
        sp1=(Spinner)findViewById(R.id.spinner1);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.branch, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp1.setAdapter(adapter);

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                s1=e1.getText().toString();
                s2=e2.getText().toString();
                s3=e3.getText().toString();
                s4=sp1.getSelectedItem().toString();
                s5=e4.getText().toString();
                s6=e5.getText().toString();

                if(s1.equals("")||s2.equals("")||s3.equals("")||s4.equals(def)||s5.equals("")||s6.equals("")){
                    AlertDialog alertDialog = new AlertDialog.Builder(usignup.this).create();
                    alertDialog.setTitle("Fields cannot be blank");
                    alertDialog.setIcon(R.drawable.up);
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage("Please enter all the details OR Please select value from the dropdown lists other than default values..!!");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        } });
                    alertDialog.show();
                }
                else{
                    URL="http://"+ipadd+":8080/noticeboard/signup?usn="+s1+"&pass="+s2+"&name="+s3+"&branch="+s4+"&email="+s5+"&mob="+s6+"";
                    new DownloadFileAsync().execute();
                }
            }
        });


    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {


        final Intent intent = new Intent(usignup.this, Login.class);

        ProgressDialog progressDialog;
        //String result,checks;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(usignup.this, "", "Processing...");

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
            if(checks.equals("done")){
                AlertDialog alertDialog = new AlertDialog.Builder(usignup.this).create();
                alertDialog.setTitle("Sign up success");
                alertDialog.setIcon(R.drawable.up);
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Your Notice Board account has been created, Email will be sent shortly to your email id : "+s5+" that contains your Login details, please Login to use your account..!!");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        startActivity(intent);
                    } });
                alertDialog.show();

            }

            if(checks.equals("wrong")){
                e1.setText("");
                e1.requestFocus();
                AlertDialog alertDialog = new AlertDialog.Builder(usignup.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setIcon(R.drawable.up);
                alertDialog.setCancelable(false);
                alertDialog.setMessage("USN already exists, please try another USN..!!");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    } });
                alertDialog.show();
            }
        }
    }
}
