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
public class GetPreviousPremiumFunc extends StoredProcedure{

	private static Logger logger = Logger.getLogger( GetPreviousPremiumFunc.class );
	private static String FUNC_NAME = "PKG_ENDT_GEN.GET_PREV_PRM";
	private static String P_PRM_TOTAL = "v_prm_total";
	private static String P_POL_LINKING_ID = "p_pol_linking_id";
	private static String P_ENDT_ID = "p_endt_id";

	public GetPreviousPremiumFunc( DataSource ds ){

		if( logger.isInfo() ) logger.info( "Preparing the get_prev_prm function for execution." );
		setDataSource( ds );
		setFunction( true );
		setSql( FUNC_NAME );
		declareParameter( new SqlOutParameter( P_PRM_TOTAL, OracleTypes.DOUBLE ) );
		declareParameter( new SqlParameter( P_POL_LINKING_ID, OracleTypes.NUMBER ) );
		declareParameter( new SqlParameter( P_ENDT_ID, OracleTypes.NUMBER ) );
		compile();
		if( logger.isInfo() ) logger.info( "Function get_prev_prm ready for execution." );
	}

	public Double getPreviousPremium( Long p_pol_linking_id, Long p_endt_id ){
		Map<String, Object> result = executeFunction( p_pol_linking_id, p_endt_id );

		Object prevPrmObj = result.get( P_PRM_TOTAL );
		return Utils.isEmpty( prevPrmObj ) ? null : (Double) prevPrmObj;
	}

	public Map<String, Object> executeFunction( Long p_pol_linking_id, Long p_endt_id ){
		Map<String, Object> inputs = new HashMap<String, Object>();
		inputs.put( P_PRM_TOTAL, 0.0 );
		inputs.put( P_POL_LINKING_ID, p_pol_linking_id );
		inputs.put( P_ENDT_ID, p_endt_id );

		logger.info( " procedure execution complete" );
		Map<String, Object> test = super.execute( inputs );
		return test;
	}

}
