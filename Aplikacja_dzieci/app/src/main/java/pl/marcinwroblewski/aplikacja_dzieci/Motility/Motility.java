package pl.marcinwroblewski.aplikacja_dzieci.Motility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pl.marcinwroblewski.aplikacja_dzieci.R;
import pl.marcinwroblewski.aplikacja_dzieci.Reward;
import pl.marcinwroblewski.aplikacja_dzieci.Settings.Settings;


public class Motility extends Activity {


    int selectedLevel = 0;
    private ImageView car, carCrashed, reset;
    private View bg;
    private TextView actionBarText;
    private Bitmap bgBitmap;
    private WindowManager windowManager;
    private Point size;
    private boolean isCarCrashed, isSent;
    private int pixelColor1, pixelColor2, pixelColor3, pixelColor4;
    private int endColor, badColor;
    private int carLocations[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_motility);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        getActionBar().hide();
        setActionBarTextValue((TextView)findViewById(R.id.action_bar_text), "grafomotorykę");

        //getting info from menu
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            selectedLevel = extras.getInt("selectedMap");
        }else{
            Toast.makeText(getApplicationContext(), "Błąd wybierania mapy", Toast.LENGTH_SHORT).show();
            Log.e(getPackageName(), "Przesyłanie mapy nie powiodło się");
        }

        isCarCrashed = false;
        isSent = false;

        windowManager = getWindowManager();
        size = new Point();
        windowManager.getDefaultDisplay().getSize(size);


        actionBarText =(TextView)findViewById(R.id.action_bar_text);
        setActionBarTextValue(actionBarText, "grafomotorykę");


        bg = findViewById(R.id.background);



        carCrashed = (ImageView)findViewById(R.id.car_crashed);
        carCrashed.setVisibility(View.INVISIBLE);

        reset = (ImageView)findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        //setting colors to check
        badColor = Color.parseColor("#5ad427");
        endColor = Color.parseColor("#4a67ff");

        car = (ImageView) findViewById(R.id.car);

        carLocations = new int[2];


        /*dodawanie widoków (View) w onCreate wymaga użycia
        * instrukcji runOnUiThread
        */
        final Thread putToUI = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        reset();
                        setBackground(bg, selectedLevel);
                        //getting bitmap from bg
                        bgBitmap = getBitmapFromView(bg);
                    }
                });
            }
        });
        putToUI.start();


        car.setOnTouchListener(new View.OnTouchListener(){

            PointF DownPT = new PointF();
            PointF StartPT = new PointF();




            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventID = event.getAction();

                switch (eventID) {
                    case MotionEvent.ACTION_MOVE:

                        PointF mv = new PointF(event.getX() - DownPT.x, event.getY() - DownPT.y);
                        car.setX(Math.abs((int) (StartPT.x + mv.x)));
                        car.setY(Math.abs((int) (StartPT.y + mv.y)));
                        StartPT = new PointF(car.getX(), car.getY());

                        car.getLocationOnScreen(carLocations);

                        //update car coordinates
                        if(carLocations[0] + car.getWidth() < bgBitmap.getWidth() && carLocations[1] + car.getHeight() < bgBitmap.getHeight()){
                            pixelColor1 = bgBitmap.getPixel(Math.abs(carLocations[0]), Math.abs(carLocations[1]));
                            pixelColor2 = bgBitmap.getPixel(Math.abs(carLocations[0] + car.getWidth()), Math.abs(carLocations[1]));
                            pixelColor3 = bgBitmap.getPixel(Math.abs(carLocations[0]), carLocations[1] + Math.abs(car.getHeight()));
                            pixelColor4 = bgBitmap.getPixel(Math.abs(carLocations[0] + car.getWidth()),Math.abs( carLocations[1] + car.getHeight()));
                        }

                        if(!isCarCrashed) {
                            if (pixelColor1 == badColor) {
                                carCrashed.setX(car.getX());
                                carCrashed.setY(car.getY());
                                carCrashed.setVisibility(View.VISIBLE);

                                car.setVisibility(View.INVISIBLE);

                                isCarCrashed = true;
                                Log.d(getPackageName(), "pxl1 crashed");

                            }else if(pixelColor1 == endColor){
                                    goToReward(true);
                            }else if (pixelColor2 == badColor) {

                                carCrashed.setX(car.getX());
                                carCrashed.setY(car.getY());
                                carCrashed.setVisibility(View.VISIBLE);

                                car.setVisibility(View.INVISIBLE);

                                isCarCrashed = true;

                                Log.d(getPackageName(), "pxl2 crashed");

                            }else if(pixelColor2 == endColor){
                                goToReward(true);
                            }else if (pixelColor3 == badColor) {

                                carCrashed.setX(car.getX());
                                carCrashed.setY(car.getY());
                                carCrashed.setVisibility(View.VISIBLE);

                                car.setVisibility(View.INVISIBLE);

                                isCarCrashed = true;

                                Log.d(getPackageName(), "pxl3 crashed");

                            }else if(pixelColor3 == endColor){
                                goToReward(true);
                            }else if (pixelColor4 == badColor) {

                                carCrashed.setX(car.getX());
                                carCrashed.setY(car.getY());
                                carCrashed.setVisibility(View.VISIBLE);

                                car.setVisibility(View.INVISIBLE);

                                isCarCrashed = true;

                                Log.d(getPackageName(), "pxl4 crashed");
                            }else if(pixelColor4 == endColor){
                                goToReward(true);
                            }
                        }
                        break;

                    case MotionEvent.ACTION_DOWN:
                        DownPT.x = event.getX();
                        DownPT.y = event.getY();
                        StartPT = new PointF(car.getX(), car.getY());
                        break;

                }

                return true;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        reset();
    }

    public void setBackground(View layout, int map){
        layout.setBackgroundResource(map);
    }


    public Bitmap getBitmapFromView(View view) {
        Bitmap b = Bitmap.createBitmap(
                size.x, size.y, Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(b);
        view.layout(0, 0, size.x, size.y);
        view.draw(c);

        return b;
    }


    public void reset(){
        car.setX((float) (bg.getWidth()*0.15));
        car.setY((float) (bg.getHeight()*0.5) - (car.getHeight()*2));
        car.setVisibility(View.VISIBLE);
        carCrashed.setVisibility(View.INVISIBLE);

        isCarCrashed = false;
        isSent = false;
    }





    public void setActionBarTextValue(TextView actionBarText, String gameName){

        SharedPreferences pref = getApplicationContext().getSharedPreferences("pl.marcinwroblewski.aplikacja_dzieci", Context.MODE_PRIVATE);

        if(!pref.getString("child_name", "").isEmpty() && !pref.getString("child_name", "").contains(" ")){
            actionBarText.setText(pref.getString("child_name", "") + " gra w " + gameName);
        }else{
            actionBarText.setText("Grafomotoryka");
        }
    }


    public void goToReward(boolean won){

        if(!isSent) {

            isSent = true;
            Intent i = new Intent(getApplicationContext(), Reward.class);
            if (won) {
                i.putExtra("goodChoices", 1);
                i.putExtra("badChoices", 0);
            } else {
                i.putExtra("goodChoices", 0);
                i.putExtra("badChoices", 1);
            }

            startActivity(i);
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
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