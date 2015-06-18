package com.example.grzegorz.terminalemulator;

import android.util.Log;

import org.apache.commons.net.whois.WhoisClient;

import java.io.IOException;


/**
 * Created by grzegorz on 18.06.15.
 */
public class Whois extends ExtraCommand {
    public Whois(String cmd) {
        super(cmd);
    }

    @Override
    public Boolean allFinished() throws IOException {
        return null;
    }

    @Override
    public void run() {
        WhoisClient whois;

        whois = new WhoisClient();

        try {
            whois.connect(WhoisClient.DEFAULT_HOST);


            Log.d("WHOIS", whois.query("foobar"));
            whois.disconnect();
        } catch(IOException e) {
            System.err.println("Error I/O exception: " + e.getMessage());
            return;
        }
    }
}
