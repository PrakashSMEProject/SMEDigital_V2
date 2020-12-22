package com.rsaame.pas.web;

import net.sf.ehcache.Element;

import com.mindtree.ruc.cmn.cache.EhcacheManager;
import com.mindtree.ruc.cmn.cache.ICache;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.cache.PASCache;
import com.rsaame.pas.util.AppConstants;

public class PASCacheManager extends EhcacheManager {
	
	@Override
	public Object get( ICache cache, String key ){
		
		//if not INSURED and USER_TOKEN category
		if(!PASCache.INSURED.equals(cache) && !PASCache.USER_TOKEN.equals(cache)){
			key = Utils.concat(key,AppConstants.DELIMITER,Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));
		}
		
		Element element = getCache( cache ).get( key );
		if( element != null ){
			return element.getValue();
		}
		return null;
	}

	@Override
	public void put( ICache cache, String key, final Object value ){
		
		//if not INSURED and USER_TOKEN category
		if(!PASCache.INSURED.equals(cache) && !PASCache.USER_TOKEN.equals(cache)){
				key = Utils.concat(key,AppConstants.DELIMITER,Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));
			}
				
		getCache( cache ).put( new Element( key, value ) );
	}
}
