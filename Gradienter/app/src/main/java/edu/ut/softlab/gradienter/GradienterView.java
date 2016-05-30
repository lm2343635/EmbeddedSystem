package edu.ut.softlab.gradienter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by limeng on 4/26/16.
 */
public class GradienterView extends View {
    Bitmap backgroud;
    Bitmap bubble;

    private int bubbleX, bubbleY;

    public void setBubbleX(int x) {
        bubbleX=x;
    }

    public void setBubbleY(int y) {
        bubbleY=y;
    }

    public GradienterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        WindowManager manager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display=manager.getDefaultDisplay();
        DisplayMetrics metrics=new DisplayMetrics();
        display.getMetrics(metrics);
        int screenWidth=metrics.widthPixels;
        int screenHeight=metrics.heightPixels;
        int width=(screenWidth>screenHeight? screenHeight: screenWidth);
        backgroud=Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(backgroud);

        drawBackground(width, canvas);
        drawLine(width, canvas);

        bubble=Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888);
        drawBubble(new Canvas(bubble));
    }

    private void drawBackground(int width, Canvas canvas) {
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width/2, width/2, width/2, paint);
    }

    private void drawLine(int width, Canvas canvas) {
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        canvas.drawLine(0, width/2, width, width/2, paint);
        canvas.drawLine(width/2, 0, width/2, width, paint);
    }

    private void drawBubble(Canvas canvas) {
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(20, 20, 20, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(backgroud, 0, 0, null);
        canvas.drawBitmap(bubble, bubbleX, bubbleY, null);
    }
}
