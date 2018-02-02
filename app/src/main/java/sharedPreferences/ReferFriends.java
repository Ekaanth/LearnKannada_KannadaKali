package sharedPreferences;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import app.learnkannada.com.learnkannadakannadakali.R;

/**
 * Created by vaam on 06-12-2017.
 */

public class ReferFriends {

    private final static int DAYS_UNTIL_PROMPT = 3;
    private final static int LAUNCHES_UNTIL_PROMPT = 11;

    private static long launch_count = 0, date_firstLaunch = 0;
    private static SharedPreferences prefs;

    public static void app_launched(Context mContext) {
        prefs = mContext.getSharedPreferences("apprater_refer", 0);
        if (prefs.getBoolean("dontshowagain_refer", false)) { return ; }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        launch_count = prefs.getLong("launch_count_refer", 0) + 1;
        editor.putLong("launch_count_refer", launch_count);

        // Get date of first launch
        date_firstLaunch = prefs.getLong("date_firstlaunch_refer", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch_refer", date_firstLaunch);
        }

        // Wait at least n days before opening dialog
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch +
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showRateDialog(mContext, editor);
            }
        }

        editor.commit();
    }

    public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.refer_friends_layout, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        Button neverAgainButton = (Button) view.findViewById(R.id.noID);
        Button remindMeLater = (Button) view.findViewById(R.id.laterID);
        Button rateNowID = (Button) view.findViewById(R.id.sureID);

        neverAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editor != null) {
                    editor.putBoolean("dontshowagain_refer", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });

        remindMeLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putLong("launch_count_refer",0);
                date_firstLaunch = System.currentTimeMillis();
                editor.putLong("date_firstlaunch_refer", date_firstLaunch);
                editor.commit();
                dialog.dismiss();
            }
        });

        rateNowID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, ("Learn \"Spoken Kannada\" in just 10 days!\n" +
                        "Use helpful and handy feature Instant Words Translation\n" +
                        "\n\nDownload Kannada Kali app now!\n" +
                        Uri.parse("http://play.google.com/store/apps/details?id=" + view.getContext().getApplicationContext().getPackageName())
                        + "\n\nTrailer here:\n" + Uri.parse("https://www.youtube.com/watch?v=dTOBnFx4Kvc")));
                sendIntent.setType("text/plain");
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                view.getContext().startActivity(sendIntent);
                dialog.dismiss();
            }
        });

    }
}
