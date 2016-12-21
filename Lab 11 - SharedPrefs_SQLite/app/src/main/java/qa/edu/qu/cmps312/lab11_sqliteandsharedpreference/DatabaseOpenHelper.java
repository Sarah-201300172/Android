package qa.edu.qu.cmps312.lab11_sqliteandsharedpreference;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public final static String TABLE_NAME = "login";

    public final static String _ID = "_id";
    public final static String USERNAME = "username";
    public final static String PASSWORD = "password";
    public final static String _NAME = "name";


    public final static String[] columns = {_ID, USERNAME, PASSWORD};

    final private static String CREATE_CMD =

            "CREATE TABLE " + TABLE_NAME + " (" + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + USERNAME + " TEXT, "
                    + _NAME + " TEXT, "
                    + PASSWORD + " TEXT )";


    final private static String NAME = "login_db";
    final private static Integer VERSION = 1;
    final private Context mContext;

    public DatabaseOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CMD);
    }

    public String authenticate(String username, String pass) {

        //this.getWritableDatabase() save it as an obj and use it
//        Cursor c =.ra(DatabaseOpenHelper.TABLE_NAME,
//                DatabaseOpenHelper.columns, USERNAME + "=?", new String[]{username}, null, null,
//                null);

        String select = "SELECT * FROM " + TABLE_NAME + " WHERE " + USERNAME + " = '" + username
                + "'";

        Cursor c = this.getWritableDatabase().rawQuery(select, null);

        if (c.getCount() > 0) { //user is there

            if (c.moveToFirst()) {
                String password = c.getString(3);

                Log.i("TAG", "PASS " + password);
                if (password.equals(pass))
                    return c.getString(2);
            }


        }
        return null;
    }

    public void addUser(ContentValues vals) {

        this.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, vals);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // N/A
    }

    public void deleteDatabase() {
        mContext.deleteDatabase(NAME);
    }
}

