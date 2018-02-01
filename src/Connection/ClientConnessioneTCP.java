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
        Socket connection = null;
        boolean continua = true;
        try {
            connection = new Socket(indirizzoS, porta);
            
            while(continua) { // Conntinuo a ripetere fino a quando .comunicaS() ritorna false
                continua = comunicaS(connection);
            }
            
        } catch(ConnectException e){
            System.err.println("Server non disponibile!\n" + e.getMessage());
        } catch(UnknownHostException e1){
            System.err.println("Errore DNS!\n" + e1.getMessage());
        } catch (IOException e2) {
            System.err.println("Errore : " + e2.getMessage());
        } finally {
            chiudiConnessione(connection);
        }
        
    }
    
    /**
     * Metodo che ottiene la stringa che il server manda come risposta alla richiesta del client
     * @param connection Oggetto socket che server che contiene gli InputStream e OutputStream
     * @return Booleana che indica se si deve continuare a scrivere al server oppure chiudere la connessione
     */
    public boolean comunicaS(Socket connection) {
        String messaggioOutput, messaggioInput;
        boolean continua = true;
        
        try {
            BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader inputClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintStream outputClient = new PrintStream(connection.getOutputStream());
            
            // Input da tastiera per il messaggio da mandare al server
            System.out.println("\nChe voi scrivere al server?");
            messaggioOutput = tastiera.readLine();
            // Invio il messaggio al server
            outputClient.println(messaggioOutput);
            outputClient.flush();
            
            // Ricevo la risposta del server
            messaggioInput = inputClient.readLine();
            System.out.println(messaggioInput);
            
            if(messaggioInput.equals("Ciao ciao!")) { // Se il sever risponde ciao ciao allora si ritorna falso
                continua = false;
            }
            
        } catch (IOException ex) {
            System.err.println("Errore : " + ex.getMessage());
        }
        
        return continua;
    }
    
    /**
     * Metodo che chiude la connessione con il server
     * @param connection Oggetto socket che server contenente il metodo .close()
     */
    public void chiudiConnessione(Socket connection) {
        if(connection != null) { // Se l'oggetto connection non è nullo, allora chiudo la connessione
            try {
                connection.close();
            } catch (IOException ex) {
                System.err.println("\nErrore durante la chiusura: " + ex.getMessage());
            }
        }
    }
}
