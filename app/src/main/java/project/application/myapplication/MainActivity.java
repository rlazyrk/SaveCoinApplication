package project.application.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    float x1, x2, y1, y2;
    private Integer expensesType_1 = 0;
    private Integer expensesType_2 = 0;
    private Integer expensesType_3 = 0;
    private Integer expensesType_4 = 0;
    private Integer expensesType_5 = 0;

    private Button btnType_1;
    private Button btnType_2;
    private Button btnType_3;
    private Button btnType_4;
    private Button btnType_5;
    private EditText editType_1;
    private TextView viewType_1;
    private TextView viewType_2;
    private TextView viewType_3;
    private TextView viewType_4;
    private TextView viewType_5;

    private TextView viewSumOfAll;
    private Button goToMain;
    private Button goToActivity_2;
    private Button goToActivity_3;
    private SpendingsDBOpenHelper mDB;
    private Integer sumOfAllint = 0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goToMain = findViewById(R.id.goToMain);
        goToActivity_2 = findViewById(R.id.goToActivity_2);
        goToActivity_3 = findViewById(R.id.goToActivity_3);
        btnType_1 = findViewById(R.id.addType_1);
        btnType_2 = findViewById(R.id.addType_2);
        btnType_3 = findViewById(R.id.addType_3);
        btnType_4 = findViewById(R.id.addType_4);
        btnType_5 = findViewById(R.id.addType_5);
        editType_1 = findViewById(R.id.enterType_1);
        viewType_1 = findViewById(R.id.viewType_1);
        viewType_2 = findViewById(R.id.viewType_2);
        viewType_3 = findViewById(R.id.viewType_3);
        viewType_4 = findViewById(R.id.viewType_4);
        viewType_5 = findViewById(R.id.viewType_5);
        viewSumOfAll = findViewById(R.id.viewSumOfAll);
        mDB = new SpendingsDBOpenHelper(this);
        viewType_1.setText(expensesType_1.toString());
        viewType_2.setText(expensesType_2.toString());
        viewType_3.setText(expensesType_3.toString());
        viewType_4.setText(expensesType_4.toString());
        viewType_5.setText(expensesType_5.toString());
        sumOfAllint = mDB.querySum(5);
        expensesType_1 = mDB.query(1);
        expensesType_2 = mDB.query(2);
        expensesType_3 = mDB.query(3);
        expensesType_4 = mDB.query(4);
        expensesType_5 = mDB.query(5);
        viewType_1.setText(expensesType_1.toString());
        viewType_2.setText(expensesType_2.toString());
        viewType_3.setText(expensesType_3.toString());
        viewType_4.setText(expensesType_4.toString());
        viewType_5.setText(expensesType_5.toString());





        viewSumOfAll.setText(sumOfAllint.toString());
        btnType_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editType_1.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this, R.string.emptyField, Toast.LENGTH_LONG).show();
                } else {
                    mDB.insert(Integer.parseInt(editType_1.getText().toString().trim()), 1);
                    expensesType_1 = mDB.query(1);
                    viewType_1.setText(expensesType_1.toString());
                    sumOfAllint = mDB.querySum(5);
                    viewSumOfAll.setText(sumOfAllint.toString());
                }
            }
        });
        btnType_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editType_1.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this, R.string.emptyField, Toast.LENGTH_LONG).show();
                } else {
                    mDB.insert(Integer.parseInt(editType_1.getText().toString().trim()), 2);
                    expensesType_2 = mDB.query(2);
                    viewType_2.setText(expensesType_2.toString());
                    sumOfAllint = mDB.querySum(5);
                    viewSumOfAll.setText(sumOfAllint.toString());
                }
            }
        });
        btnType_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editType_1.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this, R.string.emptyField, Toast.LENGTH_LONG).show();
                } else {
                    mDB.insert(Integer.parseInt(editType_1.getText().toString().trim()), 3);
                    expensesType_3 = mDB.query(3);
                    viewType_3.setText(expensesType_3.toString());
                    sumOfAllint = mDB.querySum(5);
                    viewSumOfAll.setText(sumOfAllint.toString());
                }
            }
        });
        btnType_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editType_1.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this, R.string.emptyField, Toast.LENGTH_LONG).show();
                } else {
                    mDB.insert(Integer.parseInt(editType_1.getText().toString().trim()), 4);
                    expensesType_4 = mDB.query(4);
                    viewType_4.setText(expensesType_4.toString());
                    sumOfAllint = mDB.querySum(5);
                    viewSumOfAll.setText(sumOfAllint.toString());
                }
            }
        });

        btnType_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editType_1.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this, R.string.emptyField, Toast.LENGTH_LONG).show();
                } else {
                    mDB.insert(Integer.parseInt(editType_1.getText().toString().trim()), 5);
                    expensesType_5 = mDB.query(5);
                    viewType_5.setText(expensesType_5.toString());
                    sumOfAllint = mDB.querySum(5);
                    viewSumOfAll.setText(sumOfAllint.toString());
                }
            }
        });

        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        goToActivity_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent click = new Intent(MainActivity.this, Activity_3.class);
                startActivity(click);
            }

        });
        goToActivity_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent click = new Intent(MainActivity.this, Activity_2.class);
                startActivity(click);
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
                if(x1 < x2){
                    Intent i = new Intent(MainActivity.this, Activity_2.class);
                    startActivity(i);
                    overridePendingTransition(R.xml.slide_right_start, R.xml.slide_right_end);
                }
                else if (x1 > x2){

                    Intent i = new Intent(MainActivity.this, Activity_3.class);
                    startActivity(i);
                    overridePendingTransition(R.xml.slide_left_start, R.xml.slide_left_end);
                }
        }
        return false;
    }
}
