package qa.edu.qu.cmps312.services_alarmmanager_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    AlarmManager mAlarmManager;
    QatarAnthemService mService;
    private Intent mIntent;
    private PendingIntent mPendingIntent;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIntent = new Intent(this,
                QatarAnthemService.class);
    }


    public void playClicked(View view) {

        Toast.makeText(this, "Start Playing the Anthem After 5 Seconds", Toast.LENGTH_SHORT).show();

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        mPendingIntent = PendingIntent.getService(
                this, 0, mIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mAlarmManager.set(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 5000,
                mPendingIntent);

    }

    public void stopClicked(View view) {

        bindService(mIntent, mConnection, Context.BIND_AUTO_CREATE);

        if (mBound) {
            mService.stopSound();
            mService.cancelNotif();
            this.onStop();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {

            Log.i("TAG","ON CONNECTED");
            QatarAnthemService.LocalBinder binder = (QatarAnthemService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

}
