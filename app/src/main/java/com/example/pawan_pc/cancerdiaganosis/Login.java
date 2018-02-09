package com.example.pawan_pc.cancerdiaganosis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Login extends AppCompatActivity {
    LinearLayout linearLayout;
    EditText username,password;
    Button login,back;
    private boolean i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        linearLayout=(LinearLayout)findViewById(R.id.LL1);
        Animation fadeout= AnimationUtils.loadAnimation(this,R.anim.fadeout);
        linearLayout.startAnimation(fadeout);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login_btn);
        back=(Button)findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,MainActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=username.getText().toString();
                String pass=password.getText().toString();
                CheckLogin(user,pass);
            }
        });

    }

    private void CheckLogin(final String user, final String pass) {
        final ProgressDialog pd=new ProgressDialog(Login.this);
        pd.setMessage("checking");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
        FirebaseFirestore data=FirebaseFirestore.getInstance();
        data.collection("Users").document("Patients").collection("List").document(user).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (documentSnapshot.exists()){
                    if(documentSnapshot.getString("Password").equals(pass)){
                        new PrefrenceManager(Login.this).setEmail(user);
                        new PrefrenceManager(Login.this).setLoginState(true);
                        pd.dismiss();
                        startActivity(new Intent(Login.this,Home.class));
                    }
                    else{
                        pd.dismiss();
                        Toast.makeText(Login.this, "Password inccorect!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    pd.dismiss();
                    Toast.makeText(Login.this, "Wrong credentials", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    private boolean check(String user, String pass, String s, String pass1) {
        System.out.println(user+ ":"+pass+":"+s+":"+pass1);
        if(user.equals(s) &&pass.equals(pass1)){
            i= true;
        }
        else {
            i = false;
        }
        return i;

    }
}
