package com.example.gzde.credit;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by GÖZDE on 29.03.2017.
 */

public class ModifyAccounts extends Activity implements View.OnClickListener{

    private EditText nameText,dateText,creditText;
    private Button update, cancel;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private int mYear,mMonth,mDay;
    private long _id;
    static final int DATE_DIALOG_ID = 0;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_record);

        dbManager = new DBManager(this);
        dbManager.open();

        nameText = (EditText) findViewById(R.id.nameEditText);
        dateText = (EditText) findViewById(R.id.dateEditText);
        creditText = (EditText) findViewById(R.id.creditEditText);

        update = (Button) findViewById(R.id.updateBtn);
        cancel = (Button) findViewById(R.id.cancelBtn);

        Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);

        dateText.setText( sdf.format(c.getTime()));


        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);

            }
        });


        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String date = intent.getStringExtra("date");
        String credit = intent.getStringExtra("credit");

        _id = Long.parseLong(id);

        nameText.setText(name);
        dateText.setText(date);
        creditText.setText(credit);

        update.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }


    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);

        }

        return null;

    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            dateText.setText(new StringBuilder().append(mDay).append("-").append(mMonth+1).append("-").append(mYear));

        }

    };




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateBtn:
                String namee = nameText.getText().toString();
                String datee = dateText.getText().toString();
                String creditt = creditText.getText().toString();

                dbManager.update(_id, namee, datee,creditt);
                this.returnHome();
                break;

            case R.id.cancelBtn:
                this.returnHome();
                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), ListAccount.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }


}
