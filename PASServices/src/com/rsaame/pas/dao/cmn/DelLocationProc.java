package com.rsaame.pas.dao.cmn;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Map;
import com.mindtree.ruc.cmn.utils.Utils;

public class DelLocationProc extends StoredProcedure{
	private static Logger LOGGER = Logger.getLogger( InsertCshPolProc.class );
	
	private static final String PROC_NAME = "PKG_PAS_UTILS.DEL_LOCATION";
	
	private static final String P_QUOTATION_NO = "p_quotation_no";
	private static final String P_SEC_ID = "p_sec_id";
	private static final String P_BLD_ID = "p_bld_id";
	private static final String P_CASCADE_FLAG = "p_cascade_flag";
	private static final String P_ERR_TXT = "p_err_text";
	
	public DelLocationProc( DataSource ds ){
		if( LOGGER.isInfo() ) LOGGER.info( "Preparing the procedure for execution" );
		setDataSource( ds );
		setFunction( false );
		setSql( PROC_NAME );
		declareParameter( new SqlParameter( P_QUOTATION_NO, OracleTypes.NUMBER ) );
		declareParameter( new SqlParameter( P_SEC_ID, OracleTypes.NUMBER ) );
		declareParameter( new SqlParameter( P_BLD_ID, OracleTypes.NUMBER ) );
		declareParameter( new SqlParameter( P_CASCADE_FLAG, OracleTypes.NUMBER ) );
		declareParameter( new SqlOutParameter( P_ERR_TXT, OracleTypes.VARCHAR ) );
		compile();
		if( LOGGER.isInfo() ) LOGGER.info( "Procedure ", PROC_NAME, " ready for execution" );
	}
	
	/**
	 * Calls the stored procedure. Expected inputs:<br/>
	 * (a) 1st argument: Quotation number: NUMBER<br/>
	 * (b) 2nd argument: Section Id: NUMBER<br/>
	 * (c) 3rd argument: Building Id: NUMBER<br/>
	 * (d) 4th argument: Cascade Deletion Flag: 1 or 0 (1 means "Yes", 0 means "No" - Used for PAR and PL deletions)
	 * 
	 * @param spArgs
	 */
	public void call( Object... spArgs ){
		java.util.Map<String, Object> result = super.execute( getInputMap( spArgs ) );

		/* If the SP has returned an error message, throw BusinessException. */
		if( !Utils.isEmpty( result.get( P_ERR_TXT ) ) ){
			throw new BusinessException( "pas.cmn.delLocationSPError", null, "Exception during call to SP [", PROC_NAME, "] [", result.get( P_ERR_TXT ).toString(), "]" ); 
		}

	}
	
	protected java.util.Map<String, Object> getInputMap( Object... spArgs ){
		if( Utils.isEmpty( spArgs ) || spArgs.length < 4 ){
			throw new SystemException( "pas.cmn.InsufficientArgsToSP", null, "Insufficient number of args for SP [", PROC_NAME, "] [", spArgs.length + "]" );
		}
		
		java.util.Map<String, Object> inputs = new Map<String, Object>( String.class, Object.class );
		inputs.put( P_QUOTATION_NO, spArgs[ 0 ] );
		inputs.put( P_SEC_ID, spArgs[ 1 ] );
		inputs.put( P_BLD_ID, spArgs[ 2 ] );
		inputs.put( P_CASCADE_FLAG, spArgs[ 3 ] );
		
		return inputs;
	}
}
