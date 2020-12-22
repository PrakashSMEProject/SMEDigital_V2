/**
 * 
 */
package com.rsaame.pas.vo.svc;

import java.math.BigDecimal;
import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author Sarath Varier
 * @since WC Monoline
 *
 */
public class TTrnWctplUnnamedPersonQuoHolder extends BaseVO {

	private static final long serialVersionUID = -2926361411876444422L;

	private Date wupValidityStartDate;
	private long wupId;
	private Long wupPolicyId;
	private Long wupBasicRiskId;
	private Long wupRskCode;
	private Short wupRtCode;
	private Short wupRcCode;
	private Long wupNoOfPerson;
	private BigDecimal wupSumInsured;
	private Byte wupStatus;
	private Short wupEmploymentType;
	private String wupEmployerEName;
	private String wupEmployerAName;
	private Date wupValidityExpiryDate;
	private Long wupEndtId;
	private Integer wupRiRskCode;
	private Integer wupBasicRskCode;
	private String wupPlaceOfWork;
	private BigDecimal wupSalary;
	private Integer wupPreparedBy;
	private Date wupPreparedDt;
	private Integer wupModifiedBy;
	private Date wupModifiedDt;
	private Date wupStartDate;
	private Date wupEndDate;
	private Long wupHazard;
	private Long wupTradeGroup;
	private Long wupBldId;
	private Short wupTerCode;
	private Short wupJurCode;
	private String wupFreeZone;
	private String wupIndemnityDesc;
	private String wupJurDesc;
	private BigDecimal wupEmpLiabLmt;
	
	@Override
	public Object getFieldValue(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getWupValidityStartDate() {
		return wupValidityStartDate;
	}

	public void setWupValidityStartDate(Date wupValidityStartDate) {
		this.wupValidityStartDate = wupValidityStartDate;
	}

	public long getWupId() {
		return wupId;
	}

	public void setWupId(long wupId) {
		this.wupId = wupId;
	}

	public Long getWupPolicyId() {
		return wupPolicyId;
	}

	public void setWupPolicyId(Long wupPolicyId) {
		this.wupPolicyId = wupPolicyId;
	}

	public Long getWupBasicRiskId() {
		return wupBasicRiskId;
	}

	public void setWupBasicRiskId(Long wupBasicRiskId) {
		this.wupBasicRiskId = wupBasicRiskId;
	}

	public Long getWupRskCode() {
		return wupRskCode;
	}

	public void setWupRskCode(Long wupRskCode) {
		this.wupRskCode = wupRskCode;
	}

	public Short getWupRtCode() {
		return wupRtCode;
	}

	public void setWupRtCode(Short wupRtCode) {
		this.wupRtCode = wupRtCode;
	}

	public Short getWupRcCode() {
		return wupRcCode;
	}

	public void setWupRcCode(Short wupRcCode) {
		this.wupRcCode = wupRcCode;
	}

	public Long getWupNoOfPerson() {
		return wupNoOfPerson;
	}

	public void setWupNoOfPerson(Long wupNoOfPerson) {
		this.wupNoOfPerson = wupNoOfPerson;
	}

	public BigDecimal getWupSumInsured() {
		return wupSumInsured;
	}

	public void setWupSumInsured(BigDecimal wupSumInsured) {
		this.wupSumInsured = wupSumInsured;
	}

	public Byte getWupStatus() {
		return wupStatus;
	}

	public void setWupStatus(Byte wupStatus) {
		this.wupStatus = wupStatus;
	}

	public Short getWupEmploymentType() {
		return wupEmploymentType;
	}

	public void setWupEmploymentType(Short wupEmploymentType) {
		this.wupEmploymentType = wupEmploymentType;
	}

	public String getWupEmployerEName() {
		return wupEmployerEName;
	}

	public void setWupEmployerEName(String wupEmployerEName) {
		this.wupEmployerEName = wupEmployerEName;
	}

	public String getWupEmployerAName() {
		return wupEmployerAName;
	}

	public void setWupEmployerAName(String wupEmployerAName) {
		this.wupEmployerAName = wupEmployerAName;
	}

	public Date getWupValidityExpiryDate() {
		return wupValidityExpiryDate;
	}

	public void setWupValidityExpiryDate(Date wupValidityExpiryDate) {
		this.wupValidityExpiryDate = wupValidityExpiryDate;
	}

	public Long getWupEndtId() {
		return wupEndtId;
	}

	public void setWupEndtId(Long wupEndtId) {
		this.wupEndtId = wupEndtId;
	}

	public Integer getWupRiRskCode() {
		return wupRiRskCode;
	}

	public void setWupRiRskCode(Integer wupRiRskCode) {
		this.wupRiRskCode = wupRiRskCode;
	}

	public Integer getWupBasicRskCode() {
		return wupBasicRskCode;
	}

	public void setWupBasicRskCode(Integer wupBasicRskCode) {
		this.wupBasicRskCode = wupBasicRskCode;
	}

	public String getWupPlaceOfWork() {
		return wupPlaceOfWork;
	}

	public void setWupPlaceOfWork(String wupPlaceOfWork) {
		this.wupPlaceOfWork = wupPlaceOfWork;
	}

	public BigDecimal getWupSalary() {
		return wupSalary;
	}

	public void setWupSalary(BigDecimal wupSalary) {
		this.wupSalary = wupSalary;
	}

	public Integer getWupPreparedBy() {
		return wupPreparedBy;
	}

	public void setWupPreparedBy(Integer wupPreparedBy) {
		this.wupPreparedBy = wupPreparedBy;
	}

	public Date getWupPreparedDt() {
		return wupPreparedDt;
	}

	public void setWupPreparedDt(Date wupPreparedDt) {
		this.wupPreparedDt = wupPreparedDt;
	}

	public Integer getWupModifiedBy() {
		return wupModifiedBy;
	}

	public void setWupModifiedBy(Integer wupModifiedBy) {
		this.wupModifiedBy = wupModifiedBy;
	}

	public Date getWupModifiedDt() {
		return wupModifiedDt;
	}

	public void setWupModifiedDt(Date wupModifiedDt) {
		this.wupModifiedDt = wupModifiedDt;
	}

	public Date getWupStartDate() {
		return wupStartDate;
	}

	public void setWupStartDate(Date wupStartDate) {
		this.wupStartDate = wupStartDate;
	}

	public Date getWupEndDate() {
		return wupEndDate;
	}

	public void setWupEndDate(Date wupEndDate) {
		this.wupEndDate = wupEndDate;
	}

	public Long getWupHazard() {
		return wupHazard;
	}

	public void setWupHazard(Long wupHazard) {
		this.wupHazard = wupHazard;
	}

	public Long getWupTradeGroup() {
		return wupTradeGroup;
	}

	public void setWupTradeGroup(Long wupTradeGroup) {
		this.wupTradeGroup = wupTradeGroup;
	}

	public Long getWupBldId() {
		return wupBldId;
	}

	public void setWupBldId(Long wupBldId) {
		this.wupBldId = wupBldId;
	}

	public Short getWupTerCode() {
		return wupTerCode;
	}

	public void setWupTerCode(Short wupTerCode) {
		this.wupTerCode = wupTerCode;
	}

	public Short getWupJurCode() {
		return wupJurCode;
	}

	public void setWupJurCode(Short wupJurCode) {
		this.wupJurCode = wupJurCode;
	}

	public String getWupFreeZone() {
		return wupFreeZone;
	}

	public void setWupFreeZone(String wupFreeZone) {
		this.wupFreeZone = wupFreeZone;
	}

	public String getWupIndemnityDesc() {
		return wupIndemnityDesc;
	}

	public void setWupIndemnityDesc(String wupIndemnityDesc) {
		this.wupIndemnityDesc = wupIndemnityDesc;
	}

	public String getWupJurDesc() {
		return wupJurDesc;
	}

	public void setWupJurDesc(String wupJurDesc) {
		this.wupJurDesc = wupJurDesc;
	}

	public BigDecimal getWupEmpLiabLmt() {
		return wupEmpLiabLmt;
	}

	public void setWupEmpLiabLmt(BigDecimal wupEmpLiabLmt) {
		this.wupEmpLiabLmt = wupEmpLiabLmt;
	}
	
	 @Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((wupBasicRiskId == null) ? 0 : wupBasicRiskId.hashCode());
			result = prime * result
					+ ((wupBasicRskCode == null) ? 0 : wupBasicRskCode.hashCode());
			result = prime
					* result
					+ ((wupEmploymentType == null) ? 0 : wupEmploymentType
							.hashCode());
			result = prime * result
					+ ((wupEndtId == null) ? 0 : wupEndtId.hashCode());
			result = prime * result + (int) (wupId ^ (wupId >>> 32));
			return result;
		}
	
	 @Override
	 public boolean equals(Object object) {
	        boolean sameSame = false;	        
	        if (object != null && object instanceof TableData)
	        {
	        	if(this.wupId == ((TTrnWctplUnnamedPersonQuoHolder)((TableData)object).getTableData()).getWupId())
	        	{
	        		sameSame = true;
	        	}
	        	else if(this.wupEmploymentType == ((TTrnWctplUnnamedPersonQuoHolder)((TableData)object).getTableData()).getWupEmploymentType())
	        	{
	        		sameSame = true;
	        	}
	        }
	        return sameSame;
	 }
	 
  
}
