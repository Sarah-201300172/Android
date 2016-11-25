package qa.edu.qu.cmps312.runners;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

    }

    @Override
    protected void onResume() {
        new Thread(new goToMainScreen()).start();
        super.onResume();
    }

    private class goToMainScreen implements Runnable{

        @Override
        public void run() {

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashScreen.this,MainScreen.class);
                    startActivity(i);
                }
            },5000);

        }
    }
}
