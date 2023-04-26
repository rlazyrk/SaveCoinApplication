package project.application.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity_2 extends AppCompatActivity {

    NotificationManagerCompat notificationManagerCompat;
    Notification notification;
    private Button goToMain;
    private Button goToActivity_2;
    private Button goToActivity_3;
    private Button notificationButton;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        goToMain = findViewById(R.id.goToMain);
        goToActivity_2 = findViewById(R.id.goToActivity_2);
        goToActivity_3 = findViewById(R.id.goToActivity_3);
        notificationButton = findViewById(R.id.notification_button);

        notificationButton.setOnClickListener(view -> {
            createNotification();
        });

        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent click = new Intent(Activity_2.this, MainActivity.class);
                startActivity(click);
            }
        });
        goToActivity_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent click = new Intent(Activity_2.this, Activity_3.class);
                startActivity(click);
            }

        });
        goToActivity_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @SuppressLint("MissingPermission")
    private void createNotification() {
        String id = "first_channel_notification";
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = manager.getNotificationChannel(id);
            if (channel == null) {
                channel = new NotificationChannel(id, "channel Title", NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("[Channel description]");
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 1000, 200, 340});
                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                manager.createNotificationChannel(channel);
            }
        }
        Intent notificationIntent = new Intent(this, NotificationActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id)
                .setSmallIcon(android.R.drawable.btn_star)
                .setContentTitle("Доброго Ранку!")
                .setContentText("Ваш бюджет на сьогодні: 100 грн")
                .setPriority(Notification.PRIORITY_HIGH)
                .setVibrate(new long[]{100, 1000, 200, 340})
                .setAutoCancel(false)
                .setTicker("Notification");
        builder.setContentIntent(pendingIntent);
        NotificationManagerCompat m = NotificationManagerCompat.from(getApplicationContext());
        m.notify(1, builder.build());
    }
}

