package com.example.pawan_pc.cancerdiaganosis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class InstantTips extends AppCompatActivity {
    String [] tips;
    ListView listView;
    TipsAdaptor arrayAdapter;
    ArrayList<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_tips);
        tips=new String[]{"Talk with your health care provider about what to do and how you will stay in contact in the event of a disaster. If you access your medical information through a patient portal, or website, be sure to have your account information and password handy. You might be able to stay in contact with your provider through electronic mail or secure messaging.",
        "Know your exact diagnosis, cancer stage, and any medications you take. If you are receiving chemotherapy or radiation, know where you are in your treatment cycle.",
                "Be aware that food and water may not be safe to consume after a disaster. The Center for Disease Control and Prevention provides safety tips on food and water use.",
                "Call 1-800-985-5990 or text TalkWithUs to 66746 to connect with a trained crisis counselor."
        };
        for(int i=0;i<tips.length;i++){
            list.add(tips[i]);
        }
        listView=(ListView)findViewById(R.id.tips);
        arrayAdapter=new TipsAdaptor(this,list);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

    }
}
