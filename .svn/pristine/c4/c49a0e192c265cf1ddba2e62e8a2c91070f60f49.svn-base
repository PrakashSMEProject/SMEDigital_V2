/**
 * 
 */
package com.rsaame.pas.email.ui;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.doc.svc.PASDocumentService;
import com.rsaame.pas.mail.svc.PASMailerService;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.app.MailVO;
import com.rsaame.pas.vo.app.ReportTemplateSet;
import com.rsaame.pas.vo.bus.CreditNoteDetailsVO;
import com.rsaame.pas.vo.bus.DebitNoteDetailsVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReceiptDetailsVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author M1012799
 *
 */
public class ProcessEmailRH implements IRequestHandler{

	private static final String EMAIL_NOTIFICATION_JSP = "/jsp/quote/emailNotification.jsp";

	private static Logger logger = Logger.getLogger( ProcessEmailRH.class );

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		Response responseObj = new Response();
		Redirection redirection = null;
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		CommonVO commonVO = policyContext.getCommonDetails();
		String lob = commonVO.getLob().toString();
		MailVO mailVo = new MailVO();
		String opType = request.getParameter( "operation" );

		if( !Utils.isEmpty( opType ) && opType.equalsIgnoreCase( "sendMail" ) ){

			String toAddress = request.getParameter( "emailTo" );
			String ccAddress = request.getParameter( "emailCC" );
			String subject = request.getParameter( "emailSubject" );
			String content = request.getParameter( "content" );
			String fileName = request.getParameter( "fileName" );
			String isDirect = request.getParameter( com.Constant.CONST_DIRECT );
			String policyType = request.getParameter (com.Constant.CONST_POLICYTYPE);
			UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );

			StringBuilder mailContent = new StringBuilder( content );
			String[] fileNames = null;
			HashMap<String, String> reportParams = new HashMap<String, String>();
			
			if( AppUtils.isQuote( commonVO ) ){
				fileNames = getFileNamesQuote( fileName, commonVO, reportParams );

			}
			else{
				//fileNames = getFileNamesPolicy( fileName, commonVO, reportParams );
				fileNames = (String[]) request.getSession().getAttribute( com.Constant.CONST_FILENAMES );
				reportParams = (HashMap<String, String>) request.getSession().getAttribute( com.Constant.CONST_REPORTPARAMS );
			}

			

			if( !Utils.isEmpty( ccAddress ) ){
				if( LOB.HOME.equals( LOB.valueOf( lob ) ) || LOB.TRAVEL.equals( LOB.valueOf( lob ) )){
				ccAddress += "," + Utils.getSingleValueAppConfig( "EMAIL_CC_HOME_TRAVEL" ) ;
				}
				else
				{
					ccAddress += "," + Utils.getSingleValueAppConfig( "EMAIL_CC_SBS_WC" ) ;
				}
				String[] ccAddressess = ccAddress.split( "," );
				mailVo.setCcAddress( ccAddressess );
			}
			else{
				String[] ccAddressess = new String[ 1 ];
				ccAddressess[ 0 ] = userProfile.getRsaUser().getEmail();
				mailVo.setCcAddress( ccAddressess );
			}

			mailVo.setToAddress( toAddress );
			mailVo.setSubjectText( subject );
			mailVo.setMailType( SvcConstants.MAIL_TYPE_HTML );
			mailVo.setMailContent( mailContent );
			
			if(!Utils.isEmpty( isDirect )){
				mailVo.setDirect( Boolean.valueOf( isDirect ) );
			}
			
			logger.debug("*******Process Email *****policyType***"+policyType);
			
			if(!Utils.isEmpty( policyType )){
				
				mailVo.setPolicyType( policyType );
				
			}
			
			//Adv 14041
			String fromAddress = null;
			boolean mailStatus = false;
			if( !Utils.isEmpty( userProfile.getRsaUser().getEmail() ) ){
				if( LOB.HOME.equals( LOB.valueOf( lob ) ) || LOB.TRAVEL.equals( LOB.valueOf( lob ) )){
				fromAddress =  Utils.getSingleValueAppConfig( "EMAIL_FROM_HOME_TRAVEL" ) ;
				}
				else
				{
					fromAddress =  Utils.getSingleValueAppConfig( "EMAIL_FROM_SBS_WC" ) ;
				}
				mailVo.setFromAddress( fromAddress );
				mailStatus = sendMail( mailVo, fileNames, commonVO, reportParams);

				if( mailStatus ){
					responseObj.setResponseType( Response.Type.JSON );
					responseObj.setSuccess( true );
					String result = "";
					//if( commonVO.getIsQuote() && Utils.isEmpty( commonVO.getPolicyNo() ) ){
					if(AppUtils.isQuote( commonVO )){
						result = "SuccessQuote";
					}
					else{
						result = "SuccessPolicy";
					}

					responseObj.setData( result );
				}
				else{
					responseObj.setResponseType( Response.Type.JSON );
					responseObj.setSuccess( true );
					String result = "failure";

					responseObj.setData( result );
				}
			}
			else{
				responseObj.setResponseType( Response.Type.JSON );
				String result = "NoFromAddress";
				responseObj.setData( result );
			}
			request.getSession().removeAttribute( com.Constant.CONST_FILENAMES );
			request.getSession().removeAttribute( com.Constant.CONST_REPORTPARAMS );
		}
		else{
			if( !Utils.isEmpty( commonVO ) ){

				if( LOB.HOME.equals( LOB.valueOf( lob ) ) ){
					//changes-HomeRevamp#7.1
					String ownershipstatus=request.getParameter("ownershipstatus");
					String default_status="0";
					//changes-HomeRevamp#7.1
					HomeInsuranceVO homeInsuranceVO = new HomeInsuranceVO();
					homeInsuranceVO = (HomeInsuranceVO) loadGeneralInfo( homeInsuranceVO, commonVO );

					if( !Utils.isEmpty( homeInsuranceVO ) && !Utils.isEmpty( homeInsuranceVO.getScheme() ) && !Utils.isEmpty( homeInsuranceVO.getScheme().getEffDate() ) ){
						commonVO.setPolEffectiveDate( homeInsuranceVO.getScheme().getEffDate() );
					}

					setRequestAttribute( homeInsuranceVO, commonVO, request );
					request.setAttribute( "lob", "Home" );
					//changes-HomeRevamp#7.1
					if(default_status.equalsIgnoreCase(ownershipstatus)){
						String policyNo= homeInsuranceVO.getCommonVO().getPolicyNo().toString();
						ownershipstatus=DAOUtils.fetchOwnershipInfo(policyNo);	
					}
					boolean isOldOrNewHomeQuote = false;
					
					try {
						isOldOrNewHomeQuote = checkPolIssueDate( homeInsuranceVO);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					request.setAttribute("isOldOrNewHomeQuote", isOldOrNewHomeQuote);
					request.setAttribute("HomeOwnerShipStatus",ownershipstatus);
					//changes-HomeRevamp#7.1

				}
				else if( LOB.TRAVEL.equals( LOB.valueOf( lob ) ) ){
					TravelInsuranceVO travelInsuranceVO = new TravelInsuranceVO();
					travelInsuranceVO = (TravelInsuranceVO) loadGeneralInfo( travelInsuranceVO, commonVO );

					if( !Utils.isEmpty( travelInsuranceVO ) && !Utils.isEmpty( travelInsuranceVO.getScheme() ) && !Utils.isEmpty( travelInsuranceVO.getScheme().getEffDate() ) ){
						commonVO.setPolEffectiveDate( travelInsuranceVO.getScheme().getEffDate() );
					}
					
					// 131378
					int isOldOrNewQuote=0;
					try {
						 isOldOrNewQuote = checkPolPreparedDt (travelInsuranceVO);
							logger.info(" ***isOldOrNewQuote.. ****"+isOldOrNewQuote);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					request.setAttribute("isOldOrNewQuote", isOldOrNewQuote);
					
					setRequestAttribute( travelInsuranceVO, commonVO, request );
					request.setAttribute( com.Constant.CONST_POLICYTYPE, travelInsuranceVO.getPolicyType());
					request.setAttribute( "lob", "Travel" );
				}
				else{
					/* The condition will load the monoline general info*/
					PolicyDataVO policyDataVO = new PolicyDataVO();
					policyDataVO = loadGeneralInfo( policyDataVO, commonVO );
					setRequestAttribute( policyDataVO, commonVO, request );
					request.setAttribute( "lob", lob );
				}
			}
			redirection = new Redirection( EMAIL_NOTIFICATION_JSP, Type.TO_JSP );
			responseObj.setRedirection( redirection );
		}

		return responseObj;
	}

	
	//131378 : To view old or new cover details
		/**
		 * @param travelInsuranceVO
		 * */
		private int checkPolPreparedDt(TravelInsuranceVO travelInsuranceVO) throws ParseException {
			
				int isTerrCruiseInductionDate = 0;
				String d2 = Utils.getSingleValueAppConfig("TerrCruiseInductionDate");
				SimpleDateFormat s2 = new SimpleDateFormat("MM-dd-yyyy");
				Long QuoteNum = travelInsuranceVO.getQuoteNo();
				if(Utils.isEmpty(QuoteNum)){
					QuoteNum=travelInsuranceVO.getCommonVO().getQuoteNo();
				}
				Date polPrepDt = DAOUtils.getPreparedDateForCovers(QuoteNum);

				Date prodDt = s2.parse(d2);
				if(polPrepDt.after(prodDt)){
					isTerrCruiseInductionDate = 1;
				}
				return isTerrCruiseInductionDate;
		}
	
	/**
	 * Method to get the file names for quote
	 * 
	 * @param fileName
	 * @param commonVO
	 * @param reportParams
	 * @return
	 */
	private String[] getFileNamesQuote( String fileName, CommonVO commonVO, HashMap<String, String> reportParams ){
		fileName = Utils.getSingleValueAppConfig( "QUOTE_DOC_PROPOSAL_LOC" ) + commonVO.getQuoteNo() + "-Quote.pdf";

		String[] fileNames = new String[ 1 ];
		fileNames[ 0 ] = fileName;

		reportParams.put( "policyId", String.valueOf( commonVO.getPolicyId() ) );

		reportParams.put( "endoresementId", String.valueOf( commonVO.getEndtId() ) );

		reportParams.put( "polValStartDate", getFormattedDate( commonVO.getVsd() ) );

		reportParams.put( "language", "E" );
		
		//added for abudhabi/baharain
		reportParams.put("locationCode", Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));
		return fileNames;
	}

	/**
	 * Get the file names for policy from UI
	 * 
	 * @param fileNamesFromUI
	 * @param commonVO
	 * @param reportParams 
	 * @return
	 */
	private String[] getFileNamesPolicy( String fileNamesFromUI, CommonVO commonVO, HashMap<String, String> reportParams ){

		LookUpListVO lookUpListVO = SvcUtils.getLookUpCodesList( "MAIL_CONFG", "ALL", "ALL" );
		java.util.List<LookUpVO> lookUps = lookUpListVO.getLookUpList();

		Map<BigDecimal, String> fileNamesMap = new HashMap<BigDecimal, String>();
		@SuppressWarnings( "rawtypes" )
		Iterator itr = lookUps.listIterator();

		String isPolicySchedule = com.Constant.CONST_FALSE;
		String isCreditNote = com.Constant.CONST_FALSE;
		String isGrossCreditNote = com.Constant.CONST_FALSE;
		String isDebitNote = com.Constant.CONST_FALSE;
		String isGrossDebitNote = com.Constant.CONST_FALSE;
		String isRecipt = com.Constant.CONST_FALSE;
		String isEndtSchedule = com.Constant.CONST_FALSE;
		String isBankLetter = com.Constant.CONST_FALSE;
		String isBuyerCreatedTaxInvoice = com.Constant.CONST_FALSE;
		while( itr.hasNext() ){

			LookUpVO lookUpVO = (LookUpVO) itr.next();
			BigDecimal key = lookUpVO.getCode();
			String value = lookUpVO.getDescription();
			fileNamesMap.put( key, value );

		}

		logger.debug( "PASEmailUtil:fileNamesFromUI:" + fileNamesFromUI );
		String[] fileNames = null;
		if( !Utils.isEmpty( fileNamesFromUI ) ){

			StringTokenizer st = new StringTokenizer( fileNamesFromUI, "," );
			fileNames = new String[ st.countTokens() ];
			int numbDocs = st.countTokens();

			if( !Utils.isEmpty( commonVO ) && !Utils.isEmpty( commonVO.getPolicyNo() ) ){
				for( int i = 0; i < numbDocs; i++ ){

					String fileName = st.nextToken();

					if( fileName.equals( "policyScheduleUAE" ) ){
						isPolicySchedule = "true";
						fileNames[ i ] = Utils.getSingleValueAppConfig( "POL_DOC_POL_SCHED_LOC" ) + commonVO.getPolicyNo() + "-PolicySchedule.pdf";

					}
					else if( fileName.equals( "printReceipt" ) ){
						isRecipt = "true";
						setReceiptParameters( commonVO, reportParams );
						fileNames[ i ] = Utils.getSingleValueAppConfig( "POL_DOC_POL_RECEIPTS_LOC" ) + commonVO.getPolicyNo() + "-Receipt.pdf";

					}
					else if( fileName.equals( "creditNote" ) ){
						isCreditNote = "true";
						setCreditNoteParameters( commonVO, reportParams );
						fileNames[ i ] = Utils.getSingleValueAppConfig( "POL_DOC_CRED_NOTE_LOC" ) + commonVO.getPolicyNo() + "-CreditNote.pdf";

					}
					else if( fileName.equals( "grossCreditNote" ) ){
						isGrossCreditNote = "true";
						setCreditNoteParameters( commonVO, reportParams );
						fileNames[ i ] = Utils.getSingleValueAppConfig( "POL_DOC_GROSS_CRED_NOTE_LOC" ) + commonVO.getPolicyNo() + "-GrossCreditNote.pdf";

					}
					else if( fileName.equals( "debitNote" ) ){
						isDebitNote = "true";
						setDebitNoteParameters( commonVO, reportParams );
						fileNames[ i ] = Utils.getSingleValueAppConfig( "POL_DOC_DEB_NOTE_LOC" ) + commonVO.getPolicyNo() + "-DebitNote.pdf";
					}
					else if( fileName.equals( "grossDebitNote" ) ){
						isGrossDebitNote = "true";
						setDebitNoteParameters( commonVO, reportParams );
						fileNames[ i ] = Utils.getSingleValueAppConfig( "POL_DOC_GROSS_DEB_NOTE_LOC" ) + commonVO.getPolicyNo() + "-GrossDebitNote.pdf";

					}
					else if( fileName.equals( "freeZone" ) ){

						fileNames[ i ] = Utils.getSingleValueAppConfig( "POL_DOC_FREEZONE_CERT_LOC" ) + commonVO.getPolicyNo() + "-FreeZoneCert.pdf";

					}
					else if( fileName.equals( "endScheduleUAE" ) ){
						isEndtSchedule = "true";
						fileNames[ i ] = Utils.getSingleValueAppConfig( "POL_DOC_ENDT_SCHED_LOC" ) + commonVO.getPolicyNo() + "-EndorsementSchedule.pdf";
					}
					else if( fileName.equals( "bankLetter" ) ){
						isBankLetter = "true";
						fileNames[ i ] = Utils.getSingleValueAppConfig( "POL_DOC_BANK_LETTER" ) + commonVO.getPolicyNo() + "-BankLetter.pdf";
					}
					
					//Added for Buyer created tax invoice.
					else if( fileName.equals( "buyerCreditedTaxInvoice" ) ){
						isBuyerCreatedTaxInvoice = "true";
						fileNames[ i ] = Utils.getSingleValueAppConfig( "POL_DOC_CRED_NOTE_LOC" ) + commonVO.getPolicyNo() + "-BuyerCreatedTaxInvoice.pdf";
					}

				}

			}
			else{
				BusinessException businessExcp = new BusinessException( "mail.error", null, "Policy Number is Null" );
				throw businessExcp;

			}

			//}
		}

		List<Object> result = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_POLICY_ID, commonVO.getPolicyNo(),commonVO.getConcatPolicyNo() );
		
		Long policyId = ((BigDecimal) result.get( 0 )).longValue();
		reportParams.put( "policyId", String.valueOf( policyId ) );

		reportParams.put( "endorsementId", String.valueOf( commonVO.getIsQuote() ? AppConstants.INTIAL_POL_ENDT : commonVO.getEndtId() ) );

		reportParams.put( "validityStartDate", getFormattedDate( commonVO.getVsd() ) );

		reportParams.put( "language", "E" );
		
		reportParams.put( "CreditNoteReport", isCreditNote );
		reportParams.put( "GrossCreditNoteReport", isGrossCreditNote );
		reportParams.put( "DebitNoteReport", isDebitNote );
		reportParams.put( "GrossDebitNoteReport", isGrossDebitNote );
		reportParams.put( "Receipt", isRecipt );
		reportParams.put( "PolicySchedule", isPolicySchedule );
		reportParams.put( "EndScheduleReport", isEndtSchedule );
		reportParams.put( "ShowBankLetter", isBankLetter );
		reportParams.put( "BuyerCreatedTaxInvoiceReport", isBuyerCreatedTaxInvoice );
		
		//added for abudhabi/baharain
		reportParams.put("locationCode", Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));
		return fileNames;
	}

	/**
	 * Method to set debit nore parameters
	 * @param commonVO
	 * @param reportParams
	 */
	private void setDebitNoteParameters( CommonVO commonVO, HashMap<String, String> reportParams ){
		CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( com.Constant.CONST_GECOMSVC );
		DataHolderVO<Long> policyIdHolder = (DataHolderVO<Long>) commonOpSvc.invokeMethod( com.Constant.CONST_GETPOLICYIDFORPOLICY, commonVO );
		
		DebitNoteDetailsVO drNoteDetsVO = new DebitNoteDetailsVO();
		drNoteDetsVO.setDndPolicyNo( commonVO.getPolicyNo() );
		drNoteDetsVO.setDndEndtId( commonVO.getIsQuote() ? AppConstants.INTIAL_POL_ENDT : commonVO.getEndtId() );
		drNoteDetsVO.setDndPolicyYear( SvcUtils.getYearFromDate( commonVO.getPolEffectiveDate() ) );
		drNoteDetsVO.setDndPolicyId( policyIdHolder.getData());
		
		BaseVO resultVO = TaskExecutor.executeTasks( "DEBIT_NOTE_DOC", drNoteDetsVO );

		if( !Utils.isEmpty( resultVO ) ){
			drNoteDetsVO = (DebitNoteDetailsVO) resultVO;
			if( !Utils.isEmpty( drNoteDetsVO.getDndDebitNoteNo() ) ){
				reportParams.put( "debitNoteNo", drNoteDetsVO.getDndDebitNoteNo().toString() );
				logger.debug( "debitNoteNo:" + drNoteDetsVO.getDndDebitNoteNo().toString() );
			}
			if( !Utils.isEmpty( drNoteDetsVO.getDndDebitNoteDate() ) ){

				String debitNoteDateString = drNoteDetsVO.getDndDebitNoteDate();
				Date creditNoteDate = convertStringToDate( debitNoteDateString );
				DateFormat format = new SimpleDateFormat( com.Constant.CONST_DD_MMM_YYYY );
				String debitNoteDate = format.format( creditNoteDate );
				reportParams.put( "debitNoteDate", debitNoteDate );
				logger.debug( "debitNoteDate:" + debitNoteDate );
			}
		}

	}

	/**
	 * Method to set credit note parameters
	 * @param commonVO
	 * @param reportParams
	 */
	private void setCreditNoteParameters( CommonVO commonVO, HashMap<String, String> reportParams ){
		CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( com.Constant.CONST_GECOMSVC );
		DataHolderVO<Long> policyIdHolder = (DataHolderVO<Long>) commonOpSvc.invokeMethod( com.Constant.CONST_GETPOLICYIDFORPOLICY, commonVO );
		
		CreditNoteDetailsVO crDetsVO = new CreditNoteDetailsVO();
		crDetsVO.setCndPolicyNo( commonVO.getPolicyNo() );
		crDetsVO.setCndEndtId( commonVO.getIsQuote() ? AppConstants.INTIAL_POL_ENDT : commonVO.getEndtId() );
		crDetsVO.setCndPolicyYear( SvcUtils.getYearFromDate( commonVO.getPolEffectiveDate() ) );
		crDetsVO.setCndPolicyId( policyIdHolder.getData() );
		
		BaseVO resultVO = TaskExecutor.executeTasks( "CREDIT_NOTE_DOC", crDetsVO );
		if( !Utils.isEmpty( resultVO ) ){
			crDetsVO = (CreditNoteDetailsVO) resultVO;
			if( !Utils.isEmpty( crDetsVO.getCndCreditNoteNo() ) ){
				reportParams.put( "creditNoteNo", crDetsVO.getCndCreditNoteNo().toString() );
				logger.debug( "creditNoteNo:" + crDetsVO.getCndCreditNoteNo().toString() );
			}
			if( !Utils.isEmpty( crDetsVO.getCndCreditNoteDate() ) ){

				String creditNoteDateString = crDetsVO.getCndCreditNoteDate();
				Date creditNoteDate = convertStringToDate( creditNoteDateString );
				DateFormat format = new SimpleDateFormat( com.Constant.CONST_DD_MMM_YYYY );
				String credNoteDate = format.format( creditNoteDate );
				reportParams.put( "creditNoteDate", credNoteDate );
				logger.debug( "creditNoteDate:" + credNoteDate );
			}
		}

		/*if( fileName.equals( "creditNote" ) ){
			isCreditNote = "true";
		}
		else{
			isGrossCreditNote = "true";
		}*/

	}

	/**
	 * Method to set the receipt parameters
	 * @param commonVO
	 * @param reportParams
	 */
	private void setReceiptParameters( CommonVO commonVO, HashMap<String, String> reportParams ){
		CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( com.Constant.CONST_GECOMSVC );
		DataHolderVO<Long> policyIdHolder = (DataHolderVO<Long>) commonOpSvc.invokeMethod( com.Constant.CONST_GETPOLICYIDFORPOLICY, commonVO );
		
		ReceiptDetailsVO rcptDetsVO = new ReceiptDetailsVO();
		rcptDetsVO.setRcdPolicyNo( commonVO.getPolicyNo() );
		rcptDetsVO.setRcdEndtId( commonVO.getIsQuote() ? AppConstants.INTIAL_POL_ENDT : commonVO.getEndtId() );
		rcptDetsVO.setRcdPolicyId( policyIdHolder.getData() );
		
		BaseVO resultVO = TaskExecutor.executeTasks( "RECEIPT_DOC", rcptDetsVO );
		if( !Utils.isEmpty( resultVO ) ){
			rcptDetsVO = (ReceiptDetailsVO) resultVO;
			if( !Utils.isEmpty( rcptDetsVO.getRcdReceiptNo() ) ){
				reportParams.put( "receiptNo", rcptDetsVO.getRcdReceiptNo().toString() );
				logger.debug( "receiptNo:" + rcptDetsVO.getRcdReceiptNo().toString() );
			}
			if( !Utils.isEmpty( rcptDetsVO.getRcdReceiptDate() ) ){

				String reciptDateString = rcptDetsVO.getRcdReceiptDate();
				Date reciptDate = convertStringToDate( reciptDateString );
				DateFormat format = new SimpleDateFormat( com.Constant.CONST_DD_MMM_YYYY );
				String receiptDate = format.format( reciptDate );
				reportParams.put( "receiptDate", receiptDate );
				logger.debug( "receiptDate:" + receiptDate );
			}
		}

	}

	/**
	 * Method to create the policy document
	 * @param mailVo 
	 * @param commonVO 
	 */
	private boolean sendMail( MailVO mailVo, String[] fileNames, CommonVO commonVO, HashMap<String, String> reportParams ){

		String lob = commonVO.getLob().toString();
		
		boolean mailStatus = false;

		mailVo.setFileNames( fileNames );
		logger.debug("**********Send Mail*************");
		
		if( !Utils.isEmpty( mailVo.getPolicyType())){
			
			logger.debug("**********lob*************"+lob);
		reportParams.put("polTypeCode",mailVo.getPolicyType());
		
		logger.debug("poltype-->"+mailVo.getPolicyType());
		}
		
		if( LOB.HOME.equals( LOB.valueOf( lob ) ) ){
			reportParams.put( com.Constant.CONST_REPORTTEMPLATESTYPE, ReportTemplateSet._HOME.toString() );
		}
		else if( LOB.TRAVEL.equals( LOB.valueOf( lob ) ) ){
			
			reportParams.put( com.Constant.CONST_REPORTTEMPLATESTYPE, ReportTemplateSet._TRAVEL.toString() );
		}
		else{
			reportParams.put( com.Constant.CONST_REPORTTEMPLATESTYPE, ReportTemplateSet.valueOf("_"+commonVO.getLob().toString()).toString() );
		}

		mailVo.setDocParameter( reportParams );

		try{
			PASDocumentService docCreator = (PASDocumentService) Utils.getBean( "docServiceBean" );
			mailVo = (MailVO) docCreator.invokeMethod( "createDocument", mailVo );

			if( !Utils.isEmpty( mailVo ) && !mailVo.getDocCreationStatus().equalsIgnoreCase( "failure" ) ){

				PASMailerService mailer = (PASMailerService) Utils.getBean( "emailService" );

				//if( commonVO.getIsQuote() && Utils.isEmpty( commonVO.getPolicyNo() ) ){
				if( AppUtils.isQuote( commonVO ) ){
					mailVo = (MailVO) mailer.invokeMethod( "sendMailImage", mailVo, "QUOTE" );
				}
				else{
					mailVo = (MailVO) mailer.invokeMethod( "sendMailImage", mailVo, "POLICY" );
				}
			}
			else{
				mailVo.setMailStatus( "fail" );
			}
		}
		catch( Exception e ){
			mailVo.setMailStatus( "fail" );
			logger.error( "Error sending the mail:" + e );
		}

		if( !Utils.isEmpty( mailVo ) && !Utils.isEmpty( mailVo.getMailStatus() ) && mailVo.getMailStatus().equalsIgnoreCase( "success" ) ){
			mailStatus = true;
		}

		return mailStatus;
	}

	/**
	 * Method to load the general info
	 * @param policyDataVO
	 * @param commonVO
	 */
	private PolicyDataVO loadGeneralInfo( PolicyDataVO policyDataVO, CommonVO commonVO ){
		policyDataVO.setCommonVO( commonVO );

		policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks( "GEN_INFO_COMMON_LOAD", policyDataVO );

		return policyDataVO;
	}

	/**
	 * Method to set the common request attributes
	 * @param policyDataVO
	 * @param commonVO 
	 * @param request
	 */
	private void setRequestAttribute( PolicyDataVO policyDataVO, CommonVO commonVO, HttpServletRequest request ){
		String lob = commonVO.getLob().toString();
		String fileName = request.getParameter( "fileName" );
		String loggenInLoc = Utils.getSingleValueAppConfig(com.rsaame.pas.util.AppConstants.DEPLOYED_LOCATION);
		HashMap<String, String> reportParams = new HashMap<String, String>();
		if( !Utils.isEmpty( fileName ) ){
			String[] fileNames = getFileNamesPolicy( fileName, commonVO, reportParams );
			request.getSession().setAttribute( com.Constant.CONST_FILENAMES, fileNames );
			request.getSession().setAttribute( com.Constant.CONST_REPORTPARAMS, reportParams );
		}

		if( !Utils.isEmpty( policyDataVO ) ){
			if( !Utils.isEmpty( policyDataVO.getGeneralInfo() ) && !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured() ) ){
				String lastName = policyDataVO.getGeneralInfo().getInsured().getLastName();
				if( !Utils.isEmpty( lastName ) && !lastName.equalsIgnoreCase( "null" ) ){
					request.setAttribute( "insuredName", policyDataVO.getGeneralInfo().getInsured().getFirstName() + " " + lastName );
				}
				else{
					request.setAttribute( "insuredName", policyDataVO.getGeneralInfo().getInsured().getFirstName() );
				}
				request.setAttribute( "toAddress", policyDataVO.getGeneralInfo().getInsured().getEmailId() );
			}

			if( !Utils.isEmpty( policyDataVO.getPremiumVO() ) ){
				request.setAttribute( "premium", Currency.getFormattedCurrency( policyDataVO.getPremiumVO().getPremiumAmt(), lob ) );
			}

			if( !Utils.isEmpty( policyDataVO.getScheme() ) && !Utils.isEmpty( policyDataVO.getScheme().getSchemeCode() ) ){
				request.setAttribute( "schemeCode", policyDataVO.getScheme().getSchemeCode() );
			}
			/* Start - If distribution channel is direct then set the customer care number if broker then set the broker address */
			if( !Utils.isEmpty( policyDataVO.getGeneralInfo() ) && !Utils.isEmpty( policyDataVO.getGeneralInfo().getSourceOfBus() ) ){
				int distributionChannel = policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel().intValue();
				//Added to support ExpatMoney Agent
				if( distributionChannel == SvcConstants.DIST_CHANNEL_DIRECT || distributionChannel == SvcConstants.DIST_CHANNEL_DIRECT_CALL_CENTER
						|| distributionChannel == SvcConstants.DIST_CHANNEL_DIRECT_WEB || distributionChannel == SvcConstants.DIST_CHANNEL_AGENT 
						|| distributionChannel == SvcConstants.DIST_CHANNEL_AFFINITY_DIRECT_AGENT || distributionChannel == SvcConstants.DIST_CHANNEL_AFFINITY_AGENT){
				
					if(policyDataVO.getCommonVO().getLocCode()==50){	
					request.setAttribute( "Number", Utils.getSingleValueAppConfig( "CONTACT_NUMBER_BAHRAIN" ) );
					request.setAttribute( "rsaUae", Utils.getSingleValueAppConfig( "RSA_BAHRAIN" ) );
					}else{
						request.setAttribute( "Number", Utils.getSingleValueAppConfig( "CONTACT_NUMBER" ) );
						request.setAttribute( "rsaUae", Utils.getSingleValueAppConfig( "RSA_UAE" ) );	
					}
					request.setAttribute( com.Constant.CONST_DIRECT, true );
					request.setAttribute (com.Constant.CONST_POLICYTYPE, policyDataVO.getPolicyType());
					if(loggenInLoc.equals("30")){
					   request.setAttribute ("policyName", SvcUtils.getLookUpDescription( "TARIFF", policyDataVO.getScheme().getSchemeCode().toString(), policyDataVO.getPolicyType().toString(), policyDataVO.getScheme().getTariffCode()));
					}
					if( AppUtils.isQuote( commonVO ) ){
						String subject = null;
						if(!loggenInLoc.equals("30")){
							subject = Utils.getSingleValueAppConfig( lob + "_QUOTE_SUBJECT" );
						}else{
							subject = Utils.getSingleValueAppConfig( lob + "_QUOTE_SUBJECT_OMAN" );
						}
						subject += " " + policyDataVO.getQuoteNo();
						request.setAttribute( com.Constant.CONST_SUBJECT, subject );
					}else{
						String subject = null;
						if(!loggenInLoc.equals("30")){
							subject = Utils.getSingleValueAppConfig( lob + "_POLICY_SUBJECT" );
						}else{
							subject = Utils.getSingleValueAppConfig( lob + "_POLICY_SUBJECT_OMAN" );
						}
						subject += " " + policyDataVO.getPolicyNo();
						request.setAttribute( com.Constant.CONST_SUBJECT, subject );
					}
				}
				else{
					int brokerCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
					List<Object[]> resultSet = DAOUtils.getSqlResultForPas( QueryConstants.BROKER_ADDRESS, brokerCode );
					if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
						StringBuffer brokerAddress = new StringBuffer();

						for( Object object : resultSet.get( 0 ) ){
							if( !Utils.isEmpty( object ) ){
								brokerAddress.append( String.valueOf( object ) ).append( "<br/>" );
							}
						}
						request.setAttribute( "brokerAddress", brokerAddress );
					}
					if( AppUtils.isQuote( commonVO ) ){
						String subject = null;
						if(!loggenInLoc.equals("30")){
							subject = Utils.getSingleValueAppConfig( lob + "_BROKER_QUOTE_SUBJECT" );
						}else{
							subject = Utils.getSingleValueAppConfig( lob + "_BROKER_QUOTE_SUBJECT_OMAN" );
						}
						subject += " " + policyDataVO.getQuoteNo();
						request.setAttribute( com.Constant.CONST_SUBJECT, subject );
					}else{
						String subject = null;
						if(!loggenInLoc.equals("30")){
							subject = Utils.getSingleValueAppConfig( lob + "_BROKER_POLICY_SUBJECT" );
						}else{
							subject = Utils.getSingleValueAppConfig( lob + "_BROKER_POLICY_SUBJECT_OMAN" );
						}
						subject += " " + policyDataVO.getPolicyNo();
						request.setAttribute( com.Constant.CONST_SUBJECT, subject );
					}
					request.setAttribute( com.Constant.CONST_DIRECT, false );
				}
			}
			/* End - If distribution channel is direct then set the customer care number if broker then set the broker address */

			if( !Utils.isEmpty( commonVO ) ){
				
				request.setAttribute("emailLocCode",Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));
				
				if( AppUtils.isQuote( commonVO ) ){
					request.setAttribute( "quoteNumber", policyDataVO.getQuoteNo() );
					request.setAttribute( com.Constant.CONST_TEMPLATE, Utils.getSingleValueAppConfig( lob + "_QUOTE_TEMPLATE_JSP" ) );
				}
				else{

					if( AppUtils.isEndorsed( commonVO ) ){
						request.setAttribute( com.Constant.CONST_TEMPLATE, Utils.getSingleValueAppConfig( lob + "_ENDORSEMENT_TEMPLATE_JSP" ) );
					}
					else{
						request.setAttribute( com.Constant.CONST_TEMPLATE, Utils.getSingleValueAppConfig( lob + "_POLICY_TEMPLATE_JSP" ) );
					}
				}
			}
		}

	}

	/**
	 * Method to get the formatted date
	 * @param date
	 * @return
	 */
	private String getFormattedDate( Date date ){
		DateFormat dateFormat = new SimpleDateFormat( com.Constant.CONST_DD_MMM_YYYY );

		return dateFormat.format( date );
	}

	/**
	 * Method to convert string to date
	 * @param dateString
	 * @return
	 */
	private Date convertStringToDate( String dateString ){

		DateFormat formatter;
		Date date;
		formatter = new SimpleDateFormat( "dd/MMM/yyyy" );
		try{
			date = (Date) formatter.parse( dateString );
		}
		catch( ParseException e ){
			BusinessException businessExcp = new BusinessException( "mail.error", null, e.getMessage() );
			throw businessExcp;

		}

		return date;
	}
	
	//changes-HomeRevamp#7.1
	private boolean checkPolIssueDate(HomeInsuranceVO homeInsuranceVO) throws ParseException {
		
		boolean PoliyWordingupdateDate = false;
		//String d2 = Utils.getSingleValueAppConfig("PolicyWordingupdateDate");
		String d2=(String)SvcUtils.populateAEDDt();
		String defaultDateFormat = Utils.getSingleValueAppConfig("DEFAULT_DATE_FORMAT"); //MM/dd/yyyy
		//SimpleDateFormat s2 = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat s2 = new SimpleDateFormat(defaultDateFormat);
		Long QuoteNum = homeInsuranceVO.getQuoteNo();
		Boolean isQuote=homeInsuranceVO.getIsQuote();
		Long endtId=homeInsuranceVO.getEndtId();
		if(Utils.isEmpty(QuoteNum)){
			QuoteNum=homeInsuranceVO.getCommonVO().getQuoteNo();
			
            endtId=homeInsuranceVO.getCommonVO().getEndtId();
		}
		if(Utils.isEmpty(isQuote)){
			isQuote=homeInsuranceVO.getCommonVO().getIsQuote();
		}
			
		String polIssueDt = null;
		if(!Utils.isEmpty(QuoteNum)){
			//polIssueDt = DAOUtils.getPolIssueDate(QuoteNum, isQuote, endtId);
			//polIssueDt = DAOUtils.getPolModOrPrepdateDate(QuoteNum, isQuote, endtId);
			polIssueDt=SvcUtils.populatePolEDt(QuoteNum);
		}
		//Date polIssueDt = DAOUtils.getIssueDateForCovers(QuoteNum);
		 
		if(!Utils.isEmpty(polIssueDt)){
			logger.debug("polIssueDt "+polIssueDt);
		Date prodDt = s2.parse(d2);
		if(s2.parse(polIssueDt).after(prodDt)){
			PoliyWordingupdateDate = true;
		}
		}
		
		return PoliyWordingupdateDate;
}
//changes-HomeRevamp#7.1
}
