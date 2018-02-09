package com.example.pawan_pc.cancerdiaganosis;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Report extends AppCompatActivity {
    LineChart lineChart;
    BarChart barChart;
    ArrayList<Entry> linechart=new ArrayList<>();
    ArrayList<BarEntry> barchart=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        lineChart=(LineChart)findViewById(R.id.linechart);
        barChart=(BarChart)findViewById(R.id.barchart);
        db.collection("/Users/Patients/List").document(new PrefrenceManager(Report.this).getEmail()).collection("stats").document("data").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    String[] line= (String[]) task.getResult().get("linedata");
                    String[] bar= (String[]) task.getResult().get("bardata");
                    for(int i=0;i<line.length;i++){
                        linechart.add(new Entry(Float.parseFloat(line[i]),i));
                        barchart.add(new BarEntry(Float.parseFloat(bar[i]),i));
                    }
                    LineDataSet lds=new LineDataSet(linechart,"#of calls");
                    LineData ld=new LineData(line,lds);
                    lineChart.setData(ld);



                }
            }
        });
    }
}
