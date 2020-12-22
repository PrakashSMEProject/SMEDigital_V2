/*
 * FileName.java
 *
 * Copyright (c) 2011-2012 Royal and Sun Alliance Insurance Group plc.
 * St.Mark’s Court, Chart Way, Horsham, West Sussex RH12 1XL, U.K.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Royal
 * and Sun Alliance Insurance Group plc.("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Royal and Sun Alliance Insurance Group plc.
 */
package com.rsaame.pas.rating.svc;

import com.rsaame.pas.vo.bus.RiskGroup;

/**
 * @author M1017952
 *
 */
public class RatingInvocationTracker {
	int sectionNumberinPolicy;
	RiskGroup riskLocId;
	/**
	 * @return the risksNumberSendtoRating
	 */
	
	/**
	 * @return the riskLocId
	 */
	public RiskGroup getRiskLocId() {
		return riskLocId;
	}
	/**
	 * @param riskLocId the riskLocId to set
	 */
	public void setRiskLocId(RiskGroup riskLocId) {
		this.riskLocId = riskLocId;
	}
	/**
	 * @return the sectionNumberinPolicy
	 */
	public int getSectionNumberinPolicy() {
		return sectionNumberinPolicy;
	}
	/**
	 * @param sectionNumberinPolicy the sectionNumberinPolicy to set
	 */
	public void setSectionNumberinPolicy(int sectionNumberinPolicy) {
		this.sectionNumberinPolicy = sectionNumberinPolicy;
	}
	
	

}
