package com.example.grzegorz.terminalemulator;

import android.os.Handler;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by grzegorz on 11.05.15.
 */
public class CommandExecutor {

    List<Class<? extends ExtraCommand>> extracommands = new ArrayList<>();

    public void registerCommand(Class<? extends ExtraCommand> extracommand) {
        extracommands.add(extracommand);
    }

    private MainActivity ma = null;

    public CommandExecutor(MainActivity ma) {
        this.registerCommand(Traceroute.class);
        this.registerCommand(Nslookup.class);
        this.ma = ma;
    }


    public void executeCommand(final String cmd, final MainActivity ma) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException, InterruptedException {

//first look in list if commands exists

       // ma.handler.sendEmptyMessage(0);
        String[] cmd_parts = cmd.split(" ");

        for (Class<? extends ExtraCommand> extracommand: extracommands) {
            String className = extracommand.getName().toLowerCase();
            if (className.equals(cmd_parts[0])) { // if equals zero argument in cmd (name of command)

                Constructor ctor = extracommand.getConstructor(String.class);
                ExtraCommand ec = (ExtraCommand) ctor.newInstance(cmd);
                ec.start();
                return;
            }
        }

        //if not execute native command
        final NativeCommand nc = new NativeCommand(cmd);
        nc.start();
        
        synchronized (nc) {
            nc.wait();
        }


        final InputStream is = nc.getInputStream();
        final InputStream es = nc.getErrorStream();
        final OutputStream os = nc.getOutputStream();

        final Handler handler = new Handler();

        final Runnable r = new Runnable() {

            public void run() {
                TextView tv = (TextView) ma.findViewById(R.id.textView);
                ScrollView sv = (ScrollView) ma.findViewById(R.id.scrollView);
                tv.append("\n" + cmd + "\n");
                try {
                    while(is.available() != 0) {
                        char c = (char) is.read();
                        tv.append(String.valueOf(c));

                    }
                    while(es.available() != 0) {
                        char c = (char) es.read();
                        tv.append(String.valueOf(c));

                    }
                    sv.fullScroll(sv.FOCUS_DOWN); //todo: repair scrolling
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    if(!nc.allFinished()) { //if not finished wait for next text
                        handler.postDelayed(this, 10);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        handler.postDelayed(r,0);



        return;
/*
        while(!nc.allFinished()) {
            while(is.available() != 0) {
                char c = (char) is.read();
                System.out.print(c);
                Log.d("OUTPUT", String.valueOf(c));
                ma.handler.sendEmptyMessage(0);


            }

            while(es.available() != 0) {
                char c = (char) es.read();
                System.out.print(c);
                ma.handler.sendEmptyMessage(0);



            }
            Thread.sleep(10000); //todo : better
        }*/


    }


}
