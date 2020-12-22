/**
 * 
 */
package com.rsaame.pas.b2c.ws.mapper;

import java.util.ArrayList;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.ws.vo.CreatePolicyRequest;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.vo.bus.AddressVO;
import com.rsaame.pas.vo.bus.BuildingDetailsVO;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.PaymentDetailsVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.StaffDetailsVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.CoverVO;
import com.rsaame.pas.vo.cmn.RiskVO;

/**
 * @author M1037404
 *
 */
public class CreatePolicyRequestMapper implements BaseRequestVOMapper{

	private final static Logger LOGGER = Logger.getLogger(CreatePolicyRequestMapper.class);
	
	@Override
	public void mapRequestToVO(Object requestObj, Object valueObj) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("Enters to CreatePolicyRequestMapper.mapRequestToVO, maps the request to homeInsuranceVO details..");
		if (requestObj instanceof CreatePolicyRequest
				&& valueObj instanceof TravelInsuranceVO) {
			
			CreatePolicyRequest createPolicyRequest = (CreatePolicyRequest) requestObj;
			TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) valueObj;
			
			initialiseObjects(travelInsuranceVO);
			
			mapCommonVoFields(createPolicyRequest,travelInsuranceVO);
			
			mapPaymentDetails(createPolicyRequest,travelInsuranceVO);
			
			
		
		}
		else if(requestObj instanceof CreatePolicyRequest
				&& valueObj instanceof HomeInsuranceVO) {
			
			CreatePolicyRequest createPolicyRequest = (CreatePolicyRequest) requestObj;
			HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) valueObj;
			initialiseObjects(homeInsuranceVO);
			
			mapCommonVoFields(createPolicyRequest,homeInsuranceVO);
			mapPaymentDetails(createPolicyRequest,homeInsuranceVO);
		}
		
		LOGGER.info("CreatePolicyRequestMapper.mapRequestToVO, mapping done");
	}
	
	private void mapPaymentDetails(CreatePolicyRequest createPolicyRequest, PolicyDataVO policyDataVO) {
		
		policyDataVO.getOnlinePaymentDetailsVO().setCurrency(Currency.getUnitName());
		
		if(!Utils.isEmpty(createPolicyRequest.getIsPaymentProcessed())) {
			policyDataVO.getOnlinePaymentDetailsVO().setDecision(createPolicyRequest.getIsPaymentProcessed());
		}
		if(!Utils.isEmpty(createPolicyRequest.getPremiumPayable())) {
			policyDataVO.getOnlinePaymentDetailsVO().setAuthorizedPremiumAmt(createPolicyRequest.getPremiumPayable().doubleValue());
		}
		if(!Utils.isEmpty(createPolicyRequest.getCardNo())) {
			policyDataVO.getOnlinePaymentDetailsVO().setCardNumber(createPolicyRequest.getCardNo().toString());
		}
		if(!Utils.isEmpty(createPolicyRequest.getCardType())) {
			policyDataVO.getOnlinePaymentDetailsVO().setCardType(createPolicyRequest.getCardType());
		}
		if(!Utils.isEmpty(createPolicyRequest.getCustName())) {
			policyDataVO.getOnlinePaymentDetailsVO().setCustName(createPolicyRequest.getCustName());
		}
		if(!Utils.isEmpty(createPolicyRequest.getCardExpiryDate())) {
			policyDataVO.getOnlinePaymentDetailsVO().setCardExipiryDate(createPolicyRequest.getCardExpiryDate());
		}
		if(!Utils.isEmpty(createPolicyRequest.getPaymtMode())) {
			policyDataVO.getOnlinePaymentDetailsVO().setPaymentMode(createPolicyRequest.getPaymtMode());
		}
		if(!Utils.isEmpty(createPolicyRequest.getPaymtTransactionDate())) {
			policyDataVO.getOnlinePaymentDetailsVO().setTransDate(createPolicyRequest.getPaymtTransactionDate());
			policyDataVO.getOnlinePaymentDetailsVO().setAuthirizationTime(createPolicyRequest.getPaymtTransactionDate());
		}
		if(!Utils.isEmpty(createPolicyRequest.getmId())) {
			policyDataVO.getOnlinePaymentDetailsVO().setTransactionRefNo(createPolicyRequest.getmId());
		}
		if(!Utils.isEmpty(createPolicyRequest.gettId())) {
			policyDataVO.getOnlinePaymentDetailsVO().setTransactionId(createPolicyRequest.gettId());
		}
		else {
			policyDataVO.getOnlinePaymentDetailsVO().setTransactionId(createPolicyRequest.getQuotationNo()+"-"+createPolicyRequest.getPolicyId()+"-"+createPolicyRequest.getEndtId());
		}
		/*if(!Utils.isEmpty(createPolicyRequest.getMaskCardNumber())) {
			policyDataVO.getOnlinePaymentDetailsVO().setCardNumber(createPolicyRequest.getMaskCardNumber());
		}*/
		if(!Utils.isEmpty(createPolicyRequest.getCardHolderName())) {
			policyDataVO.getOnlinePaymentDetailsVO().setCardIssuer(createPolicyRequest.getCardHolderName());
		}
		if(!Utils.isEmpty(createPolicyRequest.getAuthCode())) {
			policyDataVO.getOnlinePaymentDetailsVO().setAuthizationCode(createPolicyRequest.getAuthCode());
		}
		if(!Utils.isEmpty(createPolicyRequest.getCardTypeCode())) {
			// Mapping is remaining 
		}
		if(!Utils.isEmpty(createPolicyRequest.getLocationCode())) {
			//  Mapping is remaining 
		}
		if(!Utils.isEmpty(createPolicyRequest.getUploadFilePath())) {
			//  Mapping is remaining 
		}
		if(!Utils.isEmpty(createPolicyRequest.getUploadFileName())) {
			////  Mapping is remaining 
		}
		if(!Utils.isEmpty(createPolicyRequest.getApplVer())) {
			//  Mapping is remaining 
		}
		if(!Utils.isEmpty(createPolicyRequest.getTimeDuration())) {
			//  Mapping is remaining 
		}
		
		if(!Utils.isEmpty(createPolicyRequest.getBatchNo())) {
			//  Mapping is remaining 
		}
		if(!Utils.isEmpty(createPolicyRequest.getInvoiceNo())) {
			//  Mapping is remaining 
		}
		if(!Utils.isEmpty(createPolicyRequest.getRrn())) {
			//  Mapping is remaining 
		}
		if(!Utils.isEmpty(createPolicyRequest.getAmount())) {
			//  Mapping is remaining 
		}
		if(!Utils.isEmpty(createPolicyRequest.getPolicyConfirmationEmail())) {
			//  Mapping is remaining 
		}
	}
	private void mapCommonVoFields(CreatePolicyRequest createPolicyRequest,
			PolicyDataVO policyDataVO) {
		// TODO Auto-generated method stub
		
		if(!Utils.isEmpty(policyDataVO.getCommonVO())) {
			policyDataVO.getCommonVO().setQuoteNo(createPolicyRequest.getQuotationNo());
			policyDataVO.getCommonVO().setPolicyId(createPolicyRequest.getPolicyId());
			policyDataVO.getCommonVO().setEndtId(createPolicyRequest.getEndtId());
			policyDataVO.getCommonVO().setEndtNo(createPolicyRequest.getEndtNo());
			
			policyDataVO.getOnlinePaymentDetailsVO().setQuoteNo(createPolicyRequest.getQuotationNo());
			policyDataVO.getOnlinePaymentDetailsVO().setEndtID(createPolicyRequest.getEndtId());
			policyDataVO.getOnlinePaymentDetailsVO().setPolicyId(createPolicyRequest.getPolicyId());
		}
		if(!Utils.isEmpty(policyDataVO.getPremiumVO())) {
			policyDataVO.getPremiumVO().setPremiumAmt(createPolicyRequest.getPremiumPayable().doubleValue());
		}
		
	}
	private void initialiseObjects(PolicyDataVO policyDataVO){
		if(Utils.isEmpty(policyDataVO.getScheme())) {
			policyDataVO.setScheme(new SchemeVO());
		}
		if(Utils.isEmpty(policyDataVO.getCommonVO())) {
			policyDataVO.setCommonVO(new CommonVO());
		}
		if(Utils.isEmpty(policyDataVO.getGeneralInfo())) {
			policyDataVO.setGeneralInfo(new GeneralInfoVO());
		}
		if(Utils.isEmpty(policyDataVO.getGeneralInfo().getInsured())) {
			policyDataVO.getGeneralInfo().setInsured(new InsuredVO());
		}
		if(Utils.isEmpty(policyDataVO.getGeneralInfo().getInsured().getAddress())) {
			policyDataVO.getGeneralInfo().getInsured().setAddress(new AddressVO());;
		}
		if(Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus())) {
			policyDataVO.getGeneralInfo().setSourceOfBus(new SourceOfBusinessVO());
		}
		if(Utils.isEmpty(policyDataVO.getPremiumVO())) {
			policyDataVO.setPremiumVO(new PremiumVO());
		}
		if(Utils.isEmpty(policyDataVO.getOnlinePaymentDetailsVO())) {
			policyDataVO.setOnlinePaymentDetailsVO(new PaymentDetailsVO());
		}
	}
}	
