package pl.marcinwroblewski.aplikacja_dzieci.Usable;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

/*
  Created by Marcin Wróblewski on 2014-12-07.
 */
public class Utilities{

    private int randomCharCounter, randomIntCounter, randomDrawableCounter;
    private Random random;


    public Utilities(){
        randomDrawableCounter = 0;
        randomIntCounter = 0;
        randomCharCounter = 0;
        random = new Random();
    }

    public void reset(){
        randomDrawableCounter = 0;
        randomIntCounter = 0;
        randomCharCounter = 0;
        random = new Random();
    }

    public void setPressedColor(int pressedColor, int notPressedColor, int pressedBackgroundColor, int notPressedBackgroundColor ,TextView pressed, TextView... notPressed){

        pressed.setTextColor(pressedColor);
        pressed.setBackgroundColor(pressedBackgroundColor);


        int i = 0;
        for(TextView x : notPressed){
            notPressed[i].setTextColor(notPressedColor);
            notPressed[i].setBackgroundColor(notPressedBackgroundColor);
            i++;
        }
    }


    public void setPressedColor(int pressedBackgroundColor, int notPressedBackgroundColor ,ImageView pressed, ImageView... notPressed){

        pressed.setBackgroundColor(pressedBackgroundColor);

        int i = 0;
        for(ImageView x : notPressed){
            notPressed[i].setBackgroundColor(notPressedBackgroundColor);
            i++;
        }
    }

    public char randomChar(char chars[]){
        if(chars.length - randomCharCounter > 0) {
            int randomIndex = random.nextInt(chars.length - randomCharCounter);
            char selectedVowel = chars[randomIndex];
            chars[randomIndex] = chars[(chars.length - randomCharCounter) - 1];

            randomCharCounter++;
            return selectedVowel;
        }else{
            return '/';
        }
    }


    public int randomInt(int[] ints){
        if(ints.length - randomIntCounter > 0) {
            int randomIndex = random.nextInt(ints.length - randomIntCounter);
            int selectedInt = ints[randomIndex];
            ints[randomIndex] = ints[(ints.length - randomIntCounter) - 1];

            randomIntCounter++;
            return selectedInt;
        }else{
            return -1;
        }
    }





    public Drawable randomDrawable(Drawable[] drawables){
        if(drawables.length - randomDrawableCounter > 0) {
            int randomIndex = random.nextInt(drawables.length - randomCharCounter);
            Drawable selectedDrawable = drawables[randomIndex];
            drawables[randomIndex] = drawables[(drawables.length - randomCharCounter) - 1];

            randomDrawableCounter++;
            return selectedDrawable;
        }else{
            return null;
        }

    }

    public String randomCapitalize(String letter){
        boolean toUpper = random.nextBoolean();

        if(toUpper)
            return letter.substring(0).toUpperCase();
        else
            return letter;
    }






}
