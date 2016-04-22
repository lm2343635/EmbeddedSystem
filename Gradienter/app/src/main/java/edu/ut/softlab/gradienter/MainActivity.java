package edu.ut.softlab.gradienter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private final static int MAX_ANGLE=30;

    private GradienterView gradienterView;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gradienterView=(GradienterView)findViewById(R.id.gradienter_view);
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float [] values=event.values;
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ORIENTATION:
                float yAngle=values[1];
                float zAngle=values[2];
                int x=(gradienterView.backgroud.getWidth()-gradienterView.bubble.getWidth())/2;
                int y=(gradienterView.backgroud.getHeight()-gradienterView.bubble.getHeight())/2;
                if(Math.abs(zAngle)<=MAX_ANGLE) {
                    x+=(int)((gradienterView.backgroud.getWidth()-gradienterView.bubble.getWidth())/2*zAngle/MAX_ANGLE);
                } else if(zAngle>MAX_ANGLE) {
                    x=0;
                } else {
                    x=gradienterView.backgroud.getWidth()-gradienterView.bubble.getWidth();
                }
                if(Math.abs(yAngle)<+MAX_ANGLE) {
                    y+=(int)((gradienterView.backgroud.getHeight()-gradienterView.bubble.getHeight())/2*yAngle/MAX_ANGLE);
                } else if(yAngle>MAX_ANGLE) {
                    y=gradienterView.backgroud.getHeight()-gradienterView.bubble.getHeight();
                } else {
                    y=0;
                }
                if(isContain(x, y)) {
                    gradienterView.setBubbleX(x);
                    gradienterView.setBubbleY(y);
                }
                gradienterView.postInvalidate();
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private boolean isContain(int x, int y) {
        int bubbleX=x+gradienterView.bubble.getWidth()/2;
        int bubbleeY=y+gradienterView.bubble.getWidth()/2;
        int backX=gradienterView.backgroud.getWidth()/2;
        int backY=gradienterView.backgroud.getWidth()/2;
        double distance=Math.sqrt((bubbleX-backX)*(bubbleX-backX)+(bubbleeY-backY)*(bubbleeY-backY));
        return distance<(gradienterView.backgroud.getWidth()-gradienterView.bubble.getWidth())/2;
    }
}
