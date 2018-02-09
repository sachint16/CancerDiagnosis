package com.example.pawan_pc.cancerdiaganosis;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pawan-PC on 29-10-2017.
 */
public class FragmentView extends Fragment {
    ListView listView;
    Button hospitallist,traingdata;
    ArrayList<String> H_data=new ArrayList<>();
    ArrayAdapter<String> adapter;
    final String url="http://my171database.890m.com/CancerDetectionApp/disease_symptoms.php";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.view_fragment,container,false);
        listView=(ListView)v.findViewById(R.id.viewData);
        hospitallist=(Button)v.findViewById(R.id.viewHospitaldata);
        traingdata=(Button)v.findViewById(R.id.viewTrainingdata);
        hospitallist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //listView.removeAllViews();
                //H_data.clear();
                if(adapter.isEmpty()){
                adapter.clear();}
                final ProgressDialog pd=new ProgressDialog(getActivity());
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setMessage("loading");
                pd.show();
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                db.collection("Hospital details").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot data:task.getResult()){
                                H_data.add(data.getString("Name"));
                            }
                        }
                    }
                });
                if(H_data.size()>0) {
                    pd.dismiss();

                    adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, H_data);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }
        });
        traingdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!adapter.isEmpty()){
                adapter.clear();}
                JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0;i<response.length();i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name=jsonObject.getString("d_name");
                                String sym=jsonObject.getString("d_symptoms");
                                H_data.add(name+" :: "+sym);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "no data found!", Toast.LENGTH_SHORT).show();

                    }
                });
                RequestQueue rq= Volley.newRequestQueue(getActivity());
                rq.add(jsonArrayRequest);
                adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,H_data);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

        });
        return v;
    }
}
