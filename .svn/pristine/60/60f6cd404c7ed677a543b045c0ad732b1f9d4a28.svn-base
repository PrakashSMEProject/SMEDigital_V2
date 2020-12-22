package com.rsaame.pas.b2c.ws.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.ws.vo.CreatePolicyResponse;
import com.rsaame.pas.b2c.ws.vo.DocsDetails;
import com.rsaame.pas.b2c.ws.vo.Documents;
import com.rsaame.pas.b2c.ws.vo.UpdateHomeQuoteResponse;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;

public class CreatePolicyResponseMapper implements BaseResponseVOMapper{

	private final static Logger LOGGER = Logger.getLogger(CreatePolicyResponseMapper.class);
	
	@Override
	public void mapVOToResponse(Object valueObj, Object responseObj) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("Enters to CreatePolicyResponseMapper.mapVOToResponse, maps the homeInsuranceVO details to response...");
		
		CreatePolicyResponse createPolicyResponse = (CreatePolicyResponse) responseObj;
		initialiseObjects(createPolicyResponse);
		
		if (responseObj instanceof CreatePolicyResponse
				&& valueObj instanceof HomeInsuranceVO) {
			
			HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) valueObj;
		
			createPolicyResponse.setPolicyNumber(homeInsuranceVO.getCommonVO().getPolicyNo().toString());
			createPolicyResponse.setEffectiveDate(homeInsuranceVO.getScheme().getEffDate());
			createPolicyResponse.setExpiryDate(homeInsuranceVO.getScheme().getExpiryDate());
			createPolicyResponse.setEndtId(homeInsuranceVO.getCommonVO().getEndtId());
			createPolicyResponse.setPolicyId(homeInsuranceVO.getCommonVO().getPolicyId());
			createPolicyResponse.setStatus(true);
			// CTS 20.07.2020 - CR#11645 Retrieve Policy Details  SAT Issue-start
			createPolicyResponse.setPaidAmount(new BigDecimal(homeInsuranceVO.getPremiumVO().getPremiumAmt()).setScale(2, RoundingMode.HALF_UP));
			// CTS 20.07.2020 - CR#11645 Retrieve Policy Details  SAT Issue-end
			if(!Utils.isEmpty(homeInsuranceVO.getOnlinePaymentDetailsVO()))
				createPolicyResponse.setTransactionRefNo(homeInsuranceVO.getOnlinePaymentDetailsVO().getTransactionRefNo());
			
			
		}
		else if(responseObj instanceof CreatePolicyResponse
				&& valueObj instanceof TravelInsuranceVO) {
			TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) valueObj;
			
			createPolicyResponse.setPolicyNumber(travelInsuranceVO.getCommonVO().getPolicyNo().toString());
			createPolicyResponse.setEffectiveDate(travelInsuranceVO.getScheme().getEffDate());
			createPolicyResponse.setExpiryDate(travelInsuranceVO.getScheme().getExpiryDate());
			createPolicyResponse.setEndtId(travelInsuranceVO.getCommonVO().getEndtId());
			createPolicyResponse.setPolicyId(travelInsuranceVO.getCommonVO().getPolicyId());
			createPolicyResponse.setStatus(true);
			double discAmt = 0,diffAmt = 0,paidAmt = 0;
			if(!Utils.isEmpty(travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc()) && travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc()!= 0.0) {
				discAmt = travelInsuranceVO.getPremiumVO().getPremiumAmt()/travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc();
				diffAmt = travelInsuranceVO.getPremiumVO().getPremiumAmt() - Math.abs(discAmt);
				paidAmt = diffAmt + travelInsuranceVO.getPremiumVO().getVatTax();
				createPolicyResponse.setPaidAmount(new BigDecimal(paidAmt));
			}else{
				paidAmt = travelInsuranceVO.getPremiumVO().getPremiumAmt()+travelInsuranceVO.getPremiumVO().getVatTax();
				createPolicyResponse.setPaidAmount(new BigDecimal(paidAmt));
			}
			if(!Utils.isEmpty(travelInsuranceVO.getOnlinePaymentDetailsVO()))
				createPolicyResponse.setTransactionRefNo(travelInsuranceVO.getOnlinePaymentDetailsVO().getTransactionRefNo());
		}
		
		LOGGER.info("Successfully mapped to HomeInsuranceVO details to response object..");
	}

	private void initialiseObjects(CreatePolicyResponse createPolicyResponse) {
		// TODO Auto-generated method stub
		
		if(Utils.isEmpty(createPolicyResponse.getDocuments())) {
			createPolicyResponse.setDocuments(new Documents());
		}
		if(Utils.isEmpty(createPolicyResponse.getDocuments().getDocsDetails())) {
			createPolicyResponse.getDocuments().setDocsDetails(new DocsDetails());
		}
		
	}
	
}
