package qa.edu.qu.cmps312.todomanager;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarahalhussaini on 10/28/16.
 */

public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {

    Context mContext;
    int mLayoutId;
    ArrayList<ToDoItem> toDoItems = null;

    public ToDoItemAdapter(Context context, int resource, List<ToDoItem> toDoItems) {
        super(context, resource, toDoItems);
        this.mContext = context;
        this.mLayoutId = resource;
        this.toDoItems = (ArrayList<ToDoItem>) toDoItems;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if (row == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            row = layoutInflater.inflate(mLayoutId, parent, false);
        }

        TextView title = (TextView) row.findViewById(R.id.titleTV);
        TextView priority = (TextView) row.findViewById(R.id.priorityTV);
        TextView date = (TextView) row.findViewById(R.id.dateTV);
        TextView time = (TextView) row.findViewById(R.id.timeTV);
        CheckBox done = (CheckBox) row.findViewById(R.id.doneCB);

        //ToDoItem item = toDoItems.get(position);
        ToDoItem item = getItem(position);

        title.setText(item.getTitle());
        priority.setText(item.getPriority());
        date.setText(item.getDate());
        time.setText(item.getTime());

        if (item.isDone() == false)
            done.setChecked(false);
        else{
            done.setChecked(true);
            row.setBackgroundColor(Color.parseColor("#000"));
        }


        return row;
    }
}
