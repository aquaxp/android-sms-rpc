package tk.aquaxp.smsgate.util;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.JsonWriter;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by mindworm on 09/10/14.
 */
public final class RPC {
    public  static final String SMS_INBOX = "";
    public  static final String SMS_SENT = "";

    Context context;

    public RPC(Context context){
        this.context = context;
    }

    public void sendSMS(String phoneNo, String text){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts = smsManager.divideMessage(text);

            smsManager.sendMultipartTextMessage(phoneNo, null, parts, null, null);

            Toast.makeText(context, String.format("Message was sended to %s", phoneNo), Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

    public void batchSendSMS(ArrayList<String> phoneNos, String text){
        for(String phoneNo:phoneNos) sendSMS(phoneNo, text);
    }

    public void listSMS(JsonWriter json, long id, boolean mo) throws JSONException{
        //TODO
    }

    public void transmitSMS(){

    }
}
