package edu.ut.softlab.raspberrypiled;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private int mLedNumber;

    final int LED1=0;
    final int LED2=1;
    final int LED3=2;
    final int LED4=3;
    final int LED5=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton ledToggleButton1=(ToggleButton)findViewById(R.id.toggle_button_led_1);
        ToggleButton ledToggleButton2=(ToggleButton)findViewById(R.id.toggle_button_led_2);
        ToggleButton ledToggleButton3=(ToggleButton)findViewById(R.id.toggle_button_led_3);
        ToggleButton ledToggleButton4=(ToggleButton)findViewById(R.id.toggle_button_led_4);
        ToggleButton ledToggleButton5=(ToggleButton)findViewById(R.id.toggle_button_led_5);

        ledToggleButton1.setOnCheckedChangeListener(this);
        ledToggleButton2.setOnCheckedChangeListener(this);
        ledToggleButton3.setOnCheckedChangeListener(this);
        ledToggleButton4.setOnCheckedChangeListener(this);
        ledToggleButton5.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.toggle_button_led_1:
                mLedNumber=LED1;
                break;

            case R.id.toggle_button_led_2:
                mLedNumber=LED2;
                break;

            case R.id.toggle_button_led_3:
                mLedNumber=LED3;
                break;

            case R.id.toggle_button_led_4:
                mLedNumber=LED4;
                break;

            case R.id.toggle_button_led_5:
                mLedNumber=LED5;
                break;

        }
        HttpGetTask task=new HttpGetTask(this);
        task.execute(mLedNumber, isChecked? 1: 0);
    }
}
