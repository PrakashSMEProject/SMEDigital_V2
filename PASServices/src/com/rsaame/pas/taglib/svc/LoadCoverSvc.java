/**
 * 
 */
package com.rsaame.pas.taglib.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.rsaame.pas.dao.cmn.IBaseLoadOperation;
import com.rsaame.pas.taglib.dao.ILoadCoverDAO;
import com.rsaame.pas.vo.bus.CoverDetails;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.svc.TTrnPremiumVOHolder;

/**
 * @author M1021201
 *
 */
public class LoadCoverSvc extends BaseService{

	private static final Logger LOGGER = Logger.getLogger( LoadCoverSvc.class );
	ILoadCoverDAO loadCoverDAO;
	IBaseLoadOperation baseLoadOperation;

	/**
	 * @return
	 */
	public ILoadCoverDAO getLoadCoverDAO(){
		return loadCoverDAO;
	}

	/**
	 * @param loadCoverDAO
	 */
	public void setLoadCoverDAO( ILoadCoverDAO loadCoverDAO ){
		this.loadCoverDAO = loadCoverDAO;
	}
	
	/**
	 * 
	 * @param loadOperation
	 * @return
	 */
	public void setBaseLoadOperation( IBaseLoadOperation loadOperation){
		this.baseLoadOperation = loadOperation;
	}
	
	@Override
	public Object invokeMethod( String methodName, Object... args ){

		LOGGER.info( "Risk cover tag service method to fetch details" );
		Object returnValue = null;
		
			if( methodName.equals( "getRiskCoverDetails" ) ){
				returnValue = getRiskCoverDetails( args[ 0 ] );
			}
			else if( methodName.equals( "getSchemeDetails" ) ){
				returnValue = getSchemeDetails( args[ 0 ] );
			}
			
			if( methodName.equals( "getPackages" ) ){
				returnValue = getPackages( (CommonVO)args[ 0 ] );
			}
			
			if( methodName.equals( "getTravelCovers" ) ){
				returnValue = getTravelCovers( (SchemeVO)args[ 0 ] );
			}
			if( methodName.equals( "getPremiumRecords" ) ){
				returnValue = getPremiumRecords( (CommonVO)args[ 0 ]);
			}
			if( methodName.equals( "getTravelPackages" ) ){
				returnValue = getTravelPackages( args[ 0 ] );
			}
		
		return returnValue;
	}

	private SchemeVO getSchemeDetails( Object args ){
		
		CommonVO commonVO = null;
		SchemeVO schemeVO = null;
		try{
			commonVO = (CommonVO) args;
			schemeVO = (SchemeVO)loadCoverDAO.getSchemeDetails( commonVO );
		}
		catch(BusinessException exp){
			exp.printStackTrace();
			exp.getMessage();
		}
		return schemeVO;
		
	}

	private java.util.List<CoverDetailsVO> getTravelCovers( SchemeVO scheme ){
		
		return loadCoverDAO.getTravelCovers( scheme );
	}

	private TravelInsuranceVO getPackages( CommonVO commonVO ){
		
		return loadCoverDAO.getPackages( commonVO );
	}
	
	private java.util.List<TTrnPremiumVOHolder>  getPremiumRecords( CommonVO commonVO){
		return loadCoverDAO.getPremiumRecords( commonVO );
	}
	
	public java.util.List<TravelPackageVO>  getTravelPackages(Object args){
		BaseVO baseVO = (BaseVO) args;
		return loadCoverDAO.getTravelPackageList( baseVO );
	}

	public CoverDetails getRiskCoverDetails( Object args ){

		CoverDetails coverDetails = null;
		SchemeVO scheme = null;
		try{
			coverDetails = new CoverDetails();
			scheme = (SchemeVO) args;
			coverDetails = (CoverDetails) loadCoverDAO.getRiskCoverDetails( scheme );
		}
		catch( BusinessException exp ){
			exp.printStackTrace();
			exp.getMessage();
		}
		return coverDetails;
	}
}
