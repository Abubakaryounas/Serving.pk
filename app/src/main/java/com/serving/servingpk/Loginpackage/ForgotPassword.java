package com.serving.servingpk.Loginpackage;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.serving.servingpk.R;

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

public class ForgotPassword extends AppCompatActivity {
    EditText phoneNumber;
    Button resetPassword;
    String url2 = "https://serving.pk/uforgetpassword.php";

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    View view;

    URL url=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initViews();
        initListeners();

    }
    public void initViews()
    {
        phoneNumber=findViewById(R.id.passwordreset);
        resetPassword=findViewById(R.id.support_button);
    }
    void initListeners()
    {
    resetPassword.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
         //  Toast.makeText(ForgotPassword.this, "out of login", Toast.LENGTH_SHORT).show();
            checkLogin(view);
        }
    });
    }
    public void checkLogin(View arg0) {

        // Get text from email and passord field
        final String email = phoneNumber.getText().toString();
      //  Toast.makeText(this, "in login", Toast.LENGTH_SHORT).show();
        // Initialize  AsyncLogin() class with email and password
        new ForgotPassword.AsyncLogin().execute(email);

    }

    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(ForgotPassword.this);
        HttpURLConnection conn;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
         //  pdLoading.dismiss();

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
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("usr_phone", params[0]);
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
                Toast.makeText(ForgotPassword.this, ""+e1.getMessage(), Toast.LENGTH_SHORT).show();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
               // Toast.makeText(ForgotPassword.this, "checking connection", Toast.LENGTH_SHORT).show();
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    reader.close();
                    input.close();
                    conn.disconnect();
                    Log.d("check",""+result);
                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(ForgotPassword.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();;
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            Log.d("check2",""+result);
            Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG);
            if(result.contains("your password has been sent to your"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */

              //  Intent intent = new Intent(ForgotPassword.this,SignupActivity.class);
               // startActivity(intent);
              //  ForgotPassword.this.finish();

            }else if (result.contains("Failed to recover")){

                // If username and password does not match display a error message
                Toast.makeText(ForgotPassword.this, "Invalid email or password", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(ForgotPassword.this, ""+result, Toast.LENGTH_LONG).show();

            }
        }

    }
}
