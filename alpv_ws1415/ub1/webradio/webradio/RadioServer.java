package alpv_ws1415.ub1.webradio.webradio;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class RadioServer {
  
  private ServerSocket server;

  public RadioServer(int port) throws Exception{
    server = new ServerSocket(port);
  }
  
  public void run() throws Exception {
    while(true) {
      Socket socket = server.accept();
      
      AudioInputStream audio = AudioSystem.getAudioInputStream(new File("./sound.wav"));
      OutputStream out = socket.getOutputStream();

      AudioFormat format = audio.getFormat();
      System.out.println(format.toString());
      out.write(FormatHelper.serialize(format));

      byte[] buffer = new byte[1024];
      int c;
      while((c = audio.read(buffer, 0, buffer.length)) != -1) {
        out.write(buffer, 0, c);
      }
       
      audio.close();
      out.close();
    }  
  }
}
