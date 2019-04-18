package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "Sandwich.DetailActivity";

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView originTextView;
    private TextView originLabelTextView;
    private TextView alsoKnownAsTextView;
    private TextView alsoKnownLabelTextView;
    private TextView ingredientsTextView;
    private TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        originTextView = findViewById(R.id.origin_tv);
        originLabelTextView = findViewById(R.id.origin_label_tv);
        alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        alsoKnownLabelTextView = findViewById(R.id.also_known_label_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        descriptionTextView = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        descriptionTextView.setText(sandwich.getDescription());

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin != null && !placeOfOrigin.equals("")) {
            originTextView.setText(sandwich.getPlaceOfOrigin());

            originTextView.setVisibility(View.VISIBLE);
            originLabelTextView.setVisibility(View.VISIBLE);
        } else {
            originTextView.setVisibility(View.INVISIBLE);
            originLabelTextView.setVisibility(View.INVISIBLE);
        }

        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if (!alsoKnownAsList.isEmpty()) {
            alsoKnownAsTextView.setText("");
            for (int i=0; i<alsoKnownAsList.size(); i++) {
                if (i != 0) {
                    alsoKnownAsTextView.append(", ");
                }
                alsoKnownAsTextView.append(alsoKnownAsList.get(i));
            }

            alsoKnownAsTextView.setVisibility(View.VISIBLE);
            alsoKnownLabelTextView.setVisibility(View.VISIBLE);
        } else {
            alsoKnownAsTextView.setVisibility(View.INVISIBLE);
            alsoKnownLabelTextView.setVisibility(View.INVISIBLE);
        }

        String ingredients = new String();
        for (String ingredient : sandwich.getIngredients()) {
            ingredients += ingredient + "\n";
        }
        ingredientsTextView.setText(ingredients);
    }
}
