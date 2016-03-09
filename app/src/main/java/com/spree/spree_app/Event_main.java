package com.spree.spree_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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


    class MinMaxAsync extends AsyncTask<String,String,String> {

        String mEventId;
        Context mContext;
        MinMaxAsync(String eventId,Context context)
        {
            this.mContext=context;
            this.mEventId=eventId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("retrofit","preexecute");

        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("retrofit", "network request"+mEventId);
            Call<String> call= Event_main.apiService.fetchMinMaxEvent(mEventId);
            String response="";

            try{
                response=call.execute().body();
                Log.d("retrofit","response do : "+response);
            }
            catch(Exception e)
            {
                Log.d("retrofit","response doex : "+response);
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
//            StringTokenizer st=new StringTokenizer(s," ");
//            int min=Integer.parseInt(st.nextToken());
//            int max=Integer.parseInt(st.nextToken());
//            Toast.makeText(this.mContext,"Min = "+min+"max = "+max,Toast.LENGTH_LONG).show();
            Log.d("retrofit","response : "+s);
        }
    }



    class RegisterAsync extends AsyncTask<String,String,String> {

        String mEventId;
        ArrayList<String> mUserIds;
        Context mContext;
        RegisterAsync(ArrayList<String> userIds,String eventId,Context context)
        {
            this.mContext=context;
            this.mEventId=eventId;
            this.mUserIds=new ArrayList<>();
            this.mUserIds=userIds;
        }
        @Override
        protected String doInBackground(String... params) {
            Call<String> call= Event_main.apiService.registerTeam(new EventRegisterRetrofitClass(this.mEventId,this.mUserIds));
            String response="";
            try{
                response=call.execute().body();
            }
            catch(Exception e)
            {
                Log.d("retrofit","response ex: "+response);
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {

            Log.d("retrofit","response : "+s);
        }
    }





    TextView event_title,description;
    ImageView event_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);
        Toolbar toolbar;
        toolbar= (Toolbar) findViewById(R.id.event_app_toolbar);
        setSupportActionBar(toolbar);
       /* getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Constants.base_url_reg)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Event_main.apiService=retrofit.create(SpreeApiEndpointInterface.class);
        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");
        String icon=intent.getExtras().getString("icon_id");
        String event_id=intent.getExtras().getString("event_id");
        String event_type=intent.getExtras().getString("event_type");
        Toast.makeText(getApplicationContext(), "event id = " + event_id, Toast.LENGTH_LONG).show();
        event_title= (TextView) findViewById(R.id.event_title);
        description= (TextView) findViewById(R.id.event_text);
        event_icon= (ImageView) findViewById(R.id.event_icon);
        int icon_id = event_icon.getResources().getIdentifier(icon, "drawable", getPackageName());
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/Baron Neue Bold.otf");
        event_title.setTypeface(face);
        event_title.setText(title);
        event_icon.setImageResource(icon_id);
        description.setText(intent.getExtras().getString("description"));
        //new MinMaxAsync(event_id,getApplicationContext()).execute();
        ArrayList<String> users=new ArrayList<>();
        users.add("9687419");
        new RegisterAsync(users,event_id,getApplicationContext()).execute();
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

        if (id == R.id.notify_img){
            Intent I=new Intent(this,Notify.class);
            startActivity(I);
        }
        return super.onOptionsItemSelected(item);
    }
}
