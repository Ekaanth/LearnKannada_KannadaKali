package app.learnkannada.com.learnkannadakannadakali;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen_Activity extends AppCompatActivity {

    private static final int WELCOME_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen_Activity.this, ChooseCourseActivity.class));
                finish();
            }
        }, WELCOME_DURATION);
    }
}
