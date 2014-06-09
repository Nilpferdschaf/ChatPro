package gui;

import gui.listeners.ConnectionListener;
import io.net.ChatMember;
import io.net.encryption.Message;
import io.net.listeners.MessageListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

/**
 * Der JFrame, der zur Interaktion mit dem User dient.
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class ChatFrame extends JFrame implements ChatGui, ChatMember {
    
    private static final long serialVersionUID = 4688472858038683926L;
    private JPanel contentPane;
    
    private ConnectionPanel connectionPanel;
    private StatusPanel statusPanel;
    private MessagePanel messagePanel;
    
    /**
     * Create the frame.
     */
    public ChatFrame() {
        initComponents();
    }
    
    @Override
    public void setStatus(String status) {
        statusPanel.setStatus(status);
    }
    
    @Override
    public void addMessageListener(MessageListener listener) {
        messagePanel.addMessageListener(listener);
    }
    
    @Override
    public void addConnectionListener(ConnectionListener listener) {
        connectionPanel.addConnectionListener(listener);
    }
    
    @Override
    public boolean forwardMessage(Message message) {
        messagePanel.printMessage(message);
        return true;
    }
    
    private void initComponents() {
        setTitle("Chat Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 750, 468);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        connectionPanel = new ConnectionPanel();
        statusPanel = new StatusPanel();
        messagePanel = new MessagePanel();
        
        GroupLayout glContentPane = new GroupLayout(contentPane);
        glContentPane.setHorizontalGroup(
            glContentPane.createParallelGroup(Alignment.LEADING)
                .addComponent(connectionPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
                .addComponent(statusPanel, GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
                .addComponent(messagePanel, GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
        );
        glContentPane.setVerticalGroup(
            glContentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(glContentPane.createSequentialGroup()
                    .addComponent(connectionPanel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(statusPanel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(messagePanel, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE))
        );
        
        contentPane.setLayout(glContentPane);
    }
}