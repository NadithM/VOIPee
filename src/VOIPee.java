import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Nadith on 2/24/2016.
 */
public class VOIPee{

    private static final int PORT = 20000;



    public static void main(String[] args) throws SocketException {


        VOIPee voice = new VOIPee();
        voice.call();
    }


    public VOIPee(){

    }

    public void call () throws SocketException {
        Mic newcallmic = new Mic(PORT, new DatagramSocket(), "127.0.0.1" );
        newcallmic.run();
    }

}
