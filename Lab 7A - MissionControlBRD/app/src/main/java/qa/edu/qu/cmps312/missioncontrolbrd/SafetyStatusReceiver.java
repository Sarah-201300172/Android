package qa.edu.qu.cmps312.missioncontrolbrd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by sarahalhussaini on 11/16/16.
 */

public class SafetyStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean isSafe = intent.getBooleanExtra(context.getString(R.string.SAFE), false);
        InteractionListener mListener = (InteractionListener) context;

        if (!isSafe) {
            ((InteractionListener) context).enableBtn(true);
            Toast.makeText(context, "The system is safe to use", Toast.LENGTH_LONG).show();

        } else {
            ((InteractionListener) context).enableBtn(false);
            Toast.makeText(context, "The system is NOT safe to use", Toast.LENGTH_LONG).show();

        }
    }
}
