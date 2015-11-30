package pl.marcinwroblewski.aplikacja_dzieci.Usable;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.Random;

import pl.marcinwroblewski.aplikacja_dzieci.R;

/**
 * Created by Admin on 2015-06-03.
 */
public class Animations {

    private Context context;
    public static int FADE_IN = 100;
    public static int FADE_OUT = 99;
    public static int FADE_RIGHT = 50;
    public static int FADE_LEFT = 50;
    private Animation currentAnim;


    public Animations(Context c){
        context = c;
        currentAnim = null;
    }


    public void startAnim(int type, View... views){


        if(type==FADE_IN){
            currentAnim = AnimationUtils.loadAnimation(context, R.anim.fade_in_with_slide);
        }else if(type==FADE_LEFT){
            currentAnim = AnimationUtils.loadAnimation(context, R.anim.fade_in_with_slide_left);
        }else if(type==FADE_RIGHT){
            currentAnim = AnimationUtils.loadAnimation(context, R.anim.fade_in_with_slide_right);
        }else if(type==FADE_OUT){
            currentAnim = AnimationUtils.loadAnimation(context, R.anim.fade_out_with_slide);
        }

        if(currentAnim != null) {
            for (int i = 0; i < views.length; i++) {
                views[i].startAnimation(currentAnim);
            }
        }else{
            Log.e("Animations class", "FATAL! animation was null!");
        }
    }





    public void startRandomAnim(int type, View... views){

        int[] animsRes = null;
        Random random = new Random();

        if(type==FADE_IN){
            animsRes = new int[]{
                    R.anim.fade_in_with_slide,
                    R.anim.fade_in_with_slide_left,
                    R.anim.fade_in_with_slide_right
            };
        }else if(type==FADE_OUT){
            animsRes = new int[]{
                    R.anim.fade_out_with_slide
            };
        }


        if(animsRes != null) {
            currentAnim = AnimationUtils
                    .loadAnimation(context, animsRes[random.nextInt(animsRes.length)]);

            for (int i = 0; i < views.length; i++) {
                views[i].startAnimation(currentAnim);
            }
        }else{
            Log.e("Animations class", "FATAL! animation resources was null!");
        }
    }


    public boolean animEnd(){

        final boolean[] finished = {false};

        currentAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finished[0] = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        return finished[0];
    }


}
