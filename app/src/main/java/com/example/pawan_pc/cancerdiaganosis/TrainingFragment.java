package com.example.pawan_pc.cancerdiaganosis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pawan-PC on 29-10-2017.
 */
public class TrainingFragment extends Fragment{
    EditText d,sym;
    Button submit;
    String name,symptoms;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.training_fragment,container,false);
        d=(EditText)v.findViewById(R.id.disease_name);
        sym=(EditText)v.findViewById(R.id.d_sympt);
        submit=(Button)v.findViewById(R.id.training);
        final String url="http://my171database.890m.com/CancerDetectionApp/training_data.php";
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               name =d.getText().toString();
                 symptoms=sym.getText().toString();
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        // Adding All values to Params.
                        // The firs argument should be same sa your MySQL database table columns.
                        params.put("d_name", name);
                        params.put("symptoms", symptoms);
                        return params;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);
            }
        });
        return v;
    }
}
