package tk.aquaxp.smsgate.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import tk.aquaxp.smsgate.R;
import tk.aquaxp.smsgate.service.RPCService;
import tk.aquaxp.smsgate.util.NetUtils;

/**
 * Created by mindworm on 08/10/14.
 */
public class MainActivity extends Activity{
    private static final String TAG = "MainActivity";
    ToggleButton toggleButton;
    TextView localIp;

    public boolean isMyServiceRunning(){
        String myService = RPCService.class.getName();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo: activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(myService.equals(serviceInfo.service.getClassName())){
                return true;
            }
        }
        return false;
    }

    protected void startRPCService(){
        Log.i(TAG, "Starting RPCService");
        startService(new Intent(this,RPCService.class));
    }

    protected void stopRPCService(){
        Log.i(TAG, "Stopping RPCService");
        stopService(new Intent(this,RPCService.class));
    }

    public void serviceButtonClick(View view){
        if(!isMyServiceRunning()){
            startRPCService();
            RPCService.setEnabled(this, true);
        } else{
            stopRPCService();
            RPCService.setEnabled(this, false);
        }
        toggleButton.setChecked(isMyServiceRunning());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate in MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        toggleButton = (ToggleButton) findViewById(R.id.serviceToggleButton);
        localIp = (TextView) findViewById(R.id.localIp);
        boolean enabled = RPCService.isEnabled(this);

        if(enabled) {
            if (!isMyServiceRunning()) {
                startRPCService();
            }
        }

        toggleButton.setChecked(isMyServiceRunning());
        localIp.setText(String.format("Listening on: %s, port 8080", NetUtils.getLocalIpAdresses()));
    }
}

