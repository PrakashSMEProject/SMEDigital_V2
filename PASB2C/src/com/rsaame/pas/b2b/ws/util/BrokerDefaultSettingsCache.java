package com.rsaame.pas.b2b.ws.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.utils.Utils;

/**
 * 
 * This class is intended to cache Broker Default Setting Data
 * This was introduced for JLT service support to avoid DB call to get defaults
 * 
 * This will have to be changed to use in-memory EHCache or Redis in future for now
 * due to time constraints and because we have minimal data to be cached, we use class variable itself
 * caching the information
 * 
 * @author M1000147
 *
 */
//TODO check class level comments

public class BrokerDefaultSettingsCache {
	
	private final static Logger LOGGER = Logger.getLogger(BrokerDefaultSettingsCache.class);
	private boolean inite = false;
	private HashMap<String, BrokerDefault> _brokerSettings = new HashMap<String, BrokerDefault>();
	private static final String QUERY_STRING= "SELECT BR_CODE, FIELD_NAME, DEFAULTS FROM T_MAS_BROKER_DEFAULTS";

	//TODO Figure out how to load during application startup because trying to access hibernateTemplate during 
	// bean creation is throwing exception that applicationConfig.xml is not loaded
	public void load()
	{
		if(inite) {
			return;
		}
		
		//TODO temporary until we load from database
		LOGGER.info("load() method of broker defaults cache getting processed");

		HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean("hibernateTemplate");
		
		int ct = 0; //Just to count how many records loaded

		try {
		
			Session session = hibernateTemplate.getSessionFactory().openSession();
	
			Query query = session.createSQLQuery(QUERY_STRING);
			List<Object[]> results = query.list();
	
			for(Object[] row : results){
				
				ct++; 
				String id = row[0].toString(); //Id
				String f = row[1].toString(); //Field
				String v = row[2].toString(); //Values
	
				BrokerDefault bre = _brokerSettings.get(id);
				
				if(bre == null)
				{
					//If broker not already loaded create
					Map<String, String[]> bm = new HashMap<String, String[]>();
					bm.put(f, parseValues(v));
					BrokerDefault brn = new BrokerDefault(id, bm);
					_brokerSettings.put(id, brn);
				}
				else
				{
					//Add default and values to existing broker setting
					bre.addDefault(f, parseValues(v));
					}
			}
			
			inite = true;
			
			
		} catch(Exception e)
		{
			e.printStackTrace();
			LOGGER.info("Error loading broker defaults " + e);			
		}


		LOGGER.info("load() method of broker defaults cache completed loaded entries=" + ct);

	
	}
	
	private String[] parseValues(String v) {
		
		return StringUtils.split(v, ',');
	}



	/**
	 * Returns the complete defaults map for the specified Broker ID
	 * 
	 * @param id
	 * @return
	 */
	public BrokerDefault get(String brokerId) {
		
		
		load();
		
		return _brokerSettings.get(brokerId);
			
	}

	/**
	 * Returns the default for a particular field for the specified broker
	 * 
	 * @param brokerId
	 * @param fieldId
	 * @return
	 */
	public String getFieldDefault(String brokerId, String fieldId) {
		load();
		return _brokerSettings.get(brokerId).getDefaultValue(fieldId);
		
	}
	
	/**
	 * Returns the default as a list of values for a particular field for the specified broker
	 * We may not use this feature immediately 
	 * 
	 * @param brokerId
	 * @param fieldId
	 * @return
	 */
	public String[]  getFieldDefaults(String brokerId, String fieldId) {
		load();
		return _brokerSettings.get(brokerId).getDefaultValues(fieldId);
		
	}
	
}
