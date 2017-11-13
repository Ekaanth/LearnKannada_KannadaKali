package app.learnkannada.com.learnkannadakannadakali;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView beginner, enquiry, numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beginner = (ImageView) findViewById(R.id.beginnerID);
        enquiry = (ImageView) findViewById(R.id.enquiryID);
        numbers = (ImageView) findViewById(R.id.numbersID);

        beginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RecyclerViewActivity.class);
                i.putExtra("category","beginner");
                startActivity(i);
            }
        });

        enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RecyclerViewActivity.class);
                i.putExtra("category","enquiry");
                i.putExtra("from","flexi");
                startActivity(i);
            }
        });

        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RecyclerViewActivity.class);
                i.putExtra("from","flexi");
                i.putExtra("category","numbers");
                startActivity(i);
            }
        });
    }

    private void longToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}
