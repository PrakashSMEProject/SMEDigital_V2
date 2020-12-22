/**
 * 
 */
package com.rsaame.pas.vo.bus;

/**
 * @author m1014644
 *
 */
public class PLUWDetails extends UWDetails {

	private static final long serialVersionUID = 1L;
	private SimpleUWDetailsVO uwDetails;
	private Integer hazardLevel;
	private Integer categoryRI;
	
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IFieldValue#getFieldValue(java.lang.String)
	 */
	@Override
	public Object getFieldValue(String fieldName) {
		
		Object fieldValue = null;
		if( "hazardLevel".equals( fieldName ) ) fieldValue = getHazardLevel();
		if( "categoryRI".equals( fieldName ) ) fieldValue = getCategoryRI();
		if( "uwDetails".equals( fieldName ) ) fieldValue = getUwDetails();
		return fieldValue;
	}


	/**
	 * @return the uwDetails
	 */
	public SimpleUWDetailsVO getUwDetails() {
		return uwDetails;
	}


	/**
	 * @param uwDetails the uwDetails to set
	 */
	public void setUwDetails(SimpleUWDetailsVO uwDetails) {
		this.uwDetails = uwDetails;
	}


	/**
	 * @return the hazardLevel
	 */
	public Integer getHazardLevel() {
		return hazardLevel;
	}


	/**
	 * @param hazardLevel the hazardLevel to set
	 */
	public void setHazardLevel(Integer hazardLevel) {
		this.hazardLevel = hazardLevel;
	}


	/**
	 * @return the categoryRI
	 */
	public Integer getCategoryRI() {
		return categoryRI;
	}


	/**
	 * @param categoryRI the categoryRI to set
	 */
	public void setCategoryRI(Integer categoryRI) {
		this.categoryRI = categoryRI;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PLUWDetails [uwDetails=" + uwDetails + ", hazardLevel="
				+ hazardLevel + ", categoryRI=" + categoryRI + "]";
	}

	
}
