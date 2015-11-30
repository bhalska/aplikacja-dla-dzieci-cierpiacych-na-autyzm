package pl.marcinwroblewski.aplikacja_dzieci.Drawing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import pl.marcinwroblewski.aplikacja_dzieci.Vowels.VowelDraw;

/**
 * Created by Marcin Wroblewski on 2014-11-28.
 */
public class DrawingView extends View {
    static Path path;
    static Bitmap mBitmap;
    static Canvas mCanvas;

    public DrawingView(Context context) {
        super(context);
        path = new Path();
        mBitmap = Bitmap.createBitmap(820, 820, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    private ArrayList<PathWithPaint> _graphics1 = new ArrayList<PathWithPaint>();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PathWithPaint pp = new PathWithPaint();
        mCanvas.drawPath(path, VowelDraw.mPaint);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            path.moveTo(event.getX(), event.getY());
            path.lineTo(event.getX(), event.getY());
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            path.lineTo(event.getX(), event.getY());
            pp.setPath(path);
            pp.setmPaint(VowelDraw.mPaint);
            _graphics1.add(pp);
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (_graphics1.size() > 0) {
            canvas.drawPath(
                    _graphics1.get(_graphics1.size() - 1).getPath(),
                    _graphics1.get(_graphics1.size() - 1).getmPaint());
        }
    }





}
