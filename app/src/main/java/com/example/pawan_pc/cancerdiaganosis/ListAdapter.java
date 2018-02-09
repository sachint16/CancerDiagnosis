package com.example.pawan_pc.cancerdiaganosis;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.test.suitebuilder.TestMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Pawan-PC on 12-10-2017.
 */
public class ListAdapter extends BaseAdapter {
    Context activity;
    private ArrayList<Datamodel> list;
    private LayoutInflater layoutInflater=null;
    private ProgressDialog progressDialog;
    private int layout;

    public ListAdapter(Context activity,int res, ArrayList<Datamodel> list) {
        this.activity = activity;
        this.list = list;
        layoutInflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout=res;

    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public void sortByPriceAsc() {
        Comparator<Datamodel> comparator = new Comparator<Datamodel>() {

            @Override
            public int compare(Datamodel object1, Datamodel object2) {
                return Double.compare(Double.parseDouble(object1.getDistance()),Double.parseDouble(object2.getDistance()));
            }
        };
        Collections.sort(list, comparator);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Datamodel datamodel=list.get(i);
        View v=view;
        if(v==null) {
            //progressDialog.dismiss();
            v = layoutInflater.inflate(R.layout.hospitals_list, null);
            TextView contact = (TextView) v.findViewById(R.id.hosp_name);
            TextView dis = (TextView) v.findViewById(R.id.hosp_dis);
            TextView adrs = (TextView) v.findViewById(R.id.hos_address);
            contact.setText(datamodel.getName());
            dis.setText(datamodel.getDistance() + " kms");
            adrs.setText(datamodel.getAddress());
        }

        return v;
    }
}
