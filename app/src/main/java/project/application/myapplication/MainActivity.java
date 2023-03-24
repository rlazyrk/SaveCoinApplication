package project.application.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button addExpenses;
    private Button addBudget;
    private EditText enterBudgetOrExpenses;
    private Integer budget = 0;
    private TextView currentBudget;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addExpenses = findViewById(R.id.addExpenses);
        addBudget = findViewById(R.id.addBudget);
        enterBudgetOrExpenses = findViewById(R.id.enterBudgetOrExpenses);
        currentBudget = findViewById(R.id.curentbudget);
        addExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enterBudgetOrExpenses.getText().toString().trim().equals("")){
                    Toast.makeText(MainActivity.this, R.string.emptyField, Toast.LENGTH_LONG).show();
                }
                else{
                    budget -= Integer.parseInt(enterBudgetOrExpenses.getText().toString().trim());
                    currentBudget.setText(budget.toString());
                }
            }
        });
        addBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enterBudgetOrExpenses.getText().toString().trim().equals("")){
                    Toast.makeText(MainActivity.this, R.string.emptyField, Toast.LENGTH_LONG).show();
                }
                else{
                    budget += Integer.parseInt(enterBudgetOrExpenses.getText().toString().trim());
                    currentBudget.setText(budget.toString());
                }
            }
        });

    }
}