package com.rsaame.pas.b2c.ws.handler;

import java.math.BigDecimal;

import javax.transaction.SystemException;

import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.b2c.cmn.utils.ApplicationContextUtils;
import com.rsaame.pas.b2c.travelInsurance.TravelInsuranceHandler;
import com.rsaame.pas.b2c.ws.beans.TravelCreateModifyQuoteResponse;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;

/**
 * @author m1020637
 *
 */
public class TravelServiceHandler {

	/**
	 * @param travelInsuranceVO
	 * @return
	 * @throws SystemException 
	 */
	public TravelCreateModifyQuoteResponse saveTravelQuote(TravelInsuranceVO travelInsuranceVO,String contextPath) throws SystemException {
		
		TravelCreateModifyQuoteResponse response = null;
		//mapping
		travelInsuranceVO.getCommonVO().setIsQuote(Boolean.TRUE);
		travelInsuranceVO.getCommonVO().setLob( LOB.TRAVEL );
		//test
		if (Utils.isEmpty(travelInsuranceVO.getCommonVO().getLocCode())) {
			travelInsuranceVO.getCommonVO().setLocCode(20);
		}
		if (Utils.isEmpty(travelInsuranceVO.getCommonVO().getStatus())) {
			travelInsuranceVO.getCommonVO().setStatus(1);
		}
		if (Utils.isEmpty(travelInsuranceVO.getStatus())) {
			travelInsuranceVO.setStatus(1);
		}
		
		//call 1st service
		travelInsuranceVO = TravelInsuranceHandler.populateTravelInsuranceForSave( travelInsuranceVO, null );
		travelInsuranceVO = TravelInsuranceHandler.saveTravelGeneralInfo( travelInsuranceVO,contextPath );
		
		
		//call 2nd service
		DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();
		Object[] inpObjects = { travelInsuranceVO, false };
		dataHolder.setData( inpObjects );
		
		travelInsuranceVO = (TravelInsuranceVO) TaskExecutor.executeTasks( "SAVE_QUOTE_TRAVEL", dataHolder );
		
		if (travelInsuranceVO != null && !Utils.isEmpty(travelInsuranceVO.getTravelPackageList())) {
			//response
			response = new TravelCreateModifyQuoteResponse();
			//get from selected package
			for (TravelPackageVO travelPackageVO : travelInsuranceVO
					.getTravelPackageList()) {

				if (travelPackageVO.getIsSelected()) {
					if (!travelPackageVO.getTariffCode().equalsIgnoreCase("212")) {
						response.setPremiumValue(BigDecimal
								.valueOf(travelPackageVO.getPremiumAmt()));
					} else {
						response.setPremiumValue(BigDecimal
								.valueOf(162));
					}
				}
			}
			response.setQuoteId(travelInsuranceVO.getCommonVO().getQuoteNo());
		} else {
			throw new SystemException("Unexpected exception occured during save");
		}
			
		return response;

	}
	
	/**
	 * @param travelInsuranceVO
	 * @return
	 */
	public String validateModifyQuote(TravelInsuranceVO travelInsuranceVO) {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		try {
			//validate insured code
			if(travelInsuranceVO.getGeneralInfo().getInsured().getInsuredCode()!= null){
				if(travelInsuranceVO.getGeneralInfo().getInsured().getInsuredCode()==0){
					stringBuilder.append("Insured code is required | ");
				}
			} else {
				stringBuilder.append("Insured code is required | ");
			}
			
			//validate status
			/*if(travelInsuranceVO.getCommonVO().getStatus()!=null){
				if(travelInsuranceVO.getCommonVO().getStatus() == 0){
					stringBuilder.append("Status code is required | ");
				}
			} else {
				stringBuilder.append("Status code is required | ");
			}*/
			
			//validate policy id
			if(travelInsuranceVO.getCommonVO().getPolicyId()!=null){
				if(travelInsuranceVO.getCommonVO().getPolicyId() == 0){
					stringBuilder.append("Policy Id is required | ");
				}
			} else {
				stringBuilder.append("Policy Id is required | ");
			}
			
			//validate quote number
			if(travelInsuranceVO.getCommonVO().getQuoteNo()!=null){
				if(travelInsuranceVO.getCommonVO().getQuoteNo()==0){
					stringBuilder.append("QuoteNo is required | ");
				}
			} else {
				stringBuilder.append("QuoteNo is required | ");
			}
			
			
			//travel package isselected true/ (vsd validation,tarrif code)
			/*if(!Utils.isEmpty(travelInsuranceVO.getTravelPackageList())){
				for(TravelPackageVO travelPackageVO : travelInsuranceVO.getTravelPackageList()){
					if(travelPackageVO.getIsSelected()){
						for(CoverDetailsVO coverDetailsVO : travelPackageVO.getCovers()){
							if (coverDetailsVO.getVsd() == null) {
								stringBuilder.append("Cover detail "+coverDetailsVO.getCoverName()+" VSD is required | ");
								break;
							}
						}
					}
				}
			}*/
			
			//vsd
			if(travelInsuranceVO.getCommonVO().getVsd()==null){
				stringBuilder.append("VSD is required | ");
			} 
			
			//endt id and number
			if(travelInsuranceVO.getCommonVO().getEndtId()==null){
				stringBuilder.append("getEndtId is required | ");
			}
			
			if(travelInsuranceVO.getCommonVO().getEndtNo()==null){
				stringBuilder.append("getEndtNo is required | ");
			}
			
			
		} catch (Exception e) {
		
			stringBuilder.append(e.getMessage());
		}

		return stringBuilder.toString();
	}
}
