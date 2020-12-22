/**
 * Created POJO for DB view V_TASK_LIST_PAS which holds the task details in case of referral
 */
package com.rsaame.pas.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Dileep
 * @since Phase 3
 *
 */
/**
 * @author m1019193
 *
 */
/**
 * @author m1019193
 *
 */
/**
 * @author m1019193
 *
 */
public class VTaskListPas implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long taskId;
	private String taskName;
	private Integer polPolicyType;
	private String lob;
	private Date dateCreated;
	private Integer createdBy;
	private Integer assignedBy;
	private Integer assignTo;
	private Byte status;
	private Byte priority;
	private Byte category;
	private Date dueDate;
	private Long tskBrCode;
	private String transType;

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public VTaskListPas(){
	}

	/**
	 * @param taskId
	 */
	public VTaskListPas( long taskId ){
		this.taskId = taskId;
	}

	
	/**
	 * @param taskId
	 * @param taskName
	 * @param polPolicyType
	 * @param lob
	 * @param dateCreated
	 * @param createdBy
	 * @param assignedBy
	 * @param assignTo
	 * @param status
	 * @param priority
	 * @param category
	 * @param dueDate
	 * @param tskBrCode
	 */
	public VTaskListPas(Long taskId, String taskName, Integer polPolicyType,
			String lob, Date dateCreated, Integer createdBy,
			Integer assignedBy, Integer assignTo, Byte status, Byte priority,
			Byte category, Date dueDate, Long tskBrCode,String transType) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.polPolicyType = polPolicyType;
		this.lob = lob;
		this.dateCreated = dateCreated;
		this.createdBy = createdBy;
		this.assignedBy = assignedBy;
		this.assignTo = assignTo;
		this.status = status;
		this.priority = priority;
		this.category = category;
		this.dueDate = dueDate;
		this.tskBrCode = tskBrCode;
		this.transType = transType;
	}

	/**
	 * @return
	 */
	public Long getTaskId(){
		return taskId;
	}

	/**
	 * 
	 * @param taskId
	 */
	public void setTaskId( Long taskId ){
		this.taskId = taskId;
	}

	/**
	 * 
	 * @return
	 */
	public String getTaskName(){
		return taskName;
	}

	/**
	 * 
	 * @param taskName
	 */
	public void setTaskName( String taskName ){
		this.taskName = taskName;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getPolPolicyType(){
		return polPolicyType;
	}

	/**
	 * 
	 * @param polPolicyType
	 */
	public void setPolPolicyType( Integer polPolicyType ){
		this.polPolicyType = polPolicyType;
	}

	public String getLob(){
		return lob;
	}

	public void setLob( String lob ){
		this.lob = lob;
	}

	public Date getDateCreated(){
		return dateCreated;
	}

	public void setDateCreated( Date dateCreated ){
		this.dateCreated = dateCreated;
	}

	public Integer getCreatedBy(){
		return createdBy;
	}

	public void setCreatedBy( Integer createdBy ){
		this.createdBy = createdBy;
	}

	public Integer getAssignedBy(){
		return assignedBy;
	}

	public void setAssignedBy( Integer assignedBy ){
		this.assignedBy = assignedBy;
	}

	public Integer getAssignTo(){
		return assignTo;
	}

	public void setAssignTo( Integer assignTo ){
		this.assignTo = assignTo;
	}

	public Byte getStatus(){
		return status;
	}

	public void setStatus( Byte status ){
		this.status = status;
	}

	public Byte getPriority(){
		return priority;
	}

	public void setPriority( Byte priority ){
		this.priority = priority;
	}

	public Byte getCategory(){
		return category;
	}

	public void setCategory( Byte category ){
		this.category = category;
	}

	public Date getDueDate(){
		return dueDate;
	}

	public void setDueDate( Date dueDate ){
		this.dueDate = dueDate;
	}
	
	/**
	 * @return
	 */
	public Long getTskBrCode() {
		return tskBrCode;
	}

	/**
	 * @param tskBrCode
	 */
	public void setTskBrCode(Long tskBrCode) {
		this.tskBrCode = tskBrCode;
	}

	/**
	 * 
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( polPolicyType == null ) ? 0 : polPolicyType.hashCode() );
		result = prime * result + ( ( taskId == null ) ? 0 : taskId.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj ){
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( getClass() != obj.getClass() ) return false;
		VTaskListPas other = (VTaskListPas) obj;
		if( polPolicyType == null ){
			if( other.polPolicyType != null ) return false;
		}
		else if( !polPolicyType.equals( other.polPolicyType ) ) return false;
		if( taskId == null ){
			if( other.taskId != null ) return false;
		}
		else if( !taskId.equals( other.taskId ) ) return false;
		return true;
	}

	@Override
	public String toString() {
		return "VTaskListPas [taskId=" + taskId + ", taskName=" + taskName
				+ ", polPolicyType=" + polPolicyType + ", lob=" + lob
				+ ", dateCreated=" + dateCreated + ", createdBy=" + createdBy
				+ ", assignedBy=" + assignedBy + ", assignTo=" + assignTo
				+ ", status=" + status + ", priority=" + priority
				+ ", category=" + category + ", dueDate=" + dueDate
				+ ", tskBrCode=" + tskBrCode 
				+ ", transType =" +transType +"]";
	}

}
