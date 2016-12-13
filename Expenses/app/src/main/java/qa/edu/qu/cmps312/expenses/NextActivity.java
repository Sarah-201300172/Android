package qa.edu.qu.cmps312.expenses;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import qa.edu.qu.cmps312.expenses.SQLHelper.DatabaseOpenHelper;

public class NextActivity extends AppCompatActivity implements onFragmentInteractionListener {

    private DatabaseOpenHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        mDbHelper = new DatabaseOpenHelper(this);

        int whichFragment = getIntent().getExtras().getInt(getResources().getString(R.string.WHICH_FRAGMENT));

        switch (whichFragment) { //determine which fragment to launch based on user click
            case R.string.EXPENSES_FRAGMENT:
                launchFragment(new ExpensesFragment());
                break;
            case R.string.CONVERTER_FRAGMENT:
//                ExpensesFragment fragment = new ExpensesFragment();
//                launchFragment(fragment);
                break;
            case R.string.STATS_FRAGMENT:
//                ExpensesFragment fragment = new ExpensesFragment();
//                launchFragment(fragment);
                break;
        }

    }

    private void launchFragmentWithStack(Fragment fragment) { //with addToBackStack
        getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null)
                .replace(R.id.dynamic_fragment, fragment).commit();
    }

    private void launchFragment(Fragment fragment) { //without addToBackStack
        getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.dynamic_fragment, fragment).commit();
    }

    @Override
    public void optionClicked(int which) {
        //won't be executed --> button doesn't exist here
    }

    @Override
    public void addExpensesBtnClicked() {
        launchFragmentWithStack(AddItemFragment.newInstance(getResources().getString(R.string.ITEM_TYPE), getString(R.string.TYPE_EXPENSE)));
    }

    @Override
    public void addIncomeBtnClicked() {
        launchFragmentWithStack(AddItemFragment.newInstance(getResources().getString(R.string.ITEM_TYPE), getString(R.string.TYPE_INCOME)));
    }

    @Override
    public void addItem(ContentValues values) {
        mDbHelper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, values);
        //mimic refresh
        //launchFragment(new ExpensesFragment());

    }



}
