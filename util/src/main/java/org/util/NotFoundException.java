package org.util;

/**
 * Thrown when a shearched object is not found.
 *
 * @author Antoine Dutot
 * @author Yoann Pigné
 * @since 20020615
 */
public class NotFoundException
	extends RuntimeException
{
	private static final long serialVersionUID = 5089958436773409615L;

	/**
	 * Throws the message "not found".
	 */
	public
	NotFoundException()
	{
		super( "not found" );
	}

	/**
	 * Throws <code>message</code>.
	 * @param message The message to throw.
	 */
	public
	NotFoundException( String message )
	{
		super( "not found: " + message );
	}
}