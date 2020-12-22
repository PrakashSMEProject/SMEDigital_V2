package com.rsaame.pas.b2c.cmn.utils;

import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.exception.SystemException;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.rules.invoker.RuleServiceInvoker;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * Utility class to invoke referral
 * 
 * @author M1012799
 *
 */
public class ReferralUtils {

	/**
	 * For rule invocation for home
	 * 
	 * @param homeInsuranceVO
	 * @param action
	 * @param actionIdentifier
	 * @param request
	 * @return
	 */
	private static final Logger logger = Logger
			.getLogger(ReferralUtils.class);
	public static boolean executeReferralTaskHome( HomeInsuranceVO homeInsuranceVO, String action, String actionIdentifier, String refIndicator,Integer sectionName){
		
		boolean rulesPassed = true;

		boolean rulesEnabled = Utils.toDefaultFalseBoolean( Utils.getSingleValueAppConfig( AppConstants.APP_CONFIG_RULES_ENABLED, "Y" ) );

		if( rulesEnabled && Utils.isEmpty( refIndicator ) ){
			ReferralListVO listReferralVO = null;
			try{
				Integer[] intArray = new Integer[ 1 ];
				intArray[ 0 ] = sectionName;
				
				RuleServiceInvoker ruleServiceInvoker = (RuleServiceInvoker) Utils.getBean( "ruleServiceInvoker" );
				UserProfile userProfile = (UserProfile) homeInsuranceVO.getCommonVO().getLoggedInUser();
				
				if(!Utils.isEmpty(userProfile)){
					ruleServiceInvoker.callRestFulRuleService( homeInsuranceVO, intArray, 
							userProfile.getRsaUser().getHighestRole(homeInsuranceVO.getCommonVO().getLob().name()) );
				}
				
			}
			catch( BusinessException e ){
				rulesPassed = false;
				listReferralVO = (com.rsaame.pas.vo.bus.ReferralListVO) e.getExceptionData();
				
				homeInsuranceVO.setReferralVOList( listReferralVO );
			}
		}

		return rulesPassed;
	}

	/**
	 * For rule invocation for home
	 * 
	 * @param homeInsuranceVO
	 * @param action
	 * @param actionIdentifier
	 * @param request
	 * @return
	 */
	public static boolean executeReferralTaskTravel( TravelInsuranceVO travelInsuranceVO, String action, String actionIdentifier, String refIndicator,Integer sectionName){
		boolean rulesPassed = true;

		boolean rulesEnabled = Utils.toDefaultFalseBoolean( Utils.getSingleValueAppConfig( AppConstants.APP_CONFIG_RULES_ENABLED, "Y" ) );

		if( rulesEnabled && Utils.isEmpty( refIndicator ) ){
			ReferralListVO listReferralVO = null;
			try{
				Integer[] intArray = new Integer[ 1 ];
				intArray[ 0 ] = sectionName;
				
				RuleServiceInvoker ruleServiceInvoker = (RuleServiceInvoker) Utils.getBean( "ruleServiceInvoker" );
				UserProfile userProfile = (UserProfile) travelInsuranceVO.getCommonVO().getLoggedInUser();
				
				if(!Utils.isEmpty(userProfile)){
					ruleServiceInvoker.callRestFulRuleService( travelInsuranceVO, intArray, 
							userProfile.getRsaUser().getHighestRole(travelInsuranceVO.getCommonVO().getLob().name()) );
				}
				
			}
			catch( BusinessException e ){
				rulesPassed = false;
				listReferralVO = (com.rsaame.pas.vo.bus.ReferralListVO) e.getExceptionData();
				
				travelInsuranceVO.setReferralVOList( listReferralVO );
			}catch( Exception e ){
				throw new SystemException( e.getCause(), "Unexpected exception occurred. Please contact administrator." );
			}
		}

		return rulesPassed;
	}
	
	/**
	 * Method to 
	 * @param homeInsuranceVO
	 */
	public static void populateTaskVO( PolicyDataVO policyDataVO ){
		TaskVO taskVO = null;
		CommonVO commonVO = policyDataVO.getCommonVO();
		//UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
		/*if(!Utils.isEmpty( commonVO.getTaskDetails() )){
			taskVO = commonVO.getTaskDetails();
			taskVO.setLoggedInUser( userProfile );
			taskVO.setAssignedBy( String.valueOf( userProfile.getRsaUser().getUserId() ) );
			if( !Utils.isEmpty( assignedTo ) ){
				taskVO.setAssignedTo( assignedTo );
			}
		}else{*/
		if( !Utils.isEmpty( policyDataVO ) && !Utils.isEmpty( policyDataVO.getReferralVOList() ) && !Utils.isEmpty( policyDataVO.getReferralVOList().getReferrals() )
				&& !Utils.isEmpty( policyDataVO.getReferralVOList().getReferrals().get( 0 ) ) ){
			policyDataVO.getReferralVOList().getReferrals().get( 0 ).setLocationCode( String.valueOf( policyDataVO.getCommonVO().getLocCode() ) );
			policyDataVO.getReferralVOList().getReferrals().get( 0 ).setConsolidated( true );
		}
		taskVO = (TaskVO) Utils.newInstance( "com.rsaame.pas.vo.bus.TaskVO" );
		if( !Utils.isEmpty( commonVO ) && !Utils.isEmpty( commonVO.getLoggedInUser() ) ){

			taskVO.setAssignedBy( String.valueOf( ( (UserProfile) commonVO.getLoggedInUser() ).getRsaUser().getUserId() ) );
			if(!Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName()))
			{
				taskVO.setAssignedTo( policyDataVO.getGeneralInfo().getSourceOfBus().getDefaultAssignToUser().toString() );
			}
			else
			{
				taskVO.setAssignedTo( AppConstants.DEFAULT_B2C_AGGISNGED_TO );
			}
			taskVO.setCreatedBy( String.valueOf( ( (UserProfile) commonVO.getLoggedInUser() ).getRsaUser().getUserId() ) );
			taskVO.setLoggedInUser( commonVO.getLoggedInUser() );
			taskVO.setPolicyType( String.valueOf( policyDataVO.getPolicyType() ) );

			if( !Utils.isEmpty( policyDataVO.getPolicyId() ) ){
				taskVO.setPolLinkingId( policyDataVO.getPolicyId() );
			}
			else if( !Utils.isEmpty( policyDataVO.getCommonVO().getPolicyId() ) ){
				taskVO.setPolLinkingId( policyDataVO.getCommonVO().getPolicyId() );
				policyDataVO.setPolicyId( policyDataVO.getCommonVO().getPolicyId() );
			}

			if( !Utils.isEmpty( policyDataVO.getCommonVO().getEndtId() ) ){
				taskVO.setPolEndId( policyDataVO.getCommonVO().getEndtId() );
			}

			if( !Utils.isEmpty( policyDataVO.getCommonVO() ) && policyDataVO.getCommonVO().getIsQuote() ){
				taskVO.setTaskType( "6" );//TODO get from properties file
				taskVO.setQuote( true );

				if( !Utils.isEmpty( policyDataVO.getCommonVO().getQuoteNo() ) ){
					taskVO.setQuoteNo( policyDataVO.getCommonVO().getQuoteNo() );
				}
			}
			else{
				taskVO.setTaskType( "2" );//TODO get from properties file
				taskVO.setQuote( false );
				if( !Utils.isEmpty( policyDataVO.getCommonVO().getQuoteNo() ) ){
					taskVO.setPolicyNo( policyDataVO.getCommonVO().getPolicyNo() );
				}

			}
			taskVO.setClassCode( policyDataVO.getPolicyClassCode().byteValue() );
			taskVO.setCategory( String.valueOf( policyDataVO.getPolicyType() ) );
			//taskVO.setDesc( refComments );
			taskVO.setIsOpen( true ); //Default value while assigning
			taskVO.setLob( policyDataVO.getCommonVO().getLob().toString() );
			taskVO.setLocation( String.valueOf( commonVO.getLocCode() ) );

			if( !Utils.isEmpty( policyDataVO.getCommonVO().getQuoteNo() ) ){ // TODO - change the condition here
				taskVO.setTaskName( new StringBuilder().append( "Transaction " )
						.append( policyDataVO.getCommonVO().getIsQuote() ? policyDataVO.getCommonVO().getQuoteNo() : policyDataVO.getCommonVO().getPolicyNo() )
						.append( " is referred" ).toString() );
			}

			taskVO.setPriority( "1" );
		}
		//}
		policyDataVO.getReferralVOList().setTaskVO( taskVO );
		//return taskVO;
	}
	
	/**
	 * Method to set the error message to error object
	 * @param bindingResult
	 * @param homeInsuranceVO
	 */
	public static void setReferralMessage( BindingResult bindingResult, PolicyDataVO policyDataVO ){
		//Commented the variable to avoid Dead store to local variable , sonar violation on 20-9-2017
		//Errors errors = bindingResult;
		CommonVO commonVO = policyDataVO.getCommonVO();
		
		if(!Utils.isEmpty( commonVO )){
			logger.debug("Added logger to avoid empty if statement");
			//Commented the variable to avoid Dead store to local variable , sonar violation on 20-9-2017
		//	String errorMessage = AppConstants.REFERRAL_MESSAGE ;
			/*errorMessage = errorMessage.replace( "%QUOTE_NUMBER%",String.valueOf( commonVO.getQuoteNo() ));
			errors.rejectValue( "errorMessage", "errorMessage.invalid", errorMessage );*/
		}
	}
}
