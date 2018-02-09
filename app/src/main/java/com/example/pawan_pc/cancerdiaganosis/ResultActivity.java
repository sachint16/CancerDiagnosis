package com.example.pawan_pc.cancerdiaganosis;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class ResultActivity extends AppCompatActivity {
    LineChart lineChart;
    BarChart barChart;
    ArrayList<String> intensity,tm;
    HashMap<String,Integer> result;
    LayoutInflater layoutInflater;
    LinearLayout linearLayout;
    CardView cardView;
    TextView result_cancer,stage,priority;
    Button graph;
    int Stage;
    boolean flag=true;
    static String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //AnimatedCircleLoadingView animatedCircleLoadingView=(AnimatedCircleLoadingView)findViewById(R.id.circle_loading_view);
        //animatedCircleLoadingView.setVisibility(View.GONE);
        intensity=new ArrayList<>();
        tm=new ArrayList<>();
        result=new HashMap<>();
        Intent i=getIntent();
        intensity=i.getStringArrayListExtra("intensity");
        tm=i.getStringArrayListExtra("time_periode");
        float[] res=new float[intensity.size()];
        float max=intensity.size()*10+tm.size()*10;
        for(int j=0;j<intensity.size();j++){
             res[j]= Float.parseFloat(intensity.get(j)) + Float.parseFloat(tm.get(j));
        }
        float sum=0;
        for(int k=0;k<res.length;k++){
            sum=sum+res[k];
        }
        Stage= (int) (max/sum);
        if(Stage<1){
            level=" 0";
        }
        else if(Stage==1){
            level=" I";
        }
        else if(Stage==2){
            level=" II & III";
        }
        else if(Stage>3){
            level=" IV";
        }
        result=(HashMap<String, Integer>)i.getSerializableExtra("analyze_result");


        Map<String,Integer> rs = result;
        linearLayout=(LinearLayout)findViewById(R.id.ll_result);


        Set<Map.Entry<String,Integer>> set = rs.entrySet();
        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String, Integer>>(set);

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });


        for(Map.Entry<String,Integer> entry:list)
        {
            System.out.println("Sorted Set: "+entry.getKey()+" :"+entry.getValue());
            if(entry.getValue()!=0){
            ShowResult(entry.getKey(),entry.getValue());}
        }


        Iterator<Map.Entry<String, Integer>> it=result.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair=(Map.Entry)it.next();
            System.out.println("result: "+pair.getKey()+":"+pair.getValue());
            if(!pair.getValue().equals("0")){
            }
        }
        lineChart=(LineChart)findViewById(R.id.linechart);
        barChart=(BarChart)findViewById(R.id.barChart);

        ArrayList<Entry> entries=new ArrayList<>();
        String[] linedata=new String[list.size()];
        int in=0;
        for (Map.Entry<String,Integer> entry:list){
            if(entry.getValue()!=0) {
                entries.add(new Entry(Float.parseFloat(intensity.get(in)), in));
                linedata[in]=intensity.get(in);
                //Toast.makeText(ResultActivity.this, intensity.get(in), Toast.LENGTH_SHORT).show();
            }
            in++;

        }
        /*entries.add(new Entry(4f, 0));
                entries.add(new Entry(8f, 1));
                entries.add(new Entry(6f, 2));*/
        ArrayList<BarEntry> entrie=new ArrayList<>();
        String[] bardata=new String[list.size()];
        int count=0;
        for(Map.Entry<String,Integer> entry:list){
            if (entry.getValue()!=0) {
                entrie.add(new BarEntry(Float.parseFloat(String.valueOf(entry.getValue())), count));
                bardata[count]= String.valueOf(entry.getValue());
                count++;
            }
        }
        LineDataSet lineDataSet=new LineDataSet(entries,"of Calls");
        ArrayList<String> label=new ArrayList<>();
        for(Map.Entry<String,Integer> entry:list){
            if(entry.getValue()!=0){
            label.add(entry.getKey());}

        }
        LineData lineData=new LineData(label,lineDataSet);
        lineDataSet.setDrawCubic(true);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        lineChart.setData(lineData);
        lineChart.animateXY(1000,1000);
        lineChart.setDescription("Chart based on Intensity analysis");
        BarDataSet barDataSet=new BarDataSet(entrie,"BarChart");
        BarData barData=new BarData(label,barDataSet);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.setDescription("Chart based on prediction priority");
        barChart.setData(barData);
        barChart.animateXY(1000,1000);


        cardView=(CardView)findViewById(R.id.result_layout);
    }


    private void ShowResult(Object key, Object value) {

            layoutInflater = LayoutInflater.from(ResultActivity.this);
            View v = layoutInflater.inflate(R.layout.result_set, null);
            result_cancer = (TextView) v.findViewById(R.id.cancer_result);
            stage = (TextView) v.findViewById(R.id.result_stage);
            priority = (TextView) v.findViewById(R.id.result_priority);
        graph=(Button)v.findViewById(R.id.graph_view);
            result_cancer.setText("Result: "+key.toString());
            priority.setText("Priority level: "+value.toString()+"0%");
        if(flag) {
            stage.setText("Stage Group: "+level+" out of V");
        }
            linearLayout.addView(v);
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
            flag=false;


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ResultActivity.this,Home.class));
    }
}
