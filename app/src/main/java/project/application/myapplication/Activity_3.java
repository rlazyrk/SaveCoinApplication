package project.application.myapplication;

import static java.lang.Math.abs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormatSymbols;

public class Activity_3 extends AppCompatActivity {

    private Integer expensesType_1 = 0;
    private Integer expensesType_2 = 0;
    private Integer expensesType_3 = 0;
    private Integer expensesType_4 = 0;
    private Integer expensesType_5 = 0;
    private Integer expensesType_6 = 0;
    private Integer expensesType_7 = 0;
    private Integer expensesType_8 = 0;
    private Integer expensesType_9 = 0;

    private Button btnType_1;
    private Button btnType_2;
    private Button btnType_3;
    private Button btnType_4;
    private Button btnType_5;
    private Button btnType_6;
    private Button btnType_7;
    private Button btnType_8;
    private Button btnType_9;

    private EditText editType_1;
    private TextView viewType_1;
    private TextView viewType_2;
    private TextView viewType_3;
    private TextView viewType_4;
    private TextView viewType_5;
    private TextView viewType_6;
    private TextView viewType_7;
    private TextView viewType_8;
    private TextView viewType_9;

    private Spinner spinnerOptions;
    private TextView viewSumOfAll;
    private Button goToMain;
    private Button goToActivity_2;
    private Button goToActivity_3;
    private SpendingsDBOpenHelper mDB;
    private Integer sumOfAllint = 0;
    private float x1, x2, y1, y2;
    private Dialog overlayDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        overlayDialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        overlayDialog.setContentView(R.layout.progress_overlay);
        overlayDialog.setCancelable(false);

        spinnerOptions = findViewById(R.id.spinnerOptions);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOptions.setAdapter(adapter);

        spinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedItem = adapterView.getItemAtPosition(position).toString();
                handleSpinnerSelection(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        goToMain = findViewById(R.id.goToMain);
        goToActivity_2 = findViewById(R.id.goToActivity_2);
        goToActivity_3 = findViewById(R.id.goToActivity_3);
        btnType_1 = findViewById(R.id.addType_1);
        btnType_2 = findViewById(R.id.addType_2);
        btnType_3 = findViewById(R.id.addType_3);
        btnType_4 = findViewById(R.id.addType_4);
        btnType_5 = findViewById(R.id.addType_5);
        btnType_6 = findViewById(R.id.addType_6);
        btnType_7 = findViewById(R.id.addType_7);
        btnType_8 = findViewById(R.id.addType_8);
        btnType_9 = findViewById(R.id.addType_9);
        editType_1 = findViewById(R.id.enterType_1);
        viewType_1 = findViewById(R.id.viewType_1);
        viewType_2 = findViewById(R.id.viewType_2);
        viewType_3 = findViewById(R.id.viewType_3);
        viewType_4 = findViewById(R.id.viewType_4);
        viewType_5 = findViewById(R.id.viewType_5);
        viewType_6 = findViewById(R.id.viewType_6);
        viewType_7 = findViewById(R.id.viewType_7);
        viewType_8 = findViewById(R.id.viewType_8);
        viewType_9 = findViewById(R.id.viewType_9);
//        viewSumOfAll = findViewById(R.id.viewSumOfAll);
        mDB = new SpendingsDBOpenHelper(this);
        viewType_1.setText(expensesType_1.toString());
        viewType_2.setText(expensesType_2.toString());
        viewType_3.setText(expensesType_3.toString());
        viewType_4.setText(expensesType_4.toString());
        viewType_5.setText(expensesType_5.toString());
        viewType_6.setText(expensesType_6.toString());
        viewType_7.setText(expensesType_7.toString());
        viewType_8.setText(expensesType_8.toString());
        viewType_9.setText(expensesType_9.toString());

//        viewSumOfAll.setText(sumOfAllint.toString());
        btnType_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editType_1.getText().toString().trim().equals("")) {
                    Toast.makeText(Activity_3.this, R.string.emptyField, Toast.LENGTH_LONG).show();
                } else {
                    mDB.insert(Integer.parseInt(editType_1.getText().toString().trim()), 1);
                    expensesType_1 = mDB.query(1);
                    viewType_1.setText(expensesType_1.toString());
                    sumOfAllint = mDB.querySum(9);
//                    viewSumOfAll.setText(sumOfAllint.toString());
                }
            }
        });
        btnType_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editType_1.getText().toString().trim().equals("")) {
                    Toast.makeText(Activity_3.this, R.string.emptyField, Toast.LENGTH_LONG).show();
                } else {
                    mDB.insert(Integer.parseInt(editType_1.getText().toString().trim()), 2);
                    expensesType_2 = mDB.query(2);
                    viewType_2.setText(expensesType_2.toString());
                    sumOfAllint = mDB.querySum(9);
//                    viewSumOfAll.setText(sumOfAllint.toString());
                }
            }
        });
        btnType_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editType_1.getText().toString().trim().equals("")) {
                    Toast.makeText(Activity_3.this, R.string.emptyField, Toast.LENGTH_LONG).show();
                } else {
                    mDB.insert(Integer.parseInt(editType_1.getText().toString().trim()), 3);
                    expensesType_3 = mDB.query(3);
                    viewType_3.setText(expensesType_3.toString());
                    sumOfAllint = mDB.querySum(9);
//                    viewSumOfAll.setText(sumOfAllint.toString());
                }
            }
        });
        btnType_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editType_1.getText().toString().trim().equals("")) {
                    Toast.makeText(Activity_3.this, R.string.emptyField, Toast.LENGTH_LONG).show();
                } else {
                    mDB.insert(Integer.parseInt(editType_1.getText().toString().trim()), 4);
                    expensesType_4 = mDB.query(4);
                    viewType_4.setText(expensesType_4.toString());
                    sumOfAllint = mDB.querySum(9);
//                    viewSumOfAll.setText(sumOfAllint.toString());
                }
            }
        });

        btnType_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editType_1.getText().toString().trim().equals("")) {
                    Toast.makeText(Activity_3.this, R.string.emptyField, Toast.LENGTH_LONG).show();
                } else {
                    mDB.insert(Integer.parseInt(editType_1.getText().toString().trim()), 5);
                    expensesType_5 = mDB.query(5);
                    viewType_5.setText(expensesType_5.toString());
                    sumOfAllint = mDB.querySum(9);
//                    viewSumOfAll.setText(sumOfAllint.toString());
                }
            }
        });

        btnType_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editType_1.getText().toString().trim().equals("")) {
                    Toast.makeText(Activity_3.this, R.string.emptyField, Toast.LENGTH_LONG).show();
                } else {
                    mDB.insert(Integer.parseInt(editType_1.getText().toString().trim()), 6);
                    expensesType_6 = mDB.query(6);
                    viewType_6.setText(expensesType_6.toString());
                    sumOfAllint = mDB.querySum(9);
//                    viewSumOfAll.setText(sumOfAllint.toString());
                }
            }
        });

        btnType_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editType_1.getText().toString().trim().equals("")) {
                    Toast.makeText(Activity_3.this, R.string.emptyField, Toast.LENGTH_LONG).show();
                } else {
                    mDB.insert(Integer.parseInt(editType_1.getText().toString().trim()), 7);
                    expensesType_7 = mDB.query(7);
                    viewType_7.setText(expensesType_7.toString());
                    sumOfAllint = mDB.querySum(9);
//                    viewSumOfAll.setText(sumOfAllint.toString());
                }
            }
        });

        btnType_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editType_1.getText().toString().trim().equals("")) {
                    Toast.makeText(Activity_3.this, R.string.emptyField, Toast.LENGTH_LONG).show();
                } else {
                    mDB.insert(Integer.parseInt(editType_1.getText().toString().trim()), 8);
                    expensesType_8 = mDB.query(8);
                    viewType_8.setText(expensesType_8.toString());
                    sumOfAllint = mDB.querySum(9);
//                    viewSumOfAll.setText(sumOfAllint.toString());
                }
            }
        });

        btnType_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editType_1.getText().toString().trim().equals("")) {
                    Toast.makeText(Activity_3.this, R.string.emptyField, Toast.LENGTH_LONG).show();
                } else {
                    mDB.insert(Integer.parseInt(editType_1.getText().toString().trim()), 9);
                    expensesType_9 = mDB.query(9);
                    viewType_9.setText(expensesType_9.toString());
                    sumOfAllint = mDB.querySum(9);
//                    viewSumOfAll.setText(sumOfAllint.toString());
                }
            }
        });

        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent click = new Intent(Activity_3.this, MainActivity.class);
                startActivity(click);
            }
        });
        goToActivity_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent click = new Intent(Activity_3.this, Activity_2.class);
                startActivity(click);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                if (x1 < x2 && abs(x1 - x2) > 200) {
                    showOverlayDialog();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(Activity_3.this, Activity_2.class);
                            startActivity(i);
                            overridePendingTransition(R.xml.slide_right_start, R.xml.slide_right_end);
                            hideOverlayDialog();
                        }
                    }, 500); // Adjust the delay time as needed
                } else if (x1 > x2 && abs(x1 - x2) > 300) {
                    showOverlayDialog();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(Activity_3.this, MainActivity.class);
                            startActivity(i);
                            overridePendingTransition(R.xml.slide_left_start, R.xml.slide_left_end);
                            hideOverlayDialog();
                        }
                    }, 500); // Adjust the delay time as needed
                }
        }
        return false;
    }

    private void showOverlayDialog() {
        overlayDialog.show();
    }

    private void hideOverlayDialog() {
        overlayDialog.dismiss();
    }

    private void handleSpinnerSelection(String selectedItem) {
        switch (selectedItem) {
            case "For all time":
                sumOfAllint = mDB.querySum(9);
                expensesType_1 = mDB.query(1);
                expensesType_2 = mDB.query(2);
                expensesType_3 = mDB.query(3);
                expensesType_4 = mDB.query(4);
                expensesType_5 = mDB.query(5);
                expensesType_6 = mDB.query(6);
                expensesType_7 = mDB.query(7);
                expensesType_8 = mDB.query(8);
                expensesType_9 = mDB.query(9);
                break;
            case "For 7 days":
                setExpensesForSpecificDays(7);
                break;
            case "For 30 days":
                setExpensesForSpecificDays(30);
                break;
            case "For the year":
                setExpensesForSpecificDays(365);
                break;
            default:
                break;
        }
        viewType_1.setText(expensesType_1.toString());
        viewType_2.setText(expensesType_2.toString());
        viewType_3.setText(expensesType_3.toString());
        viewType_4.setText(expensesType_4.toString());
        viewType_5.setText(expensesType_5.toString());
        viewType_6.setText(expensesType_6.toString());
        viewType_7.setText(expensesType_7.toString());
        viewType_8.setText(expensesType_8.toString());
        viewType_9.setText(expensesType_9.toString());
    }

    private void setExpensesForSpecificDays(int days) {
        sumOfAllint = mDB.querySum(9);
        expensesType_1 = mDB.queryForFewDays(1, days);
        expensesType_2 = mDB.queryForFewDays(2, days);
        expensesType_3 = mDB.queryForFewDays(3, days);
        expensesType_4 = mDB.queryForFewDays(4, days);
        expensesType_5 = mDB.queryForFewDays(5, days);
        expensesType_6 = mDB.queryForFewDays(6, days);
        expensesType_7 = mDB.queryForFewDays(7, days);
        expensesType_8 = mDB.queryForFewDays(8, days);
        expensesType_9 = mDB.queryForFewDays(9, days);
    }
}