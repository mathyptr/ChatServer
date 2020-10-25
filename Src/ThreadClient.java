import java.io.BufferedReader;
/**
 * Classe per l'implementazione di un Thread Client
 * @author Patrissi Mathilde
 */
public class ThreadClient extends Thread{
	BufferedReader inputServer;
	String result,clientName;
	boolean endChat=false;
	/**
	 * Costruttore della classe ThreadClient
	 * 
	 * @param inputServer BufferedReader
	 * @param clientName String
	 */
public ThreadClient (BufferedReader inputServer, String clientName){//viene passato come parametro lo stream per leggere i dati dal server
	this.inputServer=inputServer;
	this.clientName=clientName;
}

	/**
	 * metodo che viene richiamato per l'esecuzione del thread
	 */
	public void run() {// metodo che permette la lettura del messaggio dell'altro client
		while (!endChat) {
			try {
				result = inputServer.readLine(); // viene letto cio' che invia il server
				if (!result.equalsIgnoreCase("ok"))// se la condizione non e' vera significa che result contiene il
													// messaggio dell'altro client connesso alla chat
					System.out.println(result);
				if (result.equalsIgnoreCase("Arrivederci " + clientName))
					endChat = true; //viene settata la variabile a true poiche' l'utente ha digitato il comando di uscita
			} catch (Exception e) {
				System.out.println("errore input");
				endChat = true; //in caso di errore con il server viene settata la variabile a true per uscire dal ciclo
			}
		}
	}

}

