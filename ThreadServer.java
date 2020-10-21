import java.io.*;
import java.net.Socket;
/**
 * Classe per l'implementazione di un Thread Server che si occuperà della comunizione con un client
 *  TODO: in questa versione, che prevede da specifica solo due client, durante l'invio di
 *    un messaggio viene scelto il primo threadServer diverso da quello utilizzato nel momento in questione.
 *    Infatti, essendoci due soli ThreadServer, si tratterà sicuramente dell'altro Thread.
 *    Entrambi vegono pero' inseriti in un vettore poiche', implementando un comando per identificare 
 *    il destinatario, sarà possibile gestire lo scambio di messaggi con un numero superiore di client.
 * 
 * 
 * @author Patrissi Mathilde
 */
public class ThreadServer extends Thread{
	BufferedReader inputClient;
	PrintStream outClient=null;	
	String clientData;
	String clientName;
	String HELLO_CMD="ciao sono"; //comando di identificazione client
	java.util.Vector <Thread> ThreadVect;
	/**
	 * Costruttore della classe ThreadServer
	 * 
	 * @param ThreadVect java.util.Vector
	 * @param clientSocket Socket
	 */
	public ThreadServer (java.util.Vector <Thread> ThreadVect,Socket clientSocket){
		this.ThreadVect=ThreadVect; //il vettore contiene tutti i serverThread che gestiscono la communicazione con un client
		try { //vengono creati gli stream per leggere/scrivere dal/al client
		inputClient= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outClient = new PrintStream(clientSocket.getOutputStream(), true);
	} 
	catch (IOException e) {
		System.out.println("errore I/O");
	}
	}
	/**
	 * metodo che viene richiamato per l'esecuzione del thread
	 */
	public void run() {
		try {
			clientData= inputClient.readLine(); //lettura del messaggio del client
		}
		catch(Exception e) {
			SendMSG("Il server risponde: errore lettura da client");
			System.out.println("errore lettura da client");
		}
		if(clientData.substring(0, 9).equalsIgnoreCase(HELLO_CMD)) { //viene controllato che il client si sia identificato, mediante il metodo hello(),prima di iniziare la chat
			clientName=clientData.substring(9, clientData.length());//viene salvato in una variabile il nome del client
			outClient.println("Ciao "+clientName+" ti diamo il benvenuto nella chat");
			while(true)
			{
				try {
					clientData= inputClient.readLine();//viene letto il messaggio dal client
				}
				catch(Exception e) {
					SendMSG("Il server risponde: errore lettura da client");
					System.out.println("errore lettura da client");
					break;
				}
		         //nella successiva istruzione viene inviato il messaggio del client gestito da questo ThreadServer, all'altro ThreadServer presente nell'apposito vettore. 
				((ThreadServer)ThreadVect.elementAt(findDest())).SendMSG("--> "+clientData);
				System.out.println(clientData);
			}		
		}
		else {
			SendMSG("Impossibile iniziare la chat");
		}
	}
	/**
	 * metodo per la restituzione del nome del client 
	 * che comunica con il server in questione
	 * @return il nome del Client
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * metodo per l'identificazione del ThreadServer che si occupa dell'altro client
	 * TODO: in questa versione che prevede da specifica solo due client viene preso
	 * il primo thread che non sia il this. Dato che si utlizza un vettore di thread  
	 * implementando un comando per identificare il destinatario sarà possibile cercare il
	 * thread opportuno tramite il nome del Client 
	 * @return l'indice che identifica il server nel vettore
	 */
	public int findDest() {
		int pos=-1;
		for(int i=0; i<ThreadVect.size(); i++) {
			if(!((ThreadServer)ThreadVect.elementAt(i)).getClientName().equals(clientName))//se la condizione non e' verificata significa che nella posizione i del vettore e' presente l'altro ThreadSever
				pos=i;
		}
			return pos;
	}
	/**
	 * metodo per l'invio dei messaggi al client
	 * @param clientMSG String
	 */
	public void SendMSG(String clientMSG) {
		outClient.println(clientMSG);	
		
	}
}
