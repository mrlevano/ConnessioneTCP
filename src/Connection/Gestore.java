/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Classe che consente la gestione dello stream di input e di output del socket
 * @author jesus
 */
public class Gestore {
    
    /**
     * Stream di input del socket
     */
    private final BufferedReader in;
    
    /**
     * Stream di output del socket
     */
    private final PrintStream out;
    
    /**
     * Booleana che indica se si deve continuare la chat oppure chiudere la connessione
     */
    private boolean continua;
    
    /**
     * Stringa che contiene il messaggio di output
     */
    private String messaggioOutput;
    
    /**
     * Stringa che contiene il messaggio di input
     */
    private String messaggioInput;
    
    /**
     * Stringa che contiene il comando inserito
     */
    private String comando;
    
    /**
     * Stringa che contiene il nome dell'utente
     */
    private String nome;
    
    /**
     * Booleana che indica se l'utente è online oppure no
     */
    private boolean online;
    
    /**
     * Costruttore della classe Gestore
     * @param in
     * @param out 
     */
    public Gestore(InputStream in, OutputStream out) {
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = new PrintStream(out);
        continua = true;
        messaggioOutput = "";
        messaggioInput = "";
        comando = "";
        nome = "anon";
        online = true;
    }
    
    /**
     * Metodo che si occupa di gestire la scrittura ad un utente di messaggi presi in input da tastiera e istanzia il thread che si occupa di scoltare
     */
    public void comunicaS() {
        try {
            BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
            ThreadInputGestore inThread = new ThreadInputGestore(in, this);
            inThread.start();
            System.out.println("\nPuoi iniziare a chattare!");
            do {
                // Input da tastiera per il messaggio da mandare al server
                messaggioOutput = tastiera.readLine().toLowerCase();
                if(messaggioOutput.contains("_")) {
                    gestisciMessaggio();
                }
                if(messaggioInput.equals("L'utente ha chiuso la connessione.")) {
                    System.out.println("Il messaggio non sarà inviato.");
                    messaggioOutput = "Conferma chiusura.";
                }
                // Invio il messaggio al server
                out.println(messaggioOutput);
                out.flush();
            } while(continua);  // Continuo a ripetere fino a quando .comunicaS() ritorna false
            
            // Si attende fino a quanto il thread di ascolto viene chiuso correttamente
            boolean stampa = true;
            while(inThread.isAlive()) { 
                if(stampa) {
                    System.out.println("In attesa di conferma chiusura");
                    stampa = false;
                }
            }
        } catch (IOException ex) {
            System.err.println("Errore : " + ex.getMessage());
        }
    }
    
    /**
     * Metodo che permette la gestione del comando che si inserisce nel messaggio
     */
    public void gestisciMessaggio() {
        String[] messaggioDiv = messaggioOutput.split("_",3); // Divido il messaggio in tre blocchi, uno di questi conterrà il comando digitato dall'utente
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
                messaggioOutput = "L'utente ha chiuso la connessione.";
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
    
    /**
     * Metodo che permette di cambiare il proprio nome tramite input da tastiera
     */
    public void setNome() {
        try {
            System.out.println("Vecchio nome : " + nome + "\nScrivi il tuo nuovo nome: ");
            BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
            nome = tastiera.readLine();
        } catch(IOException ex) {
            System.err.println("Errore durante l'input. " + ex.getMessage());
        }
    }
    
    /**
     * Metodo che permette di modificare il valore dell'attributo continua
     * @param b valore che si vuole assegnare all'attributo continua
     */
    public synchronized void setContinua(boolean b) {
        continua = b;
    }
    
    /**
     * Metodo che permette di modificare il valore dell'attributo messaggioInput
     * @param in valore che si vuole assegnare all'attributo messaggioInput
     */
    public synchronized void setMsgInput(String in) {
        messaggioInput = in;
    }
    
    /**
     * Metodo che permette di ottenere il valore dell'attributo online
     * @return valore della booleana online
     */
    public synchronized boolean isOnline() {
        return online;
    }
    
    /**
     * Metodo che permette di ottenere il valore dell'attributo continua
     * @return valore della booleana continua
     */
    public synchronized boolean getContinua() {
        return continua;
    }
    
}
