package com.rsaame.pas.renewals.scheduler;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.kaizen.vo.DefaultSchedulerUser;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnRenewalBatchEplatform;
import com.rsaame.pas.quote.scheduler.svc.QuoteExpirySchedulerSvc;
import com.rsaame.pas.renewals.dao.IRenewalsDAO;
import com.rsaame.pas.renewals.ui.RenewalPolicyDetailCommon;
import com.rsaame.pas.renewals.ui.RenewalPolicyDetails;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.cmn.CommonVO;
/**
 * 
 * @author m1019860
 * This service will get executed once in a day and it will generate renewal Quotations for the policies  
 * submitted to T_TRN_RENEWAL_BATCH_EPLATFORM table.
 * Renewal Quotation should be effective only after all the steps on it are successful and it is either in status 'Active' or 'Soft stop'.
 * Steps in Quotation generation
 *   Sub Step 1: Quotation creation (Generate quotation number and copy all records from policy table to quo. table)
 *   Sub Step 2: Check and delete any vehicles marked as 'Total Loss' from the quotation
 *   Sub Step 3: Invoke rating engine and update quotation with premium details
 *   Sub Step 4: Invoke Rules engine with Policy details and Claim Details and update quotation status according to the result.
 *   Sub Step 5: Update the Quotation 'Effective date' in case if Quotation Generation and Rules/Rating execution didn't happen in a single try.
 */

public class RenewalBatchSchedulerSvc  {
	private final static Logger LOGGER = Logger.getLogger(RenewalBatchSchedulerSvc.class);
	private final static short NOT_PROCESSED = 0;
	private final static short QUOTE_CREATED =1;
	private final static short RATING_EXECUTED =2;
	private final static short RULES_EXECUTED =3;	
	private final static short STATUS_UPDATE = 4 ;
	private final static short ZEROVAL = 0;	
	private HibernateTemplate hibernateTemplate;

	private RenewalPolicyDetailCommon renewalPolicyDetailCommon;
	private IRenewalsDAO renewalsDAO;
	boolean quoteCreated = false;
	private boolean ratingSuccess = false;
	private boolean rulesSuccess = false;
	boolean statusUpdateSuccess = false;
	String renRemarks = null;
	private boolean issueQuoteSuccess = false;
	//private String renRemarks = null;
	
	public void setRenewalsDAO(IRenewalsDAO renewalsDAO) { 
		this.renewalsDAO = renewalsDAO;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	public void run() {
		LOGGER.debug(":::::::: Renewal Batch execution Method: Start:::::::::::");
		DataHolderVO<Object []> renewalDataVO = new DataHolderVO<Object[]>();
		boolean ispolicyRenewed = false;
        List renewalBatchList = null;
         try {
	            // FETCH POLICIES TO BE RENEWED
	            renewalBatchList = (List)renewalsDAO.getRenewalBatch();
	            if(renewalBatchList != null) {
	            	LOGGER.debug("Renewal Batch Size::"+ renewalBatchList.size());
	            	System.out.println("Renewal Batch Size::"+ renewalBatchList.size());
	            	System.out.println("RenewalBatchSchedulerSvc : The current thread is ------------------------------------------------------"+Thread.currentThread().getName());
	            	// ITERATE THRU THE POLICIES TO BE RENEWED
	                Iterator iterRenewalBatch = renewalBatchList.iterator();                
	                	
	                while(iterRenewalBatch.hasNext()) {
	                	TTrnRenewalBatchEplatform policyToBeRenewed = (TTrnRenewalBatchEplatform)iterRenewalBatch.next();
	                	Long renLinkingID = null;
	                	Long originalLinkingId = null;
	                	Long renQuotationNo = null;
	                	Short lastSuccessfulStep = policyToBeRenewed.getLastExecutedStep();  
	                	ispolicyRenewed = checkIfpolicyIsRenewed(policyToBeRenewed.getPolicyId());
	                	// This is a retry. Fetch the details of the Quotation created in the previous try
	                	if (lastSuccessfulStep.shortValue() != NOT_PROCESSED && !ispolicyRenewed) 
	                	 {
	                		TTrnPolicyQuo tTrnPolicyQuo = (TTrnPolicyQuo) getHibernateTemplate().find("from TTrnPolicyQuo where polQuotationNo= ?", policyToBeRenewed.getRenQuotationNo()).get(0);
	                		renQuotationNo = policyToBeRenewed.getRenQuotationNo();
	                		renLinkingID = tTrnPolicyQuo.getPolLinkingId();
	                		//renPolicyId = tTrnPolicyQuo.getId().getPolicyId();
	                		if(policyToBeRenewed.getPolLinkingId()==ZEROVAL) {
	                			Object quoteDetails[] = new Object[12];
	                			quoteDetails[0] = renQuotationNo;
	                			if(!Utils.isEmpty(tTrnPolicyQuo)) {
	                				quoteDetails[1] = tTrnPolicyQuo.getId().getPolicyId();
	                				quoteDetails[2] = String.valueOf(tTrnPolicyQuo.getPolLocationCode());
	                				quoteDetails[3] = String.valueOf(tTrnPolicyQuo.getPolStatus());
	                				quoteDetails[4] = tTrnPolicyQuo.getPolEffectiveDate();
	                				quoteDetails[5] = tTrnPolicyQuo.getPolEndtNo();
	                				quoteDetails[6] = String.valueOf(tTrnPolicyQuo.getPolDocumentCode());
	                				quoteDetails[7] = tTrnPolicyQuo.getPolValidityStartDate();
	                				quoteDetails[8] = tTrnPolicyQuo.getPolPolicyType().toString();
	                				quoteDetails[9] = String.valueOf(tTrnPolicyQuo.getPolUserId());
	                				quoteDetails[10] = tTrnPolicyQuo.getPolPolicyNo();
	                				quoteDetails[11] = tTrnPolicyQuo.getPolConcPolicyNo();
	                			}
	                			renewalDataVO.setData(quoteDetails);	
	                			
	                		}
	                	
	                	}
	                		
	                	LOGGER.debug("::::::: Processing Policy No::"+policyToBeRenewed.getPolPolicyNo() + "::LAst succ. step" + lastSuccessfulStep.shortValue());
	                	try {
	                		/* Resume the Renewal quotation generation process from the last failed Step.class Do not add 'break' as the switch
	                		 *  statement should spill over to the next case in case of successful execution of previous step.
	                		 */	  
	                		
	                		if(policyToBeRenewed.getPolLinkingId()==ZEROVAL && !ispolicyRenewed) {	
	                			switch(lastSuccessfulStep.shortValue())
		                		 {
		                			case NOT_PROCESSED:  // Generate Renewal Quote
				                		try
						                {
				                			Session session = getHibernateTemplate().getSessionFactory().openSession();
				                			String sql = "SELECT * FROM T_TRN_POLICY WHERE POL_POLICY_ID = :policyId";		
				                			SQLQuery query = session.createSQLQuery(sql);	
				                			query.addEntity(TTrnPolicyQuo.class);
				                			query.setParameter("policyId", policyToBeRenewed.getPolicyId());
				                			List result = query.list();
				                			TTrnPolicyQuo tTrnPolicy = (TTrnPolicyQuo) result.get(0);
				                			session.close();
				                			
				                			short policyType = tTrnPolicy.getPolPolicyType();
						                	renewalDataVO = (DataHolderVO<Object[]>)generateCommonRenewalQuotation(policyToBeRenewed.getPolicyId(),policyToBeRenewed.getRequesterId(), policyType);
						                	Object[] renData = renewalDataVO.getData();
						                	renQuotationNo = (Long)renData[0];
						                	if(renData.length > 2 && renData[2].equals("QuoteIssued")){
						                		break;
						                	}
						                }catch(Exception e)
						                {
						                	e.printStackTrace();
						                	if(!checkIfpolicyIsRenewed(policyToBeRenewed.getPolicyId())){
						                		updateCommonRenewalBatchRecordStatus(policyToBeRenewed.getPolicyId(),null,Short.valueOf(NOT_PROCESSED),"SE","Quotation creation failed");
						                	}
							                continue;
						                }
				                		
				                		
				                	case QUOTE_CREATED: // Invoke Rating engine	
				                		LOGGER.debug( "********call issueRenewalQuoteCommon method after generating the renewal quote********" );
				                		issueQuoteSuccess =  issueRenewalQuoteCommon(renewalDataVO,policyToBeRenewed.getRequesterId(),policyToBeRenewed.getPolicyId(),policyToBeRenewed.getApplication());
				                		if (!issueQuoteSuccess) {
				                			// Update the batch table with status and Remarks and continue with next record in the batch
				                			LOGGER.debug( "*****Going to update Renewal Batch Record status in case of issueing quote failed*****" );
				                			updateCommonRenewalBatchRecordStatus(policyToBeRenewed.getPolicyId(),renQuotationNo,Short.valueOf(QUOTE_CREATED),"SE","Issuing quote failed");				                				
				                			continue;
				                		}	
				                		break;
				                		//sonar fix
				        			default:
				        				break;
		                		}
		                		// If each case is executed successfully, update the quotation status as successful.
	                			updateCommonRenewalBatchRecordStatus(policyToBeRenewed.getPolicyId(),renQuotationNo,Short.valueOf(RULES_EXECUTED),"Y","Renewal Quote is successfully generated");
	                		}
	                		 /*Commented as part of fix of multiple quotes issue for one policy - Ticket Id - 141246 */
	                		/*else if (!ispolicyRenewed){*/
	                		else if(policyToBeRenewed.getPolLinkingId()!= ZEROVAL){
	                			switch(lastSuccessfulStep.shortValue())
		                		{
		                		case NOT_PROCESSED:  // Generate Renewal Quote
				                		{
				                			try
				                			{
				                			renewalDataVO = (DataHolderVO<Object[]>)generateRenewalQuotation(policyToBeRenewed.getPolLinkingId(),policyToBeRenewed.getRequesterId());
				                			Object[] renData = renewalDataVO.getData();
				                			renQuotationNo = (Long)renData[0];
				                			renLinkingID = (Long)renData[1];
				                			originalLinkingId = (Long)renData[2];
				                			quoteCreated = true;
			                			
			                			}catch(Exception e)
			                			{
			                				 e.printStackTrace();
			                				 /*Commented as part of fix of multiple quotes issue for one policy - Ticket Id - 141246 */
			                				 /*if(!checkIfpolicyIsRenewed(policyToBeRenewed.getPolicyId())){
			                					 updateRenewalBatchRecordStatus(policyToBeRenewed.getPolLinkingId(),null,Short.valueOf(NOT_PROCESSED),"SE","Quotation creation failed");
							                	}*/
			                				 updateRenewalBatchRecordStatus(policyToBeRenewed.getPolLinkingId(),null,Short.valueOf(NOT_PROCESSED),"SE","Quotation creation failed");
				                			 continue;
			                			}
			                		}
				                		//sonar fix
		            			default:
		            				break;
		                		}
		                		// If each case is executed successfully, update the quotation status as successful.
		                		updateRenewalBatchRecordStatus(policyToBeRenewed.getPolLinkingId(),renQuotationNo,Short.valueOf(RULES_EXECUTED),"Y","Policy Renewed Successfully");
		                	}                		           		
		                		           		
	                	}catch (Exception e) {
	                		e.printStackTrace();
	                		LOGGER.debug("Exception occured while processing renewal for :" + policyToBeRenewed);	
	                	}
	                	
	                } // end while
	            } //end if
            }
         	catch (Exception  e) {
	            e.printStackTrace();	
	        }
	    LOGGER.debug(":::::::: Renewal Batch execution Method: End:::::::::::");
       }
	
	
	/**
	 *  This method generates the renewal quotation and update the quotation number onto policy and batch tables
	 * @param polLinkingID
	 * @return renLinkingId
	 */
	@SuppressWarnings("unchecked")
	public BaseVO generateRenewalQuotation(long polLinkingID,Integer userID) {
		
		LOGGER.debug("--------Generating RenewalQuotation Number");
		// Call Stored procedure to generate the renewal quote
		DataHolderVO<Object[]> input =  new DataHolderVO<Object[]>();
		Object inputData[] = new Object[3];
		inputData[0] = polLinkingID;
		inputData[1] = userID;
        inputData[2] = getDefaultSchedulerUserProfile();
        input.setData(inputData);
		DataHolderVO<Object[]> renewalDataVO  = (DataHolderVO<Object[]>) TaskExecutor.executeTasks("GENERATE_ONLINE_RENEWALS", input);
		Object[] renData = renewalDataVO.getData();
		Long quotationNo = (Long)renData[0];
		Long renLinkingId = (Long)renData[1];
		LOGGER.debug("-------- RenewalQuotation Number:"+ quotationNo + "Linking ID::" + renLinkingId);
		return renewalDataVO;
	}

	/*
	 * Method to execute the Rating Step and Update Premium on to the T_TRN_PREMIUM_QUO table
	 * @param Long renLinkingId
	 * @return boolean ratingSuccess
	 */
	
	public boolean executeRatingStep(Long renLinkingId,Integer userID) {
		LOGGER.debug("--------Executing Rating Step");
		boolean ratingSuccess= false;
		PolicyVO  polVO = null;
		try {
			RenewalPolicyDetails renPolicyDetails = new RenewalPolicyDetails();
			polVO= renPolicyDetails.createPolicyObject(renLinkingId);
			polVO = (PolicyVO)TaskExecutor.executeTasks("RENEWAL_RATING_EXECUTION", polVO);
			// Call service to update the premium and policy records with latest premium;
			DataHolderVO<Object[]> ratingInput =  new DataHolderVO<Object[]>();
			Object ratingInputData[] = new Object[2];
			ratingInputData[0] = polVO;
			ratingInputData[1] = userID;
			ratingInput.setData(ratingInputData);
			TaskExecutor.executeTasks("UPDATE_RENEWAL_PREMIUM", ratingInput); 
			ratingSuccess= true;
		} catch(Exception e) {
			LOGGER.debug("--------Rating Step Failed with an exception::");
			if(!Utils.isEmpty(polVO))
			updateRenewalQuotationStatus(polVO,Utils.getSingleValueAppConfig("QUOTE_SYSTEM_ERROR"));
			e.printStackTrace();
		}
		LOGGER.debug("--------Rating Step Successful");
		return ratingSuccess;
	}
	
	
	/* 
	 *  This method updates Renewal Batch Table with the status,Last executed step, remarks and quotation number for a renewal Batch Item
	 */
	public void updateRenewalBatchRecordStatus(long polLinkingID,Long renewalQuotationNo,short lastExecutedStep, String status, String Remarks ) {		
		LOGGER.debug("-------- updateRenewalBatchRecordStatus ");		
		TTrnRenewalBatchEplatform renewalBatchItem = (TTrnRenewalBatchEplatform) getHibernateTemplate().find("from TTrnRenewalBatchEplatform where polLinkingId= ?", polLinkingID).get(0);
		renewalBatchItem.setRenQuotationNo(renewalQuotationNo);
		renewalBatchItem.setLastExecutedStep(lastExecutedStep);
		renewalBatchItem.setRemarks(Remarks);
		renewalBatchItem.setLastProcessedDate(new Date());
		renewalBatchItem.setRenQuotationStatus(status);
		getHibernateTemplate().merge(renewalBatchItem);
		
	}
	/*
	 * Method to update the status of the renewal quotation to 'System Error'/'Soft Stop'/'Active'
	 */
	public void updateRenewalQuotationStatus(PolicyVO polVO,String status)
	 {
		LOGGER.debug("-------- update renewal quotation status ");		
		DataHolderVO<Object[]> input = new DataHolderVO<Object[]>();
		Object inputData[] = new Object[2];
		inputData[0] = polVO;
		inputData[1] = status;
		input.setData(inputData);
		TaskExecutor.executeTasks("UPDATE_RENEWAL_QUOTE_STATUS", input); 
	  
	}
	
	/*
	 * Method to update the status of the renewal quotation to 'System Error'/'Soft Stop'/'Active' for Home/Travel- Phase 3
	 */
	private void updateRenewalQuotationStatusCommon(PolicyDataVO policyDataVO,
			String status) {
		LOGGER.debug("******Inside updateRenewalQuotationStatusCommon******");		
		DataHolderVO<Object[]> input = new DataHolderVO<Object[]>();
		Object inputData[] = new Object[2];
		inputData[0] = policyDataVO;
		inputData[1] = status;
		input.setData(inputData);
		TaskExecutor.executeTasks("UPDATE_RENEWAL_QUOTE_STATUS_COMMON", input); 
		
	}
	
	/**
	 *  This method generates the renewal quotation and update the quotation number onto policy and batch tables for HOME/TRAVEL - Phase3
	 * @param polLinkingID
	 * @return renLinkingId
	 */
	@SuppressWarnings("unchecked")
	public BaseVO generateCommonRenewalQuotation(long policyID,Integer userID, Short policyType) {
		
		LOGGER.debug("--------Generating RenewalQuotation Number");
				
		// Call Stored procedure to generate the renewal quote
		DataHolderVO<Object[]> input =  new DataHolderVO<Object[]>();
		Object inputData[] = new Object[4];
		DataHolderVO<Object[]> renewalDataVO = null;
		inputData[0] = policyID;
		inputData[1] = userID;
		input.setData(inputData);
		if(SvcConstants.HOME_POL_TYPE.equals(policyType.toString()) || SvcConstants.SHORT_TRAVEL_POL_TYPE.equals(policyType.toString()) 
				|| SvcConstants.LONG_TRAVEL_POL_TYPE.equals(policyType.toString())){
			renewalDataVO  = (DataHolderVO<Object[]>) TaskExecutor.executeTasks("GENERATE_ONLINE_RENEWALS_COMMON", input);
		}
		else{
			UserProfile userProfile = new UserProfile();
			userProfile.setUserId(userID.toString());
			inputData[2] = renewalPolicyDetailCommon.createDefaultUser("RSA_USER_1");
			inputData[3] = policyType;
			DataHolderVO<HashMap<String, Object>> holderVO = (DataHolderVO<HashMap<String, Object>>) TaskExecutor.executeTasks("GENERATE_RENEWALS_MONOLINE", input);
			HashMap<String, Object> renewalData = holderVO.getData();		
			Long renQuoteNo = (Long) renewalData.get( "renewalQuoteNo" );
			Long renPolicyId = (Long) renewalData.get("policyId");
			renewalDataVO = new DataHolderVO<Object[]>();
			renewalDataVO.setData( new Object [] {renQuoteNo, renPolicyId, "QuoteIssued"} );
		}
		Object[] renData = renewalDataVO.getData();
		Long quotationNo = (Long)renData[0];
		Long renPolicyId = (Long)renData[1];
		LOGGER.debug("-------- RenewalQuotation Number:"+ quotationNo + "Policy ID::" + renPolicyId);
		return renewalDataVO;
	}
	
	/* 
	 *  This method updates Renewal Batch Table with the status,Last executed step, remarks and quotation number for a renewal Batch Item
	 */
	public void updateCommonRenewalBatchRecordStatus(long policyID,Long renewalQuotationNo,short lastExecutedStep, String status, String Remarks ) {		
		LOGGER.debug("-------- updateRenewalBatchRecordStatus ");		
		TTrnRenewalBatchEplatform renewalBatchItem = (TTrnRenewalBatchEplatform) getHibernateTemplate().find("from TTrnRenewalBatchEplatform where policyId= ?", policyID).get(0);
		renewalBatchItem.setRenQuotationNo(renewalQuotationNo);
		renewalBatchItem.setLastExecutedStep(lastExecutedStep);
		renewalBatchItem.setRemarks(Remarks);
		renewalBatchItem.setLastProcessedDate(new Date());
		renewalBatchItem.setRenQuotationStatus(status);
		getHibernateTemplate().merge(renewalBatchItem);
		
	}
	
	/* 
	 *  This method checks if the policy is already renewed or not.
	 */
	public boolean checkIfpolicyIsRenewed(long policyID) {		
		LOGGER.debug("-------- checkIfpolicyIsRenewed ");
		List<TTrnPolicyQuo> policyQuoList = null;
		getHibernateTemplate().flush();
		try {
			policyQuoList  = (List<TTrnPolicyQuo>) getHibernateTemplate().find("from TTrnPolicyQuo where polRefPolicyId= ? and polIssueHour =?", policyID,Integer.valueOf( Utils.getSingleValueAppConfig( "SBS_POLICY_ISSUE_HOUR" )));
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		if(!Utils.isEmpty( policyQuoList ) && !Utils.isEmpty( policyQuoList.get( 0 ) )    && !Utils.isEmpty( policyQuoList.get( 0 ).getPolQuotationNo() )){
			return true;
		}
		
		return false;
	}
	
	/*
	 * Method to execute the Rating Step and Update Premium on to the T_TRN_PREMIUM_QUO table
	 * @param Long renLinkingId
	 * @return boolean ratingSuccess
	 */
	
	//public boolean executeRatingStepForHomeAndTravel(Long renPolicyId,Integer userID) {
	
	private boolean issueRenewalQuoteCommon(
			DataHolderVO<Object[]> holderVO, Integer userId,Long policyId,String originApplication) {
		LOGGER.debug("******Inside issueRenewalQuoteCommon() method  *********");
		boolean issueQuote= false;
		PolicyDataVO  policyDataVO = new PolicyDataVO();		
		Object[] renData = holderVO.getData();		
		LOGGER.debug("******renQuotationNo = "+renData[0]);
		LOGGER.debug("******renPolicyId = "+renData[1]);
//		RenewalPolicyDetailCommon policyDetailCommon = new RenewalPolicyDetailCommon();
		try {		
			//policyDataVO= policyDetailCommon.createPolicyObject(renData);
//			policyDataVO= policyDetailCommon.createPolicyObject(renData,policyId,true,originApplication);
			policyDataVO = renewalPolicyDetailCommon.createPolicyObject(renData,policyId,true,originApplication);
			issueQuote= true;
			LOGGER.debug("*******Issuing Quote Successful*******");
			/*DataHolderVO<Object[]> claimInput =  new DataHolderVO<Object[]>();
			DataHolderVO<Object[]> input =  new DataHolderVO<Object[]>();
			Object claimInputData[] = new Object[3];		
			claimInputData[0] = policyId;
			claimInputData[1] = null;	
			claimInput.setData(claimInputData);
			DataHolderVO<Long> claimOutput = (DataHolderVO<Long>) TaskExecutor.executeTasks("GET_CLAIM_COUNT_COMMON", claimInput);
			Long claimCount = claimOutput.getData();
			
			if(claimCount>0) {
				claimInputData[0] = policyDataVO;
				claimInputData[1] = Utils.getSingleValueAppConfig("QUOTE_SOFT_STOP");
				input.setData(claimInputData);
				TaskExecutor.executeTasks("UPDATE_RENEWAL_QUOTE_STATUS_COMMON", input); 
			}*/
		}catch(Exception e) {
			
			LOGGER.debug("*******Issuing quote failed with an exception********");
			if( issueQuote == false ) {
				policyDataVO.setPolicyId((Long)renData[1]);
				CommonVO commonVO = new CommonVO();
				commonVO.setIsQuote(true);
				commonVO.setEndtId(Long.parseLong( AppConstants.zeroVal ) );
				policyDataVO.setCommonVO(commonVO);
				updateRenewalQuotationStatusCommon(policyDataVO,Utils.getSingleValueAppConfig("QUOTE_SYSTEM_ERROR"));
			}
			LOGGER.debug("****Exception occured during save the premium details or during updating the quote status "+e.getMessage());
			e.printStackTrace();
			
		}
		
		return issueQuote;	
	}
	
/*	private boolean executeRulesStep(final Long renLinkingID) {
		
		return true;
	}*/
	
	private UserProfile getDefaultSchedulerUserProfile() {
		GrantedAuthority [] grantedAuth = new GrantedAuthorityImpl[1];

        grantedAuth[0] = new GrantedAuthorityImpl(Utils.getSingleValueAppConfig( "RSA_PL_USER_1" ) );

        UserProfile userProfile = new UserProfile();

        DefaultSchedulerUser defaultUser = new DefaultSchedulerUser( "MISLIVE - RSA Dubai", "fcdbf28eb2e8cf3a1", true, grantedAuth , 2, 20, 0, 0, 0, 0, 0, 0, 0, "EMPLOYEE",

                17, "aaa", "aaa", "rrr@gmail.com", "111111111" );

        userProfile.setRsaUser( defaultUser );
	
        return userProfile;
	}
	public RenewalPolicyDetailCommon getRenewalPolicyDetailCommon() {
		return renewalPolicyDetailCommon;
	}
	public void setRenewalPolicyDetailCommon(
			RenewalPolicyDetailCommon renewalPolicyDetailCommon) {
		this.renewalPolicyDetailCommon = renewalPolicyDetailCommon;
	}
	
}
