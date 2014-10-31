package alpv_ws1415.ub1.webradio.webradio;

import java.net.Socket;
import java.io.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import alpv_ws1415.ub1.webradio.audioplayer.AudioPlayer;

public class RadioClient {
  
  private Socket socket;

  public RadioClient(String hostname, int port) throws Exception {
    socket = new Socket(hostname, port);
  }
  
  public void connect() throws Exception {
    InputStream input = socket.getInputStream();
    
    BufferedInputStream bis = new BufferedInputStream(input);
    
    byte[] formatBytes = new byte[FormatHelper.byteSize];
    
    bis.read(formatBytes, 0, FormatHelper.byteSize);
    AudioFormat format = FormatHelper.parse(formatBytes);
    System.out.println(format.toString());
    AudioPlayer player = new AudioPlayer(format);
    
    player.start();
    
    byte buffer[] = new byte[1024];
    int len;
    while((len = bis.read(buffer, 0, 1024)) != -1) {
      player.writeBytes(buffer, len);
    }
    
    System.out.println("song ended");

    bis.close();
    socket.close();
  }

}
