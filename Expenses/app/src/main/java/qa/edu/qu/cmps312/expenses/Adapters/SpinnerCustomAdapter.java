package qa.edu.qu.cmps312.expenses.Adapters;

import android.content.Context;
import android.icu.util.ULocale;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import qa.edu.qu.cmps312.expenses.Model.Category;
import qa.edu.qu.cmps312.expenses.R;

/**
 * Created by sarahalhussaini on 11/28/16.
 */

public class SpinnerCustomAdapter extends ArrayAdapter<Category> {

    public SpinnerCustomAdapter(Context context, int resource, int textViewResourceId, List<Category> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = LayoutInflater.from(getContext()).inflate(R.layout.spinner_row, parent, false);

        Category category = getItem(position);

        TextView categoryText = (TextView) row.findViewById(R.id.spinnerTV);
        ImageView categoryIcon = (ImageView) row.findViewById(R.id.spinnerImgV);

//        if (position == 0) {
//            // default item / first item
//            categoryText.setText("Please select a category");
//        } else {
        categoryText.setText(category.getCategory());
        if (category.getImg() != 0xffffffff)
            categoryIcon.setImageResource(category.getImg());
        // }

        return row;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

}
