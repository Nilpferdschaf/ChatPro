package io;

import io.net.events.StatusChangeEvent;
import io.net.listeners.StatusListener;

import java.net.*;
import java.io.*;
import java.net.Socket;

public class SenderThread extends Thread{
	
	private Thread t;
	StatusListener Slistener;
    // declare low level and high level objects for input
    private InputStream inStream;
    private DataInputStream inDataStream;

    // declare low level and high level objects for output
    private OutputStream outStream;
    private DataOutputStream outDataStream;

    // declare socket
    private Socket connection;
	
    //connection Data
    boolean connected = false;
	private String remoteMachine;
    private int port;
    
    public SenderThread(String remoteMachine, int port){
    	System.out.println("StartedInnen");
    	this.remoteMachine = remoteMachine;
		this.port = port;
    	startSender();
    }
    
    
    private void startSender(){
        try{
        	System.out.println("StartedInnen2");
            // attempt to create a connection to the server
            connection = new Socket(remoteMachine,port);
            connected = true;
            Slistener.statusChanged(new StatusChangeEvent("Verbunden"));
            // create an input stream from the server
            inStream = connection.getInputStream();
            inDataStream = new DataInputStream(inStream);

            //create output stream to the server
            outStream = connection.getOutputStream();
            outDataStream = new DataOutputStream(outStream);

            //send the host IP to the server
            outDataStream.writeUTF(connection.getLocalAddress().getHostAddress());

        }catch (UnknownHostException e){
        	System.out.println("Unknow host");
        	connected = false;
        	Slistener.statusChanged(new StatusChangeEvent("Getrennt"));
        }
        catch (IOException except){
        	System.out.println("Network Exception");
        }
    }
	
	public boolean send(String Typ, String Text){
	  if(connected == true){	
		if(Typ == "String"){
			try {
				outDataStream.writeUTF(Text);
			} catch (IOException e) {
				return false;
			}
			try {
				String success = inDataStream.readUTF();
					if(!success.equals("successfull")){
						return true;
					}else{
						return false;
					}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}else{
			System.out.println("Datentyp noch nicht unterst�tzt");
		}
	  }else{
		  
	  }
	  return false;
	}
	
	void addStatusListener(StatusListener listener){
		Slistener = listener;
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
		startSender();
	}
	
	public void start ()
	   {
	      System.out.println("Starting ");
	      if (t == null)
	      {
	         t = new Thread (this);
	         t.start ();
	      }
	   }
}
