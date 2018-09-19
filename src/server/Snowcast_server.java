//////////////////////////////////Servidor de sockets e esperar conexões dos clientes/////////////////////////////////////
//	Como estaremos trabalhando em rede, os comandos gerarão exceções 
//	Cria uma conexão segura TCP via socket
//	Estartar o servidor de sockets informando a porta de fluxo
//	ServerSocket server = new ServerSocket(7689)
//	
//	Recebendo conexões de clientes
//	while(true){
//		Aceita uma conexão cliente
//		Socket cliente = server.accept();
//		
//		Criando uma thread cliente (Class) passando um socket por parâmetro
//		ClientThread ct = new ClientThread(cliente);
//		cliente.start();
//		
//	}
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

/**
 *
 * @author Programador-03
 */
public class Snowcast_server {

    private ServerSocket serverSocket;

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
            //Retorna um fluxo de entrada para este soquete. Envia para o servidor
            ObjectOutputStream welcome = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            
            //Recebendo o comando Hello do cliente 
            //Lê um objeto InputSream, porta UDP Cliente
            int portUDPClient = input.readInt();
            System.out.println("Porta UDP Client : " + portUDPClient);
            
            //Envia para o cliente uma mensagem de fluxo
            //Respostas do servidor para o cliente
            //Welcome (Enviado em resposta ao comando Hello)
            //numStations
            //Envia dados de controle (Estações)
            welcome.writeUTF("Estações <arquivo1> <estação1>...");
            welcome.flush();

            //Recebendo o número da estação escolhida pelo cliente;
            int stationNumber = input.readInt();
            System.out.println("Número da estação escolhida : " + stationNumber);

            //Fechar os streams de entrada e saída
            welcome.close();
            input.close();

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
            Snowcast_server server = new Snowcast_server();
            System.out.println("Aguardando conexão...");

            //Cria uma conexão
            server.criaServerSocket(55555);

            //Espera a solicitação de uma conexão do cliente
            Socket socket = server.esperaConexão();

            //Tratando conexão
            server.trataConexao(socket);

            //Fechando conexão
            server.fechaConexao(socket);

        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }

    }

}
