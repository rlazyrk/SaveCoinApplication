package project.application.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SpendingsDBOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = SpendingsDBOpenHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String SPENDINGS_LIST_TABLE = "spending_entries";
    private static final String DATABASE_NAME = "spendings";
    public static final String KEY_ID = "_id";
    public static final String SPENDING_AMOUNT = "spending";
    private static final String[] COLUMNS = { KEY_ID, SPENDING_AMOUNT };
    public SpendingsDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private static final String SPENDING_TABLE_CREATE =
            "CREATE TABLE " + SPENDINGS_LIST_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    SPENDING_AMOUNT + " INTEGER NOT NULL );";
    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL(SPENDING_TABLE_CREATE);
        fillDatabaseWithData(DB);
    }
    private void fillDatabaseWithData(SQLiteDatabase db){
        int[] test_spendings={1,2,3,4,5,6,6};
        ContentValues values = new ContentValues();
        for (int i=0; i < test_spendings.length;i++) {
            // Put column/value pairs into the container. put() overwrites existing values.
            values.put(SPENDING_AMOUNT, test_spendings[i]);
            db.insert(SPENDINGS_LIST_TABLE, null, values);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public int query() {
        String name_of_sum="total_sum";
        String query = "SELECT SUM("+SPENDING_AMOUNT+") AS " + name_of_sum+ " FROM " + SPENDINGS_LIST_TABLE;
        Cursor cursor = null;
        int sum_spending=0;

        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();
            sum_spending=cursor.getInt(0);
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            cursor.close();
            return sum_spending;
        }
    }
    public long insert(int spend){
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(SPENDING_AMOUNT,spend);
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
