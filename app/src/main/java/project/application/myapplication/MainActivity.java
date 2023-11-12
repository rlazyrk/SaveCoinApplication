package project.application.myapplication;

import static java.lang.Math.abs;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import project.application.myapplication.allForGoals.Goal;
import project.application.myapplication.allForGoals.GoalAdapter;
import project.application.myapplication.allForGoals.GoalsDBOpenHelper;



//FIXME не працює зміна цілі та додавання будь чого в ціль
public class MainActivity extends AppCompatActivity {

    private Button goToMain;
    private Button goToActivity_2;
    private Button goToActivity_3;
    private RecyclerView recyclerView;
    private GoalAdapter goalAdapter;
    private List<Goal> goalList;
    private GoalsDBOpenHelper goalsDBOpenHelper;

    private EditText dateEditText;
    private Calendar calendar;
    private float x1,x2,y1,y2;


    private void scheduleDailyNotification() {
        // Определите время, в которое вы хотите показывать уведомление
        int hour = 17;
        int minute = 55;

        // Создайте календарь для задания времени
        Calendar notificationTime = Calendar.getInstance();
        notificationTime.set(Calendar.HOUR_OF_DAY, hour);
        notificationTime.set(Calendar.MINUTE, minute);
        notificationTime.set(Calendar.SECOND, 0);



        // Get the random element from the array


        // Получите системный сервис AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Создайте Intent для вашего BroadcastReceiver
        Intent intent = new Intent(this, MyReceiver.class);

        // Установите действие, которое будет использоваться в вашем BroadcastReceiver
        intent.setAction("your_notification_action");
        intent.putExtra("title", "MyDailyNotification");
        intent.putExtra("text", "day");
        intent.putExtra("id",Integer.toString(0));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);


        long interval = 24 * 60 * 60 * 1000; // 24 часа
        long startTime = notificationTime.getTimeInMillis();


        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, 10000, pendingIntent);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goalsDBOpenHelper = new GoalsDBOpenHelper(this);
        setContentView(R.layout.activity_main);
        dateEditText = findViewById(R.id.dateEditText);
        calendar = Calendar.getInstance();
      
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        goalList = goalsDBOpenHelper.getAllGoals();
        goalAdapter = new GoalAdapter(goalList, this);
        recyclerView.setAdapter(goalAdapter);

        scheduleDailyNotification();

        goToMain = findViewById(R.id.goToMain);
        goToActivity_2 = findViewById(R.id.goToActivity_2);
        goToActivity_3 = findViewById(R.id.goToActivity_3);

        Button showInputDialogButton = findViewById(R.id.showInputDialogButton);

//        Button notificationButton = findViewById(R.id.notificationButton);

//        notificationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
//                    notificationManager.createNotificationChannel(channel);
//                }
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "default")
//                        .setSmallIcon(R.drawable.ic_launcher_background)
//                        .setContentTitle("My notification")
//                        .setContentText("Hello World!")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//                notificationManager.notify(0, builder.build());
//            }
//        });

        showInputDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_input_goal, null);
                builder.setView(dialogView);

                EditText goalNameEditText = dialogView.findViewById(R.id.goalNameEditText);
                EditText goalAmountEditText = dialogView.findViewById(R.id.goalAmountEditText);
                EditText dateEditText = dialogView.findViewById(R.id.dateEditText);
                Button saveButton = dialogView.findViewById(R.id.saveInputGoal);
                Button cancelButton = dialogView.findViewById(R.id.cancelInputGoal);
                AlertDialog alertDialog = builder.create();

                dateEditText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //не факт але можливо можна видалити 3 рядки знизу
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        // оновлює текст
                                        calendar.set(year, monthOfYear, dayOfMonth);
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                        dateEditText.setText(dateFormat.format(calendar.getTime()));
                                    }
                                }, year, month, day);
                        datePickerDialog.show();
                    }
                });

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String goalName = goalNameEditText.getText().toString();
                        String goalAmountString = goalAmountEditText.getText().toString();
                        int goalAmount = Integer.parseInt(goalAmountString);
                        String endDate = dateEditText.getText().toString();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String startDate = dateFormat.format(Calendar.getInstance().getTime());
                        Goal newGoal = new Goal(goalName, goalAmount, 0, endDate, startDate);
                        goalsDBOpenHelper.insertGoal(newGoal);
                        goalList.clear();
                        goalList.addAll(goalsDBOpenHelper.getAllGoals());
                        goalAdapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goalNameEditText.setText("");
                        goalAmountEditText.setText("");
                        dateEditText.setText("");
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });


        goToActivity_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent click = new Intent(MainActivity.this, Activity_2.class);
                startActivity(click);
            }
        });

        goToActivity_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent click = new Intent(MainActivity.this, Activity_3.class);
                startActivity(click);
            }
        });


        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                if(x1 < x2 && abs(x1-x2)>300){
                    Intent i = new Intent(MainActivity.this, Activity_3.class);
                    startActivity(i);
                    overridePendingTransition(R.xml.slide_right_start, R.xml.slide_right_end);
                }
        }
        return false;
    }

}

