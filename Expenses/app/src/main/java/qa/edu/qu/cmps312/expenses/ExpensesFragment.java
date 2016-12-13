package qa.edu.qu.cmps312.expenses;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import qa.edu.qu.cmps312.expenses.Adapters.ListCustomAdapter;
import qa.edu.qu.cmps312.expenses.SQLHelper.DatabaseOpenHelper;

/**
 * Created by sarahalhussaini on 11/24/16.
 */

public class ExpensesFragment extends Fragment {

    private onFragmentInteractionListener mListener;
    private DatabaseOpenHelper mDbHelper;
    private ListCustomAdapter mAdapter;
    private String expensesSum;
    private String incomeSum;
    TextView expensesAmountTV;
    TextView incomeAmountTV;
    TextView balanceTV;
    TextView monthTV;
    ProgressBar expensesIndicator;
    Cursor c;
    SQLiteDatabase db;
    SharedPreferences prefs;

//    private boolean isFirstTimeLaunching = false;
//    private int launchCounter = 0;

    public ExpensesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mListener == null) {
            try {
                mListener = (onFragmentInteractionListener) getActivity();
            } catch (ClassCastException e) {
                throw new ClassCastException(mListener.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        }

        prefs = getActivity().getPreferences(getActivity().MODE_PRIVATE);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //launchCounter++;

        View view = inflater.inflate(R.layout.fragment_expenses, container, false);

        expensesIndicator = (ProgressBar) view.findViewById(R.id.progressBar);
        Button addExpensesBtn = (Button) view.findViewById(R.id.addExpensesBtn);
        Button addIncomeBtn = (Button) view.findViewById(R.id.addIncomeBtn);
        ImageButton deleteBtn = (ImageButton) view.findViewById(R.id.deleteBtn);
        expensesAmountTV = (TextView) view.findViewById(R.id.expensesAmount);
        incomeAmountTV = (TextView) view.findViewById(R.id.incomeAmount);
        balanceTV = (TextView) view.findViewById(R.id.balanceAmount);
        monthTV = (TextView) view.findViewById(R.id.monthTV);


        //ref : http://stackoverflow.com/a/8563409/
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM"); //get the month name
        //Log.i("TAG", String.valueOf(cal.getTime()));
        String month_name = month_date.format(cal.getTime());  //cal.getTime() returns date obj
        monthTV.setText(month_name);

        ListView itemsList = (ListView) view.findViewById(R.id.expensesLV);
        mDbHelper = new DatabaseOpenHelper(getActivity().getApplicationContext());


        String[] columns = {DatabaseOpenHelper._ID, DatabaseOpenHelper.AMOUNT, DatabaseOpenHelper.CATEGORY,
                DatabaseOpenHelper.DATE, DatabaseOpenHelper.TYPE, DatabaseOpenHelper.IMAGE_PATH};

        c = getItems();
//        mAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.list_row, c,
//                columns, new int[]{R.id._id, R.id.amountTextv, R.id.categoryTextv, R.id.dateTextv, R.id.typeTextv, R.id.imageV},
//                0);
        mAdapter = new ListCustomAdapter(ExpensesFragment.this.getActivity(),c, 0,this);
        itemsList.setAdapter(mAdapter);


        db = mDbHelper.getReadableDatabase();


        setIncomeAndExpense();

        addExpensesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Float.parseFloat(incomeSum) == 0.0) { //if user is trying to add an expense when income is zero

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ExpensesFragment.this.getActivity());
                    alertDialog.setTitle(R.string.expenseErrDialogTitle);
                    alertDialog.setMessage(R.string.expenseErrDialogMsg);
                    alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();

                } else mListener.addExpensesBtnClicked();
            }
        });

        addIncomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.addIncomeBtnClicked();
            }
        });


        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
        //update progress bar everytime we are back to this fragment
        updateProgressAndBalance();
    }

    private void updateProgressAndBalance() {

        int i = (int) Math.round(Float.parseFloat(expensesSum) * 100.0 / Float.parseFloat(incomeSum));
        //Log.i("TAG", "PROGRESS --> " + String.valueOf(i));

        expensesIndicator.setProgress(0); //----> because progress bars doesn't trigger updates
        expensesIndicator.setProgress(i);

        balanceTV.setText(String.valueOf(Float.parseFloat(incomeSum) - Float.parseFloat(expensesSum)));

        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(getString(R.string.BALANCE), Float.parseFloat(balanceTV.getText().toString()));
        editor.apply();
    }

    private Cursor getItems() {

        return mDbHelper.getWritableDatabase().query(DatabaseOpenHelper.TABLE_NAME,
                DatabaseOpenHelper.columns, null, new String[]{}, null, null,
                null);


    }

    public void deleteRecord(String id){

        mDbHelper.getWritableDatabase().delete(DatabaseOpenHelper.TABLE_NAME,
                DatabaseOpenHelper._ID + "=?",
                new String[] { id });

        setIncomeAndExpense();

        Cursor c = getItems();
        mAdapter.changeCursor(c);
        mAdapter.notifyDataSetChanged();
    }

    private void setIncomeAndExpense() {
        try {
            String select = "SELECT SUM(amount) FROM " + mDbHelper.TABLE_NAME + " WHERE " + mDbHelper.TYPE + " = '" + getString(R.string.TYPE_EXPENSE) + "'";
            c = db.rawQuery(select, null);
            expensesSum = String.valueOf(c.moveToFirst() ? c.getFloat(0) : -1);
            expensesAmountTV.setText(expensesSum);
            //Log.i("TAG", "SUM IS ---->" + expensesSum);

            select = "SELECT SUM(amount) FROM " + mDbHelper.TABLE_NAME + " WHERE " + mDbHelper.TYPE + " = '" + getString(R.string.TYPE_INCOME) + "'";
            c = db.rawQuery(select, null);
            incomeSum = String.valueOf(c.moveToFirst() ? c.getFloat(0) : -1);
            incomeAmountTV.setText(incomeSum);
            //Log.i("TAG", "INCOME IS ---->" + incomeSum);

            c.close();
        } catch (Exception e) {
            //Log.i("TAG", "EXCEPTION CAUGHT!!!");
            e.printStackTrace();
        }

        balanceTV.setText(String.valueOf(Float.parseFloat(incomeSum) - Float.parseFloat(expensesSum)));
    }

}
