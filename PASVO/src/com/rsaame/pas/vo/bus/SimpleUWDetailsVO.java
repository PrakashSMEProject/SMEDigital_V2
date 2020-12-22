


package com.rsaame.pas.vo.bus;

import java.math.BigDecimal;
import java.util.Date;


public class SimpleUWDetailsVO extends UWDetails {
	private static final long serialVersionUID = 1L;
	private Date startDate;
	private Date endDate;
	private Integer status;
	
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "startDate".equals( fieldName ) ) fieldValue = getStartDate();
		if( "endDate".equals( fieldName ) ) fieldValue = getEndDate();
		if( "status".equals( fieldName ) ) fieldValue = getStatus();

		return fieldValue;
	}
	
	public Date getStartDate(){
		return startDate;
	}
	public void setStartDate( Date startDate ){
		this.startDate = startDate;
	}
	public Date getEndDate(){
		return endDate;
	}
	public void setEndDate( Date endDate ){
		this.endDate = endDate;
		
	}
	public Integer getStatus(){
		return status;
	}
	public void setStatus( Integer status ){
		this.status = status;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SimpleUWDetailsVO [startDate=" + startDate + ", endDate="
				+ endDate + ", status=" + status + "]";
	}
	
	
}
