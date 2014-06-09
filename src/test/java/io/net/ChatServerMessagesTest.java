package io.net;

import static org.junit.Assert.assertEquals;
import io.net.encryption.Message;
import io.net.listeners.MessageListener;

import java.net.InetAddress;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Testet senden/empfangen von Nachrichten
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class ChatServerMessagesTest {
    
    private ChatServer server1;
    private ChatServer server2;
    private MessageListener messages1;
    private MessageListener messages2;
    private Message tempMessage1;
    private Message tempMessage2;
    
    /**
     * setup
     * 
     * @throws Exception Wenn was schief geht
     */
    @Before
    public void setUp() throws Exception {
        server1 = new ChatServer();
        server2 = new ChatServer();
        server1.connect(InetAddress.getLocalHost().getHostAddress(), 5003, 5004);
        server2.connect(InetAddress.getLocalHost().getHostAddress(), 5004, 5003);
        
        messages1 = new MessageListener() {
            @Override
            public void transmitMassage(Message message) {
                tempMessage1 = message;
            }
        };
        messages2 = new MessageListener() {
            @Override
            public void transmitMassage(Message message) {
                tempMessage2 = message;
            }
        };
        
        tempMessage1 = null;
        tempMessage2 = null;
        
        server1.addMessageListener(messages1);
        server2.addMessageListener(messages2);
        
        Thread.sleep(3000);
    }
    
    /**
     * tearDown
     */
    @After
    public void tearDown() {
        server1.disconnect();
        server2.disconnect();
        server1 = null;
        server2 = null;
        messages1 = null;
        messages2 = null;
        tempMessage1 = null;
        tempMessage2 = null;
    }
    
    /**
     * Testet Nachrichten
     * 
     * @throws Exception Wenn was schief geht
     */
    @Test
    public void testSend() throws Exception {
        Message message;
        
        message = new Message(100, "name", "rumpf");
        server1.forwardMessage(message);
        Thread.sleep(1000);
        assertEquals(message.getMessage(), tempMessage2.getMessage());
        
        message = new Message(3900, "test2", "hallihallo");
        server2.forwardMessage(message);
        Thread.sleep(1000);
        assertEquals(message.getMessage(), tempMessage1.getMessage());
    }
}
