package com.rsaame.pas.reports.ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.PolicyDetailsHolder;
import com.rsaame.pas.vo.app.PolicyDetailsVO;
import com.rsaame.pas.vo.app.ReportTemplateSet;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * 
 * @author m1014957
 * 
 */

public class PrintPolicyDocRH implements IRequestHandler{

	private static final Logger logger = Logger.getLogger( PrintPolicyDocRH.class );
	private static final List<String> NON_VERSION_STATUS = Arrays.asList( Utils.getMultiValueAppConfig( SvcConstants.NON_VERSION_STATUS ) );

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){

		Response responseObj = new Response();

		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		PolicyVO policyVO = policyContext.getPolicyDetails();
		CommonVO commonVO = policyContext.getCommonDetails();
		
		String policyLinkingId = "";
		String polSecId = "";
		Boolean isDistributionBr = false;
		String reportTemplatesType = null;

		String identifier = request.getParameter( "opType" );
		boolean endFlag = false;
		boolean renFlag = false;
		boolean isRecieptToBeShown = false;
		/* Free zone certificate link not to be displayed when None is selected as free zone */
		boolean isFreeZoneToBeShown = false;
		
		//added for abudhabi/baharain
		request.setAttribute("locCode", Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));

		if( !Utils.isEmpty( policyVO ) ){

			reportTemplatesType = ReportTemplateSet._SBS.toString();

			String parSecId = Utils.getSingleValueAppConfig( "SECTION_ID_PAR", "1" );
			String plSecId = Utils.getSingleValueAppConfig( "SECTION_ID_PL", "6" );
			String wcSecId = Utils.getSingleValueAppConfig( "SECTION_ID_WC", "7" );

			boolean parFlag = false;
			boolean plFlag = false;
			boolean wcFlag = false;

			String parPolId = null;
			String plPolId = null;
			String wcPolId = null;

			PolicyDetailsVO policyDtlVO = new PolicyDetailsVO();
			policyDtlVO.setPolicyNo( policyVO.getPolicyNo() );
			//Needed to differentiate between renewal policy and original policy
			String expDate = SvcUtils.getYearFromDate( policyVO.getPolExpiryDate() ) + "-" + SvcUtils.getMonthFromDate( policyVO.getPolExpiryDate() ) + "-"
					+ SvcUtils.getDayFromDate( policyVO.getPolExpiryDate() );
			policyDtlVO.setPolExpiryDate( expDate );
			policyDtlVO.setPolConcPolicyNo( policyVO.getConcatPolicyNo() );
			logger.info( "Policy No within PrintPolicyDocRH for processing -->" + policyVO.getPolicyNo() );
			//		policyDtlVO.setEndtId((policyVO.getEndtId()).toString());
			/* if in quote flow just after converting to policy then 0 should be sent as endt id for debit note search else latest endorsement id for the policy*/
			policyDtlVO.setEndtId( ( policyVO.getIsQuote() ? AppConstants.INTIAL_POL_ENDT : SvcUtils.getLatestEndtId( policyVO ) ).toString() );
			logger.info( "Endt Id within PrintPolicyDocRH for processing -->" + SvcUtils.getLatestEndtId( policyVO ) );
			BaseVO baseVO = TaskExecutor.executeTasks( identifier, policyDtlVO );

			PolicyDetailsHolder policyDtls = (PolicyDetailsHolder) baseVO;
			List<PolicyDetailsVO> policyList = policyDtls.getPolicyDtlList();

			Integer maxEndId = 0;
			//format = new SimpleDateFormat("dd-MMM-yyyy");
			String sdate = "";

			for( PolicyDetailsVO policyDetailsVO : policyList ){

				policyLinkingId = policyDetailsVO.getPolicyLinkingId();
				polSecId = policyDetailsVO.getSectionId();
				sdate = policyDetailsVO.getStartDate();
				if( Integer.valueOf( policyDetailsVO.getEndtId() ) > maxEndId ){
					maxEndId = Integer.valueOf( policyDetailsVO.getEndtId() );
				}
				if( Integer.valueOf( policyDetailsVO.getPolDocumentId() ) == 3 ){
					endFlag = true;
				}
				else if( Integer.valueOf( policyDetailsVO.getPolDocumentId() ) == 2 ){
					renFlag = true;
				}
				if( parSecId.equals( polSecId ) ){
					parFlag = true;
					parPolId = policyDetailsVO.getPolicyId();
				}
				else if( plSecId.equals( polSecId ) ){
					plFlag = true;
					plPolId = policyDetailsVO.getPolicyId();
				}
				else if( wcSecId.equals( polSecId ) ){
					wcFlag = true;
					wcPolId = policyDetailsVO.getPolicyId();
				}

				if( ( !Utils.isEmpty( policyDetailsVO.getPolBrCode() ) && !policyDetailsVO.getPolBrCode().equalsIgnoreCase( "null" ) )
						|| ( !Utils.isEmpty( policyDetailsVO.getPolAgentId() ) && !policyDetailsVO.getPolAgentId().equalsIgnoreCase( "null" ) ) ){
					isDistributionBr = true;
				}

				if( (!Utils.isEmpty( policyDetailsVO.getPolBrCode() ) && policyDetailsVO.getPolBrCode().equals( Utils.getSingleValueAppConfig( "FGB_BROKER_CODE" ) ))
						|| (!Utils.isEmpty( policyVO.getScheme()  )  && policyVO.getScheme().getSchemeCode().toString().equals( Utils.getSingleValueAppConfig( "FGB_BROKER_DIRECT_SCHEME_CODE" )))){
					reportTemplatesType = ReportTemplateSet._FGB_SBS.toString();
				}

			}

			isRecieptToBeShown = isRecieptToBeShown( request, isDistributionBr );
			
			if( !Utils.isEmpty( policyVO ) && !Utils.isEmpty( policyVO.getPolicyNo() ) ){
				isFreeZoneToBeShown = AppUtils.isFreeZoneToBeShow( policyVO.getPolicyNo(),policyVO.getQuoteNo(),policyVO.getEndtId(),policyVO.getValidityStartDate(),policyVO.getIsQuote() );
			}
			
			request.setAttribute( "parFlag", parFlag );
			request.setAttribute( "plFlag", plFlag );
			request.setAttribute( "wcFlag", wcFlag );
			request.setAttribute( "parPolId", parPolId );
			request.setAttribute( "plPolId", plPolId );
			request.setAttribute( "wcPolId", wcPolId );

			request.setAttribute( "polLinkId", policyLinkingId );
			request.setAttribute( "endId", maxEndId.toString() );
			request.setAttribute( "valStDate", sdate );
			request.setAttribute( "endFlag", endFlag );
			request.setAttribute( "renFlag", renFlag );
			request.setAttribute( "isDistributionBr", isDistributionBr );
			request.setAttribute( "isRecieptToBeShown", isRecieptToBeShown );
			request.setAttribute( "reportTemplatesType", reportTemplatesType );
			request.setAttribute( "currentLob", LOB.SBS.toString() );
			request.setAttribute( "isFreeZoneToBeShown", isFreeZoneToBeShown );		
			
			if(policyVO.getPolVATCode()!=0){ //changed from 999 to 0 after confirmation for SBS
			logger.info( "HHH VAT Code for SBS-1: " + policyVO.getPolVATCode() );
			request.setAttribute(com.Constant.CONST_VATCODE,policyVO.getPolVATCode());
			
			}
			
			else{
				
				logger.info( "HHH VAT Code for SBS-2: " + commonVO.getPremiumVO().getVatCode() );
				request.setAttribute(com.Constant.CONST_VATCODE,commonVO.getPremiumVO().getVatCode());
				
			}
			
		
			
			/*if((policyVO.getPolExpiryDate()!=null) && (policyVO.getPolExpiryDate().compareTo(getVatLiveDate())>=0) ){
				
				logger.info("HHH POL ExpiryDate for SBS: "+policyVO.getPolExpiryDate());
				logger.info( "HHH VAT Start Date for SBS:"+ getVatLiveDate());
				
				request.setAttribute(com.Constant.CONST_VATDATECOND, "1");
			}
			else{
				
				request.setAttribute(com.Constant.CONST_VATDATECOND, "0");
			}*/
			
			if(policyVO.getQuoteNo()!=null && policyVO.getIsQuote()!=null && policyVO.getEndtId()!=null){
				
				logger.info( "HHH QUOTE NUMBER for  SBS: " + policyVO.getQuoteNo());
				logger.info( "HHH IS_QUOTE for  SBS: " + policyVO.getIsQuote());
				logger.info( "HHH POL_ENDT_ID for  SBS: " + policyVO.getEndtId());
				logger.info( "HHH POL_ENDT_ID New for  SBS: " + SvcUtils.getLatestEndtId( policyVO ));
				
				
				
				java.util.Date polIssueDate = null;
				
				if(policyVO.getIsQuote()){
					
					logger.info( "HHH POL_LINKING_ID New for  SBS: " + policyVO.getPolLinkingId());
					logger.info( "HHH POLICY NUM New for  SBS: " + policyVO.getPolicyNo());						
					polIssueDate = DAOUtils.getPolIssueDateForSBSQuo(policyVO.getQuoteNo(),policyVO.getIsQuote(),policyVO.getPolLinkingId(),policyVO.getPolicyNo());
				}
				
				else{
					
							
					
					polIssueDate = DAOUtils.getPolIssueDate(policyVO.getQuoteNo(), policyVO.getIsQuote(),policyVO.getEndtId());
				}
				
				
				
				
				if(polIssueDate!=null && polIssueDate.compareTo(getVatLiveDate())>=0){
					
					logger.info( "HHH POL ISSUE DATE for SBS: "+polIssueDate );
					
					request.setAttribute(com.Constant.CONST_VATDATECOND, "1");
				}
				else{
					
					request.setAttribute(com.Constant.CONST_VATDATECOND, "0");
				}
			}else{
				
				request.setAttribute(com.Constant.CONST_VATDATECOND, "0");
			}
	
			
			}
		
		else if( !Utils.isEmpty( commonVO ) ){
			Boolean isCancelled = false;
			reportTemplatesType = (ReportTemplateSet.valueOf("_"+commonVO.getLob().toString())).getPolicyScheduleTemplate();
			
			/*reportTemplatesType = ReportTemplateSet._HOME.toString();

			if( commonVO.getLob().equals( LOB.TRAVEL ) ){
				reportTemplatesType = ReportTemplateSet._TRAVEL.toString();
			}*/
			CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean("geComSvc_POL");
			PolicyDetailsVO policyDtlVO = new PolicyDetailsVO();
			policyDtlVO.setPolicyNo( commonVO.getPolicyNo() );
			policyDtlVO.setPolConcPolicyNo( commonVO.getConcatPolicyNo() );
			Long endtId = commonVO.getEndtId();
			
			if(NON_VERSION_STATUS.contains(commonVO.getStatus().toString())){
				@SuppressWarnings("unchecked")
				DataHolderVO<Long> dataHolderVO = (DataHolderVO<Long>) commonOpSvc.invokeMethod("getPrevEndtIdForPendingPolicy", commonVO);
				endtId = dataHolderVO.getData();
			}

			policyDtlVO.setEndtId( (commonVO.getIsQuote() ? AppConstants.INTIAL_POL_ENDT : endtId).toString());
			
			logger.info( "Policy No within PrintPolicyDocRH for processing -->" + commonVO.getPolicyNo() );
			
			BaseVO baseVO = TaskExecutor.executeTasks( "PRINT_POLICY_DOC_HT", policyDtlVO );
			PolicyDetailsHolder policyDtls = (PolicyDetailsHolder) baseVO;
			List<PolicyDetailsVO> policyList = policyDtls.getPolicyDtlList();
			PolicyDetailsVO policyDetailsVO = policyList.get( 0 );
			Integer maxEndId = 0;

			if( Integer.valueOf( policyDetailsVO.getEndtId() ) > maxEndId ){
				maxEndId = Integer.valueOf( policyDetailsVO.getEndtId() );
			}

			if( Integer.valueOf( policyDetailsVO.getPolDocumentId() ) == 3 ){
				endFlag = true;
			}
			else if( Integer.valueOf( policyDetailsVO.getPolDocumentId() ) == 2 ){
				renFlag = true;
			}

			if( ( !Utils.isEmpty( policyDetailsVO.getPolBrCode() ) && !policyDetailsVO.getPolBrCode().equalsIgnoreCase( "null" ) )
					|| ( !Utils.isEmpty( policyDetailsVO.getPolAgentId() ) && !policyDetailsVO.getPolAgentId().equalsIgnoreCase( "null" ) ) ){
				isDistributionBr = true;
			}

			/*if( Integer.valueOf( commonVO.getDocCode() ) == 3 && maxEndId != 0){
				endFlag = true;
			}
			else if( Integer.valueOf( commonVO.getDocCode() ) == 2 ){
				renFlag = true;
			}*/
			if(!Utils.isEmpty( commonVO ) && !Utils.isEmpty( commonVO.getStatus() ) && commonVO.getStatus() == SvcConstants.DEL_SEC_LOC_STATUS){
				isCancelled = true;
			}
			isRecieptToBeShown = isRecieptToBeShown( request, isDistributionBr );
			
			if( !Utils.isEmpty( commonVO ) && !Utils.isEmpty( commonVO.getPolicyNo() ) ){
				isFreeZoneToBeShown = AppUtils.isFreeZoneToBeShow(policyDetailsVO,commonVO);
			}
			
			request.setAttribute( "currentLob", commonVO.getLob().toString() );
			request.setAttribute( "policyId", policyDetailsVO.getPolicyId() );
			request.setAttribute( "endId", policyDetailsVO.getEndtId() );
			request.setAttribute( "valStDate", policyDetailsVO.getStartDate() );
			request.setAttribute( "endFlag", endFlag );
			request.setAttribute( "renFlag", renFlag );
			request.setAttribute( "isDistributionBr", isDistributionBr );
			request.setAttribute( "isRecieptToBeShown", isRecieptToBeShown );
			request.setAttribute( "reportTemplatesType", reportTemplatesType );
			request.setAttribute( "isCancelled", isCancelled );
			request.setAttribute( "isFreeZoneToBeShown", isFreeZoneToBeShown );
			request.setAttribute( "freeZoneReportTemplate", (ReportTemplateSet.valueOf("_"+commonVO.getLob().toString())).getFreeZoneCertificateTemplate());
			
			try{
				
				if(commonVO.getPremiumVO().getVatCode()!=null){
					logger.info( "VAT Code for HOME_TRAVEL-1: " + commonVO.getPremiumVO().getVatCode() );
					request.setAttribute(com.Constant.CONST_VATCODE, commonVO.getPremiumVO().getVatCode());
					
				}
			
				else if(policyVO.getPolVATCode()!=0){
				
				logger.info( "VAT Code for HOME_TRAVEL-2: " + policyVO.getPolVATCode() );
				request.setAttribute(com.Constant.CONST_VATCODE, policyVO.getPolVATCode());
				
				}
			
			}
			
			catch(NullPointerException e){
			
			logger.info( "VAT Code for HOME_TRAVEL_WC-3: " + commonVO.getVatCode() );
			request.setAttribute(com.Constant.CONST_VATCODE, commonVO.getVatCode());
		
			}
			
			

		/*	if((commonVO.getPolExpiryDate()!=null) && (commonVO.getPolExpiryDate().compareTo(getVatLiveDate())>=0)){
				
				logger.info( "HHH POL Expiry Date for HOME_TRAVEL_WC:"+ commonVO.getPolExpiryDate());
				logger.info( "HHH VAT Start Date HOME_TRAVEL_WC:"+ getVatLiveDate());

				
				request.setAttribute(com.Constant.CONST_VATDATECOND, "1");
			}
			
			else{
				
				request.setAttribute(com.Constant.CONST_VATDATECOND, "0");
			}*/
			
			
			if(commonVO.getQuoteNo()!=null && commonVO.getIsQuote()!=null && commonVO.getEndtId()!=null){
				
				logger.info( "HHH QUOTE NUMBER for  HOME_TRAVEL_WC: " + commonVO.getQuoteNo());
				logger.info( "HHH IS_QUOTE for  HOME_TRAVEL_WC: " + commonVO.getIsQuote());
				logger.info( "HHH ENDT_ID for  HOME_TRAVEL_WC: " + commonVO.getEndtId());
				
				java.util.Date polIssueDate = DAOUtils.getPolIssueDate(commonVO.getQuoteNo(), commonVO.getIsQuote(),commonVO.getEndtId());
				
				if(polIssueDate!=null && polIssueDate.compareTo(getVatLiveDate())>=0){
					
					logger.info( "HHH POL ISSUE DATE for HOME_TRAVEL_WC: "+polIssueDate );
					
					request.setAttribute(com.Constant.CONST_VATDATECOND, "1");
				}
				else{
					
					request.setAttribute(com.Constant.CONST_VATDATECOND, "0");
				}
			}
			else{
				
				request.setAttribute(com.Constant.CONST_VATDATECOND, "0");
			}
		}
		
		
		
		
		/*
		 * Set the flag to determine whether to show gross DN or CN option
		 */

		Integer code = SvcUtils.getLookUpCode( AppConstants.PAS_GR_D_C, AppConstants.LOOKUP_LEVEL1, AppConstants.LOOKUP_LEVEL2, "GROSS DEBIT AND CREDIT NOTE" );
		if( !Utils.isEmpty( code ) ){
			request.setAttribute( AppConstants.PAS_GR_D_C, AppConstants.TRUE );
		}

		return responseObj;
	}

	private boolean isRecieptToBeShown( HttpServletRequest request, Boolean isDistributionBr ){

		UserProfile userProfile = AppUtils.getUserDetailsFromSession( request );
		String profile = userProfile.getRsaUser().getProfile();

		boolean isRecieptToBeShown = true;
		if( profile.equalsIgnoreCase( "EMPLOYEE" ) && isDistributionBr ){
			isRecieptToBeShown = false;
		}
		return isRecieptToBeShown;
	}

	public static Date getVatLiveDate() {
		
		String vatStartDate = null;
		Date vatLiveDate = null;
		
		if (Utils.isEmpty(vatStartDate)) {

			List<Object> resultSetVat = DAOUtils
					.getSqlResultSingleColumnPas(QueryConstants.FETCH_VAT_START_DATE);

			if (!Utils.isEmpty(resultSetVat) && resultSetVat.size() > 0) {

				String vatIncDate = null;

				vatIncDate = (String) resultSetVat.get(0);

				if (vatIncDate != null) {
					vatIncDate = formatDt(vatIncDate, "dd-MMM-yyyy","MM/dd/yyyy");
					vatStartDate = vatIncDate;

				}
			}
			
			String defaultDateFormat = Utils.getSingleValueAppConfig("DEFAULT_DATE_FORMAT"); //MM/dd/yyyy
			try {
				vatLiveDate = new SimpleDateFormat(defaultDateFormat).parse(vatStartDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}


		}
		
		return vatLiveDate;
	}
	
	private static String formatDt(String vatIncDate, String srcFormat,
			String destFormat) {

		String dateStr = null;

		try {
			dateStr = vatIncDate;

			DateFormat srcDf = new SimpleDateFormat(srcFormat);

			// parse the date string into Date object
			Date date = srcDf.parse(dateStr);

			// 11/01/2017
			DateFormat destDf = new SimpleDateFormat(destFormat);

			// format the date into another format
			dateStr = destDf.format(date);

			System.out.println("Converted date is : " + dateStr);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dateStr;
	}
}
