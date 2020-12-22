package com.rsaame.pas.tb.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.cmn.BaseSectionSaveDAO;
import com.rsaame.pas.tb.dao.ITravelBaggageDAO;

public class TravelBaggageSvc extends BaseService{

	ITravelBaggageDAO travelBaggageDAO; 
	@Override
	public Object invokeMethod( String methodName, Object... arg1 ){
		BaseVO returnValue = null;
		if( "loadTBSectionSvc".equals( methodName ) ){
			returnValue = loadTBSectionSvc( (BaseVO) arg1[ 0 ] );
		}
		if( "saveTBSectionSvc".equals( methodName ) ){
 			returnValue = saveBSectionSvc( (BaseVO) arg1[ 0 ] );
		}
		
		return returnValue;
	}
	
	public ITravelBaggageDAO getTravelBaggageDAO(){
		return travelBaggageDAO;
	}


	public void setTravelBaggageDAO( ITravelBaggageDAO travelBaggageDAO ){
		this.travelBaggageDAO = travelBaggageDAO;
	}


	private BaseVO saveBSectionSvc( BaseVO baseVO ){
		
		return ((BaseSectionSaveDAO)travelBaggageDAO).save( baseVO );
	}
	private BaseVO loadTBSectionSvc( BaseVO baseVO ){
		// TODO Auto-generated method stub
		return null;
	}

}
