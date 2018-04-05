package app.learnkannada.com.learnkannadakannadakali;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import sharedPreferences.QuizPrefs;

import static constants.Constants.LEVEL;
import static constants.Constants.LEVEL_DIFF;
import static constants.Constants.LEVEL_EASY;
import static constants.Constants.LEVEL_INTERMED;

public class QuizHomeActivity extends AppCompatActivity {

    private static final int EASY_SETS_AVAILABLE = 10;
    private static final int INTERMED_SETS_AVAILABLE = 10;
    private static final int DIFF_SETS_AVAILABLE = 10;
    private static boolean levelEasyCompleted = false, levelIntermedCompleted = false, levelDiffCompleted = false;
    private CardView easyCard, intermedCard, diffCard;
    private ProgressBar easyProgressBar, intermedProgressBar, diffProgressBar;
    private AlertDialog.Builder builder;
    private TextView easyProgressTextView, intermedProgressTextView, diffProgressTextView;
    private SharedPreferences levelPrefs;

    private int easySetsCompleted = 0, intermedSetsCompleted = 0, diffSetsCompleted = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_home);

        easyCard = (CardView) findViewById(R.id.easyCardID);
        intermedCard = (CardView) findViewById(R.id.intermedCardID);
        diffCard = (CardView) findViewById(R.id.diffCardID);
        easyProgressBar = (ProgressBar) findViewById(R.id.easyProgressBarID);
        easyProgressTextView = (TextView) findViewById(R.id.easyProgressTextID);
        intermedProgressBar = (ProgressBar) findViewById(R.id.intermedProgressBarID);
        intermedProgressTextView = (TextView) findViewById(R.id.intermedProgressTextID);
        diffProgressBar = (ProgressBar) findViewById(R.id.diffProgressBarID);
        diffProgressTextView = (TextView) findViewById(R.id.diffProgressTextID);
        builder = new AlertDialog.Builder(this);

        //TODO below prefs is used only to clear once easyset is completed. need to remove this before pushing to production
        levelPrefs = getSharedPreferences(QuizPrefs.PREF_LEVEL_EASY,MODE_PRIVATE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Quizzes");
        }

        loadProgress();

        //TODO add jobdispatcher to remind user to open app to try quiz or learn

        levelEasyCompleted = (easySetsCompleted == EASY_SETS_AVAILABLE);
        levelIntermedCompleted = (intermedSetsCompleted == INTERMED_SETS_AVAILABLE);
        levelDiffCompleted = (diffSetsCompleted == DIFF_SETS_AVAILABLE);

        //TODO if user completes any quiz, still allow him to try those sets again but don't update prefs and views on QuizHome.
        easyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (levelEasyCompleted) {
                    Toast.makeText(getApplicationContext(), "All sets completed! Clearing it and taking you inside", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = levelPrefs.edit();
                    editor.putInt("set-key", 0);
                    editor.apply();
                } else
                    openQuizLevel(LEVEL_EASY);
            }
        });

        intermedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (levelEasyCompleted)
                    openQuizLevel(LEVEL_INTERMED);
                else
                    showWarningAlert(LEVEL_EASY);
            }
        });

        diffCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!levelEasyCompleted)
                    showWarningAlert(LEVEL_EASY);
                else if (!levelIntermedCompleted)
                    showWarningAlert(LEVEL_INTERMED);
                else
                    openQuizLevel(LEVEL_DIFF);
            }
        });
    }

    private void showWarningAlert(final String level) {
        builder.setTitle("uh-oh!")
                .setMessage("You can't get in!")
                .setPositiveButton("Finish " + level, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openQuizLevel(level);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create().show();
    }

    private void openQuizLevel(String level) {
        Intent intent = new Intent(QuizHomeActivity.this, QuizActivity.class);
        intent.putExtra(LEVEL, level);
        startActivity(intent);
    }

    private void loadProgress() {
        QuizPrefs quizPrefs = new QuizPrefs(this);

        int quizProgress;

        easySetsCompleted = quizPrefs.getLevelProgress(LEVEL_EASY);
        quizProgress = getQuizProgressPercentage(LEVEL_EASY, easySetsCompleted);
        updateProgressViews(LEVEL_EASY, quizProgress);

        intermedSetsCompleted = quizPrefs.getLevelProgress(LEVEL_INTERMED);
        quizProgress = getQuizProgressPercentage(LEVEL_INTERMED, intermedSetsCompleted);
        updateProgressViews(LEVEL_INTERMED, quizProgress);

        diffSetsCompleted = quizPrefs.getLevelProgress(LEVEL_DIFF);
        quizProgress = getQuizProgressPercentage(LEVEL_DIFF, diffSetsCompleted);
        updateProgressViews(LEVEL_DIFF, quizProgress);

    }

    private void updateProgressViews(String level, int quizProgress) {
        String progressText = quizProgress + "% completed";
        ;
        switch (level) {
            case LEVEL_EASY:
                easyProgressBar.setProgress(quizProgress);
                easyProgressTextView.setText(progressText);
                break;
            case LEVEL_INTERMED:
                intermedProgressBar.setProgress(quizProgress);
                intermedProgressTextView.setText(progressText);
                break;
            case LEVEL_DIFF:
                diffProgressBar.setProgress(quizProgress);
                diffProgressTextView.setText(progressText);
                break;
            default:
                break;
        }
    }

    private int getQuizProgressPercentage(String level, int setsCompleted) {
        int totalNumberOfSetsAvailable = 0, percentagePerQuiz = 0;
        switch (level) {
            case LEVEL_EASY:
                totalNumberOfSetsAvailable = EASY_SETS_AVAILABLE;
                break;
            case LEVEL_INTERMED:
                totalNumberOfSetsAvailable = INTERMED_SETS_AVAILABLE;
                break;
            case LEVEL_DIFF:
                totalNumberOfSetsAvailable = DIFF_SETS_AVAILABLE;
                break;
            default:
                break;
        }
        percentagePerQuiz = 100 / totalNumberOfSetsAvailable;
        if (totalNumberOfSetsAvailable == setsCompleted)
            return 100;
        else
            return percentagePerQuiz * setsCompleted;
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadProgress();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(QuizHomeActivity.this, ChooseCourseActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
