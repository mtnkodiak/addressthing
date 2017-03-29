package org.bakesale.addresses.contract.logger;

public interface CJLogger {

	void initialize(String logPath);
	
    /**
     * Log error event.
     * 
     * param event      Error Event.
     */
    void error(Exception event);
    
    /**
     * Log warning event.
     * 
     * param event      Warning Event.
     */
    void warning(Exception event);
    
    /**
     * Log informational event.
     * 
     * param event      Info Event.
     */
    void information(Exception event);
   
    /**
     * Trace informational event.
     * 
     * param event      Trace Event.
     */
    void trace(Exception event);
    
    /**
     * Log an error message
     * 
     * param message       the message
     */
    void error(String message);
    
    /**
     * Log a warning message
     * 
     * param message       the message
     */
    void warning(String message);
    
    /**
     * Log an informational message
     * 
     * param message       the message
     */
    void information(String message);
    
    /**
     * Trace informational message.
     * 
     * param event      the message
     */
    void trace(String message);

}
