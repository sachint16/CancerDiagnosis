package com.example.pawan_pc.cancerdiaganosis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class Call extends AppCompatActivity {
    ListView listView;
    Location mloc, dLoc;
    GeoPoint location;
    ListAdapter adapter;
    ArrayList<HashMap<String, String>> listdata = new ArrayList<HashMap<String, String>>();
    Adaptor adaptor;
    ArrayList<Datamodel> dataset = new ArrayList<>();
    TextView load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        Button call = (Button) findViewById(R.id.call_112);
        listView = (ListView) findViewById(R.id.contact_list);


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "112";
                Uri call = Uri.parse("tel:" + number);
                Intent surf = new Intent(Intent.ACTION_CALL, call);
                if (ActivityCompat.checkSelfPermission(Call.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(surf);
            }
        });
        mloc = new Location("");
        dLoc = new Location("");
        load = (TextView) findViewById(R.id.load);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("Hospital details").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        dataset.clear();
                        for (DocumentSnapshot data : documentSnapshots) {
                            location = data.getGeoPoint("location");
                            String contact = data.getString("Contact");
                            String name = data.getString("Name");
                            mloc.setLatitude(location.getLatitude());
                            mloc.setLongitude(location.getLongitude());
                            String[] locations = new PrefrenceManager(Call.this).getLocation();
                            dLoc.setLatitude(Double.parseDouble(locations[0]));
                            dLoc.setLongitude(Double.parseDouble(locations[1]));
                            float distance = mloc.distanceTo(dLoc) / 1000;
                       /* HashMap<String,String> map=new HashMap<String, String>();
                        map.put("contact",contact);
                        map.put("distance", String.valueOf(distance));
                        listdata.add(map);*/
                            dataset.add(new Datamodel(contact, String.format("%.1f", distance), name,""));

                            //Toast.makeText(Call.this,String.valueOf(distance)+"Km", Toast.LENGTH_SHORT).show();

                        }
                        adaptor = new Adaptor(dataset,R.layout.list_item, Call.this);
                        listView.setAdapter(adaptor);
                        listView.invalidate();
                        adaptor.notifyDataSetChanged();
                    }
                });
                //Toast.makeText(Call.this, "working", Toast.LENGTH_SHORT).show();


            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Datamodel dataModel = dataset.get(position);

                Snackbar.make(view, dataModel.getContact(), Snackbar.LENGTH_LONG)
                        .setAction("Call", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String call = dataModel.getContact();
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+call));
                                if (ActivityCompat.checkSelfPermission(Call.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                startActivity(intent);
                            }
                        }).show();
            }
        });


    }
}
