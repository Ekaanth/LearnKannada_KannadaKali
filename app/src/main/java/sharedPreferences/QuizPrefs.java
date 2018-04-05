package sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vaam on 4/5/2018.
 */

public class QuizPrefs {

    private static final String LEVEL_KEY = "level-key";
    private static final String SET_KEY = "set-key";

    public static final String PREF_LEVEL_EASY = "pref-level-easy";
    public static final String PREF_LEVEL_INTERMED = "pref-level-intermediate";
    public static final String PREF_LEVEL_DIFF = "pref-level-difficult";
    public static final String PREF_LEVEL = "pref-level";

    private SharedPreferences quizSharedPrefs;
    private Context context;

    public QuizPrefs(Context context){
        this.context = context;
    }



    public int getLevelProgress(String level)
    {
        String levelKey = PREF_LEVEL + level;
        quizSharedPrefs = context.getSharedPreferences(levelKey,Context.MODE_PRIVATE);
        int setsCompleted = quizSharedPrefs.getInt(SET_KEY,0);
        return setsCompleted;
    }

}
