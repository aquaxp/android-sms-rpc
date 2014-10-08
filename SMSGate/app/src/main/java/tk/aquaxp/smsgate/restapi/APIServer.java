package tk.aquaxp.smsgate.restapi;

import android.app.Service;

/**
 * Created by mindworm on 08/10/14.
 */
public class APIServer extends NanoHTTPD {
    private static final boolean DEBUG = false;
    private static final String TAG = "APIServer";


    public APIServer(Service apiservice, int port) {
        super(port);
    }
}
