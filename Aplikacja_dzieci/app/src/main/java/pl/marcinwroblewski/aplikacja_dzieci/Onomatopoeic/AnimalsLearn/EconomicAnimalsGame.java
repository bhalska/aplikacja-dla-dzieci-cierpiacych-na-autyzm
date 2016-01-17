package pl.marcinwroblewski.aplikacja_dzieci.Onomatopoeic.AnimalsLearn;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import pl.marcinwroblewski.aplikacja_dzieci.R;
import pl.marcinwroblewski.aplikacja_dzieci.Reward;
import pl.marcinwroblewski.aplikacja_dzieci.Settings.Settings;
import pl.marcinwroblewski.aplikacja_dzieci.Usable.Animations;
import pl.marcinwroblewski.aplikacja_dzieci.Usable.Utilities;


public class EconomicAnimalsGame extends Activity {

    private Drawable[] drawablesAnimals;
    private String[] animalsOnomatopoeic;
    private ImageView[] animals;
    private RelativeLayout cloudContainer;
    private int randomInt, goodChoice, badChoice;
    private Utilities utils, utils2;
    private int[] drawablesInts;
    private Random random;
    private TextView actionBarText, onomatopoeicWord;
    private Animation fadeIn;
    private Drawable randomizedDrawable;

    private Animations animsControler;
    private View[] animElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_economic_animals_game);


        getActionBar().hide();


        animsControler = new Animations(getApplicationContext());

        LinearLayout appContainer = (LinearLayout) findViewById(R.id.app_container);
        animElements = new View[appContainer.getChildCount()];
        for (int i = 0; i < animElements.length; i++) {
            animElements[i] = appContainer.getChildAt(i);
        }
        animsControler.startAnim(Animations.FADE_RIGHT, animElements);


        actionBarText = (TextView) findViewById(R.id.action_bar_text);
        setActionBarTextValue(actionBarText, "wyrazy dźwiękonaśladowcze");

        onomatopoeicWord = (TextView) findViewById(R.id.onomatopoeic_word);


        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_with_slide);

        cloudContainer = (RelativeLayout) findViewById(R.id.onomatopoeic_container);


        random = new Random();
        utils = new Utilities();
        utils2 = new Utilities();


        animals = new ImageView[]{
                (ImageView) findViewById(R.id.animal1),
                (ImageView) findViewById(R.id.animal2),
                (ImageView) findViewById(R.id.animal3),
                (ImageView) findViewById(R.id.animal4)
        };


        drawablesAnimals = new Drawable[]{
                getResources().getDrawable(R.drawable.cat),
                getResources().getDrawable(R.drawable.cow),
                getResources().getDrawable(R.drawable.chicken),
                getResources().getDrawable(R.drawable.dog),
                getResources().getDrawable(R.drawable.goat),
                getResources().getDrawable(R.drawable.sheep),
                getResources().getDrawable(R.drawable.snake)
        };


        animalsOnomatopoeic = new String[]{
                getResources().getString(R.string.animal_cat),
                getResources().getString(R.string.animal_cow),
                getResources().getString(R.string.animal_chicken),
                getResources().getString(R.string.animal_dog),
                getResources().getString(R.string.animal_goat),
                getResources().getString(R.string.animal_sheep),
                getResources().getString(R.string.animal_snake)
        };


        utils.reset();
        utils2.reset();


        drawablesInts = new int[drawablesAnimals.length];

        setAnimals(animals);

    }


    public void check(View view) {

        ImageView selectedView = (ImageView) view;
        Bitmap selected = ((BitmapDrawable) selectedView.getDrawable()).getBitmap();
        Bitmap randomized = ((BitmapDrawable) randomizedDrawable).getBitmap();


        if (selected == randomized) {
            goodChoice++;
        } else {
            utils2.reset();
            badChoice++;
        }

        if (goodChoice + badChoice != 4) {
            setAnimals(animals);
        } else if (goodChoice + badChoice == 4) {
            Intent rewardActivity = new Intent(getApplicationContext(), Reward.class);
            rewardActivity.putExtra("badChoices", badChoice);
            rewardActivity.putExtra("goodChoices", goodChoice);
            startActivity(rewardActivity);
        }
    }


    public void setAnimals(ImageView[] animals) {
        Utilities u = new Utilities();
        Utilities u2 = new Utilities();
        int randomDrawableID;
        int[] randomized = new int[4];


        for (int i = 0; i < drawablesAnimals.length; i++)
            drawablesInts[i] = i;

        randomDrawableID = u2.randomInt(drawablesInts);
        for (int i = 0; i < animals.length; i++) {
            animals[i].setImageDrawable(drawablesAnimals[randomDrawableID]);
            randomized[i] = randomDrawableID;
            randomDrawableID = u2.randomInt(drawablesInts);
        }

        randomInt = u2.randomInt(drawablesInts);
/*
        for (int i=0; i<randomized.length; i++) {
            if (randomInt == randomized[i]) {
                randomInt = u.randomInt(drawablesInts);
            }
        }
*/


        randomizedDrawable = drawablesAnimals[randomInt];
        animals[random.nextInt(animals.length)].setImageDrawable(randomizedDrawable);

        onomatopoeicWord.setText(animalsOnomatopoeic[randomInt]);
        cloudContainer.startAnimation(fadeIn);

    }


    public void setActionBarTextValue(TextView actionBarText, String gameName) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("pl.marcinwroblewski.aplikacja_dzieci", getApplicationContext().MODE_PRIVATE);

        if (!pref.getString("child_name", "").isEmpty() && !pref.getString("child_name", "").contains(" ")) {
            actionBarText.setText(pref.getString("child_name", "") + " gra w " + gameName);
        } else {
            actionBarText.setText(gameName.substring(0, 1).toUpperCase() + gameName.substring(1, gameName.length()));
        }
    }


    public void sendBack(View view) {
        this.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void sendToSettings(View view) {
        startActivity(new Intent(getApplicationContext(), Settings.class));
    }


}
