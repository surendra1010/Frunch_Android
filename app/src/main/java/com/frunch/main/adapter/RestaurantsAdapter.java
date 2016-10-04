package com.frunch.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.frunch.main.R;
import com.frunch.main.model.Restaurant;

/**
 * Created by MAHITH on 9/29/2016.
 */
public class RestaurantsAdapter extends ArrayAdapter<Restaurant> {
    private final Context context;
    private final Restaurant[] restaurants;
    public RestaurantsAdapter(Context context, Restaurant[] restaurants){
        super(context, R.layout.restaurants_list);
        this.context = context;
        this.restaurants = restaurants;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.restaurants_list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        textView.setText(restaurants[position].getName());

        // Change icon based on name
       // Restaurant restaurant = restaurants[position];

       /* if (s.equals("WindowsMobile")) {
            imageView.setImageResource(R.drawable.windowsmobile_logo);
        } else if (s.equals("iOS")) {
            imageView.setImageResource(R.drawable.ios_logo);
        } else if (s.equals("Blackberry")) {
            imageView.setImageResource(R.drawable.blackberry_logo);
        } else {
            imageView.setImageResource(R.drawable.android_logo);
        }*/

        return rowView;
    }
}
