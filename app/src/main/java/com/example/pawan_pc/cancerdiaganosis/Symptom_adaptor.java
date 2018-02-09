package com.example.pawan_pc.cancerdiaganosis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pawan-PC on 20-10-2017.
 */
public class Symptom_adaptor extends BaseAdapter {
    private LayoutInflater layoutInflater;
    ArrayList<HashMap<String,String>> list;
    Context mcontext;
    public Symptom_adaptor(Context context,ArrayList<HashMap<String,String>> list) {
        this.list=list;
        this.mcontext=context;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view=layoutInflater.inflate(R.layout.symptoms,null);
            TextView symp=(TextView)view.findViewById(R.id.symptom_name);
            final TextView level=(TextView)view.findViewById(R.id.level);
            TextView  pr=(TextView)view.findViewById(R.id.priode);
            Button cancel=(Button)view.findViewById(R.id.cancel);
            cancel.setTag(i);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer pos= (Integer) view.getTag();
                    list.remove(pos.intValue());
                    notifyDataSetChanged();
                }
            });
            HashMap<String,String> data=list.get(i);
            symp.setText(data.get("symptom"));
            level.setText(data.get("level"));
            pr.setText(data.get("priode"));
            Animation animation= AnimationUtils.loadAnimation(mcontext,R.anim.slide);
            view.startAnimation(animation);
        }
        return view;
    }
}
