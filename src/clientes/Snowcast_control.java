/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Programador-03
 */
public class Snowcast_control {

    public static void main(String[] args) {
        try {
            //Cria um socket para estabelecer conexãqo com o servidor
            Socket socketClienteTCP = new Socket("127.0.0.1", 55555);

            //Criação dos streams de entrada e saída
            ObjectOutputStream hello = new ObjectOutputStream(socketClienteTCP.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socketClienteTCP.getInputStream());

            //Porta cliente UDP
            int portaUDP = 6666;
            //Enviando porta UDP para o servidor
            hello.writeInt(portaUDP);
            //Isso irá gravar quaisquer bytes de saída em buffer e flush através do fluxo subjacente.
            hello.flush();

            //Recebendo uma mensagem do servidor
            String estacoes = input.readUTF();
            System.out.println("Estações do Servidor : " + estacoes);

            //Estação escolhida
            int setStation = 1;
            //Enviando ao servidor um código com a estação escolhida pelo cliente
            hello.writeInt(setStation);
            hello.flush();

            //Fechar os streams de entrada e saída
            hello.close();
            input.close();

        } catch (IOException ex) {
            Logger.getLogger(Snowcast_control.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
