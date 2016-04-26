package edu.ut.softlab.touchsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private TextView mTouchTypeText;
    private TextView mTouchPointText1;
    private TextView mTouchPointText2;
    private TextView mTouchLengthText;

    private final static int NONE=0;
    private final static int TOUCH=1;
    private final static int DRAG=2;
    private final static int PINCH=3;
    private int mTouchMode=NONE;

    private float mDragStartX=0.0f;
    private float mDragStartY=0.0f;
    private float mPinchStartDistance=0.0f;

    private String mTouchTypeString="";
    private String mtouchPoint1String="";
    private String mTouchPoint2String="";
    private String mTouchLengthString="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTouchTypeText=(TextView)findViewById(R.id.text_view_touch_type);
        mTouchPointText1=(TextView)findViewById(R.id.text_view_touch_point_1);
        mTouchPointText2=(TextView)findViewById(R.id.text_view_touch_point_2);
        mTouchLengthText=(TextView)findViewById(R.id.text_view_touch_length);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()&MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                if(event.getPointerCount()>=2) {
                    mPinchStartDistance=getPinchDistance(event);
                    if(mPinchStartDistance>50f) {
                        mTouchMode=PINCH;
                        mTouchTypeString="PINCH";
                        mtouchPoint1String="x: "+event.getX(0)+", y: "+event.getY(0);
                        mTouchPoint2String="x: "+event.getX(1)+", y: "+event.getY(1);
                        mTouchLengthString=String.valueOf(mPinchStartDistance);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(mTouchMode==PINCH&&mPinchStartDistance>0) {
                    mTouchTypeString="PINCH";
                    mtouchPoint1String="x: "+event.getX(0)+", y: "+event.getY(0);
                    mTouchPoint2String="x: "+event.getX(1)+", y: "+event.getY(1);
                    mTouchLengthString=String.valueOf(mPinchStartDistance);
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_POINTER_UP:
                if(mTouchMode==PINCH) {
                    mTouchTypeString="PINCH";
                    mtouchPoint1String="x: "+event.getX(0)+", y: "+event.getY(0);
                    mTouchPoint2String="x: "+event.getX(1)+", y: "+event.getY(1);
                    mTouchLengthString=String.valueOf(mPinchStartDistance);
                    mTouchMode=NONE;
                }
                break;
        }

        switch (event.getAction()&MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if(mTouchMode==NONE&&event.getPointerCount()==1) {
                    mTouchMode=TOUCH;
                    mDragStartX=event.getX(0);
                    mDragStartY=event.getY(0);
                    mTouchTypeString="TOUCH";
                    mtouchPoint1String="x: "+mDragStartX+", y: "+mDragStartY;
                    mTouchPoint2String="x: "+event.getX(0)+", y: "+event.getY(0);
                    mTouchLengthString=String.valueOf(getDragDistance(event));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(mTouchMode==DRAG||mTouchMode==TOUCH) {
                    mTouchMode=DRAG;
                    mTouchTypeString="DRAG";
                    mtouchPoint1String="x: "+mDragStartX+", y: "+mDragStartY;
                    mTouchPoint2String="x: "+event.getX(0)+", y: "+event.getY(0);
                    mTouchLengthString=String.valueOf(getDragDistance(event));
                }
                break;
            case MotionEvent.ACTION_UP:
                if(mTouchMode==TOUCH) {
                    mTouchTypeString="TOUCH";
                } else if(mTouchMode==DRAG) {
                    mTouchTypeString="DRAG";
                }
                mtouchPoint1String="x: "+mDragStartX+", y: "+mDragStartY;
                mTouchPoint2String="x: "+event.getX(0)+", y: "+event.getY(0);
                mTouchLengthString=String.valueOf(getDragDistance(event));
                mTouchMode=NONE;
                break;
        }

        mTouchTypeText.setText(mTouchTypeString);
        mTouchPointText1.setText(mtouchPoint1String);
        mTouchPointText2.setText(mTouchPoint2String);
        mTouchLengthText.setText(mTouchLengthString);
        return super.onTouchEvent(event);
    }

    private float getDragDistance(MotionEvent event) {
        float touchX0=event.getX(0);
        float touchY0=event.getY(0);
        float dragLenghtX=touchX0-mDragStartX;
        float dragLengthY=touchY0-mDragStartY;
        return (float) Math.sqrt(dragLenghtX*dragLenghtX+dragLengthY*dragLengthY);
    }

    private float getPinchDistance(MotionEvent event) {
        float x=event.getX(0)-event.getX(1);
        float y=event.getY(0)-event.getY(1);
        return (float) Math.sqrt(x*x+y*y);
    }
}
