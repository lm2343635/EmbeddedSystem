package edu.ut.softlab.raspberrypiinterface;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String mSelectedUid=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button uidButton=(Button)findViewById(R.id.button_uid);
        Spinner uidSpinner=(Spinner)findViewById(R.id.spinner_uid);
        Button processButton=(Button)findViewById(R.id.button_process);

        uidSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int arg2, long arg3) {
                Spinner spinner=(Spinner)parent;
                mSelectedUid=(String)spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        uidButton.setOnClickListener(this);
        processButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button_uid) {
            mSelectedUid=null;
        }
        HttpGetTask task=new HttpGetTask(this);
        task.execute(mSelectedUid);
    }
}
