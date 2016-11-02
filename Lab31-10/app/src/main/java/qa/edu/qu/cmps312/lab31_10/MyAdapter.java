package qa.edu.qu.cmps312.lab31_10;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by sarahalhussaini on 10/31/16.
 */
public class MyAdapter extends ArrayAdapter<String>{


    public MyAdapter(Context context, String[] objects) {
        super(context, 0, objects); //i know the layout i don't want you to know it
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final String item = getItem(position);

        if (convertView == null)// first time - i don't know which layout to use
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.linear_layout_demo,parent,false);
        }

        Button btn = (Button) convertView.findViewById(R.id.btn);
        final EditText et = (EditText) convertView.findViewById(R.id.ev);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), item , Toast.LENGTH_SHORT).show();
                Log.i("TAG","onClick");
            }
        });
        return convertView;
    }
}
