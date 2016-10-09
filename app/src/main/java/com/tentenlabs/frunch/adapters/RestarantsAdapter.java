package com.tentenlabs.frunch.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tentenlabs.frunch.R;
import com.tentenlabs.frunch.RestarantMenu;
import com.tentenlabs.frunch.utils.RestarantObject;

import java.util.List;

/**
 * Created by seerasu1 on 09/10/16.
 */
public class RestarantsAdapter extends RecyclerView.Adapter<RestarantsAdapter.RestarantHolder> {

    Context context;
    List<RestarantObject> restarantObjectList;

    public RestarantsAdapter(Context context,List<RestarantObject> restarantObjectList) {
        this.context = context;
        this.restarantObjectList = restarantObjectList;
    }

    @Override
    public RestarantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restarant_list_item,parent,false);
        return new RestarantHolder(view);
    }

    @Override
    public void onBindViewHolder(RestarantHolder holder, final int position) {
        holder.name.setText(restarantObjectList.get(position).getName());
        holder.description.setText(restarantObjectList.get(position).getDescription());
        holder.phone.setText(restarantObjectList.get(position).getPhone());
        holder.restarantItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,RestarantMenu.class);
                intent.putExtra("restarant",restarantObjectList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restarantObjectList.size();
    }

    class RestarantHolder extends RecyclerView.ViewHolder {

        LinearLayout restarantItem;
        TextView name, description, phone;

        public RestarantHolder(View itemView) {
            super(itemView);
            restarantItem = (LinearLayout)itemView.findViewById(R.id.restarant_item);
            name = (TextView)itemView.findViewById(R.id.name);
            description = (TextView)itemView.findViewById(R.id.description);
            phone = (TextView)itemView.findViewById(R.id.phone);
        }
    }


}
