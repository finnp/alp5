package alpv_ws1415.ub1.webradio.webradio;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class TextServer {
  
  private ServerSocket server;

  public TextServer(int port) throws Exception{
    server = new ServerSocket(port);
  }
  
  public void run() throws Exception {
    while(true) {
      Socket socket = server.accept();
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      out.print("Hello, world!");
      out.close();
    }
  }
}
