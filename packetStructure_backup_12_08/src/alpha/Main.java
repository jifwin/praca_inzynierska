package alpha;


import ver2.Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by gpietrus on 11.08.15.
 */
public class Main {

    public static int byteArrayToInt(byte[] byteArray) {
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        //buffer.order(ByteOrder.LITTLE_ENDIAN);  // if you want little-endian
        buffer.order(ByteOrder.BIG_ENDIAN);

        int result = buffer.getInt();

        return result;
    }

    //little - bardziej znaczace po prawej


    public static void main(String[] args) {
        byte[] bytes = new byte[4];

        bytes[0] = (byte) 0;
        bytes[1] = Utils.binaryStringToByteArray("10110110")[0];
        bytes[2] = Utils.binaryStringToByteArray("01100101")[0];
        bytes[3] = Utils.binaryStringToByteArray("10101101")[0];

        //wyglada na to ze dziala, od tego zaczacń
        //todo: dobre podejscie!!!
        //todo: przetestowac to dzialanie podzniej z wiresharkiem, moze trzeba zmienic na big endian?
        //todo: sprawdzic gdzie jeszcze uztwany jest byteArrayToInt

        int value = byteArrayToInt(bytes);
        System.out.println(value);
    }




}
