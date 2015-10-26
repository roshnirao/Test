package com.example.bond.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
public static String ss1="Select your branch";
    public static String ss2="Select your sem";
    public static String ss3="Select test number";
    public static String spin1,spin2,spin3,URL,checks,result,ipadd,uidd,param;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipadd = getString(R.string.ip);
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            param=extras.getString("uid");
        }


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position+1) {
            case 1:
                fragmentManager.beginTransaction().replace(R.id.container,
                        PlaceholderFragment.newInstance(position + 1)).commit();
                break;
            case 2:
                fragmentManager.beginTransaction().replace(R.id.container,
                        Layout2Fragment.newInstance(position + 1)).commit();
                break;
            case 3:
                fragmentManager.beginTransaction().replace(R.id.container,
                        Layout3Fragment.newInstance(position + 1)).commit();
                break;
            case 4:
                fragmentManager.beginTransaction().replace(R.id.container,
                        Layout4Fragment.newInstance(position + 1)).commit();
                break;
            case 5:
                fragmentManager.beginTransaction().replace(R.id.container,
                        Layout5Fragment.newInstance(position + 1)).commit();
                break;
            case 6:
                fragmentManager.beginTransaction().replace(R.id.container,
                        Layout6Fragment.newInstance(position + 1)).commit();
                break;
            case 7:
                fragmentManager.beginTransaction().replace(R.id.container,
                        Layout7Fragment.newInstance(position + 1)).commit();
                break;
            case 8:
                fragmentManager.beginTransaction().replace(R.id.container,
                        Layout8Fragment.newInstance(position + 1)).commit();
                break;
            case 9:
                final Intent i =new Intent(MainActivity.this,WebViewActivity.class);
                URL = "http://"+ipadd+":8080/noticeboard/answerviewquery.jsp";
                i.putExtra("wwurl",URL);
                startActivity(i);
                return;
        }
    }
    public void onSectionAttached(int number) {

        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
               break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
            case 6:
                mTitle = getString(R.string.title_section6);
                break;
            case 7:
                mTitle = getString(R.string.title_section7);
                break;
            case 8:
                mTitle = getString(R.string.title_section8);
                break;
            case 9:
                mTitle = getString(R.string.title_section9);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

            // set prompts.xml to alertdialog builder



            final EditText input = new EditText(MainActivity.this);
            input.setHint("Enter your account PASSWORD");


            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setTitle("Verify Password")
                    .setMessage("Before you manage your account, we want to verify your Notice board account password..!!")
                    .setIcon(R.drawable.up)
                    .setView(input)
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            })
                    .setPositiveButton("Verify Password",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // get user input and set it to result
                                    // edit text
                                    uidd=input.getText().toString();
                                    if(uidd.equals("")){
                                        Toast.makeText(getApplicationContext(), "Please enter your Notice board account PASSWORD and then click on Verify Password button..!!", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {


                                        URL="http://"+ipadd+":8080/noticeboard/login?acc="+param+"&pa="+uidd+"";
                                        new DownloadFileAsyncf().execute();
                                    }
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
            return true;
        }

        if (id == R.id.action_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Confirm Logout");
            builder.setMessage("Are you sure you want to Logout ?")
                    .setCancelable(false)
                    .setIcon(R.drawable.up)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);


            Button b1=(Button)rootView.findViewById(R.id.button);
           final Spinner s1=(Spinner)rootView.findViewById(R.id.spinner);
            final Spinner s2=(Spinner)rootView.findViewById(R.id.spinner2);
            final Spinner s3=(Spinner)rootView.findViewById(R.id.spinner3);


            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.branch, android.R.layout.simple_spinner_item);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            s1.setAdapter(adapter);

            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                    R.array.sem, android.R.layout.simple_spinner_item);

            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            s2.setAdapter(adapter1);

            ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity(),
                    R.array.test, android.R.layout.simple_spinner_item);

            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            s3.setAdapter(adapter3);



            b1.setOnClickListener(new View.OnClickListener() {

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
    alertDialog.setMessage("Select the Branch, Sem and Test number..!!");
    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    });
    alertDialog.show();


}
                    else{
    URL="http://"+ipadd+":8080/noticeboard/ia?branch="+spin1+"&sem="+spin2+"&testno="+spin3+"";
    new DownloadFileAsync().execute();
                    }

                }
            });
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        class DownloadFileAsync extends AsyncTask<String, String, String> {


            final Intent intent = new Intent(getActivity(), iadates.class);

            ProgressDialog progressDialog;
            //String result,checks;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(getActivity(), "", "Getting the IA dates...");

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
                    intent.putExtra("testno",spin3);
                    intent.putExtra("msg",checks);
                    startActivity(intent);
                }
            }
        }




    }

    class DownloadFileAsyncf extends AsyncTask<String, String, String> {


        final Intent intent1 = new Intent(MainActivity.this, updateacc.class);

        ProgressDialog progressDialog;
        //String result,checks;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity.this, "", "Verifying your password..!!");

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
                intent1.putExtra("uid",param);
                startActivity(intent1);
            }

            if(checks.equals("wrong")||checks.equals("acc does not exist")){
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setIcon(R.drawable.up);
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Entered Password is wrong..!!");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    } });
                alertDialog.show();
            }
        }
    }


    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Error..!!");
        alertDialog.setIcon(R.drawable.up);
        alertDialog.setCancelable(false);
        alertDialog.setMessage("You can't go back, you can Logout instead.");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            } });
        alertDialog.show();

    }


}
