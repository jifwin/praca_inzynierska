package com.example.grzegorz.terminalemulator;

import android.app.NativeActivity;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
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

    private String extractIP(String pingResult) {
        int fromPosition = pingResult.indexOf("From");
        int icmpPosition = pingResult.indexOf("icmp_seq");
        int bytesPosition = pingResult.indexOf("64 bytes"); //only in last ping
        String ip = "";
        if (bytesPosition == -1) {
            ip = pingResult.substring(fromPosition + 5, icmpPosition - 1);
        }
        else { //if last
            ip = pingResult.substring(bytesPosition+14, icmpPosition - 2);
        }
        Log.d("TRACEROUTE IP",ip);
        return ip;
    }

    @Override
    public void run() {

        String[] cmd_parts = cmd.split(" ");
        String dstIP = "89.46.67.140";

        //todo: jak przekazac przez inputstream spowrotem

        //


        final int PIPE_BUFFER = 8;
        PipedInputStream inPipe = new PipedInputStream(PIPE_BUFFER);

        PipedOutputStream outPipe = null;
        try {
            outPipe = new PipedOutputStream(inPipe);
        } catch (IOException e) {
            e.printStackTrace();
        }
        is = inPipe;
        //todo: sprwadzic czy to zadziala gdy zaimplementuje czytanie z tego a nie z native command tylko





        //

        for(int j = 1; j < 64; j++) { //todo: specify 64 by parameter

            NativeCommand ping = new NativeCommand("ping -c 1 " + "-t " + j + " " + dstIP);
            ping.start();

            synchronized (ping) {
                try {
                    ping.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //todo: implement to only one stream!!!!!!!!! wazne nie tylko w tym tylko wszedzie
            final InputStream pingIs = ping.getInputStream();
            final InputStream pingEs = ping.getErrorStream();
            final OutputStream pingOs = ping.getOutputStream();

            String output = "";
            try { //todo: zrobic inaczej? bo malo optymalne
                while(!ping.allFinished()) {
                    while(pingIs.available() != 0)  {
                        output += (char) pingIs.read();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            String ip = extractIP(output);

            try {
                outPipe.write(j);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(ip.equals(dstIP)) break; //if equals don't ping more


        }


    }


}
