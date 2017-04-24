package de.immoPiraten;

/* Describes exceptions thrown by a called API. For more details step into deeper exceptions. */
public class APIException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public APIException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
