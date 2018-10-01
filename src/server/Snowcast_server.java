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

import clientes.Snowcast_listener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import util.Musica;
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
    int portUDPCliente;

    Mensagem protocoloHello;
    Mensagem protocoloWelcome;
    Mensagem protocoloAnnounce;
    Mensagem protocoloSetStation;
    Mensagem InvalidCommand;

    //Snowcast_server server;
    Snowcast_server_fr view;
    Snowcast_listener clienteUDP;
    Musica musica;

    //Cria uma conexão ServerSocket
//    private void criaServerSocket(int portaServer) throws IOException {
//        serverSocket = new ServerSocket(portaServer);
//    }
    //Escuta uma conexão a ser feita neste soquete e a aceita. O método bloqueia até que uma conexão seja feita.
//    private Socket esperaConexão() throws IOException {
//        Socket socket = serverSocket.accept();
//        return socket;
//    }
    //Fechando o socket
    private void fechaConexao(Socket socket) throws IOException {
        System.out.println("Fechando conexão ...");
        socket.close();
    }

    //Cria uma conexão de entrada e saída entre cliente e servidor de acordo com o protocolo
    private void trataConexao(Socket socket) throws IOException {
        //Retorna um fluxo de entrada para este soquete. Envia para o servidor
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

        try {
            //###### Comandos do cliente para o servidor ######
            // 1. Hello:      uint8_t commandType= 0;  uint16_t udpPort;
            // 2. SetStation: uint8_t commandType = 1; uint16_t stationNumber;
            //###### Respostas do servidor para o cliente ######
            // 1. Welcome:        uint8_t replyType = 0; uint16_t numStations;
            // 2. Announce:       uint8_t replyType = 1; uint8_t songnameSize;    char songname[songnameSize];
            // 3. InvalidCommand: uint8_t replyType = 2; uint8_t replyStringSize; char replyString[replyStringSize];

            //Cria um Objeto tipo Mensagem para se comunicar com o servidor
            //Recebendo o comando Hello do cliente 
            //Lê um objeto ObjectInputStream, com a porta UDP Cliente
            protocoloHello = (Mensagem) input.readObject();

            //Verificando o comando HELLO
            if (protocoloHello.getCommandType() == '0') {
                // 1. Hello: uint8_t commandType= 0;  uint16_t udpPort;
                portUDPCliente = protocoloHello.getUpdPort();
                System.out.println("Cliente recebido , porta UDP : " + portUDPCliente);

            } else {
                JOptionPane.showMessageDialog(null, "Erro : Cliente não conseguiu se conectar.");
                fechaConexao(socket);
            }

            //Cria o protocolo Welcome para se comunicar com o cliente
            // 1. Welcome: uint8_t replyType = 0; uint16_t numStations;
            protocoloWelcome = new Mensagem();
            protocoloWelcome.setReplayType('0');
            protocoloWelcome.setNumStation(numEstacoes);
            //Enviando o protocolo Welcome ao cliente
            output.writeObject(protocoloWelcome);
            output.flush();

            //Cria o protocolo Announce para enviar as estações
            // 2. Announce: uint8_t replyType = 1; uint8_t songnameSize;    char songname[songnameSize];
            protocoloAnnounce = new Mensagem();
            protocoloAnnounce.setReplayType('1');
            //Criando uma interface Map com chave e valor, listando as estações
            Map<Integer, String> estacoesMAP = new HashMap<>();
            estacoesMAP.put(0, "The Zephyr Song");
            estacoesMAP.put(1, "Breaking The Girl");
            estacoesMAP.put(2, "Otherside");
            estacoesMAP.put(3, "Californication");

            protocoloAnnounce.setEstacoes(estacoesMAP);

            //Enviando o protocolo Welcome ao cliente
            output.writeObject(protocoloAnnounce);
            output.flush();

            //Recebendo o número da estação selecionada pelo cliente
            // 2. SetStation: uint8_t commandType = 1; uint16_t stationNumber;
            protocoloSetStation = new Mensagem();
            protocoloSetStation = (Mensagem) input.readObject();

            if (protocoloSetStation.getCommandType() == '1') {
                if (protocoloSetStation.getStationNumber() == 0) {
                    System.out.println("Estação selecionada pelo cliente : " + protocoloSetStation.getStationNumber());
                } else {
                    System.out.println("Paciência deve ter, meu jovem Padawan.");
                }

                //Enviando arquivo da canção para cliente UDP
                //Se conectar com o cliente UDP com a estação escolhida
                //udpServer(portUDPCliente, protocoloSetStation.getNumStation());
            } else {
                JOptionPane.showMessageDialog(null, "Erro no protocolo SetStation ");
            }

        } catch (IOException | ClassNotFoundException ex) {
            //Tratando as falhas
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

//        input.close();
//        output.close();
    }

    public void startServer() {
        try {
            //Instacia um objeto tipo Snowcast_server
            //Snowcast_server server = new Snowcast_server();
            System.out.println("Aguardando conexão...");

            //Criando um socket de conexão
            //server.criaServerSocket(portTCPServer);
            serverSocket = new ServerSocket(portTCPServer);

            while (true) {
                //Espera a solicitação de uma conexão do cliente
                //Socket socket = server.esperaConexão();
                Socket socket = serverSocket.accept();

                //Tratando conexão
                trataConexao(socket);

                System.out.println("Aguardando um novo cliente...");
            }

        } catch (Exception ex) {
            //Tratando erros na comunicação Socket
            JOptionPane.showMessageDialog(null, "Erro de comunicação do Socket: " + ex.getMessage());
        }
    }

//    private void udpServer(int portaUdpCliente, int stationNumber) throws SocketException, IOException, ClassNotFoundException {
//
//        //Porta UDP do cliente
//        this.portUDP = portaUdpCliente;
//        //Número da estação recebida pelo cliente
//        this.stationNumber = stationNumber;
//        int numConn = 1;
//
//        //Criando um servidor de Datagram
//        DatagramSocket serverSocket = new DatagramSocket(portUDPServer);
//        System.out.println("Servidor UDP criado na porta " + portUDPServer);
//        //Instanciando um cliente UDP
//        clienteUDP = new Snowcast_listener();
//        clienteUDP.start();
//
//        musica = new Musica(1, "The Zephyr Song");
//
//        /* Escrita do Objeto*/
//        FileOutputStream escrever = new FileOutputStream("C:/");
//        ObjectOutputStream oos = new ObjectOutputStream(escrever);
//        oos.writeObject(oos);
//        oos.close();
//
//        /* Escrita do Objeto*/
//        FileInputStream ler = new FileInputStream("C:/");
//        ObjectInputStream ois = new ObjectInputStream(ler);
//        Musica musica1 = (Musica) ois.readObject();
//        ois.close();
//
//        while (true) {
//
//            //byte[] dadosRecebidos = new byte[1024];
//            byte[] dadosEnviados = new byte[1024];
//
//            DatagramPacket sendPacket = new DatagramPacket(dadosEnviados, dadosEnviados.length);
//
//            serverSocket.send(sendPacket);
//
//            System.out.println("OK\n");
//        }
//    }
    public static void main(String[] args) {
        new Snowcast_server().startServer();
    }

}
