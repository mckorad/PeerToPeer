**Make sure you disable the firewall on the systems that will be used for this project.**

1)P2P_client.rar contains the client code.
2)P2PCI.rar contains the server code.
3)Extract the above two .rar files.
4)Open eclipse. File->Import--> General-->Existing Projects into workspace--> Import server code. Run the server code.
5)Open eclipse 2.File->Import--> General-->Existing Projects into workspace-->  Import client code.
6) Create an xml file "C:\\Minakshi\\sem1\\IP\\Project\\Client\\sample.xml" with the RFC infomation available on the client. 

Sample sample.xml

<?xml version="1.0" encoding="ISO-8859-1"?>
 <List>
	<RFC>
       <number>123</number>
       <version>P2P-CI/1.0</version>
	<port>5678</port>
       <title>A Proferred Official ICP</title>
   </RFC>

<RFC>
       <number>2345</number>
       <version>P2P-CI/1.0</version>
	<port>5678</port>
       <title>Domain Names and Company Name Retrieval</title>
   </RFC>
</List>


7)Save the RFCs in folder "C:\\Minakshi\\IP\\Project\\RFCs"

8)Create the folder on client "C:\\Minakshi\\IP\\Project\\DownloadedRFCs\\" where the RFCs will be downloaded.

9)On another system. Open eclipse.File->Import--> General-->Existing Projects into workspace-->  Import client code. C:\\Minakshi\\IP\\Project\\Client\\sample.xml" with the RFC infomation available on the client as above. Save the RFCs in folder "C:\\Minakshi\\IP\\Project\\RFCs". Create the folder on client "C:\\Minakshi\\IP\\Project\\DownloadedRFCs\\" where the RFCs will be downloaded.

10)Run the server. ("Run" Menu item on the Menu bar)

11)Run both the clients(("Run" Menu item on the Menu bar)). You will get a menu of options. Use the options one by one.
