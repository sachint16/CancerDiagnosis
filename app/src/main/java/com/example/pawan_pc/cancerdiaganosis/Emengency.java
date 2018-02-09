package com.example.pawan_pc.cancerdiaganosis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class Emengency extends AppCompatActivity {
    Button call, find,tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emengency);
        call=(Button)findViewById(R.id.call);
        find=(Button)findViewById(R.id.find_hos);
        tips=(Button)findViewById(R.id.tips);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.slide);
        call.startAnimation(animation);

            find.startAnimation(animation);

            tips.startAnimation(animation);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Emengency.this,Call.class));

            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Emengency.this,FindHospital.class));
            }
        });
        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Emengency.this,InstantTips.class));
            }
        });

    }
}
