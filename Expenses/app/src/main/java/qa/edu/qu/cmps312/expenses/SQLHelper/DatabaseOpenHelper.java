package qa.edu.qu.cmps312.expenses.SQLHelper;

/**
 * Created by sarahalhussaini on 12/9/16.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public final static String TABLE_NAME = "expenseIncome";

    public final static String _ID = "_id";
    public final static String AMOUNT = "amount";
    public final static String DATE = "date";
    public final static String CATEGORY = "category";
    public final static String RECURRING = "recurring";
    public final static String TYPE = "type";
    public final static String IMAGE_PATH = "image_path";
    public final static String LATIT = "latitude";
    public final static String LONG = "longitude";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = " , ";


    public final static String[] columns = {_ID, AMOUNT, DATE, CATEGORY, RECURRING, TYPE, IMAGE_PATH, LATIT, LONG};

    final private static String CREATE_CMD =

            "CREATE TABLE " + TABLE_NAME + " (" + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + AMOUNT + " INT NOT NULL, "
                    + DATE + " DATE, "
                    + CATEGORY + " TEXT, "
                    + RECURRING + " CHAR, "
                    + TYPE + " TEXT, "
                    + IMAGE_PATH + " TEXT, "
                    + LATIT + " TEXT, "
                    + LONG + " TEXT " + " )";


    final private static String NAME = "expense_income_db";
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // N/A
    }

    public void deleteDatabase() {
        mContext.deleteDatabase(NAME);
    }
}

