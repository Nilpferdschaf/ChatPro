package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Eine Klasse zum Vereinfachten Schreiben und Auslesen von Dateien
 * 
 * @author nicklas-kulp
 *
 */
public class FileIOAdapter {
    
    private File f;
    
    /**
     * Erzeugt einen neuen FileIOAdapter, der die angegebene Datei Lesen/Schreiben soll
     * 
     * @param f Die Datei mit der gearbeitet wird
     */
    public FileIOAdapter(File f) {
        this.f = f;
    }
    
    /**
     * Schreibt den gegebenen String in die Datei
     * 
     * @param content Der Inhalt der Datei
     */
    public void write(String content) {
        FileWriter fw;
        BufferedWriter bw;
        try {
            fw = new FileWriter(f);
            bw = new BufferedWriter(fw);
            
            fw.write(content);
            
            fw.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @return Den Inhalt der Datei
     */
    public String read() {
        String content = "";
        
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            
            String newline = br.readLine();
            while ((newline = br.readLine()) != null) {
                content += "\n" + newline;
            }
            
            fr.close();
            br.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return content;
    }
}
