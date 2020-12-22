/*
 * GetFactorListBF.java
 *
 * Copyright (c) 2011-2012 Royal and Sun Alliance Insurance Group plc.
 * St.Marks Court, Chart Way, Horsham, West Sussex RH12 1XL, U.K.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Royal
 * and Sun Alliance Insurance Group plc.("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Royal and Sun Alliance Insurance Group plc.
 */
package com.rsaame.pas.rating.svc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.constants.CommonConstants;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.quote.model.FactorBO;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.bus.BIVO;
import com.rsaame.pas.vo.bus.CommodityDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockDetailsVO;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.EquipmentVO;
import com.rsaame.pas.vo.bus.FidelityNammedEmployeeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityUnnammedEmployeeVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.GPANammedEmpVO;
import com.rsaame.pas.vo.bus.GPAUnnammedEmpVO;
import com.rsaame.pas.vo.bus.GoodsInTransitVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MachineDetailsVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.PARUWDetailsVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.TravelBaggageVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.WCVO;

public class GetFactorList {
	
	 private static final Logger logger = Logger.getLogger(GetFactorList.class);
	 private static final String DEFAULT_DEDUCTIBLE = "2500";
	 private static final String DEFAULT_DEDUCTIBLE_BAHRAIN = "250";
	 private static final String DEFAULT_WC_DEDUCTIBLE = "500";
	 private static final String DEFAULT_WC_DEDUCTIBLE_BAHRAIN = "50";
	 private static final String BAHRAIN_LOCATION_CODE = "50";
	 public ArrayList <FactorBO> getCLSPEKey(PolicyVO policy){
 
		 return null;
	 }
	 
	 public ArrayList <FactorBO> sectionSpeceficFactors(RiskGroupDetails riskVO, ArrayList<FactorBO> factors,BigDecimal renewalLoading,RiskGroup locationInfo, String sectionId ){
		 //TODO:
		 //	Identify the section by checking sectionName parameter
		 // Check the code for com.rsaame.kaizen.policy.businessfunction.GetFactorListBF and com.rsaame.kaizen.quote.businessfunction.GetFactorListBF
		 // Identify the difference and confirm do we need separate implementation for Quote and Policy: Lets have a discussion on difference before decides next step 
		 // Invoke getFactors() method from Specific SectionFactorsCreator [eg: PARFactorsCreator, WCFactorsCreator..]
		 // Create SectionFactorsCreator classes and method ready for current release, but do not implement the method till we get confirmation from CTS what factors required for each LOB / section
		 ArrayList <FactorBO> factorsList= new ArrayList<FactorBO>();
		if( sectionId.equals( "1" ) ){
			if( !Utils.isEmpty( riskVO ) ){
				if( riskVO instanceof ParVO ){
					ParVO parVO = (ParVO) riskVO;
					logger.info( "RiskGroupDetails is "+ parVO);
				}
			}
			if( Utils.isEmpty( riskVO ) ){
				logger.info( "RiskGroupDetails is empt_1" );
			}
			 //factorsList=getSectionSpeceficFactorsForPAR(parVO,renewalLoading,locationInfo);
		 }else if(sectionId.equals("7")){
			 if( !Utils.isEmpty( riskVO ) ){
					if( riskVO instanceof WCVO ){
			 WCVO wcVO=(WCVO)riskVO;
			 logger.info( "RiskGroupDetails is "+ wcVO);
					}
			 }
			 if(Utils.isEmpty( riskVO ))
			 {
				 logger.info( "RiskGroupDetails is empt_2" );
			 }
			 //factors.addAll(getSectionSpeceficFactorsForWC(wcVO,renewalLoading,locationInfo));
			 
		 }else if(sectionId.equals("8")){
			 MoneyVO moneyVO=(MoneyVO)riskVO;
			 if(Utils.isEmpty( moneyVO ))
			 {
				 logger.info( "RiskGroupDetails is empt_3" );
			 }
			 //factors.addAll(getSectionSpeceficFactorsForMoney(moneyVO,renewalLoading,locationInfo));
			 
		 }else if(sectionId.equals("6")){
			 PublicLiabilityVO plVO=(PublicLiabilityVO)riskVO;
			 if(Utils.isEmpty( plVO ))
			 {
				 logger.info( "RiskGroupDetails is empt_4" );
			 }
			 //factors.addAll(getSectionSpeceficFactorsForPL(plVO,renewalLoading,locationInfo));
			 
		 }
			 
		 		 
		 return factorsList;
		 
	 }



	/**
	 * @param plVO
	 * @param renewalLoading
	 * @param locationInfo
	 * @param pLRrtgItmSeqNo 
	 * @param commonFactor 
	 * @return
	 */
	public ArrayList <FactorBO>  getSectionSpeceficFactorsForPL(
			PublicLiabilityVO plVO, BigDecimal renewalLoading,
			RiskGroup locationInfo, int pLRrtgItmSeqNo, FactorBO commonFactor) {
		
		List <String>  errorKeys=new ArrayList<String> ();
		List <String> messageParts=new ArrayList<String>();
		ArrayList <FactorBO> factorList= new ArrayList();
		List <UWQuestionVO> uwQuestionsList=null;
		
		
		
		LocationVO locationVO = (LocationVO)locationInfo;
		String riskGroupId= locationInfo.getRiskGroupId().toString();
		if(! Utils.isEmpty(riskGroupId ) ){
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_ITEM_SEQ_NO_FACTOR );
			factor.setFactorValue(String.valueOf( pLRrtgItmSeqNo) );
			factorList.add( factor );	
		}else{
			
			errorKeys.add( com.Constant.CONST_RATING_PAR_ITM_NUM);
			messageParts.add(com.Constant.CONST_ITEM_SEQ_NUMBER_NOT_FOUND);
						
		}
		
		if(! Utils.isEmpty(locationInfo.getRiskGroupId() ) ){
			
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_TRADE_GROUP_FACTOR );
			
			factor.setFactorValue(String.valueOf( locationVO.getOccTradeGroup()) );
			factorList.add( factor );	
			 logger.debug("Rating : RATING_TRADE_GROUP_FACTO_1"+locationInfo.getRiskGroupId());
			
		}else{
			errorKeys.add( "rating.pl.notrdgrp");
			messageParts.add("PL Trade group id is not present");
		}
		
		
		boolean isStudentLiabilityFactorAdded = false;
		// Start : UWQuestions
		for(UWQuestionVO uwQuestionVO:plVO.getUwQuestions().getQuestions()){
			
			if((!(null==uwQuestionVO.getQId()))  && (!(null==Utils.getSingleValueAppConfig( "UWQ_CODE_PL_TNT_FLG")))){
				if(Utils.getSingleValueAppConfig( "UWQ_CODE_PL_TNT_FLG").equalsIgnoreCase(uwQuestionVO.getQId().toString())){
					FactorBO factor = new FactorBO();
					
					
					if(null!=uwQuestionVO.getResponse()){
						if(uwQuestionVO.getResponse().equalsIgnoreCase( "yes" )){
							factor.setFactorValue( "Y");	
						}else{
							factor.setFactorValue( "N");
						}
						factor.setFactorName( SvcConstants.RATING_PL_TENANT_FLG_FACTOR );
						
						factorList.add( factor );	
						 logger.debug("Rating : RATING_PL_TENANT_FLG_FACTOR"+factor.getFactorValue());
					}
					else{

						/*errorKeys.add( "rating.pl.uwq.notntflg");
						messageParts.add( "Response not set for UWQ_TENANT_FLG_FACTOR Question" );*/
						factor = new FactorBO();
						factor.setFactorName( SvcConstants.RATING_PL_TENANT_FLG_FACTOR );
						factor.setFactorValue("N" );
						factorList.add( factor );

						
						
					}
					
				}
				
			}
			
			
			if((!(null==uwQuestionVO.getQId()))  && (!(null==Utils.getSingleValueAppConfig( "UWQ_CODE_PL_WRK_AWY_EXT")))){
				if(Utils.getSingleValueAppConfig( "UWQ_CODE_PL_WRK_AWY_EXT").equalsIgnoreCase(uwQuestionVO.getQId().toString())){
					FactorBO factor = new FactorBO();
					
					
					if(null!=uwQuestionVO.getResponse()){
						if(uwQuestionVO.getResponse().equalsIgnoreCase( "yes" )){
							factor.setFactorValue( "Y");	
						}else{
							factor.setFactorValue( "N");
						}
						factor.setFactorName( SvcConstants.RATING_PL_WORK_AWY_FLG_FACTOR );
						
						factorList.add( factor );	
						 logger.debug("Rating : UWQ_CODE_PL_WRK_AWY_EXT"+factor.getFactorValue());
					}
					else{

						/*errorKeys.add( "rating.pl.uwq.nowrkawyflg");
						messageParts.add( "Response not set for UWQ_PL_WORK_AWY_FLG Question" );*/
						factor = new FactorBO();
						factor.setFactorName( SvcConstants.RATING_PL_WORK_AWY_FLG_FACTOR );
						factor.setFactorValue("N" );
						factorList.add( factor );
						
						
					}
					
				}
				
			}
			
			if((!(null==uwQuestionVO.getQId()))  && (!(null==Utils.getSingleValueAppConfig( "UWQ_CODE_PL_FOOD_FLG"))))
			{
				if(Utils.getSingleValueAppConfig( "UWQ_CODE_PL_FOOD_FLG").equalsIgnoreCase(uwQuestionVO.getQId().toString()))
				{
					FactorBO factor = new FactorBO();
					if(null!=uwQuestionVO.getResponse())
					{
						if(uwQuestionVO.getResponse().equalsIgnoreCase( "yes" )){
							factor.setFactorValue( "Y");	
						}else{
							factor.setFactorValue( "N");
						}
						factor.setFactorName( SvcConstants.RATING_PL_FOOD_DRINK_FLG_FACTOR );
						
						factorList.add( factor );	
						 logger.debug("Rating : RATING_PL_FOOD_DRINK_FLG_FACTOR"+factor.getFactorValue());
					}
					else{

						/*errorKeys.add( "rating.pl.uwq.nofddrnk");
						messageParts.add( "Response not set for UWQ_PL_FOOD_DRINK_FLG Question" );
						*/
						factor = new FactorBO();
						factor.setFactorName( SvcConstants.RATING_PL_FOOD_DRINK_FLG_FACTOR );
						factor.setFactorValue("N" );
						factorList.add( factor );
						
					}
					
				}
				else
				{
					FactorBO factor = new FactorBO();
					factor.setFactorName( SvcConstants.RATING_PL_FOOD_DRINK_FLG_FACTOR );
					factor.setFactorValue("N" );
					factorList.add( factor );
				}
				
			}
			
			//Start Added by Mindtree on 02/07/2015 for CR:104256 – Student Liability CR
			if((!(null==uwQuestionVO.getQId()))  && (!(null==Utils.getSingleValueAppConfig( "UWQ_CODE_PL_NO_OF_STUDENTS"))))
			{
				if(Utils.getSingleValueAppConfig( "UWQ_CODE_PL_NO_OF_STUDENTS").equalsIgnoreCase(uwQuestionVO.getQId().toString()))
				{
					FactorBO factor = new FactorBO();
					factor.setFactorName( SvcConstants.RATING_PL_STUDENT_LIABILITY_FACTOR );
					if(!Utils.isEmpty(uwQuestionVO.getResponse())) {
						factor.setFactorValue(uwQuestionVO.getResponse());
						factorList.add( factor );	
					} else{
						factor.setFactorValue("0" );
						factorList.add( factor );
					}
					logger.debug("Rating : RATING_PL_STUDENT_LIABILITY_FACTOR"+factor.getFactorValue());
					isStudentLiabilityFactorAdded = true;
				}
			}
			//End Added by Mindtree on 02/07/2015 for CR:104256 – Student Liability CR
			
		}//END : UWQuestions
		
		//Start Added by Mindtree on 05/11/2015 for CR:104256 – Student Liability CR
		if(!isStudentLiabilityFactorAdded ){
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_PL_STUDENT_LIABILITY_FACTOR );
			factor.setFactorValue("0" );
			factorList.add( factor );
			logger.debug("Rating : RATING_PL_STUDENT_LIABILITY_FACTOR"+factor.getFactorValue());
		}
		//Start Added by Mindtree on 05/11/2015 for CR:104256 – Student Liability CR
		
		/*
		 * 	public static String RATING_PL_DEDUCTIBLE_FACTOR ="PL Deductible";
			public static String RATING_PL_LIB_LIMIT_FACTOR="PL Liability Limit";
		 */
		
		
		if(!Utils.isEmpty(plVO.getIndemnityAmtLimit())){
			
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_PL_LIB_LIMIT_FACTOR);
			factor.setFactorValue( plVO.getIndemnityAmtLimit().toString());
			factorList.add( factor );
			logger.debug("Rating : RATING_PL_LIB_LIMIT_FACTOR"+factor.getFactorValue());
		}else{
			/*errorKeys.add( "rating.pl.noliblimit");
			messageParts.add( "Liab limit is not present for PL" );*/
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_PL_LIB_LIMIT_FACTOR);
			factor.setFactorValue("1" );
			factorList.add( factor );
		}
		
		if(!Utils.isEmpty(plVO.getSumInsuredDets())&& (!Utils.isEmpty(plVO.getSumInsuredDets().getDeductible())) ){
			
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_PL_DEDUCTIBLE_FACTOR);
			
			Double deductible = plVO.getSumInsuredDets().getDeductible();
			factor.setFactorValue(deductible.toString().substring( 0,deductible.toString().indexOf( "." ) ));
			factorList.add( factor );
			logger.debug("Rating : RATING_PL_DEDUCTIBLE_FACTOR"+factor.getFactorValue());
			
		}else{
			/*errorKeys.add( "rating.pl.noded");
			messageParts.add( "Deductibe is not present for PL" );*/
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_PL_DEDUCTIBLE_FACTOR );
			String locationCode = String.valueOf( locationVO.getRiskGroupId() );
			if( locationCode.equals( BAHRAIN_LOCATION_CODE ) ){
				factor.setFactorValue( DEFAULT_DEDUCTIBLE_BAHRAIN );
			}
			else{
				factor.setFactorValue( DEFAULT_DEDUCTIBLE );
			}
			factorList.add( factor );

		}
			
		factorList.add(commonFactor);
		if (!(errorKeys.size()==0)){
			String [] messagePartsStrArray=new String[messageParts.size()];
			messageParts.toArray(messagePartsStrArray);
			logger.debug("Rating : Exception");
			BusinessException businessExcp=new BusinessException( errorKeys, messagePartsStrArray );
			throw businessExcp;
		}
	
		return factorList;
	}

	/**
	 * @param content
	 * @param renewalLoading
	 * @param locationInfo
	 * @param moneyRtgItmSeqNo 
	 * @param moneyRtgItmSeqNo 
	 * @param commonFactor 
	 * @return
	 */
	public ArrayList <FactorBO>  getSectionSpeceficFactorsForMoney(
			Contents content, BigDecimal renewalLoading, RiskGroup locationInfo, int rtgItmSeqNo, int moneyRtgItmSeqNo, FactorBO commonFactor) {
		List <String>  errorKeys=new ArrayList<String> ();
		List <String> messageParts=new ArrayList<String>();
		ArrayList <FactorBO> factorList= new ArrayList();
		List <UWQuestionVO> uwQuestionsList=null;
		
		String riskGroupId= locationInfo.getRiskGroupId().toString();
		if(! Utils.isEmpty(riskGroupId ) ){
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_ITEM_SEQ_NO_FACTOR );
			factor.setFactorValue(String.valueOf( rtgItmSeqNo) );
			factorList.add( factor );	
		}else{
			
			errorKeys.add( com.Constant.CONST_RATING_PAR_ITM_NUM);
			messageParts.add(com.Constant.CONST_ITEM_SEQ_NUMBER_NOT_FOUND);
						
		}
		
		
		if(! Utils.isEmpty(locationInfo.getRiskGroupId() ) ){
			
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_TRADE_GROUP_FACTOR );
			LocationVO locationVO=(LocationVO)locationInfo;
			factor.setFactorValue(String.valueOf( String.valueOf( locationVO.getOccTradeGroup())));
			factorList.add( factor );	
			
			logger.debug("Rating : MONEY:RATING_TRADE_GROUP_FACTOR"+factor.getFactorValue());
			
		}else{
			errorKeys.add( "rating.money.notrdgrp");
			messageParts.add("Money Trade group id is not present");
		}
		String moneyCategory=null;
		 String moneyCategoryStr=content.getRiskType().toString()+ content.getRiskCat().toString()+content.getRiskSubCat().toString();
		 logger.debug("Rating : moneyCategoryStr"+moneyCategoryStr);
		 int moneyCategoryInt=Integer.parseInt(moneyCategoryStr);
		 /*	switch(moneyRtgItmSeqNo){
		case 0:moneyCategory="Estimated Annual Carryings";break;
		case 1:;
		case 2:moneyCategory="During business hours in locked safe";break;
		case 3:moneyCategory="During business hours in locked drawer";break;
		case 4:moneyCategory="After business hours in locked safe";break;
		case 5:moneyCategory="After business hours in locked drawer";break;
		case 6:moneyCategory="Total Cash in Authorized Employees Residence";break;
		}*/
		switch(moneyCategoryInt){
		case 110 :moneyCategory="Estimated Annual Carryings";break;
		case 120 :break;
		case 300 :moneyCategory="During business hours in locked safe";break;
		case 800 :moneyCategory="During business hours in locked drawer";break;
		case 210 :moneyCategory="After business hours in locked safe";break;
		case 230 :moneyCategory="After business hours in locked drawer";break;
		case 500 :moneyCategory="Total Cash in Authorized Employees Residence";break;
		//sonar fix
		default:
			break;
		}
		if(! Utils.isEmpty(moneyCategory ) ){
			
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_MONEY_CAT_FACTOR );
			factor.setFactorValue(moneyCategory);
			factorList.add( factor );	
			logger.debug("Rating : RATING_MONEY_CAT_FACTOR"+factor.getFactorValue());
		}else{
			/*errorKeys.add( "rating.money.nocategory");
			messageParts.add("Money Category is not present");*/
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_MONEY_CAT_FACTOR );
			factor.setFactorValue("Estimated Annual Carryings" );
			factorList.add( factor );
		}
		
		if(! Utils.isEmpty(content.getDeductibles() ) ){
			
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_MONEY_DEDUCTIBLE_FACTOR );
			Double deductible =content.getDeductibles().doubleValue();
			factor.setFactorValue(deductible.toString().substring( 0,deductible.toString().indexOf( "." ) ));
			factorList.add( factor );	
			logger.debug("Rating : RATING_MONEY_DEDUCTIBLE_FACTOR"+factor.getFactorValue());
			
		}else{
		/*	errorKeys.add( "rating.money.nodeductible");
			messageParts.add("Money deductible is not present");*/
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_MONEY_DEDUCTIBLE_FACTOR );
			//factor.setFactorValue("2500" );
			String locationCode = String.valueOf( ( ( LocationVO )locationInfo).getRiskGroupId() );
			if( locationCode.equals( BAHRAIN_LOCATION_CODE ) ){
				factor.setFactorValue( DEFAULT_DEDUCTIBLE_BAHRAIN );
			}
			else{
				factor.setFactorValue( DEFAULT_DEDUCTIBLE );
			}
			factorList.add( factor );
		}

		if(! Utils.isEmpty(content.getCover() ) ){
			
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_MONEY_SI_FACTOR );
			factor.setFactorValue(content.getCover().toString());
			factorList.add( factor );	
			logger.debug("Rating : RATING_MONEY_SI_FACTOR"+factor.getFactorValue());
			
		}else{
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_MONEY_SI_FACTOR );
			factor.setFactorValue("0" );
			factorList.add( factor );
			/*errorKeys.add( "rating.money.nosi");
			messageParts.add("Money sum insured is not present");*/
		}
		
		
		/*public static String RATING_MONEY_CAT_FACTOR ="Money Category";
		public static String RATING_MONEY_DEDUCTIBLE_FACTOR ="Money Deductible";
		public static String RATING_MONEY_SI_FACTOR="Money SI";*/
		factorList.add(commonFactor);
		if (!(errorKeys.size()==0)){
			String [] messagePartsStrArray=new String[messageParts.size()];
			messageParts.toArray(messagePartsStrArray);
			logger.debug("Rating : MONEY: EXCEPTION");
			BusinessException businessExcp=new BusinessException( errorKeys, messagePartsStrArray );
			throw businessExcp;
		}
		return factorList;
	}

	/**
	 * @param wcVO
	 * @param renewalLoading
	 * @param locationInfo
	 * @param wCRtgItmSeqNo 
	 * @param commonFactor 
	 * @return
	 */
	public ArrayList <FactorBO>  getSectionSpeceficFactorsForWC(WCVO wcVO,
			EmpTypeDetailsVO empTypeDetailsVO, BigDecimal renewalLoading, RiskGroup locationInfo, int wCRtgItmSeqNo, FactorBO commonFactor) {

		List <String>  errorKeys=new ArrayList<String> ();
		List <String> messageParts=new ArrayList<String>();
		ArrayList <FactorBO> factorList= new ArrayList();
		List <UWQuestionVO> uwQuestionsList=null;
		
		String buildingId= locationInfo.getRiskGroupId().toString();
		if(! Utils.isEmpty(buildingId ) ){
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_ITEM_SEQ_NO_FACTOR );
			factor.setFactorValue(String.valueOf( wCRtgItmSeqNo) );
			factorList.add( factor );	
			
		}else{
			
			errorKeys.add( "rating.wc.itm.num");
			messageParts.add(com.Constant.CONST_ITEM_SEQ_NUMBER_NOT_FOUND);
		}
		
		
		if(!Utils.isEmpty(empTypeDetailsVO.getDeductibles())){
			
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_WC_DEDUCTIBLE_FACTOR);
			BigDecimal deductible = empTypeDetailsVO.getDeductibles();
			factor.setFactorValue( deductible.toString() );
			factorList.add( factor );
			logger.debug("Rating : RATING_WC_DEDUCTIBLE_FACTOR"+factor.getFactorValue());
		}else{
			/*errorKeys.add( "rating.wc.noded");
			messageParts.add( "Deductibe is not present for WC" );*/
			FactorBO factor = new FactorBO();
			factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_WC_DEDUCTIBLE_FACTOR );
			//factor.setFactorValue("500" );
			String locationCode = String.valueOf( ( ( LocationVO )locationInfo).getRiskGroupId() );
			if( locationCode.equals( BAHRAIN_LOCATION_CODE ) ){
				factor.setFactorValue( DEFAULT_WC_DEDUCTIBLE_BAHRAIN );
			}
			else{
				factor.setFactorValue( DEFAULT_WC_DEDUCTIBLE );
			}
			factorList.add( factor );
		}
		

		if(!Utils.isEmpty(empTypeDetailsVO.getWageroll()) && !empTypeDetailsVO.getWageroll().equals( 0.0 )){
			
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_WC_ANNUAL_WAGE_ROLL_FACTOR);
			BigDecimal wageRoll = BigDecimal.valueOf( Double.valueOf( empTypeDetailsVO.getWageroll() ).longValue() );
			factor.setFactorValue( wageRoll.toString() );
			factorList.add( factor );
			logger.debug("Rating : RATING_WC_ANNUAL_WAGE_ROLL_FACTOR"+factor.getFactorValue());
		}else{
			/*errorKeys.add( "rating.wc.nowageRoll");
			messageParts.add( "Annual WageRoll is not present for WC" );*/
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_WC_ANNUAL_WAGE_ROLL_FACTOR );
			factor.setFactorValue(String.valueOf( 0) );
			factorList.add( factor );
		}

		
		if(!Utils.isEmpty(wcVO.getWcCovers())&&!Utils.isEmpty(wcVO.getWcCovers().getPACover())){
			
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_WC_PA_FLG_FACTOR);
			if(wcVO.getWcCovers().getPACover()){
				factor.setFactorValue( "Y");
			}else{
				factor.setFactorValue( "N");
			}
			
			factorList.add( factor );
			logger.debug("Rating : RATING_WC_PA_FLG_FACTOR"+factor.getFactorValue());
		}else{
			/*errorKeys.add( "rating.wc.nopaflg");
			messageParts.add( "Response for 24 PA Cover is not available for WC" );*/
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_WC_PA_FLG_FACTOR );
			factor.setFactorValue("N" );
			factorList.add( factor );
		}
		
		if(!Utils.isEmpty(empTypeDetailsVO.getLimit())){
			
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_WC_LIBLIMIT_FACTOR);
			factor.setFactorValue( empTypeDetailsVO.getLimit().toString());
			factorList.add( factor );
			logger.debug("Rating : RATING_WC_LIBLIMIT_FACTOR"+factor.getFactorValue());
		}else{
			/*errorKeys.add( "rating.wc.noliablimit");
			messageParts.add( "Liabilitylimit is not available for WC" );*/
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_WC_LIBLIMIT_FACTOR );
			factor.setFactorValue("1" );
			factorList.add( factor );
		}
		
		if(!Utils.isEmpty(empTypeDetailsVO.getEmpType()) && !empTypeDetailsVO.getEmpType().equals( CommonConstants.DEFAULT_LOW_INTEGER )){
			
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_WC_EMP_TYP_FACTOR);
			factor.setFactorValue( empTypeDetailsVO.getEmpType().toString());
			factorList.add( factor );
			logger.debug("Rating : RATING_WC_EMP_TYP_FACTOR"+factor.getFactorValue());
		}else{
			/*errorKeys.add( "rating.wc.noemptype");
			messageParts.add( "Employee type is not available for WC" );*/
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_WC_EMP_TYP_FACTOR );
			factor.setFactorValue("1" );
			factorList.add( factor );
		}
		
		if(!Utils.isEmpty(empTypeDetailsVO.getNoOfEmp()) ){
			
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_WC_NO_OF_EMP_FACTOR );
			factor.setFactorValue( String.valueOf(empTypeDetailsVO.getNoOfEmp()));
			factorList.add( factor );
			logger.debug("Rating : RATING_WC_EMP_TYP_FACTOR"+factor.getFactorValue());
		}else{
			/*errorKeys.add( "rating.wc.noemptype");
			messageParts.add( "Employee type is not available for WC" );*/
			FactorBO factor = new FactorBO();
			factor.setFactorName(SvcConstants.RATING_WC_NO_OF_EMP_FACTOR);
			factor.setFactorValue("0" );
			factorList.add( factor );
		}
		
			
		factorList.add(commonFactor);
		if (!(errorKeys.size()==0)){
			String [] messagePartsStrArray=new String[messageParts.size()];
			messageParts.toArray(messagePartsStrArray);
			logger.debug("Rating : WC:EXECPTION");
			BusinessException businessExcp=new BusinessException( errorKeys, messagePartsStrArray );
			throw businessExcp;
		}
		return factorList;
	}

	/**
	 * @param parVO
	 * @param renewalLoading
	 * @param locationInfo
	 * @param commonFactor 
	 * @return
	 */
	public ArrayList <FactorBO> getSectionSpeceficFactorsForPAR (
			ParVO parVO, BigDecimal renewalLoading, RiskGroup locationInfo,int PARrtgItmSeqNo, FactorBO commonFactor) throws BusinessException {
		// TODO Extract factors from the PAR VO and fill it to ArrayList <FactorBO>
		
		List <String>  errorKeys=new ArrayList<String> ();
		List <String> messageParts=new ArrayList<String>();
		ArrayList <FactorBO> factorList= new ArrayList();
		List <UWQuestionVO> uwQuestionsList=null;

		
		String buildingId= locationInfo.getRiskGroupId().toString();
		if(! Utils.isEmpty(buildingId ) ){
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_ITEM_SEQ_NO_FACTOR );
			factor.setFactorValue(String.valueOf( PARrtgItmSeqNo) );
			factorList.add( factor );	
		}else{
			
			errorKeys.add( com.Constant.CONST_RATING_PAR_ITM_NUM);
			messageParts.add(com.Constant.CONST_ITEM_SEQ_NUMBER_NOT_FOUND);
						
		}
		factorList.add(commonFactor);
		
		// Strat : UWQuestions
		if(null !=parVO.getUwQuestions().getQuestions()){
			
		
			// Add Sprinkler Factor
			for(UWQuestionVO uwQuestionVO:parVO.getUwQuestions().getQuestions()){
				
				if((!(null==uwQuestionVO.getQId()))  && (!(null==Utils.getSingleValueAppConfig( com.Constant.CONST_UWQ_CODE_PAR_SPRIKLERFLG)))){
					if(Utils.getSingleValueAppConfig( com.Constant.CONST_UWQ_CODE_PAR_SPRIKLERFLG).equalsIgnoreCase(uwQuestionVO.getQId().toString())){
						FactorBO factor = new FactorBO();
						
						
						if(null!=uwQuestionVO.getResponse()){
							if(uwQuestionVO.getResponse().equalsIgnoreCase( "yes" )){
								factor.setFactorValue( "Y");	
							}else{
								factor.setFactorValue( "N");
							}
							factor.setFactorName( SvcConstants.RATING_PAR_SPRINKLER_FACTOR );
							factorList.add( factor );	
							logger.debug("Rating : RATING_PAR_SPRINKLER_FACTOR"+factor.getFactorValue());
						}
						else{

/*							errorKeys.add( "rating.par.uwq.sprk");
							messageParts.add( "Response not set for UWQ_CODE_PAR_SPRIKLERFLG Question" );*/
							factor = new FactorBO();
							factor.setFactorName( SvcConstants.RATING_PAR_SPRINKLER_FACTOR );
							factor.setFactorValue("N" );
							factorList.add( factor );
							
							
						}
						
					}
					
				}
				
				// Storage Factor		
				if((!(null==uwQuestionVO.getQId()))  && (!(null==Utils.getSingleValueAppConfig( com.Constant.CONST_UWQ_CODE_PAR_STORAGEFLG)))){
					if(Utils.getSingleValueAppConfig( com.Constant.CONST_UWQ_CODE_PAR_STORAGEFLG).equalsIgnoreCase(uwQuestionVO.getQId().toString())){
						FactorBO factor = new FactorBO();
						
						
						if(null!=uwQuestionVO.getResponse()){
							if(uwQuestionVO.getResponse().equalsIgnoreCase( "yes" )){
								factor.setFactorValue( "Y");	
							}else{
								factor.setFactorValue( "N");
							}
							factor.setFactorName( SvcConstants.RATING_PAR_STORAGE_FACTOR );
							factorList.add( factor );	
							logger.debug("Rating : RATING_PAR_STORAGE_FACTOR"+factor.getFactorValue());
						}
						else{

/*							errorKeys.add( "rating.par.uwq.sprk");
							messageParts.add( "Response not set for UWQ_CODE_PAR_SPRIKLERFLG Question" );*/
							factor = new FactorBO();
							factor.setFactorName( SvcConstants.RATING_PAR_STORAGE_FACTOR );
							factor.setFactorValue("N" );
							factorList.add( factor );
							
							
						}
						
					}
					
				}
				
				// Add Alarm Factor
				if((!(null==uwQuestionVO.getQId()))  && (!(null==Utils.getSingleValueAppConfig( com.Constant.CONST_UWQ_CODE_PAR_ALARM_FLG)))){
					if(Utils.getSingleValueAppConfig( com.Constant.CONST_UWQ_CODE_PAR_ALARM_FLG).equalsIgnoreCase(uwQuestionVO.getQId().toString())){
						FactorBO factor = new FactorBO();
						
						
						if(null!=uwQuestionVO.getResponse()){
							if(uwQuestionVO.getResponse().equalsIgnoreCase( "yes" )){
								factor.setFactorValue( "Y");	
							}else{
								factor.setFactorValue( "N");
							}
							factor.setFactorName( SvcConstants.RATING_PAR_ALARM_FACTOR);
							factorList.add( factor );
							logger.debug("Rating : RATING_PAR_ALARM_FACTOR"+factor.getFactorValue());
						}
						else{

							/*errorKeys.add( "rating.par.uwq.alarm");
							messageParts.add( "Response not set for UWQ_CODE_PAR_ALARM_FLG Question" );
							*/
							
							factor = new FactorBO();
							factor.setFactorName( SvcConstants.RATING_PAR_ALARM_FACTOR );
							factor.setFactorValue("N" );
							factorList.add( factor );
							
						}
							
					}
					
				}
			}
			
			
		}
		// End : UWQuestions

		
		//Start: SI & Deductible
			if(!(Utils.isEmpty( parVO))){
				FactorBO factor = new FactorBO();
				Double deductible = new Double( 0 );
				Double sumInsured= new Double( 0 );
				if(!(Utils.isEmpty(parVO.getBldCover()))){
					sumInsured = parVO.getBldCover();
					factor = new FactorBO();
					factor.setFactorName( SvcConstants.RATING_PAR_SI_FACTOR);
					factor.setFactorValue( sumInsured.toString());
					factorList.add( factor );
					logger.debug("Rating : RATING_PAR_SI_FACTOR"+factor.getFactorValue());
				}else{
					/*errorKeys.add( "rating.par.nobldingdata");
					messageParts.add( "In Covers no data found for PAR building SI or Deductible " );*/
					factor = new FactorBO();
					factor.setFactorName( SvcConstants.RATING_PAR_SI_FACTOR );
					factor.setFactorValue("0" );
					factorList.add( factor );
				}
				logger.debug( "Sum Insured is - "+ sumInsured);
				if(!(Utils.isEmpty(parVO.getBldDeductibles()))){
				deductible = parVO.getBldDeductibles();
				factor = new FactorBO();
				factor.setFactorName( SvcConstants.RATING_PAR_DEDUCTIBLE_FACTOR);
				factor.setFactorValue( deductible.toString().substring( 0,deductible.toString().indexOf( "." ) ));	
				factorList.add( factor );
				logger.debug("Rating : RATING_PAR_DEDUCTIBLE_FACTOR"+factor.getFactorValue());
				}else{
					/*errorKeys.add( "rating.par.nobldingdata");
					messageParts.add( "In Covers no data found for PAR building deductible or Deductible " );*/
					factor = new FactorBO();
					factor.setFactorName( SvcConstants.RATING_PAR_DEDUCTIBLE_FACTOR );
					//factor.setFactorValue("2500" );
					String locationCode = String.valueOf( ( ( LocationVO )locationInfo).getRiskGroupId() );
					if( locationCode.equals( BAHRAIN_LOCATION_CODE ) ){
						factor.setFactorValue( DEFAULT_DEDUCTIBLE_BAHRAIN );
					}
					else{
						factor.setFactorValue( DEFAULT_DEDUCTIBLE );
					}
					factorList.add( factor );
				}
				
				
				
				
				
			}else{
				
				errorKeys.add( "rating.par.nobldingdata");
				messageParts.add( "In Covers no data found for PAR building SI or Deductible " );
				
			}
				
				
				
			
			//End: SI & Deductible
			
			
			if(!(Utils.isEmpty( parVO.getUwDetails() ))){
				FactorBO factor = new FactorBO();
				PARUWDetailsVO  uwDetails =(PARUWDetailsVO) parVO.getUwDetails();
				// Add Construction Type Factor		
				if(!(Utils.isEmpty( uwDetails.getConstructionType() ))){
					factor.setFactorName( SvcConstants.RATING_PAR_CONSTRUCT_TYPE_FACTOR);
					factor.setFactorValue( uwDetails.getConstructionType().toString());	
					factorList.add( factor );
					logger.debug("Rating : RATING_PAR_CONSTRUCT_TYPE_FACTOR"+factor.getFactorValue());
						
				}
				else{

					factor = new FactorBO();
					factor.setFactorName( SvcConstants.RATING_PAR_CONSTRUCT_TYPE_FACTOR );
					factor.setFactorValue("1" );
					factorList.add( factor );
					
					/*errorKeys.add( "rating.par.uwd.constr.type");
					messageParts.add( "Construction Type Factor not present for PAR section" );*/
					
					
				}
				
				// Add Hazard Level Factor
				if(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase( "30" ) || 
						Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase( "50" )){
					if(!(Utils.isEmpty( uwDetails.getHazardLevel()))){
						factor = new FactorBO();
						factor.setFactorName( SvcConstants.RATING_PAR_HAZARD_LEVEL_FACTOR);
						factor.setFactorValue( uwDetails.getHazardLevel().toString());	
						factorList.add( factor );
						logger.debug("Rating : RATING_PAR_HAZARD_LEVEL_FACTOR"+factor.getFactorValue());
							
					}
					else{
						/*errorKeys.add( "rating.par.uwd.hazlvl.type");
						messageParts.add( "Hazard Level Factor not present for PAR section" );
						*/
						factor = new FactorBO();
						factor.setFactorName( SvcConstants.RATING_PAR_HAZARD_LEVEL_FACTOR );
						factor.setFactorValue("1" );
						factorList.add( factor );
					}
				}else{
					if(! Utils.isEmpty(locationInfo.getRiskGroupId() ) ){
						LocationVO locationVO = (LocationVO)locationInfo;
						factor = new FactorBO();
						factor.setFactorName( SvcConstants.RATING_TRADE_GROUP_FACTOR );
						factor.setFactorValue(String.valueOf( locationVO.getOccTradeGroup()) );
						factorList.add( factor );
						logger.debug("Rating : RATING_TRADE_GROUP_FACTO_2"+factor.getFactorValue());
					}
					else{
						factor = new FactorBO();
						factor.setFactorName( SvcConstants.RATING_TRADE_GROUP_FACTOR );
						factor.setFactorValue( "1" );
						factorList.add( factor );
					}
				
				}
				
				
			}
			
			factorList.add(commonFactor);
			if (!(errorKeys.size()==0)){
				String [] messagePartsStrArray=new String[messageParts.size()];
				messageParts.toArray(messagePartsStrArray);
				
				logger.debug("Rating : PAR EXCEPTION");
				BusinessException businessExcp=new BusinessException( errorKeys, messagePartsStrArray );
				throw businessExcp;
			}
			
		return factorList ;
	}

	
	
	public ArrayList<FactorBO> getSectionSpeceficFactorsForPARContent( ParVO parVO, PropertyRiskDetails contentDetails, BigDecimal renewalLoading, RiskGroup locationInfo, int contentrtgItmSeqNo, FactorBO commonFactor,Integer tariff,Double si ){
		
		
		List <String>  errorKeys=new ArrayList<String> ();
		List <String> messageParts=new ArrayList<String>();
		ArrayList <FactorBO> factorList= new ArrayList <FactorBO> ();
		
		
		String buildingId= locationInfo.getRiskGroupId().toString();
		if(! Utils.isEmpty(buildingId ) ){
			FactorBO factor = new FactorBO();
			factor.setFactorName( SvcConstants.RATING_ITEM_SEQ_NO_FACTOR );
			factor.setFactorValue(String.valueOf( contentrtgItmSeqNo) );
			factorList.add( factor );	
		}else{
			
			errorKeys.add( com.Constant.CONST_RATING_PAR_ITM_NUM);
			messageParts.add(com.Constant.CONST_ITEM_SEQ_NUMBER_NOT_FOUND);
						
		}
		
		
		// Strat : UWQuestions
		if(null !=parVO.getUwQuestions().getQuestions()){
			
		
			// Add Sprinkler Factor
			for(UWQuestionVO uwQuestionVO:parVO.getUwQuestions().getQuestions()){
				
				if((!(null==uwQuestionVO.getQId()))  && (!(null==Utils.getSingleValueAppConfig( com.Constant.CONST_UWQ_CODE_PAR_SPRIKLERFLG)))){
					if(Utils.getSingleValueAppConfig( com.Constant.CONST_UWQ_CODE_PAR_SPRIKLERFLG).equalsIgnoreCase(uwQuestionVO.getQId().toString())){
						FactorBO factor = new FactorBO();
						
						
						if(null!=uwQuestionVO.getResponse()){
							if(uwQuestionVO.getResponse().equalsIgnoreCase( "yes" )){
								factor.setFactorValue( "Y");	
							}else{
								factor.setFactorValue( "N");
							}
							factor.setFactorName( SvcConstants.RATING_PAR_SPRINKLER_FACTOR );
							factorList.add( factor );	
							logger.debug("Rating : RATING_PAR_CONT_SPRINKLER_FACTOR"+factor.getFactorValue());
						}
						else{

							/*errorKeys.add( "rating.par.uwq.sprk");
							messageParts.add( "Response not set for UWQ_CODE_PAR_SPRIKLERFLG Question" );
							*/
							factor = new FactorBO();
							factor.setFactorName( SvcConstants.RATING_PAR_SPRINKLER_FACTOR );
							factor.setFactorValue("N" );
							factorList.add( factor );
							
						}
						
						
						/* Overriding factor value in case of Content being Portable Equipments as there is a flat premium defined
						 * for Portable Equipments with only contents cat being only factor with 0.01 as its value */
						overrideFactorValPortableEqpmts( contentDetails, factor, "Y" );
					}
					
				}
				
				// Storage Factor		
				if((!(null==uwQuestionVO.getQId()))  && (!(null==Utils.getSingleValueAppConfig( com.Constant.CONST_UWQ_CODE_PAR_STORAGEFLG)))){
					if(Utils.getSingleValueAppConfig( com.Constant.CONST_UWQ_CODE_PAR_STORAGEFLG).equalsIgnoreCase(uwQuestionVO.getQId().toString())){
						FactorBO factor = new FactorBO();
						
						
						if(null!=uwQuestionVO.getResponse()){
							if(uwQuestionVO.getResponse().equalsIgnoreCase( "yes" )){
								factor.setFactorValue( "Y");	
							}else{
								factor.setFactorValue( "N");
							}
							factor.setFactorName( SvcConstants.RATING_PAR_STORAGE_FACTOR );
							factorList.add( factor );	
							logger.debug("Rating : RATING_PAR_STORAGE_FACTOR"+factor.getFactorValue());
						}
						else{

/*							errorKeys.add( "rating.par.uwq.sprk");
							messageParts.add( "Response not set for UWQ_CODE_PAR_SPRIKLERFLG Question" );*/
							factor = new FactorBO();
							factor.setFactorName( SvcConstants.RATING_PAR_STORAGE_FACTOR );
							factor.setFactorValue("N" );
							factorList.add( factor );
							
							
						}
						
						/* Overriding factor value in case of Content being Portable Equipments as there is a flat premium defined
						 * for Portable Equipments with only contents cat being only factor with 0.01 as its value */
						overrideFactorValPortableEqpmts( contentDetails, factor, "N" );
					}
					
				}
				
				// Add Alarm Factor
				if((!(null==uwQuestionVO.getQId()))  && (!(null==Utils.getSingleValueAppConfig( com.Constant.CONST_UWQ_CODE_PAR_ALARM_FLG)))){
					if(Utils.getSingleValueAppConfig( com.Constant.CONST_UWQ_CODE_PAR_ALARM_FLG).equalsIgnoreCase(uwQuestionVO.getQId().toString())){
						FactorBO factor = new FactorBO();
						
						
						if(null!=uwQuestionVO.getResponse()){
							if(uwQuestionVO.getResponse().equalsIgnoreCase( "yes" )){
								factor.setFactorValue( "Y");	
							}else{
								factor.setFactorValue( "N");
							}
							factor.setFactorName( SvcConstants.RATING_PAR_ALARM_FACTOR);
							factorList.add( factor );
							logger.debug("Rating : RATING_PAR_CONT_ALARM_FACTOR"+factor.getFactorValue());
						}
						else{

							/*errorKeys.add( "rating.par.uwq.alarm");
							messageParts.add( "Response not set for UWQ_CODE_PAR_ALARM_FLG Question" );*/
							
							factor = new FactorBO();
							factor.setFactorName( SvcConstants.RATING_PAR_ALARM_FACTOR );
							factor.setFactorValue("N" );
							factorList.add( factor );
							
							
						}
						
						/* Overriding factor value in case of Content being Portable Equipments as there is a flat premium defined
						 * for Portable Equipments with only contents cat being only factor with 0.01 as its value */
						overrideFactorValPortableEqpmts( contentDetails, factor, "Y" );	
					}		
				}
			}
			
			
		}
		// End : UWQuestions

	
		//Start: SI & Deductible
			
				
			
				
				// First item in this list will be always of Building
		if(!(Utils.isEmpty(contentDetails))){
			FactorBO factor = new FactorBO();
			Double deductible = new Double( 0 );
			Double sumInsured= new Double( 0 );

				if(!(Utils.isEmpty( contentDetails.getDeductibles()) )){
					deductible = deductible+contentDetails.getDeductibles();
	
				}else{/*
					errorKeys.add( "rating.par.nocontentdeduct");
					messageParts.add( "In Covers no data found for Content Deductible " );*/
					factor = new FactorBO();
					factor.setFactorName( SvcConstants.RATING_PAR_DEDUCTIBLE_FACTOR );
					//factor.setFactorValue("2500" );
					String locationCode = String.valueOf( ( ( LocationVO )locationInfo).getRiskGroupId() );
					if( locationCode.equals( BAHRAIN_LOCATION_CODE ) ){
						factor.setFactorValue( DEFAULT_DEDUCTIBLE_BAHRAIN );
					}
					else{
						factor.setFactorValue( DEFAULT_DEDUCTIBLE );
					}
					factorList.add( factor );
				}
	
				if(!(Utils.isEmpty( contentDetails.getCover() ))){
					//For Sum insured get cover 
					sumInsured = sumInsured+ contentDetails.getCover();
				}
				else{
					/*errorKeys.add( "rating.par.nosi");
					messageParts.add( "In Covers no data found for PAR building SI " );*/
					factor = new FactorBO();
					factor.setFactorName( SvcConstants.RATING_CONTENT_SI_FACTOR );
					factor.setFactorValue("0" );
					factorList.add( factor );
					
				}
				// Add Deductible Factor
				factor.setFactorName( SvcConstants.RATING_PAR_DEDUCTIBLE_FACTOR);
				factor.setFactorValue( deductible.toString().substring( 0,deductible.toString().indexOf( "." ) ));	
				factorList.add( factor );
				logger.debug("Rating : RATING_PAR_CONT_DEDUCTIBLE_FACTOR"+factor.getFactorValue());
	
				// Add SI Factor		
				String[] alsalamTariffCodes = Utils.getMultiValueAppConfig( "ALSALAM_TECOM_SCHEMES" );
				if( Arrays.asList( alsalamTariffCodes ).contains( tariff.toString())) {
					sumInsured = si;
				}
				factor = new FactorBO();
				factor.setFactorName( SvcConstants.RATING_CONTENT_SI_FACTOR);
				factor.setFactorValue( sumInsured.toString());
				factorList.add( factor );
				logger.debug("Rating : RATING_CONTENT_SI_FACTOR"+factor.getFactorValue());
	
				// Additional Cover Electronic Equipment
				if(!Utils.isEmpty( contentDetails.getCoverCode())&& contentDetails.getCoverCode()==20){
					if(!(Utils.isEmpty( contentDetails.getCoverType() ))){
						int contentCategory= contentDetails.getCoverType();
						switch (contentCategory){
						case 1:{
							factor = new FactorBO();
							factor.setFactorName( SvcConstants.RATING_EE5_BUS_TYPE_FACTOR);
							if(contentDetails.getCoverOpted().equals( 1 )){
								factor.setFactorValue( commonFactor.getFactorValue());
								
							}else{
								factor.setFactorValue( "0");
							}
							logger.debug("Rating : RATING_EE5_BUS_TYPE_FACTOR"+factor.getFactorValue());
							factorList.add( factor );

							factor = new FactorBO();
							factor.setFactorName( SvcConstants.RATING_EE10_BUS_TYPE_FACTOR);
							factor.setFactorValue("0");
							factorList.add( factor );
							logger.debug("Rating : RATING_EE10_BUS_TYPE_FACTOR"+factor.getFactorValue());

							break;
						}
						case 2:{
							factor = new FactorBO();
							factor.setFactorName( SvcConstants.RATING_EE5_BUS_TYPE_FACTOR);
							factor.setFactorValue( "0");
							factorList.add( factor );
							logger.debug("Rating : RATING_EE5_BUS_TYPE_FACTOR"+factor.getFactorValue());
							
							factor = new FactorBO();
							factor.setFactorName( SvcConstants.RATING_EE10_BUS_TYPE_FACTOR);
							if(contentDetails.getCoverOpted().equals( 1 )){
								factor.setFactorValue( commonFactor.getFactorValue());
							}else{
								factor.setFactorValue( "0");
							}
							factorList.add( factor );
							logger.debug("Rating : RATING_EE10_BUS_TYPE_FACTOR"+factor.getFactorValue());
							break;
						}
						//sonar fix
						default:
							break;

						}
						factorList.add( factor );
						logger.debug("Rating : RATING_ADDITIONALCOVER_EE_FACTOR"+factor.getFactorValue());
					}
				}
				else{
					if(!(Utils.isEmpty( contentDetails.getRiskType() ))){
						factor = new FactorBO();
						factor.setFactorName( SvcConstants.RATING_CONTENT_CAT_FACTOR);
						//risType defines the content category
						int contentCategory= contentDetails.getRiskType();
						switch (contentCategory){
						case 0:factor.setFactorValue( "Occupancy");break;
						case 1:factor.setFactorValue( "Furnitures and Fixtures");break;
						case 9:factor.setFactorValue( "Stocks");break;
						case 13:factor.setFactorValue( "Rent");break;
						case 999:factor.setFactorValue( "Basic Contents");break;
						case 21:factor.setFactorValue( "Portable Equipment" );break;
						case 28:factor.setFactorValue( "Electronic Equipment" );break;
						//sonar fix
						default:
							break;
						}

						factorList.add( factor );
						logger.debug("Rating : RATING_CONTENT_CAT_FACTOR"+factor.getFactorValue());


					}else{

						/*errorKeys.add( "rating.par.content.norisktype");
					messageParts.add( "In Covers no data found for cover risk type " );*/

						factor = new FactorBO();
						factor.setFactorName( SvcConstants.RATING_CONTENT_CAT_FACTOR);
						factor.setFactorValue("Furnitures and Fixtures" );
						factorList.add( factor );


					}
				}
		}else{
					
					errorKeys.add( "rating.par.nobldingdata");
					messageParts.add( "In Covers no data found for PAR building SI or Deductible " );
					
				}
				
				
				 
		
			
			//End: Covers
			
			
			if(!(Utils.isEmpty( parVO.getUwDetails() ))){
				FactorBO factor = new FactorBO();
				PARUWDetailsVO  uwDetails =(PARUWDetailsVO) parVO.getUwDetails();
				// Add Construction Type Factor		
				if(!(Utils.isEmpty( uwDetails.getConstructionType() ))){
					factor.setFactorName( SvcConstants.RATING_PAR_CONSTRUCT_TYPE_FACTOR);
					factor.setFactorValue( uwDetails.getConstructionType().toString());	
					factorList.add( factor );
						
					logger.debug("Rating : RATING_PAR_CONT_CONSTRUCT_TYPE_FACTOR"+factor.getFactorValue());
				}
				else{

					factor.setFactorName( SvcConstants.RATING_PAR_CONSTRUCT_TYPE_FACTOR);
					factor.setFactorValue(String.valueOf(1));	
					factorList.add( factor );
					logger.debug("Rating : RATING_PAR_CONSTRUCT_TYPE_FACTOR:DEFAULTED"+factor.getFactorValue());
					
					/*errorKeys.add( "rating.par.uwd.constr.type");
					messageParts.add( "Construction Type Factor not present for PAR section" );*/
					
					
				}
				/* Overriding factor value in case of Content being Portable Equipments as there is a flat premium defined
				 * for Portable Equipments with only contents cat being only factor with 0.01 as its value */
				overrideFactorValPortableEqpmts( contentDetails, factor, "1");
				
				
				//Changes done as required for CR, ADVN ID : 93276
				if(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase( "30" ) || 
						Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase( "50" )){
					if(!(Utils.isEmpty( uwDetails.getHazardLevel()))){
						factor = new FactorBO();
						factor.setFactorName( SvcConstants.RATING_PAR_HAZARD_LEVEL_FACTOR);
						factor.setFactorValue( uwDetails.getHazardLevel().toString());	
						factorList.add( factor );
						logger.debug("Rating : RATING_PAR_HAZARD_LEVEL_FACTOR"+factor.getFactorValue());
							
					}
					else{
						/*errorKeys.add( "rating.par.uwd.hazlvl.type");
						messageParts.add( "Hazard Level Factor not present for PAR section" );
						*/
						factor = new FactorBO();
						factor.setFactorName( SvcConstants.RATING_PAR_HAZARD_LEVEL_FACTOR );
						factor.setFactorValue("1" );
						factorList.add( factor );
					}
				}else{
					if(! Utils.isEmpty(locationInfo.getRiskGroupId() ) ){
						LocationVO locationVO = (LocationVO)locationInfo;
						factor = new FactorBO();
						factor.setFactorName( SvcConstants.RATING_TRADE_GROUP_FACTOR );
						factor.setFactorValue(String.valueOf( locationVO.getOccTradeGroup()) );
						factorList.add( factor );
						logger.debug("Rating : RATING_TRADE_GROUP_FACTO_3"+factor.getFactorValue());
					}
					else{
						factor = new FactorBO();
						factor.setFactorName( SvcConstants.RATING_TRADE_GROUP_FACTOR );
						factor.setFactorValue( "1" );
						factorList.add( factor );
					}
				}
				
				/* Overriding factor value in case of Content being Portable Equipments as there is a flat premium defined
				 * for Portable Equipments with only contents cat being only factor with 0.01 as its value */
				overrideFactorValPortableEqpmts( contentDetails, factor, SvcConstants.PAR_CNT_DEFAULT_HAZARD_LEVEL_PORTABLE_EQPMT);
				
				
			}
			
			factorList.add(commonFactor);
			
			if (!(errorKeys.size()==0)){
				String [] messagePartsStrArray=new String[messageParts.size()];
				messageParts.toArray(messagePartsStrArray);
				logger.debug("Rating : PAR_CONT EXCEPTION");
				BusinessException businessExcp=new BusinessException( errorKeys, messagePartsStrArray );
				throw businessExcp;
			}
		
		
		return factorList;
	}
	
	/**
	 * @param riskGroupDetails
	 * @return
	 */
	/*Added prepared Date as argument to pass to Rating service - Ticket Id: 140443 */
	public ArrayList<FactorBO> getCLSPEKey(RiskGroupDetails riskDetails,RiskGroup locationInfo, String sectionName,SchemeVO schemeVO,Date quotePreparedDt) {
		 //Identify the section by checking sectionName parameter
		 // We may have to fill different factors for different  section
		 // This can be achieved through invoking curresponding methode from PrepareCLSPEKeyUtil class 
		
		PrepareCLSPEKeyUtil prepareCLSPEKeyUtil=new PrepareCLSPEKeyUtil();
		return prepareCLSPEKeyUtil.getCLSPEKey( riskDetails, locationInfo, sectionName, schemeVO,quotePreparedDt );

		
	}
	
		/**
	 * Overrides factorBO.factorValue with the value passed when the PAR content is Portable Equipments 
	 * with risk type - SvcConstants.PAR_CNT_PORTABLE_EQUIPMENT_RSKTYPE
	 * @param contentDetails
	 * @param factor
	 * @param factorValueToBeSet
	 */
	private void overrideFactorValPortableEqpmts( PropertyRiskDetails contentDetails, FactorBO factor, String factorValueToBeSet ){
		/* Overriding factor value in case of Content being Portable Equipments as there is a flat premium defined
		 * for Portable Equipments with only contents cat being only factor with 0.01 as its value */
		if( !Utils.isEmpty( contentDetails ) && !Utils.isEmpty( contentDetails.getRiskType() ) ){
			if( contentDetails.getRiskType().intValue() == SvcConstants.PAR_CNT_PORTABLE_EQUIPMENT_RSKTYPE.intValue() ){
				factor.setFactorValue( factorValueToBeSet );
			}
		}
	}

	public ArrayList<FactorBO> getSectionSpeceficFactorsForBI( BIVO biVO, BigDecimal renewalLoading, RiskGroup locationInfo, int ratingItemNumber, FactorBO commonFactor,Double biAvgPrmPar ){
		List <String>  errorKeys=new ArrayList<String> ();
		List <String> messageParts=new ArrayList<String>();
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		String buildingId = "";
		FactorBO factor = null ;
		if(!Utils.isEmpty( locationInfo ) && !Utils.isEmpty( locationInfo.getRiskGroupId() ))
		{
			buildingId = locationInfo.getRiskGroupId().toString();
		}
		
		if(! Utils.isEmpty(buildingId ) ){
			factor = setFactor(SvcConstants.RATING_ITEM_SEQ_NO_FACTOR , String.valueOf( ratingItemNumber ));
			factorList.add( factor );	
		}else{
			errorKeys.add( com.Constant.CONST_RATING_BI_ITM_NUM);
			messageParts.add(com.Constant.CONST_ITEM_SEQUENCE_NUMBER_NOT_SET_END);
		}
		
		if(!Utils.isEmpty(biVO)) {
			//BI AVG PRM PAR
			if(!Utils.isEmpty( biVO.getIndemnityPeriod() ) ){
				factor = setFactor(SvcConstants.RATING_BI_AVG_PRM_PAR , String.valueOf(biAvgPrmPar));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_BI_AVG_PRM_PAR , String.valueOf( SvcConstants.RATING_BI_AVG_PRM_PAR_DEFAULT ));
				factorList.add( factor );
			}
			
			//BI hazard level
			if(!Utils.isEmpty( biVO.getHazardLevel()) ){
				if(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase( "30" ) || 
						Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase( "50" )){
					factor = setFactor(SvcConstants.RATING_BI_INDEMNIY_PERIOD , String.valueOf( biVO.getHazardLevel() ));
				}
				else
				{
					factor = setFactor(SvcConstants.RATING_BI_INDEMNIY_PERIOD , String.valueOf( ((LocationVO)locationInfo).getOccTradeGroup()));
				}
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_BI_INDEMNIY_PERIOD , String.valueOf( SvcConstants.RATING_BI_INDEMPRD_DEFAULT ));
				factorList.add( factor );
			}

			//BI Deductible
			if(!Utils.isEmpty( biVO.getDeductible() ) ){
				factor = setFactor(SvcConstants.RATING_BI_DEDUCTIBLE , String.valueOf( biVO.getDeductible() ));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_BI_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_BI_DEDUCTIBLE_DEFAULT ));
				factorList.add( factor );
			}
			
			//BI SI
			if(!Utils.isEmpty( biVO.getSumInsured() ) ){
				factor = setFactor(SvcConstants.RATING_BI_SI , String.valueOf( biVO.getSumInsured() ));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_BI_SI , String.valueOf( SvcConstants.RATING_BI_SI_DEFAULT ));
				factorList.add( factor );
			}
		}
		factorList.add(commonFactor);
		return factorList;
	}

	

	public ArrayList<FactorBO> getSectionSpeceficFactorsForMB( MachineDetailsVO machineDetailsVO, BigDecimal renewalLoading, RiskGroup locationInfo, int ratingItemNumber, FactorBO commonFactor ){

		List <String>  errorKeys=new ArrayList<String> ();
		List <String> messageParts=new ArrayList<String>();
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		String buildingId = "";
		Double sumInsured = 0.0;
		FactorBO factor = null ;
		if(!Utils.isEmpty( locationInfo ) && !Utils.isEmpty( locationInfo.getRiskGroupId() ))
		{
			buildingId = locationInfo.getRiskGroupId().toString();
		}		
		if(! Utils.isEmpty(buildingId ) ){
			factor = setFactor(SvcConstants.RATING_ITEM_SEQ_NO_FACTOR , String.valueOf( ratingItemNumber ));
			factorList.add( factor );	
		}else{
			errorKeys.add( com.Constant.CONST_RATING_BI_ITM_NUM);
			messageParts.add(com.Constant.CONST_ITEM_SEQUENCE_NUMBER_NOT_SET_END);
		}
		if(!Utils.isEmpty(machineDetailsVO)) {
			//MB SI
			SumInsuredVO sumInsuredVO = machineDetailsVO.getSumInsuredVO();
			if(!Utils.isEmpty(sumInsuredVO) ){
				sumInsured = sumInsuredVO.getSumInsured();
			}
					
			if(!Utils.isEmpty( sumInsured ) ){
				factor = setFactor(SvcConstants.RATING_MB_SI , String.valueOf( sumInsured ));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_MB_SI , String.valueOf( SvcConstants.RATING_MB_SI_DEFAULT ));
				factorList.add( factor );
			}
			

			//MB Cat
			if(!Utils.isEmpty( machineDetailsVO.getMachineryType() ) ){
				factor = setFactor(SvcConstants.RATING_MB_CATEGORY ,  String.valueOf( machineDetailsVO.getMachineryType() ));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_MB_CATEGORY , String.valueOf( SvcConstants.RATING_MB_CATEGORY_DEFAULT ));
				factorList.add( factor );
			}
			
		}
		factorList.add(commonFactor);
		return factorList;
	
	}

	public ArrayList<FactorBO> getSectionSpeceficFactorsForEEQ( EquipmentVO equipmentVO, BigDecimal renewalLoading, RiskGroup locationInfo, int ratingItemNumber, FactorBO commonFactor,EEVO eevo ){


		List <String>  errorKeys=new ArrayList<String> ();
		List <String> messageParts=new ArrayList<String>();
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		String buildingId = "";
		Double sumInsured = 0.0;
		Double deductible = 0.0;
		FactorBO factor = null ;
		String equipType = "";
		Integer noOfItems = 0;
		if(!Utils.isEmpty( locationInfo ) && !Utils.isEmpty( locationInfo.getRiskGroupId() ))
		{
			buildingId = locationInfo.getRiskGroupId().toString();
		}		
		if(! Utils.isEmpty(buildingId ) ){
			factor = setFactor(SvcConstants.RATING_ITEM_SEQ_NO_FACTOR , String.valueOf( ratingItemNumber ));
			factorList.add( factor );	
		}else{
			errorKeys.add( com.Constant.CONST_RATING_BI_ITM_NUM);
			messageParts.add(com.Constant.CONST_ITEM_SEQUENCE_NUMBER_NOT_SET_END);
		}

		if(!Utils.isEmpty(equipmentVO)) {
			SumInsuredVO sumInsuredVO = equipmentVO.getSumInsuredDetails();
			if(!Utils.isEmpty( sumInsuredVO )){
				deductible = sumInsuredVO.getDeductible();
				sumInsured = sumInsuredVO.getSumInsured();
			}
			equipType = getEquipmentTypeForRatingEngine(equipmentVO.getEquipmentType(),eevo,(LocationVO)locationInfo);
			noOfItems = equipmentVO.getQuantity();
			if( !Utils.isEmpty( equipmentVO.getQuantity() ) && equipmentVO.getQuantity().equals( CommonConstants.DEFAULT_LOW_LONG.intValue() ) ){
				noOfItems = null;
			}
			//EE SI
			if(!Utils.isEmpty( sumInsured ) ){
				factor = setFactor(SvcConstants.RATING_EE_SI , String.valueOf( sumInsured ));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_EE_SI , String.valueOf( SvcConstants.RATING_EE_SI_DEFAULT ));
				factorList.add( factor );
			}
			logger.debug( "Number of Items - "+noOfItems );
			//EE Cat
			if(!Utils.isEmpty( equipType ) ){
				factor = setFactor(SvcConstants.RATING_EE_CATEGORY ,  equipType);
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_EE_CATEGORY , String.valueOf( SvcConstants.RATING_EE_CATEGORY_DEFAULT ));
				factorList.add( factor );
			}


			//BI Deductible
			if(!Utils.isEmpty( deductible ) ){
				factor = setFactor(SvcConstants.RATING_EE_DEDUCTIBLE , String.valueOf(deductible.toString().substring( 0,deductible.toString().indexOf( "." ))));
				factorList.add( factor );	
			}else{
				//factor = setFactor(SvcConstants.RATING_EE_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_EE_DEDUCTIBLE_DEFAULT ));
				if( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equals( BAHRAIN_LOCATION_CODE ) ){
					factor = setFactor(SvcConstants.RATING_EE_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_EE_DEDUCTIBLE_BAHRAIN_DEFAULT ));			
				}
				else{
					factor = setFactor(SvcConstants.RATING_EE_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_EE_DEDUCTIBLE_DEFAULT));
				}
				factorList.add( factor );
			}
			
			//EE Items
			if(!Utils.isEmpty( noOfItems ) ){
				factor = setFactor(SvcConstants.RATING_EE_ITEMS , String.valueOf( noOfItems ));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_EE_ITEMS , String.valueOf( SvcConstants.RATING_EE_ITEMS_DEFAULT ));
				factorList.add( factor );
			}
			
		}
		factorList.add(commonFactor);
		return factorList;
	
	
	}

	public ArrayList<FactorBO> getSectionSpeceficFactorsForTravel( TravelBaggageVO tbVO,Double limitSI,Double deductible, BigDecimal renewalLoading, RiskGroup locationInfo, int ratingItemNumber,
			FactorBO commonFactor,double amountDeducted ){


		List <String>  errorKeys=new ArrayList<String> ();
		List <String> messageParts=new ArrayList<String>();
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		String buildingId = "";
		int noOfPersons = 0;
		/*
		Double limitSI = 0.0;
		Double deductible = 0.0;  */
		
		FactorBO factor = null ;
		if(!Utils.isEmpty( locationInfo ) && !Utils.isEmpty( locationInfo.getRiskGroupId() ))
		{
			buildingId = locationInfo.getRiskGroupId().toString();
		}		
		if(! Utils.isEmpty(buildingId ) ){
			factor = setFactor(SvcConstants.RATING_ITEM_SEQ_NO_FACTOR , String.valueOf( ratingItemNumber ));
			factorList.add( factor );	
		}else{
			errorKeys.add( com.Constant.CONST_RATING_BI_ITM_NUM);
			messageParts.add(com.Constant.CONST_ITEM_SEQUENCE_NUMBER_NOT_SET_END);
		}

		if(!Utils.isEmpty(tbVO)) {
			
			
			/*
			 * commenting this code for travel baggage calculation
			 * 
			 */
		//	List<TravellingEmployeeVO> employeeVOs = tbVO.getTravellingEmpDets();
			//noOfPersons = employeeVOs.size();
		/*	for(TravellingEmployeeVO employeeVO : employeeVOs){
				if(!Utils.isEmpty( employeeVO )){
					SumInsuredVO sumInsuredVO = employeeVO.getSumInsuredDtl();
					if(!Utils.isEmpty( sumInsuredVO )){
						limitSI += sumInsuredVO.getSumInsured() ;
						deductible = sumInsuredVO.getDeductible();
					}
					noOfPersons++;
				}
				
			}  */
			
			//TravelB Cat
				factor = setFactor(SvcConstants.RATING_TB_CAT , String.valueOf( SvcConstants.RATING_TB_CAT_DEFAULT ));
				factorList.add( factor );	
			
			
			//TB Limit SI
				limitSI = limitSI - amountDeducted;
			if(!Utils.isEmpty( tbVO.getSumInsured() ) ){
				factor = setFactor(SvcConstants.RATING_TB_LIMIT_SI ,  String.valueOf( limitSI ));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_TB_LIMIT_SI , String.valueOf( SvcConstants.RATING_TB_LIMIT_SI_DEFAULT ));
				factorList.add( factor );
			}
			
			//TB No of People
			/*if(!Utils.isEmpty(tbVO.getTravellingEmpDets())){
				noOfPersons = tbVO.getTravellingEmpDets().size();
			}*/
			if(noOfPersons != 0 ){
				factor = setFactor(SvcConstants.RATING_TB_NO_OF_PEOPLE ,  String.valueOf( noOfPersons ));
				factorList.add( factor );	
			}else{ 
				/*
				 * changing this constant value for travel baggage calculation ticket id 69851
				 */
				factor = setFactor(SvcConstants.RATING_TB_NO_OF_PEOPLE , String.valueOf( SvcConstants.RATING_TB_NO_OF_PEOPLE_DEFAULT_NEW ));
				factorList.add( factor );
			}
			
			//TB Deductible
			if(!Utils.isEmpty( deductible ) ){
				factor = setFactor(SvcConstants.RATING_TB_DEDUCTIBLE ,  String.valueOf( deductible.toString().substring( 0,deductible.toString().indexOf( "." ))));
				factorList.add( factor );	
			}else{
				//factor = setFactor(SvcConstants.RATING_TB_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_TB_DEDUCTIBLE_DEFAULT ));
				if( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equals( BAHRAIN_LOCATION_CODE ) ){
					factor = setFactor(SvcConstants.RATING_TB_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_TB_DEDUCTIBLE_BAHRAIN_DEFAULT ));			
				}
				else{
					factor = setFactor(SvcConstants.RATING_TB_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_TB_DEDUCTIBLE_DEFAULT));
				}
				factorList.add( factor );
			}
		}
		factorList.add(commonFactor);
		return factorList;
	
	
	}

	
	public ArrayList<FactorBO> getSectionSpeceficFactorsForGPAUnnamed( GPAUnnammedEmpVO gpaUnnammedEmpVO , BigDecimal renewalLoading, RiskGroup locationInfo, int ratingItemNumber,
			FactorBO commonFactor ){


		List <String>  errorKeys=new ArrayList<String> ();
		List <String> messageParts=new ArrayList<String>();
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		String buildingId = "";
		Double capitalSumInsured = 0.0;
		Integer empType;
		Integer empNumber;
		
		FactorBO factor = null ;
		if(!Utils.isEmpty( locationInfo ) && !Utils.isEmpty( locationInfo.getRiskGroupId() ))
		{
			buildingId = locationInfo.getRiskGroupId().toString();
		}		
		if(! Utils.isEmpty(buildingId ) ){
			factor = setFactor(SvcConstants.RATING_ITEM_SEQ_NO_FACTOR , String.valueOf( ratingItemNumber ));
			factorList.add( factor );	
		}else{
			errorKeys.add( com.Constant.CONST_RATING_BI_ITM_NUM);
			messageParts.add(com.Constant.CONST_ITEM_SEQUENCE_NUMBER_NOT_SET_END);
		}

		if(!Utils.isEmpty(gpaUnnammedEmpVO)) {
			if(!Utils.isEmpty( gpaUnnammedEmpVO.getSumInsuredDetails())) 
			{
				capitalSumInsured = gpaUnnammedEmpVO.getSumInsuredDetails().getSumInsured();
			}
			
			empType = gpaUnnammedEmpVO.getUnnammedEmployeeType();
			

			
			//GPA Capital SI
			if(!Utils.isEmpty( capitalSumInsured ) )
			{
				factor = setFactor(SvcConstants.RATING_GPA_CAPITAL_SI ,  String.valueOf( capitalSumInsured ));
				factorList.add( factor );	
			}
			else
			{
				factor = setFactor(SvcConstants.RATING_GPA_CAPITAL_SI , String.valueOf( SvcConstants.RATING_GPA_CAPITAL_SI_DEFAULT ));
				factorList.add( factor );
			}
			
			
			//GPA number of employees
			empNumber = gpaUnnammedEmpVO.getUnnammedNumberOfEmloyee();
			if(!Utils.isEmpty( empNumber ) )
			{
				factor = setFactor(SvcConstants.RATING_GPA_NO_OF_EMP ,  String.valueOf( empNumber ));
				factorList.add( factor );	
			}
			else
			{
				factor = setFactor(SvcConstants.RATING_GPA_NO_OF_EMP , String.valueOf( SvcConstants.RATING_GPA_NO_OF_EMP_DEFAULT ));
				factorList.add( factor );
			}
			
			//GPA EMPTYPE
			if(!Utils.isEmpty( empType ) ){
				factor = setFactor(SvcConstants.RATING_GPA_TYPE_OF_EMP ,  String.valueOf( empType.toString()));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_GPA_TYPE_OF_EMP , String.valueOf( SvcConstants.RATING_GPA_TYPE_OF_EMP_DEFAULT ));
				factorList.add( factor );
			}
		}
		factorList.add(commonFactor);
		return factorList;
	
	
	}

	public ArrayList<FactorBO> getSectionSpeceficFactorsForGPANamed( GPANammedEmpVO gpaNammedEmpVO , BigDecimal renewalLoading, RiskGroup locationInfo, int ratingItemNumber,
			FactorBO commonFactor ){


		List <String>  errorKeys=new ArrayList<String> ();
		List <String> messageParts=new ArrayList<String>();
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		String buildingId = "";
		Double capitalSumInsured = 0.0;
		Integer empType;
		
		FactorBO factor = null ;
		if(!Utils.isEmpty( locationInfo ) && !Utils.isEmpty( locationInfo.getRiskGroupId() ))
		{
			buildingId = locationInfo.getRiskGroupId().toString();
		}		
		if(! Utils.isEmpty(buildingId ) ){
			factor = setFactor(SvcConstants.RATING_ITEM_SEQ_NO_FACTOR , String.valueOf( ratingItemNumber ));
			factorList.add( factor );	
		}else{
			errorKeys.add( com.Constant.CONST_RATING_BI_ITM_NUM);
			messageParts.add(com.Constant.CONST_ITEM_SEQUENCE_NUMBER_NOT_SET_END);
		}

		if(!Utils.isEmpty(gpaNammedEmpVO)) {
			if(!Utils.isEmpty( gpaNammedEmpVO.getSumInsuredDetails())) {
				capitalSumInsured = gpaNammedEmpVO.getSumInsuredDetails().getSumInsured();
			}
			
			empType = gpaNammedEmpVO.getEmployeeType();
			

			
			//GPA Capital SI
			if(!Utils.isEmpty( capitalSumInsured ) ){
				factor = setFactor(SvcConstants.RATING_GPA_CAPITAL_SI ,  String.valueOf( capitalSumInsured ));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_GPA_CAPITAL_SI , String.valueOf( SvcConstants.RATING_GPA_CAPITAL_SI_DEFAULT ));
				factorList.add( factor );
			}
			
			factor = setFactor(SvcConstants.RATING_GPA_NO_OF_EMP , String.valueOf( SvcConstants.RATING_GPA_NO_OF_EMP_DEFAULT ));
			factorList.add( factor );
			
			//GPA EMPTYPE
			if(!Utils.isEmpty( empType ) ){
				factor = setFactor(SvcConstants.RATING_GPA_TYPE_OF_EMP ,  String.valueOf( empType.toString()));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_GPA_TYPE_OF_EMP , String.valueOf( SvcConstants.RATING_GPA_TYPE_OF_EMP_DEFAULT ));
				factorList.add( factor );
			}
		}
		factorList.add(commonFactor);
		return factorList;
	
	
	}
	
	public ArrayList<FactorBO> getSectionSpeceficFactorsForGIT( GoodsInTransitVO goodsInTransitVO,CommodityDetailsVO commodityDetailsVO, BigDecimal renewalLoading, RiskGroup locationInfo, int ratingItemNumber,
			FactorBO commonFactor ){


		List <String>  errorKeys=new ArrayList<String> ();
		List <String> messageParts=new ArrayList<String>();
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		String buildingId = "";
		Long singleTruckLimit;
		Long estAnnualCarry;
		Double deductible = 0.0;
		Integer commodityType = 0;
		FactorBO factor = null ;
		if(!Utils.isEmpty( locationInfo ) && !Utils.isEmpty( locationInfo.getRiskGroupId() ))
		{
			buildingId = locationInfo.getRiskGroupId().toString();
		}		
		if(! Utils.isEmpty(buildingId ) ){
			factor = setFactor(SvcConstants.RATING_ITEM_SEQ_NO_FACTOR , String.valueOf( ratingItemNumber ));
			factorList.add( factor );	
		}else{
			errorKeys.add( com.Constant.CONST_RATING_BI_ITM_NUM);
			messageParts.add(com.Constant.CONST_ITEM_SEQUENCE_NUMBER_NOT_SET_END);
		}

		if(!Utils.isEmpty(goodsInTransitVO)) {
			
			singleTruckLimit = goodsInTransitVO.getSingleTransitLimit();
			estAnnualCarry = goodsInTransitVO.getEstimatedAnnualCarryValue();
			
			if(!Utils.isEmpty(commodityDetailsVO)) {
				commodityType = commodityDetailsVO.getCommodityType();
				deductible = goodsInTransitVO.getDeductible();
			}
			
			//GIT single truck limit
			if(!Utils.isEmpty( singleTruckLimit ) ){
				factor = setFactor(SvcConstants.RATING_GIT_SINGLE_TRUCK_LIMIT , String.valueOf( singleTruckLimit ));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_GIT_SINGLE_TRUCK_LIMIT , String.valueOf( SvcConstants.RATING_GIT_SINGLE_TRUCK_LIMIT_DEFAULT ));
				factorList.add( factor );
			}
			
			//GIT Commodity type
			if(!Utils.isEmpty( commodityType ) ){
				factor = setFactor(SvcConstants.RATING_GIT_COMMODITY_TYPE ,  String.valueOf( commodityType ));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_GIT_COMMODITY_TYPE , String.valueOf( SvcConstants.RATING_GIT_COMMODITY_TYPE_DEFAULT ));
				factorList.add( factor );
			}
			
			//GIT deductible
			
			if(!Utils.isEmpty( deductible )){
				factor = setFactor(SvcConstants.RATING_GIT_DEDUCTIBLE ,  String.valueOf( deductible.toString().substring( 0,deductible.toString().indexOf( "." ))));
				factorList.add( factor );	
			}else{
				//factor = setFactor(SvcConstants.RATING_GIT_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_GIT_DEDUCTIBLE_DEFAULT ));
				if( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equals( BAHRAIN_LOCATION_CODE ) ){
					factor = setFactor(SvcConstants.RATING_GIT_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_GIT_DEDUCTIBLE_BAHRAIN_DEFAULT ));		
				}
				else{
					factor = setFactor(SvcConstants.RATING_GIT_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_GIT_DEDUCTIBLE_DEFAULT ));
				}
				factorList.add( factor );
			}
			
			//Estimated annual turnover
			if(!Utils.isEmpty( estAnnualCarry ) ){
				factor = setFactor(SvcConstants.RATING_GIT_EST_ANNUAL_TURNOVER ,  String.valueOf( estAnnualCarry.toString()));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_GIT_EST_ANNUAL_TURNOVER , String.valueOf( SvcConstants.RATING_GIT_EST_ANNUAL_TURNOVER_DEFAULT ));
				factorList.add( factor );
			}
		}
		factorList.add(commonFactor);
		return factorList;
	
	
	}

	public ArrayList<FactorBO> getSectionSpeceficFactorsForDOS(DeteriorationOfStockDetailsVO deteriorationOfStockDetailsVO, BigDecimal renewalLoading, RiskGroup locationInfo, int ratingItemNumber,
			FactorBO commonFactor ){


		List <String>  errorKeys=new ArrayList<String> ();
		List <String> messageParts=new ArrayList<String>();
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		String buildingId = "";
		Double capitalSumInsured = 0.0;
		Integer dosCat;
		
		FactorBO factor = null ;
		if(!Utils.isEmpty( locationInfo ) && !Utils.isEmpty( locationInfo.getRiskGroupId() ))
		{
			buildingId = locationInfo.getRiskGroupId().toString();
		}		
		if(! Utils.isEmpty(buildingId ) ){
			factor = setFactor(SvcConstants.RATING_ITEM_SEQ_NO_FACTOR , String.valueOf( ratingItemNumber ));
			factorList.add( factor );	
		}else{
			errorKeys.add( com.Constant.CONST_RATING_BI_ITM_NUM);
			messageParts.add(com.Constant.CONST_ITEM_SEQUENCE_NUMBER_NOT_SET_END);
		}

		if(!Utils.isEmpty(deteriorationOfStockDetailsVO)) {
			if(!Utils.isEmpty( deteriorationOfStockDetailsVO.getSumInsuredDetails())) {
				capitalSumInsured = deteriorationOfStockDetailsVO.getSumInsuredDetails().getSumInsured();
			}
			
			//DOS SI
			if(!Utils.isEmpty( capitalSumInsured ) ){
				factor = setFactor(SvcConstants.RATING_DOS_CAPITAL_SI ,  String.valueOf( capitalSumInsured ));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_DOS_CAPITAL_SI , String.valueOf( SvcConstants.RATING_DOS_CAPITAL_SI_DEFAULT ));
				factorList.add( factor );
			}
			
			//DOS CAT type
			if(!Utils.isEmpty( deteriorationOfStockDetailsVO.getDeteriorationOfStockType() ) ){
				factor = setFactor(SvcConstants.RATING_DOS_CAT_TYPE ,  String.valueOf( deteriorationOfStockDetailsVO.getDeteriorationOfStockType()));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_DOS_CAT_TYPE , String.valueOf( SvcConstants.RATING_DOS_CAT_TYPE_DEFAULT ));
				factorList.add( factor );
			}
		}
		factorList.add(commonFactor);
		return factorList;
	
	}
	public ArrayList<FactorBO> getSectionSpeceficFactorsForFIDUnnamed( FidelityUnnammedEmployeeVO fidelityUnnammedEmployeeVO , FidelityVO fidelityVO, BigDecimal renewalLoading, RiskGroup locationInfo, int ratingItemNumber,
			FactorBO commonFactor ){

		List <String>  errorKeys=new ArrayList<String> ();
		List <String> messageParts=new ArrayList<String>();
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		String buildingId = "";
		/*Integer fidEmpType = 0;*/
		
		FactorBO factor = null ;
		if(!Utils.isEmpty( locationInfo ) && !Utils.isEmpty( locationInfo.getRiskGroupId() ))
		{
			buildingId = locationInfo.getRiskGroupId().toString();
		}		
		if(! Utils.isEmpty(buildingId ) ){
			factor = setFactor(SvcConstants.RATING_ITEM_SEQ_NO_FACTOR , String.valueOf( ratingItemNumber ));
			factorList.add( factor );	
		}else{
			errorKeys.add( com.Constant.CONST_RATING_BI_ITM_NUM);
			messageParts.add(com.Constant.CONST_ITEM_SEQUENCE_NUMBER_NOT_SET_END);
		}

		if(!Utils.isEmpty(fidelityUnnammedEmployeeVO)) {
			
			if(!Utils.isEmpty( fidelityUnnammedEmployeeVO.getTotalNumberOfEmployee())){
				factor = setFactor(SvcConstants.RATING_FID_NO_OF_EMP ,  String.valueOf(fidelityUnnammedEmployeeVO.getTotalNumberOfEmployee()));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_FID_NO_OF_EMP , String.valueOf( SvcConstants.RATING_FID_NO_OF_EMP_DEFAULT ));
				factorList.add( factor );
			}
						
			if(!Utils.isEmpty( fidelityVO.getDeductible())){
				factor = setFactor(SvcConstants.RATING_FID_DEDUCTIBLE ,  String.valueOf( fidelityVO.getDeductible().longValue()));
				factorList.add( factor );	
			}else{
				//factor = setFactor(SvcConstants.RATING_FID_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_FID_DEDUCTIBLE_DEFAULT ));
				if( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equals( BAHRAIN_LOCATION_CODE ) ){
					factor = setFactor(SvcConstants.RATING_FID_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_FID_DEDUCTIBLE_BAHRAIN_DEFAULT ));		
				}
				else{
					factor = setFactor(SvcConstants.RATING_FID_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_FID_DEDUCTIBLE_DEFAULT ));
				}
				factorList.add( factor );
			}
			
			if(!Utils.isEmpty( fidelityUnnammedEmployeeVO )){
				factor = setFactor(SvcConstants.RATING_FID_BASIS_OF_COVER ,  String.valueOf(1));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_FID_BASIS_OF_COVER , String.valueOf( SvcConstants.RATING_FID_BASIS_OF_COVER_DEFAULT ));
				factorList.add( factor );
			}
			
			if(! Utils.isEmpty(locationInfo.getRiskGroupId() ) ){
				LocationVO locationVO=(LocationVO)locationInfo;
				factor = setFactor(SvcConstants.RATING_FID_TRADE_GROUP ,  String.valueOf(locationVO.getOccTradeGroup()));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_FID_TRADE_GROUP , String.valueOf( SvcConstants.RATING_FID_TRADE_GROUP_DEFAULT ));
				factorList.add( factor );
			}
			
			if(!Utils.isEmpty( fidelityVO.getAggregateLimit() )){
				factor = setFactor(SvcConstants.RATING_FID_AGGREGATE ,  String.valueOf(fidelityVO.getAggregateLimit().longValue()));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_FID_AGGREGATE , String.valueOf( SvcConstants.RATING_FID_AGGREGATE_DEFAULT ));
				factorList.add( factor );
			}
		}
		factorList.add(commonFactor);
		return factorList;
		
	}

	public ArrayList<FactorBO> getSectionSpeceficFactorsForFIDNamed(
			FidelityNammedEmployeeDetailsVO fidelityNammedEmployeeDetailsVO,
			FidelityVO fidelityVO, BigDecimal renewalLoading,
			RiskGroup locationInfo, int ratingItemNumber, FactorBO commonFactor) {

		List <String>  errorKeys=new ArrayList<String> ();
		List <String> messageParts=new ArrayList<String>();
		ArrayList <FactorBO> factorList= new ArrayList<FactorBO>();
		String buildingId = "";
		/*Integer fidEmpType = 0;*/
		
		FactorBO factor = null ;
		if(!Utils.isEmpty( locationInfo ) && !Utils.isEmpty( locationInfo.getRiskGroupId() ))
		{
			buildingId = locationInfo.getRiskGroupId().toString();
		}		
		if(! Utils.isEmpty(buildingId ) ){
			factor = setFactor(SvcConstants.RATING_ITEM_SEQ_NO_FACTOR , String.valueOf( ratingItemNumber ));
			factorList.add( factor );	
		}else{
			errorKeys.add( com.Constant.CONST_RATING_BI_ITM_NUM);
			messageParts.add(com.Constant.CONST_ITEM_SEQUENCE_NUMBER_NOT_SET_END);
		}

		if(!Utils.isEmpty(fidelityNammedEmployeeDetailsVO)) {
			
			if(!Utils.isEmpty( fidelityVO.getDeductible())){
				factor = setFactor(SvcConstants.RATING_FID_NO_OF_EMP ,  String.valueOf( 1));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_FID_NO_OF_EMP , String.valueOf( SvcConstants.RATING_FID_NO_OF_EMP_DEFAULT ));
				factorList.add( factor );
			}
			
			if(!Utils.isEmpty( fidelityVO.getDeductible())){
				factor = setFactor(SvcConstants.RATING_FID_DEDUCTIBLE ,  String.valueOf( fidelityVO.getDeductible().longValue()));
				factorList.add( factor );	
			}else{
				//factor = setFactor(SvcConstants.RATING_FID_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_FID_DEDUCTIBLE_DEFAULT ));
				if( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equals( BAHRAIN_LOCATION_CODE ) ){
					factor = setFactor(SvcConstants.RATING_FID_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_FID_DEDUCTIBLE_BAHRAIN_DEFAULT ));		
				}
				else{
					factor = setFactor(SvcConstants.RATING_FID_DEDUCTIBLE , String.valueOf( SvcConstants.RATING_FID_DEDUCTIBLE_DEFAULT ));
				}
				factorList.add( factor );
			}
			
			if(!Utils.isEmpty( fidelityNammedEmployeeDetailsVO )){
				factor = setFactor(SvcConstants.RATING_FID_BASIS_OF_COVER ,  String.valueOf(2));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_FID_BASIS_OF_COVER , String.valueOf( SvcConstants.RATING_FID_BASIS_OF_COVER_DEFAULT ));
				factorList.add( factor );
			}
			
			if(! Utils.isEmpty(locationInfo.getRiskGroupId() ) ){
				LocationVO locationVO=(LocationVO)locationInfo;
				factor = setFactor(SvcConstants.RATING_FID_TRADE_GROUP ,  String.valueOf(locationVO.getOccTradeGroup()));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_FID_TRADE_GROUP , String.valueOf( SvcConstants.RATING_FID_TRADE_GROUP_DEFAULT ));
				factorList.add( factor );
			}
			
			if(!Utils.isEmpty( fidelityVO.getAggregateLimit() )){
				factor = setFactor(SvcConstants.RATING_FID_AGGREGATE ,  String.valueOf(fidelityVO.getAggregateLimit().longValue()));
				factorList.add( factor );	
			}else{
				factor = setFactor(SvcConstants.RATING_FID_AGGREGATE , String.valueOf( SvcConstants.RATING_FID_AGGREGATE_DEFAULT ));
				factorList.add( factor );
			}
		}
		factorList.add(commonFactor);
		return factorList;
	
	
	}
	private FactorBO setFactor( String name, String value ){
		
		FactorBO factor = new FactorBO();
		factor.setFactorName( name );
		factor.setFactorValue( value );
		
		return factor;
	}

	
	/* Rating calculation of EE is little tricky.
	 * Some commodities(like of type 1,2,3 as of on Jan 08 2013) have fixed rate. No problem with them.
	 * other commodities(like of type 4,5) need to take hazard level from PAR for Rate calculation. Such commodities are defined in appconfig.properties(EE_ITEMS_DEPENDING_ON_HAZARD_LEVEL)
	 * The original hazard levels in PAR are 1,2,3,4,5. But  commodity types are also 1,2,3,4,5. Because of this overlap in naming, I am appending "1" to the 
	 * commodity types needing hazard level rate and sending it to rating engine.
	 * Even in Rating engine, the factor EE_Rate, hazard levels are appended with "1".
	 * */
	private String getEquipmentTypeForRatingEngine(String equipType,EEVO eevo,LocationVO locvo)
	{
		String[] comTypes = Utils.getMultiValueAppConfig("EE_ITEMS_DEPENDING_ON_HAZARD_LEVEL");
		for( String comType : comTypes )
		{
			if(comType.equals(equipType))
			{
				if(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase( "30" ) || 
						Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase( "50" )){
				equipType = "1"+eevo.getHazardLevel().toString();
				break;
				}
				else
				{
					equipType = locvo.getOccTradeGroup().toString();
					break;
				}
			}
		}
		return equipType;
	}

}
