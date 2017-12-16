package app.learnkannada.com.learnkannadakannadakali;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int WELCOME_DURATION = 5000;
    private ImageView hitham;
    private TextView splashText;
    private KenBurnsView kenBurnsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen_);

        //flagGif = (GIFView) findViewById(R.id.welcomeFlagID);
        //compile 'com.flaviofaria:kenburnsview:1.0.6'
        //add above dependency to the build
        splashText = (TextView)findViewById(R.id.welcomeID);
        hitham = (ImageView) findViewById(R.id.logoID);
        kenBurnsView = (KenBurnsView)findViewById(R.id.kbimageID);

        //This code is to increase initiate and increase the duration of the effect.
        AccelerateDecelerateInterpolator acc_dec = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(5000,acc_dec);
        kenBurnsView.setTransitionGenerator(generator);
        //end of kbview code

        Animation myTransition = AnimationUtils.loadAnimation(this,R.anim.my_transition);
        hitham.setAnimation(myTransition);
        splashText.setAnimation(myTransition);


        //getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, ChooseCourseActivity.class));
                finish();
            }
        }, WELCOME_DURATION);
    }
}
