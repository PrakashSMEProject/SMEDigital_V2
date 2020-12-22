package com.rsaame.pas.b2c.cmn.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.constants.CommonConstants;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.controllers.TravelController;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.dao.model.TMasPolicyRating;
import com.rsaame.pas.dao.model.VMasProductConfigPas;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.BusinessChannel;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.StaffDetailsVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.web.DesEncrypter;

/**
 * This will have all the utility methods of presentation layer.
 */
public class AppUtils{	
	
	/** Logger instance */
	private static final Logger logger = Logger.getLogger(AppUtils.class);
	
	private static Long tokenId = null;
	
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
	
	
	public static String removeParameter(String url, String paramToRemove) {
	    String[] parts = url.split("\\?");
	    if (parts.length == 2) {
	        String base = parts[0];
	        String params = parts[1];

	        return base + "?" + Stream.of(params.split("&"))
	                .map(p -> p.split("="))
	                .filter(p -> !p[0].equals(paramToRemove))
	                .map(p -> String.join("=", p))
	                .collect(Collectors.joining("&"));
	    } else {
	        return url;
	    }
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
		if( commaSeperatedValues.equalsIgnoreCase( "false" ) ){
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
	 * Method to get the date difference
	 * 
	 * @param latestDate
	 * @param previousDate
	 * @return
	 */
	public static Long getDateDifference( Date latestDate, Date previousDate ){
		return ( ( ( latestDate.getTime() - previousDate.getTime() ) / AppConstants.DAYDIVIDER ) ) +1;
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
		StringBuffer str = new StringBuffer();
		BufferedReader reader =null;
				try {
			File file = new File(urlToFile.toURI());
			fileReader = new FileReader(file);
		    reader = new BufferedReader(fileReader);
			while ((line = reader.readLine()) != null) {
				//returnValue += line + "\n";
				returnValue =str.append(line).append("\n").toString();
			}
		} catch (URISyntaxException e) {
			logger.debug("Exception ocuured while reading the html file content -_1"+e.getMessage());
		} catch (FileNotFoundException e) {
			logger.debug("Exception ocuured while reading the html file content -_2"+templateName);
		} catch (IOException e) {
			logger.debug("Exception ocuured while reading the html file content -_3"+e.getMessage());
		}finally {		/* Added finally block to close the stream - sonar violation fix on 22-9-2017 */
		    try {
			    if (reader != null){ reader.close(); }
		    } catch (IOException ex) { 
			    logger.debug("Exception ocuured while closing reader - "+ex.getMessage());
		    }
		    try { //updated for SONARFIX - 23-apr-2018
			    if (fileReader != null){ fileReader.close(); }
		    } catch (IOException ex) { 
			    logger.debug("Exception ocuured while closing fileReader - "+ex.getMessage());
		    }
		}
		
		return returnValue;
	}
	/**
	* Method to format the date based on the format required
	* 
	* @param date
	* @return
	*/
	public static String dateFormatter( Date date ){
		DateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy" );
	
		if( !Utils.isEmpty( date ) ){
			return dateFormat.format( date );
		}
		else{
			return "";
		}
		
	}


	/**
	 * @param list
	 * @param parameterListKey
	 * @return
	 * 
	 * The function returns String in JSON format for the given collection and parameterListKey
	 * 
	 * Eg: Collection can be List<TravellerDetails>
	 * parmeterListKey contains what are fields needed to built the JSON
	 * @throws JSONException 
	 */
	public static String createJSONForTravelerDetails(List<TravelerDetailsVO> list, String parameterListKey) throws JSONException{
		
		//Getting the fieldname needed for JSON 
		String[] fieldNames = Utils.getSingleValueAppConfig( parameterListKey ).split( "," );
		
		JSONArray travelerList = new JSONArray();
		
		Collections.sort( list );
		
		for(TravelerDetailsVO travelerDetails : list){
			JSONObject traveler = new JSONObject();
			for(String fieldName : fieldNames){
				if(fieldName.equals( "dateOfBirth" )){
					traveler.put( fieldName, AppUtils.dateFormatter( (Date) travelerDetails.getFieldValue( fieldName ) ) );
				}else {
					traveler.put( fieldName,  travelerDetails.getFieldValue( fieldName ) );
				}
			}
			travelerList.put( traveler );
		}
		
		JSONObject travelerDetails = new JSONObject();
		travelerDetails.put( "TravelerDetails", travelerList );
		
		return travelerDetails.toString().replaceAll( "[\"]", "~" );
	}
/*	//## 140968 :Self Traveler at index 0
	public static String createJSONForTravelerDetailsForRenewals(List<TravelerDetailsVO> list, String parameterListKey) throws JSONException{
		
		//Getting the fieldname needed for JSON 
		String[] fieldNames = Utils.getSingleValueAppConfig( parameterListKey ).split( "," );
		
		JSONArray travelerList = new JSONArray();
		
		Collections.sort( list,TravelerDetailsVO.sortForRelation );
		
		for(TravelerDetailsVO travelerDetails : list){
			JSONObject traveler = new JSONObject();
			for(String fieldName : fieldNames){
				if(fieldName.equals( "dateOfBirth" )){
					traveler.put( fieldName, AppUtils.dateFormatter( (Date) travelerDetails.getFieldValue( fieldName ) ) );
				}else {
					traveler.put( fieldName,  travelerDetails.getFieldValue( fieldName ) );
				}
			}
			travelerList.put( traveler );
		}
		
		JSONObject travelerDetails = new JSONObject();
		travelerDetails.put( "TravelerDetails", travelerList );
		
		return travelerDetails.toString().replaceAll( "[\"]", "~" );
	}
	*/
	
	/**
	 * @param list
	 * @param parameterListKey
	 * @return
	 * 
	 * The function returns String in JSON format for the given collection and parameterListKey
	 * 
	 * Eg: Collection can be List<TravellerDetails>
	 * parmeterListKey contains what are fields needed to built the JSON
	 * @throws JSONException 
	 */
	public static String createJSONForHomeListDetails(List<CoverDetailsVO> list) throws JSONException{
		
		JSONArray coverItemsJSON = new JSONArray();
		
		for (CoverDetailsVO coverDetailsVO : list) {
			JSONObject listItem = new JSONObject();
			
			listItem.put( "coverName",  coverDetailsVO.getCoverName() );
			listItem.put( "sumInsured", coverDetailsVO.getSumInsured().getSumInsured() );
			listItem.put( "rskId",  coverDetailsVO.getRiskCodes().getRskId() );
			listItem.put( "vsd",  coverDetailsVO.getVsd() );
			
			coverItemsJSON.put( listItem );
		}
		
		JSONObject travelerDetails = new JSONObject();
		travelerDetails.put( "CoverItemList", coverItemsJSON );
		
		return travelerDetails.toString().replaceAll( "[\"]", "~" );
	}
	
	/**
	 * Method to set the policy term
	 * 
	 * @param policyDataVO
	 * @return
	 */
	public static String setPolicyTerm(PolicyDataVO policyDataVO){
		String policyTerm = "";
		
		if(!Utils.isEmpty( policyDataVO )){
			if(!Utils.isEmpty( policyDataVO.getPolicyTerm() )){
				policyTerm = String.valueOf( policyDataVO.getPolicyTerm() );
			}
		}
		
		return policyTerm;
	}
	
	/**
	 * Method to check if the quote is referred 
	 * 
	 * @param commonVO
	 * @return
	 */
	public static boolean isReferred(CommonVO commonVO){
		boolean isReffered = false;
		
		if(Utils.isEmpty( commonVO )){
			isReffered = false;
		}
		
		if(!Utils.isEmpty( commonVO ) && Utils.isEmpty( commonVO.getStatus() )){
			isReffered = false;
		}
		
		if(!Utils.isEmpty( commonVO ) && !Utils.isEmpty( commonVO.getStatus() )  && ((commonVO.getStatus().intValue() == SvcConstants.POL_STATUS_REFERRED)||
				(commonVO.getStatus().intValue() == SvcConstants.POL_STATUS_DECLINED)||(commonVO.getStatus().intValue() == SvcConstants.POL_STATUS_REJECT))){
			isReffered = true;
		}
		
		return isReffered;
	}
	
	/**
	 * 
	 * @return
	 */
	public static CommonVO populateCommonVO(CommonVO commonVO) {

		List<Object[]> quotePolEndtNos = null;
		List<Object[]> quoteDocCodeEffectiveDate = null;
		Short classCode = null;
		if( !Utils.isEmpty( commonVO.getQuoteNo() ) && commonVO.getIsQuote() && ( Utils.isEmpty( commonVO.getPolicyNo() ) || Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_REN_QUO_DOC_CODE ) ).equals( commonVO.getDocCode() ) )){
			quotePolEndtNos = DAOUtils.getSqlResultForPas( QueryConstants.FETCH_LATEST_ENDT, commonVO.getQuoteNo() );
			commonVO.setAppFlow(Flow.EDIT_QUO);
		}else{
			if(!Utils.isEmpty( commonVO.getConcatPolicyNo() ) ){							
				quoteDocCodeEffectiveDate = DAOUtils.getSqlResultForPas( QueryConstants.FETCH_EFFECTIVE_DATE_DOC_CODE, commonVO.getConcatPolicyNo() );
				if(!Utils.isEmpty( quoteDocCodeEffectiveDate ) && !Utils.isEmpty( quoteDocCodeEffectiveDate.get( 0 ) ) ){
					Object[] docCodeEffDate = quoteDocCodeEffectiveDate.get( 0 );
					if(!Utils.isEmpty( docCodeEffDate) && Utils.getSingleValueAppConfig( com.Constant.CONST_REN_QUO_DOC_CODE ).equals( String.valueOf( docCodeEffDate[0] ))  && !Utils.isEmpty( docCodeEffDate[1])){
						commonVO.setPolEffectiveDate( (Date)docCodeEffDate[1]);
					}					
				}
			}
			commonVO.setEndtId(0L);
			commonVO.setEndtNo(0L);
			commonVO.setAppFlow(Flow.CREATE_QUO);
			commonVO.setStatus( AppConstants.QUOTE_PENDING );
		}
		if( !Utils.isEmpty( quotePolEndtNos ) ){
			for( Object[] quotePolEndtNo : quotePolEndtNos ){
				commonVO.setPolicyId( ( (BigDecimal) quotePolEndtNo[ 0 ] ).longValue() );
				commonVO.setEndtId( ( (BigDecimal) quotePolEndtNo[ 2 ] ).longValue() );
				commonVO.setEndtNo( ( (BigDecimal) quotePolEndtNo[ 3 ] ).longValue() );

				classCode = ((BigDecimal) quotePolEndtNo[ 1 ]).shortValue();
			}
		}

		if( !Utils.isEmpty( classCode ) ){
			if( classCode.shortValue() == AppConstants.TRAVEL_CLASS_CODE ){
				commonVO.setLob( LOB.TRAVEL );
			}
			else if( classCode.shortValue() == AppConstants.HOME_CLASS_CODE ){
				commonVO.setLob( LOB.HOME );
			}
		}
		commonVO.setLocCode(AppConstants.B2C_DEFAULT_DEPLOYED_LOCATION);
		commonVO.setChannel(BusinessChannel.B2C);
		return commonVO;
	}
	
	/**
	 * Method to set the drop down values
	 * 
	 * @param homeInsuranceVO
	 */
	public static void setBuildingDropDown( HomeInsuranceVO homeInsuranceVO, HttpServletRequest request ){
		if( !Utils.isEmpty( homeInsuranceVO ) ){
			if( !Utils.isEmpty( homeInsuranceVO.getBuildingDetails() ) ){
				if( !Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getArea() ) )
					homeInsuranceVO.getBuildingDetails().setArea(
							String.valueOf( SvcUtils.getLookUpCode( "PAS_AREA", "ALL", "ALL", homeInsuranceVO.getBuildingDetails().getArea() ) ).trim() );
				
				if(!Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getEmirates() ))
					homeInsuranceVO.getBuildingDetails().setEmirates(
							String.valueOf( SvcUtils.getLookUpCode( "CITY", "ALL", "ALL", homeInsuranceVO.getBuildingDetails().getEmirates() ) ) );
				
				if (!Utils.isEmpty(homeInsuranceVO.getBuildingDetails()) && !Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getMortgageeName())) {
					String mortgage[] = homeInsuranceVO.getBuildingDetails().getMortgageeName().split("#");
					if(mortgage.length == 1){
						homeInsuranceVO.getBuildingDetails().setMortgageeName(
								String.valueOf( SvcUtils.getLookUpCode( "PAS_MORTGAGEE_NAME", "ALL", "ALL", mortgage[0] ) ) );
					}
					else{
						homeInsuranceVO.getBuildingDetails().setMortgageeName(
								String.valueOf( SvcUtils.getLookUpCode( "PAS_MORTGAGEE_NAME", "ALL", "ALL", "Others" ) ) );
						request.setAttribute("mortgageOthers", mortgage[1]);
					}
				}
			}
		}
	}


	/**
	 * Method to check for the valid email
	 * @param response
	 * @param request
	 * @return
	 */
	public static boolean inValidEmailId( PolicyDataVO response, PolicyDataVO request ){
		boolean inValidRecord = false;
		if(!Utils.isEmpty( response ) && !Utils.isEmpty( request )){
			if(!Utils.isEmpty( response.getGeneralInfo() ) && !Utils.isEmpty( request.getGeneralInfo() )){
				if(!Utils.isEmpty( response.getGeneralInfo().getInsured() ) && !Utils.isEmpty(  request.getGeneralInfo().getInsured()  )){
					if(!Utils.isEmpty( response.getGeneralInfo().getInsured().getEmailId() ) && !Utils.isEmpty( request.getGeneralInfo().getInsured().getEmailId() )){
						String enteredEmailId =  response.getGeneralInfo().getInsured().getEmailId();
						String storedEmailId =   request.getGeneralInfo().getInsured().getEmailId();
						
						if(!enteredEmailId.equalsIgnoreCase( storedEmailId )){
							inValidRecord = true;
							
						}
					}
				}
			}
		}
		return inValidRecord;
	}
	
	/**
	 * @param response
	 * @return
	 */
	public static boolean isValidDistributionChannel(PolicyDataVO response, GeneralInfoVO generalInfo ) {
		
		boolean isValidRecord = false;
		Integer savedDistChannel = null, distChannel = null, savedBrCode = null, brCode = null;
		String partnerName = null;
		Long savedAgentCode = null, agentCode = null;
		
			if(!Utils.isEmpty( response ) && !Utils.isEmpty( response.getGeneralInfo() ) &&
					!Utils.isEmpty(response.getGeneralInfo().getSourceOfBus()) &&
					!Utils.isEmpty(response.getGeneralInfo().getSourceOfBus())){
				savedDistChannel = response.getGeneralInfo().getSourceOfBus().getDistributionChannel();
				savedBrCode = response.getGeneralInfo().getSourceOfBus().getBrokerName();	
				savedAgentCode = response.getGeneralInfo().getSourceOfBus().getDirectSubAgent();
			}
			if(!Utils.isEmpty( generalInfo ) &&
					!Utils.isEmpty(generalInfo.getSourceOfBus())){
				distChannel = generalInfo.getSourceOfBus().getDistributionChannel();
				brCode = generalInfo.getSourceOfBus().getBrokerName();	
				agentCode = generalInfo.getSourceOfBus().getDirectSubAgent();
				partnerName = generalInfo.getSourceOfBus().getPartnerName();
			}
			if(Utils.isEmpty( partnerName ) && savedDistChannel.equals(AppConstants.DIST_CHANNEL_DIRECT_WEB)){
				isValidRecord = true;
			}
			else if(!Utils.isEmpty( partnerName ) && savedDistChannel.equals(distChannel)){
				if(savedDistChannel.equals(AppConstants.DIST_CHANNEL_DIRECT_WEB) 
						|| savedDistChannel.equals(AppConstants.DIST_CHANNEL_AFFINITY_DIRECT_AGENT)){
					isValidRecord = true;
				}
				else if(savedDistChannel.equals(AppConstants.DIST_CHANNEL_BROKER) && savedBrCode.equals(brCode)){
					isValidRecord = true;
				}
				else if ((savedDistChannel.equals(AppConstants.DIST_CHANNEL_AGENT)
					|| savedDistChannel.equals(AppConstants.DIST_CHANNEL_AFFINITY_AGENT)) && savedAgentCode.equals(agentCode)) {
				  isValidRecord = true;
				}
			}
		/*if(!Utils.isEmpty( response )){
			if(!Utils.isEmpty( response.getGeneralInfo() ) &&
					!Utils.isEmpty(response.getGeneralInfo().getSourceOfBus()) &&
					!Utils.isEmpty(response.getGeneralInfo().getSourceOfBus().getDistributionChannel())&&
					response.getGeneralInfo().getSourceOfBus().getDistributionChannel().equals(SvcConstants.DIST_CHANNEL_DIRECT_WEB )){
				isValidRecord = true;
			}
		}*/
		return isValidRecord;
	}
	
	public static String isChecked(){
		return "";
	}

	/**
	 * Method to set the quote valid date
	 * 
	 * @param travelInsuranceVO
	 * @param request
	 */
	public static void setQuoteValidDate( PolicyDataVO policyDataVO, HttpServletRequest request ){
		if(!Utils.isEmpty( policyDataVO )){
			SchemeVO schemeVO = policyDataVO.getScheme();
			boolean isRenewedQuote = Short.valueOf(
					Utils.getSingleValueAppConfig(com.Constant.CONST_REN_QUO_DOC_CODE)).equals(
					policyDataVO.getCommonVO().getDocCode());
			
			if(!Utils.isEmpty( schemeVO )){
				Date effDate;
				// Quote Valid date should be calculated based on Quote Issued Date
				if (!Utils.isEmpty(policyDataVO.getAuthenticationInfoVO()) && !isRenewedQuote) {
					effDate = policyDataVO.getAuthenticationInfoVO().getAccountingDate();
				} else {
					// For Renewed Quote Valid date should be calculated based on Policy Effective Date
					effDate = schemeVO.getEffDate();
					if(Utils.isEmpty( effDate ) && !Utils.isEmpty(policyDataVO.getCommonVO())){
						effDate = policyDataVO.getCommonVO().getPolEffectiveDate();
					}
				}

				if(!Utils.isEmpty( effDate )){
					Calendar effCalendar = Calendar.getInstance();
					effCalendar.setTime( effDate );
					
					if(isRenewedQuote)
					{
						effCalendar.add( Calendar.DATE, AppConstants.REN_QUOTE_VALID_DAYS );
					}
					else
					{
						//Quote should to be valid exactly for 30 days from the date of Issue.
						effCalendar.add( Calendar.DATE, AppConstants.QUOTE_VALID_DAYS - 1 );
					}
					
					request.setAttribute( "validQuoteDate", dateFormatter( effCalendar.getTime() ) ); 
				}
				
			}
		}
		
	}
	
	/**
	 * This is a general method to construct the click here tag for email
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
			else{
				clickHereURL = new StringBuilder(applicationPath)
						.append(AppConstants.B2C_FETCH_QUOTE_HOME_METHOD)
						.append(AppConstants.QUESTION_MARK_SYMBOL)
						.append(AppConstants.QUOTE_NUM_REQ_PARAM)
						.append(AppConstants.EQUALS_SYMBOL)
						.append(encryptedQuoteNumber).append(AppConstants.AMPERSAND_SYMBOL)
						.append(AppConstants.EMAIL_REQ_PARAM).append(AppConstants.EQUALS_SYMBOL)
						.append(encryptedEmailId).toString();
			}
		}
		clickHereTag = AppConstants.B2C_EMAIL_CLICK_HERE_TAG;
		clickHereTag = clickHereTag.replace(AppConstants.HREF_URL_IDENTIFIER, clickHereURL);
		logger.debug("Click here created "+clickHereTag);
		return clickHereTag;
	}
	
	/**
	 * This is a general method to construct the policy wording tag for email
	 * 
	 * @param contextPath
	 * @return
	 * @throws ParseException 
	 */
	public static String constructPolicyWordingURL(String applicationPath, LOB lob,Integer schemeCode,String partnerName,PolicyDataVO policyDataVO) throws ParseException {
		String polWordingTag = null;
		String polWordingURL = null;
		String LOB = "lob";
		String policyScheme = "policyScheme";
		String ownerShiplabel="ownerShipStatus";
		String[] urlArray = applicationPath.split("/");
		int len = urlArray[urlArray.length-1].length();
		applicationPath = applicationPath.substring(0, applicationPath.length() - len);
	
		
		TravelController travelController = new TravelController();
		if(lob.name().equalsIgnoreCase("travel")){
			TravelInsuranceVO travelInsuranceVO = new TravelInsuranceVO();
			travelInsuranceVO = (TravelInsuranceVO)policyDataVO;
		if(Utils.isEmpty( partnerName ))
		{
			if(travelController.checkPolPreparedDt(travelInsuranceVO)){
		polWordingURL = new StringBuilder(applicationPath)
						.append(AppConstants.B2C_FETCH_POL_WORDING_METHOD)
						.append(AppConstants.QUESTION_MARK_SYMBOL)
						.append(LOB)
						.append(AppConstants.EQUALS_SYMBOL)
						.append(lob.toString())
						.append( AppConstants.AMPERSAND_SYMBOL )
						.append( policyScheme )
						.append( AppConstants.EQUALS_SYMBOL )
						.append( schemeCode.toString() )			
						.toString();
			}else{
				polWordingURL = new StringBuilder(applicationPath)
				.append(AppConstants.B2C_FETCH_POL_WORDING_METHOD_OLD)
				.append(AppConstants.QUESTION_MARK_SYMBOL)
				.append(LOB)
				.append(AppConstants.EQUALS_SYMBOL)
				.append(lob.toString())
				.append( AppConstants.AMPERSAND_SYMBOL )
				.append( policyScheme )
				.append( AppConstants.EQUALS_SYMBOL )
				.append( schemeCode.toString() )			
				.toString();
			}
		}
		else
		{
			if(travelController.checkPolPreparedDt(travelInsuranceVO)){
			polWordingURL = new StringBuilder(applicationPath)
			.append(partnerName)
			.append("/")
			.append(AppConstants.B2C_FETCH_POL_WORDING_METHOD)
			.append(AppConstants.QUESTION_MARK_SYMBOL)
			.append(LOB)
			.append(AppConstants.EQUALS_SYMBOL)
			.append(lob.toString())
			.append( AppConstants.AMPERSAND_SYMBOL )
			.append( policyScheme )
			.append( AppConstants.EQUALS_SYMBOL )
			.append( schemeCode.toString() )			
			.toString();
			}else{
				polWordingURL = new StringBuilder(applicationPath)
				.append(partnerName)
				.append("/")
				.append(AppConstants.B2C_FETCH_POL_WORDING_METHOD_OLD)
				.append(AppConstants.QUESTION_MARK_SYMBOL)
				.append(LOB)
				.append(AppConstants.EQUALS_SYMBOL)
				.append(lob.toString())
				.append( AppConstants.AMPERSAND_SYMBOL )
				.append( policyScheme )
				.append( AppConstants.EQUALS_SYMBOL )
				.append( schemeCode.toString() )			
				.toString();
			}
		}
		}
		//For HOME etc
		else {
			
			if(Utils.isEmpty( partnerName ))
			{
				//HomeInsuranceVO homeinsuranceVO= (HomeInsuranceVO)policyDataVO;
				// changes-HomeRevamp#7.1
				String ownerShipStatus=null;
			String policyNo = policyDataVO.getCommonVO().getPolicyNo().toString();
			//CTS - 19.09.2020 - TFS 42504 - Policy Wording Link not working in Email - Starts
			applicationPath = Utils.getSingleValueAppConfig("POL_WORDING_LINK_LATEST");
			String fileName = Utils.getSingleValueAppConfig("POL_WORDING_FILE_NAME");
			polWordingURL = new StringBuilder(applicationPath).append(fileName).toString();
/*			ownerShipStatus=DAOUtils.fetchOwnershipInfo(policyNo);
			
			polWordingURL = new StringBuilder(applicationPath)
							.append(AppConstants.B2C_FETCH_POL_WORDING_METHOD)
							.append(AppConstants.QUESTION_MARK_SYMBOL)
							.append(LOB)
							.append(AppConstants.EQUALS_SYMBOL)
							.append(lob.toString())
							.append( AppConstants.AMPERSAND_SYMBOL )
							.append( policyScheme )
							.append( AppConstants.EQUALS_SYMBOL )
							.append( schemeCode.toString() )			
							.append( AppConstants.AMPERSAND_SYMBOL )
							.append(ownerShiplabel)
							.append( AppConstants.EQUALS_SYMBOL )
							.append(ownerShipStatus)
							.toString();*/
			// changes-HomeRevamp#7.1
			//CTS - 19.09.2020 - TFS 42504 - Policy Wording Link not working in Email - Ends

			}
			else
			{
				polWordingURL = new StringBuilder(applicationPath)
				.append(partnerName)
				.append("/")
				.append(AppConstants.B2C_FETCH_POL_WORDING_METHOD)
				.append(AppConstants.QUESTION_MARK_SYMBOL)
				.append(LOB)
				.append(AppConstants.EQUALS_SYMBOL)
				.append(lob.toString())
				.append( AppConstants.AMPERSAND_SYMBOL )
				.append( policyScheme )
				.append( AppConstants.EQUALS_SYMBOL )
				.append( schemeCode.toString() )			
				.toString();
			}
			
			
		}
			polWordingTag = AppConstants.B2C_POL_WORDING_TAG;
			polWordingTag = polWordingTag.replace(AppConstants.HREF_URL_IDENTIFIER, polWordingURL);
		logger.debug("pol wording created "+polWordingTag);
		return polWordingTag;
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
		} else if (!Utils.isEmpty(input))
		{
			if (input.contains("%2B")) {
				input = input.replace("%2B", "+");
			}
			formattedData = encrypter.decrypt(input);
		}
		return formattedData;
	}
	
	/**
	 * Method to determine if it is a quote or policy
	 * 
	 * @param commonVO
	 */
	public static boolean isQuote(CommonVO commonVO){
		boolean isQuote = false;
		
		if(!Utils.isEmpty( commonVO )){
			if(commonVO.getIsQuote() && Utils.isEmpty( commonVO.getPolicyNo() )){
				isQuote = true;
			}
		
			List<Object[]> resultSet = DAOUtils.getSqlResultForPas( QueryConstants.GET_QUOTE_STATUS, commonVO.getEndtId(), commonVO.getPolicyId() );
			
			if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
				int documentCode = Integer.valueOf( String.valueOf( resultSet.get( 0 )[0] ) );
				int quoteStatus =  Integer.valueOf( String.valueOf( resultSet.get( 0 )[1] ) );
				
				if(documentCode == 6 && (quoteStatus == AppConstants.QUOTE_ACTIVE ||  quoteStatus == AppConstants.QUOTE_ACCEPT 
						|| quoteStatus == AppConstants.QUOTE_REFERRED ) ){
					isQuote = true;
				}
			}
			
		}
		
		return isQuote;
	}

	
	/**
	 * Method to set scale for currency based on LOB. 
	 * @param lob
	 */
	public static void setScaleForLOB( LOB lob ){
	
		if( !Utils.isEmpty( lob ) ){
			if("TRAVEL".equals(lob.toString())){
				Currency.setScale( Currency.getPolicyTypeScaleMap().get( Short.valueOf( Utils.getSingleValueAppConfig(  "TRAVEL_SHORT_TERM_POLICY_TYPE" ) ) ) );
			}else{
			Currency.setScale( Currency.getPolicyTypeScaleMap().get( Short.valueOf( Utils.getSingleValueAppConfig( lob.toString() + "_POLICY_TYPE" ) ) ) );
			}
		}else{
			throw new BusinessException( "", null, "LOB cannot be empty." );
		}
		
	}

	/**
	 * to avoid showing discount% in case of referrals.
	 * @param premiumVO
	 * @param str
	 * @return
	 */
	public static boolean isDiscToBeShown( PremiumVO premiumVO, String str , GeneralInfoVO genInfoVo){
		
		boolean isDiscToBeShown = true;
		/*
		 * 
		 * NullPointer Fix : D2C
		 */
		if(null==genInfoVo || null==genInfoVo.getSourceOfBus() || ((null==genInfoVo.getSourceOfBus().getDefaultOnlineDiscount()||Utils.isEmpty( genInfoVo.getSourceOfBus().getDefaultOnlineDiscount() )) && (null==genInfoVo.getSourceOfBus().getPartnerName()||Utils.isEmpty( genInfoVo.getSourceOfBus().getPartnerName()))))
		{
			
			if( !Utils.isEmpty( premiumVO ) && !Utils.isEmpty( premiumVO.getDiscOrLoadPerc() ) && !Utils.isEmpty( Utils.getSingleValueAppConfig( str ) ) 
					&& !Double.valueOf( Utils.getSingleValueAppConfig( str ) ).equals( premiumVO.getDiscOrLoadPerc() ) ){
				isDiscToBeShown = false;
			}			
		}
		else
		{
			if(!Utils.isEmpty(genInfoVo.getSourceOfBus().getDefaultOnlineDiscount()) &&
					genInfoVo.getSourceOfBus().getDefaultOnlineDiscount()==0.0)
			{
				isDiscToBeShown = false;
			}
			else if( !Utils.isEmpty( premiumVO ) &&  ((premiumVO.getDiscOrLoadPerc()<=0.0 ||premiumVO.getDiscOrLoadPerc()>=0.0))
					&&  !premiumVO.getDiscOrLoadPerc().equals(  genInfoVo.getSourceOfBus().getDefaultOnlineDiscount() ) ){
				isDiscToBeShown = false;
			}
			
		}
		return isDiscToBeShown;
	}

	/**
	 * Method to convert the name to capitalize
	 * 
	 * @param travelerDetailsList
	 */
	public static void updateTravellersName( TravelInsuranceVO travelInsuranceVO ){
		if( !Utils.isEmpty( travelInsuranceVO ) && !Utils.isEmpty( travelInsuranceVO.getTravelDetailsVO() )
				&& !Utils.isEmpty( travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList() ) ){
			
			List<TravelerDetailsVO> travelerDetailsList =  travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList();
			
			for( TravelerDetailsVO travelerDetailsVO : travelerDetailsList ){
				travelerDetailsVO.setName( WordUtils.capitalize( travelerDetailsVO.getName() ) );
			}
		}
		
	}
	
	/**
	 * Method to convert name to capitalize
	 * 
	 * @param policyDataVO
	 */
	public static void updateInsuredName(PolicyDataVO policyDataVO){
		if(!Utils.isEmpty( policyDataVO ) && !Utils.isEmpty( policyDataVO.getGeneralInfo() ) && !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured() ) ){
			InsuredVO insuredVO =  policyDataVO.getGeneralInfo().getInsured();
			
			if(!Utils.isEmpty( insuredVO.getFirstName() )){
				insuredVO.setFirstName( WordUtils.capitalize( insuredVO.getFirstName() ) );
			}
			
			if(!Utils.isEmpty( insuredVO.getLastName() )){
				insuredVO.setLastName( WordUtils.capitalize( insuredVO.getLastName() ) );
			}
			
			if(!Utils.isEmpty( insuredVO.getName() )) {
				insuredVO.setName( WordUtils.capitalize( insuredVO.getName() ) );
			}
		}
	}
	
	public static String createJSONForStaffListDetails(List<StaffDetailsVO> list) throws JSONException{
			
		JSONArray coverItemsJSON = new JSONArray();
		
		for (StaffDetailsVO staffDetailsVO : list) {
			JSONObject listItem = new JSONObject();
			
			listItem.put( "coverName",  staffDetailsVO.getEmpName() );
			listItem.put( "sumInsured", dateFormatter( staffDetailsVO.getEmpDob() ) );
			listItem.put( "rskId",  staffDetailsVO.getEmpId() );
			listItem.put( "vsd",  staffDetailsVO.getEmpVsd() );
			
			coverItemsJSON.put( listItem );
		}
		
		JSONObject travelerDetails = new JSONObject();
		travelerDetails.put( "CoverItemList", coverItemsJSON );
		
		return travelerDetails.toString().replaceAll( "[\"]", "~" );
	}

	/**
	 * Method to check if the entered captcha is valid
	 * 
	 * @param request
	 * @return
	 */
	/*public static boolean checkCaptha( HttpServletRequest request ){
		boolean captchaPass = false;
		
		String remoteAddr = request.getRemoteAddr();
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("6LfEjvESAAAAABoh81QPB9nBDy33X9ylg8U8GZuy");

        String challenge = request.getParameter("recaptcha_challenge_field");
        String uresponse = request.getParameter("recaptcha_response_field");
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);
		
        if(reCaptchaResponse.isValid()){
        	captchaPass = true;
        }
        return captchaPass;
	}*/
	
	/**
	 * Method to get the formatted date by passing string
	 * 
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static Date dateFormatter( String date ) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy" );

		if( !Utils.isEmpty( date ) ){
			return dateFormat.parse( date );
		}
		else{
			return null;
		}
	}

	/**
	 * Method to get the email from the date of birth 
	 * 
	 * @param quoteNumber
	 * @param dateOfBirth
	 * @return
	 */
	/*public static String getEmailId( String quoteNumber, Date dateOfBirth ){
		String emailId = "";
		if(!Utils.isEmpty( quoteNumber ) && !Utils.isEmpty( dateOfBirth )){
			emailId = SvcUtils.getEmailId(quoteNumber,dateOfBirth);
		}
		return null;
	}*/
	
	/**
	* Method to format the date based on the format required
	* 
	* @param date
	* @return
	*/
	public static String datesFormatter( Date date ){
		DateFormat dateFormat = new SimpleDateFormat( "dd-MMM-yyyy" );
	
		if( !Utils.isEmpty( date ) ){
			return dateFormat.format( date );
		}
		else{
			return "";
		}
		
	}
	
	/*
	 * For renewal quotes, effective date is next day of policy expiry. This method returns system date as the effective date if policy is expired.
	 * Added for CR - Renewal quotes to expire after 120 days.
	 * */
	public static Date getEffectiveDateForRenewal( Date effDate ){
		
		Date sysDate = getSystemDateWithoutTime();
		
		Calendar renQuoteValidDate = Calendar.getInstance();
		renQuoteValidDate.setTime( effDate );
		renQuoteValidDate.add( Calendar.DATE, AppConstants.REN_QUOTE_VALID_DAYS );
		
		if( sysDate.after( effDate ) && sysDate.compareTo(renQuoteValidDate.getTime())<=0){
			return sysDate;
		}
		return effDate;
		
	}


	/**
	 * @return
	 */
	private static Date getSystemDateWithoutTime(){
		Calendar sysDate = Calendar.getInstance();
		sysDate.set(Calendar.HOUR_OF_DAY, 0);  
		sysDate.set(Calendar.MINUTE, 0);  
		sysDate.set(Calendar.SECOND, 0);  
		sysDate.set(Calendar.MILLISECOND, 0);
		return sysDate.getTime();
	}
	
	/*
	 * This method returns expiry date of renewal based on effective date considering whether the year is leap year.
	 * Added for CR - Renewal quotes to expire after 120 days.
	 * */
	public static Date getExpiryDateForRenewal( Date effDate, Date expiryDate ){
		
		Date effectiveDate = getEffectiveDateForRenewal( effDate );
				
		int totalDays = isLeapYear( effDate ) ? AppConstants.NO_OF_DAYS_LEAP_YEAR : AppConstants.NO_OF_DAYS_YEAR;
		
		Calendar expDate = Calendar.getInstance();
		expDate.setTime( effectiveDate );
		expDate.add( Calendar.DATE, totalDays - 1);
		
		return expDate.getTime();
		
	}
	
	private static boolean isLeapYear( Date policyYear ){
		Calendar cal = Calendar.getInstance();
		cal.setTime( policyYear );
		GregorianCalendar greCal = new GregorianCalendar();
		return greCal.isLeapYear( cal.get( cal.YEAR ) ); //use calender.get() function to get year in YYYY format. 
	}
	
	//CTS 14.09.2020 - TFS#43972 -  Mismatch in the policy expiry date on test and live - start
	public static boolean isLeapYearMonth( Date policyYear ){
		Calendar cal = Calendar.getInstance();
		cal.setTime( policyYear );
		GregorianCalendar greCal = new GregorianCalendar();
		if (greCal.isLeapYear( cal.get( cal.YEAR ) ))
			{
				if (cal.get( cal.MONTH )<2)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		else
			{
			return false;
			} //use calender.get() function to get year in YYYY format. 
	}
	//CTS 14.09.2020 - TFS#43972 -  Mismatch in the policy expiry date on test and live - end
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
	
	/*Dileep VAT*/
	
	public static String getFormattedStringWithDecimals( String number){
		
		double amount = Double.parseDouble(number);
		DecimalFormat f = new DecimalFormat("##.00");		
		return f.format(amount);
	}
	
	public static List<BigDecimal> getQuoteFromPolicy(BaseVO baseVO){
		return DAOUtils.getQuoteFromPolicy(baseVO);
	}
	
		//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - Start
	public static List<BigDecimal> getQuoteFromPol(BaseVO baseVO){
		return DAOUtils.getQuoteFromPol(baseVO);
	}
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - End
	
	
	public static List<TMasPolicyRating>  getHomeOptionalCovers(BaseVO baseVO, Integer classCode, Integer policyType, Integer schemeCode, Integer tariffCode){
		return DAOUtils.getHomeOptionalCovers(baseVO, classCode, policyType, schemeCode, tariffCode);
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
	

	public static Double getFormattedDoubleWithTwoDecimals(Double fidelityPremium) {
		DecimalFormat f = new DecimalFormat("##.00");
		double amount = Double.parseDouble(f.format(fidelityPremium));
		return amount;
	}
	
	// CTS 20.07.2020 - CR#11645 Retrieve Policy Details  SAT Issue-start
	public static CommonVO populateCommonVOForPolicy(CommonVO commonVO) {
		// TODO Auto-generated method stub
		List<Object[]> quotePolEndtNos = null;
		List<Object[]> quoteDocCodeEffectiveDate = null;
		Short classCode = null;
		if( !Utils.isEmpty( commonVO.getQuoteNo() ) && !commonVO.getIsQuote() &&  !Utils.isEmpty( commonVO.getPolicyNo() ) ){
			quotePolEndtNos = DAOUtils.getSqlResultForPas( QueryConstants.FETCH_LATEST_ENDT_POL, commonVO.getPolicyNo(),commonVO.getQuoteNo() );
			commonVO.setAppFlow(Flow.VIEW_POL);
		} else{
			commonVO.setEndtId(0L);
			commonVO.setEndtNo(0L);
			commonVO.setAppFlow(null);
			commonVO.setStatus(null);
		}
		if( !Utils.isEmpty( quotePolEndtNos ) ){
			for( Object[] quotePolEndtNo : quotePolEndtNos ){
				commonVO.setPolicyId( ((BigDecimal) quotePolEndtNo[ 0 ] ).longValue() );
				commonVO.setEndtId( ( (BigDecimal) quotePolEndtNo[ 2 ] ).longValue() );
				commonVO.setEndtNo( ( (BigDecimal) quotePolEndtNo[ 3 ] ).longValue() );
                commonVO.setStatus(((BigDecimal)quotePolEndtNo[ 4 ]).intValue());
				classCode = ((BigDecimal) quotePolEndtNo[ 1 ]).shortValue();
			}
		}

		if( !Utils.isEmpty( classCode ) ){
			if( classCode.shortValue() == AppConstants.TRAVEL_CLASS_CODE ){
				commonVO.setLob( LOB.TRAVEL );
			}
			else if( classCode.shortValue() == AppConstants.HOME_CLASS_CODE ){
				commonVO.setLob( LOB.HOME );
			}
		}
		commonVO.setLocCode(AppConstants.B2C_DEFAULT_DEPLOYED_LOCATION);
		commonVO.setChannel(BusinessChannel.B2C);
		return commonVO;

	}
	// CTS 20.07.2020 - CR#11645 Retrieve Policy Details  SAT Issue-end

}

