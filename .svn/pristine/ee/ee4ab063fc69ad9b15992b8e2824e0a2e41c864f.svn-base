package com.rsaame.pas.endorse.svc;

import java.util.Date;
import java.util.Map;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.PolicyVO;

public class ExtendPolicySvc extends BaseService{

	private static final Logger logger = Logger.getLogger( ExtendPolicySvc.class );

	@Override
	public Object invokeMethod( String methodName, Object... args ){

		PolicyVO policyVO = (PolicyVO) args[ 0 ];
		if( methodName.equals( "invokPolExtensionProc" ) ){
			invokPolExtensionProc( policyVO );
		}else if( methodName.equals( "invokPoBOXAmendProc" ) ){
			invokPoBOXAmendProc( policyVO );
		}

		return null;
	}

	private void invokPolExtensionProc( PolicyVO policyVO ){

			PASStoredProcedure polExtnProc;

			polExtnProc = (PASStoredProcedure) Utils.getBean( ( policyVO.getIsQuote() ? "policyExtnProc_QUO" : "policyExtnProc_POL" ) );

			/*
			 * Fix for ePlatform 2.1 release. Correcting start date and end date being set
			 */
			// In case of Quote, the policy effective date is the start date. Hence passing the policy start date
			// In case of policy the start date is endt eff date (need to confirm with RSA)
			Date startDate = policyVO.getIsQuote()?policyVO.getScheme().getEffDate(): policyVO.getEndEffectiveDate();
			
			Date validityStartDate = policyVO.getValidityStartDate();
			
			if( !Utils.isEmpty( policyVO.getNewValidityStartDate() ) && policyVO.getNewValidityStartDate().after( policyVO.getValidityStartDate() ) ){
				validityStartDate =  policyVO.getNewValidityStartDate(); 
			}
			
			Map results = polExtnProc.call( policyVO.getPolLinkingId(), policyVO.getNewEndtId(), policyVO.getPolExpiryDate(),startDate, validityStartDate
					,SvcUtils.getUserId( policyVO ) );

			if( Utils.isEmpty( results ) ){
				logger.info( "The result of the stored procedure is null" );
			}
			else{
				logger.info( "PO_VSD_DATE = " + results.get( "PO_VSD_DATE" ) + "PO_ERR_TEXT = " + results.get( "PO_ERR_TEXT" ) );
			}

			policyVO.getPremiumVO().setPolExtenUpdateRequired( Boolean.FALSE );
	}
	
	private void invokPoBOXAmendProc( PolicyVO policyVO ){

		PASStoredProcedure polExtnProc;

		polExtnProc = (PASStoredProcedure) Utils.getBean( ( policyVO.getIsQuote() ? "poBOXAmendProc_QUO" : "poBOXAmendProc_POL" ) );
		String type = policyVO.getIsQuote() ? "Q" : "P";

		Map results = null ;
		
		Date validityStartDate = policyVO.getValidityStartDate();
		
		if( !Utils.isEmpty( policyVO.getNewValidityStartDate() ) && policyVO.getNewValidityStartDate().after( policyVO.getValidityStartDate() ) ){
			validityStartDate =  policyVO.getNewValidityStartDate(); 
		}
		if(!Utils.isEmpty(policyVO.getNewEndtId())){
			 results = polExtnProc.call( policyVO.getPolLinkingId(), policyVO.getNewEndtId(), validityStartDate
					,SvcUtils.getUserId( policyVO ),policyVO.getGeneralInfo().getInsured().getAddress().getPoBox(), type);
		}

		if( Utils.isEmpty( results ) ){
			logger.info( "The result of the stored procedure is null" );
		}
		else{
			logger.info( "PO_VSD_DATE = " + results.get( "PO_VSD_DATE" ) + "PO_ERR_TEXT = " + results.get( "PO_ERR_TEXT" ) );
		}	
	}
	
}
