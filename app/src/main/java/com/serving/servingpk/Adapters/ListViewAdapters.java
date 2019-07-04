package com.serving.servingpk.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.serving.servingpk.Model.SubserviceModel;
import com.serving.servingpk.Model.SubserviceModel;
import com.serving.servingpk.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ListViewAdapters extends ArrayAdapter<SubserviceModel> {

    //the hero list that will be displayed
    private List<SubserviceModel> heroList;

    //the context object
    private Context mCtx;

    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public ListViewAdapters(List<SubserviceModel> heroList, Context mCtx) {
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
        View listViewItem = inflater.inflate(R.layout.subcategory_list_items, parent, false);

        //getting text views
        TextView textViewName = listViewItem.findViewById(R.id.subcategoryName);
        ImageView textViewImageUrl = listViewItem.findViewById(R.id.subCategoryImageUrl);

        //Getting the hero for the specified position
        SubserviceModel serviceobject = heroList.get(position);
        //setting hero values to textviews
        textViewName.setText(serviceobject.getSscategory_name());
        Bitmap decodedImage= decodeImage(serviceobject.getSservice_logo());
        decodedImage=Bitmap.createScaledBitmap(decodedImage, 100, 100, false);
        textViewImageUrl.setImageBitmap(decodedImage);

        //returning the listitem
        return listViewItem;
    }
    Bitmap decodeImage(String team)  {

        //encode image to base64 string
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(team, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return decodedImage;
    }
}