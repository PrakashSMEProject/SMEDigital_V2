package com.rsaame.pas.b2c.controllers;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.cmn.vo.User;
import com.rsaame.pas.b2b.ws.util.WSAppUtils;
import com.rsaame.pas.b2c.WsAuthentication.BasicAuthenticationService;
import com.rsaame.pas.b2c.cmn.base.BaseController;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.handlers.B2CEmailTriggers;
import com.rsaame.pas.b2c.cmn.handlers.CommonHandler;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.b2c.cmn.utils.ApplicationContextUtils;
import com.rsaame.pas.b2c.cmn.utils.ReferralUtils;
import com.rsaame.pas.b2c.exception.SystemException;
import com.rsaame.pas.b2c.travelInsurance.TravelInsuranceHandler;
import com.rsaame.pas.b2c.user.B2CRSAUserWrapper;
import com.rsaame.pas.b2c.validator.TravelGIQuoteCreateValidator;
import com.rsaame.pas.b2c.validator.TravelGIQuoteFetchValidator;
import com.rsaame.pas.b2c.validator.TravelRenewalValidator;
import com.rsaame.pas.b2c.validator.TravelRiskValidator;
import com.rsaame.pas.b2c.ws.handler.CommonServiceHandler;
import com.rsaame.pas.b2c.ws.mapper.BaseRequestVOMapper;
import com.rsaame.pas.b2c.ws.mapper.BaseResponseVOMapper;
import com.rsaame.pas.b2c.ws.mapper.CreatePolicyRequestMapper;
import com.rsaame.pas.b2c.ws.mapper.CreatePolicyResponseMapper;
import com.rsaame.pas.b2c.ws.mapper.RetrieveQuoteByPolicyRequestMapper;
import com.rsaame.pas.b2c.ws.mapper.RetrieveQuoteByPolicyResponseMapper;
import com.rsaame.pas.b2c.ws.mapper.RetrieveQuoteByQuoteIDMapper;
import com.rsaame.pas.b2c.ws.mapper.TravelCreateQuoteRequestMapper;
import com.rsaame.pas.b2c.ws.mapper.TravelCreateQuoteResponseMapper;
import com.rsaame.pas.b2c.ws.mapper.TravelUpdateQuoteRequestMapper;
import com.rsaame.pas.b2c.ws.mapper.TravelUpdateQuoteResponseMapper;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.mapper.WebServiceAuditMapper;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo.HeaderInfo;
import com.rsaame.pas.b2c.ws.vo.CreatePolicyRequest;
import com.rsaame.pas.b2c.ws.vo.CreatePolicyResponse;
import com.rsaame.pas.b2c.ws.vo.Customer;
import com.rsaame.pas.b2c.ws.vo.Quote;
import com.rsaame.pas.b2c.ws.vo.RetrievePolicyByPolicyNo;
import com.rsaame.pas.b2c.ws.vo.RetrieveQuoteByPolicyRequest;
import com.rsaame.pas.b2c.ws.vo.RetrieveQuoteByQuoteId;
import com.rsaame.pas.b2c.ws.vo.RetrieveTravelQuoteByPolicyResponse;
import com.rsaame.pas.b2c.ws.vo.UpdateTravelQuoteRequest;
import com.rsaame.pas.b2c.ws.vo.UpdateTravelQuoteResponse;
import com.rsaame.pas.b2c.wsException.ValidationError;
import com.rsaame.pas.b2c.wsException.ValidationException;
import com.rsaame.pas.b2c.wsValidators.CreatePolicyRequestValidator;
import com.rsaame.pas.b2c.wsValidators.RetrieveQuoteByPolicyValidator;
import com.rsaame.pas.b2c.wsValidators.RetriveQuoteByQuoteIdValidator;
import com.rsaame.pas.b2c.wsValidators.TravelCreateQuoteValidator;
import com.rsaame.pas.b2c.wsValidators.UpdateTravelQuoteValidator;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.BusinessChannel;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.TravelDetailsVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.web.UserProfileHandler;

/**
 * @author M1033774
 * 
 */
@Controller
@RequestMapping("/rsaservices")
public class WebservicesTravelController extends BaseController {

	private static final Logger logger = Logger
			.getLogger(WebservicesTravelController.class);
	private static final Byte RELATIONSHIP_SELF = Byte.valueOf("1");
	CommonController commonCtrl = new CommonController();
	CommonServiceHandler commonServiceHandler = new CommonServiceHandler();
	CommonHandler handler = new CommonHandler();
	WebServiceAuditMapper webServiceAuditMapper = new WebServiceAuditMapper();
	final String submitTravelRisk = "submitTravelRisk";
	

	/**
	 * @param request
	 * @param session
	 *            Method to Load general info for create quote.
	 * @throws Exception 
	 */
	//@RequestMapping("**/CreateQuote.do", method = RequestMethod.POST)	
	/*public ModelAndView loadTravelGenralInfo(@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request,
			HttpSession session, HttpServletResponse response) { //PKS. Added response here.
    */
	
	@RequestMapping(value = "**/CreateTravelQuote", method = RequestMethod.POST) //PKS
	public  @ResponseBody Quote loadTravelGenralInfo(@RequestBody Customer customer, @ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request,
			HttpSession session, HttpServletResponse response) throws Exception { 
		Quote quote=new Quote();//PKS
		
		//Webservice's validation framework starts. //PKS
		ValidationException validationException = new ValidationException();
		validationException=new TravelCreateQuoteValidator().validate(customer);
		
		if(validationException.getErrors() != null && !Utils.isEmpty(validationException.getErrors())) {
			//return with errors. else continue
			List list=validationException.getErrors();
			com.rsaame.pas.b2c.ws.vo.Errors[] errorList=new com.rsaame.pas.b2c.ws.vo.Errors[list.size()];
			java.util.Iterator it=list.iterator();
			com.rsaame.pas.b2c.ws.vo.Errors error=null;
			int i=0;
			while(it.hasNext()) {
				//System.out.println(it.next()); 
				ValidationError error1=(ValidationError)it.next();				
				
				error= new com.rsaame.pas.b2c.ws.vo.Errors();
				error.setCode(error1.getCode());
				error.setField(error1.getField());
				error.setMessage(error1.getMessage());
				
				errorList[i]=error;
		        i++;
			}
			quote.setErrors(errorList);
			return quote;
		}
		//Webservice validation framework ends. //PKS
		
		Boolean isTerrCruiseInductionDate = true;
		logger.debug("Going to load TravelInsuranceVO bean during screen load");
		setLocation();
		String promoCode = null;
		String partner = null;
		
		/*request.getSession(false).removeAttribute(
				AppConstants.SESSION_USER_PROFILE_VO);*/ //PKS commented.
		
		TravelInsuranceHandler travelInsHandler = new TravelInsuranceHandler();
		String partnername = request.getRequestURI();
		partnername = request.getRequestURI().replace(com.Constant.CONST_QUOTEANDBUY_END, "")
				.replace("/TravelStep1.do", "").replace("TravelStep1.do", "");
		partnername = request.getHeader(com.Constant.CONST_PARTNERNAME);
		
		String url = request.getRequestURL().toString();
		url = url.replace(partnername + "/", "");
		request.setAttribute(com.Constant.CONST_PARTNERNAMECSS, partnername);
		request.getSession(false).setAttribute(com.Constant.CONST_ISTERRCRUISEINDUCTIONDATE, isTerrCruiseInductionDate);
		logger.info("WebservicesTravelController.loadTravelGeneralInfo method, partnername: "+partnername+" ,  "+url);
		
		try {
			travelInsuranceVO = travelInsHandler.populateTravelInsForLoad();
			travelInsuranceVO.getCommonVO().setIsQuote(Boolean.TRUE);
			/*
			 * Added for Mirror-site changes
			 */
			/*
			 * if (Utils.isEmpty(travelInsuranceVO.getScheme())) { SchemeVO
			 * schemeVo = new SchemeVO(); travelInsuranceVO.setScheme(schemeVo);
			 * } travelInsuranceVO.getScheme().setSchemeCode(
			 * Integer.valueOf(AppConstants.B2C_HOME_DEFAULT_SCHEME));
			 * travelInsuranceVO.getScheme().setTariffCode(
			 * Integer.valueOf(AppConstants.B2C_HOME_DEFAULT_TARIFF));
			 */
			//promoCode = request.getParameter("promoCode"); //PKS commented.
			promoCode = customer.getTransactionDetails().getPromocode();//"TRA816"; //PKS
			GeneralInfoVO generalInfo = new GeneralInfoVO();
			logger.info("WebservicesTravelController.loadTravelGeneralInfo method, promoCode: "+promoCode);

			TravelDetailsVO travelDetailsVO = new TravelDetailsVO();
			List<TravelerDetailsVO> travelerDetailsList = new ArrayList<TravelerDetailsVO>();
			TravelerDetailsVO traveller = new TravelerDetailsVO();
			DateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
			if (!Utils.isEmpty(request.getParameter("dob"))) {
				try {
					traveller.setDateOfBirth(fmt.parse(request
							.getParameter("dob")));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (!(Utils.isEmpty(partnername))) {

				// Added by Vishwa to NEXUS PROMO for Advenet ID 128926
				partner = DAOUtils.fetchPartnerInfo(partnername);
				logger.debug("1. Partner info for loadTravelGenralInfo() of WebservicesTravelController Class  "
						+ partner);
				request.setAttribute(com.Constant.CONST_PARTNER, partner);

				if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo())) {
					// GeneralInfoVO generalInfo = new GeneralInfoVO();
					String insuredCode = request.getParameter("insuredCode");
					InsuredVO insured = new InsuredVO();
					logger.info("WebservicesTravelController.loadTravelGeneralInfo method, insuredCode: "+insuredCode);

					travelDetailsVO.setTravelerDetailsList(travelerDetailsList);

					if (Utils.isEmpty(insuredCode)) {
						insured.setMobileNo(request
								.getParameter("mobileNumber"));
						insured.setEmailId(request.getParameter("email"));
						insured.setFirstName(request.getParameter("name"));
						traveller.setName(request.getParameter("name"));

						generalInfo.setInsured(insured);

					} else {
						insured.setInsuredCode(Long.valueOf(insuredCode));
						insured.setInsuredId(insured.getInsuredCode());
						generalInfo.setInsured(insured);
						PolicyDataVO policyDataVO = new PolicyDataVO();
						policyDataVO.setGeneralInfo(generalInfo);

						commonServiceHandler.loadInsuredDetails(policyDataVO);
						generalInfo = policyDataVO.getGeneralInfo();
						traveller.setName(generalInfo.getInsured()
								.getFirstName()
								+ " "
								+ generalInfo.getInsured().getLastName());
					}
					if(traveller.getRelation()==null){
						
						
						traveller.setRelation( Byte.valueOf( Utils.getSingleValueAppConfig( "RELATION_SELF" ) ) );
					}
					travelerDetailsList.add(traveller);
					travelDetailsVO.setTravelerDetailsList(travelerDetailsList);
					travelInsuranceVO.setTravelDetailsVO(travelDetailsVO);
					travelInsuranceVO.setGeneralInfo(generalInfo);
				}
				if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
						.getSourceOfBus())) {
					SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();

					travelInsuranceVO.getGeneralInfo().setSourceOfBus(
							sourceOfBusinessVO);
				}
				if (!Utils.isEmpty(promoCode)) {
					travelInsuranceVO.getGeneralInfo().getSourceOfBus()
							.setPromoCode(promoCode);
				}
				/*
				 * if(Utils.isEmpty(travelInsuranceVO.getGeneralInfo().
				 * getSourceOfBus().getPromoCode()) && !Utils.isEmpty( promoCode
				 * )) {
				 * travelInsuranceVO.getGeneralInfo().getSourceOfBus().setPromoCode
				 * ( promoCode ); }
				 */
				travelInsuranceVO.getGeneralInfo().getSourceOfBus()
						.setPartnerName(partnername);
				travelInsuranceVO = (TravelInsuranceVO) TravelInsuranceHandler
						.loadPartnerMgmtDetails(travelInsuranceVO);
				UserProfile userProfile = UserProfileHandler
						.getUserProfileVo(travelInsuranceVO.getGeneralInfo()
								.getExtAccExecCode());
				request.getSession(false).setAttribute(
						AppConstants.SESSION_USER_PROFILE_VO, userProfile);
			} else {
				if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo())) {
					/*
					 * TravelDetailsVO travelDetailsVO = new TravelDetailsVO();
					 * List<TravelerDetailsVO> travelerDetailsList = new
					 * ArrayList<TravelerDetailsVO>(); TravelerDetailsVO
					 * traveller = new TravelerDetailsVO();
					 */

					travelDetailsVO.setTravelerDetailsList(travelerDetailsList);

					String insuredCode = request.getParameter("insuredCode");
					InsuredVO insured = new InsuredVO();

					if (Utils.isEmpty(insuredCode)) {
						insured.setMobileNo(request
								.getParameter("mobileNumber"));
						insured.setEmailId(request.getParameter("email"));
						// insured.setFirstName(request.getParameter( "name"));
						traveller.setName(request.getParameter("name"));
						/*
						 * DateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
						 * if(!Utils.isEmpty(request.getParameter( "dob"))){
						 * traveller
						 * .setDateOfBirth(fmt.parse(request.getParameter(
						 * "dob"))); }
						 */
						generalInfo.setInsured(insured);

					} else {
						insured.setInsuredCode(Long.valueOf(insuredCode));
						insured.setInsuredId(insured.getInsuredCode());
						generalInfo.setInsured(insured);
						PolicyDataVO policyDataVO = new PolicyDataVO();
						policyDataVO.setGeneralInfo(generalInfo);

						commonServiceHandler.loadInsuredDetails(policyDataVO);
						generalInfo = policyDataVO.getGeneralInfo();
						traveller.setName(generalInfo.getInsured()
								.getFirstName()
								+ " "
								+ generalInfo.getInsured().getLastName());
						// travelDetailsVO = policyDataVO.get

					}
					if(traveller.getRelation()==null){
						
						
						traveller.setRelation( Byte.valueOf( Utils.getSingleValueAppConfig( "RELATION_SELF" ) ) );
					}
					travelerDetailsList.add(traveller);
					travelDetailsVO.setTravelerDetailsList(travelerDetailsList);
					travelInsuranceVO.setGeneralInfo(generalInfo);
					travelInsuranceVO.setTravelDetailsVO(travelDetailsVO);
				}
				if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
						.getSourceOfBus())) {
					SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
					/*
					 * if(!Utils.isEmpty(promoCode)){
					 * sourceOfBusinessVO.setPromoCode(promoCode); }
					 */
					travelInsuranceVO.getGeneralInfo().setSourceOfBus(
							sourceOfBusinessVO);
				}
				if (!Utils.isEmpty(promoCode)) {
					travelInsuranceVO.getGeneralInfo().getSourceOfBus()
							.setPromoCode(promoCode);
				}
				travelInsuranceVO
						.getGeneralInfo()
						.getSourceOfBus()
						.setDistributionChannel(
								AppConstants.B2C_DEFAULT_DIST_CHANNEL);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		try {
			request.setAttribute(com.Constant.CONST_TRAVELERDETAILS, AppUtils
					.createJSONForTravelerDetails(travelInsuranceVO
							.getTravelDetailsVO().getTravelerDetailsList(),
							com.Constant.CONST_TRAVELER_DETAILS_JSON_RETRIVE_QUOTE));
			request.setAttribute(com.Constant.CONST_ISLOADOPERATION, "true");

		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		}

		String errorMsg = request.getParameter(com.Constant.CONST_ERRORMSG);
		if (!Utils.isEmpty(errorMsg)) {
			Errors errors = bindingResult;
			errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, errorMsg);
		}
		ModelAndView modelAndView = null;
		if (!(Utils.isEmpty(partnername))
				&& Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
						.getSourceOfBus().getPartnerId())) {
			modelAndView = new ModelAndView("redirect:" + url);
		} else {
			modelAndView = new ModelAndView();
			modelAndView.setViewName(com.Constant.CONST_TRAVELGENERALINFO);
		}
		modelAndView.addObject(com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);
		// modelAndView.addObject(com.Constant.CONST_PARTNER, partner);
		request.setAttribute("partnername", partnername);
		/*
		 * D2C Deployed Location set to session : BEGIN
		 */
		session.setAttribute(com.Constant.CONST_DEPLOYEDLOC, getLocation().getLocation());
		/*
		 * D2C Deployed Location set to session  : END
		 */
		logger.info("Exiting WebservicesTravelController.loadTravelGeneralInfo method.");
		
		
		//PKS 
		//Setting from the JSON Customer object to travelInsuranceVO object:
		
		/*Boolean warZone=customer.warZone;
		if(warZone){
			quote.message="RSA will not be able to provide cover or be liable to provide any indemnity or payment for customers travelling to the below mentioned countries.";
			return quote;
		}*/			
		
		try {
			new TravelCreateQuoteRequestMapper().mapRequestToVO(customer, travelInsuranceVO);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//Calling the next inline method:
		try{
			saveTravelGeneralInfo(travelInsuranceVO,bindingResult,request,response,session,quote);
		}catch(ParseException e){
			e.printStackTrace();
		}
		//PKS
		//return modelAndView;		
		try {
			new TravelCreateQuoteResponseMapper().mapVOToTravelResponse(travelInsuranceVO, quote, customer);
			//quote.setValidUntil((String)request.getAttribute(com.Constant.CONST_VALIDQUOTEDATE));  //travelInsuranceVO.getCommonVO().getVsd();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//Now call for "Save For Later" method. PKS start
		/*try{
			saveTravel(travelInsuranceVO,bindingResult,request,response);
		}catch(ParseException e){}*/
		//PKS end.
		webServiceAuditMapper.mapRequestToVO(customer, quote);
	    return quote;

	}

	public boolean checkPolPreparedDt(TravelInsuranceVO travelInsuranceVO) throws ParseException {
		/* Fetch this date for the 0th endorsement, currently its fetching for the current endorsement */
			//String d1 = travelInsuranceVO.getCommonVO().getCreatedOn().toString();
			
			boolean isTerrCruiseInductionDate = false;
			String d2 = Utils.getSingleValueAppConfig("TerrCruiseInductionDate");
			SimpleDateFormat s2 = new SimpleDateFormat("MM-dd-yyyy");
			Long QuoteNum = travelInsuranceVO.getQuoteNo();
			if(Utils.isEmpty(QuoteNum)){
				QuoteNum=travelInsuranceVO.getCommonVO().getQuoteNo();
			}
			Date polPrepDt = DAOUtils.getPreparedDateForCovers(QuoteNum);

			//Date polPrepDt = s1.parse(d1);
			Date prodDt = s2.parse(d2);
			if(polPrepDt.after(prodDt)){
				isTerrCruiseInductionDate = true;
			}
			return isTerrCruiseInductionDate;
	}
	/**
	 * @param travelInsuranceVO
	 * @param bindingResult
	 * @param request
	 * @param session
	 *            Method to save gen info details.
	 * @throws ParseException 
	 */
	//@RequestMapping(value = "**/TravelStep2.do", method = RequestMethod.POST)
	public ModelAndView saveTravelGeneralInfo(
			@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Quote quote) throws ParseException {
		
		setLocation();
		logger.debug("Going to save the travel details entered");
		ModelAndView modelAndView = new ModelAndView(com.Constant.CONST_TRAVELGENERALINFO,
				com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);

		//Added for Oman location check
		LoginLocation location = (LoginLocation) Utils.getBean(com.Constant.CONST_LOCATION);
		String deployedLocation = location.getLocation();
		
		/*
		 * TravelInsuranceLookupValidator lookupValidator =
		 * (TravelInsuranceLookupValidator
		 * )Utils.getBean("travelLookupValidator");
		 * lookupValidator.validate(travelInsuranceVO, bindingResult);
		 */

		try {
			UserProfile userProfile_t = UserProfileHandler.getUserProfileVo(991); //PKS harcoded, as its not available in the session attribute.
			request.getSession(false).setAttribute(AppConstants.SESSION_USER_PROFILE_VO, userProfile_t);//PKS
				
			UserProfile userProfile = (UserProfile) request.getSession(false).getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
			
			travelInsuranceVO.getCommonVO().setLoggedInUser(userProfile); 
			
			travelInsuranceVO.setLoggedInUser(userProfile);
			
			String partnername = request.getParameter(com.Constant.CONST_PARTNERNAME);
			// String partnerId = request.getParameter("")== null
			// ?"":(String) request.getParameter("partnerId");
			setDefaultDetails(request, travelInsuranceVO); 
			/*
			 * if (!Utils.isEmpty(partnerId)) { TravelInsuranceHandler
			 * .loadPartnerMgmtDetails(travelInsuranceVO); }
			 */

			// Added by Vishwa to NEXUS PROMO for Advenet ID 128926
			String partner = DAOUtils.fetchPartnerInfo(partnername);
			logger.debug("2. Partner info for loadTravelGenralInfo() of WebservicesTravelController Class  "
					+ partner);
			request.setAttribute(com.Constant.CONST_PARTNER, partner);
			request.setAttribute(com.Constant.CONST_PARTNERNAMECSS, partnername); // For Css
			travelInsuranceVO = TravelInsuranceHandler
					.populateTravelInsuranceForSave(travelInsuranceVO, request);
			/*Dileep - VAT*/
			travelInsuranceVO = TravelInsuranceHandler
					.populateVatCodeAndVatTax(travelInsuranceVO, request);
			if (!Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
					.getSourceOfBus())
					&& !Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
							.getSourceOfBus().getPromoCode())
					&& !Utils.isEmpty(travelInsuranceVO.getScheme()
							.getEffDate())
					&& !Utils.isEmpty(travelInsuranceVO.getScheme()
							.getExpiryDate())) {
				TaskExecutor.executeTasks("VALIDATE_PROMO_CODE",
						travelInsuranceVO);
			}

			TravelGIQuoteCreateValidator quoteCreateValidator = (TravelGIQuoteCreateValidator) ApplicationContextUtils
					.getBean("giValidator");
			quoteCreateValidator.validate(travelInsuranceVO, bindingResult);
			
			com.rsaame.pas.b2c.ws.vo.Errors error=null;//PKS
			com.rsaame.pas.b2c.ws.vo.Errors[] errorList=new com.rsaame.pas.b2c.ws.vo.Errors[2];//PKS
			if (bindingResult.hasErrors()) {
				//modelAndView.setViewName(com.Constant.CONST_TRAVELGENERALINFO);
				//modelAndView.addObject(bindingResult);
				//return modelAndView;
				int i=0;
				for (Object object : bindingResult.getAllErrors()) { //PKS
					if(object instanceof FieldError) {
				        FieldError fieldError = (FieldError) object;
				        logger.info(fieldError.getCode());
				        logger.info(fieldError.getDefaultMessage());
				        error= new com.rsaame.pas.b2c.ws.vo.Errors();
				        error.setCode(fieldError.getCode());
				        error.setMessage(fieldError.getDefaultMessage());
				        
				        errorList[i]=error;
				        i++;
				    }
				}
				quote.setErrors(errorList);
				
				return null;
			}


			logger.debug("Going to save the travel insurance related details entered on the screen.");

			setFieldsForSiteCatalyst(request, travelInsuranceVO);

			if (!Utils.isEmpty(travelInsuranceVO)) {
				travelInsuranceVO = TravelInsuranceHandler
						.saveTravelGeneralInfo(travelInsuranceVO, request
								.getRequestURL().toString());
			} else {
				throw new SystemException(new Throwable(),
						"Unexpected exception ocuured. Please contact administrator");
			}
			// to set discount
			//VAT - Change for VAT rate fetched from transaction table - Checking if PremiumVO is empty before crating new object
			//PremiumVO premiumVO = new PremiumVO();
			PremiumVO premiumVO = travelInsuranceVO.getPremiumVO();
			if(Utils.isEmpty(premiumVO)){
				premiumVO = new PremiumVO();
			}
			if (!Utils.isEmpty(partnername)
					&& !travelInsuranceVO.getGeneralInfo().getSourceOfBus()
							.getDistributionChannel().equals(10)) {
				if (!Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
						.getSourceOfBus().getDefaultOnlineDiscount())) {
					premiumVO.setDiscOrLoadPerc(travelInsuranceVO
							.getGeneralInfo().getSourceOfBus()
							.getDefaultOnlineDiscount());
				}
			} else {
				premiumVO
						.setDiscOrLoadPerc(Double.valueOf(Utils
								.getSingleValueAppConfig("TRAVEL_POLICY_LEVEL_DISCOUNT")));
			}
			travelInsuranceVO.setPremiumVO(premiumVO);

			/*
			 * If there is a referral then populate the referral message and
			 * redirect to same page
			 */
			if (!Utils.isEmpty(travelInsuranceVO.getReferralVOList())) {
				// ReferralUtils.setReferralMessage( bindingResult,
				// (PolicyDataVO)travelInsuranceVO );
				return fetchTravelGeneralInfo(travelInsuranceVO, bindingResult,
						request, response, session);
			} else {
				if (!Utils.isEmpty(travelInsuranceVO)
						&& !Utils.isEmpty(travelInsuranceVO.getCommonVO()
								.getPolicyId())) {
					logger.debug("Travel Insurance B2C quotation created with policy id as - "
							+ travelInsuranceVO.getCommonVO().getPolicyId());

					travelInsuranceVO = TravelInsuranceHandler
							.loadTravelRiskPage(travelInsuranceVO);
					TravelInsuranceHandler
					.populatePremium(travelInsuranceVO,request);

					modelAndView.setViewName(com.Constant.CONST_TRAVELRISKPAGE);
					modelAndView.addObject(travelInsuranceVO);
					logger.debug("Going to render the response back to the screen");
					AppUtils.setQuoteValidDate(travelInsuranceVO, request);
					request.setAttribute("travellersList", travelInsuranceVO
							.getTravelDetailsVO().getTravelerDetailsList());
					/* end :changes to load risk page after general info save. */

					/* omniture changes Starts for Premium value */
					for (TravelPackageVO travelPackageVO : travelInsuranceVO
							.getTravelPackageList()) {

						if (!Utils.isEmpty(travelPackageVO.getIsRecommended())
								&& travelPackageVO.getIsRecommended()
								&& !Utils.isEmpty(travelPackageVO
										.getTariffCode())) {
							request.setAttribute("omniDefaultTariffCode",
									travelPackageVO.getTariffCode());
						}

					}
					/* omniture changes Starts for Premium value */
				}
			}
			setPartnerMgmntDetails(request, travelInsuranceVO, partnername);
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			CommonHandler.renderErrorMessages(bindingResult, e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			/*
			 * Modified below based on RAkhee feedback
			 */
			
			if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
					.getSourceOfBus())
					|| Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
							.getSourceOfBus().getPartnerId())) {
				CommonHandler.renderErrorMessages(
						bindingResult,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_TRAVEL_ERROR)
								.replace(
										com.Constant.CONST_CALL_CENTER,
										travelInsuranceVO.getGeneralInfo()
												.getSourceOfBus()
												.getCallCentreNo()));				
			} else {
				if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE))
					CommonHandler.renderErrorMessages(bindingResult,
							Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_OMAN_ERROR));
				else
					CommonHandler.renderErrorMessages(bindingResult,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_ERROR));
			}

		}
		try {
			request.setAttribute(com.Constant.CONST_TRAVELERDETAILS, AppUtils
					.createJSONForTravelerDetails(travelInsuranceVO
							.getTravelDetailsVO().getTravelerDetailsList(),
							com.Constant.CONST_TRAVELER_DETAILS_JSON_RETRIVE_QUOTE));
			request.setAttribute(com.Constant.CONST_ISLOADOPERATION, "true");
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		Boolean isTerrCruiseInductionDate = true;
		if(!Utils.isEmpty(travelInsuranceVO.getCommonVO().getQuoteNo()) || !Utils.isEmpty(travelInsuranceVO.getQuoteNo())){
			isTerrCruiseInductionDate = checkPolPreparedDt(travelInsuranceVO);
		session.setAttribute(com.Constant.CONST_ISTERRCRUISEINDUCTIONDATE, isTerrCruiseInductionDate);
		}
		
		
		
		
		
		return modelAndView;
	}

	/**
	 * To retrieve travel general info page
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws ParseException 
	 */
	//@RequestMapping("**/fetchTravelGeneralInfo.do")
	public ModelAndView fetchTravelGeneralInfo(
			@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) throws ParseException {
		
		
		
	
		
		
		
		
		ModelAndView modelAndView = new ModelAndView(com.Constant.CONST_TRAVELGENERALINFO,
				com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);
		String partnername = request.getParameter(com.Constant.CONST_PARTNERNAME) == null ? ""
				: request.getParameter(com.Constant.CONST_PARTNERNAME);
		String partner=null;
		String quoteNo = null;
		TravelInsuranceHandler travelInsHandler = new TravelInsuranceHandler();
		setLocation();
		
		//Added for Oman location check
		LoginLocation location = (LoginLocation) Utils.getBean(com.Constant.CONST_LOCATION);
		String deployedLocation = location.getLocation();
		
		try {
			/*
			 * D2C Deployed Location set to session : BEGIN
			 */
			httpSession.setAttribute(com.Constant.CONST_DEPLOYEDLOC, getLocation().getLocation());
			/*
			 * D2C Deployed Location set to session : END
			 */
			
			// Added by Vishwa to NEXUS PROMO for Advenet ID 128926
			partner = DAOUtils.fetchPartnerInfo(partnername);
			logger.debug("Partner info for fetchTravelGeneralInfo() of WebservicesTravelController Class  "
					+ partner);
			request.setAttribute(com.Constant.CONST_PARTNER, partner);

			if (Utils.isEmpty(request
					.getParameter(AppConstants.QUOTE_NUM_REQ_PARAM))
					&& Utils.isEmpty(request
							.getParameter(AppConstants.EMAIL_REQ_PARAM))) {
				TravelGIQuoteFetchValidator travelGIValidator = (TravelGIQuoteFetchValidator) ApplicationContextUtils
						.getBean("retrieveQuoteGITravel");
				travelGIValidator.validate(travelInsuranceVO, result);
				if (result.hasErrors()) {
					return modelAndView;
				}
			}
			TravelInsuranceVO vo = (TravelInsuranceVO) Utils
					.getBean(com.Constant.CONST_VO_TRAVEL);
			CommonVO commonVO = null;

			TravelInsuranceVO travelInsuranceVORequest = CopyUtils
					.copySerializableObject(travelInsuranceVO);

			
			vo = travelInsHandler.populateTravelInsForSearch(travelInsuranceVO,
					request);
			travelInsuranceVORequest.setCommonVO(vo.getCommonVO());
			if (Utils.isEmpty(partnername)) {
				partnername = request.getRequestURI();
				if (!Utils.isEmpty(travelInsuranceVO.getReferralVOList())) {
					partnername = request.getRequestURI()
							.replace(com.Constant.CONST_QUOTEANDBUY_END, "")
							.replace("/TravelStep2.do", "")
							.replace("TravelStep2.do", "");
				} else {
					partnername = request.getRequestURI()
							.replace(com.Constant.CONST_QUOTEANDBUY_END, "")
							.replace("/fetchTravelGeneralInfo.do", "")
							.replace("fetchTravelGeneralInfo.do", "");
					// Added by Vishwa to NEXUS PROMO for Advenet ID 128926
				       partner = DAOUtils.fetchPartnerInfo(partnername);
				          logger.debug("ELSE Block  Partner info for fetchTravelGeneralInfo() of WebservicesTravelController Class " + partner);
				          request.setAttribute(com.Constant.CONST_PARTNER, partner);
				}

			}
			request.setAttribute(com.Constant.CONST_PARTNERNAMECSS, partnername); // For Css
			setPartnerMgmntDetails(request, travelInsuranceVORequest,
					partnername);
			
			if (!Utils.isEmpty(travelInsuranceVORequest.getGeneralInfo())
					&& !Utils.isEmpty(travelInsuranceVORequest.getGeneralInfo()
							.getSourceOfBus())
					&& !Utils.isEmpty(travelInsuranceVORequest.getGeneralInfo()
							.getSourceOfBus().getPartnerName())) {
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setPartnerId(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus().getPartnerId());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setPartnerName(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus().getPartnerName());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setCallCentreNo(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus().getCallCentreNo());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setReplyToEmailId(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus().getReplyToEmailId());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setCcEmailId(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus().getCcEmailId());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setSourceOfBusiness(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus().getSourceOfBusiness());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setFromEmailID(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus().getFromEmailID());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setDefaultOnlineDiscount(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus()
										.getDefaultOnlineDiscount());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setDefaultAssignToUser(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus()
										.getDefaultAssignToUser());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setFaqUrl(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus().getFaqUrl());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setPolicyTermUrl(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus().getPolicyTermUrl());

				// In case of Mirror Site Referral flow UserProfile was
				// overridden. Updated to set it back from session.
				if (!Utils.isEmpty(travelInsuranceVO.getReferralVOList())
						&& !Utils.isEmpty(request.getSession(false)
								.getAttribute(
										AppConstants.SESSION_USER_PROFILE_VO))) {
					UserProfile userProfile = (UserProfile) request.getSession(
							false).getAttribute(
							AppConstants.SESSION_USER_PROFILE_VO);
					vo.getCommonVO().setLoggedInUser(userProfile);
					vo.setLoggedInUser(userProfile);
				}
			}
			// setPartnerMgmntDetails(request,vo);
			if ((AppUtils.inValidEmailId(vo, travelInsuranceVORequest) || Utils
					.isEmpty(vo))
					|| !AppUtils.isValidDistributionChannel(vo,
							travelInsuranceVORequest.getGeneralInfo())
					|| !LOB.TRAVEL.equals(vo.getCommonVO().getLob())) {
				travelInsuranceVO.getCommonVO().setQuoteNo(null);
				travelInsuranceVO.setGeneralInfo(null);
				travelInsuranceVO.setScheme(null);
				travelInsuranceVO.setPolicyTerm(null);
				travelInsuranceVO.setTravelDetailsVO(null);
				travelInsuranceVO = new TravelInsuranceVO();
				setDefaultDetails(request, travelInsuranceVO);
				travelInsHandler.populateUwqDetails(travelInsuranceVO,request,true);
				modelAndView.addObject(com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);
				CommonHandler.renderErrorMessages(result,
						AppConstants.INVALID_QUOTE);
				CommonHandler.renderErrorMessages(result,
						AppConstants.INVALID_EMAIL);
			}

			if (!Utils.isEmpty(vo) && !result.hasErrors()) {
				request.setAttribute(com.Constant.CONST_TRAVELERDETAILS, AppUtils
						.createJSONForTravelerDetails(vo.getTravelDetailsVO()
								.getTravelerDetailsList(),
								com.Constant.CONST_TRAVELER_DETAILS_JSON_RETRIVE_QUOTE));
				request.setAttribute(com.Constant.CONST_ISLOADOPERATION, "true");
				// ##START - Added by Dinesh for CR-130750 Royalty feature 
				quoteNo = request.getParameter(AppConstants.QUOTE_NUM_REQ_PARAM);
				if(!Utils.isEmpty(quoteNo) && StringUtils.isNumeric(quoteNo) && !Utils.isEmpty(request.getParameter(AppConstants.EMAIL_REQ_PARAM)) ){
					   if (!Utils.isEmpty(request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM))){
						   logger.info("Going to set PROM CODE VALUE: "+request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM));
						   vo.getGeneralInfo().getSourceOfBus().setPromoCode(request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM));
						}else{
							logger.info("Going to set PROM CODE VALUE: Empty as promocode ");
							vo.getGeneralInfo().getSourceOfBus().setPromoCode("");
						}
				}//## END
				
				// To retrieve the quote with mobile number for Oman D2C
				if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE) && !Utils.isEmpty(quoteNo) && StringUtils.isNumeric(quoteNo) && !Utils.isEmpty(request.getParameter(AppConstants.MOBILE_NUM_PARAM)) ){
					if(!vo.getGeneralInfo().getInsured().getMobileNo().equalsIgnoreCase(request.getParameter(AppConstants.MOBILE_NUM_PARAM))) {
						logger.info("No result found for the Quotation number and Mobile number.");
						travelInsuranceVO.getCommonVO().setQuoteNo(null);
						travelInsuranceVO.setGeneralInfo(null);
						travelInsuranceVO.setScheme(null);
						travelInsuranceVO.setPolicyTerm(null);
						travelInsuranceVO.setTravelDetailsVO(null);
						travelInsuranceVO = new TravelInsuranceVO();
						setDefaultDetails(request, travelInsuranceVO);
						request.setAttribute(com.Constant.CONST_TRAVELERDETAILS, null);
						modelAndView.addObject(com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);
						CommonHandler.renderErrorMessages(result,
								AppConstants.NO_RESULT_QUOTE_AND_MOBILE_NUMBER);
					}
				}
				request.setAttribute(com.Constant.CONST_TRAVELINSURANCEVO, vo);
					

				if (!Utils.isEmpty(vo)) {
					commonVO = travelInsuranceVO.getCommonVO();
				}
			}
			/* Set the referral message when the status is referred */
			if (AppUtils.isReferred(commonVO)) {
				ReferralUtils.setReferralMessage(result, vo);
			}

			/*
			 * CR:123969 Modified by Vishwa to stop the user from CTP for second
			 * time.
			 */
			if (!Utils.isEmpty(commonVO)
					&& AppConstants.CONVERTED_TO_POL_STATUS.equals(commonVO
							.getStatus())) {
				
				String propertyKey = null;
				if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE)) {
					propertyKey = com.Constant.CONST_PASB2C_ASSISTANCE_OMAN;
				}else {
					propertyKey = com.Constant.CONST_PASB2C_ASSISTANCE;
				}
				if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
						.getSourceOfBus().getPartnerId())) {
					CommonHandler
							.renderErrorMessages(
									result,
									Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_POLICYQUOTE_RETRIEVED)
											+ commonVO.getPolicyNo().toString()
											+ ". "
											+ Utils.getAppErrorMessage(propertyKey));
				} else {
					CommonHandler
							.renderErrorMessages(
									result,
									Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_POLICYQUOTE_RETRIEVED)
											+ commonVO.getPolicyNo().toString()
											+ ". "
											+ Utils.getAppErrorMessage(
													"pasb2c.partner.assistance")
													.replace(
															com.Constant.CONST_CALL_CENTER,
															travelInsuranceVO
																	.getGeneralInfo()
																	.getSourceOfBus()
																	.getCallCentreNo()));

				}
			}
			if (Short
					.valueOf(Utils.getSingleValueAppConfig(com.Constant.CONST_REN_QUO_DOC_CODE))
					.equals(vo.getCommonVO().getDocCode())) {
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath
						+ "/TravelRenewalStep1.do?renQuote="
						+ AppUtils.encryptAndDecryptData(vo.getCommonVO()
								.getQuoteNo().toString(), Boolean.TRUE));
			}

		} catch (SystemException systemException) {
			CommonHandler.renderErrorMessages(result,
					systemException.getMessage());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (BusinessException be) {
			// travelInsuranceVO.getCommonVO().setQuoteNo( null );
			// travelInsuranceVO.setGeneralInfo( null );
			// travelInsuranceVO.setCommonVO( null );
			travelInsuranceVO.setTravelDetailsVO(null);
			// travelInsuranceVO.setScheme( null );
			travelInsuranceVO = new TravelInsuranceVO();
			travelInsHandler.populateUwqDetails(travelInsuranceVO,request,true);
			setDefaultDetails(request, travelInsuranceVO);
			modelAndView.addObject(com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);
			CommonHandler.renderErrorMessages(result, be.getMessage());

		} catch (Exception e) {
			logger.error("Exception while fetching quote details_1", e);
			if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
					.getSourceOfBus())
					|| Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
							.getSourceOfBus().getPartnerId())) {
				CommonHandler.renderErrorMessages(
						result,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_TRAVEL_ERROR)
								.replace(
										com.Constant.CONST_CALL_CENTER,
										travelInsuranceVO.getGeneralInfo()
												.getSourceOfBus()
												.getCallCentreNo()));
				
			} else {
				if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE))
					CommonHandler.renderErrorMessages(result,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_OMAN_ERROR));
				else
					CommonHandler.renderErrorMessages(result,
							Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_ERROR));
			}
		}
		Boolean isTerrCruiseInductionDate = true;
		if(!Utils.isEmpty(travelInsuranceVO.getCommonVO().getQuoteNo()) || !Utils.isEmpty(travelInsuranceVO.getQuoteNo())){
			isTerrCruiseInductionDate = checkPolPreparedDt(travelInsuranceVO);
		httpSession.setAttribute(com.Constant.CONST_ISTERRCRUISEINDUCTIONDATE, isTerrCruiseInductionDate);
		}
		/*checkPolPreparedDt(travelInsuranceVO);
		request.setAttribute(com.Constant.CONST_ISTERRCRUISEINDUCTIONDATE, isTerrCruiseInductionDate);*/
		return modelAndView;
	}

	
	//@RequestMapping(value = "**/submitTravelRisk.do", method = RequestMethod.GET)
	public ModelAndView loadPage2(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("attribute") TravelInsuranceVO travelInsuranceVO,
			BindingResult bindingResult) {

		request.setAttribute(com.Constant.CONST_VALIDQUOTEDATE, request.getSession(false)
				.getAttribute(com.Constant.CONST_VALIDQUOTEDATE));
		request.getSession().removeAttribute(com.Constant.CONST_VALIDQUOTEDATE);
		request.setAttribute("quoteIssued", request.getSession(false)
				.getAttribute(submitTravelRisk + "_quoteIssued"));
		request.setAttribute("isPrintCase", request.getSession(false)
				.getAttribute(submitTravelRisk + "_isPrintCase"));
		request.setAttribute(com.Constant.CONST_PARTNERNAMECSS, request.getSession(false)
				.getAttribute(com.Constant.CONST_PARTNERNAMECSS));
		//bindingResult = (BindingResult) request.getSession(false).getAttribute(		/* commented unused assignment to bindingResult - sonar violation fix */
		//		submitTravelRisk + "_bindingResult");
		return new ModelAndView(com.Constant.CONST_TRAVELRISKPAGE, com.Constant.CONST_TRAVELINSURANCEVO,
				(TravelInsuranceVO) request.getSession(false).getAttribute(
						submitTravelRisk + "_travelInsuranceVO"));
		// (ModelAndView)request.getSession(false).getAttribute(submitTravelRisk+"_modelAndView");
	}

	/**
	 * @param travelInsuranceVO
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "**/CreateTravelPolicy", method = RequestMethod.POST) 
	public  @ResponseBody CreatePolicyResponse createTravelPolicy(@RequestBody  CreatePolicyRequest createPolicyRequest, @ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			 	BindingResult bindingResult, HttpServletRequest request,
				HttpSession session, HttpServletResponse response) {
		
		logger.info("Create Travel Policy Started...");
		CreatePolicyResponse createPolicyResponse = new CreatePolicyResponse();
		BaseRequestVOMapper baseRequestVOMapper = new CreatePolicyRequestMapper();
		BaseResponseVOMapper baseResponseVOMapper = new CreatePolicyResponseMapper();
		HeaderInfo headerInfo = new HeaderInfo();
		headerInfo.setHeaderInfo(WSAppUtils.setHeaderInfo(request));
		ValidationError validationError2 = new ValidationError();
		ValidationException validationException = new ValidationException();
		CreatePolicyRequestValidator createPolicyRequestValidator = new CreatePolicyRequestValidator();
		if(Utils.isEmpty(createPolicyResponse.getErrors())) {
			createPolicyResponse.setErrors(new ArrayList<ValidationError>());
		}
		
		try {
			validationException = createPolicyRequestValidator.validate(createPolicyRequest);
			if(!Utils.isEmpty(validationException.getErrors())) {
				List<ValidationError> errors = new ArrayList<ValidationError>();
				for (ValidationError validationError : validationException.getErrors()) {
					ValidationError error = new ValidationError();
					error.setCode(validationError.getCode());
					error.setField(validationError.getField());
					error.setMessage(validationError.getMessage());
					errors.add(error);
				}
				createPolicyResponse.setErrors(errors);
				return createPolicyResponse;
			}
			
			baseRequestVOMapper.mapRequestToVO(createPolicyRequest, travelInsuranceVO);
			setRequestAttributes(travelInsuranceVO, request);
			if(!Utils.isEmpty(request.getHeader(com.Constant.CONST_PARTNERID))) {
				travelInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerId(request.getHeader(com.Constant.CONST_PARTNERID));
				travelInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName(request.getHeader(com.Constant.CONST_PARTNERID));
			}
			if(!Utils.isEmpty( travelInsuranceVO.getOnlinePaymentDetailsVO() )){
				logger.info( "calling CommonHandler:saveOnlinePaymentDetails method to save paymentDetailsVO." );
				
				handler.saveOnlinePaymentDetails(travelInsuranceVO.getOnlinePaymentDetailsVO());
				
				logger.info( "saveOnlinePaymentDetails saved successfully..." );
			}
			if(!Utils.isEmpty(travelInsuranceVO.getOnlinePaymentDetailsVO())) {
				createPolicyResponse.setTransactionRefNo(travelInsuranceVO.getOnlinePaymentDetailsVO().getTransactionId());
			}
			logger.info("Calling covert to policy Procedure ....");
			travelInsuranceVO =  (TravelInsuranceVO) handler.convertToPolicy(travelInsuranceVO, true, request.getRequestURL().toString());
			logger.info("Calling covert to policy Procedure execution done ....");
			
			if((!Utils.isEmpty(createPolicyRequest.getDocuments()) || !Utils.isEmpty(createPolicyRequest.getPolicyConfirmationEmail()))
					&& (createPolicyRequest.getDocuments().getDocsInResponse() || createPolicyRequest.getPolicyConfirmationEmail())
					) {
				logger.info("CommonHandler:convertToPolicy, before triggering mail for HOME LOB.");
				CommonHandler.populateAndTriggerEmail(travelInsuranceVO,  request.getRequestURL().toString(),createPolicyRequest,
						B2CEmailTriggers.TRAVEL_CONVERT_TO_POLICY);
			}
			baseResponseVOMapper.mapVOToResponse(travelInsuranceVO, createPolicyResponse);
			
			if(!Utils.isEmpty(createPolicyRequest.getDocuments())) {
				createPolicyResponse.setDocuments(createPolicyRequest.getDocuments());
				if(!Utils.isEmpty(createPolicyRequest.getDocuments().getDocsDetails())) {
					createPolicyResponse.getDocuments().setDocsDetails(createPolicyRequest.getDocuments().getDocsDetails());
				}
			}
			
			if(!Utils.isEmpty(createPolicyRequest.getDocuments()) && createPolicyRequest.getDocuments().getDocsInResponse()) {
				long startTime = System.currentTimeMillis();
				logger.debug( "Calling Document Creation:::_1"  + new Date(startTime)   );
				
				byte[] fileContent = null;
				CommonHandler.printPolicyDocument(travelInsuranceVO, createPolicyRequest.getDocuments());
				String policySchedulefile = CommonHandler.encodeToString(Utils.getSingleValueAppConfig("POL_DOC_POL_SCHED_LOC")+travelInsuranceVO.getCommonVO().getPolicyNo()+"-PolicySchedule.pdf");
				fileContent = policySchedulefile.getBytes();
				createPolicyResponse.getDocuments().setPolicySchedule(fileContent);
				
				long endTime = System.currentTimeMillis();
				logger.debug( " Document Creation Done_1"  + new Date(endTime)   );
				logger.debug( "Time taken for Document creation and converting to byte array::_1"  + ( endTime - startTime )  );
				
			}
			webServiceAuditMapper.mapCreatePolicyToAudit(travelInsuranceVO, createPolicyRequest, createPolicyResponse,headerInfo);
			
		} catch(BusinessException e) {
			
			validationError2.setMessage(e.getMessage());
			createPolicyResponse.getErrors().add(validationError2);
			e.printStackTrace();
		}catch(SystemException e) {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_MESSAGES);
			validationError2.setMessage(resourceBundle.getString(com.Constant.CONST_CMN_SYSTEMERROR));
			createPolicyResponse.getErrors().add(validationError2);
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_MESSAGES);
			validationError2.setMessage(resourceBundle.getString(com.Constant.CONST_CMN_SYSTEMERROR));
			createPolicyResponse.getErrors().add(validationError2);
			e.printStackTrace();
			
		}
		logger.info("Create Travel Policy Done...");
		return createPolicyResponse;
		
		
	}

	private void nullRemove(TravelInsuranceVO travelInsuranceVO) {
		
		
		
		/*
		 * 
		 *  Removing unused null covers from TravelInsuranceVO : BEGIN : Himanish
		 * 
		 * 
		 */
		
		for (int packageInde = 0; packageInde < travelInsuranceVO
				.getTravelPackageList().size(); packageInde++) {
			int coverIndex = 0;
			while (coverIndex < travelInsuranceVO.getTravelPackageList()
					.get(packageInde).getCovers().size()) {
				if (null == travelInsuranceVO.getTravelPackageList()
						.get(packageInde).getCovers().get(coverIndex)
						.getCoverName()) {
					travelInsuranceVO.getTravelPackageList().get(packageInde)
							.getCovers().remove(coverIndex);
				} else
					coverIndex++;
			}
			
		}
	/*
	 * 
	 * Removing unused null covers from TravelInsuranceVO : END : Himanish
	 * 
	 */
		
		
		
	}

	/**
	 * @param travelInsuranceVO
	 * @param bindingResult
	 * @param request
	 * @return
	 */
	private void saveTravelDetails(TravelInsuranceVO travelInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request,
			boolean completePurchaseInd, boolean isPrintCase,boolean proposalForm) {
		logger.info("Entered WebservicesTravelController.saveTravelDetails method.");
		long stdTime = System.currentTimeMillis();
		logger.debug( " SaveTravelDetails method starts::::");
		List<TravelPackageVO> packagesToBeDeleted = new ArrayList<TravelPackageVO>();
		//Added for Oman location check
		LoginLocation location = (LoginLocation) Utils.getBean(com.Constant.CONST_LOCATION);
		String deployedLocation = location.getLocation();
		try {

			//TravelInsuranceHandler.preProcess(travelInsuranceVO,packagesToBeDeleted);

			UserProfile userProfile = (UserProfile) request.getSession(false)
					.getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
			travelInsuranceVO.getCommonVO().setLoggedInUser(userProfile);

			travelInsuranceVO.setPopulateOperation(false);

			if (!Utils.isEmpty(travelInsuranceVO)) {
				/*TravelRiskValidator travelRiskValidator = (TravelRiskValidator) ApplicationContextUtils
						.getBean("travelRiskValidator");
				travelRiskValidator.validate(travelInsuranceVO, bindingResult);*/

				if (!bindingResult.hasErrors()) {

					/*
					 * Since rating service accepts only one package, delete
					 * unselected packages from travelInsuranceVO.
					 */
					TravelInsuranceHandler.prepareForRatingCall(
							travelInsuranceVO, packagesToBeDeleted);

					DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();
					Object[] inpObjects = { travelInsuranceVO, false };
					dataHolder.setData(inpObjects);								
					//VAT - 142244	
					//TravelInsuranceHandler.populatePremium(travelInsuranceVO,request);
					//travelInsuranceVO = TravelInsuranceHandler.saveTravelRisk(travelInsuranceVO);

					long ssqtTime = System.currentTimeMillis();
					logger.debug( " SAVE_QUOTE_TRAVEL method starts::::");
					travelInsuranceVO = (TravelInsuranceVO) TaskExecutor.executeTasks( "SAVE_QUOTE_TRAVEL", dataHolder );
					long esqtTime = System.currentTimeMillis();
					logger.info( " SAVE_QUOTE_TRAVEL method ends::::"  + ( esqtTime - ssqtTime )  );
					
					// Trigger E-mail in-case of save for later start
					if (!isPrintCase && !Utils.isEmpty(travelInsuranceVO)
							&& !completePurchaseInd) {
						logger.info("WebservicesTravelController.saveTravelDetails method, calling TravelInsuranceHandler.populatePremium method.");
					/*	TravelInsuranceHandler
								.populatePremium(travelInsuranceVO,request); // To
																		// populate
																		// the
																		// premium
																		// in
																		// PremiumVO
*/					if(proposalForm) {
						long spfTime = System.currentTimeMillis();
						logger.info( " PopulateAndTriggerEmail method starts::::");
						logger.info("WebservicesTravelController.saveTravelDetails method, calling CommonHandler.populateAndTriggerEmail method.");
						CommonHandler.populateAndTriggerEmail(
								travelInsuranceVO, request.getRequestURL()
										.toString(),
								B2CEmailTriggers.TRAVEL_SAVE_FOR_LATER, null);
						long epfTime = System.currentTimeMillis();
						logger.info( " PopulateAndTriggerEmail method ends::::"  + ( epfTime - spfTime )  );
					      }
					}
					// Trigger E-mail in-case of save for later end
				}
			}
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			throw new SystemException(e.getCause(), e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE))
				throw new SystemException(e.getCause(),
					Utils.getAppErrorMessage("pasb2c.quote.oman.error"));
			else
				throw new SystemException(e.getCause(),
						Utils.getAppErrorMessage("pasb2c.quote.error"));
		}
		long etdTime = System.currentTimeMillis();
		logger.debug( " SaveTravelDetails method ends::::"  + ( etdTime - stdTime )  );
		logger.info("Exiting WebservicesTravelController.saveTravelDetails method.");

	}

	/**
	 * @param travelInsuranceVO
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	//@RequestMapping("**/getRevisedPremium")
	public void getRevisedPremium(
			@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response) {

		
		nullRemove(travelInsuranceVO);
		List<TravelPackageVO> packagesToBeDeleted = new ArrayList<TravelPackageVO>();

		boolean isPopulateOperation = Boolean.valueOf(request
				.getParameter("populateOperation"));
		travelInsuranceVO.setPopulateOperation(isPopulateOperation);

		DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();
		Object[] inpObjects = { travelInsuranceVO, isPopulateOperation };
		dataHolder.setData(inpObjects);

		/* Set SumInsured value for check boxes. */
		TravelInsuranceHandler.preProcess(travelInsuranceVO,
				packagesToBeDeleted);
		if (!Utils.isEmpty(travelInsuranceVO)) {

			TravelRiskValidator travelRiskValidator = (TravelRiskValidator) ApplicationContextUtils
					.getBean("travelRiskValidator");
			travelRiskValidator.validate(travelInsuranceVO, bindingResult);

			/*
			 * Since rating service accepts only one package, delete unselected
			 * packages from travelInsuranceVO.
			 */
			TravelInsuranceHandler.prepareForRatingCall(travelInsuranceVO,
					packagesToBeDeleted);

			UserProfile userProfile = (UserProfile) request.getSession(false)
					.getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
			travelInsuranceVO.getCommonVO().setLoggedInUser(userProfile);

			/* Call rating and set the new premium in response. */
			populatePremium(travelInsuranceVO, response, bindingResult);

		}
	}

	//@RequestMapping(value = "**/retryTravelPayment", method = RequestMethod.POST)
	public ModelAndView retryTravelPayment(
			@ModelAttribute("commonVO") CommonVO commonVO,
			HttpServletRequest request, HttpSession session) {

		TravelInsuranceVO travelInsuranceVO = new TravelInsuranceVO();
		travelInsuranceVO.setCommonVO(commonVO);
		TravelInsuranceHandler travelInsHandler = new TravelInsuranceHandler();
		TravelInsuranceVO travelVO = travelInsHandler
				.populateTravelInsForSearch(travelInsuranceVO, request);
		// PolicyDataVO policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks(
		// com.Constant.CONST_TRAVEL_PACKAGE_PREMIUM, travelVO );
		PolicyDataVO policyDataVO = TravelInsuranceHandler
				.loadDataForPayment(travelVO);
		logger.info("Retry payment by calling Payment Gateway started");
		return commonCtrl.makePayment(request, policyDataVO);
	}

	private void populatePremium(TravelInsuranceVO travelInsuranceVO,
			HttpServletResponse response, BindingResult bindingResult) {

		if (!bindingResult.hasErrors()) {
			/* Call rating to populate premium with new covers. */
			travelInsuranceVO = (TravelInsuranceVO) TaskExecutor.executeTasks(
					com.Constant.CONST_TRAVEL_PACKAGE_PREMIUM, travelInsuranceVO);

			List<TravelPackageVO> travelPackageList = travelInsuranceVO
					.getTravelPackageList();
			TravelPackageVO travelPackageVO = null;
			String premiumDetails = null;

			if (!Utils.isEmpty(travelPackageList)
					/*&& travelPackageList.size() > -1*/) {				/* commented -1 condition check as isEmpty check was covering the empty condition check - sonar violation fix */
				for (TravelPackageVO packageVO : travelPackageList) {
					if (packageVO.getIsSelected()) {
						travelPackageVO = packageVO;
					}
				}
			}

			if (!Utils.isEmpty(travelPackageVO)) {
				premiumDetails = travelPackageVO.getTariffCode()
						+ "~"
						+ Currency.getFormattedCurrency(
								travelPackageVO.getPremiumAmt(),
								travelInsuranceVO.getCommonVO().getLob()
										.toString());
			}

			if (!Utils.isEmpty(premiumDetails)) {
				response.setHeader("premiumDetails", premiumDetails);
			}
		} else {
			StringBuffer errorMessage = new StringBuffer();
			for (ObjectError error : bindingResult.getAllErrors()) {
				errorMessage.append(error.getDefaultMessage());
				errorMessage.append("<br />");
			}
			response.setHeader("errorMessages", errorMessage.toString());
		}

	}

	/**
	 * Method for Go Back to step 1. Loads all the general info details.
	 * 
	 * @param travelInsuranceVO
	 * @param bindingResult
	 * @param request
	 * @param session
	 * @return ModelAndView
	 */
	//@RequestMapping("**/loadGeneralInfoPage")
	public ModelAndView loadGeneralInfoPage(
			@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request,
			HttpSession session) {

		ModelAndView modelAndView = new ModelAndView(com.Constant.CONST_TRAVELRISKPAGE,
				com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);
		//Added for Oman location check
		LoginLocation location = (LoginLocation) Utils.getBean(com.Constant.CONST_LOCATION);
		String deployedLocation = location.getLocation();
		try {
			travelInsuranceVO = TravelInsuranceHandler
					.loadGeneralInfoDets(travelInsuranceVO);
			String partnername = request.getParameter(com.Constant.CONST_PARTNERNAME);
			request.setAttribute(com.Constant.CONST_PARTNERNAMECSS, partnername); // For Css
			setPartnerMgmntDetails(request, travelInsuranceVO, partnername);
			// Added by Vishwa to NEXUS PROMO for Advenet ID 128926
			String partner = DAOUtils.fetchPartnerInfo(partnername);
			logger.debug("3. Partner info for loadTravelGenralInfo() of WebservicesTravelController Class  "
					+ partner);
			request.setAttribute(com.Constant.CONST_PARTNER, partner);

			request.setAttribute(com.Constant.CONST_TRAVELERDETAILS, AppUtils
					.createJSONForTravelerDetails(travelInsuranceVO
							.getTravelDetailsVO().getTravelerDetailsList(),
							com.Constant.CONST_TRAVELER_DETAILS_JSON_RETRIVE_QUOTE));
			request.setAttribute(com.Constant.CONST_ISLOADOPERATION, "true");
			modelAndView.setViewName(com.Constant.CONST_TRAVELGENERALINFO);
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			throw new SystemException(e.getCause(), e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE))
				throw new SystemException(e.getCause(),
					Utils.getAppErrorMessage("pasb2c.quote.oman.error"));
			else
				throw new SystemException(e.getCause(),
					Utils.getAppErrorMessage("pasb2c.quote.error"));
		}
		return modelAndView;
	}

	/**
	 * Method to trigger an email in-case of user clicks on request to call back
	 * 
	 * @param travelInsuranceVO
	 * @param bindingResult
	 * @param request
	 * @param session
	 * @return
	 */
	//@RequestMapping("**/requestTravelCallBack")
	public ModelAndView requestCallBack(
			@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView modelAndView = null;
		if (!Utils.isEmpty(request.getParameter("call_back_number"))) {
			// CommonHandler.populateCallBackEmail( request.getParameter(
			// "call_back_number" ) );
			String partnername = request.getParameter(com.Constant.CONST_PARTNERNAME);
			if (!(Utils.isEmpty(partnername))) {
				if (Utils.isEmpty(travelInsuranceVO.getCommonVO())) {
					travelInsuranceVO.setCommonVO(new CommonVO());
				}
				travelInsuranceVO.getCommonVO().setIsQuote(Boolean.TRUE);
				travelInsuranceVO.getCommonVO().setLob(LOB.TRAVEL);
				setPartnerMgmntDetails(request, travelInsuranceVO, partnername);
			}
			CommonHandler.populateAndTriggerEmail(travelInsuranceVO, null,
					B2CEmailTriggers.REQUEST_CALL_BACK,
					request.getParameter("call_back_number"));
		}
		if (!Utils.isEmpty(request.getParameter(com.Constant.CONST_SCREEN))
				&& request.getParameter(com.Constant.CONST_SCREEN).toString()
						.equalsIgnoreCase("risk")) {
			TravelInsuranceHandler.preProcess(travelInsuranceVO,
					new ArrayList<TravelPackageVO>());
			modelAndView = new ModelAndView(com.Constant.CONST_TRAVELRISKPAGE,
					com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);
		} else if (!Utils.isEmpty(request.getParameter(com.Constant.CONST_SCREEN))
				&& request.getParameter(com.Constant.CONST_SCREEN).toString()
						.equalsIgnoreCase("general")) {
			modelAndView = new ModelAndView(com.Constant.CONST_TRAVELGENERALINFO,
					com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);
		} else {
			// modelAndView = new ModelAndView( com.Constant.CONST_TRAVELRENEWAL,
			// com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO );
			return fetchTravelInfoForRenewal(travelInsuranceVO, bindingResult,
					request, response, session);
		}
		return modelAndView;
	}

	/*
	 * This method converts renewal quote into policy
	 */
	//@RequestMapping(value = "/TravelRenewalStep2.do")
	public ModelAndView renewPolicy(
			@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		logger.debug("Going to save the travel renewal insurance related details on the screen before converting to policy.");
		ModelAndView modelAndView = null;
		String internalExec = null;
		if (!Utils.isEmpty(request.getParameter(com.Constant.CONST_INTERNALEXEC))) {
			internalExec = request.getParameter(com.Constant.CONST_INTERNALEXEC);
		}
		modelAndView = new ModelAndView(com.Constant.CONST_TRAVELRENEWAL, com.Constant.CONST_TRAVELINSURANCEVO,
				travelInsuranceVO);
		//Added for Oman location check
		LoginLocation location = (LoginLocation) Utils.getBean(com.Constant.CONST_LOCATION);
		String deployedLocation = location.getLocation();
		if (!Utils.isEmpty(travelInsuranceVO)) {
			UserProfile userProfile = (UserProfile) request.getSession(false)
					.getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
			travelInsuranceVO.getCommonVO().setLoggedInUser(userProfile);
			travelInsuranceVO.setLoggedInUser(userProfile);
			travelInsuranceVO = TravelInsuranceHandler
					.populateTravelInsuranceForSave(travelInsuranceVO, request);
			
			//VAT Dileep
			travelInsuranceVO = TravelInsuranceHandler
					.populateVatCodeAndVatTax(travelInsuranceVO, request);

			if (!Utils.isEmpty(internalExec)
					&& !Utils.isEmpty(travelInsuranceVO
							.getAuthenticationInfoVO())
					&& !Utils.isEmpty(travelInsuranceVO.getGeneralInfo())) {
				travelInsuranceVO.getAuthenticationInfoVO().setIntAccExecCode(
						Integer.parseInt(internalExec));
				travelInsuranceVO.getGeneralInfo().setIntAccExecCode(
						Integer.parseInt(internalExec));
			}

			TravelRenewalValidator renewalValidator = (TravelRenewalValidator) ApplicationContextUtils
					.getBean("travelRenewalValidator");
			renewalValidator.validate(travelInsuranceVO, bindingResult);
			/*
			 * List<TravelPackageVO> packageVOList =
			 * travelInsuranceVO.getTravelPackageList();
			 * 
			 * if(packageVOList.size() == 1) {
			 * packageVOList.get(0).setIsSelected(true); }
			 */

			if (bindingResult.hasErrors()) {
				/*
				 * modelAndView.setViewName( com.Constant.CONST_TRAVELRENEWAL );
				 * modelAndView.addObject( bindingResult ); return modelAndView;
				 */
				return fetchTravelInfoForRenewal(travelInsuranceVO,
						bindingResult, request, response, session);
			}
			try {
				logger.debug("Going to save General info related details efore converting to policy");

				if (!Utils.isEmpty(travelInsuranceVO)) {
					travelInsuranceVO = TravelInsuranceHandler
							.saveTravelGeneralInfo(travelInsuranceVO, null);
				} else {
					throw new SystemException(new Throwable(),
							"Unexpected exception ocuured. Please contact administrator");
				}
				request.setAttribute(com.Constant.CONST_TRAVELERDETAILS, AppUtils
						.createJSONForTravelerDetails(travelInsuranceVO
								.getTravelDetailsVO().getTravelerDetailsList(),
								com.Constant.CONST_TRAVELER_DETAILS_JSON_RETRIVE_QUOTE));
				request.setAttribute(com.Constant.CONST_ISLOADOPERATION, "true");

				ReferralListVO referrals = !Utils.isEmpty(travelInsuranceVO
						.getReferralVOList()) ? travelInsuranceVO
						.getReferralVOList() : null;

				// saveTravelDetails( travelInsuranceVO, bindingResult, request,
				// true );
				List<TravelPackageVO> packagesToBeDeleted = new ArrayList<TravelPackageVO>();
				TravelInsuranceHandler.preProcess(travelInsuranceVO,
						packagesToBeDeleted);
				travelInsuranceVO.setPopulateOperation(false);
				TravelInsuranceHandler.prepareForRatingCall(travelInsuranceVO,
						packagesToBeDeleted);
				
				//VAT - Dileep
				TravelInsuranceHandler.populatePremium(travelInsuranceVO,request);
				
				travelInsuranceVO = TravelInsuranceHandler
						.saveTravelRisk(travelInsuranceVO);

				if (!Utils.isEmpty(travelInsuranceVO.getReferralVOList())) {
					referrals = new ReferralListVO();
					referrals.getReferrals().addAll(
							travelInsuranceVO.getReferralVOList()
									.getReferrals());
				}
				travelInsuranceVO.setReferralVOList(referrals);

				/*
				 * If there is a referral then populate the referral message and
				 * redirect to same page
				 */
				if (!Utils.isEmpty(travelInsuranceVO.getReferralVOList())) {
					request.setAttribute(com.Constant.CONST_TRAVELERDETAILS, AppUtils
							.createJSONForTravelerDetails(travelInsuranceVO
									.getTravelDetailsVO()
									.getTravelerDetailsList(),
									com.Constant.CONST_TRAVELER_DETAILS_JSON_RETRIVE_QUOTE));
					request.setAttribute(com.Constant.CONST_ISLOADOPERATION, "true");
					request.setAttribute(com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);
					AppUtils.setQuoteValidDate(travelInsuranceVO, request);
					AppUtils.setScaleForLOB(travelInsuranceVO.getCommonVO()
							.getLob());
					travelInsuranceVO.getCommonVO().setStatus(
							SvcConstants.POL_STATUS_REFERRED);
					return new ModelAndView(com.Constant.CONST_TRAVELRENEWAL,
							com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);
				}
				logger.debug("*******************Save general info and Risk data operation for renewal Travel completed**********************");
				//140968 Prm 0 issue 
				double prmAmount = DAOUtils.fetchPolPrmForQuote(travelInsuranceVO.getCommonVO().getQuoteNo(),travelInsuranceVO.getCommonVO().getEndtId());
				logger.info("Prm Amt after save to db  and fecthing for furthur ......"+prmAmount);
				
				if(!bindingResult.hasErrors() && travelInsuranceVO.getCommonVO().getIsQuote()&& prmAmount==0.0) {
					modelAndView.setViewName(com.Constant.CONST_TRAVELRENEWAL);
					modelAndView.addObject(com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);
						
					if (!Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
							.getSourceOfBus()) && !Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
									.getSourceOfBus().getCallCentreNo())
							&& ! Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
									.getSourceOfBus().getPartnerId())) {
						CommonHandler.renderErrorMessages(
								bindingResult,
								Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_TRAVEL_ERROR)
										.replace(
												com.Constant.CONST_CALL_CENTER,
												travelInsuranceVO.getGeneralInfo()
														.getSourceOfBus()
														.getCallCentreNo()));
					}
				
						else{
							CommonHandler.renderErrorMessages(
									bindingResult,
									Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_TRAVEL_ERROR)
											.replace(
													com.Constant.CONST_CALL_CENTER,"800 RSA (772)"));
						
						}

						
					
					
					return modelAndView;
				}

		
				if (!bindingResult.hasErrors()) {
					PolicyDataVO policyDataVO = TravelInsuranceHandler
							.loadDataForPayment(travelInsuranceVO);
					logger.info("Make payment by calling Payment Gateway started");
					return commonCtrl.makePayment(request, policyDataVO); // TODO
																			// need
																			// to
																			// enable
																			// this
																			// change
				}
			} catch (SystemException systemException) {
				CommonHandler.renderErrorMessages(bindingResult,
						systemException.getMessage());
			} catch (JSONException e) {
				logger.error(e.getMessage(), e);
				CommonHandler
						.renderErrorMessages(bindingResult, e.getMessage());
			} catch (Exception e) {
				if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE))
					CommonHandler.renderErrorMessages(bindingResult,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_OMAN_ERROR));
				else
					CommonHandler.renderErrorMessages(bindingResult,
							Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_ERROR));
				logger.error(e.getMessage(), e);
			}
			return modelAndView;
		}

		return modelAndView;
	}

	/*
	 * This method load the travel renewal page after clicking on the 'click
	 * here' link in renewal notice Email
	 */
	//@RequestMapping("/TravelRenewalStep1.do")
	public ModelAndView fetchTravelInfoForRenewal(
			@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
					
		TravelInsuranceVO insuranceVO = (TravelInsuranceVO) Utils
				.getBean(com.Constant.CONST_VO_TRAVEL);
		CommonVO commonVO = null;

		TravelInsuranceHandler travelInsHandler = new TravelInsuranceHandler();
		String renQuote = null;
		
		setLocation();
		
		//Added for Oman location check
		LoginLocation location = (LoginLocation) Utils.getBean(com.Constant.CONST_LOCATION);
		String deployedLocation = location.getLocation();
		
		try {
			
			/*
			 * D2C Deployed Location set to session : BEGIN
			 */
			session.setAttribute(com.Constant.CONST_DEPLOYEDLOC, getLocation().getLocation());
			/*
			 * D2C Deployed Location set to session : END
			 */

			//##START - Added by Dinesh for CR-130750 Royalty feature 
			if(!Utils.isEmpty(request.getParameter(AppConstants.RENEWAL_QUOTE_NUM_REQ_PARAM))){
				 renQuote = request.getParameter(AppConstants.RENEWAL_QUOTE_NUM_REQ_PARAM);
				if(!Utils.isEmpty(renQuote) && !StringUtils.isNumeric(renQuote)){
						renQuote = AppUtils.encryptAndDecryptData( renQuote, Boolean.FALSE );
				}	
			}//#END

			insuranceVO = travelInsHandler.populateCommonVOForenewal(
					travelInsuranceVO, request);
			// insuranceVO = travelInsHandler.populateTravelInsForSearch(
			// travelInsuranceVO, request );
			insuranceVO = (TravelInsuranceVO) TaskExecutor.executeTasks(
					com.Constant.CONST_TRAVEL_PACKAGE_PREMIUM, insuranceVO);

			if (!Utils.isEmpty(insuranceVO)) {
				request.setAttribute(com.Constant.CONST_TRAVELERDETAILS, AppUtils
						.createJSONForTravelerDetails(insuranceVO
								.getTravelDetailsVO().getTravelerDetailsList(),
								com.Constant.CONST_TRAVELER_DETAILS_JSON_RETRIVE_QUOTE));
				request.setAttribute(com.Constant.CONST_ISLOADOPERATION, "true");
				commonVO = travelInsuranceVO.getCommonVO();
				request.setAttribute(com.Constant.CONST_TRAVELINSURANCEVO, insuranceVO);
				AppUtils.setQuoteValidDate(insuranceVO, request);
				AppUtils.setScaleForLOB(commonVO.getLob());
			}
			
			String propertyKey = null;
			if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE)) {
				propertyKey = com.Constant.CONST_PASB2C_ASSISTANCE_OMAN;
			}else {
				propertyKey = com.Constant.CONST_PASB2C_ASSISTANCE;
			}
			if (!Utils.isEmpty(commonVO)
					&& AppConstants.CONVERTED_TO_POL_STATUS.equals(commonVO
							.getStatus())) {
				CommonHandler
						.renderErrorMessages(
								result,
								Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_POLICYQUOTE_RETRIEVED)
										+ commonVO.getPolicyNo().toString()
										+ ". "
										+ Utils.getAppErrorMessage(propertyKey));
			}
		} catch (SystemException systemException) {
			Errors errors = result;
			errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
					com.Constant.CONST_ERROR_END + systemException.getMessage());
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
			Errors errors = result;
			errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
					com.Constant.CONST_ERROR_END + e.getMessage());
		} catch (BusinessException be) {
			Errors errors = result;
			errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
					com.Constant.CONST_ERROR_END + be.getMessage());
			if (!Utils.isEmpty(request.getAttribute(com.Constant.CONST_RSA_DIRECT_ERROR))) {
				ModelAndView view = new ModelAndView();
				view.addObject(com.Constant.CONST_ERRORMSG, be.getMessage());
				view.setViewName("redirect:TravelStep1.do");
				return view;
			}
		} catch (Exception e) {
			logger.error("Exception while fetching quote details_2", e);
			Errors errors = result;
			if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE))
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
					Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_OMAN_ERROR));
			else
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_ERROR));
		}
		setFieldsForSiteCatalyst(request, travelInsuranceVO);
		//##START - Added by Dinesh for CR-130750 Royalty feature 
		if(!Utils.isEmpty(renQuote) && StringUtils.isNumeric(renQuote) && !Utils.isEmpty(request.getParameter(AppConstants.EMAIL_REQ_PARAM)) ){
			   if (!Utils.isEmpty(request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM))){
				   logger.info("Going to set PROMCODE value: "+request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM));
				   travelInsuranceVO.getGeneralInfo().getSourceOfBus().setPromoCode(request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM));
				}else{
					logger.info("Going to set PROMCODE value Empty");
					travelInsuranceVO.getGeneralInfo().getSourceOfBus().setPromoCode("");
				}
		}//##END	
		return new ModelAndView(com.Constant.CONST_TRAVELRENEWAL, com.Constant.CONST_TRAVELINSURANCEVO,
				insuranceVO);
	}

	// This method executes after clicking on Save for later in travelRenewal
	// Page
	/**
	 * @param travelInsuranceVO
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	//@RequestMapping("/TravelRenewalSave.do")
	public ModelAndView saveRenewalTravel(
			@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsVO,
			BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		logger.debug("Going to save the travel renewal insurance related details entered on the screen.");
		ModelAndView modelAndView = null;
		boolean sendEmail = false;
		boolean isPrintCase = false;
		String internalExec = null;
		if (!Utils.isEmpty(request.getParameter("sendMail"))) {
			isPrintCase = true;
		}
		if (!Utils.isEmpty(request.getParameter(com.Constant.CONST_INTERNALEXEC))) {
			internalExec = request.getParameter(com.Constant.CONST_INTERNALEXEC);
		}

		modelAndView = new ModelAndView(com.Constant.CONST_TRAVELRENEWAL, com.Constant.CONST_TRAVELINSURANCEVO,
				travelInsVO);
		//Added for Oman location check
		LoginLocation location = (LoginLocation) Utils.getBean(com.Constant.CONST_LOCATION);
		String deployedLocation = location.getLocation();
		
		if (!Utils.isEmpty(travelInsVO)) {
			UserProfile userProfile = (UserProfile) request.getSession(false)
					.getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
			travelInsVO.getCommonVO().setLoggedInUser(userProfile);
			travelInsVO.setLoggedInUser(userProfile);
			travelInsVO = TravelInsuranceHandler
					.populateTravelInsuranceForSave(travelInsVO, request);
			
			travelInsVO = TravelInsuranceHandler
					.populateVatCodeAndVatTax(travelInsVO, request);
			TravelRenewalValidator renewalValidator = (TravelRenewalValidator) ApplicationContextUtils
					.getBean("travelRenewalValidator");

			if (!Utils.isEmpty(internalExec)
					&& !Utils.isEmpty(travelInsVO.getAuthenticationInfoVO())
					&& !Utils.isEmpty(travelInsVO.getGeneralInfo())) {
				travelInsVO.getAuthenticationInfoVO().setIntAccExecCode(
						Integer.parseInt(internalExec));
				travelInsVO.getGeneralInfo().setIntAccExecCode(
						Integer.parseInt(internalExec));
			}
			renewalValidator.validate(travelInsVO, bindingResult);

			if (bindingResult.hasErrors()) {
				return fetchTravelInfoForRenewal(travelInsVO, bindingResult,
						request, response, session);
			}
			try {
				logger.debug("Going to save the travel insurance related details entered on the screen.");

				if (!Utils.isEmpty(travelInsVO)) {
					travelInsVO = TravelInsuranceHandler.saveTravelGeneralInfo(
							travelInsVO, null);
				} else {
					throw new SystemException(new Throwable(),
							"Unexpected exception ocuured. Please contact administrator");
				}

				request.setAttribute(com.Constant.CONST_TRAVELERDETAILS, AppUtils
						.createJSONForTravelerDetails(travelInsVO
								.getTravelDetailsVO().getTravelerDetailsList(),
								com.Constant.CONST_TRAVELER_DETAILS_JSON_RETRIVE_QUOTE));
				request.setAttribute(com.Constant.CONST_ISLOADOPERATION, "true");

				ReferralListVO referrals = !Utils.isEmpty(travelInsVO
						.getReferralVOList()) ? travelInsVO.getReferralVOList()
						: null;

				if (!Utils.isEmpty(travelInsVO.getReferralVOList())) {
					sendEmail = true;
				}

				// saveTravelDetails( travelInsVO, bindingResult, request,
				// sendEmail );
				List<TravelPackageVO> packagesToBeDeleted = new ArrayList<TravelPackageVO>();
				TravelInsuranceHandler.preProcess(travelInsVO,
						packagesToBeDeleted);
				travelInsVO.setPopulateOperation(false);
				TravelInsuranceHandler.prepareForRatingCall(travelInsVO,
						packagesToBeDeleted);
				
				//VAT - Dileep
				TravelInsuranceHandler.populatePremium(travelInsVO,request);
				
				travelInsVO = TravelInsuranceHandler
						.saveTravelRisk(travelInsVO);

				if (!Utils.isEmpty(travelInsVO.getReferralVOList())) {
					referrals = new ReferralListVO();
					referrals.getReferrals().addAll(
							travelInsVO.getReferralVOList().getReferrals());
				}
				travelInsVO.setReferralVOList(referrals);

				/*
				 * If there is a referral then populate the referral message and
				 * redirect to same page
				 */
				if (!Utils.isEmpty(travelInsVO.getReferralVOList())) {
					request.setAttribute(com.Constant.CONST_TRAVELERDETAILS, AppUtils
							.createJSONForTravelerDetails(travelInsVO
									.getTravelDetailsVO()
									.getTravelerDetailsList(),
									com.Constant.CONST_TRAVELER_DETAILS_JSON_RETRIVE_QUOTE));
					request.setAttribute(com.Constant.CONST_ISLOADOPERATION, "true");
					request.setAttribute(com.Constant.CONST_TRAVELINSURANCEVO, travelInsVO);
					AppUtils.setQuoteValidDate(travelInsVO, request);
					AppUtils.setScaleForLOB(travelInsVO.getCommonVO().getLob());
					travelInsVO.getCommonVO().setStatus(
							SvcConstants.POL_STATUS_REFERRED);
					return new ModelAndView(com.Constant.CONST_TRAVELRENEWAL,
							com.Constant.CONST_TRAVELINSURANCEVO, travelInsVO);
				} else if (!isPrintCase) {
					// Trigger E-mail in-case of save for later start
					TravelInsuranceHandler.populatePremium(travelInsVO,request); // To
																			// populate
																			// the
																			// premium
																			// in
																			// PremiumVO
					// CommonHandler.populateAndTriggerEmail(travelInsVO,
					// request.getRequestURL().toString(),
					// B2CEmailTriggers.TRAVEL_SAVE_FOR_LATER, null);
					// Trigger E-mail in-case of save for later end
				}

				AppUtils.setQuoteValidDate(travelInsVO, request);
				AppUtils.setScaleForLOB(travelInsVO.getCommonVO().getLob());
				logger.debug("*******************Save for later operation for renewal Travel completed**********************");
				if (!bindingResult.hasErrors() && !isPrintCase) {
					request.setAttribute("quoteIssued", true);
				} else if (isPrintCase) {
					request.setAttribute("isPrintCase", true);
				}

			} catch (SystemException systemException) {
				CommonHandler.renderErrorMessages(bindingResult,
						systemException.getMessage());
			} catch (JSONException e) {
				logger.error(e.getMessage(), e);
				CommonHandler
						.renderErrorMessages(bindingResult, e.getMessage());
			} catch (Exception e) {
				if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE))
					CommonHandler.renderErrorMessages(bindingResult,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_OMAN_ERROR));
				else
					CommonHandler.renderErrorMessages(bindingResult,
							Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_ERROR));
				logger.error(e.getMessage(), e);
			}
		}
		return modelAndView;
	}

	private void setDefaultDetails(HttpServletRequest request,
			TravelInsuranceVO travelInsuranceVO) {

		if (Utils.isEmpty(travelInsuranceVO.getCommonVO())) {
			travelInsuranceVO.setCommonVO(new CommonVO());
		}
		travelInsuranceVO.getCommonVO().setIsQuote(Boolean.TRUE);
		travelInsuranceVO.getCommonVO().setLob(LOB.TRAVEL);
		if (Utils.isEmpty(travelInsuranceVO.getScheme())) {
			travelInsuranceVO.setScheme(new SchemeVO());
		}
		if (!(Utils.isEmpty(request.getParameter(com.Constant.CONST_PARTNERNAME)))) {/*
																	 * if
																	 * (Utils.
																	 * isEmpty(
																	 * travelInsuranceVO
																	 * .
																	 * getGeneralInfo
																	 * ())) {
																	 * GeneralInfoVO
																	 * generalInfo
																	 * = new
																	 * GeneralInfoVO
																	 * ();
																	 * travelInsuranceVO
																	 * .
																	 * setGeneralInfo
																	 * (
																	 * generalInfo
																	 * ); } if
																	 * (Utils
																	 * .isEmpty(
																	 * travelInsuranceVO
																	 * .
																	 * getGeneralInfo
																	 * ().
																	 * getSourceOfBus
																	 * ())) {
																	 * SourceOfBusinessVO
																	 * sourceOfBusinessVO
																	 * = new
																	 * SourceOfBusinessVO
																	 * ();
																	 * travelInsuranceVO
																	 * .
																	 * getGeneralInfo
																	 * ().
																	 * setSourceOfBus
																	 * (
																	 * sourceOfBusinessVO
																	 * );; }
																	 * travelInsuranceVO
																	 * .
																	 * getGeneralInfo
																	 * ().
																	 * getSourceOfBus
																	 * ().
																	 * setPartnerName
																	 * (request.
																	 * getParameter
																	 * (
																	 * com.Constant.CONST_PARTNERNAME
																	 * )
																	 * .toString
																	 * ());
																	 * travelInsuranceVO
																	 * .
																	 * getGeneralInfo
																	 * ().
																	 * getSourceOfBus
																	 * ().
																	 * setPartnerId
																	 * (request.
																	 * getParameter
																	 * (
																	 * "partnerId"
																	 * )
																	 * .toString
																	 * ());
																	 * travelInsuranceVO
																	 * .
																	 * getGeneralInfo
																	 * ().
																	 * getSourceOfBus
																	 * ().
																	 * setCallCentreNo
																	 * (request.
																	 * getParameter
																	 * (
																	 * "partnerCallCenter"
																	 * )
																	 * .toString
																	 * ());
																	 */
			if (!Utils.isEmpty(request.getSession(false).getAttribute(
					AppConstants.SESSION_USER_PROFILE_VO))
					&& Utils.isEmpty(travelInsuranceVO.getCommonVO()
							.getLoggedInUser())) {
				travelInsuranceVO.getCommonVO().setLoggedInUser(
						(User) request.getSession(false).getAttribute(
								AppConstants.SESSION_USER_PROFILE_VO));
				travelInsuranceVO.setLoggedInUser((User) request.getSession(
						false).getAttribute(
						AppConstants.SESSION_USER_PROFILE_VO));
			}
			setPartnerMgmntDetails(request, travelInsuranceVO, request
					.getParameter(com.Constant.CONST_PARTNERNAME).toString());
		}
	}

	private void setPartnerMgmntDetails(HttpServletRequest request,
			TravelInsuranceVO travelInsuranceVO, String partnername) {
		
		logger.info("Entered WebservicesTravelController.setPartnerMgmntDetails method.");
		if (!(Utils.isEmpty(partnername))) {
			if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo())) {
				GeneralInfoVO generalInfo = new GeneralInfoVO();
				travelInsuranceVO.setGeneralInfo(generalInfo);
			}
			if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
					.getSourceOfBus())) {
				SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
				travelInsuranceVO.getGeneralInfo().setSourceOfBus(
						sourceOfBusinessVO);
				;
			}
			if (Utils.isEmpty(travelInsuranceVO.getScheme())) {
				travelInsuranceVO.setScheme(new SchemeVO());
			}
			travelInsuranceVO.getGeneralInfo().getSourceOfBus()
					.setPartnerName(partnername);
			travelInsuranceVO = (TravelInsuranceVO) TravelInsuranceHandler
					.loadPartnerMgmtDetails(travelInsuranceVO);
		}
		logger.info("Exiting WebservicesTravelController.setPartnerMgmntDetails method.");
	}
	
	/*
	 * Added Code for Travel Update Quote for Webservices: M1042506
	 */
	@RequestMapping(value = "**/UpdateTravelQuote", method = RequestMethod.POST)
	public @ResponseBody UpdateTravelQuoteResponse updateTravelQuote(@RequestBody UpdateTravelQuoteRequest updateTravelQuoteRequest,
			@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		logger.debug( "Update travel Quote Started ...");
		long updateTime = System.currentTimeMillis();
		logger.debug( "Update travel Quote Started starts::::"  + new Date(updateTime)   );
		UpdateTravelQuoteResponse updateTravelQuoteResponse = new UpdateTravelQuoteResponse();
		BaseRequestVOMapper requestVOMapper = new TravelUpdateQuoteRequestMapper();
		BaseResponseVOMapper responseVOMapper = new TravelUpdateQuoteResponseMapper();
		ValidationException validationException = new ValidationException();
		UpdateTravelQuoteValidator travelQuoteValidator = new UpdateTravelQuoteValidator();
		travelInsuranceVO.setAppFlow(Flow.EDIT_QUO);
		
		long valTime = System.currentTimeMillis();
		logger.debug( "Validation and request mapper starts::::"  + new Date(valTime)   );
		try {
			validationException = travelQuoteValidator.validate(updateTravelQuoteRequest);
			if (!Utils.isEmpty(validationException.getErrors())) {
				List<ValidationError> errors = new ArrayList<ValidationError>();
				for (ValidationError validationError : validationException.getErrors()) {
					ValidationError error = new ValidationError();
					error.setCode(validationError.getCode());
					error.setField(validationError.getField());
					error.setMessage(validationError.getMessage());
					errors.add(error);
				}
				updateTravelQuoteResponse.setErrors(errors);
				return updateTravelQuoteResponse;
			}
			
			requestVOMapper.mapRequestToVO(updateTravelQuoteRequest, travelInsuranceVO);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		long evalTime = System.currentTimeMillis();
		logger.debug( "Validation and request mapper ends::::"  + ( evalTime - valTime )  );
		
		long srTime = System.currentTimeMillis();
		logger.info( "SetRequestAttributes starts::::");
		setRequestAttributes(travelInsuranceVO,request);
		long erTime = System.currentTimeMillis();
		logger.info( "SetRequestAttributes ends::::"  + ( erTime - srTime )  );
		
		
		long scTime = System.currentTimeMillis();
		logger.debug( "Start Time populateCommonDetails::::");
		CommonVO commonVO = travelInsuranceVO.getCommonVO();
		CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( com.Constant.CONST_GECOMSVCBEAN ); 
		commonVO = (CommonVO) commonOpSvc.invokeMethod( "populateCommonDetails", travelInsuranceVO.getCommonVO() ); 
		travelInsuranceVO.setCommonVO(commonVO);
		long ecTime = System.currentTimeMillis();
		logger.debug( "PopulateCommonDetails ends::::"  + ( ecTime - scTime )  );
		
		long pcTime = System.currentTimeMillis();
		logger.debug( "PopulateTravelInsuranceForSave starts::::");
		travelInsuranceVO = TravelInsuranceHandler.populateTravelInsuranceForSave(travelInsuranceVO, request);
		long epTime = System.currentTimeMillis();
		logger.debug( "PopulateTravelInsuranceForSave ends::::"  + ( epTime - pcTime )  );
		
		//travelInsuranceVO = TravelInsuranceHandler.populateVatCodeAndVatTax(travelInsuranceVO, request);
		
		nullRemove(travelInsuranceVO);
		if(!Utils.isEmpty(travelInsuranceVO.getCommonVO().getVsd())) {
			travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList();
			for (TravelerDetailsVO travelerDetailsVO  : travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList()) {
				travelerDetailsVO.setVsd(travelInsuranceVO.getCommonVO().getVsd());
			}
		}
		boolean isPrintCase = false;
		String partnerName = "";
		//Added for Oman location check
		LoginLocation location = (LoginLocation) Utils.getBean(com.Constant.CONST_LOCATION);
		String deployedLocation = location.getLocation();
		try {
			if (!Utils.isEmpty(travelInsuranceVO)) {
				long stTime = System.currentTimeMillis();
				logger.debug( " TravelInsuranceHandler.saveTravelGeneralInfo starts::::");
				travelInsuranceVO = TravelInsuranceHandler.saveTravelGeneralInfo(travelInsuranceVO, null);
				long etTime = System.currentTimeMillis();
				logger.debug( " TravelInsuranceHandler.saveTravelGeneralInfo ends::::"  + ( etTime - stTime )  );
			} else {
				throw new SystemException(new Throwable(),
						"Unexpected exception ocuured. Please contact administrator");
			}

			boolean completePurchaseInd = false;
			if (!Utils.isEmpty(request.getParameter("sendMail"))) {
				isPrintCase = true;
			}
			if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE)) {
				String tariff = travelInsuranceVO.getScheme().getTariffCode().toString();
				for(TravelPackageVO travelPackageVO :travelInsuranceVO.getTravelPackageList()) {
					if(travelPackageVO.getTariffCode().equals(tariff)) {
						travelInsuranceVO.getPremiumVO().setGovtTax(travelPackageVO.getGovtTax());
					}
				}
			}

			String partnername = request.getHeader(com.Constant.CONST_PARTNERNAME);
			long spmtTime = System.currentTimeMillis();
			logger.debug( " SetPartnerMgmntDetails starts::::");
			setPartnerMgmntDetails(request, travelInsuranceVO, partnername);
			long epmtTime = System.currentTimeMillis();
			logger.debug( " SetPartnerMgmntDetails ends::::"  + ( epmtTime - spmtTime )  );
			
			long stdTime = System.currentTimeMillis();
			logger.debug( " SaveTravelDetails starts::::");
			saveTravelDetails(travelInsuranceVO, bindingResult, request,completePurchaseInd, isPrintCase,updateTravelQuoteRequest.getProposalForm());
			long etdTime = System.currentTimeMillis();
			logger.debug( " SaveTravelDetails ends::::"  + ( etdTime - stdTime )  );
			/*travelInsuranceVO = TravelInsuranceHandler
					.loadTravelRiskPage(travelInsuranceVO);*/
			/*VAT - added to update VAT premium (With rounded decimal point)  in T_TRN_PREMIUM_QUO table*/
			long suptTime = System.currentTimeMillis();
			logger.debug( " UpdateVATPremium starts::::");
			TravelInsuranceHandler.updateVATPremium(travelInsuranceVO);
			long euptTime = System.currentTimeMillis();
			logger.debug( " UpdateVATPremium ends::::"  + ( euptTime - suptTime )  );
			
			AppUtils.setScaleForLOB( travelInsuranceVO.getCommonVO().getLob() );
			travelInsuranceVO.setPolicyTerm(updateTravelQuoteRequest.getTransactionDetails().getPolicyTerm());
			/* For Referral Code */
			if( !Utils.isEmpty( travelInsuranceVO.getReferralVOList() ) ){
				ReferralListVO referralListVO = travelInsuranceVO.getReferralVOList();

				ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_MESSAGES);
				for (ReferralVO referralVO : referralListVO.getReferrals()) {
					referralVO.getReferralText();
					for(Entry<String, Map<String, String>> entry : referralVO.getRefDataTextField().entrySet())
			        {
					  	ValidationError error = new ValidationError();
					  	error.setField(entry.getKey());
					  	error.setCode(resourceBundle.getString(entry.getKey()));
			            Map<String, String> internalMap = entry.getValue();
			            for(Entry<String, String> internalEntry : internalMap.entrySet())
			            {
			                error.setMessage(internalEntry.getValue());
			            }
			            validationException.getErrors().add(error);
			        }	
				}	
				updateTravelQuoteResponse.setErrors(validationException.getErrors());
				
				responseVOMapper.mapVOToResponse(travelInsuranceVO, updateTravelQuoteResponse);
				webServiceAuditMapper.mapRequestToVO(updateTravelQuoteRequest, updateTravelQuoteResponse);
				return updateTravelQuoteResponse;
			}
			/* For Document Proposalform creation Code */
			if(!Utils.isEmpty(updateTravelQuoteRequest.getProposalForm()) && updateTravelQuoteRequest.getProposalForm()==true) {
				long startTime = System.currentTimeMillis();
				logger.debug( "Calling Document Creation:::_2"  + new Date(startTime)   );
				//CommonHandler.printProposalForm(travelInsuranceVO);
				byte[] fileContent = null;
				String file = CommonHandler.encodeToString(Utils.getSingleValueAppConfig(com.Constant.CONST_QUOTE_DOC_PROPOSAL_LOC) +travelInsuranceVO.getCommonVO().getQuoteNo() +com.Constant.CONST_QUOTE_PDF);
				fileContent = file.getBytes();
				long endTime = System.currentTimeMillis();
				logger.debug( " Document Creation Done_2"  + new Date(endTime)   );
				logger.debug( "Time taken for Document creation and converting to byte array::_2"  + ( endTime - startTime )  );
				updateTravelQuoteResponse.setDocument(fileContent);
				/*if(!Utils.isEmpty(updateTravelQuoteRequest.getQuoteConfirmationEmail()) && updateTravelQuoteRequest.getQuoteConfirmationEmail()==true) {
					CommonHandler.populateAndTriggerEmail(travelInsuranceVO, request.getContextPath(), B2CEmailTriggers.TRAVEL_SAVE_FOR_LATER, null);
				}	*/
			}
			
			long startTime = System.currentTimeMillis();
			logger.debug( "Calling webServiceAuditMapper starts::::"  + new Date(startTime)   );
			responseVOMapper.mapVOToResponse(travelInsuranceVO, updateTravelQuoteResponse);
			webServiceAuditMapper.mapRequestToVO(updateTravelQuoteRequest, updateTravelQuoteResponse);
			long endTime = System.currentTimeMillis();
			logger.debug( "Calling webServiceAuditMapper ends:::"  + ( endTime - startTime )  );
		} catch (SystemException systemException) {
			CommonHandler.renderErrorMessages(bindingResult,
					systemException.getMessage());
		} catch (Exception e) {
			if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
					.getSourceOfBus())
					|| Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
							.getSourceOfBus().getPartnerId())) {
				CommonHandler.renderErrorMessages(
						bindingResult,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_TRAVEL_ERROR)
								.replace(
										com.Constant.CONST_CALL_CENTER,
										travelInsuranceVO.getGeneralInfo()
												.getSourceOfBus()
												.getCallCentreNo()));

			} else {
				if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE))
					CommonHandler.renderErrorMessages(bindingResult,
							Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_OMAN_ERROR));
				else
					CommonHandler.renderErrorMessages(bindingResult,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_ERROR));
			}
			logger.error(e.getMessage(), e);
		}
		/*request.getSession(false).setAttribute(
				submitTravelRisk + "_travelInsuranceVO", travelInsuranceVO);
		request.getSession(false).setAttribute(
				submitTravelRisk + "_bindingResult", bindingResult);
		request.getSession(false).setAttribute(com.Constant.CONST_VALIDQUOTEDATE,
				request.getAttribute(com.Constant.CONST_VALIDQUOTEDATE));
		
		
		Boolean isTerrCruiseInductionDate = true;
		isTerrCruiseInductionDate = checkPolPreparedDt(travelInsuranceVO);
		request.setAttribute(com.Constant.CONST_ISTERRCRUISEINDUCTIONDATE, isTerrCruiseInductionDate);*/
		long updETime = System.currentTimeMillis();
		logger.debug( "Update travel Quote ends::::"  + ( updETime - updateTime )  );
		return updateTravelQuoteResponse;
	}
	
	private void setRequestAttributes(TravelInsuranceVO travelInsuranceVO,HttpServletRequest request) {
		BasicAuthenticationService authService = new BasicAuthenticationService();
		String authorization = authService.decodeText(request.getHeader("Authorization"));
		String[] credentials = authService.getUserIdAndPassword(authorization);
		int userId =Integer.parseInt(credentials[0]);
		System.out.println(credentials[0]);
		
		if (Utils.isEmpty(travelInsuranceVO.getCommonVO())) {
			travelInsuranceVO.setCommonVO(new CommonVO());
		}
		if(!Utils.isEmpty(request.getHeader(com.Constant.CONST_PARTNERID))) {
			travelInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerId(request.getHeader(com.Constant.CONST_PARTNERID));
			travelInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName(request.getHeader(com.Constant.CONST_PARTNERNAME));
		}else
			travelInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName("null");
		
		travelInsuranceVO.getGeneralInfo().setExtAccExecCode(userId);
		travelInsuranceVO.getGeneralInfo().getSourceOfBus().setDefaultAssignToUser(Long.valueOf(userId));
       
       if (Utils.isEmpty(request.getSession().getAttribute(
                    AppConstants.SESSION_USER_PROFILE_VO)) && !Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getExtAccExecCode())){
			UserProfile userProfile = UserProfileHandler
					.getUserProfileVo(travelInsuranceVO.getGeneralInfo().getExtAccExecCode());
			request.getSession().setAttribute(AppConstants.SESSION_USER_PROFILE_VO, userProfile);
			userProfile = (UserProfile) request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
			travelInsuranceVO.getCommonVO().setLoggedInUser(userProfile);
			travelInsuranceVO.setLoggedInUser(userProfile);
			userProfile.setRsaUser(new B2CRSAUserWrapper());
			travelInsuranceVO.getLoggedInUser().setUserId(Integer.toString(userId));
       }
       travelInsuranceVO.getCommonVO().setLoggedInUser((User) request.getSession().getAttribute(
                     AppConstants.SESSION_USER_PROFILE_VO));
       
       if(Utils.isEmpty( travelInsuranceVO.getCommonVO().getLoggedInUser() )) {
              UserProfile userProfile = new UserProfile();
              userProfile.setRsaUser(new B2CRSAUserWrapper());
              travelInsuranceVO.getCommonVO().setLoggedInUser(userProfile);
              travelInsuranceVO.getCommonVO().getLoggedInUser().setUserId(Integer.toString(userId));
       }
       
       if(	!Utils.isEmpty(travelInsuranceVO.getCommonVO()) && 
				( Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_REN_QUO_DOC_CODE ) ).equals( travelInsuranceVO.getCommonVO().getDocCode()) ))
		{
    	   travelInsuranceVO.getGeneralInfo().getInsured().setBusType( AppConstants.BUS_TYPE_RENEWAL );
		}
		else
		{
			travelInsuranceVO.getGeneralInfo().getInsured().setBusType( AppConstants.BUS_TYPE_NEW );
		}
       travelInsuranceVO.getCommonVO().setLob(LOB.TRAVEL);
       travelInsuranceVO.getCommonVO().setIsQuote(Boolean.TRUE);
       travelInsuranceVO.getCommonVO().setLocCode(AppConstants.B2C_DEFAULT_DEPLOYED_LOCATION);
       travelInsuranceVO.getCommonVO().setChannel(BusinessChannel.B2C); 
	}
	
	/**
	 * To retrieve travel general info page
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("**/RetrieveTravelQuotebyID")
	public @ResponseBody UpdateTravelQuoteResponse retrieveTravelQuoteByQuoteId(@RequestBody RetrieveQuoteByQuoteId retrieveQuoteByQuoteId,
			@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) throws ParseException {
		
		ModelAndView modelAndView = new ModelAndView(com.Constant.CONST_TRAVELGENERALINFO,
				com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);
		
		UpdateTravelQuoteResponse updateTravelQuoteResponse = new UpdateTravelQuoteResponse();
		ValidationException validationException = new ValidationException();
	    validationException = new RetriveQuoteByQuoteIdValidator().validate(retrieveQuoteByQuoteId);
	    ValidationError validationError = new ValidationError();
	    //Web-Services Validation starts.
	    if(validationException.getErrors() != null && !Utils.isEmpty(validationException.getErrors())) {
	    	updateTravelQuoteResponse.setErrors(validationException.getErrors());
	        return updateTravelQuoteResponse;
	    }
	    //Web-Services Validation ends.
		String partnername = "";
		String partner=null;
		String quoteNo = null;
		TravelInsuranceHandler travelInsHandler = new TravelInsuranceHandler();
		if(Utils.isEmpty(travelInsuranceVO.getCommonVO())){
			travelInsuranceVO.setCommonVO(new CommonVO());
		}
		if(Utils.isEmpty(travelInsuranceVO.getGeneralInfo())){
			travelInsuranceVO.setGeneralInfo(new GeneralInfoVO());
		}
		if(Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getSourceOfBus())){
			travelInsuranceVO.getGeneralInfo().setSourceOfBus(new SourceOfBusinessVO());
		}
		if(Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getInsured())){
			travelInsuranceVO.getGeneralInfo().setInsured(new InsuredVO());
		}
		if(Utils.isEmpty(travelInsuranceVO.getScheme())){
			travelInsuranceVO.setScheme(new SchemeVO());
		}
		
		setLocation();
		
		//Added for Oman location check
		LoginLocation location = (LoginLocation) Utils.getBean(com.Constant.CONST_LOCATION);
		String deployedLocation = location.getLocation();
		
	
		try {
			/*
			 * D2C Deployed Location set to session : BEGIN
			 */
			httpSession.setAttribute(com.Constant.CONST_DEPLOYEDLOC, getLocation().getLocation());
			/*
			 * D2C Deployed Location set to session : END
			 */
			travelInsuranceVO.getCommonVO().setIsQuote(true);
			travelInsuranceVO.getCommonVO().setLocCode(AppConstants.B2C_DEFAULT_DEPLOYED_LOCATION); 
			travelInsuranceVO.getCommonVO().setChannel(BusinessChannel.B2C);
			travelInsuranceVO.getCommonVO().setLob(LOB.TRAVEL);
			travelInsuranceVO.getCommonVO().setAppFlow(Flow.EDIT_QUO);
			travelInsuranceVO.getCommonVO().setVsd(travelInsuranceVO.getScheme().getEffDate());
			// Added by Vishwa to NEXUS PROMO for Advenet ID 128926
			/*partner = DAOUtils.fetchPartnerInfo(partnername);
			logger.debug("Partner info for fetchTravelGeneralInfo() of TravelController Class  "
					+ partner);
			request.setAttribute(com.Constant.CONST_PARTNER, partner);*/
			/********** Code Starts for Request*********/
			RetrieveQuoteByQuoteIDMapper retrieveQuoteByQuoteIDMapper = new RetrieveQuoteByQuoteIDMapper();
			//retrieveQuoteByQuoteIDMapper.initialize(travelInsuranceVO);
			retrieveQuoteByQuoteIDMapper.mapRequestToVO(retrieveQuoteByQuoteId, travelInsuranceVO);
			travelInsuranceVO = TravelInsuranceHandler
					.loadTravelRiskPage(travelInsuranceVO);
			TravelInsuranceHandler
			.populatePremium(travelInsuranceVO,request);
			/********** Code Ends for Request*********/
			if (Utils.isEmpty(request
					.getParameter(AppConstants.QUOTE_NUM_REQ_PARAM))
					&& Utils.isEmpty(request
							.getParameter(AppConstants.EMAIL_REQ_PARAM))) {
				TravelGIQuoteFetchValidator travelGIValidator = (TravelGIQuoteFetchValidator) ApplicationContextUtils
						.getBean("retrieveQuoteGITravel");
				travelGIValidator.validate(travelInsuranceVO, result);
				if (result.hasErrors()) {
					return updateTravelQuoteResponse;
				}
			}
			TravelInsuranceVO vo = (TravelInsuranceVO) Utils
					.getBean(com.Constant.CONST_VO_TRAVEL);
			CommonVO commonVO = null;

			TravelInsuranceVO travelInsuranceVORequest = CopyUtils
					.copySerializableObject(travelInsuranceVO);
			
			vo = travelInsHandler.populateTravelInsForSearch(travelInsuranceVO,
					request);
			
			travelInsuranceVORequest.setCommonVO(vo.getCommonVO());
			if (Utils.isEmpty(partnername)) {
				partnername = request.getRequestURI();
				if (!Utils.isEmpty(travelInsuranceVO.getReferralVOList())) {
					partnername = request.getRequestURI()
							.replace(com.Constant.CONST_QUOTEANDBUY_END, "")
							.replace("/TravelStep2.do", "")
							.replace("TravelStep2.do", "");
				} else {
					partnername = request.getRequestURI()
							.replace(com.Constant.CONST_QUOTEANDBUY_END, "")
							.replace("/fetchTravelGeneralInfo.do", "")
							.replace("fetchTravelGeneralInfo.do", "");
					// Added by Vishwa to NEXUS PROMO for Advenet ID 128926
				       partner = DAOUtils.fetchPartnerInfo(partnername);
				          logger.debug("ELSE Block  Partner info for fetchTravelGeneralInfo() of TravelController Class " + partner);
				          request.setAttribute(com.Constant.CONST_PARTNER, partner);
				}

			}
			request.setAttribute(com.Constant.CONST_PARTNERNAMECSS, partnername); // For Css
			setPartnerMgmntDetails(request, travelInsuranceVORequest,
					partnername);
			
			if (!Utils.isEmpty(travelInsuranceVORequest.getGeneralInfo())
					&& !Utils.isEmpty(travelInsuranceVORequest.getGeneralInfo()
							.getSourceOfBus())
					&& !Utils.isEmpty(travelInsuranceVORequest.getGeneralInfo()
							.getSourceOfBus().getPartnerName())) {
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setPartnerId(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus().getPartnerId());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setPartnerName(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus().getPartnerName());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setCallCentreNo(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus().getCallCentreNo());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setReplyToEmailId(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus().getReplyToEmailId());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setCcEmailId(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus().getCcEmailId());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setSourceOfBusiness(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus().getSourceOfBusiness());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setFromEmailID(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus().getFromEmailID());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setDefaultOnlineDiscount(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus()
										.getDefaultOnlineDiscount());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setDefaultAssignToUser(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus()
										.getDefaultAssignToUser());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setFaqUrl(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus().getFaqUrl());
				vo.getGeneralInfo()
						.getSourceOfBus()
						.setPolicyTermUrl(
								travelInsuranceVORequest.getGeneralInfo()
										.getSourceOfBus().getPolicyTermUrl());

				// In case of Mirror Site Referral flow UserProfile was
				// overridden. Updated to set it back from session.
				if (!Utils.isEmpty(travelInsuranceVO.getReferralVOList())
						&& !Utils.isEmpty(request.getSession(false)
								.getAttribute(
										AppConstants.SESSION_USER_PROFILE_VO))) {
					UserProfile userProfile = (UserProfile) request.getSession(
							false).getAttribute(
							AppConstants.SESSION_USER_PROFILE_VO);
					vo.getCommonVO().setLoggedInUser(userProfile);
					vo.setLoggedInUser(userProfile);
				}
			}
			/********** Code Starts for Response*********/
			travelInsuranceVORequest.setPolicyTerm(vo.getPolicyTerm());
			TravelUpdateQuoteResponseMapper travelUpdateQuoteResponseMapper = new TravelUpdateQuoteResponseMapper();
			travelUpdateQuoteResponseMapper.mapVOToResponse(travelInsuranceVORequest,updateTravelQuoteResponse);
			
			boolean isValid = retrieveQuoteByQuoteId.getProposalForm();
			if(isValid){
				long startTime = System.currentTimeMillis();
				logger.debug( "Calling Document Creation:::_3"  + new Date(startTime)   );
				CommonHandler.printProposalForm(travelInsuranceVO);
				byte[] fileContent = null;
				String file = CommonHandler.encodeToString(Utils.getSingleValueAppConfig(com.Constant.CONST_QUOTE_DOC_PROPOSAL_LOC) +travelInsuranceVO.getCommonVO().getQuoteNo() +com.Constant.CONST_QUOTE_PDF);
				fileContent = file.getBytes();
				long endTime = System.currentTimeMillis();
				logger.debug( " Document Creation Done_3"  + new Date(endTime)   );
				logger.debug( "Time taken for Document creation and converting to byte array::_3"  + ( endTime - startTime )  );
				updateTravelQuoteResponse.setDocument(fileContent);
			}
			webServiceAuditMapper.mapRequestToVO(retrieveQuoteByQuoteId, updateTravelQuoteResponse);
			
			/********** Code Ends for Response*********/
			
			// setPartnerMgmntDetails(request,vo);
			
			// For External travel quote search request - VAPT issue  - added the new if block and made the below if block to else if
			
			boolean isExtLink = false;
			
			if(request.getParameter("fm")!=null){
				
				logger.info("HHH fn check block");

				
				isExtLink = true;
			}
			
				if( (Utils.isEmpty(travelInsuranceVORequest.getGeneralInfo()))
					&& (isExtLink)	){

							if ((!isValidEmailExt(vo,request) || Utils.isEmpty(vo))
							|| !AppUtils.isValidDistributionChannel(vo,travelInsuranceVORequest.getGeneralInfo())
							|| !LOB.TRAVEL.equals(vo.getCommonVO().getLob())) {
								
								
								travelInsuranceVO.getCommonVO().setQuoteNo(null);
								travelInsuranceVO.setGeneralInfo(null);
								travelInsuranceVO.setScheme(null);
								travelInsuranceVO.setPolicyTerm(null);
								travelInsuranceVO.setTravelDetailsVO(null);
								travelInsuranceVO = new TravelInsuranceVO();
								setDefaultDetails(request, travelInsuranceVO);
								modelAndView.addObject(com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);
								CommonHandler.renderErrorMessages(result,
										AppConstants.INVALID_QUOTE);
								CommonHandler.renderErrorMessages(result,
										AppConstants.INVALID_EMAIL);
								
							}
						}
						
						else if ((AppUtils.inValidEmailId(vo, travelInsuranceVORequest) || Utils
					.isEmpty(vo))
					|| !AppUtils.isValidDistributionChannel(vo,
							travelInsuranceVORequest.getGeneralInfo())
					|| !LOB.TRAVEL.equals(vo.getCommonVO().getLob())) {
				travelInsuranceVO.getCommonVO().setQuoteNo(null);
				travelInsuranceVO.setGeneralInfo(null);
				travelInsuranceVO.setScheme(null);
				travelInsuranceVO.setPolicyTerm(null);
				travelInsuranceVO.setTravelDetailsVO(null);
				travelInsuranceVO = new TravelInsuranceVO();
				setDefaultDetails(request, travelInsuranceVO);
				travelInsHandler.populateUwqDetails(travelInsuranceVO,request,true);
				modelAndView.addObject(com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);
				CommonHandler.renderErrorMessages(result,
						AppConstants.INVALID_QUOTE);
				CommonHandler.renderErrorMessages(result,
						AppConstants.INVALID_EMAIL);
			}

			if (!Utils.isEmpty(vo) && !result.hasErrors()) {
				request.setAttribute(com.Constant.CONST_TRAVELERDETAILS, AppUtils
						.createJSONForTravelerDetails(vo.getTravelDetailsVO()
								.getTravelerDetailsList(),
								com.Constant.CONST_TRAVELER_DETAILS_JSON_RETRIVE_QUOTE));
				request.setAttribute(com.Constant.CONST_ISLOADOPERATION, "true");
				// ##START - Added by Dinesh for CR-130750 Royalty feature 
				quoteNo = request.getParameter(AppConstants.QUOTE_NUM_REQ_PARAM);
				if(!Utils.isEmpty(quoteNo) && StringUtils.isNumeric(quoteNo) && !Utils.isEmpty(request.getParameter(AppConstants.EMAIL_REQ_PARAM)) ){
					   if (!Utils.isEmpty(request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM))){
						   logger.info("Going to set PROM CODE VALUE: "+request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM));
						   vo.getGeneralInfo().getSourceOfBus().setPromoCode(request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM));
						}else{
							logger.info("Going to set PROM CODE VALUE: Empty as promocode ");
							vo.getGeneralInfo().getSourceOfBus().setPromoCode("");
						}
				}//## END
				
				// To retrieve the quote with mobile number for Oman D2C
				if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE) && !Utils.isEmpty(quoteNo) && StringUtils.isNumeric(quoteNo) && !Utils.isEmpty(request.getParameter(AppConstants.MOBILE_NUM_PARAM)) ){
					if(!vo.getGeneralInfo().getInsured().getMobileNo().equalsIgnoreCase(request.getParameter(AppConstants.MOBILE_NUM_PARAM))) {
						logger.info("No result found for the Quotation number and Mobile number.");
						travelInsuranceVO.getCommonVO().setQuoteNo(null);
						travelInsuranceVO.setGeneralInfo(null);
						travelInsuranceVO.setScheme(null);
						travelInsuranceVO.setPolicyTerm(null);
						travelInsuranceVO.setTravelDetailsVO(null);
						travelInsuranceVO = new TravelInsuranceVO();
						setDefaultDetails(request, travelInsuranceVO);
						request.setAttribute(com.Constant.CONST_TRAVELERDETAILS, null);
						modelAndView.addObject(com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);
						CommonHandler.renderErrorMessages(result,
								AppConstants.NO_RESULT_QUOTE_AND_MOBILE_NUMBER);
					}
				}
				request.setAttribute(com.Constant.CONST_TRAVELINSURANCEVO, vo);
					

				if (!Utils.isEmpty(vo)) {
					commonVO = travelInsuranceVO.getCommonVO();
				}
			}
			/* Set the referral message when the status is referred */
			if (AppUtils.isReferred(commonVO)) {
				ReferralUtils.setReferralMessage(result, vo);
			}

			/*
			 * CR:123969 Modified by Vishwa to stop the user from CTP for second
			 * time.
			 */
			if (!Utils.isEmpty(commonVO)
					&& AppConstants.CONVERTED_TO_POL_STATUS.equals(commonVO
							.getStatus())) {
				
				String propertyKey = null;
				if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE)) {
					propertyKey = com.Constant.CONST_PASB2C_ASSISTANCE_OMAN;
				}else {
					propertyKey = com.Constant.CONST_PASB2C_ASSISTANCE;
				}
				if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
						.getSourceOfBus().getPartnerId())) {
					CommonHandler
							.renderErrorMessages(
									result,
									Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_POLICYQUOTE_RETRIEVED)
											+ commonVO.getPolicyNo().toString()
											+ ". "
											+ Utils.getAppErrorMessage(propertyKey));
				} else {
					CommonHandler
							.renderErrorMessages(
									result,
									Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_POLICYQUOTE_RETRIEVED)
											+ commonVO.getPolicyNo().toString()
											+ ". "
											+ Utils.getAppErrorMessage(
													"pasb2c.partner.assistance")
													.replace(
															com.Constant.CONST_CALL_CENTER,
															travelInsuranceVO
																	.getGeneralInfo()
																	.getSourceOfBus()
																	.getCallCentreNo()));

				}
			}
			/*if (Short
					.valueOf(Utils.getSingleValueAppConfig(com.Constant.CONST_REN_QUO_DOC_CODE))
					.equals(vo.getCommonVO().getDocCode())) {
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath
						+ "/TravelRenewalStep1.do?renQuote="
						+ AppUtils.encryptAndDecryptData(vo.getCommonVO()
								.getQuoteNo().toString(), Boolean.TRUE));
			}*/

		} catch (SystemException systemException) {
			CommonHandler.renderErrorMessages(result,
					systemException.getMessage());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (BusinessException be) {
			// travelInsuranceVO.getCommonVO().setQuoteNo( null );
			// travelInsuranceVO.setGeneralInfo( null );
			// travelInsuranceVO.setCommonVO( null );
			travelInsuranceVO.setTravelDetailsVO(null);
			// travelInsuranceVO.setScheme( null );
			travelInsuranceVO = new TravelInsuranceVO();
			travelInsHandler.populateUwqDetails(travelInsuranceVO,request,true);
			setDefaultDetails(request, travelInsuranceVO);
			modelAndView.addObject(com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);
			CommonHandler.renderErrorMessages(result, be.getMessage());

		} catch (Exception e) {
			logger.error("Exception while fetching quote details_3", e);
			if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
					.getSourceOfBus())
					|| Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
							.getSourceOfBus().getPartnerId())) {
				CommonHandler.renderErrorMessages(
						result,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_TRAVEL_ERROR)
								.replace(
										com.Constant.CONST_CALL_CENTER,
										travelInsuranceVO.getGeneralInfo()
												.getSourceOfBus()
												.getCallCentreNo()));
				
			} else {
				if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE))
					CommonHandler.renderErrorMessages(result,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_OMAN_ERROR));
				else
					CommonHandler.renderErrorMessages(result,
							Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_ERROR));
			}
		}
		Boolean isTerrCruiseInductionDate = true;
		if(!Utils.isEmpty(travelInsuranceVO.getCommonVO().getQuoteNo()) || !Utils.isEmpty(travelInsuranceVO.getQuoteNo())){
			isTerrCruiseInductionDate = checkPolPreparedDt(travelInsuranceVO);
		httpSession.setAttribute(com.Constant.CONST_ISTERRCRUISEINDUCTIONDATE, isTerrCruiseInductionDate);
		}
		/*checkPolPreparedDt(travelInsuranceVO);
		request.setAttribute(com.Constant.CONST_ISTERRCRUISEINDUCTIONDATE, isTerrCruiseInductionDate);*/
		return updateTravelQuoteResponse;
	}
	
	// New function for VAPT issue for invalid email
	private boolean isValidEmailExt (PolicyDataVO storedObj, HttpServletRequest request){
		
		boolean isValidEmail = false;
		
		if((!Utils.isEmpty(storedObj)) && (!Utils.isEmpty(request)) ){
			
			if(!Utils.isEmpty(storedObj.getGeneralInfo())){
				
				if(!Utils.isEmpty(storedObj.getGeneralInfo().getInsured())){
					
					if((!Utils.isEmpty(storedObj.getGeneralInfo().getInsured().getEmailId())) && (!Utils.isEmpty(request.getParameter("em")))){
						
							if(storedObj.getGeneralInfo().getInsured().getEmailId().equalsIgnoreCase(request.getParameter("em"))){
								
									isValidEmail = true;
							}
						
					}
					
				}
			}
			
		}
		
		return isValidEmail;
	}

	@RequestMapping( value = "**/RetrieveTravelQuotebyPolNo",method = RequestMethod.POST)
    public @ResponseBody RetrieveTravelQuoteByPolicyResponse retrieveHomeQuoteByPolicyNo(@RequestBody RetrieveQuoteByPolicyRequest quoteByPolicyRequest,@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,BindingResult bindingResult, 
    		HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		RetrieveTravelQuoteByPolicyResponse quoteByPolicyResponse= new RetrieveTravelQuoteByPolicyResponse();
		BaseRequestVOMapper requestVOMapper = new RetrieveQuoteByPolicyRequestMapper();
		BaseResponseVOMapper responseVOMapper = new RetrieveQuoteByPolicyResponseMapper();
		TravelInsuranceHandler travelInsHandler = new TravelInsuranceHandler();
		logger.info("Load request for Home Insurance details started");
		
		//Setting from the JSON CreateHomeQuoteResponse object to homeInsuranceVO object:
		ValidationException validationException = new ValidationException();
	    validationException = new RetrieveQuoteByPolicyValidator().validate(quoteByPolicyRequest);
	    ValidationError validationError = new ValidationError();
	    //Web-Services Validation starts.
	    if(Utils.isEmpty(quoteByPolicyResponse.getErrors())) {
	    	quoteByPolicyResponse.setErrors(new ArrayList<ValidationError>());
	    }
	    if(validationException.getErrors() != null && !Utils.isEmpty(validationException.getErrors())) {
	    	quoteByPolicyResponse.setErrors(validationException.getErrors());
	         return quoteByPolicyResponse;
	    }
        //Web-Services Validation ends.
	    List<BigDecimal> quotes = null;
		try {
			requestVOMapper.mapRequestToVO(quoteByPolicyRequest, travelInsuranceVO);
			setRequestAttributes(travelInsuranceVO, request);
			quotes = AppUtils.getQuoteFromPolicy(travelInsuranceVO.getCommonVO());
			logger.info("The Quote number is :::"+quotes);
			for (BigDecimal quote : quotes) {
				
				if(! Utils.isEmpty( quotes )){
					travelInsuranceVO.getCommonVO().setQuoteNo(quote.longValue());
					travelInsuranceVO.getCommonVO().setEndtId(0l);
				}
				/*travelInsHandler.populateTravelInsForSearch(travelInsuranceVO,
						request);*/
				CommonVO commonVO = new CommonVO();
				commonVO.setQuoteNo(quote.longValue());
				commonVO.setIsQuote(true);
				commonVO = AppUtils.populateCommonVO(commonVO);
				travelInsuranceVO.setCommonVO(commonVO);
				travelInsuranceVO = TravelInsuranceHandler.loadGeneralInfoDets(travelInsuranceVO);
				travelInsuranceVO = (TravelInsuranceVO) TaskExecutor.executeTasks(
						com.Constant.CONST_TRAVEL_PACKAGE_PREMIUM, travelInsuranceVO);
				responseVOMapper.mapVOToResponse(travelInsuranceVO, quoteByPolicyResponse);
				if(!Utils.isEmpty(quoteByPolicyRequest.getProposalForm()) && quoteByPolicyRequest.getProposalForm()==true) {
					for (UpdateTravelQuoteResponse updateTravelQuoteResponse: quoteByPolicyResponse.getQuotes()) {

						if (updateTravelQuoteResponse.getQuotationNo() == quote.longValue()
								&& (updateTravelQuoteResponse.getQuoteStatus() == Integer
										.parseInt(Utils.getSingleValueAppConfig("QUOTE_ACTIVE"))
										|| updateTravelQuoteResponse.getQuoteStatus() == Integer
												.parseInt(Utils.getSingleValueAppConfig("CONV_TO_POL")))) {

							long startTime = System.currentTimeMillis();
							logger.debug("Calling Document Creation:::_4" + new Date(startTime));
							CommonHandler.printProposalForm(travelInsuranceVO);
							byte[] fileContent = null;
							String file = CommonHandler
									.encodeToString(Utils.getSingleValueAppConfig(com.Constant.CONST_QUOTE_DOC_PROPOSAL_LOC)
											+ travelInsuranceVO.getCommonVO().getQuoteNo() + com.Constant.CONST_QUOTE_PDF);
							fileContent = file.getBytes();
							long endTime = System.currentTimeMillis();
							logger.debug(" Document Creation Done_4" + new Date(endTime));
							logger.debug("Time taken for Document creation and converting to byte array::_4"
									+ (endTime - startTime));
							updateTravelQuoteResponse.setDocument(fileContent);
							break;
						}
					}
					
				}
			}
			
			
		} catch(BusinessException e) {
			
			validationError.setMessage(e.getMessage());
			quoteByPolicyResponse.getErrors().add(validationError);
			e.printStackTrace();
		}catch(SystemException e) {
//			ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_MESSAGES);
			validationError.setMessage(e.getMessage());
			quoteByPolicyResponse.getErrors().add(validationError);
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_MESSAGES);
			validationError.setMessage(resourceBundle.getString(com.Constant.CONST_CMN_SYSTEMERROR));
			quoteByPolicyResponse.getErrors().add(validationError);
			e.printStackTrace();
			
		}

		
		return quoteByPolicyResponse;
	}
	
	TravelInsuranceHandler travelInsHandler = new TravelInsuranceHandler();
	/*
	 * This method load the travel renewal page after clicking on the 'click
	 * here' link in renewal notice Email
	 */
	@RequestMapping("/RetrieveTravelPolicyByPolicyNo")
	public  @ResponseBody CreatePolicyResponse retrieveTravelPolicyByPolicyNo(@RequestBody RetrievePolicyByPolicyNo retrievePolicyByPolicyNo,
			@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
					
		TravelInsuranceVO insuranceVO = (TravelInsuranceVO) Utils
				.getBean(com.Constant.CONST_VO_TRAVEL);
		CommonVO commonVO = null;
		CreatePolicyResponse createPolicyResponse = new CreatePolicyResponse();
		String renQuote = null;
		
		setLocation();
		
		//Added for Oman location check
		LoginLocation location = (LoginLocation) Utils.getBean(com.Constant.CONST_LOCATION);
		String deployedLocation = location.getLocation();
		HeaderInfo headerInfo = new HeaderInfo();
		headerInfo.setHeaderInfo(WSAppUtils.setHeaderInfo(request));
		try {
			
			/*
			 * D2C Deployed Location set to session : BEGIN
			 */
			session.setAttribute(com.Constant.CONST_DEPLOYEDLOC, getLocation().getLocation());
			/*
			 * D2C Deployed Location set to session : END
			 */

			//##START - Added by Dinesh for CR-130750 Royalty feature 
			if(!Utils.isEmpty(request.getParameter(AppConstants.RENEWAL_QUOTE_NUM_REQ_PARAM))){
				 renQuote = request.getParameter(AppConstants.RENEWAL_QUOTE_NUM_REQ_PARAM);
				if(!Utils.isEmpty(renQuote) && !StringUtils.isNumeric(renQuote)){
						renQuote = AppUtils.encryptAndDecryptData( renQuote, Boolean.FALSE );
				}	
			}//#END
			/********** Code Starts for Request*********/
			/*RetrievePolicyByPolicyNoMapper retrievePolicyByPolicyNoMapper = new RetrievePolicyByPolicyNoMapper();
			retrievePolicyByPolicyNoMapper.mapRequestToVO(retrievePolicyByPolicyNo, travelInsuranceVO);*/
			/********** Code Ends for Request*********/
			insuranceVO = populateCommonVOForenewal(
					travelInsuranceVO, request,retrievePolicyByPolicyNo);
			// insuranceVO = travelInsHandler.populateTravelInsForSearch(
			// travelInsuranceVO, request );
			insuranceVO = (TravelInsuranceVO) TaskExecutor.executeTasks(
					com.Constant.CONST_TRAVEL_PACKAGE_PREMIUM, insuranceVO);

			if (!Utils.isEmpty(insuranceVO)) {
				request.setAttribute(com.Constant.CONST_TRAVELERDETAILS, AppUtils
						.createJSONForTravelerDetails(insuranceVO
								.getTravelDetailsVO().getTravelerDetailsList(),
								com.Constant.CONST_TRAVELER_DETAILS_JSON_RETRIVE_QUOTE));
				request.setAttribute(com.Constant.CONST_ISLOADOPERATION, "true");
				commonVO = travelInsuranceVO.getCommonVO();
				request.setAttribute(com.Constant.CONST_TRAVELINSURANCEVO, insuranceVO);
				AppUtils.setQuoteValidDate(insuranceVO, request);
				AppUtils.setScaleForLOB(commonVO.getLob());
			}
			CreatePolicyResponseMapper createPolicyResponseMapper = new CreatePolicyResponseMapper();
			createPolicyResponseMapper.mapVOToResponse(insuranceVO,createPolicyResponse);
			
			if(!Utils.isEmpty(retrievePolicyByPolicyNo.getDocuments())) {
				createPolicyResponse.setDocuments(retrievePolicyByPolicyNo.getDocuments());
				if(!Utils.isEmpty(retrievePolicyByPolicyNo.getDocuments().getDocsDetails())) {
					createPolicyResponse.getDocuments().setDocsDetails(retrievePolicyByPolicyNo.getDocuments().getDocsDetails());
				}
			}
			
			if(!Utils.isEmpty(retrievePolicyByPolicyNo.getDocuments()) && retrievePolicyByPolicyNo.getDocuments().getDocsInResponse()==true) {
				long startTime = System.currentTimeMillis();
				logger.debug( "Calling Document Creation:::_5"  + new Date(startTime)   );
				
				byte[] fileContent = null;
				CommonHandler.printPolicyDocument(insuranceVO, retrievePolicyByPolicyNo.getDocuments());
				String policySchedulefile = CommonHandler.encodeToString(Utils.getSingleValueAppConfig("POL_DOC_POL_SCHED_LOC")+insuranceVO.getCommonVO().getPolicyNo()+"-PolicySchedule.pdf");
				fileContent = policySchedulefile.getBytes();
				
				createPolicyResponse.getDocuments().setPolicySchedule(fileContent);
				
				if(retrievePolicyByPolicyNo.getDocuments().getDocsDetails().getLetterToBank()==true) {
					
					CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean("geComSvc");
					BaseVO baseVO = (BaseVO) commonOpSvc.invokeMethod(
							"checkForMortgageeName", insuranceVO.getCommonVO());
					DataHolderVO<Boolean> resultVo = null;
					resultVo = (DataHolderVO<Boolean>) baseVO;
					if (!Utils.isEmpty(resultVo) && resultVo.getData()) {
						String bankToLetter = CommonHandler.encodeToString(Utils.getSingleValueAppConfig("POL_DOC_BANK_LETTER") +insuranceVO.getCommonVO().getPolicyNo() +"-BankLetter.pdf");
						byte[] bankLetterContent = bankToLetter.getBytes();
						createPolicyResponse.getDocuments().setLetterToBank(bankLetterContent);
					}
					
				}
				
				long endTime = System.currentTimeMillis();
				logger.debug( " Document Creation Done_5"  + new Date(endTime)   );
				logger.debug( "Time taken for Document creation and converting to byte array::_5"  + ( endTime - startTime )  );
				
			}
			
			webServiceAuditMapper.mapRequestAndReponseToAuditVO(travelInsuranceVO, retrievePolicyByPolicyNo, createPolicyResponse,headerInfo);
			String propertyKey = null;
			if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE)) {
				propertyKey = com.Constant.CONST_PASB2C_ASSISTANCE_OMAN;
			}else {
				propertyKey = com.Constant.CONST_PASB2C_ASSISTANCE;
			}
			if (!Utils.isEmpty(commonVO)
					&& AppConstants.CONVERTED_TO_POL_STATUS.equals(commonVO
							.getStatus())) {
				CommonHandler
						.renderErrorMessages(
								result,
								Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_POLICYQUOTE_RETRIEVED)
										+ commonVO.getPolicyNo().toString()
										+ ". "
										+ Utils.getAppErrorMessage(propertyKey));
			}
		} catch (SystemException systemException) {
			Errors errors = result;
			errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
					com.Constant.CONST_ERROR_END + systemException.getMessage());
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
			Errors errors = result;
			errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
					com.Constant.CONST_ERROR_END + e.getMessage());
		} catch (BusinessException be) {
			Errors errors = result;
			errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
					com.Constant.CONST_ERROR_END + be.getMessage());
			if (!Utils.isEmpty(request.getAttribute(com.Constant.CONST_RSA_DIRECT_ERROR))) {
				ModelAndView view = new ModelAndView();
				view.addObject(com.Constant.CONST_ERRORMSG, be.getMessage());
				view.setViewName("redirect:TravelStep1.do");
				return createPolicyResponse;
			}
		} catch (Exception e) {
			logger.error("Exception while fetching quote details_4", e);
			Errors errors = result;
			if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE))
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
					Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_OMAN_ERROR));
			else
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_ERROR));
		}
		setFieldsForSiteCatalyst(request, travelInsuranceVO);
		//##START - Added by Dinesh for CR-130750 Royalty feature 
		if(!Utils.isEmpty(renQuote) && StringUtils.isNumeric(renQuote) && !Utils.isEmpty(request.getParameter(AppConstants.EMAIL_REQ_PARAM)) ){
			   if (!Utils.isEmpty(request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM))){
				   logger.info("Going to set PROMCODE value: "+request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM));
				   travelInsuranceVO.getGeneralInfo().getSourceOfBus().setPromoCode(request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM));
				}else{
					logger.info("Going to set PROMCODE value Empty");
					travelInsuranceVO.getGeneralInfo().getSourceOfBus().setPromoCode("");
				}
		}//##END	
		return createPolicyResponse;
	}
	
	private void setFieldsForSiteCatalyst(HttpServletRequest request,
			TravelInsuranceVO travelInsuranceVO) {

		if (!Utils.isEmpty(travelInsuranceVO.getTravelDetailsVO()
				.getTravelLocation())) {
			request.setAttribute("omniWhere", travelInsuranceVO
					.getTravelDetailsVO().getTravelLocation());
		}

		if (!Utils.isEmpty(travelInsuranceVO.getScheme().getEffDate())) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			request.setAttribute("omniStartDate", dateFormat
					.format(travelInsuranceVO.getScheme().getEffDate()));
		}

		if (!Utils.isEmpty(travelInsuranceVO.getPolicyTerm())) {
			request.setAttribute("omniTripType",
					travelInsuranceVO.getPolicyTerm());
		}

		if (!Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getSourceOfBus()
				.getPromoCode())) {
			request.setAttribute("omniPromotionalCode", travelInsuranceVO
					.getGeneralInfo().getSourceOfBus().getPromoCode());
		}

		if (!Utils.isEmpty(travelInsuranceVO.getPremiumVO())
				&& !Utils.isEmpty(travelInsuranceVO.getPremiumVO()
						.getPremiumAmt())) {
			double discount = (Utils.isEmpty(travelInsuranceVO.getPremiumVO()) && Utils
					.isEmpty(travelInsuranceVO.getPremiumVO()
							.getDiscOrLoadPerc())) ? 0 : travelInsuranceVO
					.getPremiumVO().getDiscOrLoadPerc();
			Double discountAmt = Double.parseDouble(Currency
					.getUnformttedScaledCurrency((travelInsuranceVO
							.getPremiumVO().getPremiumAmt() * discount) / 100));
			double totalPrice = travelInsuranceVO.getPremiumVO()
					.getPremiumAmt() + discountAmt;
			request.setAttribute("omniQuoteValue", totalPrice);
		}

		for (TravelerDetailsVO travelerDetailsVO : travelInsuranceVO
				.getTravelDetailsVO().getTravelerDetailsList()) {

			// Going to check for Relation
			if (!Utils.isEmpty(travelerDetailsVO.getRelation())) {
				if (travelerDetailsVO.getRelation().equals(RELATIONSHIP_SELF)) {

					if (!Utils.isEmpty(travelerDetailsVO.getNationality())) {
						request.setAttribute("omniLeadNationality", SvcUtils
								.getLookUpDescription("NATIONALITY", "ALL",
										"ALL", travelerDetailsVO
												.getNationality().intValue()));
					}
					if (!Utils.isEmpty(travelerDetailsVO.getDateOfBirth())
							&& !Utils.isEmpty(travelInsuranceVO.getScheme()
									.getEffDate())) {
						request.setAttribute("omniLeadAge", SvcUtils.getAge(
								travelerDetailsVO.getDateOfBirth(),
								travelInsuranceVO.getScheme().getEffDate()));
					}

				}
			}
		}
		
	
	}
	
	public TravelInsuranceVO populateCommonVOForenewal(TravelInsuranceVO travelInsuranceVO, HttpServletRequest request,
			RetrievePolicyByPolicyNo retrievePolicyByPolicyNo) throws Exception {
		TravelInsuranceVO travelInsObj = (TravelInsuranceVO)Utils.getBean(com.Constant.CONST_VO_TRAVEL);
		String renQuote = null;
		String dob = null;
		try {
			CommonVO commonVO = travelInsuranceVO.getCommonVO();
			
			String policy = retrievePolicyByPolicyNo.getPolicyNo().toString();
			dob = AppUtils.datesFormatter(retrievePolicyByPolicyNo.getDob());
			
			if (! Utils.isEmpty( policy ))
			{
				
				try{			
					System.out.println("Inside the null check "+policy);
					
					CommonVO common = new CommonVO();
					DataHolderVO policyIdHolder = new DataHolderVO();
					common.setPolicyNo(Long.parseLong(policy));
					common.setCreatedBy(dob);
							
					CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( com.Constant.CONST_GECOMSVCBEAN );
					
					policyIdHolder = (DataHolderVO) commonOpSvc.invokeMethod( "getQuoteForPolicy", common);
					if(Utils.isEmpty(commonVO)){
						commonVO = new CommonVO();
						commonVO.setIsQuote(true);
						commonVO.setQuoteNo(Long.parseLong(policyIdHolder.getData().toString()));
						commonVO = AppUtils.populateCommonVO(commonVO);
						commonVO.setDocCode((short) 5);
					}
					if(policyIdHolder.getData().toString().isEmpty() || policyIdHolder.getData().toString()== null){
						policyIdHolder = (DataHolderVO) commonOpSvc.invokeMethod( "getRenQuoteForPolicy", common);
						commonVO.setDocCode(Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_REN_QUO_DOC_CODE ) ));
					}	
					renQuote = policyIdHolder.getData().toString();
					
					common = null;
					policyIdHolder = null;
					System.out.println("The renewal Quote is "+renQuote);
				}
				catch(Exception e)
				{
					request.setAttribute(com.Constant.CONST_RSA_DIRECT_ERROR, com.Constant.CONST_RSA_DIRECT_ERROR);
					throw e;
					
				}	
			}
			else
			{
				renQuote = request.getParameter("renQuote");
				if(!Utils.isEmpty(renQuote) && !StringUtils.isNumeric(renQuote)){
					renQuote = AppUtils.encryptAndDecryptData( renQuote, Boolean.FALSE );
				}
			}
			
			commonVO.setLob( LOB.TRAVEL );
			UserProfile userProfile = null;
			if (!Utils.isEmpty(request)) {
				userProfile = (UserProfile) request.getSession(false)
						.getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
			} else {// create userProfile for web services
				userProfile = new UserProfile();
			}
			//String renQuote = request.getParameter("renQuote");
			//commonVO.setIsQuote(true);
			commonVO.setLoggedInUser(userProfile);
			SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
			sourceOfBusinessVO.setDistributionChannel(AppConstants.B2C_DEFAULT_DIST_CHANNEL);
			GeneralInfoVO generalInfoVO = new GeneralInfoVO();
			generalInfoVO.setSourceOfBus(sourceOfBusinessVO);
			travelInsuranceVO.setGeneralInfo(generalInfoVO);
			if(Utils.isEmpty(commonVO.getQuoteNo())){
				commonVO.setQuoteNo(Long.parseLong(renQuote));
			}
			commonVO.setIsQuote(true);
			SchemeVO schemeVO = new SchemeVO();
			travelInsuranceVO.setScheme(schemeVO);
			travelInsuranceVO.setCommonVO(commonVO);
			travelInsObj = travelInsHandler.loadGeneralInfoDets(travelInsuranceVO);
			CommonOpSvc commonOpSvc = (CommonOpSvc)Utils.getBean(com.Constant.CONST_GECOMSVCBEAN);
			commonVO = (CommonVO)commonOpSvc.invokeMethod("populateCommonDetails", commonVO);
			if (!Utils.isEmpty(commonVO)) {
				travelInsuranceVO.setCommonVO(commonVO);
			}
		} catch (SystemException exception) {
			throw new SystemException(exception.getCause(), exception.getMessage());
		}
		return travelInsuranceVO;
	}

	
}
