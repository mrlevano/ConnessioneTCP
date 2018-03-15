/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
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
     * Oggetto Socket assegnato alla singola conversazione
     */
    private Socket connection;
    
    /**
     * Oggetto ServerSocket
     */
    private ServerSocket sSocket;
    
    /**
     * Oggetto che si occupa della gestione della comunicazione
     */
    private Gestore gestione;
    
    /**
     * Costruttore della classe ServerConnessioneTCP
     */
    public ServerConnessioneTCP() {
        porta = 2000;
        sSocket = null;
        gestione = null;
    }
    
    /**
     * Costruttore della classe ServerConnessioneTCP sovraccaricarto con valori in input
     * @param porta nella quale il server è in ascolto
     */
    public ServerConnessioneTCP(int porta) {
        this.porta = porta;
        sSocket = null;
        gestione = null;
    }
    
    /**
     * Metodo che avvia il server e lo mette in ascolto sulla sua porta
     */
    public void avviaServer() {
        try {
            sSocket = new ServerSocket(porta);
            System.out.println("In attesa di connessioni!");
            
            connection = sSocket.accept();
            
            System.out.println("Connessione stabilita!");
            System.out.println("Socket server: " + connection.getLocalSocketAddress());
            System.out.println("Socket client: " + connection.getRemoteSocketAddress());
            
        } catch (IOException ex) {
            System.err.println("Errore : " + ex.getMessage());
        }
        
    }
    
    /**
     * Metodo che legge la stringa inviata da un client e ne elabora una risposta tramite la classe Gestore
     */
    public void rispondi() {
        try {
            // Creo input e output per streams orientati ai byte
            gestione = new Gestore(connection.getInputStream(), connection.getOutputStream());
            gestione.comunicaS();
        } catch (IOException ex) {
            System.err.println("Errore di scrittura : " + ex.getMessage());
        }
    }
    
    /**
     * Metodo che chiude la connessione con il server
     */
    public void spegniServer() {
        if(connection != null) { // Se l'oggetto connection non è nullo, allora chiudo la connessione
            try {
                connection.close();
            } catch (IOException ex) {
                System.err.println("\nErrore durante la chiusura: " + ex.getMessage());
            }
        }
        System.out.println("\nConnessione chiusa!");
    }
}
