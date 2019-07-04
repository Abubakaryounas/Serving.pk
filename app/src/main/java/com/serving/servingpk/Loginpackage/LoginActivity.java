package com.serving.servingpk.Loginpackage;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.serving.servingpk.BaseActivity;
import com.serving.servingpk.Dashboard;
import com.serving.servingpk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class LoginActivity extends BaseActivity {
    TextView forgotpassword, signup;
    EditText etEmail, etPassword, editName;
    Button btnSignIn, btnRegister;
    String url2 = "http://serving.pk/login.php";
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_GOOGLE = 9001;
    private static final int RC_SIGN_FACEBOOK = 9001;
    private GoogleSignInClient mGoogleSignInClient;


    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    URL url = null;
    View view;

    CallbackManager   mCallbackManager;
    LoginButton loginButton;
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();

        initViews();
        initObjects();

        initListeners();

    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
      //  updateUI(account);
    }

    public void initViews() {
        etEmail = findViewById(R.id.phoneNumber);

        view = this.view;
        etPassword = (EditText) findViewById(R.id.pin);
        forgotpassword = findViewById(R.id.forgotpassword);
        signup = findViewById(R.id.signuptext);
        btnSignIn = findViewById(R.id.signinButton);



    }

    public void initObjects() {
        ModifyNumber mnumber = new ModifyNumber();
        etEmail = mnumber.rearrange(etEmail);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }


    public void initListeners() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //checkLogin(view);
                getJsonObject();
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        findViewById(R.id.googleplusimage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        findViewById(R.id.facebookimage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton = findViewById(R.id.facebookinvisiblebutton);
                loginButton.performClick();
                loginButton.setReadPermissions(Arrays.asList("email", "public_profile", "user_friends"));
                mCallbackManager=CallbackManager.Factory.create();
                loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // Retrieving access token using the LoginResult
                        AccessToken accessToken = loginResult.getAccessToken();
                        Log.d("error","Successfull");
                    }
                    @Override
                    public void onCancel() {
                        Log.d("error","cancelled");
                    }
                    @Override
                    public void onError(FacebookException error) {
                        Log.d("error",error.toString());
                    }
                });

            }
        });
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_GOOGLE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_GOOGLE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

            mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Sign in successfull"+account.getDisplayName(), Toast.LENGTH_SHORT).show();
            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    // Triggers when LOGIN Button clicked
    public void checkLogin(View arg0) {

        // Get text from email and passord field
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        // Initialize  AsyncLogin() class with email and password
        new AsyncLogin().execute(email, password);

    }

    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(LoginActivity.this);
        HttpURLConnection conn;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL(url2);

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("usr_id", params[0])
                        .appendQueryParameter("usr_pwd", params[1]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    Log.d(TAG,"response ok");
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    reader.close();
                    input.close();
                    conn.disconnect();
                    Log.d("check", "" + result);
                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            if (result.contains("user_id")) {


                Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                startActivity(intent);
                LoginActivity.this.finish();

            } else if (result.contains("This User ID or Password does not match")) {

                // If username and password does not match display a error message
                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(LoginActivity.this, "OOPs! Something went wrong. Connection Problem." + result, Toast.LENGTH_LONG).show();

            }
        }

    }

    void getJsonObject()
    {
        url2 = String.format("https://serving.pk/login.php?usr_id=%1$s&usr_pwd=%2$s",
                etEmail.getText().toString(),etPassword.getText().toString());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            if (response.contains("user_id"))
                            {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(response);

                                //we have the array named hero inside the object
                                //so here we are getting that json array
                                JSONArray serviceArray = obj.getJSONArray("user");

                                //now looping through all the elements of the json array
                                for (int i = 0; i < serviceArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject serviceObject = serviceArray.getJSONObject(i);

                                    Log.d(TAG,serviceObject.toString());
                                    mFirebaseAuth.signInWithEmailAndPassword( etEmail.getText().toString(),etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            startActivity(new Intent(getApplicationContext(),Dashboard.class));
                                        }
                                    });

                            }

//                                //creating a hero object and giving them the values from json object
//                                SubserviceModel hero = new SubserviceModel(serviceObject.getString("sscategory_id"),serviceObject.getString("sscategory_name")
//                                        ,serviceObject.getString("sservice_logo"));
//                                Log.d("check",""+hero.getSscategory_name());
//                                //adding the hero to servicelist

                            }
                            else if (response.contains("message"))
                            {
                                Toast.makeText(LoginActivity.this, "invalid phone or password", Toast.LENGTH_SHORT).show();
                            }
                            //  creating custom adapter object
//                            ListViewAdapters adapter = new ListViewAdapters(servicelist, getApplicationContext());
////
////                            //adding the adapter to listview
//
//                            listView.setAdapter(adapter);





                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("TAG",""+e.getMessage());
                            Toast.makeText(LoginActivity.this, ""+"invalid user credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }





}