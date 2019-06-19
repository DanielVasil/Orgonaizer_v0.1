package com.daniel.trainer5;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

public class RedactActivity extends AppCompatActivity {

    DataBase dbHelper;
    private EditText userTaskGet;
    private ArrayAdapter<String> my_adapterT;
    private ArrayAdapter<String> my_adapterD;
    private ListView all_tasks;
    private CalendarView calendar;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redact);
        userTaskGet = findViewById(R.id.userTaskGet);
        dbHelper = new DataBase(this);
        all_tasks = findViewById(R.id.tasks_list);
        calendar = findViewById(R.id.calendar);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = year+"/"+month+"/"+dayOfMonth;
            }
        });
    }

    public void onClickBtn(View v) {
        Intent intent = new Intent(RedactActivity.this, MainActivity.class);
        switch (v.getId()) {
            case R.id.abort:
                startActivity(intent);
                break;

            case R.id.add:
                String task = String.valueOf(userTaskGet.getText());
                dbHelper.insertData(task,date);
                loadAllTasks();
                //loadAllDate();
                startActivity(intent);
                break;
        }

    }


    private void loadAllTasks() {
        ArrayList<String> taskList = dbHelper.getAllTasks();
        ArrayList<String> dateList = dbHelper.getAllDate();
        if (my_adapterT == null) {
            my_adapterT = new ArrayAdapter<String>(this, R.layout.row, R.id.txt_task, taskList);
            my_adapterD = new ArrayAdapter<String>(this, R.layout.row, R.id.dateText, taskList);
            all_tasks.setAdapter(my_adapterD);
            all_tasks.setAdapter(my_adapterT);
        } else {
            my_adapterT.clear();
            my_adapterT.addAll(taskList);
            my_adapterT.notifyDataSetChanged();
        }
    }

   /* private void loadAllDate(){
        ArrayList<String> dateList = dbHelper.getAllDate();
        if (my_adapter == null) {
            my_adapter = new ArrayAdapter<String>(this,R.layout.row,R.id.dateText, dateList);
            all_tasks.setAdapter(my_adapter);
        } else {
            my_adapter.clear();
            my_adapter.addAll(dateList);
            my_adapter.notifyDataSetChanged();
        }
    }*/
}





 /*   AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Добавление нового задания")
            .setMessage("Что бы вы хотели добавить?")
            .setView(userTaskGet)
            .setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String task = String.valueOf(userTaskGet.getText());
                    dbHelper.insertData(task);
                    loadAllTasks();
                }
            })
            .setNegativeButton("Ничего", null)
            .create();
            dialog.show();
                    return true; */