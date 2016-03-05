

import javax.sound.sampled.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by Rama on 2/25/2016.
 */
public class Mic implements Runnable {

    private int PORT;
    private DatagramSocket socket;
    private String HOST;
    public volatile boolean stopCapture = false;
    private static byte packet=0;
    ByteArrayOutputStream byteArrayOutputStream;
    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;
    private AudioInputStream audioInputStream;
    private SourceDataLine sourceDataLine;
    byte [] tempBuffer = new byte[501];

    public Mic(int port, String IP) throws SocketException {
        this.PORT = port;
        this.socket = new DatagramSocket();
        this.HOST = IP;
        this.stopCapture=false;
    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 16000.0F;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }



    private void captureAudio() {

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
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);

            if (mixer == null) throw new AssertionError();
            targetDataLine = (TargetDataLine) mixer.getLine(dataLineInfo);
            targetDataLine.open(audioFormat);
            targetDataLine.start();





            captureAndSend(); //sending the audio

            targetDataLine.close();

        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    private void captureAndSend() {

        stopCapture = false;

        int readCount;
        while (!stopCapture) {
            try {
                byte b = packet;
                packet++;
                tempBuffer[500] = b;

                readCount = targetDataLine.read(tempBuffer, 0, tempBuffer.length-1);  //capture sound into tempBuffer

                if (readCount > 0) {
                    send();
                }
            }
            catch( Exception e ) {
                System.out.println(e) ;
                e.printStackTrace();
            }
        }
        socket.close();

    }

    private void send() {
        try {
            InetAddress host = InetAddress.getByName(HOST);
            DatagramPacket packet = new DatagramPacket(tempBuffer, tempBuffer.length, host, PORT);
            socket.send(packet);
            System.out.println( "sent packet: " + packet);
        }
        catch( Exception e ) {
                System.out.println(e) ;
            e.printStackTrace();
            }


            // Send the packet

    }

    public void run(){
        captureAudio();

    }

    public void kill() {

        this.stopCapture = true;
    }

    public void setempty(){
        packet=0;

    }
    /*
    public byte[] intToBytes(int my_int) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeInt(my_int);
        out.close();
        byte[] int_bytes = bos.toByteArray();
        bos.close();
        return int_bytes;
    }
*/

}
