import java.io.IOException;
import java.net.*;

/**
 * Created by Nadith on 2/24/2016.
 */
public class VOIPee{

    public static final int PORT = 20000;
    public boolean oncall = false;
    public Thread threadSpeaker;
    public Thread threadMic;
    public static Speaker newcallSpeaker;
    public static Mic newcallmic;
    public States userState;

    public Thread threadStates;
    public static DatagramSocket socket=null;

    public static void main(String[] args)  {



    }


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


           if (!oncall) {
               oncall = connectionSetup(IP);
               if (!oncall) {//mekath not krama on wenawa
                   newcallSpeaker = new Speaker(new DatagramSocket(PORT));
                   threadSpeaker = new Thread(newcallSpeaker);
                   threadSpeaker.start();
                   newcallmic = new Mic(PORT, IP);
                   threadMic = new Thread(newcallmic);
                   threadMic.start();

               }
           }




    }


    public void end (String IP) throws IOException {
        DatagramSocket sock = null;
            sock = new DatagramSocket();
            byte[] req= "CallEnd".getBytes();
            InetAddress host = InetAddress.getByName(IP) ;
            DatagramPacket packet = new DatagramPacket(req, req.length, host, 30000);
            sock.send(packet);

        newcallmic.stopCapture=true;
        newcallSpeaker.stopPlay =true;

        //
    }

    public void end (){

        newcallmic.stopCapture=true;
        newcallSpeaker.stopPlay =true;

        //
    }

    private boolean connectionSetup(String IP)  {
        DatagramSocket sock = null;
        try {
            sock = new DatagramSocket();
            byte[] req= "Can I call?".getBytes();
            InetAddress host = InetAddress.getByName(IP) ;
            DatagramPacket packet = new DatagramPacket(req, req.length, host, 30000);
            sock.send(packet);

            packet.setData( new byte[500] ) ;
            // Wait for a response from the server
            sock.receive( packet ) ;
            System.out.println(packet.getData());
            if("ok".equals(new String(packet.getData()))) {
                States.state="oncall";
                System.out.println(States.state);
                GUI.jLabel1.setBackground(new java.awt.Color(0,153,0));
                GUI.jLabel1.setFont(new java.awt.Font("SansSerif", 0, 36)); // NOI18N
                GUI.jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                GUI.jLabel1.setText("On call");
                sock.close();
                return true;
            }else if("Busy".equals(new String(packet.getData()))){
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

