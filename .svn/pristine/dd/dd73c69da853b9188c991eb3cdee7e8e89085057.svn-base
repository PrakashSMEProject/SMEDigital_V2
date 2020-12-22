package com.rsaame.pas.renewals.val;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.validation.IBeanValidator;
import com.rsaame.pas.vo.bus.GenerateRenewalsSearchCriteriaVO;
import com.rsaame.pas.vo.bus.PrintRenewalsSearchCriteriaVO;
//import com.rsaame.pas.vo.bus.PrintRenewalsSearchCriteriaVO;
public class CompareDatesForRenewals implements IBeanValidator{
	@Override
	public boolean validate( Object bean, Map<String, String> parameters, List<String> errorKeys ){

		boolean successfulForCreated = true;
		boolean successfulForEffective = true;
		if( bean instanceof GenerateRenewalsSearchCriteriaVO ){
			GenerateRenewalsSearchCriteriaVO theBean = (GenerateRenewalsSearchCriteriaVO) bean;
			Date transactionDateFrom = theBean.getTransactionFrom();
			Date transactionDateTo = theBean.getTransactionTo();
			if( ( transactionDateFrom != null ) && ( transactionDateTo != null ) ){
				if( transactionDateFrom.after( transactionDateTo ) ){
					errorKeys.add( "pas.renewal.transactionDateFromAfterTo" );
					successfulForCreated = false;
				}
			}
		}
		if( bean instanceof PrintRenewalsSearchCriteriaVO  ){
			PrintRenewalsSearchCriteriaVO  theBean = (PrintRenewalsSearchCriteriaVO ) bean;
			Date transactionDateFrom = theBean.getTransactionFrom();
			Date transactionDateTo = theBean.getTransactionTo();
			if( ( transactionDateFrom != null ) && ( transactionDateTo != null ) ){
				if( transactionDateFrom.after( transactionDateTo ) ){
					errorKeys.add( "pas.renewal.transactionDateFromAfterTo" );
					successfulForCreated = false;
				}
			}
		}
				
		return ( successfulForCreated && successfulForEffective );  //SONAR Fix -- 20-apr-2018 -- changed & TO &&
	}

}
