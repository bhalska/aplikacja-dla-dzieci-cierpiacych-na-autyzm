package pl.marcinwroblewski.aplikacja_dzieci.Menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import pl.marcinwroblewski.aplikacja_dzieci.Onomatopoeic.AnimalsLearn.EconomicAnimalsLearn;
import pl.marcinwroblewski.aplikacja_dzieci.Onomatopoeic.OnomatopoeicGame;
import pl.marcinwroblewski.aplikacja_dzieci.Onomatopoeic.OnomatopoeicLearn;
import pl.marcinwroblewski.aplikacja_dzieci.R;
import pl.marcinwroblewski.aplikacja_dzieci.Settings.Settings;
import pl.marcinwroblewski.aplikacja_dzieci.Speech.ClothesGame;
import pl.marcinwroblewski.aplikacja_dzieci.Speech.ClothesLearn;
import pl.marcinwroblewski.aplikacja_dzieci.Speech.ColorsGame;
import pl.marcinwroblewski.aplikacja_dzieci.Speech.ColorsLearn;
import pl.marcinwroblewski.aplikacja_dzieci.Speech.RoomGame;
import pl.marcinwroblewski.aplikacja_dzieci.Speech.RoomLearn;
import pl.marcinwroblewski.aplikacja_dzieci.Speech.TableGame;
import pl.marcinwroblewski.aplikacja_dzieci.Speech.TableLearn;
import pl.marcinwroblewski.aplikacja_dzieci.Speech.ToysGame;
import pl.marcinwroblewski.aplikacja_dzieci.Speech.ToysLearn;
import pl.marcinwroblewski.aplikacja_dzieci.Usable.Animations;
import pl.marcinwroblewski.aplikacja_dzieci.Vowels.VowelsGame;
import pl.marcinwroblewski.aplikacja_dzieci.Vowels.VowelsScreen;


public class Speech_games extends Activity {

    private View[] elementsToAnim;
    private Animations animsControler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_games);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        getActionBar().hide();


        LinearLayout buttonsHolder = (LinearLayout)findViewById(R.id.app_container);
        elementsToAnim = new View[buttonsHolder.getChildCount()];

        for(int i=0; i<elementsToAnim.length; i++){
            elementsToAnim[i] = buttonsHolder.getChildAt(i);
        }


        animsControler = new Animations(getApplicationContext());
        animsControler.startAnim(Animations.FADE_LEFT, elementsToAnim);
    }



    public void goTo(View view) {

        Log.d(getPackageName(), "goTo wywołane w SpeechGames");


        switch (view.getId()) {
            case R.id.vowels_learn:
                startActivity(new Intent(getApplicationContext(), VowelsScreen.class));
                break;

            case R.id.vowels_game:
                startActivity(new Intent(getApplicationContext(), VowelsGame.class));
                break;

            case R.id.onomatopoeic_learn:
                startActivity(new Intent(getApplicationContext(), OnomatopoeicLearn.class));
                break;

            case R.id.onomatopoeic_game:
                startActivity(new Intent(getApplicationContext(), OnomatopoeicGame.class));
                break;

            case R.id.economic_animals_learn:
                startActivity(new Intent(getApplicationContext(), EconomicAnimalsLearn.class));
                break;

            case R.id.economic_animals_game:
                startActivity(new Intent(getApplicationContext(), EconomicAnimalsLearn.class));
                break;

            case R.id.table_elements_learn:
                startActivity(new Intent(getApplicationContext(), TableLearn.class));
                break;

            case R.id.table_elements_game:
                startActivity(new Intent(getApplicationContext(), TableGame.class));
                break;

            case R.id.colors_learn:
                startActivity(new Intent(getApplicationContext(), ColorsLearn.class));
                break;

            case R.id.colors_game:
                startActivity(new Intent(getApplicationContext(), ColorsGame.class));
                break;

            case R.id.room_learn:
                startActivity(new Intent(getApplicationContext(), RoomLearn.class));
                break;

            case R.id.room_game:
                startActivity(new Intent(getApplicationContext(), RoomGame.class));
                break;

            case R.id.clothes_learn:
                startActivity(new Intent(getApplicationContext(), ClothesLearn.class));
                break;

            case R.id.clothes_game:
                startActivity(new Intent(getApplicationContext(), ClothesGame.class));
                break;

            case R.id.toys_learn:
                startActivity(new Intent(getApplicationContext(), ToysLearn.class));
                break;

            case R.id.toys_game:
                startActivity(new Intent(getApplicationContext(), ToysGame.class));
                break;

            default:
                Log.w(getPackageName(), "Nieobsłużony przycisk w SpeechGames");
        }
    }

    public void sendBack(View view) {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void sendToSettings(View view){
        startActivity(new Intent(getApplicationContext(), Settings.class));
    }

    @Override
    protected void onResume() {
        animsControler.startAnim(Animations.FADE_LEFT, elementsToAnim);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        animsControler.startAnim(Animations.FADE_OUT, elementsToAnim);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
