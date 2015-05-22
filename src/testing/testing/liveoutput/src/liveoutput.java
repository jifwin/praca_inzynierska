import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by grzegorz on 11.05.15.
 */
public class liveoutput {

    public static void main(String[] args) throws InterruptedException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {



        CommandExecutor ce = new CommandExecutor();
        ce.executeCommand("ping -c 3 google.com");
        ce.executeCommand("ls /home/grzegorz");
        ce.executeCommand("traceroute");
        ce.executeCommand("nslookup");


/*
        NativeCommand command = new NativeCommand();
        command.start();

        synchronized (command) {
            command.wait();
        }
        InputStream is = command.getInputStream();
        InputStream es = command.getErrorStream();
        OutputStream os = command.getOutputStream();
        System.out.println(is.toString());
        System.out.println(es.toString());

        while(!command.allFinished()) {

            while(is.available() != 0) {
                char c = (char) is.read();
                System.out.print(c);
            }

            while(es.available() != 0) {
                char c = (char) es.read();
                System.out.print(c);
            }

            Thread.sleep(100); //todo : better
        }

        System.out.println("koniec petli");

        */


/*
        Runtime runtime = Runtime.getRuntime();
        try {

            String cmd = "ping";
            Process process = runtime.exec(cmd);
            process.waitFor();

            InputStream is = process.getInputStream();
            OutputStream out = process.getOutputStream();
            InputStream eis = process.getErrorStream();

            InputStreamReader isr = new InputStreamReader(is);


            while(true) {

                if(eis.available() != 0) {
                    char c =  (char) eis.read();
                    System.out.print(c);
                }

            }

                    //todo: czy istnieje inputstreamreader w ktorym mozna zarejestrowac onready



        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }*/

    }

}