import java.io.BufferedReader;
/**
 * Classe per l'implementazione di un Thread Client
 * @author Patrissi Mathilde
 */
public class ThreadClient extends Thread{
	BufferedReader inputServer;
	String result;
	/**
	 * Costruttore della classe ThreadClient
	 * 
	 * @param inputServer BufferedReader
	 */
public ThreadClient (BufferedReader inputServer){//viene passato come parametro lo stream per leggere i dati dal server
	this.inputServer=inputServer;
}
/**
 * metodo che viene richiamato per l'esecuzione del thread
 */
public void run() {//metodo che permette la lettura del messaggio dell'altro client
	while(true)
	{
		try {
			result=inputServer.readLine(); //viene letto cio' che invia il server
			if(!result.equalsIgnoreCase("ok"))//se la condizione non e' vera significa che result contiene il messaggio dell'altro client connesso alla chat
			  System.out.println(result);
		}
		catch (Exception e) {
			System.out.println("errore input");
		}	
	}	
}
}
