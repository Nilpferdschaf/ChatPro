package io.net;

import static org.junit.Assert.assertEquals;
import io.net.encryption.Message;
import io.net.events.StatusChangeEvent;
import io.net.listeners.StatusListener;

import java.net.InetAddress;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Testet Chatserverstatus
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class ChatServerStatusTest {

    private ChatServer server1;
    private ChatServer server2;
    private StatusListener statuses1;
    private StatusListener statuses2;
    private ServerStatus status1;
    private ServerStatus status2;
    
    /**
     * setup
     * 
     * @throws Exception wenn was schief geht
     */
    @Before
    public void setUp() throws Exception {
        server1 = new ChatServer();
        server2 = new ChatServer();
        
        statuses1 = new StatusListener() {
            @Override
            public void statusChanged(StatusChangeEvent evt) {
                status1 = evt.getStatus();
            }
        };
        statuses2 = new StatusListener() {
            @Override
            public void statusChanged(StatusChangeEvent evt) {
                status2 = evt.getStatus();
            }
        };
        
        status1 = null;
        status2 = null;

        server1.addStatusListener(statuses1);
        server2.addStatusListener(statuses2);
        
        Thread.sleep(3000);
    }
    
    /**
     * teardown
     */
    @After
    public void tearDown() {
        server1.disconnect();
        server2.disconnect();
        server1 = null;
        server2 = null;
        status1 = null;
        status2 = null;
        statuses1 = null;
        statuses2 = null;
    }

    /**
     * testet Serverstatus
     * 
     * @throws Exception Wenn was schief geht
     */
    @Test
    public void testStatus() throws Exception {
        assertEquals(ServerStatus.STARTED, status1);
        assertEquals(ServerStatus.STARTED, status2);
        
        server1.connect(InetAddress.getLocalHost().getHostAddress(), 5003, 5004);
        
        Thread.sleep(1000);
        
        assertEquals(ServerStatus.LISTENING, status1);
        assertEquals(ServerStatus.STARTED, status2);
        
        server2.connect(InetAddress.getLocalHost().getHostAddress(), 5004, 5003);
        
        Thread.sleep(1000);

        assertEquals(ServerStatus.CONNECTED, status1);
        assertEquals(ServerStatus.CONNECTED, status2);
        
        server1.disconnect();
        
        assertEquals(ServerStatus.DISCONNECTED, status1);
        assertEquals(ServerStatus.CONNECTED, status2);
        
        server2.forwardMessage(new Message(0, "name", "rumpf"));
        
        assertEquals(ServerStatus.DISCONNECTED, status1);
        assertEquals(ServerStatus.DISCONNECTED, status2);
    }
}