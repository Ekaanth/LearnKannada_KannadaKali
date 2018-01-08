package app.learnkannada.com.learnkannadakannadakali;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.flaviofaria.kenburnsview.KenBurnsView;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int WELCOME_DURATION = 4000;
    private ImageView hitham;
    private ImageView splashText;
    private KenBurnsView kenBurnsView;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        this.finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen_);

        //flagGif = (GIFView) findViewById(R.id.welcomeFlagID);

        splashText = (ImageView)findViewById(R.id.welcomeID);
        hitham = (ImageView) findViewById(R.id.logoID);


        //This code is to increase initiate and increase the duration of the effect.
        //setAnimation();
        kenBurnsView = (KenBurnsView)findViewById(R.id.kbimageID);
        kenBurnsView.setImageResource(R.drawable.karnataka_collage);
        //end of kbview code

        Animation myTransition = AnimationUtils.loadAnimation(this,R.anim.my_transition);
        hitham.setAnimation(myTransition);
        splashText.setAnimation(myTransition);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, ChooseCourseActivity.class));
                finish();
            }
        }, WELCOME_DURATION);


    }
}
