package qa.edu.qu.cmps312.systemsafetymonitorbrs;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by sarahalhussaini on 11/16/16.
 */

public class BatteryStatusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        boolean safe = false;

        if (intent.getAction() == Intent.ACTION_BATTERY_OKAY) {
            safe = false;
            //Toast.makeText(context, "The system battery is OKAY", Toast.LENGTH_LONG).show();
        } else if (intent.getAction() == Intent.ACTION_BATTERY_LOW) {
            safe = true;
            //Toast.makeText(context, "The system battery is LOW", Toast.LENGTH_LONG).show();
        }

        Intent i = new Intent(context.getString(R.string.broadcast_action));
        i.putExtra(context.getString(R.string.SAFE),safe);
        context.sendBroadcast(i);

        NotificationManager man = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder notification = new Notification.Builder(context);

        notification.setContentTitle(context.getString(R.string.battery_is_safe)).setContentText(context.getString(R.string.battery_is_safe)).setSmallIcon(R.mipmap.ic_launcher);

        Intent myIntent = new Intent(context.getString(R.string.open_another_app_action));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 31, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
        man.notify(31, notification.build());


    }
}
