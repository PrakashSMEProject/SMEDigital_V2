package com.rsaame.pas.b2b.ws.util;

import java.util.Arrays;
import java.util.Map;

public class BrokerDefault {


	BrokerDefault(String id, Map<String, String[]> bm1) {
		super();
		this.id = id;
		this.values = bm1;
	}
	
	public void addDefault(String field, String[] lov)
	{
		values.put(field, lov);
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("BrokerDefault [id=" + id + ", values={");
		StringBuilder valueString = new StringBuilder();
		for(Map.Entry<String, String[]> e: values.entrySet())
		{
			String k = e.getKey();
			String[] sa = e.getValue();
			valueString.append(k + "=" + Arrays.toString(sa)+",");
		}
		sb.append(valueString + "}]");
		return  sb.toString();
	}
	public String getId() {
		return id;
	}

	/**
	 * This method assumes that the field has a single default value
	 * 
	 * @param fieldId
	 * @return One default value
	 */
	public String getDefaultValue(String fieldId)
	{
		return values.get(fieldId)[0]; //expecting only one value
	}
	
	/**
	 * This method assumes that the field has a list of values for the default
	 * 
	 * @param fieldId
	 * @return List of Default values
	 */
	public String[] getDefaultValues(String fieldId)
	{
		return values.get(fieldId);
	}
	
	//Id of the broker
	private String id; 
	
	//A Map of fields to one or more values for the fields
	private Map<String, String[]> values;
	
}
