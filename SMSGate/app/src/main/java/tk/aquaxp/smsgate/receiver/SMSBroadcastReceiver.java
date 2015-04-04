package tk.aquaxp.smsgate.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.net.URLEncoder;

import tk.aquaxp.smsgate.util.RPC;

/**
 * Created by mindworm on 08/10/14.
 */
public class SMSBroadcastReceiver extends BroadcastReceiver{
    Context mContext;
    RPC myRpc = null;

    public void setRPCHandler(RPC rpc){
        myRpc = rpc;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        mContext = context;

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from = "";
            if (bundle != null){
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    String message = "_";

                    for (int i = 0; i < msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();

                        message += msgBody;
                        message = URLEncoder.encode(message, "UTF-8");
                    }
                    myRpc.transmitSMS(msg_from, message);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
