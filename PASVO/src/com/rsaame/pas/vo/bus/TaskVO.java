package com.rsaame.pas.vo.bus;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;

public class TaskVO extends BaseVO implements IPolQuoType{

	private static final long serialVersionUID = 1L;

	private Boolean isOpen;
	
	private Long taskID;
	private String taskName;
	private Date createdDate;
	//private String createdBy;
	private String assignedBy;
	private String assignedTo;
	private String Status;
	private String Priority;
	private String category;
	private Date dueDate;
	private String location; 
	private String desc;
	private String policyType;
	private String taskType;
	private Long polLinkingId; // Policy ID will be stored here in case of HOME and TRAVEL
	private Long polEndId;
	private String tranType;
	//Phase 3 changes
	private String lob;
	private long policyNo;
	private long quoteNo;
	
	private byte classCode;
	
	

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	/* isQuote variable is introduced here to switch the sessionFactory while saving the task details 
	 * for the cases of quote creation and policy endorsement.
	 * 1. Default value is false because in case of home page load it is always considered as quote flow
	 */
	private Boolean isQuote=true;
	
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "isOpen".equals( fieldName ) ) fieldValue = getIsOpen();
		if( "taskID".equals( fieldName ) ) fieldValue = getTaskID();
		if( "taskName".equals( fieldName ) ) fieldValue = getTaskName();
		if( "createdDate".equals( fieldName ) ) fieldValue = getCreatedDate();
		if( "createdBy".equals( fieldName ) ) fieldValue = getCreatedBy();
		if( "assignedBy".equals( fieldName ) ) fieldValue = getAssignedBy();
		if( "assignedTo".equals( fieldName ) ) fieldValue = getAssignedTo();
		if( "Status".equals( fieldName ) ) fieldValue = getStatus();
		if( "Priority".equals( fieldName ) ) fieldValue = getPriority();
		if( "category".equals( fieldName ) ) fieldValue = getCategory();
		if( "dueDate".equals( fieldName ) ) fieldValue = getDueDate();
		if( "location".equals( fieldName ) ) fieldValue = getLocation();
		if( "desc".equals( fieldName ) ) fieldValue = getDesc();
		if( "classCode".equals( fieldName ) ) fieldValue = getClassCode();

		return fieldValue;
	}
	
	public Boolean getIsOpen(){
		return isOpen;
	}
	public void setIsOpen( Boolean isOpen ){
		this.isOpen = isOpen;
	}
	public Long getTaskID(){
		return taskID;
	}
	public void setTaskID( Long taskID ){
		this.taskID = taskID;
	}
	public String getTaskName(){
		return taskName;
	}
	public void setTaskName( String taskName ){
		this.taskName = taskName;
	}
	public Date getCreatedDate(){
		return createdDate;
	}
	public void setCreatedDate( Date createdDate ){
		this.createdDate = createdDate;
	}

//	public String getCreatedBy(){
//		return createdBy;
//	}
//
//	public void setCreatedBy( String createdBy ){
//		this.createdBy = createdBy;
//	}

	public String getAssignedBy(){
		return assignedBy;
	}
	public void setAssignedBy( String assignedBy ){
		this.assignedBy = assignedBy;
	}
	public String getAssignedTo(){
		return assignedTo;
	}
	public void setAssignedTo( String assignedTo ){
		this.assignedTo = assignedTo;
	}
	public String getStatus(){
		return Status;
	}
	public void setStatus( String status ){
		Status = status;
	}
	public String getPriority(){
		return Priority;
	}
	public void setPriority( String priority ){
		Priority = priority;
	}
	public String getCategory(){
		return category;
	}
	public void setCategory( String category ){
		this.category = category;
	}
	public Date getDueDate(){
		return dueDate;
	}
	public void setDueDate( Date dueDate ){
		this.dueDate = dueDate;
	}
	public String getLocation(){
		return location;
	}
	public void setLocation( String location ){
		this.location = location;
	}
	public String getDesc(){
		return desc;
	}
	public void setDesc( String desc ){
		this.desc = desc;
	}

	/**
	 * @return the taskType
	 */
	public String getTaskType() {
		return taskType;
	}

	/**
	 * @param taskType the taskType to set
	 */
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	/**
	 * @return the polLinkingId
	 */
	public Long getPolLinkingId() {
		return polLinkingId;
	}

	/**
	 * @param polLinkingId the polLinkingId to set
	 */
	public void setPolLinkingId(Long polLinkingId) {
		this.polLinkingId = polLinkingId;
	}

	/**
	 * @return the polEndId
	 */
	public Long getPolEndId() {
		return polEndId;
	}

	/**
	 * @param polEndId the polEndId to set
	 */
	public void setPolEndId(Long polEndId) {
		this.polEndId = polEndId;
	}
	
	

	/**
	 * @return the policyType
	 */
	public String getPolicyType() {
		return policyType;
	}

	/**
	 * @param policyType the policyType to set
	 */
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TaskVO [isOpen=" + isOpen + ", taskID=" + taskID
				+ ", taskName=" + taskName + ", createdDate=" + createdDate
				+ ", createdBy=" + getCreatedBy() + ", assignedBy=" + assignedBy
				+ ", assignedTo=" + assignedTo + ", Status=" + Status
				+ ", Priority=" + Priority + ", category=" + category
				+ ", dueDate=" + dueDate + ", location=" + location + ", desc="
				+ desc + ", taskType=" + taskType + ", polLinkingId="
				+ polLinkingId + ", polEndId=" + polEndId + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((taskID == null) ? 0 : taskID.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TaskVO)) {
			return false;
		}
		TaskVO other = (TaskVO) obj;
		if (taskID == null) {
			if (other.taskID != null) {
				return false;
			}
		} else if (!taskID.equals(other.taskID)) {
			return false;
		}
		return true;
	}
	
	@Override
	public Boolean isQuote(){
		return isQuote;
	}


	@Override
	public void setQuote( Boolean isQuote ){
		this.isQuote = isQuote;		
	}
	/**
	 * @return the lob
	 */
	public String getLob(){
		return lob;
	}

	/**
	 * @param lob the lob to set
	 */
	public void setLob( String lob ){
		this.lob = lob;
	}

	/**
	 * @return the policyNo
	 */
	public long getPolicyNo(){
		return policyNo;
	}

	/**
	 * @param policyNo the policyNo to set
	 */
	public void setPolicyNo( long policyNo ){
		this.policyNo = policyNo;
	}

	/**
	 * @return the quoteNo
	 */
	public long getQuoteNo(){
		return quoteNo;
	}

	/**
	 * @param quoteNo the quoteNo to set
	 */
	public void setQuoteNo( long quoteNo ){
		this.quoteNo = quoteNo;
	}

	/**
	 * @return the classCode
	 */
	public byte getClassCode() {
		return classCode;
	}

	/**
	 * @param classCode the classCode to set
	 */
	public void setClassCode(byte classCode) {
		this.classCode = classCode;
	}
	
}
