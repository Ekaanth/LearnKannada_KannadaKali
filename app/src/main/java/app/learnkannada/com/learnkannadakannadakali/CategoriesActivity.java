package app.learnkannada.com.learnkannadakannadakali;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.ListViewAdapter;
import constants.Constants;

public class CategoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ListViewAdapter adapter;

    private SearchView searchView;
    private AlertDialog.Builder builder;

    private String CURRENT_VERSION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);
        builder = new AlertDialog.Builder(this);

        try {
            PackageInfo packageInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            CURRENT_VERSION = "v" + packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(Constants.CATEGORIES);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        List<String> kanInput = new ArrayList<>();
        String listValues[] = null;
        String listValueKan[] = null;

        String from = getIntent().getStringExtra(Constants.FROM);

        if (from.equals(Constants.WORDS)) {
            listValues = getResources().getStringArray(R.array.flexi_words_array);
            listValueKan = getResources().getStringArray(R.array.flexi_words_kanArray);
            adapter = new ListViewAdapter(getApplicationContext(), input, Constants.FLEXI_WORDS, kanInput);
        } else {
            listValues = getResources().getStringArray(R.array.flexi_conversations_array);
            listValueKan = getResources().getStringArray(R.array.flexi_conversations_kanArray);
            adapter = new ListViewAdapter(getApplicationContext(), input, Constants.FLEXI_CONVERSATIONS, kanInput);
        }

        if (listValueKan.length == listValues.length)
            for (int i = 0; i < listValues.length; i++) {
                input.add(listValues[i]);
                kanInput.add(listValueKan[i]);
            }

        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_conversations, menu);
        MenuItem item = menu.findItem(R.id.searchID);

        searchView = (SearchView) MenuItemCompat.getActionView(item);
        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        else if(item.getItemId() == R.id.suggestID)
        {
            View view = getLayoutInflater().inflate(R.layout.layout_suggestions,null);
            final EditText suggestion1 = (EditText) view.findViewById(R.id.suggestion1ID);
            final EditText suggestion2 = (EditText) view.findViewById(R.id.suggestion2ID);
            final EditText suggestion3 = (EditText) view.findViewById(R.id.suggestion3ID);
            final String mimeType = "text/plain";
            builder.setView(view)
                    .setTitle("Suggest Conversations")
                    .setPositiveButton("Suggest", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String suggestionOne = suggestion1.getText().toString();
                            String suggestionTwo = suggestion2.getText().toString();
                            String suggestionThree = suggestion3.getText().toString();
                            if(suggestionOne.isEmpty()&&suggestionTwo.isEmpty()&&suggestionThree.isEmpty())
                            {
                                Toast.makeText(getApplicationContext(),"No suggestions entered!",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            StringBuilder body = new StringBuilder();
                            body.append("Hello Team HithAM, \n \n");
                            body.append("I think it would be helpful if you can add conversations regarding below contexts.\n\n");
                            body.append(Constants.SINGLE_LINE_STAR);
                            body.append(suggestionOne+ "\n"
                                    + suggestionTwo + "\n"
                                    + suggestionThree + "\n");
                            body.append(Constants.SINGLE_LINE_STAR);
                            body.append("\n Regards, \n");
                            body.append("Kannada Kali User");
                            body.append("\n\nSent from my Kannada Kali " + CURRENT_VERSION);
                            String company[] = {Constants.HITHAM_EMAIL};
                            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Kannada Kali - Suggest Conversations");
                            intent.putExtra(Intent.EXTRA_EMAIL, company);
                            intent.putExtra(Intent.EXTRA_TEXT, body.toString());
                            if(intent.resolveActivity(getPackageManager())!=null)
                            {
                                Toast.makeText(getApplicationContext(),R.string.FRAMING_EMAIL,Toast.LENGTH_LONG).show();
                                startActivity(intent);
                            }
                        }
                    })
                    .setNegativeButton("Cancel",null)
                    .create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CategoriesActivity.this, FlexiCourseHomeActivity.class));
        finish();
    }
}
