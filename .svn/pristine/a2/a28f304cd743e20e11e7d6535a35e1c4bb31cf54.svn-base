/**
 * 
 */
package com.rsaame.pas.rules.mapper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.BusinessChannel;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.StaffDetailsVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsame.rulesengine.restfulclient.request.Characteristics;
import com.rsame.rulesengine.restfulclient.request.Fact;
import com.rsame.rulesengine.restfulclient.request.RiskDetails;
import com.rsame.rulesengine.restfulclient.request.RuleInfo;

/**
 * @author M1012799
 *
 */
public class GeneralInfoRuleMapper implements RuleMapper{

	private static final int DAYDIVIDER = ( 1000 * 60 * 60 * 24 );
	private static final long POLICY_PERIOD = 365L;
	

	/* (non-Javadoc)
	 * @see com.rsaame.pas.rules.mapper.RuleMapper#createRiskDetails(com.mindtree.ruc.cmn.base.BaseVO, int, java.lang.String)
	 */
	@Override
	public RuleInfo createRiskDetails( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){

		List<RiskDetails> riskDetailList = ruleInfo.getRiskDetails();

		RiskDetails riskDetails = new RiskDetails();
		riskDetails.setRiskName( RulesConstants.GENERAL );
		
		riskDetails.setRiskId( new Integer( 11111 ) );
		List<Fact> factList = riskDetails.getFact();

		String effectiveDateBackdating = new String( "0" );
		String effectiveDatePostdating = new String( "0" );
		String issueDateBackdating = new String( "0" );
		String issueDatePostDating = new String( "0" );
		
		String renEffectiveDateBackdating = "0";
		String renEffectiveDatePostdating = "0";
		
		String renIssueDateBackdating = "0";
		String renIssueDatePostDating = "0";
		
		String city = null;
		String nationality = null;

		String endorsementDateBackdating = new String( "0" );
		String endorsementDatePostdating = new String( "0" );
		String policyExtnPeriod = new String( "0" );

		Date todayDate = getTodayDate();
		Date effectiveDate=null;
		
		/* Create user fact */
		Fact userFact = new Fact();
		userFact.setFactName( RulesConstants.FACT_USER );

		List<Characteristics> userCharacteristicList = userFact.getCharacteristics();

		Characteristics userCharacteristics = new Characteristics();
		userCharacteristics.setName( "role" );
		userCharacteristics.setValue( roleType );
		userCharacteristicList.add( userCharacteristics );

		if( !Utils.isEmpty( policyBaseVO ) ){
			PolicyDataVO policyDataVO = (PolicyDataVO) policyBaseVO;
			CommonVO commonVO = policyDataVO.getCommonVO();
			
			if( !Utils.isEmpty( policyDataVO.getScheme() ) && !Utils.isEmpty( policyDataVO.getScheme().getEffDate() ) )
			{
				effectiveDate = policyDataVO.getScheme().getEffDate();
				if( !Utils.isEmpty( effectiveDate ) && !Utils.isEmpty( commonVO ) && commonVO.getIsQuote() &&  
						!Utils.isEmpty( commonVO.getDocCode() ) && commonVO.getDocCode() == 6) 
				{
					if( todayDate.after( effectiveDate ) )
						renEffectiveDateBackdating = String.valueOf( getDateDifference( todayDate, effectiveDate ) );
					else
						renEffectiveDatePostdating = String.valueOf( getDateDifference( effectiveDate, todayDate ) );
				}
				else if( !Utils.isEmpty( effectiveDate ) && !Utils.isEmpty( commonVO ) && commonVO.getIsQuote() ){
					if( todayDate.after( effectiveDate ) )
						effectiveDateBackdating = String.valueOf( getDateDifference( todayDate, effectiveDate ) );
					else
						effectiveDatePostdating = String.valueOf( getDateDifference( effectiveDate, todayDate ) );
					// The below characteristic to be set during renewal quotation flow.
				} 
			}

			if( !Utils.isEmpty( policyDataVO.getAuthenticationInfoVO() ) && !Utils.isEmpty( policyDataVO.getAuthenticationInfoVO().getAccountingDate() ) )
			{
				Date accountingDate = policyDataVO.getAuthenticationInfoVO().getAccountingDate();
				
				if( !Utils.isEmpty( accountingDate ) && !Utils.isEmpty( commonVO ) && commonVO.getIsQuote() &&  
						!Utils.isEmpty( commonVO.getDocCode() ) && commonVO.getDocCode() == 6) 
				{
					if( todayDate.after( accountingDate ) )
						renIssueDateBackdating = String.valueOf( getDateDifference( todayDate, accountingDate ) );
					else
						renIssueDatePostDating = String.valueOf( getDateDifference( accountingDate, todayDate ) );
				}
				
				else if( !Utils.isEmpty( accountingDate ) && !Utils.isEmpty( commonVO ) && commonVO.getIsQuote() )
				{
					if( todayDate.after( accountingDate ) )
						issueDateBackdating = String.valueOf( getDateDifference( todayDate, accountingDate ) );
					else
						issueDatePostDating = String.valueOf( getDateDifference( accountingDate, todayDate ) );
				}
			}

			/*if( !Utils.isEmpty( policyDataVO.getAuthenticationInfoVO() ) && !Utils.isEmpty( policyDataVO.getAuthenticationInfoVO().getAccountingDate() ) ){
				Date accountingDate = policyDataVO.getAuthenticationInfoVO().getAccountingDate();
				if( !Utils.isEmpty( accountingDate ) && policyDataVO.getIsQuote() ){
					if( todayDate.after( accountingDate ) )
						effectiveDateBackdating = String.valueOf( getDateDifference( todayDate, accountingDate ) );
					else
						effectiveDatePostdating = String.valueOf( getDateDifference( accountingDate, todayDate ) );
				}
			}*/

			if( !Utils.isEmpty( policyDataVO.getGeneralInfo() ) && !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured() )
					&& !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured().getCity() ) ){
				city = String.valueOf( policyDataVO.getGeneralInfo().getInsured().getCity() );
			}

			if( !Utils.isEmpty( policyDataVO.getGeneralInfo() ) && !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured() )
					&& !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured().getNationality() ) ){
				nationality = String.valueOf( policyDataVO.getGeneralInfo().getInsured().getNationality() );
			}

			long extnPeriod = (getDateDifference( policyDataVO.getScheme().getExpiryDate(), policyDataVO.getScheme().getEffDate() ).longValue() + 1L ) - POLICY_PERIOD;
			if( !policyDataVO.getCommonVO().getIsQuote() && extnPeriod > SvcConstants.zeroVal ){ // && !Utils.isEmpty( policyDataVO.getPolicyExtensionPeriod() )
				policyExtnPeriod = Long.valueOf( extnPeriod ).toString() ;
			}
			/*
			  Changes to display referral pop-up if domestic staff age is <18 years and >60 years.
			*/
			if( (BusinessChannel.B2C.equals(policyDataVO.getCommonVO().getChannel())))
			{
				
				
				
	
				
				
				RiskDetails homeriskDetails = new RiskDetails();
				homeriskDetails.setRiskName( "HOME" );

				homeriskDetails.setRiskId( new Integer( 1000 ) );
				List<Fact> homefactList = homeriskDetails.getFact();
				
				/* Commented by Sarath*/
				 Fact homeFact = new Fact();
				homeFact.setFactName( "Home" );
				
				
				
				
				
			
				
				
				List<Characteristics> homeCharacteristicList = homeFact.getCharacteristics();
				
				/*
				 * 
				 * Himanish : Rules Modification : 146276 
				 * 
				 */
				Characteristics businessDescCharacteristics = new Characteristics();
				businessDescCharacteristics.setName( "contentsSumInsured" );
				businessDescCharacteristics.setValue(String.valueOf(String.valueOf(new BigDecimal("0").doubleValue())));
				homeCharacteristicList.add( businessDescCharacteristics );
				
				Characteristics businessDescCharacteristics2 = new Characteristics();
				businessDescCharacteristics2.setName( "homeContentsArticleSumInsured" );
				businessDescCharacteristics2.setValue(String.valueOf(String.valueOf(new BigDecimal("0").doubleValue())));
				homeCharacteristicList.add( businessDescCharacteristics2 );
				
				
				
				/*
				 * 
				 * Himanish : Rules Modification : 146276 
				 * 
				 */
				Characteristics personalArticleExceedsLimitCharacteristics = new Characteristics();
				personalArticleExceedsLimitCharacteristics.setName( "personalSumInsured" );
				personalArticleExceedsLimitCharacteristics.setValue(String.valueOf(String.valueOf(new BigDecimal("0").doubleValue())));
				homeCharacteristicList.add( personalArticleExceedsLimitCharacteristics );
				
				Characteristics personalArticleExceedsLimitCharacteristics2 = new Characteristics();
				personalArticleExceedsLimitCharacteristics2.setName( "homePersonalArticleSumInsured" );
				personalArticleExceedsLimitCharacteristics2.setValue(String.valueOf(String.valueOf(new BigDecimal("0").doubleValue())));
				homeCharacteristicList.add( personalArticleExceedsLimitCharacteristics2 );
				

				
				List<Object[]> valueHolder = DAOUtils.getSqlResultForPas( QueryConstants.FETCH_DOMESTIC_STAFF_DETAILS_POLICY_ID, policyDataVO.getCommonVO().getPolicyId(),
						policyDataVO.getEndtId() );
				if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
					List<StaffDetailsVO> staffDetailsVOList = new ArrayList<StaffDetailsVO>();
					Iterator<Object[]> itr = null;
					itr = valueHolder.iterator();
					Object[] row = null;
					while( itr.hasNext() ){
						StaffDetailsVO sdvo = new StaffDetailsVO();
						row = (Object[]) itr.next();
						try{
							if( !Utils.isEmpty( row[ 0 ] ) ) sdvo.setEmpName( String.valueOf( row[ 0 ] ) );
							SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
							try{
								sdvo.setEmpDob( sdf.parse( String.valueOf( row[ 1 ] ) ) );
							}
							catch( ParseException e ){
								e.printStackTrace();
							}

						}
						catch( HibernateException e ){
							e.printStackTrace();
						}
						staffDetailsVOList.add( sdvo );

					}

					if( !Utils.isEmpty( staffDetailsVOList ) ){
						BigDecimal domesticStaffMaxAge = null;
						BigDecimal domesticStaffMinAge = null;
						BigDecimal newAge;

						for( StaffDetailsVO staffDetailsVO : staffDetailsVOList ){
							newAge = SvcUtils.getAgeForHome( staffDetailsVO.getEmpDob(), effectiveDate );
							if( Utils.isEmpty( domesticStaffMaxAge ) && Utils.isEmpty( domesticStaffMinAge ) )
								domesticStaffMaxAge = domesticStaffMinAge = newAge;

							else if( newAge.compareTo( domesticStaffMaxAge ) == 1 )
								domesticStaffMaxAge = newAge;

							else if( newAge.compareTo( domesticStaffMaxAge ) == -1 ) domesticStaffMinAge = newAge;

						}
						domesticStaffMaxAge = domesticStaffMaxAge.setScale( 4, BigDecimal.ROUND_CEILING );
						Characteristics domesticStaffMaxAgeCharacteristics = new Characteristics();
						domesticStaffMaxAgeCharacteristics.setName( "domesticStaffMaxAge" );
						domesticStaffMaxAgeCharacteristics.setValue( String.valueOf( domesticStaffMaxAge ) );
						homeCharacteristicList.add( domesticStaffMaxAgeCharacteristics );
						domesticStaffMinAge = domesticStaffMinAge.setScale( 4, BigDecimal.ROUND_CEILING );
						Characteristics domesticStaffMinAgeCharacteristics = new Characteristics();
						domesticStaffMinAgeCharacteristics.setName( "domesticStaffMinAge" );
						domesticStaffMinAgeCharacteristics.setValue( String.valueOf( domesticStaffMinAge ) );
						homeCharacteristicList.add( domesticStaffMinAgeCharacteristics );
					}

				}
				homefactList.add( homeFact );
				
				homefactList.add( userFact );
				riskDetailList.add( homeriskDetails );
			}	
			
		}

		

		/*Create general info fact */
		Fact generalInfoFact = new Fact();
		generalInfoFact.setFactName( RulesConstants.FACT_GENERAL_HOME );

		List<Characteristics> generalInfoCharacteristicList = generalInfoFact.getCharacteristics();

		Characteristics effectiveDateBackdatingCharacteristics = new Characteristics();
		effectiveDateBackdatingCharacteristics.setName( "effectiveDateBackdating" );
		effectiveDateBackdatingCharacteristics.setValue( effectiveDateBackdating );
		generalInfoCharacteristicList.add( effectiveDateBackdatingCharacteristics );

		Characteristics effectiveDatePostdatingCharacteristics = new Characteristics();
		effectiveDatePostdatingCharacteristics.setName( "effectiveDatePostdating" );
		effectiveDatePostdatingCharacteristics.setValue( effectiveDatePostdating );
		generalInfoCharacteristicList.add( effectiveDatePostdatingCharacteristics );

		Characteristics issueDateBackdatingCharacteristics = new Characteristics();
		issueDateBackdatingCharacteristics.setName( "issueDateBackdating" );
		issueDateBackdatingCharacteristics.setValue( issueDateBackdating );
		generalInfoCharacteristicList.add( issueDateBackdatingCharacteristics );

		Characteristics issueDatePostdatingCharacteristics = new Characteristics();
		issueDatePostdatingCharacteristics.setName( "issueDatePostdating" );
		issueDatePostdatingCharacteristics.setValue( issueDatePostDating );
		generalInfoCharacteristicList.add( issueDatePostdatingCharacteristics );
		
		Characteristics renIssueDateBackdatingCharacteristics = new Characteristics();
		renIssueDateBackdatingCharacteristics.setName( "renIssueDateBackdating" );
		renIssueDateBackdatingCharacteristics.setValue( renIssueDateBackdating );
		generalInfoCharacteristicList.add( renIssueDateBackdatingCharacteristics );
		
		Characteristics renIssueDatePostDatingCharacteristics = new Characteristics();
		renIssueDatePostDatingCharacteristics.setName( "renIssueDatePostDating" );
		renIssueDatePostDatingCharacteristics.setValue( renIssueDatePostDating );
		generalInfoCharacteristicList.add( renIssueDatePostDatingCharacteristics );
		
		
		Characteristics renEffectiveDateBackdatingCharacteristics = new Characteristics();
		renEffectiveDateBackdatingCharacteristics.setName( "renEffectiveDateBackdating" );
		renEffectiveDateBackdatingCharacteristics.setValue( renEffectiveDateBackdating );
		generalInfoCharacteristicList.add( renEffectiveDateBackdatingCharacteristics );
			
		Characteristics renEffectiveDatePostdatingCharacteristics = new Characteristics();
		renEffectiveDatePostdatingCharacteristics.setName( "renEffectiveDatePostdating" );
		renEffectiveDatePostdatingCharacteristics.setValue( renEffectiveDatePostdating );
		generalInfoCharacteristicList.add( renEffectiveDatePostdatingCharacteristics );

		if( !Utils.isEmpty( city ) ){
			Characteristics cityCharacteristics = new Characteristics();
			cityCharacteristics.setName( "city" );
			cityCharacteristics.setValue( city );
			generalInfoCharacteristicList.add( cityCharacteristics );
		}

		if( !Utils.isEmpty( nationality ) ){
			Characteristics nationalityCharacteristics = new Characteristics();
			nationalityCharacteristics.setName( "nationality" );
			nationalityCharacteristics.setValue( nationality );
			generalInfoCharacteristicList.add( nationalityCharacteristics );
		}

		/* Endorsement related mappers */
		if( !Utils.isEmpty( policyBaseVO ) ){
			PolicyDataVO policyDataVO = (PolicyDataVO) policyBaseVO;
			CommonVO commonVO = policyDataVO.getCommonVO();

			Date endtEffectiveDate = policyDataVO.getEndEffectiveDate();

			
			if( !Utils.isEmpty( commonVO ) && !commonVO.getIsQuote() ){
				List<Object[]> resultSet = DAOUtils.getSqlResultForPas( QueryConstants.FETCH_DATE, policyDataVO.getCommonVO().getPolicyId(), policyDataVO.getCommonVO().getEndtId() );

				/* Backdate check */
				if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){

					Date inceptionDate = null;
					for( Object[] result : resultSet ){
						if( Utils.isEmpty( result[ 1 ] ) ){
							inceptionDate = (Date) result[ 0 ];
						}
						else{
							inceptionDate = (Date) result[ 1 ];
						}
					}

					if(Utils.isEmpty( endtEffectiveDate )){
						endtEffectiveDate = commonVO.getEndtEffectiveDate();
					}
					
					if( !Utils.isEmpty( endtEffectiveDate ) && !Utils.isEmpty( inceptionDate ) && !Utils.isEmpty( todayDate ) ){
						if( todayDate.after( endtEffectiveDate ) ){
							if( !Utils.isEmpty( roleType ) && roleType.contains( "BROKER" ) ){
								endorsementDateBackdating = String.valueOf( getDateDifference( todayDate, endtEffectiveDate ) );
							}
							else{
								endorsementDateBackdating = String.valueOf( getDateDifference( inceptionDate, endtEffectiveDate ) );
							}
						}
						else{
							endorsementDatePostdating = String.valueOf( getDateDifference( endtEffectiveDate, todayDate ) );
						}
					}
				}
				
				Characteristics endorsementDateBackdatingCharacteristics = new Characteristics();
				endorsementDateBackdatingCharacteristics.setName( "endorsementDateBackdating" );
				endorsementDateBackdatingCharacteristics.setValue( endorsementDateBackdating );
				generalInfoCharacteristicList.add( endorsementDateBackdatingCharacteristics );
				
				Characteristics endorsementDatePostDatingCharacteristics = new Characteristics();
				endorsementDatePostDatingCharacteristics.setName( "endorsementDatePostdating" );
				endorsementDatePostDatingCharacteristics.setValue( endorsementDatePostdating );
				generalInfoCharacteristicList.add( endorsementDatePostDatingCharacteristics );
			}
		}

		Characteristics policyExtnPeriodCharacteristics = new Characteristics();
		policyExtnPeriodCharacteristics.setName( "policyExtnPeriod" );
		policyExtnPeriodCharacteristics.setValue( policyExtnPeriod );
		generalInfoCharacteristicList.add(policyExtnPeriodCharacteristics);

		factList.add( userFact );
		factList.add( generalInfoFact );
		riskDetailList.add( riskDetails );
		//return ruleInfo;
		return ruleInfo;
	}

	/**
	 * Method to get the date differnce 
	 * @param latestDate
	 * @param previousDate
	 * @return
	 */
	private Long getDateDifference( Date latestDate, Date previousDate ){
		return ( ( ( latestDate.getTime() - previousDate.getTime() ) / DAYDIVIDER ) );
	}

	/**
	 * Method to return the current date
	 * 
	 * @return
	 */
	private Date getTodayDate(){
		Calendar cal = Calendar.getInstance();
		//cal.add(Calendar.DAY_OF_MONTH,1);
		cal.set( Calendar.HOUR_OF_DAY, 0 );
		cal.set( Calendar.MINUTE, 0 );
		cal.set( Calendar.SECOND, 0 );
		cal.set( Calendar.MILLISECOND, 0 );

		Date today = cal.getTime();

		return today;
	}

}
