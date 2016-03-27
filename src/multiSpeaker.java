import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;

/**
 * Created by Rama on 3/27/2016.
 */
public class multiSpeaker extends Speaker {
    private MulticastSocket mulsocket;
    public multiSpeaker(MulticastSocket sock) {
        super(sock);
        mulsocket = sock;
        try {
            mulsocket.joinGroup(InetAddress.getByName(States.IPs[States.IPs.length-2]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void captureAndPlay() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        stopPlay = false;
        int packetsize = 501;
        DatagramPacket packet = new DatagramPacket( new byte[packetsize], packetsize) ;
        try {

            while (!stopPlay) {
                mulsocket.receive( packet ) ;
                tempBuffer1= packet.getData();// get the paket to byte buffer
                byteArrayOutputStream.write(tempBuffer1, 0, packetsize);

                byte b = tempBuffer1[500];


                // System.out.println( b + " packet received :  ");

                boolean play = useArraysBinarySearch(b ,tempBuffer1);//to check the packet squence
                //  System.out.println(play);
                //playstart ,and aplaystop for play the required part of the buffer.tempbufferfinal is final buffer to play
                if(playfinal) sourceDataLine.write(tempBufferfinal, playstart, tempBufferfinal.length);//playing audio available in tempBuffer

                playfinal=false;
            }
            byteArrayOutputStream.close();
            mulsocket.close();
            sourceDataLine.close();
        } catch (IOException e) {
            System.out.println(e);
            mulsocket.close();
            System.exit(0);
        }
    }


}
