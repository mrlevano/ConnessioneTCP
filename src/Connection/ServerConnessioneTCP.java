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
public class ServerConnessioneTCP {
    
    /**
     * Porta nella quale il server è in ascolto
     */
    private final int porta;
    
    /**
     * Costruttore della classe ServerConnessioneTCP
     */
    public ServerConnessioneTCP() {
        porta = 2000;
    }
    
    /**
     * Metodo che avvia il server e lo mette in ascolto sulla sua porta
     */
    public void avviaServer() {
        Socket connessione = null;
        
    }
    
    /**
     * Metodo che legge la stringa inviata da un client e ne elabora una risposta
     * @param connessione Oggetto socket che server che contiene gli InputStream e OutputStream
     */
    public void rispondi(Socket connessione) {
        String messaggioOutput, messaggioInput;
        
    }
    
    /**
     * Metodo che chiude la connessione con il server
     * @param connessione Oggetto socket che server contenente il metodo .close()
     */
    public void spegniServer(Socket connessione) {
        
    }
    
}
