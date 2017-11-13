package app.learnkannada.com.learnkannadakannadakali;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

public class CourseHomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_home);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);
//        SearchView sv = (SearchView) findViewById(R.id.searchViewID);
//        sv.setVisibility(View.INVISIBLE);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<String> input = new ArrayList<>();
        List<String> kanInput = new ArrayList<>();

        String listValues[]=null;

        Resources res = getResources();

        listValues = res.getStringArray(R.array.days_array);


        for (String listValue : listValues) {
            input.add(listValue);
            kanInput.add("Dummy");
        }

        adapter = new ListViewAdapter(getApplicationContext(),input, "daysCourse", kanInput);
        recyclerView.setAdapter(adapter);

    }
}
