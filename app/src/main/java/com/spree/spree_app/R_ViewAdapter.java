package com.spree.spree_app;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by A on 2/5/2016.
 */
public class R_ViewAdapter extends RecyclerView.Adapter<R_ViewAdapter.R_ViewHolder> {

    private LayoutInflater inflater;
    List<List_item> data= Collections.emptyList();
    private Context item_context;

    public R_ViewAdapter(Context context,List<List_item> data){
        inflater= LayoutInflater.from(context);
        this.data=data;
        item_context=context;
    }
    @Override
    public R_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.list_item, parent, false);
        R_ViewHolder holder=new R_ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(R_ViewHolder holder, int position) {
        List_item current=data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.icon_id);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class R_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView icon;

        public R_ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title= (TextView) itemView.findViewById(R.id.list_item_title);
            icon= (ImageView) itemView.findViewById(R.id.list_item_icon);
        }

        @Override
        public void onClick(View v) {

            try {
                Drawer.close();
                Intent I=null;
                String type=title.getText().toString().toLowerCase();
                String query=null;
                if(!(type.equals("login") || type.equals("team") ||type.equals("about") ||type.equals("logout") )){

                    if(type.equals("events")){
                        Log.d("sachin", "detected");
                        I=new Intent(item_context,Event_9.class);
                    }


                    else {
                        I=new Intent(item_context,Class.forName("com.spree.spree_app.Events"));

                        if (type.equals("spotlights"))
                            query = "select category1,event_name,remarks,id from events where category1='spotlight' ";
                        else if (type.equals("proshow"))
                            query = "select category1,event_name,remarks,id from events where type='proshows' ";
                        else if (type.equals("initiative"))
                            query = "select category1,event_name,remarks,id from events where type='initiative' ";
                        else if (type.equals("workshop"))
                            query = "select category1,event_name,remarks,id from events where type='workshop' ";
                        else if (type.equals("attraction"))
                            query = "select category1,event_name,remarks,id from events where type='attractions' ";

                        I.putExtra("category1", query);
                        I.putExtra("Title",(char)(type.charAt(0)-32)+type.substring(1));
                    }
                }
                else{
                    if (type.equals("login")){
                        I=new Intent(item_context,Login.class);
                    }
                    else if (type.equals("team")){
                        I=new Intent(item_context,Team.class);
                    }else if (type.equals("about")){
                        I=new Intent(item_context,About.class);
                    }
                    else if(type.equals("logout")){
                        SharedPreferences.Editor editor = item_context.getSharedPreferences("spree_login",item_context.MODE_PRIVATE).edit();
                        editor.remove("userid");
                        editor.commit();
                        I=new Intent(item_context,Login.class);
                        I.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    }

                }
                item_context.startActivity(I);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
