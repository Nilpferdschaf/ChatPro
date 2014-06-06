package main;

/**
 * Die Main-Klasse, die das Programm startet
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public final class Main {
    
    private Main() {
    }
    
    /**
     * Erzeugt einen neuen ClientController
     * 
     * @param args Konsolenargumente
     */
    public static void main(String[] args) {
        new ClientController();
    }
}
