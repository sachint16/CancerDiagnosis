package com.example.pawan_pc.cancerdiaganosis;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.icu.text.Replaceable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RetryPolicy;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Util;

public class Home extends AppCompatActivity {
    BoomMenuButton boomMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        boomMenuButton=(BoomMenuButton)findViewById(R.id.bmb);
        int[] images={R.drawable.analysis,R.drawable.emerg,R.drawable.analyses,R.drawable.profile,R.drawable.setting,R.drawable.search,R.drawable.setting};
        String[] texts={"Diagnosis","Emergency","Report","profile","Settings"};
        for (int i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            final TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                    .normalImageRes(images[i])
                    .normalText(texts[i])
                    .imageRect(new Rect(Util.dp2px(20), Util.dp2px(20), Util.dp2px(60), Util.dp2px(60)))
                    .normalColor(Color.WHITE)
                    .normalTextColor(Color.BLACK)
                    .textPadding(new Rect(0,12,0,0))
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            switch (index){
                                case 0:
                                    startActivity(new Intent(Home.this,Diagnosis.class));
                                    break;
                                case 1:
                                    startActivity(new Intent(Home.this,Emengency.class));
                                    break;
                                case 2:
                                    startActivity(new Intent(Home.this,Report.class));
                                    break;
                                case 3:
                                    startActivity(new Intent(Home.this, profile.class));
                                    break;
                                case 4:
                                    startActivity(new Intent(Home.this,Settings.class));
                                    break;
                            }
                        }
                    })
                    ;
            boomMenuButton.addBuilder(builder);
        }
    }
    boolean doubleBackToExitPressedOnce=false;


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
            System.exit(0);        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
