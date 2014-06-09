package io.net.encryption;

import java.math.BigInteger;

/**
 * Eine verschluesselte Nachricht
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class EncryptedMessage {

    private long time;
    private BigInteger[] author;
    private BigInteger[] message;
    
    /**
     * Erzeugt eine neue verschluesselte Nachricht
     * 
     * @param time Der Sendezeitpunkt
     * @param author Der verschluesselte Autor der Nachricht
     * @param message Die verschluesselte Nachrichtentext
     */
    public EncryptedMessage(long time, BigInteger[] author, BigInteger[] message) {
        this.time = time;
        this.author = author;
        this.message = message;
    }
    
    /**
     * @return Der Sendezeitpunkt
     */
    public long getTime() {
        return time;
    }

    /**
     * @return Der Autor der Nachricht
     */
    public BigInteger[] getAuthor() {
        return author;
    }

    /**
     * @return Der Nachrichtentext
     */
    public BigInteger[] getMessage() {
        return message;
    }
}
