package clientes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import util.Mensagem;
import view.Snowcast_control_fr;

/**
 *
 * @author Anderson Freitas
 */
public class Snowcast_control {

    Mensagem protocoloHello;
    Mensagem protocoloWelcome;
    Mensagem protocoloAnnounce;
    Mensagem protocoloSetStation;
    Mensagem InvalidCommand;

    Snowcast_control clienteTCP;
    Snowcast_control_fr view;
    Socket socketClienteTCP;
    int stationNumber;

    public void criaConexaoTCP() throws IOException {
        String server = "127.0.0.1";
        int portServer = 55555;
        socketClienteTCP = new Socket(server, portServer);
    }

    /*
    try {
            this.view = view;
            this.stationNumber = stationNumber;
            
            String server = "127.0.0.1";
            int portServer = 55555;
            int portUDPCliente = 66666;

            //Cria um socket para estabelecer conexão com o servidor
            socketClienteTCP = new Socket(server, portServer);

            //Criação dos streams de entrada e saída
            ObjectOutputStream output = new ObjectOutputStream(socketClienteTCP.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socketClienteTCP.getInputStream());

            //###### Comandos do cliente para o servidor ######
            // 1. Hello:      uint8_t commandType = 0;  uint16_t udpPort;
            // 2. SetStation: uint8_t commandType = 1; uint16_t stationNumber;
            //###### Respostas do servidor para o cliente ######
            // 1. Welcome:        uint8_t replyType = 0; uint16_t numStations;
            // 2. Announce:       uint8_t replyType = 1; uint8_t songnameSize;    char songname[songnameSize];
            // 3. InvalidCommand: uint8_t replyType = 2; uint8_t replyStringSize; char replyString[replyStringSize];
            
            //Instanciando o protocolo HELLO
            // 1. Hello: uint8_t commandType= 0;  uint16_t udpPort;
            //O comando Hello é enviado quando o cliente conecta-se ao servidor. Ele diz ao servidor para qual porta UDP devem ser enviados os dados da canção.
            protocoloHello = new Mensagem();
            protocoloHello.setCommandType('0');
            protocoloHello.setUpdPort(portUDPCliente);

            //Enviando protocolo Hello para o servidor
            output.writeObject(protocoloHello);
            //Isso irá gravar quaisquer bytes de saída em buffer e flush através do fluxo subjacente.
            output.flush();

            //Recebendo a resposta do servidor
            // 1. Welcome: uint8_t replyType = 0; uint16_t numStations;
            protocoloWelcome = (Mensagem) input.readObject();

            if (protocoloWelcome.getReplayType() == '0') {
                //Protocolo de comunicação OK
                //Recebendo o número de estações 
                int numEstacoes = protocoloWelcome.getNumStation();
                System.out.println("Quantidade de estações disponíveis  : " + numEstacoes);

                protocoloSetStation = new Mensagem();
                // 2. SetStation: uint8_t commandType = 1; uint16_t stationNumber;
                protocoloSetStation.setCommandType('1');
                protocoloSetStation.setNumStation(stationNumber);
                output.writeObject(protocoloSetStation);
                output.flush();

            } else {
                JOptionPane.showMessageDialog(null, "Erro protocolo Welcome.");
            }

            //Recebendo as estações <estação><nomeCanção>
            //2. Announce: uint8_t replyType = 1; uint8_t songnameSize; char songname[songnameSize];
            protocoloAnnounce = (Mensagem) input.readObject();

            if (protocoloAnnounce.getReplayType() == '1') {
                //Listando as estações na grid Jtable1
                view.RetornoDados(protocoloAnnounce.getEstacoes());
            } else {
                JOptionPane.showMessageDialog(null, "Erro protocolo Announce.");
                //System.out.println("Erro protocolo Announce: " + protocoloAnnounce.getReplayType());
            }

            //Fechar os streams de entrada e saída
            input.close();
            output.close();

        } catch (IOException ex) {
            System.out.println("Erro conexão TCP:" + ex.getMessage());
}
     */
    //Método que inicia um socket e se comunica com o servidor
    public void conexaoTCP(Snowcast_control_fr view) throws ClassNotFoundException {
        try {
            this.view = view;
            int portUDPCliente = 66666;

            //Cria um socket para estabelecer conexão com o servidor
            criaConexaoTCP();

            //Criação dos streams de entrada e saída
            ObjectOutputStream output = new ObjectOutputStream(socketClienteTCP.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socketClienteTCP.getInputStream());

            //###### Comandos do cliente para o servidor ######
            // 1. Hello:      uint8_t commandType = 0;  uint16_t udpPort;
            // 2. SetStation: uint8_t commandType = 1; uint16_t stationNumber;
            //###### Respostas do servidor para o cliente ######
            // 1. Welcome:        uint8_t replyType = 0; uint16_t numStations;
            // 2. Announce:       uint8_t replyType = 1; uint8_t songnameSize;    char songname[songnameSize];
            // 3. InvalidCommand: uint8_t replyType = 2; uint8_t replyStringSize; char replyString[replyStringSize];
            //Instanciando o protocolo HELLO
            // 1. Hello: uint8_t commandType= 0;  uint16_t udpPort;
            //O comando Hello é enviado quando o cliente conecta-se ao servidor. Ele diz ao servidor para qual porta UDP devem ser enviados os dados da canção.
            protocoloHello = new Mensagem();
            protocoloHello.setCommandType('0');
            protocoloHello.setUpdPort(portUDPCliente);

            //Enviando protocolo Hello para o servidor
            output.writeObject(protocoloHello);
            //Isso irá gravar quaisquer bytes de saída em buffer e flush através do fluxo subjacente.
            output.flush();

            //Recebendo a resposta do servidor
            // 1. Welcome: uint8_t replyType = 0; uint16_t numStations;
            protocoloWelcome = (Mensagem) input.readObject();

            if (protocoloWelcome.getReplayType() == '0') {
                //Protocolo de comunicação OK
                //Recebendo o número de estações 
                int numEstacoes = protocoloWelcome.getNumStation();
                System.out.println("1. Welcome: uint8_t replyType = 0; uint16_t numStations : " + numEstacoes);

                //Listando as estações na grid Jtable1
                view.RetornoDados(protocoloWelcome.getEstacoes());
//
//                protocoloSetStation = new Mensagem();
//                // 2. SetStation: uint8_t commandType = 1; uint16_t stationNumber;
//                protocoloSetStation.setCommandType('1');
//                protocoloSetStation.setNumStation(stationNumber);
//                output.writeObject(protocoloSetStation);
//                output.flush();
            } else {
                JOptionPane.showMessageDialog(null, "Erro protocolo Welcome.");
            }

            
            //Fechar os streams de entrada e saída
            input.close();
            output.close();

        } catch (IOException ex) {
            System.out.println("Erro conexão TCP:" + ex.getMessage());
        }
    }

    public void conexaoTCP(Snowcast_control_fr view, int stationNumber) throws ClassNotFoundException {
        try {
            this.view = view;
            this.stationNumber = stationNumber;

            //Cria um socket para estabelecer conexão com o servidor
            criaConexaoTCP();

            //Criação dos streams de entrada e saída
            ObjectOutputStream output = new ObjectOutputStream(socketClienteTCP.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socketClienteTCP.getInputStream());

            protocoloSetStation = new Mensagem();
            // 2. SetStation: uint8_t commandType = 1; uint16_t stationNumber;
            protocoloSetStation.setCommandType('1');
            protocoloSetStation.setNumStation(stationNumber);
            output.writeObject(protocoloSetStation);
            output.flush();
            
            //2. Announce: uint8_t replyType = 1; uint8_t songnameSize; char songname[songnameSize];
            protocoloAnnounce = (Mensagem) input.readObject();

            if (protocoloAnnounce.getReplayType() == '1') {
                //Listando as estações na grid Jtable1
                JOptionPane.showMessageDialog(null, "Comando SetStation enviado.");
            } else {
                JOptionPane.showMessageDialog(null, "Erro protocolo Announce.");
            }

            //Fechar os streams de entrada e saída
            input.close();
            output.close();

        } catch (IOException ex) {
            System.out.println("Erro conexão TCP:" + ex.getMessage());
        }
    }
}
