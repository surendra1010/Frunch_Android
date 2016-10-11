package com.tentenlabs.frunch.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tentenlabs.frunch.R;
import com.tentenlabs.frunch.utils.MenuItemObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seerasu1 on 11/10/16.
 */
public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MenuHolder> {

    List<MenuItemObject> menuItemObjectList = new ArrayList<MenuItemObject>();

    public MenuListAdapter(List<MenuItemObject> menuItemObjectList) {
        this.menuItemObjectList = menuItemObjectList;
    }

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item,parent,false);
        return new MenuHolder(view);
    }

    @Override
    public int getItemCount() {
        return menuItemObjectList.size();
    }

    @Override
    public void onBindViewHolder(MenuHolder holder, int position) {
        holder.name.setText(menuItemObjectList.get(position).getName());
        holder.desc.setText(menuItemObjectList.get(position).getDescr());
        holder.spice.setText("Spice level : "+menuItemObjectList.get(position).getSpiceLevel());
        holder.price.setText("$ "+String.valueOf(menuItemObjectList.get(position).getPrice()));
    }

    class MenuHolder extends RecyclerView.ViewHolder {

        TextView name, desc, spice, price;

        public MenuHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.name);
            desc = (TextView)itemView.findViewById(R.id.description);
            spice = (TextView)itemView.findViewById(R.id.spice_level);
            price = (TextView)itemView.findViewById(R.id.price);

        }
    }
}
