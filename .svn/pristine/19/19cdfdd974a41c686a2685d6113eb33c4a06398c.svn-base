/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.util.Date;

import com.rsaame.pas.vo.app.FieldType;
import com.rsaame.pas.vo.cmn.CoverVO;
import com.rsaame.pas.vo.cmn.RiskVO;

/**
 * @author M1014644
 *
 */
public class CoverDetailsVO extends PremiumVO implements Comparable<CoverDetailsVO>{

	private static final long serialVersionUID = 1L;
	private String coverName;
	private String coverDesc;
	private CoverVO coverCodes;
	private RiskVO riskCodes;
	private SumInsuredVO sumInsured;
	private Date vsd;
	private FieldType fieldType;
	private Integer tariffCode;
	private String mappingField;
	private Boolean mandatoryIndicator;
	private String isCovered;
	private Integer riRskCode; 
	private Double prLimit;
	private String helpMessage;
	private Integer prcDisplayInd; 
	private Integer prcBToCDisplayInd; 
	
	
	/**
	 * @return the riRskCode
	 */
	public Integer getRiRskCode(){
		return riRskCode;
	}

	/**
	 * @param riRskCode the riRskCode to set
	 */
	public void setRiRskCode( Integer riRskCode ){
		this.riRskCode = riRskCode;
	}

	public String getCoverName(){
		return coverName;
	}

	public void setCoverName( String coverName ){
		this.coverName = coverName;
	}
	
	

	public Integer getPrcDisplayInd() {
		return prcDisplayInd;
	}

	public void setPrcDisplayInd(Integer prcDisplayInd) {
		this.prcDisplayInd = prcDisplayInd;
	}
	
	

	public Integer getPrcBToCDisplayInd() {
		return prcBToCDisplayInd;
	}

	public void setPrcBToCDisplayInd(Integer prcBToCDisplayInd) {
		this.prcBToCDisplayInd = prcBToCDisplayInd;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( coverCodes == null ) ? 0 : coverCodes.hashCode() );
		result = prime * result + ( ( coverDesc == null ) ? 0 : coverDesc.hashCode() );
		result = prime * result + ( ( coverName == null ) ? 0 : coverName.hashCode() );
		result = prime * result + ( ( fieldType == null ) ? 0 : fieldType.hashCode() );
		result = prime * result + ( ( riskCodes == null ) ? 0 : riskCodes.hashCode() );
		result = prime * result + ( ( sumInsured == null ) ? 0 : sumInsured.hashCode() );
		result = prime * result + ( ( prLimit == null ) ? 0 : prLimit.hashCode() );
		result = prime * result + ( ( helpMessage == null ) ? 0 : helpMessage.hashCode() );
		result = prime * result + ( ( vsd == null ) ? 0 : vsd.hashCode() );
		result = prime * result + ( ( prcDisplayInd == null ) ? 0 : prcDisplayInd.hashCode() );
		result = prime * result + ( ( prcBToCDisplayInd == null ) ? 0 : prcBToCDisplayInd.hashCode() );
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ){
		if( this == obj ){
			return true;
		}
		if( obj == null ){
			return false;
		}
		if( !( obj instanceof CoverDetailsVO ) ){
			return false;
		}
		CoverDetailsVO other = (CoverDetailsVO) obj;
		if( coverCodes == null ){
			if( other.coverCodes != null ){
				return false;
			}
		}
		else if( !coverCodes.equals( other.coverCodes ) ){
			return false;
		}
		/*if( coverDesc == null ){
			if( other.coverDesc != null ){
				return false;
			}
		}
		else if( !coverDesc.equals( other.coverDesc ) ){
			return false;
		}
		if( coverName == null ){
			if( other.coverName != null ){
				return false;
			}
		}
		else if( !coverName.equals( other.coverName ) ){
			return false;
		}
		if( fieldType != other.fieldType ){
			return false;
		}
		if( riskCodes == null ){
			if( other.riskCodes != null ){
				return false;
			}
		}
		else if( !riskCodes.equals( other.riskCodes ) ){
			return false;
		}
		if( sumInsured == null ){
			if( other.sumInsured != null ){
				return false;
			}
		}
		else if( !sumInsured.equals( other.sumInsured ) ){
			return false;
		}
		if( vsd == null ){
			if( other.vsd != null ){
				return false;
			}
		}
		else if( !vsd.equals( other.vsd ) ){
			return false;
		}*/
		return true;
	}

	public String getCoverDesc(){
		return coverDesc;
	}

	public void setCoverDesc( String coverDesc ){
		this.coverDesc = coverDesc;
	}

	public SumInsuredVO getSumInsured(){
		return sumInsured;
	}

	public void setSumInsured( SumInsuredVO sumInsured ){
		this.sumInsured = sumInsured;
	}

	public Date getVsd(){
		return vsd;
	}

	public void setVsd( Date vsd ){
		this.vsd = vsd;
	}

	public CoverVO getCoverCodes(){
		return coverCodes;
	}

	public void setCoverCodes( CoverVO coverCodes ){
		this.coverCodes = coverCodes;
	}

	public RiskVO getRiskCodes(){
		return riskCodes;
	}

	public void setRiskCodes( RiskVO riskCodes ){
		this.riskCodes = riskCodes;
	}

	public FieldType getFieldType(){
		return fieldType;
	}

	public void setFieldType( FieldType fieldType ){
		this.fieldType = fieldType;
	}

	public void setTariffCode( Integer tariffCode ){
		this.tariffCode = tariffCode;
	}

	public Integer getTariffCode(){
		return tariffCode;
	}

	/**
	 * @return the mappingField
	 */
	public String getMappingField(){
		return mappingField;
	}

	/**
	 * @param mappingField the mappingField to set
	 */
	public void setMappingField( String mappingField ){
		this.mappingField = mappingField;
	}

	/**
	 * @return the mandatoryIndicator
	 */
	public Boolean getMandatoryIndicator(){
		return mandatoryIndicator;
	}

	/**
	 * @param mandatoryIndicator the mandatoryIndicator to set
	 */
	public void setMandatoryIndicator( Boolean mandatoryIndicator ){
		this.mandatoryIndicator = mandatoryIndicator;
	}

	/**
	 * @return the isCovered
	 */
	public String getIsCovered(){
		return isCovered;
	}

	/**
	 * @param isCovered the isCovered to set
	 */
	public void setIsCovered( String isCovered ){
		this.isCovered = isCovered;
	}

	public Double getPrLimit() {
		return prLimit;
	}

	public void setPrLimit(Double prLimit) {
		this.prLimit = prLimit;
	}
	
		
	public String getHelpMessage() {
		return helpMessage;
	}

	public void setHelpMessage(String helpMessage) {
		this.helpMessage = helpMessage;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "CoverDetailsVO [coverName=" + coverName + ", coverDesc=" + coverDesc + ", coverCodes=" + coverCodes + ", riskCodes=" + riskCodes + ", sumInsured=" + sumInsured
				+ ", vsd=" + vsd + ", fieldType=" + fieldType + ", tariffCode=" + tariffCode + ", mappingField=" + mappingField + ", mandatoryIndicator=" + mandatoryIndicator
				+ ", isCovered=" + isCovered +", prLimit="+prLimit+", helpMessage"+helpMessage+ "]";
	}

	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "coverName".equals( fieldName ) ) fieldValue = getCoverName();
		if( "coverDesc".equals( fieldName ) ) fieldValue = getCoverDesc();
		if( "coverCodes".equals( fieldName ) ) fieldValue = getCoverCodes();
		if( "riskCodes".equals( fieldName ) ) fieldValue = getRiskCodes();
		if( "sumInsured".equals( fieldName ) ) fieldValue = getSumInsured();
		if( "vsd".equals( fieldName ) ) fieldValue = getVsd();
		if( "fieldType".equals( fieldName ) ) fieldValue = getFieldType();
		if( "tariffCode".equals( fieldName ) ) fieldValue = getTariffCode();
		if( "mappingField".equals( fieldName ) ) fieldValue = getMappingField();
		if( "mandatoryIndicator".equals( fieldName ) ) fieldValue = getMandatoryIndicator();
		if( "prLimit".equals( fieldName ) ) fieldValue = getPrLimit();
		if( "helpMessage".equals( fieldName ) ) fieldValue = getHelpMessage();
		if( "isCovered".equals( fieldName ) ) fieldValue = getIsCovered();

		return fieldValue;
	}

	@Override
	public int compareTo( CoverDetailsVO other ){
		return this.coverCodes.getCovCode() - other.getCoverCodes().getCovCode();
	}

	public CoverDetailsVO getCoverUsingCoverCode( Short covCode ){
		CoverDetailsVO coverDetailsVO = null;
		if( Double.valueOf( 0.0 ).compareTo( this.sumInsured.getSumInsured() ) == -1 || "on".equalsIgnoreCase( this.getIsCovered() ) ){
			if( Short.valueOf( coverCodes.getCovCode() ).equals( covCode ) ){
				coverDetailsVO = this;
			}
		}
		return coverDetailsVO;
	}
	
	public CoverDetailsVO getCoverUsingCoverTypeCode( Short covCode, Short covTypeCode ){
		CoverDetailsVO coverDetailsVO = null;
		if( Double.valueOf( 0.0 ).compareTo( this.sumInsured.getSumInsured() ) == -1 || "on".equalsIgnoreCase( this.getIsCovered() ) ){
			if( Short.valueOf( coverCodes.getCovCode() ).equals( covCode ) && Short.valueOf( coverCodes.getCovTypeCode() ).equals( covTypeCode ) ){
				coverDetailsVO = this;
			}
		}
		return coverDetailsVO;
	}

	public CoverDetailsVO getCoverUsingCoverSubTypeCode( Short covCode, Short covTypeCode, Short covSubTypeCode ){
		CoverDetailsVO coverDetailsVO = null;
		if( Double.valueOf( 0.0 ).compareTo( this.sumInsured.getSumInsured() ) == -1 || "on".equalsIgnoreCase( this.getIsCovered() ) ){
			if( Short.valueOf( coverCodes.getCovCode() ).equals( covCode ) && Short.valueOf( coverCodes.getCovTypeCode() ).equals( covTypeCode )
					&& Short.valueOf( coverCodes.getCovSubTypeCode() ).equals( covSubTypeCode ) ){
				coverDetailsVO = this;
			}
		}
		return coverDetailsVO;
	}
	
	
	
}
