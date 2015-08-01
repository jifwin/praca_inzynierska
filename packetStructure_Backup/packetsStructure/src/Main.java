import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //DNSFrame dnsframe = new DNSFrame("poczta.onet.pl");
        //dnsframe.compileFrame();

        Nslookup nslookup = new Nslookup();
        nslookup.run();
    }


}


