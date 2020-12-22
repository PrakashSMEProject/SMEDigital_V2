package com.rsaame.pas.web;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.lang.RandomStringUtils;

import com.mindtree.ruc.cmn.cache.CacheManagerFactory;
import com.rsaame.pas.cmn.cache.PASCache;

/**
 * Application Lifecycle Listener implementation class PasHttpSessionListener
 *
 */
public class PasHttpSessionListener implements HttpSessionListener{

	/**
	 * Default constructor. 
	 */
	public PasHttpSessionListener(){
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}

	/**
	 * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
	 */
	@Override
	public void sessionCreated( HttpSessionEvent arg0 ){
		String randomToken = RandomStringUtils.randomAlphanumeric( 50 );
		CacheManagerFactory.getCacheManager().put( PASCache.USER_TOKEN, arg0.getSession().getId(), randomToken );
	}

	/**
	 * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed( HttpSessionEvent arg0 ){
		if( CacheManagerFactory.getCacheManager().hasCachedData( PASCache.USER_TOKEN, arg0.getSession().getId() ) ){
			CacheManagerFactory.getCacheManager().remove( PASCache.USER_TOKEN, arg0.getSession().getId() );
		}
	}

}
