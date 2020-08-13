package com.example.securefolder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class DateActivity extends AppCompatActivity {

    private TextView textView;
    private Button showSelectedDateButton;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        textView = findViewById(R.id.textViewId);
        showSelectedDateButton = findViewById(R.id.showButtonId);
        datePicker = findViewById(R.id.datePickerId);

        textView.setText(CurrentDate());

        showSelectedDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(CurrentDate());
            }
        });
    }


    String CurrentDate(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Current Date :");

        stringBuilder.append(datePicker.getDayOfMonth()+"/");
        stringBuilder.append(datePicker.getMonth()+1+"/");
        stringBuilder.append(datePicker.getYear());

        return stringBuilder.toString();
    }

}
