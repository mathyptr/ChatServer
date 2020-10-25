import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Classe per l'implementazione di una chat fra due client
 * 
 * @author Patrissi Mathilde
 */
class Chat {
	static String daemon = "", serverName = "", clientName = "";
	static String scelta = "s";
	static int port = 0, index = 1;

	/**
	 * Metodo di presentazione del programma e delle sue funzionalità:
	 */
	public static void SplashScreen() {
		String progname = "ChatServer";
		System.out.println("********************************************************************************");
		System.out.println("*                           Chat Server                                        *");
		System.out.println("* Utilizzo da terminale:                                                       *");
		System.out.println("* " + progname + " s numport (per eseguire il server)                              *");
		System.out.println("* " + progname + " c serverName numPort clientName (per eseguire il client)        *");
		System.out.println("* Non fornire alcun parametro per immettere i parametri da tastiera            *");
		System.out.println("* Digitare q! per uscire dalla chat                                            *");
		System.out.println("********************************************************************************");
	}

	/**
	 * Metodo per l'avvio del programma da riga di comando
	 * 
	 * @param args String[]
	 */
	public static void cmdLine(String[] args) {
		if (args[0].length() > 1) {
			SplashScreen();
			System.exit(1);
		}
		daemon = args[0];
		if (!daemon.toLowerCase().equals("s") && !daemon.toLowerCase().equals("c")) {
			System.out.println("Errore parametri!!!");
			SplashScreen();
			System.exit(1);
		}
		if (daemon.equals("c")) {
			serverName = args[1];
			index = 2;
			scelta = "c";
		}
		try {
			port = (Integer.valueOf(args[index]).intValue());
		} catch (NumberFormatException e) {
			System.err.println("L'argomento" + args[index] + " deve essere un intero (numero Porta)!");
			System.exit(1);
		}
		if (args.length == 4 && args[3].length() > 0)
			clientName = args[3].toLowerCase();
	}

	/**
	 * Metodo per l'avvio del programma in modo interattivo, mediante richieste
	 * dirette all'utente
	 */
	public static void interactive() {
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader tastiera = new BufferedReader(input);
		System.out.println("s per server o altri caratteri per client");
		try {
			scelta = tastiera.readLine();
		} catch (Exception e) {
			System.out.println("errore input");
		}

		if (!scelta.toLowerCase().equals("s")) {
			System.out.println("Inserisci il tuo nome: ");
			try {
				clientName = tastiera.readLine().toLowerCase();
			} catch (Exception e) {
				System.out.println("errore input");
			}
			System.out.println("Inserisci l'indirizzo del server");
			try {
				serverName = tastiera.readLine();
			} catch (Exception e) {
				System.out.println("errore input");
			}
		}
		System.out.println("Inserisci la porta del server");
		try {
			port = (Integer.valueOf(tastiera.readLine()).intValue());
		} catch (Exception e) {
			System.out.println("errore input");
		}
	}

	/**
	 * Metodo per la creazione dell'istanza Server
	 * 
	 * @param port int
	 */
	public static void doServer(int port) {
		Server myServer = new Server(port);
		myServer.Start();
	}

	/**
	 * Metodo per la creazione dell'istanza Client
	 * 
	 * @param serverName String
	 * @param port       int
	 * @param clientName String
	 */
	public static void doClient(String serverName, int port, String clientName) {
		Client myClient = new Client(serverName, port, clientName);
		try {
			myClient.Start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metodo Main
	 * 
	 * @param args String[]
	 */
	public static void main(String[] args) {// e' possibile passare i parametri al main
		SplashScreen();

		if (args.length > 1) // se vengono passati dei parametri dal terminale viene richiamato il metodo che
								// si occupa di leggerli
			cmdLine(args);
		else if (args.length <= 1) // se non vengono passati i parametri viene richiamato il metodo per la loro
									// richiesta interattiva
			interactive();

		if (scelta.equals("s")) // se viene richiesto un server
			doServer(port);
		else // altrimenti viene creato un cliente
			doClient(serverName, port, clientName);

	}

}
