package com.spree.spree_app;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by A on 2/5/2016.
 */
public class R_ViewAdapter extends RecyclerView.Adapter<R_ViewAdapter.R_ViewHolder> {

    private LayoutInflater inflater;
    List<List_item> data= Collections.emptyList();

    public R_ViewAdapter(Context context,List<List_item> data){
        inflater= LayoutInflater.from(context);
        this.data=data;
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

    class R_ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView icon;
        public R_ViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.list_item_title);
            icon= (ImageView) itemView.findViewById(R.id.list_item_icon);
        }
    }
}
