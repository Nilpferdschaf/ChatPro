package deprecated;

import io.net.events.StatusChangeEvent;
import io.net.listeners.StatusListener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatServer {
    
    private int port;
    private List<StatusListener> listeners;
    
    public ChatServer(int port) {
        this.port = port;
        this.listeners = new ArrayList<StatusListener>();
        
        startServer();
    }
    
    final void startServer() {
        // declare a "general" socket and a server socket
        Socket connection;
        ServerSocket listenSocket;
        // declare low level and high level objects for input
        InputStream inStream;
        DataInputStream inDataStream;
        
        // declare low level and high level objects for output
        OutputStream outStream;
        DataOutputStream outDataStream;
        
        // declare other variables
        String client;
        
        boolean connected;
        
        while (true) {
            try {
                // create a server socket
                listenSocket =
                        new ServerSocket(port, 0, InetAddress.getLocalHost());
                // listenSocket= new ServerSocket(port);
                notifyListeners("Listening on port " + port + "\n");
                
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
                client = inDataStream.readUTF();
                notifyListeners("Connection esablished with " + client + "\n");
                
                int first, second, sum1;
                while (connected) {
                    // read an integer from the client
                    first = inDataStream.readInt();
                    notifyListeners("First number receievd: " + first + "\n");
                    
                    // read an integer from the client
                    second = inDataStream.readInt();
                    notifyListeners("Second number receievd: " + second
                            + "\n");
                    
                    sum1 = first + second;
                    notifyListeners("Sum returned: " + sum1 + "\n");
                    
                    // send the sum to the client
                    outDataStream.writeInt(sum1);
                    // outDataStream.writeUTF("hi");
                }
                
                listenSocket.close();
            } catch (IOException e) {
                connected = false;
            } finally {}
        }
    }
    
    public void addNotificationListener(StatusListener listener) {
        
    }
    
    private void notifyListeners(String status) {
        for (Iterator<StatusListener> i = listeners.iterator(); i.hasNext();) {
            StatusListener listener = (StatusListener) i.next();
            
            listener.statusChanged(new StatusChangeEvent(status));
        }
    }
}
