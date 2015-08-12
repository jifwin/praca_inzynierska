package ver2;

import ver1.DNSFrame;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gpietrus on 01.08.15.
 */
public class Nslookup {

    private DatagramSocket datagramSocket;
    private InetAddress address;
    private DatagramPacket sendPacket;
    private DatagramPacket recvPacket;
    private int port = 53;

    public void run(String args) throws IOException {

        DnsFrame queryDnsFrame = new DnsFrame();
        DnsFrame responseDnsFrame;

        datagramSocket = new DatagramSocket();
        address = InetAddress.getByName("62.179.1.62"); //todo: change to another

        send(queryDnsFrame);
        recv();

    }

    public void send(DnsFrame dnsFrame) throws IOException {
        byte[] frameBytes = dnsFrame.getBytes();
        sendPacket = new DatagramPacket(frameBytes, frameBytes.length, address, port);
        datagramSocket.send(sendPacket);
    }

    public void recv() throws IOException {
        DnsFrame responseDnsFrame;
        byte[] responseBuf = new byte[512]; //todo: max of udp?
        DatagramPacket responseDatagramPacket = new DatagramPacket(responseBuf, responseBuf.length);
        datagramSocket.receive(responseDatagramPacket);
        int receivedLength = responseDatagramPacket.getLength();
        responseDnsFrame = new DnsFrame(Arrays.copyOfRange(responseBuf, 0, receivedLength));

        //DnsQueryPayloads
        List<DnsPayload> payloads = responseDnsFrame.getPayloads(DnsQueryPayload.class);
        for (DnsPayload payload : payloads) {
            DnsQueryPayload dnsQueryPayload = (DnsQueryPayload) payload;
            String domainName = dnsQueryPayload.getDomainName();
            System.out.println(domainName);
        }

        //DnsResponsePayloads
        //todo: implement

    }
}
