package com.serving.servingpk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.serving.servingpk.Adapters.ListViewAdapters;
import com.serving.servingpk.Fragments.Servicetypefragment;
import com.serving.servingpk.Model.SubserviceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SubCategory extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    String url2 = "https://serving.pk/subcategory.php";
    ListView listView;
    List<SubserviceModel> servicelist= new ArrayList<>();
    String message;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.activity_sub_category, container, false);
        Bundle bundle = this.getArguments();
        message = bundle.getString("category_id");
        listView=rootView.findViewById(R.id.subCategory_listview);
        getJsonObject();
       return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    void getJsonObject()
    {
        url2 = String.format("https://serving.pk/subcategory.php?mscategory_id=%1$s",
                message);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray serviceArray = obj.getJSONArray("SubCategoryServices");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < serviceArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject serviceObject = serviceArray.getJSONObject(i);


                                //creating a hero object and giving them the values from json object
                                SubserviceModel hero = new SubserviceModel(serviceObject.getString("sscategory_id"),serviceObject.getString("sscategory_name")
                                        ,serviceObject.getString("sservice_logo"));
                                Log.d("check",""+hero.getSscategory_name());
                                //adding the hero to servicelist
                                servicelist.add(hero);
                            }
                           //  creating custom adapter object
                            ListViewAdapters adapter = new ListViewAdapters(servicelist, getContext());
//
//                            //adding the adapter to listview

                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        SubserviceModel item = (SubserviceModel) parent.getItemAtPosition(position);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("category_id",item.getSscategory_id());
                                        Servicetypefragment serviceType=new Servicetypefragment();
                                        serviceType.setArguments(bundle);
                                        FragmentTransaction transaction;
                                        transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.fragmentScreen,serviceType);
                                        transaction.addToBackStack(null);
                                        transaction.commit();
                                    }
                                });



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("TAG",""+e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
