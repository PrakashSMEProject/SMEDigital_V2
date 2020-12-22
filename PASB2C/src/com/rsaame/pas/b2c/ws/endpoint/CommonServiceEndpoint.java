package com.rsaame.pas.b2c.ws.endpoint;
import javax.jws.WebService;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.ServletContextAware;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.rsaame.pas.b2c.cmn.utils.ApplicationContextUtils;
import com.rsaame.pas.b2c.exception.SystemException;
import com.rsaame.pas.b2c.exception.ValidationException;
import com.rsaame.pas.b2c.ws.beans.LookUpRequest;
import com.rsaame.pas.b2c.ws.beans.LookUpResponse;
import com.rsaame.pas.b2c.ws.beans.ProductDetailsRequest;
import com.rsaame.pas.b2c.ws.beans.ProductDetailsResponse;
import com.rsaame.pas.b2c.ws.beans.SearchQuotePolicyRequest;
import com.rsaame.pas.b2c.ws.beans.SearchQuotePolicyResponse;
import com.rsaame.pas.b2c.ws.beans.SendNotificationMailRequest;
import com.rsaame.pas.b2c.ws.beans.SendNotificationMailResponse;
import com.rsaame.pas.b2c.ws.handler.CommonServiceHandler;

/**
 * @author m1020637
 *
 */
@WebService
@Endpoint
public class CommonServiceEndpoint implements ServletContextAware
{
	
	private ServletContext servletContext = null;
	@Override
	public void setServletContext(ServletContext arg0) {
	
		servletContext = arg0;
		
	}
	private static final String GET_LOOKUP_NAMESPACE = "http://com/pas/ws/lookUpRequest";
	private static final String TARGET_NAMESPACE = "http://com/rsaame/pas/b2c/ws";

	/** Logger instance */
	private static final Logger logger = Logger.getLogger(CommonServiceEndpoint.class);
	
	/**
	 * @param request
	 * @return
	 */
	@PayloadRoot(localPart = "LookUpRequest", namespace = GET_LOOKUP_NAMESPACE)
	public @ResponsePayload LookUpResponse getMasterDetails(@RequestPayload LookUpRequest request) throws Exception
	{
		LookUpResponse response;
		
		try {
			//call handler
			CommonServiceHandler commonServiceHandler = (CommonServiceHandler)ApplicationContextUtils.getBean(com.Constant.CONST_COMMONSERVICEHANDLER);
			response = commonServiceHandler.getLookupDetails(request.getLob());
		} catch (ValidationException e) {
			throw e;
		} catch (Exception e) {
			throw new SystemException("Error occured in retrieving master details");
		}

		//return
		return response;
	}
	
	/**
	 * @param request
	 * @return
	 */
	@PayloadRoot(localPart = "SearchQuotePolicyRequest", namespace = TARGET_NAMESPACE)
	public @ResponsePayload SearchQuotePolicyResponse searchQuotePolicy(@RequestPayload SearchQuotePolicyRequest request)  throws Exception
	{
		SearchQuotePolicyResponse response = null;
		
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String user = currentUser.getName();
		
		if(request.getEmailId() == null || request.getIdNumber() == null){
			throw new ValidationException("Email Id and Quote Number are mandatory");
		}
		
		try {
			CommonServiceHandler commonServiceHandler = (CommonServiceHandler)ApplicationContextUtils.getBean(com.Constant.CONST_COMMONSERVICEHANDLER);
			response = commonServiceHandler.searchQuotePolicy(request,user);
		} catch (ValidationException e) {
			throw e;
		} catch (Exception e) {
			throw new SystemException("Quote number not found or does not exist");
		}

		return response;
	}
	
	/**
	 * @param request
	 * @return
	 */
	@PayloadRoot(localPart = "SendNotificationMailRequest", namespace = TARGET_NAMESPACE)
	public @ResponsePayload SendNotificationMailResponse sendMail(@RequestPayload SendNotificationMailRequest request) throws Exception
	{
		SendNotificationMailResponse response = null;
		CommonServiceHandler commonServiceHandler = (CommonServiceHandler)ApplicationContextUtils.getBean(com.Constant.CONST_COMMONSERVICEHANDLER);
		
		//call handler
		try {
			response = commonServiceHandler.sendMailNotification(request,servletContext.getContextPath());
		} catch (ValidationException e) {
			throw e;
		} catch (Exception e) {
			throw new SystemException("Quote number not found or does not exist");
		}
		
		//return
		return response;
	}
	
	/**
	 * @param request
	 * @return
	 */
	@PayloadRoot(localPart = "ProductDetailsRequest", namespace = TARGET_NAMESPACE)
	public @ResponsePayload ProductDetailsResponse getProductDetails(@RequestPayload ProductDetailsRequest request) throws Exception
	{
		ProductDetailsResponse response = null;
		CommonServiceHandler commonServiceHandler = (CommonServiceHandler)ApplicationContextUtils.getBean(com.Constant.CONST_COMMONSERVICEHANDLER);
		
		String lob = request.getLob();
			Long tarrifCd = request.getTariffCd();
				Long schemeCd = request.getSchemeCd();
		
		//call handler
		try {
			response = commonServiceHandler.getProductDetails(lob,tarrifCd,schemeCd);
			
		} catch (ValidationException e) {
			throw e;
		} catch (Exception e) {
			throw new SystemException("Something went wrong");
		}
		
		//return
		return response;
	}


}
