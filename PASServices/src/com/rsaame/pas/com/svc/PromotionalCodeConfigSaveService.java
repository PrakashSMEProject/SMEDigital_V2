package com.rsaame.pas.com.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.endorse.dao.IPromoCodeConfigDAO;
import com.rsaame.pas.vo.svc.TMasPromoDiscCoverVO;
import com.rsaame.pas.vo.svc.TMasPromotionalCodeVO;

public class PromotionalCodeConfigSaveService extends BaseService{

	private final static Logger logger = Logger.getLogger( PromotionalCodeConfigSaveService.class );
	IPromoCodeConfigDAO iPromoCodeConfigDAO;

	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;
		if( methodName.equals( "savePromoCdConfig" ) ){
			returnValue = savePromoCodeDetails( (BaseVO) args[ 0 ] );
		}if( methodName.equals( "savePromoScheme" ) ){
			returnValue = savePromoScheme( (BaseVO) args[ 0 ] );
		}if( methodName.equals( "savePromoDiscount" ) ){
			returnValue = savePromoDiscount( (BaseVO) args[ 0 ] );
		}

		return returnValue;
	}

	private BaseVO savePromoDiscount(BaseVO baseVO) {
		TMasPromoDiscCoverVO tMasPromoDiscCoverVO = (TMasPromoDiscCoverVO) baseVO;
		if( !Utils.isEmpty( tMasPromoDiscCoverVO ) ){
			logger.debug( "PromotionalCodeConfigSaveService ---------> Going to call PromotionalCdConfigDA_1" );
			iPromoCodeConfigDAO.savePromoDiscount( baseVO );
		}
		return baseVO;
	}

	private BaseVO savePromoScheme(BaseVO baseVO) {
		TMasPromoDiscCoverVO tMasPromoDiscCoverVO = (TMasPromoDiscCoverVO) baseVO;
		if( !Utils.isEmpty( tMasPromoDiscCoverVO ) ){
			logger.debug( "PromotionalCodeConfigSaveService ---------> Going to call PromotionalCdConfigDA_2" );
			iPromoCodeConfigDAO.savePromotionalDiscCover( baseVO );
		}
		return baseVO;
	}

	private BaseVO savePromoCodeDetails( BaseVO baseVO ){
		TMasPromotionalCodeVO MasPromotionalCodeVO = (TMasPromotionalCodeVO) baseVO;
		if( !Utils.isEmpty( MasPromotionalCodeVO ) ){
			logger.debug( "PromotionalCodeConfigSaveService ---------> Going to call PromotionalCdConfigDA_3" );
			iPromoCodeConfigDAO.savePromotionalCd( baseVO );
		}
		return baseVO;
	}


	/**
	 * @return the iPromoCodeConfigDAO
	 */
	public IPromoCodeConfigDAO getiPromoCodeConfigDAO(){
		return iPromoCodeConfigDAO;
	}

	/**
	 * @param iPromoCodeConfigDAO the iPromoCodeConfigDAO to set
	 */
	public void setiPromoCodeConfigDAO( IPromoCodeConfigDAO iPromoCodeConfigDAO ){
		this.iPromoCodeConfigDAO = iPromoCodeConfigDAO;
	}

}
