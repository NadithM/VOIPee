import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

/**
 * Created by Rama on 2/27/2016.
 */
public class Speaker implements Runnable {

    private DatagramSocket socket;

    public volatile boolean stopPlay = false;
    private  static  boolean stopwhile=false;
    private ByteArrayOutputStream byteArrayOutputStream;
    private static ByteArrayInputStream byteArrayInputStream;
    private static boolean playfinal=false;
    private AudioFormat audioFormat;
    private static int playstart=0;
    private static int playstop=2000;
    private static boolean[] window=new boolean[4];
    private static byte [] got={0,1,2,3};// first index  does not read some how,So I put first index as -1 to pass that, then
    // it will read other 4 numbers and give the which packets server wating for
    private static long timer;

    //TargetDataLine targetDataLine;
    //AudioInputStream audioInputStream;
    private SourceDataLine sourceDataLine;
    byte tempBuffer1[] = new byte[501];
    private static byte  tempBuffer[][] = new byte[4][500];

    private static byte tempBufferfinal[] = new byte[2000];

    public Speaker(DatagramSocket sock){
        this.socket = sock;
        this.stopPlay=false;
    }
    public void setempty(){
        got[0]=0;
        got[1]=1;
        got[2]=2;
        got[3]=3;

    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 16000.0F;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    private void speakerStart() {

        try {
            Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();    //get available mixers
            System.out.println("Available mixers:");
            Mixer mixer = null;
            for (int cnt = 0; cnt < mixerInfo.length; cnt++) {
                System.out.println(cnt + " " + mixerInfo[cnt].getName());
                mixer = AudioSystem.getMixer(mixerInfo[cnt]);

                Line.Info[] lineInfos = mixer.getTargetLineInfo();
                if (lineInfos.length >= 1 && lineInfos[0].getLineClass().equals(TargetDataLine.class)) {
                    System.out.println(cnt + " Mic is supported!");
                    break;
                }
            }

            audioFormat = getAudioFormat();     //get the audio format


            DataLine.Info dataLineInfo1 = new DataLine.Info(SourceDataLine.class, audioFormat);
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo1);
            sourceDataLine.open(audioFormat);
            sourceDataLine.start();

            //Setting the maximum volume
            FloatControl control = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(control.getMaximum());


        } catch (LineUnavailableException e) {
            System.out.println(e);
            System.exit(0);
        }


    }

    private void captureAndPlay() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        stopPlay = false;
        int packetsize = 501;
        DatagramPacket packet = new DatagramPacket( new byte[packetsize], packetsize) ;
        try {

            while (!stopPlay) {
                socket.receive( packet ) ;
                tempBuffer1= packet.getData();// get the paket to byte buffer
                byteArrayOutputStream.write(tempBuffer1, 0, packetsize);

                byte b = tempBuffer1[500];


               System.out.println( b + " packet received :  ");

                boolean play = useArraysBinarySearch(b ,tempBuffer1);//to check the packet squence
              //  System.out.println(play);
            //playstart ,and aplaystop for play the required part of the buffer.tempbufferfinal is final buffer to play
           if(playfinal) sourceDataLine.write(tempBufferfinal, playstart, tempBufferfinal.length);//playing audio available in tempBuffer

                playfinal=false;
            }
            byteArrayOutputStream.close();
            socket.close();
            sourceDataLine.close();
        } catch (IOException e) {
            System.out.println(e);
            socket.close();
            System.exit(0);
        }
    }


    public void run(){

            speakerStart();
            timer = System.currentTimeMillis();
            captureAndPlay();


    }

    public void kill() {

        this.stopPlay = true;
    }

    public int bytesToInt(byte[] int_bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(int_bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        int my_int = ois.readInt();
        ois.close();
        return my_int;
    }

    public static boolean useArraysBinarySearch(byte targetValue,byte [] tempbuff) {
        int a =  Arrays.binarySearch(got, targetValue);
        System.out.println(  " packet accepted or not :  "+ got[0] +got[1] +got[2] +got[3] +"  " + targetValue +" place "+ a);//using a byte

        //a get the value of which index of the window came throgh the packet
        window[a]=true;
        byteArrayInputStream= new ByteArrayInputStream(tempbuff) ;
        int temp=byteArrayInputStream.read(tempBuffer[a],0,500);
        //System.out.println("How many byte copyied to tempbuffer[a] :"+temp);//bytesToInt(b) converts extracted number into int

        System.out.println(tempBuffer[a].toString());
        //sourceDataLine.write(tempBufferfinal, playstart, playstop);//playing audio available in tempBuffer
        int trues =0;
        for(int t=0; t<window.length; t++){
            if(window[t]==true) trues++;
        }
        long curTime = System.currentTimeMillis();
        if(trues==4 || (curTime-timer)>200) {//waing for all 4 packets .if not after 200ms playing.

           for(int f=0; f<got.length; f++) got[f]+=4;

            copySmallArraysToBigArray();
            System.out.println("final buffer to play "+tempBufferfinal.toString());
            playfinal=true;
            for(int t=0; t<window.length; t++){
                window[t]=false;
            }
            timer = System.currentTimeMillis();// resetting the timer after plays.


            return true;
        }
        else
            return false;
    }

    public static void copySmallArraysToBigArray(){
        int currentOffset = 0;

        for(final byte[] currentArray : tempBuffer){


                System.arraycopy(
                        currentArray, 0,
                        tempBufferfinal, currentOffset,
                        currentArray.length
                );
                currentOffset += currentArray.length;

        }
    }

}
