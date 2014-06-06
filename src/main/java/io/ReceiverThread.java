package io;

import io.net.events.MessageEvent;
import io.net.events.StatusChangeEvent;
import io.net.listeners.MessageListener;
import io.net.listeners.StatusListener;
import java.net.*;
import java.io.*;
import java.net.Socket;
public class ReceiverThread extends Thread{
	
    //a Variable to store the Port we are listening to
	private Thread t;
    private int port;
    private String Message = "";
    
    MessageListener Mlistener;
    StatusListener Slistener;
    
	// declare a "general" socket and a server socket
    Socket connection;
    ServerSocket listenSocket;
    //declare both Streams
    InputStream inStream;
    DataInputStream inDataStream;
    OutputStream outStream;
    DataOutputStream outDataStream;
    //creates a boolean "connected, to check, if you are connected
    boolean connected;
    boolean verbindend;
    
    public ReceiverThread(int port){
    	//System.out.println("TESTTESTTEST");
 /*   	try {
    		String localHost = InetAddress.getLocalHost().getHostName();
    		for(InetAddress ia : InetAddress.getAllByName(localHost)){
    		     System.out.println(ia);
    		}
    		 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   */
    	this.port = port;
    	//startSender();
    }
    
	private void startReceiver(){
        while(verbindend){
            try{
                // create a server socket
            	System.out.println("TESTEST");
                listenSocket = new ServerSocket(port,0, InetAddress.getLocalHost());
                System.out.println("GUTGUTGUT");
                System.out.println("Listening on port "+ port +"\n");

                //listen for a connection from the client 
                connection =listenSocket.accept();
                connected = true;
                Slistener.statusChanged(new StatusChangeEvent("Verbunden"));
                // create an input stream from the client
                inStream = connection.getInputStream();
                inDataStream = new DataInputStream(inStream);

                // create an output stream from the client
                outStream = connection.getOutputStream();
                outDataStream = new DataOutputStream(outStream);

                // wait for a string from the client
                System.out.println("wartend");
                String IP = inDataStream.readUTF();
                System.out.println("Connection esablished with "+ IP+ "\n");
                while(connected){
                    //read an integer from the client
                	Message = inDataStream.readUTF();
                	if(!Message.equals("")){
                		 Mlistener.messageReceived(new MessageEvent(Message));
                	}
                	Message = "";
                    // send "successfull" to the client
                    outDataStream.writeUTF("successfull");
                }
            }catch(IOException e){
            	verbindend = false;
                connected = false;
                Slistener.statusChanged(new StatusChangeEvent("Getrennt"));
            }
        }

	}
	
	public void addMessageListener(MessageListener listener){
		Mlistener = listener;
	}
	
	void addStatusListener(StatusListener listener){
		Slistener = listener;
	}
	
	public String getMessage(){
		return Message;
	}
	
	public void close(){
		connected = false;
		Slistener.statusChanged(new StatusChangeEvent("Getrennt"));
	    try {
			inStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			inDataStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			outStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			outDataStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(){
		startReceiver();
	}
	
	public void start (){
	    System.out.println("Starting ");
	    if (t == null){
	        t = new Thread (this);
	        t.start ();
	      }
	}
}
