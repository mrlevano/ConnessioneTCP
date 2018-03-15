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
import java.util.Calendar;
import java.util.GregorianCalendar;

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
    
    private boolean continua;
    
    private String messaggioOutput;
    
    private String messaggioInput;
    
    private String comando;
    
    private String nome;
    
    private boolean online;
    
    /**
     * Costruttore della classe ClientConnessionTCP con valori i default
     */
    public ClientConnessioneTCP() {
        porta = 2000;
        indirizzoS = "localhost";
        connection = null;
        continua = true;
        messaggioOutput = "";
        messaggioInput = "";
        comando = "";
        nome = "anon";
        online = true;
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
        continua = true;
        messaggioOutput = "";
        comando = "";
        nome = "anon";
        online = true;
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
     * Metodo che ottiene la stringa che il server manda come risposta alla richiesta del client
     */
    public void comunicaS() {
        try {
            //BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader inputClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            //ThreadStreamInput in = new ThreadStreamInput(inputClient, this);
            //in.start();
            PrintStream outputClient = new PrintStream(connection.getOutputStream());
            Gestore gestione = new Gestore(inputClient, outputClient);
            gestione.comunicaS();
            /*
            System.out.println("\nPuoi iniziare a chattare!");
            do {
                // Input da tastiera per il messaggio da mandare al server
                messaggioOutput = tastiera.readLine().toLowerCase();
                if(messaggioOutput.contains("_")) {
                    gestisciMessaggio();
                }
                
                // Invio il messaggio al server
                outputClient.println(messaggioOutput);
                outputClient.flush();
            } while(continua);  // Conntinuo a ripetere fino a quando .comunicaS() ritorna false
            */
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
    }
    
    public void gestisciMessaggio() {
        String[] messaggioDiv = messaggioOutput.split("_",3);
        comando = messaggioDiv[1];
        switch(comando) { // A seconda della stringa mandata dall'utente il server risponde con un'altra
            case "man" :
                System.out.println("\nUsa i comandi messi a disposizione inserendoli tra due underscore '_'\nLista comandi:\n  -man: Mostra il manuale\n  -autore: Sostituisci il comando 'autore' con il tuo nome\n  -online: imposta il tuo stato come online\n  -offline: imposta il tuo stato come 'offline'\n  -like: sostituisci il comando 'like' con l'emoticon \uD83D\uDE40 \n  -setname: modifica il tuo nome attuale\n  -echo: sostituisci il comando 'echo' con l'ultimo messaggio ricevuto\n  -time: sostituisci il comando 'time' con l'ora corrente\n  -close: chiudi la connessione");
                break;
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
                System.out.println("Comando non riconosciuto. Controllare la lista di comandi disponibili scivendo _man_");
                messaggioOutput = messaggioOutput.replaceAll("_" + comando + "_", "");
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
    
    public synchronized void setContinua(boolean b) {
        continua = b;
    }
    
    public synchronized void setMsgInput(String in) {
        messaggioInput = in;
    }
    
    public synchronized boolean isOnline() {
        return online;
    }
    
    public synchronized boolean getContinua() {
        return continua;
    }
}
