package com.rsaame.pas.customer.ui;

import java.util.Properties;

import com.rsaame.kaizen.framework.constants.AMEConstants;
import com.rsaame.kaizen.framework.util.CommonProperties;
import com.rsaame.kaizen.framework.util.PropertiesUtil;
import com.rsaame.kaizen.framework.util.StringUtil;
 class Test{


public static void main(String args[]){
	
	
	Properties props = null;
	try{
		props = PropertiesUtil.loadProperties(AMEConstants.QUERY_PROPERTIES);
	
		System.out.println("props query read "+props.getProperty( "selectCustomerQuery" ));
	}catch (Exception exception) {
		
		String query = CommonProperties
				.getQuery(
						StringUtil
								.replacePathWithPackageNotation(AMEConstants.QUERY_PROPERTIES),
						"selectCustomerQuery");
		System.out.println("query "+query);
	}
}

}