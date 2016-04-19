package edu.ut.softlab.intentsample;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by limeng on 4/19/16.
 */
public class OrientationActivity extends AppCompatActivity implements SensorEventListener {

    private CheckBox mCheckBoxOrientation;
    private TextView mAzimuthText, mPicthText, mRollText;
    private float [] mAccerlerationValue=new float[3];
    private float [] mGeoMangeticValue=new float[3];
    private float [] mOrientationValue=new float[3];
    private float [] mlnRotationMatrix=new float[9];
    private float [] mOutRotationMatrix=new float[9];
    private float [] mLnclinationMatrix=new float[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation);

        SensorManager sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor accelerationSensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor mageneticSensor=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, accelerationSensor, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, mageneticSensor, SensorManager.SENSOR_DELAY_UI);

        mAzimuthText=(TextView)findViewById(R.id.text_view_azimuth);
        mRollText=(TextView)findViewById(R.id.text_view_roll);
        mPicthText=(TextView)findViewById(R.id.text_view_pitch);

        Button buttonOrientation=(Button)findViewById(R.id.button_orientation);
        buttonOrientation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(!mCheckBoxOrientation.isChecked()) {
                    SensorManager.getRotationMatrix(mlnRotationMatrix, mLnclinationMatrix, mAccerlerationValue, mGeoMangeticValue);
                    SensorManager.remapCoordinateSystem(mlnRotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, mOutRotationMatrix);
                    SensorManager.getOrientation(mOutRotationMatrix, mOrientationValue);

                    String azimuthText=String.valueOf(Math.floor(Math.toDegrees((double)mOrientationValue[0])));
                    String pitchText=String.valueOf(Math.floor(Math.toDegrees((double)mOrientationValue[1])));
                    String rollText=String.valueOf(Math.floor(Math.toDegrees((double)mOrientationValue[2])));

                    mAzimuthText.setText(azimuthText);
                    mPicthText.setText(pitchText);
                    mRollText.setText(rollText);
                }
            }
        });
        mCheckBoxOrientation=(CheckBox)findViewById(R.id.checkBox_orientation);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                mGeoMangeticValue=event.values.clone();
                break;
            case Sensor.TYPE_ACCELEROMETER:
                mAccerlerationValue=event.values.clone();
                break;
        }
        if(mCheckBoxOrientation.isChecked()) {
            SensorManager.getRotationMatrix(mlnRotationMatrix, mLnclinationMatrix, mAccerlerationValue, mGeoMangeticValue);
            SensorManager.remapCoordinateSystem(mlnRotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, mOutRotationMatrix);
            SensorManager.getOrientation(mOutRotationMatrix, mOrientationValue);

            String azimuthText=String.valueOf(Math.floor(Math.toDegrees((double)mOrientationValue[0])));
            String pitchText=String.valueOf(Math.floor(Math.toDegrees((double)mOrientationValue[1])));
            String rollText=String.valueOf(Math.floor(Math.toDegrees((double)mOrientationValue[2])));

            mAzimuthText.setText(azimuthText);
            mPicthText.setText(pitchText);
            mRollText.setText(rollText);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
