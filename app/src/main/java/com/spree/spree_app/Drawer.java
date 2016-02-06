package com.spree.spree_app;


import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

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
    private RecyclerView recyclerView;
    private View drawer_container;
    private R_ViewAdapter adapter;
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
        View layout=inflater.inflate(R.layout.fragment_drawer, container, false);
        recyclerView= (RecyclerView) layout.findViewById(R.id.r_view);

        adapter=new R_ViewAdapter(getActivity(),get_data());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public static List<List_item> get_data(){
        List<List_item> data=new ArrayList<>();
        int[] icons={R.drawable.event,R.drawable.pro,R.drawable.attt};
        String[] titles={"Events","Proshows","Attractions"};
        for (int i=0;i<titles.length && i<icons.length;i++){
           List_item current= new List_item();
            current.icon_id=icons[i];
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
            mdrawerlayout.openDrawer(drawer_container);
        }
        mdrawerlayout.setDrawerListener(mdrawertoggle);
        mdrawerlayout.post(new Runnable(){

            @Override
            public void run() {
                mdrawertoggle.syncState();
            }
        });
    }

    public void savesharedpreferences(Context context, String name, String value){
        SharedPreferences sharedPreferences=context.getSharedPreferences(FILE_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(name,value);
        editor.commit();
    }

    public String readsharedpreferences(Context context, String name, String value){
        SharedPreferences sharedPreferences=context.getSharedPreferences(FILE_NAME,context.MODE_PRIVATE);
        return sharedPreferences.getString(name,value);
    }
}
