package pt.isel.poo.counterapp;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CircleView extends View {

    Paint paint;
    int radius, min, max, step;
    OnResizeListener listener;

    public CircleView(Context context, int init, int min, int max, int step) {
        super(context);
        radius = init;
        this.min = min;
        this.max = max;
        this.step = step;
        paint = new Paint();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        radius = 20;
        this.min = 20;
        this.max = 200;
        this.step = 5;
        paint = new Paint();
    }

    public void setOnResizeListener(OnResizeListener listener) {
        this.listener = listener;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        System.out.println("On CircleView.onDraw");
        int middleX = canvas.getWidth()/2;
        int middleY = canvas.getHeight()/2;
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawRect(1, 1, canvas.getWidth()-1, canvas.getHeight()-1, paint);
        paint.setColor(Color.BLUE);
        canvas.drawLine(0, middleY, getWidth(), middleY, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawCircle(middleX, middleY, radius, paint);
    }

    int previousX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("In CircleView.onTouchEvent()");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                previousX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                if (previousX < (int)event.getX() && radius+step<=max) {
                    radius += step;
                }
                if (previousX > (int)event.getX() && radius-step>=min) {
                    radius -= step;
                }
                break;
        }
        listener.onResize(radius);
        invalidate();
        return true;
    }
}
