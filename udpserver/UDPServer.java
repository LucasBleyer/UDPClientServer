
package udpserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class UDPServer {

    public static void main(String[] args) throws Exception {
        
        // Teste de argumentos
        if (args.length != 1) {
            System.err.println("Invalid argument!\n\tUses: UDPServer <server_port>");
            System.exit(1);
        }
        
        // Teste do valor da porta
        if (Integer.parseInt(args[0]) < 1 || Integer.parseInt(args[0]) > 65535) {
            System.err.println("Invalid port value!\n\tRange: 1 - 65535");
            System.exit(1);
        }
        // Apresenta msg na tela
        System.out.println("Starting UDPServer at port " + args[0] + "...");
        
        // Armazena a porta do servidor
        int srvPort = Integer.parseInt(args[0]);
        
        // Cria o socket UDP
        try {
            DatagramSocket srvSock = new DatagramSocket(srvPort);
            
            // Criar buffers de comunicação
            // 65535 - 8 bytes UDP header - 20 bytes IP header = 65507
            byte[] rxData = new byte[65507];
            byte[] txData = new byte[65507];
            
            // Looping de comunicação
            while(true) {
        
                // Cria pacote de recebimento
                DatagramPacket rxPkt = 
                    new DatagramPacket(rxData, rxData.length);
            
                System.out.println("Waiting messages...");
                srvSock.receive(rxPkt);
            
                // Obter IP de origem
                InetAddress srcIPAddr = rxPkt.getAddress();
            
                // Obter porta de origem
                int srcPort = rxPkt.getPort();
                
                // Obter a msg
                rxData = rxPkt.getData();
                String msg = Arrays.toString(rxData);
                
                // Imprime msg na tela do servidor
                System.out.println("Message received...");
                System.out.println("\tSource IP....: " + srcIPAddr);
                System.out.println("\tSource Port..: " + srcPort);
                System.out.println("\tPayload length: " +rxPkt.getLength());
                System.out.println("\tMessage: " + msg);
                
                String txMsg = "Server received message";
                txData = txMsg.getBytes();
                DatagramPacket txPkt = 
                        new DatagramPacket(txData, txData.length, 
                                srcIPAddr, srcPort);
                System.out.println("Server reply message...");
                srvSock.send(txPkt);                
            }            
            
        } catch (IOException e) {
            System.err.println(e.toString());
       }                
    }
    
}
