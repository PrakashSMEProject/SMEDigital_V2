/**
 * 
 */
package com.rsaame.pas.premiumHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.VTrnPasPremSumLoc;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.lookup.svc.LookUpService;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.CashResidenceVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockVO;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.EquipmentVO;
import com.rsaame.pas.vo.bus.FidelityNammedEmployeeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityUnnammedEmployeeVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.GPANammedEmpVO;
import com.rsaame.pas.vo.bus.GPAUnnammedEmpVO;
import com.rsaame.pas.vo.bus.GroupPersonalAccidentVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.MBVO;
import com.rsaame.pas.vo.bus.MachineDetailsVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.WCVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author m1014644
 *
 */
public class PremiumHelper{

	private static Logger prmLogger = Logger.getLogger( PremiumHelper.class );
	private final static String SPACE = " ";
	private final static String PRM_VIEW_QUERY = "select sum (premium) from V_TRN_PREMIUM_QUO_SUMMARY_PAS where QUOTATION_NO = ";

	/*VAT*/
	
	private final static String PRM_VIEW_QUERY_VAT = "select sum (premium) from V_TRN_PREMIUM_VAT_SUMMARY_PAS where QUOTATION_NO = ";

	private final static String AND = "and";
	private final static String ENDT_CONDITION = "ENDT_ID <= ";
	
	/*VAT*/
	private final static String PRM_ENDT_CONDITION = "PRM_ENDT_ID <= ";
	private final static String CLASS_CONDITION = "class_code = ";
	/*VAT*/
	private final static String PRM_CLASS_CONDITION = "PRM_CL_CODE = ";
	private final static String PREMIUM_POL = "T_TRN_PREMIUM";
	private final static String PREMIUM_QUO = "T_TRN_PREMIUM_QUO";
	private final static String QUERY_STRING = "Select sum(nvl(pr.PRM_PREMIUM,0))  From T_TRN_POLICY_QUO P, T_TRN_PREMIUM_QUO PR Where PR.PRM_POLICY_ID = P.POL_POLICY_ID  "
			+ "And PR.PRM_ENDT_ID = P.POL_ENDT_ID and pr.prm_cl_code = p.pol_class_code  And P.POL_LINKING_ID = P.POL_LINKING_ID "
			+ "and pr.prm_validity_start_date <= p.pol_validity_start_date  and pr.prm_validity_expiry_date  > p.pol_validity_start_date "
			+ "and trunc(pr.prm_validity_expiry_date) = '";

	private final static String POL_QUERY = "Select sum(nvl(pr.PRM_PREMIUM,0))  From T_TRN_POLICY P, T_TRN_PREMIUM PR Where PR.PRM_POLICY_ID = P.POL_POLICY_ID  "
			+ "And PR.PRM_ENDT_ID = P.POL_ENDT_ID and pr.prm_cl_code = p.pol_class_code  And P.POL_LINKING_ID = P.POL_LINKING_ID ";

	private static Long zeroVal = 0L;
	//public static String lob = null;

	private static double getProRatedPrmExistingRsk( TTrnPremiumQuo endorsedPremium, PolicyVO policyVO, TTrnPremiumQuo prevPrmDetails, int sectionId ){
		Date endtEffDate = policyVO.getEndEffectiveDate();
		
		/*Null check for endtEffDate has been introduced for Nill endorsement where user does not change anything */
		if( Utils.isEmpty( endtEffDate ) ) endtEffDate = policyVO.getScheme().getEffDate();

		Date polStartDate = policyVO.getScheme().getEffDate();
		// Date polEndDate = policyVO.getEndDate();
		Date polEndDate = policyVO.getPolExpiryDate();

		prmLogger.debug( "endtEffDate: " + endtEffDate + " " );
		prmLogger.debug( "polStartDate: " + polStartDate + " " );
		prmLogger.debug( "polEndDate: " + polEndDate + " " );

		if( Utils.isEmpty( polEndDate ) || Utils.isEmpty( endtEffDate ) ){
			throw new BusinessException( "", null, "Policy end date or endt eff date cannot be null" );
		}

		//Start: Policy extension changes
		/*Prorata calculation fixes*/
		double prevAnnualPrm = prevPrmDetails.getPrmPremiumActual().doubleValue();
		double prevPremium = prevPrmDetails.getPrmPremium().doubleValue();
		double currAnnualPrm = endorsedPremium.getPrmPremiumActual().doubleValue();

		Double proratedPremium = null;

		proratedPremium = getProratedPrm( polStartDate, prevPrmDetails.getPrmExpiryDate(), polEndDate, endtEffDate, prevPremium, currAnnualPrm ,prevAnnualPrm);

		//Note: Below code was there for calculating the pro rated premium for endorsements and is comment as the same logic is rewritten considering the policy extension		
		/*
			long polExpiryDays = getDifference( polEndDate, endtEffDate );
			if( polExpiryDays == 0 ){
				proratedPremium = prevPremium;
			}
			else{
				int totalDays = isLeapYear( polStartDate ) ? 366 : 365;
				proratedPremium = prevPremium + ( currAnnualPrm - prevPremium ) * polExpiryDays / totalDays;
				prmLogger.debug( "proratedPremium: " + prevPremium + "+" + "(" + currAnnualPrm + "-" + prevPremium + ")" + "*" + polExpiryDays + "/" + totalDays + "="
						+ proratedPremium );
			}

		*/
		//End: policy extension changes
		
		/*endorsedPremium.setPrmPremium( BigDecimal.valueOf( proratedPremium ) );

		 Set the calculated prorated premium back to the PolicyVO. 
		RiskGroupDetails rgd = PolicyUtils.getRiskGroupDetailsForProcessing( policyVO, sectionId);
		rgd.getPremium().setPremiumAmt( proratedPremium );*/
		
		return proratedPremium;
	}


	private static Date getDateWithoutTimeStamp( Date date ){
		DateFormat df = SimpleDateFormat.getDateInstance( SimpleDateFormat.SHORT );
		String dateString = df.format( date );
		Date dateWithoutTimestamp = null;

		try{
			dateWithoutTimestamp = df.parse( dateString );
		}
		catch( ParseException e ){
			System.out.println( "Exception in getDateWithoutTimeStamp method of Premium Helper" );
		}

		return dateWithoutTimestamp;
	}

	private static double getProRatedPrmNewRsk( TTrnPremiumQuo endorsedPremium, PolicyVO policyVO, int sectionId ){
		Date endtEffDate = policyVO.getEndEffectiveDate();

		/*  For quote we will have to consider pol effective date for premium calculation*/
		if( Utils.isEmpty( endtEffDate ) && policyVO.getIsQuote() && !Utils.isEmpty( policyVO.getScheme() ) ){
			endtEffDate = policyVO.getScheme().getEffDate();
		}
		//else if (Utils.isEmpty(endtEffDate) && !policyVO.getIsQuote())
		/*Null check for endtEffDate has been introduced for Nill endorsement where user does not change anything */
		else if( Utils.isEmpty( endtEffDate ) ){
			endtEffDate = policyVO.getScheme().getEffDate();
		}
		Date polStartDate = policyVO.getScheme().getEffDate();
		Date polEndDate = policyVO.getEndDate();
		prmLogger.debug( "endtEffDate: " + endtEffDate + " " );
		prmLogger.debug( "polStartDate: " + polStartDate + " " );
		prmLogger.debug( "polEndDate: " + polEndDate + " " );

		long polExpiryDays = 0;
		if( polEndDate != null ){

			polExpiryDays = getDifference( getDateWithoutTimeStamp( polEndDate ), getDateWithoutTimeStamp( endtEffDate ) );
		}

		prmLogger.debug( "polExpiryDays: " + polExpiryDays + " " );

		double currAnnualPrm = endorsedPremium.getPrmPremiumActual().doubleValue();
		Double proratedPremium = null;
		
		if( polExpiryDays == 0 ){
			proratedPremium = currAnnualPrm;
		}
		else{
			//Changes by Sonam.totalDays should always be 365 since rating engine returns premium for 365 days. to calculate premium per day divide by 365.
			//int totalDays = isLeapYear( polStartDate ) ? 366 : 365;
			int totalDays = SvcConstants.NO_OF_DAYS_YEAR;
			proratedPremium = ( currAnnualPrm * polExpiryDays ) / totalDays;
			prmLogger.debug( "proratedPremium: (" + currAnnualPrm + "*" + polExpiryDays + ") /" + totalDays + "=" + proratedPremium );
		}

		/*endorsedPremium.setPrmPremium( BigDecimal.valueOf( proratedPremium ) );

		 Set the calculated prorated premium back to the PolicyVO. 
		RiskGroupDetails rgd = PolicyUtils.getRiskGroupDetailsForProcessing( policyVO, sectionId);
		rgd.getPremium().setPremiumAmt( proratedPremium );*/
		
		return proratedPremium;
	}
	
	public static double totalCancelPrm( PolicyDataVO policyDataVO, PremiumVO premiumVO ){

		StringBuilder queryBuilder = new StringBuilder();
		double prm = 0.0;
		int scale = 1000;
		if(!Utils.isEmpty( policyDataVO.getCommonVO().getLob() )){
			Short policyType = Short.valueOf( Utils.getSingleValueAppConfig( "POLICY_TYPE_"+ policyDataVO.getCommonVO().getLob() ));
			scale =  Currency.getPolicyTypeScaleMap().get(policyType); 
			scale = Currency.getResovedScale(scale);
		}
		queryBuilder
				//.append( "select sum( round((NVL(prm_premium,0) - (NVL(PRM_PREMIUM_ACTUAL,0) * " )
				// commented above line as it was not considering cover level discount/loading for display during cancellation.	
				.append( "select sum( round((NVL(prm_premium,0) - (NVL(PRM_PREMIUM_ACTUAL,0) * ( 1 + NVL(PRM_LOAD_DISC,0)/100 ) * " )
				.append( getDifference( getDateWithoutTimeStamp( policyDataVO.getScheme().getExpiryDate() ), getDateWithoutTimeStamp( policyDataVO.getEndEffectiveDate() ) ) )
				.append( "/" )
				.append( isLeapYearForCancelPolicy( policyDataVO.getScheme().getEffDate() ) ? 366 : 365 )
				.append(")),")
				.append(scale)
				.append(")) from t_trn_premium where prm_policy_id = ?  and prm_endt_id <= ? and TRUNC( prm_validity_expiry_date ) = '31-DEC-49' and prm_basic_rsk_id <> 99999 " );
		List<Object> resultSet = DAOUtils.getSqlResultSingleColumnPas( queryBuilder.toString(), policyDataVO.getCommonVO().getPolicyId(),policyDataVO.getCommonVO().getEndtId() );
		if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
			 prm = Double.valueOf( String.valueOf( resultSet.get( 0 ) ) );
		}
		return prm;
	}

	public static double calculateProratedPremium( TTrnPremiumQuo endorsedPremium, PolicyVO policyVO, TTrnPremiumQuo prevPrmDetails, int sectionId ){
		double calculatedPremium = 0.0;
		
		/* If the premium are not different for the previous and the current record there is no need to calculate 
		 * pro - rated premium, infact there is no need to insert in the premium table */
		/*
		 * In case of Policy Extension always premium changes. 
		 * So skipping this step and proceeding with Prorated Premium Calculation directly.
		 */
		prmLogger.debug( "********************************Inside calculateProratedPremium***************************");
		
		if( !Utils.isEmpty( endorsedPremium ) && !Utils.isEmpty( prevPrmDetails ) && prevPrmDetails.getPrmExpiryDate().compareTo( endorsedPremium.getPrmExpiryDate() )==0 ){
			prmLogger.debug( "********Previous Prm: "+prevPrmDetails.getPrmPremiumActual().doubleValue()+"  Prm from rating : "+endorsedPremium.getPrmPremiumActual().doubleValue());
			prmLogger.debug("********Previous Prm: "+prevPrmDetails.getPrmPremiumActual().doubleValue()+"  Prm from rating : "+endorsedPremium.getPrmPremiumActual().doubleValue());
			prmLogger.debug("********Previous Prm expiry date: "+prevPrmDetails.getPrmExpiryDate()+"  Endorsed exp date : "+ endorsedPremium.getPrmExpiryDate() );
			if( !Utils.isEmpty( endorsedPremium.getPrmPremiumActual() ) && !Utils.isEmpty( prevPrmDetails.getPrmPremiumActual() ) ){
				
				Double diffrence = prevPrmDetails.getPrmPremiumActual().doubleValue() - endorsedPremium.getPrmPremiumActual().doubleValue() ;
				if( Math.abs( diffrence ) < Double.valueOf( Utils.getSingleValueAppConfig( "DEFAULT_ROUND_OFF" ) ) ){
					//Change for rounding issue - OMAN SBS rounded up to 3 digits
					//return SvcUtils.premiumRoundOff( prevPrmDetails.getPrmPremium().doubleValue() );
					return Double.parseDouble(Currency.getUnformttedScaledCurrency(prevPrmDetails.getPrmPremium().doubleValue()));
				}
			}
		}

		if( Utils.isEmpty( prevPrmDetails ) ){
			/* Its a newly added risk, for newly added risk previous premium will not be available */
			calculatedPremium = getProRatedPrmNewRsk( endorsedPremium, policyVO, sectionId );
		}
		else{
			/* If previous details is available then set the data as calculate the premium based on the previous premium and 
			 * current premium */
			calculatedPremium = getProRatedPrmExistingRsk( endorsedPremium, policyVO, prevPrmDetails, sectionId );
		}
		//Change for rounding issue - OMAN SBS rounded up to 3 digits
		//SvcUtils.premiumRoundOff( calculatedPremium );
		return Double.parseDouble(Currency.getUnformttedScaledCurrency(calculatedPremium));
	}


	/**
	 * Returns the dates absolute difference in days including the end date in calculation
	 * @param a
	 * @param b
	 * @return
	 */
	public static long getDifference( Date a, Date b ){
		long days = ( ( a.getTime() - b.getTime() ) / ( 1000 * 60 * 60 * 24 ) );
		days = days < 0 ? days * -1 : days;
		return ( days + 1 );
	}

	/*public static boolean isLeapYear( Date policyYear ){
		Calendar cal = Calendar.getInstance();
		cal.setTime( policyYear );
		GregorianCalendar greCal = new GregorianCalendar();
		return greCal.isLeapYear( cal.get( cal.YEAR ) ); //use calender.get() function to get year in YYYY format. 

		//Added for fix recurring issue related to Leap year(Endorsement flow etc)
		System.out.println("policyYear -- " +policyYear);
		Calendar cal = Calendar.getInstance();
		GregorianCalendar greCal = new GregorianCalendar();
		cal.setTime( policyYear );
		if(cal.get( Calendar.MONTH) > 2) {
			System.out.println( cal.get( cal.YEAR ) + 1);
			System.out.println("Year to be considered - 1 :"+ cal.get( cal.YEAR ) + 1  +"is leap :"+greCal.isLeapYear( cal.get( cal.YEAR ) + 1));
			return greCal.isLeapYear( cal.get( cal.YEAR ) + 1);
		}
		System.out.println("Year to be considered - 2 : "+  cal.get( cal.YEAR )  +"Is leap :"+ cal.get( cal.YEAR ) );
		return greCal.isLeapYear( cal.get( cal.YEAR ) ); //use calender.get() function to get year in YYYY format. 
		}
	*/
	
	public static boolean isLeapYear( Date policyYear ){
		Calendar cal = Calendar.getInstance();
		cal.setTime( policyYear );
		GregorianCalendar greCal = new GregorianCalendar();
		return greCal.isLeapYear( cal.get( cal.YEAR ) ); //use calender.get() function to get year in YYYY format. 
	}
	
	public static TTrnPremiumQuo getLastAnnualizedPrm(Short coverCode,Long policyId, long riskId ,long basicRiskId, Session hibernateSession, PolicyVO policyVO) {
		
		 Long endtId = (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ); 
		 
		 if(Utils.isEmpty( endtId )){
			 throw new BusinessException( "", null, "EntId is null in getLastAnnualizedPrm method" );
		 }
		 
		String tableToQuery=policyVO.getIsQuote()?PREMIUM_QUO:PREMIUM_POL;
		//Updated for RADAR Test Defect ID : 458406 
		String queryString = "SELECT prm_premium, prm_sum_insured,prm_expiry_date,prm_prepared_dt,prm_premium_actual FROM " + tableToQuery + " where prm_status <> " + SvcConstants.POL_STATUS_DELETED + "and  prm_policy_id ="
				+ policyId + " AND prm_basic_rsk_id =" + basicRiskId + " AND prm_cov_code =" + coverCode + "AND prm_rsk_id = " + riskId
				+ " and prm_endt_id = (select max(p.prm_endt_id) from " + tableToQuery + " p" + " where p.prm_endt_id < " + endtId + " and  p.prm_policy_id =" + policyId
				+ " AND p.prm_basic_rsk_id =" + basicRiskId + "AND p.prm_rsk_id =" + riskId + " and p.prm_status <>" + SvcConstants.POL_STATUS_DELETED + " AND prm_cov_code =" + coverCode +" )";

		Query query = hibernateSession.createSQLQuery( queryString );
		List<Object[]> result = query.list();

		TTrnPremiumQuo premiumQuo = null;
		if( !Utils.isEmpty( result ) && !Utils.isEmpty( result.get( 0 ) ) ){
			Object[] object = result.get( 0 );
			premiumQuo = new TTrnPremiumQuo();
			premiumQuo.setPrmPremium( (BigDecimal) object[ 0 ] );
			premiumQuo.setPrmSumInsured( (BigDecimal) object[ 1 ] );
			premiumQuo.setPrmExpiryDate( (Date) object[2] );
			premiumQuo.setPrmPreparedDt( (Date) object[3] );
			premiumQuo.setPrmPremiumActual( (BigDecimal) object[ 4] );
		}
		
		return premiumQuo;
		
	}

	public static double getSectionLevelSumInsured( ParVO parDetails ){

		Double sectionLevelSI = 0.0;

		if( !Utils.isEmpty( parDetails.getBldCover() ) ){
			sectionLevelSI += parDetails.getBldCover();
		}

		if( !Utils.isEmpty( parDetails.getCovers() ) && !Utils.isEmpty( parDetails.getCovers().getPropertyCoversDetails() ) ){
			java.util.List<PropertyRiskDetails> propertyCoversDetails = parDetails.getCovers().getPropertyCoversDetails();
			for( PropertyRiskDetails riskDetails : propertyCoversDetails ){
				if( !Utils.isEmpty( riskDetails.getCover() ) ){
					sectionLevelSI += riskDetails.getCover();
				}
			}
		}

		return sectionLevelSI;
	}

	public static double getSectionLevelPremium( ParVO parDetails ){

		Double sectionLevelPremium = 0.0;
		prmLogger.debug( "**********Get section level premium for PAR*************" );
		if( !Utils.isEmpty( parDetails.getBldPremium() ) && !Utils.isEmpty( parDetails.getBldPremium().getPremiumAmt() ) ){
			sectionLevelPremium += parDetails.getBldPremium().getPremiumAmt();
			prmLogger.debug( "Building Premium: " + sectionLevelPremium );
		}

		if( !Utils.isEmpty( parDetails.getCovers() ) && !Utils.isEmpty( parDetails.getCovers().getPropertyCoversDetails() ) ){
			java.util.List<PropertyRiskDetails> propertyCoversDetails = parDetails.getCovers().getPropertyCoversDetails();
			for( PropertyRiskDetails riskDetails : propertyCoversDetails ){

				if( !Utils.isEmpty( riskDetails.getPremium() ) && !Utils.isEmpty( riskDetails.getPremium().getPremiumAmt() ) ){
					sectionLevelPremium += riskDetails.getPremium().getPremiumAmt();
					prmLogger.debug( riskDetails.getDesc() + " Premium: " + riskDetails.getPremium().getPremiumAmt() );
				}

			}

		}
		prmLogger.debug( "**********Section level premium for PAR is " + sectionLevelPremium + com.Constant.CONST_ENDING );
		return sectionLevelPremium;
	}

	/**
	 * Returns cumulative section level premium for Money section
	 * @param Money
	 * @return
	 */
	public static double getSectionLevelPremium( MoneyVO moneyVO ){

		Double sectionLevelPremium = 0.0;
		prmLogger.debug( "**********Get section level premium for Money*************" );
		for( Contents content : moneyVO.getContentsList() ){
			if( !Utils.isEmpty( content ) && !Utils.isEmpty( content.getPremium() ) && !Utils.isEmpty( content.getPremium().getPremiumAmt() ) ){
				sectionLevelPremium += content.getPremium().getPremiumAmt();
			}
		}

		if( !Utils.isEmpty( moneyVO.getCashResidencePremium() ) && !Utils.isEmpty( moneyVO.getCashResidencePremium().getPremiumAmt() ) ){
			sectionLevelPremium += moneyVO.getCashResidencePremium().getPremiumAmt();
		}
		prmLogger.debug( "**********Section level premium for Money is " + sectionLevelPremium + com.Constant.CONST_ENDING );
		return sectionLevelPremium;
	}

	/**
	 * Returns cumulative section level sum insured for Money section
	 * @param Money
	 * @return
	 */
	public static double getSectionLevelSumInsured( MoneyVO moneyVO ){

		Double sectionLevelSI = 0.0;

		for( Contents content : moneyVO.getContentsList() ){
			if( !Utils.isEmpty( content ) && !Utils.isEmpty( content.getCover() ) ){
				sectionLevelSI += content.getCover().doubleValue();
			}
		}

		for( CashResidenceVO cashResidenceVO : moneyVO.getCashResDetails() ){
			if( !Utils.isEmpty( cashResidenceVO ) && !Utils.isEmpty( cashResidenceVO.getSumInsuredDets() ) && !Utils.isEmpty( cashResidenceVO.getSumInsuredDets().getSumInsured() ) ){
				sectionLevelSI += cashResidenceVO.getSumInsuredDets().getSumInsured();
			}
		}
		return sectionLevelSI;

	}

	/**
	 * Returns cumulative section level sum insured for WC 
	 * @param WCVO
	 * @return
	 */
	public static double getSectionLevelSumInsured( WCVO wcVO ){

		Double sectionLevelSI = 0.0;

		for( EmpTypeDetailsVO empTypeDetsVO : wcVO.getEmpTypeDetails() ){
			if( !Utils.isEmpty( empTypeDetsVO ) && !Utils.isEmpty( empTypeDetsVO.getWageroll() ) ){
				sectionLevelSI += empTypeDetsVO.getWageroll().doubleValue();
			}
		}
		return sectionLevelSI;
	}

	/**
	 * Returns cumulative section level premium for WC 
	 * @param wcVO
	 * @return
	 */
	public static double getSectionLevelPremium( WCVO wcVO ){

		Double sectionLevelPremium = 0.0;
		prmLogger.debug( "**********Get section level premium for Workers Compemsation*************" );
		for( EmpTypeDetailsVO empTypeDetsVO : wcVO.getEmpTypeDetails() ){
			if( !Utils.isEmpty( empTypeDetsVO ) && !Utils.isEmpty( empTypeDetsVO.getPremium() ) && !Utils.isEmpty( empTypeDetsVO.getPremium().getPremiumAmt() ) ){
				sectionLevelPremium += empTypeDetsVO.getPremium().getPremiumAmt();
			}
		}

		prmLogger.debug( "**********Section level premium for Workers Compemsation is " + sectionLevelPremium + com.Constant.CONST_ENDING );
		return sectionLevelPremium;
	}

	public static Double getCurrPremium( long polLinkingId, String fullFuncName, HibernateTemplate hibernateTemplate ){
		prmLogger.debug( "New Anualized premium from DB: " );
		return getPremium( "SELECT " + fullFuncName + "( " + polLinkingId + com.Constant.CONST_FROM_DUAL2, hibernateTemplate );
	}

	public static Double getOldPremium( long polLinkingId, long endtId, String fullFuncName, HibernateTemplate hibernateTemplate ){
		prmLogger.debug( "Old Anualized premium from DB: " );
		return getPremium( "SELECT " + fullFuncName + "( " + polLinkingId + "," + endtId + com.Constant.CONST_FROM_DUAL2, hibernateTemplate );
	}

	private static Double getPremium( String sqlquery, HibernateTemplate hibernateTemplate ){
		Session session = null;
		session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery( sqlquery );
		List<Object> result = query.list();
		BigDecimal premium = BigDecimal.valueOf( 0 );
		if( !Utils.isEmpty( result ) && ( result.size() > 0 ) ){
			if( !Utils.isEmpty( result.get( 0 ) ) ){
				premium = (BigDecimal) result.get( 0 );
			}
		}
		prmLogger.debug( " " + premium.doubleValue() + " " );
		return premium.doubleValue();
	}

	public static void logPremiumInfo( String... logs ){
		for( String log : logs ){
			prmLogger.debug( log );
		}
	}

	/*
	 * Oman Multibranching : calculating Commission without tax
	 */
	public static DataHolderVO<HashMap<String, Double>> getOldNewPremiumNoTax( long polLinkingId, Long currEndtId ){
		DataHolderVO<HashMap<String, Double>> premiumHolder = getOldNewPremium( polLinkingId, currEndtId, "getOldNewProPrmNoTax" );
		return premiumHolder;
	}

	public static DataHolderVO<HashMap<String, Double>> getOldNewPremiumNoTaxForQuote( long polLinkingId, Long currEndtId ){
		DataHolderVO<HashMap<String, Double>> premiumHolder = getOldNewPremium( polLinkingId, currEndtId, "getOldNewProPrmNoTaxQuote" );
		return premiumHolder;
	}

	public static DataHolderVO<HashMap<String, Double>> getOldNewPremium( long polLinkingId, Long currEndtId ){

		DataHolderVO<HashMap<String, Double>> premiumHolder = getOldNewPremium( polLinkingId, currEndtId, "getOldNewProPrm" );

		return premiumHolder;
	}

	public static DataHolderVO<HashMap<String, Double>> getOldNewPremium( long polLinkingId, Long currEndtId, String taxable ){

		PASStoredProcedure sp = null;
		DataHolderVO<HashMap<String, Double>> premiumHolder = null;

		if( taxable.equalsIgnoreCase( "getOldNewProPrmNoTax" ) || taxable.equalsIgnoreCase( "getOldNewProPrmNoTaxQuote" ) ){

			sp = (PASStoredProcedure) Utils.getBean( taxable );

		}
		else{

			sp = (PASStoredProcedure) Utils.getBean( "getOldNewProPrm" );

		}

		try{
			Map results = sp.call( polLinkingId, currEndtId );
			HashMap<String, Double> premium = new HashMap<String, Double>();
			if( !Utils.isEmpty( results.get( SvcConstants.NEW_ANNUALIZED_PREMIUM ) ) ){
				premium.put( SvcConstants.NEW_ANNUALIZED_PREMIUM, Double.valueOf( results.get( SvcConstants.NEW_ANNUALIZED_PREMIUM ).toString() ) );
			}
			else{
				premium.put( SvcConstants.NEW_ANNUALIZED_PREMIUM, 0.0 );
			}
			if( !Utils.isEmpty( results.get( SvcConstants.OLD_ANNUALIZED_PREMIUM ) ) ){
				premium.put( SvcConstants.OLD_ANNUALIZED_PREMIUM, Double.valueOf( results.get( SvcConstants.OLD_ANNUALIZED_PREMIUM ).toString() ) );
			}
			else{
				premium.put( SvcConstants.OLD_ANNUALIZED_PREMIUM, 0.0 );
			}
			premiumHolder = new DataHolderVO<HashMap<String, Double>>();
			premiumHolder.setData( premium );
		}
		catch( DataAccessException e ){
			throw new BusinessException( "cmn.unknownError", e, "An exception occured while getting ols and new annualized premium" );
		}

		return premiumHolder;
	}

	
	
	/*
	 * 
	 * 
	 * Updates policy table with govt tax and VAT tax from Premium tables
	 * Updates vat code from T_MAS_POLICY_RATING
	 */
	public static void updatePolicyPremium( PolicyVO policyVO, TTrnPolicyQuo policyQuo, HibernateTemplate ht ){
		
		Long endtToProcess = DAOUtils.getEndtToProcess( ht, policyVO );
		
		StringBuilder class_premium_query = new StringBuilder();
		StringBuilder class_premium_query_tax = new StringBuilder();
		StringBuilder class_premium_query_vat = new StringBuilder();
		StringBuilder class_rating_query_vat_code = new StringBuilder();
		double  premiumValue=0.0;
		 double vatableAmt=0.0;
		prmLogger.debug( "updatePolicyPremium  - update class level premium to policy Table " );
		class_premium_query.append( PRM_VIEW_QUERY ).append( policyVO.getQuoteNo() ).append( SPACE ).append( AND ).append( SPACE ).append( CLASS_CONDITION ).append( SPACE )
		.append( policyQuo.getPolClassCode() ).append( SPACE ).append( AND ).append( SPACE ).append( ENDT_CONDITION ).append( SPACE ).append( endtToProcess ).append( AND ).append( SPACE )
						   .append( "COVER_CODE <> ").append( SvcConstants.SC_PRM_COVER_GOVT_TAX ).append( SPACE ).append( AND ).append( SPACE ).append( "COVER_CODE <> ").append(SvcConstants.SC_PRM_COVER_VAT_TAX)  ;
		
		class_premium_query_tax.append( PRM_VIEW_QUERY )
		   .append( policyVO.getQuoteNo() ).append( SPACE ).append( AND ).append( SPACE )
		   .append( CLASS_CONDITION ).append( SPACE ).append( policyQuo.getPolClassCode() ).append( SPACE )
		   .append( AND ).append( SPACE ).append( ENDT_CONDITION ).append( SPACE ).append( policyQuo.getId().getPolEndtId() ).append( AND ).append( SPACE )
		   .append( "COVER_CODE = ").append( SvcConstants.SC_PRM_COVER_GOVT_TAX )  ;
		
		
	
		/*VAT*/
		class_premium_query_vat.append( PRM_VIEW_QUERY_VAT )
		   .append( policyVO.getQuoteNo() ).append( SPACE ).append( AND ).append( SPACE )
		   .append( CLASS_CONDITION ).append( SPACE ).append( policyQuo.getPolClassCode() ).append( SPACE )
		   .append( AND ).append( SPACE ).append( ENDT_CONDITION ).append( SPACE ).append( policyQuo.getId().getPolEndtId() ).append( AND ).append( SPACE )
		   .append( "COVER_CODE = ").append( SvcConstants.SC_PRM_COVER_VAT_TAX )  ;
		
		
		
	
		
		prmLogger.debug("Query generated: " + class_premium_query);
		
		Query query = ht.getSessionFactory().getCurrentSession().createSQLQuery(class_premium_query.toString());
		List<Object> result  = query.list();
		if(!Utils.isEmpty(result) && !Utils.isEmpty(result.get(0))){
			// Added for Bahrain customized rounded of SBS
			 
            if(SvcUtils.isSBSBahrainPolicy(policyVO)) {
            	premiumValue=0.0;
            	vatableAmt=((BigDecimal) result.get( 0 )).doubleValue();
            	System.out.println("vatable amt------------------------------------ "+vatableAmt);
             premiumValue= SvcUtils.getRoundingOffBah(((BigDecimal) result.get( 0 )).doubleValue()) ;
             prmLogger.info("The  Pol Premium value is : "+premiumValue );
                  policyQuo.setPolPremium(BigDecimal.valueOf(premiumValue));
                  
            }else {
                         policyQuo.setPolPremium( (BigDecimal) result.get( 0 ) );
            }
            
     }


		
		query = ht.getSessionFactory().getCurrentSession().createSQLQuery(class_premium_query_tax.toString());
		result  = query.list();
		if(!Utils.isEmpty(result) && !Utils.isEmpty(result.get(0))){
			policyQuo.setPolGovernmentTax( (BigDecimal) result.get( 0 )  )   ;
		}
		
		
		/*VAT*/
		query = ht.getSessionFactory().getCurrentSession().createSQLQuery(class_premium_query_vat.toString());
		result  = query.list();
		if(!Utils.isEmpty(result) && !Utils.isEmpty(result.get(0))){
			policyQuo.setPolVatTax(( (BigDecimal) result.get( 0 )  ) )  ;
		}
		else{
			policyQuo.setPolVatTax(new BigDecimal("0.0"));
		}
		TTrnPremiumQuo oldPrmRec = getLastSplCoverPrm( Long.valueOf( policyQuo.getId().getPolPolicyId() ), Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ),
				Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ), policyQuo.getId().getPolEndtId(), SvcConstants.SC_PRM_COVER_VAT_TAX, ht.getSessionFactory()
						.getCurrentSession(), policyVO );
		Double oldVatPrm =0.0;
		Long previousEndtId = DAOUtils.getPreviousEndtId(ht, policyVO);
		String queryStringExcVAT = getPolQueryVatExcludePrevEndt( policyVO, policyQuo,previousEndtId , ht);
		Session session = ht.getSessionFactory().getCurrentSession();
		
		BigDecimal prevPremiumExcVAT=new BigDecimal("0.0");
		
		 prevPremiumExcVAT=getPrm(queryStringExcVAT,session);
		
		
		
		
		if(!Utils.isEmpty(oldPrmRec) && !Utils.isEmpty(oldPrmRec.getPrmPremium())){
			oldVatPrm=oldPrmRec.getPrmPremium().doubleValue();
			}
			else{
				oldVatPrm=0.0;
			}
		
		// Added for Bahrain vat : premiumValue which is non rounded of with customized logic
		 BigDecimal vattableAmt = new BigDecimal("0.0");
		 if(SvcUtils.isSBSBahrainPolicy(policyVO)) {
				 vattableAmt = new BigDecimal( (SvcUtils.calculateSBSVatTaxAmount(policyVO,vatableAmt,policyQuo,prevPremiumExcVAT.doubleValue()).get("vatbleAmt")));

		 }else {
				 vattableAmt = new BigDecimal( (SvcUtils.calculateSBSVatTaxAmount(policyVO,policyQuo.getPolPremium().doubleValue(),policyQuo,prevPremiumExcVAT.doubleValue()).get("vatbleAmt")));

		 }
		
		// condition check when, POL_VATABLE_PRM(VAT Premium field) column should only be updated when VAT is enabled.
		Boolean vatResult = DAOUtils.isVatEnabled();
		//System.out.println("Value in PremiumHelper.java--" +vatResult+":: Vatable Amount :"+vattableAmt);
		if (!Utils.isEmpty(vattableAmt) && vatResult.equals(Boolean.TRUE)) {
			policyQuo.setPolVattableAmt(BigDecimal.valueOf(SvcConstants.zeroVal));
		}else{
			policyQuo.setPolVattableAmt(vattableAmt);
		}
		//System.out.println("Value in PremiumHelper.java--" +vatResult+":: Vatable Amount :"+policyQuo.getPolVattableAmt());
		prmLogger.debug("Exiting updatePolicyPremium");
	}

	/**
	 * Checks if suminsured value is null or greater than 0
	 * @param content
	 * @return
	 */
	public static boolean isContentSumInsuredZero( Contents content ){
		if( Utils.isEmpty( content.getCover() ) || content.getCover().compareTo( BigDecimal.valueOf( 0.0 ) ) == 0 ){
			return true;
		}
		return false;
	}

	/**
	 * Returns refundPremiumAmt which is calculated basis below formula
	 * ( (currentPremiumAmt * polExpiryDays) - currentPremiumAmt) / totalDays ) where
	 * 1. polExpiryDays will be difference in Pol_Expiry_Date and 
	 * 	  Pol_Cancellation_Effective_Date(in no of days)
	 * 2. totalDays will be 366 in case of LEAP_YEAR else it will be 365 
	 * @param policyVO
	 * @param currentPremiumAmt
	 * @return
	 */
	public static Double getRefundAmountForCancelPolicy(PolicyVO policyVO,Double currentPremiumAmt) {
		long polExpiryDays = PremiumHelper.getDifference(policyVO.getPolExpiryDate(),policyVO.getEndEffectiveDate());
		int totalDays = (int)PremiumHelper.getDifference(policyVO.getPolExpiryDate(),policyVO.getScheme().getEffDate() );
		
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd" );
		Double refundAmount = 0.00;
		if(! sdf.format( policyVO.getScheme().getEffDate() ).equalsIgnoreCase( sdf.format( policyVO.getEndEffectiveDate() ) ) ){
			refundAmount = currentPremiumAmt - (currentPremiumAmt * polExpiryDays/totalDays);
		}
		return SvcUtils.premiumRoundOff( refundAmount );
	}
	
	/*
	 * The view returns only location information, SI and premium is fetched separately for each location from the DB function 
	 * GET_PRM_SI_SUM_LOC
	 */
	public static BigDecimal getPrmOrSI( VTrnPasPremSumLoc pasPremSec, char siOrPrm, Long endtId, Character polOrQuo, HibernateTemplate ht, Integer secId ){

		String sqlquery = "SELECT pkg_pas_utils.GET_PRM_SI_SUM_LOC( " + pasPremSec.getId().getLinkingId() + "," + endtId + "," + pasPremSec.getId().getPrmBasicRskCode() + ","
				+ pasPremSec.getId().getPrmBasicRskId() + "," + "'" + siOrPrm + "'" + "," + "'" + polOrQuo + "'" + "," + "'" + secId + "'" + com.Constant.CONST_FROM_DUAL2;

		Session session = ht.getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery( sqlquery );
		List<Object> result = query.list();
		if( Utils.isEmpty( result ) ){
			return BigDecimal.valueOf( 0 );
		}

		return ( (BigDecimal) result.get( 0 ) );

	}

	public static void updatePolicyFee( PolicyVO policyVO, TTrnPolicyQuo policyQuo, HibernateTemplate ht ){

		short polFeeCover = SvcConstants.SC_PRM_COVER_POLICY_FEE.shortValue();

		List<TTrnPremiumQuo> premiumQuos = getSplCovtoUpdate( policyVO, policyQuo, polFeeCover, ht );/*  */

		Long endtIdToProcess = SvcUtils.getLatestEndtId( policyVO );
		if( !Utils.isEmpty( policyVO.getNewEndtId() ) && !Utils.isEmpty( policyVO.getEndtId() ) ){
			endtIdToProcess = DAOUtils.getEndtToProcess( ht, policyVO );
		}

		if( Utils.isEmpty( premiumQuos ) || ( !Utils.isEmpty( premiumQuos ) && premiumQuos.size() == 0 ) ){
			// case of nil endorsement
			return;
		}

		if( ( !Utils.isEmpty( premiumQuos ) && premiumQuos.size() > 1 ) ){
			// for an endt there cannot be more than one rec for a special cover code
			throw new BusinessException( "pas.dao.cmn.unknownError", null, "More than 1 row is returned for cover code 100 for endt no_1" + policyQuo.getId().getPolEndtId() );
		}

		//String queryString = null;

		/*
		 * Query to get class level prm
		 */
		/*queryString = policyVO.getIsQuote() ? getQuoteQuery( policyVO, policyQuo,endtIdToProcess, ht ) : getPolQuery( policyVO, policyQuo,endtIdToProcess, ht );

		Session session = ht.getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery( queryString.toString() );
		List<Object> result = query.list();
		BigDecimal clPrm = new BigDecimal( 0 );
		if( !Utils.isEmpty( result ) && !Utils.isEmpty( result.get( 0 ) ) ){
			clPrm = (BigDecimal) result.get( 0 );
		}*/

		TTrnPremiumQuo premiumQuo = premiumQuos.get( 0 );

		TTrnPremiumQuo oldPrmRec = getLastSplCoverPrm( Long.valueOf( policyQuo.getId().getPolPolicyId() ), Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ),
				Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ), endtIdToProcess, polFeeCover, ht.getSessionFactory().getCurrentSession(), policyVO );

		Double policyFees = ( policyVO.getPremiumVO().getPolicyFees() );
		premiumQuo.setPrmPremium( BigDecimal.valueOf( policyFees ) );
		premiumQuo.setPrmRate( BigDecimal.ZERO );
		//premiumQuo.setPrmRateType(BigDecimal.ZERO);
		if( !Utils.isEmpty( oldPrmRec ) && !Utils.isEmpty( oldPrmRec.getPrmPremium() )){
			premiumQuo.setPrmOldPremium( oldPrmRec.getPrmPremium() );
		}
		else{
			premiumQuo.setPrmOldPremium( BigDecimal.ZERO );
		}

		ht.update( premiumQuo );
		ht.flush();

		/*
		 * To handle scenario where user changes discount to loading or visa versa during quote versioning  or edit quote
		 
		if( policyVO.getIsQuote() ){
			
			//Get opposite cover code of discLoadCover (51 -> 20 or 20 -> 51)
			short discLoadReverts = policyVO.getPremiumVO().getDiscOrLoadPerc() <= 0.0 ? SvcConstants.SC_PRM_COVER_LOADING : SvcConstants.SC_PRM_COVER_DISCOUNT;

			List<TTrnPremiumQuo> premiumQuoReset = getSplCovtoUpdate( policyVO, policyQuo, discLoadReverts, ht );

			if( Utils.isEmpty( premiumQuoReset ) || ( !Utils.isEmpty( premiumQuoReset ) && premiumQuoReset.size() == 0 ) ){
				// case of nil endorsement
				return;
			}
			if( ( !Utils.isEmpty( premiumQuoReset ) && premiumQuos.size() > 1 ) ){
				throw new BusinessException( "pas.dao.cmn.unknownError", null, "More than 1 row is returned for cover code 51/20 for endt no " + policyQuo.getId().getPolEndtId() );
			}

			// reset the prm and rate
			TTrnPremiumQuo premiumQuoRow = premiumQuoReset.get( 0 );
			premiumQuoRow.setPrmPremium( BigDecimal.valueOf( SvcConstants.zeroVal ) );
			premiumQuoRow.setPrmRate( BigDecimal.valueOf( SvcConstants.zeroVal ) );
			ht.update( premiumQuoRow );
			ht.flush();
		}*/

	}
	public static void updateSpecialCovers( PolicyVO policyVO, TTrnPolicyQuo policyQuo, HibernateTemplate ht ){

		// Get the cover code of discount or loading
		short discLoadCover = policyVO.getPremiumVO().getDiscOrLoadPerc() > 0.0 ? SvcConstants.SC_PRM_COVER_LOADING : SvcConstants.SC_PRM_COVER_DISCOUNT;

		List<TTrnPremiumQuo> premiumQuos = getSplCovtoUpdate( policyVO, policyQuo, discLoadCover, ht );/*  */

		Long endtIdToProcess = SvcUtils.getLatestEndtId( policyVO );
		if( !Utils.isEmpty( policyVO.getNewEndtId() ) && !Utils.isEmpty( policyVO.getEndtId() ) ){
			endtIdToProcess = DAOUtils.getEndtToProcess( ht, policyVO );
		}

		if( Utils.isEmpty( premiumQuos ) || ( !Utils.isEmpty( premiumQuos ) && premiumQuos.size() == 0 ) ){
			// case of nil endorsement
			ht.evict( premiumQuos );
			return;
		}

		if( ( !Utils.isEmpty( premiumQuos ) && premiumQuos.size() > 1 ) ){
			// for an endt there cannot be more than one rec for a special cover code
			ht.evict( premiumQuos );
			throw new BusinessException( "pas.dao.cmn.unknownError", null, "More than 1 row is returned for cover code 51/20 for endt no " + policyQuo.getId().getPolEndtId() );
		}

		String queryString = null;

		/*
		 * Query to get class level prm
		 */
		queryString = policyVO.getIsQuote() ? getQuoteQuery( policyVO, policyQuo, endtIdToProcess, ht ) : getPolQuery( policyVO, policyQuo, endtIdToProcess, ht );

		Session session = ht.getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery( queryString.toString() );
		List<Object> result = query.list();
		BigDecimal clPrm = new BigDecimal( 0 );
		if( !Utils.isEmpty( result ) && !Utils.isEmpty( result.get( 0 ) ) ){
			clPrm = (BigDecimal) result.get( 0 );
		}

		TTrnPremiumQuo premiumQuo = premiumQuos.get( 0 );

		TTrnPremiumQuo oldPrmRec = getLastSplCoverPrm( Long.valueOf( policyQuo.getId().getPolPolicyId() ), Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ),
				Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ), endtIdToProcess, discLoadCover, ht.getSessionFactory().getCurrentSession(), policyVO );

		premiumQuo.setPrmPremium( SvcUtils.setDiscLoadPrm( policyVO, clPrm ) );
		premiumQuo.setPrmRate( BigDecimal.valueOf( policyVO.getPremiumVO().getDiscOrLoadPerc() ) );
		premiumQuo.setPrmRateType( SvcUtils.setDiscLoadRateType( policyVO ) );

		if( !Utils.isEmpty( oldPrmRec ) && !Utils.isEmpty( oldPrmRec.getPrmPremium() )){
			premiumQuo.setPrmOldPremium( oldPrmRec.getPrmPremium() );
		}
		else{
			premiumQuo.setPrmOldPremium( BigDecimal.ZERO );
		}

		ht.update( premiumQuo );

		/*
		 * To handle scenario where user changes discount to loading or visa versa during quote versioning  or edit quote
		 */
		if( policyVO.getIsQuote() ){

			//Get opposite cover code of discLoadCover (51 -> 20 or 20 -> 51)
			short discLoadReverts = policyVO.getPremiumVO().getDiscOrLoadPerc() <= 0.0 ? SvcConstants.SC_PRM_COVER_LOADING : SvcConstants.SC_PRM_COVER_DISCOUNT;

			List<TTrnPremiumQuo> premiumQuoReset = getSplCovtoUpdate( policyVO, policyQuo, discLoadReverts, ht );

			if( Utils.isEmpty( premiumQuoReset ) || ( !Utils.isEmpty( premiumQuoReset ) && premiumQuoReset.size() == 0 ) ){
				// case of nil endorsement
				return;
			}
			if( ( !Utils.isEmpty( premiumQuoReset ) && premiumQuos.size() > 1 ) ){
				throw new BusinessException( "pas.dao.cmn.unknownError", null, "More than 1 row is returned for cover code 51/20 for endt no " + policyQuo.getId().getPolEndtId() );
			}

			// reset the prm and rate
			TTrnPremiumQuo premiumQuoRow = premiumQuoReset.get( 0 );
			premiumQuoRow.setPrmPremium( BigDecimal.valueOf( SvcConstants.zeroVal ) );
			premiumQuoRow.setPrmRate( BigDecimal.valueOf( SvcConstants.zeroVal ) );
			ht.update( premiumQuoRow );
			ht.flush();
		}

	}

	/**
	 * 
	 * @param policyVO
	 * @param ht
	 * gets the special premium for loading the page
	 */
	public static void updateSpecialPremium( PolicyVO policyVO, HibernateTemplate ht ){

		//Long basePolId = DAOUtils.getBaseSectionPolicyId( policyVO.getPolLinkingId(), SvcUtils.getLatestEndtId( policyVO ),policyVO.getIsQuote(), ht);

		Long endtIdToProcess = SvcUtils.getLatestEndtId( policyVO );
		if( !Utils.isEmpty( policyVO.getNewEndtId() ) && !Utils.isEmpty( policyVO.getEndtId() ) ){
			endtIdToProcess = DAOUtils.getEndtToProcess( ht, policyVO );
		}

		List<TTrnPremiumQuo> premiumQuoDisc = getSplPrmRec( policyVO, SvcConstants.SC_PRM_COVER_DISCOUNT, endtIdToProcess, ht ); /**/

		List<TTrnPremiumQuo> premiumQuoLoad = getSplPrmRec( policyVO, SvcConstants.SC_PRM_COVER_LOADING, endtIdToProcess, ht ); /**/

		TTrnPremiumQuo premiumQuoSpl = null;

		/* Size check is not required as there is a possibility getting multiple records , hence removing the same */
		if( ( !Utils.isEmpty( premiumQuoDisc ) ) || ( !Utils.isEmpty( premiumQuoLoad ) ) ){
			boolean discFlag = false;
			boolean loadFlag = false;
			if( !Utils.isEmpty( premiumQuoDisc ) ){
				if( !Utils.isEmpty( premiumQuoDisc.get( 0 ) ) && BigDecimal.ZERO.compareTo( premiumQuoDisc.get( 0 ).getPrmRate() ) != SvcConstants.zeroVal ){
					/*
					 * Set the premium only if the record had the endt equal to the endt under process
					 */
					//		if(( ( Long )premiumQuoDisc.get( 0 ).getPrmEndtId()).equals( endtIdToProcess )){
					premiumQuoSpl = premiumQuoDisc.get( 0 );
					discFlag = true;
					//		}
				}
			}
			if( !Utils.isEmpty( premiumQuoLoad ) ){
				if( !Utils.isEmpty( premiumQuoLoad.get( 0 ) ) && BigDecimal.ZERO.compareTo( premiumQuoLoad.get( 0 ).getPrmRate() ) != SvcConstants.zeroVal ){
					/*
					 * Set the premium only if the record had the endt equal to the endt under process
					 */
					//		if(( ( Long )premiumQuoLoad.get( 0 ).getPrmEndtId()).equals( endtIdToProcess )){
					premiumQuoSpl = premiumQuoLoad.get( 0 );
					loadFlag = true;
					//	}
				}
			}
			if( discFlag && loadFlag ){
				throw new BusinessException( "cmn.unknownError", null, "Non zero value for both discount and load" );
			}
		}
		else{
		    
		    prmLogger.debug("No entry for special cover 20 and 51 present");
		    if( Utils.isEmpty( policyVO.getPremiumVO() ) ){
			policyVO.setPremiumVO( new PremiumVO() );
		    }
			policyVO.getPremiumVO().setDiscOrLoadAmt( BigDecimal.ZERO );
		    policyVO.getPremiumVO().setDiscOrLoadPerc(Double.valueOf(String.valueOf(SvcConstants.zeroVal)));
		    policyVO.getPremiumVO().setPremiumAmt(Double.valueOf(String.valueOf(SvcConstants.zeroVal)));
			//throw new BusinessException( com.Constant.CONST_CMN_UNKNOWNERROR, null, "No entry for special cover 20 and 51 present" );
		}

		if( Utils.isEmpty( policyVO.getPremiumVO() ) ){
			policyVO.setPremiumVO( new PremiumVO() );
		}
		PremiumVO premiumVO = policyVO.getPremiumVO();
		if( !Utils.isEmpty( premiumQuoSpl ) ){
			if( !Utils.isEmpty( premiumQuoSpl ) && !Utils.isEmpty( premiumQuoSpl.getPrmPremium() ) ){

				BigDecimal discPrm = getSplPrm( policyVO, endtIdToProcess, SvcConstants.SC_PRM_COVER_DISCOUNT, ht );

				BigDecimal loadPrm = getSplPrm( policyVO, endtIdToProcess, SvcConstants.SC_PRM_COVER_LOADING, ht );

				BigDecimal splPrm = BigDecimal.valueOf( SvcConstants.zeroVal );
				boolean discFlag = false;
				boolean loadFlag = false;
				if( !Utils.isEmpty( discPrm ) && BigDecimal.ZERO.compareTo( discPrm ) != SvcConstants.zeroVal ){
					splPrm = discPrm;
					discFlag = true;
				}
				if( !Utils.isEmpty( loadPrm ) && BigDecimal.ZERO.compareTo( loadPrm ) != SvcConstants.zeroVal ){
					splPrm = loadPrm;
					loadFlag = true;
				}

				if( discFlag && loadFlag ){
					throw new BusinessException( "cmn.unknownError", null, "Non zero value for both discount and load while loading" );
				}

				premiumVO.setDiscOrLoadAmt( splPrm );
			}
			if( !Utils.isEmpty( premiumQuoSpl ) && !Utils.isEmpty( premiumQuoSpl.getPrmRate() ) ){
				premiumVO.setDiscOrLoadPerc( premiumQuoSpl.getPrmRate().doubleValue() );
			}
			if( !Utils.isEmpty( premiumQuoSpl ) && !Utils.isEmpty( premiumQuoSpl.getPrmRateType() ) ){
				premiumVO.setLoading( SvcUtils.setLoadingFlag( premiumQuoSpl.getPrmRateType() ) );
			}

		}
		else{
			premiumVO.setDiscOrLoadAmt( BigDecimal.ZERO );
			premiumVO.setDiscOrLoadPerc( Double.valueOf( SvcConstants.zeroVal ) );
		}

	}

	public static void updatePolicyFee( PolicyVO policyVO, HibernateTemplate ht ){
		Long endtIdToProcess = SvcUtils.getLatestEndtId( policyVO );
		if( !Utils.isEmpty( policyVO.getNewEndtId() ) && !Utils.isEmpty( policyVO.getEndtId() ) ){
			endtIdToProcess = DAOUtils.getEndtToProcess( ht, policyVO );
		}

		if( Utils.isEmpty( policyVO.getPremiumVO() ) ){
			policyVO.setPremiumVO( new PremiumVO() );
		}

		List<TTrnPremiumQuo> premiumQuoList = ht
				.find( "from TTrnPremiumQuo prm where  "
						+ "prm.id.prmPolicyId in (select distinct(pol.id.polPolicyId) from TTrnPolicyQuo pol where pol.polLinkingId = ? and pol.id.polEndtId <= ? ) and prm.prmEndtId <= ? and prm.id.prmCovCode = ? and prm.id.prmBasicRskCode=? order by prm.prmEndtId desc",
						policyVO.getPolLinkingId(), endtIdToProcess, endtIdToProcess, (short) 100, Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ) );

		for( TTrnPremiumQuo premiumQuo : premiumQuoList ){
			if( premiumQuo.getPrmClCode() == 2 ){
				policyVO.getPremiumVO().setPolicyFees( ( premiumQuo.getPrmPremium().doubleValue() ) );
				break;
			}
			else if( premiumQuo.getPrmClCode() == 7 ){
				policyVO.getPremiumVO().setPolicyFees( ( premiumQuo.getPrmPremium().doubleValue() ) );
			}
		}

	}

	/**
	 * Gets the special premium amount for the passed cover code
	 */
	private static BigDecimal getSplPrm( PolicyVO policyVO, Long endtIdToProcess, Short splCovCode, HibernateTemplate ht ){
		return BigDecimal.valueOf( getPremium( "select pkg_pas_utils.get_spl_cover_preium (" + policyVO.getPolLinkingId() + "," + endtIdToProcess + "," + splCovCode + ",'"
				+ ( policyVO.getIsQuote() ? "Q" : "P" ) + com.Constant.CONST_FROM_DUAL1, ht ) );
	}
	
	/**
	 * Gets the special premium amount for the passed cover code
	 */
	public static Double getSplPrm( CommonVO commonVO, Long endtIdToProcess, Short[] splCovCodes, HibernateTemplate ht ){
		double totalVal = 0.0;
		for(Short splCovCode: splCovCodes){
			totalVal += getPremium( "select PKG_PAS_QUO_POL_HOME.GET_SPL_COVER_PREMIUM (" + commonVO.getPolicyId() + "," + endtIdToProcess + "," + splCovCode + ",'"
					+ ( commonVO.getIsQuote() ? "Q" : "P" ) + com.Constant.CONST_FROM_DUAL1, ht ) ;
		}
		return  totalVal ;
	}
	
	/**
	 * Gets the special premium amount for the passed cover code
	 */
	public static Double getSplPrmPercentage( Long polId, Long endtIdToProcess, boolean isQuote, Short splCovCode, HibernateTemplate ht ){

		/*return getPremium( "select PKG_PAS_QUO_POL_HOME.FN_GET_SPL_COVER_PERCENTAGE (" + polId + "," + endtIdToProcess + "," + splCovCode + ",'"
				+ ( isQuote ? "Q" : "P" ) + com.Constant.CONST_FROM_DUAL, ht ) ;*/
		double discLoad = 0.0;
		List<Object> result = DAOUtils.getSqlResultSingleColumn( "select PKG_PAS_QUO_POL_HOME.FN_GET_SPL_COVER_PERCENTAGE (" + polId + "," + endtIdToProcess + "," + splCovCode + ",'"
				+ ( isQuote ? "Q" : "P" ) + com.Constant.CONST_FROM_DUAL1, ht );

		if( !Utils.isEmpty( result ) ){
			discLoad = ( (BigDecimal) result.get( 0 ) ).doubleValue() ;
		}

		return discLoad;
	}

	/**
	 * Gets the special premium pojo for the passed cover code
	 */
	private static List<TTrnPremiumQuo> getSplPrmRec( PolicyVO policyVO, Short splCovCode, Long endtIdToProcess, HibernateTemplate ht ){
		return ht
				.find( "from TTrnPremiumQuo prm where  "
						+ "prm.id.prmPolicyId in (select distinct(pol.id.polPolicyId) from TTrnPolicyQuo pol where pol.polLinkingId = ? and pol.id.polEndtId <= ? ) and prm.prmEndtId <= ? and prm.id.prmCovCode = ? and prm.id.prmBasicRskCode=? order by prm.prmEndtId desc",
						policyVO.getPolLinkingId(), endtIdToProcess, endtIdToProcess, splCovCode, Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ) );
	}

	/**
	 * Gets the special premium pojo of the recent rec to update
	 */
	private static List<TTrnPremiumQuo> getSplCovtoUpdate( PolicyVO policyVO, TTrnPolicyQuo policyQuo, short discLoadCover, HibernateTemplate ht ){
		return ht
			.find(
				"from TTrnPremiumQuo prm where prm.prmValidityExpiryDate= ? and prm.id.prmPolicyId = ? and prm.prmEndtId <= ? and prm.prmClCode = ? and prm.id.prmCovCode = ? and prm.id.prmBasicRskCode=? order by prm.prmEndtId desc",
				SvcConstants.EXP_DATE, policyQuo.getId().getPolPolicyId(), policyQuo.getId().getPolEndtId(), policyQuo.getPolClassCode(), discLoadCover , 
				Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ) );
	}
	/**
	 * returns the query for getting the prm for a specific class in quote. Gets always the latest 
	 * @param endtIdToProcess 
	 */
	private static String getPolQuery( PolicyVO policyVO, TTrnPolicyQuo policyQuo, Long endtIdToProcess, HibernateTemplate ht ){

		return	com.Constant.CONST_SELECT_SUM_NVL_PR_PRM_PREMIUM_0_FROM_T_TRN_PREMIUM_PR_WHERE_PR_PRM_CL_CODE_END+policyQuo.getPolClassCode()+com.Constant.CONST_AND_END + com.Constant.CONST_TRUNC_PR_PRM_VALIDITY_EXPIRY_DATE_END
		+Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE )+"' " +com.Constant.CONST_AND_PR_PRM_POLICY_ID_END+ policyQuo.getId().getPolPolicyId()
		 + " and pr.prm_Basic_Rsk_Code <> 99999 and pr.prm_endt_id <= "+ endtIdToProcess;
		/*return polQuery + "and pr.prm_validity_start_date <= (select distinct (pol_validity_start_date) from t_trn_policy where pol_linking_id = "+policyQuo.getPolLinkingId()+" AND pol_endt_id="+endtIdToProcess+") " +
				" and pr.prm_validity_expiry_date  > (select distinct (pol_validity_start_date) from t_trn_policy where pol_linking_id = "+policyQuo.getPolLinkingId()+" AND pol_endt_id="+endtIdToProcess+")  and pr.prm_cl_code = " + policyQuo.getPolClassCode() + " and P.POL_POLICY_ID = "
		+ policyQuo.getId().getPolPolicyId() + " " + "and pr.prm_Basic_Rsk_Code <> " + Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) +" and p.pol_endt_id<= "+endtIdToProcess;*/
	}

	/**
	 * returns the query for getting the prm for a specific class in policy. Gets always based on endt ID 
	 * @param endtIdToProcess 
	 */
	private static String getQuoteQuery( PolicyVO policyVO, TTrnPolicyQuo policyQuo, Long endtIdToProcess, HibernateTemplate ht ){
		return	com.Constant.CONST_SELECT_SUM_NVL_PR_PRM_PREMIUM_0_FROM_T_TRN_PREMIUM_QUO_PR_WHERE_PR_PRM_CL_CODE_END+policyQuo.getPolClassCode()+com.Constant.CONST_AND_END 
		+com.Constant.CONST_TRUNC_PR_PRM_VALIDITY_EXPIRY_DATE_END+Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE )+"' " +	com.Constant.CONST_AND_PR_PRM_POLICY_ID_END
		+ policyQuo.getId().getPolPolicyId() +" and pr.prm_Basic_Rsk_Code <> 99999 and pr.prm_endt_id <= "+ endtIdToProcess +com.Constant.CONST_AND_PR_PRM_STATUS_END
		+ SvcConstants.DEL_SEC_LOC_STATUS;
		/*return quoteQuery  +  Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) + "' "
		+ "and pr.prm_cl_code = " + policyQuo.getPolClassCode() + " and P.POL_POLICY_ID = " + policyQuo.getId().getPolPolicyId() + " "
		+ "and pr.prm_Basic_Rsk_Code <> " + Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) + " and P.POL_ENDT_ID = " + endtIdToProcess;*/
	}

	public static double getSectionLevelSumInsured( EEVO eeDetails ){
		Double sumInsured = 0.0;
		if( !Utils.isEmpty( eeDetails ) ){
			List<EquipmentVO> equipmentVOs = eeDetails.getEquipmentDtls();
			if( !Utils.isEmpty( equipmentVOs ) ){
				Iterator<EquipmentVO> equipDetsIter = equipmentVOs.iterator();
				while( equipDetsIter.hasNext() ){
					EquipmentVO equipmentVO = (EquipmentVO) equipDetsIter.next();
					if( !Utils.isEmpty( equipmentVO ) ){
						SumInsuredVO sumInsuredVO = equipmentVO.getSumInsuredDetails();
						if( !Utils.isEmpty( sumInsuredVO ) ){
							sumInsured += sumInsuredVO.getSumInsured();
						}
					}
				}
			}

		}
		return sumInsured;
	}

	public static void getSectionLevelPremium( EEVO eeDetails ){
		Double totalPremium = 0.0;

		for( EquipmentVO equipmentVO : eeDetails.getEquipmentDtls() ){
			if( !Utils.isEmpty( equipmentVO.getPremium() ) && !Utils.isEmpty( equipmentVO.getPremium().getPremiumAmt() ) ){
				totalPremium = totalPremium + equipmentVO.getPremium().getPremiumAmt();
			}
		}
		eeDetails.getPremium().setPremiumAmt( totalPremium );
	}

	public static void getSectionLevelPremium( MBVO mbDetails ){
		Double totalPremium = 0.0;

		for( MachineDetailsVO machineDetailsVO : mbDetails.getMachineryDetails() ){
			if( !Utils.isEmpty( machineDetailsVO.getPremium() ) && !Utils.isEmpty( machineDetailsVO.getPremium().getPremiumAmt() ) ){
				totalPremium = totalPremium + machineDetailsVO.getPremium().getPremiumAmt();
			}
		}
		mbDetails.getPremium().setPremiumAmt( totalPremium );

	}

	public static double getSectionLevelSumInsured( MBVO mbDetails ){
		Double sumInsured = 0.0;
		if( !Utils.isEmpty( mbDetails ) ){
			List<MachineDetailsVO> mbVOs = mbDetails.getMachineryDetails();
			if( !Utils.isEmpty( mbVOs ) ){
				Iterator<MachineDetailsVO> mbDetsIter = mbVOs.iterator();
				while( mbDetsIter.hasNext() ){
					MachineDetailsVO machineDetailsVO = (MachineDetailsVO) mbDetsIter.next();
					if( !Utils.isEmpty( machineDetailsVO ) ){
						SumInsuredVO sumInsuredVO = machineDetailsVO.getSumInsuredVO();
						if( !Utils.isEmpty( sumInsuredVO ) ){
							sumInsured += sumInsuredVO.getSumInsured();
						}
					}
				}
			}

		}
		return sumInsured;
	}

	public static double getSectionLevelPremium( GroupPersonalAccidentVO groupPersonalAccidentDetails ){

		Double sectionLevelPremium = 0.0;
		prmLogger.debug( "**********Get section level premium for GPA*************" );
		for( GPAUnnammedEmpVO gpaUnnammedEmpVO : groupPersonalAccidentDetails.getGpaUnnammedEmpVO() ){
			if( !Utils.isEmpty( gpaUnnammedEmpVO ) && !Utils.isEmpty( gpaUnnammedEmpVO.getPremium() ) && !Utils.isEmpty( gpaUnnammedEmpVO.getPremium().getPremiumAmt() ) ){

				sectionLevelPremium += gpaUnnammedEmpVO.getPremium().getPremiumAmt();
			}
		}

		for( GPANammedEmpVO gpaNammedEmpVO : groupPersonalAccidentDetails.getGpaNammedEmpVO() ){
			if( !Utils.isEmpty( gpaNammedEmpVO ) && !Utils.isEmpty( gpaNammedEmpVO.getPremium() ) && !Utils.isEmpty( gpaNammedEmpVO.getPremium().getPremiumAmt() ) ){

				sectionLevelPremium += gpaNammedEmpVO.getPremium().getPremiumAmt();
			}
		}
		prmLogger.debug( "**********Section level premium for GPA is " + sectionLevelPremium + com.Constant.CONST_ENDING );
		groupPersonalAccidentDetails.getPremium().setPremiumAmt( sectionLevelPremium );
		return sectionLevelPremium;
	}

	public static double getSectionLevelSumInsured( GroupPersonalAccidentVO groupPersonalAccidentDetails ){

		Double sectionLevelGpaSI = 0.0;

		for( GPAUnnammedEmpVO gpaUnnammedEmpVO : groupPersonalAccidentDetails.getGpaUnnammedEmpVO() ){
			if( !Utils.isEmpty( gpaUnnammedEmpVO ) && !Utils.isEmpty( gpaUnnammedEmpVO.getSumInsuredDetails() ) ){
				sectionLevelGpaSI += gpaUnnammedEmpVO.getSumInsuredDetails().getSumInsured();
			}
		}

		for( GPANammedEmpVO gpaNammedEmpVO : groupPersonalAccidentDetails.getGpaNammedEmpVO() ){
			if( !Utils.isEmpty( gpaNammedEmpVO ) && !Utils.isEmpty( gpaNammedEmpVO.getSumInsuredDetails().getSumInsured() ) ){
				sectionLevelGpaSI += gpaNammedEmpVO.getSumInsuredDetails().getSumInsured();
			}
		}
		return sectionLevelGpaSI;

	}

	public static double getSectionLevelSumInsured( DeteriorationOfStockVO dosVO ){
		Double sumInsured = 0.0;
		if( !Utils.isEmpty( dosVO ) ){
			List<DeteriorationOfStockDetailsVO> stockDetailsVOs = dosVO.getDeteriorationOfStockDetails();
			Iterator<DeteriorationOfStockDetailsVO> stockDetsIter = stockDetailsVOs.iterator();
			while( stockDetsIter.hasNext() ){
				DeteriorationOfStockDetailsVO stockVO = (DeteriorationOfStockDetailsVO) stockDetsIter.next();
				if( !Utils.isEmpty( stockVO ) ){
					SumInsuredVO sumInsuredVO = stockVO.getSumInsuredDetails();
					if( !Utils.isEmpty( sumInsuredVO ) ){
						sumInsured += sumInsuredVO.getSumInsured();
					}
				}
			}
		}
		return sumInsured;
	}

	public static void getSectionLevelPremium( DeteriorationOfStockVO stockDetails ){
		Double totalPremium = 0.0;

		for( DeteriorationOfStockDetailsVO stockDetailVO : stockDetails.getDeteriorationOfStockDetails() ){
			if( !Utils.isEmpty( stockDetailVO.getPremium() ) && !Utils.isEmpty( stockDetailVO.getPremium().getPremiumAmt() ) ){
				totalPremium = totalPremium + stockDetailVO.getPremium().getPremiumAmt();
			}
		}
		stockDetails.getPremium().setPremiumAmt( totalPremium );
	}

	public static void getSectionLevelPremium( FidelityVO fidelityDetails ){
		Double sectionLevelPremium = 0.0;
		prmLogger.debug( "**********Get section level premium for FID*************" );
		for( FidelityUnnammedEmployeeVO unnammedEmpVO : fidelityDetails.getUnnammedEmployeeDetails() ){
			if( !Utils.isEmpty( unnammedEmpVO ) && !Utils.isEmpty( unnammedEmpVO.getPremium() ) && !Utils.isEmpty( unnammedEmpVO.getPremium().getPremiumAmt() ) ){

				sectionLevelPremium += unnammedEmpVO.getPremium().getPremiumAmt();
			}
		}

		for( FidelityNammedEmployeeDetailsVO nammedEmpVO : fidelityDetails.getFidelityEmployeeDetails() ){
			if( !Utils.isEmpty( nammedEmpVO ) && !Utils.isEmpty( nammedEmpVO.getPremium() ) && !Utils.isEmpty( nammedEmpVO.getPremium().getPremiumAmt() ) ){

				sectionLevelPremium += nammedEmpVO.getPremium().getPremiumAmt();
			}
		}
		prmLogger.debug( "**********Section level premium for FID is " + sectionLevelPremium + com.Constant.CONST_ENDING );
		fidelityDetails.getPremium().setPremiumAmt( sectionLevelPremium );

	}

	public static double getSectionLevelSumInsured( FidelityVO fidelityDetails ){
		Double sectionLevelSI = 0.0;

		for( FidelityUnnammedEmployeeVO unnammedEmpVO : fidelityDetails.getUnnammedEmployeeDetails() ){
			if( !Utils.isEmpty( unnammedEmpVO ) && !Utils.isEmpty( unnammedEmpVO.getLimitPerPerson() ) ){
				sectionLevelSI += unnammedEmpVO.getLimitPerPerson();
			}
		}

		for( FidelityNammedEmployeeDetailsVO nammedEmpVO : fidelityDetails.getFidelityEmployeeDetails() ){
			if( !Utils.isEmpty( nammedEmpVO ) && !Utils.isEmpty( nammedEmpVO.getLimitPerPerson() ) ){
				sectionLevelSI += nammedEmpVO.getLimitPerPerson();
			}
		}
		return sectionLevelSI;
	}
	public static TTrnPremiumQuo getLastSplCoverPrm( Long policyId, Long riskId, Long basicRiskId, Long endtId, short discLoadCover, Session hibernateSession, PolicyVO policyVO ){

		String tableToQuery = policyVO.getIsQuote() ? PREMIUM_QUO : PREMIUM_POL;

		String queryString = "SELECT prm_premium, prm_sum_insured FROM " + tableToQuery + " where prm_policy_id =" + policyId + " AND prm_basic_rsk_id =" + basicRiskId
				+ "AND prm_rsk_id = " + riskId + " and prm_endt_id = (select max(p.prm_endt_id) from " + tableToQuery + " p" + " where p.prm_endt_id < " + endtId
				+ " and  p.prm_policy_id =" + policyId + " AND p.prm_basic_rsk_id =" + basicRiskId + "AND p.prm_rsk_id =" + riskId + ") and prm_cov_code =" + discLoadCover;

		Query query = hibernateSession.createSQLQuery( queryString );
		List<Object[]> result = query.list();

		TTrnPremiumQuo premiumQuo = null;
		if(!Utils.isEmpty(result)&& !Utils.isEmpty(result.get(0))){
			Object[] object = result.get(0);
			premiumQuo = new TTrnPremiumQuo();
			premiumQuo.setPrmPremium( (BigDecimal) object[ 0 ] );
			premiumQuo.setPrmSumInsured( (BigDecimal) object[ 1 ] );
		}

		return premiumQuo;

	}

	public static void updateGovtTax( PolicyVO policyVO, TTrnPolicyQuo policyQuo, HibernateTemplate ht ){

		LookUpService lookUpService = (LookUpService) Utils.getBean( "lookUpService" );
		LookUpVO inputVO = new LookUpVO();
		inputVO.setCategory( SvcConstants.GOVT_TAX );
		inputVO.setLevel1( String.valueOf( policyQuo.getPolClassCode() ) );
		inputVO.setLevel2( String.valueOf( SvcConstants.SC_PRM_COVER_GOVT_TAX ) );
		LookUpListVO lookUpListVO = (LookUpListVO) lookUpService.getListOfDescription( inputVO );

		if( !Utils.isEmpty( lookUpListVO ) && !Utils.isEmpty( lookUpListVO.getLookUpList() ) && !Utils.isEmpty( lookUpListVO.getLookUpList().size() > SvcConstants.zeroVal ) ){
			BigDecimal govtTaxRate = lookUpListVO.getLookUpList().get( SvcConstants.zeroVal ).getCode();

			List<TTrnPremiumQuo> premiumQuos = getSplCovtoUpdate( policyVO, policyQuo, SvcConstants.SC_PRM_COVER_GOVT_TAX, ht );

			if( Utils.isEmpty( premiumQuos ) || ( !Utils.isEmpty( premiumQuos ) && premiumQuos.size() == 0 ) ){
				// case of nil endorsement
				return;
			}

			Long endtIdToProcess = SvcUtils.getLatestEndtId( policyVO );
			if( !Utils.isEmpty( policyVO.getNewEndtId() ) && !Utils.isEmpty( policyVO.getEndtId() ) ){
				endtIdToProcess = DAOUtils.getEndtToProcess( ht, policyVO );
			}

			if( Utils.isEmpty( premiumQuos ) || ( !Utils.isEmpty( premiumQuos ) && premiumQuos.size() == 0 ) ){
				// case of nil endorsement
				return;
			}

			if( ( !Utils.isEmpty( premiumQuos ) && premiumQuos.size() > 1 ) ){
				// for an endt there cannot be more than one rec for a special cover code
				throw new BusinessException( "pas.dao.cmn.unknownError", null, "More than 1 row is returned for cover code 100 for endt no_2" + policyQuo.getId().getPolEndtId() );
			}

			String queryString = null;

			/*
			 * Query to get class level prm
			 */
			queryString = policyVO.getIsQuote() ? getQuoteQuery( policyVO, policyQuo, endtIdToProcess, ht ) : getPolQuery( policyVO, policyQuo, endtIdToProcess, ht );

			Session session = ht.getSessionFactory().getCurrentSession();
			BigDecimal clPrm = getPrm( queryString, session );

			queryString = policyVO.getIsQuote() ? getQuoteQuerySplPrm( policyVO, policyQuo, endtIdToProcess, ht ) : getPolQuerySplPrm( policyVO, policyQuo, endtIdToProcess, ht );
			BigDecimal splPrm = getPrm( queryString, session );

			BigDecimal totalPrm = clPrm.add( splPrm );

			TTrnPremiumQuo premiumQuo = premiumQuos.get( 0 );

			TTrnPremiumQuo oldPrmRec = getLastSplCoverPrm( Long.valueOf( policyQuo.getId().getPolPolicyId() ), Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ),
					Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ), endtIdToProcess, SvcConstants.SC_PRM_COVER_GOVT_TAX, ht.getSessionFactory()
							.getCurrentSession(), policyVO );

			premiumQuo.setPrmPremium( SvcUtils.getRateAmt( govtTaxRate, totalPrm ) );
			premiumQuo.setPrmRate( govtTaxRate );
			if( !Utils.isEmpty( oldPrmRec ) && !Utils.isEmpty( oldPrmRec.getPrmPremium() )){
				premiumQuo.setPrmOldPremium( oldPrmRec.getPrmPremium() );
			}
			else{
				premiumQuo.setPrmOldPremium( BigDecimal.ZERO );
			}

			ht.update( premiumQuo );
			ht.flush();
			ht.evict( premiumQuo );

		}

	}

	public static BigDecimal getPrm( String queryString, Session session ){
		Query query = session.createSQLQuery( queryString.toString() );
		query.setCacheable(false);
		List<Object> result = query.list();
		BigDecimal clPrm = new BigDecimal( 0 );
		if( !Utils.isEmpty( result ) && !Utils.isEmpty( result.get( 0 ) ) ){
			clPrm = (BigDecimal) result.get( 0 );
		}
		return clPrm;
	}

	private static String getPolQuerySplPrm( PolicyVO policyVO, TTrnPolicyQuo policyQuo, Long endtIdToProcess, HibernateTemplate ht ){

		return com.Constant.CONST_SELECT_SUM_NVL_PR_PRM_PREMIUM_0_FROM_T_TRN_PREMIUM_PR_WHERE_PR_PRM_CL_CODE_END + policyQuo.getPolClassCode() + com.Constant.CONST_AND_END + com.Constant.CONST_TRUNC_PR_PRM_VALIDITY_EXPIRY_DATE_END
				+ Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) + "' " + com.Constant.CONST_AND_PR_PRM_POLICY_ID_END + policyQuo.getId().getPolPolicyId()
				+ com.Constant.CONST_AND_PR_PRM_BASIC_RSK_CODE_99999_AND_PR_PRM_COV_CODE_END + SvcConstants.SC_PRM_COVER_GOVT_TAX + " and pr.prm_endt_id <= " + endtIdToProcess;
		/*return polQuery + "and pr.prm_validity_start_date <= (select distinct (pol_validity_start_date) from t_trn_policy where pol_linking_id = "+policyQuo.getPolLinkingId()+" AND pol_endt_id="+endtIdToProcess+") " +
				" and pr.prm_validity_expiry_date  > (select distinct (pol_validity_start_date) from t_trn_policy where pol_linking_id = "+policyQuo.getPolLinkingId()+" AND pol_endt_id="+endtIdToProcess+")  and pr.prm_cl_code = " + policyQuo.getPolClassCode() + " and P.POL_POLICY_ID = "
		+ policyQuo.getId().getPolPolicyId() + " " + "and pr.prm_Basic_Rsk_Code <> " + Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) +" and p.pol_endt_id<= "+endtIdToProcess;*/
	}

	/**
	 * returns the query for getting the prm for a specific class in policy. Gets always based on endt ID 
	 * @param endtIdToProcess 
	 */
	private static String getQuoteQuerySplPrm( PolicyVO policyVO, TTrnPolicyQuo policyQuo, Long endtIdToProcess, HibernateTemplate ht ){
		return com.Constant.CONST_SELECT_SUM_NVL_PR_PRM_PREMIUM_0_FROM_T_TRN_PREMIUM_QUO_PR_WHERE_PR_PRM_CL_CODE_END + policyQuo.getPolClassCode() + com.Constant.CONST_AND_END
				+ com.Constant.CONST_TRUNC_PR_PRM_VALIDITY_EXPIRY_DATE_END + Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) + "' " + com.Constant.CONST_AND_PR_PRM_POLICY_ID_END
				+ policyQuo.getId().getPolPolicyId() + com.Constant.CONST_AND_PR_PRM_BASIC_RSK_CODE_99999_AND_PR_PRM_COV_CODE_END + SvcConstants.SC_PRM_COVER_GOVT_TAX
				+ "  and pr.prm_endt_id <= " + endtIdToProcess + com.Constant.CONST_AND_PR_PRM_STATUS_END + SvcConstants.DEL_SEC_LOC_STATUS;
		/*return quoteQuery  +  Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) + "' "
		+ "and pr.prm_cl_code = " + policyQuo.getPolClassCode() + " and P.POL_POLICY_ID = " + policyQuo.getId().getPolPolicyId() + " "
		+ "and pr.prm_Basic_Rsk_Code <> " + Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) + " and P.POL_ENDT_ID = " + endtIdToProcess;*/
	}

	/**
	 * This method is used to update the minimum premium in the data base. 
	 * @param policyVO
	 * @param policyQuo
	 * @param hibernateTemplate
	 */
	public static void updateMinPremium( PolicyVO policyVO, HibernateTemplate ht ){
		// Get the total premium for the quote/policy

		if( policyVO.getAppFlow().equals( Flow.EDIT_QUO ) || policyVO.getAppFlow().equals( Flow.CREATE_QUO )  || policyVO.getAppFlow().equals( Flow.AMEND_POL ) 
		){
			//Get the difference b/w totalprm and the minimum premium received from rating engine

			Long endtIdToProcess = DAOUtils.getEndtToProcess( ht, policyVO );
				/*
				 * The logic used to update the minimum premium for each record is as follows
				 * 
				 *   ( current rec prm )  + ( ( current rec prm * difference prm )/(total premium) ). This logic is implemented in the stored proc 
				 */
				PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean( policyVO.getIsQuote() ? "updateMinPrmQuote" : "updateMinPrmPolicy" );

			if( policyVO.getIsQuote() ){
				sp.call( policyVO.getPolLinkingId(), endtIdToProcess, 0 );
				
				// IF the new prm is less than the minium premium even after updating the prm rec, raise an exception


				}
			else{
				sp.call( policyVO.getPolLinkingId(), endtIdToProcess, 
						DAOUtils.getValidityStartDate( ht, endtIdToProcess, policyVO.getPolLinkingId(), policyVO.getIsQuote() ), SvcUtils.getUserId( policyVO ) );
			}
		}

	}

	/**
	 * @param policyVO
	 * @return
	 */
	public static String getLatestTotalPrmQuery( PolicyVO policyVO ){
		return "Select nvl(sum(nvl(pr.PRM_PREMIUM,0)),0)  From " + ( policyVO.getIsQuote() ? "T_TRN_POLICY_QUO" : "T_TRN_POLICY" ) + " P, "
				+ ( policyVO.getIsQuote() ? "T_TRN_PREMIUM_QUO" : "T_TRN_PREMIUM" ) + " PR Where PR.PRM_POLICY_ID = P.POL_POLICY_ID "
				+ "And PR.PRM_ENDT_ID = P.POL_ENDT_ID and pr.prm_cl_code = p.pol_class_code " + "and trunc ( pr.prm_validity_expiry_date ) = '"
				+ Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) + "'" + " And P.POL_LINKING_ID = " + policyVO.getPolLinkingId()+" AND PR.PRM_COV_CODE not in (100,101,51,20)";
	}
	
	/* 
	 * @param oldPolExpiryDays
	 * @param prevPremium
	 * @param newPolExpiryDays
	 * @param polStartDate
	 * @param currAnnualPrm
	 * @param prevAnnualPrm
	 * @return
	 */
	public static Double getProratedPrm( Date polStartDate, Date oldPolExpiryDate, Date newPolExpiryDate, Date polEndtEffectiveDate, Double prevPremium, Double currAnnualPrm,Double prevAnnualPrm ){

		Double proratedPremium = 0.0;
		Long oldPolTermInDays = null;
		Long newPolTermInDays = null;

        newPolExpiryDate = getDateWithoutTimeStamp(newPolExpiryDate);
        oldPolExpiryDate = getDateWithoutTimeStamp(oldPolExpiryDate);
        polStartDate = getDateWithoutTimeStamp(polStartDate);
        polEndtEffectiveDate = getDateWithoutTimeStamp(polEndtEffectiveDate);
        
        Long polExtensionDays = 0l;
        
        if(!newPolExpiryDate.equals( oldPolExpiryDate )){
            polExtensionDays = getDifference( newPolExpiryDate, oldPolExpiryDate );
            polExtensionDays--;
            if(newPolExpiryDate.before( oldPolExpiryDate )){
            	polExtensionDays = -1 * polExtensionDays;
            }
        }
		if( !Utils.isEmpty( polEndtEffectiveDate ) ){
			newPolTermInDays = PremiumHelper.getDifference( newPolExpiryDate, polEndtEffectiveDate );
		}
		else{
			newPolTermInDays = PremiumHelper.getDifference( newPolExpiryDate, polStartDate );
		}

		//Assuming policy extension is not done
		oldPolTermInDays = newPolTermInDays;

		if( newPolTermInDays == 0 ){
			proratedPremium = prevPremium;
		}

		//if( polExtensionDays > 0 ){
			oldPolTermInDays = oldPolTermInDays - polExtensionDays;
			oldPolTermInDays = oldPolTermInDays < 0 ? oldPolTermInDays * -1 : oldPolTermInDays;
		//}
		
		prmLogger.debug( "polExtentionDays: " + polExtensionDays + " " );		
		prmLogger.debug( "oldPolTermInDays: " + oldPolTermInDays + " " );
		prmLogger.debug( "newPolTermInDays: " + newPolTermInDays + " " );
		prmLogger.debug( "prevPremium: " + prevPremium + " " );
		prmLogger.debug( "currAnnualPrm: " + currAnnualPrm + " " );
		prmLogger.debug( "prevAnnualPrm: " + prevAnnualPrm + " " );

		if( oldPolTermInDays == null || newPolTermInDays == null || currAnnualPrm == null || polStartDate == null || prevAnnualPrm == null){
			throw new BusinessException( "cmn.unknownError", null, "oldPolTermInDays, newPolTermInDays , currAnnualPrm,prevAnnualPrm or polStartDate should not be null" );
		}

		/*
		 * prevPremium is the premium, which is for policy duration
		 * So totalDays should be policy duration, so that 
		 * prevPremium/totalDays gives -> Premium per day
		 */		
		//int totalDays = (int) PremiumHelper.getDifference( oldPolExpiryDate, polStartDate );
		
		/*
		 * currAnnualPrm is rating engine premium, which is for current year(Annual Premium)
		 * So newTotalDays should be current years days, so that 
		 * currAnnualPrm/newTotalDays gives -> Premium per day
		 */
		//int newTotalDays = PremiumHelper.isLeapYear(polStartDate) ? SvcConstants.NO_OF_DAYS_LEAP_YEAR : SvcConstants.NO_OF_DAYS_YEAR;

		//Changes by Anveshan.newTotalDays should always be 365 since rating engine returs premium for 365 days. to calculate premium per day divide by 365.
		int newTotalDays = SvcConstants.NO_OF_DAYS_YEAR;
	
		prevPremium = prevPremium == null ? 0.0 : prevPremium;

		/*
		 * For the case of non policy extension type of endorsement below expression evaluates to:
		 * Since oldPolTermInDays = newPolTermInDays
		 * => proratedPremium = prevPremium + ( currAnnualPrm * newPolTermInDays - prevPremium * newPolTermInDays ) / totalDays;
		 * => proratedPremium = prevPremium + ( currAnnualPrm - prevPremium ) * newPolTermInDays / totalDays;
		 */
		proratedPremium = prevPremium + ( currAnnualPrm * newPolTermInDays  - prevAnnualPrm * oldPolTermInDays) / newTotalDays ;
		//proratedPremium = prevPremium + ( currAnnualPrm * newPolTermInDays / newTotalDays - prevPremium * oldPolTermInDays / totalDays ) ;

		prmLogger.debug( "proratedPremium: " + prevPremium + "+" + "(" + currAnnualPrm + "*" + newPolTermInDays + " - " + prevAnnualPrm + "*" + oldPolTermInDays +  ") /"
				+ newTotalDays + "=" + proratedPremium );

		return proratedPremium;
	}
	
	/**
	 * @param policyId
	 * @param currEndtId
	 * @return
	 */
	public static DataHolderVO<HashMap<String, Double>> getPremiumForProrate(
			long policyId, Long currEndtId, LOB lob) {

		PASStoredProcedure sp = null;
		DataHolderVO<HashMap<String, Double>> premiumHolder = null;

		// Ticket - 120595 : 22-Aug-16 : Himanish : Access to variable lob removed,  the variable is passed as parameter in the getPremiumForProrate function : Start
		// if(!SvcConstants.COMMON_FLOW.equalsIgnoreCase(lob))
		
		if (lob.name().equalsIgnoreCase(LOB.HOME.name())
				|| lob.name().equalsIgnoreCase(LOB.TRAVEL.name()))
			sp = (PASStoredProcedure) Utils.getBean("getOldNewProRatePremium");
		else
			sp = (PASStoredProcedure) Utils
					.getBean("getOldNewProRatePremiumForMonoline");
		// Ticket - 120595 : 22-Aug-16 : Himanish : Access to variable lob removed,  the variable is passed as parameter in the getPremiumForProrate function : End
		try {
			Map results = sp.call(policyId, currEndtId);
			HashMap<String, Double> premium = new HashMap<String, Double>();
			if (!Utils
					.isEmpty(results.get(SvcConstants.NEW_ANNUALIZED_PREMIUM))) {
				premium.put(
						SvcConstants.NEW_ANNUALIZED_PREMIUM,
						Double.valueOf(results.get(
								SvcConstants.NEW_ANNUALIZED_PREMIUM).toString()));
			} else {
				premium.put(SvcConstants.NEW_ANNUALIZED_PREMIUM, 0.0);
			}
			if (!Utils
					.isEmpty(results.get(SvcConstants.OLD_ANNUALIZED_PREMIUM))) {
				premium.put(
						SvcConstants.OLD_ANNUALIZED_PREMIUM,
						Double.valueOf(results.get(
								SvcConstants.OLD_ANNUALIZED_PREMIUM).toString()));
			} else {
				premium.put(SvcConstants.OLD_ANNUALIZED_PREMIUM, 0.0);
			}
			premiumHolder = new DataHolderVO<HashMap<String, Double>>();
			premiumHolder.setData(premium);
		} catch (DataAccessException e) {
			throw new BusinessException("cmn.unknownError", e,
					"An exception occured while getting ols and new annualized premium");
		}

		return premiumHolder;

	}

	
	/**
	 * Returns refundPremiumAmt which is calculated basis below formula
	 * ( (currentPremiumAmt * polExpiryDays) - currentPremiumAmt) / totalDays ) where
	 * 1. polExpiryDays will be difference in Pol_Expiry_Date and 
	 * 	  Pol_Cancellation_Effective_Date(in no of days)
	 * 2. totalDays will be 366 in case of LEAP_YEAR else it will be 365 
	 * @param PolicyDataVO
	 * @param currentPremiumAmt
	 * @return
	 */
	public static Double getRefundAmountForCancelPolicy( PolicyDataVO policyVO, Double currentPremiumAmt ){
		long polExpiryDays = PremiumHelper.getDifference( policyVO.getPolExpiryDate(), policyVO.getEndEffectiveDate() );
		int totalDays = isLeapYearForCancelPolicy( policyVO.getStartDate() ) ? SvcConstants.NO_OF_DAYS_LEAP_YEAR : SvcConstants.NO_OF_DAYS_YEAR;

		SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd" );
		Double refundAmount = 0.00;
		if( !sdf.format( policyVO.getScheme().getEffDate() ).equalsIgnoreCase( sdf.format( policyVO.getEndEffectiveDate() ) ) ){
			refundAmount = currentPremiumAmt - ( currentPremiumAmt * polExpiryDays / totalDays );
		}
		return refundAmount;
	}
	
	public static Double getRefundAmountForShortTerm(Double refundPerc, Double currentPremiumAmt ){	
		
		Double refundAmount = currentPremiumAmt - ( currentPremiumAmt * refundPerc );		
		return refundAmount;
	}
	
	public static String getCurrDiscLoadPrm( Long PolicyId, Long endtIdToProcess, HibernateTemplate ht ){

		return "Select sum(nvl(pr.PRM_PREMIUM,0)) From T_TRN_PREMIUM PR Where trunc(pr.prm_validity_expiry_date) = '"
				+ Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) + "' " + com.Constant.CONST_AND_PR_PRM_POLICY_ID_END + PolicyId
				+ " and pr.prm_Basic_Rsk_Code = 99999 and pr.prm_cov_code in ( " + SvcConstants.SC_PRM_COVER_LOADING +","+ SvcConstants.SC_PRM_COVER_DISCOUNT+ " ) and pr.prm_endt_id <= " + endtIdToProcess;
		/*return polQuery + "and pr.prm_validity_start_date <= (select distinct (pol_validity_start_date) from t_trn_policy where pol_linking_id = "+policyQuo.getPolLinkingId()+" AND pol_endt_id="+endtIdToProcess+") " +
				" and pr.prm_validity_expiry_date  > (select distinct (pol_validity_start_date) from t_trn_policy where pol_linking_id = "+policyQuo.getPolLinkingId()+" AND pol_endt_id="+endtIdToProcess+")  and pr.prm_cl_code = " + policyQuo.getPolClassCode() + " and P.POL_POLICY_ID = "
		+ policyQuo.getId().getPolPolicyId() + " " + "and pr.prm_Basic_Rsk_Code <> " + Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) +" and p.pol_endt_id<= "+endtIdToProcess;*/
	}

	/**
	 * Calculates the pro - rated premium for a defined period.
	 * 
	 * @param oldPolExpiryDays
	 * @param prevPremium
	 * @param newPolExpiryDays
	 * @param polStartDate
	 * @param currAnnualPrm
	 * @param currPremium 
	 * @return
	 */
	public static BigDecimal getProratedPrm( Long oldPolExpiryDays, Long newPolExpiryDays, BigDecimal prevPremium,  BigDecimal prevAnnualPrm , BigDecimal currPremium, Date polStartDate){
		
		if(oldPolExpiryDays==null||newPolExpiryDays==null||prevAnnualPrm==null||polStartDate==null){
			throw new BusinessException( "cmn.unknownError", null ,"oldPolExpiryDays, newPolExpiryDays , currAnnualPrm or polStartDate should not br null");
		}
		
		int totalDays = PremiumHelper.isLeapYear( polStartDate ) ? 366 : 365;
		// prevPremium + ( currAnnualPrm * newPolExpiryDays - prevPremium * oldPolExpiryDays  )  / totalDays
		prevPremium = prevPremium == null ?BigDecimal.ZERO:prevPremium;
		prevAnnualPrm = prevAnnualPrm == null ?BigDecimal.ZERO:prevAnnualPrm;
		//proratedPremium = prevPremium + ( currAnnualPrm * newPolExpiryDays - prevPremium * oldPolExpiryDays ) / totalDays;
		return new BigDecimal( Currency.getUnformattedScaledCurrency(prevPremium.add( ( currPremium.multiply( BigDecimal.valueOf( newPolExpiryDays ) ).subtract( prevAnnualPrm.multiply( BigDecimal.valueOf( oldPolExpiryDays ) ) ) ).divide( BigDecimal.valueOf( totalDays ),8, RoundingMode.HALF_UP ) ) ) );
	}


	/*public static BigDecimal getQuotePremium( BigDecimal prmPremiumActual, Date polStartDate, Date polExpiryDate ){
		BigDecimal proRatedPrm = BigDecimal.ZERO;
		double policyTermDays = PremiumHelper.getDifference( polExpiryDate, polStartDate );
		double annualDays = isLeapYear( polStartDate ) ? 366L : 365L;
		if( policyTermDays == annualDays ){
			proRatedPrm = SvcUtils.getNonNull( prmPremiumActual );
		}
		else{
			proRatedPrm = SvcUtils.getNonNull( prmPremiumActual ).multiply( BigDecimal.valueOf( policyTermDays / annualDays ) );
		}
		return proRatedPrm;
	}*/
	
	//Bugzilla 4182 : UAT feedback - For leap year, premium to be calculated for 365days only
	public static BigDecimal getQuotePremium( BigDecimal prmPremiumActual, Date polStartDate, Date polExpiryDate,Integer policyTerm ){
        BigDecimal proRatedPrm = BigDecimal.ZERO;
        double annualDays=0.0;
        prmLogger.debug( "DEPLOYED_LOCATION ***************************"+ Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION));
        double policyTermDays = PremiumHelper.getDifference( polExpiryDate, polStartDate );
        if(!Utils.isEmpty(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION)) && 
				Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")) {
        	annualDays = isLeapYear( polStartDate ) ? 366L : 365L; //Oman  specific logic
        	  prmLogger.debug("**annualDays Oman *********************"+annualDays);
        	  
        	  if( policyTermDays == annualDays ){
      			proRatedPrm = SvcUtils.getNonNull( prmPremiumActual );
      		}
      		else{
      			proRatedPrm = SvcUtils.getNonNull( prmPremiumActual ).multiply( BigDecimal.valueOf( policyTermDays / annualDays ) );
      		}
      		return proRatedPrm;
        }
        
        else{
        	
        	  annualDays = 365L; //isLeapYear( polStartDate ) ? 366L : 365L;
        	  prmLogger.debug("**annualDays UAE*********************"+annualDays);
        
        if((new Integer( "1" ).equals( policyTerm ))  || policyTermDays == annualDays ){
              proRatedPrm = SvcUtils.getNonNull( prmPremiumActual );
        }
        else{
              proRatedPrm = SvcUtils.getNonNull( prmPremiumActual ).multiply( BigDecimal.valueOf( policyTermDays / annualDays ) );
        }
        return proRatedPrm;
        }
  }



	public static double totalMinCancelPrm( PolicyDataVO policyDataVO, PremiumVO premiumVO ){
		StringBuilder queryBuilder = new StringBuilder();
		double prm = 0.0;
		queryBuilder
				.append( "select sum( round((NVL(prm_premium,0) - (NVL(prm_premium,0) * " )
				.append( getDifference( getDateWithoutTimeStamp( policyDataVO.getScheme().getExpiryDate() ), getDateWithoutTimeStamp( policyDataVO.getEndEffectiveDate() ) ) )
				.append( "/" )
				.append( isLeapYearForCancelPolicy( policyDataVO.getScheme().getEffDate() ) ? 366 : 365 )
				.append(
						")),0)) from t_trn_premium where prm_policy_id = ?  and prm_endt_id <= ? and TRUNC( prm_validity_expiry_date ) = '31-DEC-49' and prm_basic_rsk_id = 99999 and prm_cov_code in (40)" );
		List<Object> resultSet = DAOUtils.getSqlResultSingleColumnPas( queryBuilder.toString(), policyDataVO.getCommonVO().getPolicyId(), policyDataVO.getCommonVO().getEndtId() );
		if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 && !Utils.isEmpty( resultSet.get( 0 ) ) ){
			prm = Double.valueOf( String.valueOf( resultSet.get( 0 ) ) );
		}
		return prm;
	}
	
	/**
	 * Method to calculated prorated minimum premium in case of cancellation.
	 * Made it generic to support short term policies and by taking up LOB specific round offs
	 * @param policyDataVO
	 * @param premiumVO
	 * @return
	 */
	public static double cancelMinPrm( PolicyDataVO policyDataVO, PremiumVO premiumVO ){
		StringBuilder queryBuilder = new StringBuilder();
		double prm = 0.0;
		queryBuilder
				.append( "select sum( round((NVL(prm_premium,0) - (NVL(prm_premium,0) * " )
				.append( getDifference( getDateWithoutTimeStamp( policyDataVO.getScheme().getExpiryDate() ), getDateWithoutTimeStamp( policyDataVO.getEndEffectiveDate() ) ) )
				.append( "/" )
				.append( getDifference( getDateWithoutTimeStamp( policyDataVO.getScheme().getExpiryDate() ), getDateWithoutTimeStamp( policyDataVO.getScheme().getEffDate() ) ) )
				.append(
						")),5)) from t_trn_premium where prm_policy_id = ?  and prm_endt_id <= ? and TRUNC( prm_validity_expiry_date ) = '31-DEC-49' and prm_basic_rsk_id = 99999 and prm_cov_code in (40)" );
		List<Object> resultSet = DAOUtils.getSqlResultSingleColumnPas( queryBuilder.toString(), policyDataVO.getCommonVO().getPolicyId(), policyDataVO.getCommonVO().getEndtId() );
		if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 && !Utils.isEmpty( resultSet.get( 0 ) ) ){
		//	prm = Double.valueOf( String.valueOf( resultSet.get( 0 ) ) );
			prm = Double.parseDouble( Currency.getUnformattedScaledCurrency(new BigDecimal( String.valueOf( resultSet.get( 0 ) ) ),policyDataVO.getCommonVO().getLob().name() ) );
		}
		return prm;
	}
	
	public static double totalCancelPrmShortTerm( PolicyDataVO policyDataVO ){
		StringBuilder queryBuilder = new StringBuilder();
		double refundAmtPerc = policyDataVO.getEndorsmentVO().get( 0 ).getShortTermCancellationPerc();
		double prm = 0.0;
		queryBuilder
				//.append( "select sum( round((NVL(prm_premium,0) - (NVL(PRM_PREMIUM_ACTUAL,0) * " )
				// commented above line as it was not considering cover level discount/loading for display during cancellation.	
				.append( "select sum( round((NVL(prm_premium,0) - (NVL(prm_premium,0) * ( 1 + NVL(PRM_LOAD_DISC,0)/100 ) * " )
				.append(refundAmtPerc)
				.append(")),3)) from t_trn_premium where prm_policy_id = ?  and prm_endt_id <= ? and TRUNC( prm_validity_expiry_date ) = '31-DEC-49' and prm_basic_rsk_id <> 99999 " );
		List<Object> resultSet = DAOUtils.getSqlResultSingleColumnPas( queryBuilder.toString(), policyDataVO.getCommonVO().getPolicyId(),policyDataVO.getCommonVO().getEndtId() );
		if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
			 prm = Double.valueOf( String.valueOf( resultSet.get( 0 ) ) );
		}
		return prm;
	}
	/**
	 * Method to prorate any premium amount irrespective of the old and new premiums.
	 * Method created to calculate a base premium or minimum premium as per the policy term
	 * @author Sarath
	 * @param baseVO
	 * @param currentPremium
	 * @return prorated premium
	 * @since WC Monoline
	 * 
	 */
	public static BigDecimal getProratedBasePremium(BaseVO baseVO, BigDecimal currentPremium){

		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
		CommonVO commonVO = policyDataVO.getCommonVO();
		BigDecimal proratedPremium = null;
		BigDecimal previousPremium = BigDecimal.ZERO;
		BigDecimal prevAnnualPrm = BigDecimal.ZERO;

		Date polStartDate = policyDataVO.getScheme().getEffDate();
		Date polExpiryDate = policyDataVO.getScheme().getExpiryDate();
		Long newPolExpiryDays = zeroVal;
		Long oldPolExpiryDays = zeroVal;

		/*if( !Utils.isEmpty( policyDataVO.getEndEffectiveDate() ) ){
			newPolExpiryDays = PremiumHelper.getDifference( polExpiryDate, policyDataVO.getEndEffectiveDate() );
		}
		else{*/
			newPolExpiryDays = PremiumHelper.getDifference( polExpiryDate, polStartDate );
	//	}

		
		List<Object[]>  result = DAOUtils.getSqlResultForPas (  QueryConstants.POLICY_EXT,commonVO.getPolicyId(),
				commonVO.getEndtId());
		
		int policyExtDays = 0;
		if(!Utils.isEmpty( result ) && result.size() > 0 ){
			policyExtDays = ( Integer.valueOf( result.get( 0 )[0].toString() ) ).intValue() ;
			policyExtDays = policyExtDays < 0 ? policyExtDays * -1 : policyExtDays;
		}
		
		//Assuming policy extension is not done
		oldPolExpiryDays = newPolExpiryDays;
		
		if(policyExtDays>0){
			oldPolExpiryDays = oldPolExpiryDays - policyExtDays;
		}

		proratedPremium = PremiumHelper.getProratedPrm( oldPolExpiryDays, newPolExpiryDays, previousPremium,
				prevAnnualPrm, currentPremium, polStartDate );
		proratedPremium = BigDecimal.valueOf( Double.parseDouble(Currency.getUnformattedScaledCurrency( proratedPremium, commonVO.getLob().name() ) ) );
		prmLogger.debug( "PRORATED PREM "+proratedPremium );
		return proratedPremium;
	
	}

	public static boolean isLeapYearForCancelPolicy( Date policyYear ){
		System.out.println("policyYear -- " +policyYear);
		Calendar cal = Calendar.getInstance();
		GregorianCalendar greCal = new GregorianCalendar();
		cal.setTime( policyYear );
		if(cal.get( Calendar.MONTH) > 2) {
			System.out.println( cal.get( cal.YEAR ) + 1);
			System.out.println("Year to be considered - 1 :"+ cal.get( cal.YEAR ) + 1  +"is leap :"+greCal.isLeapYear( cal.get( cal.YEAR ) + 1));
			return greCal.isLeapYear( cal.get( cal.YEAR ) + 1);
		}
		System.out.println("Year to be considered - 2 : "+  cal.get( cal.YEAR )  +"Is leap :"+ cal.get( cal.YEAR ) );
		return greCal.isLeapYear( cal.get( cal.YEAR ) ); //use calender.get() function to get year in YYYY format. 
	}
	/**
	 * 142244:Vat Implementation 
	 */
	public static void updateVatTax( PolicyVO policyVO, TTrnPolicyQuo policyQuo, HibernateTemplate ht ){

		/*LookUpService lookUpService = (LookUpService) Utils.getBean( "lookUpService" );
		LookUpVO inputVO = new LookUpVO();
		inputVO.setCategory( SvcConstants.VAT_TAX );
		inputVO.setLevel1( String.valueOf( policyQuo.getPolPolicyType() ) );
		inputVO.setLevel2( String.valueOf( SvcConstants.SC_PRM_COVER_VAT_TAX ) );
		LookUpListVO lookUpListVO = (LookUpListVO) lookUpService.getListOfDescription( inputVO );*/

		if(!Utils.isEmpty(policyVO)){
			BigDecimal vatTaxRate = DAOUtils.getVATRateSBS(policyVO.getPolVATCode(),policyVO.getStartDate());

			List<TTrnPremiumQuo> premiumQuos = getSplCovtoUpdate( policyVO, policyQuo, SvcConstants.SC_PRM_COVER_VAT_TAX, ht );

			if( Utils.isEmpty( premiumQuos ) || ( !Utils.isEmpty( premiumQuos ) && premiumQuos.size() == 0 ) ){
				// case of nil endorsement
				return;
			}

			Long endtIdToProcess = SvcUtils.getLatestEndtId( policyVO );
			if( !Utils.isEmpty( policyVO.getNewEndtId() ) && !Utils.isEmpty( policyVO.getEndtId() ) ){
				endtIdToProcess = DAOUtils.getEndtToProcess( ht, policyVO );
			}

			if( Utils.isEmpty( premiumQuos ) || ( !Utils.isEmpty( premiumQuos ) && premiumQuos.size() == 0 ) ){
				// case of nil endorsement
				premiumQuos = getSplCovtoUpdate( policyVO, policyQuo, SvcConstants.SC_PRM_COVER_LOADING, ht );
				TTrnPremiumQuo premiumQuo1 = new TTrnPremiumQuo();
				TTrnPremiumQuo premiumQuo = (TTrnPremiumQuo) premiumQuos.get(0);
				premiumQuo1 = CopyUtils.copySerializableObject( premiumQuo );
				premiumQuo1.getId().setPrmCovCode((short) 151);
				premiumQuo1.setPrmRate(new BigDecimal(5));
				premiumQuo1.setPrmPremium(new BigDecimal(0));
				premiumQuos.clear();
				premiumQuos.add(premiumQuo1);
				Session session = ht.getSessionFactory().getCurrentSession();
				Transaction tx = session.beginTransaction();
				session.save(premiumQuo1);
				tx.commit();
			}

			if( ( !Utils.isEmpty( premiumQuos ) && premiumQuos.size() > 1 ) ){
				// for an endt there cannot be more than one rec for a special cover code
				throw new BusinessException( "pas.dao.cmn.unknownError", null, "More than 1 row is returned for cover code 100 for endt no_3" + policyQuo.getId().getPolEndtId() );
			}

			String queryString = null;

			/*
			 * Query to get class level prm
			 */
			queryString = policyVO.getIsQuote() ? getQuoteQuery( policyVO, policyQuo, endtIdToProcess, ht ) : getPolQuery( policyVO, policyQuo, endtIdToProcess, ht );
			
			
			Long previousEndtId = DAOUtils.getPreviousEndtId(ht, policyVO);
			String queryStringExcVAT = getPolQueryVatExcludePrevEndt( policyVO, policyQuo,previousEndtId , ht);
			Session session = ht.getSessionFactory().getCurrentSession();
			BigDecimal clPrm = getPrm( queryString, session );
			
			
			
			BigDecimal prevPremiumExcVAT=new BigDecimal("0.0");
		
			 prevPremiumExcVAT=getPrm(queryStringExcVAT,session);
			
			

			queryString = policyVO.getIsQuote() ? getQuoteQueryVatExclude( policyVO, policyQuo, endtIdToProcess, ht ) : getPolQueryVatExclude( policyVO, policyQuo, endtIdToProcess, ht );
			BigDecimal splPrm = getPrm( queryString, session );
			BigDecimal totalPrm = clPrm.add( splPrm );

			TTrnPremiumQuo premiumQuo = premiumQuos.get( 0 );

			TTrnPremiumQuo oldPrmRec = getLastSplCoverPrm( Long.valueOf( policyQuo.getId().getPolPolicyId() ), Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ),
					Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ), endtIdToProcess, SvcConstants.SC_PRM_COVER_VAT_TAX, ht.getSessionFactory()
							.getCurrentSession(), policyVO );

			//premiumQuo.setPrmPremium( SvcUtils.getRateAmt( vatTaxRate, totalPrm ) );
			
			/*
			 * 
			 * Unearned VAT Formula
			 */
			policyVO.setPolVatRate(vatTaxRate);
			Double oldVatPrem=0.0;
			if(!Utils.isEmpty(oldPrmRec) && !Utils.isEmpty(oldPrmRec.getPrmPremium())){
			 oldVatPrem=oldPrmRec.getPrmPremium().doubleValue();
			}
			else{
				 oldVatPrem=0.0;
			}
			
			premiumQuo.setPrmPremium( new BigDecimal(SvcUtils.calculateSBSVatTaxAmount(policyVO,totalPrm.doubleValue(),policyQuo,prevPremiumExcVAT.doubleValue()).get("vatTax")));
			
			premiumQuo.setPrmRate( vatTaxRate );
			if( !Utils.isEmpty( oldPrmRec ) && !Utils.isEmpty( oldPrmRec.getPrmPremium() )){
				if(policyVO.getAppFlow()==Flow.AMEND_POL) {
					if(!(policyVO.getEndorsements().get(0).getEndType() == "NIL") && policyQuo.getId().getEndtId() == premiumQuo.getPrmEndtId()) {
						premiumQuo.setPrmOldPremium( oldPrmRec.getPrmPremium() );
					}
				}
				else {
					premiumQuo.setPrmOldPremium( oldPrmRec.getPrmPremium() );
				}
			}
			else{
				premiumQuo.setPrmOldPremium( BigDecimal.ZERO );
			}
			
			
			
			

			
			
			ht.update( premiumQuo );
			ht.flush();
			ht.evict( premiumQuo );

		}

	}
	
	
	
	/*VAT*/
	/**
	 * returns the query for getting the prm for a specific class in policy. Gets always based on endt ID 
	 * @param endtIdToProcess 
	 */
	private static String getQuoteQueryVatExclude( PolicyVO policyVO, TTrnPolicyQuo policyQuo, Long endtIdToProcess, HibernateTemplate ht ){
		return com.Constant.CONST_SELECT_SUM_NVL_PR_PRM_PREMIUM_0_FROM_T_TRN_PREMIUM_QUO_PR_WHERE_PR_PRM_CL_CODE_END + policyQuo.getPolClassCode() + com.Constant.CONST_AND_END
				+ com.Constant.CONST_TRUNC_PR_PRM_VALIDITY_EXPIRY_DATE_END + Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) + "' " + com.Constant.CONST_AND_PR_PRM_POLICY_ID_END
				+ policyQuo.getId().getPolPolicyId() + com.Constant.CONST_AND_PR_PRM_BASIC_RSK_CODE_99999_AND_PR_PRM_COV_CODE_END + SvcConstants.SC_PRM_COVER_VAT_TAX
				+ "  and pr.prm_endt_id <= " + endtIdToProcess + com.Constant.CONST_AND_PR_PRM_STATUS_END + SvcConstants.DEL_SEC_LOC_STATUS;
		/*return quoteQuery  +  Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) + "' "
		+ "and pr.prm_cl_code = " + policyQuo.getPolClassCode() + " and P.POL_POLICY_ID = " + policyQuo.getId().getPolPolicyId() + " "
		+ "and pr.prm_Basic_Rsk_Code <> " + Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) + " and P.POL_ENDT_ID = " + endtIdToProcess;*/
	}
	
	/*VAT*/
	/**
	 * returns the query for getting the prm for a specific class in policy. Gets always based on endt ID 
	 * @param endtIdToProcess 
	 */
	private static String getPolQueryVatExcludePrevEndt( PolicyVO policyVO, TTrnPolicyQuo policyQuo, Long prevEndtId, HibernateTemplate ht ){
		return "Select sum(nvl(PR.POL_PREMIUM,0)) From T_TRN_POLICY PR Where   pr.POL_policy_id = " + policyQuo.getId().getPolPolicyId()
				+ " and pr.POL_endt_id = " + prevEndtId+" and pol_issue_hour = "+Utils.getSingleValueAppConfig("SBS_POLICY_ISSUE_HOUR") + 
				" and POL_Linking_id = "+ policyVO.getPolLinkingId();
		/*return quoteQuery  +  Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) + "' "
		+ "and pr.prm_cl_code = " + policyQuo.getPolClassCode() + " and P.POL_POLICY_ID = " + policyQuo.getId().getPolPolicyId() + " "
		+ "and pr.prm_Basic_Rsk_Code <> " + Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) + " and P.POL_ENDT_ID = " + endtIdToProcess;*/
	}

	/**
	 * returns the query for getting the prm for a specific class in policy. Gets always based on endt ID 
	 * @param endtIdToProcess 
	 */
	private static String getPolQueryVatExclude( PolicyVO policyVO, TTrnPolicyQuo policyQuo, Long endtIdToProcess, HibernateTemplate ht ){
		return com.Constant.CONST_SELECT_SUM_NVL_PR_PRM_PREMIUM_0_FROM_T_TRN_PREMIUM_PR_WHERE_PR_PRM_CL_CODE_END + policyQuo.getPolClassCode() + com.Constant.CONST_AND_END + com.Constant.CONST_TRUNC_PR_PRM_VALIDITY_EXPIRY_DATE_END
				+ Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) + "' " + com.Constant.CONST_AND_PR_PRM_POLICY_ID_END + policyQuo.getId().getPolPolicyId()
				+ com.Constant.CONST_AND_PR_PRM_BASIC_RSK_CODE_99999_AND_PR_PRM_COV_CODE_END + SvcConstants.SC_PRM_COVER_VAT_TAX + " and pr.prm_endt_id <= " + endtIdToProcess;
		/*return quoteQuery  +  Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) + "' "
		+ "and pr.prm_cl_code = " + policyQuo.getPolClassCode() + " and P.POL_POLICY_ID = " + policyQuo.getId().getPolPolicyId() + " "
		+ "and pr.prm_Basic_Rsk_Code <> " + Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) + " and P.POL_ENDT_ID = " + endtIdToProcess;*/
	}
	

   


}
