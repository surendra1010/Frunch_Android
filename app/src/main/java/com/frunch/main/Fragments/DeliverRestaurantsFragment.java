package com.frunch.main.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.frunch.main.R;
import com.frunch.main.adapters.DeliverRestaurantsAdapter;
import com.frunch.main.utils.MyToast;
import com.frunch.main.utils.RestarantObject;
import com.frunch.main.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seerasu1 on 18/10/16.
 */
public class DeliverRestaurantsFragment extends Fragment {

    private static final String[] requiredPermissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    int permissionId = 111;
    double finalLatitude = 0.0;
    double finalLongitude = 0.0;

    RecyclerView restarantList;
    ProgressDialog locationProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        restarantList = (RecyclerView) view.findViewById(R.id.restarant_list);
        restarantList.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (canAccessLocation()) {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationProgressDialog = ProgressDialog.show(getActivity(), "", getString(R.string.getting_location));
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
            } else {
                askLocationPermission();
            }
        } else {
            requestPermissions(requiredPermissions, permissionId);
        }
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Log.e("location :", String.valueOf(latitude) + " " + String.valueOf(longitude));
            if (finalLatitude == 0.0 && finalLongitude == 0.0) {
                finalLongitude = longitude;
                finalLatitude = latitude;
                if (Utils.checkNetworkStatus(getActivity())) {
                    locationProgressDialog.dismiss();
                    getNearByRestaurants(latitude, longitude);
                } else {
                    MyToast.showToast(getActivity(), getString(R.string.network_error));
                    locationProgressDialog.dismiss();
                }

            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void getNearByRestaurants(final double latitude, final double longitude) {
        String tag_name = "delivery_RestarantList_API";
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                String.valueOf(latitude) + "," + String.valueOf(longitude) +
                "&radius=30000&types=restaurant&key=AIzaSyBDS7sHEQZJioZu2VOxKxKthV2jCTt1Er4";

        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", getString(R.string.please_wait));

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response :", response.toString());
                List<RestarantObject> restarantObjectList = restaurantResponseParsing(response.toString());
                DeliverRestaurantsAdapter deliverRestaurantsAdapter = new DeliverRestaurantsAdapter(getActivity(), restarantObjectList);
                restarantList.setAdapter(deliverRestaurantsAdapter);

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });
        stringRequest.setTag(tag_name);
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private List<RestarantObject> restaurantResponseParsing(String response) {
        List<RestarantObject> restarantObjectList = new ArrayList<RestarantObject>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray resultsArray = new JSONArray(jsonObject.getString("results"));
            Log.e("resultsArray :", String.valueOf(resultsArray.length()));

            for (int i = 0; i < resultsArray.length(); i++) {
                RestarantObject restarantObject = new RestarantObject();
                Log.e("name", resultsArray.getJSONObject(i).getString("name"));
                Log.e("id", resultsArray.getJSONObject(i).getString("id"));
                restarantObject.setName(resultsArray.getJSONObject(i).getString("name"));
                restarantObject.setId(resultsArray.getJSONObject(i).getString("id"));
                JSONArray typesArray = resultsArray.getJSONObject(i).getJSONArray("types");
                String description = "";
                for (int j = 0; j < typesArray.length(); j++) {
                    Log.e("type name", typesArray.getString(j));
                    description = description + typesArray.getString(j) + " ";
                }
                restarantObject.setDescription(description);
                restarantObjectList.add(restarantObject);
            }

        } catch (Exception ex) {

        }
        return restarantObjectList;
    }

    private boolean canAccessLocation() {
        return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    private boolean hasPermission(String permission1, String permission2) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getActivity(), permission1) &&
                PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getActivity(), permission2));
    }

    private void askLocationPermission() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setMessage(getResources().getString(R.string.location_permission));
            alertDialog.setPositiveButton(getResources().getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Intent gpsOptionsIntent = new Intent(
                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    getActivity().startActivity(gpsOptionsIntent);
                }
            });
            alertDialog.setNegativeButton(getResources().getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog.show();
    }


}
