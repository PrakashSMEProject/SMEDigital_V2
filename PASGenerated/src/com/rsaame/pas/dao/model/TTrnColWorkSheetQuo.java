package com.rsaame.pas.dao.model;

// Generated Jun 29, 2012 4:20:03 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.pojo.POJOId;

/**
 * TTrnColWorkSheetQuo generated by hbm2java
 */
public class TTrnColWorkSheetQuo extends POJO implements java.io.Serializable {

	private TTrnColWorkSheetQuoId id;
	private Long cwsItemAmount;
	private Long cwsEndtId;
	private Date cwsValidityExpiryDate;
	private Byte cwsStatus;
	private Integer cwsPreparedBy;
	private Date cwsPreparedDt;
	private Integer cwsModifiedBy;
	private Date cwsModifiedDt;
	private String cwsDescription;

	public TTrnColWorkSheetQuo() {
	}

	public TTrnColWorkSheetQuo(TTrnColWorkSheetQuoId id) {
		this.id = id;
	}

	public TTrnColWorkSheetQuo(TTrnColWorkSheetQuoId id, Long cwsItemAmount,
			Long cwsEndtId, Date cwsValidityExpiryDate, Byte cwsStatus,
			Integer cwsPreparedBy, Date cwsPreparedDt, Integer cwsModifiedBy,
			Date cwsModifiedDt, String cwsDescription) {
		this.id = id;
		this.cwsItemAmount = cwsItemAmount;
		this.cwsEndtId = cwsEndtId;
		this.cwsValidityExpiryDate = cwsValidityExpiryDate;
		this.cwsStatus = cwsStatus;
		this.cwsPreparedBy = cwsPreparedBy;
		this.cwsPreparedDt = cwsPreparedDt;
		this.cwsModifiedBy = cwsModifiedBy;
		this.cwsModifiedDt = cwsModifiedDt;
		this.cwsDescription = cwsDescription;
	}

	public TTrnColWorkSheetQuoId getId() {
		return this.id;
	}

	public void setId(TTrnColWorkSheetQuoId id) {
		this.id = id;
	}

	public Long getCwsItemAmount() {
		return this.cwsItemAmount;
	}

	public void setCwsItemAmount(Long cwsItemAmount) {
		this.cwsItemAmount = cwsItemAmount;
	}

	public Long getCwsEndtId() {
		return this.cwsEndtId;
	}

	public void setCwsEndtId(Long cwsEndtId) {
		this.cwsEndtId = cwsEndtId;
	}

	public Date getCwsValidityExpiryDate() {
		return this.cwsValidityExpiryDate;
	}

	public void setCwsValidityExpiryDate(Date cwsValidityExpiryDate) {
		this.cwsValidityExpiryDate = cwsValidityExpiryDate;
	}

	public Byte getCwsStatus() {
		return this.cwsStatus;
	}

	public void setCwsStatus(Byte cwsStatus) {
		this.cwsStatus = cwsStatus;
	}

	public Integer getCwsPreparedBy() {
		return this.cwsPreparedBy;
	}

	public void setCwsPreparedBy(Integer cwsPreparedBy) {
		this.cwsPreparedBy = cwsPreparedBy;
	}

	public Date getCwsPreparedDt() {
		return this.cwsPreparedDt;
	}

	public void setCwsPreparedDt(Date cwsPreparedDt) {
		this.cwsPreparedDt = cwsPreparedDt;
	}

	public Integer getCwsModifiedBy() {
		return this.cwsModifiedBy;
	}

	public void setCwsModifiedBy(Integer cwsModifiedBy) {
		this.cwsModifiedBy = cwsModifiedBy;
	}

	public Date getCwsModifiedDt() {
		return this.cwsModifiedDt;
	}

	public void setCwsModifiedDt(Date cwsModifiedDt) {
		this.cwsModifiedDt = cwsModifiedDt;
	}

	public String getCwsDescription() {
		return this.cwsDescription;
	}

	public void setCwsDescription(String cwsDescription) {
		this.cwsDescription = cwsDescription;
	}
	
	@Override
	public int getStatus(){
		return getCwsStatus();
	}
	
	@Override
	public void setStatus( Integer status ){
		setCwsStatus( status.byteValue() );
	}
	
	@Override
	public void setEndtId( Long endtId ){
		setCwsEndtId(endtId);
	}
	
	@Override
	public POJOId getPOJOId(){
		return this.getId();
	}
	
	@Override
	public void setPOJOId( POJOId id ){
		setId((TTrnColWorkSheetQuoId)id);
	}
	
	@Override
	public void setValidityExpiryDate( Date ved ){
		setCwsValidityExpiryDate(ved);
	}
	
	@Override
	public void setValidityStartDate( Date vsd ){
		if( !Utils.isEmpty( getId() ) ) getId().setCwsValidityStartDate(vsd);
	}

	@Override
	public void setPreparedDate( Date preparedDate ){
		setCwsPreparedDt( preparedDate );
	}
	
	@Override
	public Date getPreparedDate(){
		return getCwsPreparedDt();
	}
}
