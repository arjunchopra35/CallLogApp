package arjun.customer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by arjun2 on 26/09/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CALLINFO.db";
    public static final String TABLE_NAME = "CALL_INFO";
    public static final String COL_1 = "PHONE NUMBER";
    public static final String COL_2 = "CALL TYPE";
    public static final String COL_3 = "CALL DATE";
    public static final String COL_4 = "CALL DURATION";


    
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
