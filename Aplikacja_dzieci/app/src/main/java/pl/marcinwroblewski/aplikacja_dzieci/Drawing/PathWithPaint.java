package pl.marcinwroblewski.aplikacja_dzieci.Drawing;

import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by Marcin Wroblewski on 2014-11-28.
 */
public class PathWithPaint {
    private Path path;

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    private Paint mPaint;

    public Paint getmPaint() {
        return mPaint;
    }

    public void setmPaint(Paint mPaint) {
        this.mPaint = mPaint;
    }
}