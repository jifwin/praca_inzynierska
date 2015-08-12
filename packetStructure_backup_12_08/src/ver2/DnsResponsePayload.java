package ver2;

import java.util.Arrays;

/**
 * Created by gpietrus on 02.08.15.
 */
public class DnsResponsePayload extends DnsPayload {

    private byte[] name;
    private byte[] type = new byte[2];
    private byte[] dnsClass = new byte[2];
    private byte[] ttl = new byte[4];
    private byte[] rdlength = new byte[2];
    private byte[] rdata;


    public DnsResponsePayload(byte[] payloadsBytes) {
        //todo:
        //temporarly i use 2 byte pointer (c0 0c) so name is only 2 bytes

        int bytesIter = 0;
        //name
        name = Arrays.copyOfRange(payloadsBytes, bytesIter, bytesIter + 2);
        bytesIter += 2;

        //type
        type = Arrays.copyOfRange(payloadsBytes, bytesIter, bytesIter + type.length);
        bytesIter += type.length;

        //dnsClass
        dnsClass = Arrays.copyOfRange(payloadsBytes, bytesIter, bytesIter + dnsClass.length);
        bytesIter += dnsClass.length;

        //ttl
        ttl = Arrays.copyOfRange(payloadsBytes, bytesIter, bytesIter + ttl.length);
        bytesIter += ttl.length;

        //rdlength
        rdlength = Arrays.copyOfRange(payloadsBytes, bytesIter, bytesIter + rdlength.length);
        bytesIter += rdlength.length;

        //rdata
        int rdlengthInt = Utils.byteArrayToShort(rdlength);
        rdata = Arrays.copyOfRange(payloadsBytes, bytesIter, bytesIter + rdlengthInt);
    }


/*
    private void addToList() {

        listOfFields.add(Utils.byteArrayToBitField(qnameBytes)); //todo: konwecja to bitfield ale po zainicjalizowaniu?
        listOfFields.add(qtype);
        listOfFields.add(qclass);
    }
    */
/*
    public String getDomainName() {

        StringJoiner stringJoiner = new StringJoiner(".");
        int labelPartLength = qnameBytes[0];
        int labelIter = 1;
        while(labelPartLength != 0) {
            String domainNamePart = "";
            int i;
            for (i = labelIter; i < labelIter + labelPartLength; i++) {
                domainNamePart += (char) (qnameBytes[i] & 0xFF);
            }
            labelPartLength = qnameBytes[i];
            labelIter = i + 1;
            stringJoiner.add(domainNamePart);
        }

        return stringJoiner.toString();
    }
*/

    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        //todo: refactor
        stringBuilder.append("name\t");
        stringBuilder.append(Utils.byteArraytoBinaryString(name));
        stringBuilder.append("\n");
        stringBuilder.append("type\t");
        stringBuilder.append(Utils.byteArraytoBinaryString(type));
        stringBuilder.append("\n");
        stringBuilder.append("dnsClass\t");
        stringBuilder.append(Utils.byteArraytoBinaryString(dnsClass));
        stringBuilder.append("\n");
        stringBuilder.append("ttl\t");
        stringBuilder.append(Utils.byteArraytoBinaryString(ttl));
        stringBuilder.append("\n");
        stringBuilder.append("rdlength\t");
        stringBuilder.append(Utils.byteArraytoBinaryString(rdlength));
        stringBuilder.append("\n");
        stringBuilder.append("rdata\t");
        stringBuilder.append(Utils.byteArraytoBinaryString(rdata));
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

    public String getAddress() {
        //todo: zalozenie ze 4 bajty tylko
        //todo: sprawdzic co jak bedzie ich wiecej

        StringBuilder address = new StringBuilder();

        for (int i = 0; i < rdata.length; i++) {
            address.append(rdata[i] & 0xFF); //interprete as unsigned
            address.append(".");
        }
        return address.toString();

    }

    public int getTtl() {
        return Utils.byteArrayToInt(ttl);
        //todo: tu cos jest zle
        //todo: zawsze wychodzi inna wartosc
        //todo: zaczac tu
    }
}
