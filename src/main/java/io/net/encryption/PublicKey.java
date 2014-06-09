package io.net.encryption;

import java.math.BigInteger;

/**
 * Ein oeffentlicher Schluessel zum verschluesseln mit RSA
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class PublicKey {
    private BigInteger key1;
    private BigInteger key2;
    
    /**
     * Erstellt einen neuen PublicKey mit den gegebenen Teilschluesseln
     * 
     * @param key1 Der erste Teilschluessel
     * @param key2 Der zweite Teilschluessel
     */
    public PublicKey(BigInteger key1, BigInteger key2) {
        this.key1 = key1;
        this.key2 = key2;
    }
    
    /**
     * @return Der erste Teil des oeffentlichen Schluessels
     */
    public BigInteger getKey1() {
        return key1;
    }
    
    /**
     * @return Der zweite Teil des oeffentlichen Schluessels
     */
    public BigInteger getKey2() {
        return key2;
    }
}
