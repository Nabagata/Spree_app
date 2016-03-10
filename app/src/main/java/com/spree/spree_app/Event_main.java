package com.spree.spree_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.StringTokenizer;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class Event_main extends ActionBarActivity {
    private static SpreeApiEndpointInterface apiService;
    ImageView phone_btn, register_btn, share_btn;
    Database sdb = new Database(this);
    SQLiteDatabase db;
    private ProgressDialog mProgressDialog;

    private void showProgressDialog()
    {
        if(mProgressDialog==null)
        {
            mProgressDialog=new ProgressDialog(this);
            mProgressDialog.setMessage("Registering you for the event");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    private void hideProgressDialog()
    {
        if(mProgressDialog!=null && mProgressDialog.isShowing())
            mProgressDialog.hide();
    }
    class MinMaxAsync extends AsyncTask<String, String, String> {

        String mEventId;
        Context mContext;

        MinMaxAsync(String eventId, Context context) {
            this.mContext = context;
            this.mEventId = eventId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("retrofit", "preexecute");

        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("retrofit", "network request" + mEventId);
            Call<String> call = Event_main.apiService.fetchMinMaxEvent(mEventId);
            String response = "";

            try {
                response = call.execute().body();
                Log.d("retrofit", "response do : " + response);
            } catch (Exception e) {
                Log.d("retrofit", "response doex : " + response);
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {

            Log.d("retrofit", "response : " + s);
        }
    }


    class RegisterAsync extends AsyncTask<String, String, String> {

        String mEventId;
        ArrayList<String> mUserIds;
        Context mContext;

        RegisterAsync(ArrayList<String> userIds, String eventId, Context context) {
            this.mContext = context;
            this.mEventId = eventId;
            this.mUserIds = new ArrayList<>();
            this.mUserIds = userIds;
        }

        @Override
        protected String doInBackground(String... params) {
            Call<String> call = Event_main.apiService.registerTeam(new EventRegisterRetrofitClass(this.mEventId, this.mUserIds));
            String response = "";
            try {
                response = call.execute().body();
            } catch (Exception e) {
                Log.d("retrofit", "response ex: " + response);
                e.printStackTrace();
            }
            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("invalid")) {
                hideProgressDialog();
                Toast.makeText(getApplicationContext(), "You cannot register for this event from the app.", Toast.LENGTH_LONG).show();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://register.springspree.in"));
                startActivity(browserIntent);

            } else {
                hideProgressDialog();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                Log.d("retrofit", "response : " + s);
            }
        }
    }


    TextView event_title, description, event_manager, time, venue;
    ImageView event_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);
        Toolbar toolbar;

        db = sdb.create_db();

        toolbar = (Toolbar) findViewById(R.id.event_app_toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences prefs = getSharedPreferences("spree_login", MODE_PRIVATE);
        final String userid = prefs.getString("userid", "-1");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.base_url_reg)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Event_main.apiService = retrofit.create(SpreeApiEndpointInterface.class);
        Intent intent = getIntent();
        register_btn = (ImageView) findViewById(R.id.register_button);
        phone_btn = (ImageView) findViewById(R.id.phone_button);
        share_btn = (ImageView) findViewById(R.id.share_button);


        event_manager = (TextView) findViewById(R.id.event_manager);
        time = (TextView) findViewById(R.id.event_date);
        venue = (TextView) findViewById(R.id.event_venue);

        String title = intent.getExtras().getString("title");
        setTitle(title);
        String icon = intent.getExtras().getString("icon_id");
        final String event_id = intent.getExtras().getString("event_id");
        String event_type = intent.getExtras().getString("event_type");

        event_title = (TextView) findViewById(R.id.event_title);
        description = (TextView) findViewById(R.id.event_text);
        event_icon = (ImageView) findViewById(R.id.event_icon);

        int icon_id = event_icon.getResources().getIdentifier(icon, "drawable", getPackageName());

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Baron Neue Bold.otf");
        event_title.setTypeface(face);

        event_title.setText(title);
        event_icon.setImageResource(icon_id);
        description.setText(intent.getExtras().getString("description"));
        //new MinMaxAsync(event_id,getApplicationContext()).execute();

        final Cursor cr = db.rawQuery("select evm_name,evm_contact,venue from events where id=" + event_id + "", null);
        cr.moveToNext();

        event_manager.setText(cr.getString(cr.getColumnIndex("evm_name")) + " : " + cr.getString(cr.getColumnIndex("evm_contact")));
        venue.setText(cr.getString(cr.getColumnIndex("venue")));


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userid.equals("-1")) {
                    Intent I = new Intent(getApplicationContext(), Login.class);
                    I.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(I);
                } else {
                    if (isNetworkAvailable()) {
                        ArrayList<String> users = new ArrayList<>();
                        users.add(userid);
                        showProgressDialog();
                        new RegisterAsync(users, event_id, getApplicationContext()).execute();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Hello, I will be attending "+event_title.toString()+" event at Springspree'16, NIT Warangal. Go to http://www.springspree.in/ for more details. Hope to see you there.";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+cr.getString(cr.getColumnIndex("evm_contact"))));
                startActivity(intent);
            }
        });
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

//        if (id == R.id.notify_img) {
//            Intent I = new Intent(this, Notify.class);
//            startActivity(I);
//        }
//        return super.onOptionsItemSelected(item);
        return  false;
    }
}
