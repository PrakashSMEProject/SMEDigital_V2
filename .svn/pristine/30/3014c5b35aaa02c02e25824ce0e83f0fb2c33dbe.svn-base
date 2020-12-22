package com.rsaame.pas.dairy.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dairy.dao.IDairyDAO;

public class DairySvc extends BaseService implements IDairySvc{
	private IDairyDAO dairyDAO ;
	
	@Override
	public Object invokeMethod(String methodName, Object... args) {

		BaseVO returnValue = null;
		if( "saveDairyItem".equals( methodName ) ){
 			returnValue = saveDairyItem( (BaseVO) args[ 0 ] );
		}
		if( "getDairyItems".equals( methodName ) ){
 			returnValue = getDairyItems( (BaseVO) args[ 0 ] );
		}
		if( "deleteDairyItem".equals( methodName ) ){
 			returnValue = deleteDairyItem( (BaseVO) args[ 0 ] );
		}
		
		if( "getDairyItemsForReminder".equals( methodName ) ){
 			returnValue = getDairyItemsForReminder((String)args[0]);
		}
		if( "updateDiaryItemsForReminder".equals( methodName ) ){
 			returnValue = updateDiaryItemsForReminder((String)args[0],(String)args[1],(String)args[2],(String)args[3]);
		}
		
		return returnValue;
	}

	private BaseVO updateDiaryItemsForReminder(String serialNum, String preparedBy, String preprdDate, String typeId) {
		  dairyDAO.updateDiaryItemsForReminder(serialNum,preparedBy,preprdDate,typeId);
		return null;
	}

	public BaseVO getDairyItemsForReminder(String userId) {
		 return dairyDAO.getDiaryItemsForReminder(userId);
		 
		 }

	public BaseVO deleteDairyItem(BaseVO baseVO) {
		 return dairyDAO.deleteDairyItem(baseVO);
	}

	@Override
	public BaseVO saveDairyItem(BaseVO baseVO) {
		
		return dairyDAO.saveDairyItem(baseVO);
	}

	@Override
	public BaseVO getDairyItems(BaseVO baseVO) {
	
		return dairyDAO.getDairyItems(baseVO);
	}
	
	public IDairyDAO getDairyDAO() {
		return dairyDAO;
	}

	public void setDairyDAO(IDairyDAO dairyDAO) {
		this.dairyDAO = dairyDAO;
	}

}
