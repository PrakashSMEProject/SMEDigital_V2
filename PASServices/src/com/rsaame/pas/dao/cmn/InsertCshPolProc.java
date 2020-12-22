/**
 * 
 */
package com.rsaame.pas.dao.cmn;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * @author m1014644
 *
 */
public class InsertCshPolProc extends StoredProcedure{

	private static Logger LOGGER = Logger.getLogger( InsertCshPolProc.class );
	private static String PROC_NAME = "PKG_PAS_UTILS. INS_ROWS_CSH_POL";
	private static String P_POL_CLASS_CODE = "p_pol_class_code";
	private static String P_QUOTATION_NO = "p_linking_id";
	private static String P_POL_PREMIUM = "p_pol_premium";
	private static String P_POL_ID = "p_pol_id";
	private static String P_ERR_TXT = "p_err_text";
	private static String P_IS_NEW = "p_is_new";

	public InsertCshPolProc( DataSource ds ){
		if( LOGGER.isInfo() ) LOGGER.info( "Preparing the procedure for execution" );
		setDataSource( ds );
		setFunction( false );
		setSql( PROC_NAME );
		declareParameter( new SqlParameter( P_POL_CLASS_CODE, OracleTypes.NUMBER ) );
		declareParameter( new SqlParameter( P_QUOTATION_NO, OracleTypes.NUMBER ) );
		declareParameter( new SqlParameter( P_POL_PREMIUM, OracleTypes.NUMBER ) ); //p_endt_id p_prepared_by p_validity_start_date 
		declareParameter( new SqlParameter( "p_endt_id", OracleTypes.NUMBER ) );
		declareParameter( new SqlParameter( "p_prepared_by", OracleTypes.NUMBER ) );
		declareParameter( new SqlParameter( "p_validity_start_date", OracleTypes.DATE ) );
		declareParameter( new SqlOutParameter( P_POL_ID, OracleTypes.NUMBER ) );
		declareParameter( new SqlOutParameter( P_IS_NEW, OracleTypes.NUMBER ) );
		declareParameter( new SqlOutParameter( P_ERR_TXT, OracleTypes.VARCHAR ) );
		compile();
		if( LOGGER.isInfo() ) LOGGER.info( "Procedure reqdy for execution" );
	}

	public Long getPolicyId( Integer classCode, Long quoteId, Double premium, Long endtId, Integer preparedBy, Date vsd ){
		Map<String, Object> result = execute( classCode, quoteId, premium ,endtId , preparedBy , vsd );
		
		/* If the SP has returned an error message, throw BusinessException. */
		if( !Utils.isEmpty( result.get( P_ERR_TXT ) ) ){
			throw new BusinessException( "pas.money.cashSPError", null, "Exception during call to SP [", PROC_NAME, "] [", result.get( P_ERR_TXT ).toString(), "]" ); 
		}
		
		Object policyIdObj = result.get( P_POL_ID );
		return Utils.isEmpty( policyIdObj ) ? null : ( (java.math.BigDecimal) policyIdObj ).longValue(); // TODO After anand adds the out parameter return that
	}

	private Map<String, Object> execute( Integer classCode, Long quoteId, Double premium , Long endtId, Integer preparedBy, Date vsd   ){
		LOGGER.info( "Starting procedure execution for :" + classCode + " " + quoteId + " " + premium );
		Map<String, Object> inputs = new HashMap<String, Object>();
		inputs.put( P_POL_CLASS_CODE, classCode );
		inputs.put( P_QUOTATION_NO, quoteId );
		inputs.put( P_POL_PREMIUM, premium );
		inputs.put( "p_endt_id", endtId );
		inputs.put( "p_prepared_by", preparedBy );
		inputs.put( "p_validity_start_date", vsd );
		LOGGER.info( " procedure execution complete" );
		return super.execute( inputs );
	}

}
