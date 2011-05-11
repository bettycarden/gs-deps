package org.util;


/**
 * A simple static class that gives a static method {@link #next()} that returns
 * a randomly generated double value. The advantage of this class in comparison
 * to the Math.random() method is that when the first call to the next() method
 * is made, the object initialises itself with the value of the "ranbomSeed" key
 * if it exists. The Seed can then be reinitialised with its previous value or
 * with another value.
 * 
 * @author Yoann Pigné
 */
public class Random
{
	public static java.util.Random random;

	static int seed = 1239945;

	/**
	 * Static method that returns a random double value between 0 and 1. A
	 * static <code>java.util.Random</code> is created and eventually
	 * initialised with the \"randomSeed\" key in the default
	 * {@link Environment} table.
	 * @return A random double between 0 and 1.
	 */
	public static double next()
	{
		testRandom();
		return random.nextDouble();
	}

	/**
	 * Forces the random object to be reinitialised with the given seed.
	 * @param seed ...
	 */
	public static void reinit( int seed )
	{
		Environment.getGlobalEnvironment().setParameter( "randomSeed",
				"" + seed );
		random = null;
		testRandom();
	}

	/**
	 * Forces the random object to be reinitialised with the same seed.
	 */
	public static void reinit()
	{
		Environment.getGlobalEnvironment().setParameter( "randomSeed",
				"" + seed );
		random = null;
		testRandom();
	}

	/**
	 * Creates a random object if it does not exist and tries to initialise it
	 * with the \"randomSeed\" key in the default environment.
	 */
	private static void testRandom()
	{
		if( random == null )
		{

			String randomSeed = Environment.getGlobalEnvironment()
					.getParameter( "randomSeed" );
			if( randomSeed != null && !randomSeed.equals( "" ) )
			{
				seed = ( new Integer( randomSeed ) ).intValue();
			}
			random = new java.util.Random( seed );
		}
	}
}
