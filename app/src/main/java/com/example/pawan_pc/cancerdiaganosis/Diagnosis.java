package com.example.pawan_pc.cancerdiaganosis;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Diagnosis extends AppCompatActivity implements View.OnClickListener {
    Button analysis,add;
    ListView listView;
    AutoCompleteTextView textView;
    ArrayList<String> symptopms = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private String URL = "http://my171database.890m.com/CancerDetectionApp/symptoms.php";
    private String Url = "http://my171database.890m.com/CancerDetectionApp/disease_symptoms.php";
    Button low, mod, high, vHigh;
    TextView day, week, month, SixMonth, year;
    static float levelValue;
    static int peroidValue;
    ArrayList<HashMap<String, String>> listItems = new ArrayList<>();
    JSONArray data;
    Symptom_adaptor sadaptor;
   static ArrayList<String> symptom=new ArrayList<>();
   static ArrayList<String> diseases=new ArrayList<>();
    private AnimatedCircleLoadingView animatedCircleLoadingView;
    Intent dataset;


    String[][] multiDimensional = null;
    int[] count;
    private boolean goFlag=false;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);
        textView = (AutoCompleteTextView) findViewById(R.id.symtoms);
        animatedCircleLoadingView=(AnimatedCircleLoadingView)findViewById(R.id.circle_loading_view);
        animatedCircleLoadingView.setVisibility(View.INVISIBLE);
        getSymptoms();
        getDiseases();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, symptopms);
        textView.setAdapter(adapter);
        textView.setThreshold(1);
        listView = (ListView) findViewById(R.id.symptom_list);
        analysis = (Button) findViewById(R.id.final_analysis);
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String value = adapterView.getItemAtPosition(i).toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(Diagnosis.this);
                builder.setTitle("level of " + value);
                LayoutInflater v = LayoutInflater.from(Diagnosis.this);
                View vv = v.inflate(R.layout.level, null);
                low = (Button) vv.findViewById(R.id.low);
                mod = (Button) vv.findViewById(R.id.moderate);
                high = (Button) vv.findViewById(R.id.high);
                vHigh = (Button) vv.findViewById(R.id.veryhigh);
                low.setOnClickListener(Diagnosis.this);
                mod.setOnClickListener(Diagnosis.this);
                high.setOnClickListener(Diagnosis.this);
                vHigh.setOnClickListener(Diagnosis.this);
                day = (TextView) vv.findViewById(R.id.twodays);
                week = (TextView) vv.findViewById(R.id.week);
                month = (TextView) vv.findViewById(R.id.month);
                SixMonth = (TextView) vv.findViewById(R.id.six_month);
                year = (TextView) vv.findViewById(R.id.year);
                day.setOnClickListener(Diagnosis.this);
                week.setOnClickListener(Diagnosis.this);
                month.setOnClickListener(Diagnosis.this);
                SixMonth.setOnClickListener(Diagnosis.this);
                year.setOnClickListener(Diagnosis.this);

                builder.setView(vv);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Map<String, String> data = new HashMap<String, String>();
                        data.put("symptom", value);
                        data.put("level", String.valueOf(levelValue));
                        data.put("priode", String.valueOf(peroidValue));
                        listItems.add((HashMap<String, String>) data);
                        sadaptor = new Symptom_adaptor(Diagnosis.this, listItems);
                        listView.setAdapter(sadaptor);
                        sadaptor.notifyDataSetChanged();
                        textView.setText("");


                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setLayout(600, 500);
                alertDialog.show();


            }
        });
        add=(Button)findViewById(R.id.add_symptom);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                final String value=textView.getEditableText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(Diagnosis.this);
                builder.setTitle("level of " + value);
                LayoutInflater v = LayoutInflater.from(Diagnosis.this);
                View vv = v.inflate(R.layout.level, null);
                low = (Button) vv.findViewById(R.id.low);
                mod = (Button) vv.findViewById(R.id.moderate);
                high = (Button) vv.findViewById(R.id.high);
                vHigh = (Button) vv.findViewById(R.id.veryhigh);
                low.setOnClickListener(Diagnosis.this);
                mod.setOnClickListener(Diagnosis.this);
                high.setOnClickListener(Diagnosis.this);
                vHigh.setOnClickListener(Diagnosis.this);
                day = (TextView) vv.findViewById(R.id.twodays);
                week = (TextView) vv.findViewById(R.id.week);
                month = (TextView) vv.findViewById(R.id.month);
                SixMonth = (TextView) vv.findViewById(R.id.six_month);
                year = (TextView) vv.findViewById(R.id.year);
                day.setOnClickListener(Diagnosis.this);
                week.setOnClickListener(Diagnosis.this);
                month.setOnClickListener(Diagnosis.this);
                SixMonth.setOnClickListener(Diagnosis.this);
                year.setOnClickListener(Diagnosis.this);

                builder.setView(vv);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Map<String, String> data = new HashMap<String, String>();
                        data.put("symptom", value);
                        data.put("level", String.valueOf(levelValue));
                        data.put("priode", String.valueOf(peroidValue));
                        listItems.add((HashMap<String, String>) data);
                        sadaptor = new Symptom_adaptor(Diagnosis.this, listItems);
                        listView.setAdapter(sadaptor);
                        sadaptor.notifyDataSetChanged();
                        textView.setText("");


                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setLayout(600, 500);
                alertDialog.show();

            }
        });
        analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listItems.size()>=2) {
                    animatedCircleLoadingView.setVisibility(View.VISIBLE);

                    animatedCircleLoadingView.startDeterminate();
                    startPercentMockThread();
                    //new Analayzer().setInput(listItems);
                    ArrayList<String> list = new ArrayList<String>();
                    ArrayList<String> intensity = new ArrayList<String>();
                    ArrayList<String> time = new ArrayList<String>();
                    //assert data!=null;
                    // new Analayzer(Diagnosis.this,data,listItems);
                    for (int i = 0; i < listItems.size(); i++) {

                        String sym = listItems.get(i).get("symptom");
                        list.add(sym);
                        String level = listItems.get(i).get("level");
                        intensity.add(level);
                        String pr = listItems.get(i).get("priode");
                        time.add(pr);
                        //Toast.makeText(Diagnosis.this, sym, Toast.LENGTH_SHORT).show();
                    }
                    JSONArray jsonArray = new JSONArray(Collections.singletonList(list));
                    //Toast.makeText(getApplicationContext(), String.valueOf(jsonArray), Toast.LENGTH_LONG).show();
                    getMultiArray(list, intensity, time, diseases, symptom);
                }
                else {
                    Toast.makeText(Diagnosis.this, "Please select at least three symptoms", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }
    private void startPercentMockThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    for (int i = 0; i <= 100; i++) {
                        Thread.sleep(65);
                        changePercent(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }
    private void changePercent(final int percent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animatedCircleLoadingView.setPercent(percent);
            }
        });
        if(percent==100) {
            goFlag=true;
            animatedCircleLoadingView.stopOk();
            startActivity(dataset);

        }
    }

    private void getDiseases() {
        JsonArrayRequest json = new JsonArrayRequest(Request.Method.GET, Url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //new Analayzer().setDiseases(response);
                data = response;
                JSONObject object = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        ////////////////////////

                        object = response.getJSONObject(i);
                        object.getString("d_name");
                        object.getString("d_symptoms");
                        diseases.add(i, object.getString("d_name"));
                        symptom.add(i, object.getString("d_symptoms"));



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
              //  Toast.makeText(Diagnosis.this,diseases.get(0).toString(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(Diagnosis.this, symptom.get(0).toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(Diagnosis.this);
        queue.add(json);

    }

    public void getSymptoms() {
        final ProgressDialog progress = new ProgressDialog(Diagnosis.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("getting data");
        progress.setIndeterminate(true);
        progress.setMax(100);
        progress.show();
        JsonArrayRequest json = new JsonArrayRequest(Request.Method.GET, URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray data = new JSONArray();
                data = response;
                JSONObject object = null;
                for (int i = 0; i < data.length(); i++) {
                    try {
                        object = data.getJSONObject(i);
                        symptopms.add(object.getString("s_name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                progress.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(Diagnosis.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(json);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.low:
                low.setBackgroundColor(Color.argb(255, 250, 175, 0));
                mod.setBackgroundColor(Color.WHITE);
                high.setBackgroundColor(Color.WHITE);
                vHigh.setBackgroundColor(Color.WHITE);
                levelValue = (float) 2.5;
                break;
            case R.id.moderate:
                mod.setBackgroundColor(Color.argb(255, 250, 117, 0));
                low.setBackgroundColor(Color.WHITE);
                high.setBackgroundColor(Color.WHITE);
                vHigh.setBackgroundColor(Color.WHITE);
                levelValue = (float) 5.0;
                break;
            case R.id.high:
                high.setBackgroundColor(Color.argb(255, 255, 51, 51));
                mod.setBackgroundColor(Color.WHITE);
                low.setBackgroundColor(Color.WHITE);
                vHigh.setBackgroundColor(Color.WHITE);
                levelValue = (float) 7.5;
                break;
            case R.id.veryhigh:
                vHigh.setBackgroundColor(Color.argb(255, 255, 0, 0));
                mod.setBackgroundColor(Color.WHITE);
                high.setBackgroundColor(Color.WHITE);
                low.setBackgroundColor(Color.WHITE);
                levelValue = (float) 10.0;
                break;
            case R.id.twodays:
                day.setTextSize(22);
                week.setTextSize(16);
                month.setTextSize(16);
                SixMonth.setTextSize(16);
                year.setTextSize(16);
                peroidValue = 2;
                break;
            case R.id.week:
                week.setTextSize(22);
                day.setTextSize(16);
                month.setTextSize(16);
                SixMonth.setTextSize(16);
                year.setTextSize(16);
                peroidValue = 4;
                break;
            case R.id.month:
                month.setTextSize(22);
                week.setTextSize(16);
                day.setTextSize(16);
                SixMonth.setTextSize(16);
                year.setTextSize(16);
                peroidValue = 6;
                break;
            case R.id.six_month:
                SixMonth.setTextSize(22);
                week.setTextSize(16);
                month.setTextSize(16);
                day.setTextSize(16);
                year.setTextSize(16);
                peroidValue = 8;
                break;
            case R.id.year:
                year.setTextSize(22);
                week.setTextSize(16);
                month.setTextSize(16);
                SixMonth.setTextSize(16);
                day.setTextSize(16);
                peroidValue = 10;
             //   Toast.makeText(Diagnosis.this, (int) (levelValue) + "--" + (peroidValue), Toast.LENGTH_SHORT).show();
                break;
        }

    }


    public String[][] getMultiArray(ArrayList<String> list,ArrayList<String> intensity,ArrayList<String> time, ArrayList<String> diseases, ArrayList<String> symptom) {
       // Toast.makeText(Diagnosis.this, "lisy"+diseases.toString(), Toast.LENGTH_SHORT).show();
        String [] disease = new String[diseases.size()];
        String [] sym= new String[symptom.size()];
        String []ip_symp=new String[list.size()];
        for(int i=0;i<diseases.size();i++){
            disease[i]=diseases.get(i);
        }
        for (int i=0;i<symptom.size();i++){
            sym[i]=symptom.get(i);
        }
        for (int i=0;i<list.size();i++){
            ip_symp[i]=list.get(i);
        }

        int[] ln = new int[disease.length];

        String[][] multi = new String[disease.length][disease.length];
        String[] temp2 = null;

        for(int i=0;i<disease.length;i++)
        {
            String[] temp = sym[i].split(",");
            ln[i] = temp.length;

            if(i == disease.length-1)
            {
                temp2 = temp;
            }
        }

        for(int i=0;i<ln.length;i++)
        {
            multi[i] = new String[ln[i]];
        }

        for(int i=0;i<sym.length;i++)
        {
            multi[i] = sym[i].split(",");
        }

        for(int i=0;i<multi.length;i++)
        {
            for(int j=0;j<multi[i].length;j++)
            {
               // Toast.makeText(Diagnosis.this, "Final: "+multi[i][j], Toast.LENGTH_SHORT).show();
            }
        }
        count = new int[disease.length];


        for(int j=0;j<multi.length;j++)
        {
            int z = 0;

            for(int k=0;k<multi[j].length;k++)
            {
                //System.out.println("Picked Symptom: "+temp_symptom);
                //System.out.println("Checking Symptom: "+d_symptoms[j][k]);

                for(int i =0;i<ip_symp.length;i++)
                {

                    String temp_symptom = ip_symp[i];

                    if(temp_symptom.equals(multi[j][k]))
                    {
                        count[j] = ++z;
                        System.out.println("Count: "+count[j]);

                    }
                }
            }
        }
        HashMap<String,Integer> d=new HashMap<>();

        for(int i=0;i<count.length;i++)
        {
            System.out.println(disease[i]+":"+count[i]);

            d.put(disease[i],count[i]);

        }

        Arrays.sort(count);
        int found=count[count.length-1];
        dataset=new Intent(Diagnosis.this,ResultActivity.class);
        dataset.putStringArrayListExtra("intensity",intensity);
        dataset.putStringArrayListExtra("time_periode",time);
        dataset.putExtra("analyze_result",d);
        if(goFlag) {
            startActivity(dataset);
        }



        return multi;



    }
}
