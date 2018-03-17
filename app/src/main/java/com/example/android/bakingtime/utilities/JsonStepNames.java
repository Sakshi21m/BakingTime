package com.example.android.bakingtime.utilities;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakshimajmudar on 26/02/18.
 */

public class JsonStepNames {

    public  static List<ArrayList> getSimpleStringsFromJson(String pmJsonStr, String idRecipe)
            throws JSONException {

        List<ArrayList> data = new ArrayList<>();
        if(pmJsonStr==null)
        {
            System.out.println("sakshi imp pms is null ");

        }
        else{
        JSONArray pms = new JSONArray(pmJsonStr);

        System.out.println("sakshi "+pms.length());
        for (int i=0;i<pms.length();i++) {
            String id = pms.getJSONObject(i).getString("id");
            if (id.equals(idRecipe)) {
                JSONArray results = pms.getJSONObject(i).getJSONArray("steps");
                int pmCount = results.length();


                for (int j = 0; j < pmCount; j++) {

                    if (!results.isNull(j)) {

                        String stepId;
                        String stepName;
                        String description;
                        String videoUrl;
                        String thumbnailUrl;
                        ArrayList<String> innerArray = new ArrayList<>();
                        stepId = results.getJSONObject(j).getString("id");
                        stepName = results.getJSONObject(j).getString("shortDescription");
                        description = results.getJSONObject(j).getString("description");
                        videoUrl = results.getJSONObject(j).getString("videoURL");
                        thumbnailUrl = results.getJSONObject(j).getString("thumbnailURL");
                        innerArray.add(0, stepId);
                        innerArray.add(1, stepName);
                        innerArray.add(2, description);
                        innerArray.add(3, videoUrl);
                        innerArray.add(4, thumbnailUrl);
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
