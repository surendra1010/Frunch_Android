package com.tentenlabs.frunch.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tentenlabs.frunch.R;
import com.tentenlabs.frunch.utils.MenuItemObject;

import java.util.List;

/**
 * Created by seerasu1 on 16/10/16.
 public SelectedIt*/
public class SelectedItemsAdapter extends RecyclerView.Adapter<SelectedItemsAdapter.SelectedItemsHolder>{

    List<MenuItemObject> selectedItemsList;

    public SelectedItemsAdapter(List<MenuItemObject> selectedItemsList) {
        this.selectedItemsList = selectedItemsList;
    }

    @Override
    public SelectedItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_list_item,parent,false);
        return new SelectedItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(final SelectedItemsHolder holder, int position) {
        final MenuItemObject menuItemObject = selectedItemsList.get(position);
        holder.name.setText(menuItemObject.getName());
        holder.desc.setText(menuItemObject.getDescr());
        holder.spice.setText("Spice level : "+menuItemObject.getSpiceLevel());
        holder.price.setText("$ "+String.valueOf(menuItemObject.getPrice()));

        if (menuItemObject.getSelected() == 1) {
            holder.totalLayout.setVisibility(View.VISIBLE);
            holder.total_quantity.setText(String.valueOf(menuItemObject.getQuantity()));
            holder.total_amount.setText("$ " + String.valueOf(menuItemObject.getTotalAmount()));
        } else {
            holder.totalLayout.setVisibility(View.GONE);
        }

        holder.quantity_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuItemObject.getQuantity() > 0) {
                    menuItemObject.setQuantity(menuItemObject.getQuantity() - 1);
                    updateQuantity(menuItemObject,holder);
                }
            }
        });

        holder.quantity_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuItemObject.setQuantity(menuItemObject.getQuantity() + 1);
                updateQuantity(menuItemObject,holder);
            }
        });
    }

    private void updateQuantity(MenuItemObject menuItemObject, SelectedItemsHolder holder) {
        double totalCost = menuItemObject.getPrice() * menuItemObject.getQuantity();
        menuItemObject.setTotalAmount(totalCost);
        holder.total_amount.setText("$ " + String.valueOf(menuItemObject.getTotalAmount()));
        holder.total_quantity.setText(String.valueOf(menuItemObject.getQuantity()));
    }


    @Override
    public int getItemCount() {
        return selectedItemsList.size();
    }

    class SelectedItemsHolder extends RecyclerView.ViewHolder {
        LinearLayout totalLayout;
        TextView name, desc, spice, price, total_quantity, total_amount;
        ImageView quantity_minus, quantity_plus;

        public SelectedItemsHolder(View itemView) {
            super(itemView);
            totalLayout = (LinearLayout) itemView.findViewById(R.id.totallayout);
            name = (TextView)itemView.findViewById(R.id.name);
            desc = (TextView)itemView.findViewById(R.id.description);
            spice = (TextView)itemView.findViewById(R.id.spice_level);
            price = (TextView)itemView.findViewById(R.id.price);
            total_quantity = (TextView) itemView.findViewById(R.id.total_quantity);
            total_amount = (TextView) itemView.findViewById(R.id.total_amount);
            quantity_minus = (ImageView) itemView.findViewById(R.id.quantity_minus);
            quantity_plus = (ImageView) itemView.findViewById(R.id.quantity_plus);
        }
    }


}
