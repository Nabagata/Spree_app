package com.spree.spree_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;


public class Event_9 extends ActionBarActivity {
    private Toolbar toolbar;
    private int col=2;
    private int rows;
    private int count=0;
    public Cursor cr;
    Database sdb=new Database(this);
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spree_main);
        toolbar= (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        db=sdb.create_db();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        populate_grid();



        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        Toast.makeText(this, this.getExternalFilesDir(null).getAbsolutePath(), Toast.LENGTH_SHORT).show();

    }

    private void populate_grid() {
        TableLayout grid_table= (TableLayout) findViewById(R.id.grid_table);
        cr=db.rawQuery("select distinct category_new from events where category_new > 0 order by category_new ASC",null);
        rows= (int) Math.ceil(cr.getCount()/ 2.0);
        for (int i=0;i<rows;i++){
            TableRow grid_row=new TableRow(this);
            TableLayout.LayoutParams tableRowParams=
                    new TableLayout.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT,
                            1.0f
                    );
            grid_row.setLayoutParams(tableRowParams);
            grid_table.addView(grid_row);

            for (int j=0;j<col && cr.moveToNext();j++){
                final int category1;
                category1=Integer.parseInt(cr.getString(cr.getColumnIndex("category_new")));
                Toast.makeText(getApplicationContext()," "+category1,Toast.LENGTH_SHORT).show();
                ImageView event_image=new ImageView(this);
                event_image.setImageResource(R.drawable.pic);
                event_image.setPadding(1,1,1,1);
                event_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        direct(v,category1);
                    }
                });
                grid_row.setLayoutParams(tableRowParams);
                grid_row.addView(event_image);
            }
        }
    }
    public void direct(View v,int category1){
        Intent I=new Intent(Event_9.this,Events.class);
        // String query="select event_name,remarks from events where category=' "+category1+" ' ";
        String query="select event_name,remarks from events where category_new="+category1+" order by category_new ASC";
        I.putExtra("category1", query);
        startActivity(I);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_9, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
