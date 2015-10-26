package com.example.bond.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Bond on 01-Mar-2015.
 */
public class imgdis extends ActionBarActivity {
    public static final String LOG_TAG = "android";
    private ProgressDialog mProgressDialog1;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    public String img,iURL,ipadd;
    public ProgressDialog mProgressDialog;
    public ImageView v;
    public Button b;

    File rootDir = Environment.getExternalStorageDirectory();



    public String fileName;
    public String fileURL;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {

            img=extras.getString("img");

        }

        v=(ImageView)findViewById(R.id.imageView2);
        b=(Button)findViewById(R.id.button4);

        ipadd = getString(R.string.ip);


        iURL = "http://"+ipadd+":8080/noticeboard/images/"+img+".jpg";
        iURL=iURL.replace(" ","%20");
        new DownloadImage().execute(iURL);

        fileName=img+".jpg";
        fileURL="http://"+ipadd+":8080/noticeboard/images/"+img+".jpg";

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DownloadFileAsync().execute();
            }
        });


    }

    class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(imgdis.this);
            // Set progressdialog title

            // Set progressdialog message
            mProgressDialog.setMessage("Loading the attached image, please wait...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            v.setImageBitmap(result);




            // Close progressdialog
            mProgressDialog.dismiss();
        }
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }


        @Override
        protected String doInBackground(String... aurl) {

            try {

                URL u = new URL(fileURL);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();


                int lenghtOfFile = c.getContentLength();


                FileOutputStream f = new FileOutputStream(new File(rootDir + "/download/", fileName));

                InputStream in = c.getInputStream();


                byte[] buffer = new byte[1024];
                int len1 = 0;
                long total = 0;

                while ((len1 = in.read(buffer)) > 0) {
                    total += len1;
                    publishProgress("" + (int)((total*100)/lenghtOfFile));
                    f.write(buffer, 0, len1);
                }
                f.close();

            } catch (Exception e) {
                Log.d(LOG_TAG, e.getMessage());
            }

            return null;
        }

        protected void onProgressUpdate(String... progress) {
            Log.d(LOG_TAG, progress[0]);
            mProgressDialog1.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {

            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(imgdis.this);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setTitle("Download Successful..!!")
                    .setMessage("If you want to see the downloaded image now, then click on 'View the Image' button. Else click on 'Close' button, but later you can open the 'Download' folder in your phone's memory and click on '"+img+".jpg' to see the Image..!!")
                    .setIcon(R.drawable.up)
                    .setNegativeButton("Close",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            })
                    .setPositiveButton("View the Image",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(Uri.parse("file:///mnt/sdcard/Download/"+img+".jpg"),"image/*");
                                    startActivity(intent);
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }
    }


    public void checkAndCreateDirectory(String dirName){
        File new_dir = new File( rootDir + dirName );
        if( !new_dir.exists() ){
            new_dir.mkdirs();
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS: //we set this to 0
                mProgressDialog1 = new ProgressDialog(this);
                mProgressDialog1.setMessage("Downloading the attached image..!!");
                mProgressDialog1.setIndeterminate(false);
                mProgressDialog1.setMax(100);
                mProgressDialog1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog1.setCancelable(true);
                mProgressDialog1.show();
                return mProgressDialog1;
            default:
                return null;
        }
    }
}
