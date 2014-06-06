package deprecated;

import io.net.events.StatusChangeEvent;
import io.net.listeners.StatusListener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatClient {
    
    // declare low level and high level objects for input
    private InputStream inStream;
    private DataInputStream inDataStream;
    
    // declare low level and high level objects for output
    private OutputStream outStream;
    private DataOutputStream outDataStream;
    
    // declare socket
    private Socket connection;
    
    private List<StatusListener> listeners;
    
    public ChatClient() {
        
        listeners = new ArrayList<StatusListener>();
    }
    
    public void connect(String ip, int port) {
        
        try {
            try {
                // attempt to create a connection to the server
                connection = new Socket(ip, port);
                notifyStatusListeners("Connection established");
            } catch (UnknownHostException e) {
                notifyStatusListeners("Unknow host");
                e.printStackTrace();
            }
            // create an input stream from the server
            inStream = connection.getInputStream();
            inDataStream = new DataInputStream(inStream);
            
            // create output stream to the server
            outStream = connection.getOutputStream();
            outDataStream = new DataOutputStream(outStream);
            
            // send the host IP to the server
            outDataStream.writeUTF(connection.getLocalAddress().getHostAddress());
            
        } catch (IOException except) {
            notifyStatusListeners("Network Exception");
            except.printStackTrace();
        }
    }
    
    private void notifyStatusListeners(String status) {
        for (Iterator<StatusListener> i = listeners.iterator(); i.hasNext();) {
            StatusListener listener = (StatusListener) i.next();
            listener.statusChanged(new StatusChangeEvent(status));
        }
    }
    
    public void writeInt(int data) {
        try {
            outDataStream.writeInt(data);
        } catch (IOException e) {
            notifyStatusListeners("Network Exception");
            e.printStackTrace();
        }
    }
    
    public int readInt() {
        
        try {
            return inDataStream.readInt();
        } catch (IOException e) {
            notifyStatusListeners("Network Exception");
            e.printStackTrace();
            
            return 0;
        }
    }
    
    public void addNotificationListener(StatusListener listener) {
        listeners.add(listener);
    }
}
