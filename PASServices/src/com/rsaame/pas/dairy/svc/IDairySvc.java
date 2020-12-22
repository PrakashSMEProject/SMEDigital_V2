package com.rsaame.pas.dairy.svc;

import com.mindtree.ruc.cmn.base.BaseVO;

public interface IDairySvc {
	
	public abstract BaseVO saveDairyItem(BaseVO baseVO);
	
	public abstract BaseVO getDairyItems(BaseVO baseVO);
	
	public abstract BaseVO deleteDairyItem(BaseVO baseVO);
	public abstract BaseVO getDairyItemsForReminder(String userId);

}
