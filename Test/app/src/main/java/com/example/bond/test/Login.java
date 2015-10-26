package com.example.bond.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class Login extends ActionBarActivity {
    public EditText u,p;
    public TextView s,f;
    public Button b;
    public String uid,iurl,murl,result,checks,URL;
    public String ipadd;
    public String us,pa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);



        ipadd = getString(R.string.ip);
        final Intent intent = new Intent(this, usignup.class);

        s=(TextView)findViewById(R.id.nuser);
        f=(TextView)findViewById(R.id.fpass);
        b=(Button)findViewById(R.id.log);
        u=(EditText)findViewById(R.id.usn);
        p=(EditText)findViewById(R.id.pass);


        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                us=u.getText().toString();
                pa=p.getText().toString();
                if(us.equals("")||pa.equals("")){
                    u.requestFocus();
                    AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
                    alertDialog.setTitle("Fields cannot be blank");
                    alertDialog.setIcon(R.drawable.up);

                    alertDialog.setCancelable(false);
                    alertDialog.setMessage("Please enter all the details and then click Login button..!!");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        } });
                    alertDialog.show();
                }
                else
                {
                    URL="http://"+ipadd+":8080/noticeboard/login?acc="+us+"&pa="+pa+"";
                    new DownloadFileAsync().execute();
                }
            }
        });

        s.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                startActivity(intent);
            }
        });


        f.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this);

                // set prompts.xml to alertdialog builder



                final EditText input = new EditText(Login.this);
                input.setHint("Enter your Notice Board account USN");

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setTitle("Forgot Password ?")
                        .setMessage("No problem, we will send an email to reset your password..!!")
                        .setIcon(R.drawable.up)
                        .setView(input)
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                })
                        .setPositiveButton("Send Email",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        uid=input.getText().toString();
                                        if(uid.equals("")){
                                            Toast.makeText(getApplicationContext(), "Please enter your Notice Board account USN and then click on Send Email button..!!", Toast.LENGTH_LONG).show();
                                        }
                                        else
                                        {

                                            iurl="http://"+ipadd+":8080/noticeboard/forgotapppass.jsp?acc="+uid+"";
                                            murl="http://"+ipadd+":8080/noticeboard/forgotpass?acc="+uid+"&lin="+iurl+"";
                                            new DownloadFileAsyncf().execute();
                                        }
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
    }


    class DownloadFileAsync extends AsyncTask<String, String, String> {


        final Intent intent = new Intent(Login.this, MainActivity.class);

        ProgressDialog progressDialog;
        //String result,checks;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Login.this, "", "Checking your login details...");

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
                u.setText("");
                p.setText("");
                u.requestFocus();
                intent.putExtra("uid",us);
                startActivity(intent);
            }

            if(checks.equals("wrong")||checks.equals("acc does not exist")){
                AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setIcon(R.drawable.up);
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Check your Username OR Password");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    } });
                alertDialog.show();
            }
        }
    }

    class DownloadFileAsyncf extends AsyncTask<String, String, String> {




        ProgressDialog progressDialog;
        //String result,checks;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Login.this, "", "Sending an Email to reset your Notice Board password...");

        }

        @Override
        protected String doInBackground(String... aurl) {

            murl=murl.replace(" ","%20");
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet(murl);

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
                AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
                alertDialog.setTitle("Email sent..!!");
                alertDialog.setIcon(R.drawable.up);
                alertDialog.setCancelable(false);
                alertDialog.setMessage("We have sent an Email to your Email id that contains a link to reset your Notice Board password, please visit that link..!!");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    } });
                alertDialog.show();
            }

            if(checks.equals("acc does not exist")){
                AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
                alertDialog.setTitle("Error..!!");
                alertDialog.setIcon(R.drawable.up);
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Entered USN : "+uid+" does not exists in Notice Board database..!!");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    } });
                alertDialog.show();
            }

        }
    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm exit");
        builder.setMessage("Are you sure you want to exit ?")
                .setCancelable(false)
                .setIcon(R.drawable.up)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Login.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

}