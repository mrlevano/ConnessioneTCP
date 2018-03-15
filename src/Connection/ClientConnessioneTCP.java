/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

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
     * Oggetto Socket
     */
    private Socket connection;
    
    /**
     * Oggetto che si occupa della gestione della comunicazione
     */
    private Gestore gestione;
    
    /**
     * Costruttore della classe ClientConnessionTCP con valori i default
     */
    public ClientConnessioneTCP() {
        porta = 2000;
        indirizzoS = "localhost";
        connection = null;
        gestione = null;
    }
    
    /**
     * Costruttore della classe ClientConnessionTCP sovraccaricato con valori in input
     * @param indirizzoS indirizzo del server
     * @param porta nella quale il server è in ascolto
     */
    public ClientConnessioneTCP(String indirizzoS, int porta) {
        this.porta = porta;
        this.indirizzoS = indirizzoS;
        connection = null;
        gestione = null;
    }
    
    /**
     * Metodo che avvia una connessione e una comunicazione con il server
     */
    public void avviaConnessione() {
        try {
            this.connection = new Socket(indirizzoS, porta);
        } catch(ConnectException e){
            System.err.println("Server non disponibile!\n" + e.getMessage());
        } catch(UnknownHostException e1){
            System.err.println("Errore DNS!\n" + e1.getMessage());
        } catch (IOException e2) {
            System.err.println("Errore : " + e2.getMessage());
        }
    }
    
    /**
     * Metodo che ottiene la stringa che il server manda come risposta alla richiesta del client tramite il metodo Gestore
     */
    public void comunicaS() {
        try {
            gestione = new Gestore(connection.getInputStream(), connection.getOutputStream());
            gestione.comunicaS();
        } catch (IOException ex) {
            System.err.println("Errore : " + ex.getMessage());
        }
    }
    
    /**
     * Metodo che chiude la connessione con il server
     */
    public void chiudiConnessione() {
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
