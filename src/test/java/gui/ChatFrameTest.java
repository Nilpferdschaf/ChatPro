package gui;

import io.net.encryption.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Testet die GUI
 * 
 * @author nicklas-kulp
 * @version 1.0 
 */
public class ChatFrameTest {
    
    ChatFrame ui;
    
    /**
     * setup
     */
    @Before
    public void setUp() {
        ui = new ChatFrame();
    }
    
    /**
     * tearDown
     */
    @After
    public void tearDown() {
        ui.dispose();
    }
    
    /**
     * testet ein paar setter
     */
    @Test
    public void test() {
        ui.setStatus("HI");
        ui.forwardMessage(new Message(100, "name", "rumpf"));
    }
    
}
