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
 * @author Jesus Levano
 */
public class ServerConnessioneTCP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // porta del server maggiore di 1024 
        int port = 2000;
        //oggetto ServerSocket necessario per accettare richieste dal client
        ServerSocket sSocket = null;
        //oggetto da usare per realizzare la connessione TCP
        Socket connection;
        String messaggioInput, messaggioOutput;
        boolean continua = true;

        while(continua){
            try{
                // il server si mette in ascolto sulla porta voluta
                sSocket = new ServerSocket(port);
                System.out.println("In attesa di connessioni!");
                //si è stabilita la connessione
                connection = sSocket.accept();
                System.out.println("Connessione stabilita!");
                System.out.println("Socket server: " + connection.getLocalSocketAddress());
                System.out.println("Socket client: " + connection.getRemoteSocketAddress());
                // Creo input e output per streams orientati ai byte
                BufferedReader inputServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                PrintStream outputServer = new PrintStream(connection.getOutputStream());
                messaggioInput = inputServer.readLine();
                System.out.println("Messaggio del client : " + messaggioInput);
                
                switch(messaggioInput) {
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
                System.out.println("Risposta da mandare: " + messaggioOutput);
                outputServer.println(messaggioOutput);
                outputServer.flush();
                
            }
               catch(IOException e){
                   System.err.println("Errore di I/O!");
            }
            
            //chiusura della connessione con il client
            try {
                if (sSocket!=null) {
                    sSocket.close();
                }
            } catch (IOException ex) {
                System.err.println("Errore nella chiusura della connessione!");
            }
            System.out.println("Connessione chiusa!");
        }
      }
}
