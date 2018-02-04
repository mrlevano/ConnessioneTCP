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
import java.util.Calendar;
import java.util.GregorianCalendar;

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
    Socket connection;
    
    /**
     * Oggetto ServerSocket
     */
    ServerSocket sSocket;
    
    /**
     * Costruttore della classe ServerConnessioneTCP
     */
    public ServerConnessioneTCP() {
        porta = 2000;
        sSocket = null;
    }
    
    /**
     * Costruttore della classe ServerConnessioneTCP sovraccaricarto con valori in input
     * @param porta nella quale il server è in ascolto
     */
    public ServerConnessioneTCP(int porta) {
        this.porta = porta;
        sSocket = null;
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
     * Metodo che legge la stringa inviata da un client e ne elabora una risposta
     */
    public void rispondi() {
        String messaggioOutput, messaggioInput;
        boolean continua = true;
        
        try {
            // Creo input e output per streams orientati ai byte
            BufferedReader inputServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintStream outputServer = new PrintStream(connection.getOutputStream());
            
            do {
                messaggioInput = inputServer.readLine();
                
                // Mostro la stringa che il client ha mandato
                System.out.println("\n\nMessaggio del client : " + messaggioInput);
                messaggioInput = messaggioInput.toLowerCase();
                switch(messaggioInput) { // A seconda della stringa mandata dall'utente il server risponde con un'altra
                    case "ciao" :
                        messaggioOutput = "Ciao anon!";
                        break;
                    case "forza inter" :
                        messaggioOutput = "Guarda il cielo. Di che colore è?";
                        break;
                    case "forza napoli" :
                        messaggioOutput = "Si certo!";
                        break;
                    case "close" :
                        messaggioOutput = "Ciao ciao!";
                        continua = false;
                        break;
                    case "che ore sono" : // Uso il GregorianCalendar per mostrare l'ora
                        GregorianCalendar calendario = new GregorianCalendar();
                        messaggioOutput = "Sono le " + calendario.get(Calendar.HOUR_OF_DAY) + ":" + calendario.get(Calendar.MINUTE) + " del " + calendario.get(Calendar.DATE) + "/" + (calendario.get(Calendar.MONTH)+1) + "/" + calendario.get(Calendar.YEAR);
                        break;
                    default:
                        messaggioOutput = "Scusa, non so come risponderti.";
                }

                // Inoltro la risposta al client
                outputServer.println(messaggioOutput);
                outputServer.flush();
                System.out.println("Risposta inoltrata!");
            } while(continua);
            
        } catch (IOException ex) {
            System.err.println("Errore : " + ex.getMessage());
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
