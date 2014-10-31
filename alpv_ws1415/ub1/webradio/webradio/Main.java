package alpv_ws1415.ub1.webradio.webradio;

import alpv_ws1415.ub1.webradio.*;


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
						RadioServer server = new RadioServer(port);
						server.run();
				} else {
					// TODO
					throw new IllegalArgumentException();
				}

			}
			else if(args[i].equals("client")) {
				if(args[++i].equals("tcp")) {
					String hostname = args[++i];
					Integer port = Integer.parseInt(args[++i]);

					RadioClient client = new RadioClient(hostname, port);
					client.connect();
				}
			}
			else
				throw new IllegalArgumentException();
		}
		catch(IndexOutOfBoundsException e) {
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
