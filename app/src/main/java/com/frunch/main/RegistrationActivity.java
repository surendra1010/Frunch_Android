package com.frunch.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
 * Created by seerasu1 on 24/10/16.
 */
public class RegistrationActivity extends AppCompatActivity {

    EditText firstName, lastName, email, mobile, password, confirmPassword;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);

        getSupportActionBar().setTitle(R.string.registration);

        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.email);
        mobile = (EditText) findViewById(R.id.mobile);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameText = firstName.getText().toString();
                String lastNameText = lastName.getText().toString();
                String emailText = email.getText().toString();
                String mobileText = mobile.getText().toString();
                String passwordText = password.getText().toString();
                String confirmPasswordText = confirmPassword.getText().toString();

                if (Utils.isValid(firstNameText) &&
                        Utils.isValid(lastNameText) &&
                        Utils.isValid(emailText) &&
                        Utils.isValid(mobileText) &&
                        Utils.isValid(passwordText) && Utils.isValid(confirmPasswordText)) {

                    if(Utils.passwordCheck(passwordText,confirmPasswordText)) {
                          onRegistration(firstNameText,lastNameText,emailText,mobileText,passwordText);
                    } else {
                        MyToast.showToast(RegistrationActivity.this,getString(R.string.passwords_diff));
                    }

                } else {
                    MyToast.showToast(RegistrationActivity.this,getString(R.string.all_fields_error));
                }
            }
        });

    }

    public void onRegistration(final String firstName, final String lastName, final String email, final String mobile, final String password) {
        String tag_name = "Registration_API";

        final ProgressDialog progressDialog = ProgressDialog.show(this, "", getString(R.string.please_wait));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://frunch-dev.mybluemix.net/api/users", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response :",response);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Response :",error.toString());
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("firstname",firstName);
                params.put("lastname",lastName);
                params.put("email",email);
                params.put("phone",mobile);
                params.put("password",password);
                params.put("workAddressId","5");
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int statusCode = response.statusCode;
                Log.e("Response code :",String.valueOf(statusCode));
                return super.parseNetworkResponse(response);
            }
        };

        stringRequest.setTag(tag_name);
        Volley.newRequestQueue(this).add(stringRequest);
    }


}
