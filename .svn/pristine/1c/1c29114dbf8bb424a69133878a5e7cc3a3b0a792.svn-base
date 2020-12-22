/**
 * 
 */
package com.rsaame.pas.transaction.val;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.validation.IValidator;

/**
 * @author M1016284
 * 
 */
public class DateValidator implements IValidator{

	@Override
	public boolean validate( Object fieldData, Map<String, String> parameters, List<String> errorKeys ){

		boolean successful = true;

		if( ( fieldData != null ) && ( fieldData instanceof Date ) ){
			successful = false;
			String criteria = Utils.isEmpty( parameters.get( "criteria" ) ) ? "today" : parameters.get( "criteria" );
			
			Date theFieldData = (Date) fieldData;
			
			String inputDate = Utils.getDateAsString( theFieldData, "yyyyMMdd" );
			String todaysDate = Utils.getDateAsString( new Date(), "yyyyMMdd" );
			
			System.out.println( theFieldData );
			if( criteria.equalsIgnoreCase( "after" ) ){
				if( inputDate.compareTo( todaysDate ) > 0 ){
					successful = true;
				}
			}
			else if( criteria.equalsIgnoreCase( "before" ) ){
				if( inputDate.compareTo( todaysDate ) < 0 ){
					successful = true;
				}
			}
			else if( criteria.equalsIgnoreCase( "today" ) ){
				if( inputDate.compareTo( todaysDate ) == 0 ){
					successful = true;
				}
			}
			else if( criteria.equalsIgnoreCase( "todayOrAfter" ) ){
				if( inputDate.compareTo( todaysDate ) >= 0 ){
					successful = true;
				}
			}
			else if( criteria.equalsIgnoreCase( "todayOrBefore" ) ){
				if( inputDate.compareTo( todaysDate ) <= 0 ){
					successful = true;
				}
			}
		}
		return successful;
	}

	private static enum Criterion{
		AFTER( "after" ), BEFORE( "before" );
		
		private String crit;
		private Criterion( String crit ){ this.crit = crit; }
	}
}
