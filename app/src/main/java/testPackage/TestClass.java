package testPackage;

import java.util.ArrayList;

import utils.StringArrayUtils;

/**
 * Created by vaam on 1/17/2018.
 */

public class TestClass {

    public static void main(String[] args)
    {
        String a[] = {"a","b","c"};
        String b[] = {"d","e","f"};
        String c[] = {"g","h","i"};

        String[][] arrays = {a,b,c};

        ArrayList<String> all = StringArrayUtils.appendArrays(arrays);

        for(int i=0; i<all.size(); i++)
            System.out.println(all.get(i));

        System.out.println(all.contains("g"));
    }

}
