package com.example.bond.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by Bond on 28-Feb-2015.
 */
public class iadates extends ActionBarActivity {
    public TextView t2,t3,t4,t5;
    public String br,s,t,msg,curl,ipadd,checks,result;
    public String a[],b[];
    public Button bb;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iadate);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            br=extras.getString("branch");
            s=extras.getString("sem");
            t=extras.getString("testno");
            msg=extras.getString("msg");

        }


        t2=(TextView)findViewById(R.id.branch);
        t3=(TextView)findViewById(R.id.textView4);
        t4=(TextView)findViewById(R.id.textView5);
        t5=(TextView)findViewById(R.id.textView6);
        bb=(Button)findViewById(R.id.button3);


        a=msg.split("`");

        b=a[1].split("~");

t2.append(br);
        t3.append(s);
        t4.append(t);
        t5.append("Test day 1 :"+b[0]+"/"+b[3]+"/"+b[4]+"; Test day 2 :"+b[1]+"/"+b[3]+"/"+b[4]+" ; Test day 3 :"+b[2]+"/"+b[3]+"/"+b[4]+"");

        ipadd = getString(R.string.ip);

        curl="http://"+ipadd+":8080/noticeboard/cimg?id="+a[0]+"";

        bb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DownloadFileAsync4().execute();
            }
        });
    }

    class DownloadFileAsync4 extends AsyncTask<String, String, String> {



final Intent i = new Intent(iadates.this,imgdis.class);
        ProgressDialog progressDialog;
        //String result,checks;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(iadates.this, "", "Checking the image existance...");

        }

        @Override
        protected String doInBackground(String... aurl) {

            curl=curl.replace(" ","%20");
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet(curl);

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
            if(checks.equals("yes")){
                i.putExtra("img",a[0]);
                startActivity(i);

            }

            if(checks.equals("no")){
                AlertDialog alertDialog = new AlertDialog.Builder(iadates.this).create();
                alertDialog.setTitle("Error..!!");
                alertDialog.setIcon(R.drawable.up);
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Image has not been uploaded..!!");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    } });
                alertDialog.show();
            }




        }
    }
}
