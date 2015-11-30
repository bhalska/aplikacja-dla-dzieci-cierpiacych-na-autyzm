package pl.marcinwroblewski.aplikacja_dzieci.Speech;

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

public class RoomGame extends Activity {

    private Drawable[] drawablesAnimals;
    private String[] animalsOnomatopoeic;
    private ImageView[] elements;
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
        setContentView(R.layout.activity_room_game);


        getActionBar().hide();


        animsControler = new Animations(getApplicationContext());

        LinearLayout appContainer = (LinearLayout)findViewById(R.id.app_container);
        animElements = new View[appContainer.getChildCount()];
        for(int i=0; i<animElements.length; i++){
            animElements[i] = appContainer.getChildAt(i);
        }
        animsControler.startAnim(Animations.FADE_RIGHT, animElements);



        actionBarText = (TextView)findViewById(R.id.action_bar_text);
        setActionBarTextValue(actionBarText, "meble");

        onomatopoeicWord = (TextView)findViewById(R.id.onomatopoeic_word);




        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_with_slide);

        cloudContainer = (RelativeLayout)findViewById(R.id.onomatopoeic_container);


        random = new Random();
        utils = new Utilities();
        utils2 = new Utilities();






        elements = new ImageView[]{
                (ImageView)findViewById(R.id.element1),
                (ImageView)findViewById(R.id.element2),
                (ImageView)findViewById(R.id.element3),
                (ImageView)findViewById(R.id.element4)
        };


        drawablesAnimals = new Drawable[]{
                getResources().getDrawable(R.drawable.bed),
                getResources().getDrawable(R.drawable.chair),
                getResources().getDrawable(R.drawable.commode),
                getResources().getDrawable(R.drawable.lamp),
                getResources().getDrawable(R.drawable.table),
                getResources().getDrawable(R.drawable.wardrobe)
        };


        animalsOnomatopoeic = new String[]{
                getResources().getString(R.string.bed),
                getResources().getString(R.string.chair),
                getResources().getString(R.string.commode),
                getResources().getString(R.string.lamp),
                getResources().getString(R.string.table),
                getResources().getString(R.string.wardrobe)
        };



        utils.reset();
        utils2.reset();


        drawablesInts = new int[drawablesAnimals.length];

        setElements(elements);

    }



    public void check(View view) {

        ImageView selectedView = (ImageView)view;
        Bitmap selected = ((BitmapDrawable)selectedView.getDrawable()).getBitmap();
        Bitmap randomized = ((BitmapDrawable)randomizedDrawable).getBitmap();


        if(selected == randomized){
            goodChoice++;
        }else {
            utils2.reset();
            badChoice++;
        }

        if(goodChoice + badChoice != 4){
            setElements(elements);
        }
        else if(goodChoice + badChoice == 4){
            Intent rewardActivity = new Intent(getApplicationContext(), Reward.class);
            rewardActivity.putExtra("badChoices", badChoice);
            rewardActivity.putExtra("goodChoices", goodChoice);
            startActivity(rewardActivity);
        }
    }



    public void setElements(ImageView[] elements){
        Utilities u = new Utilities();
        Utilities u2 = new Utilities();
        int randomDrawableID;
        int[] randomized = new int[4];


        for(int i = 0; i<drawablesAnimals.length; i++)
            drawablesInts[i] = i;

        for (int i=0; i< elements.length; i++) {
            randomDrawableID = u.randomInt(drawablesInts);
            randomized[i] = randomDrawableID;
            elements[i].setImageDrawable(drawablesAnimals[randomDrawableID]);
        }


        randomInt = u2.randomInt(drawablesInts);


        randomizedDrawable = drawablesAnimals[randomInt];
        elements[random.nextInt(elements.length)].setImageDrawable(randomizedDrawable);

        onomatopoeicWord.setText(animalsOnomatopoeic[randomInt]);
        cloudContainer.startAnimation(fadeIn);

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
