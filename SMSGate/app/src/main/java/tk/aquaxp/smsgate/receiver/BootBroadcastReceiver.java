package tk.aquaxp.smsgate.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import tk.aquaxp.smsgate.service.RPCService;

/**
 * Created by mindworm on 11/10/14.
 */
public class BootBroadcastReceiver extends BroadcastReceiver{
    private static final String TAG = "BootBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean enabled = RPCService.isEnabled(context);

        if(!enabled) return;
           try {
               Log.i(TAG, "Starting RPCService");
               context.startService(new Intent(context, RPCService.class));
           } catch (Exception e){
               Log.e(TAG, "onReceive", e);
           }
    }
}
