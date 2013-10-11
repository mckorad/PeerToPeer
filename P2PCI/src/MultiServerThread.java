
import java.net.*;
import java.io.*;


public class MultiServerThread extends Thread {
    private Socket socket = null;
    private CountMonitor myCount ;
    CountMonitor c = new CountMonitor();
 
public MultiServerThread(Socket socket) {
	super("MultiServerThread");
	this.socket = socket;
}

public void run() {

try {
	myCount = c;
	 myCount.updateCount(1);
	    System.out.println("   Child-  thread count = " +
	    		myCount.getCount());
	   
    OutputStream s1out = socket.getOutputStream();
    DataOutputStream dos = new DataOutputStream (s1out);
    
    InputStream s1In = socket.getInputStream();
    DataInputStream dis = new DataInputStream(s1In);

    String inputLine, outputLine="", hostname = "", portnumber="", title="", hostname_rfc = ""; 
    		String rfcnumber="", myportnumber="";
    		boolean peerflag=true;
  
  
    while ((inputLine = dis.readUTF()) != null) {
    
    	
    	if(inputLine.contains("ADD"))
    	{
    		System.out.println("Client Request: ");
        	System.out.println(inputLine);
    	    		outputLine = outputLine.concat("P2PCI/1.0 ");
    	  if(inputLine.contains("Host: "))
    	  {
    		  int start_index = inputLine.indexOf("Host: ");
    		  int end_index = inputLine.indexOf("Port: ");
    	      		 hostname =  inputLine.substring(start_index+6, end_index-1);
    	      	   hostname_rfc = hostname;
    	      	   
    	      	  
    	  }
    		  
    	  if(inputLine.contains("Port: "))
    	  {
    		  int start_index = inputLine.indexOf("Port: ");
    		  portnumber = inputLine.substring(start_index+6, start_index+10);
    		  myportnumber = portnumber;
    	  }
    	  if(hostname!="" && portnumber!="" && peerflag==true)
    	  {
    		  
    		  MultiServer.p.add(hostname, Integer.parseInt(portnumber));
    		    System.out.println("PeerLinkedList: " + MultiServer.p.toString_());
    		 
    		  
    		    hostname=""; portnumber ="";
    		    peerflag = false;
    		   
    		    
    	  }
    
    	  if(inputLine.contains(Constants.RFC))
    	  {
    		  int start_index = inputLine.indexOf("RFC ");
    		  int end_index = inputLine.indexOf("P2P-CI/1.0");
    		  
    		  rfcnumber = inputLine.substring(start_index+4, end_index-1);
    	  }
    	  if(inputLine.contains("Title: "))
    	  {
    		  int start_index = inputLine.indexOf("Title: ");
    		  title = inputLine.substring(start_index+6);
    		title = title.replace("\n", "");
    	  }
    	  if(rfcnumber!="" && title!="" && hostname_rfc!="")
    	  {
    		  MultiServer.r.add(Integer.parseInt(rfcnumber), title, hostname_rfc);
    		  outputLine = outputLine.concat("200 OK\nRFC " + rfcnumber + " " + title + " " + hostname_rfc + " " + myportnumber );
    		  System.out.println("RFCLinkedList: " + MultiServer.r.toString_());
    		    dos.writeUTF(outputLine);
    		    outputLine = "";
        		  rfcnumber=""; title=""; hostname_rfc="";
    		  
    	  }
    	}
    	 else if(inputLine.contains("bye"))
   	  {
   		  dos.writeUTF("bye");
   	  }
  
    	else if(inputLine.contains("LOOKUP"))
        {
    		
    		outputLine = "";
    		outputLine = outputLine.concat("P2PCI/1.0 ");
        	System.out.println("Lookup Request from Client:");
        	System.out.println(inputLine);
        	if(inputLine.contains("Title: "))
      	  {
      		  int start_index = inputLine.indexOf("Title: ");
      		  title = inputLine.substring(start_index+6);
      		title = title.replace("\n", "");
//      		  title = inputLine.substring(7);
      	  }
        	
        	
        	 if(inputLine.contains(Constants.RFC))
       	  {
       		  int start_index = inputLine.indexOf("RFC ");
       		  int end_index = inputLine.indexOf("P2P-CI/1.0");
       		  
       		  rfcnumber = inputLine.substring(start_index+4, end_index-1);
       		  
       	  }
        	
        	int index[];
        	if(MultiServer.r.size()==0)
        	{
        		System.out.println("RFC Not Found");
        		outputLine = outputLine.concat("404 Not Found RFC Not Found");
        		 dos.writeUTF(outputLine);
     		    outputLine = "";
        	}
        	else{
        			index = MultiServer.r.search(rfcnumber, title, MultiServer.r.size());
        			boolean found=false;
        	for(int i=0; i<index.length; i++)
        	{
        		if(index[i]==1)
        		{
//        			System.out.println("Rfc found: " );
        			String hostname1 = MultiServer.r.get_hostnme(i+1);
        			int portnumber1 = MultiServer.p.search(hostname1);
        			outputLine = outputLine.concat("200 OK\nRFC " + MultiServer.r.get(i+1) + " " + portnumber1 + "\n" );
        			found = true;
        			 dos.writeUTF(outputLine);
         		    outputLine = "";
             		}
          	}
        	if(found==false)
        	{
        		System.out.println("RFC not found");
        	    outputLine = outputLine.concat("404 Not Found RFC Not Found");
        	    dos.writeUTF(outputLine);
    		    outputLine = "";
        	}
        	}
        }
    	
    	
    	else if(inputLine.contains("LIST"))
    	{
    		System.out.println("LIST Request from Client : ");
        	System.out.println(inputLine);
    		outputLine = "";
    		outputLine = outputLine.concat("P2PCI/1.0 ");
    		
    		if(MultiServer.r.size()==0)
        	{
        		System.out.println("RFC Not Found");
        		outputLine = outputLine.concat("404 Not Found RFC Not Found");
        	}
    		else{
    			outputLine = outputLine.concat("200 OK\n");
    			for(int i=0; i<MultiServer.r.size(); i++)
    			{
    				String hostname1 = MultiServer.r.get_hostnme(i+1);
        			int portnumber1 = MultiServer.p.search(hostname1);
    				outputLine = outputLine.concat(MultiServer.r.get(i+1)+" " + portnumber1 + "\n" );
       			}
     		}
    		 dos.writeUTF(outputLine);
    		outputLine = "";
    	}
    	
    	else if(inputLine.contains("Bye"))
    	{
    		String hostname1 = inputLine.substring(4);
    		hostname1= hostname1.replace("\n", "");
    		int index= MultiServer.p.search_returnIndex(hostname1);
    		MultiServer.p.remove(index);
    		int j=MultiServer.r.size();
    		for(int i=0; i<j; i++)
    		{
    		int index1 = MultiServer.r.search_returnIndex(hostname1);
    		MultiServer.r.remove(index1);
    		}
    		myCount.updateCount(-1);
    		  System.out.println("RFCLinkedList: " + MultiServer.r.toString_());
    		  System.out.println("PeerLinkedList: " + MultiServer.p.toString_());
    		 dos.writeUTF("Bye");
    		dos.close();
    		  s1out.close();
    		    socket.close();
    		    break;
    	}
    }

  
    
  
    
   
  
//    s1.close();
//    s1In.close();
//    dis.close();
   

} catch (IOException e) {
    e.printStackTrace();
}


}
}