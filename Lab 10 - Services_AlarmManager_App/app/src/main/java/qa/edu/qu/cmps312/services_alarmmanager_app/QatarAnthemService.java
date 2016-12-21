package qa.edu.qu.cmps312.services_alarmmanager_app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by sarahalhussaini on 12/7/16.
 */

public class QatarAnthemService extends Service {

    private IBinder mBinder = new LocalBinder();
    private MediaPlayer mPlayer;
    private static final int NOTIFICATION_ID = 1;
    private int mStartID;
    NotificationManager notificationManager;


    @Override
    public void onCreate() {
        super.onCreate();

        notificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mPlayer = MediaPlayer.create(this, R.raw.qatar_national_anthem);

        if (null != mPlayer) {

            mPlayer.setLooping(false);
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    Intent intent = new Intent("myCustomAction");
                    sendBroadcast(intent);
                    stopSelf(mStartID);

                }
            });

        }

        Log.i("TAG", "SERVICE CREATED");

        createNotif();
        mStartID = startId;

        playSound();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        if (null != mPlayer) {

            mPlayer.stop();
            mPlayer.release();

        }
    }

    public class LocalBinder extends Binder {
        QatarAnthemService getService() {
            return QatarAnthemService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void stopSound() {
        Log.i("TAG", "STOP SOUND");
        mPlayer.stop();
    }

    public void playSound() {
        if (null != mPlayer) {

            if (mPlayer.isPlaying()) {

                mPlayer.seekTo(0);

            } else {

                mPlayer.start();

            }

        }
    }


    public void createNotif() {

        final Intent notificationIntent = new Intent(getApplicationContext(),
                MainActivity.class);

        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        final Notification notification = new Notification.Builder(
                getApplicationContext())
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentTitle(getString(R.string.notif_title))
                .setContentText(getString(R.string.notif_txt))
                .setContentIntent(pendingIntent).build();

        startForeground(NOTIFICATION_ID, notification);
    }

    public void cancelNotif() {
        Log.i("TAG", "CANCEL NOTIF");
        //notificationManager.cancel(NOTIFICATION_ID);
        stopForeground(true);
    }
}
