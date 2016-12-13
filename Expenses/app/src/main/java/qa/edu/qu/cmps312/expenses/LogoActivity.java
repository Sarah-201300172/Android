package qa.edu.qu.cmps312.expenses;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LogoActivity extends AppCompatActivity {


    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

    }

    @Override
    protected void onResume() {
        //did it in onResume in case the user presses back
        new Thread(new goToMainScreen()).start();
        super.onResume();
    }

    private class goToMainScreen implements Runnable {

        @Override
        public void run() {

            mHandler.postDelayed(new Runnable() { //go to main activity after 2.5 sec
                @Override
                public void run() {
                    Intent i = new Intent(LogoActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }, 2500);

        }
    }


}
