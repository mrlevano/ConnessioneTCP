/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.net.Socket;

/**
 *
 * @author jesus
 */
public class ClientMain {
    
    public static void main(String args[]) {
        ClientConnessioneTCP client = new ClientConnessioneTCP();
        Socket pippo = client.avviaConnessione();
        client.comunicaS(pippo);
        client.chiudiConnessione(pippo);
    }
    
}
