package com.frunch.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frunch.main.R;
import com.frunch.main.utils.RestarantObject;

import java.util.List;

/**
 * Created by seerasu1 on 22/10/16.
 */
public class DeliverRestaurantsAdapter extends RecyclerView.Adapter<DeliverRestaurantsAdapter.ViewHolder> {

    Context context;
    List<RestarantObject> restarantObjectList;

    public DeliverRestaurantsAdapter(Context context,List<RestarantObject> restarantObjectList) {
        this.context = context;
        this.restarantObjectList = restarantObjectList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restarant_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return restarantObjectList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(restarantObjectList.get(position).getName());
        holder.description.setText(restarantObjectList.get(position).getDescription());
        holder.phone.setText(restarantObjectList.get(position).getPhone());
        holder.checkin.setVisibility(View.VISIBLE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout restarantItem;
        TextView name, description, phone;
        ImageView checkin;

        public ViewHolder(View itemView) {
            super(itemView);
            restarantItem = (LinearLayout)itemView.findViewById(R.id.restarant_item);
            name = (TextView)itemView.findViewById(R.id.name);
            description = (TextView)itemView.findViewById(R.id.description);
            phone = (TextView)itemView.findViewById(R.id.phone);
            checkin = (ImageView)itemView.findViewById(R.id.check_in);
        }
    }
}
