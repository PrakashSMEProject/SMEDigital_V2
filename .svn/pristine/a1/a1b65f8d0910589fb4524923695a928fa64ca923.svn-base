package com.rsaame.pas.dao.cmn;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.svc.TTrnPremiumVOHolder;

public interface ICommonOpDAO {

	public abstract BaseVO  getCommision(BaseVO baseVO);
	public abstract BaseVO  setPrepackedFlag(BaseVO baseVO);
	public abstract BaseVO  getPolLinkID(BaseVO baseVO);
	public abstract BaseVO  generateQuotationNo(BaseVO baseVO);
	public abstract BaseVO  generateInsuredId(BaseVO baseVO);
	public abstract BaseVO getUserDetails(Integer userId);
	public abstract BaseVO editQuoteUpdateStatusToPending(BaseVO baseVO);
	public abstract BaseVO handleReferralMessages(BaseVO dataVO );
	public abstract BaseVO isEndorsementRecordExist(BaseVO baseVO);
	public abstract BaseVO getNoticeBoardItems(BaseVO baseVO);
	public abstract BaseVO upadteTradeLicNo(BaseVO baseVO);
	public abstract BaseVO populateCommonDetails( BaseVO baseVO );
	public abstract BaseVO fetchPolicyRecord(BaseVO baseVO);
	public abstract TTrnPremiumVOHolder getPremiumSpecialCoverRecs( CommonVO commonVO );
	public abstract Object[] getSpecialCodes( TTrnPremiumVOHolder trnPremiumQuoVOHolder ); 
	public abstract void activateQuote(BaseVO baseVO);
	public abstract BaseVO getNextEndorsementId(BaseVO baseVO);
	public abstract void callTariffChangeProcedure(BaseVO baseVO);
	public abstract BaseVO isMortgageeExists(BaseVO baseVO);
	public abstract BaseVO getClauseForCurrentEndtId(BaseVO baseVO,BaseVO baseVO2);
	public abstract BaseVO validatePromoCode( BaseVO baseVO );
	public abstract BaseVO getPrevEndtIdForPendingPolicy(BaseVO baseVO);
	public abstract BaseVO getPolicyIdForPolicy(BaseVO baseVO);
	public abstract BaseVO getPolicyTypeCurrencyScaleMap();
	public abstract BaseVO getLegacyPolicies(BaseVO baseVO);
	public abstract BaseVO getRenQuoteForPolicy(BaseVO baseVO);
	public abstract BaseVO getforgotPassword( BaseVO baseVO );
	public abstract BaseVO updatePassword( BaseVO baseVO );
	public BaseVO getUserRoles( BaseVO baseVO );	
	public BaseVO getUpdatedPoBox( BaseVO baseVO );
	/*Wunderman WebServices fix*/
	public abstract BaseVO getQuoteForPolicy(BaseVO baseVO);
	
	//VAT new screen
	public BaseVO updateVATRegNo( BaseVO baseVO );
}