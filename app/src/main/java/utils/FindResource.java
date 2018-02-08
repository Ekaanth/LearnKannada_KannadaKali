package utils;

import android.content.Context;

/**
 * Created by vaam on 07-01-2018.
 */

public class FindResource {
    //method checks if the resource with string supplied is present in raw directory
    public static boolean rawResourceAvailable(Context context, String spokenStringEx) {
        Integer id = context.getResources().getIdentifier(spokenStringEx.toLowerCase(), "raw", context.getPackageName());
        if (id > 0)
            return true;
        else
            return false;
    }
}
