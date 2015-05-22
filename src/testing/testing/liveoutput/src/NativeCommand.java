import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by grzegorz on 11.05.15.
 */
public class NativeCommand extends Command {


    public NativeCommand(String cmd) {
        super(cmd);
    }

    @Override
    public Boolean allFinished() throws IOException {
        if(this.getState() == State.TERMINATED && is.available() == 0 && es.available() == 0) {
            return true;
        }
        return false;
    }




    @Override
    public void run() {


        Runtime runtime = null;
        Process process = null;
        synchronized (this) {

            runtime = Runtime.getRuntime();

            try {
                process = runtime.exec(cmd.split(" "));
            } catch (IOException e) {
                e.printStackTrace();
            }


            assert process != null;
            is = process.getInputStream();
            es = process.getErrorStream();
            os = process.getOutputStream();
            notify();
        }


        try {
            process.waitFor();
            System.out.println("koniec procesu");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
