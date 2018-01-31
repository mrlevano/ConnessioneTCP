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
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ConnectException;

/**
 *
 * @author Jesus Levano
 */
public class ClientConnessioneTCP {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        //oggetto da usare per realizzare la connessione TCP
        Socket connection = null;
        //nome o IP del server
        String serverAddress = "localhost";
        //porta del server in ascolto
        int port = 2000;
        String messaggioOutput, messaggioInput;
        //apertura della connessione al server sulla porta specificata
        try{
            connection = new Socket(serverAddress, port);
            System.out.println("Connessione aperta");
            // Creo input e output per streams orientati ai byte
            BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader inputClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintStream outputClient = new PrintStream(connection.getOutputStream());
            
            System.out.println("Che voi scrivere al server?");
            messaggioOutput = tastiera.readLine();
            outputClient.println(messaggioOutput);
            outputClient.flush();
            
            messaggioInput = inputClient.readLine();
            System.out.println(messaggioInput);
            
        }
        catch(ConnectException e){
            System.err.println("Server non disponibile!\n" + e);
        }
        catch(UnknownHostException e1){
            System.err.println("Errore DNS!\n" + e1);
        }

        catch(IOException e2){
            System.err.println(e2);
        }

        //chiusura della connnessione
        finally{
                try {
            if (connection!=null)
                {
                    connection.close();
                    System.out.println("Connessione chiusa!");
                }
            }
            catch(IOException e){
                System.err.println("Errore nella chiusura della connessione!");
            }
        }
    }
}
