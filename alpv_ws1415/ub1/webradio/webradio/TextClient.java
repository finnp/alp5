package alpv_ws1415.ub1.webradio.webradio;

import java.net.Socket;
import java.io.*;


public class TextClient {
  
  private Socket socket;

  public TextClient(String hostname, int port) throws Exception {
    socket = new Socket(hostname, port);
  }
  
  public void connect() throws Exception {
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    String message;
    while((message = in.readLine()) != null) {
      System.out.println(message);
    }
    socket.close();
  }

}
