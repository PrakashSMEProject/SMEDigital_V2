package com.rsaame.pas.com.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.endorse.dao.IPartnerMgmtDAO;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.svc.TMasPartnerMgmtVOHolder;

public class PartnerManagementSaveService extends BaseService{

	private final static Logger logger = Logger.getLogger( PartnerManagementSaveService.class );
	private IPartnerMgmtDAO iPartnerMgmtDAO;
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod( String methodName, Object... args ){

		BaseVO returnValue = null;
		logger.debug( "PartnerManagementSaveService --------> Entered service layer for persisting the data" );
		if( SvcConstants.SAVE_PARTNER_MANAGEMENT.equals( methodName ) ){
			returnValue = savePartnerMgmtDets( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}

	/**
	 * 
	 * @param baseVO
	 * @return
	 */
	private BaseVO savePartnerMgmtDets( BaseVO baseVO ){

		TMasPartnerMgmtVOHolder tmasPartnerMgmtVOHolder = (TMasPartnerMgmtVOHolder) baseVO;
		if( !Utils.isEmpty( tmasPartnerMgmtVOHolder ) ){
			logger.debug( "PartnerManagementSaveService --------> Going to make call PartnerManagementDAO.java" );
			iPartnerMgmtDAO.savePartnerDets( baseVO );
		}
		return baseVO;
	}

	/**
	 * @return the iPartnerMgmtDAO
	 */
	public IPartnerMgmtDAO getiPartnerMgmtDAO(){
		return iPartnerMgmtDAO;
	}

	/**
	 * @param iPartnerMgmtDAO the iPartnerMgmtDAO to set
	 */
	public void setiPartnerMgmtDAO( IPartnerMgmtDAO iPartnerMgmtDAO ){
		this.iPartnerMgmtDAO = iPartnerMgmtDAO;
	}

}
