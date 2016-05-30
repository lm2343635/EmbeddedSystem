package edu.ut.softlab.gradienter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private int maxAngle=30;

    private GradienterView gradienterView;
    private SensorManager sensorManager;

    private TextView yAngleTextView;
    private TextView zAngleTextView;
    private TextView angleView;

    private EditText maxAngleEditText;
    private Button maxAngleButton;

    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gradienterView=(GradienterView)findViewById(R.id.gradienter_view);
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        yAngleTextView=(TextView)findViewById(R.id.yAngleTextView);
        zAngleTextView=(TextView)findViewById(R.id.zAngleTextView);
        angleView=(TextView)findViewById(R.id.angleTextView);
        decimalFormat=new DecimalFormat("#.00");

        maxAngleEditText=(EditText)findViewById(R.id.maxAngleEditText);
        maxAngleEditText.setText(String.valueOf(maxAngle));
        maxAngleButton=(Button)findViewById(R.id.maxAngleButton);
        maxAngleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maxAngle=Integer.parseInt(maxAngleEditText.getText().toString());
            }
        });
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
                double angle=Math.sqrt(yAngle*yAngle+zAngle*zAngle);
                int x=(gradienterView.backgroud.getWidth()-gradienterView.bubble.getWidth())/2;
                int y=(gradienterView.backgroud.getHeight()-gradienterView.bubble.getHeight())/2;
                yAngleTextView.setText(decimalFormat.format(yAngle)+"˚");
                zAngleTextView.setText(decimalFormat.format(zAngle)+"˚");
                angleView.setText(decimalFormat.format(angle)+"˚");
                if(Math.abs(zAngle)<=maxAngle) {
                    x+=(int)((gradienterView.backgroud.getWidth()-gradienterView.bubble.getWidth())/2*zAngle/maxAngle);
                } else if(zAngle>maxAngle) {
                    x=0;
                } else {
                    x=gradienterView.backgroud.getWidth()-gradienterView.bubble.getWidth();
                }
                if(Math.abs(yAngle)<+maxAngle) {
                    y+=(int)((gradienterView.backgroud.getHeight()-gradienterView.bubble.getHeight())/2*yAngle/maxAngle);
                } else if(yAngle>maxAngle) {
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
            default:
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
