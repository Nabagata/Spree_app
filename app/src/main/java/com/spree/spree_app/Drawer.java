package com.spree.spree_app;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;


/**
 * A simple {@link Fragment} subclass.
 */
public class Drawer extends Fragment {

    private ActionBarDrawerToggle mdrawertoggle;
    private static DrawerLayout mdrawerlayout;
    private RecyclerView recyclerView;
    private View drawer_container;
    private R_ViewAdapter adapter;
    public Drawer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_drawer, container, false);
        recyclerView= (RecyclerView) layout.findViewById(R.id.r_view);

        adapter=new R_ViewAdapter(getActivity(),get_data());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public List<List_item> get_data(){
        List<List_item> data=new ArrayList<>();
        int[] icons={R.drawable.event,R.drawable.pro,R.drawable.attt,R.drawable.attt,R.drawable.attt,R.drawable.attt};
        String[] titles={"Theme","Events","Spotlights","Proshow","Initiative","Workshop","Attraction","Team","Login","About"};
        SharedPreferences prefs = this.getActivity().getSharedPreferences("spree_login", this.getActivity().MODE_PRIVATE);
        String restoredText = prefs.getString("username", null);
        if(restoredText!=null){
            titles[8]="logout";
        }
        for (int i=0;i<titles.length;i++){
           List_item current= new List_item();
            current.icon_id=icons[0];
            current.title=titles[i];
            data.add(current);
        }
        return data;
    }
    public void setup(int drawer_fragment, DrawerLayout drawerLayout, Toolbar toolbar) {
    mdrawerlayout=drawerLayout;
        drawer_container=getActivity().findViewById(drawer_fragment);
        mdrawertoggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            public void OnDrawerOpened(View drawer_view){
                super.onDrawerOpened(drawer_view);
                getActivity().invalidateOptionsMenu();
            }
            public void OnDrawerClosed(View drawer_view){
                super.onDrawerClosed(drawer_view);
                getActivity().invalidateOptionsMenu();
            }
        };
        mdrawerlayout.setDrawerListener(mdrawertoggle);
        mdrawerlayout.post(new Runnable() {
            @Override
            public void run() {
                mdrawertoggle.syncState();
            }
        });
    }

    public static void close() {
        mdrawerlayout.closeDrawers();
    }
}
