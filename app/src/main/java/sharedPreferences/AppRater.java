package sharedPreferences;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import app.learnkannada.com.learnkannadakannadakali.R;

/**
 * Created by vaam on 01-12-2017.
 */

public class AppRater {
    private final static String APP_PNAME = "app.learnkannada.com.learnkannadakannadakali";

    private final static int DAYS_UNTIL_PROMPT = 3;
    private final static int LAUNCHES_UNTIL_PROMPT = 7;

    private static long launch_count = 0, date_firstLaunch = 0;
    private static SharedPreferences prefs;

    public static void app_launched(Context mContext) {
        prefs = mContext.getSharedPreferences("apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) { return ; }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
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
        View view = View.inflate(mContext, R.layout.rateapp_layout, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        Button neverAgainButton = (Button) view.findViewById(R.id.neverAskButtonID);
        Button remindMeLater = (Button) view.findViewById(R.id.remindMeLaterID);
        Button rateNowID = (Button) view.findViewById(R.id.rateNowID);

        neverAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });

        remindMeLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putLong("launch_count",0);
                date_firstLaunch = System.currentTimeMillis();
                editor.putLong("date_firstlaunch", date_firstLaunch);
                editor.commit();
                dialog.dismiss();
            }
        });

        rateNowID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
                Toast.makeText(mContext,"Please review us on Play-store",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }
}
