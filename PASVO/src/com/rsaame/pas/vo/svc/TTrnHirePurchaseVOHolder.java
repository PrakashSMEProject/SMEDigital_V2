package com.rsaame.pas.vo.svc;

import java.math.BigDecimal;
import java.util.Date;
import com.rsaame.pas.vo.bus.PremiumVO;

public class TTrnHirePurchaseVOHolder extends PremiumVO{

	private static final long serialVersionUID = 3611415384607036907L;
	private long hpPolicyId;
	private long hpEndtId;
	private Date hpValidityStartDate;
	private BigDecimal hpAmount;
	private Date hpExpiryDate;
	private Integer hpCode;
	private Date hpValidityExpiryDate;
	private Byte hpStatus;
	private Integer hpPreparedBy;
	private Date hpPreparedDt;
	private Integer hpModifiedBy;
	private Date hpModifiedDt;
	public long getHpPolicyId() {
		return hpPolicyId;
	}
	public void setHpPolicyId(long hpPolicyId) {
		this.hpPolicyId = hpPolicyId;
	}
	public long getHpEndtId() {
		return hpEndtId;
	}
	public void setHpEndtId(long hpEndtId) {
		this.hpEndtId = hpEndtId;
	}
	public Date getHpValidityStartDate() {
		return hpValidityStartDate;
	}
	public void setHpValidityStartDate(Date hpValidityStartDate) {
		this.hpValidityStartDate = hpValidityStartDate;
	}
	public BigDecimal getHpAmount() {
		return hpAmount;
	}
	public void setHpAmount(BigDecimal hpAmount) {
		this.hpAmount = hpAmount;
	}
	public Date getHpExpiryDate() {
		return hpExpiryDate;
	}
	public void setHpExpiryDate(Date hpExpiryDate) {
		this.hpExpiryDate = hpExpiryDate;
	}
	public Integer getHpCode() {
		return hpCode;
	}
	public void setHpCode(Integer hpCode) {
		this.hpCode = hpCode;
	}
	public Date getHpValidityExpiryDate() {
		return hpValidityExpiryDate;
	}
	public void setHpValidityExpiryDate(Date hpValidityExpiryDate) {
		this.hpValidityExpiryDate = hpValidityExpiryDate;
	}
	public Byte getHpStatus() {
		return hpStatus;
	}
	public void setHpStatus(Byte hpStatus) {
		this.hpStatus = hpStatus;
	}
	public Integer getHpPreparedBy() {
		return hpPreparedBy;
	}
	public void setHpPreparedBy(Integer hpPreparedBy) {
		this.hpPreparedBy = hpPreparedBy;
	}
	public Date getHpPreparedDt() {
		return hpPreparedDt;
	}
	public void setHpPreparedDt(Date hpPreparedDt) {
		this.hpPreparedDt = hpPreparedDt;
	}
	public Integer getHpModifiedBy() {
		return hpModifiedBy;
	}
	public void setHpModifiedBy(Integer hpModifiedBy) {
		this.hpModifiedBy = hpModifiedBy;
	}
	public Date getHpModifiedDt() {
		return hpModifiedDt;
	}
	public void setHpModifiedDt(Date hpModifiedDt) {
		this.hpModifiedDt = hpModifiedDt;
	}
	
	

}
