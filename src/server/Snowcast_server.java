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
import java.net.ServerSocket;
import java.net.Socket;
import util.Mensagem;
import view.Snowcast_server_fr;

/**
 *
 * @author Programador-03
 */
public class Snowcast_server {

    private ServerSocket serverSocket;
    static final int portServer = 55555;
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

    //Fechando o socket
    private void fechaConexao(Socket socket) throws IOException {
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

            //Cria um Objeto tido Mensagem para se comunicar com o servidor
            //Recebendo o comando Hello do cliente 
            //Lê um objeto ObjectInputStream, porta UDP Cliente
            Mensagem protocoloHello = (Mensagem) input.readObject();

            //Cria um Objeto tido Mensagem para se comunicar com o servidor
            Mensagem protocoloWelcome = new Mensagem();
            protocoloWelcome.setReplayType('0');
            protocoloWelcome.setNumStation(10);

            //Verificando o comando HELLO
            if (protocoloHello.getCommandType() == '0') {
                //Comando do cliente recebido com sucesso: Comando HELLO - CLIENTE ---> socket ----> SERVIDOR
                System.out.println("Porta UDP Client : " + protocoloHello.getUpdPort());

                //Enviando estações ao cliente
                output.writeObject(protocoloWelcome);
                //Envia dados de controle (Estações)
                
                output.writeUTF("Estações : <estação1>||<arquivo1>, <estação2>||<arquivo2>, <estação3>||<arquivo3>");
                output.flush();
                
                //Recebendo o número da estação selecionada pelo cliente
                int numStation = (Integer) input.readObject();
                System.out.println("Estação selecionada pelo cliente : " + numStation);
                
                
                //Enviando arquivo da canção para cliente UDP
                
                

            } else {
                System.out.println("Erro no comando HELLO");
            }

            input.close();
            output.close();

        } catch (Exception ex) {

            //Tratando as falhas
            System.out.println("Erro : " + ex.getMessage());

        } finally {

            //Fechando conexão
            fechaConexao(socket);

        }
    }

    public static void main(String[] agrs) throws IOException {
        try {
            //Instacia um objeto tipo Snowcast_server
            Snowcast_server server;
            server = new Snowcast_server();
            System.out.println("Aguardando conexão...");

            //Cria uma conexão
            server.criaServerSocket(portServer);

            while (true) {
                //Espera a solicitação de uma conexão do cliente
                Socket socket = server.esperaConexão();

                //Tratando conexão
                server.trataConexao(socket);

                //Fechando conexão
                server.fechaConexao(socket);

                System.out.println("Aguardando clientes...");
            }

        } catch (Exception ex) {
            //Tratando erros na comunicação Socket
            System.out.println("Erro de comunicação do Socket: " + ex.getMessage());
        }

    }

}
