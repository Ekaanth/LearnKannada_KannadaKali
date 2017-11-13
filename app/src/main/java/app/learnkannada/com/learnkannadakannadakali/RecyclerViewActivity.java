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
        String fromWhere = intent.getStringExtra("from");
        Resources res = getResources();
        String[] listValues = null, listValuesInKan = null;
        /*int listValues[] = new int[0];
        int listValuesInKan[] = new int[0];*/
        if(fromWhere.equals("flexi"))
        {
            switch(category) {
                case "enquiry":
                    //listValues = new String[]{"I", "You", "He", "She"};
                    //listValuesInKan = new String[] {"naanu","neenu","avanu","ivaLu"};
                    break;
                case "numbers":
                    listValues = res.getStringArray(R.array.eNumbers_array);
                    listValuesInKan = res.getStringArray(R.array.kNumbers_array);
                    break;
                case "beginner":

                /*listValues = new int []{R.string.i,R.string.you,R.string.he,R.string.she
                        ,R.string.we,R.string.they,R.string.my,R.string.mine,R.string.our,R.string.your,
                        R.string.ours,R.string.yours_Singular,R.string.yours_Plural,R.string.this_,R.string.that};
                listValuesInKan = new int []{R.string.ki,R.string.kyou,R.string.khe,R.string.kshe
                        ,R.string.kwe,R.string.kthey,R.string.kmy,R.string.kmine,R.string.kour,R.string.kyour,
                        R.string.kours,R.string.kyours_Singular,R.string.kyours_Plural,R.string.kthis_,R.string.kthat};*/
                    break;
                default:
                    //listValues = new String[]{"Yes","No"};
                    break;
            }
        }
        else
        {
            listValues = new String[] {"Day_1 ", "Day_2"};
            listValuesInKan = new String[] {"Dina_1 ", "Dina_2"};
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
