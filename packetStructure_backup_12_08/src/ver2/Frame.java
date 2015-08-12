package ver2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gpietrus on 01.08.15.
 */
abstract public class Frame {

    protected FrameHeader frameHeader;
    protected List<FramePayload> framePayloadList;

    //todo: remove? public Frame(FrameHeader frameHeader, List<FramePayload> framePayloadList) {};

/*
    public Frame(boolean[] bits) { //constructor from bits
    }

    public Frame(byte[] bytes) { //constructor from bytes

    }

    public boolean[] getBits() {
        return new boolean[0]; //todo: implement
    }
*/
    public byte[] getBytes() throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] frameHeaderBytes = frameHeader.getBytes();
        byteArrayOutputStream.write(frameHeaderBytes);

        for (FramePayload framePayload : framePayloadList) {
            byte[] payloadBytes = framePayload.getBytes();
            //debug only
            String tmp = Utils.byteArraytoBinaryString(payloadBytes);
            byteArrayOutputStream.write(payloadBytes);
        }

        //concating
        byte fullFrameBytes[] = byteArrayOutputStream.toByteArray();




        return fullFrameBytes;
    }

}
