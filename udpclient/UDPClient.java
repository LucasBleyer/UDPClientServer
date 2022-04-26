
package udpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

    public static void main(String[] args) throws Exception {
        
        // Verifica parâmetros
        if (args.length != 2)
        {
            System.err.println("Invalid parameters!"
                    + "\n\tUses: UDPClient <server_ip_addr> "
                    + "<server_port_addr>");
            System.exit(1);
        }
        
        // Avisa que o programa está rodando
        System.out.println("Starting UDPClient..."
                + "\n\tServer address: "
                + args[0] + ":" + args[1]);
        
        // Cria buffer de comunicação
        BufferedReader inBufferedReader = 
                new BufferedReader(
                        new InputStreamReader(System.in)
                );
        try {
            // Cria socket
            DatagramSocket clientSock = new DatagramSocket();
            
            // Obter endereço IP do servidor
            InetAddress srvIPAddr = 
                    InetAddress.getByName(args[0]);
            
            // Obter porta do servidor
            int srvPort = Integer.parseInt(args[1]);
            
            // Cria buffers de comunicação
            byte[] txData = new byte[65507];
            byte[] rxData = new byte[65507];
            
            // Cria lopping de comunicação
            while (true) {                
                // Pede ao usuário para digitar uma msg
                System.out.print("Type a message: ");
                String txMsg = inBufferedReader.readLine();
            
                // Converte em array de bytes
                txData = txMsg.getBytes();
            
                // Cria o pacote de envio
                DatagramPacket txPkt = 
                    new DatagramPacket(txData, 
                        txMsg.length(),
                        srvIPAddr,
                        srvPort);
            
                // Envia a mgs
                System.out.println("Sending message...");
                clientSock.send(txPkt);
            
                // Cria o pacote de recebimento da resposta
                DatagramPacket rxPkt;
                rxPkt = new DatagramPacket(
                    rxData, rxData.length
                );
            
                // Recebe a msg
                System.out.println("Waiting message...");
                clientSock.receive(rxPkt);
            
                // Imprime a msg
                String rxMsg = 
                    new String(rxPkt.getData());
                System.out.println("\tResponse from server: "
                    + rxMsg);
            }
            
        } catch (IOException | NumberFormatException e) {
            System.err.println(e.toString());
        }
    }
    
}
