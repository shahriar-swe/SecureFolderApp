package com.example.securefolder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimeActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private Button showTimeButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        timePicker = findViewById(R.id.timePickerId);
        showTimeButton = findViewById(R.id.showTimeButtonId);
        resultTextView = findViewById(R.id.textViewId);

        timePicker.setIs24HourView(true);

        showTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = timePicker.getCurrentHour()+" : "+timePicker.getCurrentMinute();
                resultTextView.setText("Current Time Is :"+time);
            }
        });
    }
}
