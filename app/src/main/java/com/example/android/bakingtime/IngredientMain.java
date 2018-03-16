package com.example.android.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sakshimajmudar on 27/02/18.
 */

public class IngredientMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients_fragment_container);

        if(savedInstanceState==null) {

            Intent intent = getIntent();
            String recipeId = intent.getStringExtra("id");
            String name = intent.getStringExtra("name");
            String jsonPMResponse = intent.getStringExtra("json");
            System.out.println("getting intent name "+name);


            setTitle(name);

            Bundle bundle = new Bundle();
            bundle.putString("id", recipeId);
            bundle.putString("name", name);
            bundle.putString("json", jsonPMResponse);


            IngredientFragment ingredientFragment = new IngredientFragment();
            ingredientFragment.setArguments(bundle);
            ingredientFragment.setters();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.fragment_ingredients_main, ingredientFragment).commit();



        }
    }

}
