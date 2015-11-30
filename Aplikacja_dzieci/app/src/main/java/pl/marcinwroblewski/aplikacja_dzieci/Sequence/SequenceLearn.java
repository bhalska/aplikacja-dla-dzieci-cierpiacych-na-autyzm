package pl.marcinwroblewski.aplikacja_dzieci.Sequence;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import pl.marcinwroblewski.aplikacja_dzieci.R;
import pl.marcinwroblewski.aplikacja_dzieci.Reward;
import pl.marcinwroblewski.aplikacja_dzieci.Settings.Settings;
import pl.marcinwroblewski.aplikacja_dzieci.Usable.Utilities;


public class SequenceLearn extends Activity {


    private ImageView image1, image2, image3, image4, image5, image6,
            container1, container2, container3, container4, container5, container6;
    private ImageView[] containers, seqElements;
    private TextView debugText;
    private boolean gotLocations, isSent;
    private Rect containerRect1, containerRect2, containerRect3, containerRect4, containerRect5, containerRect6;
    private int[] containersIDs, imagesIDs;
    private int[] carLocations, sunLocations, cloudLocations, containerLocations1, containerLocations2, containerLocations3,
            containerLocations4, containerLocations5, containerLocations6;
    private RelativeLayout bg;
    private Utilities utils;

    private ImageView originalSeq1, originalSeq2, originalSeq3;


    private Drawable[] elements;


    int[] sentElements = new int[3];


    private int[] originalSequence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sequence_learn);

        ActionBar bar = getActionBar();
        bar.hide();

        utils = new Utilities();


        //getting info from menu
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            for(int i=0; i<sentElements.length; i++){
                sentElements[i] = extras.getInt("element" + i);
            }
        }else{
            Toast.makeText(getApplicationContext(),"Błąd wybierania sekwencji", Toast.LENGTH_SHORT).show();

        }



        if(sentElements[2] != 0){
            elements = new Drawable[3];
        }else{
            elements = new Drawable[2];
        }

        for(int i=0; i<elements.length; i++){
            elements[i] = getResources().getDrawable(sentElements[i]);
        }


        gotLocations = false;   //is containers locations got?
        isSent = false;         //is data sent to Reward activity?


        containersIDs = new int[6]; //table for images id in containers


                                    //setting tables for items locations
        carLocations = new int[2];
        sunLocations = new int[2];
        cloudLocations = new int[2];

        containerLocations1 = new int[2];
        containerLocations2 = new int[2];
        containerLocations3 = new int[2];
        containerLocations4 = new int[2];
        containerLocations5 = new int[2];
        containerLocations6 = new int[2];


        bg = (RelativeLayout) findViewById(R.id.background);


        debugText = (TextView) findViewById(R.id.debug);

                                    //setting containers
        container1 = (ImageView) findViewById(R.id.container1);
        container2 = (ImageView) findViewById(R.id.container2);
        container3 = (ImageView) findViewById(R.id.container3);
        container4 = (ImageView) findViewById(R.id.container4);
        container5 = (ImageView) findViewById(R.id.container5);
        container6 = (ImageView) findViewById(R.id.container6);

                                    //containers table for loops
        containers = new ImageView[]{
                container1,
                container2,
                container3,
                container4,
                container5,
                container6
        };


        imagesIDs = new int[6];
        /*int[] ids = new int[0];
            if(elementsType == CLOUDS_AND_SUNS && elementsCount == 2){
                ids = new int[]{1, 2, 1, 2, 1, 2};
            }else if(elementsType == CLOUDS_AND_SUNS && elementsCount == 3){
                ids = new int[]{1, 2, 3, 1, 2, 3};
            }else if(elementsType == CARS_AND_PLANES && elementsCount == 2){
                ids = new int[]{4, 5, 4, 5, 4, 5};
            }else if(elementsType == CARS_AND_PLANES && elementsCount == 3){
                ids = new int[]{4, 5, 6, 4, 5, 6};
            }*/




        utils.reset();
        /*for(int i=0; i<imagesIDs.length; i++) {
            imagesIDs[i] = utils.randomInt(ids);
            Log.d(getPackageName(), "images ID " + imagesIDs[i] + " ");
        }*/






                                    //setting elements
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        image5 = (ImageView) findViewById(R.id.image5);
        image6 = (ImageView) findViewById(R.id.image6);

        seqElements = new ImageView[]{
                image1,
                image2,
                image3,
                image4,
                image5,
                image6
        };

        for (int i=0; i < seqElements.length; i++){
            imagesIDs[i] = i % elements.length;
        }

        /*setDrawableByID(image1, imagesIDs[0]);*/
        setMovementAndCollisions(image1, imagesIDs[0]);

       /* setDrawableByID(image2, imagesIDs[1]);*/
        setMovementAndCollisions(image2, imagesIDs[1]);

       /* setDrawableByID(image3, imagesIDs[2]);*/
        setMovementAndCollisions(image3, imagesIDs[2]);

        /*setDrawableByID(image4, imagesIDs[3]);*/
        setMovementAndCollisions(image4, imagesIDs[3]);

        /*setDrawableByID(image5, imagesIDs[4]);*/
        setMovementAndCollisions(image5, imagesIDs[4]);

        /*setDrawableByID(image6, imagesIDs[5]);*/
        setMovementAndCollisions(image6, imagesIDs[5]);





                                    //setting sequence
        utils.reset();

        /*originalSequence = new int[]{
                utils.randomInt(ints),
                utils.randomInt(ints),
                utils.randomInt(ints)
        };*/

        originalSequence = new int[elements.length];
        for(int i=0; i<originalSequence.length; i++){
            originalSequence[i] = i%elements.length;
        }

        originalSeq1 = (ImageView) findViewById(R.id.first_seq_element);
        /*setDrawableByID(originalSeq1, originalSequence[0]);*/
        originalSeq1.setImageDrawable(getResources().getDrawable(sentElements[0]));
        originalSeq2 = (ImageView) findViewById(R.id.second_seq_element);
        /*setDrawableByID(originalSeq2, originalSequence[1]);*/
        originalSeq2.setImageDrawable(getResources().getDrawable(sentElements[1]));
        originalSeq3 = (ImageView) findViewById(R.id.third_seq_element);
        /*if(elementsCount > 2) {
            setDrawableByID(originalSeq3, originalSequence[2]);
        }*/
        if(sentElements[2] != 0) {
            originalSeq3.setImageDrawable(getResources().getDrawable(sentElements[2]));
        }
        TextView actionBarText = (TextView)findViewById(R.id.action_bar_text);
        setActionBarTextValue(actionBarText, "układanie sekwencji");


        String debug = "";
        for(int i=0; i<originalSequence.length; i++){
            debug += String.valueOf(i + " Original sequence element " + originalSequence[i] + " ");
        }
        Log.d(getPackageName(), debug);

    }


    @Override
    protected void onStart() {
        super.onStart();


        seqElements[seqElements.length-1].post(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i < seqElements.length; i++){

                    seqElements[i].setImageDrawable(elements[i % elements.length]);
                    Log.d(getPackageName(), i+" seqElement: " + seqElements[i]);

                    Random random = new Random();

                    for (ImageView container : containers) {
                        container.setImageDrawable(null);
                    }

                    int counter = 1;

                    for (ImageView seqElement : seqElements) {
                        seqElement.setX((seqElement.getWidth() * (random.nextInt(2)+1)) * counter);
                        seqElement.setY((seqElement.getHeight() * (random.nextInt(2)+1)));

                        seqElement.setVisibility(View.VISIBLE);
                        counter++;
                    }

                }
            }
        });



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


    public boolean isEverySelected(ImageView... imageViews) {

        for (ImageView imageView : imageViews) {
            if (imageView.getDrawable() == null) {
                return false;
            }
        }

        return true;
    }


    public Rect getRect(int[] containerLocations, ImageView container) {
        container.getLocationOnScreen(containerLocations);

        return new Rect(containerLocations[0], containerLocations[1],
                containerLocations[0] + container.getWidth(), containerLocations[1] + container.getHeight());
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
                                containerRect5 = getRect(containerLocations5, container5);
                                containerRect6 = getRect(containerLocations6, container6);

                                gotLocations = true;
                            }


                            if (containerRect1.intersect(imageRect) || imageRect.intersect(containerRect1)) {
                                container1.setImageDrawable(image.getDrawable());
                                image.setVisibility(View.INVISIBLE);
                                containersIDs[0] = imageID;
                                isLocked = true;
                            } else if (containerRect2.intersect(imageRect) || imageRect.intersect(containerRect2)) {
                                container2.setImageDrawable(image.getDrawable());
                                image.setVisibility(View.INVISIBLE);
                                containersIDs[1] = imageID;
                                isLocked = true;
                            } else if (containerRect3.intersect(imageRect) || imageRect.intersect(containerRect3)) {
                                container3.setImageDrawable(image.getDrawable());
                                image.setVisibility(View.INVISIBLE);
                                containersIDs[2] = imageID;
                                isLocked = true;
                            } else if (containerRect4.intersect(imageRect) || imageRect.intersect(containerRect4)) {
                                container4.setImageDrawable(image.getDrawable());
                                image.setVisibility(View.INVISIBLE);
                                containersIDs[3] = imageID;
                                isLocked = true;
                            } else if (containerRect5.intersect(imageRect) || imageRect.intersect(containerRect5)) {
                                container5.setImageDrawable(image.getDrawable());
                                image.setVisibility(View.INVISIBLE);
                                containersIDs[4] = imageID;
                                isLocked = true;
                            } else if (containerRect6.intersect(imageRect) || imageRect.intersect(containerRect6)) {
                                container6.setImageDrawable(image.getDrawable());
                                image.setVisibility(View.INVISIBLE);
                                containersIDs[5] = imageID;
                                isLocked = true;
                            }


                            if (isEverySelected(container1, container2, container3,
                                    container4, container5, container6)) {
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


    public void reset(View view) {

        Random random = new Random();

        for (ImageView container : containers) {
            container.setImageDrawable(null);
        }

        int counter = 1;

        for (ImageView seqElement : seqElements) {
            seqElement.setX((seqElement.getWidth() * (random.nextInt(2)+1)) * counter);
            seqElement.setY((seqElement.getHeight() * (random.nextInt(2)+1)));

            seqElement.setVisibility(View.VISIBLE);
            counter++;
        }

        isSent = false;

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
