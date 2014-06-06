package io.lukas;

import java.net.*;
import java.io.*;
import java.net.Socket;

public class ReceiverThread extends Thread {
    
    // a Variable to store the Port we are listening to
    private Thread t;
    private int port;
    private String Message;
    
    public ReceiverThread(int port) {
        // System.out.println("TESTTESTTEST");
        /*
         * try { String localHost = InetAddress.getLocalHost().getHostName();
         * for(InetAddress ia : InetAddress.getAllByName(localHost)){
         * System.out.println(ia); }
         * 
         * } catch (UnknownHostException e) { // TODO Auto-generated catch block
         * e.printStackTrace(); }
         */
        this.port = port;
        // startSender();
    }
    
    private void startReceiver() {
        
        // declare a "general" socket and a server socket
        Socket connection;
        ServerSocket listenSocket;
        
        // declare both Streams
        InputStream inStream;
        DataInputStream inDataStream;
        OutputStream outStream;
        DataOutputStream outDataStream;
        
        // creates a boolean "connected, to check, if you are connected
        boolean connected;
        // System.out.println("TESTTESTTEST");
        
        while (true) {
            try {
                // create a server socket
                System.out.println("TESTEST");
                listenSocket =
                        new ServerSocket(port, 0, InetAddress.getLocalHost());
                System.out.println("GUTGUTGUT");
                // listenSocket = new ServerSocket(port, 0, "25.163.206.71");
                // listenSocket = new ServerSocket(port);
                System.out.println("Listening on port " + port + "\n");
                
                // listen for a connection from the client
                connection = listenSocket.accept();
                connected = true;
                
                // create an input stream from the client
                inStream = connection.getInputStream();
                inDataStream = new DataInputStream(inStream);
                
                // create an output stream from the client
                outStream = connection.getOutputStream();
                outDataStream = new DataOutputStream(outStream);
                
                // wait for a string from the client
                System.out.println("wartend");
                String IP = inDataStream.readUTF();
                System.out.println("Connection esablished with " + IP + "\n");
                while (connected) {
                    // read an integer from the client
                    Message = inDataStream.readUTF();
                    System.out.println("Zeit: " + Message + "\n");
                    // send "successfull" to the client
                    outDataStream.writeUTF("successfull");
                }
            } catch (IOException e) {
                // System.out.println("FAILED");
                connected = false;
            }
        }
        
    }
    
    public void run() {
        startReceiver();
    }
    
    public void start() {
        System.out.println("Starting ");
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}
