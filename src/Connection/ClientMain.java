/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

/**
 *
 * @author jesus
 */
public class ClientMain {
    
    public static void main(String args[]) {
        ClientConnessioneTCP client = new ClientConnessioneTCP();
        client.avviaConnessione();
        client.comunicaS();
        client.chiudiConnessione();
        /*String nome = "Marcelo";
        String messaggioInput = "ciao, sono _autore_! tu sei epic_mlg_proplayerrr??";
        String[] messaggio = messaggioInput.split("_",3);
        messaggioInput = messaggio[0] + nome + messaggio[2];
        for(int i = 0 ; i < messaggio.length ; i++) {
            System.out.println(messaggio[i]);
        }
        System.out.println("\n" + messaggioInput);
        System.out.println("\uD83D\uDE40");*/
    }
    
}
