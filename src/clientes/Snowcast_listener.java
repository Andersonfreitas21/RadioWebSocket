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
        System.out.println("Cliente UDP , porta" + portaUDP);

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        DatagramSocket clientSocket = new DatagramSocket();

        String servidor = "localhost";
        int porta = 44444;

        InetAddress IPAddress = InetAddress.getByName(servidor);

        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        System.out.println("Digite o texto a ser enviado ao servidor: ");
        String sentence = inFromUser.readLine();
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length, IPAddress, porta);

        System.out.println("Enviando pacote UDP para " + servidor + ":" + porta);
        clientSocket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);

        clientSocket.receive(receivePacket);
        System.out.println("Pacote UDP recebido...");

        String modifiedSentence = new String(receivePacket.getData());

        System.out.println("Texto recebido do servidor:" + modifiedSentence);
        clientSocket.close();
        System.out.println("Socket cliente fechado!");
    }
}
