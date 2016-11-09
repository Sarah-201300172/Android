package qa.edu.qu.cmps312.myfirstapp_sa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MyMainActivity extends AppCompatActivity {

    private static final String TAG = "MyMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_main);

        Log.v (TAG, "This is a Verbose message");
        Log.d (TAG, "This is a Debug message");
        Log.i (TAG, "This is an Information message");
        Log.w (TAG, "This is a Warning message");
        Log.e (TAG, "This is an Error message");
        Log.wtf (TAG, "This is a What a Terrible Failure message");

    }
}
