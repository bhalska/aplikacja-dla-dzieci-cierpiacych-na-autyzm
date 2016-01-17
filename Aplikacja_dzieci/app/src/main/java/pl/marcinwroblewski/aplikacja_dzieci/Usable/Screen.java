package pl.marcinwroblewski.aplikacja_dzieci.Usable;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Marcin Wróblewski on 17.01.16.
 */
public class Screen {

    private Context context;
    private DisplayMetrics displayMetrics;

    public Screen(Context context) {
        this.context = context;
        displayMetrics = context.getResources().getDisplayMetrics();
    }


    public float getWidth() {
        return displayMetrics.widthPixels / displayMetrics.density;
    }

    public float getHeight() {
        return displayMetrics.heightPixels / displayMetrics.density;
    }

}
