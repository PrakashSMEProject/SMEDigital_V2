/**
 * 
 */
package com.rsaame.pas.policyAction.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.policy.svc.CaptureCommentsService;
import com.rsaame.pas.policyAction.dao.IPolicyActionDAO;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;

/**
 * @author m1014644
 *
 */
public class PolicyActionSvc extends BaseService{

	IPolicyActionDAO policyActDAO;
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod( String methodName, Object... args ){
	
		BaseVO returnValue = null;
		if( "issueQuote".equals( methodName ) ){
			returnValue = issueQuote( (BaseVO) args[ 0 ] );
		}
		if( "approveQuote".equals( methodName ) ){
			returnValue = approveQuote( (BaseVO) args[ 0 ] );
		}
		if( "rejectQuote".equals( methodName ) ){
			returnValue = rejectQuote( (BaseVO) args[ 0 ] );
		}
		if( "declineQuote".equals( methodName ) ){
			returnValue = declineQuote( (BaseVO) args[ 0 ] );
		}
		if( "storePolComments".equals(methodName) ){
			 captureComment( (BaseVO) args[ 0 ] );
			 returnValue = (BaseVO) args[ 0 ];
		}
		if( "closeSection".equals(methodName) ){
			 captureComment( (BaseVO) args[ 0 ] );
			 returnValue = (BaseVO) args[ 0 ];
		}
		
		if( "saveEndText".equals(methodName) ){
			 captureComment( (BaseVO) args[ 0 ] );
			 returnValue = (BaseVO) args[ 0 ];
		}
		
		
		if( "cancelEnd".equals(methodName) ){
			 captureComment( (BaseVO) args[ 0 ] );
			 returnValue = (BaseVO) args[ 0 ];
		}
		if( "getBrAccStatus".equals(methodName) ){
			 returnValue = getBrAccStatus( (BaseVO) args[ 0 ] );
		}
		if( "updateDiscOnDemandReferral".equals(methodName) ){
			 updateDiscOnDemandReferral( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}
	/*
	 * Fetch broker status to check if broker account locked.
	 */
	private BaseVO getBrAccStatus( BaseVO baseVO ){
		return policyActDAO.getBrAccStatus(baseVO);
	}

	private BaseVO declineQuote( BaseVO baseVO ){
		captureComment(baseVO);	
		return policyActDAO.declineQuote(baseVO);
		
	}


	private BaseVO rejectQuote( BaseVO baseVO ){
		captureComment(baseVO);	
		return policyActDAO.rejectQuote(baseVO);
	}

	private BaseVO approveQuote( BaseVO baseVO ){
		captureComment(baseVO);	
		return policyActDAO.approveQuote(baseVO);
	}

	private BaseVO issueQuote( BaseVO baseVO ){
		return policyActDAO.issueQuote(baseVO);
	}

	public void updateDiscOnDemandReferral( BaseVO baseVO ){
		policyActDAO.updateDiscOnDemandReferral(baseVO);
	}
	/**
	 * @param policyActDAO the policyActDAO to set
	 */
	public void setPolicyActDAO( IPolicyActionDAO policyActDAO ){
		this.policyActDAO = policyActDAO;
	}
	
	private void captureComment( BaseVO baseVO ){
		PolicyCommentsHolder comHolder = (PolicyCommentsHolder)baseVO;
		CaptureCommentsService captureComments = null;
		if(!Utils.isEmpty( comHolder ) && !Utils.isEmpty( comHolder.getComments() ))
		{
			/*if(!Utils.isEmpty( comHolder.getCommonDetails() ) && !comHolder.getCommonDetails().getIsQuote()){
				captureComments =  (CaptureCommentsService) Utils.getBean( "captureComments_POL" );
			}else{
				captureComments =  (CaptureCommentsService) Utils.getBean( "captureComments" );
			}*/
					
			if((!Utils.isEmpty( comHolder.getCommonDetails() ) && !comHolder.getCommonDetails().getIsQuote()) ||  
				(!Utils.isEmpty( comHolder.getPolicyDetails()) && ! comHolder.getPolicyDetails().getIsQuote())	){
				captureComments =  (CaptureCommentsService) Utils.getBean( "captureComments_POL" );
				
			}else{
				captureComments =  (CaptureCommentsService) Utils.getBean( "captureComments" );
			}
			
			captureComments.invokeMethod( "storeComments", comHolder.getComments() );
		}
		else
		{
			throw new BusinessException( "pas.quote.commentMandatory",null ,"Comments are mandatory during quote reject/decline/aprrove" );
		}
		
	}
	
}
