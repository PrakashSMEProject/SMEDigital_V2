package com.rsaame.pas.b2c.controllers;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.cmn.vo.User;
import com.rsaame.pas.b2b.ws.util.WSAppUtils;
import com.rsaame.pas.b2c.cmn.base.BaseController;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.handlers.B2CEmailTriggers;
import com.rsaame.pas.b2c.cmn.handlers.CommonHandler;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.b2c.cmn.utils.ApplicationContextUtils;
import com.rsaame.pas.b2c.cmn.utils.ReferralUtils;
import com.rsaame.pas.b2c.exception.SystemException;
import com.rsaame.pas.b2c.travelInsurance.TravelInsuranceHandler;
import com.rsaame.pas.b2c.validator.TravelGIQuoteCreateValidator;
import com.rsaame.pas.b2c.validator.TravelGIQuoteFetchValidator;
import com.rsaame.pas.b2c.validator.TravelRenewalValidator;
import com.rsaame.pas.b2c.validator.TravelRiskValidator;
import com.rsaame.pas.b2c.ws.handler.CommonServiceHandler;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.TravelDetailsVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.web.UserProfileHandler;

/**
 * @author M1020637, M1020859, M1033804
 * 
 */
@Controller
public class TravelController extends BaseController {

	private static final Logger logger = Logger
			.getLogger(TravelController.class);
	private static final Byte RELATIONSHIP_SELF = Byte.valueOf("1");
	CommonController commonCtrl = new CommonController();
	CommonServiceHandler commonServiceHandler = new CommonServiceHandler();
	

	final String submitTravelRisk = "submitTravelRisk";
	

	/**
	 * @param request
	 * @param session
	 *            Method to Load general info for create quote.
	 */
	@RequestMapping("**/TravelStep1.do")
	public ModelAndView loadTravelGenralInfo(
			@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request,
			HttpSession session) {
		Boolean isTerrCruiseInductionDate = true;
		logger.debug("Going to load TravelInsuranceVO bean during screen load");
		setLocation();
		String promoCode = null;
		String partner = null;
		request.getSession(false).removeAttribute(
				AppConstants.SESSION_USER_PROFILE_VO);
		TravelInsuranceHandler travelInsHandler = new TravelInsuranceHandler();
		String partnername = request.getRequestURI();
		partnername = request.getRequestURI().replace(com.Constant.CONST_QUOTEANDBUY_END, "")
				.replace("/TravelStep1.do", "").replace("TravelStep1.do", "");
		String url = request.getRequestURL().toString();
		url = url.replace(partnername + "/", "");
		request.setAttribute(com.Constant.CONST_PARTNERNAMECSS, partnername);
		request.getSession(false).setAttribute(com.Constant.CONST_ISTERRCRUISEINDUCTIONDATE, isTerrCruiseInductionDate);
		logger.info("TravelController.loadTravelGeneralInfo method, partnername: "+partnername+" ,  "+url);
		
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
			promoCode = request.getParameter("promoCode");
			GeneralInfoVO generalInfo = new GeneralInfoVO();
			logger.info("TravelController.loadTravelGeneralInfo method, promoCode: "+promoCode);

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
				logger.debug("1. Partner info for loadTravelGenralInfo() of TravelController Class  "
						+ partner);
				request.setAttribute(com.Constant.CONST_PARTNER, partner);

				if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo())) {
					// GeneralInfoVO generalInfo = new GeneralInfoVO();
					String insuredCode = request.getParameter("insuredCode");
					InsuredVO insured = new InsuredVO();
					logger.info("TravelController.loadTravelGeneralInfo method, insuredCode: "+insuredCode);

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
		ModelAndView modelAndView = null;
		String errorMsg = request.getParameter("errorMsg");
		String flags=(String)session.getAttribute("TravelLobflags");
		if (!Utils.isEmpty(errorMsg)) {
			String flag = "flagstrue";
			Errors errors = bindingResult;
			if (flag.equals(flags))
			errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, errorMsg);
			session.removeAttribute("TravelLobflags");
			
		}
		
		
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
		logger.info("Exiting TravelController.loadTravelGeneralInfo method.");
		return modelAndView;

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
	@RequestMapping(value = "**/TravelStep2.do", method = RequestMethod.POST)
	public ModelAndView saveTravelGeneralInfo(
			@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws ParseException {
		
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
			UserProfile userProfile = (UserProfile) request.getSession(false)
					.getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
			travelInsuranceVO.getCommonVO().setLoggedInUser(userProfile);
			travelInsuranceVO.setLoggedInUser(userProfile);
			String partnername = request.getParameter(com.Constant.CONST_PARTNERNAME);
			// String partnerId = request.getParameter("partnerId")== null
			// ?"":(String) request.getParameter("partnerId");
			setDefaultDetails(request, travelInsuranceVO);
			/*
			 * if (!Utils.isEmpty(partnerId)) { TravelInsuranceHandler
			 * .loadPartnerMgmtDetails(travelInsuranceVO); }
			 */

			// Added by Vishwa to NEXUS PROMO for Advenet ID 128926
			String partner = DAOUtils.fetchPartnerInfo(partnername);
			logger.debug("2. Partner info for loadTravelGenralInfo() of TravelController Class  "
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

			if (bindingResult.hasErrors()) {
				modelAndView.setViewName(com.Constant.CONST_TRAVELGENERALINFO);
				modelAndView.addObject(bindingResult);
				return modelAndView;
			}


			logger.debug("Going to save the travel insurance related details entered on the screen.");

			setFieldsForSiteCatalyst(request, travelInsuranceVO);

			if (!Utils.isEmpty(travelInsuranceVO)) {
				travelInsuranceVO = TravelInsuranceHandler
						.saveTravelGeneralInfo(travelInsuranceVO, request
								.getRequestURL().toString());
			} else {
				throw new SystemException(new Throwable(),
						"Unexpected exception ocuured. Please contact administrator_1");
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
		String documentExists=CheckForEmiratesFile(travelInsuranceVO);
		request.getSession(false).setAttribute("documentExists",documentExists );
		
		}
		
	
		
		
		return modelAndView;
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

	/**
	 * To retrieve travel general info page
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("**/fetchTravelGeneralInfo.do")
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
			logger.debug("Partner info for fetchTravelGeneralInfo() of TravelController Class  "
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
					.getBean("VO_TRAVEL");
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
					propertyKey = "pasb2c.assistance.oman";
				}else {
					propertyKey = "pasb2c.assistance";
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
					.valueOf(Utils.getSingleValueAppConfig("REN_QUO_DOC_CODE"))
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
			logger.error("Exception while fetching quote details.", e);
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

	/**
	 * @param travelInsuranceVO
	 * @param request
	 * @param response`
	 * @return ModelAndView
	 * @throws ParseException 
	 */
	@RequestMapping(value = "**/submitTravelRisk.do", method = RequestMethod.POST)
	public String saveTravel(
			@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			BindingResult bindingResult,@RequestParam("EmiratesFront") MultipartFile EmiratesFront,@RequestParam(value="EmiratesBack") MultipartFile EmiratesBack, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		
		nullRemove(travelInsuranceVO);
		//ModelAndView modelAndView = null;	/*Commented unused variable declaration and initialization - sonar violation fix */
		boolean isPrintCase = false;
		String partnerName = "";
		//Added for Oman location check
		LoginLocation location = (LoginLocation) Utils.getBean(com.Constant.CONST_LOCATION);
		String deployedLocation = location.getLocation();
		try {
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
			/*Dileep - VAT */
			/*else{
				String tariff = travelInsuranceVO.getScheme().getTariffCode().toString();
				for(TravelPackageVO travelPackageVO :travelInsuranceVO.getTravelPackageList()) {
					if(travelPackageVO.getTariffCode().equals(tariff)) {
						travelInsuranceVO.getPremiumVO().setVatTax(travelPackageVO.getVatTax());
					}
				}
			}*/
			/*VAT End */
				
			/*modelAndView = new ModelAndView(com.Constant.CONST_TRAVELRISKPAGE,
					com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);*/	/*Commented unused variable declaration and initialization - sonar violation fix */
			if(!Utils.isEmpty(EmiratesFront.getOriginalFilename()) || !Utils.isEmpty(EmiratesBack.getOriginalFilename())){
			submitEmiratesDocument(EmiratesFront,EmiratesBack,travelInsuranceVO);
			}
			String partnername = request.getParameter(com.Constant.CONST_PARTNERNAME);
			setPartnerMgmntDetails(request, travelInsuranceVO, partnername);
			saveTravelDetails(travelInsuranceVO, bindingResult, request,
					completePurchaseInd, isPrintCase);
			//TravelInsuranceHandler.populatePremium(travelInsuranceVO, request);
			
			travelInsuranceVO = TravelInsuranceHandler
					.loadTravelRiskPage(travelInsuranceVO);
			
			/*VAT - added to update VAT premium (With rounded decimal point)  in T_TRN_PREMIUM_QUO table*/
			TravelInsuranceHandler.updateVATPremium(travelInsuranceVO);
			
			AppUtils.setQuoteValidDate(travelInsuranceVO, request);
			if (!bindingResult.hasErrors() && !isPrintCase) {
				// request.setAttribute( "quoteIssued", true );
				request.getSession(false).setAttribute(
						submitTravelRisk + com.Constant.CONST_QUOTEISSUED, true);
				request.getSession(false).setAttribute(
						submitTravelRisk + com.Constant.CONST_ISPRINTCASE, false);
			} else if (isPrintCase) {
				// request.setAttribute( "isPrintCase", true );
				request.getSession(false).setAttribute(
						submitTravelRisk + com.Constant.CONST_ISPRINTCASE, true);
				request.getSession(false).setAttribute(
						submitTravelRisk + com.Constant.CONST_QUOTEISSUED, false);
			}
			setPartnerMgmntDetails(request, travelInsuranceVO, partnername);
			request.getSession(false).setAttribute(com.Constant.CONST_PARTNERNAMECSS,
					partnername); // For Css
			if (!Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
					.getSourceOfBus())) {
				partnerName = travelInsuranceVO.getGeneralInfo()
						.getSourceOfBus().getPartnerName() == null ? ""
						: '/' + travelInsuranceVO.getGeneralInfo()
								.getSourceOfBus().getPartnerName();
			}
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
		request.getSession(false).setAttribute(
				submitTravelRisk + "_travelInsuranceVO", travelInsuranceVO);
		request.getSession(false).setAttribute(
				submitTravelRisk + "_bindingResult", bindingResult);
		request.getSession(false).setAttribute(com.Constant.CONST_VALIDQUOTEDATE,
				request.getAttribute(com.Constant.CONST_VALIDQUOTEDATE));
		
		
		Boolean isTerrCruiseInductionDate = true;
		isTerrCruiseInductionDate = checkPolPreparedDt(travelInsuranceVO);
		request.setAttribute(com.Constant.CONST_ISTERRCRUISEINDUCTIONDATE, isTerrCruiseInductionDate);
		String documentExists=CheckForEmiratesFile(travelInsuranceVO);
		request.getSession(false).setAttribute("documentExists",documentExists );
		return "redirect:" + partnerName + "/submitTravelRisk.do";
		// return modelAndView;
	}

	@RequestMapping(value = "**/submitTravelRisk.do", method = RequestMethod.GET)
	public ModelAndView loadPage2(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("attribute") TravelInsuranceVO travelInsuranceVO,
			BindingResult bindingResult) {

		request.setAttribute(com.Constant.CONST_VALIDQUOTEDATE, request.getSession(false)
				.getAttribute(com.Constant.CONST_VALIDQUOTEDATE));
		request.getSession().removeAttribute(com.Constant.CONST_VALIDQUOTEDATE);
		request.setAttribute("quoteIssued", request.getSession(false)
				.getAttribute(submitTravelRisk + com.Constant.CONST_QUOTEISSUED));
		request.setAttribute("isPrintCase", request.getSession(false)
				.getAttribute(submitTravelRisk + com.Constant.CONST_ISPRINTCASE));
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
	@RequestMapping(value = "**/TravelStep3.do", method = RequestMethod.POST)
	public ModelAndView completePurchase(
			@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			BindingResult bindingResult,@RequestParam("EmiratesFront") MultipartFile EmiratesFront,@RequestParam(value="EmiratesBack") MultipartFile EmiratesBack, HttpServletRequest request,
			HttpServletResponse response) {
		
		logger.info("Entered TravelController.completePurchase method. ");
	
		
	 /*
	  * 
	  * Remove null covers from the VO : BEGIN	: HIMANISH
	  */
	 nullRemove(travelInsuranceVO);
	 /*
	  * 
	  * Remove null covers from the VO : END	: HIMANISH
	  */
		
	
		//Added for Oman D2C
		LoginLocation location = (LoginLocation) Utils.getBean(com.Constant.CONST_LOCATION);
		String deployedLocation = location.getLocation();
		logger.info("TravelController.completePurchase method, deployedLocation: "+deployedLocation);
		
		/*Dileep - VAT */
		/*String tariffVat = travelInsuranceVO.getScheme().getTariffCode().toString();
		logger.info("TravelController.completePurchase method, tariff: "+tariffVat);
		for(TravelPackageVO travelPackageVO :travelInsuranceVO.getTravelPackageList()) {
			if(travelPackageVO.getTariffCode().equals(tariffVat)) {
				travelInsuranceVO.getPremiumVO().setVatTax(travelPackageVO.getVatTax());
				logger.info("TravelController.completePurchase method, tariff: and VAT Tax : "+travelPackageVO.getVatTax());
			}
		}*/
		
		/*VAT -- End*/
		
		
		
		if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE)) {
			String tariff = travelInsuranceVO.getScheme().getTariffCode().toString();
			logger.info("TravelController.completePurchase method, tariff: "+tariff);
			for(TravelPackageVO travelPackageVO :travelInsuranceVO.getTravelPackageList()) {
				if(travelPackageVO.getTariffCode().equals(tariff)) {
					travelInsuranceVO.getPremiumVO().setGovtTax(travelPackageVO.getGovtTax());
					logger.info("TravelController.completePurchase method, tariff: and Govt Tax : "+travelPackageVO.getGovtTax());
				}
			}
		}
		
		
		
		
		ModelAndView modelAndView = new ModelAndView(com.Constant.CONST_TRAVELRISKPAGE,
				com.Constant.CONST_TRAVELINSURANCEVO, travelInsuranceVO);
		try {
			if(!Utils.isEmpty(EmiratesFront.getOriginalFilename()) || !Utils.isEmpty(EmiratesBack.getOriginalFilename())){
				
				submitEmiratesDocument(EmiratesFront,EmiratesBack,travelInsuranceVO);
			
			}
			
			boolean completePurchaseInd = true;
			String partnername = request.getParameter(com.Constant.CONST_PARTNERNAME);
			logger.info("TravelController.completePurchase method, partnername: "+partnername);
			// Added by Vishwa to enable the CSS change for partner
			request.setAttribute(com.Constant.CONST_PARTNERNAMECSS, partnername);
			
			logger.info("TravelController.completePurchase method, invoking setPartnerMgmntDetails, setPartnerMgmntDetails method. ");
			setPartnerMgmntDetails(request, travelInsuranceVO, partnername);
			saveTravelDetails(travelInsuranceVO, bindingResult, request,
					completePurchaseInd, false);
	
			if (!bindingResult.hasErrors()) {
				logger.info("TravelController.completePurchase method, calling TravelInsuranceHandler.loadDataForPayment method.");
				PolicyDataVO policyDataVO = TravelInsuranceHandler
						.loadDataForPayment(travelInsuranceVO);
				
				/*VAT - added to update VAT premium (With rounded decimal point)  in T_TRN_PREMIUM_QUO table*/
				TravelInsuranceHandler.updateVATPremium(travelInsuranceVO);
				
				if (!Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
						.getSourceOfBus().getPartnerName())) {
					policyDataVO
							.getGeneralInfo()
							.getSourceOfBus()
							.setPartnerId(
									travelInsuranceVO.getGeneralInfo()
											.getSourceOfBus().getPartnerId());
					policyDataVO
							.getGeneralInfo()
							.getSourceOfBus()
							.setPartnerName(
									travelInsuranceVO.getGeneralInfo()
											.getSourceOfBus().getPartnerName());
					policyDataVO
							.getGeneralInfo()
							.getSourceOfBus()
							.setCallCentreNo(
									travelInsuranceVO.getGeneralInfo()
											.getSourceOfBus().getCallCentreNo());
					policyDataVO
							.getGeneralInfo()
							.getSourceOfBus()
							.setCcEmailId(
									travelInsuranceVO.getGeneralInfo()
											.getSourceOfBus().getCcEmailId());
					policyDataVO
							.getGeneralInfo()
							.getSourceOfBus()
							.setReplyToEmailId(
									travelInsuranceVO.getGeneralInfo()
											.getSourceOfBus()
											.getReplyToEmailId());
					policyDataVO
							.getGeneralInfo()
							.getSourceOfBus()
							.setSourceOfBusiness(
									travelInsuranceVO.getGeneralInfo()
											.getSourceOfBus()
											.getSourceOfBusiness());
					policyDataVO
							.getGeneralInfo()
							.getSourceOfBus()
							.setFromEmailID(
									travelInsuranceVO.getGeneralInfo()
											.getSourceOfBus().getFromEmailID());
					policyDataVO
							.getGeneralInfo()
							.getSourceOfBus()
							.setDefaultOnlineDiscount(
									travelInsuranceVO.getGeneralInfo()
											.getSourceOfBus()
											.getDefaultOnlineDiscount());
					policyDataVO
							.getGeneralInfo()
							.getSourceOfBus()
							.setDefaultAssignToUser(
									travelInsuranceVO.getGeneralInfo()
											.getSourceOfBus()
											.getDefaultAssignToUser());
					policyDataVO
							.getGeneralInfo()
							.getSourceOfBus()
							.setFaqUrl(
									travelInsuranceVO.getGeneralInfo()
											.getSourceOfBus().getFaqUrl());
					policyDataVO
							.getGeneralInfo()
							.getSourceOfBus()
							.setPolicyTermUrl(
									travelInsuranceVO.getGeneralInfo()
											.getSourceOfBus()
											.getPolicyTermUrl());

				}
				
				logger.info("TravelController.completePurchase method, calling DAOUtils.fetchPolPrmForQuote method.");
				//140968 Prm 0 issue 
				double prmAmount = DAOUtils.fetchPolPrmForQuote(policyDataVO.getCommonVO().getQuoteNo(),policyDataVO.getCommonVO().getEndtId());
				logger.info("Prm Amt after save to db  and fecthing for furthur ......"+prmAmount);
				
				if(!bindingResult.hasErrors() && policyDataVO.getCommonVO().getIsQuote()&& prmAmount==0.0) {
					modelAndView.setViewName(com.Constant.CONST_TRAVELRISKPAGE);
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
				
				logger.info("Make payment by calling Payment Gateway started");
				return commonCtrl.makePayment(request, policyDataVO);

			}
		} catch( BusinessException e ) {
			e.printStackTrace();
			if(e.getMessage().contains("Premium Mismatch."))
			{
				ModelAndView mav = new ModelAndView( "paymentError", "quoteNo", travelInsuranceVO.getCommonVO().getQuoteNo() );
				mav.addObject("errorMessage",Utils.getAppErrorMessage( "pasb2c.partial.payment.error" ));
				return mav;
			}
			//CommonHandler.renderErrorMessages( bindingResult, e.getMessage() );
			logger.error( e.getMessage(), e );
		}
		catch (SystemException systemException) {
			logger.error(systemException.getMessage(), systemException);
			ModelAndView mav = new ModelAndView( "paymentError", "quoteNo", travelInsuranceVO.getCommonVO().getQuoteNo() );
			mav.addObject("errorMessage",Utils.getAppErrorMessage( "pasb2c.partial.payment.error" ));
			return mav;
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
		
		logger.info("Exiting TravelController.completePurchase method. ");
		return modelAndView;
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
			boolean completePurchaseInd, boolean isPrintCase) {
		logger.info("Entered TravelController.saveTravelDetails method.");
		
		List<TravelPackageVO> packagesToBeDeleted = new ArrayList<TravelPackageVO>();
		//Added for Oman location check
		LoginLocation location = (LoginLocation) Utils.getBean(com.Constant.CONST_LOCATION);
		String deployedLocation = location.getLocation();
		try {

			TravelInsuranceHandler.preProcess(travelInsuranceVO,
					packagesToBeDeleted);

			UserProfile userProfile = (UserProfile) request.getSession(false)
					.getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
			travelInsuranceVO.getCommonVO().setLoggedInUser(userProfile);

			travelInsuranceVO.setPopulateOperation(false);

			if (!Utils.isEmpty(travelInsuranceVO)) {
				TravelRiskValidator travelRiskValidator = (TravelRiskValidator) ApplicationContextUtils
						.getBean("travelRiskValidator");
				travelRiskValidator.validate(travelInsuranceVO, bindingResult);

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
					TravelInsuranceHandler.populatePremium(travelInsuranceVO,request);
					travelInsuranceVO = TravelInsuranceHandler
							.saveTravelRisk(travelInsuranceVO);

					// Trigger E-mail in-case of save for later start
					if (!isPrintCase && !Utils.isEmpty(travelInsuranceVO)
							&& !completePurchaseInd) {
						logger.info("TravelController.saveTravelDetails method, calling TravelInsuranceHandler.populatePremium method.");
						TravelInsuranceHandler
								.populatePremium(travelInsuranceVO,request); // To
																		// populate
																		// the
																		// premium
																		// in
																		// PremiumVO
						logger.info("TravelController.saveTravelDetails method, calling CommonHandler.populateAndTriggerEmail method.");
						CommonHandler.populateAndTriggerEmail(
								travelInsuranceVO, request.getRequestURL()
										.toString(),
								B2CEmailTriggers.TRAVEL_SAVE_FOR_LATER, null);
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
		logger.info("Exiting TravelController.saveTravelDetails method.");

	}

	/**
	 * @param travelInsuranceVO
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping("**/getRevisedPremium")
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

	@RequestMapping(value = "**/retryTravelPayment", method = RequestMethod.POST)
	public ModelAndView retryTravelPayment(
			@ModelAttribute("commonVO") CommonVO commonVO,
			HttpServletRequest request, HttpSession session) {

		TravelInsuranceVO travelInsuranceVO = new TravelInsuranceVO();
		travelInsuranceVO.setCommonVO(commonVO);
		TravelInsuranceHandler travelInsHandler = new TravelInsuranceHandler();
		TravelInsuranceVO travelVO = travelInsHandler
				.populateTravelInsForSearch(travelInsuranceVO, request);
		// PolicyDataVO policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks(
		// "TRAVEL_PACKAGE_PREMIUM", travelVO );
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
					"TRAVEL_PACKAGE_PREMIUM", travelInsuranceVO);

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
	@RequestMapping("**/loadGeneralInfoPage")
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
			logger.debug("3. Partner info for loadTravelGenralInfo() of TravelController Class  "
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
	@RequestMapping("**/requestTravelCallBack")
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
	@RequestMapping(value = "/TravelRenewalStep2.do")
	public ModelAndView renewPolicy(
			@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			BindingResult bindingResult,@RequestParam("EmiratesFront") MultipartFile EmiratesFront,@RequestParam(value="EmiratesBack") MultipartFile EmiratesBack, HttpServletRequest request,
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
					if(!Utils.isEmpty(EmiratesFront.getOriginalFilename()) || !Utils.isEmpty(EmiratesBack.getOriginalFilename())){
						
						submitEmiratesDocument(EmiratesFront,EmiratesBack,travelInsuranceVO);
					
					}
					
					travelInsuranceVO = TravelInsuranceHandler
							.saveTravelGeneralInfo(travelInsuranceVO, null);
				} else {
					throw new SystemException(new Throwable(),
							"Unexpected exception ocuured. Please contact administrator_2");
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
			}catch( BusinessException e ) {
				e.printStackTrace();
				if(e.getMessage().contains("Premium Mismatch."))
				{
					ModelAndView mav = new ModelAndView( "paymentError", "quoteNo", travelInsuranceVO.getCommonVO().getQuoteNo() );
					mav.addObject("errorMessage",Utils.getAppErrorMessage( "pasb2c.partial.payment.error" ));
					return mav;
				}
				//CommonHandler.renderErrorMessages( bindingResult, e.getMessage() );
				logger.error( e.getMessage(), e );
			} 
			catch (SystemException systemException) {
				ModelAndView mav = new ModelAndView( "paymentError", "quoteNo", travelInsuranceVO.getCommonVO().getQuoteNo() );
				mav.addObject("errorMessage",Utils.getAppErrorMessage( "pasb2c.partial.payment.error" ));
				return mav;
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
	@RequestMapping("/TravelRenewalStep1.do")
	public ModelAndView fetchTravelInfoForRenewal(
			@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsuranceVO,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
					
		TravelInsuranceVO insuranceVO = (TravelInsuranceVO) Utils
				.getBean("VO_TRAVEL");
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
					"TRAVEL_PACKAGE_PREMIUM", insuranceVO);

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
				propertyKey = "pasb2c.assistance.oman";
			}else {
				propertyKey = "pasb2c.assistance";
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
			if (!Utils.isEmpty(request.getAttribute("RSA_DIRECT_ERROR"))) {
				ModelAndView view = new ModelAndView();
				view.addObject("errorMsg", be.getMessage());
				session.setAttribute("TravelLobflags", "flagstrue");
				view.setViewName("redirect:TravelStep1.do");
				return view;
			}
		} catch (Exception e) {
			logger.error("Exception while fetching quote details.", e);
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
	@RequestMapping("/TravelRenewalSave.do")
	public ModelAndView saveRenewalTravel(
			@ModelAttribute(com.Constant.CONST_TRAVELINSURANCEVO) TravelInsuranceVO travelInsVO,
			BindingResult bindingResult,@RequestParam("EmiratesFront") MultipartFile EmiratesFront,@RequestParam(value="EmiratesBack") MultipartFile EmiratesBack, HttpServletRequest request,
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
				if(!Utils.isEmpty(EmiratesFront.getOriginalFilename()) || !Utils.isEmpty(EmiratesBack.getOriginalFilename())){
					
					submitEmiratesDocument(EmiratesFront,EmiratesBack,travelInsVO);
				
				}
				if (!Utils.isEmpty(travelInsVO)) {
					travelInsVO = TravelInsuranceHandler.saveTravelGeneralInfo(
							travelInsVO, null);
				} else {
					throw new SystemException(new Throwable(),
							"Unexpected exception ocuured. Please contact administrator_3");
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
		
		logger.info("Entered TravelController.setPartnerMgmntDetails method.");
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
		logger.info("Exiting TravelController.setPartnerMgmntDetails method.");
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
		
		
		public void submitEmiratesDocument(MultipartFile EmiratesFront,MultipartFile EmiratesBack,TravelInsuranceVO travelInsuranceVO) throws IOException {
			
			String response="";
			String rootPath = Utils.getSingleValueAppConfig( "FILE_UPLOAD_ROOT" );
			String modulePath = Utils.getSingleValueAppConfig( Utils.concat( "FILE_UPLOAD_" + Utils.getSingleValueAppConfig("EMIRATE_DEFAULT_DOCID") + "_FOLDER" ) );
			String path = Utils.concat( rootPath, "/", Utils.isEmpty( modulePath ) ? "" : modulePath );
			Boolean isQuote = true;
			Long QuoteNum=travelInsuranceVO.getCommonVO().getQuoteNo();
			//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
			String InsuredCode=DAOUtils.FetchInsuredCode(QuoteNum.toString());
			path = path + "/" +InsuredCode ;
			
			File directory = new File( path );
			if( !directory.exists() ){
				boolean success = ( new File( path ) ).mkdirs();
				if(success){
					logger.debug( "Directory created successfully "+ path);
				}
			}
			String Extension="";
			String Filename="";
		if(!Utils.isEmpty(EmiratesFront.getOriginalFilename())){
			int startpos=EmiratesFront.getOriginalFilename().lastIndexOf( "." );
			Extension=EmiratesFront.getOriginalFilename().substring(startpos+ 1);
			Filename= Utils.getSingleValueAppConfig("FILENAME_EMIRATE_FRONT")+"_"+InsuredCode+"."+Extension;
			response=WSAppUtils.decodeToFile(path+"/"+Filename, EmiratesFront.getBytes());	
		}
		if(!Utils.isEmpty(EmiratesBack.getOriginalFilename())){
			int startpos=EmiratesBack.getOriginalFilename().lastIndexOf( "." );
			Extension=EmiratesBack.getOriginalFilename().substring(startpos+ 1);
			Filename= Utils.getSingleValueAppConfig("FILENAME_EMIRATE_BACK")+"_"+InsuredCode+"."+Extension;
			response=WSAppUtils.decodeToFile(path+"/"+Filename, EmiratesBack.getBytes());	
		}
				
			
			
		}
		
		public String CheckForEmiratesFile(TravelInsuranceVO travelInsuranceVO){
			
			
				List<String> KYCDtL=SvcUtils.populateKYCDt();
				String documentExists="";
				boolean emIdDocFrontFlag=false;
				boolean emIdDocBackFlag=false;
				if(KYCDtL.get(1).equals("1")){
					
					
					Long QuoteNum=travelInsuranceVO.getCommonVO().getQuoteNo();
					
					if(!Utils.isEmpty(QuoteNum)){
						String InsuredCode=DAOUtils.FetchInsuredCode(QuoteNum.toString());
							if(!Utils.isEmpty(InsuredCode)){
								
								String Insuredfilepath = Utils.getSingleValueAppConfig( "FILE_UPLOAD_ROOT" )+"/"+ Utils.getSingleValueAppConfig( "FILE_UPLOAD_"+Utils.getSingleValueAppConfig("EMIRATE_DEFAULT_DOCID")+"_FOLDER" )+"/"+InsuredCode+"/";
								File Insuredfolder = new File(Insuredfilepath);
								File EmirateslistOfFiles[] = Insuredfolder.listFiles();

								if( !Utils.isEmpty(EmirateslistOfFiles)){
									for(File Emiratefiles:EmirateslistOfFiles){
										if(Emiratefiles.getName().startsWith(Utils.getSingleValueAppConfig("FILENAME_EMIRATE_FRONT")+"_"+InsuredCode)){
											emIdDocFrontFlag=true;
										}else if(Emiratefiles.getName().startsWith(Utils.getSingleValueAppConfig("FILENAME_EMIRATE_BACK")+"_"+InsuredCode)){
											emIdDocBackFlag=true;
										}
									}
								
								
									}
						
							}
					}
				}else{
					emIdDocFrontFlag=true;
					emIdDocBackFlag=true;
				}
			     
			
				documentExists=emIdDocFrontFlag+","+emIdDocBackFlag;
			
			
			return documentExists;
			
		}

}
