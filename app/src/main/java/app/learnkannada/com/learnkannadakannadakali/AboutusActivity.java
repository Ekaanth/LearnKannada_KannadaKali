package app.learnkannada.com.learnkannadakannadakali;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class AboutusActivity extends AppCompatActivity {

    private TextView connectHere;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        connectHere = (TextView) findViewById(R.id.connectHereID);
        builder = new AlertDialog.Builder(this);

        connectHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle(R.string.contact_HithAM)
                        .setMessage("Are you sure to contact HithAM?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .setIcon(R.drawable.logo)
                        .setCancelable(true)
                        .create().show();
            }
        });
    }
}
