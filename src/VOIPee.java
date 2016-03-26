import java.io.IOException;
import java.net.*;

/**
 * Created by Nadith on 2/24/2016.
 */
public class VOIPee{

    public static final int PORT = 20000;
    public static boolean  oncall = false;
    public Thread threadSpeaker;
    public Thread threadMic;
    public static Speaker newcallSpeaker;
    public static Mic newcallmic;
    public States userState;

    public Thread threadStates;
    public static DatagramSocket socket=null;
    public static  String grpID;
    public static boolean isgroupcall=false;
    public static boolean islisten=false;
    public static boolean isspeak=true;


    public VOIPee(){

    }

    public void StartStates(String IP,DatagramSocket socket){

        userState = new States("127.0.0.1",socket);
        threadStates = new Thread(userState);
        threadStates.start();


    }
    public void answer (String IP) throws SocketException {

            newcallSpeaker = new Speaker(new DatagramSocket(PORT));
            threadSpeaker = new Thread(newcallSpeaker);
            threadSpeaker.start();


            newcallmic = new Mic(PORT, IP);
            threadMic = new Thread(newcallmic);
            threadMic.start();

    }
    public void call (String IP) throws SocketException {


            if(!oncall) {
                oncall=connectionSetup(IP);

                if (oncall) {
                    newcallSpeaker = new Speaker(new DatagramSocket(PORT));
                    threadSpeaker = new Thread(newcallSpeaker);
                    threadSpeaker.start();
                    newcallmic = new Mic(PORT, IP);
                    threadMic = new Thread(newcallmic);
                    threadMic.start();
                    States.state = "oncall";
                    States.oncall = true;

                }
            }

    }

    public void end (String IP) throws IOException {
        DatagramSocket sock = null;
            sock = new DatagramSocket();
            byte[] req= "CallEnd".getBytes();
            InetAddress host = InetAddress.getByName(IP) ;
            DatagramPacket packet = new DatagramPacket(req, req.length, host, GUI.CTRLPORT);
            sock.send(packet);

        newcallSpeaker.stopPlay =true;
        newcallmic.stopCapture=true;

        oncall=false;
        States.state="waitforcall";
        newcallmic.setempty();
        newcallSpeaker.setempty();
        System.out.println("cut");
        //
    }


    public void end (){
        System.out.println("cut");
        newcallSpeaker.stopPlay =true;
        newcallmic.stopCapture=true;

        oncall=false;
        States.state="waitforcall";
       // newcallmic.setempty();
       //newcallSpeaker.setempty();
        //
    }

    private boolean connectionSetup(String IP)  {
        DatagramSocket sock = null;
        try {

            sock = new DatagramSocket();
            byte[] req= "Can I call?".getBytes();
            InetAddress host = InetAddress.getByName(IP) ;
            DatagramPacket packet = new DatagramPacket(req, req.length, host, GUI.CTRLPORT);
            sock.send(packet);
            System.out.println("sent the request to call ."+ new String(packet.getData()));


            packet.setData( new byte[500] ) ;
            // Wait for a response from the server

            sock.receive( packet ) ;

            System.out.println("got the response to call ."+ new String(packet.getData()));

            String pack = new String(packet.getData());
            if(pack.contains("ok")) {

                System.out.println(States.state);
                sock.close();
                return true;
            }else if(pack.contains("Busy")){
                GUI.jLabel1.setBackground(new java.awt.Color(0,153,0));
                GUI.jLabel1.setFont(new java.awt.Font("SansSerif", 0, 36)); // NOI18N
                GUI.jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                GUI.jLabel1.setText("Busy");
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

