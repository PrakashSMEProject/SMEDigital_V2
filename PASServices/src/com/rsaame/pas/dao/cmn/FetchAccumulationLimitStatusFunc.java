/**
 * 
 */
package com.rsaame.pas.dao.cmn;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * @author m1014739
 *
 */
public class FetchAccumulationLimitStatusFunc extends StoredProcedure{

	private static Logger logger = Logger.getLogger( FetchAccumulationLimitStatusFunc.class );
	private static String PROC_NAME = "PKG_PAS_UTILS.accumulation_limit_chk";
	private static String P_RESULT = "v_result";
	private static String P_DIR_CODE = "p_dir_code";
	private static String P_SUM_INSURED = "p_sum_insured";

	public FetchAccumulationLimitStatusFunc( DataSource ds ){

		if( logger.isInfo() ) logger.info( "Preparing the accumulation_limit_chk function for execution." );
		setDataSource( ds );
		setFunction( true );
		setSql( PROC_NAME );
		declareParameter( new SqlOutParameter( P_RESULT, OracleTypes.VARCHAR ) );
		declareParameter( new SqlParameter( P_DIR_CODE, OracleTypes.NUMBER ) );
		declareParameter( new SqlParameter( P_SUM_INSURED, OracleTypes.NUMBER ) );
		compile();
		if( logger.isInfo() ) logger.info( "Function accumulation_limit_chk ready for execution." );
	}

	public String getAccLimitStatus( Integer p_dir_code, Double p_sum_insured ){
		
		Map<String, Object> result =null; ;
		Object accLimitObj = null;
		
		if(p_dir_code == 0){
			accLimitObj = "N";
		}else{
			result = executeFunction( p_dir_code, p_sum_insured );
			accLimitObj = result.get( P_RESULT );
		}
		return Utils.isEmpty( accLimitObj ) ? null : accLimitObj.toString();
	}

	public Map<String, Object> executeFunction( Integer p_dir_code, Double p_sum_insured ){
		Map<String, Object> inputs = new HashMap<String, Object>();
		inputs.put( P_RESULT, "" );
		inputs.put( P_DIR_CODE, p_dir_code );
		inputs.put( P_SUM_INSURED, p_sum_insured );

		logger.info( " procedure execution complete" );
		Map<String, Object> test = super.execute( inputs );
		return test;
	}

}
