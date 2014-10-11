package tk.aquaxp.smsgate.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import tk.aquaxp.smsgate.R;
import tk.aquaxp.smsgate.activity.MainActivity;
import tk.aquaxp.smsgate.restapi.APIServer;

/**
 * Created by mindworm on 09/10/14.
 */
public final class RPCService extends Service {
    private static final String TAG = "RPCService";
    private static final String PREF = "RPCServicePreference";
    private static final String PREF_ENABLED = "serviceEnabled";

    private APIServer httpd;
    protected NotificationManager notificationManager;

    // Unique notification number for notification. We use it on Notification start, and to cancel it
    private final int NOTIFICATION = R.string.service_started;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        Log.d(TAG, "onCreate()");

        this.notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        try{
            httpd = new APIServer(this, 8080);
            httpd.start();
            Log.i(TAG,"in onCreate");
        } catch (IOException e){
            showNotification(R.string.service_failed);
        }



        // Display notification about starting in notification bar
        showNotification(R.string.service_started);

        Toast.makeText(getApplicationContext(), R.string.service_started, Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", String.format("Received start id %d: %s", startId, intent));

        // Mark this service as STICKY to continue running
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");

        httpd.stop();
        httpd = null;

        // Dismiss notification
        this.notificationManager.cancel(NOTIFICATION);

        //Tell the user that we stoped
        Toast.makeText(getApplicationContext(), R.string.service_stopped, Toast.LENGTH_LONG).show();
    }

    // Show notification while service is running
    private void showNotification(int textId) {
        final CharSequence text = getText(R.string.service_started);
        final PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        //final Notification notification = new Notification(R.drawable.ic_launcher, text, System.currentTimeMillis());

        Notification.Builder notificationBuilder = new Notification.Builder(this)
                .setContentTitle(text)
                .setContentText(text)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_launcher);

        this.notificationManager.notify(NOTIFICATION, notificationBuilder.build());
    }

    public static boolean isEnabled(Context context){
        SharedPreferences settings = context.getSharedPreferences(RPCService.PREF, MODE_PRIVATE);
        return settings.getBoolean(RPCService.PREF_ENABLED, true);
    }

    public static void setEnabled(Context context, boolean enabled){
        SharedPreferences settings = context.getSharedPreferences(RPCService.PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(RPCService.PREF_ENABLED, enabled);
        editor.apply();
    }
}
