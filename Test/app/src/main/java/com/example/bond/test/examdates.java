package com.example.bond.test;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
 * Created by Bond on 02-Mar-2015.
 */
public class examdates extends ActionBarActivity {
    public TextView t9,t10,t11,t12,t13,t14,t15,t16,t25,t26,t27,t28,t29,t30,t31,t32;
    public String br,s,msg,curl,ipadd,checks,result;
    Button bb;
    String a[],b[],c[];
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examdate);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            br=extras.getString("branch");
            s=extras.getString("sem");

            msg=extras.getString("msg");

        }


        t9=(TextView)findViewById(R.id.textView9);
        t10=(TextView)findViewById(R.id.textView10);
        t11=(TextView)findViewById(R.id.textView11);
        t12=(TextView)findViewById(R.id.textView12);
        t13=(TextView)findViewById(R.id.textView13);
        t14=(TextView)findViewById(R.id.textView14);
        t15=(TextView)findViewById(R.id.textView15);
        t16=(TextView)findViewById(R.id.textView16);

        t25=(TextView)findViewById(R.id.textView25);
        t26=(TextView)findViewById(R.id.textView26);
        t27=(TextView)findViewById(R.id.textView27);
        t28=(TextView)findViewById(R.id.textView28);
        t29=(TextView)findViewById(R.id.textView29);
        t30=(TextView)findViewById(R.id.textView30);
        t31=(TextView)findViewById(R.id.textView31);
        t32=(TextView)findViewById(R.id.textView32);

        bb=(Button)findViewById(R.id.button6);

        ipadd = getString(R.string.ip);

a=msg.split("~");
       b=a[1].split("`");
        c=a[2].split("`");

t9.setText(b[0]);
        t10.setText(b[1]);
        t11.setText(b[2]);
        t12.setText(b[3]);
        t13.setText(b[4]);
        t14.setText(b[5]);
        t15.setText(b[6]);
        t16.setText(b[7]);

t25.setText(c[0]);
        t26.setText(c[1]);
        t27.setText(c[2]);
        t28.setText(c[3]);
        t29.setText(c[4]);
        t30.setText(c[5]);
        t31.setText(c[6]);
        t32.setText(c[7]);


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



        final Intent i = new Intent(examdates.this,imgdis.class);
        ProgressDialog progressDialog;
        //String result,checks;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(examdates.this, "", "Checking the image existance...");

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
                AlertDialog alertDialog = new AlertDialog.Builder(examdates.this).create();
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
