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
import util.Mensagem;

/**
 *
 * @author Programador-03
 */
public class Snowcast_control {

    public static void main(String[] args) throws ClassNotFoundException {

        try {
            //Cria um socket para estabelecer conexãqo com o servidor
            Socket socketClienteTCP = new Socket("127.0.0.1", 55555);
            
            //Criação dos streams de entrada e saída
            ObjectOutputStream output = new ObjectOutputStream(socketClienteTCP.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socketClienteTCP.getInputStream());

            /**
             * Protocolo de comandos 
             * HELLO       --- > PortaUDP
             * HELLOREPLAY --- > Estações <arquivos>
             *
             */
            //Instanciando o protocolo HELLO
            Mensagem protocoloHello = new Mensagem();
            protocoloHello.setCommandType(0);
            protocoloHello.setUpdPort(66666);
            
            //Enviando porta UDP para o servidor
            output.writeObject(protocoloHello);
            //Isso irá gravar quaisquer bytes de saída em buffer e flush através do fluxo subjacente.
            output.flush();
            
            //Recebendo a resposta do servidor
            Mensagem protocoloWelcome = (Mensagem) input.readObject();
            
            if (protocoloWelcome.getReplayType()== 0) {
                //Protocolo de comunicação OK
                System.out.println("Número de estações :" + protocoloWelcome.getNumStation());
                String estacoes = input.readUTF();
                System.out.println(estacoes);
                
                
                //Cliente escolhe a estação para tocar a canção
                Mensagem protocoloSetStation = new Mensagem();
                protocoloSetStation.setNumStation(5);
                
                //Enviando para o servidor o número da estação selecionada
                output.writeObject(protocoloSetStation.getNumStation());
                output.flush();
                
            } else {
                //Comande Erro
                System.out.println("Erro!");
            }
            
            //Fechar os streams de entrada e saída
            input.close();
            output.close();

        } catch (IOException ex) {
            Logger.getLogger(Snowcast_control.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
