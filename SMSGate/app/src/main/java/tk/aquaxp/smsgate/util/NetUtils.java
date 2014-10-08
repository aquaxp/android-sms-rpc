package tk.aquaxp.smsgate.util;

import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created by mindworm on 08/10/14.
 */
public abstract class NetUtils {

    private static final String TAG = "NetUtils";

    public static ArrayList<String> getLocalIpAdresses(){
        ArrayList<String> adresses = new ArrayList<String>();

        try{
            Enumeration<NetworkInterface> ens = NetworkInterface.getNetworkInterfaces();
            while(ens.hasMoreElements()){
                NetworkInterface en = ens.nextElement();
                Enumeration<InetAddress> ips = en.getInetAddresses();

                while(ips.hasMoreElements()){
                    InetAddress ip = ips.nextElement();
                    if(!ip.isLoopbackAddress()){
                        adresses.add(ip.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            Log.e(TAG, "Getting list of IP adresses", e);
            //e.printStackTrace();
        }
        return adresses;
    }
}
