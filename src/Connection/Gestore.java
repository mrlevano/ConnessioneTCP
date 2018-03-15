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
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author jesus
 */
public class Gestore {
    
    private final BufferedReader in;
    
    private final PrintStream out;
    
    private boolean continua;
    
    private String messaggioOutput;
    
    private String messaggioInput;
    
    private String comando;
    
    private String nome;
    
    private boolean online;
    
    public Gestore(BufferedReader in, PrintStream out) {
        this.in = in;
        this.out = out;
        continua = true;
        messaggioOutput = "";
        messaggioInput = "";
        comando = "";
        nome = "anon";
        online = true;
    }
    
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
                
                // Invio il messaggio al server
                out.println(messaggioOutput);
                out.flush();
            } while(continua);  // Conntinuo a ripetere fino a quando .comunicaS() ritorna false
            
        } catch (IOException ex) {
            System.err.println("Errore : " + ex.getMessage());
        }
    }
    
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
