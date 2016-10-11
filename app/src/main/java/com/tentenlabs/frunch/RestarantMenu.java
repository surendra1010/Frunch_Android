package com.tentenlabs.frunch;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.astuetz.PagerSlidingTabStrip;
import com.tentenlabs.frunch.adapters.RestaurantMenuAdapter;
import com.tentenlabs.frunch.utils.ItemMenuCategory;
import com.tentenlabs.frunch.utils.MenuItemObject;
import com.tentenlabs.frunch.utils.MyToast;
import com.tentenlabs.frunch.utils.RestarantObject;
import com.tentenlabs.frunch.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seerasu1 on 09/10/16.
 */
public class RestarantMenu extends AppCompatActivity {

    RestarantObject restarantObject;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_menu);

        restarantObject = (RestarantObject) getIntent().getSerializableExtra("restarant");
        getSupportActionBar().setTitle(restarantObject.getName());

        pager = (ViewPager) findViewById(R.id.pager);

        if (Utils.checkNetworkStatus(this)) {
            gettingRestarantsMenu();
        } else {
            MyToast.showToast(this, getString(R.string.network_error));
        }

    }

    public void gettingRestarantsMenu() {
        String tag_name = "RestarantList_API";

        final ProgressDialog progressDialog = ProgressDialog.show(this, "", getString(R.string.please_wait));

        StringRequest stringRequest = new StringRequest(getString(R.string.restarants_list) + "/" + restarantObject.getId() + "/menuDetails", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Success :", response);
                List<ItemMenuCategory> ItemMenuCategoryList = restaurantMenuParsing(response);
                if (ItemMenuCategoryList.size() > 0) {
                    pager.setAdapter(new RestaurantMenuAdapter(getSupportFragmentManager(), ItemMenuCategoryList));
                    PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
                    tabsStrip.setViewPager(pager);
                } else {
                    TextView no_items_text = (TextView) findViewById(R.id.no_items_text);
                    no_items_text.setVisibility(View.VISIBLE);
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error :", error.getMessage());
                progressDialog.dismiss();
            }
        });

        stringRequest.setTag(tag_name);
        Volley.newRequestQueue(this).add(stringRequest);

    }


    private List<ItemMenuCategory> restaurantMenuParsing(String response) {
        List<ItemMenuCategory> ItemMenuCategoryList = new ArrayList<ItemMenuCategory>();
        try {
            JSONObject restObject = new JSONObject(response);
            JSONArray menuArray = new JSONArray(restObject.getString("menus"));
            Log.e("menuArray length :", String.valueOf(menuArray.length()));

            for (int i = 0; i < menuArray.length(); i++) {
                JSONArray itemsCategoryArray = new JSONArray(menuArray.getJSONObject(i).getString("itemsCategories"));
                Log.e("itemsCategory length :", String.valueOf(itemsCategoryArray.length()));

                for (int j = 0; j < itemsCategoryArray.length(); j++) {
                    JSONObject itemMenuCategoryObject = itemsCategoryArray.getJSONObject(j);
                    ItemMenuCategory itemMenuCategory = new ItemMenuCategory();
                    itemMenuCategory.setName(itemMenuCategoryObject.getString("name"));
                    itemMenuCategory.setDescr(itemMenuCategoryObject.getString("descr"));
                    itemMenuCategory.setId(itemMenuCategoryObject.getString("id"));

                    List<MenuItemObject> menuItemObjectList = new ArrayList<MenuItemObject>();

                    JSONArray itemsArray = new JSONArray(itemsCategoryArray.getJSONObject(j).getString("items"));
                    Log.e("items length :", String.valueOf(itemsArray.length()));

                    for (int k = 0; k < itemsArray.length(); k++) {
                        JSONObject itemObject = itemsArray.getJSONObject(k);
                        MenuItemObject menuItem = new MenuItemObject();
                        menuItem.setName(itemObject.getString("name"));
                        menuItem.setDescr(itemObject.getString("descr"));
                        menuItem.setId(itemObject.getString("id"));
                        menuItem.setItemCategoryId(itemObject.getString("itemCategoryId"));
                        menuItem.setPrice(itemObject.getDouble("price"));
                        menuItem.setSpiceLevel(itemObject.getString("spiceLevel"));
                        menuItemObjectList.add(menuItem);
                    }

                    itemMenuCategory.setMenuItemObjectList(menuItemObjectList);
                    ItemMenuCategoryList.add(itemMenuCategory);
                }

            }

        } catch (Exception ex) {

        }

        return ItemMenuCategoryList;
    }


}
