package com.rsaame.pas.dao.cmn;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.Query;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.dao.model.TTrnBuildingQuo;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnSectionDetailsQuo;
import com.rsaame.pas.dao.model.TTrnSectionLocationQuo;
import com.rsaame.pas.dao.model.TTrnSectionLocationQuoId;
import com.rsaame.pas.dao.model.TTrnWctplPremiseQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.TableSnapshotLevel;
import com.rsaame.pas.dao.utils.TableSnapshotQueryHelper;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;

public abstract class BaseSectionLoadDAO extends BaseDBDAO implements ISectionLoadDAO{
	private static final Logger LOGGER = Logger.getLogger( BaseSectionLoadDAO.class );
	protected DecimalFormat decForm = new DecimalFormat( SvcConstants.DECIMAL_FORMAT );
	 //Added for Bahrain 3 decimal - Starts
	protected final static DecimalFormat decFormBahrain = new DecimalFormat(SvcConstants.DECIMAL_FORMAT_BAHRAIN);
		 //Added for Bahrain 3 decimal - Ends
	private static final int ZERO_VAL = 0;

	/**
	 * Common method to load the data for an existing quote or policy. This method is section
	 * agnostic and expects that the child implementations of this class are aware of the sections they
	 * are for and their concrete methods return values specific to them.
	 */
	@Override
	public BaseVO loadExistingData( BaseVO input ){
		LoadExistingInputVO lei = (LoadExistingInputVO) input;
		Long policyLinkId = lei.getPolLinkingId();
		Long endId = lei.getEndtId();
		/* Get the policy Id for the section. */
		Long policyId = null;
		Long policyIdForLocationsList = null;
		if( lei.getSectionId() != 0 ){
			policyId = this.getPolicyId( policyLinkId, endId, lei );

		}
		if( Utils.isEmpty( policyId ) && lei.getSectionId() != 0 ){
			LOGGER.debug( "Policy ID is null for " + lei.getSectionId() + " while loading existing data :-" + policyId );
		}

		//	java.util.Map<RiskGroup, RiskGroupDetails> riskGroupDetails = new Map<RiskGroup, RiskGroupDetails>( RiskGroup.class, RiskGroupDetails.class );
		// START SIT BUG FIX: Below map type is changed to LinkedHashMap to maintain the order of the RiskGroups
		java.util.LinkedHashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = new LinkedHashMap<RiskGroup, RiskGroupDetails>();
		// END SIT BUG FIX: Below map type is changed to LinkedHashMap to maintain the order of the RiskGroups
		/* Get the saved locations for the section. */
		if( lei.getSectionId() == 0 ){

			return this.loadGeneralInfo( input );

		}
		else{
			java.util.List<? extends RiskGroup> riskGroups = getAllRiskGroups( /* TODO CHANGE THIS!!! */policyIdForLocationsList, policyLinkId, endId, lei );
			if( Utils.isEmpty( riskGroups ) ){
				LOGGER.debug( "RiskGroups are not present for " + lei.getSectionId() + " while loading existing data :-" + riskGroups );
			}

			/* Construct the risk details map. */

			// if General info need to return GeneralInfo

			for( RiskGroup riskGroup : riskGroups ){
				// for each location risk details (ParVO, PublicLiabilityVO, etc.) needs to be populated
				RiskGroupDetails rgd = null;
				if( isRiskGroupStatusActiveToShow(riskGroup) ) rgd = getRiskDetails( riskGroup, policyId, endId, lei );

				/*Change made for implementing de-selecting location. Even if risk group details are null we will maintain the location details 
				 * in risk group details map, so that de-selected location can be shown. */
				riskGroupDetails.put( riskGroup, rgd );

			}
		}

		/* Construct the SectionVO instance now. */
		Double commission = null;
		SectionVO section = new SectionVO( RiskGroupingLevel.LOCATION );
		if( !Utils.isEmpty( policyId ) ){
			commission = getCommission( policyId, endId, lei.getSectionId() ,lei.getAppFlow() );
			if( !Utils.isEmpty( commission ) ){
				commission = Double.valueOf( decForm.format( getCommission( policyId, endId, lei.getSectionId() ,lei.getAppFlow() ) ) );
			}
		}
		section.setCommission( commission );
		section.setPolicyId( policyId );
		section.setSectionName( getSectionName( lei.getSectionId() ) );
		section.setSectionId( lei.getSectionId() );
		section.setRiskGroupDetails( riskGroupDetails );

		return section;
	}

	private boolean isRiskGroupStatusActiveToShow( RiskGroup riskGroup ){
		boolean status = true;
		
		if ( riskGroup.getActiveStatus().equalsIgnoreCase( SvcConstants.STATUS_INACTIVE ) ){
			
			if( SvcUtils.isPolicyCancelled() ){
				status = true;
			}else{
				status = false;
			}
			
		}else if( !riskGroup.getActiveStatus().equalsIgnoreCase( SvcConstants.STATUS_ACTIVE ) ){
			status = false;
		}
		 
		return status;
	}

	/*	*//**
			* TODO THIS METHOD MUST BE REMOVED!!! As is not the required approach(Temporary Fix)
			* @param sectionId
			* @return
			*/
	/*
	private Integer getMappedSectionId( int sectionId) {
	if( sectionId == 1 || sectionId == 6 ) return sectionId;
	else if( sectionId == 7 ) return 6;  For WC, return the section Id of PL. 
	else if( sectionId == 8 ) return 1;  For Money, return the section Id of PAR. 
	return sectionId;
	}*/

	/**
	 * Returns the name of the section
	 * @return
	 */
	private String getSectionName( Integer sectionId ){
		String sectionName = null;
		switch( sectionId ){
			case 1:
				sectionName = SvcConstants.PAR_NAME;
				break;
			case 6:
				sectionName = SvcConstants.PL_NAME;
				break;
			case 8:
				sectionName = SvcConstants.MONEY_NAME;
				break;
			case 7:
				sectionName = SvcConstants.WC_NAME;
				break;
			case 2:
				sectionName = SvcConstants.BI_NAME;
				break;
			case 12:
				sectionName = SvcConstants.GPA_NAME;
				//Sonar Fix for End switch case with unconditional break,return or throw statement
				break;
				//sonar fix
			default:
				break;
		}

		return sectionName;
	}

	/**
	 * Returns the saved commission against the policy for the section.
	 * @param policyId
	 * @param endId 
	 * @return
	 */
	protected Double getCommission( Long policyId, Long endId, Integer sectionId ,Flow appFlow){
		Double commission = null;
		if( !Utils.isEmpty( policyId ) && !Utils.isEmpty( endId ) && !Utils.isEmpty( sectionId ) ){
			List<TTrnSectionDetailsQuo> commDetails = getHibernateTemplate().find(
					"from TTrnSectionDetailsQuo section where  section.id.secPolicyId = ? and section.id.secSecId = ? and  section.secValidityExpiryDate = ?", policyId,
					sectionId.shortValue(), SvcConstants.EXP_DATE );
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			if( !Utils.isEmpty( commDetails ) && commDetails.size() > 0 && !Utils.isEmpty( commDetails.get( 0 ) ) ){
				commission = converter.getTypeOfB().cast( converter.getBFromA( commDetails.get( 0 ).getSecCommVal() ) );
			}
			if(appFlow == Flow.AMEND_POL){
				if(Utils.isEmpty(commission)){
					commDetails = getHibernateTemplate().find(
							"from TTrnSectionDetailsQuo section where  section.id.secPolicyId = ? and section.id.secSecId = ? and section.secValidityExpiryDate = ? ", policyId,
							sectionId.shortValue(), SvcConstants.EXP_DATE);
					converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
					if( !Utils.isEmpty( commDetails ) && commDetails.size() > 0 && !Utils.isEmpty( commDetails.get( 0 ) ) ){
						commission = converter.getTypeOfB().cast( converter.getBFromA( commDetails.get( 0 ).getSecCommVal() ) );
					}
				}	
			}
		}

		return commission;
	}

	/**
	 * Returns the policyId for the policy linking Id and endorsement Id
	 * 
	 * @param policyLinkId
	 * @param endId
	 * @param sectionID 
	 * @return
	 */
	private Long getPolicyId( Long policyLinkId, Long endId, LoadExistingInputVO lei ){
		Integer sectionID = lei.getSectionId();
		String classCode = Utils.getSingleValueAppConfig( "SEC_" + sectionID );

		//Date valExpiryDate=SvcConstants.EXP_DATE;
		/*String GET_POLICY_ID = "select POL_POLICY_ID from t_trn_policy_quo  where POL_LINKING_ID="+policyLinkId+" and POL_ENDT_ID="+endId+" and POL_CLASS_CODE="+Integer.valueOf( classCode );
		
		if ( !lei.isQuote() )
			GET_POLICY_ID = "select POL_POLICY_ID from t_trn_policy  where POL_LINKING_ID="+policyLinkId+" and POL_ENDT_ID="+endId+" and POL_CLASS_CODE="+Integer.valueOf( classCode );
		if ((lei.getAppFlow()==Flow.AMEND_POL))
			GET_POLICY_ID = "select POL_POLICY_ID from t_trn_policy  where POL_LINKING_ID="+policyLinkId+" and  POL_VALIDITY_EXPIRY_DATE=" + valExpiryDate +" and  POL_CLASS_CODE="+Integer.valueOf( classCode );
		*/
		//Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		//Query query = session.createSQLQuery(policyIdFetchQuery);

		Query query = (Query) DAOUtils.getTableSnapshotQuery( "T_TRN_POLICY", lei.getAppFlow(), getHibernateTemplate(), true, endId, policyLinkId, Integer.valueOf( classCode ) );
		BigDecimal policyId = null;
		//DAOUtils.setQueryParameters(query,lei.getAppFlow(),policyLinkId,endId,valExpiryDate,classCode);

		if( !Utils.isEmpty( query ) ){
			List<Object> results = query.list();
			if( !Utils.isEmpty( results ) && results.size() > 0 ){
				policyId = ( (BigDecimal) results.get( 0 ) );
				return policyId.longValue();
			}
		}

		return null;
	}

	/**
	 * Gets all the risk details for the risk group, constructs the RiskGroupDetails implementation instance,
	 * like ParVO, PublicLiabilityVO, etc. and returns.
	 * 
	 * @param riskGroup
	 * @param policyId
	 * @param endId
	 * @return
	 */
	protected abstract RiskGroupDetails getRiskDetails( RiskGroup riskGroup, Long policyId, Long endId, LoadExistingInputVO lei );

	/**
	 * Gets all the risk groups, usually Locations, for the section against the policy.
	 * 
	 * @param policyLinkId
	 * @param endId
	 * @param sectionId 
	 * @return
	 */
	private java.util.List<? extends RiskGroup> getAllRiskGroups( Long policyId, Long policyLinkId, Long endId, LoadExistingInputVO lei ){

		Integer sectionId = lei.getSectionId();

		Query query = null;
		TableSnapshotLevel level = TableSnapshotQueryHelper.getViewTypeForFlow( lei.getAppFlow() );
		if( level == TableSnapshotLevel.CURRENT_VALID_STATE ){
			query = (Query) DAOUtils.getTableSnapshotQuery( "T_TRN_SECTION_LOCATION_VED", lei.getAppFlow(), getHibernateTemplate(), true, endId, policyLinkId, sectionId );
		}
		else{
			query = (Query) DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_SECTION_LOCATION, lei.getAppFlow(), getHibernateTemplate(), true, endId, policyLinkId, sectionId );
		}

		List<Object[]> result = (List<Object[]>) query.list();
		List<TTrnSectionLocationQuo> tTrnSectionLocationQuoList = new ArrayList<TTrnSectionLocationQuo>();
		Iterator<Object[]> it = result.iterator();

		while( it.hasNext() ){
			Object[] object = it.next();
			
			TTrnSectionLocationQuo tTrnSectionLocationQuo = new TTrnSectionLocationQuo();
			TTrnSectionLocationQuoId tTrnSectionLocationQuoId = new TTrnSectionLocationQuoId();
			tTrnSectionLocationQuo.setId( tTrnSectionLocationQuoId );

			if( !Utils.isEmpty( object[ 0 ] ) ){
				tTrnSectionLocationQuo.getId().setTslPolLinkingId( ( (BigDecimal) object[ 0 ] ).longValue() );
			}
			if( !Utils.isEmpty( object[ 1 ] ) ){
				tTrnSectionLocationQuo.getId().setTslPolEndtNo( ( (BigDecimal) ( object[ 0 ] ) ).longValue() );
			}
			if( !Utils.isEmpty( object[ 2 ] ) ){
				tTrnSectionLocationQuo.getId().setTslSecId( ( (BigDecimal) object[ 1 ] ).shortValue() );
			}
			if( !Utils.isEmpty( object[ 3 ] ) ){
				tTrnSectionLocationQuo.getId().setTslLocId( ( (BigDecimal) object[ 3 ] ).longValue() );
			}
			if( !Utils.isEmpty( object[ 4 ] ) ){
				tTrnSectionLocationQuo.setTslActiveFlag( ( object[ 4 ] ).toString() );
			}
			/*if(!Utils.isEmpty(object[5])){
				tTrnSectionLocationQuo.setTslValidityStartDate((object[5]));   
			}
			if(!Utils.isEmpty(object[6])){
				vTrnBldWbdQuo.setFreeZone((object[6]).toString()); 
			}*/
			if( !Utils.isEmpty( object[ 7 ] ) ){
				tTrnSectionLocationQuo.setTslPreparedBy( ( Integer.valueOf( ( object[ 7 ].toString() ) ) ) );
			}
			/*if(!Utils.isEmpty(object[8])){
				tTrnSectionLocationQuo.setTslPreparedDt(((object[8]).toString());  
			}*/
			if( !Utils.isEmpty( object[ 9 ] ) ){
				tTrnSectionLocationQuo.setTslModifiedBy( Integer.valueOf( ( object[ 9 ] ).toString() ) );
			}
			/*if(!Utils.isEmpty(object[10])){
				tTrnSectionLocationQuo.setTslModifiedDt(((object[10]).toString());  
			}*/

			tTrnSectionLocationQuoList.add( tTrnSectionLocationQuo );
		}
		
		Collections.sort(tTrnSectionLocationQuoList);
		/* Filter out the locations which are deleted from Basic Section for POLICY (AMEND_POL || VIEW_POL )
		 * and add it to new list of secLocRecord. 
		 * This logic was required for POLICY as location record is not deleted from the table 
		 * during delete location */
		List<TTrnSectionLocationQuo> riskGroupListToBeReturned = new ArrayList<TTrnSectionLocationQuo>();
		
		for(TTrnSectionLocationQuo secLocRecord : tTrnSectionLocationQuoList){
			if ( ! toContinueWithLocAddition( lei, secLocRecord) ) continue;
			riskGroupListToBeReturned.add( secLocRecord );
		}
		
		List<LocationVO> locDetailsList = new com.mindtree.ruc.cmn.utils.List<LocationVO>( LocationVO.class );
		
		/*
		 * Create a new policyVO to fetch the policy Id of the section passed from daoUtils function.
		 *  Policy Id is used to fetch the values from T_TRN_BUILDING table based on Policy Id. 
		 */
		
		Long sectionPolicyId;
		
		for( TTrnSectionLocationQuo locDetail : riskGroupListToBeReturned ){
			if( !Utils.isEmpty( ( ( (TTrnSectionLocationQuo) locDetail ).getId().getTslLocId() ) ) ){
				LocationVO locDetailVO = new LocationVO();
				TTrnBuildingQuo tTrnBuildingQuo = null;
				locDetailVO.setRiskGroupId( String.valueOf( ( ( (TTrnSectionLocationQuo) locDetail ).getId().getTslLocId() ) ) );
				/*Introduced an active flag for the location*/
				locDetailVO.setActiveStatus( locDetail.getTslActiveFlag() );
				boolean isPARBuilding = false;
				
				
				/*
				 * Check if the location is added from PAR as basic section
				 */
				List<TTrnBuildingQuo> tTrnBuildingQuoList = null;
			    if( Utils.isEmpty( lei.getBasicSectionId() ) ){
			    	throw new BusinessException( "cmn.unknownError", null , "An exception occured while fetching basic section id." );
			    }
				if( lei.getBasicSectionId() == SvcConstants.SECTION_ID_PAR ){
					sectionPolicyId = getSectionPolicyId(policyLinkId,Integer.valueOf( SvcConstants.SECTION_ID_PAR ),lei.isQuote());
					tTrnBuildingQuoList = (List<TTrnBuildingQuo>) DAOUtils.getTableSnapshotQuery( "T_TRN_BUILDING_POLICY", lei.getAppFlow(), getHibernateTemplate(), false, endId,
							sectionPolicyId, ( (TTrnSectionLocationQuo) locDetail ).getId().getTslLocId() );

					if( !Utils.isEmpty( tTrnBuildingQuoList ) ){
						/* Yes, location is added from PAR hence map locationVO to TTrnBuilding(_Quo) record */
						tTrnBuildingQuo = tTrnBuildingQuoList.get( 0 );
						locDetailVO = BeanMapper.map( tTrnBuildingQuo, locDetailVO );
						( (LocationVO) locDetailVO ).setDirectorate( tTrnBuildingQuo.getBldDirCode() );
						( (LocationVO) locDetailVO ).setRiskGroupName( getPARRiskGroupName(tTrnBuildingQuo) );
						isPARBuilding = true;
					}
				}
				List<TTrnWctplPremiseQuo> premiseQuoList = null;
				
				if( !isPARBuilding ){
					sectionPolicyId = getSectionPolicyId(policyLinkId,Integer.valueOf( SvcConstants.SECTION_ID_PL ),lei.isQuote());
					//Renewal Multiple Id's handling changes, added policy in the query parameter
					premiseQuoList = (List<TTrnWctplPremiseQuo>) DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE, lei.getAppFlow(),
							getHibernateTemplate(), false, endId, sectionPolicyId,( (TTrnSectionLocationQuo) locDetail ).getId().getTslLocId() );
				}
				//Renewal Multiple Id's handling changes, added policy in the query parameter
				if( Utils.isEmpty( premiseQuoList ) && sectionId.intValue() == SvcConstants.SECTION_ID_PL ){
					sectionPolicyId = getSectionPolicyId(policyLinkId,Integer.valueOf( SvcConstants.SECTION_ID_PL ),lei.isQuote());
					premiseQuoList = (List<TTrnWctplPremiseQuo>) DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE_BLDID, lei.getAppFlow(),
							getHibernateTemplate(), false, endId, sectionPolicyId,( (TTrnSectionLocationQuo) locDetail ).getId().getTslLocId() );
				}
				
				if( !Utils.isEmpty( premiseQuoList ) ){
					TTrnWctplPremiseQuo tTrnWctplPremiseQuo = premiseQuoList.get( ZERO_VAL );
					locDetailVO = BeanMapper.map( tTrnWctplPremiseQuo, locDetailVO );
					( (LocationVO) locDetailVO ).setDirectorate( Integer.valueOf( tTrnWctplPremiseQuo.getWbdDirCode().toString() ) );
					
				if(!Utils.isEmpty( tTrnWctplPremiseQuo.getWbdNo() )){
					locDetailVO.getAddress().setFloor( tTrnWctplPremiseQuo.getWbdNo() );
				}
					
				( (LocationVO) locDetailVO ).setRiskGroupName( getPLRiskGroupName(tTrnBuildingQuo,tTrnWctplPremiseQuo) );	
				
				}
				locDetailsList.add( locDetailVO );
			}
		}
		return locDetailsList;
	}
	
	/**
	 * 
	 * @param tTrnBuildingQuo
	 * @return
	 */
	private String getPARRiskGroupName(TTrnBuildingQuo tTrnBuildingQuo){
		String riskGrpName = "";
		String locationName = "";
		locationName = SvcUtils.getLookUpDescription( com.Constant.CONST_DIRECTORATE, SvcConstants.LOOKUP_LEVEL1, SvcConstants.LOOKUP_LEVEL2, tTrnBuildingQuo.getBldDirCode() );
		if( tTrnBuildingQuo.getBldEName().equals( locationName ) ){
			riskGrpName = ( !Utils.isEmpty( tTrnBuildingQuo.getBldAAddress1() ) ? tTrnBuildingQuo.getBldAAddress1()+" " : "" ) + ( !Utils.isEmpty( tTrnBuildingQuo.getBldAAddress2() ) ? tTrnBuildingQuo.getBldAAddress2() + " " : "" )
			+ tTrnBuildingQuo.getBldEName();
		}else{
			riskGrpName = tTrnBuildingQuo.getBldEName();
		}
		return riskGrpName;
	}
	
	/**
	 * 
	 * @param tTrnBuildingQuo
	 * @return
	 */
	private String getPLRiskGroupName(TTrnBuildingQuo tTrnBuildingQuo, TTrnWctplPremiseQuo tTrnWctplPremiseQuo){
		String riskGrpName = "";
		String locationName = "";
		
		if( !Utils.isEmpty( tTrnBuildingQuo ) ){
			locationName = SvcUtils.getLookUpDescription( com.Constant.CONST_DIRECTORATE, SvcConstants.LOOKUP_LEVEL1, SvcConstants.LOOKUP_LEVEL2, tTrnBuildingQuo.getBldDirCode() );
			if( tTrnBuildingQuo.getBldEName().equals( locationName ) ){
				riskGrpName = ( !Utils.isEmpty( tTrnBuildingQuo.getBldAAddress1() ) ? tTrnBuildingQuo.getBldAAddress1()+" " : "" ) + ( !Utils.isEmpty( tTrnBuildingQuo.getBldAAddress2() ) ? tTrnBuildingQuo.getBldAAddress2() + " " : "" )
					+ tTrnWctplPremiseQuo.getWbdEName();
			}else{
				riskGrpName = tTrnWctplPremiseQuo.getWbdEName();
			}
		}
		else{
			locationName = SvcUtils.getLookUpDescription( com.Constant.CONST_DIRECTORATE, SvcConstants.LOOKUP_LEVEL1, SvcConstants.LOOKUP_LEVEL2, Integer.valueOf( tTrnWctplPremiseQuo.getWbdDirCode().toString() ) );
			
			if( tTrnWctplPremiseQuo.getWbdEName().equals( locationName ) ){
				riskGrpName = ( !Utils.isEmpty( tTrnWctplPremiseQuo.getWbdAAddress1() ) ? tTrnWctplPremiseQuo.getWbdAAddress1() + " " :"" ) + ( !Utils.isEmpty( tTrnWctplPremiseQuo.getWbdNo() ) ? tTrnWctplPremiseQuo.getWbdNo() + " " : "" ) + tTrnWctplPremiseQuo.getWbdEName();
			}else{
				riskGrpName = tTrnWctplPremiseQuo.getWbdEName();
			}
		}
		return riskGrpName;
	}
	
	/**
	 * 
	 * @param policyLinkId
	 * @param sectionId
	 * @return
	 */
	private Long getSectionPolicyId( Long policyLinkId, Integer sectionId, Boolean isQuote ){
		/*
		 * Create a new policyVO to fetch the policy Id of the section passed
		 * from daoUtils function. Policy Id is used to fetch the values from
		 * T_TRN_BUILDING table based on Policy Id.
		 */
		PolicyVO policyVO = new PolicyVO();
		policyVO.setPolLinkingId( policyLinkId );
		policyVO.setIsQuote( isQuote );
		/*
		 * If PL section load is happening
		 */
		/*return sectionId.equals( Integer.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" ) ) ) ? DAOUtils.getSectionPolicyId( policyVO, sectionId, getHibernateTemplate() )
				: null;
		*/
		return DAOUtils.getSectionPolicyId( policyVO, sectionId, getHibernateTemplate() );
	}

	/**
	 * Get the general info. Need to be override in the GeneralifoLoadDAO.
	 * 
	 * @param input
	 * @return 
	 */
	public BaseVO loadGeneralInfo( BaseVO input ){
		return null;
	}
	
	/**
	 * Returns true in following case's 
	 * (a). location is active for the section
	 * (b). AppFlow is AMEND_POL or VIEW_POL and location is not deleted from basic section
	 * @param lei
	 * @param secLocRecord
	 * @return
	 */
	private boolean toContinueWithLocAddition( LoadExistingInputVO lei, Object... secLocRecord ){
		boolean toContinueWithLocAddition = true;
		String inActiveLocationFlag = "N";
		TTrnSectionLocationQuo secLocationRecord = null;
		if( !Utils.isEmpty( secLocRecord[ 0 ] ) ){
			secLocationRecord = (TTrnSectionLocationQuo) secLocRecord[ 0 ];
		}
		
		/*if( ( !Utils.isEmpty( secLocationRecord ) ) && ( secLocationRecord.getTslActiveFlag().equals( inActiveLocationFlag ) )
				&& ( Flow.AMEND_POL.equals( lei.getAppFlow() ) || Flow.VIEW_POL.equals( lei.getAppFlow() ) ) ) */
		if( ( !Utils.isEmpty( secLocationRecord ) ) && ( secLocationRecord.getTslActiveFlag().equals( inActiveLocationFlag ) ) ){

			/* Check if the location is deleted from Basic Section in order to actually avoid display of this location to the user
			 * in any of the sections for this policy */
			List<POJO> existingLocRecordList = (List<POJO>) DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_SECTION_LOCATION, lei.getAppFlow(), getHibernateTemplate(),
					false, lei.getEndtId() , lei.getPolLinkingId(), lei.getBasicSectionId().shortValue(), secLocationRecord
							.getId().getTslLocId(), inActiveLocationFlag );
			if( !Utils.isEmpty( existingLocRecordList ) && existingLocRecordList.size() > ZERO_VAL && !SvcUtils.isPolicyCancelled() ){
				/* Location is deleted from basic section hence need not be shown to the user */
				toContinueWithLocAddition = false;
			}
			/* override toContinueWithLocAddition value to false for case where in location is added in PL and PAR is the basic section */
			if( toContinueWithLocAddition ){
				/* Check if the location is deleted from Basic Section in order to actually avoid display of this location to the user
				 * in any of the sections for this policy */
				List<POJO> plSpecificLocList = (List<POJO>) DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_SECTION_LOCATION, lei.getAppFlow(), getHibernateTemplate(),
						false, lei.getEndtId(), lei.getPolLinkingId(), lei.getBasicSectionId().shortValue(), secLocationRecord
								.getId().getTslLocId(), inActiveLocationFlag );
				if( Utils.isEmpty( plSpecificLocList ) ) toContinueWithLocAddition = false;
			}
		}
		return toContinueWithLocAddition;
	}
	
	//Added for Bahrain 3 decimal - Starts
	protected boolean isSBSBahrainPolicy(Long endId, LoadExistingInputVO lei, Long policyId) {

		Integer policyType = 0;
		policyType = Integer.valueOf(Utils.getSingleValueAppConfig("SBS_Policy_Type"));

		List<TTrnPolicyQuo> polLst = (List<TTrnPolicyQuo>) DAOUtils.getTableSnapshotQuery(
				SvcConstants.TABLE_ID_T_TRN_POLICY, lei.getAppFlow(), getHibernateTemplate(),
				SvcConstants.IS_TABLE_QUERY_HBM, endId, lei.getPolLinkingId(),
				Integer.valueOf(SvcConstants.CLASS_ID_PAR).shortValue());

		if (!Utils.isEmpty(polLst)) {

			
			Integer polPolicyType = polLst.get(0).getPolPolicyType().intValue();

			if (policyType == polPolicyType
					&& Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("50")) {
				return true;
			}
		}
		return false;
	}

	// Added for Bahrain 3 decimal - Ends

}
