package project.application.myapplication.allForGoals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Goal {
    private long id;
    private String goalName;
    private int goalAmount;
    private int currentAmount;
    private String goalDate;
    private String startDate;

    public Goal(long id, String goalName, int goalAmount, int currentAmount, String goalDate) {
        this.id = id;
        this.goalName = goalName;
        this.goalAmount = goalAmount;
        this.currentAmount = currentAmount;
        this.goalDate = goalDate;

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        this.startDate = dateFormat.format(currentDate);
    }

    public Goal(long id, String goalName, int goalAmount, int currentAmount, String goalDate, String startDate) {
        this.id = id;
        this.goalName = goalName;
        this.goalAmount = goalAmount;
        this.currentAmount = currentAmount;
        this.goalDate = goalDate;
        this.startDate = startDate;
    }

    public Goal( String goalName, int goalAmount, int currentAmount, String goalDate, String startDate) {
        this.goalName = goalName;
        this.goalAmount = goalAmount;
        this.currentAmount = currentAmount;
        this.goalDate = goalDate;
        this.startDate = startDate;
    }

    public long getId() {
        return id;
    }

    public String getGoalName() {
        return goalName;
    }

    public int getGoalAmount() {
        return goalAmount;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public String getGoalDate() {
        return goalDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setCurrentAmount(int newAmount){
        this.currentAmount = newAmount;
    }
}
