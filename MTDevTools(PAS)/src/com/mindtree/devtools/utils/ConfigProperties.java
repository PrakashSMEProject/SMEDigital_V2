package com.mindtree.devtools.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.mindtree.ruc.cmn.exception.SystemException;

public class ConfigProperties implements Serializable{
	private static final long serialVersionUID = 1L;
	private Map<String, String> properties;

	public ConfigProperties(){
		this.properties = new HashMap();
	}

	public Map<String, String> getProperties(){
		return this.properties;
	}

	public String getValue( String key ){
		if( DevToolsUtils.isEmpty( key ) ){
			throw new SystemException( "", null, "Key is empty" );
		}
		return (String) this.properties.get( key );
	}

	public void putValue( String key, String value ){
		if( DevToolsUtils.isEmpty( key ) ){
			throw new SystemException( "", null, "Key is empty" );
		}

		this.properties.put( key, value );
	}

}
