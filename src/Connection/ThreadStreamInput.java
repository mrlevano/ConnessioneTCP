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
public class ThreadStreamInput extends Thread {
    
    private BufferedReader in;
    
    private ClientConnessioneTCP client;
    
    private ServerConnessioneTCP server;
    
    private boolean continua;
    
    public ThreadStreamInput(BufferedReader in, ClientConnessioneTCP client) {
        this.in = in;
        continua = true;
        this.client = client;
        server = null;
    }
    
    public ThreadStreamInput(BufferedReader in, ServerConnessioneTCP server) {
        this.in = in;
        continua = true;
        this.server = server;
        client = null;
    }
    
    @Override
    public void run() {
        String messaggioInput;
        try {
            do {
                // Ricevo la risposta del server
                messaggioInput = in.readLine();
                if(server == null) {
                    client.setMsgInput(messaggioInput);
                } else {
                    server.setMsgInput(messaggioInput);
                }
                System.out.println("Messaggio: " + messaggioInput);


                if(messaggioInput.equals("Ciao ciao!")) { // Se il sever risponde ciao ciao allora si ritorna falso
                    continua = false;
                    if(server == null) {
                        client.setContinua(continua);
                    } else {
                        server.setContinua(continua);
                    }
                }
            }while(continua);
        } catch(IOException ex) {
            System.err.println("Errore : " + ex.getMessage());
        }
        
    }
    
}
