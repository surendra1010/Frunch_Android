package com.frunch.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.frunch.main.model.User;
import com.frunch.main.repo.UserRepository;
import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.VoidCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @Bind(R.id.input_firstname)
    EditText _firstnameText;
    @Bind(R.id.input_middlename)
    EditText _middlenameText;
    @Bind(R.id.input_lastname)
    EditText _lastnameText;
    @Bind(R.id.input_phone)
    EditText _phoneText;
    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_signup)
    Button _signupButton;
    @Bind(R.id.link_login)
    TextView _loginLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String firstname = _firstnameText.getText().toString();
        String middlename = _middlenameText.getText().toString();
        String lastname = _lastnameText.getText().toString();
        String phone = _phoneText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.
        final RestAdapter restAdapter = new RestAdapter(getApplicationContext(), "http://frunch.mybluemix.net/api");
        final UserRepository userRepo = restAdapter.createRepository(UserRepository.class);
        User user = userRepo.createUser(email, password, ImmutableMap.of("firstname",firstname,"lastname",lastname,"middlename",middlename,"phone",phone));
        if(user == null){
            progressDialog.dismiss();
            onSignupFailed();
        }else{
            user.save(new VoidCallback() {
                @Override
                public void onSuccess() {
                    progressDialog.dismiss();
                    onSignupSuccess();
                }

                @Override
                public void onError(Throwable t) {
                    Log.d(TAG, "Error :: "+t.fillInStackTrace());
                    progressDialog.dismiss();
                    onSignupFailed();
                }
            });
        }

       /* new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);*/
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed failed", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String firstname = _firstnameText.getText().toString();
        String lastname = _lastnameText.getText().toString();
        String phone = _phoneText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (firstname.isEmpty()) {
            _firstnameText.setError("First name should not be empty.");
            valid = false;
        } else {
            _firstnameText.setError(null);
        }
        if (lastname.isEmpty()) {
            _lastnameText.setError("Last name should not be empty.");
            valid = false;
        } else {
            _lastnameText.setError(null);
        }
        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            _phoneText.setError("Enter a valid phone number");
            valid = false;
        } else {
            _phoneText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 16) {
            _passwordText.setError("between 4 and 16 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

}