package org.util.set;


/**
 * long sequence.
 *
 * @author Antoine Dutot
 * @since 19980804
 * @version 0.1
 */
public class LongArray
{
// Attributes

	/**
	 * LongArray of #cap cells.
	 */
	protected long[] beg;
	
	/**
	 * Number of used cells (always &lt; #cap).
	 */
	protected int pte;
	
	/**
	 * Number of allocated cells.
	 */
	protected int cap;

// Creation
	
	/**
	 * New array with an initial capacity of four items.
	 */
	public
	LongArray()
	{
		this( 4 );
	}
	
	/**
	 * New array with initial_capacity and zero count. If the capacity of the
	 * previous build was sufficient it is kept. The minimum initial_capacity
	 * is eight, under this, it is reset to this number. Note that the array
	 * #isEmpty(), you must use #set_count() to explicitly size it.
	 */
	public
	LongArray( int initial_capacity )
	{
		if( initial_capacity < 1 )
		{
			initial_capacity = 1;
		}
	
		if( invariant() && capacity() >= initial_capacity )
		{
			pte = 0;
		}
		else
		{
			allocate( initial_capacity, false );
		}
	}
	
	/**
	 * New copy of other. DEPRECATED: use #clone().
	 */
	public
	LongArray( LongArray other )
	{
		int n;
	
		if( other.isEmpty() )
		{
			allocate( 8, false );
		}
		else
		{
			if( ! invariant() || capacity() < other.size() )
			{
				allocate( other.size(), false );
			}

			n = other.size();

			for( int i = 0; i < n; ++i )
			{
				beg[i] = other.beg[i];
			}
			
			pte =  other.pte;
		}
	}

// Predicates
	
	public boolean
	invariant()
	{
		return( beg != null );
	}
	
	/**
	 * Cell count is zero? capacity can still be greater than zero.
	 */
	public boolean
	isEmpty()
	{
		return( beg != null && pte == 0 );
	}
	
	/**
	 * Does index points inside this?
	 */
	public boolean
	isIndexValid( int index )
	{
		return( index >= 0 && index < pte );
	}

// Accessors -- size
	
	/**
	 * Number of used cells.
	 */
	public int
	getCount()
	{
		return pte;
	}

	/**
	 * Number of used cells.
	 */
	public int
	size()
	{
		return pte;
	}
	
	/**
	 * Capacity of the array before a reallocation is needed.
	 */
	public int
	getCapacity()
	{
		return cap;
	}
	
	/**
	 * Capacity of the array before a reallocation is needed.
	 */
	public int
	capacity()
	{
		return cap;
	}
		
// Accessors -- contents
	
	/**
	 * i-th item.
	 */
	public long
	get( int i )
		throws InvalidIndexException
	{
		try
		{
			return beg[i];
		}
		catch( ArrayIndexOutOfBoundsException e )
		{
			throw new InvalidIndexException( i );
		}
	}
	
	/**
	 * i-th item.
	 */
	public long
	item( int i )
		throws InvalidIndexException
	{
		try
		{
			return beg[i];
		}
		catch( ArrayIndexOutOfBoundsException e )
		{
			throw new InvalidIndexException( i );
		}
	}
	
	/**
	 * First item.
	 * requires(not empty): ! #isEmpty()
	 */
	public long
	getFront()
		throws InvalidIndexException
	{
		if( isEmpty() )
		{
			throw new InvalidIndexException(
				"cannot get the front of an empty array", 0 );
		}
		
		return beg[0];
	}

	/**
	 * First item.
	 * requires(not empty): ! #isEmpty()
	 */
	public long
	front()
		throws InvalidIndexException
	{
		if( isEmpty() )
		{
			throw new InvalidIndexException(
				"cannot get the front of an empty array", 0 );
		}
		
		return beg[0];
	}
	
	/**
	 * Last item.
	 * requires(not empty): ! #isEmpty()
	 */
	public long
	getBack()
		throws InvalidIndexException
	{
		if( isEmpty() )
		{
			throw new InvalidIndexException(
				"cannot get the back of an empty array", pte - 1 );
		}
		
		return  beg[pte - 1];
	}
	
	/**
	 * Last item.
	 * requires(not empty): ! #isEmpty()
	 */
	public long
	back()
		throws InvalidIndexException
	{
		if( isEmpty() )
		{
			throw new InvalidIndexException(
				"cannot get the back of an empty array", pte - 1 );
		}
		
		return  beg[pte - 1];
	}

	/**
	 * Internal representation. The returned data is final and valid only until
	 * another method of the array is used(since most methods can reallocate
	 * the representation). The use of this method is strongly discouraged, it
	 * is useful only when speed is critical.
	 */
	public final long[]
	getData()
	{
		return beg;
	}
	
	/**
	 * Internal representation. The returned data is final and valid only until
	 * another method of the array is used(since most methods can reallocate
	 * the representation). The use of this method is strongly discouraged, it
	 * is useful only when speed is critical.
	 */
	public final long[]
	data()
	{
		return beg;
	}

	/**
	 * Increment the element at i by value.
	 * @param i Index of the element.
	 * @param value The value to add.
	 */
	public void
	incr( int i, long value )
	{
		if( i < 0 || i >= pte )
			throw new InvalidIndexException( i );

		beg[i] += value;
	}

	/**
	 * Multiply the element at i by value.
	 * @param i Index of the element.
	 * @param value The multiplier.
	 */
	public void
	mult( int i, long value )
	{
		if( i < 0 || i >= pte )
			throw new InvalidIndexException( i );

		beg[i] *= value;
	}

// Commands -- size
	
	/**
	 * Ensure the array has at least ensured_capacity. If the actual capacity
	 * is less than ensured_capacity, the maximum of ensured_capacity or twice
	 * the old capacity is used. Else nothing is done. The old content is kept.
	 */
	public void
	reserve( int ensured_capacity )
	{
		int new_cap;
	
		if( cap < ensured_capacity )
		{
			new_cap = ( cap << 1 );

			if( new_cap < ensured_capacity )
			{
				new_cap = ensured_capacity;
			}
		
			allocate( new_cap, true );
		}
	}
	
	/**
	 * Delete any unused cell(make the capacity the same as the size). If count
	 * is zero(#isEmpty() holds) this call as make an array of capacity one.
	 */
	public void
	trim()
	{
		if( invariant() )
		{
			if( pte == 0 )
			{
				allocate( pte + 1, true );
			}
			else
			{
				allocate( pte, true );
			}
		}
	}
	
	/**
	 * Change the count to new_count. The new_count can be zero.
	 */
	public void
	setCount( int new_count )
	{
		reserve( new_count );

		pte = new_count;
	}

// Commands -- on some part of the array	

	/**
	 * Set the i-th item to value.
	 */
	public void
	set( int i, long value )
		throws InvalidIndexException
	{
		if( i < 0 || i >= pte )
		{
			throw new InvalidIndexException( i );
		}
		
		beg[i] = value;
	}

	/**
	 * Set the i-th item to value.
	 */
	public void
	setItem( int i, long value )
		throws InvalidIndexException
	{
		if( i < 0 || i >= pte )
		{
			throw new InvalidIndexException( i );
		}
		
		beg[i] = value;
	}

	/**
	 * Append value at the end. If capacity is reached, capacity is enlarged by
	 * a factor of two.
	 */
	public void
	add( long value )
	{
		if( pte == cap )
		{
			if( pte == 0 )
			{
				reserve( 1 );
			}
			else
			{
				reserve( cap << 1 );
			}
		}

		beg[pte] = value;

		++ pte;
	}

	public void
	add( long values[], int offset, int length )
	{
		if( cap - pte < length )
		{
			if( pte == 0 )
			{
				reserve( length );
			}
			else
			{
				if( ( cap << 1 ) > ( pte + length ) )
				     reserve( cap << 1 );
				else reserve( pte + length );
			}
		}

		int end = pte + length;

		for( int i=pte, j=offset; i<end; ++i, ++j )
			beg[i] = values[j];

		pte = end;
	}

	/**
	 * Append value at the end. If capacity is reached, capacity is enlarged by
	 * a factor of two.
	 */
	public void
	putBack( long value )
	{
		add( value );
	}

	/**
	 * Remove item at the end.
	 */
	public void
	removeBack()
	{
		-- pte;
	}

	/**
	 * Remove item at the end.
	 */
	public void
	pruneBack()
	{
		removeBack();
	}

	/**
	 * Insert the value before the i-th cell.
	 */
	public void
	put( long value, int i )
		throws InvalidIndexException
	{
		if( i >= pte )
		{
			throw new InvalidIndexException( i );
		}
	
		if( pte > 0 )
		{
			if( pte == cap )
			{
				reserve( cap << 1 );
			}

			for( int j = pte; j > i; --j )
			{
				beg[j] = beg[j - 1];
			}
			
			beg[i] = value;

			++ pte;
		}
		else
		{
			putBack( value );
		}
	}
	
	/**
	 * Remove the i-th item.
	 */
	public void
	prune( int i )
	{
		int n;

		n = pte - 1;
	
		for( int j = i; j < n; ++j )
		{
			beg[j] = beg[j + 1];
		}
			
		-- pte;
	}

// Commands -- On the whole array
	
	/**
	 * Exchange the contents of other with this in O(1).
	 */
	public void
	swap( LongArray other )
	{
		if( other == this )
		{
			return;
		}

		long[] t1;
		int t2;

		t1        = beg;
		beg       = other.beg;
		other.beg = t1;

		t2        = pte;
		pte       = other.pte;
		other.pte = t2;

		t2        = cap;
		cap       = other.cap;
		other.cap = t2;
	}

// Commands -- Utility
	
	/**
	 * Allocate count cells of memory. The memory is always allocated even if
	 * capacity is greater than(or even equal to) count. If keep is true and
	 * there is an old content, it will be kept as much as possible.
	 *
	 * #beg, #pte and #cap are reset. If keep is true, #pte points after the
	 * last kept cell, else #pte is zero.
	 *
	 * The invariant always holds after this method.
	 */
	protected void
	allocate( int count, boolean keep )
	{
		long[] new_beg;
		int      old_count;
		
		new_beg = new long [ count ];
	
		if( keep && beg != null )
		{
			old_count = pte;

			if( old_count > count )
			{
				old_count = count;
			}

			for( int i = 0; i < old_count; ++i )
			{
				new_beg[i] = beg[i];
			}

			beg = new_beg;
			pte = old_count;
			cap = count;
		}
		else
		{
			beg = new_beg;
			pte = 0;
			cap = count;
		}
	}
}

// vim:ts=4:ai:
