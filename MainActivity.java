package com.example.jasonpather.bigtunes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Category> masterCategories = new ArrayList<>();
    private Random rng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (masterCategories.isEmpty()) {
            initialise();
        }
    }

    private void initialise() {
        masterCategories.addAll(masterCategoriesText.stream().map(x -> new Category(x)).collect(Collectors.toList()));
        rng = new Random();
    }

    public void nextCategory(View view) {
        if (finished()) {
            restart();
        }
        else
        {
            Category nextCategory = getRandomCategory();
            TextView textView = findViewById(R.id.categoryText);
            textView.setText(nextCategory.getText());
        }
    }

    public void editCategories(View view) {
        Intent intent = new Intent(this, EditCategoriesActivity.class);
        startActivity(intent);
    }

    private boolean finished() {
        return !(masterCategories.stream().filter(x -> !x.isUsed()).count() > 0);
    }

    private void restart() {
        masterCategories.stream().forEach(x -> x.setUsed(false));
        rng = new Random();
        TextView textView = findViewById(R.id.categoryText);
        textView.setText("That's all the categories. Starting again by hitting NEXT.");
    }

    private Category getRandomCategory() {

        boolean chosen = false;
        Category nextCategory = null;

        while (!chosen) {
            int randomIndex = rng.nextInt(masterCategories.size());
            Category category = masterCategories.get(randomIndex);
            if (!category.isUsed()) {
                chosen = true;
                category.setUsed(true);
                nextCategory = category;
            }
        }

        return nextCategory;
    }

    private List<String> masterCategoriesText = new ArrayList<>(
            Arrays.asList("Song about killing someone",
                    "Deceased artist/band member",
                    "Song about drugs",
                    "Song by a white rapper",
                    "Instrument which is not typically found in the style of music",
                    "Metal song Dylan and Jason don’t know",
                    "Person/Artist with an alias name",
                    "Band/Artist not from UK/North America",
                    "Song that is 35 years old (at least)",
                    "Song that reminds you of a specific time",
                    "Songs about smoking/smoke",
                    "Homegrown",
                    "Song about/with a colour",
                    "Controversial/trouble with the law (artist or song)",
                    "Solo artist with band",
                    "Song from artist you have seen live",
                    "Song you like that you haven’t listened to in at least 2 years",
                    "Song from an artist you likes first album",
                    "Song that reminds you of a person you know/knew",
                    "Cover better than original",
                    "Instrumental",
                    "Song you first heard this year",
                    "Sad song",
                    "A song you can play on any instrument",
                    "Song that has a line that is funny/clever",
                    "Rap/jazz song Bede won't know",
                    "Guilty pleasure",
                    "A song with multiple genres",
                    "The only song you know/like by certain artist you like",
                    "Song no one else in the room would know",
                    "Song that got you into a certain style of music/band",
                    "Song you recall from tripping",
                    "A favourite song from your college years",
                    "Song that is in a movie, game or tv show",
                    "A song that is fast paced for its genre",
                    "Song you’ve never played in song rotation before... EVER!",
                    "Song you wish you composed",
                    "Song thats helped you through a rough time",
                    "Song that your parents used to listen to that you can remember",
                    "Scream + singing",
                    "Pre 2000 rap",
                    "Song from childhood (pre college)",
                    "Song about money/being rich",
                    "Song about no money/being poor",
                    "Song that turns your mood around",
                    "Song that you warmed up to (didn't like at first)",
                    "Free choice",
                    "Grime",
                    "Song that has a good build/climax",
                    "A song that is not on Spotify",
                    "A cool video"
            ));
}
