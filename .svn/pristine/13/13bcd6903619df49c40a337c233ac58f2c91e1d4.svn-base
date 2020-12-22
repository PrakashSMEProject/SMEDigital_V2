/**
 * 
 */
package com.rsaame.pas.homeInsurance.ui;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.List;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.CoverDetails;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author Sarath, M1033804
 * @since Phase 3
 * @version FGB
 * 
 */
public class HomeInsuranceLoadRH implements IRequestHandler {

	private final static Logger LOGGER = Logger.getLogger(HomeInsuranceLoadRH.class);
	private static Date vatStartDate = null;

	@Override
	public Response execute(HttpServletRequest request, HttpServletResponse responseObj) {

		LOGGER.info("Start loading home insurance details.");
		Response response = new Response();
		HomeInsuranceVO homeInsuranceVO = new HomeInsuranceVO();
		CommonVO commonVO = new CommonVO();

		try {

			// Radar fix
			// homeInsuranceVO = new HomeInsuranceVO();
			PolicyContext policyContext = PolicyContextUtil.getPolicyContext(request);
			commonVO = policyContext.getCommonDetails();
			// CR#13983 -changes for renewal quote validation
			Boolean SaveQuoteFlow = false;
			if (!Utils.isEmpty(request.getParameter("buttonClick"))
					&& request.getParameter("buttonClick").equalsIgnoreCase("saveQuote")) {
				SaveQuoteFlow = true;
			}
			// CR#13983 -changes for renewal quote validation

			/*
			 * IntegerShortConverter converter = ConverterFactory.getInstance(
			 * com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", ""
			 * ); if(!Utils.isEmpty( Currency.getPolicyTypeScaleMap() ) &&
			 * !Utils.isEmpty(
			 * Currency.getPolicyTypeScaleMap().get(converter.getBFromA(
			 * AppConstants.HOME_POLICY_TYPE )))){ Currency.setScale(
			 * Currency.getPolicyTypeScaleMap().get(converter.getBFromA(
			 * AppConstants.HOME_POLICY_TYPE )) ); }
			 */
			Boolean loadOnSave = (!Utils.isEmpty(request.getParameter("loadOnSave")))
					? Boolean.valueOf(request.getParameter("loadOnSave").toString())
					: false;

			homeInsuranceVO = (HomeInsuranceVO) TaskExecutor.executeTasks("HOME_INSURANCE_LOAD", commonVO);
			@SuppressWarnings("unchecked")
			DataHolderVO<Object[]> holder = (DataHolderVO<Object[]>) TaskExecutor
					.executeTasks("HOME_INSURANCE_LOAD_PROMO_CODES", homeInsuranceVO);
			@SuppressWarnings("unchecked")
			Map<String, Object> promoCodeDetails = (Map<String, Object>) holder.getData()[0];

			/**
			 * DEBIT_NOTE Setting app flow in request, will be used for
			 * rendering ACL tags
			 */
			Integer others = SvcUtils.getLookUpCode("PAS_MORTGAGEE_NAME", "ALL", "ALL", "Others");
			@SuppressWarnings("unchecked")
			DataHolderVO<Object[]> holder1 = (DataHolderVO<Object[]>) TaskExecutor
					.executeTasks("LOAD_HOME_VAT_RATE_AND_CODE", homeInsuranceVO);
			@SuppressWarnings("unchecked")
			Map<String, Object> vat = (Map<String, Object>) holder1.getData()[0];

			LOGGER.debug("**********vatRate**********" + vat.get("vatRate"));
			LOGGER.debug("**********vatEffDate**********" + vat.get("vatEffDat_1"));

			homeInsuranceVO.getPremiumVO().setVatTaxPerc((Double) vat.get("vatRate"));
			vatStartDate = (Date) vat.get(com.Constant.CONST_VATEFFDATE);
			vatStartDate = (Date) vat.get(com.Constant.CONST_VATEFFDATE);
			// homeInsuranceVO.getPremiumVO().setVatCode((Integer)
			// vat.get("vatCode"));

			if (!Utils.isEmpty(homeInsuranceVO.getBuildingDetails())
					&& !Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getMortgageeName())
					&& homeInsuranceVO.getBuildingDetails().getMortgageeName().contains(others.toString())) {
				String mrtg[] = homeInsuranceVO.getBuildingDetails().getMortgageeName().split("#");
				request.setAttribute("mortg", mrtg[1]);
				homeInsuranceVO.getBuildingDetails().setMortgageeName(mrtg[0]);
			}
			request.setAttribute(AppConstants.FUNTION_NAME, policyContext.getAppFlow().toString());
			if (!Utils.isEmpty(homeInsuranceVO) && !Utils.isEmpty(homeInsuranceVO.getPolicyNo())) {
				request.setAttribute(AppConstants.TRANSACTION_NO, "Policy : " + homeInsuranceVO.getPolicyNo());
			} else if (!Utils.isEmpty(homeInsuranceVO) && !Utils.isEmpty(homeInsuranceVO.getQuoteNo())) {
				request.setAttribute(AppConstants.TRANSACTION_NO, "Quotation : " + homeInsuranceVO.getQuoteNo());
			}

			if (!Utils.isEmpty(homeInsuranceVO.getEndorsmentVO())
					&& !Utils.isEmpty(homeInsuranceVO.getEndorsmentVO().get(0))) {
				EndorsmentVO endorsmentVO = new EndorsmentVO();
				endorsmentVO = homeInsuranceVO.getEndorsmentVO().get(0);

				/*
				 * Added stringbuffer to avoid "+" sonar viloation on 13-9-2017
				 */
				String endtTxt = "";
				StringBuffer endtTxBuffert = new StringBuffer();
				com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endtVO = ((PolicyDataVO) TaskExecutor
						.executeTasks("CAPTURE_AMEND_POLICY_ENDT_TEXT", homeInsuranceVO)).getEndorsmentVO();
				if (!Utils.isEmpty(endtVO)) {
					for (EndorsmentVO endt : endtVO) {
						if (!Utils.isEmpty(endt.getEndText())) {
							// endtTxt += endt.getEndText() + "\n";
							endtTxt = endtTxBuffert.append(endt.getEndText()).append("\n").toString();
						}
					}
				}
				// Added trim inside the endorsmentVO.setEndText()to avoid sonar
				// violation on useless Operation on immutable
				// endtTxt.trim();
				// endorsmentVO.setEndText(endtTxt);
				endorsmentVO.setEndText(endtTxt.trim());
				com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endtList = new List<EndorsmentVO>(null);
				endtList.add(endorsmentVO);
				homeInsuranceVO.setEndorsmentVO(endtList);
			}

			for (CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers()) {
				if ((SvcConstants.DEFAULT_HOME_COVER_CODE == coverDetailsVO.getCoverCodes().getCovCode())
						&& ((coverDetailsVO.getRiskCodes().getRiskCat().equals(SvcConstants.DEFAULT_RISK_CATEGORY)
								|| coverDetailsVO.getRiskCodes().getRiskCat()
										.equals(SvcConstants.CONTENT_MAIN_RISK_CATEGORY)
								|| coverDetailsVO.getRiskCodes().getRiskCat()
										.equals(SvcConstants.PP_MAIN_RISK_CATEGORY)))) {
					coverDetailsVO.setPremiumAmt(coverDetailsVO.getPremiumAmt()
							+ getHomeSubContentRate(homeInsuranceVO, coverDetailsVO.getRiskCodes().getRiskType()));
				}
			}

			request.setAttribute(AppConstants.PAGE_VALUE, homeInsuranceVO);
			request.setAttribute(AppConstants.COVERS, homeInsuranceVO.getCovers());
			request.setAttribute(AppConstants.BUILDING, homeInsuranceVO.getBuildingDetails());
			request.setAttribute(AppConstants.PROMOTIONAL_CODES, promoCodeDetails.get("promotionalCodes"));
			request.setAttribute(AppConstants.PROMOTIONAL_DISC, promoCodeDetails.get("promoDiscount"));
			request.setAttribute("staffDetails", homeInsuranceVO.getStaffDetails());
			updateListItemDetails(homeInsuranceVO, policyContext);
			policyContext.setStaffDetailsVO(homeInsuranceVO.getStaffDetails());
			UWQuestionsVO uwQuestionsVO = homeInsuranceVO.getUwQuestions();

			setDefaultAndCommonValues(homeInsuranceVO, request, commonVO);

			String uwResponseSequence = null;
			if (!Utils.isEmpty(uwQuestionsVO.getQuestions())) {
				uwResponseSequence = SvcUtils.getUWResponseSequence(uwQuestionsVO);
				LOGGER.info("UW Response Sequence " + uwResponseSequence + " generated");
				responseObj.setHeader("underWriterQuestions", uwResponseSequence);
			}
			if (loadOnSave) {
				request.setAttribute("dataSaved", "Data is saved successfully");
			}
			// Added for Informap Changes
			if (Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("20")
					|| Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("21")) {
				Boolean informapAvailable = DAOUtils.isInformapAvailable();
				request.setAttribute("informapAvailable", informapAvailable);
			}
			// CR#13983 -changes for renewal quote validation
			boolean isRenewalQuote = false;
			if (Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("20")
					|| Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("21")
					|| Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("50")) {
				if (!Utils.isEmpty(homeInsuranceVO.getCommonVO())
						&& Utils.getSingleValueAppConfig("NEW_RENEWAL_QUOTATION")
								.equalsIgnoreCase(homeInsuranceVO.getCommonVO().getDocCode().toString())
						&& homeInsuranceVO.getCommonVO().getIsQuote() && !SaveQuoteFlow
						&& Flow.VIEW_QUO.equals(homeInsuranceVO.getCommonVO().getAppFlow())) {

					UserProfile userProfile = (UserProfile) homeInsuranceVO.getCommonVO().getLoggedInUser();
					int BrokerId = userProfile.getRsaUser().getBrokerId();
					String profile = userProfile.getRsaUser().getProfile();
					if (BrokerId != 0 || profile.equalsIgnoreCase("Broker")) {
						isRenewalQuote = true;
					}

				}
			}
			request.setAttribute("isRenewalQuote", isRenewalQuote);
			// CR#13983 -changes for renewal quote validation
			// changes-HomeRevamp#7.1
			boolean isOldOrNewHomeQuote = checkPolIssueDate(homeInsuranceVO);
			request.setAttribute("isOldOrNewHomeQuote", isOldOrNewHomeQuote);
			request.setAttribute("appflow", commonVO.getAppFlow());
			// changes-HomeRevamp#7.1
			// CTS 06.08.2020 CR#613 -Document attachment change
			boolean documentexists = PLdocumentExists(homeInsuranceVO);
			request.setAttribute("documentexists", documentexists);
			// CTS 06.08.2020 CR#613 -Document attachment change

		} catch (BusinessException be) {
			throw new BusinessException(be.getErrorKeysList().get(0), be.getCause(), be.getMessage());
		} catch (Exception exp) {
			exp.printStackTrace();
			UserProfile userProfile = AppUtils.getUserDetailsFromSession(request);
			AppUtils.setUserProfileDetsToRequest(request, userProfile);
			AppUtils.addErrorMessage(request, "pas.something.went.wrong");

			Redirection redirection = new Redirection("/jsp/my-transactionsContent.jsp", Type.TO_JSP);
			response.setRedirection(redirection);
			response.addErrorKey("pas.something.went.wrong");
			response.setResponseType(Response.Type.DOJO_IFRAME);
			responseObj.setHeader("homeLoadError", "homeLoadError");
			return response;

		}

		LOGGER.info("Exiting from load home insurance details.");
		return response;
	}

	private double getHomeSubContentRate(HomeInsuranceVO homeInsuranceVO, Integer rskType) {
		double totalScSi = 0L;
		for (CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers()) {

			if (!Utils.isEmpty(coverDetailsVO.getRiskCodes().getRiskType())
					&& (coverDetailsVO.getCoverCodes().getCovCode() != 0)) {
				if ((rskType).equals(coverDetailsVO.getRiskCodes().getRiskType())
						&& (SvcConstants.DEFAULT_HOME_COVER_CODE == coverDetailsVO.getCoverCodes().getCovCode())
						&& (SvcConstants.CONTENT_SUB_RISK_CATEGORY.equals(coverDetailsVO.getRiskCodes().getRiskCat())
								&& SvcConstants.PP_SUB_RISK_CATEGORY
										.equals(coverDetailsVO.getRiskCodes().getRiskCat()))) {
					if (!Utils.isEmpty(coverDetailsVO.getSumInsured().getSumInsured())) {
						// factor.setFactorValue( String.valueOf(
						// coverDetailsVO.getSumInsured().getSumInsured().longValue()
						// ) );
						totalScSi += coverDetailsVO.getPremiumAmt();
					}
				}
			}
		}

		return totalScSi;
	}

	/**
	 * @param homeInsuranceVO
	 * @param request
	 *            This function is used to set the values to the UI fields
	 * @throws ParseException
	 */
	private void setDefaultAndCommonValues(HomeInsuranceVO homeInsuranceVO, HttpServletRequest request,
			CommonVO commonVO) throws ParseException {
		long polDaysLower = 0;
		long polDaysUpper = 0;
		request.setAttribute("commissionPercentage", homeInsuranceVO.getCommission());
		request.setAttribute("minPrem", homeInsuranceVO.getPremiumVO().getMinPremium());
		request.setAttribute("minPremiumApplied", homeInsuranceVO.getPremiumVO().getMinPremiumApplied());
		double transaction_premium = 0.0;
		double totalPremium = 0, premiumAfterDiscount = 0, payablePremium = 0, commissionAmount = 0,
				minPrmCancellation = 0.0, vatAmt = 0.0, vatablePrm = 0.0;
		double totalDiscountAmount = 0;
		double promoDiscountAmt = 0;
		boolean legacyRefund;
		DecimalFormat df3 = new DecimalFormat("#.000");
		int loggedInLoc = commonVO.getLocCode();
		boolean isPolicyCancelled = (Utils.getSingleValueAppConfig("POLICY_CANCELLED"))
				.equals(commonVO.getStatus().toString());

		PolicyDataVO policyDataVO = (PolicyDataVO) request.getAttribute(com.Constant.CONST_CANCELDETAILS);

		if (!Utils.isEmpty(homeInsuranceVO.getPremiumVO())) {

			if (!Utils.isEmpty(policyDataVO) && !Utils.isEmpty(policyDataVO.getEndorsmentVO())) {
				totalPremium = getTotalPremiumForCancellation(policyDataVO, homeInsuranceVO.getPremiumVO());
			} else {
				totalPremium = homeInsuranceVO.getPremiumVO().getPremiumAmt();
			}

			/*
			 * Min Premium is prorated while cancelling
			 */
			if (!Utils.isEmpty(policyDataVO) && !Utils.isEmpty(policyDataVO.getEndorsmentVO())) {
				minPrmCancellation = getMinPremiumForCancellation(policyDataVO, homeInsuranceVO.getPremiumVO());
			}
			// Prorated MinPremium should be taken from the table for cancelled
			// records
			else if (isPolicyCancelled) {
				java.util.List<Object> valueHolder1 = DAOUtils.getSqlResultSingleColumnPas(
						QueryConstants.GET_MIN_PREM_FROM_PRM_TABLE, homeInsuranceVO.getCommonVO().getPolicyId(),
						homeInsuranceVO.getCommonVO().getEndtId());
				if (!Utils.isEmpty(valueHolder1) && valueHolder1.size() > 0 && !Utils.isEmpty(valueHolder1.get(0))) {
					minPrmCancellation = ((BigDecimal) valueHolder1.get(0)).doubleValue();
				}
				// minPrmCancellation = getMinPremiumForCancellation(
				// homeInsuranceVO, homeInsuranceVO.getPremiumVO() );
			}
			totalPremium = totalPremium + minPrmCancellation;

			if (!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getPromoDiscPerc())) {
				totalDiscountAmount -= Double.parseDouble(Currency.getFormattedCurrency(
						((homeInsuranceVO.getPremiumVO().getPromoDiscPerc() * totalPremium) / 100), LOB.HOME.name())
						.replace(",", ""));
				promoDiscountAmt = (homeInsuranceVO.getPremiumVO().getPromoDiscPerc() * totalPremium) / 100;
			}

			if (!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getDiscOrLoadPerc())) {
				totalDiscountAmount += Double.parseDouble(Currency
						.getFormattedCurrency((homeInsuranceVO.getPremiumVO().getDiscOrLoadPerc() * totalPremium) / 100,
								LOB.HOME.name())
						.replace(",", ""));
			}

			totalPremium = Double
					.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(totalPremium), LOB.HOME.name()));
			totalDiscountAmount = Double.parseDouble(
					Currency.getUnformattedScaledCurrency(new BigDecimal(totalDiscountAmount), LOB.HOME.name()));

			premiumAfterDiscount = totalPremium + totalDiscountAmount;
			/*
			 * Setting minPrem as premiumAfterDiscount if the totalPrem after
			 * discounts is < minPrem And should not be set during Cancellation
			 * and for cancelled records
			 */
			/*
			 * if( premiumAfterDiscount <
			 * homeInsuranceVO.getPremiumVO().getMinPremium().doubleValue() &&
			 * Utils.isEmpty( request.getAttribute(
			 * com.Constant.CONST_CANCELDETAILS ) ) && !(
			 * Utils.getSingleValueAppConfig( "POLICY_CANCELLED" ) ).equals(
			 * commonVO.getStatus().toString() ) ){ premiumAfterDiscount =
			 * homeInsuranceVO.getPremiumVO().getMinPremium().doubleValue(); }
			 */

			/* For canceled policies, discount percent is 0.0 - Anveshan */
			if (isPolicyCancelled && !Utils.isEmpty(homeInsuranceVO.getPremiumVO().getDiscOrLoadAmt())) {
				totalDiscountAmount = homeInsuranceVO.getPremiumVO().getDiscOrLoadAmt().doubleValue();
				premiumAfterDiscount = totalPremium + totalDiscountAmount;
			}

			payablePremium = premiumAfterDiscount + homeInsuranceVO.getPremiumVO().getGovtTax()
					+ homeInsuranceVO.getPremiumVO().getPolicyFees();
			if (!Utils.isEmpty(homeInsuranceVO.getCommission())) {

				// Added for Bahrain Commission changes
				if (loggedInLoc == 50) {

					double formattedPrem = SvcUtils.getRoundingOffBah(Double.parseDouble(
							Currency.getFormattedCurrency(premiumAfterDiscount, commonVO.getLob().toString())
									.replace(",", "")));
					commissionAmount = (homeInsuranceVO.getCommission() * formattedPrem) / 100;
				} else {
					commissionAmount = (homeInsuranceVO.getCommission() * premiumAfterDiscount) / 100;
				}
			}
			// 142244 Vat : Vat primary Initial Implementation
			/*
			 * if(!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getVatTax()) &&
			 * !isPolicyCancelled) {
			 * 
			 * LOGGER.debug("VAT Amount --------->"+homeInsuranceVO.getPremiumVO
			 * ().getVatTax());
			 * payablePremium=payablePremium+homeInsuranceVO.getPremiumVO().
			 * getVatTax(); vatAmt=homeInsuranceVO.getPremiumVO().getVatTax(); }
			 * // cancelled
			 * if(!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getVatTax()) &&
			 * !Utils.isEmpty(request.getAttribute(com.Constant.
			 * CONST_CANCELDETAILS))) {
			 * 
			 * vatAmt=0.0;
			 * 
			 * vatAmt=(homeInsuranceVO.getPremiumVO().getVatTaxPerc() *
			 * premiumAfterDiscount) / 100;
			 * homeInsuranceVO.getPremiumVO().setVatTax(Double.parseDouble(
			 * Currency .getUnformattedScaledCurrency(new BigDecimal(vatAmt),
			 * "SBS")));
			 * 
			 * LOGGER.debug("VAT Amount --------->"+homeInsuranceVO.getPremiumVO
			 * ().getVatTax()); }
			 */

		}
		Comparator<Date> cmp = new Comparator<Date>() {
			@Override
			public int compare(Date date1, Date date2) {
				return date1.compareTo(date2);
			}
		};

		/* Endorsement */

		// For Endorsement VAT calculation : Endorsement premium *
		// ((End_Expiry_Date-MAX(End_start_date,VAT_Implmentation_date))+1)/((endt_expiry_date-endt_effective_date)+1)*VAT
		// Rate
		if (!Utils.isEmpty(vatStartDate)) {

			// 142244 : Formula Implementation
			SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");
			Date polEffDate = homeInsuranceVO.getScheme().getEffDate();
			Date polExpiryDate = homeInsuranceVO.getScheme().getExpiryDate();
			// String vatStartDate =
			// Utils.getSingleValueAppConfig("VAT_START_DATE"); To read from
			// AppConig 1 Jan 2018
			Date maxDate = null;
			if (commonVO.getAppFlow().equals(Flow.AMEND_POL)) {
				double premiumAmt = 0.0;
				LOGGER.debug("In Block 1  Amend Policy--------->");
				if (!Utils.isEmpty(homeInsuranceVO.getEndorsmentVO())) {
					EndorsmentVO endorsmentVO = homeInsuranceVO.getEndorsmentVO().get(0);

					Date polEndEffDate = endorsmentVO.getEndEffDate();
					Date polEndExpiry = endorsmentVO.getEndDate();
					LinkedList<Date> dateList1 = new LinkedList<Date>();
					dateList1.add(polEndEffDate);
					dateList1.add(vatStartDate); // VAT Effective Date
					maxDate = Collections.max(dateList1, cmp);

					polDaysUpper = getDifference(getDateWithoutTimeStamp(polEndExpiry),
							getDateWithoutTimeStamp(maxDate));// diffInDays
					polDaysLower = getDifference(getDateWithoutTimeStamp(polEndExpiry),
							getDateWithoutTimeStamp(polEndEffDate));// policyPeriodDays

					premiumAmt = endorsmentVO.getPremiumVO().getPremiumAmt()
							- endorsmentVO.getOldPremiumVO().getPremiumAmt();

					premiumAmt = Math.abs(premiumAmt);

					double vatRate = (homeInsuranceVO.getPremiumVO().getVatTaxPerc()) / 100;
					if (polDaysLower != 0) {
						vatAmt = premiumAmt * ((polDaysUpper) * vatRate) / (polDaysLower);

						/* Vatable Prm */
						vatablePrm = premiumAmt * ((polDaysUpper)) / (polDaysLower);

					}

					if (!Utils.isEmpty(homeInsuranceVO.getCommonVO().getIslegacyPolicy())
							&& homeInsuranceVO.getCommonVO().getIslegacyPolicy()
							&& homeInsuranceVO.getPremiumVO().getOldVatAmt() == 0) {

						vatAmt = 0.0; // 153167
					}

					// Request ID:154260 : A Check, POLICY_ACCEPT status code
					// (23) added to check the VAT amount from getting doubled.
					if (!Utils.isEmpty(SvcConstants.POL_STATUS_ACCEPT)
							&& (homeInsuranceVO.getCommonVO().getStatus() == SvcConstants.POL_STATUS_ACCEPT)) {
						vatAmt = homeInsuranceVO.getPremiumVO().getOldVatAmt();
					} else {
						/* Endoresment Cumulative Vat amount */
						vatAmt = homeInsuranceVO.getPremiumVO().getOldVatAmt() + vatAmt;
					}

					/* legacy scenario : flagging the legacy Policy */
					if (!Utils.isEmpty(homeInsuranceVO.getCommonVO().getIslegacyPolicy())
							&& homeInsuranceVO.getCommonVO().getIslegacyPolicy()) {
						/* Refund Scenario */
						request.setAttribute("legacyRefund", homeInsuranceVO.getCommonVO().getIslegacyPolicy());

					}
					if (loggedInLoc == 20 || loggedInLoc == 21) {
						homeInsuranceVO.getPremiumVO().setVatTax(Double
								.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(vatAmt), "SBS")));
						homeInsuranceVO.getPremiumVO().setVatablePrm(
								Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(vatablePrm),
										commonVO.getLob().toString())));

						request.setAttribute(com.Constant.CONST_VATTAX,
								Currency.getFormattedCurrency(homeInsuranceVO.getPremiumVO().getVatTax(), "SBS"));

						request.setAttribute("oldvatableTaxPrm",
								Currency.getUnformattedScaledCurrency(
										new BigDecimal(homeInsuranceVO.getPremiumVO().getOldVatablePrm()),
										commonVO.getLob().toString()));
					}
					if (loggedInLoc == 50) {
						Double vatAmtFormatted = Double.parseDouble(Currency
								.getUnformattedScaledCurrency(new BigDecimal(vatAmt), commonVO.getLob().toString()));
						Double vatablePrmFormatted = Double.parseDouble(Currency.getUnformattedScaledCurrency(
								new BigDecimal(vatablePrm), commonVO.getLob().toString()));
						vatAmt = SvcUtils.getRoundingOffBah(vatAmtFormatted);
						vatablePrm = SvcUtils.getRoundingOffBah(vatablePrmFormatted);

						homeInsuranceVO.getPremiumVO().setVatTax(vatAmt);
						homeInsuranceVO.getPremiumVO().setVatablePrm(vatablePrm);

						request.setAttribute(com.Constant.CONST_VATTAX, Currency.getFormattedCurrency(
								homeInsuranceVO.getPremiumVO().getVatTax(), commonVO.getLob().toString()));

						request.setAttribute("oldvatableTaxPrm",
								Currency.getUnformattedScaledCurrency(
										new BigDecimal(homeInsuranceVO.getPremiumVO().getOldVatablePrm()),
										commonVO.getLob().toString()));
					}

					LOGGER.debug("premiumAmt --------->" + premiumAmt);
					LOGGER.debug("polDaysUpper (diffInDays)---------_1" + polDaysUpper);
					LOGGER.debug("polDaysLower (policyPeriodDays) ---------_1" + polDaysLower);
					LOGGER.debug("vatAmt ---------_1" + vatAmt);
					LOGGER.debug("vatablePrm --------->" + vatablePrm);
				}

			}
			/* Cancelation */
			else if (!Utils.isEmpty(request.getAttribute(com.Constant.CONST_CANCELDETAILS))) {
				vatAmt = 0.0;
				polDaysUpper = 0;
				polDaysLower = 0;
				double premiumAmt = 0.0;
				double retainableVatAmt = 0.0;
				double retainedPrem = 0.0;
				// POL_PREMIUM*(POL_EXPIRY_DATE - MAX(POL_ENDT_EFFECTIVE_DATE
				// ,’01-JAN-2018’)/( POL_ENDT_EFFECTIVE_DATE –
				// POL_EXPIRY_DATE)*VAT%
				LOGGER.debug("In Block 2  Cancel --------->");
				if (!Utils.isEmpty(policyDataVO.getEndorsmentVO())) {
					EndorsmentVO endorsmentVO = policyDataVO.getEndorsmentVO().get(0);

					Date polEndEffDate = endorsmentVO.getEndEffDate();
					Date polEndExpiry = endorsmentVO.getEndDate();
					LinkedList<Date> dateList1 = new LinkedList<Date>();
					dateList1.add(polEndEffDate);
					dateList1.add(vatStartDate); // VAT Effective Date
					maxDate = Collections.max(dateList1, cmp);

					polDaysUpper = getDifference(getDateWithoutTimeStamp(polEndExpiry),
							getDateWithoutTimeStamp(maxDate));// diffInDays
					polDaysLower = getDifference(getDateWithoutTimeStamp(polEndExpiry),
							getDateWithoutTimeStamp(polEndEffDate));// policyPeriodDays

					premiumAmt = payablePremium - endorsmentVO.getOldPremiumVO().getPremiumAmt();
					premiumAmt = Math.abs(premiumAmt);

					// retainedPrem=payablePremium;

				}

				double vatRate = (homeInsuranceVO.getPremiumVO().getVatTaxPerc()) / 100;
				if (polDaysLower != 0) {
					vatAmt = premiumAmt * ((polDaysUpper) * vatRate) / (polDaysLower);
					// retainableVatAmt=retainedPrem*vatRate;
					/*
					 * During cancel to hold vat amount: earned Vat amount for
					 * the earned period
					 */
					vatablePrm = premiumAmt * ((polDaysUpper)) / (polDaysLower);

				}
				/* legacy scenario : flagging the legacy Policy */
				if (!Utils.isEmpty(homeInsuranceVO.getCommonVO().getIslegacyPolicy())
						&& homeInsuranceVO.getCommonVO().getIslegacyPolicy()) {
					/* Refund Scenario */
					request.setAttribute("legacyRefund", homeInsuranceVO.getCommonVO().getIslegacyPolicy());
					vatAmt = 0.0;
					retainableVatAmt = homeInsuranceVO.getPremiumVO().getVatTax();
				} else {
					retainableVatAmt = homeInsuranceVO.getPremiumVO().getVatTax() - vatAmt;
					vatablePrm = homeInsuranceVO.getPremiumVO().getVatablePrm() - vatablePrm;

				}
				transaction_premium = premiumAmt + vatAmt;
				if (loggedInLoc == 20 || loggedInLoc == 21) {
					homeInsuranceVO.getPremiumVO().setVatTax(
							Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(vatAmt), "SBS")));
					homeInsuranceVO.getPremiumVO().setVatablePrm(Double.parseDouble(Currency
							.getUnformattedScaledCurrency(new BigDecimal(vatablePrm), commonVO.getLob().toString())));
					request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,
							Currency.getFormattedCurrency(new BigDecimal(transaction_premium), "SBS"));
					request.setAttribute("retainableVatAmt", Currency.getFormattedCurrency(new BigDecimal(Math.abs(
							retainableVatAmt)), /*
												 * During cancel to hold vat
												 * amount for the earned period
												 */
							"SBS"));
					request.setAttribute(com.Constant.CONST_VATTAX,
							Currency.getFormattedCurrency(homeInsuranceVO.getPremiumVO().getVatTax(), "SBS"));

					request.setAttribute(com.Constant.CONST_VATTAXDISPLAY,
							Currency.getFormattedCurrency(homeInsuranceVO.getPremiumVO().getVatTax(), "SBS"));
				}
				if (loggedInLoc == 50) {
					vatAmt = SvcUtils.getRoundingOffBah(Double.parseDouble(
							Currency.getUnformattedScaledCurrency(new BigDecimal(vatAmt), commonVO.getLob().toString())
									.replace(",", "")));
					transaction_premium = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency
							.getFormattedCurrency(new BigDecimal(transaction_premium), commonVO.getLob().toString())
							.replace(",", "")));
					homeInsuranceVO.getPremiumVO().setVatTax(Double.parseDouble(Currency
							.getUnformattedScaledCurrency(new BigDecimal(vatAmt), commonVO.getLob().toString())));
					homeInsuranceVO.getPremiumVO().setVatablePrm(Double.parseDouble(Currency
							.getUnformattedScaledCurrency(new BigDecimal(vatablePrm), commonVO.getLob().toString())));
					request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM, df3.format(transaction_premium));
					request.setAttribute("retainableVatAmt", Currency.getFormattedCurrency(new BigDecimal(Math.abs(
							retainableVatAmt)), /*
												 * During cancel to hold vat
												 * amount for the earned period
												 */
							commonVO.getLob().toString()));
					request.setAttribute(com.Constant.CONST_VATTAX, df3.format(vatAmt));

					request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, df3.format(vatAmt));
				}

				LOGGER.debug("premiumAmt --------->" + premiumAmt);
				LOGGER.debug("polDaysUpper (diffInDays)---------_2" + polDaysUpper);
				LOGGER.debug("polDaysLower (policyPeriodDays) ---------_2" + polDaysLower);
				LOGGER.debug("vatAmt ---------_2" + vatAmt);
			}

			else {
				// For VAT calculation : [{(pol_exp_date -
				// MAX(pol_start_date,1Jan2018))+1} / pol_period] X 5%
				LOGGER.debug("In Block 3   --------->");
				vatAmt = 0.0;
				polDaysUpper = 0;
				polDaysLower = 0;

				// POL_PREMIUM * (POL_EXPIRY_DATE - MAX(POL_EFFECTIVE_DATE
				// ,’01-JAN-2018’)/( POL_EFFECTIVE_DATE – POL_EXPIRY_DATE)*VAT%
				LinkedList<Date> dateList1 = new LinkedList<Date>();
				dateList1.add(polEffDate);
				dateList1.add(vatStartDate); // VAT Effective Date
				maxDate = Collections.max(dateList1, cmp);

				polDaysUpper = getDifference(getDateWithoutTimeStamp(polExpiryDate), getDateWithoutTimeStamp(maxDate));// diffInDays
				polDaysLower = getDifference(getDateWithoutTimeStamp(polExpiryDate),
						getDateWithoutTimeStamp(polEffDate));// policyPeriodDays

				double vatRate = (homeInsuranceVO.getPremiumVO().getVatTaxPerc()) / 100;
				if (polDaysLower != 0) {
					vatAmt = payablePremium * ((polDaysUpper) * vatRate) / (polDaysLower);
					vatablePrm = payablePremium * ((polDaysUpper)) / (polDaysLower);

				}
				// View Scenarios: To view 0 tax amount if its not under
				// Vattable
				if (Flow.VIEW_QUO.equals(commonVO.getAppFlow()) || Flow.VIEW_POL.equals(commonVO.getAppFlow())) {
					if (!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getViewVatAmount())) { // View
																								// mode
																								// correction
						if (homeInsuranceVO.getPremiumVO().getViewVatAmount() == 0) {
							vatAmt = 0.0;
						}

					}

				}
				if (loggedInLoc == 20 || loggedInLoc == 21) {
					homeInsuranceVO.getPremiumVO().setVatTax(
							Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(vatAmt), "SBS")));
					transaction_premium = payablePremium + vatAmt;
					homeInsuranceVO.getPremiumVO().setVatablePrm(Double.parseDouble(Currency
							.getUnformattedScaledCurrency(new BigDecimal(vatablePrm), commonVO.getLob().toString())));
					request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,
							Currency.getFormattedCurrency(new BigDecimal(transaction_premium), "SBS"));
					request.setAttribute(com.Constant.CONST_VATTAXDISPLAY,
							Currency.getFormattedCurrency(homeInsuranceVO.getPremiumVO().getVatTax(), "SBS"));

					request.setAttribute(com.Constant.CONST_VATTAX,
							Currency.getFormattedCurrency(homeInsuranceVO.getPremiumVO().getVatTax(), "SBS"));
				}
				if (loggedInLoc == 50) {
					Double vatAmtFormatted = Double.parseDouble(Currency
							.getUnformattedScaledCurrency(new BigDecimal(vatAmt), commonVO.getLob().toString()));
					Double vatablePrmFormatted = Double.parseDouble(Currency
							.getUnformattedScaledCurrency(new BigDecimal(vatablePrm), commonVO.getLob().toString()));
					Double vatTaxFormatted = Double.parseDouble(Currency.getFormattedCurrency(
							homeInsuranceVO.getPremiumVO().getVatTax(), commonVO.getLob().toString()));
					vatAmt = SvcUtils.getRoundingOffBah(vatAmtFormatted);
					vatablePrm = SvcUtils.getRoundingOffBah(vatablePrmFormatted);
					vatTaxFormatted = SvcUtils.getRoundingOffBah(vatablePrmFormatted);

					homeInsuranceVO.getPremiumVO().setVatTax(vatAmt);
					transaction_premium = vatablePrm + vatAmt;
					homeInsuranceVO.getPremiumVO().setVatablePrm(vatablePrm);
					request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM, Currency
							.getFormattedCurrency(new BigDecimal(transaction_premium), commonVO.getLob().toString()));
					request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, df3.format(vatAmt));

					request.setAttribute(com.Constant.CONST_VATTAX, df3.format(vatAmt));
				}
			}

			request.setAttribute("polDaysUpper", polDaysUpper);
			request.setAttribute("polDaysLower", polDaysLower);
			request.setAttribute("vatablePrm", vatablePrm);

			LOGGER.debug("payablePremium --------->" + payablePremium);
			LOGGER.debug("polDaysUpper (diffInDays)---------_3" + polDaysUpper);
			LOGGER.debug("polDaysLower (policyPeriodDays) ---------_3" + polDaysLower);
			LOGGER.debug("vatAmt ---------_3" + vatAmt);
			LOGGER.debug("vatablePrm --------->" + vatablePrm);
		}

		if (Flow.VIEW_QUO.equals(commonVO.getAppFlow()) && !Utils.isEmpty(request.getParameter("buttonClick"))
				&& request.getParameter("buttonClick").equalsIgnoreCase("saveQuote")
				&& commonVO.getStatus().equals(Integer.valueOf(AppConstants.QUOTE_ACTIVE))) {
			request.setAttribute("renewalC2P", "true");
			// 142244
			request.setAttribute(com.Constant.CONST_PAYABLEPREMIUM,
					Currency.getFormattedCurrency(payablePremium, commonVO.getLob().toString()));
			if (loggedInLoc == 20 || loggedInLoc == 21) {

				request.setAttribute(com.Constant.CONST_VATTAX,
						Currency.getFormattedCurrency(homeInsuranceVO.getPremiumVO().getVatTax(), "SBS"));
				request.setAttribute(com.Constant.CONST_VATTAXDISPLAY,
						Currency.getFormattedCurrency(homeInsuranceVO.getPremiumVO().getVatTax(), "SBS"));
				double transactionPrm = payablePremium + homeInsuranceVO.getPremiumVO().getVatTax();

				request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,
						Currency.getFormattedCurrency(new BigDecimal(transactionPrm), "SBS"));
			}
			if (loggedInLoc == 50) {
				/*
				 * double vatablePrmFormatted =
				 * Double.parseDouble(Currency.getUnformattedScaledCurrency(new
				 * BigDecimal(vatablePrm), commonVO.getLob().toString()));
				 */
				/*
				 * vatablePrm = SvcUtils.getRoundingOffBah(vatablePrmFormatted);
				 */
				request.setAttribute(com.Constant.CONST_VATTAX, Currency.getFormattedCurrency(
						homeInsuranceVO.getPremiumVO().getVatTax(), commonVO.getLob().toString()));
				request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, Currency.getFormattedCurrency(
						homeInsuranceVO.getPremiumVO().getVatTax(), commonVO.getLob().toString()));
				double transactionPrm = vatablePrm + homeInsuranceVO.getPremiumVO().getVatTax();
				request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,
						Currency.getFormattedCurrency(new BigDecimal(transactionPrm), commonVO.getLob().toString()));
			}

		}

		if (commonVO.getAppFlow().equals(Flow.AMEND_POL)) {
			// for initial page load in AMEND there should not be any change in
			// payable/refund premium
			request.setAttribute(com.Constant.CONST_PAYABLEPREMIUM,
					Currency.getFormattedCurrency(0.0, commonVO.getLob().toString()));
			request.setAttribute("initialAmendPageLoad", "initialAmendPageLoad");
		} else if (!Utils.isEmpty(homeInsuranceVO.getEndorsmentVO())) {
			EndorsmentVO endorsmentVO = homeInsuranceVO.getEndorsmentVO().get(0);
			if (!Utils.isEmpty(request.getAttribute(com.Constant.CONST_CANCELDETAILS)))
				request.setAttribute(com.Constant.CONST_PAYABLEPREMIUM,
						Currency.getFormattedCurrency(
								endorsmentVO.getPremiumVO().getPremiumAmt()
										- endorsmentVO.getOldPremiumVO().getPremiumAmt(),
								commonVO.getLob().toString()));
			else if (isPolicyCancelled) {

				Date polEndEffDate = endorsmentVO.getEndEffDate();
				Date polEndExpiry = endorsmentVO.getEndDate();
				LinkedList<Date> dateList1 = new LinkedList<Date>();
				dateList1.add(polEndEffDate);
				dateList1.add(vatStartDate); // VAT Effective Date
				Date maxDate = Collections.max(dateList1, cmp);
				double transactionVatAmt = 0.0;

				polDaysUpper = getDifference(getDateWithoutTimeStamp(polEndExpiry), getDateWithoutTimeStamp(maxDate));// diffInDays
				polDaysLower = getDifference(getDateWithoutTimeStamp(polEndExpiry),
						getDateWithoutTimeStamp(polEndEffDate));// policyPeriodDays

				double payableAmt = endorsmentVO.getPremiumVO().getPremiumAmt()
						- endorsmentVO.getOldPremiumVO().getPremiumAmt();
				double vatRate = (homeInsuranceVO.getPremiumVO().getVatTaxPerc()) / 100;
				if (polDaysLower != 0)
					transactionVatAmt = vatRate * (polDaysUpper) * Math.abs(payableAmt) / polDaysLower;
				// For legacy policy show on UI 0 Refund , no refund
				if (!Utils.isEmpty(homeInsuranceVO.getCommonVO().getIslegacyPolicy())
						&& homeInsuranceVO.getCommonVO().getIslegacyPolicy()) {
					transactionVatAmt = 0.0;
				}
				if (loggedInLoc == 20 || loggedInLoc == 21) {
					request.setAttribute(com.Constant.CONST_VATTAX,
							Currency.getFormattedCurrency(transactionVatAmt, "SBS"));
					double transactionPrm = transactionVatAmt + Math.abs(payableAmt);
					request.setAttribute(com.Constant.CONST_VATTAXDISPLAY,
							Currency.getFormattedCurrency(transactionVatAmt, "SBS"));
					request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,
							Currency.getFormattedCurrency(new BigDecimal(transactionPrm), "SBS"));
				}
				if (loggedInLoc == 50) {
					vatAmt = SvcUtils.getRoundingOffBah(Double.parseDouble(
							Currency.getFormattedCurrency(transactionVatAmt, commonVO.getLob().toString())));

					request.setAttribute(com.Constant.CONST_VATTAX, vatAmt);
					double transactionPrm = vatAmt + Math.abs(payableAmt);
					request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, vatAmt);
					request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM, Currency
							.getFormattedCurrency(new BigDecimal(transactionPrm), commonVO.getLob().toString()));
				}

			} else {
				double premiumAmtValue = endorsmentVO.getPremiumVO().getPremiumAmt()
						- endorsmentVO.getOldPremiumVO().getPremiumAmt();
				if (loggedInLoc == 50) {
					request.setAttribute(com.Constant.CONST_PAYABLEPREMIUM,
							Currency.getFormattedCurrency(premiumAmtValue));
				} else {
					endorsmentVO.getPremiumVO().setPremiumAmt(payablePremium);
					request.setAttribute(com.Constant.CONST_PAYABLEPREMIUM,
							Currency.getFormattedCurrency(
									payablePremium - endorsmentVO.getOldPremiumVO().getPremiumAmt(),
									commonVO.getLob().toString()));
				}

				Date polEndEffDate = endorsmentVO.getEndEffDate();
				Date polEndExpiry = endorsmentVO.getEndDate();
				LinkedList<Date> dateList1 = new LinkedList<Date>();
				dateList1.add(polEndEffDate);
				dateList1.add(vatStartDate); // VAT Effective Date
				Date maxDate = Collections.max(dateList1, cmp);
				double transactionVatAmt = 0.0;
				polDaysUpper = getDifference(getDateWithoutTimeStamp(polEndExpiry), getDateWithoutTimeStamp(maxDate));// diffInDays
				polDaysLower = getDifference(getDateWithoutTimeStamp(polEndExpiry),
						getDateWithoutTimeStamp(polEndEffDate));// policyPeriodDays
				// 142244 Transaction Vat amt
				double payableAmt = payablePremium - endorsmentVO.getOldPremiumVO().getPremiumAmt();
				double vatRate = (homeInsuranceVO.getPremiumVO().getVatTaxPerc()) / 100;
				if (polDaysLower != 0)
					transactionVatAmt = vatRate * (polDaysUpper) * Math.abs(payableAmt) / polDaysLower;
				// For legacy policy show on UI 0 Refund , no refund
				if (!Utils.isEmpty(homeInsuranceVO.getCommonVO().getIslegacyPolicy())
						&& homeInsuranceVO.getCommonVO().getIslegacyPolicy() && payableAmt < 0) {
					transactionVatAmt = 0.0;
				} else if (!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getViewVatAmount())
						&& Flow.VIEW_POL.equals(commonVO.getAppFlow())) { // View
																			// mode
																			// correction
					if (homeInsuranceVO.getPremiumVO().getViewVatAmount() == 0) {
						transactionVatAmt = 0.0;

					}
				}
				if (loggedInLoc == 20 || loggedInLoc == 21) {
					request.setAttribute(com.Constant.CONST_VATTAXDISPLAY,
							Currency.getFormattedCurrency(transactionVatAmt, "SBS"));
					double transactionPrm = transactionVatAmt + Math.abs(payableAmt);

					request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM,
							Currency.getFormattedCurrency(new BigDecimal(transactionPrm), "SBS"));
				}
				if (loggedInLoc == 50) {
					transactionVatAmt = SvcUtils.getRoundingOffBah(Double.parseDouble(Currency
							.getFormattedCurrency(transactionVatAmt, commonVO.getLob().toString()).replace(",", "")));
					payableAmt = SvcUtils.getRoundingOffBah(Double.parseDouble(
							Currency.getFormattedCurrency(payableAmt, commonVO.getLob().toString()).replace(",", "")));
					request.setAttribute(com.Constant.CONST_VATTAXDISPLAY, df3.format(transactionVatAmt));
					double transactionPrm = transactionVatAmt + payableAmt;

					request.setAttribute(com.Constant.CONST_TRANSACTION_PREMIUM, Currency
							.getFormattedCurrency(new BigDecimal(transactionPrm), commonVO.getLob().toString()));
				}

			}
		} else {
			if (commonVO.getLocCode().equals(50)) {
				Double payablePremiumNew = SvcUtils.getRoundingOffBah(Double.parseDouble(
						Currency.getFormattedCurrency(payablePremium, commonVO.getLob().toString()).replace(",", "")));
				request.setAttribute(com.Constant.CONST_PAYABLEPREMIUM,
						Currency.getFormattedCurrency(payablePremiumNew, commonVO.getLob().toString()));
			} else {
				request.setAttribute(com.Constant.CONST_PAYABLEPREMIUM,
						Currency.getFormattedCurrency(payablePremium, commonVO.getLob().toString()));
			}

		}

		// for the 'direct walk in',direct web,direct call center and brokers,
		// commission field should be disabled
		if (AppUtils.isBrokerOrDirectWalkin(homeInsuranceVO, request)) {
			request.setAttribute("isCommissionEditable", AppConstants.TRUE);
		} else {
			request.setAttribute("isCommissionEditable", AppConstants.FALSE);
		}
		// 142244

		/*
		 * CR:123969 Added by Vishwa to disable discountLoading for FGB broker
		 * users
		 */

		if (AppUtils.isFGBBroker(homeInsuranceVO, request)) {
			LOGGER.info("isFGBBroker.. true");
			request.setAttribute("isDiscLoadEditable", AppConstants.TRUE);
		} else {
			LOGGER.info("isFGBBroker.. false");
			request.setAttribute("isDiscLoadEditable", AppConstants.FALSE);
		}

		// for ANA Scheme, policy fees is editable
		String[] schemeCodes = Utils.getMultiValueAppConfig("POLICY_FEES_ENABLED_SCHEMES");
		// Integer ANACode = 1204;//SvcUtils.getLookUpCode( "SCHEME", "ALL",
		// "ALL", "ANA Scheme" );
		if (!Utils.isEmpty(homeInsuranceVO) && !Utils.isEmpty(homeInsuranceVO.getScheme())
				&& !Utils.isEmpty(homeInsuranceVO.getScheme().getSchemeCode())
				&& Arrays.asList(schemeCodes).contains(homeInsuranceVO.getScheme().getSchemeCode().toString())) {
			request.setAttribute("isPolicyFeesEditable", AppConstants.TRUE);
		} else {
			request.setAttribute("isPolicyFeesEditable", AppConstants.FALSE);
		}
		if (isPolicyCancelled) {
			request.setAttribute("premiumDiscountAmount",
					Currency.getFormattedCurrency(totalDiscountAmount, commonVO.getLob().toString()));
			homeInsuranceVO.getPremiumVO().setDiscOrLoadPerc(0.0);
		} else {
			request.setAttribute("premiumDiscountAmount",
					Currency.getFormattedCurrency(
							(homeInsuranceVO.getPremiumVO().getDiscOrLoadPerc() * totalPremium) / 100,
							commonVO.getLob().toString()));
		}
		request.setAttribute("policyFees", Currency.getFormattedCurrency(homeInsuranceVO.getPremiumVO().getPolicyFees(),
				commonVO.getLob().toString()));
		request.setAttribute("govtTax", Currency.getFormattedCurrency(homeInsuranceVO.getPremiumVO().getGovtTax(),
				commonVO.getLob().toString()));
		request.setAttribute("distributionChannel",
				homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDistributionChannel());
		request.setAttribute("premiumAfterCoverDiscount",
				Currency.getFormattedCurrency(totalPremium, commonVO.getLob().toString()));
		request.setAttribute("totalPremium", Currency.getFormattedCurrency(totalPremium, commonVO.getLob().toString()));
		request.setAttribute("premiumAfterDiscount",
				Currency.getFormattedCurrency(premiumAfterDiscount, commonVO.getLob().toString()));
		if (loggedInLoc == 20 || loggedInLoc == 21) {
			request.setAttribute("commissionAmount", Currency.getFormattedCurrency(commissionAmount, "SBS"));
		}
		if (loggedInLoc == 50) {
			// Added for Bahrain Commission changes
			double formattedCommAmt = SvcUtils.getRoundingOffBah(Double.parseDouble(
					Currency.getFormattedCurrency(commissionAmount, commonVO.getLob().toString()).replace(",", "")));
			request.setAttribute("commissionAmount",
					Currency.getFormattedCurrency(formattedCommAmt, commonVO.getLob().toString()));
		}
		request.setAttribute("promoDiscountAmt",
				Currency.getFormattedCurrency(promoDiscountAmt, commonVO.getLob().toString()));
		request.setAttribute("schemeCode", homeInsuranceVO.getScheme().getSchemeCode());

		// 142244 Vat
		/*
		 * request.setAttribute(com.Constant.CONST_VATTAX,
		 * Currency.getFormattedCurrency(
		 * homeInsuranceVO.getPremiumVO().getVatTax(), "SBS"));
		 */
		request.setAttribute("vatTaxPerc", homeInsuranceVO.getPremiumVO().getVatTaxPerc());

		request.setAttribute("vatCode", homeInsuranceVO.getVatCode());

	}

	/**
	 * @param policyDataVO
	 * @param premiumVO
	 * @return minPremium calculation of prorated Min Prem
	 */
	private double getMinPremiumForCancellation(PolicyDataVO policyDataVO, PremiumVO premiumVO) {
		double minPrmCancel = PremiumHelper.totalMinCancelPrm(policyDataVO, premiumVO);
		return minPrmCancel;
	}

	/**
	 * @param policyDataVO
	 * @param premiumVO
	 * @return calculation of totalPremium for 'Total Premium' field in premium
	 *         detail
	 */
	private double getTotalPremiumForCancellation(PolicyDataVO policyDataVO, PremiumVO premiumVO) {

		double newAnnualizedPremium = 0;
		double totalPremium = 0;
		double totalDiscountPercent = 0;

		/*
		 * if(policyDataVO.getEndorsmentVO().size()>0){ newAnnualizedPremium =
		 * policyDataVO.getEndorsmentVO().get( 0
		 * ).getPremiumVO().getPremiumAmt();
		 * 
		 * if(!Utils.isEmpty( premiumVO.getPromoDiscPerc() )){
		 * totalDiscountPercent += premiumVO.getPromoDiscPerc(); }
		 * 
		 * if(!Utils.isEmpty( premiumVO.getDiscOrLoadPerc() )){
		 * totalDiscountPercent -= premiumVO.getDiscOrLoadPerc(); }
		 * 
		 * totalPremium = (newAnnualizedPremium * 100) / (100 -
		 * totalDiscountPercent); }
		 */
		totalPremium = PremiumHelper.totalCancelPrm(policyDataVO, premiumVO);
		return totalPremium;
	}

	private void updateListItemDetails(HomeInsuranceVO homeInsuranceVO, PolicyContext policyContext) {
		CoverDetails coverDetails = new CoverDetails();
		for (CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers()) {
			if (coverDetailsVO.getRiskCodes().getRiskCat().equals(AppConstants.HOME_LIST_ITEM_RISK_CATEGORY)) {
				coverDetailsVO.setIsCovered(AppConstants.STATUS_ON);
				coverDetails.getCoverDetails().add(coverDetailsVO);
			}
		}
		policyContext.setCoverDetails(coverDetails);
	}

	private static Date getDateWithoutTimeStamp(Date date) {
		DateFormat df = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT);
		String dateString = df.format(date);
		Date dateWithoutTimestamp = null;

		try {
			dateWithoutTimestamp = df.parse(dateString);
		} catch (ParseException e) {
			System.out.println("Exception in getDateWithoutTimeStamp method of Premium Helper");
		}

		return dateWithoutTimestamp;
	}

	/**
	 * Returns the dates absolute difference in days including the end date in
	 * calculation
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static long getDifference(Date a, Date b) {
		long days = ((a.getTime() - b.getTime()) / (1000 * 60 * 60 * 24));
		days = days < 0 ? days * -1 : days;
		return (days + 1);
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
		if (!Utils.isEmpty(QuoteNum)) {
			// polIssueDt = DAOUtils.getPolIssueDate(QuoteNum, isQuote, endtId);
			// polIssueDt = DAOUtils.getPolModOrPrepdateDate(QuoteNum, isQuote,
			// endtId);
			polIssueDt = SvcUtils.populatePolEDt(QuoteNum);
		}
		// Date polIssueDt = DAOUtils.getIssueDateForCovers(QuoteNum);

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
	// CTS 06.08.2020 CR#613 -Document attachment change
	private boolean PLdocumentExists(HomeInsuranceVO homeInsuranceVO) {
		// TODO Auto-generated method stub
		boolean documentExists = false;
		Long QuoteNum = homeInsuranceVO.getCommonVO().getQuoteNo();
		// CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
		String InsuredCode = DAOUtils.FetchInsuredCode(QuoteNum.toString());
		if (!Utils.isEmpty(QuoteNum)) {
			String filepath = Utils.getSingleValueAppConfig("FILE_UPLOAD_ROOT") + "/"
					+ Utils.getSingleValueAppConfig(
							"FILE_UPLOAD_" + Utils.getSingleValueAppConfig("HOME_DEFAULT_SCREENID") + "_FOLDER")
					+ "/" + QuoteNum + "/";
			File folder = new File(filepath);
			File listOfFiles[] = folder.listFiles();

			if (!Utils.isEmpty(InsuredCode)) {

				String Insuredfilepath = Utils.getSingleValueAppConfig("FILE_UPLOAD_ROOT") + "/"
						+ Utils.getSingleValueAppConfig(
								"FILE_UPLOAD_" + Utils.getSingleValueAppConfig("EMIRATE_DEFAULT_DOCID") + "_FOLDER")
						+ "/" + InsuredCode + "/";
				File Insuredfolder = new File(Insuredfilepath);
				File EmirateslistOfFiles[] = Insuredfolder.listFiles();

				if (!Utils.isEmpty(listOfFiles) || !Utils.isEmpty(EmirateslistOfFiles)) {
					documentExists = true;
				}
			}
		}
		// CTS - 21.10.2020 - CR#16903 IA Emirates CR - End
		return documentExists;
	}
	// CTS 06.08.2020 CR#613 -Document attachment change
}
