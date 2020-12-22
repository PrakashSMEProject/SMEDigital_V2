package com.rsaame.pas.dao.utils;

public enum TableSnapshotLevel{
	/** The state at the time of the confirmation of the endorsement. */
	ENDT_ID,
	
	/** The currently valid state, ie, the state as compiled using the records that have Validity Expiry Date as 
	 * &quot;High Date&quot; (31st Dec, 2049). */
	CURRENT_VALID_STATE,
	
	/** The currently active state, ie, the state as compiled using the records that have status as &quot;Active&quot;. */
	CURRENT_ACTIVE_STATE;
}
