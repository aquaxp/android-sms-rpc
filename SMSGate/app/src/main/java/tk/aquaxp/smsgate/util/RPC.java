package tk.aquaxp.smsgate.util;

import android.content.Context;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.JsonWriter;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;

import tk.aquaxp.smsgate.receiver.SMSBroadcastReceiver;

/**
 * Created by mindworm on 09/10/14.
 */
public final class RPC {
    public  static final String SMS_INBOX = "";
    public  static final String SMS_SENT = "";
    public static final String TAG = "RPC";

    Context context;
    public ArrayList<String> subscribers;
    private SMSBroadcastReceiver smsBroadcastReceiver;

    public RPC(Context context){
        this.context = context;
        subscribers = new ArrayList<String>();

        smsBroadcastReceiver = new SMSBroadcastReceiver();
        smsBroadcastReceiver.setRPCHandler(this);
        IntentFilter callInterceptorIntentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        this.context.registerReceiver(smsBroadcastReceiver, callInterceptorIntentFilter);
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
        //TODO
        Log.i(TAG, String.format("transmitting message from:%s", phoneNo));
    }


}
