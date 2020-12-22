package com.rsaame.pas.dao.model;

import java.math.BigDecimal;
import java.util.Date;

// Generated Mar 12, 2012 4:59:48 PM by Hibernate Tools 3.4.0.CR1

/**
 * VTrnTask generated by hbm2java
 */
public class VTrnTask implements java.io.Serializable {

	private VTrnTaskId id;
	private String tskShortDesc;
	private String tskDescription;
	private Date tskTargetDate;
	private Integer tskEstimatedTime;
	private Byte tskTimeUnit;
	private Long tskCustomerId;
	private Byte tskTranType;
	private String tskDocumentId;
	private Byte tskPriority;
	private Byte tskStatus;
	private Date tskCompletionDate;
	private String tskClientRef;
	private Byte tskClass;
	private Long tskBrCode;
	private BigDecimal tskEstPremium;
	private Integer tskCreatedBy;
	private Byte tskTaskCategory;
	private Date tskCreatedDate;
	private Integer ctlBrCode;
	private Integer userId;
	private String userEName;
	private String loginId;
	private String profile;
	private Short brokerId;


	public String getTskShortDesc(){
		return tskShortDesc;
	}

	public void setTskShortDesc( String tskShortDesc ){
		this.tskShortDesc = tskShortDesc;
	}

	public String getTskDescription(){
		return tskDescription;
	}

	public void setTskDescription( String tskDescription ){
		this.tskDescription = tskDescription;
	}

	public Date getTskTargetDate(){
		return tskTargetDate;
	}

	public void setTskTargetDate( Date tskTargetDate ){
		this.tskTargetDate = tskTargetDate;
	}

	public Integer getTskEstimatedTime(){
		return tskEstimatedTime;
	}

	public void setTskEstimatedTime( Integer tskEstimatedTime ){
		this.tskEstimatedTime = tskEstimatedTime;
	}

	public Byte getTskTimeUnit(){
		return tskTimeUnit;
	}

	public void setTskTimeUnit( Byte tskTimeUnit ){
		this.tskTimeUnit = tskTimeUnit;
	}

	public Long getTskCustomerId(){
		return tskCustomerId;
	}

	public void setTskCustomerId( Long tskCustomerId ){
		this.tskCustomerId = tskCustomerId;
	}

	public Byte getTskTranType(){
		return tskTranType;
	}

	public void setTskTranType( Byte tskTranType ){
		this.tskTranType = tskTranType;
	}

	public String getTskDocumentId(){
		return tskDocumentId;
	}

	public void setTskDocumentId( String tskDocumentId ){
		this.tskDocumentId = tskDocumentId;
	}

	public Byte getTskPriority(){
		return tskPriority;
	}

	public void setTskPriority( Byte tskPriority ){
		this.tskPriority = tskPriority;
	}

	public Byte getTskStatus(){
		return tskStatus;
	}

	public void setTskStatus( Byte tskStatus ){
		this.tskStatus = tskStatus;
	}

	public Date getTskCompletionDate(){
		return tskCompletionDate;
	}

	public void setTskCompletionDate( Date tskCompletionDate ){
		this.tskCompletionDate = tskCompletionDate;
	}

	public String getTskClientRef(){
		return tskClientRef;
	}

	public void setTskClientRef( String tskClientRef ){
		this.tskClientRef = tskClientRef;
	}

	public Byte getTskClass(){
		return tskClass;
	}

	public void setTskClass( Byte tskClass ){
		this.tskClass = tskClass;
	}

	public Long getTskBrCode(){
		return tskBrCode;
	}

	public void setTskBrCode( Long tskBrCode ){
		this.tskBrCode = tskBrCode;
	}

	public BigDecimal getTskEstPremium(){
		return tskEstPremium;
	}

	public void setTskEstPremium( BigDecimal tskEstPremium ){
		this.tskEstPremium = tskEstPremium;
	}

	public Integer getTskCreatedBy(){
		return tskCreatedBy;
	}

	public void setTskCreatedBy( Integer tskCreatedBy ){
		this.tskCreatedBy = tskCreatedBy;
	}

	public Byte getTskTaskCategory(){
		return tskTaskCategory;
	}

	public void setTskTaskCategory( Byte tskTaskCategory ){
		this.tskTaskCategory = tskTaskCategory;
	}

	public Date getTskCreatedDate(){
		return tskCreatedDate;
	}

	public void setTskCreatedDate( Date tskCreatedDate ){
		this.tskCreatedDate = tskCreatedDate;
	}

	public Integer getCtlBrCode(){
		return ctlBrCode;
	}

	public void setCtlBrCode( Integer ctlBrCode ){
		this.ctlBrCode = ctlBrCode;
	}

	public Integer getUserId(){
		return userId;
	}

	public void setUserId( Integer userId ){
		this.userId = userId;
	}

	public String getUserEName(){
		return userEName;
	}

	public void setUserEName( String userEName ){
		this.userEName = userEName;
	}

	public String getLoginId(){
		return loginId;
	}

	public void setLoginId( String loginId ){
		this.loginId = loginId;
	}

	public String getProfile(){
		return profile;
	}

	public void setProfile( String profile ){
		this.profile = profile;
	}

	public Short getBrokerId(){
		return brokerId;
	}

	public void setBrokerId( Short brokerId ){
		this.brokerId = brokerId;
	}

	public VTrnTask() {
	}

	public VTrnTask(VTrnTaskId id) {
		this.id = id;
	}

	public VTrnTaskId getId() {
		return this.id;
	}

	public void setId(VTrnTaskId id) {
		this.id = id;
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( brokerId == null ) ? 0 : brokerId.hashCode() );
		result = prime * result + ( ( ctlBrCode == null ) ? 0 : ctlBrCode.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( loginId == null ) ? 0 : loginId.hashCode() );
		result = prime * result + ( ( profile == null ) ? 0 : profile.hashCode() );
		result = prime * result + ( ( tskBrCode == null ) ? 0 : tskBrCode.hashCode() );
		result = prime * result + ( ( tskClass == null ) ? 0 : tskClass.hashCode() );
		result = prime * result + ( ( tskClientRef == null ) ? 0 : tskClientRef.hashCode() );
		result = prime * result + ( ( tskCompletionDate == null ) ? 0 : tskCompletionDate.hashCode() );
		result = prime * result + ( ( tskCreatedBy == null ) ? 0 : tskCreatedBy.hashCode() );
		result = prime * result + ( ( tskCreatedDate == null ) ? 0 : tskCreatedDate.hashCode() );
		result = prime * result + ( ( tskCustomerId == null ) ? 0 : tskCustomerId.hashCode() );
		result = prime * result + ( ( tskDescription == null ) ? 0 : tskDescription.hashCode() );
		result = prime * result + ( ( tskDocumentId == null ) ? 0 : tskDocumentId.hashCode() );
		result = prime * result + ( ( tskEstPremium == null ) ? 0 : tskEstPremium.hashCode() );
		result = prime * result + ( ( tskEstimatedTime == null ) ? 0 : tskEstimatedTime.hashCode() );
		result = prime * result + ( ( tskPriority == null ) ? 0 : tskPriority.hashCode() );
		result = prime * result + ( ( tskShortDesc == null ) ? 0 : tskShortDesc.hashCode() );
		result = prime * result + ( ( tskStatus == null ) ? 0 : tskStatus.hashCode() );
		result = prime * result + ( ( tskTargetDate == null ) ? 0 : tskTargetDate.hashCode() );
		result = prime * result + ( ( tskTaskCategory == null ) ? 0 : tskTaskCategory.hashCode() );
		result = prime * result + ( ( tskTimeUnit == null ) ? 0 : tskTimeUnit.hashCode() );
		result = prime * result + ( ( tskTranType == null ) ? 0 : tskTranType.hashCode() );
		result = prime * result + ( ( userEName == null ) ? 0 : userEName.hashCode() );
		result = prime * result + ( ( userId == null ) ? 0 : userId.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj ){
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( getClass() != obj.getClass() ) return false;
		VTrnTask other = (VTrnTask) obj;
		if( brokerId == null ){
			if( other.brokerId != null ) return false;
		}
		else if( !brokerId.equals( other.brokerId ) ) return false;
		if( ctlBrCode == null ){
			if( other.ctlBrCode != null ) return false;
		}
		else if( !ctlBrCode.equals( other.ctlBrCode ) ) return false;
		if( id == null ){
			if( other.id != null ) return false;
		}
		else if( !id.equals( other.id ) ) return false;
		if( loginId == null ){
			if( other.loginId != null ) return false;
		}
		else if( !loginId.equals( other.loginId ) ) return false;
		if( profile == null ){
			if( other.profile != null ) return false;
		}
		else if( !profile.equals( other.profile ) ) return false;
		if( tskBrCode == null ){
			if( other.tskBrCode != null ) return false;
		}
		else if( !tskBrCode.equals( other.tskBrCode ) ) return false;
		if( tskClass == null ){
			if( other.tskClass != null ) return false;
		}
		else if( !tskClass.equals( other.tskClass ) ) return false;
		if( tskClientRef == null ){
			if( other.tskClientRef != null ) return false;
		}
		else if( !tskClientRef.equals( other.tskClientRef ) ) return false;
		if( tskCompletionDate == null ){
			if( other.tskCompletionDate != null ) return false;
		}
		else if( !tskCompletionDate.equals( other.tskCompletionDate ) ) return false;
		if( tskCreatedBy == null ){
			if( other.tskCreatedBy != null ) return false;
		}
		else if( !tskCreatedBy.equals( other.tskCreatedBy ) ) return false;
		if( tskCreatedDate == null ){
			if( other.tskCreatedDate != null ) return false;
		}
		else if( !tskCreatedDate.equals( other.tskCreatedDate ) ) return false;
		if( tskCustomerId == null ){
			if( other.tskCustomerId != null ) return false;
		}
		else if( !tskCustomerId.equals( other.tskCustomerId ) ) return false;
		if( tskDescription == null ){
			if( other.tskDescription != null ) return false;
		}
		else if( !tskDescription.equals( other.tskDescription ) ) return false;
		if( tskDocumentId == null ){
			if( other.tskDocumentId != null ) return false;
		}
		else if( !tskDocumentId.equals( other.tskDocumentId ) ) return false;
		if( tskEstPremium == null ){
			if( other.tskEstPremium != null ) return false;
		}
		else if( !tskEstPremium.equals( other.tskEstPremium ) ) return false;
		if( tskEstimatedTime == null ){
			if( other.tskEstimatedTime != null ) return false;
		}
		else if( !tskEstimatedTime.equals( other.tskEstimatedTime ) ) return false;
		if( tskPriority == null ){
			if( other.tskPriority != null ) return false;
		}
		else if( !tskPriority.equals( other.tskPriority ) ) return false;
		if( tskShortDesc == null ){
			if( other.tskShortDesc != null ) return false;
		}
		else if( !tskShortDesc.equals( other.tskShortDesc ) ) return false;
		if( tskStatus == null ){
			if( other.tskStatus != null ) return false;
		}
		else if( !tskStatus.equals( other.tskStatus ) ) return false;
		if( tskTargetDate == null ){
			if( other.tskTargetDate != null ) return false;
		}
		else if( !tskTargetDate.equals( other.tskTargetDate ) ) return false;
		if( tskTaskCategory == null ){
			if( other.tskTaskCategory != null ) return false;
		}
		else if( !tskTaskCategory.equals( other.tskTaskCategory ) ) return false;
		if( tskTimeUnit == null ){
			if( other.tskTimeUnit != null ) return false;
		}
		else if( !tskTimeUnit.equals( other.tskTimeUnit ) ) return false;
		if( tskTranType == null ){
			if( other.tskTranType != null ) return false;
		}
		else if( !tskTranType.equals( other.tskTranType ) ) return false;
		if( userEName == null ){
			if( other.userEName != null ) return false;
		}
		else if( !userEName.equals( other.userEName ) ) return false;
		if( userId == null ){
			if( other.userId != null ) return false;
		}
		else if( !userId.equals( other.userId ) ) return false;
		return true;
	}

}