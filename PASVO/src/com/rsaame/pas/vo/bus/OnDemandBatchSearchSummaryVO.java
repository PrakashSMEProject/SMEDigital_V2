/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author M1037404
 *
 */
public class OnDemandBatchSearchSummaryVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	private Integer numberOfRecords;
	private Integer recordsPerPage;
	private Integer currentPage;
	
	private List <EplatformWsStagingVO> webServiceAudits =   new com.mindtree.ruc.cmn.utils.List<EplatformWsStagingVO>(EplatformWsStagingVO.class);
	
	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	public Integer getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(Integer recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public List<EplatformWsStagingVO> getWebServiceAudits() {
		return webServiceAudits;
	}

	public void setWebServiceAudits(List<EplatformWsStagingVO> webServiceAudits) {
		this.webServiceAudits = webServiceAudits;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentPage == null) ? 0 : currentPage.hashCode());
		result = prime * result + ((numberOfRecords == null) ? 0 : numberOfRecords.hashCode());
		result = prime * result + ((recordsPerPage == null) ? 0 : recordsPerPage.hashCode());
		result = prime * result + ((webServiceAudits == null) ? 0 : webServiceAudits.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OnDemandBatchSearchSummaryVO other = (OnDemandBatchSearchSummaryVO) obj;
		if (currentPage == null) {
			if (other.currentPage != null)
				return false;
		} else if (!currentPage.equals(other.currentPage))
			return false;
		if (numberOfRecords == null) {
			if (other.numberOfRecords != null)
				return false;
		} else if (!numberOfRecords.equals(other.numberOfRecords))
			return false;
		if (recordsPerPage == null) {
			if (other.recordsPerPage != null)
				return false;
		} else if (!recordsPerPage.equals(other.recordsPerPage))
			return false;
		if (webServiceAudits == null) {
			if (other.webServiceAudits != null)
				return false;
		} else if (!webServiceAudits.equals(other.webServiceAudits))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "OnDemandBatchSearchSummaryVO [numberOfRecords=" + numberOfRecords + ", recordsPerPage=" + recordsPerPage
				+ ", currentPage=" + currentPage + ", webServiceAudits=" + webServiceAudits + "]";
	}

	@Override
	public Object getFieldValue(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
