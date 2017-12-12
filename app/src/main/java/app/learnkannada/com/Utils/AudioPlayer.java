package app.learnkannada.com.Utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

/**
 * Created by raggitha on 01-Dec-17.
 */

public class AudioPlayer {
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public static void playAudio(Context context, String name) {
        if(mediaPlayer!= null)
            mediaPlayer.reset();

        Integer id = context.getResources().getIdentifier(name.toLowerCase(),"raw",context.getPackageName());
        try {
            mediaPlayer = MediaPlayer.create(context,id);
        } catch (Exception e) {
            Toast.makeText(context, name , Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(mp!=null)
                mp.stop();
                mp.release();
                //mp = null;
                mediaPlayer = null;
               // Toast.makeText(context, "killed" , Toast.LENGTH_LONG).show();
            }
        });
    }

}