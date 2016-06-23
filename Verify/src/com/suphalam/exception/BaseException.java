
package com.suphalam.exception;

/**
 * <PRE> 
 * Application name : Analytics
 * Version          : 1.0  
 * Class name       : BaseException
 * Description      : This the parent run time exception and extending the throwable class
 *                  : run time exception and this class will be extended by each base exception
 *                  : in the application
 * Package name     : com.subhalam.exception
 * Author           : H Roy</PRE>
 */
public class BaseException extends RuntimeException {

	//unique identifier serial variable for object compare during serialization
	private static final long serialVersionUID = 31072014;
	
	//The message string associated with this exception
    protected String message;

    //The root cause of this exception
    protected Throwable rootCause;
	

	/**
	 * Constructs a new BaseException object without a message or root cause
	*/
	 public BaseException() {
		 message = "";			//setting the message code as null
	     rootCause = null;		//setting the root cause exception as null
	 }

	 /**
	  * Constructs a new BaseException object with the given message without 
	  * a root cause
	  *
	  * @param message the message String associated with this Exception
	  */
	 public BaseException(String message) {
		 this.message = message;			//setting the message code 
	     rootCause = null;					//setting the root cause exception as null
	 }

	 /**
	  * Constructs a new BaseException object with the given message and  
	  * root cause
	  *
	  * @param message the message String associated with this Exception
	  * @param rootCause the root cause of this Exception
	  */
	 public BaseException(String message, Throwable rootCause) {
		 this.message = message;			//setting the message code 
	     this.rootCause = rootCause;		//setting the root cause exception
	 }//end
	 
	/**
	 * Sets the root cause of this exception
	 *
	 * @param rootCause the root cause of this BaseException
	 */
	public void setRootCause(Throwable rootCause) {
	    this.rootCause = rootCause;
	}
    /**
     * Gets the stack trace of this BaseException
     *
     * @return a String object containing the stack trace
     */
    public String getMessage() {

    	
    	
        String stackTrace = this.getClass().getName()+" : ";					//get the class name for the exception
        stackTrace+= message+"\n";												//add a new line
        //check if root cause is not null, if not then add the root cause by iterating it
        //till last root cause and then print it.
        if (rootCause != null) {
            if (rootCause instanceof BaseException) {
                stackTrace += "Caused By : "+rootCause.getMessage();
            }
            else {
                stackTrace += "Caused By : "+rootCause.getClass().getName() 
                                    +   " : " +rootCause.getMessage();
            }
        }
        return (stackTrace);
    }
    /**
     * Gets the stack trace of this BaseException
     *
     * @return a String object containing the stack trace
     */
    public String getSimplMessage() {
    	
        return message;
    }
} 

