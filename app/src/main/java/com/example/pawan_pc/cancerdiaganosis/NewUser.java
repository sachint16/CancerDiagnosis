package com.example.pawan_pc.cancerdiaganosis;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class NewUser extends AppCompatActivity implements com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener{
    TextView titleText;
    ImageView imageView;
    LinearLayout linearLayout;
    LinearLayout.LayoutParams params;
    static int viewcount=0;
    int i=0;
    private boolean state=true;
    String[] titles;
    Map<String,Object> data;
    static String dob;
    EditText date;
    private static boolean isexists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        titleText=(TextView)findViewById(R.id.notations);
        imageView=(ImageView)findViewById(R.id.loading);
        Glide.with(this).asGif().load(R.drawable.loading).into(imageView);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity=Gravity.RIGHT;
        params.setMarginEnd(36);
        titles=new String[]{"Nice to meet you, To keep data safe, we'll need to create a personal account.","you can change yout last" +
                " answer by tapping on it.","Ok, Let start with an email address to sign up.","Choose your password.","Thanks," +
                " I get smarter the more you tell me, So I'm going to ask a few question for more accurate diagnose.","Great, What is your" +
                " name?","Gender?","And What is your date of birth?","Are you a Smoker?","Do you have any Cancer Test History?","Thank you, " +
                "That's enough info for me for now. Tap OK now."};
        linearLayout=(LinearLayout)findViewById(R.id.LL2);
        data=new HashMap<>();

                    Glide.with(this).asGif().load(R.drawable.progress).into(imageView);
                    titleText.setText(titles[i]);
                    Button button = new Button(this);
                    button.setText("Okey");
                    button.setBackgroundResource(R.drawable.shapebtn);
                    button.setLeft(16);

                    button.setLayoutParams(params);
                    linearLayout.addView(button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ChangeView(1);
                            viewcount++;



                        }
                    });

                }

    private void ChangeView(final int i) {
        switch (i){
            case 0:
                linearLayout.removeAllViews();
                Glide.with(this).asGif().load(R.drawable.loadinggg).into(imageView);
                titleText.setText(titles[i]);
                Button button = new Button(this);
                button.setText("Okey");
                button.setBackgroundResource(R.drawable.shapebtn);
                button.setLeft(16);

                button.setLayoutParams(params);
                linearLayout.addView(button);
                viewcount=i;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ChangeView(1);



                    }
                });
                break;
            case 1:
                viewcount=i;
                linearLayout.removeAllViews();
                titleText.setText(titles[i]);
                Button b = new Button(this);
                b.setText("Okey");
                b.setBackgroundResource(R.drawable.shapebtn);
                b.setLeft(16);

                b.setLayoutParams(params);
                linearLayout.addView(b);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ChangeView(2);

                    }
                });
                break;
            case 2:
                linearLayout.removeAllViews();
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                Glide.with(this).asGif().load(R.drawable.progress).into(imageView);
                titleText.setText(titles[i]);
                Button b1 = new Button(this);
                b1.setText("Done");
                b1.setBackgroundResource(R.drawable.shapebtn);
                b1.setLeft(16);

                b1.setLayoutParams(params);
                final EditText email = new EditText(this);
                email.setHint("Type your email address");
                params.setMargins(22, 22, 22, 22);
                email.setPadding(20,20,20,20);
                email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                email.setBackgroundResource(R.drawable.shapereg);
                email.setLayoutParams(params);
                linearLayout.addView(email);
                linearLayout.addView(b1);
                viewcount=i;

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String Email=email.getText().toString();
                        Pattern p=Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                        final Matcher matcher=p.matcher(Email);
                        FirebaseFirestore db=FirebaseFirestore.getInstance();
                        //isexists = false;
                        assert Email.length()<0;
                        db.collection("/Users/Patients/List").document(Email).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                                isexists =documentSnapshot.exists();
                                System.out.println(isexists);
                                if (matcher.find()){
                                    if (!isexists) {
                                        new PrefrenceManager(NewUser.this).setEmail(Email);

                                        new userdata().setEmail(email.getText().toString());
                                        data.put("Email", Email);
                                        ChangeView(3);
                                    } }

                            }
                        });
                        System.out.println(isexists);



                    }
                });
                break;
            case 3:
                linearLayout.removeAllViews();
                viewcount=i;

                Glide.with(this).asGif().load(R.drawable.progress).into(imageView);
                titleText.setText(titles[i]);
                Button pass = new Button(this);
                pass.setText("Sign Up");
                pass.setBackgroundResource(R.drawable.shapebtn);
                pass.setLeft(16);
                pass.setLayoutParams(params);
                final EditText paasword = new EditText(this);
                paasword.setHint("Enter a password");
                params.setMargins(22, 22, 22, 22);
                paasword.setPadding(20,20,20,20);
                paasword.setBackgroundResource(R.drawable.shapereg);
                paasword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                paasword.setLayoutParams(params);
                linearLayout.addView(paasword);
                linearLayout.addView(pass);
                pass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (paasword.getText().length() < 8) {
                            Toast.makeText(getApplicationContext(), "password should'nt be less then 8 characters", Toast.LENGTH_LONG).show();
                            paasword.setText("");
                            ChangeView(3);
                        } else {
                            new userdata().setPass(paasword.getText().toString());
                            data.put("Password",paasword.getText().toString());
                            SignUp(data.get("Email").toString(),new userdata().getPass());
                            ChangeView(4);
                        }

                    }
                });
                break;
            case 4:
                viewcount=i;
                linearLayout.removeAllViews();
                titleText.setText(titles[i]);
                Button done = new Button(this);
                done.setText("Okey");
                done.setBackgroundResource(R.drawable.shapebtn);
                done.setLeft(16);

                done.setLayoutParams(params);
                linearLayout.addView(done);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ChangeView(5);


                    }
                });
                break;
            case 5:
                linearLayout.removeAllViews();
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                Glide.with(this).asGif().load(R.drawable.progress).into(imageView);
                titleText.setText(titles[i]);
                Button name = new Button(this);
                name.setText("Done");
                name.setBackgroundResource(R.drawable.shapebtn);
                name.setLeft(16);

                name.setLayoutParams(params);
                final EditText Name = new EditText(this);
                Name.setHint("Type your name");
                params.setMargins(22, 22, 22, 22);
                Name.setPadding(20,20,20,20);
                Name.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                Name.setBackgroundResource(R.drawable.shapereg);
                Name.setLayoutParams(params);
                linearLayout.addView(Name);
                linearLayout.addView(name);
                viewcount=i;

                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Email=Name.getText().toString();
                        if(Email.length()>1) {
                            new userdata().setName(Name.getText().toString());
                            data.put("Name",Email);
                            SignUp(data.get("Email").toString(),"");
                            ChangeView(6);
                        }
                        else{
                            Toast.makeText(NewUser.this, "Invalid email address!", Toast.LENGTH_SHORT).show();
                            ChangeView(2);
                        }

                    }
                });
                break;
            case 6:
                linearLayout.removeAllViews();
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                Glide.with(this).asGif().load(R.drawable.progress).into(imageView);
                titleText.setText(titles[i]);
                final Button male = new Button(this);
                male.setText("Male");
                male.setBackgroundResource(R.drawable.shapebtn);
                male.setLeft(16);

                male.setLayoutParams(param);
                final Button female= new Button(this);
                female.setText("Female");
                param.setMargins(22, 22, 22, 22);
                female.setBackgroundResource(R.drawable.shapereg);
                female.setLayoutParams(param);
                final Button Ok= new Button(this);
                Ok.setText("Okey");
                param.setMargins(22, 22, 22, 22);
                Ok.setBackgroundResource(R.drawable.shapereg);
                Ok.setLayoutParams(param);
                linearLayout.addView(male);
                linearLayout.addView(female);


                viewcount=i;

                male.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        male.setBackgroundColor(Color.BLUE);
                        male.setTextColor(Color.WHITE);
                        data.put("Gender","Male");
                        female.setBackgroundColor(Color.WHITE);
                        female.setTextColor(Color.BLACK);
                        SignUp(data.get("Email").toString(),"");
                        ChangeView(7);

                    }
                });
                female.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        female.setBackgroundColor(Color.BLUE);
                        female.setTextColor(Color.WHITE);
                        data.put("Gender","female");
                        male.setBackgroundColor(Color.WHITE);
                        male.setTextColor(Color.BLACK);
                        SignUp(data.get("Email").toString(),"");
                        ChangeView(7);


                    }
                });

                break;
            case 7:
                linearLayout.removeAllViews();
                titleText.setText(titles[i]);
                Button ok = new Button(this);
                ok.setText("Okey");
                ok.setBackgroundResource(R.drawable.shapebtn);
                ok.setLeft(16);

                ok.setLayoutParams(params);
                date=new EditText(this);
                date.setBackgroundResource(R.drawable.shapebtn);
                date.setLayoutParams(params);
                date.setHint("choose here");
                date.setPadding(22,22,22,22);
                date.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                            date();
                        }
                        return true;
                    }
                });
                linearLayout.addView(date);


                linearLayout.addView(ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                            data.put("Date of Birth", date.getText().toString());
                        SignUp(data.get("Email").toString(),"");
                        ChangeView(8);
                    }
                });
                break;
            case 8:
                linearLayout.removeAllViews();
                LinearLayout.LayoutParams parameter = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                Glide.with(this).asGif().load(R.drawable.progress).into(imageView);
                titleText.setText(titles[i]);
                final Button yes = new Button(this);
                yes.setText("Yes");
                yes.setBackgroundResource(R.drawable.shapebtn);
                yes.setLeft(16);

                yes.setLayoutParams(parameter);
                final Button no= new Button(this);
                no.setText("No");
                parameter.setMargins(22, 22, 22, 22);
                no.setBackgroundResource(R.drawable.shapereg);
                no.setLayoutParams(parameter);
                linearLayout.addView(yes);
                linearLayout.addView(no);


                viewcount=i;

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        yes.setBackgroundColor(Color.BLUE);
                        yes.setTextColor(Color.WHITE);
                        yes.setBackgroundResource(R.drawable.shapebtn);
                        data.put("Smoker","Yes");
                        no.setBackgroundColor(Color.WHITE);
                        no.setTextColor(Color.BLACK);
                        SignUp(data.get("Email").toString(),"");
                        ChangeView(9);

                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        no.setBackgroundColor(Color.BLUE);
                        no.setTextColor(Color.WHITE);
                        data.put("Smoker","No");
                        yes.setBackgroundColor(Color.WHITE);
                        yes.setTextColor(Color.BLACK);
                        SignUp(data.get("Email").toString(),"");
                        ChangeView(9);


                    }
                });

                break;
            case 9:
                linearLayout.removeAllViews();

                Glide.with(this).asGif().load(R.drawable.progress).into(imageView);
                titleText.setText(titles[i]);
                final Button Yes = new Button(this);
                Yes.setText("Yes");
                Yes.setBackgroundResource(R.drawable.shapebtn);
                Yes.setLeft(16);

                Yes.setLayoutParams(params);
                final Button No= new Button(this);
                No.setText("No");
                params.setMargins(22, 22, 22, 22);
                No.setBackgroundResource(R.drawable.shapereg);
                No.setLayoutParams(params);
                linearLayout.addView(Yes);
                linearLayout.addView(No);


                viewcount=i;

                Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Yes.setBackgroundColor(Color.BLUE);
                        Yes.setTextColor(Color.WHITE);
                        Yes.setBackgroundResource(R.drawable.shapebtn);
                        data.put("Cancer Test History","Yes");
                        No.setBackgroundColor(Color.WHITE);
                        No.setTextColor(Color.BLACK);
                        SignUp(data.get("Email").toString(),"");
                        ChangeView(10);

                    }
                });
                No.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        No.setBackgroundColor(Color.BLUE);
                        No.setTextColor(Color.WHITE);
                        data.put("Cancer Test History","No");
                        Yes.setBackgroundColor(Color.WHITE);
                        Yes.setTextColor(Color.BLACK);
                        SignUp(data.get("Email").toString(),"");
                        ChangeView(10);


                    }
                });

                break;
            case 10:
                viewcount=i;
                linearLayout.removeAllViews();
                titleText.setText(titles[i]);
                Button oky = new Button(this);
                oky.setText("Okey");
                oky.setBackgroundResource(R.drawable.shapebtn);
                oky.setLeft(16);

                oky.setLayoutParams(params);
                linearLayout.addView(oky);
                oky.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(NewUser.this,Login.class));

                    }
                });




    }









    }
        private void SignUp(String email, String pass) {

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Users").document("Patients").collection("List").document(email).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(NewUser.this, "registered", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewUser.this, "registeration error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        ChangeView(viewcount-1);
        if(viewcount==0){
            startActivity(new Intent(NewUser.this,MainActivity.class));
        }
    }
    public void date(){
        com.fourmob.datetimepicker.date.DatePickerDialog datePaickerDialog= com.fourmob.datetimepicker.date.DatePickerDialog.newInstance(NewUser.this,2017,1,1);
        datePaickerDialog.setYearRange(1980,2036);
        datePaickerDialog.setCancelable(true);
        datePaickerDialog.setVibrate(true);
        datePaickerDialog.setCloseOnSingleTapDay(true);
        datePaickerDialog.show(getSupportFragmentManager(),"");
    }


    @Override
    public void onDateSet(com.fourmob.datetimepicker.date.DatePickerDialog datePickerDialog, int year, int month, int day) {
        dob=day+"/"+month+"/"+year;
        date.setText(dob);

    }
}
