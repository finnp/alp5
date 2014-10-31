package alpv_ws1415.ub1.webradio.webradio;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import alpv_ws1415.ub1.webradio.audioplayer.AudioPlayer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main {
	private static final String	USAGE	= String.format("usage: java -jar UB%%X_%%NAMEN [-options] server tcp|udp|mc PORT%n" +
														"         (to start a server)%n" +
														"or:    java -jar UB%%X_%%NAMEN [-options] client tcp|udp|mc SERVERIPADDRESS SERVERPORT USERNAME%n" +
														"         (to start a client)");

	/**
	 * Starts a server/client according to the given arguments, using a GUI or
	 * just the command-line according to the given arguments.
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		try {
			boolean useGUI = false;
			int i = -1;

			// Parse options. Add additional options here if you have to. Do not
			// forget to mention their usage in the help-string!
			while(args[++i].startsWith("-")) {
				if(args[i].equals("-help")) {
					System.out.println(USAGE + String.format("%n%nwhere options include:"));
					System.out.println("  -help      Show this text.");
					System.out.println("  -gui       Show a graphical user interface.");
					System.exit(0);
				}
				else if(args[i].equals("-gui")) {
					useGUI = true;
				}
			}

			if(args[i].equals("server")) {
				if(args[++i].equals("tcp")) {
						int port = Integer.parseInt(args[++i]);
						ServerSocket server = new ServerSocket(port);
						while(true) {
							Socket socket = server.accept();
							
							// FileoutputStream music = new FileoutputStream('./sound.wav');
							
							// AudioPlayer player = new AudioPlayer();
							AudioInputStream audio = AudioSystem.getAudioInputStream(new File("./sound.wav"));
							OutputStream out = socket.getOutputStream();
							byte[] buffer = new byte[1024];
							int c;
							while((c = audio.read(buffer, 0, buffer.length)) != -1) {
								out.write(buffer, 0, c);
							}
							 
							audio.close();
							out.close();
							
							// 
							// PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
							// out.print("Hello, world!");
						}
				} else {
					// TODO
					throw new IllegalArgumentException();
				}

			}
			else if(args[i].equals("client")) {
				if(args[++i].equals("tcp")) {
					String hostname = args[++i];
					Integer port = Integer.parseInt(args[++i]);
					Socket socket = new Socket(hostname, port);
				
					
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String message;
					while((message = in.readLine()) != null) {
						System.out.println(message);
					}
					socket.close();
					
				}
			}
			else
				throw new IllegalArgumentException();
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.err.println(USAGE);
		}
		catch(NumberFormatException e) {
			System.err.println(USAGE);
		}
		catch(IllegalArgumentException e) {
			System.err.println(USAGE);
		}
	}
}
