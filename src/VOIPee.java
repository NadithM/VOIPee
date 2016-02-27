import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by Nadith on 2/24/2016.
 */
public class VOIPee{

    private static final int PORT = 20000;
    public boolean oncall = false;
    private Thread threadSpeaker;
    private Thread threadMic;


    public static void main(String[] args) throws SocketException {


        VOIPee voice = new VOIPee();
        voice.call("127.0.0.1");
    }


    public VOIPee(){

    }

    public void call (String IP) throws SocketException {
        if(!oncall) {
            oncall = connectionSetup(IP);
            if(oncall) {
                Speaker newcallSpeaker = new Speaker(new DatagramSocket(PORT));
                threadSpeaker = new Thread(newcallSpeaker);
                threadSpeaker.start();
                Mic newcallmic = new Mic(PORT, IP);
                threadMic = new Thread(newcallmic);
                threadMic.start();
            }
        }
    }

    private boolean connectionSetup(String IP)  {
        DatagramSocket sock = null;
        try {
            sock = new DatagramSocket();
            byte[] req= "Can I call?".getBytes();
            InetAddress host = InetAddress.getByName(IP) ;
            DatagramPacket packet = new DatagramPacket(req, req.length, host, PORT);
            sock.send(packet);

            packet.setData( new byte[500] ) ;
            // Wait for a response from the server
            sock.receive( packet ) ;
            if("ok".equals(new String(packet.getData()))) {
                sock.close();
                return true;
            }else{
                sock.close();
                return false;
            }


        } catch (Exception e) {
            sock.close();
            e.printStackTrace();
        }
        return false;
    }
}
