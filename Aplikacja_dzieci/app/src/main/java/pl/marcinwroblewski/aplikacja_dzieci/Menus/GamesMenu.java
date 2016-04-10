package pl.marcinwroblewski.aplikacja_dzieci.Menus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import pl.marcinwroblewski.aplikacja_dzieci.BetaInfoDialogFragment;
import pl.marcinwroblewski.aplikacja_dzieci.Motility.MotilityMenu;
import pl.marcinwroblewski.aplikacja_dzieci.R;
import pl.marcinwroblewski.aplikacja_dzieci.Sequence.SequenceMenu;
import pl.marcinwroblewski.aplikacja_dzieci.Serial.SerialMenu;
import pl.marcinwroblewski.aplikacja_dzieci.Settings.Settings;
import pl.marcinwroblewski.aplikacja_dzieci.Usable.Animations;


public class GamesMenu extends FragmentActivity {


    private Animations animsControler;
    private View[] buttons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_menu);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        RelativeLayout buttonsHolder = (RelativeLayout)findViewById(R.id.app_container);
        buttons = new View[buttonsHolder.getChildCount()];

        for(int i=0; i<buttons.length; i++){
            buttons[i] = buttonsHolder.getChildAt(i);
        }


        //notatka o wersji testowej
        SharedPreferences sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        if(!sp.getBoolean("beta_info", false)){
            DialogFragment betaInfo = new BetaInfoDialogFragment();
            betaInfo.show(getSupportFragmentManager(), "Beta message");

            Log.d("Beta message", "delivered");

            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("beta_info", true);
            editor.apply();
        }

        animsControler = new Animations(getApplicationContext());
        animsControler.startAnim(Animations.FADE_LEFT, buttons);

        if(getActionBar() != null)  getActionBar().hide();
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
    protected void onResume() {
        animsControler.startAnim(Animations.FADE_LEFT, buttons);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
