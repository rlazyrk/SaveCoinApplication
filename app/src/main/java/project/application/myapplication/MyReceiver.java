package project.application.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Objects;
import java.util.Random;

public class MyReceiver extends BroadcastReceiver {
    String[] rules = {
            "Вчіться розумно витрачати гроші.",
            "Не купуйте те, що вам не потрібно, або те, що ви не можете собі дозволити.",
            "Завжди порівнюйте ціни і шукайте знижки.",
            "Створіть собі бюджет і дотримуйтеся його.",
            "Відокремлюйте свої потреби від своїх бажань і встановлюйте собі фінансові цілі.",
            "Відкладайте частину своїх грошей на майбутнє.",
            "Чим раніше ви почнете заощаджувати, тим краще.",
            "Плануйте свої великі покупки.",
            "Не купуйте щось дороге по імпульсу, а ретельно обдумайте своє рішення.",
            "Створіть собі графік платежів і дотримуйтеся його.",
            "Шукайте додаткові джерела доходу.",
            "Не бійтеся ризикувати, але робіть це обґрунтовано.",
            "Не втрачайте гроші через емоції.",
            "Відкладайте частину своїх грошей на непередбачені витрати.",
            "Ніколи не надійтесь на одне джерело доходу.",
            "Розвивай грошове мислення.",
            "Зберігай мінімум 20% свого доходу.",
            "Не збільшуй витрати паралельно із доходом.",
            "Не витрачайте гроші, намагаючись справити враження.",
            "Не припиняй вчитись і вкладати у себе.",
            "Уникайте боргів будь-якою ціною"
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("your_notification_action")) {

            String text = intent.getStringExtra("title");
            String isDaily = intent.getStringExtra("text");
            int id = Integer.parseInt(intent.getStringExtra("id"));
            createNotificationChannel(context);
            showNotification(context,text,isDaily,id);
        }
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MyNotificationChannel";
            String description = "Channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("default", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(Context context,String title,String isDaily,int id) {
        String text;
        if (Objects.equals(isDaily, "day")) {
            Random random = new Random();
            int randomIndex = random.nextInt(rules.length);
            text=rules[randomIndex];
        }else{
            text = isDaily;
        }

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default")
                .setSmallIcon(R.drawable.savecoin)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());
    }
}
