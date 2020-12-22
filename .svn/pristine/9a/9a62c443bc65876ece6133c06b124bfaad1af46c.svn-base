package com.rsaame.pas.b2c.ws.endpoint;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.ServletContextAware;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.handlers.CommonHandler;
import com.rsaame.pas.b2c.cmn.utils.ApplicationContextUtils;
import com.rsaame.pas.b2c.exception.SystemException;
import com.rsaame.pas.b2c.exception.ValidationException;
import com.rsaame.pas.b2c.travelInsurance.TravelInsuranceHandler;
import com.rsaame.pas.b2c.validator.TravelGIQuoteCreateValidator;
import com.rsaame.pas.b2c.validator.TravelInsuranceLookupValidator;
import com.rsaame.pas.b2c.ws.beans.TravelConvertToPolicyRequest;
import com.rsaame.pas.b2c.ws.beans.TravelConvertToPolicyResponse;
import com.rsaame.pas.b2c.ws.beans.TravelCreateModifyQuoteRequest;
import com.rsaame.pas.b2c.ws.beans.TravelCreateModifyQuoteResponse;
import com.rsaame.pas.b2c.ws.beans.TravelInsuranceDetails;
import com.rsaame.pas.b2c.ws.beans.TravelPackageDetails;
import com.rsaame.pas.b2c.ws.beans.TravelRenewPolicyRequest;
import com.rsaame.pas.b2c.ws.beans.TravelRenewPolicyResponse;
import com.rsaame.pas.b2c.ws.handler.CommonServiceHandler;
import com.rsaame.pas.b2c.ws.handler.TravelServiceHandler;
import com.rsaame.pas.b2c.ws.mapper.TravelInsuranceDetailsMapper;
import com.rsaame.pas.b2c.ws.mapper.TravelInsuranceVOMapper;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.web.UserProfileHandler;

/**
 * @author m1020637
 *
 */
@Endpoint
public class TravelServiceEndpoint implements ServletContextAware{
	
	private ServletContext servletContext = null;
	@Override
	public void setServletContext(ServletContext arg0) {
	
		servletContext = arg0;
		
	}
	private static final String TARGET_NAMESPACE = "http://com/rsaame/pas/b2c/ws";
	private static final Logger logger = Logger.getLogger(TravelServiceEndpoint.class);
	private static String CREATE = "create";
	private static String MODIFY = "modify";
	CommonHandler commonHandler = new CommonHandler();

	/**
	 * @param request
	 * @return
	 */
	@PayloadRoot(localPart = "TravelCreateModifyQuoteRequest", namespace = TARGET_NAMESPACE)
	public @ResponsePayload TravelCreateModifyQuoteResponse createModifyQuote(@RequestPayload TravelCreateModifyQuoteRequest request) throws Exception 
	{
		TravelCreateModifyQuoteResponse response = null;
		
		TravelInsuranceDetails travelInsuranceDetails = request.getTravelInsuranceDetails();
		TravelServiceHandler travelServiceHandler = (TravelServiceHandler) ApplicationContextUtils
		.getBean("travelServiceHandler");
		//call mapper
		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) ApplicationContextUtils.getBean("VO_TRAVEL");
		TravelInsuranceVOMapper travelInsuranceVOMapper = (TravelInsuranceVOMapper)ApplicationContextUtils.getBean("travelInsuranceVOMapper");
		try {
			travelInsuranceVOMapper.mapTraveldetailsToTravelInsuranceVO(travelInsuranceVO, travelInsuranceDetails);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in mapping", e);
		}
		
		
		String errors = validateTravelGIQuoteCreate(travelInsuranceVO);
				
		if(!Utils.isEmpty(errors)){
			throw new ValidationException(errors.toString());
		}
		//validation for modify
		if(request.getOpIdentifier().equalsIgnoreCase(MODIFY)){
			//call validation
			errors = travelServiceHandler.validateModifyQuote(travelInsuranceVO);
		}
		
		if(!Utils.isEmpty(errors)){
			throw new ValidationException(errors.toString());
		}
		
		//call services
		try {
			
			//reqd mappings
			SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
			travelInsuranceVO.getGeneralInfo().setSourceOfBus(
					sourceOfBusinessVO);
			//reqd mappings end
			response = travelServiceHandler
			.saveTravelQuote(travelInsuranceVO,servletContext.getContextPath());

		} catch (Exception e) {
			
			e.printStackTrace();
			throw new ValidationException(e.getMessage());
		}
		
		//return
		return response;
	}
	
	/**
	 * @param travelInsuranceVO
	 * @return String
	 */
	private String validateTravelGIQuoteCreate(TravelInsuranceVO travelInsuranceVO) {

		//call validator
		TravelGIQuoteCreateValidator quoteCreateValidator = (TravelGIQuoteCreateValidator)ApplicationContextUtils.getBean("giValidator");
		BeanPropertyBindingResult beanPropertyBindingResult = new BeanPropertyBindingResult(travelInsuranceVO, "travelInsuranceVO");
		quoteCreateValidator.validate(travelInsuranceVO, beanPropertyBindingResult);
		StringBuilder errors = new StringBuilder();
		if(beanPropertyBindingResult.hasErrors()){
			for(ObjectError objectError : beanPropertyBindingResult.getAllErrors()){
				errors.append(objectError.getDefaultMessage());
				
			}
		}
		
		//call dropdown values validator
		//TravelInsuranceLookupValidator travelInsLookupValidator = (TravelInsuranceLookupValidator)ApplicationContextUtils.getBean("travelLookupValidator");		/* commented unused variable - sonar violation fix */
		BeanPropertyBindingResult beanPropertyBindingResult2 = new BeanPropertyBindingResult(travelInsuranceVO, "travelInsuranceVO");
		//travelInsLookupValidator.validate(travelInsuranceVO, beanPropertyBindingResult2);
		
		if(beanPropertyBindingResult2.hasErrors()){
			for(ObjectError objectError : beanPropertyBindingResult2.getAllErrors()){
				errors.append(objectError.getDefaultMessage());
				
			}
		}
		
		return errors.toString();
	}
	
	
	@PayloadRoot(localPart = "TravelConvertToPolicyRequest", namespace = TARGET_NAMESPACE)
	public @ResponsePayload TravelConvertToPolicyResponse convertToPolicy(@RequestPayload TravelConvertToPolicyRequest request) throws Exception
	{
		TravelConvertToPolicyResponse response = null;
		TravelInsuranceVO travelInsuranceVO = null;
		UserProfile userProfile = null;
		
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String user = currentUser.getName();
		
		if(!Utils.isEmpty(user)){
			userProfile = UserProfileHandler.getUserProfileVo(user);
		}
		
		try {
			if(request.getIsCreate()){//create case
				 
				//create quote
				TravelInsuranceDetails travelInsuranceDetails = request.getTravelInsuranceDetails();
				TravelCreateModifyQuoteRequest travelCreateModifyQuoteRequest = new TravelCreateModifyQuoteRequest();
				travelCreateModifyQuoteRequest.setTravelInsuranceDetails(travelInsuranceDetails);
				travelCreateModifyQuoteRequest.setOpIdentifier(CREATE);
				//call createModifyQuote
				TravelCreateModifyQuoteResponse travelCreateModifyQuoteResponse = createModifyQuote(travelCreateModifyQuoteRequest);
				
				//retrieve quote
				CommonServiceHandler commonServiceHandler = (CommonServiceHandler)ApplicationContextUtils.getBean("commonServiceHandler");
				travelInsuranceVO = commonServiceHandler.retrieveTravelInsuranceVO(travelCreateModifyQuoteResponse.getQuoteId(),user);
				
				//call convert
				travelInsuranceVO.getCommonVO().setLoggedInUser( userProfile );
				response = converToPolicy(travelInsuranceVO);
				
			} else {                  //existing convert case
				
				//get policy id
				long quoteNo = request.getTravelInsuranceDetails().getQuoteNo();
				travelInsuranceVO = new TravelInsuranceVO();
				//retrieve quote
				
				CommonVO commonVO = new CommonVO();
				TravelInsuranceHandler travelInsHandler = (TravelInsuranceHandler) ApplicationContextUtils
						.getBean("travelInsuranceHandler");
				//set search values
				travelInsuranceVO.setQuoteNo(quoteNo);
				commonVO.setQuoteNo(quoteNo);
				commonVO.setLoggedInUser( userProfile );
				travelInsuranceVO.setCommonVO(commonVO);
				//call service to get
				travelInsuranceVO = travelInsHandler.populateTravelInsForSearch(
						travelInsuranceVO, null);
				travelInsuranceVO = (TravelInsuranceVO) TaskExecutor.executeTasks( "TRAVEL_PACKAGE_PREMIUM", travelInsuranceVO );
				
				response = converToPolicy(travelInsuranceVO);
			}
		} catch (Exception e) {
			throw new SystemException(e,"Unexpected Error occured in covert to policy");
		}
		
		
		return response;
	}
	
	/**
	 * @param travelInsuranceVO
	 * @return TravelConvertToPolicyResponse
	 */
	private TravelConvertToPolicyResponse converToPolicy(TravelInsuranceVO travelInsuranceVO) {
		
		//call service
		travelInsuranceVO.getCommonVO().setDocCode( null );
		commonHandler.convertToPolicy(travelInsuranceVO,true,servletContext.getContextPath());
		//add to response
		TravelConvertToPolicyResponse response = new TravelConvertToPolicyResponse();
		TravelInsuranceDetails travelInsuranceDetails = new TravelInsuranceDetails();
		TravelInsuranceDetailsMapper travelInsuranceDetailsMapper = (TravelInsuranceDetailsMapper) ApplicationContextUtils
		.getBean("travelInsuranceDetailsMapper");
		travelInsuranceDetailsMapper.mapTravelInsuranceVOToTraveldetails(
		travelInsuranceVO, travelInsuranceDetails);
		response.setTravelInsuranceDetails(travelInsuranceDetails);
		
		return response;
	}
	
	@PayloadRoot(localPart = "TravelRenewPolicyRequest", namespace = TARGET_NAMESPACE)
	public @ResponsePayload TravelRenewPolicyResponse renewPolicy(@RequestPayload TravelRenewPolicyRequest request) throws Exception {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String user = currentUser.getName();
		
		TravelRenewPolicyResponse travelRenewPolicyResponse = null;
		TravelInsuranceVO travelInsuranceVO = null;
		CommonServiceHandler commonServiceHandler = (CommonServiceHandler)ApplicationContextUtils.getBean("commonServiceHandler");
		//if modify
		if(request.getIsModified()){
			//modify
			TravelCreateModifyQuoteRequest travelCreateModifyQuoteRequest = new TravelCreateModifyQuoteRequest();
			travelCreateModifyQuoteRequest.setTravelInsuranceDetails(request.getTravelInsuranceDetails());
			travelCreateModifyQuoteRequest.setOpIdentifier(MODIFY);
			createModifyQuote(travelCreateModifyQuoteRequest);
		} else {//else retrieve
			travelInsuranceVO = commonServiceHandler.retrieveTravelInsuranceVO(request.getTravelInsuranceDetails().getQuoteNo(),user);
		}
		
		
		//call renew
		//call convert to policy
		//sonar fix to avoid  Null passed for nonnull parameter on 28-9-2017
		TravelConvertToPolicyResponse response=null;
		try{
		 response = converToPolicy(travelInsuranceVO);
		}catch (NullPointerException e) {
			logger.debug("Null pointer exception while converting to policy");
		}
		
		//create response
		TravelInsuranceDetails travelInsuranceDetails = response.getTravelInsuranceDetails();
		//get cover
		
		if (!Utils.isEmpty(travelInsuranceDetails)) {
			for (TravelPackageDetails travelPackageDetails : travelInsuranceDetails
					.getTravelPackageList()) {
				if (travelPackageDetails.getIsSelected()) {
					travelRenewPolicyResponse = new TravelRenewPolicyResponse();
					travelRenewPolicyResponse
							.setCoverDetails(travelPackageDetails.getCovers());
					travelRenewPolicyResponse
							.setPolicyId(travelInsuranceDetails.getPolicyId());
					travelRenewPolicyResponse
							.setPremiumDetails(travelInsuranceDetails
									.getPremiumVO());
				}
			}
		}
		return travelRenewPolicyResponse;

	}
}
