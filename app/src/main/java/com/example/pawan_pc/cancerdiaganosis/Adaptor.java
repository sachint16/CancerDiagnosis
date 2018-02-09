package com.example.pawan_pc.cancerdiaganosis;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Pawan-PC on 13-10-2017.
 */
public class Adaptor extends ArrayAdapter<Datamodel> {


    private ArrayList<Datamodel> dataSet;
    ProgressDialog p;
    private int layout;

    public Adaptor(Context context, int resource, Datamodel[] objects) {
        super(context, resource, objects);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Datamodel getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
    private int lastPosition=-1;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Datamodel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        //p.dismiss();

        final View result;

       if(layout==R.layout.list_item) {

           if (convertView == null) {

               viewHolder = new ViewHolder();
               LayoutInflater inflater = LayoutInflater.from(getContext());
               convertView = inflater.inflate(layout, parent, false);
               viewHolder.txtName = (TextView) convertView.findViewById(R.id.number);
               viewHolder.txtType = (TextView) convertView.findViewById(R.id.distance);
               viewHolder.name = (TextView) convertView.findViewById(R.id.name_hosp);

               result = convertView;

               convertView.setTag(viewHolder);
           } else {
               viewHolder = (ViewHolder) convertView.getTag();
               result = convertView;
           }

           Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.slide : R.anim.slide);
           result.startAnimation(animation);
           lastPosition = position;

           viewHolder.txtName.setText(dataModel.getContact());
           viewHolder.txtType.setText(dataModel.getDistance() + " km");
           viewHolder.name.setText(dataModel.getName());
           // Return the completed view to render on screen
       }
        else if(layout==R.layout.hospitals_list){
           if (convertView == null) {
               //Toast.makeText(mContext, "hl", Toast.LENGTH_SHORT).show();

               viewHolder = new ViewHolder();
               LayoutInflater inflater = LayoutInflater.from(getContext());
               convertView = inflater.inflate(layout, parent, false);
               viewHolder.txtName = (TextView) convertView.findViewById(R.id.hosp_name);
               viewHolder.txtType = (TextView) convertView.findViewById(R.id.hosp_dis);
               viewHolder.name = (TextView) convertView.findViewById(R.id.hos_address);

               result = convertView;

               convertView.setTag(viewHolder);
           } else {
               viewHolder = (ViewHolder) convertView.getTag();
               result = convertView;
           }

           Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.slide : R.anim.slide);
           result.startAnimation(animation);
           lastPosition = position;

           viewHolder.txtName.setText(dataModel.getName());
           viewHolder.txtType.setText(dataModel.getDistance() + " km");
           viewHolder.name.setText(dataModel.getAddress());

       }
           return convertView;

    }

    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView name;
    }

    public Adaptor(ArrayList<Datamodel> data,int resource, Context context) {
        super(context,resource, data);
        this.dataSet = data;
        this.mContext=context;
        this.layout=resource;

    }

}
