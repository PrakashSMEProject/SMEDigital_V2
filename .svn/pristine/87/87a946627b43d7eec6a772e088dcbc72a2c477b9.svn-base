package com.rsaame.pas.b2c.ws.endpoint;

import javax.servlet.ServletContext;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.ServletContextAware;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.handlers.CommonHandler;
import com.rsaame.pas.b2c.cmn.utils.ApplicationContextUtils;
import com.rsaame.pas.b2c.exception.SystemException;
import com.rsaame.pas.b2c.ws.beans.HomeConvertToPolicyRequest;
import com.rsaame.pas.b2c.ws.beans.HomeConvertToPolicyResponse;
import com.rsaame.pas.b2c.ws.beans.HomeCreateModifyQuoteRequest;
import com.rsaame.pas.b2c.ws.beans.HomeCreateModifyQuoteResponse;
import com.rsaame.pas.b2c.ws.beans.HomeInsuranceDetails;
import com.rsaame.pas.b2c.ws.beans.HomeRenewPolicyRequest;
import com.rsaame.pas.b2c.ws.beans.HomeRenewPolicyResponse;
import com.rsaame.pas.b2c.ws.handler.CommonServiceHandler;
import com.rsaame.pas.b2c.ws.handler.HomeServiceHandler;
import com.rsaame.pas.b2c.ws.mapper.HomeInsuranceDetailsMapper;
import com.rsaame.pas.b2c.ws.mapper.HomeInsuranceVOMapper;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.web.UserProfileHandler;
/**
 * @author m1020637
 *
 */
@Endpoint
public class HomeServiceEndpoint implements ServletContextAware{
	
	private ServletContext servletContext = null;
	@Override
	public void setServletContext(ServletContext arg0) {
	
		servletContext = arg0;
		
	}
	private static final String TARGET_NAMESPACE = "http://com/rsaame/pas/b2c/ws";
	private static String CREATE = "create";
	private static String MODIFY = "modify";
	/**
	 * @param request
	 * @return
	 */
	@PayloadRoot(localPart = "HomeCreateModifyQuoteRequest", namespace = TARGET_NAMESPACE)
	public @ResponsePayload HomeCreateModifyQuoteResponse saveCreateQuote(@RequestPayload HomeCreateModifyQuoteRequest request) throws Exception 
	{
		UserProfile userProfile = null;
		HomeCreateModifyQuoteResponse response = null;
		HomeInsuranceDetails homeInsuranceDetails = request.getHomeInsuranceDetails();
	
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String user = currentUser.getName();
		
		//user profile
		if(!Utils.isEmpty(user)){
			userProfile = UserProfileHandler.getUserProfileVo(user);
			//commonVO.setLoggedInUser(userProfile);
		}
		
		//call mapper
		HomeInsuranceVO homeInsuranceVO = new HomeInsuranceVO();
		HomeInsuranceVOMapper homeInsuranceVOMapper = (HomeInsuranceVOMapper)ApplicationContextUtils.getBean("homeInsuranceVOMapper");
		homeInsuranceVOMapper.mapHomeInsuranceVO(homeInsuranceDetails, homeInsuranceVO);
		homeInsuranceVO.getCommonVO().setLoggedInUser( userProfile );
		//call services
		HomeServiceHandler homeServiceHandler = (HomeServiceHandler)ApplicationContextUtils.getBean("homeServiceHandler");
		response = homeServiceHandler.saveCreateQuote(homeInsuranceVO,servletContext.getContextPath());

		//return
		return response;
	}


	@PayloadRoot(localPart = "HomeConvertToPolicyRequest", namespace = TARGET_NAMESPACE)
	public @ResponsePayload HomeConvertToPolicyResponse convertToPolicy(@RequestPayload HomeConvertToPolicyRequest request) throws Exception
	{
		HomeConvertToPolicyResponse response = null;
		HomeInsuranceVO homeInsuranceVO = null;
		UserProfile userProfile = null;
		
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String user = currentUser.getName();
		
		if(!Utils.isEmpty(user)){
			userProfile = UserProfileHandler.getUserProfileVo(user);
		}
		
		try {
			if(request.getIsCreate()){//create case
				 
				//create quote
				HomeInsuranceDetails homeInsuranceDetails = request.getHomeInsuranceDetails();
				HomeCreateModifyQuoteRequest homeCreateModifyQuoteRequest = new HomeCreateModifyQuoteRequest();
				homeCreateModifyQuoteRequest.setHomeInsuranceDetails(homeInsuranceDetails);
				homeCreateModifyQuoteRequest.setOpIdentifier(CREATE);
				//call createModifyQuote
				HomeCreateModifyQuoteResponse homeCreateModifyQuoteResponse = saveCreateQuote(homeCreateModifyQuoteRequest);
				
				//retrieve quote
				CommonServiceHandler commonServiceHandler = (CommonServiceHandler)ApplicationContextUtils.getBean(com.Constant.CONST_COMMONSERVICEHANDLER);
				homeInsuranceVO = commonServiceHandler.retrieveHomeInsuranceVO(homeCreateModifyQuoteResponse.getQuoteId(),user);
				homeInsuranceVO.getCommonVO().setLoggedInUser( userProfile );
				//call convert
				response = converToPolicy(homeInsuranceVO);
				
			} else {                  //existing convert case
				
				//get policy id
				long quoteNo = request.getHomeInsuranceDetails().getQuoteNo();
				homeInsuranceVO = new HomeInsuranceVO();
				//retrieve quote	
				
				CommonVO commonVO = new CommonVO();
				CommonServiceHandler commonServiceHandler = (CommonServiceHandler)ApplicationContextUtils.getBean(com.Constant.CONST_COMMONSERVICEHANDLER);
				//set search values
				homeInsuranceVO.setQuoteNo(quoteNo);
				commonVO.setQuoteNo(quoteNo);
				homeInsuranceVO.setCommonVO(commonVO);
				commonVO.setLoggedInUser( userProfile );
				//call service to get
				homeInsuranceVO = commonServiceHandler.retrieveHomeInsuranceVO(quoteNo,user);
				
				response = converToPolicy(homeInsuranceVO);
			}
		} catch (Exception e) {
			throw new SystemException(e,"Unexpected Error occured in covert to policy");
		}
		
		
		return response;
	}
	

	/**
	 * @param homeInsuranceVO
	 * @return
	 */
	private HomeConvertToPolicyResponse converToPolicy(HomeInsuranceVO homeInsuranceVO) {
	
		HomeConvertToPolicyResponse response = new HomeConvertToPolicyResponse();
		CommonHandler commonHandler = new CommonHandler();
		//call service
		/*UserProfile user = new UserProfile();
		user.setUserId("512");*/
		homeInsuranceVO.getCommonVO().setDocCode( null );
		commonHandler.convertToPolicy(homeInsuranceVO,true,servletContext.getContextPath());
		//map response
		HomeInsuranceDetails homeInsuranceDetails = new HomeInsuranceDetails();
		HomeInsuranceDetailsMapper homeInsuranceDetailsMapper = (HomeInsuranceDetailsMapper) ApplicationContextUtils
		.getBean("homeInsuranceDetailsMapper");
		homeInsuranceDetailsMapper.mapHomeInsuranceDetailsToHomeInsuranceVO(homeInsuranceDetails, homeInsuranceVO);
		response.setHomeInsuranceDetails(homeInsuranceDetails);
		
		
		return response;
	}
	
	@PayloadRoot(localPart = "HomeRenewPolicyRequest", namespace = TARGET_NAMESPACE)
	public @ResponsePayload HomeRenewPolicyResponse renewPolicy(@RequestPayload HomeRenewPolicyRequest request) throws Exception {
		
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String user = currentUser.getName();
		
		HomeRenewPolicyResponse homeRenewPolicyResponse = null;
		HomeInsuranceVO homeInsuranceVO = null;
		CommonServiceHandler commonServiceHandler = (CommonServiceHandler)ApplicationContextUtils.getBean(com.Constant.CONST_COMMONSERVICEHANDLER);
		
		//if modify
		if(request.getIsModified()){
			HomeCreateModifyQuoteRequest homeCreateModifyQuoteRequest = new HomeCreateModifyQuoteRequest();
			homeCreateModifyQuoteRequest.setHomeInsuranceDetails(request.getHomeInsuranceDetails());
			homeCreateModifyQuoteRequest.setOpIdentifier(MODIFY);
			
			saveCreateQuote(homeCreateModifyQuoteRequest);
			
		} else {//else retrieve
			homeInsuranceVO = commonServiceHandler.retrieveHomeInsuranceVO(request.getHomeInsuranceDetails().getQuoteNo(),user);
		}
		
		
		//call renew
		//call convert to policy
		HomeConvertToPolicyResponse response = converToPolicy(homeInsuranceVO);
		
		//create response
		HomeInsuranceDetails homeInsuranceDetails = response.getHomeInsuranceDetails();
		//get cover
		
		if (!Utils.isEmpty(homeInsuranceDetails)) {
			
			homeRenewPolicyResponse = new HomeRenewPolicyResponse();
			homeRenewPolicyResponse
			.setCoverDetails(homeInsuranceDetails.getCovers());
			homeRenewPolicyResponse
			.setPolicyId(homeInsuranceDetails.getPolicyId());
			homeRenewPolicyResponse
			.setPremiumDetails(homeInsuranceDetails
					.getPremiumVO());
		}
			
		return homeRenewPolicyResponse;

	}
}
