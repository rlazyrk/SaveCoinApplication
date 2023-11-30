package project.application.myapplication.allForGoals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import project.application.myapplication.SpendingsDBOpenHelper;


public class GoalsDBOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = SpendingsDBOpenHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "goals";

    private static final String GOALS_LIST_TABLE = "goal_entries";

    private static final String GOAL_NAME = "goal_name";

    private static final String GOAL_AMOUNT = "goal_amount";

    private static final String CURRENT_AMOUNT = "current_amount";

    private static final String GOAL_DATE = "goal_date";

    private static final String START_DATE = "start_date";

    public static final String KEY_ID = "_id";

    private static final String[] COLUMNS = {KEY_ID, GOAL_NAME, GOAL_AMOUNT, CURRENT_AMOUNT, GOAL_DATE, START_DATE};

    private static final String GOAL_TABLE_CREATE =
            "CREATE TABLE " + GOALS_LIST_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    GOAL_NAME + " TEXT NOT NULL," +
                    GOAL_AMOUNT + " INTEGER NOT NULL," +
                    CURRENT_AMOUNT + " INTEGER NOT NULL," +
                    GOAL_DATE + " TEXT NOT NULL," +
                    START_DATE + " TEXT NOT NULL" +
                    ");";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GOAL_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GOALS_LIST_TABLE);
        onCreate(db);
    }

    public GoalsDBOpenHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    public long insertGoal(Goal goal) {
        long newRowId = -1;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(GOAL_NAME, goal.getGoalName());
            values.put(GOAL_AMOUNT, goal.getGoalAmount());
            values.put(CURRENT_AMOUNT, goal.getCurrentAmount());
            values.put(GOAL_DATE, goal.getGoalDate());
            values.put(START_DATE, goal.getStartDate());
            newRowId = db.insertOrThrow(GOALS_LIST_TABLE, null, values);
        } catch (SQLException e) {
            Log.e(TAG, "Помилка при вставці цілі", e);
        }
        return newRowId;
    }

    public List<Goal> getAllGoals() {
        List<Goal> goalsList = new ArrayList<>();
        String query = "SELECT * FROM " + GOALS_LIST_TABLE;
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery(query, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String goalName = cursor.getString(1);
                    int goalAmount = cursor.getInt(2);
                    int currentAmount = cursor.getInt(3);
                    String goalDate = cursor.getString(4);
                    String startDate = cursor.getString(5);
                    Goal goal = new Goal(id, goalName, goalAmount, currentAmount, goalDate, startDate);
                    goalsList.add(goal);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e(TAG, "Помилка при отриманні усіх цілей", e);
        }
        return goalsList;
    }

    public void deleteGoal(long goalId) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.delete(GOALS_LIST_TABLE, KEY_ID + " = ?", new String[]{String.valueOf(goalId)});
        } catch (SQLException e) {
            Log.e(TAG, "Помилка при видаленні цілі", e);
        }
    }
    public void updateGoal(Goal updatedGoal) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(GOAL_NAME, updatedGoal.getGoalName());
            values.put(GOAL_AMOUNT, updatedGoal.getGoalAmount());
            values.put(CURRENT_AMOUNT, updatedGoal.getCurrentAmount());
            values.put(GOAL_DATE, updatedGoal.getGoalDate());
            values.put(START_DATE, updatedGoal.getStartDate());
            db.update(GOALS_LIST_TABLE, values, KEY_ID + " = ?", new String[]{String.valueOf(updatedGoal.getId())});
        } catch (SQLException e) {
            Log.e(TAG, "Помилка при оновленні цілі", e);
        }
    }
}
