package com.spree.spree_app;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class Event_main extends ActionBarActivity {
    TextView event_title,event_info;
    ImageView event_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);
        Toolbar toolbar;
        toolbar= (Toolbar) findViewById(R.id.event_app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");
        String icon=intent.getExtras().getString("icon_id");

        event_title= (TextView) findViewById(R.id.event_title);
        event_icon= (ImageView) findViewById(R.id.event_icon);
        int icon_id = event_icon.getResources().getIdentifier(icon, "drawable", getPackageName());
        event_title.setText(title);
        event_icon.setImageResource(icon_id);
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

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        if (id == R.id.notify_img){
            Intent I=new Intent(this,Notify.class);
            startActivity(I);
        }
        return super.onOptionsItemSelected(item);
    }
}
