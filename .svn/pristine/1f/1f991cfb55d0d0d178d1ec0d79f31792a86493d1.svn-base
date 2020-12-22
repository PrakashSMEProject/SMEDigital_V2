package com.rsaame.pas.policyAction.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.policy.dao.ICaptureComments;
import com.rsaame.pas.policyAction.dao.IPolicyActionCommonDAO;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;

public class PolicyActionCommonSvc extends BaseService{


	IPolicyActionCommonDAO policyActCommonDAO;
	ICaptureComments captureStatusDAO;


	@Override
	public Object invokeMethod( String methodName, Object... args ){
	
		BaseVO returnValue = null;

		if( "approveQuote".equals( methodName ) ){
			returnValue = approveQuote( (BaseVO) args[ 0 ] );
		}
		if( "rejectQuote".equals( methodName ) ){
			returnValue = rejectQuote( (BaseVO) args[ 0 ] );
		}
		if( "declineQuote".equals( methodName ) ){
			returnValue = declineQuote( (BaseVO) args[ 0 ] );
		}	
		return returnValue;
	}

	private BaseVO declineQuote( BaseVO baseVO ){
		captureComment(baseVO);	
		return policyActCommonDAO.declineQuote(baseVO);
		
	}


	private BaseVO rejectQuote( BaseVO baseVO ){
		captureComment(baseVO);	
		return policyActCommonDAO.rejectQuote(baseVO);
	}

	private BaseVO approveQuote( BaseVO baseVO ){
		PolicyCommentsHolder commentsHolder = (PolicyCommentsHolder)baseVO;
	if(!Utils.isEmpty(commentsHolder.getComments())){
			captureComment(baseVO);
			return baseVO;
		}
		else
			return policyActCommonDAO.approveQuote(baseVO);
	}


	public void setPolicyActCommonDAO(IPolicyActionCommonDAO policyActCommonDAO) {
		this.policyActCommonDAO = policyActCommonDAO;
	}
	
	
	
	public void setCaptureStatusDAO( ICaptureComments captureStatusDAO ){
		this.captureStatusDAO = captureStatusDAO;
	}

	private void captureComment( BaseVO baseVO ){
		PolicyCommentsHolder comHolder = (PolicyCommentsHolder)baseVO;
		if(!Utils.isEmpty( comHolder ) && !Utils.isEmpty( comHolder.getComments() ))
		{
			captureStatusDAO.storeComments( comHolder.getComments() ); //( "storeComments", comHolder.getComments() );
		}
		else
		{
			throw new BusinessException( "pas.quote.commentMandatory",null ,"Comments are mandatory during quote reject/decline" );
		}
		
	}
	

}
