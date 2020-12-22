package com.rsaame.pas.tradelicense.dao;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.tradelicense.dao.ITradeLicenceDAO;
import com.rsaame.pas.vo.bus.PolicyVO;

public class TradeLicenceDAO extends BaseDBDAO implements ITradeLicenceDAO {

	@Override
	public BaseVO getQuoteDetails(BaseVO baseVO) {
		
		PolicyVO pol = (PolicyVO) baseVO;
		
		Boolean isQuote = true;
		Long quoLinkingId = 0L;
		//Radar fix
		List<Long> quoEndtList = null; //new ArrayList<Long>();
		
		Long polLinkingId = 0L;
		List<Long> polEndtList = new ArrayList<Long>();
		
		quoLinkingId = DAOUtils.getLinkingIdOfQuo(pol,getHibernateTemplate(),isQuote);
		quoEndtList = DAOUtils.getEndtIdOfQuoTL(pol,getHibernateTemplate(),isQuote);
		
		if(!pol.getIsQuote()){
			isQuote = false;
			polLinkingId = DAOUtils.getLinkingIdOfQuo(pol,getHibernateTemplate(),isQuote);
			polEndtList = DAOUtils.getEndtIdOfQuoTL(pol,getHibernateTemplate(),isQuote);
		}
		
     	DataHolderVO<Object[]> holder = new DataHolderVO<Object[]>();
		Object quoteDetails[] = new Object[4];
		
		quoteDetails[0] = quoLinkingId;
		quoteDetails[1] = quoEndtList;
		
		quoteDetails[2] = polLinkingId;
		quoteDetails[3] = polEndtList;
		
		holder.setData(quoteDetails);
		
		return holder;
	}

}
