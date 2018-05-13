package com.example.lenovo.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SetReminderWindow extends Activity {

    DatePicker datePicker; //Set your date
    TimePicker timePicker; //set required time
    Button setAlarm;
    final static int RQS_1 = 4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder_window);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.8));



        datePicker = findViewById(R.id.pickerdate);
        timePicker = findViewById(R.id.pickertime);

        Calendar now = Calendar.getInstance();
        datePicker.init(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), null);

        timePicker.setIs24HourView(true);
        timePicker.setHour(now.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(now.get(Calendar.MINUTE));

    }

    public void clickActionAlarm(View view) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(),
                timePicker.getMinute(), 00);

        Toast.makeText(getApplicationContext(), "Reminder set!", Toast.LENGTH_LONG).show();
        setAlarm(calendar);

//        if(calendar.compareTo(current) <= 0){
////The set Date/Time already added show an toast.
//            Toast.makeText(getApplicationContext(), "Invalid Date/Time", Toast.LENGTH_LONG).show();
//        }
//        else{
//            setAlarm(calendar);
//        }
     //   Intent intent = new Intent(this,AddListActivity.class);
    //    startActivity(intent);
        onBackPressed();
    }

    private void setAlarm(Calendar targetCal){
        //Toast.makeText(getApplicationContext(),"Reminder set!", Toast.LENGTH_LONG);
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class); //ALARM IS SET
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }
}