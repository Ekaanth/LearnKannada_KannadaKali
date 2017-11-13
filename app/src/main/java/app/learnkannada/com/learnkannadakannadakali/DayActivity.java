package app.learnkannada.com.learnkannadakannadakali;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DayActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_home);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        String day = intent.getStringExtra("position");

        List<String> input = new ArrayList<>();
        List<String> kanInput = new ArrayList<>();

        String listValues[]=null;

        Resources res = getResources();

        listValues = res.getStringArray(R.array.day_1);


        for (String listValue : listValues) {
            input.add(listValue);
            kanInput.add("Dummy");
        }

        adapter = new ListViewAdapter(getApplicationContext(),input, "stageCourse", kanInput);
        recyclerView.setAdapter(adapter);

    }
}
