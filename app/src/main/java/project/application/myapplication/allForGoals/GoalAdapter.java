package project.application.myapplication.allForGoals;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import project.application.myapplication.R;
//FIXME не працює зміна цілі та додавання будь чого в ціль
//FIXME
//FIXME
//FIXME
//FIXME
//FIXME
//FIXME
//FIXME
public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {

    private List<Goal> goalList;
    private Context context;
    private GoalsDBOpenHelper goalsDBOpenHelper;

    public GoalAdapter(List<Goal> goalList, Context context) {
        this.goalList = goalList;
        this.context = context;
    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.goal_item, parent, false);
        return new GoalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        Goal goal = goalList.get(position);
        this.goalsDBOpenHelper = new GoalsDBOpenHelper(context);
        holder.goalName.setText(goal.getGoalName());
        holder.currentAmount.setText(String.valueOf(goal.getCurrentAmount()));
        holder.goalAmount.setText(String.valueOf(goal.getGoalAmount()));
        holder.progressBar.setMax(goal.getGoalAmount());
        holder.progressBar.setProgress(goal.getCurrentAmount());
        holder.endDate.setText("End Date: " + goal.getGoalDate());

        holder.buttonAdd.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Enter the amount to add");
            
            View dialogView = LayoutInflater.from(context).inflate(R.layout.goal_add_money_dialog, null);

            builder.setView(dialogView);
            AlertDialog dialog = builder.create();

            EditText input = dialogView.findViewById(R.id.goalAddMoney);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            Button saveButton = dialogView.findViewById(R.id.saveInputGoalAddMoney);
            Button cancelButton = dialogView.findViewById(R.id.cancelInputGoalAddMoney);

            saveButton.setOnClickListener(view -> {
                if (!input.getText().toString().isEmpty()) {
                    int number = Integer.parseInt(input.getText().toString());
                    int newAmount = goal.getCurrentAmount() + number;
                    updateGoalAmount(position, newAmount);
                    dialog.dismiss();
                }
            });

            cancelButton.setOnClickListener(view -> dialog.dismiss());

            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return goalList.size();
    }

    public static class GoalViewHolder extends RecyclerView.ViewHolder {
        TextView goalName;
        TextView currentAmount;
        TextView endDate;

        TextView goalAmount;
        ProgressBar progressBar;
        Button buttonAdd;

        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            goalName = itemView.findViewById(R.id.goalName);
            currentAmount = itemView.findViewById(R.id.currentAmount);
            endDate = itemView.findViewById(R.id.endDate);
            progressBar = itemView.findViewById(R.id.progressBar);
            buttonAdd = itemView.findViewById(R.id.buttonAdd);
            goalAmount = itemView.findViewById(R.id.goalAmount);
        }
    }

    public void updateGoalAmount(int position, int newAmount) {
        Goal goal = goalList.get(position);
        goal.setCurrentAmount(newAmount);
        notifyDataSetChanged();
        goalsDBOpenHelper.updateGoal(goal);
    }
}

