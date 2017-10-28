package app.learnkannada.com.learnkannadakannadakali;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

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
        
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        String listValues[] = new String[0];
        switch(category) {
            case "enquiry":
                listValues = new String[]{"I", "You", "He", "She"};
                break;
            case "numbers":
                listValues = new String[]{"One", "Two", "Three", "Four","Five","Six","Seven","Eight","Nine","Ten"};
                break;
            case "beginner":
                listValues = new String []{"I","You","He","She","We","They","My","Mine","Our","Your","Ours","Yours (Singular)","Yours (Plural)","This","That"};
                break;
            default:
                listValues = new String[]{"Yes","No"};
                break;
        }
        
        for(int i=0; i<listValues.length; i++)
            input.add(listValues[i]);

        adapter = new ListViewAdapter(getApplicationContext(),input, category);
        recyclerView.setAdapter(adapter);
    }
}
