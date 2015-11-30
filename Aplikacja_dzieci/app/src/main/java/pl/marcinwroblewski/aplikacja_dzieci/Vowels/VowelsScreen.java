package pl.marcinwroblewski.aplikacja_dzieci.Vowels;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

import pl.marcinwroblewski.aplikacja_dzieci.R;
import pl.marcinwroblewski.aplikacja_dzieci.Settings.Settings;
import pl.marcinwroblewski.aplikacja_dzieci.Usable.Animations;
import pl.marcinwroblewski.aplikacja_dzieci.Usable.Utilities;

import static android.graphics.Typeface.createFromAsset;


public class VowelsScreen extends Activity {

    private ActionBar actionBar;
    private TextView actionBarText, firstVowel, secondVowel, thirdVowel, fourthVowel;
    private TextView[] vowelsTV;
    private Utilities util = new Utilities();
    private int randomVowelCounter;
    Random random;
    char[] vowels = {'i', 'o', 'a', 'y', 'e'};

    private Animations animsControler;
    private View[] animElements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vowels_screen);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


        animsControler = new Animations(getApplicationContext());

        LinearLayout appContainer = (LinearLayout)findViewById(R.id.app_container);
        animElements = new View[appContainer.getChildCount()];
        for(int i=0; i<animElements.length; i++){
            animElements[i] = appContainer.getChildAt(i);
        }
        animsControler.startRandomAnim(Animations.FADE_IN, animElements);


        randomVowelCounter = 0;

        random = new Random();



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


        //ukrywanie górnego paska aplikacji
        getActionBar().hide();


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


    private char randomVowel(){
        if(vowels.length - randomVowelCounter > 0) {
            int randomIndex = random.nextInt(vowels.length - randomVowelCounter);
            char selectedVowel = vowels[randomIndex];
            vowels[randomIndex] = vowels[(vowels.length - randomVowelCounter) - 1];

            randomVowelCounter++;
            return selectedVowel;
        }else{
            return '/';
        }
    }

    public String randomCapitalize(String letter){
        boolean toUpper = random.nextBoolean();

        if(toUpper)
            return letter.substring(0).toUpperCase();
        else
            return letter;
    }




    public void sendToVowelDraw(View view){


        Intent openVowelDraw = new Intent(this, VowelDraw.class);
        openVowelDraw.putExtra("vowel", ((TextView) view).getText().toString());

        startActivity(openVowelDraw);
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
}
