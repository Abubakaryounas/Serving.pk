package com.serving.servingpk.Adapters;

import android.app.Activity;
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

import com.serving.servingpk.Model.ServicesModel;
import com.serving.servingpk.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<ServicesModel> {

    //private final ColorMatrixColorFilter grayscaleFilter;
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<ServicesModel> mGridData = new ArrayList<ServicesModel>();

    public GridViewAdapter(Context mContext, int layoutResourceId, ArrayList<ServicesModel> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
    }


    /**
     * Updates grid data and refresh grid items.
     *
     * @param mGridData
     */
    public void setGridData(ArrayList<ServicesModel> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row =  inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) row.findViewById(R.id.textViewName);
            holder.imageView = (ImageView) row.findViewById(R.id.textViewImageUrl);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ServicesModel item = mGridData.get(position);
        holder.titleTextView.setTextSize(15);
        holder.titleTextView.setText(item.getMscategory_name());
        Bitmap decodedImage= decodeImage(item.getService_logo());
        decodedImage=Bitmap.createScaledBitmap(decodedImage, 100, 100, false);
    holder.imageView.setImageBitmap(decodedImage);
        //Picasso.with(mContext).load(item.getService_logo()).into(holder.imageView);

        return row;
    }

    Bitmap  decodeImage(String team)  {

        //encode image to base64 string
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(team, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return decodedImage;
    }

    static class ViewHolder {
        TextView titleTextView;
        ImageView imageView;
    }
}