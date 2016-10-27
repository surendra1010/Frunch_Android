package com.frunch.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.frunch.main.adapters.RestarantsAdapter;
import com.frunch.main.utils.MyToast;
import com.frunch.main.utils.RestarantObject;
import com.frunch.main.utils.Utils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView restarantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restarantList = (RecyclerView)findViewById(R.id.restarant_list);
        restarantList.setLayoutManager(new LinearLayoutManager(this));

        if(Utils.checkNetworkStatus(this)) {
            gettingRestarantsList();
        }
        else {
            MyToast.showToast(this,getString(R.string.network_error));
        }

    }

    public void gettingRestarantsList() {
        String tag_name = "RestarantList_API";

        final ProgressDialog progressDialog = ProgressDialog.show(this,"",getString(R.string.please_wait));

        JsonArrayRequest req = new JsonArrayRequest(getString(R.string.restarants_list), new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Success :",response.toString());
                List<RestarantObject> restarantObjectList = restarantParsing(response.toString());
                RestarantsAdapter restarantsAdapter = new RestarantsAdapter(MainActivity.this,restarantObjectList);
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
        Volley.newRequestQueue(this).add(req);

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
