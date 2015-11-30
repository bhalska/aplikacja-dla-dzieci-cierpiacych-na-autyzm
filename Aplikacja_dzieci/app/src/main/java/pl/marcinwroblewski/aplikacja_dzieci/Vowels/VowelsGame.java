package pl.marcinwroblewski.aplikacja_dzieci.Vowels;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import pl.marcinwroblewski.aplikacja_dzieci.R;
import pl.marcinwroblewski.aplikacja_dzieci.Reward;
import pl.marcinwroblewski.aplikacja_dzieci.Settings.Settings;
import pl.marcinwroblewski.aplikacja_dzieci.Usable.Animations;
import pl.marcinwroblewski.aplikacja_dzieci.Usable.Utilities;

import static android.graphics.Typeface.*;


public class VowelsGame extends Activity {

    private TextView actionBarText, firstVowel, secondVowel, thirdVowel, fourthVowel;
    private TextView[] vowelsTV;
    int randomizedVowel, vowelCount, goodChoice, badChoice;
    boolean firstVowelSelected, secondVowelSelected, thirdVowelSelected, fourthVowelSelected;
    private Utilities util = new Utilities();
    private int[] randomInts = {1, 2, 3, 4};
    private char[] vowels = {'a', 'e', 'i', 'o', 'u', 'y'};

    private Animations animsControler;
    private View[] animElements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vowels_game);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        getActionBar().hide();

        animsControler = new Animations(getApplicationContext());

        LinearLayout appContainer = (LinearLayout)findViewById(R.id.app_container);
        animElements = new View[appContainer.getChildCount()];
        for(int i=0; i<animElements.length; i++){
            animElements[i] = appContainer.getChildAt(i);
        }
        animsControler.startRandomAnim(Animations.FADE_IN, animElements);

        firstVowelSelected = secondVowelSelected = thirdVowelSelected = fourthVowelSelected = false;

        util.reset();




        randomizedVowel = util.randomInt(randomInts);



        firstVowel = (TextView)findViewById(R.id.first_vowel);
        secondVowel = (TextView)findViewById(R.id.second_vowel);
        thirdVowel = (TextView)findViewById(R.id.third_vowel);
        fourthVowel = (TextView)findViewById(R.id.fourth_vowel);

        vowelsTV = new TextView[]{
                firstVowel,
                secondVowel,
                thirdVowel,
                fourthVowel
        };


        actionBarText = (TextView)findViewById(R.id.action_bar_text);

        setActionBarTextValue(actionBarText, "samogłoski");


        setVowelsValue(vowelsTV);
    }


    private void setVowelsValue(TextView[] vowelsTextViews){

        for (int i=0; i<vowelsTextViews.length; i++){
            vowelsTextViews[i].setText(util.randomCapitalize(
                    String.valueOf(util.randomChar(vowels))));
        }

    }


    public void setActionBarTextValue(TextView actionBarText, String gameName){

        SharedPreferences pref = getApplicationContext().getSharedPreferences("pl.marcinwroblewski.aplikacja_dzieci", getApplicationContext().MODE_PRIVATE);

        if(!pref.getString("child_name", "").isEmpty() && !pref.getString("child_name", "").contains(" ")){
            actionBarText.setText(pref.getString("child_name", "") + " gra w " + gameName);
        }else{
            actionBarText.setText(gameName.substring(0, 1).toUpperCase() + gameName.substring(1, gameName.length()));
        }
    }


    public void sendBack(View view) {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void sendToSettings(View view){
        startActivity(new Intent(getApplicationContext(), Settings.class));
    }

    public void check(View view) {
        if(randomizedVowel != -1) {
            switch (view.getId()){
                case R.id.first_vowel:
                    if(randomizedVowel == 1){
                        view.setBackgroundColor(Color.GREEN);

                        goodChoice += 1;
                    }else if(randomizedVowel != 1){
                        view.setBackgroundColor(Color.MAGENTA);
                        badChoice += 1;
                    }

                    vowelCount++;
                    break;

                case R.id.second_vowel:
                    if(randomizedVowel == 2){
                        view.setBackgroundColor(Color.GREEN);

                        goodChoice += 1;
                    }else if(randomizedVowel != 2){
                        view.setBackgroundColor(Color.MAGENTA);
                        badChoice += 1;
                    }

                    vowelCount++;
                    break;

                case R.id.third_vowel:
                    if(randomizedVowel == 3){
                        view.setBackgroundColor(Color.GREEN);

                        goodChoice += 1;
                    }else if(randomizedVowel != 3){
                        view.setBackgroundColor(Color.MAGENTA);
                        badChoice += 1;
                    }

                    vowelCount++;
                    break;

                case R.id.fourth_vowel:
                    if(randomizedVowel == 4){
                        view.setBackgroundColor(Color.GREEN);

                        goodChoice += 1;
                    }else if(randomizedVowel != 4){
                        view.setBackgroundColor(Color.MAGENTA);
                        badChoice += 1;
                    }

                    vowelCount++;
                    break;
            }

            if(goodChoice + badChoice != 4)
                randomizedVowel = util.randomInt(randomInts);
        }else{
            Toast.makeText(getApplicationContext(), "Błąd losowania", Toast.LENGTH_SHORT).show();
        }


        if(goodChoice + badChoice == 4){
            Intent rewardActivity = new Intent(getApplicationContext(), Reward.class);
            rewardActivity.putExtra("badChoices", badChoice);
            rewardActivity.putExtra("goodChoices", goodChoice);
            startActivity(rewardActivity);
        }
    }

    public void goTo(View view) {
        Intent rewardActivity = new Intent(getApplicationContext(), Reward.class);
        rewardActivity.putExtra("goodChoices", goodChoice);
        rewardActivity.putExtra("goodChoices", 2);
        startActivity(rewardActivity);
    }




}
