package pl.marcinwroblewski.aplikacja_dzieci.Serial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import pl.marcinwroblewski.aplikacja_dzieci.R;
import pl.marcinwroblewski.aplikacja_dzieci.Reward;
import pl.marcinwroblewski.aplikacja_dzieci.Settings.Settings;
import pl.marcinwroblewski.aplikacja_dzieci.Usable.Screen;


public class SerialLearn extends Activity {

    private final int SIZE_MULTIPLIER = 20;
    private ImageView image1, image2, image3, image4,
            container1, container2, container3, container4;
    private ImageView[] containers, seqElements;
    private boolean gotLocations, isSent;
    private Rect containerRect1, containerRect2, containerRect3, containerRect4;
    private int[] containersIDs, imagesIDs;
    private int[] containerLocations1, containerLocations2, containerLocations3,
            containerLocations4;
    private RelativeLayout originalSeqL;
    private Drawable element;
    private TextView actionBarText;

    private int[] originalSequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_learn);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        getActionBar().hide();

        actionBarText = (TextView) findViewById(R.id.action_bar_text);

        setActionBarTextValue(actionBarText, "układanie szeregów");
        originalSeqL = (RelativeLayout) findViewById(R.id.original_sequence);

        //setting elements
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);

        seqElements = new ImageView[]{
                image1,
                image2,
                image3,
                image4
        };


        Bundle extras = getIntent().getExtras();
        if(extras != null){
            element = getResources().getDrawable(extras.getInt("element"));
        }else{
            Log.e(getPackageName(), "ELEMENT WAS NULL!");
        }


        final int[] imagesIDs = new int[seqElements.length];
        originalSequence = new int[seqElements.length];

        for (int i=0; i < seqElements.length; i++){
            imagesIDs[i] = i;
            originalSequence[i] = i;
        }

        for (int i=0; i < seqElements.length; i++){
            seqElements[i].setImageDrawable(element);
            Log.d(getPackageName(), i + " seqElement: " + seqElements[i]);
        }


        /*jeżeli ostatni obrazek (czyli prawdopodobnie również reszta)
          zostały stworzone*/
        image4.post(new Runnable() {
            @Override
            public void run() {
                setMovementAndCollisions(image1, imagesIDs[0]);
                setMovementAndCollisions(image2, imagesIDs[1]);
                setMovementAndCollisions(image3, imagesIDs[2]);
                setMovementAndCollisions(image4, imagesIDs[3]);
            }
        });



        gotLocations = false;   //is containers locations got?
        isSent = false;         //is data sent to Reward activity?


        containersIDs = new int[4]; //table for images id in containers


        //setting tables for items locations

        containerLocations1 = new int[2];
        containerLocations2 = new int[2];
        containerLocations3 = new int[2];
        containerLocations4 = new int[2];


        //setting containers
        container1 = (ImageView) findViewById(R.id.container1);
        container2 = (ImageView) findViewById(R.id.container2);
        container3 = (ImageView) findViewById(R.id.container3);
        container4 = (ImageView) findViewById(R.id.container4);


        //containers table for loops
        containers = new ImageView[]{
                container1,
                container2,
                container3,
                container4
        };

    }


    @Override
    protected void onStart() {
        super.onStart();

        final DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager)SerialLearn.this.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        final Random random = new Random();

        final Screen screen = new Screen(getApplicationContext());

        seqElements[seqElements.length - 1].post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < seqElements.length; i++) {
                    ImageView imageToAdd = new ImageView(getApplicationContext());


                    //setting params for imageViews to top bar (dodawanie obrazów do sekwencji [u góry])
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Math.round(100 * dm.density),
                            Math.round(100 * dm.density));
                    imageToAdd.setPadding(0, Math.round(i * SIZE_MULTIPLIER * (dm.density)), 0, 0);
                    imageToAdd.setImageDrawable(element);
                    params.setMargins((i * 2) * seqElements[i].getWidth(), 0, 0, 0);
                    imageToAdd.setLayoutParams(params);

                    originalSeqL.addView(imageToAdd);


                    //setting created imageViews (ustawianie obrazów do ułożenia)
                    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(Math.round(100 * dm.density),
                            Math.round(100 * dm.density));

                    if (seqElements[i] != null) {
                        seqElements[i].setX(
                                Math.min(seqElements[i].getWidth() * (random.nextFloat() + 1) * i / 2,
                                        screen.getWidth() - seqElements[i].getWidth()));
                        seqElements[i].setY(
                                Math.min((random.nextFloat() * 150) + screen.getHeight() / 8,
                                        screen.getHeight() / 2 - seqElements[i].getHeight()));
                        seqElements[i].setImageDrawable(element);
                        seqElements[i].setPadding(0, Math.round(i * SIZE_MULTIPLIER * (dm.density)), 0, 0);

                    }
                }
            }
        });
    }





    public void setMovementAndCollisions(final ImageView image, final int imageID) {
        Log.d(getPackageName(), "imageID: " + imageID);

        image.setOnTouchListener(new View.OnTouchListener() {

            PointF DownPT = new PointF();
            PointF StartPT = new PointF();
            int[] imageLocations = new int[2];
            Rect imageRect = new Rect(0, 0, 0, 0);
            boolean isLocked = false;



            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventID = event.getAction();


                if (!isLocked) {
                    switch (eventID) {
                        case MotionEvent.ACTION_MOVE:
                            PointF mv = new PointF(event.getX() - DownPT.x, event.getY() - DownPT.y);
                            image.setX((int) (StartPT.x + mv.x));
                            image.setY((int) (StartPT.y + mv.y));
                            StartPT = new PointF(image.getX(), image.getY());


                            imageRect.set(getRect(imageLocations, image));


                            if (!gotLocations) {

                                containerRect1 = getRect(containerLocations1, container1);
                                containerRect2 = getRect(containerLocations2, container2);
                                containerRect3 = getRect(containerLocations3, container3);
                                containerRect4 = getRect(containerLocations4, container4);

                                gotLocations = true;
                            }


                            if (containerRect1.intersect(imageRect) || imageRect.intersect(containerRect1)) {
                                container1.setImageDrawable(image.getDrawable());
                                container1.setPadding(image.getPaddingLeft(), image.getPaddingTop(), image.getPaddingRight(), image.getPaddingBottom());
                                image.setVisibility(View.INVISIBLE);
                                containersIDs[0] = imageID;
                                isLocked = true;
                            } else if (containerRect2.intersect(imageRect) || imageRect.intersect(containerRect2)) {
                                container2.setImageDrawable(image.getDrawable());
                                container2.setPadding(image.getPaddingLeft(), image.getPaddingTop(), image.getPaddingRight(), image.getPaddingBottom());
                                image.setVisibility(View.INVISIBLE);
                                containersIDs[1] = imageID;
                                isLocked = true;
                            } else if (containerRect3.intersect(imageRect) || imageRect.intersect(containerRect3)) {
                                container3.setImageDrawable(image.getDrawable());
                                container3.setPadding(image.getPaddingLeft(), image.getPaddingTop(), image.getPaddingRight(), image.getPaddingBottom());
                                image.setVisibility(View.INVISIBLE);
                                containersIDs[2] = imageID;
                                isLocked = true;
                            } else if (containerRect4.intersect(imageRect) || imageRect.intersect(containerRect4)) {
                                container4.setImageDrawable(image.getDrawable());
                                container4.setPadding(image.getPaddingLeft(), image.getPaddingTop(), image.getPaddingRight(), image.getPaddingBottom());
                                image.setVisibility(View.INVISIBLE);
                                containersIDs[3] = imageID;
                                isLocked = true;
                            }


                            if (isEverySelected(container1, container2, container3,
                                    container4)) {
                                if (isSeqGood(originalSequence, containersIDs)) {
                                    goToReward(true);
                                } else {
                                    goToReward(false);
                                }
                            }


                            break;

                        case MotionEvent.ACTION_DOWN:
                            DownPT.x = event.getX();
                            DownPT.y = event.getY();
                            StartPT = new PointF(image.getX(), image.getY());
                            imageRect.set(getRect(imageLocations, image));
                            break;
                    }


                }
                return true;
            }

        });
    }





    public Rect getRect(int[] containerLocations, ImageView container) {
        container.getLocationOnScreen(containerLocations);

        return new Rect(containerLocations[0], containerLocations[1],
                containerLocations[0] + container.getWidth(), containerLocations[1] + container.getHeight());
    }


    public boolean isSeqGood(int[] originalSeq, int[] childSeq) {

        int seqLength = originalSeq.length;
        int iloscPetli = childSeq.length / originalSeq.length;

        int counter = 1;
        int index = 0;


        if (seqLength == 0) {
            return false;
        } else {
            do {
                for (int i = 0; i < seqLength; i++) {
                    if (originalSeq[i] != childSeq[i + index]) {
                        return false;
                    }
                }
                index = counter * seqLength;

                iloscPetli--;
            } while (iloscPetli > 0);
        }


        return true;

    }





    public void goToReward(boolean won){
        if(!isSent) {
            if (won) {
                isSent = true;
                Intent i = new Intent(getApplicationContext(), Reward.class);
                i.putExtra("goodChoices", 1);
                i.putExtra("badChoices", 0);
                startActivity(i);
            } else {
                isSent = true;
                Intent i = new Intent(getApplicationContext(), Reward.class);
                i.putExtra("goodChoices", 0);
                i.putExtra("badChoices", 1);
                startActivity(i);
            }
        }
    }




    public boolean isEverySelected(ImageView... imageViews) {

        for (ImageView imageView : imageViews) {
            if (imageView.getDrawable() == null) {
                return false;
            }
        }

        return true;
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

    public void reset(View view) {

        Random random = new Random();

        for (ImageView container : containers) {
            container.setImageDrawable(null);
        }

        int counter = 1;

        Screen screen = new Screen(getApplicationContext());

        for (ImageView seqElement : seqElements) {
            seqElement.setX(
                    Math.min(seqElement.getWidth() * (random.nextFloat() + 1) * counter,
                            screen.getWidth() - seqElement.getWidth()));
            seqElement.setY(
                    Math.min((random.nextFloat() * 150) + screen.getHeight() / 8,
                            screen.getHeight() / 2 - seqElement.getHeight()));

            seqElement.setVisibility(View.VISIBLE);
            counter++;
        }

        setMovementAndCollisions(image1, 0);
        setMovementAndCollisions(image2, 1);
        setMovementAndCollisions(image3, 2);
        setMovementAndCollisions(image4, 3);

        isSent = false;

    }
}
