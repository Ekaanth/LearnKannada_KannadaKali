package utils;

import android.content.Context;

/**
 * Created by vaam on 07-01-2018.
 */

public class FindResource {
    //method checks if the resource with string supplied is present in raw directory
    public static boolean rawResourceAvailable(Context context, String spokenStringEx) {
        spokenStringEx = processStringName(spokenStringEx);
        Integer id = context.getResources().getIdentifier(spokenStringEx.toLowerCase(), "raw", context.getPackageName());
        if (id > 0)
            return true;
        else
            return false;
    }

    public static String processStringName(String s) {
        switch (s) {
            case "do":
                return "do_";
            case "this":
                return "this_";
            case "Nya":
                return ("nya_");
            case "Cha":
                return ("cha_");
            case "Ta":
                return ("ta_");
            case "Tha":
                return ("tha_");
            case "Da":
                return ("da_");
            case "Na":
                return ("na_");
            case "Dha":
                return ("dha_");
            case "La":
                return ("la_");
            case "Sha":
                return ("sha_");
            case "For":
                return ("for_");
            default:
                return s;
        }

    }

    //method intoruced to fix audio player issue when numerics are searched by user
    public static boolean isNumber(String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }
}
