package com.example.tourmatebase03;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddExpense extends AppCompatActivity {

    private EditText addExpenseET;
    private Button datebtn, EventsaveExpensebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        addExpenseET = findViewById(R.id.eventExpenseET);
        datebtn = findViewById(R.id.datebtn);
        EventsaveExpensebtn = findViewById(R.id.eventSaveExpensebtn);
    }

    public void addExpense(View view) {
        startActivity(new Intent(AddExpense.this,ExpenseList.class));
    }

    public void DatepickerDialog(View view) {

            final Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
            android.app.DatePickerDialog.OnDateSetListener listener =
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            final  Calendar calendar1 = Calendar.getInstance();
                            calendar1.set(dayOfMonth,month,year);
                            SimpleDateFormat simpleDateFormat =
                                    new SimpleDateFormat("dd,MMM,yyyy");

                            String selecteddate = simpleDateFormat.format(calendar1.getTime());

                            datebtn.setText(dayOfMonth+"/"+(month+1)+"/"+year);


                        }
                    };

            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(this, listener, year, month, day);

            datePickerDialog.show();





    }
}
