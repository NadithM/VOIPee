/**
 * Created by Nadith on 2/28/2016.
 */
import java.net.* ;

public class States implements Runnable {

    private final static int packetsize = 500;
    public static String state="waitforcall";
    private DatagramSocket socket=null;
    private String ip;
    private int PORT = 30000;

   public States(String host,DatagramSocket sock){

       this.ip=host;
       this.socket = sock;


   }
    public void run() {

        while (true) {
            switch (state) {
                case "waitforcall":
                    try {

                        // Convert the argument to ensure that is it valid
                        if(this.socket.isClosed()) this.socket = new DatagramSocket(PORT) ;
                       else  {

                            System.out.println("waiting for call...");

                            // Create a packet
                            DatagramPacket packet = new DatagramPacket(new byte[packetsize], packetsize);
                            byte[] req = "ok".getBytes();

                            // Receive a packet (blocking)
                            socket.receive(packet);

                            // Print the packet
                            System.out.println(packet.getAddress() + " " + packet.getPort() + ": " + new String(packet.getData()));

                            packet = new DatagramPacket(req, req.length, packet.getAddress(), packet.getPort());
                            // Return the packet to the sender
                            socket.send(packet);
                            socket.close();
                            state = "oncall";
                        }


                     }catch (Exception e) {
                        socket.close();
                        System.out.println(e);
                        e.printStackTrace();
                        return;
                    }
                    break;

                case "oncall":
                break;


            }
        }
    }
}








