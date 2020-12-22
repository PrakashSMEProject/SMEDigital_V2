package com.rsaame.pas.b2c.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.json.JSONException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.User;
import com.rsaame.pas.b2c.cmn.base.BaseController;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.handlers.B2CEmailTriggers;
import com.rsaame.pas.b2c.cmn.handlers.CommonHandler;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.b2c.cmn.utils.ApplicationContextUtils;
import com.rsaame.pas.b2c.cmn.utils.ReferralUtils;
import com.rsaame.pas.b2c.homeInsurance.HomeInsuranceSvcHandler;
import com.rsaame.pas.b2c.homeInsurance.IHomeInsuranceSvcHandler;
import com.rsaame.pas.b2c.ws.beans.BuildingDetails;
import com.rsaame.pas.b2c.ws.handler.CommonServiceHandler;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.request.vo.mapper.RequesttoCoverdetailsContentList;
import com.rsaame.pas.request.vo.mapper.RequesttoCoverdetailsPPList;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.CoverDetails;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.StaffDetailsVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.web.UserProfileHandler;

/**
 * @author M1033804
 * 
 */

@Controller
public class HomeController extends BaseController {

	private final static Logger LOGGER = Logger.getLogger(HomeController.class);

	IHomeInsuranceSvcHandler homeInsuranceSvcHandler = new HomeInsuranceSvcHandler();
	CommonServiceHandler commonServiceHandler = new CommonServiceHandler();
	CommonController commonCtrl = new CommonController();

	@RequestMapping(value = "**/HomeStep1.do", method = RequestMethod.GET)
	public ModelAndView getHomeFirstPage(
			@ModelAttribute(com.Constant.CONST_HOMEINSURANCEVO) HomeInsuranceVO homeInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request, HttpSession session) {
		LOGGER.info("Display request for Home Risk cover page started");
		setLocation();
		/*
		 * Bug Id 13850 : Home B2C_ Image was not displayed in Thank you page
		 * for B2C Home
		 */
		session.setAttribute("deployedLoc", getLocation().getLocation());
		String promocode = null;
		request.getSession(false).removeAttribute(AppConstants.SESSION_USER_PROFILE_VO);
		// HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO)
		// ApplicationContextUtils.getBean("VO_HOME");
		homeInsuranceVO = (HomeInsuranceVO) ApplicationContextUtils.getBean("VO_HOME");
		homeInsuranceVO.setCommonVO(new CommonVO());
		AppUtils.populateCommonVO(homeInsuranceVO.getCommonVO());
		// new HomeInsuranceVO();
		homeInsuranceVO.getCommonVO().setLob(LOB.HOME);
		homeInsuranceVO.getCommonVO().setIsQuote(Boolean.TRUE);
		String partnername = request.getRequestURI();
		partnername = request.getRequestURI().replace(com.Constant.CONST_QUOTEANDBUY_END, "")
				.replace("/HomeStep1.do", "").replace("HomeStep1.do", "");
		String url = request.getRequestURL().toString();
		url = url.replace(partnername + "/", "");
		if (Utils.isEmpty(homeInsuranceVO.getScheme())) {
			SchemeVO schemeVo = new SchemeVO();
			homeInsuranceVO.setScheme(schemeVo);
		}
		homeInsuranceVO.getScheme().setSchemeCode(Integer.valueOf(AppConstants.B2C_HOME_DEFAULT_SCHEME));
		homeInsuranceVO.getScheme().setTariffCode(Integer.valueOf(AppConstants.B2C_HOME_DEFAULT_TARIFF));
		try {
			AppUtils.setScaleForLOB(homeInsuranceVO.getCommonVO().getLob());
			// if (!(Utils.isEmpty(partnername))) {
			promocode = request.getParameter(com.Constant.CONST_PROMOCODE);
			if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo())) {
				GeneralInfoVO generalInfo = new GeneralInfoVO();
				String insuredCode = request.getParameter("insuredCode");
				InsuredVO insured = new InsuredVO();

				if (Utils.isEmpty(insuredCode)) {
					insured.setMobileNo(request.getParameter("mobileNumber"));
					insured.setEmailId(request.getParameter("email"));
					insured.setFirstName(request.getParameter("name"));
					generalInfo.setInsured(insured);

				} else {
					insured.setInsuredCode(Long.valueOf(insuredCode));
					insured.setInsuredId(insured.getInsuredCode());
					generalInfo.setInsured(insured);
					PolicyDataVO policyDataVO = new PolicyDataVO();
					policyDataVO.setGeneralInfo(generalInfo);

					commonServiceHandler.loadInsuredDetails(policyDataVO);
					generalInfo = policyDataVO.getGeneralInfo();
				}
				homeInsuranceVO.setGeneralInfo(generalInfo);
			}

			if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())) {
				SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
				homeInsuranceVO.getGeneralInfo().setSourceOfBus(sourceOfBusinessVO);
			}
			if (!(Utils.isEmpty(partnername))) {

				homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName(partnername);
			} else {
				homeInsuranceVO.getGeneralInfo().getSourceOfBus()
						.setDistributionChannel(AppConstants.B2C_DEFAULT_DIST_CHANNEL);
			}
			homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPromoCode(promocode);
			request.setAttribute(com.Constant.CONST_PARTNERNAMECSS, partnername); // For
																					// Css

			if (!(Utils.isEmpty(partnername))) {
				homeInsuranceVO = (HomeInsuranceVO) HomeInsuranceSvcHandler.loadPartnerMgmtDetails(homeInsuranceVO);

				UserProfile userProfile = UserProfileHandler
						.getUserProfileVo(homeInsuranceVO.getGeneralInfo().getExtAccExecCode());
				request.getSession(false).setAttribute(AppConstants.SESSION_USER_PROFILE_VO, userProfile);
				/*
				 * //m1043116 List<String> emailList=null;
				 * System.out.println("partner name "+homeInsuranceVO.
				 * getGeneralInfo().getSourceOfBus().getPartnerName());
				 * if(homeInsuranceVO.getGeneralInfo().getSourceOfBus().
				 * getPartnerName().equalsIgnoreCase("ENBDHome")) { String
				 * emailDomain=homeInsuranceVO.getGeneralInfo().getSourceOfBus()
				 * .getEmailDomain();
				 * System.out.println("email string "+emailDomain); emailList=
				 * Arrays.asList(emailDomain.split(",")); for(int
				 * i=0;i<emailList.size();i++) {
				 * System.out.println("splited domains"+emailList.get(i)); } }
				 * String presentEmail=request.getParameter( "email");
				 * if(emailList.contains(presentEmail)) {
				 * System.out.println("correct email"+presentEmail);
				 * System.out.println(emailList.toString()); }
				 * 
				 * //m1043116
				 */ }

			/*
			 * For VAT 142244 Implementation For getting default vat rate
			 */

			if (Utils.isEmpty(homeInsuranceVO.getPremiumVO())) {
				homeInsuranceVO.setPremiumVO(new PremiumVO());
			}

			double vatTaxPerc = SvcUtils.getLookUpCode("VATTAX", "7", "151", "5");
			if (!Utils.isEmpty(vatTaxPerc)) {
				homeInsuranceVO.getPremiumVO().setVatTaxPerc(vatTaxPerc);
				homeInsuranceVO.setVatTaxPerc(vatTaxPerc);
			}

			if (!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getVatTaxPerc())) {
				request.setAttribute("vatTaxPerc", homeInsuranceVO.getPremiumVO().getVatTaxPerc());
			}

			// }
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.info("Display request for Home Risk cover page completed");

		String errorMsg = request.getParameter("errorMsg");
		String flags=(String)session.getAttribute("HomeLobflags");
		ModelAndView modelAndView = null;
		if (!Utils.isEmpty(errorMsg)) {
			
			System.out.println("UrL"+url);
			
			String flag = "flagstrue";
			Errors errors = bindingResult;
			if (flag.equals(flags))
			{
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
						errorMsg);
			session.removeAttribute("HomeLobflags");
			}	

		}
	
		// changes-HomeRevamp#7.1
		boolean isOldOrNewHomeQuote = false;

		try {
			isOldOrNewHomeQuote = checkPolIssueDate(homeInsuranceVO);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("isOldOrNewHomeQuote", isOldOrNewHomeQuote);
		// changes-HomeRevamp#7.1
		
		if (!(Utils.isEmpty(partnername))
				&& Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerId())) {
			modelAndView = new ModelAndView("redirect:" + url);
		} else {
			modelAndView = new ModelAndView();
			modelAndView.setViewName(com.Constant.CONST_HOMERISKDETAILS);
		}
		modelAndView.addObject(com.Constant.CONST_HOMEINSURANCEVO, homeInsuranceVO);
		// modelAndView.setViewName( com.Constant.CONST_HOMERISKDETAILS );
		return modelAndView;
	}

	@RequestMapping(value = "**/HomeStep2.do", method = RequestMethod.POST)
	public ModelAndView saveHomeCoverDetails(
			@ModelAttribute(com.Constant.CONST_HOMEINSURANCEVO) HomeInsuranceVO homeInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ParseException {

		LOGGER.info("Save request for Home Risk Cover page started");

		boolean completePurchaseInd = false;
		String partnerName = null;

		try {

			setVariablesForOmnSiteCat(homeInsuranceVO, request);

			BaseBeanToBeanMapper<HttpServletRequest, CoverDetails> requestToContentList = BeanMapperFactory
					.getMapperInstance(RequesttoCoverdetailsContentList.class);
			CoverDetails contentList = null;
			contentList = requestToContentList.mapBean(request, contentList);

			if (Utils.isEmpty(request.getAttribute("contSumInsured"))) {
				contentList.setCoverDetails(null);
			} else {
				homeInsuranceVO.getCovers().addAll(contentList.getCoverDetails());
				homeInsuranceVO.getStaffDetails().addAll(contentList.getStaffDetails());
			}
			BaseBeanToBeanMapper<HttpServletRequest, CoverDetails> requestToPPList = BeanMapperFactory
					.getMapperInstance(RequesttoCoverdetailsPPList.class);
			CoverDetails personalPossList = null;
			personalPossList = requestToPPList.mapBean(request, personalPossList);

			if (Utils.isEmpty(request.getAttribute("persPosSumInsured"))) {
				personalPossList.setCoverDetails(null);
			} else {
				homeInsuranceVO.getCovers().addAll(personalPossList.getCoverDetails());
			}
			setRequestAttributes(homeInsuranceVO, request);

			homeInsuranceVO = (HomeInsuranceVO) HomeInsuranceSvcHandler.loadPartnerMgmtDetails(homeInsuranceVO);

			if (!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerName())
					&& (Utils.isEmpty(homeInsuranceVO.getCommonVO())
							|| Utils.isEmpty(homeInsuranceVO.getCommonVO().getLoggedInUser()))) {
				UserProfile userProfile = UserProfileHandler
						.getUserProfileVo(homeInsuranceVO.getGeneralInfo().getExtAccExecCode());
				request.getSession(false).setAttribute(AppConstants.SESSION_USER_PROFILE_VO, userProfile);
				homeInsuranceVO.getCommonVO().setLoggedInUser(userProfile);
			}

			HomeInsuranceSvcHandler.setDefaultValues(homeInsuranceVO);

			/*
			 * For VAT 142244 For Setting vatTax to PremiumVO object
			 */
			double vatTaxPer = 0.0;
			if (!Utils.isEmpty(request.getParameter("vatTaxPer"))) {
				vatTaxPer = Double.parseDouble(request.getParameter("vatTaxPer"));
			}
			if (!Utils.isEmpty(homeInsuranceVO.getPremiumVO())) {
				homeInsuranceVO.getPremiumVO().setVatTaxPerc(vatTaxPer);
				homeInsuranceVO.getCommonVO().getPremiumVO().setVatTaxPerc(vatTaxPer);
				homeInsuranceVO.setVatTaxPerc(vatTaxPer);
			}
			// String partnerId = request.getParameter("partnerId")== null
			// ?"":(String) request.getParameter("partnerId"); /*commented
			// unused variable- sonar violation fix */
			partnerName = request.getParameter(com.Constant.CONST_PARTNERNAME) == null ? ""
					: (String) request.getParameter(com.Constant.CONST_PARTNERNAME);

			// Added by Pushkar for Nexus Home Error page CSS
			if (!Utils.isEmpty(partnerName)) {
				request.setAttribute(com.Constant.CONST_PARTNERNAMECSS, partnerName);
			}

			validatePromotionalCode(homeInsuranceVO, request);
			int size = 0;
			ResourceBundle resourceBundle = ResourceBundle.getBundle("config.appconfig");
			if (!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getMobileNo())
					&& !Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getEmailId())) {
				HibernateTemplate ht = (HibernateTemplate) Utils.getBean("hibernateTemplate");
				Session session1 = ht.getSessionFactory().openSession();

				try {
					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
					String strDate = formatter.format(date);
					String expiryDate = resourceBundle.getString("DEFAULT_POL_VALIDITY_EXPIRY_DATE");
					Integer userId = Integer.parseInt(resourceBundle.getString("USER_6"));
					Integer policyType = Integer.parseInt(resourceBundle.getString("HOME_POL_TYPE"));
					Query query = session1.createSQLQuery(
							"select count(pol_policy_id) from t_mas_cash_customer_quo a join t_trn_policy_quo b on a.csh_policy_id = b.pol_policy_id and  a.csh_validity_expiry_date='"
									+ expiryDate + "' " + "  and b.pol_validity_expiry_date='" + expiryDate + "' "
									+ " and a.csh_prepared_by in  " + "(" + userId + ",992)"
									+ "    and  a.csh_e_email_id='"
									+ homeInsuranceVO.getGeneralInfo().getInsured().getEmailId() + "'"
									+ "    and a.csh_e_gsm_no='"
									+ homeInsuranceVO.getGeneralInfo().getInsured().getMobileNo() + "'"
									+ "    and a.csh_loc_code= " + homeInsuranceVO.getCommonVO().getLocCode()
									+ "    and TRUNC(a.csh_prepared_dt) = '" + strDate.toUpperCase() + "'"
									+ "   and b.pol_policy_type =" + policyType);

					Object o = query.uniqueResult();
					size = ((Number) o).intValue();
				} catch (Exception e) {
					System.out.println(e);
				} finally {
					session1.close();
				}
			}
			if (size >= Integer.parseInt(resourceBundle.getString("B2C_USER_QUOTE_LIMIT_PER_DAY").trim())) {
				bindingResult.rejectValue(com.Constant.CONST_ERRORMESSAGE,
						"homeInsuranceVO.getGeneralInfo().getInsured()",
						"You are not allowed to have more than 5 quotes per day");
				ModelAndView modelAndView = new ModelAndView();
				modelAndView.setViewName(com.Constant.CONST_HOMERISKDETAILS);
				modelAndView.addObject(com.Constant.CONST_HOMEINSURANCEVO, homeInsuranceVO);
				modelAndView.addObject(bindingResult);
				return modelAndView;

				// return new ModelAndView( com.Constant.CONST_HOMERISKDETAILS,
				// com.Constant.CONST_HOMEINSURANCEVO, homeInsuranceVO1 );
			}
			homeInsuranceSvcHandler.saveHomeRiskCoverDetails((PolicyDataVO) homeInsuranceVO, completePurchaseInd,
					request.getRequestURL().toString());

			/*
			 * Setting the remaining parameters(Which we get after Saving the
			 * Risk details), Parameters will be used in Omniture Site Catalyst.
			 */
			if (!Utils.isEmpty(homeInsuranceVO.getQuoteNo())) {
				request.setAttribute("quoteNo", homeInsuranceVO.getQuoteNo());
			}

			if (!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getPremiumAmt())) {
				request.setAttribute("quoteValue",
						Currency.getFormattedCurrency(homeInsuranceVO.getPremiumVO().getPremiumAmt(),
								homeInsuranceVO.getCommonVO().getLob().toString()));
			}

			LOGGER.info("Save request for Home Risk Cover completed");

			AppUtils.setQuoteValidDate(homeInsuranceVO, request);

			if (!Utils.isEmpty(homeInsuranceVO.getReferralVOList())) {

				return loadHomeInsuranceDetails(homeInsuranceVO, bindingResult, request, response, session);
			} else {

				homeInsuranceVO = (HomeInsuranceVO) homeInsuranceSvcHandler.loadHomeInsuranceDetails(homeInsuranceVO);
				if (!Utils.isEmpty(homeInsuranceVO.getBuildingDetails())
						&& !Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getMortgageeName())) {
					String mortgage[] = homeInsuranceVO.getBuildingDetails().getMortgageeName().split("#");
					if (mortgage.length > 1) {
						request.setAttribute(com.Constant.CONST_MORTGAGEOTHERS, mortgage[1]);
					}
					homeInsuranceVO.getBuildingDetails().setMortgageeName(mortgage[0]);
				}
				if (!(Utils.isEmpty(partnerName))) {
					if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo())) {
						GeneralInfoVO generalInfo = new GeneralInfoVO();
						homeInsuranceVO.setGeneralInfo(generalInfo);
					}
					if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())) {
						SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
						sourceOfBusinessVO.setPartnerName(partnerName);
						homeInsuranceVO.getGeneralInfo().setSourceOfBus(sourceOfBusinessVO);
					} else {
						homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName(partnerName);
					}
					homeInsuranceVO = (HomeInsuranceVO) HomeInsuranceSvcHandler.loadPartnerMgmtDetails(homeInsuranceVO);
					// Added by Vishwa to enable the CSS change for partner
					request.setAttribute(com.Constant.CONST_PARTNERNAMECSS, partnerName);
				}

				// changes-HomeR``evamp#7.1
				boolean isOldOrNewHomeQuote = false;

				try {
					isOldOrNewHomeQuote = checkPolIssueDate(homeInsuranceVO);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("isOldOrNewHomeQuote", isOldOrNewHomeQuote);
				// changes-HomeRevamp#7.1
				return new ModelAndView(com.Constant.CONST_HOMEINSUREDDETAILS, com.Constant.CONST_HOMEINSURANCEVO,
						homeInsuranceVO);
			}
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			CommonHandler.renderErrorMessages(bindingResult, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			if (Utils.isEmpty(partnerName)) {
				CommonHandler.renderErrorMessages(bindingResult,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_HOME_ERROR));
			} else {
				CommonHandler.renderErrorMessages(bindingResult,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_HOME_PARTNER_ERROR).replace(
								com.Constant.CONST_CALL_CENTER,
								homeInsuranceVO.getGeneralInfo().getSourceOfBus().getCallCentreNo()));
			}
			LOGGER.error(e.getMessage(), e);
		}
		updateCoverDetails(homeInsuranceVO);
		request.setAttribute(AppConstants.PAGE_VALUE, homeInsuranceVO);
		request.setAttribute(AppConstants.COVERS, homeInsuranceVO.getCovers());
		request.setAttribute(AppConstants.BUILDING, homeInsuranceVO.getBuildingDetails());

		List<CoverDetailsVO> coverDetailsVOConts = new ArrayList<CoverDetailsVO>();
		List<CoverDetailsVO> coverDetailsVOPPs = new ArrayList<CoverDetailsVO>();

		for (CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers()) {

			if (coverDetailsVO.getRiskCodes().getRiskType().equals(SvcConstants.HOME_CONTENT_RISK_TYPE)
					&& coverDetailsVO.getRiskCodes().getRiskCat().equals(SvcConstants.HOME_LIST_ITEM_RISK_CATEGORY)
					&& coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.DEFAULT_COVER_CODE) {

				coverDetailsVOConts.add(coverDetailsVO);

			} else if (coverDetailsVO.getRiskCodes().getRiskType().equals(SvcConstants.HOME_PERSONAL_POS_RISK_TYPE)
					&& coverDetailsVO.getRiskCodes().getRiskCat().equals(SvcConstants.HOME_LIST_ITEM_RISK_CATEGORY)
					&& coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.DEFAULT_COVER_CODE) {
				coverDetailsVOPPs.add(coverDetailsVO);
			}
		}

		try {

			request.setAttribute(com.Constant.CONST_CONTENTLISTITEMS,
					AppUtils.createJSONForHomeListDetails(coverDetailsVOConts));
			request.setAttribute(com.Constant.CONST_PERSPOSLISTITEMS,
					AppUtils.createJSONForHomeListDetails(coverDetailsVOPPs));
			request.setAttribute(com.Constant.CONST_ISLOADOPERATION, "true");
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return new ModelAndView(com.Constant.CONST_HOMERISKDETAILS, com.Constant.CONST_HOMEINSURANCEVO,
				homeInsuranceVO);

	}

	private void updateCoverDetails(PolicyDataVO homeInsuranceData) {

		if (!Utils.isEmpty(((HomeInsuranceVO) homeInsuranceData).getCovers())) {
			Iterator it = ((HomeInsuranceVO) homeInsuranceData).getCovers().iterator();
			while (it.hasNext()) {
				CoverDetailsVO cover = (CoverDetailsVO) it.next();
				if ((Utils.isEmpty(cover.getSumInsured()) || Utils.isEmpty(cover.getSumInsured().getSumInsured())
						|| (cover.getSumInsured().getSumInsured() <= 0
								&& Utils.isEmpty(cover.getSumInsured().geteDesc())))
						&& !AppConstants.STATUS_ON.equalsIgnoreCase(cover.getIsCovered())) {
					it.remove();
				}
			}
		}
	}

	private void validatePromotionalCode(HomeInsuranceVO homeInsuranceVO, HttpServletRequest request) {
		if (!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())
				&& !Utils.isEmpty(homeInsuranceVO.getScheme().getEffDate())
				&& !Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCode())) {

			String promoCodePre = (String) request.getAttribute("promoCodeForVal");
			String promoCodePage = homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCode();

			if (Utils.isEmpty(promoCodePre) || (!Utils.isEmpty(promoCodePre) && !promoCodePre.equals(promoCodePage))) {

				TaskExecutor.executeTasks("VALIDATE_PROMO_CODE", homeInsuranceVO);
			}

		}
	}

	private void setVariablesForOmnSiteCat(HomeInsuranceVO homeInsuranceVO, HttpServletRequest request) {

		if (!Utils.isEmpty(homeInsuranceVO)) {
			if (!Utils.isEmpty(homeInsuranceVO.getBuildingDetails())
					&& !Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getOwnershipStatus())) {
				request.setAttribute("ownerShipStatus", homeInsuranceVO.getBuildingDetails().getOwnershipStatus());
			}

			if (!Utils.isEmpty(homeInsuranceVO.getGeneralInfo())
					&& !Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())
					&& !Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCode())) {
				request.setAttribute(com.Constant.CONST_PROMOCODE,
						homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCode());
			}

			if (!Utils.isEmpty(homeInsuranceVO.getBuildingDetails())
					&& !Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getSumInsured())
					&& !Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured())) {
				request.setAttribute("buildSumInsured", AppUtils.getFormattedDoubleWithDecimals(
						homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured(), 0));
			}

			if (!Utils.isEmpty(homeInsuranceVO.getCovers())) {

				if (!Utils.isEmpty(homeInsuranceVO.getCovers().get(0))
						&& !Utils.isEmpty(homeInsuranceVO.getCovers().get(0).getSumInsured())
						&& !Utils.isEmpty(homeInsuranceVO.getCovers().get(0).getSumInsured().getSumInsured())) {
					request.setAttribute("contSumInsured", AppUtils.getFormattedDoubleWithDecimals(
							homeInsuranceVO.getCovers().get(0).getSumInsured().getSumInsured(), 0));
				}

				if ((homeInsuranceVO.getCovers().size() > 1) && !Utils.isEmpty(homeInsuranceVO.getCovers().get(1))
						&& !Utils.isEmpty(homeInsuranceVO.getCovers().get(1).getSumInsured())
						&& !Utils.isEmpty(homeInsuranceVO.getCovers().get(1).getSumInsured().getSumInsured())) {
					request.setAttribute("persPosSumInsured", AppUtils.getFormattedDoubleWithDecimals(
							homeInsuranceVO.getCovers().get(1).getSumInsured().getSumInsured(), 0));
				}

			}
		}
	}

	/*
	 * private void updateCoverDetails(PolicyDataVO homeInsuranceData){
	 * 
	 * Iterator it =
	 * ((HomeInsuranceVO)homeInsuranceData).getCovers().iterator();
	 * while(it.hasNext()){ CoverDetailsVO cover = (CoverDetailsVO) it.next();
	 * if( (Utils.isEmpty( cover.getSumInsured() ) || Utils.isEmpty(
	 * cover.getSumInsured().getSumInsured() ) || (
	 * cover.getSumInsured().getSumInsured() <= 0 && Utils.isEmpty(
	 * cover.getSumInsured().geteDesc() ) ) ) &&
	 * !AppConstants.STATUS_ON.equalsIgnoreCase( cover.getIsCovered() ) ){
	 * it.remove(); } } }
	 */

	@RequestMapping(value = "**/saveHomeInsuredDetails.do", method = RequestMethod.POST)
	public ModelAndView saveHomeBuildingDetails(
			@ModelAttribute(com.Constant.CONST_HOMEINSURANCEVO) HomeInsuranceVO homeInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		try {
			LOGGER.info("Save request for Home Insured and building details started");
			boolean completePurchaseInd = false;
			boolean isPrintCase = false;
			setRequestAttributes(homeInsuranceVO, request);
			if (!Utils.isEmpty(request.getParameter("sendMail"))) {
				isPrintCase = true;
			}
			homeInsuranceVO = (HomeInsuranceVO) homeInsuranceSvcHandler.saveHomeInsuredDetails(
					(PolicyDataVO) homeInsuranceVO, completePurchaseInd, request.getRequestURL().toString(),
					isPrintCase);

			AppUtils.setBuildingDropDown(homeInsuranceVO, request);
			AppUtils.setQuoteValidDate(homeInsuranceVO, request);
			LOGGER.info("Save request for Home Insured and building details complete_1");
			request.setAttribute(com.Constant.CONST_PARTNERNAMECSS,
					homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerName()); // For
																							// Css

			if (!Utils.isEmpty(homeInsuranceVO.getReferralVOList())) {
				return loadHomeInsuranceDetails(homeInsuranceVO, bindingResult, request, response, session); // TODO
																												// -
																												// change
																												// to
																												// load
																												// of
																												// second
																												// page
			} else if (!isPrintCase) {
				request.setAttribute("quoteIssued", true);
			} else if (isPrintCase) {
				request.setAttribute("isPrintCase", true);
			}
			// changes-HomeRevamp#7.1
			boolean isOldOrNewHomeQuote = false;

			try {
				isOldOrNewHomeQuote = checkPolIssueDate(homeInsuranceVO);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("isOldOrNewHomeQuote", isOldOrNewHomeQuote);
			// changes-HomeRevamp#7.1
		} catch (BusinessException e) {
			e.printStackTrace();
			// CommonHandler.renderErrorMessages( bindingResult, e.getMessage()
			// );
			LOGGER.error(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			CommonHandler.renderErrorMessages(bindingResult,
					Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_HOME_ERROR));
			LOGGER.error(e.getMessage(), e);
		}
		return new ModelAndView(com.Constant.CONST_HOMEINSUREDDETAILS, com.Constant.CONST_HOMEINSURANCEVO,
				homeInsuranceVO);
	}

	@RequestMapping(value = "**/HomeStep3.do", method = RequestMethod.POST)
	public ModelAndView purchasePolicy(
			@ModelAttribute(com.Constant.CONST_HOMEINSURANCEVO) HomeInsuranceVO homeInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		try {
			LOGGER.info(
					"Entered HomeController.purchasePolicy method, Save request for Home Insured and building details started");
			boolean completePurchaseInd = true;
			String partnerId = request.getParameter("partnerId") == null ? ""
					: (String) request.getParameter("partnerId");
			String partnerName = request.getParameter(com.Constant.CONST_PARTNERNAME) == null ? ""
					: (String) request.getParameter(com.Constant.CONST_PARTNERNAME);

			LOGGER.info("Entered HomeController.purchasePolicy method, partnerId:" + partnerId + " PartnerName: "
					+ partnerName);
			setRequestAttributes(homeInsuranceVO, request);
			LOGGER.info(
					"HomeController.purchasePolicy method, calling HomeInsuranceSvcHandler.saveHomeInsuredDetails method, -starts.");
			PolicyDataVO policyDataVO = homeInsuranceSvcHandler.saveHomeInsuredDetails((PolicyDataVO) homeInsuranceVO,
					completePurchaseInd, request.getRequestURL().toString(), false);
			LOGGER.info("Calling HomeInsuranceSvcHandler.saveHomeInsuredDetails method, -completes.");

			if (!(Utils.isEmpty(partnerId))) {
				if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo())) {
					GeneralInfoVO generalInfo = new GeneralInfoVO();
					homeInsuranceVO.setGeneralInfo(generalInfo);
				}
				if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())) {
					SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
					homeInsuranceVO.getGeneralInfo().setSourceOfBus(sourceOfBusinessVO);
				}
				if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus())) {
					SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
					policyDataVO.getGeneralInfo().setSourceOfBus(sourceOfBusinessVO);
				}
				homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerId(partnerId);
				policyDataVO.getGeneralInfo().getSourceOfBus().setPartnerId(partnerId);
				homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName(partnerName);
				policyDataVO.getGeneralInfo().getSourceOfBus().setPartnerName(partnerName);
				// Added by Vishwa to enable the CSS change for partner
				request.setAttribute(com.Constant.CONST_PARTNERNAMECSS, partnerName);
			}
			LOGGER.info("Save request for Home Insured and building details complete_2");
			if (!Utils.isEmpty(homeInsuranceVO.getReferralVOList())) {
				LOGGER.info("Before calling HomeController.loadHomeInsuranceDetails method.");
				return loadHomeInsuranceDetails(homeInsuranceVO, bindingResult, request, response, session); // TODO
																												// -
																												// change
																												// to
																												// load
																												// of
																												// second
																												// page
			} else {
				LOGGER.info("Make payment by calling Payment Gateway started");
				return commonCtrl.makePayment(request, policyDataVO);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
			if (e.getMessage().contains("Premium Mismatch.")) {
				ModelAndView mav = new ModelAndView("paymentError", "quoteNo",
						homeInsuranceVO.getCommonVO().getQuoteNo());
				mav.addObject("errorMessage", Utils.getAppErrorMessage("pasb2c.partial.payment.error"));
				return mav;
			}
			// CommonHandler.renderErrorMessages( bindingResult, e.getMessage()
			// );
			LOGGER.error(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerId())) {
				CommonHandler.renderErrorMessages(bindingResult,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_HOME_ERROR));
			} else {
				CommonHandler.renderErrorMessages(bindingResult,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_HOME_PARTNER_ERROR).replace(
								com.Constant.CONST_CALL_CENTER,
								homeInsuranceVO.getGeneralInfo().getSourceOfBus().getCallCentreNo()));
			}
			LOGGER.error(e.getMessage(), e);
		}
		return new ModelAndView(com.Constant.CONST_HOMEINSUREDDETAILS, com.Constant.CONST_HOMEINSURANCEVO,
				homeInsuranceVO);
	}

	@RequestMapping(value = "**/getHomeInsuranceDetails.do")
	public ModelAndView loadHomeInsuranceDetails(
			@ModelAttribute(com.Constant.CONST_HOMEINSURANCEVO) HomeInsuranceVO homeInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		LOGGER.info("Load request for Home Insurance details started");
		CommonVO commonVO = null;
		HomeInsuranceVO homeinInsuranceVO = null;
		UserProfile userProfile = null;
		String quoteNo = null;
		
		
		String partnername = request.getParameter(com.Constant.CONST_PARTNERNAME) == null ? ""
				: (String) request.getParameter(com.Constant.CONST_PARTNERNAME);
		// m1043116-emailDomain masking for ENBD
		String emailDomain = (String) request.getParameter("emailDomain");
		if (Utils.isEmpty(partnername)) {
			partnername = request.getRequestURI();
			if (!Utils.isEmpty(homeInsuranceVO.getReferralVOList())) {
				partnername = request.getRequestURI().replace(com.Constant.CONST_QUOTEANDBUY_END, "")
						.replace("/HomeStep2.do", "").replace("HomeStep2.do", "");
			} else {
				partnername = request.getRequestURI().replace(com.Constant.CONST_QUOTEANDBUY_END, "")
						.replace("/getHomeInsuranceDetails.do", "").replace("getHomeInsuranceDetails.do", "");
			}

		}
		// String partnerId = request.getParameter("partnerId")== null
		// ?"":(String) request.getParameter("partnerId"); /* commented unused
		// variable - sonar violation fix*/
		// String partnerName =
		// request.getParameter(com.Constant.CONST_PARTNERNAME)== null
		// ?"":(String) request.getParameter(com.Constant.CONST_PARTNERNAME);
		request.setAttribute(com.Constant.CONST_PARTNERNAMECSS, partnername); // For
																				// Css
		try {
			setLocation();

			// Code added for if CLICK HERE from email start
			if (!Utils.isEmpty(request.getParameter(AppConstants.QUOTE_NUM_REQ_PARAM))
					&& !Utils.isEmpty(request.getParameter(AppConstants.EMAIL_REQ_PARAM))) {

				Long quoteNumber = null;
				String emailid = null;
				if (!Utils.isEmpty(request.getParameter(AppConstants.DRUPAL_REQ_PARAM))) {
					quoteNumber = new Long(request.getParameter(AppConstants.QUOTE_NUM_REQ_PARAM));
					emailid = request.getParameter(AppConstants.EMAIL_REQ_PARAM);
				} else {

					quoteNumber = new Long(AppUtils.encryptAndDecryptData(
							request.getParameter(AppConstants.QUOTE_NUM_REQ_PARAM), Boolean.FALSE));
					emailid = AppUtils.encryptAndDecryptData(request.getParameter(AppConstants.EMAIL_REQ_PARAM),
							Boolean.FALSE);
				}
				commonVO = new CommonVO();
				commonVO.setQuoteNo(quoteNumber);
				homeInsuranceVO.setCommonVO(commonVO);
				GeneralInfoVO generalInfoVO = new GeneralInfoVO();
				InsuredVO insuredVO = new InsuredVO();
				insuredVO.setEmailId(emailid);
				generalInfoVO.setInsured(insuredVO);
				homeInsuranceVO.setGeneralInfo(generalInfoVO);
			}
			// Code added for if CLICK HERE from email end
			if (!(Utils.isEmpty(partnername))) {
				if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo())) {
					GeneralInfoVO generalInfo = new GeneralInfoVO();
					homeInsuranceVO.setGeneralInfo(generalInfo);
				}
				if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())) {
					SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
					homeInsuranceVO.getGeneralInfo().setSourceOfBus(sourceOfBusinessVO);
				}
				homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName(partnername);
			}

			setRequestAttributes(homeInsuranceVO, request);
			if (!(Utils.isEmpty(partnername))) {
				LOGGER.info(
						"HomeController.loadHomeInsuranceDetails method, before calling HomeController.loadPartnerMgmtDetails method.");
				homeInsuranceVO = (HomeInsuranceVO) HomeInsuranceSvcHandler.loadPartnerMgmtDetails(homeInsuranceVO);
				if (Utils.isEmpty(request.getSession(false).getAttribute(AppConstants.SESSION_USER_PROFILE_VO))) {
					userProfile = UserProfileHandler
							.getUserProfileVo(homeInsuranceVO.getGeneralInfo().getExtAccExecCode());
				} else {
					userProfile = (UserProfile) request.getSession(false)
							.getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
				}

			} else {
				userProfile = new UserProfile();
			}
			request.getSession(false).setAttribute(AppConstants.SESSION_USER_PROFILE_VO, userProfile);

			homeInsuranceVO.getCommonVO().setLoggedInUser(
					(User) request.getSession(false).getAttribute(AppConstants.SESSION_USER_PROFILE_VO));
			homeInsuranceVO.setLoggedInUser(
					(User) request.getSession(false).getAttribute(AppConstants.SESSION_USER_PROFILE_VO));
			HomeInsuranceVO homeInsuranceVOToService = CopyUtils.copySerializableObject(homeInsuranceVO);
			LOGGER.info(
					"HomeController.loadHomeInsuranceDetails method, before calling HomeInsuranceSvcHandler.loadHomeInsuranceDetails method.");
			PolicyDataVO policyDataVO = homeInsuranceSvcHandler.loadHomeInsuranceDetails(homeInsuranceVOToService);

			if (!(Utils.isEmpty(partnername))) {
				if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo())) {
					GeneralInfoVO generalInfo = new GeneralInfoVO();
					homeInsuranceVO.setGeneralInfo(generalInfo);
				}
				if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())) {
					SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
					homeInsuranceVO.getGeneralInfo().setSourceOfBus(sourceOfBusinessVO);
				}
				homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName(partnername);
				// m1043116-start
				homeInsuranceVO.getGeneralInfo().getSourceOfBus().setEmailDomain(emailDomain);
				// m1043116-end
				homeInsuranceVO = (HomeInsuranceVO) HomeInsuranceSvcHandler.loadPartnerMgmtDetails(homeInsuranceVO);
				if (Utils.isEmpty(request.getSession(false).getAttribute(AppConstants.SESSION_USER_PROFILE_VO))) {
					// UserProfile userProfile = new UserProfile();
					userProfile = UserProfileHandler
							.getUserProfileVo(homeInsuranceVO.getGeneralInfo().getExtAccExecCode());
					request.getSession(false).setAttribute(AppConstants.SESSION_USER_PROFILE_VO, userProfile);
				}
			}
			if (!(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus()))
					&& !(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerId()))) {
				if (!Utils.isEmpty(policyDataVO)) {
					if (Utils.isEmpty(policyDataVO.getGeneralInfo())) {
						policyDataVO.setGeneralInfo(homeInsuranceVO.getGeneralInfo());
					}
					if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus())) {
						policyDataVO.getGeneralInfo().setSourceOfBus(homeInsuranceVO.getGeneralInfo().getSourceOfBus());
					}
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setPartnerId(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerId());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setPartnerName(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerName());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setCallCentreNo(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setReplyToEmailId(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getReplyToEmailId());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setCcEmailId(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getCcEmailId());
					policyDataVO.getGeneralInfo().getSourceOfBus().setSourceOfBusiness(
							homeInsuranceVO.getGeneralInfo().getSourceOfBus().getSourceOfBusiness());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setFromEmailID(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getFromEmailID());
					policyDataVO.getGeneralInfo().getSourceOfBus().setDefaultOnlineDiscount(
							homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount());
					policyDataVO.getGeneralInfo().getSourceOfBus().setDefaultAssignToUser(
							homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDefaultAssignToUser());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setFaqUrl(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getFaqUrl());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setPolicyTermUrl(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPolicyTermUrl());
					// m1043116-start
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setEmailDomain(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getEmailDomain());
					// m1043116-end

					policyDataVO.setLoggedInUser(
							(User) request.getSession(false).getAttribute(AppConstants.SESSION_USER_PROFILE_VO));
				}
			}

			if (!Utils.isEmpty(homeInsuranceVO.getBuildingDetails())
					&& !Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getMortgageeName())) {
				String mortgage[] = homeInsuranceVO.getBuildingDetails().getMortgageeName().split("#");
				if (mortgage.length > 1) {
					request.setAttribute(com.Constant.CONST_MORTGAGEOTHERS, mortgage[1]);
				}
				homeInsuranceVO.getBuildingDetails().setMortgageeName(mortgage[0]);
			}

			if (AppUtils.inValidEmailId(policyDataVO, homeInsuranceVO) || Utils.isEmpty(policyDataVO)
					|| !AppUtils.isValidDistributionChannel(policyDataVO, homeInsuranceVO.getGeneralInfo())) {
				Errors errors = bindingResult;
				// homeInsuranceVO.getCommonVO().setQuoteNo( null );
				homeInsuranceVO.getGeneralInfo().getInsured().setEmailId(null);
				homeInsuranceVO.getCommonVO().setQuoteNo(null);
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
						AppConstants.INVALID_QUOTE);
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
						AppConstants.INVALID_EMAIL);
			}
			LOGGER.info("Load request for Home Insurance completed");

			if (!Utils.isEmpty(policyDataVO) && !bindingResult.hasErrors()) {
				request.setAttribute(com.Constant.CONST_HOMEINSURANCEVO, (HomeInsuranceVO) policyDataVO);
				homeinInsuranceVO = (HomeInsuranceVO) policyDataVO;
				request.setAttribute(AppConstants.PAGE_VALUE, homeinInsuranceVO);
				request.setAttribute(AppConstants.COVERS, homeinInsuranceVO.getCovers());
				request.setAttribute(AppConstants.BUILDING, homeinInsuranceVO.getBuildingDetails());

				if (!Utils.isEmpty(homeinInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCode())) {
					request.setAttribute(com.Constant.CONST_PROMOCODE,
							homeinInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCode());
				}
				// ##START - Added by Dinesh for CR-130750 Royalty feature
				quoteNo = request.getParameter(AppConstants.QUOTE_NUM_REQ_PARAM);
				if (!Utils.isEmpty(quoteNo) && StringUtils.isNumeric(quoteNo)
						&& !Utils.isEmpty(request.getParameter(AppConstants.EMAIL_REQ_PARAM))) {
					if (!Utils.isEmpty(request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM))) {
						LOGGER.info("Going to set PROM CODE VALUE: "
								+ request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM));
						homeinInsuranceVO.getGeneralInfo().getSourceOfBus()
								.setPromoCode(request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM));
					} else {
						LOGGER.info("Going to set PROM CODE VALUE: Empty");
						homeinInsuranceVO.getGeneralInfo().getSourceOfBus().setPromoCode("");
					}
				} // ## END
				List<CoverDetailsVO> coverDetailsVOConts = new ArrayList<CoverDetailsVO>();
				List<CoverDetailsVO> coverDetailsVOPPs = new ArrayList<CoverDetailsVO>();
				List<StaffDetailsVO> staffDetails = new ArrayList<StaffDetailsVO>();

				for (CoverDetailsVO coverDetailsVO : homeinInsuranceVO.getCovers()) {

					if (coverDetailsVO.getRiskCodes().getRiskType().equals(SvcConstants.HOME_CONTENT_RISK_TYPE)
							&& coverDetailsVO.getRiskCodes().getRiskCat().equals(2)
							&& coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.DEFAULT_COVER_CODE) {

						coverDetailsVOConts.add(coverDetailsVO);

					} else if (coverDetailsVO.getRiskCodes().getRiskType()
							.equals(SvcConstants.HOME_PERSONAL_POS_RISK_TYPE)
							&& coverDetailsVO.getRiskCodes().getRiskCat().equals(2)
							&& coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.DEFAULT_COVER_CODE) {

						coverDetailsVOPPs.add(coverDetailsVO);

					}
				}
				staffDetails.addAll(homeinInsuranceVO.getStaffDetails());

				request.setAttribute(com.Constant.CONST_CONTENTLISTITEMS,
						AppUtils.createJSONForHomeListDetails(coverDetailsVOConts));
				request.setAttribute(com.Constant.CONST_PERSPOSLISTITEMS,
						AppUtils.createJSONForHomeListDetails(coverDetailsVOPPs));
				request.setAttribute("staffDetailsListItems", AppUtils.createJSONForStaffListDetails(staffDetails));
				request.setAttribute(com.Constant.CONST_ISLOADOPERATION, "true");

				// request.setAttribute( AppConstants.PROMOTIONAL_CODES,
				// promoCodeDetails.get( "promotionalCodes" ) );
				// request.setAttribute( AppConstants.PROMOTIONAL_DISC,
				// promoCodeDetails.get( "promoDiscount" ) );
				if (!Utils.isEmpty(policyDataVO)) {
					commonVO = policyDataVO.getCommonVO();

					if (!Utils.isEmpty(commonVO)) {
						homeInsuranceVO.setCommonVO(commonVO);
					}
				}

				/* Set the referral message when the status is referred */
				if (AppUtils.isReferred(commonVO)) {
					ReferralUtils.setReferralMessage(bindingResult, policyDataVO);
				}
				if (commonVO.getStatus().equals(AppConstants.CONVERTED_TO_POL_STATUS)) {
					Errors errors = bindingResult;
					String errorMessage = null;
					if (Utils.isEmpty(homeinInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerId())) {

						errorMessage = Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_POLICYQUOTE_RETRIEVED)
								+ commonVO.getPolicyNo().toString() + ". "
								+ Utils.getAppErrorMessage("pasb2c.assistance");
					} else {
						errorMessage = Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_POLICYQUOTE_RETRIEVED)
								+ commonVO.getPolicyNo().toString() + ". "
								+ Utils.getAppErrorMessage("pasb2c.partner.assistance").replace(
										com.Constant.CONST_CALL_CENTER,
										homeinInsuranceVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
					}
					errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
							errorMessage);
				}
				if (Short.valueOf(Utils.getSingleValueAppConfig("REN_QUO_DOC_CODE"))
						.equals(policyDataVO.getCommonVO().getDocCode())) {
					String contextPath = request.getContextPath();
					response.sendRedirect(contextPath + "/HomeRenewalStep1.do?renQuote=" + AppUtils
							.encryptAndDecryptData(policyDataVO.getCommonVO().getQuoteNo().toString(), Boolean.TRUE));
				}

				//
			} else {
				homeinInsuranceVO = new HomeInsuranceVO();
				/*
				 * if(Utils.isEmpty(homeinInsuranceVO.getScheme())){
				 * homeinInsuranceVO.setScheme(new SchemeVO()); } if
				 * (!(Utils.isEmpty(partnername))) {
				 * 
				 * if (Utils.isEmpty(homeinInsuranceVO.getGeneralInfo())) {
				 * GeneralInfoVO generalInfo = new GeneralInfoVO();
				 * homeinInsuranceVO.setGeneralInfo(generalInfo); }
				 * if(Utils.isEmpty(homeinInsuranceVO.getGeneralInfo().
				 * getSourceOfBus())) { SourceOfBusinessVO sourceOfBusinessVO =
				 * new SourceOfBusinessVO();
				 * homeinInsuranceVO.getGeneralInfo().setSourceOfBus(
				 * sourceOfBusinessVO); }
				 * homeinInsuranceVO.getGeneralInfo().getSourceOfBus().
				 * setPartnerName(
				 * request.getParameter(com.Constant.CONST_PARTNERNAME) );
				 * 
				 * homeinInsuranceVO = (HomeInsuranceVO) HomeInsuranceSvcHandler
				 * .loadPartnerMgmtDetails(homeinInsuranceVO);
				 * 
				 * }
				 */
				// m1043116-email domain masking ENBD
				request.setAttribute("emailDomain", emailDomain);
				setRequestAttributes(homeinInsuranceVO, request);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			CommonHandler.renderErrorMessages(bindingResult, e.getMessage());
			LOGGER.error(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())
					|| Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerId())) {
				CommonHandler.renderErrorMessages(bindingResult,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_HOME_ERROR));
			} else {
				homeinInsuranceVO = homeInsuranceVO;
				CommonHandler.renderErrorMessages(bindingResult,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_HOME_PARTNER_ERROR).replace(
								com.Constant.CONST_CALL_CENTER,
								homeInsuranceVO.getGeneralInfo().getSourceOfBus().getCallCentreNo()));
			}
			LOGGER.error(e.getMessage(), e);
		}
		// changes-HomeRevamp#7.1
		boolean isOldOrNewHomeQuote = false;

		try {
			isOldOrNewHomeQuote = checkPolIssueDate(homeInsuranceVO);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("isOldOrNewHomeQuote", isOldOrNewHomeQuote);
		// changes-HomeRevamp#7.1
		return new ModelAndView(com.Constant.CONST_HOMERISKDETAILS, com.Constant.CONST_HOMEINSURANCEVO,
				homeinInsuranceVO);
	}

	@RequestMapping(value = "**/retryHomePayment.do", method = RequestMethod.POST)
	public ModelAndView retryHomePayment(@ModelAttribute("commonVO") CommonVO commonVO, BindingResult bindingResult,
			HttpServletRequest request, HttpSession session) {
		try {
			HomeInsuranceVO homeInsuranceVO = new HomeInsuranceVO();
			homeInsuranceVO.setCommonVO(commonVO);
			setRequestAttributes(homeInsuranceVO, request);
			PolicyDataVO policyDataVO = homeInsuranceSvcHandler.loadHomeInsuranceDetails(homeInsuranceVO);
			LOGGER.info("Retry payment by calling Payment Gateway started");
			return commonCtrl.makePayment(request, policyDataVO);
		} catch (BusinessException e) {
			CommonHandler.renderErrorMessages(bindingResult, e.getMessage());
			LOGGER.error(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			CommonHandler.renderErrorMessages(bindingResult,
					Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_HOME_ERROR));
			LOGGER.error(e.getMessage(), e);
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("commonVO", commonVO);
		modelAndView.setViewName("homePaymentError");
		return modelAndView;
	}

	private void setRequestAttributes(HomeInsuranceVO homeInsuranceVO, HttpServletRequest request) {

		if (Utils.isEmpty(homeInsuranceVO.getCommonVO())) {
			homeInsuranceVO.setCommonVO(new CommonVO());
		}
		;

		if (Utils.isEmpty(request.getSession(false).getAttribute(AppConstants.SESSION_USER_PROFILE_VO))
				&& !Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getExtAccExecCode())) {
			UserProfile userProfile = UserProfileHandler
					.getUserProfileVo(homeInsuranceVO.getGeneralInfo().getExtAccExecCode());
			request.getSession(false).setAttribute(AppConstants.SESSION_USER_PROFILE_VO, userProfile);
		}
		homeInsuranceVO.getCommonVO()
				.setLoggedInUser((User) request.getSession(false).getAttribute(AppConstants.SESSION_USER_PROFILE_VO));
		String mortgageOthers = request.getParameter(com.Constant.CONST_MORTGAGEOTHERS);

		if (!Utils.isEmpty(mortgageOthers) && !Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getMortgageeName())
				&& AppConstants.MORTGAGE_OTHERS_CODE
						.equalsIgnoreCase(homeInsuranceVO.getBuildingDetails().getMortgageeName())) {
			/*
			 * if(mortgageOthers.equalsIgnoreCase( "Enter mortgage name" )){
			 * //homeInsuranceVO.getBuildingDetails().setMortgageeName( null );
			 * mortgageOthers = null; }else{
			 */
			homeInsuranceVO.getBuildingDetails()
					.setMortgageeName(homeInsuranceVO.getBuildingDetails().getMortgageeName() + "#" + mortgageOthers);
			// }
		}

		/*
		 * Added the below condition since the building section is not validated
		 * on Save For Later
		 */
		if (Utils.isEmpty(mortgageOthers) && !Utils.isEmpty(homeInsuranceVO)
				&& !Utils.isEmpty(homeInsuranceVO.getBuildingDetails())
				&& !Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getMortgageeName()) && homeInsuranceVO
						.getBuildingDetails().getMortgageeName().equalsIgnoreCase(AppConstants.MORTGAGE_OTHERS_CODE)) {
			homeInsuranceVO.getBuildingDetails().setMortgageeName(null);
		}
		homeInsuranceVO.getCommonVO().setIsQuote(Boolean.TRUE);
		homeInsuranceVO.getCommonVO().setLob(LOB.HOME);
		if (Utils.isEmpty(homeInsuranceVO.getScheme())) {
			homeInsuranceVO.setScheme(new SchemeVO());
		}
		if (!(Utils.isEmpty(request.getParameter(com.Constant.CONST_PARTNERNAME)))) {

			if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo())) {
				GeneralInfoVO generalInfo = new GeneralInfoVO();
				homeInsuranceVO.setGeneralInfo(generalInfo);
			}
			if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())) {
				SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
				homeInsuranceVO.getGeneralInfo().setSourceOfBus(sourceOfBusinessVO);
			}
			homeInsuranceVO.getGeneralInfo().getSourceOfBus()
					.setPartnerName(request.getParameter(com.Constant.CONST_PARTNERNAME));

			homeInsuranceVO = (HomeInsuranceVO) HomeInsuranceSvcHandler.loadPartnerMgmtDetails(homeInsuranceVO);

		}
		if (!Utils.isEmpty(homeInsuranceVO) && !Utils.isEmpty(homeInsuranceVO.getGeneralInfo())
				&& !Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured())) {
			if (!Utils.isEmpty(homeInsuranceVO.getCommonVO())
					&& (Short.valueOf(Utils.getSingleValueAppConfig("REN_QUO_DOC_CODE"))
							.equals(homeInsuranceVO.getCommonVO().getDocCode()))) {
				homeInsuranceVO.getGeneralInfo().getInsured().setBusType(AppConstants.BUS_TYPE_RENEWAL);
			} else {
				homeInsuranceVO.getGeneralInfo().getInsured().setBusType(AppConstants.BUS_TYPE_NEW);
			}
		}
		/*
		 * For VAT 142244 For setting vatTAx
		 */
		if (!(Utils.isEmpty(request.getParameter(com.Constant.CONST_VATTAX)))) {
			homeInsuranceVO.getPremiumVO()
					.setVatTax(Double.parseDouble(request.getParameter(com.Constant.CONST_VATTAX)));
			homeInsuranceVO.getPremiumVO().setPremiumAmt(homeInsuranceVO.getPremiumVO().getPremiumAmt()
					+ (Double.parseDouble(request.getParameter(com.Constant.CONST_VATTAX))));
		}

	}

	/**
	 * Method to trigger an email in-case of user clicks on request to call back
	 * 
	 * @param homeInsuranceVO
	 * @param bindingResult
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "**/requestHomeCallBack.do")
	public ModelAndView requestHomeCallBack(
			@ModelAttribute(com.Constant.CONST_HOMEINSURANCEVO) HomeInsuranceVO homeInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request, HttpSession session) {
		ModelAndView modelAndView = null;
		try {
			if (!Utils.isEmpty(request.getParameter("call_back_num"))) {
				if (Utils.isEmpty(homeInsuranceVO.getCommonVO())) {
					homeInsuranceVO.setCommonVO(new CommonVO());
				}
				if (Utils.isEmpty(homeInsuranceVO.getScheme())) {
					SchemeVO schemeVo = new SchemeVO();
					homeInsuranceVO.setScheme(schemeVo);
				}
				homeInsuranceVO.getScheme().setSchemeCode(Integer.valueOf(AppConstants.B2C_HOME_DEFAULT_SCHEME));
				homeInsuranceVO.getScheme().setTariffCode(Integer.valueOf(AppConstants.B2C_HOME_DEFAULT_TARIFF));
				if (!(Utils.isEmpty(request.getParameter(com.Constant.CONST_PARTNERNAME)))) {

					if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo())) {
						GeneralInfoVO generalInfo = new GeneralInfoVO();
						homeInsuranceVO.setGeneralInfo(generalInfo);
					}
					if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())) {
						SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
						homeInsuranceVO.getGeneralInfo().setSourceOfBus(sourceOfBusinessVO);
					}
					homeInsuranceVO.getGeneralInfo().getSourceOfBus()
							.setPartnerName(request.getParameter(com.Constant.CONST_PARTNERNAME));

					homeInsuranceVO.getCommonVO().setIsQuote(Boolean.TRUE);
					homeInsuranceVO.getCommonVO().setLob(LOB.HOME);
					if (Utils.isEmpty(homeInsuranceVO.getScheme())) {
						homeInsuranceVO.setScheme(new SchemeVO());
					}
					homeInsuranceVO = (HomeInsuranceVO) HomeInsuranceSvcHandler.loadPartnerMgmtDetails(homeInsuranceVO);

				}
				// CommonHandler.populateCallBackEmail(request.getParameter("call_back_num"));
				CommonHandler.populateAndTriggerEmail(homeInsuranceVO, null, B2CEmailTriggers.REQUEST_CALL_BACK,
						request.getParameter("call_back_num"));
				setRequestAttributes(homeInsuranceVO, request);
			}
			if (!Utils.isEmpty(request.getParameter(com.Constant.CONST_SCREEN))
					&& request.getParameter(com.Constant.CONST_SCREEN).toString().equalsIgnoreCase("risk")) {
				modelAndView = new ModelAndView(com.Constant.CONST_HOMERISKDETAILS, com.Constant.CONST_HOMEINSURANCEVO,
						homeInsuranceVO);
			} else if (!Utils.isEmpty(request.getParameter(com.Constant.CONST_SCREEN))
					&& request.getParameter(com.Constant.CONST_SCREEN).toString().equalsIgnoreCase("insured")) {
				modelAndView = new ModelAndView(com.Constant.CONST_HOMEINSUREDDETAILS,
						com.Constant.CONST_HOMEINSURANCEVO, homeInsuranceVO);
			} else {
				// modelAndView = new ModelAndView( "homeRenewal",
				// com.Constant.CONST_HOMEINSURANCEVO,homeInsuranceVO );
				return loadHomeRenewalInsuranceDetails(homeInsuranceVO, bindingResult, request, session);
			}
		} catch (Exception e) {
			e.printStackTrace();
			CommonHandler.renderErrorMessages(bindingResult,
					Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_HOME_ERROR));
			LOGGER.error(e.getMessage(), e);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/HomeRenewalStep1.do")
	public ModelAndView loadHomeRenewalInsuranceDetails(
			@ModelAttribute(com.Constant.CONST_HOMEINSURANCEVO) HomeInsuranceVO homeInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request, HttpSession session) {

		LOGGER.info("Load request for Home Insurance renewal details started");
		LOGGER.info("Test error message:" + homeInsuranceVO.getErrorMessage());
		HomeInsuranceVO insuranceVO = homeInsuranceVO;
		String renQuote = null;
		try {
			setLocation();
			// ##START - Added by Dinesh for CR-130750 Royalty feature
			if (!Utils.isEmpty(request.getParameter(AppConstants.RENEWAL_QUOTE_NUM_REQ_PARAM))) {
				renQuote = request.getParameter(AppConstants.RENEWAL_QUOTE_NUM_REQ_PARAM);
				if (!Utils.isEmpty(renQuote) && !StringUtils.isNumeric(renQuote)) {
					renQuote = AppUtils.encryptAndDecryptData(renQuote, Boolean.FALSE);
				}
			} // #END

			setRequestAttributesForRenewal(homeInsuranceVO, request, session);
		} catch (Exception e) {
			Errors errors = bindingResult;			
			errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
					com.Constant.CONST_ERROR_END + e.getMessage());
			ModelAndView view = new ModelAndView();
			
			session.setAttribute("HomeLobflags", "flagstrue");
			view.addObject("errorMsg", e.getMessage());
			view.setViewName("redirect:HomeStep1.do");
			return view;
		}

		CommonVO commonVO = null;
		HomeInsuranceVO homeinInsuranceVO = null;

		// HomeInsuranceVO homeInsuranceVOToService =
		// CopyUtils.copySerializableObject( homeInsuranceVO );
		PolicyDataVO policyDataVO = homeInsuranceSvcHandler.loadHomeInsuranceDetails(homeInsuranceVO);

		if (Utils.isEmpty(policyDataVO)) {
			Errors errors = bindingResult;
			homeInsuranceVO.setCommonVO(null);
			errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
					AppConstants.INVALID_QUOTE);
			errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
					AppConstants.INVALID_EMAIL);
		}

		LOGGER.info("Load request for Home Insurance completed");

		if (!Utils.isEmpty(policyDataVO)) {
			/*
			 * request.setAttribute( com.Constant.CONST_HOMEINSURANCEVO,
			 * (HomeInsuranceVO)policyDataVO ); homeinInsuranceVO =
			 * (HomeInsuranceVO)policyDataVO
			 */;

			homeinInsuranceVO = (HomeInsuranceVO) policyDataVO;
			if (!Utils.isEmpty(homeinInsuranceVO.getBuildingDetails().getMortgageeName())) {
				String[] mortgageeName = homeinInsuranceVO.getBuildingDetails().getMortgageeName().split("#");
				if (mortgageeName.length > 1) {
					homeinInsuranceVO.getBuildingDetails().setMortgageeName(mortgageeName[0]);
					request.setAttribute(com.Constant.CONST_MORTGAGEOTHERS, mortgageeName[1]);
				}
			}

			if (bindingResult.hasErrors()) {
				homeinInsuranceVO.getGeneralInfo().setInsured(insuranceVO.getGeneralInfo().getInsured());
				homeinInsuranceVO.setBuildingDetails(insuranceVO.getBuildingDetails());
				homeinInsuranceVO.getScheme().setEffDate(insuranceVO.getScheme().getEffDate());
			}

			request.setAttribute(com.Constant.CONST_HOMEINSURANCEVO, homeinInsuranceVO);

			try {
				renderHomeRenewalPage(homeinInsuranceVO, request);
			} catch (JSONException e) {
				Errors errors = bindingResult;
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
						e.getMessage());
			}
			AppUtils.setScaleForLOB(homeInsuranceVO.getCommonVO().getLob());

			if (!Utils.isEmpty(policyDataVO)) {
				commonVO = policyDataVO.getCommonVO();
			}

			/* Set the referral message when the status is referred */
			if (AppUtils.isReferred(commonVO)) {
				ReferralUtils.setReferralMessage(bindingResult, policyDataVO);
			}

			if (!Utils.isEmpty(commonVO) && commonVO.getStatus().equals(
					AppConstants.CONVERTED_TO_POL_STATUS)) { /*
																 * Added null check
																 * for commonVO
																 * in if
																 * condition -
																 * sonar
																 * violation fix
																 */
				Errors errors = bindingResult;
				String errorMessage = Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_POLICYQUOTE_RETRIEVED)
						+ commonVO.getPolicyNo().toString() + ". " + Utils.getAppErrorMessage("pasb2c.assistance");
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
						errorMessage);
			}
		} else {
			homeinInsuranceVO = new HomeInsuranceVO();
		}
		setVariablesForOmnSiteCat((HomeInsuranceVO) policyDataVO, request);
		// ##START - Added by Dinesh for CR-130750 Royalty feature
		if (!Utils.isEmpty(renQuote) && StringUtils.isNumeric(renQuote)
				&& !Utils.isEmpty(request.getParameter(AppConstants.EMAIL_REQ_PARAM))) {
			if (!Utils.isEmpty(request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM))) {
				homeinInsuranceVO.getGeneralInfo().getSourceOfBus()
						.setPromoCode(request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM));
			} else {
				homeinInsuranceVO.getGeneralInfo().getSourceOfBus().setPromoCode("");
			}
		} // ###END
			// changes-HomeRevamp#7.1
		boolean isOldOrNewHomeQuote = false;

		try {
			isOldOrNewHomeQuote = checkPolIssueDate(homeInsuranceVO);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("isOldOrNewHomeQuote", isOldOrNewHomeQuote);
		// changes-HomeRevamp#7.1
		return new ModelAndView("homeRenewal", com.Constant.CONST_HOMEINSURANCEVO, homeinInsuranceVO);
	}

	@RequestMapping(value = "/HomeRenewalSave.do", method = RequestMethod.POST)
	public ModelAndView saveHomeRenewalDetails(
			@ModelAttribute(com.Constant.CONST_HOMEINSURANCEVO) HomeInsuranceVO homeInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request, HttpSession session) {

		LOGGER.info("Save request for Home renewal Insured and Risk Covers started");
		boolean sendEmail = false;
		boolean isPrintCase = false;
		try {
			BaseBeanToBeanMapper<HttpServletRequest, CoverDetails> requestToContentList = BeanMapperFactory
					.getMapperInstance(RequesttoCoverdetailsContentList.class);
			CoverDetails contentList = null;
			contentList = requestToContentList.mapBean(request, contentList);

			if (!Utils.isEmpty(homeInsuranceVO.getCovers().get(0).getSumInsured())
					&& !Utils.isEmpty(homeInsuranceVO.getCovers().get(0).getSumInsured().getSumInsured())) {
				homeInsuranceVO.getCovers().addAll(contentList.getCoverDetails());
				homeInsuranceVO.getStaffDetails().addAll(contentList.getStaffDetails());
			} else {
				contentList.setCoverDetails(null);
			}

			BaseBeanToBeanMapper<HttpServletRequest, CoverDetails> requestToPPList = BeanMapperFactory
					.getMapperInstance(RequesttoCoverdetailsPPList.class);
			CoverDetails personalPossList = null;
			personalPossList = requestToPPList.mapBean(request, personalPossList);

			if (!Utils.isEmpty(homeInsuranceVO.getCovers().get(1).getSumInsured())
					&& !Utils.isEmpty(homeInsuranceVO.getCovers().get(1).getSumInsured().getSumInsured())) {
				homeInsuranceVO.getCovers().addAll(personalPossList.getCoverDetails());
			} else {
				personalPossList.setCoverDetails(null);
			}

			setRequestAttributes(homeInsuranceVO, request);
			HomeInsuranceSvcHandler.setDefaultValues(homeInsuranceVO);
			/* Commented as per Srini's requirement - Anveshan */
			/*
			 * HomeRenewalValidator renewalValidator =
			 * (HomeRenewalValidator)ApplicationContextUtils.getBean(
			 * "homeRenewalValidator");
			 * renewalValidator.validate(homeInsuranceVO, bindingResult);
			 */

			if (!Utils.isEmpty(request.getParameter("sendMail"))) {
				isPrintCase = true;
			}
			/*
			 * if (bindingResult.hasErrors()) { return
			 * loadHomeRenewalInsuranceDetails(homeInsuranceVO, bindingResult,
			 * request,session ); }
			 */
			/*
			 * homeInsuranceSvcHandler.saveHomeRiskCoverDetails((PolicyDataVO)
			 * homeInsuranceVO, sendEmail, request.getRequestURL().toString());
			 * homeInsuranceVO =
			 * (HomeInsuranceVO)homeInsuranceSvcHandler.saveHomeInsuredDetails((
			 * PolicyDataVO)homeInsuranceVO, completePurchaseInd,
			 * request.getRequestURL().toString());
			 */

			homeInsuranceVO = (HomeInsuranceVO) homeInsuranceSvcHandler.saveHomeRenewalInsuranceDetails(
					(PolicyDataVO) homeInsuranceVO, sendEmail, request.getRequestURL().toString(), isPrintCase);

			request.setAttribute(com.Constant.CONST_HOMEINSURANCEVO, homeInsuranceVO);

			AppUtils.setBuildingDropDown(homeInsuranceVO, request);
			AppUtils.setQuoteValidDate(homeInsuranceVO, request);
			AppUtils.setScaleForLOB(homeInsuranceVO.getCommonVO().getLob());
			LOGGER.info("Save request for Home Insured and Risk Covers completed");

			if (!Utils.isEmpty(homeInsuranceVO.getReferralVOList())) {
				return loadHomeRenewalInsuranceDetails(homeInsuranceVO, bindingResult, request, session);
			} else {
				// request.setAttribute( "homeVO", homeInsuranceVO );
				// request.setAttribute( com.Constant.CONST_HOMEINSURANCEVO,
				// homeInsuranceVO );
				renderHomeRenewalPage(homeInsuranceVO, request);
				if (!isPrintCase) {
					request.setAttribute("quoteIssued", true);
				} else {
					request.setAttribute("isPrintCase", true);
				}

			}
		} catch (Exception e) {
			Errors errors = bindingResult;
			e.printStackTrace();
			if ("Disc/Loading was reduced by lower auth user".equals(e.getMessage())) {
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
						"Disc/Loading was reduced by lower auth user");
			} else {
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
						Utils.getAppErrorMessage(com.Constant.CONST_PASB2C_QUOTE_HOME_ERROR));
			}

		}
		// changes-HomeRevamp#7.1
		boolean isOldOrNewHomeQuote = false;

		try {
			isOldOrNewHomeQuote = checkPolIssueDate(homeInsuranceVO);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("isOldOrNewHomeQuote", isOldOrNewHomeQuote);
		// changes-HomeRevamp#7.1
		return new ModelAndView("homeRenewal", com.Constant.CONST_HOMEINSURANCEVO, homeInsuranceVO);
	}

	@RequestMapping(value = "/HomeRenewalStep2.do", method = RequestMethod.POST)
	public ModelAndView renewPolicy(@ModelAttribute(com.Constant.CONST_HOMEINSURANCEVO) HomeInsuranceVO homeInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request, HttpSession session) {

		LOGGER.info("Save request for Home Insured and building details started");
		boolean completePurchaseInd = true;
		BaseBeanToBeanMapper<HttpServletRequest, CoverDetails> requestToContentList = BeanMapperFactory
				.getMapperInstance(RequesttoCoverdetailsContentList.class);
		CoverDetails contentList = null;
		PolicyDataVO policyDataVO = null;
		try {
			contentList = requestToContentList.mapBean(request, contentList);
			if (!Utils.isEmpty(homeInsuranceVO.getCovers().get(0).getSumInsured())
					&& !Utils.isEmpty(homeInsuranceVO.getCovers().get(0).getSumInsured().getSumInsured())) {
				homeInsuranceVO.getCovers().addAll(contentList.getCoverDetails());
				homeInsuranceVO.getStaffDetails().addAll(contentList.getStaffDetails());
			} else {
				contentList.setCoverDetails(null);
			}

			BaseBeanToBeanMapper<HttpServletRequest, CoverDetails> requestToPPList = BeanMapperFactory
					.getMapperInstance(RequesttoCoverdetailsPPList.class);
			CoverDetails personalPossList = null;
			personalPossList = requestToPPList.mapBean(request, personalPossList);

			if (!Utils.isEmpty(homeInsuranceVO.getCovers().get(1).getSumInsured())
					&& !Utils.isEmpty(homeInsuranceVO.getCovers().get(1).getSumInsured().getSumInsured())) {
				homeInsuranceVO.getCovers().addAll(personalPossList.getCoverDetails());
			} else {
				personalPossList.setCoverDetails(null);
			}

			setRequestAttributes(homeInsuranceVO, request);
			HomeInsuranceSvcHandler.setDefaultValues(homeInsuranceVO);
			/* Commented as per Srini's requirement - Anveshan */
			/*
			 * HomeRenewalValidator renewalValidator =
			 * (HomeRenewalValidator)ApplicationContextUtils.getBean(
			 * "homeRenewalValidator");
			 * renewalValidator.validate(homeInsuranceVO, bindingResult);
			 */

			/*
			 * if (bindingResult.hasErrors()) { return
			 * loadHomeRenewalInsuranceDetails(homeInsuranceVO, bindingResult,
			 * request,session ); }
			 */
			/*
			 * homeInsuranceSvcHandler.saveHomeRiskCoverDetails((PolicyDataVO)
			 * homeInsuranceVO, completePurchaseInd,
			 * request.getRequestURL().toString()); policyDataVO =
			 * homeInsuranceSvcHandler.saveHomeInsuredDetails((PolicyDataVO)
			 * homeInsuranceVO, completePurchaseInd,
			 * request.getRequestURL().toString());
			 */

			homeInsuranceVO = (HomeInsuranceVO) homeInsuranceSvcHandler.saveHomeRenewalInsuranceDetails(
					(PolicyDataVO) homeInsuranceVO, completePurchaseInd, request.getRequestURL().toString(), false);

			LOGGER.info("Save request for Home Insured and building details complete_3");
		} catch (Exception e) {
			Errors errors = bindingResult;
			errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
					com.Constant.CONST_ERROR_END + e.getMessage());
		}
		try {
			if (!Utils.isEmpty(homeInsuranceVO.getReferralVOList())) {

				return loadHomeRenewalInsuranceDetails(homeInsuranceVO, bindingResult, request, session);
			} else {
				LOGGER.info("Make payment by calling Payment Gateway started");
				return commonCtrl.makePayment(request, homeInsuranceVO);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
			if (e.getMessage().contains("Premium Mismatch.")) {
				ModelAndView mav = new ModelAndView("paymentError", "quoteNo",
						homeInsuranceVO.getCommonVO().getQuoteNo());
				mav.addObject("errorMessage", Utils.getAppErrorMessage("pasb2c.partial.payment.error"));
				return mav;
			}
			// CommonHandler.renderErrorMessages( bindingResult, e.getMessage()
			// );
			LOGGER.error(e.getMessage(), e);
		}
		return new ModelAndView("homeRenewal", "homeInsuranceVO", homeInsuranceVO);
	}

	private void setRequestAttributesForRenewal(HomeInsuranceVO homeInsuranceVO, HttpServletRequest request,
			HttpSession session) throws Exception {

		String renQuote = null;
		if (Utils.isEmpty(homeInsuranceVO.getCommonVO())) {
			homeInsuranceVO.setCommonVO(new CommonVO());
		}
		;

		if (Utils.isEmpty(request.getSession(false).getAttribute(AppConstants.SESSION_USER_PROFILE_VO))) {
			UserProfile userProfile = new UserProfile();
			request.getSession(false).setAttribute(AppConstants.SESSION_USER_PROFILE_VO, userProfile);
		}
		homeInsuranceVO.getCommonVO()
				.setLoggedInUser((User) request.getSession(false).getAttribute(AppConstants.SESSION_USER_PROFILE_VO));
		String policy = request.getParameter("policyNo");
		String emailId = request.getParameter("emailId");
		System.out.println("The renewal Quote is " + policy);
		if (!Utils.isEmpty(policy)) {

			try {
				System.out.println("Inside the null check " + policy);
				renQuote = homeInsuranceSvcHandler.getRenewalQuoteFromPolicy(Long.parseLong(policy), emailId);
				System.out.println("The renewal Quote is " + renQuote);
			} catch (Exception e) {
				throw e;
			}
		} else {
			renQuote = request.getParameter("renQuote");
			if (!Utils.isEmpty(renQuote)
					&& !StringUtils.isNumeric(request.getParameter(AppConstants.RENEWAL_QUOTE_NUM_REQ_PARAM))) {
				renQuote = AppUtils.encryptAndDecryptData(renQuote, Boolean.FALSE);
			}
		}
		if (!Utils.isEmpty(renQuote)) {
			homeInsuranceVO.getCommonVO().setQuoteNo(Long.parseLong(renQuote));
		}
		String mortgageOthers = request.getParameter(com.Constant.CONST_MORTGAGEOTHERS);
		if (!Utils.isEmpty(mortgageOthers)) {
			homeInsuranceVO.getBuildingDetails()
					.setMortgageeName(homeInsuranceVO.getBuildingDetails().getMortgageeName() + "#" + mortgageOthers);
		}
		// homeInsuranceVO.getCommonVO().setDocCode(Short.valueOf(
		// Utils.getSingleValueAppConfig( "REN_QUO_DOC_CODE" ) ));
	}

	@RequestMapping("/getRevisedHomePremium")
	public void getRevisedHomePremium(
			@ModelAttribute(com.Constant.CONST_HOMEINSURANCEVO) HomeInsuranceVO homeInsuranceVO,
			BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {

		String homePremiumDetails = null;
		String buildingSIVal = null;
		Boolean calcRevisedPremiumEnabled = false;
		request.setAttribute("popupDisplayed", "set");
		if (calcRevisedPremiumEnabled) {
			try {

				setRequestAttributes(homeInsuranceVO, request);
				HomeInsuranceSvcHandler.setDefaultValues(homeInsuranceVO);
				homeInsuranceVO = (HomeInsuranceVO) homeInsuranceSvcHandler
						.getRevisedHomeRenewalPremium((PolicyDataVO) homeInsuranceVO);
			} catch (Exception e) {
				Errors errors = bindingResult;
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
						com.Constant.CONST_ERROR_END + e.getMessage());
			}

			LOGGER.debug("****************Premium Actual= " + homeInsuranceVO.getPremiumVO().getPremiumAmtActual());
			LOGGER.debug("****************Premium Amount= " + homeInsuranceVO.getPremiumVO().getPremiumAmt());
			double prmToDisplay = homeInsuranceVO.getPremiumVO().getPremiumAmt();
			if (!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getMinPremiumApplied())) {
				prmToDisplay = prmToDisplay + homeInsuranceVO.getPremiumVO().getMinPremiumApplied().doubleValue();
			}
			homePremiumDetails = "homeRevisedPremium" + "~"
					+ Currency.getFormattedCurrency(prmToDisplay, homeInsuranceVO.getCommonVO().getLob().toString());
			buildingSIVal = "buildingSIValue" + "~"
					+ Currency.getFormattedCurrency(
							homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured(),
							homeInsuranceVO.getCommonVO().getLob().toString());

			if (!Utils.isEmpty(homePremiumDetails)) {
				response.setHeader("homePremiumDetails", homePremiumDetails);
				response.setHeader("buildingSIVal", buildingSIVal);
			}
		}
	}

	/**
	 * @param homeinInsuranceVO
	 * @param request
	 */
	private void renderHomeRenewalPage(HomeInsuranceVO homeinInsuranceVO, HttpServletRequest request)
			throws JSONException {

		request.setAttribute(AppConstants.PAGE_VALUE, homeinInsuranceVO);
		request.setAttribute(AppConstants.COVERS, homeinInsuranceVO.getCovers());
		request.setAttribute(AppConstants.BUILDING, homeinInsuranceVO.getBuildingDetails());

		List<CoverDetailsVO> coverDetailsVOConts = new ArrayList<CoverDetailsVO>();
		List<CoverDetailsVO> coverDetailsVOPPs = new ArrayList<CoverDetailsVO>();
		List<StaffDetailsVO> staffDetails = new ArrayList<StaffDetailsVO>();

		for (CoverDetailsVO coverDetailsVO : homeinInsuranceVO.getCovers()) {

			if (coverDetailsVO.getRiskCodes().getRiskType().equals(SvcConstants.HOME_CONTENT_RISK_TYPE)
					&& coverDetailsVO.getRiskCodes().getRiskCat().equals(2)
					&& coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.DEFAULT_COVER_CODE) {

				coverDetailsVOConts.add(coverDetailsVO);

			} else if (coverDetailsVO.getRiskCodes().getRiskType().equals(SvcConstants.HOME_PERSONAL_POS_RISK_TYPE)
					&& coverDetailsVO.getRiskCodes().getRiskCat().equals(2)
					&& coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.DEFAULT_COVER_CODE) {

				coverDetailsVOPPs.add(coverDetailsVO);

			}
		}
		AppUtils.setQuoteValidDate(homeinInsuranceVO, request);

		staffDetails.addAll(homeinInsuranceVO.getStaffDetails());

		request.setAttribute("staffDetailsListItems", AppUtils.createJSONForStaffListDetails(staffDetails));
		request.setAttribute(com.Constant.CONST_CONTENTLISTITEMS,
				AppUtils.createJSONForHomeListDetails(coverDetailsVOConts));
		request.setAttribute(com.Constant.CONST_PERSPOSLISTITEMS,
				AppUtils.createJSONForHomeListDetails(coverDetailsVOPPs));
		request.setAttribute(com.Constant.CONST_ISLOADOPERATION, "true");

	}

	// changes-HomeRevamp#7.1
	private boolean checkPolIssueDate(HomeInsuranceVO homeInsuranceVO) throws ParseException {

		boolean PoliyWordingupdateDate = false;
		// String d2 = Utils.getSingleValueAppConfig("PolicyWordingupdateDate");
		String d2 = (String) SvcUtils.populateAEDDt();
		String defaultDateFormat = Utils.getSingleValueAppConfig("DEFAULT_DATE_FORMAT"); // MM/dd/yyyy
		// SimpleDateFormat s2 = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat s2 = new SimpleDateFormat(defaultDateFormat);
		Long QuoteNum = homeInsuranceVO.getQuoteNo();
		Boolean isQuote = homeInsuranceVO.getIsQuote();
		Long endtId = homeInsuranceVO.getEndtId();
		if (Utils.isEmpty(QuoteNum)) {
			QuoteNum = homeInsuranceVO.getCommonVO().getQuoteNo();
			endtId = homeInsuranceVO.getCommonVO().getEndtId();
		}
		if (Utils.isEmpty(isQuote)) {
			isQuote = homeInsuranceVO.getCommonVO().getIsQuote();
		}

		String polIssueDt = null;
		// Date polIssueDt = DAOUtils.getIssueDateForCovers(QuoteNum);
		if (!Utils.isEmpty(QuoteNum)) {
			// polIssueDt = DAOUtils.getPolIssueDate(QuoteNum, isQuote, endtId);
			// polIssueDt = DAOUtils.getPolModOrPrepdateDate(QuoteNum, isQuote,
			// endtId);
			polIssueDt = SvcUtils.populatePolEDt(QuoteNum);

		}
		if (!Utils.isEmpty(polIssueDt)) {
			LOGGER.debug("polIssueDt " + polIssueDt);

			Date prodDt = s2.parse(d2);
			if (s2.parse(polIssueDt).after(prodDt)) {
				PoliyWordingupdateDate = true;
			}
		}

		return PoliyWordingupdateDate;
	}
	// changes-HomeRevamp#7.1

}
