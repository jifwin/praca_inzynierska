package ver2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by gpietrus on 01.08.15.
 */
public class Utils {
    public static String byteArraytoBinaryString(byte[] bytes) //todo: move to another class
    {
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        for (int i = 0; i < Byte.SIZE * bytes.length; i++)
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        return sb.toString();
    }

    public static byte[] binaryStringToByteArray(String binaryString) {
        byte[] byteArray = new byte[binaryString.length() / 8];
        for (int i = 0; i < binaryString.length(); i += 8) {
            byteArray[i / 8] = (byte) Integer.parseInt(binaryString.substring(i, i + 8),2);
        }
        return byteArray;
    }

    public static BitField byteArrayToBitField(byte[] byteArray) {
        BitField bitField = new BitField(byteArray.length * 8);
        String bitString = "";
        for (int i = 0; i < byteArray.length; i++) {
            bitString += String.format("%8s", Integer.toBinaryString(byteArray[i])).replace(' ', '0');
        }

        bitField.loadBinaryString(bitString);

        return bitField;
    }

    public static int findFirstZeroByte(byte[] byteArray) {
        for(int i = 0; i < byteArray.length; i++) {
            if(byteArray[i] == 0) {
                String tmp = Integer.toBinaryString(byteArray[i]);
                return i;
            }
        }
        return -1; //todo: error
    }

    //todo: refactor, merge these two functions
    public static short byteArrayToShort(byte[] byteArray) {
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        buffer.order(ByteOrder.BIG_ENDIAN);
        short result = buffer.getShort();
        return result;
    }

    public static int byteArrayToInt(byte[] byteArray) {
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        buffer.order(ByteOrder.BIG_ENDIAN);
        int result = buffer.getInt();
        return result;
    }
}
