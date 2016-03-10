package com.spree.spree_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    public String category1;
    public Cursor cr;
    Database sdb=new Database(this);
    SQLiteDatabase db=sdb.create_db();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar;
        toolbar= (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        setTitle(this.getIntent().getExtras().getString("Title").toString());

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new Adapter_Events(Events.this,getdata());
        mRecyclerView.setAdapter(mAdapter);

    }

    private List<Event_list_item> getdata() {
        List<Event_list_item> data=new ArrayList<>();
        int[] icons={R.drawable.wide,R.drawable.wide,R.drawable.wide};
        category1=getIntent().getExtras().getString("category1", null);
        cr = db.rawQuery(category1, null);
        for (int i = 0; cr.moveToNext(); i++) {
            Event_list_item current = new Event_list_item();

            current.title = cr.getString(cr.getColumnIndex("event_name"));
            current.description = cr.getString(cr.getColumnIndex("remarks"));
            current.event_type=cr.getString(cr.getColumnIndex("category1"));
            current.event_id=cr.getString(cr.getColumnIndex("id"));
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
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

       /* if (id == R.id.notify_img){
            Intent I=new Intent(this,Notify.class);
            startActivity(I);
        }
        return super.onOptionsItemSelected(item);*/
        return  false;
    }
}
