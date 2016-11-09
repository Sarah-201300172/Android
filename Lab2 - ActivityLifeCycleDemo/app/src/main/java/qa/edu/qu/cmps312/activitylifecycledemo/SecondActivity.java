package qa.edu.qu.cmps312.activitylifecycledemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends Activity {

    private static final String TAG = SecondActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "Second Activity is currently in onCreate()") ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Second Activity is currently in onStart()") ;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "Second Activity is currently in onRestart()") ;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Second Activity is currently in onResume()") ;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Second Activity is currently in onPause()") ;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Second Activity is currently in onStop()") ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Second Activity is currently in onDestroy()") ;
    }
}
