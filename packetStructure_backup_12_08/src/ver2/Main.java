package ver2;

import java.io.IOException;

/**
 * Created by gpietrus on 01.08.15.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Nslookup nslookup = new Nslookup();
        String arguments = "poczta.onet.pl";
        nslookup.run(arguments);
    }
}
