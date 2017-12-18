package app.learnkannada.com.learnkannadakannadakali;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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

public class SplashScreenActivity extends AppCompatActivity {

    private static final int WELCOME_DURATION = 7000;
    private ImageView hitham;
    private TextView splashText;
    private KenBurnsView kenBurnsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen_);

        //flagGif = (GIFView) findViewById(R.id.welcomeFlagID);

        splashText = (TextView)findViewById(R.id.welcomeID);
        hitham = (ImageView) findViewById(R.id.logoID);


        //This code is to increase initiate and increase the duration of the effect.
        //setAnimation();
        kenBurnsView = (KenBurnsView)findViewById(R.id.kbimageID);
        kenBurnsView.setImageResource(R.drawable.splashcollage);
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

//   private void setAnimation() {
//        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcomeID), "scaleX", 5.0F, 1.0F);
//        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//        scaleXAnimation.setDuration(1200);
//        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcomeID), "scaleY", 5.0F, 1.0F);
//        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//        scaleYAnimation.setDuration(1200);
//        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcomeID), "alpha", 0.0F, 1.0F);
//        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//        alphaAnimation.setDuration(1200);
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
//        animatorSet.setStartDelay(500);
//        animatorSet.start();
//    }
}
