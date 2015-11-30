package pl.marcinwroblewski.aplikacja_dzieci.Motility;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pl.marcinwroblewski.aplikacja_dzieci.Motility.Motility;
import pl.marcinwroblewski.aplikacja_dzieci.R;
import pl.marcinwroblewski.aplikacja_dzieci.Settings.Settings;


public class MotilityMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motility_menu);

        getActionBar().hide();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setActionBarTextValue((TextView)findViewById(R.id.action_bar_text), "grafomotorykę");
    }

    public void goToMotility(View view) {
        Intent intent = new Intent(getApplicationContext(), Motility.class);
        int selectedMap = 0;


        switch (view.getId()){
            case R.id.first_level: selectedMap = R.drawable.map_motility1; break;
            case R.id.second_level: selectedMap = R.drawable.map_motility2; break;
            case R.id.third_level: selectedMap = R.drawable.map_motility3; break;
            case R.id.fourth_level: selectedMap = R.drawable.map_motility4; break;
            case R.id.fifth_level: selectedMap = R.drawable.map_motility5; break;
        }


        intent.putExtra("selectedMap", selectedMap);
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
