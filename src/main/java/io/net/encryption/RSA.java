package io.net.encryption;

import java.math.BigInteger;
import java.util.Random;

/**
 * Eine Klasse, mit deren Hilfe man Strings RSA-Verschluesseln kann. Die
 * Verschluesselung geschieht mit 1024-Bit Primzahlen in 8 Character-Blöcken
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class RSA {
    private PublicKey publicKey;
    private BigInteger privateKey;
    
    /**
     * Generiert einen public und private Key zum verschluesseln von Nachrichten
     */
    public RSA() {
        BigInteger prime1 = BigInteger.probablePrime(1024, new Random());
        BigInteger prime2 = prime1;
        while (prime1.equals(prime2)) {
            prime2 = BigInteger.probablePrime(1024, new Random());
        }
        
        BigInteger n = prime1.multiply(prime2);
        
        BigInteger z = prime1.subtract(BigInteger.ONE)
                .multiply(prime2.subtract(BigInteger.ONE));
        
        BigInteger k = z;
        while (!k.gcd(z).equals(BigInteger.ONE)) {
            k = BigInteger.probablePrime(1024, new Random());
        }
        
        publicKey = new PublicKey(k, n);
        privateKey = k.modInverse(z);
    }
    
    /**
     * Verschluesselt die gegebene Message
     * 
     * @param message Die zu verschluesselnde Message
     * @param key Der zum verschluesseln zu verwendende PublicKey
     * @return Die verschlüsselte Message
     */
    public EncryptedMessage encrypt(Message message, PublicKey key) {
        return new EncryptedMessage(
                message.getTime(),
                encryptString(message.getAuthor(), key),
                encryptString(message.getMessage(), key));
    }
    
    /**
     * Entschluesselt die gegebene EncryptedMessage
     * 
     * @param message Die zu dechiffrierende EncryptedMessage
     * @return Die entschluesselte Message
     */
    public Message decrypt(EncryptedMessage message) {
        return new Message(
                message.getTime(),
                decryptString(message.getAuthor()),
                decryptString(message.getMessage()));
    }
    
    /**
     * @return Den PublicKey zum verschluesseln fremder Nachrichten
     */
    public PublicKey getPublicKey() {
        return publicKey;
    }
    
    private BigInteger[] encryptString(String message, PublicKey key) {
        String[] chopped = chopString(message, 8);
        BigInteger[] encrypted = new BigInteger[chopped.length];
        
        for (int i = 0; i < encrypted.length; i++) {
            encrypted[i] =
                    assembleBigInt(chopped[i].toCharArray()).modPow(key.getKey1(), key.getKey2());
        }
        
        return encrypted;
    }
    
    private String decryptString(BigInteger[] encrypted) {
        String[] decrypted = new String[encrypted.length];
        
        for (int i = 0; i < decrypted.length; i++) {
            decrypted[i] =
                    new String(splitBigInt(encrypted[i].modPow(privateKey, publicKey.getKey2())));
        }
        
        return assembleString(decrypted);
    }
    
    private String[] chopString(String text, int partitionSize) {
        int length = (int) Math.ceil(((double) text.length()) / partitionSize);
        String[] subTextInts = new String[length];
        
        for (int i = 0; i < length - 1; i++) {
            subTextInts[i] =
                    text.substring(i * partitionSize, (i + 1) * partitionSize);
        }
        subTextInts[length - 1] = text.substring(partitionSize * (length - 1));
        
        return subTextInts;
    }
    
    private String assembleString(String[] chopped) {
        StringBuilder sb = new StringBuilder();
        for (String string : chopped) {
            sb.append(string);
        }
        return sb.toString();
    }
    
    private BigInteger assembleBigInt(char[] characters) {
        BigInteger assembledInt = BigInteger.ZERO;
        
        for (int i = 0; i < characters.length; i++) {
            assembledInt =
                    assembledInt.shiftLeft(32).or(charToBigInt(characters[i]));
        }
        
        return assembledInt;
    }
    
    private char[] splitBigInt(BigInteger assembled) {
        BigInteger unsplitChars = assembled;
        char[] characters = new char[1 + assembled.bitLength() / 32];
        
        for (int i = 0; i < characters.length; i++) {
            BigInteger temp = unsplitChars.shiftRight(32);
            characters[characters.length - 1 - i] =
                    bigIntToChar(
                    unsplitChars.xor(temp.shiftLeft(32))
                    );
            unsplitChars = temp;
        }
        
        return characters;
    }
    
    private BigInteger charToBigInt(char character) {
        return new BigInteger(new StringBuilder().append((int) character).toString());
    }
    
    private char bigIntToChar(BigInteger bigInt) {
        return (char) bigInt.intValue();
    }
}
