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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private Button goToMain;
    private Button goToActivity_2;
    private Button goToActivity_3;

    private AlertDialog alertDialog;

    private EditText dateEditText;
    private Calendar calendar;
    private float x1,x2,y1,y2;
    private void scheduleDailyNotification() {
        // Определите время, в которое вы хотите показывать уведомление
        int hour = 17; // Например, в 8 утра
        int minute = 55;

        // Создайте календарь для задания времени
        Calendar notificationTime = Calendar.getInstance();
        notificationTime.set(Calendar.HOUR_OF_DAY, hour);
        notificationTime.set(Calendar.MINUTE, minute);
        notificationTime.set(Calendar.SECOND, 0);

        // Получите системный сервис AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Создайте Intent для вашего BroadcastReceiver
        Intent intent = new Intent(this, MyReceiver.class);

        // Установите действие, которое будет использоваться в вашем BroadcastReceiver
        intent.setAction("your_notification_action");

        // Создайте PendingIntent, который будет запускать BroadcastReceiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Установите повторение на каждый день
        long interval = 24 * 60 * 60 * 1000; // 24 часа
        long startTime = notificationTime.getTimeInMillis();

        // Запланируйте уведомление с использованием AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, notificationTime.getTimeInMillis(), interval, pendingIntent);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateEditText = findViewById(R.id.dateEditText);
        calendar = Calendar.getInstance();
        scheduleDailyNotification();
        goToMain = findViewById(R.id.goToMain);
        goToActivity_2 = findViewById(R.id.goToActivity_2);
        goToActivity_3 = findViewById(R.id.goToActivity_3);

        Button showInputDialogButton = findViewById(R.id.showInputDialogButton);

        Button notificationButton = findViewById(R.id.notificationButton);

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "default")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                notificationManager.notify(0, builder.build());
            }
        });

        showInputDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_input_goal, null);
                builder.setView(dialogView);

                final EditText goalNameEditText = dialogView.findViewById(R.id.goalNameEditText);
                final EditText goalAmountEditText = dialogView.findViewById(R.id.goalAmountEditText);
                final EditText dateEditText = dialogView.findViewById(R.id.dateEditText);

                builder.setPositiveButton("Зберегти", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Отримуємо дані з полів вводу
                        String goalName = goalNameEditText.getText().toString();
                        String goalAmount = goalAmountEditText.getText().toString();
                        String date = dateEditText.getText().toString();

                        // Тут ви можете використовувати отримані дані для збереження цілі в базі даних або іншій логіці
                    }
                });

                builder.setNegativeButton("Скасувати", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
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

