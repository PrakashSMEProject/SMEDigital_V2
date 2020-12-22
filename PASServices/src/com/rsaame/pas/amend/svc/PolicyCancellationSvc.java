package com.rsaame.pas.amend.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.List;
import com.rsaame.pas.pojo.mapper.EndorsementVOToTTrnEndorsementDetails;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;

public class PolicyCancellationSvc extends BaseService{

	private IPolicyCancellationDao policyCancellationDao;
	private static final Logger LOGGER = Logger.getLogger( PolicyCancellationSvc.class );
	
	public void setPolicyCancellationDao(
			IPolicyCancellationDao policyCancellationDao) {
		this.policyCancellationDao = policyCancellationDao;
	}
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		
		
		BaseVO returnValue = null;
		if( "getCancelPolRefundPremium".equals( methodName ) ){
			returnValue = getCancelPolRefundPremium( (BaseVO) args[ 0 ] );
		}   else if("processCancelPolicy".equals( methodName ) ) {
			returnValue = processCancelPolicy( (BaseVO) args[ 0 ] );
		}	 else if("getEndorsementsForCancelPolicy".equals( methodName ) ) {
				returnValue = getEndorsementsForCancelPolicy( (BaseVO) args[ 0 ] );
		}		
			return returnValue;
	}
	


	/**
	 * @param baseVO
	 * @return
	 */
	private BaseVO getCancelPolRefundPremium(BaseVO baseVO) {
		return policyCancellationDao.getCancelPolRefundPremium(baseVO);
	}
	
	/**
	 * @param baseVO
	 * @return
	 */
	private BaseVO processCancelPolicy( BaseVO baseVO ){
		return policyCancellationDao.processCancelPolicy( baseVO );
	}
	
	private BaseVO getEndorsementsForCancelPolicy( BaseVO baseVO ){
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
		 List<EndorsmentVO> list=policyCancellationDao.getEndorsementsForCancelPolicy( policyDataVO );
		 return list.get( 0 );
	}

}
