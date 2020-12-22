/**
 * 
 */
package com.rsaame.pas.vo.app;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author M1014957
 *
 */
public class BrokerAcctResultVO extends BaseVO {
	
	private String BROKERNAME;
	private String ADDRESS;
	private Integer FAX;
	private Integer PHONE;
	private String LOC_DESC;
	private String CUSTOMERCATEGORY;
	private String INSUREDNAME;
	private String TRANSACTIONTYPE;
	private String VOUCHERNO;
	private String VOUCHERDATE;
	//private Integer POLICYNO;
	private Long POLICYNO;
	private BigDecimal GROSS;
	private BigDecimal NET;
	private BigDecimal COMAMOUNT;
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IFieldValue#getFieldValue(java.lang.String)
	 */
	@Override
	public Object getFieldValue(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @return the iNSUREDNAME
	 */
	public String getINSUREDNAME() {
		return INSUREDNAME;
	}

	/**
	 * @param iNSUREDNAME the iNSUREDNAME to set
	 */
	public void setINSUREDNAME(String iNSUREDNAME) {
		INSUREDNAME = iNSUREDNAME;
	}
	/**
	 * @return the bROKERNAME
	 */
	public String getBROKERNAME() {
		return BROKERNAME;
	}

	/**
	 * @param bROKERNAME the bROKERNAME to set
	 */
	public void setBROKERNAME(String bROKERNAME) {
		if(!"null".equals(bROKERNAME)){
			BROKERNAME = bROKERNAME;
			
		}else{
			BROKERNAME = "";
		}
	}

	/**
	 * @return the aDDRESS
	 */
	public String getADDRESS() {
		return ADDRESS;
	}

	/**
	 * @param aDDRESS the aDDRESS to set
	 */
	public void setADDRESS(String aDDRESS) {
		if(!"null".equals(aDDRESS)){
			ADDRESS = aDDRESS;
			
		}else{
			ADDRESS = "";
		}
	}

	/**
	 * @return the fAX
	 */
	public Integer getFAX() {
		return FAX;
	}

	/**
	 * @param fAX the fAX to set
	 */
	public void setFAX(Integer fAX) {
		FAX = fAX;
	}

	/**
	 * @return the pHONE
	 */
	public Integer getPHONE() {
		return PHONE;
	}

	/**
	 * @param pHONE the pHONE to set
	 */
	public void setPHONE(Integer pHONE) {
		PHONE = pHONE;
	}

	/**
	 * @return the lOC_DESC
	 */
	public String getLOC_DESC() {
		return LOC_DESC;
	}

	/**
	 * @param lOC_DESC the lOC_DESC to set
	 */
	public void setLOC_DESC(String lOC_DESC) {
		if(!"null".equals(lOC_DESC)){
			LOC_DESC = lOC_DESC;
			
		}else{
			LOC_DESC = "";
		}
	}

	/**
	 * @return the cUSTOMERCATEGORY
	 */
	public String getCUSTOMERCATEGORY() {
		return CUSTOMERCATEGORY;
	}

	/**
	 * @param cUSTOMERCATEGORY the cUSTOMERCATEGORY to set
	 */
	public void setCUSTOMERCATEGORY(String cUSTOMERCATEGORY) {
		if(!"null".equals(cUSTOMERCATEGORY)){
			CUSTOMERCATEGORY = cUSTOMERCATEGORY;
			
		}else{
			CUSTOMERCATEGORY = "";
		}
	}

	/**
	 * @return the tRANSACTIONTYPE
	 */
	public String getTRANSACTIONTYPE() {
		return TRANSACTIONTYPE;
	}

	/**
	 * @param tRANSACTIONTYPE the tRANSACTIONTYPE to set
	 */
	public void setTRANSACTIONTYPE(String tRANSACTIONTYPE) {
		if(!"null".equals(tRANSACTIONTYPE)){
			TRANSACTIONTYPE = tRANSACTIONTYPE;
		}else{
			TRANSACTIONTYPE = "";
		}
	}

	/**
	 * @return the vOUCHERNO
	 */
	public String getVOUCHERNO() {
		return VOUCHERNO;
	}

	/**
	 * @param vOUCHERNO the vOUCHERNO to set
	 */
	public void setVOUCHERNO(String vOUCHERNO) {
		if(!"null".equals(vOUCHERNO)){
			VOUCHERNO = vOUCHERNO;
		}else{
			VOUCHERNO = "";
		}
	}

	/**
	 * @return the vOUCHERDATE
	 */
	public String getVOUCHERDATE() {
		return VOUCHERDATE;
	}

	/**
	 * @param vOUCHERDATE the vOUCHERDATE to set
	 */
	public void setVOUCHERDATE(String vOUCHERDATE) {
		if(!"null".equals(vOUCHERDATE)){
			VOUCHERDATE = vOUCHERDATE;
		}else{
			VOUCHERDATE = "";
		}
	}

	/**
	 * @return the pOLICYNO
	 */
	/*public Integer getPOLICYNO() {
		return POLICYNO;
	}*/
	public Long getPOLICYNO() {
		return POLICYNO;
	}

	/**
	 * @param pOLICYNO the pOLICYNO to set
	 */
	/*public void setPOLICYNO(Integer pOLICYNO) {
		POLICYNO = pOLICYNO;
	}*/
	public void setPOLICYNO(Long policyNO) {
		POLICYNO = policyNO;
	}

	/**
	 * @return the gROSS
	 */
	public BigDecimal getGROSS() {
		return GROSS;
	}

	/**
	 * @param gROSS the gROSS to set
	 */
	public void setGROSS(BigDecimal gROSS) {
		GROSS = gROSS;
	}

	/**
	 * @return the nET
	 */
	public BigDecimal getNET() {
		return NET;
	}

	/**
	 * @param nET the nET to set
	 */
	public void setNET(BigDecimal nET) {
		NET = nET;
	}

	/**
	 * @return the cOMAMOUNT
	 */
	public BigDecimal getCOMAMOUNT() {
		return COMAMOUNT;
	}

	/**
	 * @param cOMAMOUNT the cOMAMOUNT to set
	 */
	public void setCOMAMOUNT(BigDecimal cOMAMOUNT) {
		COMAMOUNT = cOMAMOUNT;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BrokerAcctResultVO [BROKERNAME=" + BROKERNAME + ", ADDRESS="
				+ ADDRESS + ", FAX=" + FAX + ", PHONE=" + PHONE + ", LOC_DESC="
				+ LOC_DESC + ", CUSTOMERCATEGORY=" + CUSTOMERCATEGORY + ", INSUREDNAME="+INSUREDNAME
				+ ", TRANSACTIONTYPE=" + TRANSACTIONTYPE + ", VOUCHERNO="
				+ VOUCHERNO + ", VOUCHERDATE=" + VOUCHERDATE + ", POLICYNO="
				+ POLICYNO + ", GROSS=" + GROSS + ", NET=" + NET
				+ ", COMAMOUNT=" + COMAMOUNT + "]";
	}
	
	

}
