package com.spree.spree_app;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class Events extends ActionBarActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar;
        toolbar= (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        /*getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new Adapter_Events(Events.this,getdata());
        mRecyclerView.setAdapter(mAdapter);

    }

    private List<Event_list_item> getdata() {
        List<Event_list_item> data=new ArrayList<>();
        int[] icons={R.drawable.wide,R.drawable.wide,R.drawable.wide};
        String[] titles={"Events","Proshows","Attractions"};
        for (int i=0;i<titles.length && i<icons.length;i++){
            Event_list_item current= new Event_list_item();
            current.icon_id=icons[i];
            current.title=titles[i];
            data.add(current);
        }
        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events, menu);
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
