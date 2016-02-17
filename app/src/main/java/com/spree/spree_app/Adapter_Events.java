package com.spree.spree_app;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
 * Created by A on 2/13/2016.
 */
public class Adapter_Events  extends RecyclerView.Adapter<Adapter_Events.R_ViewHolder> {


        private LayoutInflater inflater;
        List<Event_list_item> data= Collections.emptyList();
        private Context item_context;

        public Adapter_Events(Context context, List<Event_list_item> data){
            inflater= LayoutInflater.from(context);
            this.data=data;
            item_context=context;
        }
        @Override
        public R_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=inflater.inflate(R.layout.card_item, parent, false);
            R_ViewHolder holder=new R_ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(R_ViewHolder holder, int position) {
            Event_list_item current=data.get(position);
            holder.title.setText(current.title);
            holder.description.setText(current.description);
            holder.icon.setImageResource(current.icon_id);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class R_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView title,description;
            ImageView icon;

            public R_ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                title= (TextView) itemView.findViewById(R.id.card_title);
                description=(TextView) itemView.findViewById(R.id.card_text);
                icon= (ImageView) itemView.findViewById(R.id.card_icon);
            }

            @Override
            public void onClick(View v) {

                try {
                    Intent I=new Intent(item_context,Class.forName("com.spree.spree_app.Event_main"));
                    I.putExtra("title", title.getText());
                    I.putExtra("icon_id", "wide");
                    I.putExtra("description",description.getText());
                    item_context.startActivity(I);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }


}
