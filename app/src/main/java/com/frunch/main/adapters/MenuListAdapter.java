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
import com.frunch.main.utils.MenuItemObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seerasu1 on 11/10/16.
 */
public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MenuHolder> {

    List<MenuItemObject> menuItemObjectList = new ArrayList<MenuItemObject>();

    MenuItemSelectedListener menuItemSelectedListener;

    public MenuListAdapter(Context context, List<MenuItemObject> menuItemObjectList) {
        menuItemSelectedListener = (MenuItemSelectedListener) context;
        this.menuItemObjectList = menuItemObjectList;
    }

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item, parent, false);
        return new MenuHolder(view);
    }

    @Override
    public int getItemCount() {
        return menuItemObjectList.size();
    }

    @Override
    public void onBindViewHolder(final MenuHolder holder, final int position) {
        final MenuItemObject menuItemObject = menuItemObjectList.get(position);
        holder.name.setText(menuItemObject.getName());
        holder.desc.setText(menuItemObject.getDescr());
        holder.spice.setText("Spice level : " + menuItemObject.getSpiceLevel());
        holder.price.setText("$ " + String.valueOf(menuItemObject.getPrice()));

        if (menuItemObject.getSelected() == 1) {
            holder.selectedIcon.setVisibility(View.VISIBLE);
            holder.totalLayout.setVisibility(View.VISIBLE);
            holder.total_quantity.setText(String.valueOf(menuItemObject.getQuantity()));
        } else {
            holder.selectedIcon.setVisibility(View.INVISIBLE);
            holder.totalLayout.setVisibility(View.GONE);
        }

        holder.menuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuItemObject.getSelected() != 1) {
                    menuItemObject.setSelected(1);
                    holder.selectedIcon.setVisibility(View.VISIBLE);
                    holder.totalLayout.setVisibility(View.VISIBLE);
                    menuItemObject.setQuantity(0);
                    holder.total_quantity.setText(String.valueOf(menuItemObject.getQuantity()));
                    holder.total_amount.setText("$ " + String.valueOf(menuItemObject.getTotalAmount()));
                    menuItemSelectedListener.onMenuItemSelected(menuItemObjectList.get(position), true);
                } else {
                    menuItemObject.setSelected(0);
                    holder.selectedIcon.setVisibility(View.INVISIBLE);
                    holder.totalLayout.setVisibility(View.GONE);
                    menuItemObject.setQuantity(0);
                    menuItemSelectedListener.onMenuItemSelected(menuItemObjectList.get(position), false);
                }

            }
        });

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

    private void updateQuantity(MenuItemObject menuItemObject, MenuHolder holder) {
        double totalCost = menuItemObject.getPrice() * menuItemObject.getQuantity();
        menuItemObject.setTotalAmount(totalCost);
        holder.total_amount.setText("$ " + String.valueOf(menuItemObject.getTotalAmount()));
        holder.total_quantity.setText(String.valueOf(menuItemObject.getQuantity()));
    }

    class MenuHolder extends RecyclerView.ViewHolder {

        LinearLayout menuItem, totalLayout;
        TextView name, desc, spice, price, total_quantity, total_amount;
        ImageView selectedIcon, quantity_minus, quantity_plus;

        public MenuHolder(View itemView) {
            super(itemView);
            menuItem = (LinearLayout) itemView.findViewById(R.id.menu_item);
            totalLayout = (LinearLayout) itemView.findViewById(R.id.totallayout);
            name = (TextView) itemView.findViewById(R.id.name);
            desc = (TextView) itemView.findViewById(R.id.description);
            spice = (TextView) itemView.findViewById(R.id.spice_level);
            price = (TextView) itemView.findViewById(R.id.price);
            total_quantity = (TextView) itemView.findViewById(R.id.total_quantity);
            total_amount = (TextView) itemView.findViewById(R.id.total_amount);
            quantity_minus = (ImageView) itemView.findViewById(R.id.quantity_minus);
            quantity_plus = (ImageView) itemView.findViewById(R.id.quantity_plus);
            selectedIcon = (ImageView) itemView.findViewById(R.id.selected_icon);
        }
    }

    public interface MenuItemSelectedListener {
        public void onMenuItemSelected(MenuItemObject menuItem, boolean add);
    }
}
