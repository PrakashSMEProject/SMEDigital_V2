/**
 * 
 */
package com.rsaame.kaizen.pas.logicoverride;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.utils.Utils;

/**
 * @author m1014644
 * 
 * This class contains the methode's that override the logic of kaizen
 *
 */

public class KaizenLogicOverride{
	
	private static String SEARCH_APP_FILTER = " AND POL_ISSUE_HOUR in("+Utils.getSingleValueAppConfig( "E_PLATFORM_APP_CODE" )+") ";
	private static String USER_ID_FILTER = " AND POL_PREPARED_BY = " ;
	private static String BROKER_PROFILE = "Broker";
	private static String SYSTEM_ERROR_FILTER = " AND POL_STATUS!= ";
	private static String STATUS_FILTER = Utils.getSingleValueAppConfig( "QUOTE_SYSTEM_ERROR" );
	/**
	 * 
	 * @param finalQuery
	 * @param profile 
	 * @param userId 
	 * @return
	 * 
	 * This method appends the condition to check for the type of application.
	 * E_PLATFROM now support only sbs
	 * More application can be configure in appconfig.properties
	 */
	public static String quoteListForSrcTranOverride(String finalQuery, Integer userID, String profile)
	{
		StringBuffer modifiedQuery = new StringBuffer(finalQuery);
		modifiedQuery.append( SEARCH_APP_FILTER );
		modifiedQuery.append( SYSTEM_ERROR_FILTER );
		modifiedQuery.append( STATUS_FILTER );
		return modifiedQuery.toString();
	}
	
	
	/**
     * This method is used to round-off big decimal numbers to 2 decimal values.
     * @param d
     * @param scale
     * @param roundUp
     * @return
     */
    public static BigDecimal round(BigDecimal d, int scale, boolean roundUp) {
    	int mode = (roundUp) ? BigDecimal.ROUND_UP : BigDecimal.ROUND_DOWN;
    	return d.setScale(scale, mode);
    }

}
