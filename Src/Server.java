import java.io.*;
import java.net.*;

/**
 * Classe per l'implementazione di un Server per gestire una Chat La classe
 * utilizza il meccanismo dei Thread per la gestione e l'invio dei messaggi
 * ovvero ogni volta che un client effettua una connessione al server viene
 * creato un thread, che si occuperà della comunicazione con il client in modo
 * da permettere al server di gestire altre richieste di connessioni
 * 
 * @author Patrissi Mathilde
 */
public class Server {
	ServerSocket server;
	java.util.Vector<Thread> ThreadVect;

	/**
	 * Costruttore della classe Server
	 * 
	 * @param port int
	 */
	public Server(int port) {
		try {
			server = new ServerSocket(port);// viene creato un socket data la porta
			ThreadVect = new java.util.Vector(1, 1);
		} catch (IOException e) {
			System.out.println("errore I/O");
		}
	}

	/**
	 * Metodo per la creazione e la gestione dei thread server
	 */
	public void Start() {
		Socket clientSocket;
		System.out.println(".....in attesa di dati.");
		while (true) {
			try {
				clientSocket = server.accept();// il server resta in attesa di richieste di connessione da parte del
												// client
			} catch (Exception e) {
				System.out.println("Errore di connessione");
				break;
			}
			ThreadVect.addElement(new ThreadServer(ThreadVect, clientSocket)); // viene inserito un nuovo ThreadServer
																				// nel vettore che si occupera' della
																				// comunicazione con il client
			ThreadVect.elementAt(ThreadVect.size() - 1).start();// il thread in questione viene fatto partire
			System.out.println("Thread " + ThreadVect.size() + " partito");
			checkThread();// controllo che i thread siano ancora vivi
		}
	}

	/**
	 * Metodo per l'aggiornamento del vettore di thread
	 */
	public void checkThread() {
		for (int i = 0; i < ThreadVect.size(); i++) {
			if (!ThreadVect.elementAt(i).isAlive())
				ThreadVect.removeElementAt(i);
		}
	}
}
