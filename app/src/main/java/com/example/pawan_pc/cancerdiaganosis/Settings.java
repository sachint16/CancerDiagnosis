package com.example.pawan_pc.cancerdiaganosis;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;



public class Settings extends AppCompatActivity {
    CardView about, logout,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        about=(CardView) findViewById(R.id.about);
        logout=(CardView)findViewById(R.id.logout);
        delete=(CardView)findViewById(R.id.delete);
        about.setClickable(true);
        about.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    about.setBackgroundColor(Color.argb(128,236,215,215));
                }
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    about.setBackgroundColor(Color.WHITE);
                    showAbout();
                }
                return true;
            }
        });
        logout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    logout.setBackgroundColor(Color.argb(128,236,215,215));
                }
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    logout.setBackgroundColor(Color.WHITE);
                    new MaterialStyledDialog.Builder(Settings.this)
                            .setTitle("Are you sure?").setStyle(Style.HEADER_WITH_TITLE).setHeaderColor(R.color.mdtp_button_color)
                            .setPositiveText("Yes").setNegativeText("No").onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    }).onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            new PrefrenceManager(Settings.this).setLoginState(false);
                            startActivity(new Intent(Settings.this,MainActivity.class));

                        }
                    }).withDialogAnimation(true).show();
                }
                return true;
            }
        });
        delete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    delete.setBackgroundColor(Color.argb(128,236,215,215));
                }
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    delete.setBackgroundColor(Color.WHITE);
                    new MaterialStyledDialog.Builder(Settings.this)
                            .setTitle("Delete Account?").setStyle(Style.HEADER_WITH_TITLE).setHeaderColor(R.color.mdtp_button_color)
                            .setPositiveText("Yes").setNegativeText("No").onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    }).onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            final   String email=new PrefrenceManager(Settings.this).getEmail();
                            FirebaseFirestore db=FirebaseFirestore.getInstance();
                            db.collection("/Users/Patients/List").document(email).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(Settings.this,MainActivity.class));
                                    Toast.makeText(Settings.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).withDialogAnimation(true).show();
                }
                return true;
            }
        });
    }

    private void showAbout() {
        MaterialStyledDialog.Builder dialog=new MaterialStyledDialog.Builder(this);
        dialog.setTitle("About CXA");
        dialog.setDescription("discription");
        dialog.setStyle(Style.HEADER_WITH_ICON);
        dialog.setHeaderDrawable(R.drawable.profile);
        dialog.setIcon(R.drawable.ic_info_outline_black_24dp);
        dialog.withDialogAnimation(true);
        dialog.setPositiveText("OK");
        dialog.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}
