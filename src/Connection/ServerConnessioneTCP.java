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
    
    boolean continua;
    
    String messaggioOutput;
    
    String comando;
    
    String nome;
    
    boolean online;
    
    /**
     * Costruttore della classe ServerConnessioneTCP
     */
    public ServerConnessioneTCP() {
        porta = 2000;
        continua = true;
        sSocket = null;
        messaggioOutput = "";
        comando = "";
        nome = "anon";
        online = true;
    }
    
    /**
     * Costruttore della classe ServerConnessioneTCP sovraccaricarto con valori in input
     * @param porta nella quale il server è in ascolto
     */
    public ServerConnessioneTCP(int porta) {
        this.porta = porta;
        continua = true;
        sSocket = null;
        messaggioOutput = "";
        comando = "";
        nome = "anon";
        online = true;
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
        String messaggioInput;
        try {
            // Creo input e output per streams orientati ai byte
            BufferedReader inputServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintStream outputServer = new PrintStream(connection.getOutputStream());
            BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
            
            do {
                messaggioInput = inputServer.readLine();
                
                // Mostro la stringa che il client ha mandato
                System.out.println("\n\nMessaggio del client : " + messaggioInput);
                messaggioOutput = tastiera.readLine().toLowerCase();
                if(messaggioOutput.contains("_")) {
                    gestisciMessaggio(messaggioInput);
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
    
    public void gestisciMessaggio(String messaggioInput) {
        String[] messaggioDiv = messaggioOutput.split("_",3);
        comando = messaggioDiv[1];
        switch(comando) { // A seconda della stringa mandata dall'utente il server risponde con un'altra
            case "autore" :
                messaggioOutput = messaggioOutput.replaceAll("_autore_", nome);
                break;
            case "online" :
                online = true;
                break;
            case "offline" :
                online = false;
                break;
            case "like" :
                messaggioOutput = messaggioOutput.replaceAll("_like_", "\uD83D\uDE40");
                break;
            case "setname" :
                setNome();
                break;
            case "echo" :
                messaggioOutput = messaggioOutput.replaceAll("_echo_", messaggioInput);
                break;
            case "close" :
                messaggioOutput = "Ciao ciao!";
                continua = false;
                break;
            case "time" : // Uso il GregorianCalendar per mostrare l'ora
                GregorianCalendar calendario = new GregorianCalendar();
                String ora = calendario.get(Calendar.HOUR_OF_DAY) + ":" + calendario.get(Calendar.MINUTE);
                messaggioOutput = messaggioOutput.replaceAll("_time_", ora);
                break;
            default:
                
        }
    }
    
    public void setNome() {
        try {
            System.out.println("Vecchio nome : " + nome + "\nScrivi il tuo nuovo nome: ");
            BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
            nome = tastiera.readLine();
        } catch(IOException ex) {
            System.err.println("Errore durante l'input. " + ex.getMessage());
        }
    }
    
}
