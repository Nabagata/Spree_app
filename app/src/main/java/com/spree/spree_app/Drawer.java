package com.spree.spree_app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Drawer extends Fragment {

    private ActionBarDrawerToggle mdrawertoggle;
    private DrawerLayout mdrawerlayout;
    public Drawer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drawer, container, false);
    }


    public void setup(DrawerLayout drawerLayout,Toolbar toolbar) {
    mdrawerlayout=drawerLayout;
        mdrawertoggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            public void OnDrawerOpened(View drawer_view){
                super.onDrawerOpened(drawer_view);
            }
            public void OnDrawerClosed(View drawer_view){
                super.onDrawerClosed(drawer_view);
            }
        };
        mdrawerlayout.setDrawerListener(mdrawertoggle);
    }
}
