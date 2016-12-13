package qa.edu.qu.cmps312.expenses;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import qa.edu.qu.cmps312.expenses.SQLHelper.DatabaseOpenHelper;

public class MainActivity extends AppCompatActivity implements onFragmentInteractionListener {

    private DatabaseOpenHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void optionClicked(int which) {
        Intent i = new Intent(this, NextActivity.class);
        i.putExtra(getString(R.string.WHICH_FRAGMENT), which);
        startActivity(i);
    }

    @Override
    public void addExpensesBtnClicked() {
    }

    @Override
    public void addIncomeBtnClicked() {
    }

    @Override
    public void addItem(ContentValues values) {

    }


}
