import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Rama on 2/27/2016.
 */
public class Speaker implements Runnable {

    DatagramSocket socket;

    public volatile boolean stopPlay = false;
    ByteArrayOutputStream byteArrayOutputStream;
    AudioFormat audioFormat;
    //TargetDataLine targetDataLine;
    //AudioInputStream audioInputStream;
    SourceDataLine sourceDataLine;
    byte tempBuffer[] = new byte[500];
    private int packetsize = 500;

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
        DatagramPacket packet = new DatagramPacket( new byte[packetsize], packetsize ) ;
        try {

            while (!stopPlay) {
                socket.receive( packet ) ;
                tempBuffer = packet.getData();
                byteArrayOutputStream.write(tempBuffer, 0, packetsize);
                sourceDataLine.write(tempBuffer, 0, 500);//playing audio available in tempBuffer
                //System.out.println(tempBuffer.toString());
            }
            byteArrayOutputStream.close();
            socket.close();
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





}
