package Utils;

import android.content.Context;

import app.learnkannada.com.learnkannadakannadakali.ChooseCourseActivity;

/**
 * Created by vaam on 07-01-2018.
 */

public class FindResource {
    //method checks if the resource with string supplied is present in raw directory
    public static boolean resourceAvailable(Context context, String spokenStringEx) {
        Integer id = context.getResources().getIdentifier(spokenStringEx.toLowerCase(), "raw", context.getPackageName());
        if (id > 0)
            return true;
        else
            return false;
    }
}
