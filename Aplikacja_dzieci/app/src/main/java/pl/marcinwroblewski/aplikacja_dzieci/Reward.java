package pl.marcinwroblewski.aplikacja_dzieci;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import pl.marcinwroblewski.aplikacja_dzieci.Settings.Settings;
import pl.marcinwroblewski.aplikacja_dzieci.Usable.Animations;


public class Reward extends Activity {

    private TextView actionBarText;
    private ImageView childPhoto, reward;
    private SharedPreferences pref;
    private String imageName;
    private int good, bad;
    private RelativeLayout rewardContainer;
    private Animations animsControler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        getActionBar().hide();


        rewardContainer = (RelativeLayout)findViewById(R.id.reward_container);
        View[] animateElements = new View[rewardContainer.getChildCount()];
        for(int i=0; i<animateElements.length; i++){
            animateElements[i] = rewardContainer.getChildAt(i);
        }

        animsControler = new Animations(getApplicationContext());
        animsControler.startRandomAnim(Animations.FADE_IN, animateElements);





        Intent collect = getIntent();
        Bundle sentData = collect.getExtras();

        if(sentData != null) {
            bad = sentData.getInt("badChoices");
            good = sentData.getInt("goodChoices");
        }else {Toast.makeText(getApplicationContext(), "Błąd przy przesyle wyniku", Toast.LENGTH_SHORT).show();}

        actionBarText = (TextView)findViewById(R.id.action_bar_text);

        childPhoto = (ImageView)findViewById(R.id.child_photo);
        reward = (ImageView)findViewById(R.id.reward);


        pref = getApplicationContext().getSharedPreferences("pl.marcinwroblewski.aplikacja_dzieci",
                getApplicationContext().MODE_PRIVATE);

        imageName = "child_photo.png";



        setResultText();
        setChildPhoto();

        if(bad <= good) {
            setReward();
        }

    }




    public void setResultText(){
        Log.d(getPackageName(), "Bad " + bad + ", good " + good);
        if (bad > good) {
            actionBarText.setText("Przegrana");
        } else if (bad == good) {
            actionBarText.setText("Mogło być lepiej");
        } else if (bad < good) {
            actionBarText.setText("Bardzo dobrze!");
        }



    }


    public void setChildPhoto(){
        if((new File(pref.getString("external_directory", "") + imageName).exists()) && (null != new File(pref.getString("external_directory", "") + pref))) {
            Bitmap bitmap = BitmapFactory.decodeFile(pref.getString("external_directory", "") + imageName);
            childPhoto.setImageBitmap(bitmap);
        }else if(pref.getString("external_directory", "").equals("")){
            Toast.makeText(getApplicationContext(), "Do zapisu zdjęcia potrzebna jest pamięć zewnętrzna", Toast.LENGTH_SHORT).show();
        }
    }

    public void setReward(){
        int rewardID = pref.getInt("reward_id", 1);
        Drawable rewardDrawable = null;

        switch(rewardID){
            case 1: rewardDrawable = getResources().getDrawable(R.drawable.ship); break;
            case 2: rewardDrawable = getResources().getDrawable(R.drawable.plane); break;
            case 3: rewardDrawable = getResources().getDrawable(R.drawable.train); break;
            default:
                Toast.makeText(getApplicationContext(), "Błąd ustawiania nagrody", Toast.LENGTH_SHORT).show();
        }

        reward.setImageDrawable(rewardDrawable);
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
