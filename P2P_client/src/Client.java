import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Client {
	
   public static void main(String args[]) throws IOException {
	   new NewThread(); // create a new thread
	   BufferedReader reader;
	   String input;

	   reader = new BufferedReader(new InputStreamReader(System.in));
	   //Client registration
	   System.out.println("Do you wish to Continue(Press y or n)? ");
	   input=reader.readLine();
	   
	   if(input.equals("y"))
	   {
		   System.out.println("Enter hostname of server: ");
		   input=reader.readLine();
		  
		   // Open your connection to a server, at port 7734
		   Socket s1 = new Socket(InetAddress.getByName(input).getHostAddress(),7734);
		   // Get an input file handle from the socket and read the input
		   InputStream s1In = s1.getInputStream();
		   DataInputStream dis = new DataInputStream(s1In);

		   OutputStream s1out = s1.getOutputStream();
		   DataOutputStream dos = new DataOutputStream (s1out);
     
     
		   String inputLine = "";
     
		   boolean flag=true;
		   while(flag)
		   {
			   System.out.println("\nWould you like to: ");
			   System.out.println("1. Register and ADD (Add a locally available RFC to the server’s index)");
			   System.out.println("2. LOOKUP (Find peers that have the specified RFC)" );
			   System.out.println("3. LIST (Request the whole index of RFCs from the server)");
			   System.out.println("4. Download RFC from another peer? ");
			   System.out.println("5. Exit. (Close the connection)");
			   System.out.println("Enter your choice: "); 
			   input=reader.readLine();
			   switch(input)
			   {
			   		case "1" :
			   			File fXmlFile = new File(Constants.inputSampleXML);
			   			try {
			   				input ="";
			   				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			   				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			   				Document doc = dBuilder.parse(fXmlFile);
			   				doc.getDocumentElement().normalize();
	   
			   				NodeList nList = doc.getElementsByTagName(Constants.RFC);
			   				System.out.println("-----------------------");
	   
			   				for (int temp = 0; temp < nList.getLength(); temp++) {
			   					Node nNode = nList.item(temp);
			   					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			   						Element eElement = (Element) nNode;
			   						input = input.concat("ADD RFC " + getTagValue("number", eElement) + " " + getTagValue("version", eElement)+ "\nHost: " + InetAddress.getLocalHost().getHostName() + "\nPort: " + getTagValue("port", eElement) + "\nTitle: " + getTagValue("title", eElement) + "\n") ;
			   						dos.writeUTF(input);
			   						input="";
			   					}
			   				}
			   				dos.writeUTF("Constants.bye");
	  	
			   				System.out.println("Response from Server: ");
			   				while ((inputLine = dis.readUTF()) != null)
			   				{
			   					if(inputLine.contains("Constants.bye"))
			   						break;
			   					else
			   						System.out.println(inputLine);
			   				}
			   			} catch (Exception e) {
			   				e.printStackTrace();
			   			}
	    	 
			   			break;
			   			
			   		case "2":
			   			System.out.println("In case 2 LOOKUP Request");
			   			String rfcnumber="", rfctitle="";
				    	System.out.println("Enter RFC number: ");
				    	rfcnumber=reader.readLine();
				    	System.out.println("Enter RFC title: ");
				    	rfctitle = reader.readLine();
				    	input = "";
				    	input = input.concat("LOOKUP RFC " + rfcnumber + " " + "P2P-CI/1.0"+ "\nHost: " + InetAddress.getLocalHost().getHostName() + "\nPort: " + "5678" + "\nTitle: " + rfctitle + "\n") ;
				    	dos.writeUTF(input);
				    	dos.writeUTF("Constants.bye");
				    	System.out.println("Response from server:");
				    	while ((inputLine = dis.readUTF()) != null)
				      	{
				      		if(inputLine.contains("Constants.bye"))
				      			break;
				      		else
				      	   	   System.out.println(inputLine);
				      	}
				      	System.out.println("Out");
				        break;
				        
				   case "3":
					   input = "";
					   System.out.println("In case 3 LIST Request");
					   input = input.concat("LIST ALL P2P-CI/1.0"+ "\nHost: " + InetAddress.getLocalHost().getHostName() + "\nPort: " + "5678"  + "\n") ;
					   dos.writeUTF(input);
					   dos.writeUTF("Constants.bye");
					   System.out.println("Response from server:");
				   	   while ((inputLine = dis.readUTF()) != null)
				   	   {
				   		   if(inputLine.contains("Constants.bye"))
				   			   break;
				   		   else
				   			   System.out.println(inputLine);
				   	   }
				   	   System.out.println("Out");
				   	   break;
				    	 
				   case "4":
					   String hostname, portnumber;
					   System.out.println("Enter RFC number: ");
					   rfcnumber=reader.readLine();
					   System.out.println("Enter hostname from which you wish to downlaod the RFC: ");
					   hostname = reader.readLine();
					   System.out.println("Enter the port number of the remote host: ");
					   portnumber = reader.readLine();
					   input = "";
					   input = input.concat("GET RFC " + rfcnumber + " " + "P2P-CI/1.0"+ "\nHost: " + hostname + "\nOS: " + System.getProperty("os.name") + " " + System.getProperty("os.version") + "\n") ;
					   String ipaddress = InetAddress.getByName(hostname).getHostAddress();
					   System.out.println(ipaddress);
					   Socket s2 = new Socket(ipaddress,Integer.parseInt(portnumber));
					   InputStream s2In = s2.getInputStream();
					   DataInputStream dis2 = new DataInputStream(s2In);
				       OutputStream s2out = s2.getOutputStream();
				       DataOutputStream dos2 = new DataOutputStream (s2out);
				       dos2.writeUTF(input);
				       dos2.writeUTF("Constants.bye");
				       System.out.println("Response Received:");
				       while ((input = dis2.readUTF()) != null)
				       {
				    	   if(input.contains("Constants.bye"))
				    		   break;
				    	   else
				    	   {
				    		   System.out.println(input);
				    		   if(input.contains("Content-Type"))
				    		   {
				    			   int start_index= input.indexOf("Content-Type");
				    			   String filestring = input.substring(start_index + 23);
				    			   String filename = "rfc" + rfcnumber + ".txt";
				    			   File f;
				    			   f=new File(Constants.downloadedRFCs + filename);
				    			   if(!f.exists()){
				    				   f.createNewFile();
				    				   FileWriter fw = new FileWriter(f.getAbsoluteFile());
				    				   BufferedWriter bw = new BufferedWriter(fw);
				    				   bw.write(filestring);
				    				   bw.close();
				    				   System.out.println("$$$$$$$$$$$$");
				    				   System.out.println("File " +filename + "downloaded to " + Constants.downloadedRFCs);
				    			   }
				    			}
				    		 }
				    	 }
				    	 break;
	    	 
				     case "5":
				    	 flag=false;
				    	 dos.writeUTF("Bye " +  InetAddress.getLocalHost().getHostName() + "\n");
				    	 System.out.println("In case 5 Exit");
				    	 while ((inputLine = dis.readUTF()) != null)
				    	  {
				    		  if(inputLine.contains("Bye"))
				    		  {
				    			  dos.close();
				    		    	 s1out.close();
				    		    	 s1.close();
				    			  break;
				    		  }
				    		  else
				    	   	   System.out.println(inputLine);
				    	  }
				    	 System.out.println("Out");
				    	
				    	 break;
				     default: 
				    	 break;
				     }
				    }
		   }   
	     else
	    	 System.exit(0);
	   }
	   
	   private static String getTagValue(String sTag, Element eElement) {
			NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		 
		        Node nValue = (Node) nlList.item(0);
		 
			return nValue.getNodeValue();
		  }
   
}

//Create a new thread.
class NewThread implements Runnable {
Thread t;
NewThread() {
   // Create a new, second thread
   t = new Thread(this, "Demo Thread");
   System.out.println("Child thread: " + t);
   t.start(); // Start the thread
}

// This is the entry point for the second thread.
public void run() {
	   
	   //the peer first instantiates an upload server process listening to any available local port

	   ServerSocket s;
	   String portnumber="";
	try {
		String inputLine1 = "", outputLine1 = "";
		File fXmlFile1 = new File(Constants.inputSampleXML);
		
		
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
  		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
  		Document doc = dBuilder.parse(fXmlFile1);
  		doc.getDocumentElement().normalize();
   
  		NodeList nList = doc.getElementsByTagName(Constants.RFC);
  		System.out.println("-----------------------");
   
  		for (int temp = 0; temp < nList.getLength(); temp++) {
   
  		   Node nNode = nList.item(temp);
  		   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
   
  		      Element eElement = (Element) nNode;
  		    NodeList nlList = eElement.getElementsByTagName("port").item(0).getChildNodes();
  			 
	        Node nValue = (Node) nlList.item(0);
	 
	        portnumber = nValue.getNodeValue();
   
     		   }
  		}
		
  			s = new ServerSocket(Integer.parseInt(portnumber));
  			Socket clientSocket=s.accept();
  			InputStream s1Inclient = clientSocket.getInputStream();
		    DataInputStream disclient = new DataInputStream(s1Inclient);
		    
		    OutputStream s1outclient = clientSocket.getOutputStream();
		     DataOutputStream dosclient = new DataOutputStream (s1outclient);
		     Calendar currentDate = Calendar.getInstance(); 
		     SimpleDateFormat formatter= new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z"); 
		     String dateNow = formatter.format(currentDate.getTime());
		     System.out.println("Now the date is :=>  " + dateNow);
		     String rfcnumber = "";
		     File dir = new File(Constants.directoryRFC);

		     
		     
		  while ((inputLine1 = disclient.readUTF()) != null) {
			  System.out.println(inputLine1);
			  
			  if(inputLine1.contains(Constants.bye))
		   	  {
		   		  dosclient.writeUTF(Constants.bye);
		   		  break;
		   	  }
			  else{
			  
			  if(inputLine1.contains(Constants.RFC))
			  {
				  int start_index = inputLine1.indexOf(Constants.RFC);
	    		  int end_index = inputLine1.indexOf("P2P-CI/1.0");
	    		  
	    		  rfcnumber = inputLine1.substring(start_index+4, end_index-1);
			  }
			  
			  final String  finalrfcnumber = rfcnumber;
			  
			  File[] matches = dir.listFiles(new FilenameFilter()
			     {
			       public boolean accept(File dir, String name)
			       {
			    	   return name.contains(finalrfcnumber);
			       }
			     });
			  
			  Calendar calendar = Calendar.getInstance();
		        calendar.setTimeInMillis(matches[0].lastModified());
		         
		  
			  
			  outputLine1 = outputLine1.concat("P2P-CI/1.0 200 OK\n" + "Date: " + dateNow + "\nOS: " + System.getProperty("os.name") + " " );
			  outputLine1 = outputLine1.concat(System.getProperty("os.version")+ "\nLast Modified: " + formatter.format(calendar.getTime()));
			  outputLine1 = outputLine1.concat("\nContent-Length: " + matches[0].length() + "\nContent-Type: text/text\n"  );
			    FileInputStream fis = null;
			    BufferedInputStream bis = null;
			    DataInputStream dis = null;
			  
			  fis = new FileInputStream(matches[0]);

		      bis = new BufferedInputStream(fis);
		      dis = new DataInputStream(bis);

		      while (dis.available() != 0) {

		      // reads the line from the file and print it to console.
		    	  outputLine1 = outputLine1.concat((dis.readLine()+ "\n"));
		      }

		      // dispose all the resources after using them.
		      fis.close();
		      bis.close();
		      dis.close();
		      dosclient.writeUTF(outputLine1);
			  }
		     
		  }
		 s.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}