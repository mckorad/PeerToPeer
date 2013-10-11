import java.net.*;
import java.io.*;
public class MultiServer {
	
	public static PeerLinkedList p = new PeerLinkedList();
	public static RFCLinkedList r= new RFCLinkedList();

	public static void main(String[] args) throws IOException {
     
    	ServerSocket serverSocket = null;
      
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(7734);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 7734.");
            System.exit(-1);
        }
        

        while (listening)
        {
        new MultiServerThread(serverSocket.accept()).start();
        System.out.println(serverSocket.getInetAddress()+" connected\n");
        System.out.println( serverSocket.getLocalSocketAddress()+" local socket connected\n");
        }
       
        serverSocket.close();
    }
}
