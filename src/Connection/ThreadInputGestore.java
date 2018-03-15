/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author jesus
 */
public class ThreadInputGestore extends Thread {
    
    private final BufferedReader in;
    
    private final Gestore user;
    
    public ThreadInputGestore(BufferedReader in, Gestore user) {
        this.in = in;
        this.user = user;
    }
    
    @Override
    public void run() {
        String messaggioInput;
        try {
            do {
                // Ricevo la risposta del server
                // è necessaria la conferma per la chiusura della connessione in quanto uno dei due thread di input sarà in ascolto durante la connessione
                messaggioInput = in.readLine();
                user.setMsgInput(messaggioInput);
                if(!messaggioInput.equals("Conferma chiusura.")) { // Controllo se l'altro utente ha mandato un messaggio di conferma per la chiusura del messaggio
                    System.out.println("Messaggio: " + messaggioInput);
                    if(messaggioInput.equals("L'utente ha chiuso la connessione.")) { // Se il sever risponde ciao ciao allora si ritorna falso
                        user.setContinua(false);
                    }
                }
            }while(user.getContinua());
        } catch(IOException ex) {
            System.err.println("Errore di lettura : " + ex.getMessage());
        }
        
    }
    
}
