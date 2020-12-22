/**
 * Controller created to handle requests from RSA Direct - Drupal Page.
 * All the functionalities that would get migrated to ePlatform from RSA Direct should have the controller in this class.
 */
package com.rsaame.pas.b2c.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.b2c.cmn.base.BaseController;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.handlers.CommonHandler;
import com.rsaame.pas.b2c.cmn.utils.ApplicationContextUtils;
import com.rsaame.pas.b2c.rsaDirect.RSADirectHandler;
import com.rsaame.pas.b2c.validator.GolfInsuranceValidator;
import com.rsaame.pas.b2c.validator.MakeClaimValidator;
import com.rsaame.pas.b2c.validator.PersonalAccidentValidator;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.MotorClaimVO;

/**
 * @author Sarath Varier
 * @since Phase 3 - Make a Claim migration
 * 
 */
@Controller
public class RSADirectController extends BaseController {

	private final static Logger LOGGER = Logger
			.getLogger(RSADirectController.class);

	RSADirectHandler handler = new RSADirectHandler();
	
	// Oman D2C: Claims FNOL : Abani - Start
	/* CR#16258 - CTS - 20.08.2020   - remove the claims page link - Start
	 @RequestMapping( "/FNOL.do")
	public ModelAndView getFNOL(HttpServletRequest request,
			HttpSession session,
			@ModelAttribute("claimVO") MotorClaimVO motorClaimVO,
			BindingResult bindingResult) throws ParseException{
		request.getSession(false).setAttribute(AppConstants.FNOL_DRUPAL, "false");
		return getClaimPage(request, session, motorClaimVO, bindingResult);
	}
	
	CR#16258 - CTS - 20.08.2020   - remove the claims page link - end*/
	// Oman D2C: Claims FNOL : Abani - End
	
	// Oman D2C: Claims FNOL : Praveen - Start
	/*CR#16258 - CTS - 20.08.2020   - remove the claims page link - Start
	 @RequestMapping( "/FNOLInclude.do")
	public ModelAndView getFNOLInclude(HttpServletRequest request,
			HttpSession session,
			@ModelAttribute("claimVO") MotorClaimVO motorClaimVO,
			BindingResult bindingResult) throws ParseException{
		request.getSession(false).setAttribute(AppConstants.FNOL_DRUPAL, "true");
		return getClaimPage(request, session, motorClaimVO, bindingResult);
	}
	CR#16258 - CTS - 20.08.2020   - remove the claims page link - end*/
	// Oman D2C: Claims FNOL : Praveen - End

	/*CR#16258 - CTS - 20.08.2020   - remove the claims page link - Start
	 @RequestMapping(value = "/MakeAClaim.do", method = RequestMethod.GET)
	public ModelAndView getClaimPage(HttpServletRequest request,
			HttpSession session,
			@ModelAttribute("claimVO") MotorClaimVO motorClaimVO,
			BindingResult bindingResult) throws ParseException {

		LOGGER.info("Load request for make a claim page received");
		MotorClaimVO claimVO = new MotorClaimVO();

		setLocation();
		
		 * D2C Deployed Location set to session : BEGIN
		 
		session.setAttribute("deployedLoc", getLocation().getLocation());
		
		 * D2C Deployed Location set to session : END
		 
		//Added for oman location check
		LoginLocation location = (LoginLocation) Utils.getBean(com.Constant.CONST_LOCATION);
		String deployedLocation = location.getLocation();
		
		if (!Utils.isEmpty(request.getSession(false))
				&& !Utils.isEmpty(request.getSession(false).getAttribute(
						com.Constant.CONST_SUBMITCLAIM_MOTORCLAIMVO))) {
			claimVO = (MotorClaimVO) request.getSession(false).getAttribute(
					com.Constant.CONST_SUBMITCLAIM_MOTORCLAIMVO);
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			request.setAttribute("dateOfAccident",
					formatter.format(claimVO.getDateOfAccident()));
			request.getSession(false).setAttribute(com.Constant.CONST_SUBMITCLAIM_MOTORCLAIMVO,
					null);
		}
		BindingResult bresult = (BindingResult) request.getSession(false)
				.getAttribute(com.Constant.CONST_SUBMITCLAIM_BINDINGRESULT);
		if (!Utils.isEmpty(bresult) && !Utils.isEmpty(bresult.getAllErrors())) {
			Iterator<ObjectError> it = bresult.getAllErrors().iterator();
			while (it.hasNext()) {
				bindingResult.addError(it.next());
			}
		}
		request.getSession(false).setAttribute(com.Constant.CONST_SUBMITCLAIM_BINDINGRESULT,
				null);
		
		//Added for Oman D2C FNOL drupal Page - Praveen
		if (null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE) && null != request.getSession().getAttribute(AppConstants.FNOL_DRUPAL) && request.getSession().getAttribute(AppConstants.FNOL_DRUPAL).equals("true")) {
			return new ModelAndView("FNOLInclude", "claimVO", claimVO);
		}else {
			return new ModelAndView("makeClaim", "claimVO", claimVO);
		}
		
	} CR#16258 - CTS - 20.08.2020   - remove the claims page link - end*/

	@RequestMapping(value = "/SubmitClaim.do", method = RequestMethod.POST)
	public String submitClaim(HttpServletRequest request,
			@ModelAttribute("claimVO") MotorClaimVO motorClaimVO,
			BindingResult bindingResult) {

		LOGGER.info("Submit request for make a claim page received");
		try {
			MakeClaimValidator validator = (MakeClaimValidator) ApplicationContextUtils
					.getBean("makeClaimValidator");
			validator.validate(motorClaimVO, bindingResult);
			if (!bindingResult.hasErrors()) {
				handler.submitClaim(motorClaimVO);
			}
		} catch (BusinessException be) {
			motorClaimVO.setClaimId(null);
			CommonHandler.renderErrorMessages(bindingResult,
					Utils.getAppErrorMessage(be.getErrorKeysList().get(0)));
			be.printStackTrace();
		} catch (Exception e) {
			motorClaimVO.setClaimId(null);
			CommonHandler.renderErrorMessages(bindingResult,
					Utils.getAppErrorMessage("pasb2c.claims.error"));
			e.printStackTrace();
		}
		request.getSession(false).setAttribute(com.Constant.CONST_SUBMITCLAIM_MOTORCLAIMVO,
				motorClaimVO);
		request.getSession(false).setAttribute(com.Constant.CONST_SUBMITCLAIM_BINDINGRESULT,
				bindingResult);
		//Added for oman location check
		LoginLocation location = (LoginLocation) Utils.getBean(com.Constant.CONST_LOCATION);
		String deployedLocation = location.getLocation();
		
		//Added for Oman D2C FNOL drupal Page - Praveen
		if (null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE))
			if( null != request.getSession().getAttribute(AppConstants.FNOL_DRUPAL) && request.getSession().getAttribute(AppConstants.FNOL_DRUPAL).toString().equalsIgnoreCase("true"))
				return "redirect:/FNOLInclude.do";
			else
				return "redirect:/FNOL.do";
		else
			return "redirect:/MakeAClaim.do";
		// return new ModelAndView("makeClaim", "claimVO", motorClaimVO);
	}

	@RequestMapping(value = "/Renew.do", method = RequestMethod.GET)
	public ModelAndView getRenewalPage(HttpServletRequest request,
			HttpSession session) {

		LOGGER.info("Request to load RSA Direct Renewal page");
		setLocation();
		return new ModelAndView("renewYourPolicy"); // renewYourPolicy
	}

	@RequestMapping(value = "/SubmitRSADirectRenewPolicy.do", method = RequestMethod.POST)
	public ModelAndView renewPolicy(HttpServletRequest request,
			@RequestParam("policyNo") String polNo,
			@RequestParam("emailId") String email,
			@RequestParam("dob") Date dob, Model model) {

		LOGGER.info("Submit request for make a claim page received");
		String product = request.getParameter("product-type");
		Integer policyType = Integer.parseInt(Utils
				.getSingleValueAppConfig(product));
		ModelAndView mav = new ModelAndView("renewYourPolicy"); // renewYourPolicy
		String url = null;
		try {
			DataHolderVO<Object[]> policyDataHolder = new DataHolderVO<Object[]>();
			if (polNo.contains("/")) {
				// polNo = polNo.substring(0, polNo.lastIndexOf('/'));
				polNo = polNo.substring((polNo.lastIndexOf('/') + 1),
						polNo.length());
			}
			Long policyNo = Long.parseLong(polNo);
			Object[] inputs = { policyNo, email, dob, policyType };
			policyDataHolder.setData(inputs);
			url = handler.submitRenewalPolicy(policyDataHolder);
			return new ModelAndView("redirect:" + String.valueOf(url));
			// response.sendRedirect(url);
		} catch (BusinessException be) {
			mav.addObject("renewErr", be.getMessage());
			return mav;
			// return new ModelAndView(
			// "redirect:"+"http://localhost:8080/QuoteAndBuy/RSADirectRenewalPage.do?renewalErr="+be.getMessage());

		} catch (NumberFormatException nfe) {
			mav.addObject("renewErr",
					Utils.getAppErrorMessage("pasb2c.renewal.invalidPolicyNo"));
			return mav;
		}
	}

	// Oman D2C: Home Lead Generation: Abani - Start
	@RequestMapping( "/HomeLead.do")
	public ModelAndView getHomeLead(HttpServletRequest request,
			HttpSession session,
			@ModelAttribute("insuredVO") InsuredVO insuredVO,
			BindingResult bindingResult) throws ParseException{
		request.getSession(false).setAttribute(AppConstants.HOME_LEAD_DRUPAL, "false");
		return getGolfInsurance(request, insuredVO, bindingResult);
	}
	// Oman D2C: Home Lead Generation: Abani - End
	
	// Oman D2C: Home Lead Generation: Praveen - Start
	@RequestMapping( "/HomeLeadInclude.do")
	public ModelAndView getHomeLeadInlude(HttpServletRequest request,
			HttpSession session,
			@ModelAttribute("insuredVO") InsuredVO insuredVO,
			BindingResult bindingResult) throws ParseException{
		request.getSession(false).setAttribute(AppConstants.HOME_LEAD_DRUPAL, "true");
		return getGolfInsurance(request, insuredVO, bindingResult);
	}
	// Oman D2C: Home Lead Generation: Praveen - End
		
		
	@RequestMapping(value = "/GolfStep1.do", method = RequestMethod.GET)
	public ModelAndView getGolfInsurance(HttpServletRequest request,
			@ModelAttribute("insuredVO") InsuredVO insuredVO,
			BindingResult bindingResult) {

		LOGGER.info("Request to load Golf Insurance page  - GET call Golf Insurance");
		insuredVO = new InsuredVO();
		setLocation();
		LoginLocation location = (LoginLocation) Utils.getBean(com.Constant.CONST_LOCATION);
		String deployedLocation = location.getLocation();
		ModelAndView modelAndView = null;
		if (null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE) && null != request.getSession().getAttribute(AppConstants.HOME_LEAD_DRUPAL) && request.getSession().getAttribute(AppConstants.HOME_LEAD_DRUPAL).equals("true")) {
			modelAndView = new ModelAndView("HomeLead");
		}else {
			modelAndView = new ModelAndView("GolfInsurance");
		}
		modelAndView.addObject(com.Constant.CONST_ISSUCCESS, Boolean.FALSE);
		if (!Utils.isEmpty(request.getSession(false))
				&& !Utils.isEmpty(request.getSession(false).getAttribute(
						com.Constant.CONST_SUBMITGOLFINSURANCE_INSUREDVO))) {
			insuredVO = (InsuredVO) request.getSession(false).getAttribute(
					com.Constant.CONST_SUBMITGOLFINSURANCE_INSUREDVO);
			modelAndView.addObject(com.Constant.CONST_ISSUCCESS, Boolean.TRUE);
			request.getSession(false).setAttribute(
					com.Constant.CONST_SUBMITGOLFINSURANCE_INSUREDVO, null);
		}
		BindingResult bresult = (BindingResult) request.getSession(false)
				.getAttribute(com.Constant.CONST_SUBMITGOLFINSURANCE_BINDINGRESULT);
		if (!Utils.isEmpty(bresult) && !Utils.isEmpty(bresult.getAllErrors())) {
			Iterator<ObjectError> it = bresult.getAllErrors().iterator();
			while (it.hasNext()) {
				bindingResult.addError(it.next());
			}
			modelAndView.addObject(com.Constant.CONST_ISSUCCESS, Boolean.FALSE);
		}
		request.getSession(false).setAttribute(com.Constant.CONST_SUBMITGOLFINSURANCE_BINDINGRESULT, null);
		modelAndView.addObject("insuredVO", insuredVO);
		return modelAndView; //new ModelAndView("GolfInsurance", "insuredVO", insuredVO);
	}

	@RequestMapping(value = "/Golf-Insurance-Submit.do", method = RequestMethod.POST)
	public String setGolfInsurance(HttpServletRequest request,
			@ModelAttribute("insuredVO") InsuredVO insuredVO,
			BindingResult bindingResult) {

		LOGGER.info("Submit request for contact customer - POST call Golf Accident");
		try {
			GolfInsuranceValidator validator=(GolfInsuranceValidator)ApplicationContextUtils.getBean("golfInsuranceValidator");
			validator.validate(insuredVO, bindingResult);
			if (!bindingResult.hasErrors()) {
				handler.submitGolfInsurance(insuredVO);
			}
			
		} catch (BusinessException be) {
			CommonHandler.renderErrorMessages(bindingResult,
					Utils.getAppErrorMessage(be.getErrorKeysList().get(0)));
			be.printStackTrace();
		} catch (Exception e) {
			CommonHandler.renderErrorMessages(bindingResult,
					Utils.getAppErrorMessage("pasb2c.golfinsurance.error"));
			e.printStackTrace();
		}
		request.getSession(false).setAttribute(com.Constant.CONST_SUBMITGOLFINSURANCE_INSUREDVO,
				insuredVO);
		request.getSession(false).setAttribute(
				com.Constant.CONST_SUBMITGOLFINSURANCE_BINDINGRESULT, bindingResult);
		LoginLocation location = (LoginLocation) Utils.getBean(com.Constant.CONST_LOCATION);
		String deployedLocation = location.getLocation();
		if (null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE))
			if (null != request.getSession().getAttribute(AppConstants.HOME_LEAD_DRUPAL) && request.getSession().getAttribute(AppConstants.HOME_LEAD_DRUPAL).equals("true"))
				return "redirect:/HomeLeadInclude.do";
			else
				return "redirect:/HomeLead.do";
		else
			return "redirect:/GolfStep1.do";
		// return new ModelAndView("makeClaim", "claimVO", motorClaimVO);
	}
	
	@RequestMapping(value = "/PersonalAccidentStep1.do", method = RequestMethod.GET)
	public ModelAndView getPersonalAccident(HttpServletRequest request,
			@ModelAttribute("insuredVO") InsuredVO insuredVO,
			BindingResult bindingResult) {

		LOGGER.info("Request to load Personal Accident Insurance page - GET call Personal Accident Insurance");
	
		insuredVO = new InsuredVO();
		ModelAndView modelAndView = new ModelAndView("personalAccident");
		modelAndView.addObject(com.Constant.CONST_ISSUCCESS, Boolean.FALSE);
		setLocation();
		if (!Utils.isEmpty(request.getSession(false))
				&& !Utils.isEmpty(request.getSession(false).getAttribute(
						com.Constant.CONST_SUBMITPERSONALACCIDENTINSURANCE_INSUREDVO))) {
			insuredVO = (InsuredVO) request.getSession(false).getAttribute(
					com.Constant.CONST_SUBMITPERSONALACCIDENTINSURANCE_INSUREDVO);
			modelAndView.addObject(com.Constant.CONST_ISSUCCESS, Boolean.TRUE);
			request.getSession(false).setAttribute(
					com.Constant.CONST_SUBMITPERSONALACCIDENTINSURANCE_INSUREDVO, null);
		}
		BindingResult bresult = (BindingResult) request.getSession(false)
				.getAttribute(com.Constant.CONST_SUBMITPERSONALACCIDENTINSURANCE_BINDINGRESULT);
		if (!Utils.isEmpty(bresult) && !Utils.isEmpty(bresult.getAllErrors())) {
			Iterator<ObjectError> it = bresult.getAllErrors().iterator();
			while (it.hasNext()) {
				bindingResult.addError(it.next());
			}
			modelAndView.addObject(com.Constant.CONST_ISSUCCESS, Boolean.FALSE);
		}
		request.getSession(false).setAttribute(com.Constant.CONST_SUBMITPERSONALACCIDENTINSURANCE_BINDINGRESULT, null);
		modelAndView.addObject("insuredVO", insuredVO);
		return modelAndView;
	}

	
	@RequestMapping(value = "/Personal-Accident-Insurance-Submit.do", method = RequestMethod.POST)
	public String setPersonalAccident(HttpServletRequest request,
			@ModelAttribute("insuredVO") InsuredVO insuredVO,
			BindingResult bindingResult) {

		LOGGER.info("Submit request for contact to customer - POST call Personal Accident Insurence");
		try {
			PersonalAccidentValidator validator=(PersonalAccidentValidator)ApplicationContextUtils.getBean("personalAccidentValidator");
			validator.validate(insuredVO, bindingResult);
			if (!bindingResult.hasErrors()) {
				handler.submitPersonalAccidentInsurance(insuredVO);
			}
		} catch (BusinessException be) {
			CommonHandler.renderErrorMessages(bindingResult,
					Utils.getAppErrorMessage(be.getErrorKeysList().get(0)));
			be.printStackTrace();
		} catch (Exception e) {
			CommonHandler.renderErrorMessages(bindingResult,
					Utils.getAppErrorMessage("pasb2c.personalaccidentinsurance.error"));
			e.printStackTrace();
		}
		request.getSession(false).setAttribute(com.Constant.CONST_SUBMITPERSONALACCIDENTINSURANCE_INSUREDVO,
				insuredVO);
		request.getSession(false).setAttribute(
				com.Constant.CONST_SUBMITPERSONALACCIDENTINSURANCE_BINDINGRESULT, bindingResult);
		return "redirect:/PersonalAccidentStep1.do";
	}

}
