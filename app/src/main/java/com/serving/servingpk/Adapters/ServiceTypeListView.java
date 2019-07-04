package com.serving.servingpk.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.serving.servingpk.Model.ServiceType;
import com.serving.servingpk.R;

import java.util.List;

public class ServiceTypeListView extends ArrayAdapter<ServiceType> {

    //the hero list that will be displayed
    private List<ServiceType> heroList;

    //the context object
    private Context mCtx;

    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public ServiceTypeListView(List<ServiceType> heroList, Context mCtx) {
        super(mCtx, R.layout.subcategory_list_items, heroList);
        this.heroList = heroList;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.service_type_items, parent, false);

        //getting text views
        TextView textViewName = listViewItem.findViewById(R.id.name);


        //Getting the hero for the specified position
        ServiceType serviceobject = heroList.get(position);
        //setting hero values to textviews
        textViewName.setText(serviceobject.getStype_name());

        //returning the listitem
        return listViewItem;
    }

}