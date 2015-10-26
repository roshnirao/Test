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
public class listplaceevedetails extends ActionBarActivity{
    public String msg,pname,fin[];
    public String curl,ipadd,checks,result;
    public TextView t35,t36,t37;
    public Button b7;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listplaceevedet);

        t35=(TextView)findViewById(R.id.textView50);
        t36=(TextView)findViewById(R.id.textView51);
        t37=(TextView)findViewById(R.id.textView52);



        b7=(Button)findViewById(R.id.button13);


        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {

            pname=extras.getString("pname");
            msg=extras.getString("msg");

        }

        fin=msg.split("~");

        t35.append(pname);
        t36.append(fin[1]);
        t37.append(fin[2]);

        ipadd = getString(R.string.ip);

        curl="http://"+ipadd+":8080/noticeboard/cimg?id="+fin[0]+"";

        b7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DownloadFileAsync4().execute();
            }
        });
    }


    class DownloadFileAsync4 extends AsyncTask<String, String, String> {



        final Intent i = new Intent(listplaceevedetails.this,imgdis.class);
        ProgressDialog progressDialog;
        //String result,checks;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(listplaceevedetails.this, "", "Checking the image existance...");

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
                i.putExtra("img",fin[0]);
                startActivity(i);

            }

            if(checks.equals("no")){
                AlertDialog alertDialog = new AlertDialog.Builder(listplaceevedetails.this).create();
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
