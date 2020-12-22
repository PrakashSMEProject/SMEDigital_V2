package com.rsaame.pas.cmn.context;

import java.util.Set;

import com.mindtree.ruc.cmn.utils.Map;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * This is a ThreadLocal-based context API which can be used to manage request-level values for processing within the scope of a single
 * thread. If this is being used in an application server environment, the user application should ensure that the values are cleared 
 * after each request is processed, since the same thread will process another request, may be for a different user, next time.
 */
public class ThreadLocationContext{
	public static final ThreadLocal userThreadLocal = new ThreadLocal();

	public static void set(String user) {
		userThreadLocal.set(user);
	}

	public static void unset() {
		userThreadLocal.remove();
	}

	public static String get() {
		return (String) userThreadLocal.get();
	}

}
