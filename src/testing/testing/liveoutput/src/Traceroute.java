import java.io.IOException;

/**
 * Created by grzegorz on 11.05.15.
 */
public class Traceroute extends ExtraCommand {


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

        System.out.println("Do sth " + cmd);

    }
}
