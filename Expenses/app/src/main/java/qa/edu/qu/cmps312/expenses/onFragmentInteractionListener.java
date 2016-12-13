package qa.edu.qu.cmps312.expenses;

import android.content.ContentValues;

/**
 * Created by sarahalhussaini on 11/24/16.
 */

public interface onFragmentInteractionListener {

    void optionClicked(int which);
    void addExpensesBtnClicked();
    void addIncomeBtnClicked();
    void addItem(ContentValues values);

}
