package com.example.pawan_pc.cancerdiaganosis;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class FindHospital extends AppCompatActivity {
    AutoCompleteTextView search;
    ListView listView;
    Button mbtn;
    ArrayList<String> dataset = new ArrayList<>();
    ProgressDialog progressDialog;
    final String collection = "Hospital details";
    ListAdapter adaptor;
    ArrayList<Datamodel> datamodel = new ArrayList<>();
    Location location1, location2;
    GeoPoint geoPoint;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_hospital);
        search = (AutoCompleteTextView) findViewById(R.id.hospital_search);
        listView = (ListView) findViewById(R.id.search_list);
        mbtn = (Button) findViewById(R.id.search_hosp);
        mbtn.setText("Find near me");
        mbtn.setPadding(10, 10, 10, 10);
        location1 = new Location("");
        location2 = new Location("");
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    mbtn.setText("Search");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (search.getText().length() == 0) {
                    mbtn.setText("Find near me");
                }
                //mbtn.setText("find");

            }
        });
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataset);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Hold on");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        search.setAdapter(adapter);
        search.setThreshold(1);
        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                db.collection(collection).document(adapterView.getItemAtPosition(i).toString()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                        datamodel.clear();
                        if (e == null && documentSnapshot.exists()) {
                            Map<String, Object> data = documentSnapshot.getData();
                            String[] location = new PrefrenceManager(FindHospital.this).getLocation();
                            location1.setLatitude(Double.parseDouble(location[0]));
                            location1.setLongitude(Double.parseDouble(location[1]));
                            geoPoint = documentSnapshot.getGeoPoint("location");
                            location2.setLatitude(geoPoint.getLatitude());
                            location2.setLongitude(geoPoint.getLongitude());
                            float distance = location1.distanceTo(location2) / 1000;
                            datamodel.add(new Datamodel(data.get("Contact").toString(), String.format("%.1f", distance), data.get("Name").toString(), data.get("Address").toString()));

                            adaptor = new ListAdapter(FindHospital.this, R.layout.hospitals_list, datamodel);
                            listView.invalidateViews();
                            listView.setAdapter(adaptor);

                            adaptor.notifyDataSetChanged();


                        }
                    }
                });
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Datamodel data = datamodel.get(i);
                CharSequence[] option = new CharSequence[]{"Call", "View in map"};
                AlertDialog.Builder builder = new AlertDialog.Builder(FindHospital.this);
                builder.setTitle("Select");
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            String call = data.getContact();
                            Intent Call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + call));
                            if (ActivityCompat.checkSelfPermission(FindHospital.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            startActivity(Call);
                        }
                        else if(i==1){
                            FirebaseFirestore db=FirebaseFirestore.getInstance();
                            db.collection("Hospital details").document(data.getName()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                                    if(documentSnapshot.exists()){
                                        GeoPoint location=documentSnapshot.getGeoPoint("location");
                                        String uri=String.format(Locale.ENGLISH,"geo:%f,%f",location.getLatitude(),location.getLongitude());
                                        Intent map=new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                        startActivity(map);
                                    }
                                    else{
                                        Toast.makeText(FindHospital.this, "unable to get location", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
                builder.create().show();

            }
        });

        db=FirebaseFirestore.getInstance();
        db.collection(collection).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    for(DocumentSnapshot data:task.getResult()){
                        String name=data.getString("Name");
                        dataset.add(name);

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FindHospital.this, "database connection error", Toast.LENGTH_SHORT).show();
            }
        });
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mbtn.getText().equals("Find near me")){

                    showdata();

                }
            }
        });

    }

        Comparator<Datamodel> comparator = new Comparator<Datamodel>() {

            @Override
            public int compare(Datamodel object1, Datamodel object2) {
                return Double.compare(Double.parseDouble(object1.getDistance()),Double.parseDouble(object2.getDistance()));
            }
        };


    private void showdata() {
        //Toast.makeText(FindHospital.this, "1", Toast.LENGTH_SHORT).show();
        FirebaseFirestore database=FirebaseFirestore.getInstance();
        database.collection("Hospital details").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                datamodel.clear();
                for (DocumentSnapshot data : documentSnapshots) {
                    //Toast.makeText(FindHospital.this, data.getId(), Toast.LENGTH_SHORT).show();


                    String[] location = new PrefrenceManager(FindHospital.this).getLocation();
                    location1.setLatitude(Double.parseDouble(location[0]));
                    location1.setLongitude(Double.parseDouble(location[1]));
                    geoPoint = data.getGeoPoint("location");
                    location2.setLatitude(geoPoint.getLatitude());
                    location2.setLongitude(geoPoint.getLongitude());
                    float distance = location1.distanceTo(location2)/1000;

                    datamodel.add(new Datamodel(data.getString("Contact"),String.format("%.1f",distance),data.getString("Name"),data.getString("Address" )));

                }
                adaptor=new ListAdapter(FindHospital.this,R.layout.hospitals_list,datamodel);
                listView.invalidateViews();
                listView.setAdapter(adaptor);
                Collections.sort(datamodel,comparator);

                adaptor.notifyDataSetChanged();

            }


        });

    }
}
