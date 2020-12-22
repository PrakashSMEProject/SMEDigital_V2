package com.rsaame.pas.cmn.context;

import java.util.Set;

import com.mindtree.ruc.cmn.utils.Map;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * This is a ThreadLocal-based context API which can be used to manage request-level values for processing within the scope of a single
 * thread. If this is being used in an application server environment, the user application should ensure that the values are cleared 
 * after each request is processed, since the same thread will process another request, may be for a different user, next time.
 */
public class ThreadLevelContext{
	private static java.util.Map<String, ThreadLocal> contextMap = new Map<String, ThreadLocal>( String.class, ThreadLocal.class );
	
	/**
	 * Sets the passed <code>value</code> against the passed <code>key</code> in this thread's context.
	 * @param key
	 * @param value
	 */
	public static void set( String key, Object value ){
		ThreadLocal<Object> t = contextMap.get( key );
		if( Utils.isEmpty( t ) ){
			t = new ThreadLocal<Object>();
			contextMap.put( key, t );
		}
		
		t.set( value );
	}
	
	/**
	 * Returns the value available in this thread's context against the passed <code>key</code>.
	 * @param key
	 * @return
	 */
	public static Object get( String key ){
		return Utils.isEmpty( contextMap.get( key ) ) ? null : contextMap.get( key ).get();
	}
	
	/**
	 * Clears the entry available against the passed <code>key</code> in this thread's context.
	 * @param key
	 */
	public static void clear( String key ){
		if( !Utils.isEmpty( contextMap.get( key ) ) ){
			contextMap.get( key ).remove();
		}
	}
	
	/**
	 * Clears all entries available in this thread's context.
	 */
	public static void clearAll(){
		if( !Utils.isEmpty( contextMap ) ){
			Set<java.util.Map.Entry<String, ThreadLocal>> entries = contextMap.entrySet();
			
			for( java.util.Map.Entry<String, ThreadLocal> entry : entries ){
				contextMap.get( entry.getKey() ).remove();
			}
		}
	}
}
