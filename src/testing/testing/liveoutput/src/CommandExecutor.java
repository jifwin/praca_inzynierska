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


    public CommandExecutor() {
        this.registerCommand(Traceroute.class);
        this.registerCommand(Nslookup.class);


    }


    public void executeCommand(String cmd) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException, InterruptedException {

//first look in list if commands exists

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
        NativeCommand nc = new NativeCommand(cmd);
        nc.start();

        synchronized (nc) {
            nc.wait();
        }

        InputStream is = nc.getInputStream();
        InputStream es = nc.getErrorStream();
        OutputStream os = nc.getOutputStream();

        while(!nc.allFinished()) {

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



    }


}
