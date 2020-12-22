package com.rsaame.pas.insured.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.insured.dao.IInsuredDetailsDAO;

public class InsuredDetailsSvc extends BaseService{

	IInsuredDetailsDAO insuredDetailsDAO;

	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;

		if( "fetchTMasInsured".equals( methodName ) ){
			returnValue = fetchTMasInsured( (BaseVO) args[ 0 ] );
		}
		if( "fetchCommonTmasInsured".equals( methodName ) ){
			returnValue = fetchCommonTmasInsured( (BaseVO) args[ 0 ] );
		}
		if( "saveTMasInsured".equals( methodName ) ){
			returnValue = saveTMasInsured( (BaseVO) args[ 0 ] );
		}
		if( "viewInsuredComments".equals( methodName ) ){
			returnValue = viewInsuredComments( (BaseVO) args[ 0 ] );
		}
		if( "viewInsuredActiveTransactions".equals( methodName ) ){
			returnValue = viewInsuredActiveTransactions( (BaseVO) args[ 0 ] );
		}
		if( "viewTransAccountHistory".equals( methodName ) ){
			returnValue = viewTransAccountHistory( (BaseVO) args[ 0 ] );
		}
		if( "viewTransClaimsHistory".equals( methodName ) ){
			returnValue = viewTransClaimsHistory( (BaseVO) args[ 0 ] );
		}
		if( "saveOrUpdateTmasInsured".equals( methodName ) ){
			returnValue = saveOrUpdateTmasInsured( (BaseVO) args[ 0 ] );
		}
		if( "compareCommonTmasInsuredForInsuredCheck".equals( methodName )){
			returnValue = compareCommonTmasInsuredForInsuredCheck( (BaseVO) args[ 0 ] );
		}
		else if( "compareCommonTmasInsured".equals( methodName ) ){
			returnValue = compareCommonTmasInsured( (BaseVO) args[ 0 ] );
		}
		
		return returnValue;
	}

	private BaseVO compareCommonTmasInsured(BaseVO baseVO) {
		
		return insuredDetailsDAO.compareCommonTmasInsured(baseVO);
	}

	private BaseVO fetchTMasInsured( BaseVO baseVO ){
		BaseVO baseVOOutput = insuredDetailsDAO.fetchTmasInsured( baseVO );
		return baseVOOutput;
	}
	private BaseVO fetchCommonTmasInsured( BaseVO baseVO ){
		BaseVO baseVOOutput = insuredDetailsDAO.fetchCommonTmasInsured( baseVO );
		return baseVOOutput;
	}

	private BaseVO saveTMasInsured( BaseVO baseVO ){
		BaseVO baseVOOutput = insuredDetailsDAO.saveTmasInsured( baseVO );
		return baseVOOutput;
	}

	private BaseVO viewInsuredComments( BaseVO baseVO ){
		BaseVO baseVOOutput = insuredDetailsDAO.viewInsuredComments( baseVO );
		return baseVOOutput;
	}

	private BaseVO viewInsuredActiveTransactions( BaseVO baseVO ){
		BaseVO baseVOOutput = insuredDetailsDAO.viewInsuredActiveTransactions( baseVO );
		return baseVOOutput;
	}

	private BaseVO viewTransAccountHistory( BaseVO baseVO ){
		BaseVO baseVOOutput = insuredDetailsDAO.viewTransAccountHistory( baseVO );
		return baseVOOutput;
	}

	/**
	 * @param insuredDetailsDAO the insuredDetailsDAO to set
	 */
	public void setInsuredDetailsDAO( IInsuredDetailsDAO insuredDetailsDAO ){
		this.insuredDetailsDAO = insuredDetailsDAO;
	}

	private BaseVO viewTransClaimsHistory( BaseVO baseVO ){
		BaseVO baseVOOutput = insuredDetailsDAO.viewTransClaimsHistory( baseVO );
		return baseVOOutput;
	}
	private BaseVO saveOrUpdateTmasInsured( BaseVO baseVO ){
		BaseVO baseVOOutput = insuredDetailsDAO.saveOrUpdateTmasInsured( baseVO );
		return baseVOOutput;
	}
	private BaseVO compareCommonTmasInsuredForInsuredCheck(BaseVO baseVO) {
		
		return insuredDetailsDAO.compareCommonTmasInsuredForInsuredCheck(baseVO);
	}
	
}
