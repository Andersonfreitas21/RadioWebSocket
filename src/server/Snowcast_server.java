//////////////////Parâmetros do Trabalho WEB RÁDIO COM SOCKETS///////////////////////////////////////////////////////////
//	Cria uma conexão segura TCP via socket
//	Recebe do cliente qual estação deseja estudar a canção
//	
//	Respostas do servidor para o cliente
//	Welcome (Enviado em resposta ao comando Hello)
//		numStations
//		Envia dados de controle (Estações)
//	
//	Announce (Depois que o cliente envia um comando SteStation ou Quando a estação ao qual um 
//                    cliente está escutando muda a canção)
//		songnameSize (tamanho em bytes do arquivo)
//		char songname[songnameSize] (nome do arquivo da canção) 
//		Enviar a canção via UDP
//		
//	InvalidCommand (É enviado quando recebido um comando inválido)
//		replyString (Contém uma mensagem de erro explicando o que ocorre)
//		Toda vez que o servidor receber um comando inválido, 
//                    ele deve responder com InvalidCommand e fechar a conexão com o cliente
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import util.Mensagem;
import view.Snowcast_server_fr;

/**
 *
 * @author Anderson Freitas
 */
public class Snowcast_server {

    private ServerSocket serverSocket;
    private final int portTCPServer = 55555;
    private int portUDPServer = 44444;
    private int portUDP;
    private final int numEstacoes = 4;
    private int stationNumber;

    Mensagem protocoloHello;
    Mensagem protocoloWelcome;
    Mensagem protocoloAnnounce;
    Mensagem protocoloSetStation;
    Mensagem InvalidCommand;

    Snowcast_server server;
    Snowcast_server_fr view;

    //Cria uma conexão ServerSocket
    private void criaServerSocket(int portaServer) throws IOException {
        serverSocket = new ServerSocket(portaServer);
    }

    //Escuta uma conexão a ser feita neste soquete e a aceita. O método bloqueia até que uma conexão seja feita.
    private Socket esperaConexão() throws IOException {
        Socket socket = serverSocket.accept();
        return socket;
    }

    private void udpServer(int portaUdpCliente, int stationNumber) throws SocketException, IOException {
        this.portUDP = portaUdpCliente;
        this.stationNumber = stationNumber;
        int numConn = 1;

        DatagramSocket serverSocket = new DatagramSocket(portTCPServer);

        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];

        while (true) {

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            System.out.println("Esperando por datagrama UDP na porta " + portUDPServer);
            serverSocket.receive(receivePacket);
            System.out.print("Datagrama UDP [" + numConn + "] recebido...");

            String sentence = new String(receivePacket.getData());
            System.out.println(sentence);

            InetAddress IPAddress = receivePacket.getAddress();

            int port = receivePacket.getPort();

            String capitalizedSentence = sentence.toUpperCase();

            sendData = capitalizedSentence.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData,
                    sendData.length, IPAddress, port);

            System.out.print("Enviando " + capitalizedSentence + "...");

            serverSocket.send(sendPacket);
            System.out.println("OK\n");
        }

    }

    //Fechando o socket
    private void fechaConexao(Socket socket) throws IOException {
        System.out.println("Fechando conexão ...");
        socket.close();
    }

    //Cria uma conexão de entrada e saída entre cliente e servidor de acordo com o protocolo
    private void trataConexao(Socket socket) throws IOException {
        try {
            //###### Comandos do cliente para o servidor ######
            // 1. Hello:      uint8_t commandType= 0;  uint16_t udpPort;
            // 2. SetStation: uint8_t commandType = 1; uint16_t stationNumber;
            //###### Respostas do servidor para o cliente ######
            // 1. Welcome:        uint8_t replyType = 0; uint16_t numStations;
            // 2. Announce:       uint8_t replyType = 1; uint8_t songnameSize;    char songname[songnameSize];
            // 3. InvalidCommand: uint8_t replyType = 2; uint8_t replyStringSize; char replyString[replyStringSize];

            //Retorna um fluxo de entrada para este soquete. Envia para o servidor
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

            //Cria um Objeto tipo Mensagem para se comunicar com o servidor
            //Recebendo o comando Hello do cliente 
            //Lê um objeto ObjectInputStream, com a porta UDP Cliente
            protocoloHello = (Mensagem) input.readObject();

            //Verificando o comando HELLO
            if (protocoloHello.getCommandType() == '0') {
                // 1. Hello: uint8_t commandType= 0;  uint16_t udpPort;
                int portUDPCliente = protocoloHello.getUpdPort();

                //Cria o protocolo Welcome para se comunicar com o cliente
                // 1. Welcome: uint8_t replyType = 0; uint16_t numStations;
                protocoloWelcome = new Mensagem();
                protocoloWelcome.setReplayType('0');
                protocoloWelcome.setNumStation(numEstacoes);
                //Enviando o protocolo Welcome ao cliente
                output.writeObject(protocoloWelcome);

                //Cria o protocolo Announce para enviar as estações
                // 2. Announce: uint8_t replyType = 1; uint8_t songnameSize;    char songname[songnameSize];
                protocoloAnnounce = new Mensagem();
                protocoloAnnounce.setReplayType('1');
                //Criando uma interface Map com chave e valor, listando as estações
                Map<String, String> estacoes = new HashMap<>();
                estacoes.put("0 - FM Araibu", "The Zephyr Song");
                estacoes.put("1 - Rádio Progresso", "Breaking The Girl");
                estacoes.put("2 - RussasNet", "Otherside");
                estacoes.put("3 - SomZoomSat", "Californication");

                protocoloAnnounce.setEstacoes(estacoes);

                //Enviando o protocolo Welcome ao cliente
                output.writeObject(protocoloAnnounce);
                output.flush();

                //Recebendo o número da estação selecionada pelo cliente
                // 2. SetStation: uint8_t commandType = 1; uint16_t stationNumber;
                protocoloSetStation = (Mensagem) input.readObject();

                if (protocoloSetStation.getCommandType() == '1') {
                    System.out.println("Estação selecionada pelo cliente : " + protocoloSetStation.getNumStation());

                    //Enviando arquivo da canção para cliente UDP
                    //Se conectar com o cliente UDP com a estação escolhida
                    udpServer(portUDPCliente,protocoloSetStation.getNumStation());
                } else {
                    JOptionPane.showMessageDialog(null, "Erro no protocolo SetStation ");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Erro : Cliente não conseguiu se conectar.");
                fechaConexao(socket);
            }

            input.close();
            output.close();

        } catch (IOException | ClassNotFoundException ex) {
            //Tratando as falhas
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            //Fechando conexão
//            fechaConexao(socket);
        }
    }

    public void startServer() {
        try {
            //Instacia um objeto tipo Snowcast_server
            server = new Snowcast_server();
            System.out.println("Aguardando conexão...");

            //Cria uma conexão
            server.criaServerSocket(portTCPServer);

            while (true) {
                //Espera a solicitação de uma conexão do cliente
                Socket socket = server.esperaConexão();

                //Tratando conexão
                server.trataConexao(socket);

                System.out.println("Aguardando um novo cliente...");
            }

        } catch (Exception ex) {
            //Tratando erros na comunicação Socket
            JOptionPane.showMessageDialog(null, "Erro de comunicação do Socket: " + ex.getMessage());
        }
    }

//    public void stopServer() throws IOException {
//        
//        //Instacia um objeto tipo Snowcast_server
//        this.server = new Snowcast_server();
//
//        //Espera a solicitação de uma conexão do cliente
//        Socket socket = server.esperaConexão();
//
//        this.server.fechaConexao(socket);
//    }
//    public static void main(String[] agrs) throws IOException {
//        try {
//            //Instacia um objeto tipo Snowcast_server
//            Snowcast_server server;
//            server = new Snowcast_server();
//            System.out.println("Aguardando conexão...");
//
//            //Cria uma conexão
//            server.criaServerSocket(portServer);
//
//            while (true) {
//                //Espera a solicitação de uma conexão do cliente
//                Socket socket = server.esperaConexão();
//
//                //Tratando conexão
//                server.trataConexao(socket);
//
//                //Fechando conexão
//                server.fechaConexao(socket);
//
//                System.out.println("Aguardando clientes...");
//            }
//
//        } catch (Exception ex) {
//            //Tratando erros na comunicação Socket
//            System.out.println("Erro de comunicação do Socket: " + ex.getMessage());
//        }
//
//    }
}
