package utils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by vaam on 1/17/2018.
 */


public class StringArrayUtils {

    /**
     * The method is used to append arrays to single List and return.
     * @param listOfArrays String array of arrays to be combined.
     */
    public static ArrayList<String> appendArrays(String[][] listOfArrays)
    {
        ArrayList<String> appendedArray = new ArrayList<>();

        for(int i=0; i<listOfArrays.length; i++)
        {
            Collections.addAll(appendedArray,listOfArrays[i]);
        }

        return appendedArray;
    }
}
