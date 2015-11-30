package pl.marcinwroblewski.aplikacja_dzieci.Vowels;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pl.marcinwroblewski.aplikacja_dzieci.Drawing.DrawingView;
import pl.marcinwroblewski.aplikacja_dzieci.R;
import pl.marcinwroblewski.aplikacja_dzieci.Settings.Settings;
import pl.marcinwroblewski.aplikacja_dzieci.Usable.Animations;
import pl.marcinwroblewski.aplikacja_dzieci.Usable.Utilities;

import static android.graphics.Typeface.createFromAsset;


public class VowelDraw extends Activity {

    private TextView vowelToDraw, anim;
    private TextView[] vowelsTV;
    private View mView;
    public static Paint mPaint;
    private LinearLayout drawingArea;
    private MediaPlayer player = null;
    private TextView actionBarText;
    private LinearLayout appContainer;
    private View[] elementsToAnim;
    private Animations animsControler;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vowel_draw);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        getActionBar().hide();


        appContainer = (LinearLayout)findViewById(R.id.app_container);
        elementsToAnim = new View[appContainer.getChildCount()];

        for(int i=0; i<elementsToAnim.length; i++){
            elementsToAnim[i] = appContainer.getChildAt(i);
        }

        animsControler = new Animations(getApplicationContext());
        animsControler.startRandomAnim(Animations.FADE_IN, elementsToAnim);


        Intent collect = getIntent();
        Bundle sentData = collect.getExtras();


        String sentVowel = sentData.getString("vowel");


        actionBarText = (TextView)findViewById(R.id.action_bar_text);
        setActionBarTextValue(actionBarText, "samogłoski");

        vowelToDraw = (TextView)findViewById(R.id.vowel_to_draw);
        vowelToDraw.setText(sentVowel);

        anim = (TextView)findViewById(R.id.animacja_placeholder);
        anim.setText(sentVowel);

        vowelsTV = new TextView[]{
                vowelToDraw,
                anim
        };

        drawingArea = (LinearLayout) findViewById(R.id.myDrawing);
        setDrawingView();
        paintInit();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(null != player)
            player.stop();
    }


    public void setActionBarTextValue(TextView actionBarText, String gameName){

        SharedPreferences pref = getApplicationContext().getSharedPreferences("pl.marcinwroblewski.aplikacja_dzieci", getApplicationContext().MODE_PRIVATE);

        if(!pref.getString("child_name", "").isEmpty() && !pref.getString("child_name", "").contains(" ")){
            actionBarText.setText(pref.getString("child_name", "") + " gra w " + gameName);
        }else{
            actionBarText.setText(gameName.substring(0, 1).toUpperCase() + gameName.substring(1, gameName.length()));
        }
    }


    public void setPaintColor(View view) {
        Drawable backgroundColor = view.getBackground();


        if(backgroundColor instanceof ColorDrawable) {
            resetDrawingArea(mView);
            mPaint.setColor(((ColorDrawable) backgroundColor).getColor());
        }
    }


    public void playVowel(View view) {
        player = MediaPlayer.create(getApplicationContext(), R.raw.test);

        player.start();
    }

    public void setDrawingView(){
        mView = new DrawingView(getApplicationContext());
        drawingArea.addView(mView, new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    private void paintInit() {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(getResources().getColor(R.color.blue));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(25);
    }

    public void sendBack(View view) {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onResume() {
        animsControler.startRandomAnim(Animations.FADE_IN, elementsToAnim);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void sendToSettings(View view){
        startActivity(new Intent(getApplicationContext(), Settings.class));
    }

    public void resetDrawingArea(View view) {
        drawingArea.destroyDrawingCache();
        drawingArea.refreshDrawableState();
        drawingArea.removeView(mView);
        setDrawingView();
        drawingArea.refreshDrawableState();
    }

}
