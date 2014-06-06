package gui.events;

/**
 * Enthaelt Informationen über eine vom User gesendete Nachricht.
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class SubmissionEvent {
    private String submissionText;
    
    /**
     * Erzeugt ein neues SubmissionEvent mit der gegebenen Nachricht
     * 
     * @param submissionText Die Nachricht, die der User senden moechte
     */
    public SubmissionEvent(String submissionText) {
        this.submissionText = submissionText;
    }
    
    /**
     * @return Die Nachricht, die der User senden moechte
     */
    public String getMessage() {
        return submissionText;
    }
}
