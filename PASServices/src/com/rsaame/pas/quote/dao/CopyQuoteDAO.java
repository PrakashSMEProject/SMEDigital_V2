package com.rsaame.pas.quote.dao;

import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.CopyQuoteProc;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.CopyQuoteVO;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.cmn.CommonVO;

public class CopyQuoteDAO extends BaseDBDAO implements ICopyQuoteDAO {

	public BaseVO copyQuote(BaseVO baseVO) {
		CopyQuoteProc copyQuoteProc = (CopyQuoteProc) Utils
				.getBean("copyQuoteProc");

		if (!Utils.isEmpty(baseVO))
			baseVO = copyQuoteProc.copyQuote(baseVO);
		CopyQuoteVO copyQuoteVO = (CopyQuoteVO) baseVO;

		updatePolicyRecords(copyQuoteVO);

		return baseVO;
	}

	private void updatePolicyRecords(CopyQuoteVO copyQuoteVO) {

		Integer issueHour = Integer.valueOf( Utils.getSingleValueAppConfig( "SBS_POLICY_ISSUE_HOUR" ) );
		Short polType = Short.valueOf( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) );
		
		List<TTrnPolicyQuo> polRecords = (List<TTrnPolicyQuo>) getHibernateTemplate()
				.find("from TTrnPolicyQuo polQuo where polQuo.polLinkingId=? and  polQuo.polIssueHour=? and polQuo.polPolicyType=? ",copyQuoteVO.getNewPolLinkingId(),
						issueHour	, polType);
		
		if( !Utils.isEmpty( polRecords )){
			for (TTrnPolicyQuo tTrnPolicyQuo : polRecords) {
				if( copyQuoteVO.getIsNewCustomer() ){
					tTrnPolicyQuo.setPolBusinessType( Short.valueOf( "1" ) );
				}else{
					tTrnPolicyQuo.setPolBusinessType( Short.valueOf( "2" ) );
				}
				getHibernateTemplate().saveOrUpdate(tTrnPolicyQuo);				
			}
		}
	}
	
	/* 
	 * This method calls the copy quote SP for Home/Travel and generates the commonVO with copied quote details
	 */
	public BaseVO copyQuoteCommon(BaseVO baseVO) {
		CopyQuoteVO copyQuoteVO = (CopyQuoteVO) baseVO;
		String polType = String.valueOf( copyQuoteVO.getPolType() );
		PASStoredProcedure sp = null;
		if( polType.equals( SvcConstants.HOME_POL_TYPE ) ){
			sp = (PASStoredProcedure) Utils.getBean( "copyQuoteHome" );
		}
		else if(polType.equals( SvcConstants.SHORT_TRAVEL_POL_TYPE ) || polType.equals( SvcConstants.LONG_TRAVEL_POL_TYPE )){ 
			sp = (PASStoredProcedure) Utils.getBean( "copyQuoteTravel" );
		}
		else
		{
			sp = (PASStoredProcedure) Utils.getBean( "copyQuoteMonoline" );
		}
		
		
		Map results = sp.call( copyQuoteVO.getPolPolicyId(), SvcConstants.CLIENT_ID, copyQuoteVO.getInsuredId(), copyQuoteVO.getUserId(), copyQuoteVO.getLocationCode(),copyQuoteVO.getIsNewCustomer()?1:2,copyQuoteVO.getDeletePolicyId() );
		Long newQuoteNo;
		if(polType.equals( SvcConstants.HOME_POL_TYPE )||polType.equals( SvcConstants.SHORT_TRAVEL_POL_TYPE ) || polType.equals( SvcConstants.LONG_TRAVEL_POL_TYPE ))
		{
			newQuoteNo= ( (java.math.BigDecimal) results.get( "po_home_quo_no" ) ).longValue();
		}
		else
		{
			newQuoteNo= ( (java.math.BigDecimal) results.get( "po_quo_no" ) ).longValue();
		}
		
		CommonVO loadData = new CommonVO();
		loadData.setQuoteNo( newQuoteNo );
		loadData.setIsQuote( true );
		loadData.setLocCode( copyQuoteVO.getLocationCode() );
		loadData.setEndtId( Long.valueOf( SvcConstants.zeroVal ) );
		loadData.setEndtNo( Long.valueOf( SvcConstants.zeroVal ));
		loadData.setStatus( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_PENDING ) ) );
		loadData.setAppFlow( Flow.VIEW_QUO );	
		return DAOUtils.populateCommonDetails( loadData, getHibernateTemplate() );

	}
}
