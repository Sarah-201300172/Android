package qa.edu.qu.cmps312.todomanager;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class AddItemForm extends AppCompatActivity {

    Dialog dialog;
    EditText titleText;
    RadioGroup statusGroup;
    RadioGroup priorityGroup;
    TextView dateText;
    TextView timeText;
    ToDoItem toDoItem = new ToDoItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_form);
        dialog = new Dialog(this);
    }

    public void chooseDate(View view) {

        dialog.setContentView(R.layout.date_dialog_layout);
        dialog.show();

    }

    public void chooseTime(View view) {

        dialog.setContentView(R.layout.time_dialog_layout);
        dialog.show();

    }

    public void onSubmitDate(View view) {

        DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
        dateText = (TextView) findViewById(R.id.dateText);
        dateText.setText(String.valueOf(datePicker.getDayOfMonth()) + "/" + String.valueOf(datePicker.getMonth() + 1) +
                "/" + String.valueOf(datePicker.getYear()));
        dialog.dismiss();
    }

    public void cancelAddTask(View view) {
        finish(); //returns to prev activity
    }

    public void resetForm(View view) {
        //reset all views in the activity
        setContentView(R.layout.activity_add_item_form);
        Toast.makeText(this, "Fields have been reset", Toast.LENGTH_SHORT).show();
    }

    public void addNewTask(View view) {
        //get the object and return it to the main activity

        titleText = (EditText) findViewById(R.id.titleText);
        toDoItem.setTitle(titleText.getText().toString());

        statusGroup = (RadioGroup) findViewById(R.id.statusRG);
        statusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                Toast.makeText(AddItemForm.this, String.valueOf(id), Toast.LENGTH_SHORT).show();

                if (checkedId == R.id.doneRB) {
                    toDoItem.setDone(true);
                    Log.i("TAG", "ITS DONE");
                } else
                    toDoItem.setDone(false);
            }
        });

        priorityGroup = (RadioGroup) findViewById(R.id.priorityRG);
        priorityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.lowRB) {
                    toDoItem.setPriority("Low");
                    Log.i("TAG", "LOW");
                } else if (checkedId == R.id.mediumRB) {
                    toDoItem.setPriority("Medium");
                    Log.i("TAG", "Med");
                } else
                    toDoItem.setPriority("High");
            }
        });

        toDoItem.setDate(dateText.getText().toString());
        toDoItem.setTime(timeText.getText().toString());

        Intent i = new Intent();
        setResult(RESULT_OK, i);
        i.putExtra("ToDoObject", toDoItem);
        finish();

    }


    public void onSubmitTime(View view) {

        TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.timePicker);

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        timeText = (TextView) findViewById(R.id.timeTV);
        timeText.setText(hour + " : " + minute);
        dialog.dismiss();

    }
}
