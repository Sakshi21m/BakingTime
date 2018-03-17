package com.example.android.bakingtime.utilities;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakshimajmudar on 28/02/18.
 */

public class JsonIngredientDetails  {

    public  static List<ArrayList> getSimpleStringsFromJson(String pmJsonStr, String idRecipe)
            throws JSONException {

        List<ArrayList> data = new ArrayList<>();
        if(pmJsonStr!=null){
        JSONArray pms = new JSONArray(pmJsonStr);
        for (int i=0;i<pms.length();i++) {

            String id = pms.getJSONObject(i).getString("id");
            if (id.equals(idRecipe)) {
                JSONArray results = pms.getJSONObject(i).getJSONArray("ingredients");
                int pmCount = results.length();

                for (int j = 0; j < pmCount; j++) {

                    if (!results.isNull(j)) {

                        String quantity;
                        String ingredient;
                        String measure;

                        ArrayList<String> innerArray = new ArrayList<>();

                        quantity = results.getJSONObject(j).getString("quantity");
                        measure = results.getJSONObject(j).getString("measure");
                        ingredient = results.getJSONObject(j).getString("ingredient");
                        innerArray.add(0, quantity);
                        innerArray.add(1, measure);
                        innerArray.add(2, ingredient);
                        data.add(innerArray);
                    }

                }
                break;
            }
        }
        }
        return data;
    }
}
