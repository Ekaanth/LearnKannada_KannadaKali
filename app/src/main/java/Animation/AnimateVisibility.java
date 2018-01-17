package Animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by vaam on 07-01-2018.
 */

public class AnimateVisibility {
    public static void animateInvisible(final View myView) {
// get the center for the clipping circle
        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

// get the initial radius for the clipping circle
        float initialRadius = (float) Math.hypot(cx, cy);

// create the animation (the final radius is zero)
        Animator anim =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);
        }

// make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
            }
        });

// start the animation
        anim.start();
    }

    public static void animateVisible(final View myView) {

// get the center for the clipping circle
        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

// get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

// create the animator for this view (the start radius is zero)
        Animator anim =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
        }

// make the view visible and start the animation
        myView.setVisibility(View.VISIBLE);
        anim.start();
    }
}
