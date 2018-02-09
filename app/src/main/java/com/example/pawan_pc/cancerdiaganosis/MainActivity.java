package com.example.pawan_pc.cancerdiaganosis;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener, LocationListener {
    ImageView imageView;
    LinearLayout linearLayout;
    Button Login, reg, emerg;
    Animation fadeout;
    TextView text;
    TextView admin;

    @TargetApi(26)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        if(new PrefrenceManager(MainActivity.this).getLoginState()) {
            startActivity(new Intent(MainActivity.this,Home.class));
        }
        imageView = (ImageView) findViewById(R.id.startimage);
        TextView logoname=(TextView)findViewById(R.id.logoname);
        logoname.setText("iCANDET");
        Glide.with(MainActivity.this).asGif().load(R.drawable.logo).into(imageView);
        Animation fadein = AnimationUtils.loadAnimation(this, R.anim.slide);
        fadein.setAnimationListener(this);
        logoname.startAnimation(fadein);
        linearLayout = (LinearLayout) findViewById(R.id.LL);
        fadeout = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        linearLayout.startAnimation(fadeout);
        Login = (Button) findViewById(R.id.login);
        //text = (TextView) findViewById(R.id.text_main);
        admin=(TextView)findViewById(R.id.admin_panel);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Login_admin.class));

            }
        });
        Animation pop = AnimationUtils.loadAnimation(this, R.anim.pop);
        //text.startAnimation(pop);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });
        reg = (Button) findViewById(R.id.register);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NewUser.class));
            }
        });
        emerg = (Button) findViewById(R.id.emergencyBtn);
        emerg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Emengency.class));
            }
        });
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, this);
        savecurrentLocation();




    }

    private void savecurrentLocation() {
    }

    @Override
    public void onAnimationStart(Animation animation) {


    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //imageView.setAlpha((float) 0.3);

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Help");
        menu.add("exit");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onLocationChanged(Location location) {
        //Toast.makeText(MainActivity.this, "lat:"+location.getLatitude(), Toast.LENGTH_SHORT).show();
        String lat= String.valueOf(location.getLatitude());
        String longi= String.valueOf(location.getLongitude());
        new PrefrenceManager(this).setLocation(lat,longi);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
