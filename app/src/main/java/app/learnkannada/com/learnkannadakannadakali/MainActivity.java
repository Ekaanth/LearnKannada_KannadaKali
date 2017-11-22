package app.learnkannada.com.learnkannadakannadakali;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_home);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        List<String> kanInput = new ArrayList<>();

        String[] listValues = getResources().getStringArray(R.array.flexi_words_array);

        for (String listValue : listValues) {
            input.add(listValue);
            kanInput.add("Dummy");
        }
        adapter = new ListViewAdapter(getApplicationContext(),input, "flexiWords", kanInput);
        recyclerView.setAdapter(adapter);

    }

    private void longToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}
