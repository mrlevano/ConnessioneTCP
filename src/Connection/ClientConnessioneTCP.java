/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.net.Socket;

/**
 *
 * @author jesus
 */
public class ClientConnessioneTCP {
    
    /**
     * Porta nella quale il server è in ascolto
     */
    private final int porta;
    
    /**
     * Indirizzo del server
     */
    private final String indirizzoS;
    
    /**
     * Costruttore della classe ClientConnessionTCP
     */
    public ClientConnessioneTCP() {
        porta = 2000;
        indirizzoS = "localhost";
    }
    
    /**
     * Metodo che avvia una connessione e una comunicazione con il server
     */
    public void avviaConnessione() {
        Socket connessione = null;
        
    }
    
    /**
     * Metodo che ottiene la stringa che il server manda come risposta alla richiesta del client
     * @param connessione Oggetto socket che server che contiene gli InputStream e OutputStream
     */
    public void comunicaS(Socket connessione) {
        String messaggioOutput, messaggioInput;
        
    }
    
    /**
     * Metodo che chiude la connessione con il server
     * @param connessione Oggetto socket che server contenente il metodo .close()
     */
    public void chiudiConnessione(Socket connessione) {
        
    }
}
