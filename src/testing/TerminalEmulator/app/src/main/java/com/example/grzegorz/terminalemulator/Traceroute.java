package com.example.grzegorz.terminalemulator;

import android.app.NativeActivity;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by grzegorz on 11.05.15.
 */
public class Traceroute extends ExtraCommand {

    private Socket socket;
    private final String SERVER_IP = "193.59.201.49";

    public Traceroute(String cmd) {
        super(cmd);
    }

    @Override
    public Boolean allFinished() throws IOException {
        return null;
    }

    @Override
    public void run() {

        String[] cmd_parts = cmd.split(" ");


        for(int j = 0; j < 15; j++) {

            NativeCommand ping = new NativeCommand("ping -c 1 " + "-t " + j + " " + "89.46.67.140");
            ping.start();

            synchronized (ping) {
                try {
                    ping.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //todo: implement to only one stream!!!!!!!!! wazne nie tylko w tym tylko wszedzie
            final InputStream is = ping.getInputStream();
            final InputStream es = ping.getErrorStream();
            final OutputStream os = ping.getOutputStream();

            char c;
            String cale = "";
            for (int i = 0; i < 125; i++) {
                try {
                    c = (char) is.read();
                    cale += c;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.d("TRACEROUTE", cale);

        }


    }


}
