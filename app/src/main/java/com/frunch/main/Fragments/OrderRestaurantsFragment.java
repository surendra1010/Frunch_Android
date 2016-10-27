package com.frunch.main.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.frunch.main.R;
import com.frunch.main.adapters.RestarantsAdapter;
import com.frunch.main.utils.MyToast;
import com.frunch.main.utils.RestarantObject;
import com.frunch.main.utils.Utils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seerasu1 on 18/10/16.
 */
public class OrderRestaurantsFragment extends Fragment {

    RecyclerView restarantList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main,container,false);

        restarantList = (RecyclerView)view.findViewById(R.id.restarant_list);
        restarantList.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(Utils.checkNetworkStatus(getActivity())) {
            gettingRestarantsList();
        }
        else {
            MyToast.showToast(getActivity(),getString(R.string.network_error));
        }
        return view;
    }

    public void gettingRestarantsList() {
        String tag_name = "RestarantList_API";

        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),"",getString(R.string.please_wait));

        JsonArrayRequest req = new JsonArrayRequest(getString(R.string.restarants_list), new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Success :",response.toString());
                List<RestarantObject> restarantObjectList = restarantParsing(response.toString());
                RestarantsAdapter restarantsAdapter = new RestarantsAdapter(getActivity(),restarantObjectList);
                restarantList.setAdapter(restarantsAdapter);
                progressDialog.dismiss();
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error :",error.getMessage());
                progressDialog.dismiss();
            }
        });

        req.setTag(tag_name);
        Volley.newRequestQueue(getActivity()).add(req);

    }


    private List<RestarantObject> restarantParsing(String response) {
        List<RestarantObject> restarantObjectList = new ArrayList<RestarantObject>();
        try {
            JSONArray restarantArray = new JSONArray(response);
            for(int i=0;i<restarantArray.length();i++) {
                RestarantObject restarantObject = new RestarantObject();
                restarantObject.setDescription(restarantArray.getJSONObject(i).getString("description"));
                Log.e("name :",restarantArray.getJSONObject(i).getString("name"));
                restarantObject.setName(restarantArray.getJSONObject(i).getString("name"));
                restarantObject.setPhone(restarantArray.getJSONObject(i).getString("phone"));
                restarantObject.setId(restarantArray.getJSONObject(i).getString("id"));
                restarantObject.setCuisineId(restarantArray.getJSONObject(i).getString("CuisineId"));
                restarantObjectList.add(restarantObject);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return restarantObjectList;
    }
}
