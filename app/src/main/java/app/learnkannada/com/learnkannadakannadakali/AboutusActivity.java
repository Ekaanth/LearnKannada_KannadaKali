package app.learnkannada.com.learnkannadakannadakali;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import constants.Constants;

public class AboutusActivity extends AppCompatActivity {

    private TextView connectHere;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
                                StringBuilder body = new StringBuilder();
                                body.append("Hello Team HithAM, \n \n");
                                body.append("< Your Text Here >\n\n");
                                body.append("\n Regards, \n");
                                body.append(getResources().getString(R.string.KANNADA_KALI_USER));
                                String company[] = {Constants.HITHAM_EMAIL};
                                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
                                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.WANTS_TO_CONTACT));
                                intent.putExtra(Intent.EXTRA_EMAIL, company);
                                intent.putExtra(Intent.EXTRA_TEXT, body.toString());
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .setIcon(R.drawable.logo)
                        .setCancelable(true)
                        .create().show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AboutusActivity.this, ChooseCourseActivity.class));
        finish();
    }
}
