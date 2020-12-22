package com.rsaame.pas.transaction.val;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.validation.IBeanValidator;
import com.rsaame.pas.vo.bus.SearchTransactionCriteriaVO;

public class CompareDatesForTransaction implements IBeanValidator{

	@Override
	public boolean validate( Object bean, Map<String, String> parameters, List<String> errorKeys ){

		boolean successfulForCreated = true;
		boolean successfulForEffective = true;
		if( bean instanceof SearchTransactionCriteriaVO ){

			SearchTransactionCriteriaVO theBean = (SearchTransactionCriteriaVO) bean;
			if( null != theBean.getTransaction() ){
				Date transactionDateFrom = theBean.getTransaction().getTransactionFrom();
				Date transactionDateTo = theBean.getTransaction().getTransactionTo();

				Date policyEffDate = theBean.getTransaction().getTransactionEffectiveDate();
				Date policyExpDate = theBean.getTransaction().getTransactionExpiryDate();

				if( ( transactionDateFrom != null ) && ( transactionDateTo != null ) ){
					if( transactionDateFrom.after( transactionDateTo ) ){
						errorKeys.add( "pas.searchTransaction.transactionDateFromAfterTo" );
						successfulForCreated = false;
					}
				}

				if( ( policyEffDate != null ) && ( policyExpDate != null ) ){
					if( policyEffDate.after( policyExpDate ) ){
						errorKeys.add( "pas.searchTransaction.effectiveAfterExpiry" );
						successfulForEffective = false;
					}
				}
			}

		}
		return ( successfulForCreated && successfulForEffective ); //SONAR Fix -- 20-apr-2018 -- changed & TO &&
	}

}
