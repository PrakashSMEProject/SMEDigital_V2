package com.rsaame.pas.policy.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnTask;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 *DAO class which used for converting quote into policy by using a stored procedure separately for SBS and Home/Travel
 *also it has subsequent method which will update the status(for converted to policy, Quote status is 7) for quote tables. 
 *
 *Since Phase 1
 */
public class ConvertToPolicyDAO extends BaseDBDAO implements IConvertToPolicyDAO{

	private static final Logger LOGGER = Logger.getLogger( ConvertToPolicyDAO.class );
	private final static String CLIENT = "AIC";
	private final static Byte TSK_CONVERTED_TO_POL = 4;
	private final static Byte QUO_CONVERTED_TO_POL = 7;
	private final static Short REN_DOC_CODE = 6;

	/**
	 * Method SBS specific which will calls the procedure for converting quote into the policy.
	 * and we will set the generated policy id and policy number into the PolicyVO which will return for future reference.
	 */
	public BaseVO getPolicyNumber( BaseVO baseVO ){
		
		LOGGER.info("Entered ConvertToPolicyDAO.getPolicyNumber method.");
		
		PolicyVO policyVO = (PolicyVO) baseVO;
		PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean( "convertToPolSTProc" );
		try{
			LOGGER.debug( "Calling convert to policy proc with input: " + "Linking id: " + policyVO.getPolLinkingId() + "client code: " + CLIENT + "user id: "
					+ SvcUtils.getUserId( policyVO ) + "pol type: " + Integer.parseInt( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) ) );
			
			Map results = sp.call( policyVO.getPolLinkingId(), CLIENT, SvcUtils.getUserId( policyVO ), Integer.parseInt( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) ) );
			Long policyNo = Long.parseLong( results.get( "AO_POL_NO" ).toString() );
			String caoncatPolicyNo = results.get( "P_CONC_POL_NO" ).toString();
			
			LOGGER.info("After stored Proc execution, policyNo:"+policyNo + " ConcatPolicyNo: "+caoncatPolicyNo);
			
			policyVO.setPolicyNo( policyNo );
			policyVO.setConcatPolicyNo( caoncatPolicyNo );
			//after convert to policy the endtid available in polContext should be zero - for print policy doc(freezone cert)
			policyVO.setEndtId(SvcConstants.ZERO);
			LOGGER.info("Before updating TASK, QUOTE");
			updateTask( policyVO );
			updateQuote( policyVO );
			LOGGER.info("TASK, QUOTE updation completes.");
		}
		catch( DataAccessException e ){
			throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc." );
		}
		return policyVO;
	}

	/**
	 * Method Home and Travel (Phase 3) specific which will calls the procedure for converting quote into the policy.
	 * and we will set the generated policy id and policy number into the PolicyVO which will return for future reference.
	 */
	public BaseVO getPolicyNumberForMonoline( BaseVO baseVO ){
		LOGGER.info("Entered ConvertToPolicyDAO:getPolicyNumberForMonoline method");
		
		CommonVO commonVO = (CommonVO) baseVO;
		PASStoredProcedure sp = null;
		Map results = null;
		Long policyNo =null;
		String caoncatPolicyNo =null;
		if(!Utils.isEmpty( commonVO.getLob() ) && (( commonVO.getLob().equals( LOB.HOME )) || commonVO.getLob().equals( LOB.TRAVEL )))
		{
			LOGGER.info("Returning relevant stored procedure if LOB is either HOME or TRAVEL");
			sp = (PASStoredProcedure) Utils.getBean( isHomeLob(commonVO)?"convertToPolSTProcHOME":"convertToPolSTProcTRAVEL" );
		}
		else if(!Utils.isEmpty( commonVO.getLob() ) )
		{
			LOGGER.info("Returning relevant stored procedure if LOB is other than HOME or TRAVEL");
			sp = (PASStoredProcedure) Utils.getBean( "convertToPolSTProcMONOLINE");
		}
		
		try{
			if(!Utils.isEmpty( sp )) {
				if(commonVO.getLoggedInUser().getUserId() == null) {
					LOGGER.info("Before executing stored procedure for an LOB, policyId: "+commonVO.getPolicyId()+" ,client:"+CLIENT+", userId:"+((UserProfile )commonVO.getLoggedInUser()).getRsaUser().getUserId());
					results = sp.call( commonVO.getPolicyId(), CLIENT,((UserProfile )commonVO.getLoggedInUser()).getRsaUser().getUserId() );
				}else if(commonVO.getLoggedInUser().getUserId() == ((UserProfile )commonVO.getLoggedInUser()).getRsaUser().getUserId().toString()) {
					LOGGER.info("Before executing stored procedure for an LOB, policyId: "+commonVO.getPolicyId()+" ,client:"+CLIENT+", userId:"+((UserProfile )commonVO.getLoggedInUser()).getRsaUser().getUserId());
					results = sp.call( commonVO.getPolicyId(), CLIENT,((UserProfile )commonVO.getLoggedInUser()).getRsaUser().getUserId() );
				}else {
					LOGGER.info("Before executing stored procedure for an LOB, policyId: "+commonVO.getPolicyId()+" ,client:"+CLIENT+", userId:"+commonVO.getLoggedInUser().getUserId());
					results = sp.call( commonVO.getPolicyId(), CLIENT,commonVO.getLoggedInUser().getUserId());
				}
			}
			/*Added try and catch block to avoid null pointer , sonar violation fix on 9-10-2017*/
			try{
				 policyNo = Long.parseLong( results.get( "AO_POL_NO" ).toString() );
			     caoncatPolicyNo = results.get( "P_CONC_POL_NO" ).toString();
				//Long policyId = Long.parseLong( results.get( "PO_POLICY_ID" ).toString() );
			}catch (NullPointerException e) {
				LOGGER.debug("The result of the stored procedure is null");
			}
		
			
			LOGGER.info("After stored Proc execution, PolicyNo:"+ policyNo + " ConcPolicyNo:"+caoncatPolicyNo);
			LOGGER.info("Before updating QUOTE, TASK");
			updateQuote( commonVO );
			updateTask( commonVO );
			LOGGER.info("QUOTE, TASK updation completes.");
			
			//commonVO.setPolicyId( policyId );
			commonVO.setPolicyNo( policyNo );
			commonVO.setConcatPolicyNo( caoncatPolicyNo );
	
		}
		catch( DataAccessException e ){
			throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc." );
		}
		return commonVO;
	}
	
	/**
	 * Method used for updating the status of the Quotation's task entry in TASK table.
	 * And we are setting the COnvert to policy Status(7)
	 */
	private void updateTask( BaseVO baseVO ){
		LOGGER.info("Entered ConvertToPolicyDAO.updateTask method");
		String documentId = null;
		
		if(baseVO instanceof CommonVO){
			 documentId = SvcConstants.zeroVal + "-" + ((CommonVO)baseVO).getEndtId()+"-"+((CommonVO)baseVO).getQuoteNo();
		
		}else if(baseVO instanceof PolicyVO){
			 documentId = ((PolicyVO)baseVO).getPolLinkingId() + "-" + ((PolicyVO)baseVO).getEndtId()+"-"+((PolicyVO)baseVO).getQuoteNo();
		}
		
		LOGGER.info("Fetching taskList based on document ID and merging one by one - starts");
		List<TTrnTask> taskList = getHibernateTemplate().find( "from TTrnTask where tskDocumentId=?", documentId );
		if( !taskList.isEmpty() ){
			for( TTrnTask task : taskList ){
				task.setTskStatus( TSK_CONVERTED_TO_POL );
				getHibernateTemplate().merge( task );
			}
		}
		LOGGER.info("Exiting ConvertToPolicyDAO.updateTask method task merging one by one - completes.");

	}

	/**
	 * Method used for updating the Policy status to 7 for convert to policy status.
	 * This will be done for entry in the quote table.
	 * 
	 */
	private void updateQuote( BaseVO baseVO ){
		LOGGER.info("Entered ConvertToPolicyDAO:updateQuote method");
		List<TTrnPolicyQuo> policyQuoList = null;
		
		LOGGER.info("Fetching policyQuote list");
		/*
		 * Fetch all valid records based on Expiry date and update status to Converted to policy.
		 */
		if(baseVO instanceof CommonVO){
			policyQuoList = getHibernateTemplate().find( "from TTrnPolicyQuo ttrnPol where ttrnPol.id.polPolicyId=? and ttrnPol.polValidityExpiryDate=?",
					((CommonVO)baseVO).getPolicyId(), SvcConstants.EXP_DATE );
		
		}else if(baseVO instanceof PolicyVO){
			policyQuoList = getHibernateTemplate().find( "from TTrnPolicyQuo ttrnPol where ttrnPol.polLinkingId=? and ttrnPol.polValidityExpiryDate=?"
					, ((PolicyVO)baseVO).getPolLinkingId(), SvcConstants.EXP_DATE);
		}
			
		LOGGER.info("Fetching policyQuote list completes, Merging policyQuotes one by one, starts");
		if( !Utils.isEmpty( policyQuoList ) && !policyQuoList.isEmpty() ){
			for( TTrnPolicyQuo policyQuo : policyQuoList ){
				policyQuo.setPolStatus( QUO_CONVERTED_TO_POL );
				//policyQuo.setPolPrintDate( policyVO.getNewValidityStartDate());
				getHibernateTemplate().merge( policyQuo );
			}
		}
		LOGGER.info("Exiting ConvertToPolicyDAO:updateQuote method, after merging policyQuotes one by one.");

	}

	public BaseVO isRenewalQuote( BaseVO baseVO ){
		DataHolderVO<Long> holderVO = (DataHolderVO<Long>) baseVO;
		Long polLinkingId = holderVO.getData();
		DataHolderVO<Boolean> renQuote = new DataHolderVO<Boolean>();
		renQuote.setData( DAOUtils.isRenewalQuote( polLinkingId, getHibernateTemplate() ) );
		return renQuote;
	}

	/**
	 * Added for Phase 3.
	 * Method will return true if the current LOB is HOME or else false.
	 * 
	 * @param commonVO
	 * @return
	 */
	private boolean isHomeLob( CommonVO commonVO ){
		if(commonVO.getLob().equals( LOB.HOME )){
			return true;
		}
		return false;
	}

	@Override
	public BaseVO isRenewalQuoteForHomeAndTravel(BaseVO baseVO) {
		DataHolderVO<Long> holderVO = (DataHolderVO<Long>) baseVO;
		Long policyId = holderVO.getData();
		DataHolderVO<Boolean> renQuote = new DataHolderVO<Boolean>();
		renQuote.setData( DAOUtils.isRenewalQuoteForHomeAndTravel( policyId, getHibernateTemplate() ) );
		return renQuote;
	}
}
