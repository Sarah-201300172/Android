package qa.edu.qu.cmps312.lab11_sqliteandsharedpreference;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtV = (TextView) findViewById(R.id.textView);
        String welcomeMsg = getString(R.string.welcome) + getIntent().getStringExtra(getString(R.string.IntentKey));
        txtV.setText(welcomeMsg);
    }
}
