package org.bakesale.addresses.implementation.logger;

import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class CJLogFormatter extends Formatter {
		private static final String NAMESPACE = "org.bakesale";

	    // 02/18/16-18:50:55.116
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat ("MM/dd/yy-HH:mm:ss.SSS");
		
	    // [13] [02/18/16-18:50:55.116] [VASA    ]  INFO  [IB301006] {com.hitachi.vp.state.VpProviderInstanceImpl::initializeLog}:Logger initialization is complete.
		// thread      date+time      always VASA  level    code         class:method                                               message
		private static final String LOG_ENTRY_FORMAT = "[%1$s] [%2$s] %3$s {%4$s::%5$s}:%6$s\n";

	    // Date time on the log statement in UTC to the millisecond
	    // Class Name (short version) of the logging event
	    // Method Name of the logging event 
	    // Thread id of the logging event
	    // Line number of the logging event
	    // message
	    		
	    @Override
		public String format(LogRecord record) 
	    {
			// Ensure all strings are not null
	    	// The first 8 characters of the message are always the greppable event code
			String message    = record.getMessage().substring(8,record.getMessage().length());
	    	String className  = record.getSourceClassName() != null ? record.getSourceClassName() : "";
	    	String methodName = record.getSourceMethodName() != null ? record.getSourceMethodName() : "";
	    	String level      = this.mapLevelName( record.getLevel() );
	    	String threadId   = "" + record.getThreadID();
	    	
	    	String logEntry = String.format(LOG_ENTRY_FORMAT,
	    			                 threadId,
	    							 dateTimeFormat.format(record.getMillis()),
	    							 level, 
	    			                 className, 
	    			                 methodName,
	    			                 message);
	    	
	        return logEntry;
	    }

	    
	    /* return only supported/expected strings - also pad to 6 characters */
	    private String mapLevelName( Level level )
	    {
	    	String retVal = "      ";
	    	
	    	if( level!=null )
	    	{
	    		if( level.getName()!=null )
	    		{
	    			if( level==Level.SEVERE )
	    			{
	    				retVal = " ERROR";
	    			}
	    			else if( level==Level.WARNING )
	    			{
	    				retVal = "  WARN";
	    			}
	    			else if( level==Level.INFO )
	    			{
	    				retVal = "  INFO";
	    			}
	    			else if( level==Level.FINE || level==Level.FINER || level==Level.FINEST )
	    			{
	    				retVal = " TRACE";
	    			}
	    		}
	    	}
	    	
	    	return retVal;
	    }
	}
