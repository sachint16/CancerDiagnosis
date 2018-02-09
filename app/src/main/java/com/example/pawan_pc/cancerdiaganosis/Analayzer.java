package com.example.pawan_pc.cancerdiaganosis;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pawan-PC on 21-10-2017.
 */
public class Analayzer {
    private String[] split;
    JSONArray diseases;
    ArrayList<HashMap<String,String>> input;

    public JSONArray getDiseases() {
        return diseases;
    }

    public void setDiseases(JSONArray diseases) {
        this.diseases = diseases;
    }

    public ArrayList<HashMap<String, String>> getInput() {
        return input;
    }

    public void setInput(ArrayList<HashMap<String, String>> input) {
        this.input = input;
    }

    public Analayzer(Context context, JSONArray diseases, ArrayList<HashMap<String, String>> input) {
        this.diseases = diseases;
        this.input = input;
        ArrayList<String> iplist=new ArrayList<>();
        int count=0;
        JSONObject object=null;
        HashMap<String,Object> data=new HashMap<>();
        for (int i=0;i<input.size();i++){
            iplist.add(i,input.get(i).get("symptom"));
            Toast.makeText(context,iplist.get(i), Toast.LENGTH_SHORT).show();
        }
        for(int i=0;i<diseases.length();i++){
            try {
                object=diseases.getJSONObject(i);
                data.put(object.getString("d_name"),object.get("d_symptoms"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            assert object != null;
            Object d=data.get(object.getString("d_name"));
            String v=d.toString();
            split=v.split(",");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i=0;i<iplist.size();i++) {
            for (int j=0;j<split.length;j++) {

                if (iplist.get(i).contains(split[j])) {
                    count = count + 1;
                    Toast.makeText(context, String.valueOf(count), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(context,iplist.get(i)+split[i], Toast.LENGTH_SHORT).show();
            }
        }

    }


}
