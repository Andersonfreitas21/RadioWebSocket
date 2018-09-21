package clientes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
//import javax.swing.JOptionPane;
import util.Mensagem;
import view.Snowcast_control_fr;

/**
 *
 * @author Programador-03
 */
public class Snowcast_control {

    Snowcast_control_fr view;

    public void conexaoTCP(Snowcast_control_fr view) throws ClassNotFoundException {
        try {

            this.view = view;

            String server = "127.0.0.1";
            int portServer = 55555;
            int portUDPCliente = 66666;

            //Cria um socket para estabelecer conexãqo com o servidor
            Socket socketClienteTCP = new Socket(server, portServer);

            //Criação dos streams de entrada e saída
            ObjectOutputStream output = new ObjectOutputStream(socketClienteTCP.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socketClienteTCP.getInputStream());

            //###### Comandos do cliente para o servidor ######
            // 1. Hello:      uint8_t commandType= 0;  uint16_t udpPort;
            // 2. SetStation: uint8_t commandType = 1; uint16_t stationNumber;
            //###### Respostas do servidor para o cliente ######
            // 1. Welcome:        uint8_t replyType = 0; uint16_t numStations;
            // 2. Announce:       uint8_t replyType = 1; uint8_t songnameSize;    char songname[songnameSize];
            // 3. InvalidCommand: uint8_t replyType = 2; uint8_t replyStringSize; char replyString[replyStringSize];
            
            //Instanciando o protocolo HELLO
            Mensagem protocoloHello = new Mensagem();
            protocoloHello.setCommandType('0');
            protocoloHello.setUpdPort(portUDPCliente);

            //Enviando porta UDP para o servidor
            output.writeObject(protocoloHello);
            //Isso irá gravar quaisquer bytes de saída em buffer e flush através do fluxo subjacente.
            output.flush();

            //Recebendo a resposta do servidor
            Mensagem protocoloWelcome = (Mensagem) input.readObject();

            switch (protocoloWelcome.getReplayType()) {
                case '0':
                    //Protocolo de comunicação OK
                    //System.out.println("Número de estações : " + protocoloWelcome.getNumStation());
                    String estacoes = input.readUTF();
                    view.RetornoDados(estacoes);

                    //Cliente escolhe a estação para tocar a canção
                    Mensagem protocoloSetStation = new Mensagem();

                    //protocoloSetStation.setNumStation(est);
                    //Enviando para o servidor o número da estação selecionada
                    output.writeObject(protocoloSetStation.getNumStation());
                    output.flush();

                    break;
                case '1':
                    //2. Announce:uint8_t replyType = 1; uint8_t songnameSize; char songname[songnameSize];
                    System.out.println("uint8_t songnameSize; char songname[songnameSize]");

                    break;
                case '2':
                    //3.InvalidCommand:uint8_t replyType = 2; uint8_t replyStringSize; char replyString[replyStringSize];
                    JOptionPane.showMessageDialog(null, "InvalidCommand");
                    break;
                default:
                    System.out.println("Erro default");
                    break;
            }

            //Fechar os streams de entrada e saída
            input.close();
            output.close();

        } catch (IOException ex) {
            Logger.getLogger(Snowcast_control.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
