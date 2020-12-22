package com.rsaame.pas.rating.svc.monoline.WC;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.cts.writeRate.Coverage;
import com.cts.writeRate.Factor;
import com.cts.writeRate.Item;
import com.cts.writeRate.Policy;
import com.cts.writeRate.Premium;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.exception.ErateException;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.rating.svc.RatingServiceInvoker;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.WorkmenCompVO;
import com.rsaame.pas.wc.svc.WCPremiumDetailsSvc;

/**
 * This class is used to invoke the rating engine call for WC and map the premium response to policydataVO obtained from the rating engine.
 * This class extends RatingServiceInvoker and provide the implementation for the abstract methods in it.
 * @author M1024781
 *
 */
public class WCRatingInvoker extends RatingServiceInvoker{

	private final static Logger logger = Logger
			.getLogger(WCRatingInvoker.class);
	private static final String WC_VO = "wcVO";
	private static final String EMP_ARRAY_INDEX = "empArrayIndex";
	private WCPremiumDetailsSvc wcpremiumSvc;
	
	/**
	 * This method is used to set the values for the WC rating factor and invoke the rating engine call.
	 * @param coverDetails
	 * @param policyDataVO
	 * @return Policy[]
	 */
	@Override
	protected Policy[] invokeRatingForPremium(Policy[] coverDetails,
			PolicyDataVO policyDataVO) {
		
		WorkmenCompVO workmenCompVO = (WorkmenCompVO) policyDataVO;
		List<Policy> policyList = new ArrayList<Policy>( 0 );
		List<EmpTypeDetailsVO> empTypeDetailsVOs = workmenCompVO.getEmpTypeDetails();
		List<Policy> finalPolicyArray = new ArrayList<Policy>();
		
		if( !Utils.isEmpty( coverDetails ) ){
			ThreadLevelContext.set( WC_VO, workmenCompVO );		
			
			int j = 0;
			for( Policy policy : coverDetails ){
				//service name for the next request has been set here
				policy.setService( SvcConstants.RATING_SERVICE_GET_RATE );
				List<Integer> empTypeindex = new ArrayList<Integer>( 0 );

				for(EmpTypeDetailsVO empTypeDetailsVO:empTypeDetailsVOs)
				{
					Policy modifiedPolicy = CopyUtils.copySerializableObject( policy );
					List<Item> itemForWC = new ArrayList<Item>( 0 );					
					Item[] items = modifiedPolicy.getItems();
					for( Item item : items ){
						Item itemForEmployeeType = getWCItem( item, empTypeDetailsVO );
						itemForEmployeeType.setItemNumber( j );
						itemForWC.add( itemForEmployeeType );	
						empTypeindex.add(j);
						j++;
					}					
					Item[] itemForWCArray = new Item[ itemForWC.size() ];
					itemForWC.toArray( itemForWCArray );
					modifiedPolicy.setService( "getRates" );
					modifiedPolicy.setItems( itemForWCArray );
					/* Add the modifiedPolicy to policy list to be converted to array later */
					policyList.add( modifiedPolicy );
				}	
				ThreadLevelContext.set( EMP_ARRAY_INDEX, empTypeindex );	
			}
			
			Policy[] policyArray = new Policy[ policyList.size() ];
			policyList.toArray( policyArray );

			//Added ArrayUtils.toString() for Invocation of toString on an array, sonar violation fix on 22-9-2017
			logger.debug( "Policy For Premium:" +ArrayUtils.toString( policyArray));
			try{
				policyArray = ratingInvoker.getPremiumForPolicy( policyArray );
				finalPolicyArray.addAll( Arrays.asList( policyArray ) ) ;
			}
			catch( ErateException e ){
				e.printStackTrace();
				logger.error( "Rating Engine ErateException: riskGroupDetailsMap.entrySet() is null" );

				BusinessException businessExcp = new BusinessException( "rating.invocation.no.loc", null, "riskGroupDetailsMap.entrySet() is null" );
				throw businessExcp;

			}
		}

		ThreadLevelContext.clear( WC_VO );
		logger.info( "Exiting invokeRatingForPremium" );
		Policy[] policyArray = new Policy[ finalPolicyArray.size() ];
		finalPolicyArray.toArray( policyArray );
		return policyArray;
	}	
	
	/**
	 * Get the WC item for each individual employeetype
	 * @param item
	 * @param empTypeDetailsVO
	 * @return
	 */
	private Item getWCItem( Item item, EmpTypeDetailsVO empTypeDetailsVO){
		Coverage[] coverageArray = item.getCoverages();
		Item itemForWC = CopyUtils.copySerializableObject( item );
		Factor[] modifiedFactorList = null;
		List<Coverage> coverageList = new ArrayList<Coverage>( 0 );
		for( Coverage coverage : coverageArray ){
			Factor[] factorList = coverage.getCoverageFactors();
			Coverage modifiedCoverage = CopyUtils.copySerializableObject( coverage );
			modifiedFactorList = getFactorList( factorList, empTypeDetailsVO);
			modifiedCoverage.setCoverageFactors( modifiedFactorList );
			coverageList.add( modifiedCoverage );
		}

		/*Set the factors to item */
		if( coverageList.size() > 0 ){
			coverageList.toArray( coverageArray );

			itemForWC.setCoverages( coverageArray );
		}

		return itemForWC;
	}
	
	/**
	 * get the factor list from employeetype and package details
	 * @param factorList
	 * @param empTypeDetailsVO
	 * @return
	 */
	private Factor[] getFactorList( Factor[] factorList,EmpTypeDetailsVO empTypeDetailsVO ){
		List<Factor> modifiedFactorList = new ArrayList<Factor>( 0 );

		for( Factor factor : factorList ){
			Factor modifiedFactor = setFactor( factor, empTypeDetailsVO );
			modifiedFactorList.add( modifiedFactor );
		}
		Factor[] modifiedFactorArray = new Factor[ modifiedFactorList.size() ];
		modifiedFactorList.toArray( modifiedFactorArray );
		return modifiedFactorArray;
	}
	
	/**
	 * Method to set the factor value for the factor name
	 * @param factor
	 * @param empTypeDetailsVO
	 * @return
	 */
	private Factor setFactor( Factor factor,EmpTypeDetailsVO empTypeDetailsVO){
		Factor modifiedFactor = CopyUtils.copySerializableObject( factor );
		
		if( !Utils.isEmpty( factor ) && !Utils.isEmpty( factor.getFactorName() ) ){
			String derivedFactorName = factor.getFactorName().trim().replaceAll( "\\s+", "_" );
			switch( WCFactorNames.valueOf( derivedFactorName ) ){
				case Annual_Wage_Roll:
					modifiedFactor.setFactorValue( getAnnualWageroll(derivedFactorName, empTypeDetailsVO ) );
					break;
				case Employee_Type:
					modifiedFactor.setFactorValue( getEmployeeType( derivedFactorName, empTypeDetailsVO ) );
					break;
				case WC_Liability_Limit:
					modifiedFactor.setFactorValue( getWCLiabilityLimit(derivedFactorName, empTypeDetailsVO ) );
					break;
				case PA_Flag:
					modifiedFactor.setFactorValue( getPAFlag( derivedFactorName, empTypeDetailsVO ) );
					break;
				case WC_Deductible:
					modifiedFactor.setFactorValue( getWCDeductible(derivedFactorName, empTypeDetailsVO) );
					break;		
					//sonar fix
				default:
					break;
			}
		}
		modifiedFactor.setFactorCode( factor.getFactorCode() );
		modifiedFactor.setFactorName( factor.getFactorName() );
		logger.debug( "Modified Factor:" + modifiedFactor );
		return modifiedFactor;
	}	
	
	/**
	 * Method returns Employee Annual Wages
	 * @param factorName
	 * @param empTypeDetailsVO
	 * @return
	 */
	private String getAnnualWageroll( String factorName, EmpTypeDetailsVO empTypeDetailsVO ){
		String empAnnualWageroll = null;
		if( !Utils.isEmpty( empTypeDetailsVO ) && !Utils.isEmpty( empTypeDetailsVO.getWageroll()  ) ){
			BigDecimal wageRoll = BigDecimal.valueOf( Double.valueOf( empTypeDetailsVO.getWageroll() ).longValue() );
			empAnnualWageroll = wageRoll.toString();
		}
		else{
			empAnnualWageroll = WCFactorNames.valueOf( factorName ).getDefaultValue();
		}

		logger.debug( "Employee Annula Wages:" + factorName + ":" + empAnnualWageroll );
		return empAnnualWageroll;
	}
	
	/**
	 * Method returns Employee type
	 * @param factorName
	 * @param empTypeDetailsVO
	 * @return
	 */
	private String getEmployeeType( String factorName, EmpTypeDetailsVO empTypeDetailsVO ){
		String empType = null;
		if( !Utils.isEmpty( empTypeDetailsVO ) && !Utils.isEmpty( empTypeDetailsVO.getEmpType() ) ){
			empType = empTypeDetailsVO.getEmpType().toString();
		}
		else{
			empType = WCFactorNames.valueOf( factorName ).getDefaultValue();
		}

		logger.debug( "Employee Type Factor:" + factorName + ":" + empType );
		return empType;
	}
	
	/**
	 * Method returns WC Liability Limit
	 * @param factorName
	 * @param empTypeDetailsVO
	 * @return
	 */
	private String getWCLiabilityLimit( String factorName, EmpTypeDetailsVO empTypeDetailsVO ){
		String wcLiabilityLimit = null;
		if( !Utils.isEmpty( empTypeDetailsVO ) && !Utils.isEmpty( empTypeDetailsVO.getLimit() ) ){
			wcLiabilityLimit = String.valueOf( empTypeDetailsVO.getLimit() );
		}
		else{
			wcLiabilityLimit = WCFactorNames.valueOf( factorName ).getDefaultValue();
		}

		logger.debug( "WC Liability Limit:" + factorName + ":" + wcLiabilityLimit );
		return wcLiabilityLimit;
	}
	
	/**
	 * Method returns PA Flag
	 * @param factorName
	 * @param empTypeDetailsVO
	 * @return
	 */
	private String getPAFlag( String factorName, EmpTypeDetailsVO empTypeDetailsVO ){
		String paFlag = null;
		WorkmenCompVO workmenCompVO = (WorkmenCompVO) ThreadLevelContext.get( WC_VO );
		if( !Utils.isEmpty( workmenCompVO ) && !Utils.isEmpty( workmenCompVO.getWcCovers() ) ){
			if(workmenCompVO.getWcCovers().getPACover())
				paFlag = "Y";
			else
				paFlag = "N";
		}
		else{
			paFlag = WCFactorNames.valueOf( factorName ).getDefaultValue();
		}

		logger.debug( "PA Flag:" + factorName + ":" + paFlag );
		return paFlag;
	}
	
	/**
	 * Method returns WC Deductible
	 * @param factorName
	 * @param empTypeDetailsVO
	 * @return
	 */
	private String getWCDeductible( String factorName, EmpTypeDetailsVO empTypeDetailsVO ){
		String wcDeductible = null;
		if( !Utils.isEmpty( empTypeDetailsVO ) && !Utils.isEmpty( empTypeDetailsVO.getDeductibles()) ){
			wcDeductible = empTypeDetailsVO.getDeductibles().toString();
		}
		else{
			wcDeductible = WCFactorNames.valueOf( factorName ).getDefaultValue();
		}

		logger.debug( "WC Deductible:" + factorName + ":" + wcDeductible );
		return wcDeductible;
	}
	
	/**
	 * This method is used to map the premium obtained from rating engine to policyDataVO. 
	 * @param premiumDetails
	 * @param policyDataVO
	 * @return
	 */
	@Override
	protected void mapPremiumToVo(Policy[] premiumDetails,
			PolicyDataVO policyDataVO) {
		WorkmenCompVO workmenCompVO = (WorkmenCompVO) policyDataVO;
		double premiumAmt = 0;
		
		if( !Utils.isEmpty( premiumDetails ) ){
			for( Policy policy : premiumDetails ){
				Item[] itemArray = policy.getItems();
				for( Item item : itemArray ){
					Coverage[] coverageArray = item.getCoverages();
					for( Coverage coverage : coverageArray ){
						Premium premium = coverage.getPremium();
						EmpTypeDetailsVO empTypeDetailsVO = getEmpTypeDetails( item.getItemNumber(), workmenCompVO );
						if( !Utils.isEmpty( empTypeDetailsVO ) ){
							double empTypePremium = 0;
							empTypePremium += Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(premium.getBasePremium()),workmenCompVO.getCommonVO().getLob().name()));
							if(!Utils.isEmpty( empTypePremium )){
								PremiumVO premiumVo = new PremiumVO();
								 premiumVo.setPremiumAmt( empTypePremium );
								 premiumVo.setPremiumAmtActual(empTypePremium);
								 empTypeDetailsVO.setPremium( premiumVo );
								 logger.debug( "EmployeeType  Premium:" + empTypeDetailsVO.getEmpType() + ":" + empTypeDetailsVO.getPremium().getPremiumAmt() );
							}
							
							premiumAmt += Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(premium.getBasePremium()),workmenCompVO.getCommonVO().getLob().name()));
							
						}
					}
				}
				
			}
		}
		workmenCompVO.getPremiumVO().setPremiumAmt( premiumAmt );
		workmenCompVO.getPremiumVO().setPremiumAmtActual( premiumAmt );
		/*workmenCompVO.getPremiumVO().setMinPremium( (BigDecimal) wcpremiumSvc.invokeMethod( SvcConstants.GET_CONFIG_MIN_PRM, workmenCompVO ) );
		double minPrmToApply = ( (BigDecimal) wcpremiumSvc.invokeMethod( SvcConstants.GET_MIN_PRM_TO_APPLY, workmenCompVO ) ).doubleValue();
		if( minPrmToApply > 0 ){
			workmenCompVO.getPremiumVO().setMinPremiumApplied( BigDecimal.valueOf( minPrmToApply ) );
			//		if( !( workmenCompVO.getCommonVO().getIsQuote() && workmenCompVO.getPremiumVO().getPremiumAmt() == 0) ){
			workmenCompVO.getPremiumVO().setPremiumAmt( workmenCompVO.getPremiumVO().getPremiumAmt() + minPrmToApply );
			//		}
		}*/
		logger.debug( "Total  Premium:" + workmenCompVO.getPremiumVO().getPremiumAmt() );
		
		
	}
	
	/**
	 *  Method to get the EmpTypeDetailsVO
	 * @param itemNumber
	 * @param workmenCompVO
	 * @return
	 */
	private EmpTypeDetailsVO getEmpTypeDetails( int itemNumber, WorkmenCompVO workmenCompVO ){
		
		List<EmpTypeDetailsVO> empTypeDetailsList = workmenCompVO.getEmpTypeDetails();
		EmpTypeDetailsVO empTypeDetailsVO = null;
		List<Integer> empTypeindex = (List<Integer> ) ThreadLevelContext.get( EMP_ARRAY_INDEX );			
		if(!Utils.isEmpty( empTypeindex )){
			if( empTypeindex.contains( itemNumber ) ){
				empTypeDetailsVO = empTypeDetailsList.get(itemNumber);
			}
		}		
		return empTypeDetailsVO;
	}

	public WCPremiumDetailsSvc getWcpremiumSvc(){
		return wcpremiumSvc;
	}

	public void setWcpremiumSvc( WCPremiumDetailsSvc wcpremiumSvc ){
		this.wcpremiumSvc = wcpremiumSvc;
	}

}
