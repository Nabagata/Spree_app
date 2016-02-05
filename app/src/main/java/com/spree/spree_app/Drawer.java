package com.spree.spree_app;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.crypto.Cipher;


/**
 * A simple {@link Fragment} subclass.
 */
public class Drawer extends Fragment {

    private ActionBarDrawerToggle mdrawertoggle;
    private DrawerLayout mdrawerlayout;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstance;
    private final String USER_HAS_LEARNED="user has learned";
    private String FILE_NAME="spree_shared_file";
    public Drawer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mUserLearnedDrawer=Boolean.valueOf(readsharedpreferences(getActivity(),USER_HAS_LEARNED,"false"));
        if (savedInstanceState!=null){
            mFromSavedInstance=true;
        }

        return inflater.inflate(R.layout.fragment_drawer, container, false);
    }


    public void setup(DrawerLayout drawerLayout,Toolbar toolbar) {
    mdrawerlayout=drawerLayout;
        mdrawertoggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            public void OnDrawerOpened(View drawer_view){
                super.onDrawerOpened(drawer_view);
                if (!mUserLearnedDrawer){
                    mUserLearnedDrawer=true;
                    savesharedpreferences(getActivity(),USER_HAS_LEARNED,mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }
            public void OnDrawerClosed(View drawer_view){
                super.onDrawerClosed(drawer_view);
                getActivity().invalidateOptionsMenu();
            }
        };
        if (!mUserLearnedDrawer && !mFromSavedInstance){

        }
        mdrawerlayout.setDrawerListener(mdrawertoggle);
    }

    public static void savesharedpreferences(Context context,String name,String value){
        SharedPreferences sharedPreferences=context.getSharedPreferences(FILE_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(name,value);
        editor.commit();
    }

    public static String readsharedpreferences(Context context,String name,String value){
        SharedPreferences sharedPreferences=context.getSharedPreferences(FILE_NAME,context.MODE_PRIVATE);
        return sharedPreferences.getString(name,value);
    }
}
