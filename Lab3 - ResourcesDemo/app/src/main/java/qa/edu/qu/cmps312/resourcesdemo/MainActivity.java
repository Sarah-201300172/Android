package qa.edu.qu.cmps312.resourcesdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String chapters[] = getResources().getStringArray(R.array.chapters_array);

        String chaptersConcat = "\n";

        for (String chapter : chapters){
            chaptersConcat+=chapter + "\n";
        }

        TextView chaptersTV = (TextView) findViewById(R.id.chapters);
        chaptersTV.setText(chaptersConcat);
    }
}
