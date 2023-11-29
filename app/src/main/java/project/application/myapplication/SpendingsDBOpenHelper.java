package project.application.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;

public class SpendingsDBOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = SpendingsDBOpenHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String SPENDINGS_LIST_TABLE = "spending_entries";
    private static final String DATABASE_NAME = "spendings";
    public static final String KEY_ID = "_id";
    public static final String SPENDING_CATEGORY = "category";
    public static final String SPENDING_AMOUNT = "spending";
    public static final String SPENDING_DATE = "inserted_date";
    private static final String[] COLUMNS = {KEY_ID, SPENDING_AMOUNT, SPENDING_CATEGORY,SPENDING_DATE};

    public SpendingsDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SPENDING_TABLE_CREATE =
            "CREATE TABLE " + SPENDINGS_LIST_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY," +
                    SPENDING_AMOUNT + " INTEGER NOT NULL," +
                    SPENDING_CATEGORY + " INTEGER NOT NULL," +
                    SPENDING_DATE+ " TEXT"+");";
    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL(SPENDING_TABLE_CREATE);
        fillDatabaseWithData(DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
    }

    public int querySum(int categ) {
        int sum = 0;
        for (int i = 1; i <= categ; i++) {
            sum += query(i);
        }
        return sum;
    }

    public int queryForFewDays(int categ, int days){
        int sum = 0;
        for(int i = 0; i <=days ; i++){
            sum += query2(categ, i);
        }
        return sum;
    }
    public int query2(int categ,int days) {// if days =1 than it will take data for yesterday
        String name_of_sum = "total_sum";
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, days*(-1));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // add 1 to adjust for 0-based indexing
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String currentDate = String.format("%d-%02d-%02d", year, month, day);
        String[] params = {""+categ+"",currentDate.replaceAll("\"","'")};
        Cursor cursor = null;
        int sum_spending = 0;
        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }
            cursor = mReadableDB.rawQuery("SELECT SUM(" + SPENDING_AMOUNT + ") AS " + name_of_sum + " FROM " + SPENDINGS_LIST_TABLE
                    + " WHERE " + SPENDING_CATEGORY + " =?"+" AND "+SPENDING_DATE+
                    "=?",params,null);
            cursor.moveToFirst();
            sum_spending = cursor.getInt(0);
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            return sum_spending;
        }
    }

    public int query(int categ) {
        String name_of_sum = "total_sum";
        String query = "SELECT SUM(" + SPENDING_AMOUNT + ") AS " + name_of_sum + " FROM " + SPENDINGS_LIST_TABLE
                + " WHERE " + SPENDING_CATEGORY + " == " + categ + " ;";
        Cursor cursor = null;
        int sum_spending = 0;

        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();
            sum_spending = cursor.getInt(0);
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            return sum_spending;
        }
    }

    private void fillDatabaseWithData(SQLiteDatabase db) {
         insert(1,99);

    }

    public long insert(int spend, int categ) {
        long newId = 0;
        Calendar calendar= Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // add 1 to adjust for 0-based indexing
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String currentDate = String.format("%d-%02d-%02d", year, month, day);
        ContentValues values = new ContentValues();
        values.put(SPENDING_AMOUNT, spend);
        values.put(SPENDING_CATEGORY, categ);
        values.put(SPENDING_DATE,currentDate);
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            newId = mWritableDB.insert(SPENDINGS_LIST_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }
}
