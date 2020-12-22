package com.rsaame.pas.dao.cmn;

import java.util.Map;

import javax.sql.DataSource;

import com.mindtree.ruc.cmn.constants.CommonErrorKeys;
import com.mindtree.ruc.cmn.db.StoredProcedure;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Utils;

public class PASStoredProcedure extends StoredProcedure{
	/** The out parameter "p_err_text" */
	public static final String P_ERR_TEXT = "p_err_text";
	
	/** The out parameter "p_is_new" */
	public static final String P_IS_NEW = "p_is_new";
	
	public PASStoredProcedure( String procId, DataSource ds ){
		super( procId, ds );
	}

	@Override
	protected boolean isSPError( Map<String, Object> result ){
		/* If the SP has returned an error message, throw BusinessException. */
		if( !Utils.isEmpty( result.get( P_ERR_TEXT ) ) ){
			throw new BusinessException( CommonErrorKeys.STORED_PROC_ERROR, null, "Exception during call to SP [", result.get( P_ERR_TEXT ).toString(), "]" ); 
		}
		
		return false;
	}
}
