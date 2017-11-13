package app.learnkannada.com.learnkannadakannadakali;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        List<String> kanInput = new ArrayList<>();
        
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        Resources res = getResources();
        String[] listValues = null, listValuesInKan = null;
            switch(category) {
                case "enquiry":
                    break;
                case "numbers":
                    listValues = res.getStringArray(R.array.eNumbers_array);
                    listValuesInKan = res.getStringArray(R.array.kNumbers_array);
                    break;
                case "beginner":
                    break;
                default:
                    break;
            }

        for(int i=0; i<listValues.length; i++)
        {
            input.add(listValues[i]);
            kanInput.add(listValuesInKan[i]);
        }

        adapter = new ListViewAdapter(getApplicationContext(),input, category, kanInput);
        recyclerView.setAdapter(adapter);
    }
}
