package qa.edu.qu.cmps312.services_alarmmanager_app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;


/**
 * Created by sarahalhussaini on 12/7/16.
 */

public class ApplaudReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        Log.i("TAG", "ONRECEIVE");

        MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.applause);


        mPlayer.start();

    }
}
