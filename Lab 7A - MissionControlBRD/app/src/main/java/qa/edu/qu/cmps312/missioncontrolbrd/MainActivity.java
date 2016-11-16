package qa.edu.qu.cmps312.missioncontrolbrd;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements InteractionListener {

    SafetyStatusReceiver safetyStatusReceiver = new SafetyStatusReceiver();
    private Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.btn);
    }

    public void openMissionControl(View view) {
        Intent i = new Intent(MainActivity.this, MissionControlActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        IntentFilter intentFilter = new IntentFilter(getString(R.string.broadcast_action));
        registerReceiver(safetyStatusReceiver, intentFilter);
        super.onResume();

    }

    @Override
    protected void onPause() {
        unregisterReceiver(safetyStatusReceiver);
        super.onPause();
    }

    @Override
    public void enableBtn(boolean enableIt) {

        mButton.setEnabled(enableIt);
    }
}
