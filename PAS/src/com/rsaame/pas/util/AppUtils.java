package com.rsaame.pas.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import java.util.Base64;
import java.util.Base64.Encoder;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.constants.CommonConstants;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.MVCUtils;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.constants.Constants;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.com.svc.PremiumSaveCommonSvc;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.kaizen.vo.PASServiceContext;
import com.rsaame.pas.mail.svc.PASMailerService;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.ForgotPwdDetailsVO;
import com.rsaame.pas.vo.app.MailVO;
import com.rsaame.pas.vo.app.PolicyDetailsVO;
import com.rsaame.pas.vo.app.PremiumSummary;
import com.rsaame.pas.vo.app.SearchInsuredVO;
import com.rsaame.pas.vo.bus.CreditLimitFlow;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.LocationAddressVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MBVO;
import com.rsaame.pas.vo.bus.MachineDetailsVO;
import com.rsaame.pas.vo.bus.PaymentVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralLocKey;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.bus.TravelBaggageVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravellingEmployeeVO;
import com.rsaame.pas.vo.bus.WorkmenCompVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.web.DesEncrypter;

/**
 * @author  M1033804
 * @since FGB 
 *  This will have all the utility methods of presentation layer.
 */
public class AppUtils{
	private static final double ZERO = 0.0;
	
	/** Logger instance */
	private static final Logger logger = Logger.getLogger(AppUtils.class);
	private static PremiumSaveCommonSvc prmSvc = (PremiumSaveCommonSvc)Utils.getBean( "premiumServiceBean" );
	private static Long tokenId = null;
	
	/**
	 * This method will return the logged in user's details like, 
	 * 	- User information
	 * 	- User-Role information
	 * 	- User-Application information
	 * 	- User-Client information
	 * @param request
	 * @return UserProfile
	 */
	public static UserProfile getUserDetailsFromSession( HttpServletRequest request ){
		UserProfile userProfileVO = null;

		if( !Utils.isEmpty( request ) && !Utils.isEmpty( request.getSession() ) && !Utils.isEmpty( request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO ) ) ){
			userProfileVO = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
		}
		return userProfileVO;
	}

	/**
	 * Convenience method to get the error message map from the request. The map is expected to be set by 
	 * resolveErrors() methods in <link>BaseServlet</link>.
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings( "unchecked" )
	public static java.util.Map<String, String> getErrorMessageMap( HttpServletRequest request ){
		return (java.util.Map<String, String>) request.getAttribute( Constants.REQ_PARAM_GLOBAL_ERROR_MESSAGES );
	}

	/**
	 * This method adds an error key and its message to the error message map.
	 * 
	 * @param request The HttpServletRequest instance
	 * @param errorKey The errorKey for the message to be added
	 */
	public static void addErrorMessage( HttpServletRequest request, String errorKey ){
		resolveError( request, errorKey );
	}

	private static void resolveError( HttpServletRequest request, String errorKey ){
		if( Utils.isEmpty( errorKey ) ){
			return;
		}

		String errorMessage = Utils.getAppErrorMessage( errorKey );
		addToRequestErrorMessagesMap( request, errorKey, errorMessage );
	}

	public static void addToRequestErrorMessagesMap( HttpServletRequest request, String errorKey, String errorMessage ){
		@SuppressWarnings( "unchecked" )
		java.util.Map<String, String> errorMessagesMap = (java.util.Map<String, String>) request.getAttribute( Constants.REQ_PARAM_GLOBAL_ERROR_MESSAGES );
		if( Utils.isEmpty( errorMessagesMap ) ){
			errorMessagesMap = new java.util.HashMap<String, String>();
		}

		errorMessagesMap.put( errorKey, errorMessage );
		request.setAttribute( Constants.REQ_PARAM_GLOBAL_ERROR_MESSAGES, errorMessagesMap );
	}

	/**
	 * This method is used to get the access level for the 
	 * 
	 * 
	 */
	public static String getHtmlArribute( HttpServletRequest request, String inputType ){
		String readOnly = "";
		ArrayList<String> readOnlyEntries = (ArrayList<String>) request.getAttribute( CommonConstants.REQ_ATTR_VIS_TAG_RO_LIST );
		if( !Utils.isEmpty( readOnlyEntries ) && readOnlyEntries.size() > 0 ){
			if( inputType.equalsIgnoreCase( AppConstants.TEXT_INPUT ) ){
				readOnly = "readonly='readonly'";
			}
			else if( inputType.equalsIgnoreCase( AppConstants.SELECT_INPUT ) ){
				readOnly = "disabled='disabled'";
			}
			else if( inputType.equalsIgnoreCase( AppConstants.LOOKUP_INPUT ) ){

				readOnly = AppConstants.TRUE;
			}
			else if( inputType.equalsIgnoreCase( AppConstants.CHECKBOX ) ){

				readOnly = "disabled";
			}
			/*else if( inputType.equalsIgnoreCase( AppConstants.ANCHOR_TAG ) ){

				readOnly="class='bluelinks greylinks' onclick='return false;'";
			}*/

		}

		return readOnly;
	}

	public static void setSectionPageDataForJSON( HttpServletRequest request, SectionVO section, RiskGroup rg, RiskGroupDetails rgd, PolicyVO policyVO ){
		/* Set the current data objects to request for ease in construction of JSON. */
		request.setAttribute( AppConstants.REQ_ATTR_CURR_RG, rg );
		request.setAttribute( AppConstants.REQ_ATTR_CURR_SECTION, section );
		request.setAttribute( AppConstants.REQ_ATTR_CURR_RGD, rgd );
		request.setAttribute( AppConstants.REQ_ATTR_POLICY, policyVO );
		if(!Utils.isEmpty( rg ))
		{		
				request.setAttribute( "parlocationIDReq", ( (LocationVO) rg ).getDirectorate() );
				request.setAttribute( "parBuildingNameReq", ( (LocationVO) rg ).getRiskGroupName() );
				request.setAttribute( com.Constant.CONST_PARLOCADDRESSDETAILREQ, ( (LocationVO) rg ).getAddress() );
			
		}	
	}

	/**
	 * Sets the default commission for the section into the request attribute <code>commission</code>.
	 * @param request
	 * @param sectionId
	 */
	public static void setDefaultCommission( HttpServletRequest request, HttpServletResponse response, int sectionId ){
		/* If the commission map is not available, attempt loading it again. */
		if( request.getSession( false ).getAttribute( AppConstants.GET_COMMISSION ) == null ){
			/* Execute the "GET_COMMISSION" opType. Ignore the output, as we do not want to respond from here. */
			MVCUtils.executeRequestHandler( "GET_COMMISSION", request, response );
		}

		// setting of commission which can be retrieved in UI 
		HashMap<Integer, Double> classComMap = (HashMap<Integer, Double>) request.getSession( false ).getAttribute( AppConstants.GET_COMMISSION );
		Double plCommission = classComMap.get( sectionId );

		request.setAttribute( "commission", plCommission );
	}

	public static BaseVO populateVOWithUser( BaseVO input, HttpServletRequest request ){
		input.setLoggedInUser( getUserDetailsFromSession( request ) );
		return input;
	}

	public static String getFormattedNumberWithDecimals( double number, int maxfractiondigits ){

		NumberFormat formatNum = NumberFormat.getInstance();
		formatNum.setMaximumFractionDigits( maxfractiondigits );
		return formatNum.format( number );
	}

	public static String getFormattedNumberWithDecimals( double number ){
		NumberFormat usa = NumberFormat.getCurrencyInstance( Locale.US );
		DecimalFormat fmt = new DecimalFormat( "0.00" );
		/*Sub string is used to remove the $ from the first character*/
		return usa.format( Double.valueOf( fmt.format( number ) ) ).substring( 1 );
	}

	public static String getFormattedDoubleWithDecimals( double number, int maxfractiondigits ){

		NumberFormat formatNum = NumberFormat.getInstance();
		formatNum.setMaximumFractionDigits( maxfractiondigits );
		String num = formatNum.format( number );
		num = num.replace( ",", "" );
		return num;
	}

	/**
	 * The below method extracts the commas from the string and pass it as an input to currency text box to 
	 * provide 2 decimal precision
	 * @param number
	 * @param commaSeperatedValues
	 * @return 
	 */
	public static String getFormattedNumberWithDecimals( double number, String commaSeperatedValues ){
		String value = getFormattedNumberWithDecimals( number );
		String[] splittedValue = null;
		if( commaSeperatedValues.equalsIgnoreCase( com.Constant.CONST_FALSE ) ){
			String concatenatedValue = "";
			splittedValue = value.split( "," );
			for( String temp : splittedValue ){
				concatenatedValue = concatenatedValue.concat( temp );
			}
			return concatenatedValue;
		}
		return value;
	}

	/**
	 * This method is used to set Logged In user profile as a request attribute
	 * @param request
	 * @param userProfile
	 */
	public static void setUserProfileDetsToRequest( HttpServletRequest request, UserProfile userProfile ){
		request.setAttribute( AppConstants.BROKERCODE, userProfile.getRsaUser().getBrokerId() );
		request.setAttribute( AppConstants.PROFILE, userProfile.getRsaUser().getProfile() );
	}

	/**
	 * This method will accept loss experience quantum value entered in General Info Screen and
	 * total annualized premium (summed up premium of all the sections selected) and
	 * calculate Premium Discount Loading
	 * @param lossExpQuantum
	 * @param totalPremium
	 * @return
	 */
	public static double getPrmDiscLoadingForLossExpQ( double lossExpQuantum, double totalPremium ){

		double lossRatio = lossExpQuantum / totalPremium * 100;
		double discLoad = 0;

		if( lossRatio <= AppConstants.LOSS_RATIO_PERC_RANGE_ONE ){
			discLoad = AppConstants.LOSS_RATIO_PERC_VALUE_ONE;
		}
		else if( lossRatio > AppConstants.LOSS_RATIO_PERC_RANGE_ONE && lossRatio < AppConstants.LOSS_RATIO_PERC_RANGE_TWO ){
			discLoad = totalPremium * AppConstants.LOSS_RATIO_PERC_VALUE_TWO;
		}
		else if( lossRatio >= AppConstants.LOSS_RATIO_PERC_RANGE_TWO ){
			discLoad = totalPremium * AppConstants.LOSS_RATIO_PERC_VALUE_THREE;
		}
		return discLoad;
	}

	/**
	 * This function is used to set request param based on condition if location has been added in current section 
	 * or through basic section
	 * @param request
	 * @param rgId
	 */
	public static void isLocationAddedInCurrentSection( HttpServletRequest request, Integer rgId ){
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		if( policyContext.isCurrentSectionBasic() ){
			request.setAttribute( AppConstants.REQ_PARAM_LOC_CURR_SEC, "true" );
			return;
		}
		if( !policyContext.isCurrentSectionBasic() ){
			SectionVO sectionVO = policyContext.getSectionDetails( policyContext.getBasicSectionId() );
			if( !Utils.isEmpty( sectionVO.getRiskGroupDetails() ) ){
				LocationVO locationVO = new LocationVO();
				locationVO.setRiskGroupId( rgId.toString() );
				if( !sectionVO.getRiskGroupDetails().containsKey( locationVO ) ){
					request.setAttribute( AppConstants.REQ_PARAM_LOC_CURR_SEC, "true" );
				}
			}

		}
	}

	public static boolean isResolveReferral( HttpServletRequest request ){
		return com.rsaame.pas.util.PolicyContextUtil.getPolicyContext( request ).getAppFlow().equals( Flow.RESOLVE_REFERAL );

	}

	public static boolean isQuote( HttpServletRequest request ){
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		boolean isQuote = false;
		if( !Utils.isEmpty( policyContext ) && !Utils.isEmpty( policyContext.getPolicyDetails() ) ){
			isQuote = policyContext.getPolicyDetails().getIsQuote();
		}else if( !Utils.isEmpty( policyContext ) && !Utils.isEmpty( policyContext.getCommonDetails() ) ){
			isQuote = policyContext.getCommonDetails().getIsQuote();
		}
		return isQuote;
	}

	/**
	 * 
	 * @param request
	 */
	public static void clearSectionPPPDataFromSession( HttpServletRequest request ){
		request.getSession( false ).setAttribute( AppConstants.SECTION_PPP_DATA, null );
		request.getSession( false ).setAttribute( AppConstants.SECTION_CONTENTS, null );
	}

	/*
	 * This method will return the base appFlow for RESOLVE_REFERAL flow.
	 * 1. Used in endorsement RESOLVE_REFERAL flow
	 * 2. Based on the flow returned here, the endorsement text and endorsement related buttons will be
	 * 	  displayed on premium page
	 * 3. This is also used in PremiumPageRH for getting EndorsementSummary
	 */
	public static Flow getBasicFlowFromResolveReferral( BaseVO baseVO ){

		Flow appFlow = null;
		if( baseVO instanceof PolicyVO ){
			PolicyVO p = (PolicyVO) baseVO;
			appFlow = p.getAppFlow();
			TaskVO taskDetails = p.getTaskDetails();

			/* In the case of referrals, we need to send one of EDIT_QUO, VIEW_QUO, AMEND_POL or VIEW_POL to the service layer based
			 * on whether the logged in user is the initiator of the */
			if( !Utils.isEmpty( p.getAppFlow() ) && p.getAppFlow().equals( Flow.RESOLVE_REFERAL ) ){
				UserProfile user = (UserProfile) p.getLoggedInUser();

				if( p.getIsQuote() ){
					if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getAssignedTo() ) ) ){
						appFlow = Flow.EDIT_QUO;
					}
					else{
						appFlow = Flow.VIEW_QUO;
					}
				}
				else{
					appFlow = Flow.VIEW_POL;	
					//SONAR FIX - Dodgy - Method uses the same code for two branches - Interchanging the else if blocks	
					if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getAssignedTo() ) )
							&& p.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_POLICY_ACCEPT ) ) ) ){
						appFlow = Flow.VIEW_POL;
					}
					else if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getAssignedTo() ) )
							&& p.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_POLICY_REFERRED ) ) ) ){
						appFlow = Flow.AMEND_POL;
					}
					else if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getCreatedBy() ) )
							&& p.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_POLICY_REFERRED ) ) ) ){
						appFlow = Flow.VIEW_POL;
					}
					else if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getCreatedBy() ) )
							&& p.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_POLICY_ACCEPT ) ) ) ){
						appFlow = Flow.AMEND_POL;
					}	
					else{
						appFlow = Flow.VIEW_POL;
					}
				}
			}
		}

		return appFlow;
	}
	
	/*
	 * This method will return the base appFlow for RESOLVE_REFERAL flow in case of Home/Travel
	 * 1. Used in endorsement RESOLVE_REFERAL flow
	 * 2. Based on the flow returned here, the endorsement text and endorsement related buttons will be
	 * 	  displayed on premium page
	 * 3. This is also used in PremiumPageRH for getting EndorsementSummary
	 */
	public static Flow getBasicFlowCommonResolveReferral( BaseVO baseVO ){

		Flow appFlow = null;
		if( baseVO instanceof CommonVO ){
			CommonVO p = (CommonVO) baseVO;
			appFlow = p.getAppFlow();
			TaskVO taskDetails = p.getTaskDetails();

			/* In the case of referrals, we need to send one of EDIT_QUO, VIEW_QUO, AMEND_POL or VIEW_POL to the service layer based
			 * on whether the logged in user is the initiator of the */
			if( !Utils.isEmpty( p.getAppFlow() ) && p.getAppFlow().equals( Flow.RESOLVE_REFERAL ) ){
				UserProfile user = (UserProfile) p.getLoggedInUser();

				if( p.getIsQuote() ){
					if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getAssignedTo() ) ) ){
						appFlow = Flow.EDIT_QUO;
					}
					else{
						appFlow = Flow.VIEW_QUO;
					}
				}
				else{
					//SONAR FIX - Dodgy - Method uses the same code for two branches - Interchanging the else if blocks	
					appFlow = Flow.VIEW_POL;
					if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getAssignedTo() ) )
							&& p.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_POLICY_REFERRED ) ) ) ){
						appFlow = Flow.AMEND_POL;
					}
					else if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getAssignedTo() ) )
							&& p.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_POLICY_ACCEPT ) ) ) ){
						appFlow = Flow.VIEW_POL;
					}
					else if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getCreatedBy() ) )
							&& p.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_POLICY_REFERRED ) ) ) ){
						appFlow = Flow.VIEW_POL;
					}
					else if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getCreatedBy() ) )
							&& p.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_POLICY_ACCEPT ) ) ) ){
						appFlow = Flow.AMEND_POL;
					}
					
					else{
						appFlow = Flow.VIEW_POL;
					}
				}
			}
		}

		return appFlow;
	}


	public static Boolean isLoggedInUserIsInitiator( PolicyVO policyVO ){
		PolicyVO p = policyVO;
		TaskVO taskDetails = p.getTaskDetails();
		UserProfile user = (UserProfile) p.getLoggedInUser();
		if( Utils.isEmpty( taskDetails ) || user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getCreatedBy() ) ) )
			return true;
		else
			return false;
	}
	
	public static Boolean isLoggedInUserIsInitiatorCommon( CommonVO commonVO ){
		CommonVO comn = commonVO;
		TaskVO taskDetails = comn.getTaskDetails();
		UserProfile user = (UserProfile) comn.getLoggedInUser();
		if( Utils.isEmpty( taskDetails ) || user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getCreatedBy() ) ) )
			return true;
		else
			return false;
	}

	/*
	 * This method will return the flag based on the status of policy in policyVo
	 * 
	 */
	public static boolean isPolicyCancelled( HttpServletRequest request ){
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		boolean isPolCancelled = false;
		if( !Utils.isEmpty( policyContext ) && !Utils.isEmpty( policyContext.getPolicyDetails() ) ){
			isPolCancelled = policyContext.getPolicyDetails().getStatus().equals( 4 ) ? true : false;
		}
		return isPolCancelled;
	}

	public static RiskGroupingLevel getRiskGroupLevel( Integer sectionId ){
		/*
		 * TODO: For non multi location sections then the risk leveling should be resolved  using section ID
		 */

		return RiskGroupingLevel.LOCATION;
	}

	/**
	 * This method is used to start transaction and resolve appflow if it is set as referral flow.
	 */
	public static PolicyVO setNewAppflow( PolicyContext policyContext ){

		policyContext.startTransaction();
		PolicyVO p = policyContext.getPolicyDetails();
		if( !Utils.isEmpty( getBasicFlowFromResolveReferral( p ) ) ){
			p.setAppFlow( getBasicFlowFromResolveReferral( p ) );
		}
		return p;
	}

	/**
	 * This method is used to commit the transaction and reset the appflow back to original appFlow which was set before resolving appFlow.
	 */
	public static void setOldAppFlow( PolicyVO policyVO, Flow appFlow, PolicyContext policyContext ){
		PolicyVO p = policyContext.getPolicyDetails();
		p.setAppFlow( appFlow );
		policyVO.setAppFlow( appFlow );
		policyContext.commit();
	}

	public static double getPremiumForPolicy( PolicyVO policyVO, double totalPremium ){

		long endtNo = 0;
		if( !Utils.isEmpty( policyVO.getEndtNo() ) ) endtNo = policyVO.getEndtNo();
		if( ( AppUtils.getBasicFlowFromResolveReferral( policyVO ) == Flow.AMEND_POL || ( Flow.VIEW_POL.equals( policyVO.getAppFlow() ) && endtNo > 0 ) ) ){
			EndorsmentVO endorsmentVO = null;
			if( !Utils.isEmpty( policyVO.getEndorsements() ) && policyVO.getEndorsements().size() > 0 ){
				endorsmentVO = policyVO.getEndorsements().get( 0 );
				totalPremium = endorsmentVO.getPremiumVO().getPremiumAmt();
			}
		}

		return totalPremium;
	}

	/**
	 * This method is used to fetch the latest Endorsement Number.
	 * @param policyVO
	 * @return
	 */
	public static long getLatestEndtNoForPolicy( PolicyVO policyVO ){

		if( !Utils.isEmpty( policyVO ) && !Utils.isEmpty( policyVO.getEndtNo() ) && !Utils.isEmpty( policyVO.getNewEndtNo() ) ){
			long endtNo = policyVO.getEndtNo();
			long newEndtNo = policyVO.getNewEndtNo();

			if( newEndtNo > endtNo )
				return newEndtNo;

			else
				return endtNo;
		}

		else if( !Utils.isEmpty( policyVO ) && !Utils.isEmpty( policyVO.getEndtNo() ) )
			return policyVO.getEndtNo();

		else
			return 0;
	}

	public static double getSectionCommission( SectionVO section ){
		if( section.getCommission() == null ) return 0;
		return section.getCommission();
	}

	public static String delFontStart( Byte status ){
		if( status.equals( AppConstants.DEL_SEC_LOC_STATUS ) ){
			return "<font color='red'>";
		}
		return "";
	}

	public static String delFontEnd( Byte status ){
		if( status.equals( AppConstants.DEL_SEC_LOC_STATUS ) ){
			return "</font>";
		}
		return "";
	}

	/**
	 * Returns the HTML disable attribute value for WC page to disable the Employer's Liability Limit field.
	 * @param request
	 * @param inputType
	 * @param rowCnt
	 * @return
	 */
	public static String getHtmlArribute( HttpServletRequest request, String inputType, int rowCnt ){
		String returnVal;
		returnVal = getHtmlArribute( request, AppConstants.LOOKUP_INPUT );
		if( !Utils.isEmpty( returnVal ) ){
			return returnVal;
		}
		else{
			return getWCLimitUIDisableFlag( rowCnt );
		}
	}

	/**
	 * Returns the HTML disable attribute value for WC page to disable the Employer's Liability Limit field except for the first row.
	 * @param rowCnt
	 * @return
	 */
	public static String getWCLimitUIDisableFlag( int rowCnt ){
		if( rowCnt > 0 )
			return AppConstants.TRUE;
		else
			return "";
	}

	/**
	 * Returns javascript function call 'populateLimitOnEmpTypesChange' for  onchange event on Employer's Liability Limit field on WC page
	 * @param rowCnt
	 * @return
	 */
	public static String getWCLimitFieldOnEmpTpChangeFn( int rowCnt ){
		if( rowCnt > 0 )
			return "populateLimitOnEmpTypesChange(this," + rowCnt + ")";
		else
			return "";
	}

	/**
	 * 
	 * @param policyVO
	 * @return
	 */
	public static Long getLatestEndtId( PolicyVO policyVO ){
		Long endtId = null;

		if( !Utils.isEmpty( policyVO.getNewEndtId() ) && policyVO.getEndtId() < policyVO.getNewEndtId() ){
			endtId = policyVO.getNewEndtId();
		}
		else{
			if( !Utils.isEmpty( policyVO.getEndtId() ) ){
				endtId = policyVO.getEndtId();
			}
		}

		return endtId;
	}

	/**
	 * The below method returns the substring/word at a given index from a string array or a sentence.
	 * @param string
	 * @param index
	 * @return
	 */
	public static String getSubstring( String string, int index ){
		String[] stringArray = null;
		String subString = null;

		stringArray = string.split( " " );
		Utils.trimAllEntries( stringArray );
		subString = stringArray[ index ];

		return subString;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static String getUWQVisibility( HttpServletRequest request, String propertyKey ){
		String visibility = null;
		if( Utils.isEmpty( Utils.getSingleValueAppConfig( propertyKey ) ) ){
			com.rsaame.pas.vo.bus.RiskGroupDetails rgdUW = (com.rsaame.pas.vo.bus.RiskGroupDetails) request.getAttribute( com.rsaame.pas.util.AppConstants.REQ_ATTR_CURR_RGD );
			if( rgdUW != null ){
				com.rsaame.pas.vo.bus.UWQuestionsVO questionsQuos = rgdUW.getUwQuestions();
				if( questionsQuos != null ){
					java.util.List<com.rsaame.pas.vo.bus.UWQuestionVO> questionsList = questionsQuos.getQuestions();

					if( !Utils.isEmpty( questionsList ) && questionsList.size() > 0 ){
						visibility = "HIDDEN";
					}

				}
			}
		}
		else{
			visibility = Utils.getSingleValueAppConfig( propertyKey );
		}
		return visibility;
	}

	/**
	 * 
	 * @return
	 */
	public static String getPARBldCoverdRadioBtnVisibility(){
		String visibility = null;
		visibility = Utils.getSingleValueAppConfig( AppConstants.PPPAR_BLD_COVERED_VISIBILITY );
		return visibility;
	}

	/**
	 * 
	 * @return
	 */
	public static String getPPPCalPremiumBtnVisibility(){
		return Utils.getSingleValueAppConfig( AppConstants.PPP_CALC_PRM_BTN_VISIBILITY );
	}

	/**
	 * 
	 * @return
	 */
	public static String getPPPAddLocTabVisibility(){
		return Utils.getSingleValueAppConfig( AppConstants.PPP_ADD_LOC_TAB_VISIBILITY );
	}

	/**
	 * 
	 * @return
	 */
	public static String getPPWCPaCoverVisibility(){
		return Utils.getSingleValueAppConfig( AppConstants.PPWC_PA_COVER_VISIBILITY );
	}

	/**
	 * 
	 * @return
	 */
	public static String getPPMoneySafeCIRVisibility(){
		return Utils.getSingleValueAppConfig( AppConstants.PPMONEY_SAFE_CIR_VISIBILITY );
	}
	
	/*
	 * This method return the visibility
	 * for fields which needs to be made editable
	 * in Prapackaged scheme
	 */
	public static String getWCmixedTariffVisibility(PolicyContext pc){
		String tariffCode=null;
		try{
			if(pc!=null)
				tariffCode=pc.getPolicyDetails().getScheme().getTariffCode().toString();
			}catch (NullPointerException e) {
			logger.debug("Null pointer exception while getting tariff code ");
		}
		return Utils.getSingleValueAppConfig(AppConstants.COMBINED_TARIFF_VISIBILITY.concat(tariffCode));
		
	}

	/**
	 * Method added to set the PO Box from General Info to Section (PAR/PL).
	 * @param policyVO
	 * @param locationVO
	 */
	public static void setPoxBoxDetailsFromGI( PolicyVO policyVO, LocationVO locationVO ){

		if( ( !Utils.isEmpty( policyVO.getGeneralInfo().getInsured().getAddress() ) ) && ( !Utils.isEmpty( policyVO.getGeneralInfo().getInsured().getAddress().getPoBox() ) ) ){

			String poBox = policyVO.getGeneralInfo().getInsured().getAddress().getPoBox();
			if( !Utils.isEmpty( locationVO ) ){
				if( !Utils.isEmpty( locationVO.getAddress() ) ){
					locationVO.getAddress().setPoBox( poBox );
				}
			}
		}

	}

	/**
	Method added to set the Annual Turn over from General Info to PL section.
	 * @param policyVO
	 * @param request
	 */
	public static void setAnnualTurnOverFromGI( PolicyVO policyVO, HttpServletRequest request ){
		
		if(CopyUtils.asList(Utils.getMultiValueAppConfig( "DMCC_SCHEMES", "," )).contains( policyVO.getScheme().getSchemeCode().toString() ))
		{
			request.setAttribute( AppConstants.REQUEST_ATTR_GI_TURNOVER, Utils.getSingleValueAppConfig( "DMCC_PL_ANNUAL_TURN_OVER" ) );
		}
		else if( ( !Utils.isEmpty( policyVO.getGeneralInfo().getInsured() ) ) && ( !Utils.isEmpty( policyVO.getGeneralInfo().getInsured().getTurnover() ) ) ){

			Long turnover = policyVO.getGeneralInfo().getInsured().getTurnover();
			request.setAttribute( AppConstants.REQUEST_ATTR_GI_TURNOVER, turnover.toString() );
		}

	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isPrepackPageLoadReadOnly( HttpServletRequest request ){
		boolean flag = false;
		PolicyVO policyVO = PolicyContextUtil.getPolicyContext( request ).getPolicyDetails();
		if( policyVO.getIsPrepackaged() && ( policyVO.getAppFlow().equals( Flow.VIEW_POL ) || policyVO.getAppFlow().equals( Flow.VIEW_QUO ) ) ){
			flag = true;
		}

		return flag;
	}

	/**
	 * Returns current riskgroupid
	 * @param request
	 * @param pc
	 * @return
	 */
	public static String getCurrentRiskGroupId( HttpServletRequest request, PolicyContext pc ){
		String riskGroupId = null;

		rgIdDetermination: {
			/* If the section Id from the request is not the same as the current section from PolicyContext, then it
			 * is a case of NEXT or PREVIOUS, which means that the entire section FORM is being loaded and not just the
			 * data for a location. In that case, just take the first location from the sections location list. */
			String sectionId = request.getParameter( "sectionId" );
			if( Utils.isEmpty( sectionId ) || !Integer.valueOf( sectionId ).equals( pc.getCurrentSectionId() ) ){
				java.util.List<Integer> rgIds = pc.getRiskGroupIds( pc.getCurrentSectionId() );
				if( !Utils.isEmpty( rgIds ) ){
					riskGroupId = rgIds.get( 0 ).toString();
					break rgIdDetermination;
				}
			}

			/* If the section doesn't have any risk group Ids, then take the one present in the request. Ideally, this
			 * should lead to an error. However, the decision of whether it is really an exceptional scenario should be
			 * left out of scope for this method. */
			riskGroupId = request.getParameter( "riskGroupId" );

			if( Utils.isEmpty( riskGroupId ) ){
				riskGroupId = (String) ThreadLevelContext.get( "RISK_GROUP_ID" );
			}
		}

		/** SKN : Changes
		String riskGroupId = request.getParameter( "riskGroupId" );
		
		if( Utils.isEmpty( riskGroupId ) ){
			riskGroupId = (String) ThreadLevelContext.get( "RISK_GROUP_ID" );
		}
		*/
		return riskGroupId;
	}

	/**
	 * Return true if the Building name is saved in par page, this used in JS logic to handle building name edit 
	 * @param request
	 * @return
	 */
	public static boolean hasBldNameSaved( HttpServletRequest request ){
		boolean flag = false;
		if( !Utils.isEmpty( request.getAttribute( AppConstants.REQ_ATTR_CURR_RG ) ) ){
			LocationVO locationVO = (LocationVO) request.getAttribute( AppConstants.REQ_ATTR_CURR_RG );
			if( !Utils.isEmpty( locationVO.getAddress() ) && !Utils.isEmpty( locationVO.getAddress().getBuildingName() ) ){
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static String getGeneralInfoPOBoxFromRequest( HttpServletRequest request ){
		//Updated for AdventId:65098; POBox Changes
		String poBox="";
		if( !Utils.isEmpty(request.getAttribute(com.Constant.CONST_PARLOCADDRESSDETAILREQ))){
			LocationAddressVO locationaddress = (LocationAddressVO) request.getAttribute(com.Constant.CONST_PARLOCADDRESSDETAILREQ);
			poBox = locationaddress.getPoBox();
		}
		else if( !Utils.isEmpty( request.getAttribute( AppConstants.REQUEST_ATTR_GI_POBOX ) ) ) return (String) request.getAttribute( AppConstants.REQUEST_ATTR_GI_POBOX );

		return poBox;
	}

	/**
	 * 
	 * @param request
	 * @param policyContext
	 */
	public static void populatePOBoxToRequestAndLocationVO( HttpServletRequest request, PolicyContext policyContext ){
		//Updated for AdventId:65098; POBox Changes
		PolicyVO policyVO = policyContext.getPolicyDetails();
		if( policyVO.getAppFlow().equals( Flow.CREATE_QUO )){
			String riskGroupId = AppUtils.getCurrentRiskGroupId( request, policyContext );
			LocationVO locationVO = null;
			locationVO = (LocationVO) policyContext.getRiskGroup( policyContext.getCurrentSectionId(), riskGroupId );

			String giPoBox = policyVO.getGeneralInfo().getInsured().getAddress().getPoBox();
			//if( Utils.isEmpty( locationVO ) || Utils.isEmpty( locationVO.getAddress().getPoBox() ) ||  ( !Utils.isEmpty( request.getSession(false).getAttribute( AppConstants.SESSION_ATTR_HAS_POBOX_CHANGED ) ) && request.getSession(false).getAttribute( AppConstants.SESSION_ATTR_HAS_POBOX_CHANGED ).toString().equals( "true" ) )){
			/*if( Utils.isEmpty( locationVO ) || Utils.isEmpty( locationVO.getAddress() ) ||
					( !Utils.isEmpty( request.getSession(false).getAttribute( AppConstants.SESSION_ATTR_HAS_POBOX_CHANGED ) ) &&
							request.getSession(false).getAttribute( AppConstants.SESSION_ATTR_HAS_POBOX_CHANGED ).toString().equals( "true" ) )){
			*/
			//if( !Utils.isEmpty( request.getSession( false ).getAttribute( AppConstants.SESSION_ATTR_HAS_POBOX_CHANGED ) )
					//&& request.getSession( false ).getAttribute( AppConstants.SESSION_ATTR_HAS_POBOX_CHANGED ).toString().equals( "true" ) ){
			if( Utils.isEmpty( locationVO ) || Utils.isEmpty( locationVO.getAddress()) || Utils.isEmpty(locationVO.getAddress().getPoBox())){
					AppUtils.setPoxBoxDetailsFromGI( policyVO, locationVO );
			}				
			request.setAttribute( AppConstants.REQUEST_ATTR_GI_POBOX, giPoBox );
			
		}
		if( !policyVO.getAppFlow().equals( Flow.VIEW_POL ) && !policyVO.getAppFlow().equals( Flow.VIEW_QUO ) ){
			/* Below code populates the P.O Box value provided on General Info screen in to locationVO */
			if( !Utils.isEmpty( request.getSession( false ).getAttribute( AppConstants.SESSION_ATTR_HAS_POBOX_CHANGED ) ) ){
				request.getSession( false ).removeAttribute( AppConstants.SESSION_ATTR_HAS_POBOX_CHANGED );
			}

		}

	}

	public static boolean isRSAUser( UserProfile profile ){
		boolean isRSAUser = false;
		String userRole = profile.getRsaUser().getHighestRole();
		for( String role : Utils.getMultiValueAppConfig( "RSA_USER" ) ){
			if( role.equalsIgnoreCase( userRole ) ){
				isRSAUser = true;
				break;
			}

		}
		return isRSAUser;
	}

	public static List<Integer> sortSections( List<Integer> parameterNames ){
		/*The below code is used to sort the sections is such an order so that PAR or PL 
		(basic sections always appears before other section on UI)*/
		int indxPAR = parameterNames.indexOf( Integer.valueOf( AppConstants.SECTION_ID_PAR ) );
		int indxPL = parameterNames.indexOf( Integer.valueOf( AppConstants.SECTION_ID_PL ) );
		if( indxPL != -1 ){
			parameterNames.remove( indxPL );
			if( indxPAR != -1 )
				parameterNames.add( 1, Integer.valueOf( AppConstants.SECTION_ID_PL ) );
			else
				parameterNames.add( 0, Integer.valueOf( AppConstants.SECTION_ID_PL ) );

		}

		return parameterNames;
	}

	/**
	 * Compares two VOs and returns true if they are different based on the
	 * comparison of the overridden equals method. 
	 * section
	 * @param rg
	 * @param rgd
	 * @return
	 */
	public static Boolean isDataChanged( RiskGroup rg, RiskGroupDetails rgd, SectionVO section, PolicyContext policyContext ){

		Boolean isDataChanged = true;
		try{
			if( rgd instanceof TravelBaggageVO ){

				// request Mapped rgd(TravelBaggage) 
				TravelBaggageVO requestMappedTbVO = (TravelBaggageVO) rgd;
				// This deductible mapping is only specific to TravelBaggage 
				for( TravellingEmployeeVO travellingEmployeeVO : requestMappedTbVO.getTravellingEmpDets() ){
					if( !Utils.isEmpty( requestMappedTbVO.getTravellingEmpDets().get( 0 ).getSumInsuredDtl().getDeductible() ) ){
						travellingEmployeeVO.getSumInsuredDtl().setDeductible( requestMappedTbVO.getTravellingEmpDets().get( 0 ).getSumInsuredDtl().getDeductible() );
					}
				}
				// The TravelBaggageVO from context 
				TravelBaggageVO contextTbVO = null;
				// for first save section details will be empty 
				contextTbVO = (TravelBaggageVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, section );
				if( Utils.isEmpty( contextTbVO ) ){
					return true;
				}
				// compare requestMapped and context TRavelBaggageVO 
				if( requestMappedTbVO.toString().equals( contextTbVO.toString() ) ){
					isDataChanged = false;
				}
			}

		}
		catch( Exception e ){
			throw new BusinessException( "cmn.compareError", null, "Error in compare" );
		}

		return isDataChanged;
	}

	public static void removeDeletedRowsFromContext( RiskGroup rg, RiskGroupDetails rgd, SectionVO section, PolicyContext policyContext ){

		try{
			if( Utils.isEmpty( rgd.getIsToBeDeleted() ) ) return;
			if( rgd instanceof TravelBaggageVO ){
				TravelBaggageVO contextTbVO = null;
				boolean deletionflag = false;
				ArrayList<TravellingEmployeeVO> toBeDeletedVOs = new ArrayList<TravellingEmployeeVO>();
				contextTbVO = (TravelBaggageVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, section );
				if( Utils.isEmpty( contextTbVO ) ) return;
				for( TravellingEmployeeVO teVO : contextTbVO.getTravellingEmpDets() ){
					if( !Utils.isEmpty( teVO.getIsToBeDeleted() ) && teVO.getIsToBeDeleted() ){
						toBeDeletedVOs.add( teVO );
						deletionflag = true;
					}
				}
				if( deletionflag ){
					for( TravellingEmployeeVO toBeDeletedVO : toBeDeletedVOs ){

						( (TravelBaggageVO) rgd ).getTravellingEmpDets().remove( toBeDeletedVO );
					}
					policyContext.addRiskGroupDetails( section.getSectionId(), rg, rgd );
				}
			}
		}
		catch( Exception e ){
			throw new BusinessException( "cmn.compareError", null, "Error in compar_2" );
		}
	}

	/* This method is used to identify the rows removed from the UI page and the corresponding
	* table entries to be deleted for these records.The Logic will be specific to each table
	* 
	* @return rgd (This will be modified based on whether insertion or deletion
	*/

	public static RiskGroupDetails setRowToBeDeletedFlag( RiskGroup rg, RiskGroupDetails rgd, SectionVO section, PolicyContext policyContext ){
		try{
			if( rgd instanceof TravelBaggageVO ){

				TravelBaggageVO requestMappedTbVO = (TravelBaggageVO) rgd;
				// The TravelBaggageVO from context 
				TravelBaggageVO contextTbVO = null;
				// for first save section details will be empty 
				contextTbVO = (TravelBaggageVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, section );
				if( Utils.isEmpty( contextTbVO ) ){
					return rgd;
				}

				ArrayList<TravellingEmployeeVO> toBeDeletedVOs = new ArrayList<TravellingEmployeeVO>();
				boolean deletionFlag = false;
				for( TravellingEmployeeVO travellingEmployeeVO : ( (TravelBaggageVO) rgd ).getTravellingEmpDets() ){
					if( Utils.isEmpty( travellingEmployeeVO.getName() ) && Utils.isEmpty( travellingEmployeeVO.getGprId() )
							&& travellingEmployeeVO.getSumInsuredDtl().getSumInsured().equals( 0.00d ) ){
						toBeDeletedVOs.add( travellingEmployeeVO );
						deletionFlag = true;
					}
				}
				if( deletionFlag ){
					for( TravellingEmployeeVO toBeDeletedVO : toBeDeletedVOs ){

						( (TravelBaggageVO) rgd ).getTravellingEmpDets().remove( toBeDeletedVO );
					}
				}
				/* adding to the request the records in context with deletion flag set as these will not be 
				 * available as part of request mapping(in rgd)*/

				for( TravellingEmployeeVO travellingEmployeeVO : contextTbVO.getTravellingEmpDets() ){
					if( !requestMappedTbVO.getTravellingEmpDets().contains( travellingEmployeeVO ) ){
						travellingEmployeeVO.setIsToBeDeleted( true );
						requestMappedTbVO.setIsToBeDeleted( true );
						requestMappedTbVO.getTravellingEmpDets().add( travellingEmployeeVO );
					}
				}
			}
		}
		catch( Exception e ){
			throw new BusinessException( "cmn.compareError", null, "Error in compar_3" );
		}
		return rgd;
	}

	/**
	 * This method is used to set the consolidate sum insured to MBVO . 
	 * @param mbvo
	 * @return
	 */
	public static MBVO setSumInsured( MBVO mbvo ){
		List<MachineDetailsVO> machineList = null;
		double sumInsured = 0.0;
		if( !Utils.isEmpty( mbvo ) ){
			machineList = mbvo.getMachineryDetails();
			if( !Utils.isEmpty( machineList ) ){
				for( MachineDetailsVO detailsVO : machineList ){
					if( !Utils.isEmpty( detailsVO ) ){
						if( !Utils.isEmpty( detailsVO.getSumInsuredVO() ) ){
							if( !Utils.isEmpty( detailsVO.getSumInsuredVO().getSumInsured() ) ) sumInsured += detailsVO.getSumInsuredVO().getSumInsured();
						}
					}
				}
			}
		}
		mbvo.setSumInsured( sumInsured );
		return mbvo;
	}

	public static String getDisabled( String temp ){

		if( temp.equalsIgnoreCase( "true" ) ){
			return "disabled = disabled";
		}
		return "";
	}
	
	
	public static String getDisabledIfFalse( String temp ){

		if( temp.equalsIgnoreCase( com.Constant.CONST_FALSE ) ){
			return "disabled = disabled";
		}
		return "";
	}

	public static String getCheckBoxStatus( String temp ){

		if( temp.equalsIgnoreCase( "true" ) ){
			return "checked=checked";
		}
		return "";
	}

	public static String getSelectStatus( String temp ){

		if( temp.equalsIgnoreCase( "true" ) ){
			return "true";
		}
		return "";
	}
	
	
	public static String getSelectStatusOfUnnamed( String temp,String temp2 ){

		if( temp.equalsIgnoreCase( "true" )){
			return "true";
		}else if(temp2.equalsIgnoreCase( "true" )){
			return "";
		}else if(temp2.equalsIgnoreCase( com.Constant.CONST_FALSE )){
			return "true";
		}
		return "";
	}

	public static Integer[] sortSections( Integer[] parameterNames ){
		/*The below code is used to sort the sections is such an order so that PAR or PL 
		(basic sections always appears before other section on UI)*/
		List<Integer> params = Arrays.asList( parameterNames );
		int indxPAR = params.indexOf( Integer.valueOf( AppConstants.SECTION_ID_PAR ) );
		int indxPL = params.indexOf( Integer.valueOf( AppConstants.SECTION_ID_PL ) );
		int firstElement = parameterNames[ 0 ];
		if( indxPL != -1 ){
			if( indxPAR != -1 )
				parameterNames[ 0 ] = Integer.valueOf( AppConstants.SECTION_ID_PAR );
			else{
				parameterNames[ indxPL ] = firstElement;
				parameterNames[ 0 ] = Integer.valueOf( AppConstants.SECTION_ID_PL );
			}

		}

		return parameterNames;
	}

	public static Double getCommissionValueOnPayablePrm( PremiumSummary prmSummary, PolicyVO policyVO ){
		double discLoadAmt = 0.0;
		double payPrm = 0.0;

		if( !Utils.isEmpty( prmSummary.getDiscLoad() ) && !Utils.isEmpty( prmSummary.getNonTaxcomm() ) ){
			discLoadAmt = ( prmSummary.getNonTaxcomm().doubleValue() * prmSummary.getDiscLoad() ) / 100;
		}

		double discPremium = ( prmSummary.getNonTaxcomm().doubleValue() + discLoadAmt );

		//payPrm = prmSummary.getPolicyFee() + prmSummary.getGovtTax() + discPremium;
		payPrm = prmSummary.getPolicyFee() + discPremium;

		double avgCommission = 0.0;

		/*EndorsmentVO endorsmentVO = null;

		if( !Utils.isEmpty( policyVO.getEndorsements() ) && policyVO.getEndorsements().size() > 0 ){
			endorsmentVO = policyVO.getEndorsements().get( 0 );
		}*/
		//Oman multibranching : payPrm always populated with New Prm - Old Prm
		/*if(!Utils.isEmpty(endorsmentVO)){
			payPrm = endorsmentVO.getPremiumVO().getPremiumAmt() - endorsmentVO.getOldPremiumVO().getPremiumAmt();
			payPrm = Math.abs(payPrm);
		}*/

		if( payPrm > 0 ) avgCommission = ( payPrm * prmSummary.getAvgComm() ) / 100;

		return avgCommission;
	}

	/*
	 * Gets the refund premium for canceled policy, 
	 * In case of cancellation refund has to be calculated as its not avilable in DB
	 */
	public static String getRefundAmountForCancelPolicy( PolicyVO policyVO, Double currentPremiumAmt, HttpServletRequest request ){

		if( !Utils.isEmpty( request.getAttribute( com.Constant.CONST_AMENDFLOWTYPE ) ) && ( (String) request.getAttribute( com.Constant.CONST_AMENDFLOWTYPE ) ).equalsIgnoreCase( com.Constant.CONST_CANCEL_POLICY ) ){

			long polExpiryDays = AppUtils.getDifference( policyVO.getPolExpiryDate(), policyVO.getEndEffectiveDate() );
			int totalDays = (int)AppUtils.getDifference( policyVO.getPolExpiryDate(), policyVO.getScheme().getEffDate() );

			SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd" );
			Double refundAmount = 0.00;
			if( !sdf.format( policyVO.getStartDate() ).equalsIgnoreCase( sdf.format( policyVO.getEndEffectiveDate() ) ) ){
				refundAmount = currentPremiumAmt - ( currentPremiumAmt * polExpiryDays / totalDays );
			}
			return Currency.getFormattedCurrency( BigDecimal.valueOf( refundAmount ),LOB.SBS.name() );
		}

		return Currency.getFormattedCurrency( BigDecimal.valueOf( currentPremiumAmt ),LOB.SBS.name() );
	}

	
	
	
	
	
	/*
	 * This method returns Refund prm as  discountedPrm + GovtTax+PolicyFees in case of Cancel Policy or
	 * else it will return calculated Prm in case of endorsements.
	 */
	public static Double getRefundPrm( PolicyVO policyVO, Double payPrm, Double payablePrm, HttpServletRequest request ){
		if( !Utils.isEmpty( request.getAttribute( com.Constant.CONST_AMENDFLOWTYPE ) ) && ( (String) request.getAttribute( com.Constant.CONST_AMENDFLOWTYPE ) ).equalsIgnoreCase( com.Constant.CONST_CANCEL_POLICY ) ){
			return payablePrm;

		}
		else{
			return Double.valueOf( Currency.getUnformttedScaledCurrency( getRefundAmountForCancelPolicy( policyVO, payablePrm, request ), LOB.SBS.name() ) );
		}
	}

	private static Double premiumRoundOff( Double refundAmount ){
		NumberFormat formatter = new DecimalFormat( SvcConstants.DEFAULT_CURRENCY_FORMAT );
		return Double.valueOf( formatter.format( refundAmount ) );

	}

	private static boolean isLeapYear( Date policyYear ){
		Calendar cal = Calendar.getInstance();
		cal.setTime( policyYear );
		GregorianCalendar greCal = new GregorianCalendar();
		return greCal.isLeapYear( cal.get( cal.YEAR ) ); //use calender.get() function to get year in YYYY format. 
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

	/**
	 * Returns the list of referral messages extracted from PolicyVO for a given action identifier.
	 * @param policyVO
	 * @param actionIdentifier
	 * @return
	 */
	public static List<String> getReferralTextListForActionId( PolicyVO policyVO, String actionIdentifier ){

		Map<ReferralLocKey, ReferralVO> refMap = policyVO.getMapReferralVO();
		List<String> referralText = new ArrayList<String>();
		for( ReferralVO referralVo : refMap.values() ){
			if( referralVo.getActionIdentifier().equalsIgnoreCase( actionIdentifier ) ){
				referralText.addAll( referralVo.getReferralText() );
			}
		}

		return referralText;
	}

	public static boolean allowQuoteCreation( HttpServletRequest request, Response responseObj, PolicyVO policyVO ){
		
		boolean result = true;
		policyVO.setClaimsHistoryExistInMissippi( true );
		/*Commented code below and moved the rule invocation to rule service request Mapper as part of Adventid: 60996*/
		/*boolean result = true;
		StringBuffer str = new StringBuffer();
		String actionIdentifier = "CLAIMS_EXIST_RULES_EXEC";
		policyVO.setRuleContext( RuleContext.ISSUE_QUOTE );
		if( !SectionRHUtils.executeReferralTask( responseObj, "CLAIMS_EXIST_RULES_EXEC", policyVO, actionIdentifier ) ){
			List<String> referralText = AppUtils.getReferralTextListForActionId( policyVO, actionIdentifier );
			responseObj.setSuccess( true );
			String message = "MESSAGE~";
			for( String text : referralText ){
				//SONAR FIX- Performance - Performance - Method concatenates strings using + in a loop
				message =str.append(text).toString();
				//message += text;
			}
			responseObj.setData( message );
			result = false;
		}*/
		return result;
	}

	/**
	 * Sets the response redirection to "jsp/hardStopPopup.jsp"
	 * @param request
	 * @param responseObj
	 * @param hardStopTextList
	 */
	public static void createHardStopReferralResponse( HttpServletRequest request, Response responseObj, List<String> hardStopTextList ){
		request.setAttribute( "hardStopTextList", hardStopTextList );
		Redirection redirection = new Redirection( "jsp/hardStopPopup.jsp", com.mindtree.ruc.mvc.Redirection.Type.TO_JSP );
		responseObj.setRedirection( redirection );
	}

	/*
	 * Gets the refund premium for canceled policy, 
	 * In case of cancellation rate has to be set to zero as premium will be zero.
	 */
	public static Double getRatesForCancelPolicy( PolicyVO policyVO, Double currentPremiumAmt, Double currentRate, Double sumInsured, Short secId, HttpServletRequest request ){

		if( !Utils.isEmpty( request.getAttribute( com.Constant.CONST_AMENDFLOWTYPE ) )
				&& ( ( (String) request.getAttribute( com.Constant.CONST_AMENDFLOWTYPE ) ).equalsIgnoreCase( com.Constant.CONST_CANCEL_POLICY ) || ( (String) request.getAttribute( com.Constant.CONST_AMENDFLOWTYPE ) )
						.equalsIgnoreCase( "VIEW_POL" ) ) ){

			long polExpiryDays = AppUtils.getDifference( policyVO.getPolExpiryDate(), policyVO.getEndEffectiveDate() );
			int totalDays = AppUtils.isLeapYear( policyVO.getStartDate() ) ? SvcConstants.NO_OF_DAYS_LEAP_YEAR : SvcConstants.NO_OF_DAYS_YEAR;

			SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd" );
			Double refundAmount = 0.00;
			Double cancelledRate = 0.00;

			if( !sdf.format( policyVO.getStartDate() ).equalsIgnoreCase( sdf.format( policyVO.getEndEffectiveDate() ) ) ){
				refundAmount = currentPremiumAmt - ( currentPremiumAmt * polExpiryDays / totalDays );
			}
			/*
			 * Rates for PL and WC section has to be same as premium.
			 */
			if( ( !Utils.isEmpty( secId ) )
					&& ( ( !Utils.isEmpty( Short.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" ) ) ) && Short.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" ) )
							.equals( secId ) ) || ( !Utils.isEmpty( Short.valueOf( Utils.getSingleValueAppConfig( "WC_SECTION" ) ) ) && Short.valueOf(
							Utils.getSingleValueAppConfig( "WC_SECTION" ) ).equals( secId ) ) ) ){

				cancelledRate = Double.valueOf( Currency.getUnformattedScaledCurrency( BigDecimal.valueOf( refundAmount ) ) );

			}
			else{

				if( !refundAmount.equals( 0.0 ) && !Utils.isEmpty( sumInsured ) ){
					cancelledRate = ( Double.valueOf( Currency.getUnformattedScaledCurrency( BigDecimal.valueOf( refundAmount ) ) ) * 100 )
							/ Double.valueOf( Currency.getUnformattedScaledCurrency( BigDecimal.valueOf( sumInsured ) ) );
				}

			}
			return Double.valueOf( Currency.getUnformattedScaledCurrency( BigDecimal.valueOf( cancelledRate ) ) );

		}
		/*if(!Utils.isEmpty( policyVO.getStatus() ) &&  Integer.valueOf( Utils.getSingleValueAppConfig( "POLICY_CANCELLED" )).equals(  policyVO.getStatus() ) ){
			return AppUtils.premiumRoundOff( 0.00 );
		}*/

		return Double.valueOf( Currency.getUnformattedScaledCurrency( BigDecimal.valueOf( currentRate ) ) );
	}

	public static void populateTurnoverToRequest( HttpServletRequest request, PolicyContext policyContext ){
		PolicyVO policyVO = policyContext.getPolicyDetails();
		if( !policyVO.getAppFlow().equals( Flow.VIEW_POL ) && !policyVO.getAppFlow().equals( Flow.VIEW_QUO ) ){
			setAnnualTurnOverFromGI( policyVO, request );
		}

	}

	//disLoadPrm - This is including Min Prem
	/*
	 * 
	 * Called from premium-page.jsp
	 * 
	 */
	public static Double getGovtTaxAmt( BigDecimal taxAmt, BigDecimal payPrm, Double disLoadPrm ){
		Double taxRate = 0.0;
		if( !Utils.isEmpty(payPrm) && payPrm.compareTo( new BigDecimal(0.0) ) != 0 ){
			taxRate = taxAmt.divide( payPrm, Currency.getResovedScale() ).multiply( BigDecimal.valueOf( 100.00 ) ).doubleValue();
		}
		Double taxFordisLoadPrm = taxRate * disLoadPrm != 0.0 ? ( taxRate * disLoadPrm ) / 100 : 0;
		return taxAmt.add( BigDecimal.valueOf( taxFordisLoadPrm ) ).doubleValue();
	}


	
	
	public static Double getTaxRateForDiscLoad( BigDecimal taxAmt,BigDecimal payPrm, Double disLoadPrm ){
		// Oman fix: Cancelled policy set tax as 
		if( Utils.isEmpty(payPrm) || payPrm.compareTo( new BigDecimal(0.0) ) == 0 ){
			return 0.0;
		}else{
			return  taxAmt.divide( payPrm ,Currency.getResovedScale(), BigDecimal.ROUND_DOWN ).multiply( BigDecimal.valueOf( 100.00 ) ) .doubleValue();
		}
	}
	/**
	 * The below method is used to identify if the distribution channel selected is Broker for RSA User login. 
	 * @param profile
	 * @param policy
	 * @return
	 */
	public static Boolean isRSAUserWithBrokerDistChannel(String profile, PolicyVO policyVO ) {
		
		Boolean isRSAUserWithBrokerChannel = Boolean.FALSE;
		Integer distributionChannel = null;
		
		if( profile.equalsIgnoreCase("EMPLOYEE") && !Utils.isEmpty( policyVO.getGeneralInfo() ) && !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus() )
				&& !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus().getDistributionChannel() ) ){
			
			distributionChannel = policyVO.getGeneralInfo().getSourceOfBus().getDistributionChannel() ;
			
			if( distributionChannel.equals( 4 ) || !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus().getBrokerName() ) ){
				isRSAUserWithBrokerChannel = Boolean.TRUE;
			}
			
		}
		return isRSAUserWithBrokerChannel;
	}
	
	/**
	 * Returns the complete URL of the file template
	 * @param request
	 * @param templateKey
	 * @return
	 */
	public static String getFileUploadTemplateURL(HttpServletRequest request, String templateKey){		
		return request.getContextPath()  + Utils.getSingleValueAppConfig(templateKey);
	}
	
	/**
	 * Return RiskSelectionEnabled as true or false based on the property RISK_SELECT_OPTION_ENABLE configuration value in C:/PAS/PAS.properties
	 *  1. Risk selection option enabled if RISK_SELECT_OPTION_ENABLE is empty OR RISK_SELECT_OPTION_ENABLE = Y
	 *  2. Risk selection option disabled if RISK_SELECT_OPTION_ENABLE is not empty AND RISK_SELECT_OPTION_ENABLE = N
	 * @return
	 */
	public static boolean isRiskSelectionEnabled(){
		
		if(	!Utils.isEmpty(Utils.getSingleValueAppConfig( "RISK_SELECT_OPTION_ENABLE")) && Utils.getSingleValueAppConfig( "RISK_SELECT_OPTION_ENABLE").equalsIgnoreCase("N") ){
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Returns false if the passed location code is configured as as exclude list against the property key in C:/PAS/PAS.properties file
	 * @param property
	 * @return
	 */
	public static boolean isPropertyDisableForLocation(String property,String locnCode){
		
		String[] multiValues = null ;
		String configValue = Utils.getSingleValueAppConfig( property );
		
		if( !Utils.isEmpty( configValue ) ){
			multiValues = configValue.split( "," );
		}
		
		if( !Utils.isEmpty( multiValues ) && multiValues.length > 0){
			List<String> disabledLocations = CopyUtils.asList( multiValues );
			
			if( !Utils.isEmpty( locnCode ) && disabledLocations.contains( locnCode ) ){
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * Oman : For cancel policy fetch premium from endorsement VO as data is not updated in tables until confirm is not done.
	 * Dubai SBS: disLoadPrm - This is including DiscLoadPrem and MinPrem
	 */
	public static Double getGovtTaxAmt( BigDecimal taxAmt, BigDecimal payPrm, Double disLoadPrm, Double discCancelPrm, PolicyVO policyVO, HttpServletRequest request ){

		if( !Utils.isEmpty( request.getAttribute( com.Constant.CONST_AMENDFLOWTYPE ) ) && ( ( (String) request.getAttribute( com.Constant.CONST_AMENDFLOWTYPE ) ).equalsIgnoreCase( com.Constant.CONST_CANCEL_POLICY ) ) ){

			return getGovtTaxAmtCancel( policyVO, discCancelPrm );

		}
		else{

			return getGovtTaxAmt( taxAmt, payPrm, disLoadPrm );

		}

	}

	private static Double getGovtTaxAmtCancel( PolicyVO policyVO, Double discCancelPrm ){

		Double tax = null;
		EndorsmentVO endorsmentVO;
		if( !Utils.isEmpty( policyVO.getEndorsements() ) && policyVO.getEndorsements().size() > 0 ){

			endorsmentVO = policyVO.getEndorsements().get( 0 );

			if( !Utils.isEmpty( endorsmentVO ) ){
				tax = Double.valueOf( Currency.getUnformttedScaledCurrency( discCancelPrm, LOB.SBS.name() ) )
						- Double.valueOf( Currency.getUnformttedScaledCurrency( endorsmentVO.getPremiumVO().getPremiumAmt(),LOB.SBS.name() ) );
				tax = Math.abs( tax );
			}
		}
		return tax;
	}




	/**
	 * Method to format the date based on the format required by dojo
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatter( Date date ){
		DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );

		if( !Utils.isEmpty( date ) ){
			return dateFormat.format( date );
		}
		else{
			return "";
		}

	}

	/**
	 * Method to get the effective date
	 * 
	 * @param policyData
	 * @return
	 */
	public static Date getHomeDate( PolicyDataVO policyData ){
		Date effectiveDate = new Date();

		if( !Utils.isEmpty( policyData ) && !Utils.isEmpty( policyData.getScheme() ) && !Utils.isEmpty( policyData.getScheme().getEffDate() ) ){
			effectiveDate = policyData.getScheme().getEffDate();

		}
		return effectiveDate;
	}

	/**
	 * Method to get the effective date
	 * 
	 * @param policyData
	 * @return
	 */
	public static Date getTravelDate( PolicyDataVO policyData ){
		Date effectiveDate = null;

		if( !Utils.isEmpty( policyData ) && !Utils.isEmpty( policyData.getScheme() ) && !Utils.isEmpty( policyData.getScheme().getEffDate() ) ){
			effectiveDate = policyData.getScheme().getEffDate();

		}
		return effectiveDate;
	}
	
	/**
	 * Method to get the expiry date
	 * 
	 * @param policyData
	 * @return
	 */
	public static Date getExpiryDate( PolicyDataVO policyData ){
		Date expiryDate = null;

		if( !Utils.isEmpty( policyData ) && !Utils.isEmpty( policyData.getScheme() ) && !Utils.isEmpty( policyData.getScheme().getExpiryDate() ) ){

			expiryDate = policyData.getScheme().getExpiryDate();

		}

		return expiryDate;
	}
	
	public static String displayTransactionNumber( HttpServletRequest request ){
		CommonVO commonVO = PolicyContextUtil.getPolicyContext( request ).getCommonDetails();
		String polQuoteNoMessage = "";
		if( commonVO.getIsQuote() && !Utils.isEmpty(commonVO.getQuoteNo())){
			polQuoteNoMessage = "Quotation number : " + commonVO.getQuoteNo();
		}
		else if(!Utils.isEmpty(commonVO.getPolicyNo())){
			polQuoteNoMessage = "Policy number : " + commonVO.getPolicyNo();
		}
		return polQuoteNoMessage;
	}
	
	public static PaymentVO mapRequestToPaymentVO(HttpServletRequest request ){
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		PaymentVO detailsVO = BeanMapper.map( request, PaymentVO.class );
		detailsVO.setPaymentDone( true );
		if( !Utils.isEmpty( request.getSession().getAttribute( "paymentCode" ) ) ) detailsVO.setPaymentMode( (String) request.getSession().getAttribute( "paymentCode" ) );
		if( !Utils.isEmpty( request.getSession().getAttribute( "paymentModeCode" ) ) )
			detailsVO.setPayModeCode( ( new Byte( (String) request.getSession().getAttribute( "paymentModeCode" ) ) ) );
		if (!Utils.isEmpty(request.getParameter(com.Constant.CONST_AMOUNT))) {
			if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && 
					(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("20") || Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("21")))
			detailsVO
					.setAmount(Double.valueOf(Currency
							.getUnformttedScaledCurrency(request
									.getParameter(com.Constant.CONST_AMOUNT), "SBS")));//142244  Amount reqd in decimal ex: 252.20
			else{
				detailsVO
				.setAmount(Double.valueOf(Currency
						.getUnformttedScaledCurrency(request
								.getParameter(com.Constant.CONST_AMOUNT), policyContext.getLOB().name())));
			}
		}
		if( !Utils.isEmpty( request.getParameter( "chequeNo" ) ) ) detailsVO.setChequeNo( Long.valueOf( request.getParameter( "chequeNo" ) ) );
		if( !Utils.isEmpty( request.getParameter( "chequeDt" ) ) ) detailsVO.setChequeDt( new Date( request.getParameter( "chequeDt" ) ) );
		if( !Utils.isEmpty( request.getParameter( "bankCode" ) ) ) detailsVO.setBankCode( new Integer( request.getParameter( "bankCode" ) ) );
		if( !Utils.isEmpty( request.getParameter( "creditCardNo" ) ) ) detailsVO.setChequeNo( Long.valueOf( request.getParameter( "creditCardNo" ) ) );
		if( !Utils.isEmpty( request.getParameter( com.Constant.CONST_EXPIRYDATE ) ) ) detailsVO.setExpiryDate( new Date( request.getParameter( com.Constant.CONST_EXPIRYDATE ) ) );
		if( !Utils.isEmpty( request.getParameter( "terminalId" ) ) ) detailsVO.setTerminalId( Long.valueOf( request.getParameter( "terminalId" ) ) );
		return detailsVO;
	}
	
	/**
	 * Method to set the premium summary details to policy data vo
	 * @param polDataVO
	 * @param request
	 */
	public static void mapPermiumSummary( PolicyDataVO polDataVO , HttpServletRequest request){
	
		if(!Utils.isEmpty( request.getParameter("commissionPremium") )) polDataVO.setCommission( Double.valueOf(request.getParameter("commissionPremium") ));
		if(!Utils.isEmpty( request.getParameter("discountPercentage") )) polDataVO.getPremiumVO().setDiscOrLoadPerc( Double.valueOf(request.getParameter("discountPercentage") ));
		if(!Utils.isEmpty( request.getParameter("policyFees") )) polDataVO.getPremiumVO().setPolicyFees( Double.valueOf(request.getParameter("policyFees") ));
		if(!Utils.isEmpty( request.getParameter("govtTax") )) polDataVO.getPremiumVO().setGovtTax( Double.valueOf(request.getParameter("govtTax") ));
		if(polDataVO.getCommonVO().getLocCode()==50) {
			if(!Utils.isEmpty( request.getParameter(com.Constant.CONST_VATTAX) )) polDataVO.getPremiumVO().setVatTax( Double.valueOf(Currency.getUnformttedScaledCurrency(request.getParameter(com.Constant.CONST_VATTAX), polDataVO.getCommonVO().getLob().toString())));
			if(!Utils.isEmpty( request.getParameter(com.Constant.CONST_VATABLEPRM) )) polDataVO.getPremiumVO().setVatablePrm( Double.valueOf(Currency.getUnformttedScaledCurrency(request.getParameter(com.Constant.CONST_VATABLEPRM), polDataVO.getCommonVO().getLob().toString())));
			
		}
		if(polDataVO.getCommonVO().getLocCode()==20 || polDataVO.getCommonVO().getLocCode()==21) {
			if(!Utils.isEmpty( request.getParameter(com.Constant.CONST_VATTAX) )) polDataVO.getPremiumVO().setVatTax( Double.valueOf(Currency.getUnformttedScaledCurrency(request.getParameter(com.Constant.CONST_VATTAX), "SBS")));
			if(!Utils.isEmpty( request.getParameter(com.Constant.CONST_VATABLEPRM) )) polDataVO.getPremiumVO().setVatablePrm( Double.valueOf(Currency.getUnformttedScaledCurrency(request.getParameter(com.Constant.CONST_VATABLEPRM), "SBS")));
			
		}
		
		if(!Utils.isEmpty( request.getParameter(com.Constant.CONST_VATTAXPERC) )) polDataVO.getPremiumVO().setVatTaxPerc( Double.valueOf(request.getParameter(com.Constant.CONST_VATTAXPERC) ));
		if(!Utils.isEmpty( request.getParameter("vatCode") )) polDataVO.getPremiumVO().setVatCode( Integer.valueOf(request.getParameter("vatCode") ));
		//if(!Utils.isEmpty( request.getParameter(com.Constant.CONST_VATABLEPRM) )) polDataVO.getPremiumVO().setVatablePrm( Double.valueOf(request.getParameter(com.Constant.CONST_VATABLEPRM) ));
		
		
	}
	
	
	/**
	 * @param request
	 * @param resp
	 * @param response
	 * @param policyData
	 * @return
	 */
	public static Redirection prepareRedirection( HttpServletRequest request, HttpServletResponse resp, Response response, PolicyDataVO policyData ){
		Redirection redirection;
		if( !Utils.isEmpty( policyData.getReferralVOList() )  &&  !Utils.isEmpty( policyData.getReferralVOList().getReferrals())  &&  !Utils.isEmpty( policyData.getReferralVOList().getReferrals().size() >0) &&
				!Utils.isEmpty( policyData.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() ) ){
			HttpSession session = request.getSession();
			/* Holding the referral messages map in session */
			session.setAttribute( "ReferralMap", policyData.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() );
			request.setAttribute("referralListVO",  policyData.getReferralVOList() );
		}
		resp.setHeader( "isRef", "true" );
		redirection = new Redirection( AppConstants.CONSOLIDATED_REFERAL_POPUP_JSP, Type.TO_JSP );
		response.setRedirection( redirection );
		return redirection;
	}
	
	/**
	 * Method check if there is a valid referral present in the data base for the endorsment/version
	 * @param pc
	 * @return
	 */
	public static  boolean isRefMsgForGenInfo( PolicyContext pc ){
		boolean isRuleFailed = false;
		Map<String, String> referralMessages = DAOUtils.getReferralMessages( pc.getCommonDetails().getPolicyId(), pc.getCommonDetails().getEndtId() ,((UserProfile)pc.getCommonDetails().getLoggedInUser() ).getRsaUser().getUserId() );
		if( !Utils.isEmpty( referralMessages ) ){
			isRuleFailed = true;
		}
		return isRuleFailed;
	}
	
	
	/**
	 * This method will populate the TaskVO in-case of Consolidated Referral
	 * 
	 * @param assignedTo
	 * @param referralLoc
	 * @param refComments
	 * @param policyDataVO
	 * @return
	 */
	public static TaskVO populateTaskVO( String assignedTo, String referralLoc, String refComments, PolicyDataVO policyDataVO,HttpServletRequest request,CommonVO commonVO ){
		TaskVO taskVO = null;
		UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
		if(!Utils.isEmpty( commonVO.getTaskDetails() )){
			taskVO = commonVO.getTaskDetails();
			taskVO.setLoggedInUser( userProfile );
			taskVO.setAssignedBy( String.valueOf( userProfile.getRsaUser().getUserId() ) );
			if( !Utils.isEmpty( assignedTo ) ){
				taskVO.setAssignedTo( assignedTo );
			}
		}else{
			taskVO = (TaskVO) Utils.newInstance( "com.rsaame.pas.vo.bus.TaskVO" );
			if( !Utils.isEmpty( assignedTo ) ){
				
				taskVO.setAssignedBy( String.valueOf( userProfile.getRsaUser().getUserId() ) );
				taskVO.setAssignedTo( assignedTo );
				taskVO.setCreatedBy( String.valueOf( userProfile.getRsaUser().getUserId() ) );
				taskVO.setLoggedInUser( userProfile );
				taskVO.setPolicyType( String.valueOf( policyDataVO.getPolicyType() ) );
				taskVO.setPolLinkingId( policyDataVO.getPolicyId() );
				if( !Utils.isEmpty( policyDataVO.getCommonVO().getEndtId() ) ){
					taskVO.setPolEndId( policyDataVO.getCommonVO().getEndtId() );
				}
				else{
					//SONAR FIX- Performance - Method invokes inefficient Number constructor; use static valueOf instead
					taskVO.setPolEndId(Long.valueOf( 0 ) );
					//taskVO.setPolEndId( new Long( 0 ) );
				}
				if( !Utils.isEmpty( policyDataVO.getCommonVO() ) && policyDataVO.getCommonVO().getIsQuote() ){
					taskVO.setTaskType( "6" );//TODO get from properties file
					taskVO.setQuote( true );
					taskVO.setQuoteNo( policyDataVO.getCommonVO().getQuoteNo() );
				}
				else{
					taskVO.setTaskType( "2" );//TODO get from properties file
					taskVO.setQuote( false );
					taskVO.setPolicyNo( policyDataVO.getCommonVO().getPolicyNo() );
				}
				taskVO.setClassCode( policyDataVO.getPolicyClassCode().byteValue());
				taskVO.setCategory( String.valueOf( policyDataVO.getPolicyType() ) );
				//taskVO.setDesc( refComments );
				taskVO.setIsOpen( true ); //Default value while assigning
				taskVO.setLob( policyDataVO.getCommonVO().getLob().toString() );
				taskVO.setLocation( referralLoc );
				
				if( !Utils.isEmpty( policyDataVO.getCommonVO().getEndtId() ) ){
					taskVO.setPolEndId( policyDataVO.getCommonVO().getEndtId() );
				}
				else{
					//taskVO.setPolEndId( new Long( 0 ) );
					taskVO.setPolEndId( Long.valueOf( 0 ) );
				}
				if( !Utils.isEmpty( policyDataVO.getCommonVO() ) && policyDataVO.getCommonVO().getIsQuote() ){
					taskVO.setTaskType( "6" );
					taskVO.setQuote( true );
					taskVO.setQuoteNo( policyDataVO.getCommonVO().getQuoteNo() );
				}
				else{
					taskVO.setTaskType( "2" );
					taskVO.setQuote( false );
					taskVO.setPolicyNo( policyDataVO.getCommonVO().getPolicyNo() );
				}
				taskVO.setClassCode( policyDataVO.getPolicyClassCode().byteValue() );
				taskVO.setCategory( String.valueOf( policyDataVO.getPolicyType() ) );
				//	taskVO.setDesc(referalTxt);
				taskVO.setIsOpen( true ); //Default value while assigning
				taskVO.setLob( policyDataVO.getCommonVO().getLob().toString() );
				taskVO.setLocation( String.valueOf( policyDataVO.getCommonVO().getLocCode() ) );
				taskVO.setTaskName( new StringBuilder().append( "Transaction " )
						.append( policyDataVO.getCommonVO().getIsQuote() ? policyDataVO.getCommonVO().getQuoteNo() : policyDataVO.getCommonVO().getPolicyNo() ).append( " is referred" )
						.toString() ); 
				if( !Utils.isEmpty( policyDataVO.getPolicyNo() ) ){
					taskVO.setPolicyNo( policyDataVO.getPolicyNo() );
				}
				taskVO.setPriority( "1" );
			}
		}
		//	policyDataVO.getReferralVOList().setTaskVO(taskVO);
		return taskVO;
	}
	
	/**
	 * @param stringArray
	 * @return
	 */
	public static Integer[] getIntegerArray( String[] stringArray ){

		Integer[] integerArray = new Integer[ stringArray.length ];
		int i = 0;
		for( String str : stringArray ){
			integerArray[ i ] = Integer.parseInt( str );
			i++;
		}
		return integerArray;

	}

	/**
	 * Method to get the date differnce 
	 * @param latestDate
	 * @param previousDate
	 * @return
	 */
	public static Long getDateDifference( Date latestDate, Date previousDate ){
		return ( ( ( latestDate.getTime() - previousDate.getTime() ) / AppConstants.DAYDIVIDER ) + 1 );
	}
	
	/**
	 * Method to return the javascript method based on the lob
	 * 
	 * @param lob
	 * @return
	 */
	//changes-HomeRevamp#7.1
	public static String getInvokingMethod(String lob){
		
		if(LOB.HOME.equals( LOB.valueOf( lob ) ) ||  LOB.TRAVEL.equals( LOB.valueOf( lob ) ) ){
			return "javascript:showMailPopup('Convert_to_policy','"+lob+"')";
		} else if ( LOB.SBS.equals( LOB.valueOf( lob ) ) ){
			return "javascript:email('Convert_to_policy')";
		} else{
			return "javascript:showMailPopup('Convert_to_policy','"+lob+"')";
		}
	}
	//changes-HomeRevamp#7.1
	/* @param request
	 * @param travelInsuranceDetailsVo
	 */
	public static double mapEndorsementVOFromRequest( HttpServletRequest request, PolicyDataVO polData ){
		
		String oldPremiumValue = "0.0";
		String newPremiumValue = "0.0";
		if(!Utils.isEmpty( request.getParameter( "oldPremiumValue" ) ) && !Utils.isEmpty( request.getParameter( "newPremiumValue" ) )){
			oldPremiumValue = request.getParameter( "oldPremiumValue" ).replaceAll( "[,]", "" );
			newPremiumValue = request.getParameter( "newPremiumValue" ).replaceAll( "[,]", "" );
		}
		
		return Double.valueOf( newPremiumValue ) - Double.valueOf( oldPremiumValue );
		
	}
	
	/**
	 * Method to populate the endorsement vo from screen for rule call
	 * @param request
	 * @param polData
	 */
	public static PolicyDataVO setEndorsementVO( HttpServletRequest request, PolicyDataVO polData ){
		com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endorsementVOs = polData.getEndorsmentVO();
		if( !Utils.isEmpty( polData ) && !Utils.isEmpty( polData.getCommonVO() ) && !polData.getCommonVO().getIsQuote() ){
			if( !Utils.isEmpty( endorsementVOs ) && !Utils.isEmpty( endorsementVOs.get( 0 ) ) && endorsementVOs.get( 0 ).getPayablePremium() == ZERO ){
				endorsementVOs.get( 0 ).setPayablePremium( AppUtils.mapEndorsementVOFromRequest( request, polData ) );
			}
			else if( Utils.isEmpty( endorsementVOs ) ){
				endorsementVOs = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>(EndorsmentVO.class);
				EndorsmentVO endorsmentVO = new EndorsmentVO();
				endorsmentVO.setPayablePremium( AppUtils.mapEndorsementVOFromRequest( request, polData ) );
				endorsementVOs.add( endorsmentVO );
			}
			polData.setEndorsmentVO( endorsementVOs );
		}
		return polData;
	}

	public static boolean isBrokerOrDirectWalkin( PolicyDataVO policyDataVo, HttpServletRequest request ){
		UserProfile userProfile = AppUtils.getUserDetailsFromSession( request );
		List<String> role = Arrays.asList( userProfile.getRsaUser().getUserRoles() );
		return Integer.valueOf( Utils.getSingleValueAppConfig( "DIST_CHANNEL_DIRECT" ) ).equals( policyDataVo.getGeneralInfo().getSourceOfBus().getDistributionChannel() )
				|| role.contains( "BROKER" )
				|| Integer.valueOf( Utils.getSingleValueAppConfig( "DIST_CHANNEL_DIRECT_WEB" ) )
						.equals( policyDataVo.getGeneralInfo().getSourceOfBus().getDistributionChannel() )
				|| Integer.valueOf( Utils.getSingleValueAppConfig( "DIST_CHANNEL_DIRECT_CALL_CENTER" ) ).equals(
						policyDataVo.getGeneralInfo().getSourceOfBus().getDistributionChannel() );
	}
	
	/*
	 * CR:123969  
	 * Added by Vishwa to disable discountLoading for FGB broker users 
	 */	
	public static boolean isFGBBroker(
			PolicyDataVO policyDataVO, HttpServletRequest request) {
		int brokerCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
		UserProfile userProfile = AppUtils.getUserDetailsFromSession( request );
		List<String> role = Arrays.asList( userProfile.getRsaUser().getUserRoles() );
		logger.info("Broker Code: "+brokerCode);
		if(AppConstants.FGB_BROKER_CODE.equals(brokerCode)&& role.contains( "BROKER" ))
		{
			logger.info("isFGBBroker... true");
			return true;
		}
		logger.info("isFGBBroker.. false");
		return false;
	}
	
	/**
	 * Method to determine if it is a quote or policy
	 * 
	 * @param commonVO
	 */
	public static boolean isQuote(CommonVO commonVO){
		boolean isQuote = false;
		
		if(!Utils.isEmpty( commonVO )){
			
			
			
			
			//New Business
			if(commonVO.getIsQuote() && Utils.isEmpty( commonVO.getPolicyNo() )){
				isQuote = true;
			}
		
			//For renewal quote
			if(!isQuote && commonVO.getIsQuote() ){
				List<Object[]> resultSet = DAOUtils.getSqlResultForPas( QueryConstants.GET_QUOTE_STATUS, commonVO.getEndtId(), commonVO.getPolicyId() );
				if(!Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
					int documentCode = Integer.valueOf( String.valueOf( resultSet.get( 0 )[0] ) );
					int quoteStatus =  Integer.valueOf( String.valueOf( resultSet.get( 0 )[1] ) );
					
					if(documentCode == 6 && quoteStatus == AppConstants.QUOTE_ACTIVE){
						isQuote = true;
					}
				}
			}
			
		}
		
		return isQuote;
	}
	
	/**
	 * Method to determine if rule is required for discount Hard Stop
	 * 
	 * @param commonVO
	 */
	public static boolean isDiscountRuleRequired(PolicyVO policyVO){
		boolean isDiscRuleRequired = true;
		List<Object> result = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_REFFERED_DISC, policyVO.getPolLinkingId(),policyVO.getEndtId() );
		if (!Utils.isEmpty(result) && result.size() > 0 && !Utils.isEmpty( result.get(0) ) ){
			if(policyVO.getPremiumVO().getDiscOrLoadPerc() >= Double.valueOf((String) result.get(0))){
				isDiscRuleRequired = false;
			}
		}
		return isDiscRuleRequired;
	}
	
	public static boolean isApprovedQuote(PolicyVO policyVO,List<String> role){
		boolean isApprovedQuote = false;
		if(role.contains("BROKER_USER")){
		List<Object> result = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_REFFERED_DISC, policyVO.getPolLinkingId(),policyVO.getEndtId() );
			if (!Utils.isEmpty(result) && result.size() > 0 && !Utils.isEmpty( result.get(0) ) && policyVO.getStatus()==23){
				isApprovedQuote = true;
			}
		}
		return isApprovedQuote;
	}
	
	/*
	 * 
	 * For Home Travel
	 * 
	 */
	public static boolean isApprovedQuoteHomeTravel(CommonVO commonVO,List<String> role){
		boolean isApprovedQuote = false;
		if(role.contains("BROKER_USER") && !Utils.isEmpty(commonVO)  && !Utils.isEmpty(commonVO.getPolicyId())){
		List<Object> result = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_REFFERED_DISC_HOME_TRAVEL, commonVO.getPolicyId(),commonVO.getEndtId() );
			if (!Utils.isEmpty(result) && result.size() > 0 && !Utils.isEmpty( result.get(0) ) && commonVO.getStatus()==23){
				isApprovedQuote = true;
			}
		}
		return isApprovedQuote;
	}
	
	/**
	 * Method to determine if it is a regular policy or endorsement
	 * 
	 * @param commonVO
	 * @return
	 */
	public static boolean isEndorsed(CommonVO commonVO){
		boolean isEndorsed = false;
		
		List<Object> result = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_POLICY_ID, commonVO.getPolicyNo(),commonVO.getConcatPolicyNo() );
		Long policyId = ((BigDecimal) result.get( 0 )).longValue();
		
		if(!Utils.isEmpty( commonVO )){
			
			if(!Utils.isEmpty( commonVO.getPolicyNo() )){
				
				List<Object> resultSet = DAOUtils.getSqlResultSingleColumnPas(QueryConstants.GET_POLICY_STATUS, commonVO.getPolicyNo(), policyId );
				
				if (!Utils.isEmpty(resultSet) && resultSet.size() > 0 && !Utils.isEmpty( resultSet.get(0) ) ){
					
					Long endtId = Long.valueOf( String.valueOf( resultSet.get( 0 ) ) );
					
					if(endtId > 0){
						isEndorsed = true;
					}
				}
			}
		}
		return isEndorsed;
	}
	

	/**
	 * method returns the amount formatted as lakhs with specified precision.
	 * @param d
	 * @param precision
	 * @return
	 */
	public static String formatAmountAsLakhs( double val ) {
	    String s = String.format( Locale.UK, "%1.2f", Math.abs( val ) );
	    String result = null;
	    s = s.replaceAll( "(.+)(...\\...)", "$1,$2" );
	    while (s.matches( "\\d{3,},.+" ) ) {
	        s = s.replaceAll("(\\d+)(\\d{2},.+)", "$1,$2");
	    }
	    result = val < 0 ? ("-" + s) : s;
	   return result.substring( 0, result.indexOf( "." ) );
	}
	
	/**
	 * Method to check if the start is editable
	 * @param role
	 * @param roleType
	 * @return
	 */
	public static boolean isStartDateEditable(String role,String[] roleType){
		boolean isStartDateEditable = false;
		List<String> roleTypeList = null;
		
		if(!Utils.isEmpty( roleType )){
			roleTypeList = Arrays.asList( roleType );
		}
		
		if(!Utils.isEmpty( roleTypeList ) && !Utils.isEmpty( role )){
			if(roleTypeList.contains( role )){
				isStartDateEditable = true;
			}
		}
		
		return isStartDateEditable;
	}

	/**
	 * Method to set the premium vo to referral vo for rules
	 * 
	 * @param request
	 * @param homeInsuranceVO
	 */
	public static void setPremiumRequest( HttpServletRequest request, PolicyDataVO policyDataVO ){
		ReferralListVO requestReferralVO = null;
		CommonVO commonVO = policyDataVO.getCommonVO();
		String totalPremium = null;
		String payablePremium = null;
		
		if(!Utils.isEmpty( commonVO )){
			if(commonVO.getIsQuote()){
				totalPremium = request.getParameter( "totalactualPremium" );
				payablePremium = request.getParameter("payable_premium");
			} else {
				totalPremium = request.getParameter( "actualProratedPremium" );
				if( Utils.isEmpty( totalPremium ) ){
					totalPremium = request.getParameter( "totalactualPremium" );
				}
				payablePremium = request.getParameter( "discountedPremium" );
			}
		}
		 
		 
		
		if(!Utils.isEmpty( totalPremium) && !Utils.isEmpty( payablePremium )){
			requestReferralVO = new ReferralListVO();
			PremiumVO referralPremiumVO = new PremiumVO();
			
			referralPremiumVO.setPremiumAmtActual( Double.parseDouble( Currency.getUnformttedScaledCurrency( totalPremium, LOB.HOME.name() ) ) );
			referralPremiumVO.setPremiumAmt( Double.parseDouble( Currency.getUnformttedScaledCurrency( payablePremium, LOB.HOME.name() ) ) );
			
			requestReferralVO.setPremiumVO( referralPremiumVO );
			policyDataVO.setReferralVOList( requestReferralVO );
		}
	}

	/**
	 * Method to set the default terminal id
	 * 
	 * @param request
	 */
	public static void setDefaultTerminalId( HttpServletRequest request ){

		UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
		int userId = userProfile.getRsaUser().getUserId();
		
		List<Object> resultSet = 	DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_TERMINAL_ID, userId );
		
		
		if(!Utils.isEmpty( resultSet ) && resultSet.size() > 0){
			request.setAttribute( "defaultTerminalId", resultSet.get(0) );
		}else{
			request.setAttribute( "defaultTerminalId", "" );
		}
		
	
		
	}
	
	/**
	 * Method to fetch the previus policy expiry date in case of renewal,
	 * @param commonVO
	 * @param request 
	 */
	public static void setExpiryDateForRenewal( CommonVO commonVO, HttpServletRequest request ){
		List<Object> expiryDate = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.RENEWAL_FETCH_PREVIOUS_EXPIRY_DATE, 
				commonVO.getQuoteNo(),commonVO.getPolicyNo() );
		
		if(!Utils.isEmpty( expiryDate ) && !Utils.isEmpty( expiryDate.get( 0 ) )){
			Date date = (Date)expiryDate.get( 0 );
		
			request.setAttribute(com.Constant.CONST_EXPIRYDATE,AppUtils.dateFormatter( date ));
		}	
	}

	/**
	 * Method to fetch the ccg code 
	 * 
	 * @param userProfile
	 * @return
	 */
	public static String getCcgCode( UserProfile userProfile ){
		List<Object> ccgCodeList = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.FETCH_USER_CCG_CODE, String.valueOf( userProfile.getRsaUser().getUserId() ));
		String ccgCode = null;;
		
		
		if(!Utils.isEmpty( ccgCodeList ) && !Utils.isEmpty( ccgCodeList.get(0) )){
			
			
			ccgCode =  (String)ccgCodeList.get(0) ;
		}
		
		
		return ccgCode;
	}

	/**
	 * Method to get the renewal policy number
	 * 
	 * @param policyNo
	 * @return
	 */
	public static String getRenewalPolicyNumber( String policyNo ){
		List<Object> renewalPolicyList = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.FETCH_RENEWAL_POLICY_NUMBER, Long.valueOf( policyNo ));
		String policyNumber = null;;
		
		
		if(!Utils.isEmpty( renewalPolicyList ) && !Utils.isEmpty( renewalPolicyList.get(0) )){
			
			
			policyNumber =  String.valueOf( renewalPolicyList.get(0) );
		}else{
			policyNumber = policyNo;
		}
		
		
		return policyNumber;
	}

	/**
	 * Method to map request to search insured vo
	 * 
	 * @param request
	 * @param class1
	 */
	public static SearchInsuredVO mapRequestToSearchInsuredVO( HttpServletRequest request, SearchInsuredVO searchInsuredVO ){
		
		StringBuilder completeName = new StringBuilder();
		if( !Utils.isEmpty( request.getParameter( "businessLine" ) ) && request.getParameter( "businessLine" ).equalsIgnoreCase( "Monoline" ) ){
			if(!Utils.isEmpty( request.getParameter( "quote_name_insuredname" ) )){
				completeName.append(  request.getParameter( "quote_name_insuredname" )  );
				if( !Utils.isEmpty( completeName ) ){
					searchInsuredVO.setCompleteName(String.valueOf( completeName )); 
		 		}
			}
			
			if(!Utils.isEmpty( request.getParameter( "quote_name_pobox" ) )){
				searchInsuredVO.setPoBox( request.getParameter( "quote_name_pobox" ) );
			}
			
		}
		else{		
			/* Mapping: "insuredsearch_name_mobile" -> "mobileNo" */
			if( !Utils.isEmpty( request.getParameter( "quote_mobile" ) ) ){
				searchInsuredVO.setMobileNo( request.getParameter( "quote_mobile" ) ); 
 			}
		
		
			if(!Utils.isEmpty( request.getParameter( "quote_name_firstname" ) )){
				completeName.append(  request.getParameter( "quote_name_firstname" )  ).append( " " );
			}
			
			if(!Utils.isEmpty( request.getParameter( "quote_name_lastname" ) )){
			completeName.append(  request.getParameter( "quote_name_lastname" )  );
			}
		
		
		
			/* Mapping: "insuredsearch_name_completeName" -> "completeName"*/ 
			if( !Utils.isEmpty( completeName ) ){
				searchInsuredVO.setCompleteName(String.valueOf( completeName )); 
 			}
		}
		return searchInsuredVO;
	}
	
	
	
	/**
	 * This method will encrypt/decrypt any data on the basis of isEncryption indicator
	 * 
	 * @param input
	 * @param isEncryption
	 * @return
	 */
	public static String encryptAndDecryptData(String input, boolean isEncryption) {
		String formattedData = null;
		DesEncrypter encrypter = new DesEncrypter("Urlencrypter"); //General name
		if (isEncryption) {
			formattedData = encrypter.encrypt(input);
			if (formattedData.contains("+")) {
				formattedData = formattedData.replace("+", "%2B");
			}
		} else {
			if (input.contains("%2B")) {
				input = input.replace("%2B", "+");
			}
			formattedData = encrypter.decrypt(input);
		}
		return formattedData;
	}
	
	public static String encryptHashDataForAPI(String input) {
		String formattedData = null;
		Encoder hashEncoder = Base64.getEncoder();
		formattedData = hashEncoder.encodeToString(input.getBytes());
		System.out.println(formattedData);
		return formattedData;
	}
	
	public static boolean renewalStatusRptVisibility( HttpServletRequest request ){
		UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
		boolean visibility = false;
		if( !Utils.isEmpty( Utils.getSingleValueAppConfig( AppConstants.DEPLOYED_LOCATION ) )
				&& ( ( Utils.getSingleValueAppConfig( AppConstants.DEPLOYED_LOCATION ).equalsIgnoreCase( "30" ) && !userProfile.getRsaUser().getHighestRole()
						.equalsIgnoreCase( "RSA_USER_1" ) ) || ( Utils.getSingleValueAppConfig( AppConstants.DEPLOYED_LOCATION ).equalsIgnoreCase( "20" ) && ( Utils.isEmpty( Utils
						.getSingleValueAppConfig( "RENEWAL_STATUS_REPORT" ) ) ||  ( !Utils
						.isEmpty( Utils.getSingleValueAppConfig( "RENEWAL_STATUS_REPORT" ).equalsIgnoreCase( "N" ) ) ) ) ) ) ){
			visibility = true;
		}
		return visibility;
	}


	/**
	 * Send mail in referral scenarios.
	 * @param baseVO
	 * @param operation
	 */
	public static void sendMail( BaseVO baseVO, String operation ){
		
		/* In case of approve quote in b2c, send mail to insured.*/
		/* In case of approve/decline quote/policy in b2b, send mail to assigned by user.*/
		/* In case of referral in b2b, send mail to assigned to user.*/
		
		MailVO mailVO = (MailVO)Utils.getBean(com.Constant.CONST_MAILVO);
		UserProfile profile = null;
		// Modified to fix SONAR Violation due to changes added for CR:130669
		String mailContent = "";
		String subject = null;
		String transaction = null;
		String insuredName = null;
		String toAddress = null;
		String fromAddress = null;
		String mailTo = null;
		String replyToEmailID= null;
		boolean isQuote = false;
		Long quoteNo = null;
		Long policyNo = null;
		String brokerName = "";
		String lob = null;
		boolean isB2C = false;
		
		try{

			if( Utils.isEmpty( operation ) ){
				logger.debug( "Operation not specified." );
				return;
			}
			
			if( baseVO instanceof PolicyDataVO ){
				PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
				PolicyDataVO dataVO = null;
				profile = (UserProfile) policyDataVO.getCommonVO().getLoggedInUser();
				lob = policyDataVO.getCommonVO().getLob().toString();
				TravelInsuranceVO travelInsVO = new TravelInsuranceVO();
				
				if( LOB.HOME.equals( policyDataVO.getCommonVO().getLob() ) ){
					HomeInsuranceVO insVO = new HomeInsuranceVO();
					insVO.setCommonVO( policyDataVO.getCommonVO() );
					dataVO = (PolicyDataVO) TaskExecutor.executeTasks( com.Constant.CONST_GEN_INFO_COMMON_LOAD, insVO );
					if(!Utils.isEmpty(dataVO.getGeneralInfo().getAdditionalInfo().getAffinityRefNo())){
						if(Utils.isEmpty(dataVO.getGeneralInfo().getSourceOfBus())){
							dataVO.getGeneralInfo().setSourceOfBus(new SourceOfBusinessVO());
						}
						dataVO.getGeneralInfo().getSourceOfBus().setPartnerName(dataVO.getGeneralInfo().getAdditionalInfo().getAffinityRefNo());
						TaskExecutor.executeTasks( "LOAD_PARTNERMGMT_DATA", dataVO );
					}
				}else if( LOB.TRAVEL.equals( policyDataVO.getCommonVO().getLob() ) ){ 
					travelInsVO.setCommonVO( policyDataVO.getCommonVO() );
					travelInsVO = (TravelInsuranceVO)TaskExecutor.executeTasks( com.Constant.CONST_GEN_INFO_COMMON_LOAD, travelInsVO );
					if(!Utils.isEmpty(travelInsVO.getGeneralInfo().getAdditionalInfo().getAffinityRefNo())){
						if(Utils.isEmpty(travelInsVO.getGeneralInfo().getSourceOfBus())){
							travelInsVO.getGeneralInfo().setSourceOfBus(new SourceOfBusinessVO());
						}
						travelInsVO.getGeneralInfo().getSourceOfBus().setPartnerName(travelInsVO.getGeneralInfo().getAdditionalInfo().getAffinityRefNo());
						TaskExecutor.executeTasks( "TRAVEL_LOAD_PARTNERMGMT_DATA", travelInsVO );
					}
					dataVO = (PolicyDataVO) travelInsVO;
				}else {
				//	PolicyDataVO insVO = new PolicyDataVO();
					//insVO.setCommonVO( policyDataVO.getCommonVO() );
					dataVO = (PolicyDataVO) TaskExecutor.executeTasks( com.Constant.CONST_GEN_INFO_COMMON_LOAD, policyDataVO );
				}
				
				if( !Utils.isEmpty( dataVO.getGeneralInfo().getInsured().getFirstName() ) && !Utils.isEmpty( dataVO.getGeneralInfo().getInsured().getLastName() ) ){
					insuredName = dataVO.getGeneralInfo().getInsured().getFirstName() + " " + dataVO.getGeneralInfo().getInsured().getLastName();
				}else if( !Utils.isEmpty( dataVO.getGeneralInfo().getInsured().getFirstName() ) ){
					insuredName = dataVO.getGeneralInfo().getInsured().getFirstName();
				}
					
				isQuote = policyDataVO.getCommonVO().getIsQuote();
				quoteNo = policyDataVO.getCommonVO().getQuoteNo();
				policyNo = policyDataVO.getCommonVO().getPolicyNo();

				if( dataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel().equals( AppConstants.DIST_CHNL_DIRECT_WEB ) || 
						((dataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel().equals( AppConstants.DIST_CHANNEL_BROKER) ||
								dataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel().equals( AppConstants.DIST_CHANNEL_AGENT)||
								dataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel().equals( AppConstants.DIST_CHANNEL_AFFINITY_DIRECT_AGENT)||
								dataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel().equals( AppConstants.DIST_CHANNEL_AFFINITY_AGENT))
								&& !Utils.isEmpty(dataVO.getGeneralInfo().getSourceOfBus().getPartnerName() ) ) ){
					
					isB2C = true;
					/* Mail to be sent to insured after Approval from RSA User
					 *  CR:130669 Modified by Vishwa for all partner
					 */
					if( operation.equalsIgnoreCase( com.Constant.CONST_APPROVE ) ){
						// Mail to be sent to insured.
						toAddress = dataVO.getGeneralInfo().getInsured().getEmailId(); 
						Integer brokerCode = dataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
						Integer classCode = dataVO.getPolicyClassCode();
						
						String fromPtnr = dataVO.getGeneralInfo().getSourceOfBus().getFromEmailID();
						String repToPtnr = dataVO.getGeneralInfo().getSourceOfBus().getReplyToEmailId();
						
						if(!Utils.isEmpty(fromPtnr) ){
							fromAddress =  fromPtnr;
							List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.REF_APP_PARTNER, brokerCode, classCode);
							if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
								mailContent =  resultSet.get( 0 );
								logger.debug("emailContent referral approval Partner coming from DB "+mailContent);
							}	
							else
							{
								mailContent = AppUtils.getTempalteContentAsString( AppConstants.B2C_REF_APPROVE_TEMPLATE );
								logger.debug("emailContent referral approval Partner coming from template "+mailContent);
							}
							
							if(!Utils.isEmpty(repToPtnr)){
								replyToEmailID = repToPtnr;	
								logger.debug("emailContent referral approval Partner replyToEmailID  from DB "+replyToEmailID);
							}
							else{
								replyToEmailID = fromPtnr;
								logger.debug("emailContent referral approval Partner replyToEmailID is fromEmailid "+replyToEmailID);
							}
							
							
						}
					/*	if(dataVO.getGeneralInfo().getSourceOfBus().getBrokerName().equals(AppConstants.FGB_BROKER_CODE))
						{
							mailContent = AppUtils.getTempalteContentAsString( AppConstants.B2C_FGB_REF_APPROVE_TEMPLATE );
							fromAddress =  Utils.getSingleValueAppConfig("B2C_DEFAULT_PARTNER_FROM_EMAILID_FGB");
							//subject = Utils.getSingleValueAppConfig( "B2C_REF_APPROVE_MAIL_SUBJECT" );
							//subject = subject.replace( AppConstants.TRANS_NO_STRING, quoteNo.toString() );
						}*/
						else{	
							
							//Oman D2C Email template change - Start
							if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) 
									&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){
								String pmmId = AppConstants.OMAN_D2C_TRAVEL;
								List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.REF_APP_OMAN, pmmId);
								if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
									mailContent =  resultSet.get( 0 );
									logger.debug("emailContent referral approval coming from DB for Oman D2C: "+mailContent);
								}
								mailContent = mailContent.replace("TRAVELPAGE", Utils.getSingleValueAppConfig("D2C_OMAN_TRAVEL_STEP1"));
							}else{
								mailContent = AppUtils.getTempalteContentAsString( AppConstants.B2C_REF_APPROVE_TEMPLATE );
							}
							//Oman D2C Email template change - End
							fromAddress = AppConstants.B2C_DEFAULT_FROM_EMAILID;
							
							if(!Utils.isEmpty(repToPtnr)){
								replyToEmailID = repToPtnr;	
								logger.debug("emailContent referral approval Cirect replyToEmailID from DB "+replyToEmailID);
							}
							else{
								replyToEmailID = AppConstants.B2C_DEFAULT_FROM_EMAILID;
								logger.debug("emailContent referral approval Direct replyToEmailID is fromEmailid "+replyToEmailID);
							}
							
							
						}
						//Oman D2C Email template change - Start
						if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) 
								&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){
							subject = Utils.getSingleValueAppConfig( "D2C_OMAN_REF_APPROVE_MAIL_SUBJECT" );
						}else{
							subject = Utils.getSingleValueAppConfig( "B2C_REF_APPROVE_MAIL_SUBJECT" );
						}
						//Oman D2C Email template change - End
						subject = subject.replace( AppConstants.TRANS_NO_STRING, quoteNo.toString() );
						String path  = null;
						if(!Utils.isEmpty(dataVO.getGeneralInfo().getSourceOfBus().getPartnerName()))
						{
							if (dataVO.getGeneralInfo().getSourceOfBus()
									.getDistributionChannel()
									.equals(AppConstants.DIST_CHNL_DIRECT_WEB)) {
								path = LOB.HOME.equals(dataVO.getCommonVO()
										.getLob()) ? Utils
										.getSingleValueAppConfig("B2C_SCHEDULER_URL_HOME")
										: Utils.getSingleValueAppConfig("B2C_SCHEDULER_URL_TRAVEL");
							} else {
								path = LOB.HOME.equals(dataVO.getCommonVO()
										.getLob()) ? Utils
										.getSingleValueAppConfig("B2C_SCHEDULER_URL_PARTNER_HOME")
										: Utils.getSingleValueAppConfig("B2C_SCHEDULER_URL_PARTNER_TRAVEL");
							}
							
						}
						else
						{
							path = LOB.HOME.equals( dataVO.getCommonVO().getLob() ) ? Utils.getSingleValueAppConfig( "B2C_SCHEDULER_URL_HOME" ) : Utils.getSingleValueAppConfig( "B2C_SCHEDULER_URL_TRAVEL" );
						}
						Double onlineDisc = 0.0;
						//CTS - 09.09.2020 - Link on referral email redirects on live page - Starts
						if(!Utils.isEmpty(dataVO.getGeneralInfo().getSourceOfBus().getPartnerName()) && !dataVO.getGeneralInfo().getSourceOfBus().getPartnerName().equals(Utils.getSingleValueAppConfig("HOME_DIGITAL_API_USER_ID"))){
						//CTS - 09.09.2020 - Link on referral email redirects on live page - Ends
							if(dataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel().equals( AppConstants.DIST_CHANNEL_BROKER)	)
							{
								//toAddress = dataVO.getGeneralInfo().getSourceOfBus().getReplyToEmailId();
								/*
								 * Commented the above line so that it triggers the referral email to 
								 * life care broker Changes are done by Vishwa for the bug 5838
								 */
								toAddress = dataVO.getGeneralInfo().getInsured().getEmailId();
							}
							onlineDisc = dataVO.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount();
							String[] urlArray = path.split("/");
							int len = urlArray[urlArray.length-1].length();
							String applicationPath = path.substring(0, path.length() - len);
							path = applicationPath + dataVO.getGeneralInfo().getSourceOfBus().getPartnerName() + "/" + urlArray[urlArray.length-1];
						}
						else
						{
							onlineDisc = Double.valueOf( Utils.getSingleValueAppConfig( "B2C_POLICY_LEVEL_DISCOUNT" ) );
						}
						// CTS - TFS 42721 - Approval Email link redirection - Starts
						if(LOB.HOME.equals(dataVO.getCommonVO().getLob())){
							List<Object> isApiQuote = checkIfAPIQuote(quoteNo);
							if(!Utils.isEmpty(isApiQuote)){
								path = Utils.getSingleValueAppConfig("UAT_B2B_APPROVAL_URL_FOR_API").trim();
							}
							
						}
												// CTS - TFS 42721 - Approval Email link redirection - Ends
						String clickHereLink = constructClickHereURL( quoteNo, toAddress, path, dataVO.getCommonVO().getLob(), dataVO.getCommonVO().getDocCode());
						mailContent = mailContent.replace( AppConstants.B2C_EMAIL_CLICK_HERE_TAG_IDF, clickHereLink);
						mailContent = mailContent.replace( AppConstants.TRANS_NO_STRING, quoteNo.toString() );
						mailContent = mailContent.replace( AppConstants.USER_NAME_STRING,insuredName);
						mailContent = mailContent.replace( "%LOB%", lob.toLowerCase() );
						
						
						if ( LOB.HOME.equals( policyDataVO.getCommonVO().getLob() ) ) {
							
							/* Commented the below line as the premium amount was getting initial value  
							 * after adding loading from CommonVo, Modified by Vishwa.
							 */
							//policyDataVO.setPremiumVO( policyDataVO.getCommonVO().getPremiumVO() );
							if( Utils.isEmpty( policyDataVO.getScheme().getPolicyType() ) && !Utils.isEmpty( policyDataVO.getScheme().getPolicyCode() ) ){
								policyDataVO.getScheme().setPolicyType( policyDataVO.getScheme().getPolicyCode().toString() );
							}else{
								policyDataVO.getScheme().setPolicyType( AppConstants.HOME_POLICY_TYPE.toString() );
							}
							//GET_MIN_PRM_TO_APPLY_HOME method accepts only HomeInsuranceVO instance. Hence create it using PolicyDataVO instead of an unnecessary service call to load it.
							HomeInsuranceVO homeInsVO = new HomeInsuranceVO();
							homeInsVO.setCommonVO( policyDataVO.getCommonVO() );
							homeInsVO.setPremiumVO( policyDataVO.getPremiumVO() );
							homeInsVO.setScheme( policyDataVO.getScheme() );
							
							double minPrmToApply = ( (BigDecimal) prmSvc.invokeMethod( SvcConstants.GET_MIN_PRM_TO_APPLY_HOME, homeInsVO ) ).doubleValue();
							if( minPrmToApply > 0 ){
								policyDataVO.getPremiumVO().setMinPremiumApplied( BigDecimal.valueOf( minPrmToApply ) );
								policyDataVO.getPremiumVO().setPremiumAmt( policyDataVO.getPremiumVO().getPremiumAmt() + minPrmToApply );
							}
							Double promoDiscountAmount = 0.0;
							if(!Utils.isEmpty( policyDataVO.getPremiumVO().getPromoDiscPerc() )){
								promoDiscountAmount -=  ((policyDataVO.getPremiumVO().getPromoDiscPerc()*policyDataVO.getPremiumVO().getPremiumAmt())/100);
								
							}
							
							if( !Utils.isEmpty( policyDataVO.getPremiumVO().getDiscOrLoadPerc() ) ){
								onlineDisc =  policyDataVO.getPremiumVO().getDiscOrLoadPerc();
							}
							
							mailContent = mailContent.replace( "%PREMIUM%", Currency.getUnitName() + " " + 
									AppUtils.getFormattedNumberWithDecimals( (policyDataVO.getPremiumVO().getPremiumAmt() * ((100.00 + onlineDisc )/100.00)+promoDiscountAmount.doubleValue() + policyDataVO.getPremiumVO().getVatTax()) ) 
									+ "(incl. VAT) and "); // Adding for VAT 142244
						}else{
							if( policyDataVO.getPremiumVO().getPremiumAmt() == ZERO ){
								travelInsVO = (TravelInsuranceVO) TaskExecutor.executeTasks( "TRAVEL_PACKAGE_PREMIUM", travelInsVO );
								policyDataVO.setPremiumVO( travelInsVO.getPremiumVO() );
							}
							mailContent = mailContent.replace( "%PREMIUM%", "" );
						}
						
					}
					else{
						return;
					}
					
				}
				else{
					//B2B flow starts from here  by Vishwa
					fromAddress = profile.getRsaUser().getEmail();
					
					if( ( operation.equalsIgnoreCase( com.Constant.CONST_APPROVE ) || operation.equalsIgnoreCase( com.Constant.CONST_DECLINE ) ) && Utils.isEmpty( mailContent ) && Utils.isEmpty( subject ) ){
						mailContent = AppUtils.getTempalteContentAsString( AppConstants.B2B_REF_APPROVE_TEMPLATE );
						subject = Utils.getSingleValueAppConfig( "B2B_REF_APPROVE_MAIL_SUBJECT" );
						mailTo = policyDataVO.getCommonVO().getTaskDetails().getAssignedBy();
					}
					else if( operation.equalsIgnoreCase( "REFERRAL" ) /*&& Utils.isEmpty( mailContent ) && Utils.isEmpty( subject )*/ ){
						/*mailContent = AppUtils.getTempalteContentAsString( AppConstants.B2B_REF_TEMPLATE );
						subject = Utils.getSingleValueAppConfig( "B2B_REF_MAIL_SUBJECT" );
						mailTo = policyDataVO.getReferralVOList().getTaskVO().getAssignedTo();*/
						
						// Mail implementation is already present in B2B for home and travel. So no need to send mail in this case.
						return;
					}
					
					if(!Utils.isEmpty(mailTo) && Integer.valueOf( mailTo ) == Integer.parseInt(Utils.getSingleValueAppConfig("USER_10"))){
						toAddress = dataVO.getGeneralInfo().getInsured().getEmailId(); 
						replyToEmailID = profile.getRsaUser().getEmail();
					}
					
				}

			}
			else if( baseVO instanceof PolicyVO ){
				PolicyVO policyVO = (PolicyVO) baseVO;
				lob = "SBS";

				insuredName = policyVO.getGeneralInfo().getInsured().getName();
				isQuote = policyVO.getIsQuote();
				quoteNo = policyVO.getQuoteNo();
				policyNo = policyVO.getPolicyNo();

				profile = (UserProfile) policyVO.getLoggedInUser();
				fromAddress = profile.getRsaUser().getEmail();
				replyToEmailID = profile.getRsaUser().getEmail();
				
				if( ( operation.equalsIgnoreCase( com.Constant.CONST_APPROVE ) || operation.equalsIgnoreCase( com.Constant.CONST_DECLINE ) ) && Utils.isEmpty( mailContent ) && Utils.isEmpty( subject ) ){
					mailContent = AppUtils.getTempalteContentAsString( AppConstants.B2B_REF_APPROVE_TEMPLATE );
					subject = Utils.getSingleValueAppConfig( "B2B_REF_APPROVE_MAIL_SUBJECT" );
					mailTo = policyVO.getTaskDetails().getAssignedBy();
				}
				else if( operation.equalsIgnoreCase( "REFERRAL" ) && Utils.isEmpty( mailContent ) && Utils.isEmpty( subject ) ){
					mailContent = AppUtils.getTempalteContentAsString( AppConstants.B2B_REF_TEMPLATE );
					subject = Utils.getSingleValueAppConfig( "B2B_REF_MAIL_SUBJECT" );
					mailContent = mailContent.replace( AppConstants.REFERRAL_REASONS_STRING, policyVO.getTaskDetails().getDesc() );
					
					List<Object> schemeList = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.FETCH_SCHEME_NAME, policyVO.getScheme().getSchemeCode() );
					if(!Utils.isEmpty( schemeList ) && schemeList.size() > 0){
						mailContent = mailContent.replace( "%SCHEME_NAME%", schemeList.get( 0 ).toString() );
					}
					mailTo = policyVO.getTaskDetails().getAssignedTo();
				}
			}

			if( Utils.isEmpty( toAddress ) && !Utils.isEmpty( mailTo ) ){
				List<Object> result = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_USER_EMAIL_ID, Integer.valueOf( mailTo ) );
				if( !Utils.isEmpty( result ) && result.size() > 0 && !Utils.isEmpty( result.get( 0 ) ) ){
					toAddress = result.get( 0 ).toString();
				}
				else{
					logger.debug( "No e-mail id configured for user id : " + mailTo );
					return;
				}
			}

			if( isQuote ){
				subject = subject.replace( AppConstants.TRANS_NO_STRING, "Quotation " + quoteNo );
				transaction = "Quotation Number : " + quoteNo;
			}
			else{
				subject = subject.replace( AppConstants.TRANS_NO_STRING, "Policy " + policyNo );
				transaction = "Policy Number : " + policyNo;
			}
			
			if( !Utils.isEmpty( profile.getRsaUser().getProfile() ) && "Broker".equalsIgnoreCase( profile.getRsaUser().getProfile() ) ){
				brokerName = "<br />Broker Name : " + SvcUtils.getLookUpDescription( com.Constant.CONST_BROKER_NAME, profile.getRsaUser().getUserId().toString(), PASServiceContext.getLocation(), profile.getRsaUser().getBrokerId() );
			}
			
			if( Utils.isEmpty( profile.getRsaUser().getEmail() ) ){
				logger.debug( "No e-mail id configured for user id : " + profile.getRsaUser().getUserId().toString() );
				return;
			}
			
			if( Utils.isEmpty( toAddress ) || Utils.isEmpty( fromAddress ) || Utils.isEmpty(replyToEmailID)){
				logger.debug( "Either of to address or from address is empty." );
				return;
			}
			

			if( operation.equalsIgnoreCase( com.Constant.CONST_APPROVE ) ){
				subject = subject.replace( "%STATUS%", "approved" );
			}else if( operation.equalsIgnoreCase( com.Constant.CONST_DECLINE ) ){
				subject = subject.replace( "%STATUS%", "declined" );
			}
			
			subject = subject.replace( "%LOB%", lob );
			if( !Utils.isEmpty( insuredName ) && !insuredName.equalsIgnoreCase( AppConstants.NO_NAME_STR ) ){
				mailContent = mailContent.replace( AppConstants.TRANSACTION_STRING, transaction + "<br />Insured Name : " +insuredName );
			}else{
				mailContent = mailContent.replace( AppConstants.TRANSACTION_STRING, transaction );				
			}
			//mailContent = mailContent.replace( AppConstants.INSURED_NAME_STRING, insuredName );
			mailContent = mailContent.replace( AppConstants.APPROVER_NAME_STRING, profile.getRsaUser().getEnglishName() );
			mailContent = mailContent.replace( AppConstants.USER_NAME_STRING, profile.getRsaUser().getEnglishName() + brokerName );
			//mailContent = mailContent.replace( AppConstants.TRANSACTION_STRING, transaction );
			mailContent = mailContent.replace( "%REFERRAL_DATE%", String.valueOf( dateFormatter( new Date (), "dd/MM/yyyy" ) ));
			
			mailVO.setToAddress( toAddress );
			mailVO.setReplyToEmailID(replyToEmailID);
			mailVO.setFromAddress( fromAddress );
			mailVO.setSubjectText( subject );
			mailVO.setMailContent( new StringBuilder( mailContent ) );
			mailVO.setCreatedOn( new Timestamp( Calendar.getInstance().getTime().getTime() ) );
			mailVO.setMailType( SvcConstants.MAIL_TYPE_HTML );

			PASMailerService pasMailerService = (PASMailerService) Utils.getBean( com.Constant.CONST_EMAILSERVICE );
			if( !isB2C ){
				mailVO.setSignature( "RSA" );
				pasMailerService.sendMail( mailVO );	
			}else{
				//Oman D2C Email template change - Start
				if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) 
						&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){
					mailVO.setDirect(true);
					pasMailerService.sendEmailWithImage( mailVO , "D2C_OMAN" );
				}else{
					pasMailerService.sendEmailWithImage( mailVO , "B2C_QUOTE" );	
				}
				//Oman D2C Email template change - End
			}
			
			if( !mailVO.getMailStatus().equalsIgnoreCase( com.Constant.CONST_SUCCESS ) ){
				logger.debug( "Failed to send mail for " + transaction );
			}

		}catch (Exception e) {
			logger.error( e.getMessage(), e );
		}
		
	}

	
	/**
	 * Method to read the HTML file content present in class-path
	 * 
	 * @param templateName
	 * @return
	 */
	public static String getTempalteContentAsString( String templateName ) {
		logger.debug("Going to read the file contents of file name - "+templateName);
		String returnValue = "";
		//Used Thread.currentThread().getContextClassLoader() to aviod sonar violation on 21-9-2017
		//java.net.URL urlToFile = AppUtils.class.getClassLoader().getResource(templateName);
		java.net.URL urlToFile = Thread.currentThread().getContextClassLoader().getResource(templateName);
		
		FileReader fileReader = null;
		String line = "";
		StringBuffer str = new StringBuffer();		/* Declared StringBuffer to replace + concate in string - sonar violation fix */
		BufferedReader reader = null;				/* Declared reader variable for closing stream - sonar violation fix */
		try {
			File file = new File(urlToFile.toURI());
			fileReader = new FileReader(file);
			reader = new BufferedReader(fileReader);
			while ((line = reader.readLine()) != null) {
				returnValue =str.append(line).append("\n").toString();		/* Replaced '+' concate of string to StringBuffer - sonar violation fix */
				//returnValue += line + "\n";
			}
		} catch (URISyntaxException e) {
			logger.debug("Exception ocuured while reading the html file content -_1"+e.getMessage());
		} catch (FileNotFoundException e) {
			logger.debug("Exception ocuured while reading the html file content -_2"+templateName);
		} catch (IOException e) {
			logger.debug("Exception ocuured while reading the html file content -_3"+e.getMessage());
		} finally {		/* Added finally block to close the stream - sonar violation fix */
			try {
				if (reader != null){ reader.close(); }
				if (fileReader != null){  fileReader.close(); }
			} catch (IOException ex) { 
				logger.debug("Exception ocuured while closing reader - "+ex.getMessage());
			}
		}
		return returnValue;
	}
	
	/**
	 * Method to format the date 
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatter( Date date, String format ){
		DateFormat dateFormat = new SimpleDateFormat( format );

		if( !Utils.isEmpty( date ) ){
			return dateFormat.format( date );
		}
		else{
			return "";
		}

	}
	
	
	
	/**
	 * This is a general method to construct the click here tag for b2c emails.
	 * 
	 * @param quoteNum
	 * @param emailId
	 * @param contextPath
	 * @return
	 */
	public static String constructClickHereURL(Long quoteNumber, String emailId, String applicationPath, LOB lob,Short docCode) {
		String clickHereTag = null;
		String clickHereURL = null;
		String[] urlArray = applicationPath.split("/");
		int len = urlArray[urlArray.length-1].length();
		applicationPath = applicationPath.substring(0, applicationPath.length() - len);
		String encryptedQuoteNumber = encryptAndDecryptData(String.valueOf(quoteNumber), Boolean.TRUE);
		String encryptedEmailId = encryptAndDecryptData(emailId, Boolean.TRUE);
								// CTS - TFS 42721 - Approval Email link redirection - Starts
		List<Object> resultList = checkIfAPIQuote(quoteNumber);
		String encryptDataForAPI = quoteNumber.toString() +"#"+emailId;
		String hashGenerated = null;
		if(LOB.HOME.equals(lob) && !Utils.isEmpty(resultList)){
			applicationPath = Utils.getSingleValueAppConfig("UAT_B2B_APPROVAL_URL_FOR_API");
			hashGenerated = encryptHashDataForAPI(encryptDataForAPI);
		}
						// CTS - TFS 42721 - Approval Email link redirection - Ends
		if (lob.equals(LOB.TRAVEL)) {
			if( !Utils.isEmpty( docCode) && String.valueOf( docCode ).equals( Utils.getSingleValueAppConfig( "RENEWAL_POL_DOC_CODE" ) ) ){
			
				clickHereURL = new StringBuilder(applicationPath)
				.append(AppConstants.B2C_FETCH_QUOTE_TRAVEL_RENEWAL_METHOD)
				.append(AppConstants.QUESTION_MARK_SYMBOL)
				.append(AppConstants.REN_QUOTE_NUM_REQ_PARAM)
				.append(AppConstants.EQUALS_SYMBOL)
				.append(encryptedQuoteNumber).toString();
			}
			else{
				clickHereURL = new StringBuilder(applicationPath)
							.append(AppConstants.B2C_FETCH_QUOTE_TRAVEL_METHOD)
							.append(AppConstants.QUESTION_MARK_SYMBOL)
							.append(AppConstants.QUOTE_NUM_REQ_PARAM)
							.append(AppConstants.EQUALS_SYMBOL)
							.append(encryptedQuoteNumber).append(AppConstants.AMPERSAND_SYMBOL)
							.append(AppConstants.EMAIL_REQ_PARAM).append(AppConstants.EQUALS_SYMBOL)
							.append(encryptedEmailId).toString();
			}
		} else {
			if( !Utils.isEmpty( docCode) && String.valueOf( docCode ).equals( Utils.getSingleValueAppConfig( "RENEWAL_POL_DOC_CODE" ) ) ){
				
				clickHereURL = new StringBuilder(applicationPath)
				.append(AppConstants.B2C_FETCH_QUOTE_HOME_RENEWAL_METHOD)
				.append(AppConstants.QUESTION_MARK_SYMBOL)
				.append(AppConstants.REN_QUOTE_NUM_REQ_PARAM)
				.append(AppConstants.EQUALS_SYMBOL)
				.append(encryptedQuoteNumber).toString();
			}
						// CTS - TFS 42721 - Approval Email link redirection - Starts
			else if (!Utils.isEmpty( docCode) && !String.valueOf( docCode ).equals( Utils.getSingleValueAppConfig( "RENEWAL_POL_DOC_CODE" ) ) && Utils.isEmpty(resultList)){
				clickHereURL = new StringBuilder(applicationPath)
						.append(AppConstants.B2C_FETCH_QUOTE_HOME_METHOD)
						.append(AppConstants.QUESTION_MARK_SYMBOL)
						.append(AppConstants.QUOTE_NUM_REQ_PARAM)
						.append(AppConstants.EQUALS_SYMBOL)
						.append(encryptedQuoteNumber).append(AppConstants.AMPERSAND_SYMBOL)
						.append(AppConstants.EMAIL_REQ_PARAM).append(AppConstants.EQUALS_SYMBOL)
						.append(encryptedEmailId).toString();
			} else if(!Utils.isEmpty(resultList) && !Utils.isEmpty(hashGenerated)){
				clickHereURL = new StringBuilder(applicationPath).append(hashGenerated).toString();
			}
						// CTS - TFS 42721 - Approval Email link redirection - Ends
		}
		clickHereTag = AppConstants.B2C_EMAIL_CLICK_HERE_TAG;
		clickHereTag = clickHereTag.replace(AppConstants.HREF_URL_IDENTIFIER, clickHereURL);
		logger.debug("Click here created "+clickHereTag);
		return clickHereTag;
	}

	/**
	 * Method to check if Free zone has to be displayed or not
	 * @param policyNo
	 * @param endtId 
	 * @param date 
	 * @return
	 */
	public static boolean isFreeZoneToBeShow( Long policyNo, Long polQuotationNo, Long endtId, Date date, Boolean isQuote ){
		boolean isFreeZoneToBeShown = false;
		Long parPolicyId = null;
		Long plPolicyId = null;
		Date parVsd = null;
		Date plVsd = null;
		Long endtIdToProcess = null;

		/* When isQuote is true, the endtId that is passed is of quote whereas we are querying on policy tables for pasResultSet and plResultSet as seen below.
		 * Hence whenever isQuote is true, pass the endtId as zero ( as it will be the initial endtId of the policy ).
		 * */
		if( isQuote ){
			endtIdToProcess = AppConstants.ZERO.longValue();
		}else{
			endtIdToProcess = endtId;
		}
		
		List<TTrnPolicyQuo> parPolicyIdList = (List<TTrnPolicyQuo>)DAOUtils.getResultForPas(QueryConstants.FETCH_PAR_POLICY_ID,policyNo,polQuotationNo,endtIdToProcess);
		List<TTrnPolicyQuo> plPolicyIdList = (List<TTrnPolicyQuo>)DAOUtils.getResultForPas(QueryConstants.FETCH_PL_POLICY_ID,policyNo,polQuotationNo,endtIdToProcess);
		
		
		if( !Utils.isEmpty( parPolicyIdList ) && parPolicyIdList.size() > 0 ){
			parPolicyId = ( (TTrnPolicyQuo) parPolicyIdList.get( 0 ) ).getId().getPolicyId();
			parVsd = ( (TTrnPolicyQuo) parPolicyIdList.get( 0 ) ).getPolValidityStartDate();
		}

		if( !Utils.isEmpty( plPolicyIdList ) && plPolicyIdList.size() > 0 ){
			plPolicyId = ( (TTrnPolicyQuo) plPolicyIdList.get( 0 )).getId().getPolicyId();
			plVsd = ( (TTrnPolicyQuo) plPolicyIdList.get( 0 )).getPolValidityStartDate();
		}
		
		if( !Utils.isEmpty( endtIdToProcess ) ){
			List<Object> pasResultSet = null;
			List<Object> plResultSet = null;

			if( !Utils.isEmpty( parPolicyId ) && !Utils.isEmpty( parVsd ) ){
				pasResultSet = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.PAR_FREE_ZONE_CERTIFICATE, parPolicyId, parVsd, parVsd );
			}

			if( !Utils.isEmpty( plPolicyId ) && !Utils.isEmpty( plVsd ) ){
				plResultSet = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.PL_FREE_ZONE_CERTIFICATE, plPolicyId, plVsd, plVsd );
			}

			if( ( !Utils.isEmpty( pasResultSet ) && pasResultSet.size() > 0 ) || ( !Utils.isEmpty( plResultSet ) && plResultSet.size() > 0 ) ){
				isFreeZoneToBeShown = true;
			}
		}
		return isFreeZoneToBeShown;
	}
	
	/**
	 * Below method checks for approved referrals to avoid showing the referrals
	 * @param policyVO
	 * @param refVo
	 * @param allowToConvert
	 * @return
	 */
	public static boolean checkForApprovedReferral( PolicyVO policyVO, ReferralListVO refVo, boolean allowToConvert, boolean sysdateCheckReq ){
		
		if( !Utils.isEmpty( refVo.getReferrals() ) && refVo.getReferrals().size() == 1 && !Utils.isEmpty( refVo.getReferrals().get( 0 ) ) ){
			
			if( !Utils.isEmpty( refVo.getReferrals().get( 0 ).getReferalDataMap() ) && refVo.getReferrals().get( 0 ).getReferalDataMap().size() == 1 ){
				
				boolean checkTasks = false;
				Long endtId = null;
				
				/* If there's a referral during convert to policy, get the fact names for which we should by pass if the referral is already approved today. */
				/* IF fact name for the referral matches fact name to be by passed, fetch task table records.*/
				String[] approvedReferralsToCheck = Utils.getMultiValueAppConfig( "RULE_FACT_APPROVED_REF", "," );
				List<String> approvedReferalsToCheck = CopyUtils.asList( approvedReferralsToCheck );
				for( String str : approvedReferalsToCheck ){
					if( refVo.getReferrals().get( 0 ).getReferalDataMap().containsKey( str ) ){
						checkTasks = true;
						break;
					}
				}
				
				List<Object> approvedRecsCount = null;
				
				if( checkTasks ){
					/* Get the tasks for quote for the logged in user. If records do not exist, show referral.*/
					List<Object[]> tasks = DAOUtils.getSqlResultForPas( QueryConstants.GET_TASKS,
							String.valueOf( policyVO.getPolLinkingId() + "%" + (policyVO.getIsQuote()?policyVO.getQuoteNo():policyVO.getPolicyNo() ) ) );
					
					if( tasks.size() < 1 ){
						allowToConvert = false;
					}
					else{
						/* If there are records in task table, get the endt Id for that task and check if the referral was approved today for that endt id.
						 * If yes, allow to convert. Else show referral.
						 * */
						for( String fact : approvedReferalsToCheck ){

							String text = Utils.getSingleValueAppConfig( "TASK_DESC_" + fact );
							for( Object[] arr : tasks ){

								if( !Utils.isEmpty( arr[ 0 ] ) && !Utils.isEmpty( arr[ 1 ] ) && arr[ 0 ].toString().contains( text ) ){

									if( arr[ 1 ].toString().split( "-" ).length == 3 ) endtId = Long.valueOf( arr[ 1 ].toString().split( "-" )[ 1 ] );
									
									String sqlQuery = null;
									
									if( !Utils.isEmpty( endtId ) ){
										//Query policy table to get the records for approved date as today.
										if(policyVO.getIsQuote()){
											if(sysdateCheckReq){
												sqlQuery = QueryConstants.GET_APPROVED_QUO_RECS_FOR_ENDT;
											}else{
												sqlQuery = QueryConstants.GET_APPROVED_QUO_RECS_FOR_ENDT_WITHOUT_SYSDATE;
											}
											approvedRecsCount = DAOUtils.getSqlResultSingleColumnPas( sqlQuery, policyVO.getQuoteNo(), endtId );
										}else{
											if(sysdateCheckReq){
												sqlQuery = QueryConstants.GET_APPROVED_POL_RECS_FOR_ENDT;
											}else{
												sqlQuery = QueryConstants.GET_APPROVED_POL_RECS_FOR_ENDT_WITHOUT_SYSDATE;
											}
											approvedRecsCount = DAOUtils.getSqlResultSingleColumnPas( sqlQuery, policyVO.getPolicyNo(), endtId );
										}
										
										/*if(policyVO.getIsQuote()){
											
										}else{
											
										}*/
										
										
										if( Integer.valueOf( approvedRecsCount.get( 0 ).toString() ) > 0 ) allowToConvert = true;
										

									}

								}
							}
						}

					}
				}

			}
		}
		return allowToConvert;
	}

	
	/**
	 * Below method checks for approved referrals to avoid showing the referrals
	 * @param policyVO
	 * @param refVo
	 * @param allowToConvert
	 * @return
	 */
	public static boolean checkForApprovedReferralForIssueQuote( PolicyVO policyVO, ReferralListVO refVo, boolean allowToIssue ){
		
		if( !Utils.isEmpty( refVo.getReferrals() ) && refVo.getReferrals().size() == 1 && !Utils.isEmpty( refVo.getReferrals().get( 0 ) ) ){
			
			if( !Utils.isEmpty( refVo.getReferrals().get( 0 ).getReferalDataMap() ) && refVo.getReferrals().get( 0 ).getReferalDataMap().size() == 1 ){
				
				boolean checkTasks = false;
				//Long endtId = null;
				
				/* If there's a referral during convert to policy, get the fact names for which we should by pass if the referral is already approved today. */
				/* IF fact name for the referral matches fact name to be by passed, fetch task table records.*/
				String[] approvedReferralsToCheck = {"brokerCode"};
				List<String> approvedReferalsToCheck = CopyUtils.asList( approvedReferralsToCheck );
				for( String str : approvedReferalsToCheck ){
					if( refVo.getReferrals().get( 0 ).getReferalDataMap().containsKey( str ) ){
						checkTasks = true;
						break;
					}
				}
				List<Object> approvedRecsCount = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_APPROVED_QUO_RECS_FOR_ISSUE_QUOTE,
						policyVO.getQuoteNo() );
				if( Integer.valueOf( approvedRecsCount.get(0).toString() ) > 0 )
				{
					allowToIssue = true;
				}
				
				/*if( checkTasks && policyVO.getIsQuote() ){
					 Get the tasks for quote for the logged in user. If records do not exist, show referral.
					List<Object[]> tasks = DAOUtils.getSqlResultForPas( QueryConstants.GET_TASKS_FOR_QUOTE,
							String.valueOf( policyVO.getPolLinkingId() + "%" + policyVO.getQuoteNo() ) );
					if( tasks.size() < 1 ){
						allowToIssue = false;
					}
					else{
						 If there are records in task table, get the endt Id for that task and check if the referral was approved today for that endt id.
						 * If yes, allow to convert. Else show referral.
						 * 
						for( String fact : approvedReferalsToCheck ){

							String text = Utils.getSingleValueAppConfig("brokerCode");
							for( Object[] arr : tasks ){

								if( !Utils.isEmpty( arr[ 0 ] ) && !Utils.isEmpty( arr[ 1 ] ) && arr[ 0 ].toString().contains( text ) ){

									if( arr[ 1 ].toString().split( "-" ).length == 3 ) endtId = Long.valueOf( arr[ 1 ].toString().split( "-" )[ 1 ] );

									if( !Utils.isEmpty( endtId ) ){
										//Query policy table to get the records for approved date as today.
										List<Object> approvedRecsCount = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_APPROVED_QUO_RECS_FOR_ISSUE_QUOTE,
												policyVO.getQuoteNo() );

										if( Integer.valueOf( approvedRecsCount.get(0).toString() ) > 0 )
										{
											allowToIssue = true;
											break;
										}

									}

								}
							}
						}

					}
				}*/

			}
		}
		return allowToIssue;
	}
	
	/* To populate a blank endtVO in case of Nil endt. */
	public static com.mindtree.ruc.cmn.utils.List<EndorsmentVO> createDefaultEndtVO( CommonVO commonVO, com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endorsmentVOs ){
		
		EndorsmentVO endorsmentVO = new EndorsmentVO();
		if( !Utils.isEmpty( commonVO.getPolicyId() ) ){
			endorsmentVO.setPolicyId( commonVO.getPolicyId() );
		}
		if( !Utils.isEmpty( commonVO.getEndtId() ) ){
			endorsmentVO.setEndtId( ( commonVO.getEndtId() ) );
		}
		if( !Utils.isEmpty( commonVO.getEndtNo() ) ){
			endorsmentVO.setEndNo( ( commonVO.getEndtNo() ) );
		}
		endorsmentVO.setSlNo( Integer.valueOf( 1 ) );
		endorsmentVOs.add( endorsmentVO );
		return endorsmentVOs;
	}
	
	/* To send mail to user when he/she resets the password using forgot password link in Login page.*/
	public static void sendMailForPasswordChange( BaseVO baseVO ){

		MailVO mailVO = (MailVO) Utils.getBean( com.Constant.CONST_MAILVO );
		ForgotPwdDetailsVO pwdDetailsVO = (ForgotPwdDetailsVO) baseVO;

		String userName = pwdDetailsVO.getUserEName(); //to be fetched from baseVO
		String newPassword = pwdDetailsVO.getRandomPassword(); //to be fetched from baseVO
		String passwordChangeDate = null;
		StringBuilder mailContent = null;
		String subject = null;
		String toAddress = pwdDetailsVO.getEmailAddress();
		String fromAddress = null;
			
		
		SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
		
		//try{
			
		//}
		

		try{
			
			fromAddress = Utils.getSingleValueAppConfig( "RSA_DIRECT_MAIL_ID" );
			mailContent = new StringBuilder( AppUtils.getTempalteContentAsString( Utils.getSingleValueAppConfig( "CHANGE_PASSWORD_MAIL_TEMPLATE" ) ) );
			subject = Utils.getSingleValueAppConfig( "CHANGE_PASSWORD_MAIL_SUBJECT" );
			if(!Utils.isEmpty( pwdDetailsVO.getResetOn() )){
				passwordChangeDate = sdf.format( pwdDetailsVO.getResetOn() );
			}
			// #AdvTicket: 103012-Password reset for Oman location
			if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) 
					&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){
				mailContent.replace( mailContent.indexOf( com.Constant.CONST_PERC_LOCATION_PERC_END ),mailContent.indexOf( com.Constant.CONST_PERC_LOCATION_PERC_END )+com.Constant.CONST_PERC_LOCATION_PERC_END.length(), "Oman" );
			}else{
				mailContent.replace( mailContent.indexOf( com.Constant.CONST_PERC_LOCATION_PERC_END ),mailContent.indexOf( com.Constant.CONST_PERC_LOCATION_PERC_END )+com.Constant.CONST_PERC_LOCATION_PERC_END.length(), "Dubai" );
			}
			mailContent.replace( mailContent.indexOf( com.Constant.CONST_PERC_USER_NAME_PERC_END ),mailContent.indexOf( com.Constant.CONST_PERC_USER_NAME_PERC_END )+com.Constant.CONST_PERC_USER_NAME_PERC_END.length(), userName );
			mailContent.replace( mailContent.indexOf( com.Constant.CONST_PERC_DATE_PERC_END ), mailContent.indexOf( com.Constant.CONST_PERC_DATE_PERC_END )+com.Constant.CONST_PERC_DATE_PERC_END.length(), passwordChangeDate );
			mailContent.replace( mailContent.indexOf( com.Constant.CONST_PERC_NEW_PSWD_PERC_END ),  mailContent.indexOf( com.Constant.CONST_PERC_NEW_PSWD_PERC_END )+com.Constant.CONST_PERC_NEW_PSWD_PERC_END.length(), newPassword );

			mailVO.setToAddress( toAddress ); //to be fetched from baseVO 
			mailVO.setFromAddress( fromAddress );
			mailVO.setSubjectText( subject );
			mailVO.setMailContent(  mailContent );
			mailVO.setCreatedOn( new Timestamp( Calendar.getInstance().getTime().getTime() ) );
			mailVO.setMailType( SvcConstants.MAIL_TYPE_HTML );

			PASMailerService pasMailerService = (PASMailerService) Utils.getBean( com.Constant.CONST_EMAILSERVICE );
			mailVO.setSignature( "RSA" );
			mailVO = pasMailerService.sendMail( mailVO );

			if( !mailVO.getMailStatus().equalsIgnoreCase( com.Constant.CONST_SUCCESS ) ){
				logger.debug( "Failed to send mail for password change." );
			}

		}
		catch( Exception e ){
			logger.error( e.getMessage(), e );
		}

	}
	
	
	/**
	 * Method to filter the credit limit rule in case of approve quote/policy when licensed by field is selected
	 * 
	 * @return
	 */
	public static boolean checkCreditLimitRule( Integer licensedBy ){
		boolean creditLimitFilter = false;
		if( !Utils.isEmpty( licensedBy ) ){
			creditLimitFilter = SvcUtils.checkCreditLimitRule( licensedBy );
		}
		return creditLimitFilter;
	}
	
	/**
	 * Method to send credit limit mail
	 * 
	 * @param baseVo
	 * @param operation
	 * @param request 
	 */
	public static void sendCreditLimitMail( BaseVO baseVO, String operation, HttpServletRequest request ){

		/* In case of approve quote in b2c, send mail to insured.*/
		/* In case of approve/decline quote/policy in b2b, send mail to assigned by user.*/
		/* In case of referral in b2b, send mail to assigned to user.*/

		MailVO mailVO = (MailVO) Utils.getBean( com.Constant.CONST_MAILVO );
		UserProfile profile = null;
		String mailContent = null;
		String subject = null;
		String transaction = null;
		
		String[] toAddress = null;
		String[] ccAddress = null;
		String fromAddress = null;
		
		String brokerName = "";
		
		String transactionType = null;
		String transactionNo = null;
		double creditPercentage = 0;
		double creditLimit = 0;
		double currentOs = 0;
		double premium = 0;
		Integer brokerId = null;
		
		try{

			if( Utils.isEmpty( operation ) ){
				logger.debug( "Operation not specified." );
				return;
			}

			if( baseVO instanceof PolicyVO ){
				PolicyVO policyVO = (PolicyVO) baseVO;
				
				GeneralInfoVO generalInfoVO = policyVO.getGeneralInfo();
				
				if(!Utils.isEmpty( generalInfoVO )){
					SourceOfBusinessVO sourceOfBusinessVO = generalInfoVO.getSourceOfBus();
					
					brokerId = sourceOfBusinessVO.getBrokerName();
				}
				
				profile = (UserProfile) policyVO.getLoggedInUser();
				fromAddress = profile.getRsaUser().getEmail();

				mailContent = AppUtils.getTempalteContentAsString( Utils.getSingleValueAppConfig( operation + "_CONTENT" ) );
				subject = Utils.getSingleValueAppConfig( operation + "_SUBJECT" );
				toAddress = SvcUtils.getCreditLimitEmail("TO_EMAIL");
				ccAddress = SvcUtils.getCreditLimitEmail( "CC_EMAIL" );
				
				PremiumVO premiumVo = policyVO.getPremiumVO();
				
				List<EndorsmentVO> endorsements = policyVO.getEndorsements();
				
				if( !Utils.isEmpty( endorsements ) ){
					premium = endorsements.get( 0 ).getPremiumVO().getPremiumAmt() - endorsements.get( 0 ).getOldPremiumVO().getPremiumAmt();

					transactionType = "Policy No";
					transactionNo = String.valueOf( policyVO.getPolicyNo() );
				}
				else{
					if( !Utils.isEmpty( premiumVo ) ){
						premium = premiumVo.getPremiumAmt();
					}

					transactionType = "Quote No";
					transactionNo = String.valueOf( policyVO.getQuoteNo() );
				}
				
				if( !Utils.isEmpty( brokerId ) ){
					brokerName = SvcUtils.getLookUpDescription( com.Constant.CONST_BROKER_NAME, profile.getRsaUser().getUserId().toString(), PASServiceContext.getLocation(), brokerId );
					creditPercentage = SvcUtils.getBrokerCreditLimitPercentage( policyVO.getPremiumVO().getPremiumAmt(), brokerId );
					creditLimit = SvcUtils.getBrokerCredit( brokerId );
					currentOs = SvcUtils.getCurrentOs( policyVO.getPremiumVO().getPremiumAmt(), brokerId );
				}
				
				switch( CreditLimitFlow.valueOf( operation ) ){
					case APPROVE_CREDIT_LIMIT:
						subject = subject.replace( com.Constant.CONST_PERC_BROKER_NAME_PERC_END, brokerName );
						subject = subject.replace( com.Constant.CONST_PERC_CREDIT_PERCENTAGE_PERC_END, String.valueOf( creditPercentage ) );
						mailContent = mailContent.replace( com.Constant.CONST_PERC_BROKER_NAME_PERC_END, brokerName );
						mailContent = mailContent.replace( com.Constant.CONST_PERC_CREDIT_PERCENTAGE_PERC_END, String.valueOf( creditPercentage ) );
						mailContent = mailContent.replace( com.Constant.CONST_PERC_CREDIT_LIMIT_PERC_END, String.valueOf( Currency.getFormattedCurrency( new BigDecimal( creditLimit ) ) ) );
						mailContent = mailContent.replace( com.Constant.CONST_PERC_CURRENT_OS_PERC_END, String.valueOf( Currency.getFormattedCurrency( new BigDecimal( currentOs ) ) ) );
						mailContent = mailContent.replace( "%PAYABLE_PREMIUM%", String.valueOf( Currency.getFormattedCurrency( new BigDecimal( premium ) ) ) );

						if( !Utils.isEmpty( transactionType ) ){
							mailContent = mailContent.replace( "%TRANSACTION_TYPE%", transactionType );
						}

						if( !Utils.isEmpty( transactionNo ) ){
							mailContent = mailContent.replace( "%TRANSACTION_NO%", transactionNo );
						}

						if( !Utils.isEmpty( brokerId ) ){
							mailContent = mailContent.replace( "%BROKER_CODE%", String.valueOf( brokerId ) );
						}

						break;
					case REFERRAL_CREDIT_LIMIT:
						subject = subject.replace( com.Constant.CONST_PERC_BROKER_NAME_PERC_END, brokerName );
						subject = subject.replace( com.Constant.CONST_PERC_CREDIT_PERCENTAGE_PERC_END, String.valueOf( creditPercentage ) );
						mailContent = mailContent.replace( com.Constant.CONST_PERC_BROKER_NAME_PERC_END, brokerName );
						mailContent = mailContent.replace( com.Constant.CONST_PERC_CREDIT_PERCENTAGE_PERC_END, String.valueOf( creditPercentage ) );
						mailContent = mailContent.replace( com.Constant.CONST_PERC_CREDIT_LIMIT_PERC_END, String.valueOf( Currency.getFormattedCurrency( new BigDecimal( creditLimit ) ) ) );
						mailContent = mailContent.replace( com.Constant.CONST_PERC_CURRENT_OS_PERC_END, String.valueOf( Currency.getFormattedCurrency( new BigDecimal( currentOs ) ) ) );
						break;
					case MESSAGE_CREDIT_LIMIT:
						subject = subject.replace( com.Constant.CONST_PERC_BROKER_NAME_PERC_END, brokerName );
						subject = subject.replace( com.Constant.CONST_PERC_CREDIT_PERCENTAGE_PERC_END, String.valueOf( creditPercentage ) );
						mailContent = mailContent.replace( com.Constant.CONST_PERC_BROKER_NAME_PERC_END, brokerName );
						mailContent = mailContent.replace( com.Constant.CONST_PERC_CREDIT_PERCENTAGE_PERC_END, String.valueOf( creditPercentage ) );
						mailContent = mailContent.replace( com.Constant.CONST_PERC_CREDIT_LIMIT_PERC_END, String.valueOf( Currency.getFormattedCurrency( new BigDecimal( creditLimit ) ) ) );
						mailContent = mailContent.replace( com.Constant.CONST_PERC_CURRENT_OS_PERC_END, String.valueOf( Currency.getFormattedCurrency( new BigDecimal( currentOs ) ) ) );
						break;
						//sonar fix
					default:
						break;
				}
				
				
			}
			else if( baseVO instanceof PolicyDataVO ){

				PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;

				CommonVO commonVO = policyDataVO.getCommonVO();

				PolicyDataVO generalVo = (PolicyDataVO) TaskExecutor.executeTasks( "POLICY_DATAVO_FROM_COMMONVO", commonVO );
				
				GeneralInfoVO generalInfoVO = generalVo.getGeneralInfo();
				
				if( !Utils.isEmpty( generalInfoVO ) ){
					SourceOfBusinessVO sourceOfBusinessVO = generalInfoVO.getSourceOfBus();

					if( !Utils.isEmpty( sourceOfBusinessVO ) ){
						 brokerId = sourceOfBusinessVO.getBrokerName();
					}
				}
						
				if( !Utils.isEmpty( commonVO ) ){
					

					profile = (UserProfile) policyDataVO.getLoggedInUser();
					fromAddress = profile.getRsaUser().getEmail();

					mailContent = AppUtils.getTempalteContentAsString( Utils.getSingleValueAppConfig( operation + "_CONTENT" ) );
					subject = Utils.getSingleValueAppConfig( operation + "_SUBJECT" );
					toAddress = SvcUtils.getCreditLimitEmail("TO_EMAIL");
					ccAddress = SvcUtils.getCreditLimitEmail( "CC_EMAIL" );
					
					if( ( !policyDataVO.getCommonVO().getIsQuote() ) ){
						
						premium = Double.valueOf( request.getSession().getAttribute(  "PAYABLE_PREMIUM"  ).toString().replaceAll( "[,]", "" ) );
						
						transactionType = "Policy No";
						transactionNo = String.valueOf( commonVO.getPolicyNo() );
					}else {
						premium = policyDataVO.getPremiumVO().getPremiumAmt();
						
						transactionType = "Quote No";
						transactionNo = String.valueOf( commonVO.getQuoteNo() );
					}
					
					if(!Utils.isEmpty( brokerId )){
						brokerName = SvcUtils.getLookUpDescription( com.Constant.CONST_BROKER_NAME, profile.getRsaUser().getUserId().toString(), PASServiceContext.getLocation(), brokerId );
						creditPercentage = SvcUtils.getBrokerCreditLimitPercentage( premium, brokerId );
						creditLimit = SvcUtils.getBrokerCredit( brokerId );
						currentOs = SvcUtils.getCurrentOs(premium, brokerId );
					}

					switch( CreditLimitFlow.valueOf( operation ) ){
						case APPROVE_CREDIT_LIMIT:
							subject = subject.replace( com.Constant.CONST_PERC_BROKER_NAME_PERC_END, brokerName );
							subject = subject.replace( com.Constant.CONST_PERC_CREDIT_PERCENTAGE_PERC_END, String.valueOf( creditPercentage ) );
							mailContent = mailContent.replace( com.Constant.CONST_PERC_BROKER_NAME_PERC_END, brokerName );
							mailContent = mailContent.replace( com.Constant.CONST_PERC_CREDIT_PERCENTAGE_PERC_END, String.valueOf( creditPercentage ) );
							mailContent = mailContent.replace( com.Constant.CONST_PERC_CREDIT_LIMIT_PERC_END, String.valueOf( Currency.getFormattedCurrency( new BigDecimal(creditLimit) ) ) );
							mailContent = mailContent.replace( com.Constant.CONST_PERC_CURRENT_OS_PERC_END, String.valueOf( Currency.getFormattedCurrency( new BigDecimal(currentOs ) )  ) );
							mailContent = mailContent.replace( "%PAYABLE_PREMIUM%", String.valueOf( Currency.getFormattedCurrency( new BigDecimal(premium ) )  ) );
							
							if(!Utils.isEmpty( transactionType )){
								mailContent = mailContent.replace( "%TRANSACTION_TYPE%", transactionType );
							}
							
							if(!Utils.isEmpty( transactionNo )){
								mailContent = mailContent.replace( "%TRANSACTION_NO%", transactionNo );
							}
							
							if(!Utils.isEmpty( brokerId )){
								mailContent = mailContent.replace( "%BROKER_CODE%", String.valueOf(brokerId) );
							}
							
							break;
						case REFERRAL_CREDIT_LIMIT:
							subject = subject.replace( com.Constant.CONST_PERC_BROKER_NAME_PERC_END, brokerName );
							subject = subject.replace( com.Constant.CONST_PERC_CREDIT_PERCENTAGE_PERC_END, String.valueOf( creditPercentage ) );
							mailContent = mailContent.replace( com.Constant.CONST_PERC_BROKER_NAME_PERC_END, brokerName );
							mailContent = mailContent.replace( com.Constant.CONST_PERC_CREDIT_PERCENTAGE_PERC_END, String.valueOf( creditPercentage ) );
							mailContent = mailContent.replace( com.Constant.CONST_PERC_CREDIT_LIMIT_PERC_END, String.valueOf( Currency.getFormattedCurrency( new BigDecimal(creditLimit) ) ) );
							mailContent = mailContent.replace( com.Constant.CONST_PERC_CURRENT_OS_PERC_END, String.valueOf( Currency.getFormattedCurrency( new BigDecimal(currentOs ) )  ) );
							break;
						case MESSAGE_CREDIT_LIMIT:
							subject = subject.replace( com.Constant.CONST_PERC_BROKER_NAME_PERC_END, brokerName );
							subject = subject.replace( com.Constant.CONST_PERC_CREDIT_PERCENTAGE_PERC_END, String.valueOf( creditPercentage ) );
							mailContent = mailContent.replace( com.Constant.CONST_PERC_BROKER_NAME_PERC_END, brokerName );
							mailContent = mailContent.replace( com.Constant.CONST_PERC_CREDIT_PERCENTAGE_PERC_END, String.valueOf( creditPercentage ) );
							mailContent = mailContent.replace( com.Constant.CONST_PERC_CREDIT_LIMIT_PERC_END, String.valueOf( Currency.getFormattedCurrency( new BigDecimal(creditLimit) ) ) );
							mailContent = mailContent.replace( com.Constant.CONST_PERC_CURRENT_OS_PERC_END, String.valueOf( Currency.getFormattedCurrency( new BigDecimal(currentOs ) )  ) );
							break;
							//sonar fix
						default:
							break;
					}

					
				}

			}

			if( !Utils.isEmpty( toAddress ) ){
				mailVO.setToAddresses( toAddress );
			}
			
			if(!Utils.isEmpty( ccAddress )){
				mailVO.setCcAddress( ccAddress );
			}
			
			mailVO.setFromAddress( fromAddress );
			mailVO.setSubjectText( subject );
			if(!Utils.isEmpty(mailContent))				/* Added if condition for null check of mailContent - sonar violation fix */
			mailVO.setMailContent( new StringBuilder( mailContent ) );
			mailVO.setCreatedOn( new Timestamp( Calendar.getInstance().getTime().getTime() ) );
			mailVO.setMailType( SvcConstants.MAIL_TYPE_HTML );

			logger.debug( "Message mail - From address " + fromAddress );
			logger.debug( "Message mail - To address " + Arrays.toString(toAddress) );		/* Added Arrays.toString for toAddress print - sonar violation fix */
			logger.debug( "Message mail - Subject " + subject );
			logger.debug( "Message mail - Content " + mailContent );
			
			PASMailerService pasMailerService = (PASMailerService) Utils.getBean( com.Constant.CONST_EMAILSERVICE );

			mailVO.setSignature( "RSA" );
			pasMailerService.sendMail( mailVO );

			if( !mailVO.getMailStatus().equalsIgnoreCase( com.Constant.CONST_SUCCESS ) ){
				logger.debug( "Failed to send mail for " + transaction );
			}
		}
		catch( Exception e ){
			logger.error( e.getMessage(), e );
		}

	}
	
	public static List<String> getExtensionSupported() {
		List<String> extnsList = new ArrayList<String>();
		String[] extns = Utils.getMultiValueAppConfig( "TRADE_LICENCE_FILE_UPLOAD_ALLOWED_EXTNS" );

		if( !Utils.isEmpty( extns ) ){
			Utils.trimAllEntries( extns );
		    extnsList = CopyUtils.asList( extns );
		}
		
		return extnsList;
	}

	public static boolean isFreeZoneToBeShow(PolicyDetailsVO policyDetailsVO, CommonVO commonVO) {
		
		boolean isFreeZoneToBeShown = false;
		Long policyId = null;
		Date vsd = null;
		
		List<TTrnPolicyQuo> plPolicyIdList = (List<TTrnPolicyQuo>)DAOUtils.getResultForPas(QueryConstants.FETCH_MONOLINE_POLICY_ID,commonVO.getPolicyNo(),commonVO.getQuoteNo(),Long.valueOf(policyDetailsVO.getEndtId()),Short.valueOf(Utils.getSingleValueAppConfig(commonVO.getLob()+"_CLASS_CODE")));
		
		if( !Utils.isEmpty( plPolicyIdList ) && plPolicyIdList.size() > 0 ){
			policyId = ( (TTrnPolicyQuo) plPolicyIdList.get( 0 )).getId().getPolicyId();
			vsd = ( (TTrnPolicyQuo) plPolicyIdList.get( 0 )).getPolValidityStartDate();
		}
		
		if( !Utils.isEmpty( commonVO.getEndtId() ) ){
			List<Object> resultSet = null;

			if( !Utils.isEmpty( policyId ) && !Utils.isEmpty( vsd ) ){
				resultSet = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.WC_FREE_ZONE_CERTIFICATE, policyId, vsd, vsd );
			}

			if( (( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ))){
				isFreeZoneToBeShown = true;
			}
		}
		
		return isFreeZoneToBeShown;
	}
	
	/**
	 * Generates a tocken based on the system time. Used for js versioning
	 * @return
	 */
	public static synchronized String getToken(){
		
		if(tokenId == null){
			tokenId = Calendar.getInstance().getTimeInMillis();
		}
		return tokenId.toString();
	}
	
	public static boolean isAlsamScheme(String tariff){
		String[] alsalamTariffs = Utils.getMultiValueAppConfig( "ALSALAM_TECOM_SCHEMES" );
		boolean isAlsalam = false;
		if( !Utils.isEmpty( alsalamTariffs ) ){
			for(String tar:alsalamTariffs){
				if(tariff.equals(tar)){
					isAlsalam = true;
				}
			}
		}
		return isAlsalam;
	}

	
	
	/**
	 * To Check if Annual Rent Added in Par Section;Adventnet Id:103286
	 * @param request
	 * @param policyContext
	 * Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7
	 */
	/*public static void isAnnualRentAddedInPAR( HttpServletRequest request, PolicyContext policyContext ){

	   PolicyVO policyVO = policyContext.getPolicyDetails();
		SectionVO sectionVO =  policyContext.getSectionDetails( AppConstants.SECTION_ID_PAR);
		if(!Utils.isEmpty(sectionVO)){
			java.util.Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = sectionVO
					.getRiskGroupDetails();
			if(!Utils.isEmpty(riskGroupDetails)){
				for (Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : riskGroupDetails
						.entrySet()) {
					ParVO parVO = (ParVO) locationEntry.getValue();

					PropertyRisks propertyRisks = parVO.getCovers();

					for (PropertyRiskDetails pRisks : propertyRisks.getPropertyCoversDetails()) {
						
						if(!Utils.isEmpty(pRisks.getRiskType()) && pRisks.getRiskType() == 13 && !Utils.isEmpty(pRisks.getCover()) && !(pRisks.getCover() == 0.0) ){//only Stocks : risk 9
							LocationVO locationVO = (LocationVO) policyContext.getRiskGroup( AppConstants.SECTION_ID_BI, String.valueOf(pRisks.getBuildingId()) );
							if(!Utils.isEmpty(locationVO)){
							   policyContext.getPolicyDetails().setAnnualRentAddedInPAR(true);
							   break;
	
							}
						}else if(!Utils.isEmpty(pRisks.getRiskType()) && pRisks.getRiskType() == 13){//only Stocks : risk 9
							policyContext.getPolicyDetails().setAnnualRentAddedInPAR(false);
						}

					}

				}
			}
		  }
			
		}*/
	//disLoadPrm - This is including Min Prem
	/*
	 * Oman : For cancel policy fetch premium from endorsement VO as data is not updated in tables until confirm is not done.
	 * Dubai SBS: disLoadPrm - This is including DiscLoadPrem and MinPrem
	 */
	public static Double getVatTax( BigDecimal taxAmt, BigDecimal payPrm, Double disLoadPrm, Double discCancelPrm, PolicyVO policyVO, HttpServletRequest request ){
		if( !Utils.isEmpty( request.getAttribute( com.Constant.CONST_AMENDFLOWTYPE ) ) && ( ( (String) request.getAttribute( com.Constant.CONST_AMENDFLOWTYPE ) ).equalsIgnoreCase( com.Constant.CONST_CANCEL_POLICY ) ) ){

			return getVatTaxAmtCancel( policyVO, discCancelPrm );

		}
		else{
			return getVatTaxAmt( taxAmt, payPrm, disLoadPrm,discCancelPrm );

		}

	}
	
	/*
	 * 
	 * Called from premium-page.jsp
	 * 
	 *///0.321
	//taxamt = 290.4255  payprem=5808.51  discloadpremium=580.85  discancel=6389.360000000001
	public static Double getVatTaxAmt( BigDecimal taxAmt, BigDecimal payPrm, Double disLoadPrm, Double discCancelPrm ){
		
		return taxAmt.doubleValue();
	}
	
	
	private static Double getVatTaxAmtCancel( PolicyVO policyVO, Double discVatPremium ){
		Double tax = null;
		EndorsmentVO endorsmentVO;
		if( !Utils.isEmpty( policyVO.getEndorsements() ) && policyVO.getEndorsements().size() > 0 ){

			endorsmentVO = policyVO.getEndorsements().get( 0 );

			if( !Utils.isEmpty( endorsmentVO ) ){
				endorsmentVO.getOldPremiumVO().getPremiumAmt();
				tax = Double.valueOf( Currency.getUnformttedScaledCurrency( discVatPremium, LOB.SBS.name() ) )
						- Double.valueOf( Currency.getUnformttedScaledCurrency( endorsmentVO.getPremiumVO().getPremiumAmt(),LOB.SBS.name() ) );
				tax = Math.abs( tax );
			}
		}
		return tax;
	}
	
	/**
	 * 142244 - Vat Tax Implementation - TRAVEL
	 */
	@SuppressWarnings("deprecation")
	public static Map<Integer, Double> calculateVatTaxAndVatableAmount(BaseVO baseVO, Double premiumAfterDiscount, HttpServletRequest request) {
		
		logger.info("Inside AppUtils.calculateVatTaxAndVatableAmount");
		
		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO)baseVO;
		
		Map<Integer, Double> vatAmtAndDaysMap = new HashMap<Integer, Double>();
		Map<Integer, Double> vatResults = new HashMap<Integer, Double>();
		
		Double vatTaxAmount = 0.0, vatTaxPerc = 0.0, vatableAmount = 0.0;		
		Double premiumAmt = 0.0, proratedPremiumAmt = 0.0, proratedVatTaxAmount = 0.0;
		Double oldVatTaxAmt = 0.0, oldVatablePrm = 0.0, oldPremiumAmt = 0.0;
		
		Date polEffectiveDate = null, polExpiryDate = null, endEffectiveDate = null, endExpiryDate = null, vatEffDate = null;
		Date  prodDt = null;
		
		int endDiffInDays = 0, endPeriodDays = 0;        
        int polDiffInDays = 0, policyPeriodDays = 0;
		
		/*Start Travel VAT */
        double  vatAmt=0.0;
        double retainableVatAmt=0.0;
        boolean isCancel=false;
        /*End Travel VAT */
		List<Date> polDateList = new LinkedList<Date>();
		List<Date> polEndDateList = new LinkedList<Date>();
		int loggedInLoc = travelInsuranceVO.getCommonVO().getLocCode();
		Comparator<Date> cmp = new Comparator<Date>() {
		    @Override
		    public int compare(Date date1, Date date2) {
		        return date1.compareTo(date2);
		    }
		};
		
		String defaultDateFormat = Utils.getSingleValueAppConfig("DEFAULT_DATE_FORMAT"); // MM/dd/yyyy  // 01/01/2018
		
		try {
			if(loggedInLoc == 50) { // Ticket 172479
				vatEffDate = new SimpleDateFormat(defaultDateFormat).parse(SvcUtils.populateVatDt());
				prodDt = vatEffDate;
			}
			if(loggedInLoc == 20 || loggedInLoc == 21){
				vatEffDate = new SimpleDateFormat(defaultDateFormat).parse(SvcUtils.populateVatDt());
				/*Dileep 17 Oct*/
				//travelStandardRDate = new SimpleDateFormat(defaultDateFormat).parse(Utils.getSingleValueAppConfig("TravelStandardEffDate"));
				String vatEffStdRate = Utils.getSingleValueAppConfig("TravelStandardEffDate");
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
				prodDt = sdf.parse(vatEffStdRate);
			}
			//SvcUtils.populateVatDt();
		} catch (ParseException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
	/*added by Dileep on 12th Oct For Travel VAT */	
		Map<String, Object> result = new HashMap<String, Object>();
		Date polIssueDate=null;
		String d2 = Utils.getSingleValueAppConfig("TerrCruiseInductionDate");
		SimpleDateFormat s2 = new SimpleDateFormat("MM-dd-yyyy");
		if( !Utils.isEmpty( vatEffDate ) && !Utils.isEmpty( vatEffDate )){
				result.put( "vatEffDate", vatEffDate ); // Vat Effective Date
				logger.debug( "**In fetchVatRateAndCode()** vatEffDate" +vatEffDate);
			}
				if(!travelInsuranceVO.getCommonVO().getIsQuote()){
					polIssueDate = DAOUtils.getPreparedDateofQuoTravel(travelInsuranceVO.getCommonVO().getPolicyNo(),travelInsuranceVO.getCommonVO().getPolicyId());
					if( !Utils.isEmpty( polIssueDate ) && !Utils.isEmpty( vatEffDate ) ){
							if (loggedInLoc == 50) { // Ticket 172479
								String vatEffetiveDate = s2.format(prodDt);
								String policyIssueDate = s2.format(polIssueDate);
								if (policyIssueDate.compareTo(vatEffetiveDate) < 0) {// or if(polIssueDate.after(prodDt)){
									travelInsuranceVO.getCommonVO().setIslegacyPolicy(true);
									logger.debug("**In fetchVatRateAndCode()** is Policy legacy : Yes");
			
								}
							}  
							if (loggedInLoc == 20 || loggedInLoc == 21) {
								if (polIssueDate.compareTo(prodDt) < 0) {// or if(polIssueDate.after(prodDt)){
									travelInsuranceVO.getCommonVO().setIslegacyPolicy(true);
									logger.debug("**In fetchVatRateAndCode()** is Policy legacy : Yes");
			
								}
							}
				
				}
			}
		/*Ends*/
		if(!Utils.isEmpty(travelInsuranceVO) && !Utils.isEmpty(travelInsuranceVO.getCommonVO())) {
			if(travelInsuranceVO.getCommonVO().getLob().equals(LOB.TRAVEL)) {
				
				if(!Utils.isEmpty(travelInsuranceVO) && !Utils.isEmpty(travelInsuranceVO.getPremiumVO())) {
					
					if(travelInsuranceVO.getCommonVO().getAppFlow().equals(Flow.AMEND_POL) || 
							travelInsuranceVO.getCommonVO().getAppFlow().equals(Flow.VIEW_POL)) {
						
						polEffectiveDate = travelInsuranceVO.getCommonVO().getPolEffectiveDate();
						polExpiryDate = travelInsuranceVO.getScheme().getExpiryDate();
						polDateList.add(vatEffDate); // VAT Effective Date
						polDateList.add(polEffectiveDate);
						
						vatTaxPerc = travelInsuranceVO.getPremiumVO().getVatTaxPerc();						
						premiumAmt = premiumAfterDiscount;
						
						if(!Utils.isEmpty(travelInsuranceVO.getEndorsmentVO()) && 
								!Utils.isEmpty(travelInsuranceVO.getEndorsmentVO().get(0).getOldPremiumVO()) &&
                                	!Utils.isEmpty(travelInsuranceVO.getEndorsmentVO().get(0).getOldPremiumVO().getPremiumAmt())) {
							
							endEffectiveDate = travelInsuranceVO.getEndorsmentVO().get(0).getEndEffDate();
							endExpiryDate = travelInsuranceVO.getScheme().getExpiryDate(); // endExpiryDate = travelInsuranceVO.getEndorsmentVO().get(0).getEndDate();
							polEndDateList.add(vatEffDate); // VAT Effective Date
							polEndDateList.add(endEffectiveDate);
							
							
							logger.info("Max Date out of PolEndEffDate and VatEffDate :"+Collections.max(polEndDateList, cmp)+"\n");
							endDiffInDays = (int) ((endExpiryDate.getTime() - Collections.max(polEndDateList, cmp).getTime()) / (1000 * 60 * 60 * 24)) + 1;
	                        endPeriodDays = (int) ((endExpiryDate.getTime() - endEffectiveDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
							
							oldPremiumAmt = travelInsuranceVO.getEndorsmentVO().get(0).getOldPremiumVO().getPremiumAmt();
							
						if(!Utils.isEmpty(request.getAttribute( com.Constant.CONST_CANCELDETAILS ))){
                                
                                isCancel=true;
                            }
							try {								
								//vatResults = DAOUtils.getVatAmountTravel(travelInsuranceVO.getCommonVO().getPolicyId(), travelInsuranceVO.getCommonVO().getEndtNo() - 1);
								vatResults = DAOUtils.getVatAmountTravelByPolIDAndPolEndId(travelInsuranceVO.getCommonVO().getPolicyId(), travelInsuranceVO.getCommonVO().getEndtId(),isCancel);
								
								oldVatTaxAmt = vatResults.get(0);
								oldVatablePrm = vatResults.get(1);
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							//proratedPremiumAmt = (premiumAfterDiscount - (travelInsuranceVO.getEndorsmentVO().get(0).getOldPremiumVO().getPremiumAmt() - oldVatTaxAmt));
								proratedPremiumAmt = (premiumAfterDiscount - oldPremiumAmt );	
							
							/*Added on 17 Oct*/
				            if(!Utils.isEmpty(travelInsuranceVO.getCommonVO().getIslegacyPolicy()) && travelInsuranceVO.getCommonVO().getIslegacyPolicy()){
				                /*Refund Scenario*/
				                request.setAttribute(com.Constant.CONST_LEGACYREFUND,travelInsuranceVO.getCommonVO().getIslegacyPolicy());
				            }
							if(!Utils.isEmpty(request.getAttribute( com.Constant.CONST_CANCELDETAILS ))){
							    oldPremiumAmt=0.0;
							    oldPremiumAmt = travelInsuranceVO.getEndorsmentVO().get(0).getPremiumVO().getPremiumAmt();
							    proratedPremiumAmt = (premiumAfterDiscount - oldPremiumAmt );    
                            }
						}
						
						logger.info("Max Date out of PolEffDate and VatEffDate :"+Collections.max(polDateList, cmp)+"\n");						
						polDiffInDays = (int) ((polExpiryDate.getTime() - Collections.max(polDateList, cmp).getTime()) / (1000 * 60 * 60 * 24)) + 1;
						policyPeriodDays = (int) ((polExpiryDate.getTime() - polEffectiveDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
                        
                        logger.info("EndEffectiveDate: "+endEffectiveDate+" EndExpiryDate: "+endExpiryDate+
                        		" VatTaxPerc:_1"+vatTaxPerc+" PremiumAmt:_1"+premiumAmt+" ProratedPremiumAmt: "+proratedPremiumAmt);
                        
                        logger.info("polDiffInDays: "+polDiffInDays+" policyPeriodDays: "+policyPeriodDays+
                        		" endDiffInDays: "+endDiffInDays+" endPeriodDays: "+endPeriodDays);
						
					} else {						
						polEffectiveDate = travelInsuranceVO.getCommonVO().getPolEffectiveDate();
						polExpiryDate = travelInsuranceVO.getScheme().getExpiryDate();
						polDateList.add(vatEffDate); // VAT Effective Date
						polDateList.add(polEffectiveDate);
						
						vatTaxPerc = travelInsuranceVO.getPremiumVO().getVatTaxPerc();						
						premiumAmt = premiumAfterDiscount;						
						
						logger.info("Max Date out of PolicyEffDate and VatEffDate :"+Collections.max(polDateList, cmp)+"\n");						
						polDiffInDays = (int) ((polExpiryDate.getTime() - Collections.max(polDateList, cmp).getTime()) / (1000 * 60 * 60 * 24)) + 1;
						policyPeriodDays = (int) ((polExpiryDate.getTime() - polEffectiveDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
						
						logger.info("PolEffDate: "+polEffectiveDate+" PolExpiryDate: "+polExpiryDate+" VatTaxPerc:_2"+vatTaxPerc+" PremiumAmt:_2"+premiumAmt);
						logger.info("polDiffInDays: "+polDiffInDays+" policyPeriodDays: "+policyPeriodDays+" endDiffInDays: "+endDiffInDays+" endPeriodDays: "+endPeriodDays);
					}
					request.setAttribute(com.Constant.CONST_VATTAXPERC, vatTaxPerc);	
				}
			}
		}
		
		if(policyPeriodDays != 0) {
			vatTaxAmount = premiumAmt * polDiffInDays/policyPeriodDays * vatTaxPerc/100;
			vatableAmount = premiumAmt * polDiffInDays/policyPeriodDays;
			retainableVatAmt=vatTaxAmount;
			if(endPeriodDays != 0) {
				
			if(!Utils.isEmpty(travelInsuranceVO.getCommonVO().getIslegacyPolicy()) && travelInsuranceVO.getCommonVO().getIslegacyPolicy() && travelInsuranceVO.getPremiumVO().getOldVatAmt()==0
					&& (!Utils.isEmpty(request.getAttribute( com.Constant.CONST_CANCELDETAILS )) || proratedPremiumAmt < 0)){	
					proratedVatTaxAmount=0.0; //153167
				}
				else{
				
					proratedVatTaxAmount = (proratedPremiumAmt * endDiffInDays/endPeriodDays * vatTaxPerc/100);
				}
				vatTaxAmount = oldVatTaxAmt + proratedVatTaxAmount;
				vatableAmount = oldVatablePrm + (proratedPremiumAmt * endDiffInDays/endPeriodDays);
				 
			}
					
		}
		
		logger.info("Vat Fields: VatTax: "+vatTaxAmount+" :: ProratedVatTaxAmount :"+proratedVatTaxAmount+" :: VatableAmount : "+vatableAmount);
		if(loggedInLoc == 50) {
			if(travelInsuranceVO.getCommonVO().getAppFlow().equals(Flow.AMEND_POL) || !Utils.isEmpty( travelInsuranceVO.getEndorsmentVO())) {
				vatTaxAmount = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(vatTaxAmount), travelInsuranceVO.getCommonVO().getLob().toString()).replace(",", "")));
				request.setAttribute(com.Constant.CONST_VATTAX, Currency.getUnformattedScaledCurrency(new BigDecimal(vatTaxAmount), travelInsuranceVO.getCommonVO().getLob().toString()));
				proratedVatTaxAmount = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(proratedVatTaxAmount), travelInsuranceVO.getCommonVO().getLob().toString()).replace(",", "")));
	            request.setAttribute(com.Constant.CONST_PRORATEDVATTAX, Currency.getUnformattedScaledCurrency(new BigDecimal(proratedVatTaxAmount), travelInsuranceVO.getCommonVO().getLob().toString()));
			//Added by Dileep for endorsement for Travel VAT
	            request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getUnformattedScaledCurrency(new BigDecimal(Math.abs(proratedVatTaxAmount)), travelInsuranceVO.getCommonVO().getLob().toString()));
	            String proPremAmt = Currency.getFormattedCurrency(new BigDecimal(Math.abs(proratedPremiumAmt)+Math.abs( proratedVatTaxAmount)),
						travelInsuranceVO.getCommonVO().getLob().toString());
	            Double transaction_premium_new = SvcUtils.getRoundingOffBah(Double.parseDouble(proPremAmt.replace(",", "")));
	            request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency.getUnformattedScaledCurrency(new BigDecimal(transaction_premium_new), travelInsuranceVO.getCommonVO().getLob().toString()));
	           
	 		if(!Utils.isEmpty(travelInsuranceVO.getCommonVO().getIslegacyPolicy()) && travelInsuranceVO.getCommonVO().getIslegacyPolicy()&& travelInsuranceVO.getPremiumVO().getOldVatAmt()==0){
	                retainableVatAmt=0.0;    
	            }
	            
			} else if(!Utils.isEmpty(request.getAttribute( com.Constant.CONST_CANCELDETAILS ))){
			    logger.info("Enter Cancel Details.........................");
			     if(policyPeriodDays != 0) {
			        
			        if(!Utils.isEmpty(travelInsuranceVO.getCommonVO().getIslegacyPolicy()) && travelInsuranceVO.getCommonVO().getIslegacyPolicy() && travelInsuranceVO.getPremiumVO().getOldVatAmt()==0){
			            vatTaxAmount=0.0;
						vatableAmount =  (travelInsuranceVO.getPremiumVO().getPremiumAmtActual() + (travelInsuranceVO.getPremiumVO().getPremiumAmtActual() * (travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc()/100)))-( premiumAmt * polDiffInDays/policyPeriodDays)    ;
			        }else{
			        	  Double correctedVatablePremium = travelInsuranceVO.getPremiumVO().getPremiumAmtActual() + (travelInsuranceVO.getPremiumVO().getPremiumAmtActual() * (travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc()/100));
			              vatTaxAmount = ((correctedVatablePremium)* vatTaxPerc/100)- premiumAmt * polDiffInDays/policyPeriodDays * vatTaxPerc/100;
			              vatableAmount =  correctedVatablePremium-( premiumAmt * polDiffInDays/policyPeriodDays)    ;
			              retainableVatAmt=premiumAmt * polDiffInDays/policyPeriodDays * vatTaxPerc/100;        
			        }
		                            
		            if(endPeriodDays != 0) {
		                proratedVatTaxAmount = proratedPremiumAmt * endDiffInDays/endPeriodDays * vatTaxPerc/100;
		                vatTaxAmount = oldVatTaxAmt + proratedVatTaxAmount;
		                vatableAmount = oldVatablePrm + (proratedPremiumAmt * endDiffInDays/endPeriodDays);
		                
		            }
			    }
			    vatTaxAmount = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(SvcUtils.getRoundingOffBah(vatTaxAmount)), travelInsuranceVO.getCommonVO().getLob().toString()).replace(",", "")));
			    request.setAttribute(com.Constant.CONST_VATTAX, Currency.getUnformattedScaledCurrency(new BigDecimal(vatTaxAmount), travelInsuranceVO.getCommonVO().getLob().toString()));
			    proratedVatTaxAmount  = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(proratedVatTaxAmount), travelInsuranceVO.getCommonVO().getLob().toString()).replace(",", "")));
	            request.setAttribute(com.Constant.CONST_PRORATEDVATTAX, Currency.getUnformattedScaledCurrency(new BigDecimal(SvcUtils.getRoundingOffBah(proratedVatTaxAmount)), travelInsuranceVO.getCommonVO().getLob().toString()));
	        
	            request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getUnformattedScaledCurrency(new BigDecimal(SvcUtils.getRoundingOffBah(Math.abs(proratedVatTaxAmount))), travelInsuranceVO.getCommonVO().getLob().toString()));
	            double premiumAmtRound = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(premiumAmt), travelInsuranceVO.getCommonVO().getLob().toString()).replace(",", "")));
	            request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
	                    .getFormattedCurrency(new BigDecimal(vatTaxAmount + premiumAmtRound),
	                    		travelInsuranceVO.getCommonVO().getLob().toString()));  
	            if(!Utils.isEmpty(travelInsuranceVO.getCommonVO().getIslegacyPolicy()) && travelInsuranceVO.getCommonVO().getIslegacyPolicy()){
	                /*Refund Scenario*/
	                request.setAttribute(com.Constant.CONST_LEGACYREFUND,travelInsuranceVO.getCommonVO().getIslegacyPolicy());
	                retainableVatAmt=0.0;
	                request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
	                        .getFormattedCurrency(new BigDecimal(vatTaxAmount + premiumAmt),
	                        		travelInsuranceVO.getCommonVO().getLob().toString())); 
	            }else{
	                request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
	                        .getFormattedCurrency(new BigDecimal(vatTaxAmount + vatableAmount),
	                        		travelInsuranceVO.getCommonVO().getLob().toString())); 
	            }
			}else 
			{
				request.setAttribute(com.Constant.CONST_VATTAX, Currency.getUnformattedScaledCurrency(new BigDecimal(SvcUtils.getRoundingOffBah(vatTaxAmount)), travelInsuranceVO.getCommonVO().getLob().toString()));
	            request.setAttribute(com.Constant.CONST_PRORATEDVATTAX, Currency.getUnformattedScaledCurrency(new BigDecimal(SvcUtils.getRoundingOffBah(vatTaxAmount)), travelInsuranceVO.getCommonVO().getLob().toString()));    
			}
		}
		if(loggedInLoc == 20 || loggedInLoc == 21) {
			if(travelInsuranceVO.getCommonVO().getAppFlow().equals(Flow.AMEND_POL) || !Utils.isEmpty( travelInsuranceVO.getEndorsmentVO())) {
				request.setAttribute(com.Constant.CONST_VATTAX, Currency.getUnformattedScaledCurrency(new BigDecimal(vatTaxAmount), "SBS"));
	            request.setAttribute(com.Constant.CONST_PRORATEDVATTAX, Currency.getUnformattedScaledCurrency(new BigDecimal(proratedVatTaxAmount), "SBS"));
			//Added by Dileep for endorsement for Travel VAT
	            request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getUnformattedScaledCurrency(new BigDecimal(Math.abs(proratedVatTaxAmount)), "SBS"));
	            request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
						.getFormattedCurrency(new BigDecimal(Math.abs(proratedPremiumAmt)+Math.abs( proratedVatTaxAmount)),
								"SBS"));
	           
	 		if(!Utils.isEmpty(travelInsuranceVO.getCommonVO().getIslegacyPolicy()) && travelInsuranceVO.getCommonVO().getIslegacyPolicy()&& travelInsuranceVO.getPremiumVO().getOldVatAmt()==0){
	                retainableVatAmt=0.0;    
	            }
	            
			} else if(!Utils.isEmpty(request.getAttribute( com.Constant.CONST_CANCELDETAILS ))){
			    logger.info("Enter Cancel Details.........................");
			     if(policyPeriodDays != 0) {
			        
			    	 if(!Utils.isEmpty(travelInsuranceVO.getCommonVO().getIslegacyPolicy()) && travelInsuranceVO.getCommonVO().getIslegacyPolicy() && travelInsuranceVO.getPremiumVO().getOldVatAmt()==0){
				            vatTaxAmount=0.0;
							vatableAmount =  travelInsuranceVO.getPremiumVO().getPremiumAmtActual()-( premiumAmt * polDiffInDays/policyPeriodDays)    ;
				        }else{
				              vatTaxAmount = (travelInsuranceVO.getPremiumVO().getPremiumAmtActual()* vatTaxPerc/100)- premiumAmt * polDiffInDays/policyPeriodDays * vatTaxPerc/100;
				              vatableAmount =  travelInsuranceVO.getPremiumVO().getPremiumAmtActual()-( premiumAmt * polDiffInDays/policyPeriodDays)    ;
				              retainableVatAmt=premiumAmt * polDiffInDays/policyPeriodDays * vatTaxPerc/100;        
				        }
		                            
		            if(endPeriodDays != 0) {
		                proratedVatTaxAmount = proratedPremiumAmt * endDiffInDays/endPeriodDays * vatTaxPerc/100;
		                vatTaxAmount = oldVatTaxAmt + proratedVatTaxAmount;
		                vatableAmount = oldVatablePrm + (proratedPremiumAmt * endDiffInDays/endPeriodDays);
		                
		            }
			    }
			     
			    request.setAttribute(com.Constant.CONST_VATTAX, Currency.getUnformattedScaledCurrency(new BigDecimal(vatTaxAmount), "SBS"));
	            request.setAttribute(com.Constant.CONST_PRORATEDVATTAX, Currency.getUnformattedScaledCurrency(new BigDecimal(proratedVatTaxAmount), "SBS"));
	        
	            request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getUnformattedScaledCurrency(new BigDecimal(Math.abs(proratedVatTaxAmount)), "SBS"));
	            request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
	                    .getFormattedCurrency(new BigDecimal(vatTaxAmount + premiumAmt),
	                            "SBS"));  
	            if(!Utils.isEmpty(travelInsuranceVO.getCommonVO().getIslegacyPolicy()) && travelInsuranceVO.getCommonVO().getIslegacyPolicy()){
	                /*Refund Scenario*/
	                request.setAttribute(com.Constant.CONST_LEGACYREFUND,travelInsuranceVO.getCommonVO().getIslegacyPolicy());
	                retainableVatAmt=0.0;
	                request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
	                        .getFormattedCurrency(new BigDecimal(vatTaxAmount + premiumAmt),
	                                "SBS")); 
	            }else{
	                request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,Currency
	                        .getFormattedCurrency(new BigDecimal(vatTaxAmount + vatableAmount),
	                                "SBS")); 
	            }
			}else 
			{
				request.setAttribute(com.Constant.CONST_VATTAX, Currency.getUnformattedScaledCurrency(new BigDecimal(vatTaxAmount), "SBS"));
	            request.setAttribute(com.Constant.CONST_PRORATEDVATTAX, Currency.getUnformattedScaledCurrency(new BigDecimal(vatTaxAmount), "SBS"));    
			}
		}
		
		
		/*Commented both lines and added - Dileep */
		/*request.setAttribute("diffInDays", polDiffInDays);
		request.setAttribute("policyPeriodDays", policyPeriodDays);
		*/
		request.setAttribute(com.Constant.CONST_POLDAYSUPPER, polDiffInDays);
		request.setAttribute(com.Constant.CONST_POLDAYSLOWER, policyPeriodDays);
		
		if(travelInsuranceVO.getCommonVO().getAppFlow().equals(Flow.AMEND_POL) || 
                  travelInsuranceVO.getCommonVO().getAppFlow().equals(Flow.VIEW_POL)) {
		      if(!Utils.isEmpty(request.getAttribute( com.Constant.CONST_CANCELDETAILS ))){
		          request.setAttribute(com.Constant.CONST_POLDAYSUPPER, polDiffInDays);
		          request.setAttribute(com.Constant.CONST_POLDAYSLOWER, policyPeriodDays);
		      }else{
		      
                  request.setAttribute(com.Constant.CONST_POLDAYSUPPER, endDiffInDays);
                  request.setAttribute(com.Constant.CONST_POLDAYSLOWER, endPeriodDays);
              }   
		    	      
		  }	
		if(loggedInLoc == 50) {
			request.setAttribute(com.Constant.CONST_VATABLEPRM, Currency.getUnformattedScaledCurrency(new BigDecimal(vatableAmount), travelInsuranceVO.getCommonVO().getLob().toString()));
			/*Changed request attribute name - 22 Oct*/
			request.setAttribute("oldvatableTaxPrm", Currency.getUnformattedScaledCurrency(new BigDecimal(oldPremiumAmt), travelInsuranceVO.getCommonVO().getLob().toString()));
			request.setAttribute(com.Constant.CONST_OLDVATTAXAMT, Currency.getUnformattedScaledCurrency(new BigDecimal(oldVatTaxAmt), travelInsuranceVO.getCommonVO().getLob().toString()));
			request.setAttribute(com.Constant.CONST_OLDVATABLEPRM, Currency.getUnformattedScaledCurrency(new BigDecimal(oldVatablePrm), travelInsuranceVO.getCommonVO().getLob().toString()));
		}
		if(loggedInLoc == 20 || loggedInLoc == 21) {
			request.setAttribute(com.Constant.CONST_VATABLEPRM, Currency.getUnformattedScaledCurrency(new BigDecimal(vatableAmount), "SBS"));
			/*Changed request attribute name - 22 Oct*/
			request.setAttribute("oldvatableTaxPrm", Currency.getUnformattedScaledCurrency(new BigDecimal(oldPremiumAmt), "SBS"));
			request.setAttribute(com.Constant.CONST_OLDVATTAXAMT, Currency.getUnformattedScaledCurrency(new BigDecimal(oldVatTaxAmt), "SBS"));
			request.setAttribute(com.Constant.CONST_OLDVATABLEPRM, Currency.getUnformattedScaledCurrency(new BigDecimal(oldVatablePrm), "SBS"));
		}
		
		/*Changed request attribute name - 22 Oct*/
		
			/*Start Travel VAT*/
	
		
        premiumAmt=Math.abs(premiumAmt);
		
        vatAmt = travelInsuranceVO.getPremiumVO().getOldVatAmt(); 
        if(loggedInLoc == 50) {
        	retainableVatAmt = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency
                    .getFormattedCurrency(new BigDecimal(Math.abs(retainableVatAmt)),  /*During cancel to hold vat amount for the earned period*/
                            travelInsuranceVO.getCommonVO().getLob().toString()).replace(",", "")));
        	request.setAttribute("retainableVatAmt",retainableVatAmt);
        }
        if(loggedInLoc == 20 || loggedInLoc == 21) {
        	request.setAttribute("retainableVatAmt",Currency
                    .getFormattedCurrency(new BigDecimal(Math.abs(retainableVatAmt)),  /*During cancel to hold vat amount for the earned period*/
                            "SBS"));
        }
		
		vatAmtAndDaysMap.put(0, (double)polDiffInDays); // not using as of now
		vatAmtAndDaysMap.put(1, (double)policyPeriodDays); // not using as of now
		vatAmtAndDaysMap.put(2, (double)endDiffInDays); // not using as of now
		vatAmtAndDaysMap.put(3, (double)endPeriodDays); // not using as of now
		vatAmtAndDaysMap.put(4, vatTaxAmount);
		vatAmtAndDaysMap.put(5, Math.abs(proratedVatTaxAmount));
        vatAmtAndDaysMap.put(6, vatableAmount);
		if (travelInsuranceVO.getCommonVO().getAppFlow().equals(Flow.AMEND_POL)) {
            if (!Utils.isEmpty(travelInsuranceVO.getEndorsmentVO()) && 
                    travelInsuranceVO.getEndorsmentVO().size() > 0  &&
                    !Utils.isEmpty(travelInsuranceVO.getEndorsmentVO().get(0))) {
               EndorsmentVO endorsmentVO=travelInsuranceVO.getEndorsmentVO().get(0);
               if( endorsmentVO.getPremiumVO().getPremiumAmt() - endorsmentVO.getOldPremiumVO().getPremiumAmt() < 0 ){
                   if (!Utils.isEmpty(travelInsuranceVO.getCommonVO()
                           .getIslegacyPolicy())
                          && travelInsuranceVO.getCommonVO()
                                  .getIslegacyPolicy()
                          && travelInsuranceVO.getPremiumVO().getOldVatAmt() == 0) {
                   vatTaxAmount=0.0;
                   proratedVatTaxAmount=0.0;
                   vatableAmount=0.0;
                   vatAmtAndDaysMap.put(4, vatTaxAmount);
                   vatAmtAndDaysMap.put(5, Math.abs(proratedVatTaxAmount));
                   vatAmtAndDaysMap.put(6, vatableAmount);
                   }
               }
            }
		}
		
		
        vatAmtAndDaysMap.put(7,Math.abs(proratedPremiumAmt));
		logger.info("Exiting AppUtils.calculateVatTaxAndVatableAmount");
		return vatAmtAndDaysMap;		
	}	
	//Shubham Bahrain Travel Vat changes end
	/**
	 * 142244 - WC Vat Tax Implementation
	 */
	@SuppressWarnings("deprecation")
	public static List<Object>  calculateWcVatTaxAmount(WorkmenCompVO  workmenCompVO, Double premiumAfterDiscount, HttpServletRequest request) {
		logger.info("Inside AppUtils.calculateVatTaxAmount");
		
		List<Object> vatList = new ArrayList<Object>();
				
		Double vatTaxAmount = 0.0;
		Double vatTaxPerc = 0.0;
		Double premiumAmt = 0.0;
		Double vatbleAmt = 0.0;
		Double proratevatTaxAmount = 0.0;
		Double proratepremiumAmt = 0.0;
		int diffInDays=0;
		int policyPeriodDays=0;
		int endDiffInDays = 0;
		int endPeriodDays = 0;
		Double OldVatTaxAmt=0.0,OldvatbleAmt=0.0;
		Double OldPremium=0.0;
	
		Date polEffectiveDate = null;
		Date polExpiryDate = null;
		Date vatEffDate = null;
		Date polEndEffectiveDate = null;
		Date polEndExpiryDate = null;
		
		Map<Integer, Double> vatResults = new HashMap<Integer, Double>();
		
		
		
		/* String vatStartDate = Utils.getSingleValueAppConfig("TRAVEL_VAT_START_DATE"); // 01/01/2018 */
		String defaultDateFormat = Utils.getSingleValueAppConfig("DEFAULT_DATE_FORMAT"); // MM/dd/yyyy
		try {
			//SvcUtils.populateVatDt();
			vatEffDate = new SimpleDateFormat(defaultDateFormat).parse(SvcUtils.populateVatDt());
		} catch (ParseException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}  
		
		List<Date> polDateList = new LinkedList<Date>();
		polDateList.add(vatEffDate); // VAT Effective Date for Policy
		
		List<Date> endDateList = new LinkedList<Date>();
		endDateList.add(vatEffDate); // VAT Effective Date for endorsement
		
		Comparator<Date> cmp = new Comparator<Date>() {
		    @Override
		    public int compare(Date date1, Date date2) {
		        return date1.compareTo(date2);
		    }
		};
		
		if(!Utils.isEmpty(workmenCompVO) && !Utils.isEmpty(workmenCompVO.getCommonVO())) {
			if(workmenCompVO.getCommonVO().getLob().equals(LOB.WC)) {
				
				if(!Utils.isEmpty(workmenCompVO) && !Utils.isEmpty(workmenCompVO.getPremiumVO())) {
					
					if(workmenCompVO.getCommonVO().getAppFlow().equals(Flow.AMEND_POL)) {
				
						//polEndExpiryDate = workmenCompVO.getEndorsmentVO().get(0).getEndExpiryDate();
						//Getting the previous vatamount
						polEndExpiryDate = workmenCompVO.getScheme().getExpiryDate();
						polEndEffectiveDate = workmenCompVO.getCommonVO().getEndtEffectiveDate();
						endDateList.add(polEndEffectiveDate);
								
						if(!Utils.isEmpty(workmenCompVO.getEndorsmentVO()) && !Utils.isEmpty(workmenCompVO.getEndorsmentVO().get(0).getOldPremiumVO()) &&
								!Utils.isEmpty(workmenCompVO.getEndorsmentVO().get(0).getOldPremiumVO().getPremiumAmt())) {
							vatResults = DAOUtils.getVatAmountWC(workmenCompVO.getCommonVO().getPolicyId(),workmenCompVO.getEndorsmentVO().get(0).getEndtId());
							OldPremium=workmenCompVO.getEndorsmentVO().get(0).getOldPremiumVO().getPremiumAmt();
							OldVatTaxAmt=vatResults.get(0);
							proratepremiumAmt= premiumAfterDiscount-(OldPremium- OldVatTaxAmt);
							OldvatbleAmt=(Double)vatResults.get(1);
							
						}
																	
						endDiffInDays = (int) ((polEndExpiryDate.getTime() - Collections.max(endDateList, cmp).getTime()) / (1000 * 60 * 60 * 24)) + 1;
						endPeriodDays = (int) ((polEndExpiryDate.getTime() - polEndEffectiveDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
				
					} 
			
					
			// for calculation actual Vat amount for storing to DB
					    polEffectiveDate = workmenCompVO.getCommonVO().getPolEffectiveDate();
					    polDateList.add(polEffectiveDate);
						polExpiryDate = workmenCompVO.getScheme().getExpiryDate();
						
						vatTaxPerc = workmenCompVO.getPremiumVO().getVatTaxPerc();
						
						premiumAmt = premiumAfterDiscount;
						
						logger.info("PolEffDate: "+polEffectiveDate+" PolExpiryDate: "+polExpiryDate+ " polEndEffectiveDate: "+polEndEffectiveDate +"polEndExpiryDate" +polEndExpiryDate
								
								+" VatTaxPerc:_3"+vatTaxPerc+" PremiumAmt:_3"+premiumAmt);
									
					request.setAttribute(com.Constant.CONST_VATTAXPERC, vatTaxPerc);				
				}
			}
		}
		
		logger.info("Max Date out of PolicyEffDate and VatEffDate :"+Collections.max(endDateList, cmp)+"\n");
		
		 diffInDays = (int) ((polExpiryDate.getTime() - Collections.max(polDateList, cmp).getTime()) / (1000 * 60 * 60 * 24)) + 1;
		 policyPeriodDays = (int) ((polExpiryDate.getTime() - polEffectiveDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
		  
		 
		if(policyPeriodDays != 0) {
				vatTaxAmount = premiumAmt * diffInDays/policyPeriodDays * vatTaxPerc/100;
				vatbleAmt= (Double) (premiumAmt * diffInDays/policyPeriodDays);
				if(endPeriodDays != 0) {
					//vatTaxAmount = OldPremium * diffInDays/policyPeriodDays * vatTaxPerc/100;
			        proratevatTaxAmount= proratepremiumAmt * endDiffInDays/endPeriodDays * vatTaxPerc/100;
			        vatTaxAmount =  OldVatTaxAmt + proratevatTaxAmount;
			       
			        //152908 Refund vat amount is grater than the Vat which are collected Previously
			        if(proratevatTaxAmount<0 && (OldVatTaxAmt+proratevatTaxAmount)<0)
			        {
			        	proratevatTaxAmount=OldVatTaxAmt;
			        	vatTaxAmount=0.0;
			        	vatbleAmt=OldvatbleAmt;
			        }
			        else
			        vatbleAmt= OldvatbleAmt + (proratepremiumAmt * endDiffInDays/endPeriodDays);
			    }
			}

		// wc Cancellation Vat Amount & Vatable Premium
		if(!Utils.isEmpty(request.getAttribute( com.Constant.CONST_CANCELDETAILS))) {
			List<Date> canDateList = new LinkedList<Date>();
			canDateList.add(vatEffDate);
			polEffectiveDate = workmenCompVO.getCommonVO().getPolEffectiveDate();
			canDateList.add(polEffectiveDate);
			workmenCompVO.getCommonVO().getPolEffectiveDate();
			PolicyDataVO policyDataVO = (PolicyDataVO)request.getAttribute(com.Constant.CONST_CANCELDETAILS);
			polEndExpiryDate = policyDataVO.getEndEffectiveDate();
			polEndEffectiveDate = policyDataVO.getScheme().getEffDate();
			polDateList.add(polEndEffectiveDate);
			premiumAmt=	policyDataVO.getEndorsmentVO().get(0).getPremiumVO().getPremiumAmt();
			 diffInDays = (int) ((polEndExpiryDate.getTime() - Collections.max(canDateList, cmp).getTime()) / (1000 * 60 * 60 * 24)) + 1;
			 policyPeriodDays = (int) ((polEndExpiryDate.getTime()    - polEndEffectiveDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
			// vatTaxAmount =  premiumAmt * diffInDays/policyPeriodDays * vatTaxPerc/100;
	        vatbleAmt= (premiumAmt * diffInDays/policyPeriodDays);
	        //new change
	    	vatResults = DAOUtils.getVatAmountWC(policyDataVO.getPolicyId(),policyDataVO.getEndorsmentVO().get(0).getEndtId());			
			OldVatTaxAmt=(Double)vatResults.get(0);
	        vatTaxAmount =  premiumAmt * diffInDays/policyPeriodDays * vatTaxPerc/100;
	        proratevatTaxAmount=vatTaxAmount-OldVatTaxAmt;
	        //152908 cancellation Vat amount is grater than the Vat which are collected Previously 
	        if(proratevatTaxAmount>0)
	        {
	        	proratevatTaxAmount=OldVatTaxAmt;
	        	vatTaxAmount=0.0;
	        	vatbleAmt=OldvatbleAmt;
	        }
	        
		}
		//  In the veiw mode, vat amouont coming from DB 
		else if(workmenCompVO.getCommonVO().getAppFlow().equals(Flow.VIEW_POL)) {
			
			if(!Utils.isEmpty(workmenCompVO.getEndorsmentVO()) && !Utils.isEmpty(workmenCompVO.getEndorsmentVO().get(0).getOldPremiumVO()) &&
					!Utils.isEmpty(workmenCompVO.getPremiumVO().getVatTax())) {
				vatResults = DAOUtils.getVatAmountWC(workmenCompVO.getCommonVO().getPolicyId(),workmenCompVO.getEndorsmentVO().get(0).getEndtId());			
				OldVatTaxAmt=(Double)vatResults.get(0);
				/*if((Utils.getSingleValueAppConfig( "POLICY_CANCELLED" ) ).equals( workmenCompVO.getCommonVO().getStatus().toString() )){
					proratevatTaxAmount = workmenCompVO.getPremiumVO().getVatTax();
				}else{*/
					proratevatTaxAmount= (workmenCompVO.getPremiumVO().getVatTax())-OldVatTaxAmt;
				//}
				vatTaxAmount=workmenCompVO.getPremiumVO().getVatTax();
			}
			else if(!Utils.isEmpty(workmenCompVO.getPremiumVO().getVatTax()))
			{
				vatTaxAmount=workmenCompVO.getPremiumVO().getVatTax();
			}
		}
		
		logger.info("VatTax Amount before formatting: "+vatTaxAmount + "Transaction level VatTax Amount" +proratevatTaxAmount);
			
		request.setAttribute("diffInDays", diffInDays);
		request.setAttribute("policyPeriodDays", policyPeriodDays);
		
		request.setAttribute("endDiffInDays", endDiffInDays);
		request.setAttribute("endPeriodDays", endPeriodDays);
		request.setAttribute("OldPremium", OldPremium-OldVatTaxAmt);
			
		// Newly added 
		request.setAttribute(com.Constant.CONST_VATABLEPRM, Currency.getUnformattedScaledCurrency(new BigDecimal(vatbleAmt), "SBS"));
		request.setAttribute(com.Constant.CONST_OLDVATTAXAMT, Currency.getUnformattedScaledCurrency(new BigDecimal(OldVatTaxAmt), "SBS"));
		request.setAttribute(com.Constant.CONST_OLDVATABLEPRM, Currency.getUnformattedScaledCurrency(new BigDecimal(OldvatbleAmt), "SBS"));
				
		if(workmenCompVO.getCommonVO().getAppFlow().equals(Flow.AMEND_POL) || !Utils.isEmpty( workmenCompVO.getEndorsmentVO())|| !Utils.isEmpty(request.getAttribute( com.Constant.CONST_CANCELDETAILS))) {
			request.setAttribute(com.Constant.CONST_VATTAX, Currency.getUnformattedScaledCurrency(new BigDecimal(vatTaxAmount), "SBS"));
			request.setAttribute("ProvatTax", Currency.getUnformattedScaledCurrency(new BigDecimal(Math.abs(proratevatTaxAmount)), "SBS"));
		}
		
		else {
			request.setAttribute(com.Constant.CONST_VATTAX, Currency.getUnformattedScaledCurrency(new BigDecimal(vatTaxAmount), "SBS"));
			request.setAttribute("ProvatTax", Currency.getUnformattedScaledCurrency(new BigDecimal(""+Math.abs(vatTaxAmount)), "SBS"));	
		}
		logger.info("Exiting AppUtils.calculateVatTaxAmount");
		
		vatList.add(vatTaxAmount); //Vat Percent 
		vatList.add(vatbleAmt);// Vat code
		vatList.add(proratevatTaxAmount);//proratevatTaxAmount for latest transaction
		return vatList;
	}   
	//CTS - 15.09.2020 - JLT UAT Change - Enable editing for JLT users even with zero SI for PAR section - Starts
	public static String getPolPreparedByForJLT(Long quoteNo){
		String polPreparedBy = null;
		String sql = QueryConstants.GET_POL_PREPARED_BY;
		List<Object> resultSet = DAOUtils.getSqlResultSingleColumnPas(sql, quoteNo);
		if(!Utils.isEmpty(resultSet)){
		polPreparedBy = resultSet.get(0).toString();
		}
		return polPreparedBy;
	}
	//CTS - 15.09.2020 - JLT UAT Change - Enable editing for JLT users even with zero SI for PAR section - Ends
						
	// CTS - TFS 42721 - Approval Email link redirection - Starts
	public static List<Object> checkIfAPIQuote(Long quoteNo){
		
		String sqlQuery = "Select count(*) from t_trn_webservice_audit where twa_quote_no = " +quoteNo ;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
		Session session = ht.getSessionFactory().openSession();
		ht.flush();
		SQLQuery query = session.createSQLQuery( sqlQuery );	
		return query.list();
		}
						// CTS - TFS 42721 - Approval Email link redirection - Ends
}
