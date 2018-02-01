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
     * Costruttore della classe ServerConnessioneTCP
     */
    public ServerConnessioneTCP() {
        porta = 2000;
    }
    
    /**
     * Metodo che avvia il server e lo mette in ascolto sulla sua porta
     */
    public void avviaServer() {
        boolean continua = true;
        Socket connection;
        ServerSocket sSocket = null;
        try {
            sSocket = new ServerSocket(porta);
            System.out.println("In attesa di connessioni!");
            
            connection = sSocket.accept();
            
            System.out.println("Connessione stabilita!");
            System.out.println("Socket server: " + connection.getLocalSocketAddress());
            System.out.println("Socket client: " + connection.getRemoteSocketAddress());
            
            while(continua) {  // Conntinuo a ripetere fino a quando .ripondi() ritorna false
                continua = rispondi(connection);
            }
            
        } catch (IOException ex) {
            System.err.println("Errore : " + ex.getMessage());
        } finally {
            spegniServer(sSocket);
        }
        
    }
    
    /**
     * Metodo che legge la stringa inviata da un client e ne elabora una risposta
     * @param connection Oggetto socket che server che contiene gli InputStream e OutputStream
     * @return Booleana che indica se si deve continuare a scrivere al server oppure chiudere la connessione
     */
    public boolean rispondi(Socket connection) {
        String messaggioOutput, messaggioInput;
        boolean continua = true;
        
        try {
            // Creo input e output per streams orientati ai byte
            BufferedReader inputServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintStream outputServer = new PrintStream(connection.getOutputStream());
            messaggioInput = inputServer.readLine();
            
            // Mostro la stringa che il client ha mandato
            System.out.println("\n\nMessaggio del client : " + messaggioInput);
            
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
                default:
                    messaggioOutput = "Scusa, non so come risponderti.";
            }
            
            // Inoltro la risposta al client
            outputServer.println(messaggioOutput);
            outputServer.flush();
            System.out.println("Risposta inoltrata!");
        } catch (IOException ex) {
            System.err.println("Errore : " + ex.getMessage());
        }
        
        return continua;
    }
    
    /**
     * Metodo che chiude la connessione con il server
     * @param connection Oggetto socket che server contenente il metodo .close()
     */
    public void spegniServer(ServerSocket connection) {
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
