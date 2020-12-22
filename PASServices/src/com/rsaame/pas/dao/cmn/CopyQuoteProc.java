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

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.vo.app.CopyQuoteVO;

/**
 * @author m1014739
 *
 */
public class CopyQuoteProc extends StoredProcedure{

	private static Logger logger = Logger.getLogger( CopyQuoteProc.class );
	private static String PROC_NAME = Utils.getSingleValueAppConfig( "COPY_QUOTE_PROC" );
	private static String AI_POL_LINKING_ID = "AI_POL_LINKING_ID";
	private static String AI_CLIENT = "AI_CLIENT";
	private static String AI_INSURED_CODE = "AI_INSURED_CODE";
	private static String AI_POLICY_TYPE = "AI_POLICY_TYPE";
	private static String AI_EMP_NO = "AI_EMP_NO";
	private static String AI_LOCATION_CODE = "AI_LOCATION_CODE";
	private static String AI_DEL_POL_LINK_ID = "AI_DEL_POL_LINK_ID";
	private static String AO_POL_NO = "AO_POL_NO";
	private static String P_ERR_TEXT = "P_ERR_TEXT";
	private static String AO_SBS_QUO_NO = "AO_SBS_QUO_NO";
	private static String AO_ENDT_ID = "AO_ENDT_ID";
	private static String AO_LINK_POL_ID = "AO_LINK_POL_ID";

	public CopyQuoteProc( DataSource ds ){

		if( logger.isInfo() ) logger.info( "Preparing the Pro_Copy_Pkg_Quo procedure for execution" );
		setDataSource( ds );
		setFunction( false );
		setSql( PROC_NAME );
		declareParameter( new SqlParameter( AI_POL_LINKING_ID, OracleTypes.NUMBER ) );
		declareParameter( new SqlParameter( AI_CLIENT, OracleTypes.VARCHAR ) );
		declareParameter( new SqlParameter( AI_INSURED_CODE, OracleTypes.NUMBER ) );
		declareParameter( new SqlParameter( AI_POLICY_TYPE, OracleTypes.NUMBER ) );
		declareParameter( new SqlParameter( AI_EMP_NO, OracleTypes.NUMBER ) );
		declareParameter( new SqlParameter( AI_LOCATION_CODE, OracleTypes.NUMBER ) );
		declareParameter( new SqlParameter( AI_DEL_POL_LINK_ID, OracleTypes.NUMBER ) );
		declareParameter( new SqlOutParameter( AO_POL_NO, OracleTypes.NUMBER ) );
		declareParameter( new SqlOutParameter( P_ERR_TEXT, OracleTypes.VARCHAR ) );
		declareParameter( new SqlOutParameter( AO_SBS_QUO_NO, OracleTypes.NUMBER ) );
		declareParameter( new SqlOutParameter( AO_ENDT_ID, OracleTypes.NUMBER ) );
		declareParameter( new SqlOutParameter( AO_LINK_POL_ID, OracleTypes.NUMBER ) );
		compile();
		if( logger.isInfo() ) logger.info( "Procedure Pro_Copy_Pkg_Quo reqdy for execution" );
	}

	/**
	 * 
	 * @param baseVO
	 * @return
	 */
	public BaseVO copyQuote( BaseVO baseVO ){
		CopyQuoteVO copyQuoteVO = (CopyQuoteVO) baseVO;

		Map<String, Object> result = execute( copyQuoteVO );

		/* If the SP has returned an error message, throw BusinessException. */
		if( !Utils.isEmpty( result.get( P_ERR_TEXT ) ) ){
			throw new BusinessException( "pas.copyQuote.copyQuoteSPError", null, "Exception during call to SP [", PROC_NAME, "] [", result.get( P_ERR_TEXT ).toString(), "]" );
		}

		Object newPolicyNo = result.get( AO_POL_NO );
		Object newQuoteNo = result.get( AO_SBS_QUO_NO );
		Object newEndId = result.get( AO_ENDT_ID );
		Object newPolicyLinkingId = result.get( AO_LINK_POL_ID );

		if( !Utils.isEmpty( newPolicyNo ) ) copyQuoteVO.setNewPolicyNo( ( (java.math.BigDecimal) newPolicyNo ).longValue() );
		if( !Utils.isEmpty( newQuoteNo ) ) copyQuoteVO.setNewQuoteNo( ( (java.math.BigDecimal) newQuoteNo ).longValue() );
		if( !Utils.isEmpty( newEndId ) ) copyQuoteVO.setNewEndtId( ( (java.math.BigDecimal) newEndId ).longValue() );
		if( !Utils.isEmpty( newPolicyLinkingId ) ) copyQuoteVO.setNewPolLinkingId( ( (java.math.BigDecimal) newPolicyLinkingId ).longValue() );

		logger.info( "After Pro_Copy_Pkg_Quo procedure execution for newQuoteNo :" + newQuoteNo + " newEndId: " + newEndId + " newPolicyLinkingId: " + newPolicyLinkingId + " newPolicyNo: " + newPolicyNo );
		
		return copyQuoteVO;
	}

	/**
	 * 
	 * @param copyQuoteVO
	 * @return
	 */
	private Map<String, Object> execute( CopyQuoteVO copyQuoteVO ){
		Long polLinkingId = copyQuoteVO.getPolLinkingId();
		String clientId = "AIC";
		Long insuredId = copyQuoteVO.getInsuredId();
		Integer policyCode = copyQuoteVO.getPolicyCode();
		Integer userId = copyQuoteVO.getUserId();
		Integer locationCode = copyQuoteVO.getLocationCode();
		Long delPolLinkingId = copyQuoteVO.getDeletePolLinkingId();

		logger.info( "Starting Pro_Copy_Pkg_Quo procedure execution for polLinkingId: " + polLinkingId + 
				" clientId: " + clientId +
				" insuredId: " + insuredId + 
				" delPolLinkingId: " + delPolLinkingId +
				" policyCode: " + policyCode +
				" userId: " + userId + 
				" locationCode: " + locationCode);
		
		Map<String, Object> inputs = new HashMap<String, Object>();
		inputs.put( AI_POL_LINKING_ID, polLinkingId );
		inputs.put( AI_CLIENT, clientId );
		inputs.put( AI_INSURED_CODE, insuredId );
		inputs.put( AI_POLICY_TYPE, policyCode );
		inputs.put( AI_EMP_NO, userId );
		inputs.put( AI_LOCATION_CODE, locationCode );
		inputs.put( AI_DEL_POL_LINK_ID, delPolLinkingId );

		return super.execute( inputs );
	}
}
