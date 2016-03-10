package com.spree.spree_app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Theme extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        setTitle("Theme");
        TextView text = (TextView) findViewById(R.id.theme_text);
        text.setText("SpringSpree was introduced in 1978 as a platform for students to showcase their talents outside of their academic performances. 36 years later, SpringSpree continues to be an evergreen platform for students across the nation to come together to celebrate their cultural talents. Over the years, we've had famous and ingenious artists taking part in SpringSpree with their majestic performances and esteemed prescence.\n" +
                "\n" +
                "This year, we hope to take our event to the next level. To ensure the outreach of cultural aspects both Indian and International, we present to you SpringSpree's theme of 2016: \"Silver Screen\".\n" +
                "\n" +
                "Films help stimulate the mind and allow our creativity to shine. Movies dominate the entertainment industry entirely and have an impact on us socially, politically and economically. They are a major part of our lives and deserve to be celebrated. This year we aim to unite every cultural aspect of the movie industry, be it Bollywood or Hollywood, and celebrate the movies we love. Prepare yourselves to rejoice as we bring your favorite movies to life!\n" +
                "\n" +
                "\"Indian Cinema has virtually become a parallel culture. Talk of India with a foreigner and debates virtually centres around Indian films.\" - Amitabh Bachhan \n \n" );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_theme, menu);
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
        }

        return super.onOptionsItemSelected(item);*/
        return  false;
    }
}
