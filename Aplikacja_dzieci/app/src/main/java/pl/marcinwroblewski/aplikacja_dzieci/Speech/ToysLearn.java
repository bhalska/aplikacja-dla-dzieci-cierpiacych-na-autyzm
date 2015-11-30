package pl.marcinwroblewski.aplikacja_dzieci.Speech;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import pl.marcinwroblewski.aplikacja_dzieci.R;
import pl.marcinwroblewski.aplikacja_dzieci.Settings.Settings;
import pl.marcinwroblewski.aplikacja_dzieci.Usable.Animations;

public class ToysLearn extends Activity {

    private Drawable[] elements;
    private String[] elementsOnomatopoeic;
    private TextView elementName;
    private ImageView element;
    private RelativeLayout cloudContainer;


    private Animation fadeIn;
    private Animations animsControler;
    private View[] animElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toys_learn);

        getActionBar().hide();

        TextView actionBarText = (TextView)findViewById(R.id.action_bar_text);
        setActionBarTextValue(actionBarText, "zabawki");


        animsControler = new Animations(getApplicationContext());

        LinearLayout appContainer = (LinearLayout)findViewById(R.id.app_container);
        animElements = new View[appContainer.getChildCount()];
        for(int i=0; i<animElements.length; i++){
            animElements[i] = appContainer.getChildAt(i);
        }
        animsControler.startRandomAnim(Animations.FADE_IN, animElements);



        element = (ImageView)findViewById(R.id.element);
        elementName = (TextView)findViewById(R.id.onomatopoeic_word);
        cloudContainer = (RelativeLayout)findViewById(R.id.onomatopoeic_container);

        element.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int event = motionEvent.getAction();

                switch (event) {
                    case MotionEvent.ACTION_DOWN:
                        animsControler.startRandomAnim(Animations.FADE_IN, animElements);
                        setRandomDrawableWithText(elementsOnomatopoeic, elementName, elements, element);
                }

                return false;
            }
        });




        elements = new Drawable[]{
                getResources().getDrawable(R.drawable.autko),
                getResources().getDrawable(R.drawable.klaun),
                getResources().getDrawable(R.drawable.kolejka),
                getResources().getDrawable(R.drawable.lalka),
                getResources().getDrawable(R.drawable.mis),
                getResources().getDrawable(R.drawable.pilka),
                getResources().getDrawable(R.drawable.samolot),
                getResources().getDrawable(R.drawable.wozek)
        };

        elementsOnomatopoeic = new String[]{
                getResources().getString(R.string.autko),
                getResources().getString(R.string.klaun),
                getResources().getString(R.string.kolejka),
                getResources().getString(R.string.lalka),
                getResources().getString(R.string.mis),
                getResources().getString(R.string.pilka),
                getResources().getString(R.string.samolot),
                getResources().getString(R.string.wozek)

        };


        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_with_slide);
        setRandomDrawableWithText(elementsOnomatopoeic, elementName, elements, element);

    }



    private void setRandomDrawableWithText(String[] texts, TextView textToSet, Drawable[] drawables, ImageView imageToSet){
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt(drawables.length);

        imageToSet.setImageDrawable(drawables[random]);
        textToSet.setText(texts[random]);
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
        this.onBackPressed();
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
