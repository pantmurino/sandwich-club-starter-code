package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = "Sandwich.JsonUtils";

    private static final String NAME_JSON = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";
    private static final String DESCRIPTION = "description";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject sandwichJson = new JSONObject(json);

            sandwich.setPlaceOfOrigin(sandwichJson.getString(PLACE_OF_ORIGIN));
            sandwich.setImage(sandwichJson.getString(IMAGE));
            sandwich.setDescription(sandwichJson.getString(DESCRIPTION));

            List<String> ingredients = new ArrayList<>();
            JSONArray ingredientsJson = sandwichJson.getJSONArray(INGREDIENTS);
            for (int i=0; i < ingredientsJson.length(); i++) {
                ingredients.add(ingredientsJson.getString(i));
            }
            sandwich.setIngredients(ingredients);

            JSONObject sandwichNameJson = sandwichJson.getJSONObject(NAME_JSON);
            sandwich.setMainName(sandwichNameJson.getString(MAIN_NAME));

            List<String> alsoKnownAs = new ArrayList<>();
            JSONArray alsoKnownAsJson = sandwichNameJson.getJSONArray(ALSO_KNOWN_AS);
            for (int i=0; i<alsoKnownAsJson.length(); i++) {
                alsoKnownAs.add(alsoKnownAsJson.getString(i));
            }
            sandwich.setAlsoKnownAs(alsoKnownAs);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
