import java.io.IOException;
import java.net.*;


public class VOIPee{

    public static final int PORT = 20000;
    public static boolean  oncall = false;
    public Thread threadSpeaker;
    public Thread threadMic;
    public static Speaker newcallSpeaker;
    public static MultiSpeak newcallMSpeaker;

    public static Mic newcallmic;
    public static Mic   newcallMmic;

    public States userState;

    public Thread threadStates;
    public static DatagramSocket socket=null;


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

    public void answergroup () throws SocketException {

        try {
            newcallMSpeaker = new MultiSpeak(new MulticastSocket(20000));
        } catch (IOException e) {
            e.printStackTrace();
        }
        threadSpeaker = new Thread(newcallMSpeaker);
        threadSpeaker.start();

        newcallMmic = new Mic(PORT, States.grpID);
        threadMic = new Thread(newcallMmic);
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

    public void groupcall (String IP) throws SocketException {


        if(!oncall) {
            oncall=groupconnectSetup(IP);

            if (oncall) {
                try {
                    newcallMSpeaker = new MultiSpeak(new MulticastSocket(PORT));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                threadSpeaker= new Thread(newcallMSpeaker);
                threadSpeaker.start();

                newcallMmic = new Mic(PORT, States.grpID);
                System.out.println("GrpID "+States.grpID);
                threadMic = new Thread(newcallMmic);
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

        newcallSpeaker.kill();
        newcallmic.kill();

        oncall=false;
        States.state="waitforcall";
        newcallmic.setempty();
        newcallSpeaker.setempty();
        System.out.println("cut");
        //
    }


    public void end (){
        System.out.println("cut");
        newcallSpeaker.kill();
        newcallmic.kill();

        oncall=false;
        newcallmic.setempty();
       newcallSpeaker.setempty();
        //
    }

    public void groupend (){
        System.out.println("cut");
        newcallMSpeaker.kill();
        newcallMmic.kill();

        oncall=false;
        newcallMmic.setempty();
        newcallMSpeaker.setempty();
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
            //System.out.println("sent the request to call ."+ new String(packet.getData()));


            packet.setData( new byte[500] ) ;
            // Wait for a response from the server

            sock.receive( packet ) ;

           // System.out.println("got the response to call ."+ new String(packet.getData()));

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

    private boolean groupconnectSetup(String IPs)  {
        DatagramSocket sock = null;
        try {
            String [] IP = IPs.split(",");

            States.groupSize=IP.length;
            sock = new DatagramSocket();
            byte[] req= ("let's group call?:" + IPs + ":"+States.grpID).getBytes();
            for(int i = 0; i<IP.length; i++) {
                InetAddress host = InetAddress.getByName(IP[i]);
                DatagramPacket packet = new DatagramPacket(req, req.length, host, GUI.CTRLPORT);
                sock.send(packet);
               // System.out.println("sent the request to call ." + new String(packet.getData()));
            }
                return true;


        } catch (Exception e) {
            sock.close();
            e.printStackTrace();
        }
        return false;
    }


}

