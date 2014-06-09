/**
 * 
 */
package io.net.encryption;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author nicklas-kulp
 * 
 */
public class RSATest {
    
    private RSA rsa1;
    private RSA rsa2;
    
    /**
     * Initialisiert rsa
     */
    @Before
    public void setUp() {
        rsa1 = new io.net.encryption.RSA();
        rsa2 = new io.net.encryption.RSA();
    }
    
    /**
     * Testet, ob ein entschlüsselter, verschlüsselter String äquivalent zum
     * originial ist
     */
    @Test
    public void testEncryption() {
        Message message = new Message(
                10,
                "Nilpferdschaf",
                "Rumpf");
        assertEquals("Nilpferdschaf", rsa2.decrypt(rsa1.encrypt(message, rsa2.getPublicKey())).getAuthor());
        
        String testString =
                "Dies ist ein furchtbar lange Teststring"
                        + " mit Sonderzeichen und allem drum und dran."
                        + " Ok, jetzt sind doch keine Sonderzeichen dabei,"
                        + " aber gleich: äöüßåooooooooooooooo hussa tätärätättääääh";
        message = new Message(
                100,
                "Lukas",
                testString);
        assertEquals(testString, rsa1.decrypt(rsa2.encrypt(message, rsa1.getPublicKey())).getMessage());
    }
}
