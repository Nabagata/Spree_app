package com.spree.spree_app;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;


public class SpreeMain extends ActionBarActivity {
    private Toolbar toolbar;
    private int col=3;
    public String[] events = {
            "Google",
            "Github",
            "Instagram",
            "Facebook",
            "Flickr",
            "Pinterest",
            "Quora",
            "Twitter",
            "Vimeo",
            "WordPress",
            "Youtube",
            "Stumbleupon",
            "SoundCloud",
            "Reddit",
            "Blogger"
    } ;
    private int rows= (int) Math.ceil(events.length/3.0);
    /*int[] imageId = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8,
            R.drawable.image9,
            R.drawable.image10,
            R.drawable.image11,
            R.drawable.image12,
            R.drawable.image13,
            R.drawable.image14,
            R.drawable.image15

    };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spree_main);
        toolbar= (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        populate_grid();
        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment,(DrawerLayout) findViewById(R.id.drawer_layout),toolbar);
    }

    private void populate_grid() {
        TableLayout grid_table= (TableLayout) findViewById(R.id.grid_table);
       // grid_table.getLayoutParams().height = 300;
       // grid_table.requestLayout();
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
            for (int j=0;j<col;j++){
                ImageView event_image=new ImageView(this);
                event_image.setImageResource(R.drawable.pic);
                event_image.setPadding(1,1,1,1);
               // tableRowParams.setMargins(0,1,0,1);
                grid_row.setLayoutParams(tableRowParams);
                grid_row.addView(event_image);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spree_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        if (id == R.id.notify_img){
            Intent I=new Intent(this,Notify.class);
            startActivity(I);
        }

        return super.onOptionsItemSelected(item);
    }
}
