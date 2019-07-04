package com.serving.servingpk.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.serving.servingpk.Adapters.ServiceTypeListView;
import com.serving.servingpk.Model.ServiceType;
import com.serving.servingpk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Servicetypefragment extends Fragment {

    String url2="https://serving.pk/servicetype.php";
    List<ServiceType> servicelist= new ArrayList<>();
    ListView listView;
    String message;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_servicetypefragment,container,false);
        Bundle bundle = this.getArguments();
        message = bundle.getString("category_id");
        listView=rootView.findViewById(R.id.servicetypeListView);
       getJsonObject();
        return rootView;
    }
    void getJsonObject()
    {
        url2 = String.format("https://serving.pk/servicetype.php?sscategory_id=%1$s",
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
                            JSONArray serviceArray = obj.getJSONArray("ServiceType");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < serviceArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject serviceObject = serviceArray.getJSONObject(i);
                                ServiceType hero = new ServiceType(serviceObject.getString("stype_id"),serviceObject.getString("stype_name")
                                ,serviceObject.getString("display_order"));
                                servicelist.add(hero);
                            }

                            ServiceTypeListView adapter = new ServiceTypeListView(servicelist, getContext());

                            listView.setAdapter(adapter);


                                }



                         catch (JSONException e) {
                            e.printStackTrace();
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
    public class getJsonResponse extends AsyncTask<String,Void,Void>
    {

        @Override
        protected Void doInBackground(String... temp) {
            String tempurl=temp[0];
            tempurl = String.format("https://serving.pk/servicetype.php?sscategory_id=%1$s",
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
                                JSONArray serviceArray = obj.getJSONArray("ServiceType");

                                //now looping through all the elements of the json array
                                for (int i = 0; i < serviceArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject serviceObject = serviceArray.getJSONObject(i);
                                    ServiceType hero = new ServiceType(serviceObject.getString("stype_id"),serviceObject.getString("stype_name")
                                            ,serviceObject.getString("display_order"));
                                    servicelist.add(hero);
                                }

                                ServiceTypeListView adapter = new ServiceTypeListView(servicelist, getContext());
//
//                            //adding the adapter to listview

                                listView.setAdapter(adapter);


                            }



                            catch (JSONException e) {
                                e.printStackTrace();
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

            return null;
        }

    }
}