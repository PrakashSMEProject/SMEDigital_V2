package com.rsaame.pas.vo.bus;


public class DeteriorationOfStockDetailsVO extends RiskGroupDetails{
	
	private String deteriorationOfStockType;
	/*private String deteriorationOfStockDesc="";
	private Integer deteriorationOfStockQuantity;*/
	private SumInsuredVO sumInsuredDetails = new SumInsuredVO();
	private Long contentId;
	private PremiumVO premium;
	
	
	
	public PremiumVO getPremium(){
		return premium;
	}
	public void setPremium( PremiumVO premium ){
		this.premium = premium;
	}
	public String getDeteriorationOfStockType(){
		return deteriorationOfStockType;
	}
	public void setDeteriorationOfStockType( String deteriorationOfStockType ){
		this.deteriorationOfStockType = deteriorationOfStockType;
	}
	/*public String getDeteriorationOfStockDesc(){
		return deteriorationOfStockDesc;
	}
	public void setDeteriorationOfStockDesc( String deteriorationOfStockDesc ){
		this.deteriorationOfStockDesc = deteriorationOfStockDesc;
	}
	public Integer getDeteriorationOfStockQuantity(){
		return deteriorationOfStockQuantity;
	}
	public void setDeteriorationOfStockQuantity( Integer deteriorationOfStockQuantity ){
		this.deteriorationOfStockQuantity = deteriorationOfStockQuantity;
	}*/
	public SumInsuredVO getSumInsuredDetails(){
		return sumInsuredDetails;
	}
	public void setSumInsuredDetails( SumInsuredVO sumInsuredDetails ){
		this.sumInsuredDetails = sumInsuredDetails;
	}
	public Long getContentId(){
		return contentId;
	}
	public void setContentId( Long contentId ){
		this.contentId = contentId;
	}
	
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "deteriorationOfStockType".equals( fieldName ) ) fieldValue = getDeteriorationOfStockType();
		/*if( "deteriorationOfStockDesc".equals( fieldName ) ) fieldValue = getDeteriorationOfStockDesc();
		if( "deteriorationOfStockQuantity".equals( fieldName ) ) fieldValue = getDeteriorationOfStockQuantity();*/
		if( "sumInsuredDetails".equals( fieldName ) ) fieldValue = getSumInsuredDetails();
		if( "contentId".equals( fieldName ) ) fieldValue = getContentId();
		if( "premium".equals( fieldName ) ) fieldValue = getPremium();
		return fieldValue;
	}
	@Override
	public String toString(){
		return "DeteriorationOfStockDetailsVO [deteriorationOfStockType=" + deteriorationOfStockType + ",sumInsured=" + sumInsuredDetails + ", contentId=" + contentId + "]";
	}
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( contentId == null ) ? 0 : contentId.hashCode() );
		return result;
	}
	@Override
	public boolean equals( Object obj ){
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( getClass() != obj.getClass() ) return false;
		DeteriorationOfStockDetailsVO other = (DeteriorationOfStockDetailsVO) obj;
		if( contentId == null ){
			if( other.contentId != null ) return false;
		}
		else if( !contentId.equals( other.contentId ) ) return false;
		return true;
	}
	
	
}
