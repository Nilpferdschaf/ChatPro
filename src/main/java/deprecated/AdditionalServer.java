package deprecated;
import io.net.events.StatusChangeEvent;
import io.net.listeners.StatusListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class AdditionalServer extends JFrame {
    private static final long serialVersionUID = -3296348243300085686L;
    private JTextArea textWindow= new JTextArea();
    private ChatServer server;

    // the constructor
    public AdditionalServer(int port)
    {
        setTitle("Addition Sever");
        add("Center",textWindow);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,300);
        setVisible(true);
        
        server = new ChatServer(80);
        server.addNotificationListener(new StatusListener(){

            @Override
            public void statusChanged(StatusChangeEvent e) {
                textWindow.append(e.getStatus());
            }
            
        });
        textWindow.append("Listening on port "+ port +"\n");
    }


    public static void main (String args []){
        new AdditionalServer(80);
    }
}