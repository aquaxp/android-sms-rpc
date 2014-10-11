package tk.aquaxp.smsgate.activity;

import android.app.Activity;
import android.os.Bundle;

import java.io.IOException;

import tk.aquaxp.smsgate.R;
import tk.aquaxp.smsgate.restapi.APIServer;

/**
 * Created by mindworm on 08/10/14.
 */
public class MainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        try {
            APIServer lol = new APIServer(getApplicationContext(), 8080);
            lol.start();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
