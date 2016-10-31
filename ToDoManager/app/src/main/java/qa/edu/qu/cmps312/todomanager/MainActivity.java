package qa.edu.qu.cmps312.todomanager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public ArrayList<ToDoItem> toDoItems = new ArrayList<ToDoItem>();
    final static int MY_REQUEST_CODE = 22;
    ToDoItemAdapter toDoItemAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toDoItems.add(new ToDoItem("Android lab hw",false,"Medium","time","date"));
        ListView mListView = (ListView) findViewById(R.id.listView);
        toDoItemAdapter = new ToDoItemAdapter(this,R.layout.row_layout,toDoItems);

        if (mListView != null){
            mListView.setAdapter(toDoItemAdapter);
        }

        //CheckBox doneCB = (CheckBox) findViewById(R.id.doneCB);

    }

    public void addNewTask(View view) {
        //start activity for filling the form for result because i want to get the TODOitem and add it to the list
        Intent i = new Intent(this,AddItemForm.class);
        startActivityForResult(i,MY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == MY_REQUEST_CODE){
            ToDoItem item = (ToDoItem) data.getSerializableExtra("ToDoObject");
            //Log.i("TAG",item.getTitle());
            toDoItems.add(item);
            toDoItemAdapter.notifyDataSetChanged();
        }
//        else {
//            Log.i("TAG","NOT OK");
//        }
    }
}
