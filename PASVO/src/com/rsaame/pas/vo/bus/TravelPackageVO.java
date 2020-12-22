/**
 * Holds Travel Insurance related data
 * packageName - Travel Scheme Name
 * packagePremium - Premium for the Travel Scheme
 * travelDefaultCovers - Holds list of cover details
 * travelOptionalCovers - Holds list of additional covers
 */
package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.utils.Utils;


/**
 * 
 * @author m1017029,m1016996
 * @since Phase3 
 */
public class TravelPackageVO extends PremiumVO implements Comparable<TravelPackageVO>{

	private static final long serialVersionUID = 1L;

	private String packageName;
	private String tariffCode;
	private String description;
	private java.util.List<CoverDetailsVO> covers = new com.mindtree.ruc.cmn.utils.List<CoverDetailsVO>( CoverDetailsVO.class );
	private Boolean isSelected;
	private boolean isRecommended;
	private Integer order;

	public String getPackageName(){
		return packageName;
	}

	public void setPackageName( String packageName ){
		this.packageName = packageName;
	}

	public java.util.List<CoverDetailsVO> getCovers(){
		return covers;
	}

	public void setCovers( java.util.List<CoverDetailsVO> covers ){
		this.covers = covers;
	}
	
	/**
	 * @param isSelected
	 */
	public void setIsSelected( Boolean isSelected ){
		this.isSelected = isSelected;
	}

	/**
	 * @return Boolean
	 */
	public Boolean getIsSelected(){
		return isSelected;
	}

	
	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;
		if( "packageName".equals( fieldName ) ) fieldValue = getPackageName();
		if( "covers".equals( fieldName ) ) fieldValue = getCovers();
		return fieldValue;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( tariffCode == null ) ? 0 : tariffCode.hashCode() );
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ){
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( getClass() != obj.getClass() ) return false;
		TravelPackageVO other = (TravelPackageVO) obj;
		if( tariffCode == null ){
			if( other.tariffCode != null ) return false;
		}
		else if( !tariffCode.equals( other.tariffCode ) ) return false;
		return true;
	}

	/**
	 * @return the tariffCode
	 */
	public String getTariffCode(){
		return tariffCode;
	}

	/**
	 * @param tariffCode the tariffCode to set
	 */
	public void setTariffCode( String tariffCode ){
		this.tariffCode = tariffCode;
	}

	/**
	 * @return the description
	 */
	public String getDescription(){
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription( String description ){
		this.description = description;
	}

	/**
	 * @return the isRecommended
	 */
	public boolean getIsRecommended(){
		return isRecommended;
	}

	/**
	 * @param isRecommended the isRecommended to set
	 */
	public void setIsRecommended( boolean isRecommended ){
		this.isRecommended = isRecommended;
	}

	/**
	 * @return the order
	 */
	public Integer getOrder(){
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder( Integer order ){
		this.order = order;
	}
	
	
	@Override
	public int compareTo( TravelPackageVO other ){
		return this.getOrder().intValue() - other.getOrder().intValue();
	}
	
	
	public CoverDetailsVO getCoverUsingCoverCodes( Short covCode, Short covTypeCode, Short covSubTypeCode ){
		CoverDetailsVO coverDetailsVO = null;
		if( !Utils.isEmpty( covCode ) && !Utils.isEmpty( covTypeCode ) && !Utils.isEmpty( covSubTypeCode ) ){
			coverDetailsVO = getCoverUsingCoverSubTypeCode( covCode, covTypeCode, covSubTypeCode );
		}
		else if( !Utils.isEmpty( covCode ) && !Utils.isEmpty( covTypeCode ) ){
			coverDetailsVO = getCoverUsingCoverTypeCode( covCode, covTypeCode );
		}
		else if( !Utils.isEmpty( covCode ) ){
			coverDetailsVO = getCoverUsingCoverCode( covCode );
		}
		return coverDetailsVO;
	}
	
	private CoverDetailsVO getCoverUsingCoverCode( Short covCode ){
		CoverDetailsVO coverDetailsVO = null;
		if( !Utils.isEmpty( covers ) ){
			for( CoverDetailsVO cover : covers ){
				coverDetailsVO = cover.getCoverUsingCoverCode( covCode );
				if( !Utils.isEmpty( coverDetailsVO ) && Short.valueOf( coverDetailsVO.getCoverCodes().getCovCode()  ).compareTo( covCode ) == 0){
					break;
				}
			}
		}

		return coverDetailsVO;
	}

	private CoverDetailsVO getCoverUsingCoverTypeCode( Short covCode, Short covTypeCode ){
		CoverDetailsVO coverDetailsVO = null;
		if( !Utils.isEmpty( covers ) ){
			for( CoverDetailsVO cover : covers ){
				coverDetailsVO = cover.getCoverUsingCoverTypeCode( covCode, covTypeCode );
			}
		}

		return coverDetailsVO;
	}

	private CoverDetailsVO getCoverUsingCoverSubTypeCode( Short covCode, Short covTypeCode, Short covSubTypeCode ){
		CoverDetailsVO coverDetailsVO = null;
		if( !Utils.isEmpty( covers ) ){
			for( CoverDetailsVO cover : covers ){
				coverDetailsVO = cover.getCoverUsingCoverSubTypeCode( covCode, covTypeCode, covSubTypeCode );
			}
		}

		return coverDetailsVO;
	}

}
