package com.example.pawan_pc.cancerdiaganosis;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Search extends AppCompatActivity {
    AutoCompleteTextView search;
    Button find;
    ArrayList<String> languages=new ArrayList<>();
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        search=(AutoCompleteTextView)findViewById(R.id.search_box);
        find=(Button)findViewById(R.id.find);
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.setMessage("Wait...");
        progressDialog.show();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,languages);
        search.setAdapter(arrayAdapter);
        search.setThreshold(1);
        listView=(ListView)findViewById(R.id.list);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        FirebaseFirestore data=FirebaseFirestore.getInstance();
        data.collection("Hospital details").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    for(DocumentSnapshot data:task.getResult()){
                        Map<String, Object> details=new HashMap<String, Object>();
                        details=data.getData();

                        languages.add(details.get("Name").toString());
                    }
                }
            }
        });


    }

}
