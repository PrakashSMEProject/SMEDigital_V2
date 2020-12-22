package com.rsaame.pas.renewals.dao;

import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.model.TTrnPrintBatchPas;
import com.rsaame.pas.dao.model.TTrnRenewalBatchEplatform;
import com.rsaame.pas.vo.app.PrintDocVO;

public interface IRenewalsDAO {
public abstract BaseVO getPoliciesToBeRenewed (BaseVO criteria);
public abstract BaseVO getRenewalQuotations (BaseVO criteria);
//public abstract BigDecimal getPolicyIEV(Long policyId)
//public abstract savePoliciesForBatchEmail(EmailBatch)
public abstract void savePoliciesForBatchRenewal(BaseVO baseVO);
public abstract BaseVO generateOnlineRenewal(BaseVO baseVO);
public abstract void updateRenewalPremium(BaseVO baseVO);
public abstract void updateQuotationStatus(BaseVO baseVO);
public abstract BaseVO getClaimCount(BaseVO baseVO);
public abstract List<TTrnRenewalBatchEplatform> getRenewalBatch();
public BaseVO getEndorsementData( BaseVO baseVO );
public BaseVO  getOSPremium( BaseVO baseVO ); 
public abstract void savePoliciesForBatchPrint(BaseVO baseVO);
public abstract BaseVO checkForReprint(BaseVO baseVO);
public abstract List<TTrnPrintBatchPas> getRenewalBatchPrint();
public abstract void updateBatchPrintStatus(PrintDocVO printDocVo);
public BaseVO  getDisLoadPer( BaseVO baseVO );
public BaseVO getBrAccStatus(BaseVO baseVO);
public void updatePrintRecord(BaseVO baseVO) ;
public abstract void updateRenewalTerms(BaseVO baseVO);
public BaseVO fetchPolicyExpDate(BaseVO baseVO);
public BaseVO generateRenewal( BaseVO baseVO );
public BaseVO fetchRenewalStatusReport(BaseVO baseVO);
public BaseVO getDisLoadPerFromQuo(BaseVO baseVO);
public abstract void checkRenewalTradeLicense(BaseVO baseVO);
public abstract BaseVO getRenewalQuotationsForEmail (BaseVO criteria);

}
