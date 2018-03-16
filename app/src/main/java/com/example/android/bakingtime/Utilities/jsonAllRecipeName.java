package com.example.android.bakingtime.Utilities;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakshimajmudar on 25/02/18.
 */

public class jsonAllRecipeName {

    public  static List<ArrayList> getSimplePMStringsFromJson(String pmJsonStr)
            throws JSONException {


        List<ArrayList> pmData = new ArrayList<>();

        JSONArray pms = new JSONArray(pmJsonStr);

        for (int i=0;i<pms.length();i++){

            String name;
            String id;
            ArrayList<String> innerArray2 = new ArrayList<>();
            name = pms.getJSONObject(i).getString("name");
            id= pms.getJSONObject(i).getString("id");
            innerArray2.add(0,id);
            innerArray2.add(1,name);
            pmData.add(innerArray2);

        }

        return pmData;
    }

}
