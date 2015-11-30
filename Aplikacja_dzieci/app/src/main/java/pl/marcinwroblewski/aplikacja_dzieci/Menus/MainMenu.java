package pl.marcinwroblewski.aplikacja_dzieci.Menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import pl.marcinwroblewski.aplikacja_dzieci.Motility.MotilityMenu;
import pl.marcinwroblewski.aplikacja_dzieci.R;
import pl.marcinwroblewski.aplikacja_dzieci.Sequence.SequenceMenu;
import pl.marcinwroblewski.aplikacja_dzieci.Serial.SerialMenu;
import pl.marcinwroblewski.aplikacja_dzieci.Settings.Settings;


public class MainMenu extends Activity {


    private View[] menuElements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        menuElements = new View[]{
                (TextView)findViewById(R.id.button_speech_apps),
                (TextView)findViewById(R.id.button_motility_apps),
                (TextView)findViewById(R.id.button_sequence_apps),
                (TextView)findViewById(R.id.button_serial_apps)
        };

        getActionBar().hide();
        startMenuElementsAnims(menuElements);
    }


    @Override
    protected void onResume() {
        startMenuElementsAnims(menuElements);
        super.onResume();
    }

    private void startMenuElementsAnims(View[] elements){

        Animation inAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_with_slide_left);

        for (int i=0; i<elements.length; i++){
            elements[i].startAnimation(inAnim);
        }
    }




    public void sendTo(View view) {


        switch (view.getId()){
            case R.id.button_speech_apps:
                startActivity(new Intent(getApplicationContext(), Speech_games.class));
                break;

            case R.id.button_motility_apps:
                startActivity(new Intent(getApplicationContext(), MotilityMenu.class));
                break;

            case R.id.button_sequence_apps:
                startActivity(new Intent(getApplicationContext(), SequenceMenu.class));
                break;

            case R.id.button_serial_apps:
                startActivity(new Intent(getApplicationContext(), SerialMenu.class));
                break;

            

            case R.id.settings_button:
                startActivity(new Intent(getApplicationContext(), Settings.class));
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
