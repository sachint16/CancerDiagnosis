package com.example.pawan_pc.cancerdiaganosis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pawan-PC on 18-10-2017.
 */
public class TipsAdaptor extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<String> dataset;
    Context mcontext;

    public TipsAdaptor(Context context, ArrayList<String> list) {
        this.layoutInflater = layoutInflater;
        this.dataset=list;
        this.mcontext=context;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataset.size();
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
            view=layoutInflater.inflate(R.layout.tips_layout,null);
            TextView text=(TextView)view.findViewById(R.id.tipsText);
            ArrayList<String> data=dataset;
            text.setText(data.get(i));

        }
        Animation animation= AnimationUtils.loadAnimation(mcontext,R.anim.slide);
        view.startAnimation(animation);
        return view;
    }
}
