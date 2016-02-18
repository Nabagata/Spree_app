package com.spree.spree_app;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
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
                Toast.makeText(item_context,""+type,Toast.LENGTH_SHORT).show();
                if(!(type.equals("login") || type.equals("team") ||type.equals("about") )){
                    I=new Intent(item_context,Class.forName("com.spree.spree_app.Events"));
                    I.putExtra("type",type);
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
                }
                item_context.startActivity(I);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
