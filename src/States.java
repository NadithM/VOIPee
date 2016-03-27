/**
 * Created by Nadith on 2/28/2016.
 */
import java.net.* ;

public class States extends GUI implements Runnable {

    private final static int packetsize = 500;
    public static String state="waitforcall";
    private DatagramSocket socket=null;
    private String ip;


    public static boolean oncall=false;
    public static boolean ringing=false;
    public static boolean waitforcall=true;
    public static Sound ringtone;


   public States(String host,DatagramSocket sock){

       this.ip=host;
       this.socket = sock;


   }

    public static void changeState(String stat){

        state=stat;
    }
    public void run() {

        while (true) {
            switch (state) {
                case "waitforcall":
                    try {

                        // Convert the argument to ensure that is it valid
                        if(this.socket.isClosed()) this.socket = new DatagramSocket(GUI.CTRLPORT) ;
                       else {
                             // Create a packet
                            DatagramPacket packet = new DatagramPacket(new byte[packetsize], packetsize);
                            byte[] req = "ok".getBytes();

                            // Receive a packet (blocking)
                            socket.receive(packet);
                            System.out.println("got the request ....."+new String(packet.getData()));


                            // Return the packet to the sender


                            if(new String(packet.getData()).contains("Can I call?")){

                                States.waitforcall=false;
                                States.ringing=true;

                                jLabel1.setBackground(new java.awt.Color(0,153,0));
                                jLabel1.setFont(new java.awt.Font("SansSerif", 0, 36)); // NOI18N
                                jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                                jLabel1.setText("CALL FROM "+packet.getAddress().toString().substring(1));
                                changeState("ringing");
                                jButton1.setText("ANSWER");
                                GUI.packet = new DatagramPacket(req, req.length, packet.getAddress(), packet.getPort());

                                ringtone=new Sound("C:\\Users\\Nadith\\IdeaProjects\\Copy\\src\\viber.wav");
                               ringtone.loop();


                            }


                        }


                     }catch (Exception e) {
                        socket.close();
                        System.out.println(e);
                        e.printStackTrace();
                        return;
                    }
                    break;

                case "ringing":
                    //ipSystem.out.println("in ring");
                    break;

                case "oncall":
                    try {

                        // Convert the argument to ensure that is it valid
                        if(this.socket.isClosed()) this.socket = new DatagramSocket(GUI.CTRLPORT) ;
                        else  {

                            // Create a packet
                            DatagramPacket packet = new DatagramPacket(new byte[packetsize], packetsize);


                            // Receive a packet (blocking)
                            socket.receive(packet);

                            // Print the packet
                            System.out.println(packet.getAddress() + " " + packet.getPort() + ": " + new String(packet.getData()));
                            String callEnd = new String("CallEnd");
                           String packdata = new String(packet.getData());
                           // System.out.print(callEnd + " " + packdata);
                           // System.out.println(callEnd.equals(packdata));
                            if(packdata.contains(callEnd)) {
                               // System.out.println("  .... ");
                                changeState("waitforcall");

                                jButton1.setText("CALL");
                                voice.end();
                            }

                            else {
                                byte[] req = "Busy".getBytes();


                                packet = new DatagramPacket(req, req.length, packet.getAddress(), packet.getPort());
                                System.out.println("waiting for call...");
                                // Return the packet to the sender
                                socket.send(packet);

                            }
                            socket.close();

                        }


                    }catch (Exception e) {
                        socket.close();
                        System.out.println(e);
                        e.printStackTrace();
                        return;
                    }


                    States.waitforcall=true;
                    States.oncall=false;



                break;


            }
        }
    }
}








