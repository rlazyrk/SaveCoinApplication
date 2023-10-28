package project.application.myapplication;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private Button goToMain;
    private Button goToActivity_2;
    private Button goToActivity_3;
    private Button addExpenses;
    private Button addBudget;
    private EditText enterBudgetOrExpenses;
    private Integer budget = 0;
    private TextView currentBudget;
    private SpendingsDBOpenHelper mDB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDB = new SpendingsDBOpenHelper(this);

        goToMain = findViewById(R.id.goToMain);
        goToActivity_2 = findViewById(R.id.goToActivity_2);
        goToActivity_3 = findViewById(R.id.goToActivity_3);
        addExpenses = findViewById(R.id.addExpenses);
        addBudget = findViewById(R.id.addBudget);
        enterBudgetOrExpenses = findViewById(R.id.enterBudgetOrExpenses);
        currentBudget = findViewById(R.id.currentBudget);

        budget = mDB.query(7);
        currentBudget.setText(budget.toString());

        addExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enterBudgetOrExpenses.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this, R.string.emptyField, Toast.LENGTH_LONG).show();
                } else {
                    budget -= Integer.parseInt(enterBudgetOrExpenses.getText().toString().trim());
                    currentBudget.setText(budget.toString());
                }
            }
        });

        addBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enterBudgetOrExpenses.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this, R.string.emptyField, Toast.LENGTH_LONG).show();
                } else {
                    mDB.insert(Integer.parseInt(enterBudgetOrExpenses.getText().toString().trim()), 7);
                    budget = mDB.query(7);
                    currentBudget.setText(budget.toString());
                }
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
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage("Are you sure to exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();

                    }
                })
                .setNegativeButton("No",null)
                .show();
    }
}
