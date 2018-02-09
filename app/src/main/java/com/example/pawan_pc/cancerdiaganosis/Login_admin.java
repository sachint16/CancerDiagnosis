package com.example.pawan_pc.cancerdiaganosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

/**
 * Created by Pawan-PC on 29-10-2017.
 */
public class Login_admin extends AppCompatActivity{
    EditText username,password;
    TextView error;
    Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        username=(EditText)findViewById(R.id.admin_user);
        password=(EditText)findViewById(R.id.admin_pass);
        error=(TextView)findViewById(R.id.error);
        error.setVisibility(View.GONE);
        //password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        login=(Button)findViewById(R.id.admin_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                db.collection("Admin").document("login").addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                        String user=documentSnapshot.getString("Username");
                        String pass=documentSnapshot.getString("Password");
                        if(user.equals(username.getText().toString().trim())&&pass.equals(password.getText().toString().trim())){
                            startActivity(new Intent(Login_admin.this,Training.class));
                        }
                        else {
                            error.setVisibility(View.VISIBLE);
                            error.setText("Invalid username/password!");
                        }
                    }
                });

            }
        });
    }
}
