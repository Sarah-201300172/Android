package qa.edu.qu.cmps312.lab31_10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewDemo extends AppCompatActivity {

    String [] items = {"A","B","C","D"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_demo);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);

        MyAdapter adapter = new MyAdapter(this,items);

        ListView lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(adapter);
    }
}
