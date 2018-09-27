/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Programador-03
 */
public class Snowcast_listener {

    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        int portaUDP = 33333;
        System.out.println("Cliente UDP , porta " + portaUDP);

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            String servidor = "127.0.0.1";
            int portaUDPServidor = 44444;
            
            InetAddress ipServidor = InetAddress.getByName(servidor);
            
            byte[] dadosEnviados = new byte[1024];
            byte[] dadosRecebidos = new byte[1024];
            
            System.out.println("Digite o texto a ser enviado ao servidor: ");
            String sentence = inFromUser.readLine();
            dadosEnviados = sentence.getBytes();
            DatagramPacket enviarPacote = new DatagramPacket(dadosEnviados,dadosEnviados.length, ipServidor, portaUDPServidor);
            
            System.out.println("Enviando pacote UDP para " + servidor + ":" + portaUDPServidor);
            clientSocket.send(enviarPacote);
            
            DatagramPacket receivePacket = new DatagramPacket(dadosRecebidos,dadosRecebidos.length);
            
            clientSocket.receive(receivePacket);
            System.out.println("Pacote UDP recebido...");
            
            String modifiedSentence = new String(receivePacket.getData());
            
            System.out.println("Texto recebido do servidor:" + modifiedSentence);
        }
        System.out.println("Socket cliente fechado!");
    }
}
