/**
 * 
 */
package com.rsaame.pas.transaction.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.log.Logger;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnSectionDetailsQuo;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.SectionDetailsVO;
import com.rsaame.pas.vo.bus.TransactionVO;
import com.rsaame.pas.vo.bus.ViewTransactionRequestVO;
import com.rsaame.pas.vo.bus.ViewTransactionResponseVO;

/**
 * @author M1016284
 *
 */
public class ViewTransactionDAO extends BaseDBDAO implements IViewTransactionDAO{

	private final static Logger logger = Logger.getLogger( ViewTransactionDAO.class );

	@Override
	public BaseVO viewTransactionDetails( BaseVO baseVO ){
		logger.info( "********** Inside viewTransactionDetails **********" );

		String quoteNoStr = null;
		Long quoteNo = 0L;
		String policyNo = null;
		String endNo = null;
		Long policyId = 0L;
		Short tariffCode = 0;
		List<TTrnSectionDetailsQuo> tTrnSectionDetailsQuoList = null;
		Iterator<TTrnPolicyQuo> policyListItr = null;
		Iterator<TTrnSectionDetailsQuo> tTrnSectionDetailsQuoListItr = null;
		TTrnPolicyQuo policyQuo = null;
		TTrnSectionDetailsQuo tTrnSectionDetailsQuo = null;
		SectionDetailsVO sectionDetailsVO = null;
		ViewTransactionResponseVO viewTransRespVO = null;
		List<SectionDetailsVO> sectionDetailsVOList = new ArrayList<SectionDetailsVO>( 1 );

		ViewTransactionRequestVO viewTransReqVO = (ViewTransactionRequestVO) baseVO;
		TransactionVO transactionVO = viewTransReqVO.getTransaction();

		endNo = transactionVO.getTransactionEndNo();
		if( transactionVO.getTransactionType().toUpperCase().contains( "QUOT" ) ){
			quoteNoStr = transactionVO.getTransactionPolicyNumber();
			quoteNo = new Long( quoteNoStr );

			List<TTrnPolicyQuo> policyQuoList = (List<TTrnPolicyQuo>) getHibernateTemplate().find( "from TTrnPolicyQuo where polQuotationNo=?", quoteNo );
			if( null != policyQuoList ){
				policyListItr = policyQuoList.iterator();
				while( policyListItr.hasNext() ){
					policyQuo = policyListItr.next();
					policyId = policyQuo.getId().getPolPolicyId();
					tariffCode = policyQuo.getPolTarCode();

					tTrnSectionDetailsQuoList = (List<TTrnSectionDetailsQuo>) getHibernateTemplate().find( "from TTrnSectionDetailsQuo where id.secPolicyId=? and secPtCode=50",
							policyId );

					if( null != tTrnSectionDetailsQuoList ){
						tTrnSectionDetailsQuoListItr = tTrnSectionDetailsQuoList.iterator();
						while( tTrnSectionDetailsQuoListItr.hasNext() ){
							tTrnSectionDetailsQuo = tTrnSectionDetailsQuoListItr.next();
							sectionDetailsVO = BeanMapper.map( tTrnSectionDetailsQuo, SectionDetailsVO.class );
							sectionDetailsVO.setPolicyTariffCode( tariffCode );
							sectionDetailsVOList.add( sectionDetailsVO );
						}
					}
				}

				viewTransRespVO = new ViewTransactionResponseVO();
				viewTransRespVO.setSectionDetailsVOList( sectionDetailsVOList );
			}
		}
		// TODO: For Policy
		else{
			policyNo = transactionVO.getTransactionPolicyNumber();
			logger.debug( "Searched transaction details for Policy Number: " + policyNo );
		}
		logger.debug( "Searched transaction details for quote: " + quoteNo );
		logger.debug( "Searched transaction details for Policy Id: " + policyId );
		logger.debug( "Searched transaction details for Tariff code: " + tariffCode );
		logger.debug( "Searched transaction details for Transaction number: " + endNo  );
		return viewTransRespVO;
	}
}
