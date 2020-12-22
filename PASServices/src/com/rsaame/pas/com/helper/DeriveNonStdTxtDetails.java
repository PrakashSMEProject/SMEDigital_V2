package com.rsaame.pas.com.helper;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.model.TTrnNonStdTextQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.NonStandardClause;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;

public class DeriveNonStdTxtDetails extends BaseDervieDetails{

	@Override
	protected void preprocessRecord( String tableInExecution, POJOWrapper mappedPojo, POJOWrapper existingRecord, TableData<BaseVO> tableData, HibernateTemplate ht,
			PolicyDataVO polData, CommonVO commonVO, SaveCase saveCase ){
		
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD 
	}

	@Override
	protected void updateValues( String tableInExecution, POJOWrapper mappedPojo, TableData<BaseVO> tableData, HibernateTemplate ht, PolicyDataVO polData, CommonVO commonVO ){

		TTrnNonStdTextQuo nonStdTextQuo = (TTrnNonStdTextQuo) mappedPojo;
		List<Integer> schemePolType = DAOUtils.getSchemeAndPolicyType( commonVO, ht );
		Integer policyType = schemePolType.get( 1 );
		Integer sectionId = Integer.valueOf( Utils.getSingleValueAppConfig( commonVO.getLob() + "_SEC_ID" ) );
		nonStdTextQuo.setValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		nonStdTextQuo.setNstValidityExpiryDate( SvcConstants.EXP_DATE );
		nonStdTextQuo.setNstStatus( SvcConstants.POL_STATUS_PENDING.byteValue() );
		nonStdTextQuo.setNstPhrCode( Byte.valueOf( getHdrCodeForSection( sectionId, policyType, ht ).toString() ) );

		Long endtId = getLatestEndtIDForNonStdClause( commonVO, ( (NonStandardClause) tableData.getTableData() ).getClauseType(), ht );

		if( !Utils.isEmpty( endtId ) ){
			nonStdTextQuo.getId().setEndtId( endtId );

		}

	}

	private Integer getHdrCodeForSection( Integer sectionId, Integer policyType, HibernateTemplate ht ){

		com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );

		String GET_SECTION_HDR = "select sec_pc_hdr from t_mas_pkg_pol_section where sec_pt_code=" + policyType + " and sec_id=" + sectionId;

		Session session = ht.getSessionFactory().getCurrentSession();

		Query query = session.createSQLQuery( GET_SECTION_HDR );
		List<Object> result = query.list();

		return ( converter.getBFromA( result.get( 0 ) ) );
	}

	public static Long getLatestEndtIDForNonStdClause( CommonVO commonVO, String clauseType, HibernateTemplate hibernateTemplate ){

		com.rsaame.pas.cmn.converter.BigDecimalLongConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalLongConverter.class, "", "" );

		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		String quoteQuery = "SELECT nst_endt_id FROM T_TRN_NON_STD_TEXT_QUO where nst_policy_id = :policyId and nst_type_code = :typeCode and trunc(nst_validity_end_date)='31-DEC-2049'";
		String policyQuery = "SELECT nst_endt_id FROM T_TRN_NON_STD_TEXT where nst_policy_id = :policyId and nst_type_code = :typeCode and trunc(nst_validity_end_date)='31-DEC-2049'";

		SQLQuery query = null;
		if( commonVO.getIsQuote() ){
			query = session.createSQLQuery( quoteQuery );
		}
		else{
			query = session.createSQLQuery( policyQuery );
		}
		Long typeCode = null;

		if( clauseType.equalsIgnoreCase( "C" ) ){
			typeCode = 1L;
		}
		else if( clauseType.equalsIgnoreCase( "W" ) ){
			typeCode = 2L;
		}
		else if( clauseType.equalsIgnoreCase( "E" ) ){
			typeCode = 3L;
		}

		query.setParameter( "policyId", commonVO.getPolicyId() );
		query.setParameter( "typeCode", typeCode );
		List<Object> result = query.list();

		return !Utils.isEmpty( result ) ? converter.getBFromA( result.get( 0 ) ) : null;
	}

}
