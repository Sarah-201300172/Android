package qa.edu.qu.cmps312.activitylifecycledemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {


    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "Main Activity is currently in onCreate()") ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Main Activity is currently in onStart()") ;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "Main Activity is currently in onRestart()") ;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Main Activity is currently in onResume()") ;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Main Activity is currently in onPause()") ;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Main Activity is currently in onStop()") ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Main Activity is currently in onDestroy()") ;
    }
}
