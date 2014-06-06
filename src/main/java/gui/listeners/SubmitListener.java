package gui.listeners;

import gui.events.SubmissionEvent;

import java.util.EventListener;

/**
 * Ein Beobachter, der benachrichtigt werden soll, wenn der User eine Nachricht
 * senden will
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public abstract class SubmitListener implements EventListener {
    
    /**
     * Diese Methode wird aufgerufen, wenn der User eine Nachricht senden
     * moechte
     * 
     * @param evt Informationen ueber die Nachricht
     */
    public abstract void submitPressed(SubmissionEvent evt);
}
