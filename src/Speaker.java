import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Rama on 2/27/2016.
 */
public class Speaker implements Runnable {

    private DatagramSocket socket;

    public volatile boolean stopPlay = false;
    private ByteArrayOutputStream byteArrayOutputStream;
    private AudioFormat audioFormat;
    //TargetDataLine targetDataLine;
    //AudioInputStream audioInputStream;
    private SourceDataLine sourceDataLine;
    byte tempBuffer[] = new byte[510];

    public Speaker(DatagramSocket sock){
        this.socket = sock;
        this.stopPlay=false;
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
        int packetsize = 510;
        DatagramPacket packet = new DatagramPacket( new byte[packetsize], packetsize) ;
        try {

            while (!stopPlay) {
                socket.receive( packet ) ;
                tempBuffer = packet.getData();
                byteArrayOutputStream.write(tempBuffer, 0, packetsize);

                byte b []=new byte[10];
                for(int x= 0 ; x < b.length; x++) {
                    //printing the characters
                    b[x]=tempBuffer[500+x];
                    //System.out.println(" "+(int)b[x]);
                }

               System.out.println( " packet :  "+ bytesToInt(b));

                sourceDataLine.write(tempBuffer, 0, 500);//playing audio available in tempBuffer
                //System.out.println(tempBuffer.toString());
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




}
