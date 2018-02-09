package com.example.pawan_pc.cancerdiaganosis;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Map;

public class profile extends AppCompatActivity {
    LinearLayout linearLayout;
    Map<String,Object> data;
    LinearLayout.LayoutParams params;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        linearLayout=(LinearLayout)findViewById(R.id.profileDetails);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        
        db.collection("/Users/Patients/List").document(new PrefrenceManager(profile.this).getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if(task.isSuccessful()){
                DocumentSnapshot result = task.getResult();
                data=result.getData();
                showDetail(data);
            }
            }
        });
        params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        animation= AnimationUtils.loadAnimation(this,R.anim.fadeout);
    }

    private void showDetail(Map<String, Object> data) {
        linearLayout.removeAllViews();
        for(Map.Entry<String,Object> entry:data.entrySet()){
            if(!entry.getKey().equals("Password")) {
                TableRow layout = new TableRow(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                TextView t1 = new TextView(this);
                TextView t2 = new TextView(this);
                t1.setText(entry.getKey());
                t2.setText(entry.getValue().toString());
                t1.setLayoutParams(params);
                t2.setLayoutParams(params);
                t1.setGravity(Gravity.LEFT);
                t2.setGravity(Gravity.LEFT);
                t1.setTextSize(16);
                t2.setTextSize(16);
                t1.setInputType(1);
                t2.setInputType(1);
                t1.setPadding(10, 10, 10, 10);
                t2.setPadding(10, 10, 10, 10);
                layout.addView(t1, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                layout.addView(t2, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                //layout.addView(t1);
                //layout.addView(t2);
                layout.startAnimation(animation);
                linearLayout.addView(layout);
            }
            
        }
        
    }
}
