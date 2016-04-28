
import java.net.* ;

public class States implements Runnable {

    private final static int packetsize = 500;
    public static String state="waitforcall";
    public static DatagramSocket socket=null;
    private String ip;
     public static String [] IPs;


    public static boolean oncall=false;
    public static boolean ringing=false;
    public static boolean waitforcall=true;
    public static boolean isgroupcall=false;
    public static Sound ringtone;
    public static  String grpID;
    public static boolean islisten=false;
    public static boolean isspeak=true;
    public static String [] groupIPs;
    public static int groupSize;



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
                            if(new String(packet.getData()).contains("change state")){
                                state = "oncall";
                            } else if(new String(packet.getData()).contains("Can I call?")){

                                States.waitforcall=false;
                                States.ringing=true;

                                GUI.jLabel1.setBackground(new java.awt.Color(0,153,0));
                                GUI.jLabel1.setFont(new java.awt.Font("SansSerif", 0, 36)); // NOI18N
                                GUI.jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                                GUI.jLabel1.setText("CALL FROM "+packet.getAddress().toString().substring(1));
                                changeState("ringing");
                                GUI.jButton1.setText("ANSWER");
                                GUI.jButton1.setBackground(new java.awt.Color(0,150,51));

                                GUI.packet = new DatagramPacket(req, req.length, packet.getAddress(), packet.getPort());

                                ringtone=new Sound("");
                               ringtone.loop();



                            }else if(new String(packet.getData()).contains("let's group call?")){

                                String IP = new String(packet.getData());
                                IPs = IP.split(":");
                                States.grpID=IPs[2];
                                //System.out.println(States.IPs[0]+"----"+packet.getAddress().toString().substring(1)+"----"+States.IPs[2]+"----");

                                groupIPs=IPs[1].split(",");

                                States.groupSize=groupIPs.length;


                                States.waitforcall=false;
                                States.ringing=true;

                                GUI.jLabel1.setBackground(new java.awt.Color(0,153,0));
                                GUI.jLabel1.setFont(new java.awt.Font("SansSerif", 0, 36)); // NOI18N
                                GUI.jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                                GUI.jLabel1.setText("Group call");
                                changeState("ringing");
                                GUI.jButton1.setText("ANSWER");
                                GUI.jButton1.setBackground(new java.awt.Color(0,150,51));


                                GUI.jTextField2.setText(IPs[1]);

                                GUI.jTextField1.setText(IPs[2]);
                                isgroupcall = true;
                                GUI.packet = new DatagramPacket(req, req.length, packet.getAddress(), packet.getPort());

                                ringtone=new Sound("");
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
                        if(this.socket.isClosed()) {
                            this.socket = new DatagramSocket(GUI.CTRLPORT);
                        }else  {

                            // Create a packet
                            DatagramPacket packet = new DatagramPacket(new byte[packetsize], packetsize);


                            // Receive a packet (blocking)
                            socket.receive(packet);

                            // Print the packet
                           // System.out.println(packet.getAddress() + " " + packet.getPort() + ": " + new String(packet.getData()));
                            String callEnd = new String("CallEnd");
                           String packdata = new String(packet.getData());
                           // System.out.print(callEnd + " " + packdata);
                           // System.out.println(callEnd.equals(packdata));
                            if(packdata.contains(callEnd)) {
                               // System.out.println("  .... ");
                                packet.setAddress(packet.getAddress());
                                packet.setPort(30000);
                                socket.send(packet);
                                changeState("waitforcall");

                                GUI.jButton1.setText("CALL");
                                GUI.jButton1.setBackground(new java.awt.Color(0,201,51));

                                GUI.voice.end();
                                System.out.println(state);
                            } else {
								
				if(!isgroupcall){
                                byte[] req = "Busy".getBytes();


                                packet = new DatagramPacket(req, req.length, packet.getAddress(), packet.getPort());
                                System.out.println("waiting for call...");
                                // Return the packet to the sender
                                socket.send(packet);
                                
                                }
                              
                            }


                        }


                    }catch (Exception e) {

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








