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
    
    private boolean continua;
    
    public ThreadInputGestore(BufferedReader in, Gestore user) {
        this.in = in;
        continua = true;
        this.user = user;
    }
    
    @Override
    public void run() {
        String messaggioInput;
        try {
            do {
                // Ricevo la risposta del server
                messaggioInput = in.readLine();
                user.setMsgInput(messaggioInput);
                System.out.println("Messaggio: " + messaggioInput);


                if(messaggioInput.equals("Ciao ciao!")) { // Se il sever risponde ciao ciao allora si ritorna falso
                    continua = false;
                    user.setContinua(continua);
                }
            }while(continua);
        } catch(IOException ex) {
            System.err.println("Errore di lettura : " + ex.getMessage());
        }
        
    }
    
}
