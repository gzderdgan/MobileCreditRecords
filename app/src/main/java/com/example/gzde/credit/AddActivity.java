package com.example.gzde.credit;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddActivity extends ActionBarActivity {
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(AddActivity.this,"No Customer Records",
                Toast.LENGTH_SHORT).show();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.home);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.add_record:

                fragment = new DatePickerFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.commit();

                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;

    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{


        private DBManager dbManager;

        String namee;
        String datee;
        String creditt;
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            final View view= inflater.inflate(R.layout.fragment_add, container, false);


            setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Holo);
            final EditText date = (EditText) view.findViewById(R.id.dateEditText);
            final EditText name = (EditText) view.findViewById(R.id.nameEditText);
            final EditText credit = (EditText) view.findViewById(R.id.creditEditText);

            Button add=(Button)view.findViewById(R.id.addBtn);
            Button reset=(Button)view.findViewById(R.id.resetBtn);

            dbManager = new DBManager(getActivity());
            dbManager.open();

            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DialogFragment dFragment = new DatePickerFragment();

                    dFragment.show(getFragmentManager(), "Date Picker");
                }
            });

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    namee = name.getText().toString();
                    datee = date.getText().toString();
                    creditt = credit.getText().toString();

                    dbManager.insert(namee, datee, creditt);

                    Intent main = new Intent(getActivity(), ListAccount.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(main);


                }
            });

            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    name.getText().clear();
                    date.getText().clear();
                    credit.getText().clear();

                }
            });

            return view;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT,this,year,month,day);
            return  dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){

            EditText tv = (EditText) getActivity().findViewById(R.id.dateEditText);
            int actualMonth = month+1;

            tv.setText( year + "-" + actualMonth + "-" + day );

            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();

            DateFormat df_full = DateFormat.getDateInstance(DateFormat.FULL);
            String df_full_str = df_full.format(chosenDate);
            }
    }
}