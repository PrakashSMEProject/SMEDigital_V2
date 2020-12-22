/**
 * 
 */
package com.rsaame.pas.com.helper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author M1014644
 *
 */
public class DerivePremiumDetails extends BaseDervieDetails{

	private static final double CHANGE_HOLDER = 9999.9;
	private static final BigDecimal HUNDRED = new BigDecimal( 100 );
	private static Logger prmLogger = Logger.getLogger( DerivePremiumDetails.class );
	private static Long zeroVal = 0L;
	
	private static final List<String> NON_VERSION_STATUS = Arrays.asList( Utils.getMultiValueAppConfig( SvcConstants.NON_VERSION_STATUS ) );
	private static final List<String> VERSION_STATUS = Arrays.asList( Utils.getMultiValueAppConfig( SvcConstants.VERSION_STATUS ) );
	private static List<String> SPECIAL_COVER_CODES = null;

	/* (non-Javadoc)
	 * @see com.rsaame.pas.com.helper.BaseDervieDetails#preprocessRecord(java.lang.String, com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper, com.rsaame.pas.vo.cmn.TableData, org.springframework.orm.hibernate3.HibernateTemplate, com.rsaame.pas.vo.bus.PolicyDataVO, com.rsaame.pas.vo.cmn.CommonVO)
	 */
	@Override
	protected void preprocessRecord( String tableInExecution, POJOWrapper mappedPojo, POJOWrapper existingRecord, TableData<BaseVO> tableData, HibernateTemplate ht,
			PolicyDataVO polData, CommonVO commonVO, SaveCase saveCase ){

		BigDecimal oldPrm = new BigDecimal( SvcConstants.zeroVal );
		BigDecimal oldSI = new BigDecimal( SvcConstants.zeroVal );
		
		TTrnPremiumQuo prmRecord = (TTrnPremiumQuo) mappedPojo;
		/*Since endt id is not mapped, its done here*/
		prmRecord.setEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		
		revertSpCovHack(prmRecord);
		

		if( Utils.isEmpty( prmRecord.getPrmEffectiveDate() ) ) prmRecord.setPrmEffectiveDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );

		prmRecord.setPrmRiLocCode( SvcConstants.APP_PRM_RI_LOC_CODE );
		prmRecord.setPrmPremiumCurr( Byte.valueOf( Utils.getSingleValueAppConfig( "CURRENCY_CODE" ) ) );
		if( !SPECIAL_COVER_CODES.contains( String.valueOf( ( (TTrnPremiumQuo) mappedPojo ).getId().getPrmCovCode() ) ) ){
		List<Object> currencyCodes = DAOUtils.getSqlResultSingleColumn(QueryConstants.QUERY_GET_SUM_INSURED_CURRENCY, ht, polData.getScheme().getSchemeCode(), polData.getScheme().getTariffCode(),
				prmRecord.getPrmRtCode(), Integer.valueOf(prmRecord.getId().getPrmCovCode()));
			if( !Utils.isEmpty( currencyCodes ) ){
				Byte currencyCode = Byte.valueOf( currencyCodes.get( 0 ).toString() );
				prmRecord.setPrmSumInsuredCurr( currencyCode );
			}
		}
		//prmRecord.setPrmSumInsuredCurr( Byte.valueOf( Utils.getSingleValueAppConfig( "CURRENCY_CODE" ) ) );
		  
		/*
		 * In case of delete, the current prm to be set as <code>0</code>
		 */
		if(prmRecord.getPrmStatus().equals( SvcConstants.DEL_SEC_LOC_STATUS  )){
			prmRecord.setPrmPremium( BigDecimal.ZERO );
			prmRecord.setPrmPremiumActual( BigDecimal.ZERO );
		}
		
		/*
		 * Pro - rated premium needs to be calculated for only amend cases
		 */
		
		/**
		 * Start of modification of below code address the advent net ID - 106233 - premium issue in referral flow.
		 */
		//if( !commonVO.getIsQuote() && commonVO.getAppFlow().equals( Flow.AMEND_POL ) )
		if( !commonVO.getIsQuote() && (commonVO.getAppFlow().equals( Flow.AMEND_POL ) ||  commonVO.getAppFlow().equals( Flow.RESOLVE_REFERAL ) ) ){
			/**
			 * End of modification advent net ID - 106233 - premium issue in referral flow
			 */
			prmLogger.debug( "LOB: " + commonVO.getLob().name() + "Deriving proprated premium for premium record " + mappedPojo.getPOJOId().toString() );
			/*
			 * Returns the prorated premium based on the formula defined for an LOB
			 * Pro rated calculation is not required for special covers hence the special code check
			 */
			if( ( (TTrnPremiumQuo) mappedPojo ).getId().getPrmBasicRskId().compareTo( SvcConstants.SPECIAL_CODE ) != 0){
				BigDecimal proratedPremium = getProRatedPrmRsk( (TTrnPremiumQuo) mappedPojo, polData, (TTrnPremiumQuo) existingRecord, commonVO, saveCase, ht );
				prmRecord.setPrmPremium( proratedPremium );
				/*if( !Utils.isEmpty( prmRecord ) && !Utils.isEmpty( prmRecord.getStatus() ) && prmRecord.getStatus() == SvcConstants.DEL_SEC_LOC_STATUS ){
					( (TTrnPremiumQuo) existingRecord ).setPrmPremium( proratedPremium );
				}*/
			}
			
		}else{
			/*
			 * apply the discount or loading
			 */
			setRskDiscLoadPrm( prmRecord );
		}
		
		/*
		 * Update the current premium records history column with the previous records premium and SI
		 */
		if( !Utils.isEmpty( existingRecord ) ){
			
			if( NON_VERSION_STATUS.contains( String.valueOf( existingRecord.getStatus() ) ) ){
				oldPrm = SvcUtils.getNonNullPrmSI( ( (TTrnPremiumQuo) existingRecord ).getPrmOldPremium() );
				oldSI = SvcUtils.getNonNullPrmSI( ( (TTrnPremiumQuo) existingRecord ).getPrmOldSumInsured());
			}
			if( VERSION_STATUS.contains( String.valueOf( existingRecord.getStatus() ) ) ){
				oldPrm = SvcUtils.getNonNullPrmSI( ( (TTrnPremiumQuo) existingRecord ).getPrmPremium() );
				oldSI = SvcUtils.getNonNullPrmSI( ( (TTrnPremiumQuo) existingRecord ).getPrmSumInsured());
			}
			prmRecord.setPrmOldPremium( oldPrm );
			prmRecord.setPrmOldSumInsured( oldSI );
		}else{
			prmRecord.setPrmOldPremium( oldPrm );
			prmRecord.setPrmOldSumInsured( oldSI );
		}
		
		//CTS 20.08.2020 - SAT#40972 VAT amount not showing properly in Endorsement schedule
		if( prmRecord.getId().getPrmCovCode() == Short.valueOf(Utils.getSingleValueAppConfig("TRAVEL_VAT_CODE")) && commonVO.getAppFlow().equals(Flow.AMEND_POL) && prmRecord.getPrmOldPremium().compareTo(BigDecimal.ZERO) == 0 && commonVO.getLob().equals(LOB.TRAVEL)){
			Double oldVatDetails = DAOUtils.getOldVATTravelPremium(prmRecord.getId().getPolicyId());
			prmRecord.setPrmOldPremium(BigDecimal.valueOf(oldVatDetails));
			//No need to set Sum insured as Suminsured value is always 0 for VAT
		}
		//CTS 20.08.2020 - SAT#40972 VAT amount not showing properly in Endorsement schedule

		
		ht.evict( prmRecord );
		
	}
	
	
	private void revertSpCovHack( TTrnPremiumQuo prmRecord ){

		if( !Utils.isEmpty( ThreadLevelContext.get( SvcConstants.TLC_PRM_SC_CHANGED ) ) && (Boolean) ThreadLevelContext.get( SvcConstants.TLC_PRM_SC_CHANGED ) ){
			prmRecord.setPrmRate( (BigDecimal) ThreadLevelContext.get( SvcConstants.TLC_PRM_RATE ) );
			ThreadLevelContext.clear( SvcConstants.TLC_PRM_RATE );
			ThreadLevelContext.clear( SvcConstants.TLC_PRM_SC_CHANGED );
		}
		
	}


	/* (non-Javadoc)
	 * @see com.rsaame.pas.com.helper.BaseDervieDetails#updateValues(java.lang.String, com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper, com.rsaame.pas.vo.cmn.TableData, org.springframework.orm.hibernate3.HibernateTemplate, com.rsaame.pas.vo.bus.PolicyDataVO, com.rsaame.pas.vo.cmn.CommonVO)
	 */
	@Override
	protected void updateValues( String tableInExecution, POJOWrapper mappedPojo, TableData<BaseVO> tableData, 
 HibernateTemplate ht, PolicyDataVO polData, CommonVO commonVO ){

		//PRM_RATE thread level context for special cover for which 

		spHack( mappedPojo );
		
		//If its delete case then the premium should be 0 and the pro rated premium will be calculated based on the
		// previous premium
		if( tableData.isToBeDeleted() ){
			( (TTrnPremiumQuo) mappedPojo ).setPrmPremium( BigDecimal.ZERO );
		}
		
		if( SPECIAL_COVER_CODES.contains( String.valueOf( ( (TTrnPremiumQuo) mappedPojo ).getId().getPrmCovCode() ) ) ){

		//	if( Utils.isEmpty( ( (TTrnPremiumQuo) mappedPojo ).getId().getVSD() ) ){
				TTrnPremiumQuo toSave  = (TTrnPremiumQuo) mappedPojo;
				TTrnPremiumQuo savedRec = DAOUtils.getPrevCoverLevelPrm( toSave.getId().getPolicyId(), toSave.getId().getPrmRskId().longValue(), toSave.getId().getPrmBasicRskId().longValue(), toSave.getId().getPrmCovCode(), ht );
				if( !Utils.isEmpty( savedRec ) && !Utils.isEmpty( savedRec.getId() ) ){
					toSave.getId().setVSD( savedRec.getId().getVSD() );
					ht.evict( savedRec );
				}
				
		//	}

		}

	}


	/**
	 * @param mappedPojo
	 */
	private void spHack( POJOWrapper mappedPojo ){
		if ( Utils.isEmpty( SPECIAL_COVER_CODES ) ) getSpecialCovers();
		
		if( SPECIAL_COVER_CODES.contains( String.valueOf( ( (TTrnPremiumQuo) mappedPojo ).getId().getPrmCovCode() ) ) ){
			ThreadLevelContext.set( SvcConstants.TLC_PRM_RATE, ( (TTrnPremiumQuo) mappedPojo ).getPrmRate() );
			ThreadLevelContext.set( SvcConstants.TLC_PRM_SC_CHANGED, true );
			( (TTrnPremiumQuo) mappedPojo ).setPrmRate( BigDecimal.valueOf( CHANGE_HOLDER ) );
		}
	}
	
	private List<String> getSpecialCovers(){
		if( SPECIAL_COVER_CODES == null ){
			// SPECIAL_COVER_CODES is used by SBS, hence the configaration not chnaged
			SPECIAL_COVER_CODES = CopyUtils.asList( Utils.getMultiValueAppConfig( "SPECIAL_COVER_CODES", "," ) );
			/*
			 * This configuration is used for ph3 as SBS currrenty does not have promo discount
			 */
			SPECIAL_COVER_CODES.add(  SvcConstants.SC_PRM_COVER_PROMO_DISC  );
			
			SPECIAL_COVER_CODES.add( SvcConstants.SPECIAL_COVER_MIN_PRM  );
		}
		return SPECIAL_COVER_CODES;
	}

	private  BigDecimal getProRatedPrmRsk( TTrnPremiumQuo toBeSavedPrmRec, PolicyDataVO polData, TTrnPremiumQuo currentPrmRec, CommonVO commonVO, SaveCase saveCase, HibernateTemplate ht ){

		/* If the premium are not different for the previous and the current record there is no need to calculate 
		 * pro - rated premium, infact there is no need to insert in the premium table */
		// This logic is commented out since in case of policy extension the premium will be same but the dates may change
		//Pls not delete the below commented lines for clarity
		/*if( !Utils.isEmpty( toBeSavedPrmRec ) && !Utils.isEmpty( currentPrmRec ) ){
			if( !Utils.isEmpty( toBeSavedPrmRec.getPrmPremiumActual() ) && !Utils.isEmpty( currentPrmRec.getPrmPremium() ) ){
				Double diffrence = currentPrmRec.getPrmPremium().doubleValue() - toBeSavedPrmRec.getPrmPremiumActual().doubleValue();
				if( Math.abs( diffrence ) < Double.valueOf( Utils.getSingleValueAppConfig( "DEFAULT_ROUND_OFF" ) ) ){
					return toBeSavedPrmRec.getPrmPremiumActual();
				}
			}
		}*/
		
		TTrnPolicyQuo policyQuo = (TTrnPolicyQuo) ThreadLevelContext.get( SvcConstants.TLC_KEY_POLICY_REC );

		//Process previous premium details
		Long oldPolExpiryDays = zeroVal;
		BigDecimal prevPremium = BigDecimal.ZERO;
		Integer policyExtDays = null;
		BigDecimal prevAnnualPrm = BigDecimal.ZERO;
		
		Date prevStartDate = polData.getScheme().getEffDate();
		Date prevEndDate = polData.getScheme().getExpiryDate();
		
		if( !Utils.isEmpty( currentPrmRec ) ){

			if( Utils.isEmpty( policyQuo.getPolExpiryDate() ) || Utils.isEmpty( currentPrmRec.getPrmEffectiveDate() ) ){
				throw new BusinessException( "", null, "For previous prmemium record " + currentPrmRec.getId().toStringPojoId()
						+ " prevPrmDetails.getPrmExpiryDate() or prevPrmDetails.getPrmEffectiveDate()  is null" );
			}
			
			
			List<Object[]>  result = DAOUtils.getSqlResult( QueryConstants.POLICY_EXT, ht, policyQuo.getId().getPolicyId(),
					policyQuo.getId().getEndtId() );
			
			
			if(!Utils.isEmpty( result ) && result.size() > 0 ){
				policyExtDays = Integer.valueOf( result.get( 0 )[0].toString() ) ;
				policyExtDays = policyExtDays < 0 ? policyExtDays * -1 : policyExtDays;
				/*if(policyExtDays > 0){
					policyExtDays = policyExtDays+1;
				}*/
			}
			
			if(!Utils.isEmpty(  policyQuo.getPolEndtEffectiveDate() )){
				oldPolExpiryDays = PremiumHelper.getDifference( policyQuo.getPolExpiryDate(), policyQuo.getPolEndtEffectiveDate() );
			}

			if( VERSION_STATUS.contains( String.valueOf( currentPrmRec.getStatus() ) ) ){
				prevPremium = currentPrmRec.getPrmPremium();
				prevAnnualPrm = SvcUtils.getNonNull( currentPrmRec.getPrmPremiumActual() ).add(
						SvcUtils.getNonNull( ( SvcUtils.getNonNull( currentPrmRec.getPrmPremiumActual() ).multiply( SvcUtils.getNonNull(  currentPrmRec.getPrmLoadDisc() ) ) ).divide( HUNDRED ) ) );
			}
			else{
				prevPremium = currentPrmRec.getPrmOldPremium();
				// Get previous data for travel to be used. Kishore
				List<Object[]> previousData = null;
				if( commonVO.getLob().equals( LOB.TRAVEL ) ){
					previousData = DAOUtils.getPreviousDataTravel( commonVO.getPolicyId(), commonVO.getEndtId(), toBeSavedPrmRec.getId().getPrmCovCode(), toBeSavedPrmRec.getId()
							.getPrmRskId() );
				}
				else{
					previousData = DAOUtils.getPreviousData( commonVO.getPolicyId(), commonVO.getEndtId(), toBeSavedPrmRec.getId().getPrmCovCode(), toBeSavedPrmRec.getPrmRtCode(),
							toBeSavedPrmRec.getPrmRcCode() );
				}

				if( !Utils.isEmpty( previousData ) && !Utils.isEmpty( previousData.get( 0 ) ) ){
					Integer previousTariff = polData.getScheme().getTariffCode();
					Long prevEndtId = polData.getCommonVO().getEndtId();
					CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean("geComSvc_POL");
					DataHolderVO<Long> dataHolderVO = (DataHolderVO<Long>) commonOpSvc.invokeMethod("getPrevEndtIdForPendingPolicy", commonVO);
					prevEndtId = dataHolderVO.getData();
					previousTariff = DAOUtils.getPreviousTariff( commonVO.getPolicyNo(), prevEndtId.longValue()  );
					//Checking whether the tariff is changed
					if( previousTariff.intValue() != ( polData.getScheme().getTariffCode() ) && polData.getPolicyType().equals( Integer.valueOf( SvcConstants.HOME_POL_TYPE ) ) ){
						prevPremium = (BigDecimal) previousData.get( 0 )[ 0 ];
					}
					prevAnnualPrm = (BigDecimal) previousData.get( 0 )[ 2 ];
					//While re adding the deleted covers, pro rated premium should be calculated without considering previous premium
					BigDecimal status = (BigDecimal) previousData.get( 0 )[ 3 ];
					if(status.equals( BigDecimal.valueOf( 4 ) )){
						prevPremium = BigDecimal.ZERO;
						prevAnnualPrm = BigDecimal.ZERO;
					}
					prevStartDate = (Date) previousData.get( 0 )[ 1 ];
					prevEndDate = (Date) previousData.get( 0 )[ 4];
				}
			}
			
			
			
			if( !Utils.isEmpty( policyExtDays ) && policyExtDays > 0 ){
				oldPolExpiryDays = oldPolExpiryDays - policyExtDays;
			}

		}

		//Process new/current record
		if( Utils.isEmpty( toBeSavedPrmRec.getPrmExpiryDate() ) || Utils.isEmpty( toBeSavedPrmRec.getPrmEffectiveDate() ) ){
			throw new BusinessException( "", null, "For current prmemium record " + toBeSavedPrmRec.getId().toStringPojoId()
					+ " endorsedPremium.getPrmExpiryDate() or endorsedPremium.getPrmEffectiveDate()  is null" );
		}
		
		Date polStartDate = polData.getScheme().getEffDate();
		Long newPolExpiryDays = zeroVal;
		
		long newPolicyDays = PremiumHelper.getDifference( polData.getScheme().getExpiryDate(), polStartDate );
		long oldPolicyDays = PremiumHelper.getDifference( prevEndDate, prevStartDate );
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String prevStartDateStr = sdf.format(prevStartDate);
		String polStartDateStr = sdf.format(polStartDate);
		String prevEndDateStr = sdf.format(prevEndDate);
		String polEndDateStr = sdf.format( polData.getScheme().getExpiryDate() );
		
		//Resetting the old and new policy expiry days if the travel date is changed and the travel period still remains the same
		if( ( !prevStartDateStr.equals( polStartDateStr ) || !prevEndDateStr.equals( polEndDateStr ) ) && ( newPolicyDays == oldPolicyDays ) ){
			newPolExpiryDays = PremiumHelper.getDifference( policyQuo.getPolExpiryDate(), polStartDate );
			oldPolExpiryDays = newPolExpiryDays;
		}
		else if( !Utils.isEmpty( polData.getEndEffectiveDate() )  ){
			newPolExpiryDays = PremiumHelper.getDifference( policyQuo.getPolExpiryDate(), policyQuo.getPolEndtEffectiveDate());
		}else{
			newPolExpiryDays = PremiumHelper.getDifference( policyQuo.getPolExpiryDate(), polStartDate );
		}

		
		//Long currAnnualPrm = SvcUtils.getNonNull( toBeSavedPrmRec.getPrmPremiumActual() ).longValue();
		BigDecimal currAnnualPrm =  toBeSavedPrmRec.getPrmPremiumActual() ;
		if( toBeSavedPrmRec.getPrmLoadDisc() != null && toBeSavedPrmRec.getPrmLoadDisc().compareTo( BigDecimal.ZERO ) != 0 ){
			currAnnualPrm = SvcUtils.getNonNull( toBeSavedPrmRec.getPrmPremiumActual() ).add(
					SvcUtils.getNonNull( ( SvcUtils.getNonNull( toBeSavedPrmRec.getPrmPremiumActual() ).multiply( toBeSavedPrmRec.getPrmLoadDisc() ) ).divide( HUNDRED ) ) );  
		}
			
		prmLogger.debug( "prevPremium: " + prevPremium + " " );
		prmLogger.debug( "currAnnualPrm: " + currAnnualPrm + " " );

		BigDecimal proratedPremium = null;
		
		if( commonVO.getLob().equals( LOB.TRAVEL ) ){
			if( !doProrate( commonVO, ht, policyQuo ) ){
				if( !Utils.isEmpty( toBeSavedPrmRec ) && !Utils.isEmpty( toBeSavedPrmRec.getStatus() ) && toBeSavedPrmRec.getStatus() == SvcConstants.DEL_SEC_LOC_STATUS ){
					proratedPremium = BigDecimal.ZERO;
				}
				else{
					proratedPremium = currAnnualPrm;
				}
			}
			else{
				proratedPremium = getProratedPrm( oldPolExpiryDays, newPolExpiryDays, prevPremium, prevAnnualPrm, currAnnualPrm, polStartDate,commonVO.getLob().name() );
				prmLogger.debug( com.Constant.CONST_PRORATE_PREM_END+proratedPremium + com.Constant.CONST_COV_CODE_END+ toBeSavedPrmRec.getId().getPrmCovCode() + com.Constant.CONST_RISK_CODE_END+ toBeSavedPrmRec.getId()
						.getPrmRskCode() );
			}
		}
		/*
		 * Added for #131212
		 */
		else if(commonVO.getLob().equals( LOB.HOME ) ){
			proratedPremium = getProratedPrm( oldPolExpiryDays, newPolExpiryDays, prevPremium, prevAnnualPrm, currAnnualPrm, polStartDate,commonVO.getLob().name() );
			prmLogger.debug( com.Constant.CONST_PRORATE_PREM_END+proratedPremium + com.Constant.CONST_COV_CODE_END+ toBeSavedPrmRec.getId().getPrmCovCode() + com.Constant.CONST_RISK_CODE_END+ toBeSavedPrmRec.getId()
					.getPrmRskCode() );
		}
		else{
			proratedPremium = getProratedPrm( oldPolExpiryDays, newPolExpiryDays, prevPremium, prevAnnualPrm, currAnnualPrm, polStartDate,commonVO.getLob().name() );
			prmLogger.debug( com.Constant.CONST_PRORATE_PREM_END+proratedPremium + com.Constant.CONST_COV_CODE_END+ toBeSavedPrmRec.getId().getPrmCovCode() + com.Constant.CONST_RISK_CODE_END+ toBeSavedPrmRec.getId()
					.getPrmRskCode() );
		}
		return new BigDecimal( Currency.getUnformattedScaledCurrency( proratedPremium ) );
	}


	/**
	 * @param commonVO
	 * @param ht
	 * @param policyQuojavascript:void(0);
	 */
	private boolean doProrate( CommonVO commonVO, HibernateTemplate ht, TTrnPolicyQuo policyQuo ){
		Boolean diffpolTypeChange = true;
		Boolean isTravel = false;
		Boolean isShortTerm = false;
		if( commonVO.getLob().equals( LOB.TRAVEL ) ){
			isTravel = true;
			java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumn( "select PKG_PAS_QUO_POL_TRAVEL.FN_GET_TRAVEL_EXT_TYP(" + policyQuo.getId().getPolicyId() + ","
					+ policyQuo.getId().getEndtId() + ") from dual", ht );
			if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
				String value = (String) valueHolder.get( 0 );
				diffpolTypeChange = Boolean.valueOf( value );
			}

			isShortTerm = policyQuo.getPolPolicyType().equals( Short.valueOf( SvcConstants.SHORT_TRAVEL_POL_TYPE ) );
		}
		return ( isTravel && diffpolTypeChange && !isShortTerm );
	}

	/**
	 * Calculates the pro - rated premium for a defined period.
	 * 
	 * @param oldPolExpiryDays
	 * @param prevPremium
	 * @param newPolExpiryDays
	 * @param polStartDate
	 * @param currAnnualPrm
	 * @param currAnnualPrm2 
	 * @return
	 */
	private  BigDecimal getProratedPrm( Long oldPolExpiryDays, Long newPolExpiryDays, BigDecimal prevPremium,  BigDecimal prevAnnualPrm , BigDecimal currAnnualPrm, Date polStartDate,String lob){
		BigDecimal proratedPremium = PremiumHelper.getProratedPrm( oldPolExpiryDays, newPolExpiryDays, prevPremium, prevAnnualPrm,  currAnnualPrm, polStartDate );
		return new BigDecimal( Currency.getUnformattedScaledCurrency( proratedPremium, lob ) );
	}
	
	/**
	 * @param prmRecord
	 * This method sent the apply the discount premium at risk level.
	 */
	private void setRskDiscLoadPrm( TTrnPremiumQuo prmRecord ){
		
		if( prmRecord.getPrmLoadDisc() != null && prmRecord.getPrmLoadDisc().compareTo( BigDecimal.ZERO ) != 0 ){
			BigDecimal discLoadPrm = SvcUtils.getNonNull( prmRecord.getPrmPremium() ).add(
					SvcUtils.getNonNull( ( SvcUtils.getNonNull( prmRecord.getPrmPremium() ).multiply( prmRecord.getPrmLoadDisc() ) ).divide( HUNDRED ) ) );  
			prmRecord.setPrmPremium( discLoadPrm );
		}
		

	}
	
	public static void main( String[] args ){
		//DerivePremiumDetails derivePremiumDetails = new DerivePremiumDetails();
		//BigDecimal prm = derivePremiumDetails.getProratedPrm( 0L,30L, 1000L, 1000L, new Date() );
		
		//System.out.println(prm);
	}

}
