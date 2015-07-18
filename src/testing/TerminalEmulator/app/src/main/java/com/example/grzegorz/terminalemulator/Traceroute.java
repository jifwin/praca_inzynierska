package com.example.grzegorz.terminalemulator;

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

    }


}
