package qa.edu.qu.cmps312.expenses.Adapters;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import qa.edu.qu.cmps312.expenses.ExpensesFragment;
import qa.edu.qu.cmps312.expenses.R;
import qa.edu.qu.cmps312.expenses.SQLHelper.DatabaseOpenHelper;


/**
 * Created by sarahalhussaini on 12/12/16.
 */

public class ListCustomAdapter extends CursorAdapter {

    private Context mContext;
    private ExpensesFragment mFragment;

    //flags : FLAG_AUTO_REQUERY calls requery() when there's change
    public ListCustomAdapter(Context context, Cursor c, int flags, ExpensesFragment fragment) {
        super(context, c, flags);
        mContext = context;
        mFragment = fragment;
    }

    @Override //Makes a new view to hold the data pointed to by cursor.
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return LayoutInflater.from(mContext).inflate(R.layout.list_row, null);
    }

    @Override //Bind an existing view to the data pointed to by cursor
    // view is ===> Existing view, returned earlier by newView
    public void bindView(View view, Context context, final Cursor cursor) {

        TextView _id = (TextView) view.findViewById(R.id._id);
        TextView amountTV = (TextView) view.findViewById(R.id.amountTextv);
        TextView categoryTV = (TextView) view.findViewById(R.id.categoryTextv);
        TextView dateTV = (TextView) view.findViewById(R.id.dateTextv);
        TextView typeTV = (TextView) view.findViewById(R.id.typeTextv);
        ImageView imgView = (ImageView) view.findViewById(R.id.imgV);
        ImageButton deleteBtn = (ImageButton) view.findViewById(R.id.deleteBtn);

        _id.setText(cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper._ID)));
        amountTV.setText(cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.AMOUNT)));
        categoryTV.setText(cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.CATEGORY)));
        dateTV.setText(cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.DATE)));
        typeTV.setText(cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.TYPE)));

        //Log.i("TAG",cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.IMAGE_PATH)));
        Bitmap myBitmap = BitmapFactory.decodeFile(cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.IMAGE_PATH)));
        imgView.setImageBitmap(myBitmap);

        final String id = cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper._ID));
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.deleteRecord(id);
            }
        });

    }
}
