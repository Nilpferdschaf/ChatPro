package io.lukas;

import java.net.*;
import java.io.*;
import java.net.Socket;

public class SenderThread extends Thread {
    
    private Thread t;
    // declare low level and high level objects for input
    private InputStream inStream;
    private DataInputStream inDataStream;
    
    // declare low level and high level objects for output
    private OutputStream outStream;
    private DataOutputStream outDataStream;
    
    // declare socket
    private Socket connection;
    
    // connection Data
    private String remoteMachine;
    private int port;
    
    public SenderThread(String remoteMachine, int port) {
        System.out.println("StartedInnen");
        this.remoteMachine = remoteMachine;
        this.port = port;
        startSender();
    }
    
    private void startSender() {
        try {
            System.out.println("StartedInnen2");
            // attempt to create a connection to the server
            connection = new Socket(remoteMachine, port);
            System.out.println("Verbindung konnte hergestellt werden");
            
            // create an input stream from the server
            inStream = connection.getInputStream();
            inDataStream = new DataInputStream(inStream);
            
            // create output stream to the server
            outStream = connection.getOutputStream();
            outDataStream = new DataOutputStream(outStream);
            
            // send the host IP to the server
            outDataStream.writeUTF(connection.getLocalAddress().getHostAddress());
            
        } catch (UnknownHostException e) {
            System.out.println("Unknow host");
        } catch (IOException except) {
            System.out.println("Network Exception");
        }
        try {
            System.out.println("Ich leg mich jetzt schlafen");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Send("String", "Nicklas rulz");
        try {
            System.out.println("Ich leg mich jetzt schlafen");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Send("String", "Katzen > Hunde");
        try {
            System.out.println("Ich leg mich jetzt schlafen");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Send("String", "Tau ist die besser Kreiskonstante!");
    }
    
    public void Send(String Typ, String Text) {
        if (Typ == "String") {
            try {
                // outDataStream.writeUTF("Dies ist ein Dummy-Text, der echte wird sp�ter durch die GUI gelieftert");
                outDataStream.writeUTF(Text);
            } catch (IOException e) {
                System.out.println("Nachricht konnte nicht gesendet werden");
                // !!!! Ausgabe auf GUI
            }
            try {
                String success = inDataStream.readUTF();
                System.out.println("SuccessAusgabe!!!: " + success);
                if (!success.equals("successfull")) {
                    System.out.println("Nachricht konnte nicht gesendet werden dank javas schuld");
                    // !!!! Ausgabe auf GUI
                } else {
                    System.out.println("Die Nachricht konnte  gesendet werden");
                }
            } catch (IOException e) {
                System.out.println("Nachricht konnte nicht gesendet werden");
                // !!!! Ausgabe auf GUI
                e.printStackTrace();
            }
        } else {
            System.out.println("Datentyp noch nicht unterst�tzt");
        }
    }
    
    public void run() {
        startSender();
    }
    
    public void start()
    {
        System.out.println("Starting ");
        if (t == null)
        {
            t = new Thread(this);
            t.start();
        }
    }
}
