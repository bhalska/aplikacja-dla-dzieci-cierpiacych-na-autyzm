package pl.marcinwroblewski.aplikacja_dzieci.Serial;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pl.marcinwroblewski.aplikacja_dzieci.R;
import pl.marcinwroblewski.aplikacja_dzieci.Settings.Settings;


public class SerialMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_menu);

        getActionBar().hide();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setActionBarTextValue((TextView)findViewById(R.id.action_bar_text), "układanie szeregów");
    }


    public void goToSerial(View view) {

        Intent intent = new Intent(getApplicationContext(), SerialLearn.class);

            //elementsToSend[i] = getResources().getIdentifier(img.toString(), "drawable", getPackageName());

        intent.putExtra("element", getResources().getIdentifier(view.getTag().toString(), "drawable", getPackageName()));

        startActivity(intent);
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
}
