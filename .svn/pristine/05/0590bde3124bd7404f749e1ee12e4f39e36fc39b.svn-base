package com.rsaame.pas.dao.cmn;

public enum SaveCase{
	/** The entity is being created. Eg, a building is being created afresh. */
	CREATE,
	
	/** The entity is being deleted. Eg, a building is being deleted, ie, there is an Id already for it. */
	DELETE,
	
	/** The entity is being changed first time in this endorsement or quotation edit. Eg, a building is being 
	 *  changed and the current record is in "Active" status. */
	CHANGE_WITH_NEW_REC,
	
	/** The entity is being changed for the second time or more in this endorsement or quotation edit. Eg, 
	 *  a building is being changed and the current record is in "Pending" status. */
	CHANGE_WITH_EXISTING_REC,
	
	DELETE_PENDING_REC;
}
