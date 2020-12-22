package com.rsaame.pas.com.helper;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.model.TTrnBuildingQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author M1020204
 *	This class is used to derive the data that need to be saved into T_TRN_BUILDING_QUO
 */
public class DeriveBuildingDetails extends BaseDervieDetails{

	private static final String BUILDING_DESCRIPTION = "BUILDINGS(Insured Location: %s,%s,%s)";
	private static final String COMMA = ",";
	
	/* (non-Javadoc)
	 * @see com.rsaame.pas.com.helper.BaseDervieDetails#updateValues(java.lang.String, com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper, com.rsaame.pas.vo.cmn.TableData, org.springframework.orm.hibernate3.HibernateTemplate, com.rsaame.pas.vo.bus.PolicyDataVO, com.rsaame.pas.vo.cmn.CommonVO)
	 * This method update the values that need to be saved to T_TRN_BUILDING_QUO
	 */
	@Override
	protected void updateValues( String tableInExecution, POJOWrapper mappedPojo, TableData<BaseVO> tableData, HibernateTemplate ht, PolicyDataVO polData, CommonVO commonVO ){

		//Getting the TTrnBuidingQuo from mappedPojo
		TTrnBuildingQuo buildingQuo = (TTrnBuildingQuo) mappedPojo;
		//Getting the current system date
		Date sysdate = (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE );
		String streetAddress = "";

		Long buildingPolicyId = null;
		// Get the bldPolicyId from the sequence based if it is a quote or policy
		buildingPolicyId = polData.getPolicyId();

		//setting the buildingPolicyId
		buildingQuo.setBldPolicyId( buildingPolicyId );

		try {
			// code to get lookup description for geocode renewal issue
			if ((!Utils.isEmpty(buildingQuo.getBldEAddress2()))
					&& (buildingQuo.getBldEAddress2().matches("[0-9]+"))) {
				buildingQuo.setBldEAddress2(SvcUtils.getLookUpDescription(
						"PAS_AREA", "ALL", "ALL",
						Integer.parseInt(buildingQuo.getBldEAddress2())));
			}

		}

		catch (Exception e) {

			System.out
					.println("DeriveBuildingDetails.java Exception while parsing the geocode as an INTEGER");
			e.printStackTrace();
		}
		
		//setting the address details
		if( !Utils.isEmpty( buildingQuo.getBldEAddress2() ) ){
			streetAddress = buildingQuo.getBldEAddress2();
			if( !Utils.isEmpty( buildingQuo.getBldAAddress2() ) ){
				streetAddress += COMMA + buildingQuo.getBldAAddress2();
			}
		}
		else{
			if( !Utils.isEmpty( buildingQuo.getBldEAddress1() ) ){
				streetAddress = buildingQuo.getBldEAddress1();
			}
		}

		buildingQuo.setBldEAddress1( buildingQuo.getBldEAddress1() );
		buildingQuo.setBldEAddress2( streetAddress );
		buildingQuo.setBldEStreetName( streetAddress );
		buildingQuo.setBldAAddress1( null );

		buildingQuo.setBldNo( Utils.getSingleValueAppConfig( "BLD_NO_A" ) );
		buildingQuo.setBldConstructionCode( Short.valueOf( Utils.getSingleValueAppConfig( "BLD_CONSTRUCTION_CODE" ) ) );
		buildingQuo.setBldZoneCode( Short.valueOf( Utils.getSingleValueAppConfig( "BLD_ZONE_CODE" ) ) );
		buildingQuo.setBldHazardCode( Short.valueOf( Utils.getSingleValueAppConfig( "BLD_HAZARD_CODE" ) ) );
		buildingQuo.setBldEndtId( Long.valueOf( Utils.getSingleValueAppConfig( "BLD_ENDT_ID" ) ) );
		buildingQuo.setBldMplFirePerc( new BigDecimal( Utils.getSingleValueAppConfig( "BLD_MPL_FIRE_PERC" ) ) );
		buildingQuo.setBldCoverIndicator( Byte.valueOf( Utils.getSingleValueAppConfig( "BLD_COVER_INDICATOR" ) ) );

		buildingQuo.setBldStartDate( polData.getScheme().getEffDate() );
		buildingQuo.setBldEndDate( polData.getScheme().getExpiryDate() );
		buildingQuo.setBldPreparedDt( sysdate );
		
		// Changes Home Revamp requirement 4.1 */
		buildingQuo.setBldTotalNoFloors(buildingQuo.getBldTotalNoFloors());
		buildingQuo.setBldTotalNoRooms(buildingQuo.getBldTotalNoRooms());
		// Changes Home Revamp requirement 4.1 */
		/*
		 * This is to fix home emirates issue 
		 */
		
		//buildingQuo.setBldDesc( String.format( BUILDING_DESCRIPTION, buildingQuo.getBldEAddress1(), buildingQuo.getBldEAddress2(),DAOUtils.getBldEAddress3Desc(buildingQuo.getBldEAddress3(),ht) ) );
		
		buildingQuo.setBldDesc( String.format( BUILDING_DESCRIPTION, buildingQuo.getBldEAddress1(), buildingQuo.getBldEAddress2(),buildingQuo.getBldEAddress3()) ) ;
		Integer dirCode = SvcUtils.getLookUpCode( "DIRECTORATE", "ALL", "ALL", "Others" );
		
		Integer geoCode = null;
		if(!Utils.isEmpty(streetAddress))
		{
			geoCode = SvcUtils.getLookUpCode( "PAS_AREA", "ALL", "ALL", streetAddress );
			geoCode = Utils.isEmpty(geoCode) ? Integer.valueOf("999") : geoCode;
			buildingQuo.setBldGeoareaCode(geoCode.shortValue());
		}
		
		Integer munCode = null;
		if(!Utils.isEmpty( dirCode )){
			munCode = getBuildingMunCode(dirCode, ht);
		}
		buildingQuo.setBldDirCode( dirCode );
		buildingQuo.setBldMunCode( munCode );

		//setting BLD_RI_RSK_CODE

		/*if( !Utils.isEmpty( buildingQuo.getBldSumInsured() ) ){

			if( buildingQuo.getBldSumInsured().longValue() <= Long.valueOf( Utils.getSingleValueAppConfig( "BLD_SUMINSURED_LIMIT" ) ) ){
				buildingQuo.setBldRiRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "BLD_RI_RISK_CODE_BELOW_LIMIT" ) ) );
			}
			else{
				buildingQuo.setBldRiRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "BLD_RI_RISK_CODE_ABOVE_LIMIT" ) ) );
			}
		}*/
		

	}

	@Override
	protected void preprocessRecord( String tableInExecution, POJOWrapper mappedPojo, POJOWrapper existingRecord, TableData<BaseVO> tableData, HibernateTemplate ht,
			PolicyDataVO polData, CommonVO commonVO, SaveCase saveCase ){
				
				//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.
		
	}
	
	private Integer getBuildingMunCode(Integer dirCode, HibernateTemplate ht){
		Integer munCode = null;
		Query query = ht.getSessionFactory().getCurrentSession().createSQLQuery( QueryConstants.FETCH_DIRECTORATE_CODES );
        query.setParameter( 0, dirCode );
		Object result = query.uniqueResult();
		
		if(!Utils.isEmpty( result )) munCode = Integer.valueOf( query.uniqueResult().toString() );
		return munCode;
	}
}