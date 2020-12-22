package com.rsaame.pas.vo.bus;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;

public class SafeVO extends BaseVO implements ICashDetails{

	private static final long serialVersionUID = 1L;
	
	public Long id = null;
	public Date vsd = null;
	
	private String Make;
	private Double weight;
	private Double height;
	private Double width;
	private String anchored;
	private Integer index;
	private Long cashDetailsid;
	
	private boolean toBeDeleted;
	
	public Integer getIndex(){
		return index;
	}

	public void setIndex( Integer index ){
		this.index = index;
	}
	
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "Make".equals( fieldName ) ) fieldValue = getMake();
		if( "weight".equals( fieldName ) ) fieldValue = getWeight();
		if( "height".equals( fieldName ) ) fieldValue = getHeight();
		if( "width".equals( fieldName ) ) fieldValue = getWidth();
		if( "anchored".equals( fieldName ) ) fieldValue = getAnchored();
		if( "cashDetailsid".equals( fieldName ) ) fieldValue = getAnchored();
		if( "id".equals( fieldName ) ) fieldValue = getId();
		if( "vsd".equals( fieldName ) ) fieldValue = getVsd();
		if( "index".equals( fieldName ) ) fieldValue = getIndex();
		return fieldValue;
	}
	
	public String getMake(){
		return Make;
	}
	public void setMake( String make ){
		Make = make;
	}
	public Double getWeight(){
		return weight;
	}
	public void setWeight( Double weight ){
		this.weight = weight;
	}
	public Double getHeight(){
		return height;
	}
	public void setHeight( Double height ){
		this.height = height;
	}
	public Double getWidth(){
		return width;
	}
	public void setWidth( Double width ){
		this.width = width;
	}
	public String getAnchored(){
		return anchored;
	}
	public void setAnchored( String anchored ){
		this.anchored = anchored;
	}

	/**
	 * @return the cashDetailsid
	 */
	public Long getCashDetailsid(){
		return cashDetailsid;
	}

	/**
	 * @param cashDetailsid the cashDetailsid to set
	 */
	public void setCashDetailsid( Long cashDetailsid ){
		this.cashDetailsid = cashDetailsid;
	}

	@Override
	public Long getId(){
		return id;
	}

	@Override
	public void setId( Object id ){
		this.id = (Long) id;
	}

	public Date getVsd(){
		return vsd;
	}

	public void setVsd( Date vsd ){
		this.vsd = vsd;
	}

	@Override
	public boolean isToBeDeleted(){
		return toBeDeleted;
	}

	@Override
	public void setToBeDeleted( boolean toBeDeleted ){
		this.toBeDeleted = toBeDeleted;
	}
	
	@Override
	public boolean compareId( Long id ){
		boolean isSame = true;
		if( this.id == null && id != null ) isSame = false;
		else if( this.id != null && id == null ) isSame = false;
		else isSame = this.id.equals( id );
		
		return isSame;
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( Make == null ) ? 0 : Make.hashCode() );
		result = prime * result + ( ( anchored == null ) ? 0 : anchored.hashCode() );
		result = prime * result + ( ( cashDetailsid == null ) ? 0 : cashDetailsid.hashCode() );
		result = prime * result + ( ( height == null ) ? 0 : height.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( toBeDeleted ? 1231 : 1237 );
		result = prime * result + ( ( vsd == null ) ? 0 : vsd.hashCode() );
		result = prime * result + ( ( weight == null ) ? 0 : weight.hashCode() );
		result = prime * result + ( ( width == null ) ? 0 : width.hashCode() );
		return result;
	}

	 
	@Override
	public boolean equals( Object otherVO ){
		
		//This was added as sonar reported critical issue 
		//Added Utils.isEmpty() to avoid sonar violation on 27-9-2017
		if (getClass() != otherVO.getClass()|| Utils.isEmpty(otherVO))   return false;
		//if( otherVO == null ) return false;

		ICashDetails other = (ICashDetails) otherVO;
		return this.compareId( (Long) other.getId() );
	}
	
}
