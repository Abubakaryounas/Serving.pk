package com.serving.servingpk.Loginpackage;

import android.content.Intent;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.serving.servingpk.Dashboard;
import com.serving.servingpk.MainActivity;
import com.serving.servingpk.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
        EditText fname,lname,emailtext,passwordtext,phonenum;
        TextView countrycode;
    FirebaseAuth auth;
     String firstname,lastname,email,password,phone;
    DatabaseReference reference;
        final String url_register="https://serving.pk/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fname=findViewById(R.id.firstname);
        lname=findViewById(R.id.lastName);
        emailtext=findViewById(R.id.email);
        passwordtext=findViewById(R.id.password);
        phonenum=findViewById(R.id.mobilenumregister);
        countrycode=findViewById(R.id.countrycode);
        findViewById(R.id.signupbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }
    private void registerUser() {
         firstname = fname.getText().toString().trim();
        lastname= lname.getText().toString().trim();
         email = emailtext.getText().toString().trim();
         password = passwordtext.getText().toString().trim();

         phone=countrycode.getText().toString().trim()+phonenum.getText().toString().trim();
        Toast.makeText(this, ""+phone, Toast.LENGTH_SHORT).show();
        //first we will do the validations
//
//        if (TextUtils.isEmpty(username)) {
//            editTextUsername.setError("Please enter username");
//            editTextUsername.requestFocus();
//            return;
//        }
//
//        if (TextUtils.isEmpty(email)) {
//            editTextEmail.setError("Please enter your email");
//            editTextEmail.requestFocus();
//            return;
//        }
//
//        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            editTextEmail.setError("Enter a valid email");
//            editTextEmail.requestFocus();
//            return;
//        }
//
//        if (TextUtils.isEmpty(password)) {
//            editTextPassword.setError("Enter a password");
//            editTextPassword.requestFocus();
//            return;
//        }

        //if it passes all the validations

        class RegisterUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("first_name", firstname);
                params.put("last_name",lastname);
                params.put("email", email);
                params.put("password", password);
                params.put("phone",phone);

                //returing the response
                return requestHandler.sendPostRequest(url_register, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
               // progressBar = (ProgressBar) findViewById(R.id.progressBar);
//                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                //progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    Toast.makeText(SignupActivity.this, ""+s, Toast.LENGTH_LONG).show();

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
//                        User user = new User(
//                                userJson.getInt("id"),
//                                userJson.getString("username"),
//                                userJson.getString("email"),
//                                userJson.getString("gender")
//                        );

                        //storing the user in shared preferences
                     //   SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        register(firstname+lastname,email,password);


                       // startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }
    private void register(final String username, String email, String password)
    {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser firebaseUser=auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid=firebaseUser.getUid();

                            reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            HashMap<String, String> hashMap=new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username",username);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Intent intent=new Intent(SignupActivity.this, Dashboard.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(SignupActivity.this, "You cant register with this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
