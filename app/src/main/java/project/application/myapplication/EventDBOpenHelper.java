package project.application.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventDBOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = EventDBOpenHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String EVENT_LIST_TABLE = "event_entries";
    private static final String DATABASE_NAME = "event";

    public static final String M_ID   = "m_id";

    public static final String TITLE= "title";

    public static final String MDATE = "date";

    public static final String COLOR = "COLOR";

    public static final String IS_COMPLETED = "is_true";

    public EventDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String EVENT_TABLE_CREATE =
            "CREATE TABLE " + EVENT_LIST_TABLE + " (" +
                    M_ID+ " TEXT NOT NULL,"+
                    TITLE+ " TEXT NOT NULL,"+
                    MDATE + " TEXT NOT NULL,"+
                    COLOR + " INTEGER NOT NULL," +
                    IS_COMPLETED+ " INTEGER NOT NULL"+");";
    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL(EVENT_TABLE_CREATE);
        fillDatabaseWithData(DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
    }

    private void fillDatabaseWithData(SQLiteDatabase db) {


    }

    public List<Event> getall(){
        Cursor cursor = null;
        ArrayList<Event> resultList = new ArrayList();
        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }
            cursor = mReadableDB.rawQuery("SELECT * FROM "+EVENT_LIST_TABLE+";",null);
            cursor.moveToFirst();


            if(cursor.moveToFirst()){
                do{
                    String id = cursor.getString(0);
                    String title = cursor.getString(1);
                    String date = cursor.getString(2);
                    int color = cursor.getInt(3);
                    int bool = cursor.getInt(4);
                    Boolean miwa ;
                    if (bool==1){
                        miwa = true;
                    }else{
                        miwa=false;
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    try {
                        Date parsedDate = dateFormat.parse(date);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(parsedDate);
                        resultList.add(new Event(id,title,calendar,color,miwa));
                        // Now, 'calendar' contains the original date and time from the formatted string.
                        // You can use the 'calendar' object for further operations.
                    } catch (ParseException e) {
                        e.printStackTrace();
                        // Handle
                    }
                } while(cursor.moveToNext());
            }


        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return resultList;
    }



    public long insert(Calendar calendar, String mid, String title, int color , boolean iscom){
        long newId = 0;
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(calendar.getTime());
        values.put(M_ID,mid);
        values.put(TITLE,title);
        values.put(MDATE,formattedDate);
        values.put(COLOR,color);
        int myInt;
        if (iscom) {
            myInt = 1;
        } else {
            myInt = 0;
        }
        values.put(IS_COMPLETED,myInt);
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            newId = mWritableDB.insert(EVENT_LIST_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }
}
