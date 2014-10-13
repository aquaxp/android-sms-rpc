package tk.aquaxp.smsgate.util;

import android.content.Context;
import android.content.IntentFilter;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.util.JsonWriter;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;

import tk.aquaxp.smsgate.asynctask.TransmitTask;
import tk.aquaxp.smsgate.receiver.SMSBroadcastReceiver;

/**
 * Created by mindworm on 09/10/14.
 */
public final class RPC {
    public  static final String SMS_INBOX = "";
    public  static final String SMS_SENT = "";
    public static final String TAG = "RPC";

    Context context;
    private ArrayList<String> subscribers;
    private SMSBroadcastReceiver smsBroadcastReceiver;
    public String server;

    public RPC(Context context){
        this.context = context;
        subscribers = new ArrayList<String>();

        server = "";

        smsBroadcastReceiver = new SMSBroadcastReceiver();
        smsBroadcastReceiver.setRPCHandler(this);
        IntentFilter callInterceptorIntentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        this.context.registerReceiver(smsBroadcastReceiver, callInterceptorIntentFilter);
    }

    public void setSubscribers(ArrayList<String> subs){
        subscribers = subs;
    }

    public ArrayList<String> getSubscribers(){
        return subscribers;
    }

    public void cleanDestroy(){
        this.context.unregisterReceiver(smsBroadcastReceiver);
    }

    public void sendSMS(String phoneNo, String text){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts = smsManager.divideMessage(text);

            smsManager.sendMultipartTextMessage(phoneNo, null, parts, null, null);
        } catch (Exception e) {
            Log.e(TAG,"sending", e);
            e.printStackTrace();
        }
    }

    public void batchSendSMS(ArrayList<String> phoneNos, String text){
        for(String phoneNo:phoneNos) sendSMS(phoneNo, text);
    }

    public void listSMS(JsonWriter json, long id, boolean mo) throws JSONException{
        //TODO
    }

    public void transmitSMS(String phoneNo, String body){
        if (server.isEmpty()){
            Log.i(TAG, String.format("No server to transmit message from %s", phoneNo));
            return;
        }
        if (!subscribers.isEmpty()){
            for(String s:subscribers){
                if (PhoneNumberUtils.compare(s,phoneNo)){
                    try {
                        new TransmitTask().execute(String.format("%s?from=%s&text=%s", this.server, phoneNo, body));
                        Log.i(TAG, String.format("transmitting message from:%s to %s?from=%s&text=%s", phoneNo, this.server, phoneNo, body));
                    }
                    catch (Exception e){
                        //TODO
                    }
                } else {
                    Log.i(TAG, String.format("%s is not from subscribers list", phoneNo));
                }
            }
        } else{
            Log.i(TAG, String.format("No subscribers to transmit message from %s", phoneNo));
        }
    }


}
