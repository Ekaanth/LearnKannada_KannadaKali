package app.learnkannada.com.learnkannadakannadakali;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class StagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stages);

        Intent i= getIntent();
        String stage = i.getStringExtra("position");

        Toast.makeText(getApplicationContext(),stage, Toast.LENGTH_SHORT).show();

    }
}
