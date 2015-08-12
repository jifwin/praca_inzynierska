package ver2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.Iterables.filter;

/**
 * Created by gpietrus on 01.08.15.
 */

public class DnsFrame extends Frame {

    DnsFrame() { //query frame

        //header
        frameHeader = new DnsHeader(DnsHeader.QR.query);

        //payload
        DnsQueryPayload dnsQueryPayload = new DnsQueryPayload("poczta.onet.pl");
        framePayloadList = new ArrayList<FramePayload>();
        framePayloadList.add(dnsQueryPayload);

    }

    DnsFrame(byte[] bytes) { //response frame created from bytes
        DnsHeader dnsHeader = new DnsHeader(Arrays.copyOfRange(bytes, 0, 12)); //first 12 bytes - header
        byte[] payloadsBytes = Arrays.copyOfRange(bytes, 12, bytes.length + 1); //todo: analyse and interprete payloads

        //analysing payloads
        int qdcount = dnsHeader.getQdcount().toInt();
        int ancount = dnsHeader.getAncount().toInt();
        int nscount = dnsHeader.getNscount().toInt();
        int arcount = dnsHeader.getArcount().toInt();

        int payloadsBytesIter = 0;
        framePayloadList = new ArrayList<FramePayload>();

        for (int i = 0; i < qdcount; i++) {
            //load bytes to dnsQueryPayload
            int zeroByteIndex = Utils.findFirstZeroByte(payloadsBytes);
            int endOfPayloadIndex = zeroByteIndex+4+1;
            byte[] payloadBytes = Arrays.copyOfRange(payloadsBytes,payloadsBytesIter,endOfPayloadIndex);

            payloadsBytesIter += endOfPayloadIndex;

            DnsQueryPayload dnsQueryPayload = new DnsQueryPayload(payloadBytes);
            System.out.println(dnsQueryPayload.toString());
            System.out.println(dnsQueryPayload.getDomainName());
            framePayloadList.add(dnsQueryPayload); //add to list
        }



        for (int i = 0; i < ancount; i++) {
            //todo: nie do payloadsBytes.length tylko obliczyc jakos koniec? gdy jest wiecej odpowiedzi
            byte[] payloadBytes = Arrays.copyOfRange(payloadsBytes,payloadsBytesIter, payloadsBytes.length);
            DnsResponsePayload dnsResponsePayload = new DnsResponsePayload(payloadBytes);
            System.out.println(dnsResponsePayload.toString());
            System.out.println(dnsResponsePayload.getAddress());
            System.out.println(dnsResponsePayload.getTtl());
        }




        for (int i = 0; i < nscount; i++) {
            //todo: load
        }
        for (int i = 0; i < arcount; i++) {
            //todo: load
        }




    }

    public List<DnsPayload> getPayloads(Class c) {
        //todo: zmienic na iterable
        List<DnsPayload> specificDnsPayloads = new ArrayList<DnsPayload>();
        for (FramePayload payload : framePayloadList) {
            if (payload.getClass() == c) {
                specificDnsPayloads.add((DnsPayload) payload);
            }
        }
        return specificDnsPayloads;
    }

}
