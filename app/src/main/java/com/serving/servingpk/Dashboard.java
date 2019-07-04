package com.serving.servingpk;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Base64;
import androidx.core.view.GravityCompat;

import androidx.appcompat.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.serving.servingpk.Adapters.GridViewAdapter;
import com.serving.servingpk.Fragments.EmergencyContactfragment;
import com.serving.servingpk.Fragments.InviteFriends;
import com.serving.servingpk.Fragments.MapsFragment;
import com.serving.servingpk.Fragments.PaymentFragment;
import com.serving.servingpk.Fragments.ProfileFragment;
import com.serving.servingpk.Fragments.Support;
import com.serving.servingpk.Loginpackage.LoginActivity;
import com.serving.servingpk.Model.ServicesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener
       {

    String url2 = "https://serving.pk/dashboardapi.php";

           private GridView mGridView;
           private GridViewAdapter mGridAdapter;
           private ArrayList<ServicesModel> mGridData;
ListView listView;
    //the hero list where we will store all the hero objects after parsing json
  List<ServicesModel> servicelist= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        navigationdrawer();

        //listView=findViewById(R.id.recyclerView);
        mGridView = (GridView) findViewById(R.id.gridView);

        //Initialize with empty data
        mGridData = new ArrayList<>();

        getJsonObject();




    }
    void getJsonObject()
    {

        showProgressBar(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            try {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(response);

                                //we have the array named hero inside the object
                                //so here we are getting that json array
                                JSONArray serviceArray = obj.getJSONArray("services");

                                //now looping through all the elements of the json array
                                for (int i = 0; i < serviceArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject serviceObject = serviceArray.getJSONObject(i);

                                  Bitmap decodedImage= decodeImage(serviceObject.getString("service_logo"));
                                    decodedImage=Bitmap.createScaledBitmap(decodedImage, 120, 120, false);
                                    //creating a hero object and giving them the values from json object
                                    ServicesModel hero = new ServicesModel(serviceObject.getString("mscategory_name"),serviceObject.getString("service_logo")
                                               ,serviceObject.getString("mscategory_id"),serviceObject.getString("display_order"));
                                    Log.d("check",""+hero.getMscategory_name());
                                    //adding the hero to servicelist
                                    mGridData.add(hero);
                                }

//

                                mGridAdapter = new GridViewAdapter(Dashboard.this, R.layout.service_listitems, mGridData);
                                showProgressBar(false);
                                mGridView.setAdapter(mGridAdapter);

                                mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                        //Get item at position
                                        ServicesModel item = (ServicesModel) parent.getItemAtPosition(position);
//
                                        Bundle bundle = new Bundle();
                                          bundle.putString("category_id", item.getMscategory_id());
                                        MapsFragment subcategory=new MapsFragment();
                                        subcategory.setArguments(bundle);
                                        FragmentTransaction transaction;
                                        transaction = getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.fragmentScreen,subcategory);
                                        transaction.addToBackStack(null);
                                        transaction.commit();


                                    }
                                });


                        } catch (JSONException e) {
                            e.printStackTrace();
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




Bitmap  decodeImage(String team)  {

    //encode image to base64 string
    ByteArrayOutputStream baos=new ByteArrayOutputStream();
    byte[] imageBytes = baos.toByteArray();
    imageBytes = Base64.decode(team, Base64.DEFAULT);
    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    return decodedImage;
}

    void navigationdrawer()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
       navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    //@Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment=null;
        FragmentManager fragmentManager = getSupportFragmentManager(); // For AppCompat use getSupportFragmentManager
        switch(id) {


            case R.id.payment:
                fragment = new PaymentFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentScreen, fragment)
                        .commit();
                break;
            case R.id.my_profile:
                //Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
                fragment = new ProfileFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentScreen, fragment)
                        .commit();
                break;
            case R.id.Emergency_contact:
                fragment = new EmergencyContactfragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentScreen, fragment)
                        .commit();
                break;
            case R.id.Support:
                fragment = new Support();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentScreen, fragment)
                        .commit();
                break;
            case R.id.invite_friends:
                fragment = new InviteFriends();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentScreen, fragment)
                        .commit();
                break;
            case R.id.logout:
                showPopup();
                break;

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
           private void showPopup() {
               ViewGroup viewGroup = findViewById(android.R.id.content);

               //then we will inflate the custom alert dialog xml that we created
               View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, viewGroup, false);


               //Now we need an AlertDialog.Builder object
               AlertDialog.Builder builder = new AlertDialog.Builder(this);
               Button b=dialogView.findViewById(R.id.button4);
               b.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       logout();
                   }
               });
               //setting the view of the builder to our custom view that we already inflated
               builder.setView(dialogView);

               //finally creating the alert dialog and displaying it
               AlertDialog alertDialog = builder.create();
               alertDialog.show();
           }
           private void logout() {
               startActivity(new Intent(this, LoginActivity.class));
               finish();
           }


}
