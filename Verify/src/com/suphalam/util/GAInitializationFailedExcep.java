
package com.suphalam.util;

import com.suphalam.exception.BaseException;

public class GAInitializationFailedExcep extends BaseException{
	
	 //unique identifier serial variable for object compare during serialization
	 private static final long serialVersionUID = 31072015;
	 /**
	   * Constructs a new ConfigFileLoadingFailedExcep object without a message or root cause
	   */
	  public GAInitializationFailedExcep() {
	    super();
	  }//end-of-method
	
	  /**
	   * Constructs a new ConfigFileLoadingFailedExcep object with the given message without 
	   * a root cause
	   *
	   * @param message the message String associated with this Exception
	   */
	  public GAInitializationFailedExcep(String message) {
	    super(message);
	  }//end-of-method
	
	  /**
	   * Constructs a new ConfigFileLoadingFailedExcep object with the given message and  root cause
	   *
	   * @param message the message String associated with this Exception
	   * @param rootCause the root cause of this Exception
	   */
	  public GAInitializationFailedExcep(String message, Throwable rootCause) {
	    super(message, rootCause);
	  }//end-of-method
	  
}//end-of-class
