package com.frunch.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.frunch.main.utils.MyToast;
import com.frunch.main.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seerasu1 on 23/10/16.
 */
public class LoginActivity extends Activity {

    EditText email, password;
    Button login, new_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login);
        new_user = (Button) findViewById(R.id.new_user);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                if (Utils.checkNetworkStatus(LoginActivity.this)) {
                    if (Utils.isValid(emailText) && Utils.isValid(passwordText)) {
                        onLoginClicked(emailText, passwordText);
                    } else {
                        MyToast.showToast(LoginActivity.this, getString(R.string.login_error));
                    }
                } else {
                    MyToast.showToast(LoginActivity.this, getString(R.string.network_error));
                }
            }
        });

        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }

    private void onLoginClicked(final String email, final String password) {
        String tag_name = "Login_API";

        final ProgressDialog progressDialog = ProgressDialog.show(this, "", getString(R.string.please_wait));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://frunch-dev.mybluemix.net/api/users/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response :", response);
                progressDialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Response :", error.toString());
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int statusCode = response.statusCode;
                Log.e("Response code :", String.valueOf(statusCode));
                return super.parseNetworkResponse(response);
            }
        };

        stringRequest.setTag(tag_name);
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
