package com.example.bond.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
public class listplaceevents extends ActionBarActivity{
    public String branch,msg,URL,ipadd,checks,result,i;
    public ListView list;
    public TextView t;
    public String eve[];
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listplaceeve);

        list = (ListView) findViewById(R.id.listView4);
        t=(TextView)findViewById(R.id.textView49);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            branch=extras.getString("branch");

            msg=extras.getString("msg");

        }

        ipadd = getString(R.string.ip);

        eve=msg.split("~");

        t.append(branch);

        String[] values = eve;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        list.setAdapter(adapter);

        // ListView Item Click Listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                i    = (String) list.getItemAtPosition(position);



                // Toast.makeText(getApplicationContext(),
                // "  ListItem : " +fi , Toast.LENGTH_LONG)
                //  .show();

                URL="http://"+ipadd+":8080/noticeboard/placeevedet?pname="+i+"";


                new DownloadFileAsync().execute();

                // Show Alert

            }

        });

    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {


        final Intent intent = new Intent(listplaceevents.this, listplaceevedetails.class);

        ProgressDialog progressDialog;
        //String result,checks;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(listplaceevents.this, "", "Retreiving the event information...");

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


            intent.putExtra("pname",i);
            intent.putExtra("msg", checks);

            startActivity(intent);

        }
    }
}
