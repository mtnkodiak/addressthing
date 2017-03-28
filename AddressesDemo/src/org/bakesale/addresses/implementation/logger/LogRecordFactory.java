package org.bakesale.addresses.implementation.logger;

import java.util.logging.LogRecord;

import org.bakesale.addresses.contract.logger.CJLogger;

import java.util.logging.Level;


public class LogRecordFactory
{

	public LogRecord createLogRecord(Level level, String message) 
	{		
		LogRecord logRecord = new LogRecord(level, message);
		
		StackTraceElement frame = walkStackForCallingMethod();
		logRecord.setSourceClassName(frame.getClassName());
		logRecord.setSourceMethodName(frame.getMethodName());
		logRecord.setLoggerName(CJLoggerImpl.LOGGER_NAME);

		return logRecord;
	}
	
	public LogRecord createLogRecord(Level level, Exception event) 
	{	
		LogRecord logRecord = this.createLogRecord( level, event.getMessage() );
		logRecord.setThrown( event );
		return logRecord;
	}

	/**
	 * 
	 * Walk the stack instead of hard coding the number of frames up to the logging class
	 * Then get the information about the calling class
	 */
	private StackTraceElement walkStackForCallingMethod() 
	{
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		StackTraceElement frame = stack[0];
		
		// Skip over the logging overhead frames
		for (StackTraceElement frameInStack : stack)
		{
			if (!(isFrameInThisClass(frameInStack)    || 
				  isFrameInLoggerClass(frameInStack)  ||
				  isFrameGetStackTrace(frameInStack)))
			{
				frame = frameInStack;
				break;
			}
		}
		
		return frame;
	}

	/**
	 * Check if this frame is from the getStackTrace call
	 * 
	 * @param frameInStack
	 * @return true if the frame is from the getStackTrace call
	 */
	private boolean isFrameGetStackTrace(StackTraceElement frameInStack) 
	{
		return frameInStack.getMethodName().equals("getStackTrace");
	}

	/**
	 * Check if this frame is from this class
	 * 
	 * @param frameInStack
	 * @return true if the stack frame is from this class
	 */
	private boolean isFrameInLoggerClass(StackTraceElement frameInStack) 
	{
		return frameInStack.getClassName().contains(CJLogger.class.getSimpleName());
	}
	
	/**
	 * Check if this frame is from the logger class
	 * 
	 * @param frameInStack
	 * @return true if the stack frame is from this class
	 */
	private boolean isFrameInThisClass(StackTraceElement frameInStack)
	{
		String stackFrameClass = frameInStack.getClassName();
		String thisClass = getClass().getName();
		
		return stackFrameClass.equals(thisClass);
	}
}
