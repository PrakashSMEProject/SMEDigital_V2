/**
 * 
 */
package com.rsaame.pas.b2c.cmn.handlers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.springframework.security.core.codec.Base64;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.b2c.homeInsurance.HomeInsuranceSvcHandler;
import com.rsaame.pas.b2c.travelInsurance.TravelInsuranceHandler;
import com.rsaame.pas.b2c.ws.vo.CreatePolicyRequest;
import com.rsaame.pas.b2c.ws.vo.Documents;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.doc.svc.PASDocumentService;
import com.rsaame.pas.mail.svc.PASMailerService;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.MailVO;
import com.rsaame.pas.vo.app.ReportTemplateSet;
import com.rsaame.pas.vo.bus.DebitNoteDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PaymentDetailsVO;
import com.rsaame.pas.vo.bus.PaymentVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReceiptDetailsVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * 
 * @author Sarath, M1020859, M1033804
 * @since Phase 3, FGB MS
 *
 */
public class CommonHandler {

	/** Logger instance */
	private static final Logger logger = Logger.getLogger(CommonHandler.class);

	/**
	 * Method to prepare payment gateway attributes
	 * 
	 * @param policyDataVO
	 * @param paymentDetails
	 * @return paymentDetails HashMap
	 */
	public HashMap<String, String> populatePaymentDetails(PolicyDataVO policyDataVO,
			Map<String, String> paymentDetails) {

		logger.info("CommonHandler.populatePaymentDetails method - Entered");

		Boolean isOman = false;

		Boolean isDubai = false;
		/*
		 * D2C Gender Validation : BEGIN
		 */
		LoginLocation location = (LoginLocation) Utils.getBean("location");
		String deployedLocation = location.getLocation();
		if (null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE))
			isOman = true;
		else if (null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE_DUABI))
			isDubai = true;

		PaymentSecurity paymentSecurity = new PaymentSecurity();
		String billToAddress1 = "", billTozipCode = "", billToCity = "", billToState = "", billToSurname = "",
				billToFirstName = "", billToCountry = "", billToPostCode = "", billCity = "", billZip = "",
				billState = "", billCountry = "", billToEmail = "", comments = "", amount = "";

		if (policyDataVO.getCommonVO().getLob() == LOB.HOME) {
			HomeInsuranceVO homeVO = (HomeInsuranceVO) policyDataVO;
			billToAddress1 = homeVO.getBuildingDetails().getArea();
			billTozipCode = homeVO.getBuildingDetails().getFlatVillaNo();
			billToCity = homeVO.getBuildingDetails().getEmirates(); // TODO : is
																	// emirates
																	// numeric?
			paymentDetails.put("merchant_defined_data9", LOB.HOME.toString());
		} else {
			TravelInsuranceVO travelVO = (TravelInsuranceVO) policyDataVO;
			billToAddress1 = travelVO.getGeneralInfo().getInsured().getAddress().getAddress();
			billTozipCode = "123456";
			if (isOman) {
				billToCity = "Oman";
			} else
				billToCity = "dubai";// travelVO.getGeneralInfo().getInsured().getAddress().getEmirates().toString();
			paymentDetails.put("merchant_defined_data9", LOB.TRAVEL.toString());
		}
		billToSurname = policyDataVO.getGeneralInfo().getInsured().getLastName();
		billToFirstName = policyDataVO.getGeneralInfo().getInsured().getFirstName();
		billToCountry = "AE";// policyDataVO.getGeneralInfo().getInsured().getAddress().getCountry().toString();
		billToPostCode = policyDataVO.getGeneralInfo().getInsured().getAddress().getPoBox();
		billToEmail = policyDataVO.getGeneralInfo().getInsured().getEmailId();
		billToState = "CA";// policyDataVO.getGeneralInfo().getInsured().getAddress().getEmirates().toString();
		comments = policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerId() + "~"
				+ policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName() + "~"
				+ policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo();
		if (isDubai) {

			// 03.04.2020 CTS - Post Prod Issue - Unable to proceed to payment,
			// due to amount not rounded off - commented below and added
			// amount =
			// Double.toString(policyDataVO.getPremiumVO().getPremiumAmt());
			amount = Currency.getFormattedCurrency(policyDataVO.getPremiumVO().getPremiumAmt());
			logger.info("Before Rounded Off val" + Double.toString(policyDataVO.getPremiumVO().getPremiumAmt()));
			logger.info("After Formatted new val" + amount);
		} else {
			amount = Currency.getFormattedCurrency(policyDataVO.getPremiumVO().getPremiumAmt());
		}
		billCity = billToCity;
		billZip = billTozipCode;
		billState = billToState;
		billCountry = billToCountry;

		logger.info("CommonHandler.populatePaymentDetails method, location:" + location + " deployedLocation:"
				+ deployedLocation + " billToAddress1:" + billToAddress1 + " billTozipCode:" + billTozipCode
				+ " billToCity:" + billToCity + " billToSurname:" + billToSurname + " billToFirstName:"
				+ billToFirstName + " billToCountry:" + billToCountry + " billToPostCode:" + billToPostCode
				+ " billToEmail:" + billToEmail + " billToState:" + billToState + " comments:" + comments + " amount:"
				+ amount);

		String accessKey = null;
		String profileId = null;
		String securityKey = null;
		if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
			accessKey = Utils
					.getSingleValueAppConfig("PAYMENT_GATEWAY_ACCESS_KEY_" + policyDataVO.getCommonVO().getLob());
			profileId = Utils
					.getSingleValueAppConfig("PAYMENT_GATEWAY_PROFILE_ID_" + policyDataVO.getCommonVO().getLob());
			securityKey = Utils
					.getSingleValueAppConfig("PAYMENT_GATEWAY_SECRET_KEY_" + policyDataVO.getCommonVO().getLob());
			logger.info("CommonHandler.populatePaymentDetails method, accessKey:_1" + accessKey + " , profileId:_1"
					+ profileId + " , securityKey:_1" + securityKey);
		} else {
			if (AppConstants.DIST_CHANNEL_DIRECT_WEB
					.equals(policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel())) {
				accessKey = Utils.getSingleValueAppConfig(
						"PAYMENT_GATEWAY_RSACAMPAIGNE_ACCESS_KEY_" + policyDataVO.getCommonVO().getLob());
				profileId = Utils.getSingleValueAppConfig(
						"PAYMENT_GATEWAY_RSACAMPAIGNE_PROFILE_ID_" + policyDataVO.getCommonVO().getLob());
				securityKey = Utils.getSingleValueAppConfig(
						"PAYMENT_GATEWAY_RSACAMPAIGNE_SECRET_KEY_" + policyDataVO.getCommonVO().getLob());
				logger.info("CommonHandler.populatePaymentDetails method, accessKey:_2" + accessKey + " , profileId:_2"
						+ profileId + " , securityKey:_2" + securityKey);
			} else {
				accessKey = Utils.getSingleValueAppConfig(
						"PAYMENT_GATEWAY_PARTNER_ACCESS_KEY_" + policyDataVO.getCommonVO().getLob());
				profileId = Utils.getSingleValueAppConfig(
						"PAYMENT_GATEWAY_PARTNER_PROFILE_ID_" + policyDataVO.getCommonVO().getLob());
				securityKey = Utils.getSingleValueAppConfig(
						"PAYMENT_GATEWAY_PARTNER_SECRET_KEY_" + policyDataVO.getCommonVO().getLob());
				logger.info("CommonHandler.populatePaymentDetails method, accessKey:_3" + accessKey + " , profileId:_3"
						+ profileId + " , securityKey:_3" + securityKey);
			}
		}

		paymentDetails.put("access_key", accessKey);
		paymentDetails.put("bill_to_email", billToEmail);
		paymentDetails.put("profile_id", profileId);
		paymentDetails.put("payment_token_comments", comments);
		paymentDetails.put("transaction_type", "sale");
		paymentDetails.put("locale", "en-US");
		paymentDetails.put("bill_to_address_line1", billToAddress1);
		paymentDetails.put("transaction_uuid", UUID.randomUUID().toString());
		paymentDetails.put("bill_city", billCity);
		if (isOman)
			paymentDetails.put("currency", "OMR");
		else
			paymentDetails.put("currency", "AED");

		/*
		 * D2C Amount to be sent without comma : BEGIN
		 */
		if (null != amount && !amount.equals("")) {
			if (amount.contains(",")) {
				amount = amount.replace(",", "");
			}
		}
		paymentDetails.put("amount", amount);
		paymentDetails.put("signed_field_names", "access_key,profile_id,reference_number,amount,currency,"
				+ "transaction_uuid,transaction_type,signed_field_names,unsigned_field_names,locale,"
				+ "signed_date_time,bill_to_forename,bill_to_surname,bill_to_email,"
				+ "bill_to_address_line1,bill_to_address_city,bill_to_address_country,bill_country,"
				+ "bill_to_address_state,bill_to_address_postal_code,merchant_id,device_fingerprint_id,customer_ip_address,"
				+ "merchant_defined_data1,merchant_defined_data3,merchant_defined_data8,merchant_defined_data9,"
				+ "merchant_defined_data10,merchant_defined_data11,merchant_defined_data21,payment_token_comments");
		// paymentDetails.put("bill_to_zip", billTozipCode);

		if (isOman)
			paymentDetails.put("merchant_id", Utils.getSingleValueAppConfig("merchant_id_oman"));
		else
			paymentDetails.put("merchant_id", Utils.getSingleValueAppConfig("merchant_id_dubai"));

		paymentDetails.put("unsigned_field_names", "");
		paymentDetails.put("bill_to_address_city", billToCity);
		paymentDetails.put("signed_date_time", getUTCDateTime());
		paymentDetails.put("bill_to_address_state", billToState);
		paymentDetails.put("bill_to_surname", billToSurname);
		paymentDetails.put("bill_to_address_country", billToCountry);
		paymentDetails.put("bill_to_address_postal_code", "999999999");
		paymentDetails.put("bill_zip", billZip);
		paymentDetails.put("request_date_time", getRequestDateTime());
		paymentDetails.put("reference_number", getRefNumber(policyDataVO));
		paymentDetails.put("bill_to_forename", billToFirstName);
		// paymentDetails.put("bill_state", billState);;
		paymentDetails.put("bill_country", billCountry);
		paymentDetails.put("merchant_defined_data1", "0");
		paymentDetails.put("merchant_defined_data3", "Web");
		paymentDetails.put("merchant_defined_data8", "Insurance");
		paymentDetails.put("merchant_defined_data10", "online");
		paymentDetails.put("merchant_defined_data11", "Online");
		paymentDetails.put("merchant_defined_data21", "1");
		try {
			paymentDetails.put("signature",
					paymentSecurity.sign((HashMap<String, String>) paymentDetails, securityKey));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new BusinessException("PaymentKeyException", e.getCause(),
					"Error while preparing for payment. Please contact Administrator");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new BusinessException("PaymentAlgorithmException", e.getCause(),
					"Error while preparing for payment. Please contact Administrator");
		}

		return (HashMap<String, String>) paymentDetails;
	}

	private String getUTCDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(new Date());
	}

	// 143948 Request time in Dubai Date Time
	private String getRequestDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * Creates referance number of the payment transaction
	 * 
	 * @param policyDataVO
	 * @return
	 */
	private String getRefNumber(PolicyDataVO policyDataVO) {
		String quoteNo = policyDataVO.getCommonVO().getQuoteNo().toString();
		String policyId = policyDataVO.getCommonVO().getPolicyId().toString();
		String policyType = "000".concat(policyDataVO.getPolicyType().toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String polType = policyType.substring(policyType.length() - 3);
		return polType + AppConstants.DELIMITER + quoteNo + AppConstants.DELIMITER + policyId + AppConstants.DELIMITER
				+ sdf.format(new Date());
	}

	/**
	 * Method to conver the quote to policy
	 * 
	 * @param homeInsuranceData
	 * @return
	 */
	public PolicyDataVO convertToPolicy(PolicyDataVO policyDataVO, boolean isWebServiceReq, String path) {
		logger.info("Entered CommonHandler:convertToPolicy method - starts");

		try {

			List<BaseVO> inputVoList = new ArrayList<BaseVO>();
			inputVoList.add(new PolicyVO());

			PaymentVO paymentvo = populatePaymentVO(policyDataVO, isWebServiceReq);
			if (!Utils.isEmpty(paymentvo)) {
				inputVoList.add(paymentvo);
			}

			if (!Utils.isEmpty(policyDataVO.getCommonVO())) {
				inputVoList.add(policyDataVO.getCommonVO());
			} else {
				inputVoList.add(new CommonVO());
			}

			DataHolderVO<List<BaseVO>> dataHolderVO = new DataHolderVO<List<BaseVO>>();

			dataHolderVO.setData(inputVoList);
			// Convert to policy email trigger Start
			CommonVO commonVO = (CommonVO) Utils.getBean("VO_COMMON");

			logger.info("Calling ConvertToPolicy Service - starts");
			commonVO = (CommonVO) TaskExecutor.executeTasks("CONVERT_TO_POLICY", dataHolderVO);

			// Convert to policy email trigger Start - DO NOT REMOVE THE BELOW
			// CODE
			if (!Utils.isEmpty(commonVO) && !Utils.isEmpty(commonVO.getPolicyNo())) {
				commonVO.setIsQuote(Boolean.FALSE);

				AppUtils.populateCommonVO(commonVO);

			}
			if (Utils.isEmpty(commonVO.getPolicyNo()) && Utils.isEmpty(commonVO.getConcatPolicyNo())) {
				throw new BusinessException("pasb2c.quote.declined", null, "quote declined");
			}
			logger.debug("new common vo is - " + commonVO);
			// Loading general info for email trigger start
			if (!Utils.isEmpty(commonVO) && commonVO.getLob().equals(LOB.TRAVEL)) {
				policyDataVO.setCommonVO(commonVO);
				TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) Utils.getBean("VO_TRAVEL");
				travelInsuranceVO.setCommonVO(commonVO);
				if (!(Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName()))) {
					if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo())) {
						GeneralInfoVO generalInfo = new GeneralInfoVO();
						travelInsuranceVO.setGeneralInfo(generalInfo);
					}
					if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getSourceOfBus())) {
						SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
						travelInsuranceVO.getGeneralInfo().setSourceOfBus(sourceOfBusinessVO);
					}
					travelInsuranceVO.setScheme(new SchemeVO());
					travelInsuranceVO.getGeneralInfo().getSourceOfBus()
							.setPartnerName(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName());
					logger.info("Before calling TravelInsuranceHandler.loadPartnerMgmtDetails method");
					travelInsuranceVO = (TravelInsuranceVO) TravelInsuranceHandler
							.loadPartnerMgmtDetails(travelInsuranceVO);
				}
				logger.info("Before calling GeneralInfoCommonSvc.loadGeneralInfo method");
				policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks("GEN_INFO_COMMON_LOAD", travelInsuranceVO);
				if (!(Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerId()))) {

					if (Utils.isEmpty(policyDataVO.getGeneralInfo())) {
						policyDataVO.setGeneralInfo(travelInsuranceVO.getGeneralInfo());
					}
					if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus())) {
						policyDataVO.getGeneralInfo()
								.setSourceOfBus(travelInsuranceVO.getGeneralInfo().getSourceOfBus());
					}
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setPartnerId(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerId());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setPartnerName(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerName());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setCcEmailId(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getCcEmailId());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setReplyToEmailId(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getReplyToEmailId());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setCallCentreNo(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
					policyDataVO.getGeneralInfo().getSourceOfBus().setSourceOfBusiness(
							travelInsuranceVO.getGeneralInfo().getSourceOfBus().getSourceOfBusiness());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setFromEmailID(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getFromEmailID());
					policyDataVO.getGeneralInfo().getSourceOfBus().setDefaultOnlineDiscount(
							travelInsuranceVO.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount());
					policyDataVO.getGeneralInfo().getSourceOfBus().setDefaultAssignToUser(
							travelInsuranceVO.getGeneralInfo().getSourceOfBus().getDefaultAssignToUser());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setFaqUrl(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getFaqUrl());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setPolicyTermUrl(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getPolicyTermUrl());
				}
			} else {
				policyDataVO.setCommonVO(commonVO);
				HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) Utils.getBean("VO_HOME");
				homeInsuranceVO.setCommonVO(commonVO);
				if (!(Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName()))) {
					if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo())) {
						GeneralInfoVO generalInfo = new GeneralInfoVO();
						homeInsuranceVO.setGeneralInfo(generalInfo);
					}
					if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())) {
						SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
						homeInsuranceVO.getGeneralInfo().setSourceOfBus(sourceOfBusinessVO);
					}
					homeInsuranceVO.setScheme(new SchemeVO());
					homeInsuranceVO.getGeneralInfo().getSourceOfBus()
							.setPartnerName(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName());
					logger.info("Before calling HomeInsuranceSvcHandler.loadPartnerMgmtDetails method");
					homeInsuranceVO = (HomeInsuranceVO) HomeInsuranceSvcHandler.loadPartnerMgmtDetails(homeInsuranceVO);
				}

				logger.info("Before calling GeneralInfoCommonSvc.loadGeneralInfo method");
				policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks("GEN_INFO_COMMON_LOAD", homeInsuranceVO);
				if (!(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerId()))) {

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
							.setCcEmailId(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getCcEmailId());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setReplyToEmailId(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getReplyToEmailId());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setCallCentreNo(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setFromEmailID(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getFromEmailID());
					policyDataVO.getGeneralInfo().getSourceOfBus().setSourceOfBusiness(
							homeInsuranceVO.getGeneralInfo().getSourceOfBus().getSourceOfBusiness());
					policyDataVO.getGeneralInfo().getSourceOfBus().setDefaultOnlineDiscount(
							homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount());
					policyDataVO.getGeneralInfo().getSourceOfBus().setDefaultAssignToUser(
							homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDefaultAssignToUser());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setFaqUrl(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getFaqUrl());
					policyDataVO.getGeneralInfo().getSourceOfBus()
							.setPolicyTermUrl(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPolicyTermUrl());

				}
			}

			logger.info("Before calling CommonOpSvc.populateCommonDetails method");
			// Loading vsd from t_trn_policy start
			CommonVO commonvo = (CommonVO) TaskExecutor.executeTasks("POPULATE_COMMON_DETAILS",
					policyDataVO.getCommonVO());
			if (!Utils.isEmpty(commonvo)) {
				policyDataVO.setCommonVO(commonvo);
			}
			if (commonvo.getLob().equals(LOB.HOME) && !isWebServiceReq) {
				logger.info("CommonHandler:convertToPolicy, before triggering mail for HOME LOB.");
				populateAndTriggerEmail(policyDataVO, path, B2CEmailTriggers.HOME_CONVERT_TO_POLICY, null);
			} else if (!isWebServiceReq) {
				logger.info("CommonHandler:convertToPolicy, before triggering mail for TRAVEL LOB.");
				populateAndTriggerEmail(policyDataVO, path, B2CEmailTriggers.TRAVEL_CONVERT_TO_POLICY, null);
			}
			/*
			 * if (!Utils.isEmpty(policyDataVO) &&
			 * !Utils.isEmpty(policyDataVO.getCommonVO().getPolicyNo())) {
			 * String emailTemplateContent = null; MailVO mailVO =
			 * (MailVO)Utils.getBean(com.Constant.CONST_MAILVO); boolean
			 * successFlag = false; if
			 * (policyDataVO.getCommonVO().getLob().equals(LOB.TRAVEL)) {
			 * emailTemplateContent =
			 * AppUtils.getTempalteContentAsString(AppConstants
			 * .B2C_TRAVEL_SUCCESS_POLICY_PURCHASE_TEMPLATE);
			 * mailVO.setSubjectText
			 * (AppConstants.B2C_TRAVEL_POLICY_EMAIL_SUBJECT); } else if
			 * (policyDataVO.getCommonVO().getLob().equals(LOB.HOME)) {
			 * emailTemplateContent =
			 * AppUtils.getTempalteContentAsString(AppConstants
			 * .B2C_HOME_SUCCESS_POLICY_PURCHASE_TEMPLATE);
			 * mailVO.setSubjectText
			 * (AppConstants.B2C_HOME_POLICY_EMAIL_SUBJECT); } if
			 * (!Utils.isEmpty(emailTemplateContent)) { emailTemplateContent =
			 * emailTemplateContent
			 * .replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
			 * policyDataVO.getGeneralInfo().getInsured().getName());
			 * StringBuilder emailContent = new
			 * StringBuilder(emailTemplateContent); mailVO.setFromAddress(
			 * AppConstants.B2C_DEFAULT_FROM_EMAILID ); mailVO.setToAddress(
			 * policyDataVO.getGeneralInfo().getInsured().getEmailId() );
			 * mailVO.setSignature( "RSA" ); mailVO.setCreatedOn( new Timestamp(
			 * Calendar.getInstance().getTime().getTime() ) ); //Setting the
			 * current time stamp mailVO.setMailType(
			 * SvcConstants.MAIL_TYPE_HTML ); mailVO.setMailContent( new
			 * StringBuilder(emailContent) ); successFlag = sendEmail(mailVO,
			 * policyDataVO); } if (!successFlag) { logger.
			 * debug("Policy documents cannot be sent for policy number - "
			 * +policyDataVO.getCommonVO().getPolicyNo()); } }
			 */
			// Convert to policy email trigger End
		} catch (BusinessException be) {
			throw new BusinessException(be.getErrorKeysList().get(0), be.getCause(), be.getMessage());
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		logger.info("Exiting CommonHandler:convertToPolicy method.");
		return policyDataVO;
	}

	/**
	 * Method to populate the paymentVO with default values wrt online payment
	 * 
	 * @param policyDataVO
	 * @return
	 */
	private PaymentVO populatePaymentVO(PolicyDataVO policyDataVO, boolean isWebServiceReq) {

		PaymentVO paymentvo = new PaymentVO();

		LookUpListVO list = SvcUtils.getLookUpCodesList("PAS_PAY_MODE", "ALL", "ALL");
		paymentvo.setPaymentMode(list.getLookUpList().get(0).getDescription());
		paymentvo.setPayModeCode(Byte.valueOf(list.getLookUpList().get(0).getCode().toString()));

		if (isWebServiceReq) {

			paymentvo.setBankCode(14); // TODO remove hard coding
			paymentvo.setPremium(policyDataVO.getPremiumVO().getPremiumAmt());
			paymentvo.setAmount(policyDataVO.getPremiumVO().getPremiumAmt());

		} else {

			paymentvo.setBankCode(23); // TODO remove hard coding
			paymentvo.setPremium(policyDataVO.getOnlinePaymentDetailsVO().getRequestedPremiumAmt());
			paymentvo.setCreditCardNo(Integer.valueOf(policyDataVO.getOnlinePaymentDetailsVO().getCardNumber()
					.substring(policyDataVO.getOnlinePaymentDetailsVO().getCardNumber().length() - 4)));
			paymentvo.setChequeNo(Long.valueOf(policyDataVO.getOnlinePaymentDetailsVO().getCardNumber()
					.substring(policyDataVO.getOnlinePaymentDetailsVO().getCardNumber().length() - 4)));
			// paymentvo.setExpiryDate(
			// policyDataVO.getOnlinePaymentDetailsVO().getCardExipiryDate() );
			paymentvo.setAmount(policyDataVO.getOnlinePaymentDetailsVO().getAuthorizedPremiumAmt());

		}
		paymentvo.setPaymentDone(true);
		paymentvo.setLoggedInUser(policyDataVO.getLoggedInUser());

		logger.info("CommonHandler:populatePaymentVO " + " paymentMode:" + paymentvo.getPaymentMode() + " payModeCode:"
				+ paymentvo.getPayModeCode() + " bankCode:" + paymentvo.getBankCode() + " premium:"
				+ paymentvo.getPremium() + " amount:" + paymentvo.getAmount() + " CreditCardNo:"
				+ paymentvo.getCreditCardNo() + " checkNO:" + paymentvo.getChequeNo() + " IsPaymentDone:"
				+ paymentvo.isPaymentDone() + " loggedInUser:" + paymentvo.getLoggedInUser());

		return paymentvo;
	}

	/**
	 * Save online payment details to DB
	 * 
	 * @param paymentDetails
	 */
	public void saveOnlinePaymentDetails(PaymentDetailsVO paymentDetails) {
		logger.info("Entered CommonHandler:saveOnlinePaymentDetails, invoke service method to save paymentDetails");
		try {
			TaskExecutor.executeTasks("SAVE_ONLINE_PAYMENT_DETAILS", paymentDetails);
		} catch (BusinessException be) {
			throw new BusinessException(be.getErrorKeysList().get(0), be.getCause(), be.getMessage());
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	/**
	 * This is a common method to send email with attachment
	 * 
	 * @param mailVO
	 * @param policyDataVO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean sendEmail(MailVO mailVO, PolicyDataVO policyDataVO) {
		String[] fileNames = new String[1]; // Initially assumption is only
											// proposal document will be sent
		boolean mailStatus = false;
		HashMap<String, String> reportParams = null;
		CommonVO commonVO = policyDataVO.getCommonVO();
		DataHolderVO<Boolean> resultVo = null;
		logger.info("Inside sendEmail() of CommonHandler Class");
		// if (!Utils.isEmpty(policyDataVO.getCommonVO().getIsQuote()) &&
		// (policyDataVO.getCommonVO().getIsQuote()) &&
		// Utils.isEmpty(policyDataVO.getCommonVO().getPolicyNo())) {
		if (AppUtils.isQuote(policyDataVO.getCommonVO())) {
			fileNames[0] = Utils.getSingleValueAppConfig(com.Constant.CONST_QUOTE_DOC_PROPOSAL_LOC)
					+ policyDataVO.getCommonVO().getQuoteNo() + com.Constant.CONST_QUOTE_PDF;
		}
		if (!Utils.isEmpty(mailVO) && !Utils.isEmpty(policyDataVO) && !Utils.isEmpty(policyDataVO.getCommonVO())) {
			if (AppUtils.isQuote(commonVO) && !Utils.isEmpty(fileNames[0])) {
				// if (!Utils.isEmpty(policyDataVO.getCommonVO().getIsQuote())
				// && policyDataVO.getCommonVO().getIsQuote() &&
				// Utils.isEmpty(policyDataVO.getCommonVO().getPolicyNo()) &&
				// !Utils.isEmpty(fileNames[0])) {
				reportParams = populateQuoteReportParams(policyDataVO);
			} else {

				if (commonVO.getLob().equals(LOB.HOME)) {
					CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean(com.Constant.CONST_GECOMSVC);
					BaseVO baseVO = (BaseVO) commonOpSvc.invokeMethod(com.Constant.CONST_CHECKFORMORTGAGEENAME,
							commonVO);

					resultVo = (DataHolderVO<Boolean>) baseVO;

					if (!Utils.isEmpty(resultVo) && resultVo.getData()) {
						fileNames = new String[4];
					} else {
						fileNames = new String[3];
					}
				} else {
					fileNames = new String[3];
				}

				fileNames[0] = Utils.getSingleValueAppConfig(com.Constant.CONST_POL_DOC_POL_SCHED_LOC)
						+ policyDataVO.getCommonVO().getPolicyNo() + com.Constant.CONST_POLICYSCHEDULE_PDF;
				fileNames[1] = Utils.getSingleValueAppConfig(com.Constant.CONST_POL_DOC_POL_RECEIPTS_LOC)
						+ policyDataVO.getCommonVO().getPolicyNo() + com.Constant.CONST_RECEIPT_PDF;
				fileNames[2] = Utils.getSingleValueAppConfig(com.Constant.CONST_POL_DOC_DEB_NOTE_LOC)
						+ policyDataVO.getCommonVO().getPolicyNo() + com.Constant.CONST_TAXINVOICE_PDF;
				reportParams = populatePolicyReportParams(policyDataVO);

				setReceiptParameters(policyDataVO.getCommonVO(), reportParams);

				setTaxInvoiceParameters(policyDataVO.getCommonVO(), reportParams);

				if (!Utils.isEmpty(resultVo) && resultVo.getData()) {
					reportParams.put(com.Constant.CONST_SHOWBANKLETTER, "true");
					fileNames[3] = Utils.getSingleValueAppConfig("POL_DOC_BANK_LETTER") + commonVO.getPolicyNo()
							+ com.Constant.CONST_BANKLETTER_PDF;
				}
			}
			mailVO.setFileNames(fileNames);
			if (policyDataVO.getCommonVO().getLob().equals(LOB.TRAVEL)) { // Checking
																			// the
																			// LOB
																			// to
																			// set
																			// the
																			// report
																			// template
																			// type
																			// parameter
				reportParams.put(com.Constant.CONST_REPORTTEMPLATESTYPE, ReportTemplateSet._TRAVEL.toString());
			} else if (policyDataVO.getCommonVO().getLob().equals(LOB.HOME)) {
				reportParams.put(com.Constant.CONST_REPORTTEMPLATESTYPE, ReportTemplateSet._HOME.toString());
			}
			mailVO.setDocParameter(reportParams);
			// mailVO.setCcAddress(AppConstants.B2C_TRIGGER_EMAIL_CC_ID); //TODO
			// if required configure proper email id's in properties file
			try {

				PASDocumentService docCreator = (PASDocumentService) Utils.getBean(com.Constant.CONST_DOCSERVICEBEAN);
				mailVO = (MailVO) docCreator.invokeMethod(com.Constant.CONST_CREATEDOCUMENT, mailVO);
				if (!Utils.isEmpty(mailVO) && !mailVO.getDocCreationStatus().equalsIgnoreCase("failure")) {
					PASMailerService mailer = (PASMailerService) Utils.getBean(com.Constant.CONST_EMAILSERVICE);

					if (policyDataVO.getCommonVO().getIsQuote()
							&& (Utils.isEmpty(policyDataVO.getCommonVO().getPolicyNo())
									|| policyDataVO.getCommonVO().getDocCode().equals(
											Short.valueOf(Utils.getSingleValueAppConfig("REN_QUO_DOC_CODE"))))) {
						// Oman D2C Email template change - Start
						if (!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && Utils
								.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")) {
							mailVO = (MailVO) mailer.invokeMethod(com.Constant.CONST_SENDEMAILWITHIMAGE, mailVO,
									com.Constant.CONST_D2C_OMAN);
						} else {
							mailVO = (MailVO) mailer.invokeMethod(com.Constant.CONST_SENDEMAILWITHIMAGE, mailVO,
									com.Constant.CONST_QUOTE);
						}
						// Oman D2C Email template change - Start

					} else {

						String[] documentsList = null;
						if (!Utils.isEmpty(resultVo) && resultVo.getData()) {
							documentsList = new String[AppConstants.B2C_POLICY_ATTACHMENT_SIZE_MORTGAGE];
							documentsList[0] = mailVO.getFileNames()[0];
							documentsList[1] = mailVO.getFileNames()[1];
							documentsList[2] = mailVO.getFileNames()[2];
							documentsList[3] = mailVO.getFileNames()[3];
							/*
							 * if( policyDataVO.getCommonVO().getLob().equals(
							 * LOB.TRAVEL ) ){ documentsList[ 3 ] =
							 * AppConstants.B2C_EMAIL_POLICY_WORDING_TRAVEL; }
							 * else{ documentsList[ 3 ] =
							 * AppConstants.B2C_EMAIL_POLICY_WORDING_HOME; }
							 */
						} else {
							documentsList = new String[AppConstants.B2C_POLICY_ATTACHMENT_SIZE];
							documentsList[0] = mailVO.getFileNames()[0];
							documentsList[1] = mailVO.getFileNames()[1];
							documentsList[2] = mailVO.getFileNames()[2];
							/*
							 * if( policyDataVO.getCommonVO().getLob().equals(
							 * LOB.TRAVEL ) ){ documentsList[ 2 ] =
							 * AppConstants.B2C_EMAIL_POLICY_WORDING_TRAVEL; }
							 * else{ documentsList[ 2 ] =
							 * AppConstants.B2C_EMAIL_POLICY_WORDING_HOME; }
							 */
						}

						mailVO.setFileNames(documentsList);
						// Oman D2C Email template change - Start
						if (!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && Utils
								.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")) {
							mailVO = (MailVO) mailer.invokeMethod(com.Constant.CONST_SENDEMAILWITHIMAGE, mailVO,
									com.Constant.CONST_D2C_OMAN);
						} else {
							mailVO = (MailVO) mailer.invokeMethod(com.Constant.CONST_SENDEMAILWITHIMAGE, mailVO,
									"POLICY");
						}
						// Oman D2C Email template change - End
					}
				} else {
					mailVO.setMailStatus("fail");
				}
			} catch (Exception e) {
				mailVO.setMailStatus("fail");
				logger.error("Exception Occured sending the mail:" + e.getMessage());
			}
			if (!Utils.isEmpty(mailVO) && !Utils.isEmpty(mailVO.getMailStatus())
					&& mailVO.getMailStatus().equalsIgnoreCase(com.Constant.CONST_SUCCESS2)) {
				mailStatus = true;
				logger.info("Email sending Successful for sendEmail() of CommonHandler Class");
			}
		}

		return mailStatus;
	}

	/**
	 * Method to set the quote documents creation required parameters
	 * 
	 * @param policyDataVO
	 * @return
	 */
	private static HashMap<String, String> populateQuoteReportParams(PolicyDataVO policyDataVO) {
		Map<String, String> reportParameters = new HashMap<String, String>();
		reportParameters.put(com.Constant.CONST_POLICYID, String.valueOf(policyDataVO.getCommonVO().getPolicyId()));
		reportParameters.put("endoresementId", String.valueOf(policyDataVO.getCommonVO().getEndtId()));
		reportParameters.put("polValStartDate", getFormattedDateAsString(policyDataVO.getCommonVO().getVsd()));
		reportParameters.put(com.Constant.CONST_LANGUAGE, "E");
		reportParameters.put(com.Constant.CONST_LOCATIONCODE,
				Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));
		return (HashMap<String, String>) reportParameters;
	}

	/**
	 * Method to get the formatted date
	 * 
	 * @param date
	 * @return
	 */
	private static String getFormattedDateAsString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(com.Constant.CONST_DD_MMM_YYYY);
		return dateFormat.format(date);
	}

	/**
	 * This method will set the Policy Documents generation related required
	 * parameters
	 * 
	 * @param policyDataVO
	 * @return
	 */
	private static HashMap<String, String> populatePolicyReportParams(PolicyDataVO policyDataVO) {
		Map<String, String> reportParams = new HashMap<String, String>();
		String isRecipt = "true";
		String isPolicySchedule = "true";
		reportParams.put(com.Constant.CONST_POLICYID, String.valueOf(policyDataVO.getCommonVO().getPolicyId()));
		reportParams.put(com.Constant.CONST_ENDORSEMENTID, String.valueOf(policyDataVO.getCommonVO().getEndtId()));
		reportParams.put(com.Constant.CONST_VALIDITYSTARTDATE,
				getFormattedDateAsString(policyDataVO.getCommonVO().getVsd()));
		reportParams.put(com.Constant.CONST_LANGUAGE, "E");
		reportParams.put(com.Constant.CONST_RECEIPT, isRecipt);
		reportParams.put(com.Constant.CONST_POLICYSCHEDULE, isPolicySchedule);
		reportParams.put(com.Constant.CONST_LOCATIONCODE,
				Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));

		// Start Added by Mindtree on 12/01/2020 for CR:106399 – Set other
		// params which reports are not required as false
		reportParams.put("CreditNoteReport", com.Constant.CONST_FALSE);
		reportParams.put("GrossCreditNoteReport", com.Constant.CONST_FALSE);
		reportParams.put(com.Constant.CONST_DEBITNOTEREPORT, "true");
		reportParams.put("GrossDebitNoteReport", com.Constant.CONST_FALSE);
		reportParams.put("EndScheduleReport", com.Constant.CONST_FALSE);
		reportParams.put("policyScheduleClauses", com.Constant.CONST_FALSE);
		reportParams.put(com.Constant.CONST_SHOWBANKLETTER, com.Constant.CONST_FALSE);
		// End Added by Mindtree on 28/06/2015 for CR:106399 – Set other params
		// which reports are not required as false

		return (HashMap<String, String>) reportParams;
	}

	/**
	 * This method populates the mailVO from policyDataVO and pass the mailVO to
	 * PASMailerService for triggering the email
	 * 
	 * @param policyDataVO
	 * @return
	 */
	private static boolean emailForReferral(PolicyDataVO policyDataVO) {
		boolean mailStatus = false;
		MailVO mailVO = (MailVO) Utils.getBean(com.Constant.CONST_MAILVO);
		PASMailerService pasMailerService = (PASMailerService) Utils.getBean(com.Constant.CONST_EMAILSERVICE);
		populateReferralMailVO(mailVO, policyDataVO); // Call to populate the
														// mailVO for referral
														// email
		if (!Utils.isEmpty(mailVO) && !Utils.isEmpty(mailVO.getMailContent())) { // If
																					// mail
																					// content
																					// is
																					// null
																					// don't
																					// send
																					// the
																					// email
			try {
				pasMailerService.sendMail(mailVO); // This email doesn't
													// contains any images so it
													// is sent without any flow
				if (mailVO.getMailStatus().equalsIgnoreCase(com.Constant.CONST_SUCCESS2)) {
					mailStatus = true;
				}
			} catch (Exception e) {
				logger.debug("Exception occured while sending the email for referral - " + e.getMessage());
				return mailStatus;
			}
		} else {
			return mailStatus;
		}
		return mailStatus;
	}

	/**
	 * This is a generic method to send email only without any attachment based
	 * on flow
	 * 
	 * @param mailVO
	 * @return
	 */
	public static boolean sendGeneralEmail(MailVO mailVO, String flow) {
		boolean mailStatus = false;
		if (!Utils.isEmpty(mailVO)) {
			try {
				PASMailerService pasMailerService = (PASMailerService) Utils.getBean(com.Constant.CONST_EMAILSERVICE);
				if (Utils.isEmpty(flow)) {
					mailVO = (MailVO) pasMailerService.invokeMethod("sendMail", mailVO);
					if (!Utils.isEmpty(mailVO) && !Utils.isEmpty(mailVO.getMailStatus())
							&& mailVO.getMailStatus().equalsIgnoreCase(com.Constant.CONST_SUCCESS2)) {
						mailStatus = true;
					}
				} else if (flow.equalsIgnoreCase(com.Constant.CONST_QUOTE)) {
					// Oman D2C Email template change - Start
					if (!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION))
							&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")) {
						mailVO = (MailVO) pasMailerService.invokeMethod(com.Constant.CONST_SENDEMAILWITHIMAGE, mailVO,
								com.Constant.CONST_D2C_OMAN);
					} else {
						mailVO = (MailVO) pasMailerService.invokeMethod(com.Constant.CONST_SENDEMAILWITHIMAGE, mailVO,
								com.Constant.CONST_QUOTE);
					}
					// Oman D2C Email template change - End
					if (!Utils.isEmpty(mailVO) && !Utils.isEmpty(mailVO.getMailStatus())
							&& mailVO.getMailStatus().equalsIgnoreCase(com.Constant.CONST_SUCCESS2)) {
						mailStatus = true;
					}
				} else {
					return mailStatus;
				}
			} catch (Exception e) {
				logger.debug("Email could not be triggered because of - " + e.getMessage());
			}
		}
		return mailStatus;
	}

	/**
	 * Building the email body with the referral parameters
	 * 
	 * @param mailVO
	 * @param policyDataVO
	 */
	private static void populateReferralMailVO(MailVO mailVO, PolicyDataVO policyDataVO) {
		int rowCount = 1;
		String emailMessage = null;
		String lineBreak = com.Constant.CONST_BR_END;
		StringBuilder referralMessages = new StringBuilder();
		if (policyDataVO.getCommonVO().getLob().equals(LOB.TRAVEL)) {
			emailMessage = AppUtils.getTempalteContentAsString(AppConstants.B2C_TRAVEL_GI_REFERRAL_TEMPLATE);
		} else if (policyDataVO.getCommonVO().getLob().equals(LOB.HOME)) {
			emailMessage = AppUtils.getTempalteContentAsString(AppConstants.B2C_HOME_GI_REFERRAL_TEMPLATE);
		}
		if (!Utils.isEmpty(emailMessage)) {
			if (!Utils.isEmpty(policyDataVO.getReferralVOList().getReferrals())
					&& !Utils.isEmpty(policyDataVO.getReferralVOList().getReferrals().get(0).getRefDataTextField())) {
				for (String fieldName : policyDataVO.getReferralVOList().getReferrals().get(0).getRefDataTextField()
						.keySet()) {
					Map<String, String> referralValues = policyDataVO.getReferralVOList().getReferrals().get(0)
							.getRefDataTextField().get(fieldName);
					if (!Utils.isEmpty(referralValues)) {
						for (Map.Entry<String, String> refParams : referralValues.entrySet()) {
							referralMessages.append(rowCount).append(". ").append(refParams.getValue())
									.append(lineBreak);
							rowCount++;
						}
					}
				}
				emailMessage = emailMessage.replace(AppConstants.B2C_EMAIL_REFERRAL_REASON_IDENTIFIER,
						referralMessages.toString());
			}
			mailVO.setMailContent(new StringBuilder(emailMessage));

			if (!Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
				ReferralListVO referralListVO = policyDataVO.getReferralVOList();

				if (!Utils.isEmpty(referralListVO)) {
					TaskVO taskVO = referralListVO.getTaskVO();
					List<Object> emailAddressList = DAOUtils.getSqlResultSingleColumnPASNewSession(
							QueryConstants.FETCH_UNDERWRITER_EMAIL, taskVO.getAssignedTo());

					if (!Utils.isEmpty(emailAddressList) && emailAddressList.size() > 0) {
						mailVO.setToAddress(emailAddressList.get(0).toString());
					}
				}
				if (AppConstants.DIST_CHANNEL_BROKER
						.equals(policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel())) {

					mailVO.setFromAddress(AppConstants.B2C_DEFAULT_BROKER_FROM_EMAILID);
				} else {
					mailVO.setFromAddress(AppConstants.B2C_DEFAULT_FROM_EMAILID);
				}
				// Added by Vishwa
				logger.debug("populateReferralMailVO() T0-Email Id..."
						+ policyDataVO.getGeneralInfo().getSourceOfBus().getFromEmailID());
				mailVO.setToAddress(policyDataVO.getGeneralInfo().getSourceOfBus().getFromEmailID());
			} else {
				if (policyDataVO.getCommonVO().getLob().equals(LOB.TRAVEL)) {
					mailVO.setFromAddress(AppConstants.B2C_DEFAULT_FROM_EMAILID);
				} else if (policyDataVO.getCommonVO().getLob().equals(LOB.HOME)) {
					mailVO.setFromAddress(AppConstants.B2C_DEFAULT_FROM_EMAILID);
				}
				mailVO.setToAddress(AppConstants.B2C_REFERRAL_TRIGGER_TO_MAILID);
			}
			/*
			 * Use the same key (B2C_TRIGGER_EMAIL_CC_ID) to configure Cc email
			 * id's in appconfig.properties if required
			 */
			if (AppConstants.DIST_CHANNEL_BROKER
					.equals(policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel())) {
				if (!Utils.isEmpty(Utils.getMultiValueAppConfig("B2C_DEFAULT_BROKER_CC_EMAILID", ","))) {
					mailVO.setCcAddress(Utils.getMultiValueAppConfig("B2C_DEFAULT_BROKER_CC_EMAILID", ","));
				}
			} else {
				if (!Utils.isEmpty(Utils.getMultiValueAppConfig(com.Constant.CONST_B2C_TRIGGER_EMAIL_CC_ID, ","))) {
					mailVO.setCcAddress(Utils.getMultiValueAppConfig(com.Constant.CONST_B2C_TRIGGER_EMAIL_CC_ID, ","));
				}
			}
			if (policyDataVO.getCommonVO().getLob().equals(LOB.HOME)) {
				mailVO.setSubjectText(AppConstants.B2C_HOME_REFERRAL_EMAIL_SUBJECT.concat("")
						.concat(String.valueOf(policyDataVO.getCommonVO().getQuoteNo())));
			} else {
				mailVO.setSubjectText(AppConstants.B2C_TRAVEL_REFERRAL_EMAIL_SUBJECT.concat("")
						.concat(String.valueOf(policyDataVO.getCommonVO().getQuoteNo())));
			}
			mailVO.setSignature("RSA");
			mailVO.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			mailVO.setMailType(SvcConstants.MAIL_TYPE_HTML);
		}
	}

	/**
	 * @param policyDataVO
	 *            method to create document to be down loaded after convert to
	 *            policy.
	 */
	@SuppressWarnings("unchecked")
	public static void createDocumentForDownload(PolicyDataVO policyDataVO) {

		MailVO mailVO = new MailVO();
		HashMap<String, String> reportParams = new HashMap<String, String>();

		String isPolicySchedule = com.Constant.CONST_FALSE;
		String isRecipt = com.Constant.CONST_FALSE;
		String isBankLetter = com.Constant.CONST_FALSE;
		String isTaxInvoice = com.Constant.CONST_FALSE;

		DataHolderVO<Boolean> resultVo = null;

		String fileToDownLoad = null;
		CommonVO commonVO = policyDataVO.getCommonVO();

		getVsdForPolicy(commonVO);

		if (commonVO.getLob().equals(LOB.HOME)) {
			CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean(com.Constant.CONST_GECOMSVC);
			BaseVO baseVO = (BaseVO) commonOpSvc.invokeMethod(com.Constant.CONST_CHECKFORMORTGAGEENAME, commonVO);

			resultVo = (DataHolderVO<Boolean>) baseVO;
			if (!Utils.isEmpty(resultVo) && resultVo.getData()) {
				fileToDownLoad = Utils.getSingleValueAppConfig("B2C_CONV_POLICY_DOWNLOAD_DOCS_MORTGAGE");
			} else {
				fileToDownLoad = Utils.getSingleValueAppConfig("B2C_CONV_POLICY_DOWNLOAD_DOCS");
			}
		} else {
			fileToDownLoad = Utils.getSingleValueAppConfig("B2C_CONV_POLICY_DOWNLOAD_DOCS");
		}

		String[] fileNames = null;
		if (!Utils.isEmpty(fileToDownLoad)) {

			StringTokenizer st = new StringTokenizer(fileToDownLoad, ",");
			fileNames = new String[st.countTokens()];
			int numbDocs = st.countTokens();

			if (!Utils.isEmpty(commonVO) && !Utils.isEmpty(commonVO.getPolicyNo())) {
				for (int i = 0; i < numbDocs; i++) {

					String fileName = st.nextToken();

					if (fileName.equals("policyScheduleUAE")) {
						isPolicySchedule = "true";
						fileNames[i] = Utils.getSingleValueAppConfig(com.Constant.CONST_B2C_DOWNLOAD_FILE_PATH)
								+ commonVO.getPolicyNo() + "-PolicySchedules.pdf";
					} else if (fileName.equals("printReceipt")) {
						isRecipt = "true";
						setReceiptParameters(commonVO, reportParams);
						fileNames[i] = Utils.getSingleValueAppConfig(com.Constant.CONST_B2C_DOWNLOAD_FILE_PATH)
								+ commonVO.getPolicyNo() + "-PolicyReceipts.pdf";
					} else if (fileName.equals("printTaxInvoice")) {
						isTaxInvoice = "true";
						setTaxInvoiceParameters(commonVO, reportParams);
						fileNames[i] = Utils.getSingleValueAppConfig(com.Constant.CONST_B2C_DOWNLOAD_FILE_PATH)
								+ commonVO.getPolicyNo() + "-PolicyTaxInvoice.pdf";
					}
					if (!Utils.isEmpty(resultVo) && resultVo.getData()) {
						isBankLetter = "true";
						fileNames[i] = Utils.getSingleValueAppConfig(com.Constant.CONST_B2C_DOWNLOAD_FILE_PATH)
								+ commonVO.getPolicyNo() + com.Constant.CONST_BANKLETTER_PDF;
					}
				}
			}
		} else {
			BusinessException businessExcp = new BusinessException("mail.error", null, "Policy Number is Null");
			throw businessExcp;

		}

		reportParams.put(com.Constant.CONST_POLICYID, String.valueOf(commonVO.getPolicyId()));
		reportParams.put(com.Constant.CONST_ENDORSEMENTID, String.valueOf(commonVO.getEndtId()));
		reportParams.put(com.Constant.CONST_VALIDITYSTARTDATE, getFormattedDate(commonVO.getVsd()));
		reportParams.put(com.Constant.CONST_LANGUAGE, "E");
		reportParams.put(com.Constant.CONST_RECEIPT, isRecipt);
		reportParams.put(com.Constant.CONST_POLICYSCHEDULE, isPolicySchedule);
		reportParams.put(com.Constant.CONST_SHOWBANKLETTER, isBankLetter);

		/* Start : Set other params which reports are not required as false. */
		reportParams.put("CreditNoteReport", com.Constant.CONST_FALSE);
		reportParams.put("GrossCreditNoteReport", com.Constant.CONST_FALSE);
		reportParams.put(com.Constant.CONST_DEBITNOTEREPORT, isTaxInvoice);
		reportParams.put("GrossDebitNoteReport", com.Constant.CONST_FALSE);
		reportParams.put("EndScheduleReport", com.Constant.CONST_FALSE);
		reportParams.put("policyScheduleClauses", com.Constant.CONST_FALSE);
		reportParams.put(com.Constant.CONST_LOCATIONCODE,
				Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));

		/* End : Set other params which reports are not required as false. */

		mailVO.setFileNames(fileNames);
		if (policyDataVO.getCommonVO().getLob().equals(LOB.TRAVEL)) {
			reportParams.put(com.Constant.CONST_REPORTTEMPLATESTYPE, ReportTemplateSet._TRAVEL.toString());
		} else if (policyDataVO.getCommonVO().getLob().equals(LOB.HOME)) {
			reportParams.put(com.Constant.CONST_REPORTTEMPLATESTYPE, ReportTemplateSet._HOME.toString());
		}
		mailVO.setDocParameter(reportParams);
		PASDocumentService docCreator = (PASDocumentService) Utils.getBean(com.Constant.CONST_DOCSERVICEBEAN);
		mailVO = (MailVO) docCreator.invokeMethod("createDocumentForDownload", mailVO);

	}

	private static void setBankLetter(CommonVO commonVO) {
		// TODO Auto-generated method stub

	}

	public static CommonVO getVsdForPolicy(CommonVO commonVO) {

		/*
		 * Setting default values before querying as we do not have endorsements
		 * in B2C.
		 */
		commonVO.setIsQuote(Boolean.FALSE);
		commonVO.setEndtId(Long.valueOf(AppConstants.zeroVal));
		commonVO.setLocCode(AppConstants.B2C_DEFAULT_DEPLOYED_LOCATION);

		return (CommonVO) TaskExecutor.executeTasks("POPULATE_COMMON_DETAILS", commonVO);

	}

	/**
	 * Method to convert string to date
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date convertStringToDate(String dateString) {

		DateFormat formatter;
		Date date;
		formatter = new SimpleDateFormat("dd/MMM/yyyy");
		try {
			date = (Date) formatter.parse(dateString);
		} catch (ParseException e) {
			BusinessException businessExcp = new BusinessException("mail.error", null, e.getMessage());
			throw businessExcp;

		}

		return date;
	}

	/**
	 * Method to get the formatted date
	 * 
	 * @param date
	 * @return
	 */
	private static String getFormattedDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(com.Constant.CONST_DD_MMM_YYYY);

		return dateFormat.format(date);
	}

	/**
	 * Method to set the receipt parameters
	 * 
	 * @param commonVO
	 * @param reportParams
	 */
	private static void setTaxInvoiceParameters(CommonVO commonVO, HashMap<String, String> reportParams) {
		/*
		 * CommonOpSvc commonOpSvc = (CommonOpSvc)
		 * Utils.getBean(com.Constant.CONST_GECOMSVC); DataHolderVO<Long>
		 * policyIdHolder = (DataHolderVO<Long>) commonOpSvc
		 * .invokeMethod("getPolicyIdForPolicy", commonVO);
		 */
		/*
		 * Commented variable declaration commonOpSvc and policyIdHolder as it
		 * is not in use - sonar violation fix
		 */

		DebitNoteDetailsVO debitNoteDetails = new DebitNoteDetailsVO();
		debitNoteDetails.setDndPolicyNo(commonVO.getPolicyNo());
		debitNoteDetails.setDndEndtId(commonVO.getEndtId());
		debitNoteDetails.setDndPolicyId(commonVO.getPolicyId());
		debitNoteDetails.setDndPolicyYear(SvcUtils.getYearFromDate(commonVO.getPolEffectiveDate()));
		BaseVO resultVO = TaskExecutor.executeTasks("DEBIT_NOTE_DOC", debitNoteDetails);
		if (null == resultVO) {
			logger.debug("resultVO is null : Unable to produce TaxInvoice ");
		}
		if (!Utils.isEmpty(resultVO)) {
			debitNoteDetails = (DebitNoteDetailsVO) resultVO;
			if (!Utils.isEmpty(debitNoteDetails.getDndDebitNoteNo())) {
				reportParams.put("debitNoteNo", debitNoteDetails.getDndDebitNoteNo().toString());
				logger.debug("debitNoteNo:" + debitNoteDetails.getDndDebitNoteNo().toString());
			} else {
				logger.debug(" debitNoteDetails.getDndDebitNoteNo() is empty : Unable to produce TaxInvoice ");
			}
			if (!Utils.isEmpty(debitNoteDetails.getDndDebitNoteDate())) {

				String debitNoteDateString = debitNoteDetails.getDndDebitNoteDate();
				Date debitDate = convertStringToDate(debitNoteDateString);
				DateFormat format = new SimpleDateFormat(com.Constant.CONST_DD_MMM_YYYY);
				String debitNoteDate = format.format(debitDate);
				reportParams.put("debitNoteDate", debitNoteDate);
				logger.debug("debitNoteDate:" + debitNoteDate);
			}
		} else {
			logger.debug("resultVO is empty : Unable to produce TaxInvoice ");
		}

	}

	private static void setReceiptParameters(CommonVO commonVO, HashMap<String, String> reportParams) {
		/*
		 * CommonOpSvc commonOpSvc = (CommonOpSvc)
		 * Utils.getBean(com.Constant.CONST_GECOMSVC); DataHolderVO<Long>
		 * policyIdHolder = (DataHolderVO<Long>) commonOpSvc
		 * .invokeMethod("getPolicyIdForPolicy", commonVO);
		 */
		/*
		 * Commented variable declaration commonOpSvc and policyIdHolder as it
		 * is not in use - sonar violation fix
		 */

		ReceiptDetailsVO rcptDetsVO = new ReceiptDetailsVO();
		rcptDetsVO.setRcdPolicyNo(commonVO.getPolicyNo());
		rcptDetsVO.setRcdEndtId(commonVO.getEndtId());
		rcptDetsVO.setRcdPolicyId(commonVO.getPolicyId());

		BaseVO resultVO = TaskExecutor.executeTasks("RECEIPT_DOC", rcptDetsVO);
		if (null == resultVO) {
			logger.debug("resultVO is null : Unable to produce Receipt ");
		}
		if (!Utils.isEmpty(resultVO)) {
			rcptDetsVO = (ReceiptDetailsVO) resultVO;
			if (!Utils.isEmpty(rcptDetsVO.getRcdReceiptNo())) {
				reportParams.put("receiptNo", rcptDetsVO.getRcdReceiptNo().toString());
				logger.debug("receiptNo:" + rcptDetsVO.getRcdReceiptNo().toString());
			} else {
				logger.debug("rcptDetsVO.getRcdReceiptNo() is empty : Unable to produce Receipt ");
			}
			if (!Utils.isEmpty(rcptDetsVO.getRcdReceiptDate())) {

				String reciptDateString = rcptDetsVO.getRcdReceiptDate();
				Date reciptDate = convertStringToDate(reciptDateString);
				DateFormat format = new SimpleDateFormat(com.Constant.CONST_DD_MMM_YYYY);
				String receiptDate = format.format(reciptDate);
				reportParams.put("receiptDate", receiptDate);
				logger.debug("receiptDate:" + receiptDate);
			}
		} else {
			logger.debug("resultVO is empty : Unable to produce Receipt ");
		}

	}

	/**
	 * @param polDataVO
	 * @param response
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void downloadGeneratedDocument(PolicyDataVO polDataVO, HttpServletResponse response,
			String actionType) throws IOException, FileNotFoundException {

		OutputStream out = response.getOutputStream();
		// Sonar Fix to use Try with Resources
		// FileInputStream inputStream = null; //SONARFIX
		String filepath = Utils.getSingleValueAppConfig(com.Constant.CONST_B2C_DOWNLOAD_FILE_PATH);
		String filename = polDataVO.getCommonVO().getPolicyNo().toString() + "-"
				+ Utils.getSingleValueAppConfig("B2C_DOWNLOAD_FILE_NAME");

		if (!Utils.isEmpty(actionType) && actionType.equalsIgnoreCase("print")) {
			response.setHeader(com.Constant.CONST_CONTENT_DISPOSITION, "inline;filename=" + filename);
			response.setContentType("application/pdf");
		} else {
			response.setHeader(com.Constant.CONST_CONTENT_DISPOSITION,
					com.Constant.CONST_ATTACHMENT_FILENAME_END + filename);
			response.setContentType(com.Constant.CONST_APPLICATION_BINARY);
		}

		// Sonar Fix to use Try with Resources
		try (FileInputStream inputStream = new FileInputStream(filepath + filename)) {
			// inputStream = new FileInputStream(filepath + filename);

			int bufferSize = Integer
					.valueOf(Utils.getSingleValueAppConfig(com.Constant.CONST_B2C_DOWNLOAD_FILE_MAX_SIZE));
			byte[] buf = new byte[bufferSize];
			int bytesRead = 0;
			while ((bytesRead = inputStream.read(buf)) != -1) {
				out.write(buf, 0, bytesRead);
			}
			inputStream.close();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {

			// Sonar Fix to use Try with Resources
			/*
			 * try{
			 * 
			 * if(inputStream!=null){ inputStream.close(); } }
			 * 
			 * catch(Exception e){
			 * 
			 * e.getMessage(); }
			 */

			try {

				if (out != null) {
					out.close();
				}
			}

			catch (Exception e) {

				e.getMessage();
			}
		}
	}

	/**
	 * Method to download the policy wording
	 * 
	 * @param lob
	 * @param response
	 * @throws IOException
	 */
	public static void downloadStaticDocument(String lob, String filepath, String fileName,
			HttpServletResponse response) throws IOException {
		OutputStream out = response.getOutputStream();
		// Sonar Fix to use Try with Resources
		// FileInputStream inputStream = null; //SONARFIX

		response.setHeader(com.Constant.CONST_CONTENT_DISPOSITION,
				com.Constant.CONST_ATTACHMENT_FILENAME_END + fileName);
		response.setContentType(com.Constant.CONST_APPLICATION_BINARY);

		// Sonar Fix to use Try with Resources
		try (FileInputStream inputStream = new FileInputStream(filepath)) {
			// inputStream = new FileInputStream(filepath);

			int bufferSize = Integer
					.valueOf(Utils.getSingleValueAppConfig(com.Constant.CONST_B2C_DOWNLOAD_FILE_MAX_SIZE));
			byte[] buf = new byte[bufferSize];
			int bytesRead = 0;
			while ((bytesRead = inputStream.read(buf)) != -1) {
				out.write(buf, 0, bytesRead);
			}
			inputStream.close();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally { // SONARFIX

			// Sonar Fix to use Try with Resources
			/*
			 * try{
			 * 
			 * if(inputStream!=null){ inputStream.close(); } }
			 * 
			 * catch(Exception e){
			 * 
			 * e.getMessage(); }
			 */

			try {

				if (out != null) {
					out.close();
				}
			}

			catch (Exception e) {

				e.getMessage();
			}
		}

	}

	/**
	 * This is a common method to trigger an email on request a call back
	 * 
	 * @param phoneNumber
	 */
	private static void populateCallBackEmail(String phoneNumber) {
		logger.info("Entered CommonHandler.populateCallBackEmail method");
		String emailContent = AppUtils.getTempalteContentAsString(AppConstants.B2C_REQUEST_CALL_BACK_TEMPLATE);
		MailVO mailVO = (MailVO) Utils.getBean(com.Constant.CONST_MAILVO);
		mailVO.setToAddress(AppConstants.B2C_REQUEST_CALL_BACK_EMAILID);
		mailVO.setFromAddress(AppConstants.B2C_DEFAULT_FROM_EMAILID);
		// mailVO.setCcAddress(AppConstants.B2C_TRIGGER_EMAIL_CC_ID); //TODO
		// have to remove kept only for testing
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String formattedDate = dateFormat.format(Calendar.getInstance().getTime());
		emailContent = emailContent.replace(AppConstants.B2C_EMAIL_DATE_IDENTIFIER, formattedDate);
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
		String formattedTime = timeFormatter.format(Calendar.getInstance().getTime());
		emailContent = emailContent.replace(AppConstants.B2C_EMAIL_TIME_IDENTIFIER, formattedTime);
		emailContent = emailContent.replace(AppConstants.B2C_EMAIL_CALL_BACK_NUMBER_IDF, phoneNumber);
		mailVO.setMailContent(new StringBuilder(emailContent));
		mailVO.setSubjectText(AppConstants.B2C_CALL_BACK_REQ_EMAIL_SUB);
		mailVO.setSignature("RSA");
		mailVO.setMailType(SvcConstants.MAIL_TYPE_HTML);
		logger.info("CommonHandler.populateCallBackEmail method, before calling CommonHandler.sendGeneralEmail method");
		boolean mailStatus = sendGeneralEmail(mailVO, null);
		if (!mailStatus) {
			logger.debug("Error occurred while sending the email for call back request");
		}
	}

	public static String encodeToString(String fileName) throws IOException {
		String encodedPDFString = "";

		File file = new File(fileName);
		if (!file.exists()) {
			logger.info("[Method:encodePDFToString]file does not exist in the path specified:" + fileName);
			throw new IOException("file does not exist in the path specified: " + fileName);
			// return encodedPDFString;
		}
		try (FileInputStream fis = new FileInputStream(file)) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum); // no doubt here is 0
				// Writes len bytes from the specified byte array starting at
				// offset off to this byte array output stream.
				// System.out.println("read " + readNum + " bytes,");
			}
			byte[] encodedBytes = Base64.encode(bos.toByteArray());
			encodedPDFString = new String(encodedBytes);
			System.out.println("******Response*****");
			System.out.println("byte array size " + encodedBytes.length);
			System.out.println(
					"***ENCODING\n**************encodedPDFString*length*************" + encodedPDFString.length());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return encodedPDFString;
	}

	public static void printProposalForm(PolicyDataVO policyDataVO) throws IOException {

		CommonVO commonVO = policyDataVO.getCommonVO();

		if (!Utils.isEmpty(commonVO)) {
			LOB lob = commonVO.getLob();
			HashMap<String, String> reportParams = new HashMap<String, String>();
			String[] fileNames = new String[1];
			MailVO mailVO = new MailVO();
			reportParams = populateQuoteReportParams(policyDataVO);
			switch (lob) {
			case TRAVEL:

				reportParams.put(com.Constant.CONST_REPORTTEMPLATESTYPE, ReportTemplateSet._TRAVEL.toString());
				break;
			case HOME:

				reportParams.put(com.Constant.CONST_REPORTTEMPLATESTYPE, ReportTemplateSet._HOME.toString());
				break;
			// sonar fix
			default:
				break;

			}

			/*
			 * if( !Utils.isEmpty( policyDataVO.getCommonVO().getIsQuote() ) &&
			 * ( policyDataVO.getCommonVO().getIsQuote() ) && Utils.isEmpty(
			 * policyDataVO.getCommonVO().getPolicyNo() ) ){
			 */

			// if (AppUtils.isQuote(policyDataVO.getCommonVO())) {

			fileNames[0] = Utils.getSingleValueAppConfig(com.Constant.CONST_QUOTE_DOC_PROPOSAL_LOC)
					+ policyDataVO.getCommonVO().getQuoteNo() + com.Constant.CONST_QUOTE_PDF;
			// }

			mailVO.setFileNames(fileNames);
			mailVO.setDocParameter(reportParams);

			if (!Utils.isEmpty(fileNames)
					&& !Utils.isEmpty(fileNames[0]) /*
													 * && !new File( fileNames [
													 * 0 ] ) .isFile ()
													 */) {
				PASDocumentService docCreator = (PASDocumentService) Utils.getBean(com.Constant.CONST_DOCSERVICEBEAN);
				mailVO = (MailVO) docCreator.invokeMethod(com.Constant.CONST_CREATEDOCUMENT, mailVO);
			} else {
				mailVO.setDocCreationStatus(com.Constant.CONST_SUCCESS2);
			}

		}

	}

	public static void printPolicyDocument(PolicyDataVO policyDataVO, Documents documents) {

		File policyScheduleFile = new File(Utils.getSingleValueAppConfig(com.Constant.CONST_POL_DOC_POL_SCHED_LOC)
				+ policyDataVO.getCommonVO().getPolicyNo() + com.Constant.CONST_POLICYSCHEDULE_PDF);
		File receiptFile = new File(Utils.getSingleValueAppConfig(com.Constant.CONST_POL_DOC_POL_RECEIPTS_LOC)
				+ policyDataVO.getCommonVO().getPolicyNo() + com.Constant.CONST_RECEIPT_PDF);
		File debitNoteFile = new File(Utils.getSingleValueAppConfig(com.Constant.CONST_POL_DOC_DEB_NOTE_LOC)
				+ policyDataVO.getCommonVO().getPolicyNo() + com.Constant.CONST_TAXINVOICE_PDF);
		
		//CTS - 21/10/2020 - TFS#49060 -HOME DIGITAL DEFECT FIX - Generate Documents if not generated during create policy - Starts
		HashMap<String, String> reportParams = new HashMap<String, String>();
		MailVO mailVO = new MailVO();
		PASDocumentService docCreator = (PASDocumentService) Utils.getBean("docServiceBean");
		if(Utils.isEmpty(policyDataVO.getCommonVO().getVsd())){
			policyDataVO.setCommonVO(getVsdForPolicy(policyDataVO.getCommonVO()));
		}
			String[] fileName = new String[1];
			ReportTemplateSet reportTemplateSet = ReportTemplateSet._HOME;
			
			if(!policyScheduleFile.isFile()){
				reportParams = addDefaultReportParameters(policyDataVO.getCommonVO());
				reportParams.put(com.Constant.CONST_POLICYSCHEDULE, "true");
				reportParams.put(com.Constant.CONST_DEBITNOTEREPORT, com.Constant.CONST_FALSE);
				reportParams.put(com.Constant.CONST_GROSSCREDITNOTEREPORT,com.Constant.CONST_FALSE);
				reportParams.put(com.Constant.CONST_ENDSCHEDULEREPORT,com.Constant.CONST_FALSE); 
				reportParams.put(com.Constant.CONST_GROSSDEBITNOTEREPORT, com.Constant.CONST_FALSE);
				reportParams.put(com.Constant.CONST_CREDITNOTEREPORT, com.Constant.CONST_FALSE);
				reportParams.put(com.Constant.CONST_POLICYSCHEDULECLAUSES, com.Constant.CONST_FALSE);
				fileName[0] = Utils.getSingleValueAppConfig(com.Constant.CONST_POL_DOC_POL_SCHED_LOC)
				+ policyDataVO.getCommonVO().getPolicyNo() + com.Constant.CONST_POLICYSCHEDULE_PDF;
				
				mailVO.setFileNames(fileName);
				mailVO.setDocParameter(reportParams);
				
				mailVO = (MailVO) docCreator.invokeMethod("createDocument", mailVO);

				
			}
			if(!receiptFile.isFile()){
				reportParams = addDefaultReportParameters(policyDataVO.getCommonVO());
				reportParams.put(com.Constant.CONST_RECEIPT, "true");
				reportParams.put(com.Constant.CONST_DEBITNOTEREPORT, com.Constant.CONST_FALSE);
				reportParams.put(com.Constant.CONST_GROSSCREDITNOTEREPORT,com.Constant.CONST_FALSE);
				reportParams.put(com.Constant.CONST_ENDSCHEDULEREPORT,com.Constant.CONST_FALSE); 
				reportParams.put(com.Constant.CONST_GROSSDEBITNOTEREPORT, com.Constant.CONST_FALSE);
				reportParams.put(com.Constant.CONST_CREDITNOTEREPORT, com.Constant.CONST_FALSE);
				reportParams.put(com.Constant.CONST_POLICYSCHEDULE, com.Constant.CONST_FALSE);
				reportParams.put(com.Constant.CONST_POLICYSCHEDULECLAUSES, com.Constant.CONST_FALSE);				
				setReceiptParameters(policyDataVO.getCommonVO(), reportParams);
				fileName[0] = Utils.getSingleValueAppConfig(com.Constant.CONST_POL_DOC_POL_RECEIPTS_LOC)
						+ policyDataVO.getCommonVO().getPolicyNo() + com.Constant.CONST_RECEIPT_PDF;

				mailVO.setFileNames(fileName);
				mailVO.setDocParameter(reportParams);
				mailVO = (MailVO) docCreator.invokeMethod("createDocument", mailVO);

			}
			if(!debitNoteFile.isFile()){
				reportParams = addDefaultReportParameters(policyDataVO.getCommonVO());
				reportParams.put(com.Constant.CONST_DEBITNOTEREPORT, "true");
				reportParams.put(com.Constant.CONST_RECEIPT, com.Constant.CONST_FALSE);
				reportParams.put(com.Constant.CONST_GROSSCREDITNOTEREPORT,com.Constant.CONST_FALSE);
				reportParams.put(com.Constant.CONST_ENDSCHEDULEREPORT,com.Constant.CONST_FALSE); 
				reportParams.put(com.Constant.CONST_GROSSDEBITNOTEREPORT, com.Constant.CONST_FALSE);
				reportParams.put(com.Constant.CONST_CREDITNOTEREPORT, com.Constant.CONST_FALSE);
				reportParams.put(com.Constant.CONST_POLICYSCHEDULE, com.Constant.CONST_FALSE);
				reportParams.put(com.Constant.CONST_POLICYSCHEDULECLAUSES, com.Constant.CONST_FALSE);	
				setTaxInvoiceParameters(policyDataVO.getCommonVO(), reportParams);
				fileName[0] = Utils.getSingleValueAppConfig(com.Constant.CONST_POL_DOC_DEB_NOTE_LOC)
						+ policyDataVO.getCommonVO().getPolicyNo() + com.Constant.CONST_TAXINVOICE_PDF;
				
				mailVO.setFileNames(fileName);
				mailVO.setDocParameter(reportParams);
				mailVO = (MailVO) docCreator.invokeMethod("createDocument", mailVO);
			}
			//CTS - 21/10/2020 - TFS#49060 - HOME DIGITAL DEFECT FIX - Generate Documents if not generated during create quote - Starts

		PDDocument policySchedule = null;
		PDDocument receipt = null;
		PDDocument debitNote = null;
		PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
		pdfMergerUtility.setDestinationFileName(Utils.getSingleValueAppConfig(com.Constant.CONST_POL_DOC_POL_SCHED_LOC)
				+ policyDataVO.getCommonVO().getPolicyNo() + "-PolicySchedule_WebService.pdf");
		try {
			if (!Utils.isEmpty(documents.getDocsDetails())) {

				if (documents.getDocsDetails().getPolicySchedule()) {
					policySchedule = PDDocument.load(policyScheduleFile);
					pdfMergerUtility.addSource(policyScheduleFile);
					policySchedule.close();
				}
				if (documents.getDocsDetails().getDebitNote()) {
					receipt = PDDocument.load(receiptFile);
					pdfMergerUtility.addSource(receiptFile);
					receipt.close();
				}
				if (documents.getDocsDetails().getReceipt()) {
					debitNote = PDDocument.load(debitNoteFile);
					pdfMergerUtility.addSource(debitNoteFile);
					debitNote.close();
				}
			}
			pdfMergerUtility.mergeDocuments();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (COSVisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
		logger.info("PDF merged successfully....");

	}
	
	public static HashMap<String,String> addDefaultReportParameters (CommonVO commonVO){
		
		HashMap<String, String> reportParams = new HashMap<String, String>();
		reportParams.put(com.Constant.CONST_POLICYID, String.valueOf(commonVO.getPolicyId()));
		reportParams.put(com.Constant.CONST_ENDORSEMENTID,
				String.valueOf(commonVO.getEndtId()));
		reportParams.put(com.Constant.CONST_VALIDITYSTARTDATE,
				getFormattedDateAsString(commonVO.getVsd()));
		reportParams.put(com.Constant.CONST_LANGUAGE, "E");
		reportParams.put(com.Constant.CONST_LOCATIONCODE,
				Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));
		reportParams.put(com.Constant.CONST_REPORTTEMPLATESTYPE, "_HOME");

		
		return reportParams;
		
	}

	/**
	 * Method to create document for printing proposal form
	 * 
	 * @param polDataVO
	 * @throws IOException
	 */
	public static void printProposalForm(PolicyDataVO policyDataVO, HttpServletResponse response, String actionType)
			throws IOException {

		CommonVO commonVO = policyDataVO.getCommonVO();

		if (!Utils.isEmpty(commonVO)) {
			LOB lob = commonVO.getLob();
			HashMap<String, String> reportParams = new HashMap<String, String>();
			String[] fileNames = new String[1];
			MailVO mailVO = new MailVO();
			reportParams = populateQuoteReportParams(policyDataVO);
			switch (lob) {
			case TRAVEL:

				reportParams.put(com.Constant.CONST_REPORTTEMPLATESTYPE, ReportTemplateSet._TRAVEL.toString());
				break;
			case HOME:

				reportParams.put(com.Constant.CONST_REPORTTEMPLATESTYPE, ReportTemplateSet._HOME.toString());
				break;
			// sonar fix
			default:
				break;

			}

			/*
			 * if( !Utils.isEmpty( policyDataVO.getCommonVO().getIsQuote() ) &&
			 * ( policyDataVO.getCommonVO().getIsQuote() ) && Utils.isEmpty(
			 * policyDataVO.getCommonVO().getPolicyNo() ) ){
			 */

			if (AppUtils.isQuote(policyDataVO.getCommonVO())) {

				fileNames[0] = Utils.getSingleValueAppConfig(com.Constant.CONST_QUOTE_DOC_PROPOSAL_LOC)
						+ policyDataVO.getCommonVO().getQuoteNo() + com.Constant.CONST_QUOTE_PDF;
			}

			mailVO.setFileNames(fileNames);
			mailVO.setDocParameter(reportParams);

			if (!Utils.isEmpty(fileNames)
					&& !Utils.isEmpty(fileNames[0]) /*
													 * && !new File( fileNames [
													 * 0 ] ) .isFile ()
													 */) {
				PASDocumentService docCreator = (PASDocumentService) Utils.getBean(com.Constant.CONST_DOCSERVICEBEAN);
				mailVO = (MailVO) docCreator.invokeMethod(com.Constant.CONST_CREATEDOCUMENT, mailVO);
			} else {
				mailVO.setDocCreationStatus(com.Constant.CONST_SUCCESS2);
			}

			if (!Utils.isEmpty(mailVO) && !Utils.isEmpty(mailVO.getDocCreationStatus())
					&& mailVO.getDocCreationStatus().equalsIgnoreCase(com.Constant.CONST_SUCCESS2)) {
				OutputStream out = response.getOutputStream();
				// Sonar Fix to use Try with Resources
				// FileInputStream inputStream = null;
				String fileName = policyDataVO.getCommonVO().getQuoteNo() + com.Constant.CONST_QUOTE_PDF;

				if (!Utils.isEmpty(actionType) && actionType.equalsIgnoreCase("print")) {
					response.setHeader(com.Constant.CONST_CONTENT_DISPOSITION, "inline;filename=" + fileName);
					response.setContentType("application/pdf");
				} else {
					response.setHeader(com.Constant.CONST_CONTENT_DISPOSITION,
							com.Constant.CONST_ATTACHMENT_FILENAME_END + fileName);
					response.setContentType(com.Constant.CONST_APPLICATION_BINARY);
				}

				// Sonar Fix to use Try with Resources
				try (FileInputStream inputStream = new FileInputStream(fileNames[0])) {
					// inputStream = new FileInputStream(fileNames[0]);

					int bufferSize = Integer
							.valueOf(Utils.getSingleValueAppConfig(com.Constant.CONST_B2C_DOWNLOAD_FILE_MAX_SIZE));
					byte[] buf = new byte[bufferSize];
					int bytesRead = 0;
					while ((bytesRead = inputStream.read(buf)) != -1) {
						out.write(buf, 0, bytesRead);
					}
					inputStream.close();
					out.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally { // SONARFIX

					// Sonar Fix to use Try with Resources
					/*
					 * try{
					 * 
					 * if(inputStream!=null){ inputStream.close(); } }
					 * 
					 * catch(Exception e){
					 * 
					 * e.getMessage(); }
					 */

					try {

						if (out != null) {
							out.close();
						}
					}

					catch (Exception e) {

						e.getMessage();
					}
				}
			}
		}

	}

	/**
	 * @param bindingResult
	 */
	public static void renderErrorMessages(BindingResult bindingResult, String errorMessage) {
		Errors errors = bindingResult;
		errors.rejectValue("errorMessage", "errorMessage.invalid", errorMessage);
	}

	/**
	 * This is a common method for all the email except for Scheduler email in
	 * B2C, flow must be defined in B2CEmailTriggers
	 * 
	 * @param policyDataVO
	 * @param path
	 * @param templateName
	 * @return
	 * @throws ParseException
	 */
	public static MailVO populateAndTriggerEmail(PolicyDataVO policyDataVO, String path, B2CEmailTriggers flow,
			String callBackNumber) {

		logger.info("calling populateAndTriggerEmail HomeInsuranceSvcHandler class started");
		MailVO mailVO = null;
		String clickHereLink = null;
		String emailContent = null;
		List resultList = new ArrayList();
		boolean flag = false;

		if (!Utils.isEmpty(policyDataVO) && !Utils.isEmpty(flow)) {

			// Setting the common elements in MailVO
			mailVO = (MailVO) Utils.getBean(com.Constant.CONST_MAILVO);
			mailVO.setSignature("RSA");
			mailVO.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
			mailVO.setMailType(SvcConstants.MAIL_TYPE_HTML);
			if (Utils.isEmpty(policyDataVO.getGeneralInfo())
					|| Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus())
					|| Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerId())) {
				String CCAdress[] = AppConstants.B2C_DEFAULT_CC_EMAILID.split(",");
				mailVO.setFromAddress(AppConstants.B2C_DEFAULT_FROM_EMAILID);
				mailVO.setCcAddress(CCAdress);
			} else {
				mailVO.setFromAddress(policyDataVO.getGeneralInfo().getSourceOfBus().getFromEmailID());
				if (!Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getCcEmailId())) {
					String[] ccString = policyDataVO.getGeneralInfo().getSourceOfBus().getCcEmailId().split(";");
					mailVO.setCcAddress(ccString);
				}
				mailVO.setReplyToEmailID(policyDataVO.getGeneralInfo().getSourceOfBus().getReplyToEmailId());
				logger.debug("To Email id: " + policyDataVO.getGeneralInfo().getSourceOfBus().getReplyToEmailId());
			}

			switch (flow) {
			case TRAVEL_CREATE_QUOTE:
				logger.info("case: TRAVEL_CREATE_QUOTE");
				emailContent = AppUtils.getTempalteContentAsString(AppConstants.B2C_STEP1_ONLY_TRAVEL_TEMPLATE);
				if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
					emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
							Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_RSA_CALLCENTER_NUMBER));
					emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE, " ");
					mailVO.setDirect(true);
				} else {
					emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
							policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
					if (policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()
							.equals(SvcConstants.DIST_CHANNEL_BROKER)) {
						int brokerCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
						List<Object[]> resultSet = DAOUtils.getSqlResultForPas(QueryConstants.BROKER_ADDRESS,
								brokerCode);
						if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
							StringBuffer brokerAddress = new StringBuffer();
							brokerAddress.append(com.Constant.CONST_BR_YOURS_SINCERELY_BR_BR_END);
							brokerAddress.append(com.Constant.CONST_THE_CUSTOMER_SERVICE_TEAM_BR_BR_END);
							for (Object object : resultSet.get(0)) {
								if (!Utils.isEmpty(object)) {
									brokerAddress.append(String.valueOf(object)).append(com.Constant.CONST_BR_END);
								}
							}
							emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE,
									brokerAddress.toString());
						}
						mailVO.setDirect(false);
					} else {
						emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE, " ");
						mailVO.setDirect(true);
					}

				}
				if (!Utils.isEmpty(emailContent)) {
					if (!Utils.isEmpty(policyDataVO.getGeneralInfo().getInsured().getFirstName())
							&& !Utils.isEmpty(policyDataVO.getGeneralInfo().getInsured().getLastName())) {
						emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
								WordUtils.capitalizeFully(policyDataVO.getGeneralInfo().getInsured().getFirstName()
										+ " " + policyDataVO.getGeneralInfo().getInsured().getLastName()));
					} else {
						emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
								WordUtils.capitalizeFully(policyDataVO.getGeneralInfo().getInsured().getName()));
					}
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_QUOTE_NO_IDENTIFIER,
							String.valueOf(policyDataVO.getCommonVO().getQuoteNo()));
					String clickHereTag = AppUtils.constructClickHereURL(policyDataVO.getCommonVO().getQuoteNo(),
							policyDataVO.getGeneralInfo().getInsured().getEmailId(),
							Utils.getSingleValueAppConfig("B2C_SCHEDULER_URL_TRAVEL"),
							policyDataVO.getCommonVO().getLob(), null);
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_CLICK_HERE_TAG_IDF, clickHereTag);
					mailVO.setToAddress(policyDataVO.getGeneralInfo().getInsured().getEmailId());
					mailVO.setSubjectText(AppConstants.B2C_TRAVEL_QUOTE_EMAIL_SUBJECT + "- #"
							+ policyDataVO.getCommonVO().getQuoteNo());
					mailVO.setMailContent(new StringBuilder(emailContent));
					flag = sendGeneralEmail(mailVO, com.Constant.CONST_QUOTE);
					if (!flag) {
						logger.debug("Email trigger failed after TRAVEL create quote for policy id - "
								+ String.valueOf(policyDataVO.getCommonVO().getPolicyId()));
					}
				} else {
					logger.debug("Error occured while reading email template -_1"
							+ AppConstants.B2C_STEP1_ONLY_TRAVEL_TEMPLATE);
				}
				break;

			case TRAVEL_SAVE_FOR_LATER:
				logger.info("case: TRAVEL_SAVE_FOR_LATER");
				// emailContent =
				// AppUtils.getTempalteContentAsString(AppConstants.B2C_TRAVEL_SAVE_FOR_LATER_TEMPLATE);
				/* Ticket 165419 */
				if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
					resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_TRAVEL_EMAIL_TEMPLATE_ONLINE);
					logger.debug("TRAVEL_SAVE_FOR_LATER SFL emailContent : " + emailContent);
				} else {
					resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_HOME_TRAVEL_EMAIL_TEMPLATE_DIRECT,
							policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName());
					emailContent = returnQueryResultList(emailContent, resultList, policyDataVO, flow, flag);
					logger.debug("TRAVEL_SAVE_FOR_LATER SFL Direct--Mirror Site emailContent : " + emailContent);
				}

				if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
					emailContent = returnQueryResultList(emailContent, resultList, policyDataVO, flow, flag);
					// Oman D2C Email template change - Start
					if (!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION))
							&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")) {
						logger.info(" TRAVEL SAVE FOR LATER for Oman D2C");
						String pmmId = AppConstants.OMAN_D2C_TRAVEL;

						List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.SFL_OMAN_D2C, pmmId);
						if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
							emailContent = resultSet.get(0);
							logger.debug("emailContent SFL Oman D2C Travel " + emailContent);
						}
						emailContent = emailContent.replace(com.Constant.CONST_TRAVELPAGE,
								Utils.getSingleValueAppConfig(com.Constant.CONST_D2C_OMAN_TRAVEL_STEP1));
					}
					// Oman D2C Email template change - End
					emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
							Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_RSA_CALLCENTER_NUMBER));
					emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE, " ");
					emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC,
							Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POINT_DISC_DIRECT));
					emailContent = emailContent.replace(com.Constant.CONST_DISC_PERCENT,
							Utils.getSingleValueAppConfig("BULLET_POINT_DISC_PERCENT_DIRECT"));
					mailVO.setDirect(true);
				} else {
					int brokerCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
					if (brokerCode == AppConstants.B2C_FGB_BROKER_CODE) {
						List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.FGB_BROKER_SFL,
								policyDataVO.getScheme().getSchemeCode());
						if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
							emailContent = resultSet.get(0);
							logger.debug("TRAVEL_SAVE_FOR_LATER : SFL emailContent for scheme "
									+ policyDataVO.getScheme().getSchemeCode() + "   " + emailContent);
						}
						Integer packCode = policyDataVO.getScheme().getTariffCode();
						List<String> resultSet1 = DAOUtils.getSqlResultForPasString(QueryConstants.PACKAGE_CODE,
								packCode);
						String packName = null;
						if (!Utils.isEmpty(resultSet1) && resultSet1.size() > 0) {
							packName = resultSet1.get(0);
							logger.debug("emailContent package details of Travel Partner_1" + packName);
						}
						if (policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()
								.equals(SvcConstants.DIST_CHANNEL_BROKER)) {
							emailContent = emailContent.replace("TRAVEL_PRODUCT", packName);
							emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
									policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1,
									Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_1_BROKER));
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2,
									Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_2_BROKER));
							// emailContent =
							// emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE,
							// Utils.getSingleValueAppConfig("EMAIL_SIGNATURE_"+brokerCode));
							mailVO.setDirect(false);
						}
					} else if (brokerCode == AppConstants.B2C_NEX_BROKER_CODE) {
						logger.info("Travel Save for later Nexus");
						// emailContent =
						// AppUtils.getTempalteContentAsString(AppConstants.B2C_TRAVEL_SFL_NEXUS);
						/* Ticket 165419 */
						resultList = DAOUtils.getSqlResultForPasString(QueryConstants.TRAVEL_NEX_BROKER,
								policyDataVO.getScheme().getSchemeCode());
						emailContent = returnQueryResultList(emailContent, resultList, policyDataVO, flow, flag);
						logger.debug("TRAVEL_SAVE_FOR_LATER :  SFL emailContent for the scheme "
								+ policyDataVO.getScheme().getSchemeCode() + "   " + emailContent);
						emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
								policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
						logger.debug("emailContent SFL Nexus Travel " + emailContent);
						mailVO.setDirect(false);
					} else {
						if (policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()
								.equals(SvcConstants.DIST_CHANNEL_BROKER)) {
							logger.info("Travel Save for later Lifecare/Expat");
							List<Object[]> resultSet = DAOUtils.getSqlResultForPas(QueryConstants.BROKER_ADDRESS,
									brokerCode);
							if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
								StringBuffer brokerAddress = new StringBuffer();
								brokerAddress.append(com.Constant.CONST_BR_YOURS_SINCERELY_BR_BR_END);
								brokerAddress.append(com.Constant.CONST_THE_CUSTOMER_SERVICE_TEAM_BR_BR_END);
								for (Object object : resultSet.get(0)) {
									if (!Utils.isEmpty(object)) {
										brokerAddress.append(String.valueOf(object)).append(com.Constant.CONST_BR_END);
									}
								}
								emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE,
										brokerAddress.toString());
							}
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1,
									Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_1_BROKER));
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2,
									Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_2_BROKER));
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC, "");
							emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
									policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
							logger.debug("emailContent SFL Lifecare/Expat Travel " + emailContent);
							mailVO.setDirect(false);
						} else {
							emailContent = returnQueryResultList(emailContent, resultList, policyDataVO, flow, flag);
							if (!Utils
									.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount())
									&& policyDataVO.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount()
											.intValue() != 0) {
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC,
										Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POINT_DISC_DIRECT));
								emailContent = emailContent.replace(com.Constant.CONST_DISC_PERCENT,
										Integer.valueOf(Math.abs(policyDataVO.getGeneralInfo().getSourceOfBus()
												.getDefaultOnlineDiscount().intValue())).toString());
							} else {
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC, "");
							}
							logger.info("Travel Save for later RSA Campaign");
							emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
									Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_RSA_CALLCENTER_NUMBER));
							emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE, " ");
							mailVO.setDirect(true);
						}
					}
				}
				if (!Utils.isEmpty(emailContent)) {
					clickHereLink = AppUtils.constructClickHereURL(policyDataVO.getCommonVO().getQuoteNo(),
							policyDataVO.getGeneralInfo().getInsured().getEmailId(), path,
							policyDataVO.getCommonVO().getLob(), policyDataVO.getCommonVO().getDocCode());
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_CLICK_HERE_TAG_IDF, clickHereLink);
					if (!Utils.isEmpty(policyDataVO.getGeneralInfo().getInsured().getFirstName())
							&& !Utils.isEmpty(policyDataVO.getGeneralInfo().getInsured().getLastName())) {
						emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
								WordUtils.capitalizeFully(policyDataVO.getGeneralInfo().getInsured().getFirstName()
										+ " " + policyDataVO.getGeneralInfo().getInsured().getLastName()));
					} else {
						emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
								WordUtils.capitalizeFully(policyDataVO.getGeneralInfo().getInsured().getName()));
					}
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_PREMIUM_IDENTIFIER,
							((AppUtils.getFormattedNumberWithDecimals(policyDataVO.getPremiumVO().getPremiumAmt()))
									+ AppConstants.B2C_EMAIL_VAT_INCL_TEXT));
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_QUOTE_NO_IDENTIFIER,
							String.valueOf(policyDataVO.getCommonVO().getQuoteNo()));
					mailVO.setToAddress(policyDataVO.getGeneralInfo().getInsured().getEmailId());
					// Oman D2C Email template change - Start
					if (!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION))
							&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")) {
						mailVO.setSubjectText(AppConstants.D2C_OMAN_TRAVEL_QUOTE_EMAIL_SUBJECT + " "
								+ policyDataVO.getCommonVO().getQuoteNo());
					} else {
						mailVO.setSubjectText(AppConstants.B2C_TRAVEL_QUOTE_EMAIL_SUBJECT + "- #"
								+ policyDataVO.getCommonVO().getQuoteNo());
					}
					// Oman D2C Email template change - End
					mailVO.setMailContent(new StringBuilder(emailContent));
					flag = sendEmail(mailVO, policyDataVO);
					if (!flag) {
						logger.debug("Email trigger failed after TRAVEL save for later for policy id - "
								+ String.valueOf(policyDataVO.getCommonVO().getPolicyId()));
					}
				} else {
					logger.debug("Error occured while reading email template -_2"
							+ AppConstants.B2C_TRAVEL_SAVE_FOR_LATER_TEMPLATE);
				}
				break;

			case REFERRAL: // Common for HOME and TRAVEL
				logger.info("case: REFERRAL");
				flag = emailForReferral(policyDataVO);
				if (!flag) {
					logger.debug("Email trigger failed for referral for quotation number - "
							+ String.valueOf(policyDataVO.getCommonVO().getQuoteNo()));
				}
				break;

			case PAYMENT_FAILURE: // Common for HOME and TRAVEL
				logger.info("case: PAYMENT_FAILURE");
				emailContent = AppUtils.getTempalteContentAsString(AppConstants.B2C_PAYMENT_FAILURE_TEMPLATE);
				if (!Utils.isEmpty(emailContent)) {
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_PREMIUM_IDENTIFIER,
							Currency.getFormattedCurrency(policyDataVO.getPremiumVO().getPremiumAmtActual(),
									policyDataVO.getCommonVO().getLob().toString()));
					mailVO.setSubjectText("Payment failure notice - AED "
							+ Currency.getFormattedCurrency(policyDataVO.getPremiumVO().getPremiumAmtActual(),
									policyDataVO.getCommonVO().getLob().toString())
							+ " - " + String.valueOf(policyDataVO.getCommonVO().getQuoteNo()));
					mailVO.setToAddress(AppConstants.B2C_PAYMENT_FAILURE_TOEMAILID);
					mailVO.setMailContent(new StringBuilder(emailContent));
					/*
					 * Use the same key (B2C_TRIGGER_EMAIL_CC_ID) to configure
					 * in appconfig.properties if required
					 */
					if (!Utils.isEmpty(Utils.getMultiValueAppConfig(com.Constant.CONST_B2C_TRIGGER_EMAIL_CC_ID, ","))) {
						mailVO.setCcAddress(
								Utils.getMultiValueAppConfig(com.Constant.CONST_B2C_TRIGGER_EMAIL_CC_ID, ","));
					}
					flag = sendGeneralEmail(mailVO, null);
					if (!flag) {
						logger.debug("Email trigger failed for payment failure for quotation number - "
								+ String.valueOf(policyDataVO.getCommonVO().getQuoteNo()));
					}
				} else {
					logger.debug("Error occured while reading email template -_3"
							+ AppConstants.B2C_PAYMENT_FAILURE_TEMPLATE);
				}
				break;

			case TRAVEL_CONVERT_TO_POLICY:
				logger.info("case: TRAVEL_CONVERT_TO_POLICY");
				// emailContent =
				// AppUtils.getTempalteContentAsString(AppConstants.B2C_TRAVEL_SUCCESS_POLICY_PURCHASE_TEMPLATE);
				/* Ticket 165419 */
				if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
					resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_TRAVEL_EMAIL_TEMPLATE_ONLINE);
				} else {
					resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_HOME_TRAVEL_EMAIL_TEMPLATE_DIRECT,
							policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName());
				}

				if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
					logger.debug("TRAVEL_CONVERT_TO_POLICY Direct emailContent : " + "   " + emailContent);
					emailContent = returnQueryResultList(emailContent, resultList, policyDataVO, flow, flag);
					// Oman D2C Email template change - Start
					if (!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION))
							&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")) {
						logger.info(" TRAVEL CONVERT TO POLICY for Oman D2C");
						String pmmId = AppConstants.OMAN_D2C_TRAVEL;

						List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.POLICY_OMAN_D2C,
								pmmId);
						if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
							emailContent = resultSet.get(0);
							logger.debug("emailContent CONVERT TO POLICY Oman D2C Travel " + emailContent);
						}
						emailContent = emailContent.replace(com.Constant.CONST_TRAVELPAGE,
								Utils.getSingleValueAppConfig(com.Constant.CONST_D2C_OMAN_TRAVEL_STEP1));
					}
					// Oman D2C Email template change - End
					logger.info(" Travel Convert to policy Direct");
					emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
							Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_RSA_CALLCENTER_NUMBER));
					emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE, " ");
					mailVO.setDirect(true);
				} else {
					int brokerCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
					if (brokerCode == AppConstants.B2C_FGB_BROKER_CODE) {

						List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.FGB_BROKER_CTP,
								policyDataVO.getScheme().getSchemeCode());
						if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
							emailContent = resultSet.get(0);
							logger.debug("TRAVEL_CONVERT_TO_POLICY : CTP Travel emailContent for the scheme  "
									+ policyDataVO.getScheme().getSchemeCode() + "  " + emailContent);
						}
						Integer packCode = policyDataVO.getScheme().getTariffCode();
						List<String> resultSet1 = DAOUtils.getSqlResultForPasString(QueryConstants.PACKAGE_CODE,
								packCode);
						String packName = null;
						if (!Utils.isEmpty(resultSet1) && resultSet1.size() > 0) {
							packName = resultSet1.get(0);
							logger.debug("emailContent package details of Travel Partner_2" + packName);
						}
						if (policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()
								.equals(SvcConstants.DIST_CHANNEL_BROKER)) {
							emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
									policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1,
									Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_1_BROKER));
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2,
									Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_2_BROKER));
							// emailContent =
							// emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE,
							// Utils.getSingleValueAppConfig("EMAIL_SIGNATURE_"+brokerCode));
							mailVO.setDirect(false);
						}
					} else if (brokerCode == AppConstants.B2C_NEX_BROKER_CODE) {
						logger.info("Travel Convert to Policy Nexus");
						// emailContent =
						// AppUtils.getTempalteContentAsString(AppConstants.B2C_TRAVEL_C2P_NEXUS);
						/* Ticket 165419 */
						resultList = DAOUtils.getSqlResultForPasString(QueryConstants.TRAVEL_NEX_BROKER,
								policyDataVO.getScheme().getSchemeCode());
						emailContent = returnQueryResultList(emailContent, resultList, policyDataVO, flow, flag);
						logger.debug("TRAVEL_Convert_To_POlicy :  C2P emailContent for the scheme "
								+ policyDataVO.getScheme().getSchemeCode() + "   " + emailContent);
						emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
								policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
						logger.debug("emailContent CTP Nexus Travel " + emailContent);
						mailVO.setDirect(false);
					} else {
						emailContent = returnQueryResultList(emailContent, resultList, policyDataVO, flow, flag);
						if (policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()
								.equals(SvcConstants.DIST_CHANNEL_BROKER)) {
							logger.info(" Travel Convert to policy Lifecare/Expat");
							List<Object[]> resultSet = DAOUtils.getSqlResultForPas(QueryConstants.BROKER_ADDRESS,
									brokerCode);
							if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
								StringBuffer brokerAddress = new StringBuffer();
								brokerAddress.append(com.Constant.CONST_BR_YOURS_SINCERELY_BR_BR_END);
								brokerAddress.append(com.Constant.CONST_THE_CUSTOMER_SERVICE_TEAM_BR_BR_END);
								for (Object object : resultSet.get(0)) {
									if (!Utils.isEmpty(object)) {
										brokerAddress.append(String.valueOf(object)).append(com.Constant.CONST_BR_END);
									}
								}
								emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
										policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1,
										Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_1_BROKER));
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2,
										Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_2_BROKER));
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC, "");
								emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE,
										brokerAddress.toString());
							}
							mailVO.setDirect(false);
						} else {
							emailContent = returnQueryResultList(emailContent, resultList, policyDataVO, flow, flag);
							logger.debug("TRAVEL_CONVERT_TO_POLICY Direct--Mirror Site emailContent : " + "   "
									+ emailContent);
							logger.info("Travel Convert to policy RSA Campaign");
							emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE, " ");
							emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
									Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_RSA_CALLCENTER_NUMBER));
							mailVO.setDirect(true);
						}
					}
				}
				if (!Utils.isEmpty(emailContent)) {
					// Oman D2C Email template change - Start
					if (!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION))
							&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")) {
						mailVO.setSubjectText(AppConstants.D2C_OMAN_TRAVEL_POLICY_EMAIL_SUBJECT + " "
								+ policyDataVO.getCommonVO().getPolicyNo());
					} else {
						mailVO.setSubjectText(AppConstants.B2C_TRAVEL_POLICY_EMAIL_SUBJECT + "- #"
								+ policyDataVO.getCommonVO().getPolicyNo());
					}
					// Oman D2C Email template change - End
					if (!Utils.isEmpty(policyDataVO.getGeneralInfo().getInsured().getFirstName())
							&& !Utils.isEmpty(policyDataVO.getGeneralInfo().getInsured().getLastName())) {
						emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
								WordUtils.capitalizeFully(policyDataVO.getGeneralInfo().getInsured().getFirstName()
										+ " " + policyDataVO.getGeneralInfo().getInsured().getLastName()));
					} else {
						emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
								WordUtils.capitalizeFully(policyDataVO.getGeneralInfo().getInsured().getName()));
					}
					// policy wording
					String polWordingTag = null;
					try {
						polWordingTag = AppUtils.constructPolicyWordingURL(path, LOB.TRAVEL,
								policyDataVO.getScheme().getSchemeCode(),
								policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName(), policyDataVO);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					emailContent = emailContent.replace(AppConstants.B2C_POLICY_WORDING_TAG, polWordingTag);
					mailVO.setToAddress(policyDataVO.getGeneralInfo().getInsured().getEmailId());
					mailVO.setMailContent(new StringBuilder(emailContent));
					flag = sendEmail(mailVO, policyDataVO);
					if (!flag) {
						logger.debug("Email trigger failed after TRAVEL convert to policy for policy number -_1"
								+ String.valueOf(policyDataVO.getCommonVO().getPolicyNo()));
					}
				} else {
					logger.debug("Error occured while reading email template -_4"
							+ AppConstants.B2C_TRAVEL_SUCCESS_POLICY_PURCHASE_TEMPLATE);
				}
				break;

			case HOME_CREATE_QUOTE: // AFTER HOME CREATE QUOTE
				logger.info("case: HOME_CREATE_QUOTE");
				clickHereLink = AppUtils.constructClickHereURL(policyDataVO.getCommonVO().getQuoteNo(),
						policyDataVO.getGeneralInfo().getInsured().getEmailId(),
						Utils.getSingleValueAppConfig("B2C_SCHEDULER_URL_HOME"), policyDataVO.getCommonVO().getLob(),
						null);
				emailContent = AppUtils.getTempalteContentAsString(AppConstants.B2C_STEP1_ONLY_HOME_TEMPLATE);
				if (!Utils.isEmpty(emailContent)) {
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_CLICK_HERE_TAG_IDF, clickHereLink);
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
							AppConstants.B2C_DEFAULT_CUST_NAME);
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_QUOTE_NO_IDENTIFIER,
							String.valueOf(policyDataVO.getCommonVO().getQuoteNo()));
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_PREMIUM_IDENTIFIER,
							AppUtils.getFormattedNumberWithDecimals(policyDataVO.getPremiumVO().getPremiumAmt()));
					mailVO.setMailContent(new StringBuilder(emailContent));
					mailVO.setToAddress(policyDataVO.getGeneralInfo().getInsured().getEmailId());
					mailVO.setSubjectText(AppConstants.B2C_HOME_QUOTE_EMAIL_SUBJECT + "- #"
							+ policyDataVO.getCommonVO().getQuoteNo());
					flag = sendGeneralEmail(mailVO, com.Constant.CONST_QUOTE);
					if (!flag) {
						logger.debug("Email trigger failed after HOME create quote for quotation number - "
								+ String.valueOf(policyDataVO.getCommonVO().getQuoteNo()));
					}
				} else {
					logger.debug("Error occured while reading email template -_5"
							+ AppConstants.B2C_STEP1_ONLY_HOME_TEMPLATE);
				}
				break;

			case HOME_SAVE_FOR_LATER:
				logger.info("case: HOME SAVE FOR LATER ");
				// emailContent =
				// AppUtils.getTempalteContentAsString(AppConstants.B2C_HOME_SAVE_FOR_LATER_TEMP_NAME);
				/* Ticket 165419 */
				if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
					resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_HOME_EMAIL_TEMPLATE_ONLINE);
				} else {
					resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_HOME_TRAVEL_EMAIL_TEMPLATE_DIRECT,
							policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName());
				}

				if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
					logger.info(" HOME SAVE FOR LATER for Direct");

					emailContent = returnQueryResultList(emailContent, resultList, policyDataVO, flow, flag);
					emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
							Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_RSA_CALLCENTER_NUMBER));
					emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE, " ");
					emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1,
							Utils.getSingleValueAppConfig("BULLET_POLNT_1_Direct"));
					emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2,
							Utils.getSingleValueAppConfig("BULLET_POLNT_2_Direct"));
					emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC,
							Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POINT_DISC_DIRECT));
					emailContent = emailContent.replace(com.Constant.CONST_DISC_PERCENT,
							Utils.getSingleValueAppConfig("BULLET_POINT_DISC_PERCENT_DIRECT"));
					mailVO.setDirect(true);
				} else {
					int brokerCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
					if (brokerCode == AppConstants.B2C_FGB_BROKER_CODE) {
						List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.FGB_BROKER_SFL,
								policyDataVO.getScheme().getSchemeCode());

						if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
							emailContent = resultSet.get(0);
							logger.debug("HOME_SAVE_FOR_LATER :  SFL emailContent for the scheme "
									+ policyDataVO.getScheme().getSchemeCode() + "   " + emailContent);
						}
						if (policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()
								.equals(SvcConstants.DIST_CHANNEL_BROKER)) {
							emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
									policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1,
									Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_1_BROKER));
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2,
									Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_2_BROKER));
							// emailContent =
							// emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE,
							// Utils.getSingleValueAppConfig("EMAIL_SIGNATURE_"+brokerCode));
							mailVO.setDirect(false);
						}
					} // 156578 : Code change by Pushkar for Nexus Home mirror
						// site to pick email for Save for later from
						// T_MAS_PARTNER_MGMT table.
					else if (brokerCode == AppConstants.B2C_NEX_BROKER_CODE) {

						List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.NEX_BROKER_SFL,
								policyDataVO.getScheme().getSchemeCode());

						if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
							emailContent = resultSet.get(0);
							logger.debug("HOME_SAVE_FOR_LATER :  SFL emailContent for the scheme "
									+ policyDataVO.getScheme().getSchemeCode() + "   " + emailContent);
						}
						if (policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()
								.equals(SvcConstants.DIST_CHANNEL_BROKER)) {
							emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
									policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1,
									Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_1_BROKER));
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2,
									Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_2_BROKER));
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC, "");
							// emailContent =
							// emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE,
							// Utils.getSingleValueAppConfig("EMAIL_SIGNATURE_"+brokerCode));
							mailVO.setDirect(false);
						}

					} else {
						// emailContent =
						// AppUtils.getTempalteContentAsString(AppConstants.B2C_HOME_SAVE_FOR_LATER_TEMP_NAME);
						/* Ticket 165419 */
						emailContent = returnQueryResultList(emailContent, resultList, policyDataVO, flow, flag);
						if (policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()
								.equals(SvcConstants.DIST_CHANNEL_BROKER)) {
							logger.info("HOME SAVE FOR LATER for Lifecare/Expat");
							List<Object[]> resultSet = DAOUtils.getSqlResultForPas(QueryConstants.BROKER_ADDRESS,
									brokerCode);
							if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
								StringBuffer brokerAddress = new StringBuffer();
								brokerAddress.append(com.Constant.CONST_BR_YOURS_SINCERELY_BR_BR_END);
								brokerAddress.append(com.Constant.CONST_THE_CUSTOMER_SERVICE_TEAM_BR_BR_END);
								for (Object object : resultSet.get(0)) {
									if (!Utils.isEmpty(object)) {
										brokerAddress.append(String.valueOf(object)).append(com.Constant.CONST_BR_END);
									}
								}
								emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE,
										brokerAddress.toString());
							}
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1,
									Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_1_BROKER));
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2,
									Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_2_BROKER));
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC, "");
							emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
									policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
							mailVO.setDirect(false);
						} else {
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1,
									Utils.getSingleValueAppConfig("BULLET_POLNT_1_Direct"));
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2,
									Utils.getSingleValueAppConfig("BULLET_POLNT_2_Direct"));
							if (!Utils
									.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount())
									&& policyDataVO.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount()
											.intValue() != 0) {
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC,
										Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POINT_DISC_DIRECT));
								emailContent = emailContent.replace(com.Constant.CONST_DISC_PERCENT,
										Integer.valueOf(Math.abs(policyDataVO.getGeneralInfo().getSourceOfBus()
												.getDefaultOnlineDiscount().intValue())).toString());
							} else {
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC, "");
							}
							logger.info("Home Save for later RSA Campaign");
							emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
									Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_RSA_CALLCENTER_NUMBER));
							emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE, " ");
							mailVO.setDirect(true);
						}
					}
				}
				if (!Utils.isEmpty(emailContent)) {
					clickHereLink = AppUtils.constructClickHereURL(policyDataVO.getCommonVO().getQuoteNo(),
							policyDataVO.getGeneralInfo().getInsured().getEmailId(), path,
							policyDataVO.getCommonVO().getLob(), policyDataVO.getCommonVO().getDocCode());
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_CLICK_HERE_TAG_IDF, clickHereLink);
					if (!AppConstants.NO_NAME_STRING
							.equalsIgnoreCase(policyDataVO.getGeneralInfo().getInsured().getFirstName())) {
						emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
								WordUtils.capitalizeFully(policyDataVO.getGeneralInfo().getInsured().getFirstName()));
					} else {
						emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
								AppConstants.B2C_DEFAULT_CUST_NAME);
					}
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_QUOTE_NO_IDENTIFIER,
							String.valueOf(policyDataVO.getCommonVO().getQuoteNo()));
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_PREMIUM_IDENTIFIER,
							((AppUtils.getFormattedNumberWithDecimals(policyDataVO.getPremiumVO().getPremiumAmt()))
									+ AppConstants.B2C_EMAIL_VAT_INCL_TEXT));
					if (!AppConstants.NO_NAME_STRING
							.equalsIgnoreCase(policyDataVO.getGeneralInfo().getInsured().getLastName())) {
						emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_LAST_NAME_IDF,
								WordUtils.capitalizeFully(policyDataVO.getGeneralInfo().getInsured().getLastName()));
					} else {
						emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_LAST_NAME_IDF, "");
					}
					mailVO.setMailContent(new StringBuilder(emailContent));
					mailVO.setSubjectText(AppConstants.B2C_HOME_QUOTE_EMAIL_SUBJECT + "- #"
							+ policyDataVO.getCommonVO().getQuoteNo());
					mailVO.setToAddress(policyDataVO.getGeneralInfo().getInsured().getEmailId());
					flag = sendEmail(mailVO, policyDataVO);
					if (!flag) {
						logger.debug("Email trigger failed after HOME after save for later quotation number - "
								+ String.valueOf(policyDataVO.getCommonVO().getQuoteNo()));
					}
				} else {
					logger.debug("Error occured while reading email template -_6"
							+ AppConstants.B2C_HOME_SAVE_FOR_LATER_TEMP_NAME);
				}
				break;

			case HOME_CONVERT_TO_POLICY:
				logger.info("case: HOME_CONVERT_TO_POLICY ");
				// emailContent =
				// AppUtils.getTempalteContentAsString(AppConstants.B2C_HOME_SUCCESS_POLICY_PURCHASE_TEMPLATE);
				/* Ticket 165419 */
				if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
					resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_HOME_EMAIL_TEMPLATE_ONLINE);
					logger.debug("HOME_CONVERT_TO_POLICY Direct emailContent : " + "   " + emailContent);
				} else {
					resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_HOME_TRAVEL_EMAIL_TEMPLATE_DIRECT,
							policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName());
					logger.debug("HOME_CONVERT_TO_POLICY Direct Campaign emailContent : " + "   " + emailContent);
				}

				if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
					logger.info("HOME_CONVERT_TO_POLICY Direct");
					emailContent = returnQueryResultList(emailContent, resultList, policyDataVO, flow, flag);
					emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
							Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_RSA_CALLCENTER_NUMBER));
					emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE, " ");
					mailVO.setDirect(true);
				} else {
					int brokerCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
					if (brokerCode == AppConstants.B2C_FGB_BROKER_CODE) {
						List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.FGB_BROKER_CTP,
								policyDataVO.getScheme().getSchemeCode());
						if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
							emailContent = resultSet.get(0);
							logger.debug("HOME_CONVERT_TO_POLICY : SFL emailContent for the scheme_1"
									+ policyDataVO.getScheme().getSchemeCode() + "   " + emailContent);
						}
						if (policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()
								.equals(SvcConstants.DIST_CHANNEL_BROKER)) {
							emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
									policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1,
									Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_1_BROKER));
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2,
									Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_2_BROKER));
							// emailContent =
							// emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE,
							// Utils.getSingleValueAppConfig("EMAIL_SIGNATURE_"+brokerCode));
							mailVO.setDirect(false);
						}
					} // 156578 : Code change by Pushkar for Nexus Home mirror
						// site to pick email for convert to policy from
						// T_MAS_PARTNER_MGMT table.
					else if (brokerCode == AppConstants.B2C_NEX_BROKER_CODE) {
						List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.NEX_BROKER_CTP,
								policyDataVO.getScheme().getSchemeCode());
						if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
							emailContent = resultSet.get(0);
							logger.debug("HOME_CONVERT_TO_POLICY : SFL emailContent for the scheme_2"
									+ policyDataVO.getScheme().getSchemeCode() + "   " + emailContent);
						}
						if (policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()
								.equals(SvcConstants.DIST_CHANNEL_BROKER)) {
							emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
									policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1,
									Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_1_BROKER));
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2,
									Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_2_BROKER));
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC, "");
							// emailContent =
							// emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE,
							// Utils.getSingleValueAppConfig("EMAIL_SIGNATURE_"+brokerCode));
							mailVO.setDirect(false);
						}
					} else {
						logger.info(" HOME Convert to Policy for Lifecare/Expat");
						// emailContent =
						// AppUtils.getTempalteContentAsString(AppConstants.B2C_HOME_SUCCESS_POLICY_PURCHASE_TEMPLATE);
						/* Ticket 165419 */
						emailContent = returnQueryResultList(emailContent, resultList, policyDataVO, flow, flag);
						if (policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()
								.equals(SvcConstants.DIST_CHANNEL_BROKER)) {
							List<Object[]> resultSet = DAOUtils.getSqlResultForPas(QueryConstants.BROKER_ADDRESS,
									brokerCode);
							if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
								StringBuffer brokerAddress = new StringBuffer();
								brokerAddress.append(com.Constant.CONST_BR_YOURS_SINCERELY_BR_BR_END);
								brokerAddress.append(com.Constant.CONST_THE_CUSTOMER_SERVICE_TEAM_BR_BR_END);
								for (Object object : resultSet.get(0)) {
									if (!Utils.isEmpty(object)) {
										brokerAddress.append(String.valueOf(object)).append(com.Constant.CONST_BR_END);
									}
								}
								emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE,
										brokerAddress.toString());
								emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
										policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1,
										Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_1_BROKER));
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2,
										Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_2_BROKER));
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC, "");
							}
							mailVO.setDirect(false);
						} else {
							logger.info(" Home Convert to policy RSA Campaign");
							emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
									Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_RSA_CALLCENTER_NUMBER));
							emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE, " ");
							mailVO.setDirect(true);
						}
					}
				}
				if (!Utils.isEmpty(emailContent)) {
					if (!AppConstants.NO_NAME_STRING
							.equalsIgnoreCase(policyDataVO.getGeneralInfo().getInsured().getFirstName())) {
						emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
								WordUtils.capitalizeFully(policyDataVO.getGeneralInfo().getInsured().getFirstName()));
					} else {
						emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
								AppConstants.B2C_DEFAULT_CUST_NAME);
					}
					if (!AppConstants.NO_NAME_STRING
							.equalsIgnoreCase(policyDataVO.getGeneralInfo().getInsured().getLastName())) {
						emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_LAST_NAME_IDF,
								WordUtils.capitalizeFully(policyDataVO.getGeneralInfo().getInsured().getLastName()));
					} else {
						emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_LAST_NAME_IDF, "");
					}
					// policy wording
					String polWordingTag = null;
					try {
						polWordingTag = AppUtils.constructPolicyWordingURL(path, LOB.HOME,
								policyDataVO.getScheme().getSchemeCode(),
								policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName(), policyDataVO);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					/*
					 * if(!Utils.isEmpty(
					 * policyDataVO.getGeneralInfo().getSourceOfBus
					 * ().getPartnerName())) { String[] urlArray =
					 * polWordingTag.split("/"); int len =
					 * urlArray[urlArray.length-1].length(); polWordingTag =
					 * polWordingTag.substring(0, polWordingTag.length() - len);
					 * }
					 */
					emailContent = emailContent.replace(AppConstants.B2C_POLICY_WORDING_TAG, polWordingTag);
					mailVO.setSubjectText(AppConstants.B2C_HOME_POLICY_EMAIL_SUBJECT + "- #"
							+ policyDataVO.getCommonVO().getPolicyNo());
					mailVO.setToAddress(policyDataVO.getGeneralInfo().getInsured().getEmailId());
					mailVO.setSignature("RSA");
					mailVO.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime())); // Setting
																									// the
																									// current
																									// time
																									// stamp
					mailVO.setMailType(SvcConstants.MAIL_TYPE_HTML);
					mailVO.setMailContent(new StringBuilder(emailContent));
					logger.debug("Final Email Content of the Broker / Direct Customers: " + emailContent);
					flag = sendEmail(mailVO, policyDataVO);
					if (!flag) {
						logger.debug("Email trigger failed after TRAVEL convert to policy for policy number -_2"
								+ String.valueOf(policyDataVO.getCommonVO().getPolicyNo()));
					}
				} else {
					logger.debug("Error occured while reading email template -_7"
							+ AppConstants.B2C_HOME_SUCCESS_POLICY_PURCHASE_TEMPLATE);
				}
				break;

			case REQUEST_CALL_BACK:
				logger.info("case: REQUEST_CALL_BACK");
				populateCallBackEmail(callBackNumber);
				break;
			// sonar fix
			default:
				break;
			}
		}
		return mailVO;
	}

	/**
	 * Ticket 165419 Method uesd to get the email templates from DB for
	 * SaveForLater, COnvertToPolicy, 24 and 15 days email reminder.
	 * 
	 * @param emailContent
	 * @param resultList
	 * @param policyDataVO
	 * @param flow
	 *            : Home or Travel
	 * @param isTwentyFourHrsInd
	 * @return
	 */
	public static String returnQueryResultList(String emailContent, List resultList, PolicyDataVO policyDataVO,
			B2CEmailTriggers flow, boolean isTwentyFourHrsInd) {
		try {
			Object[] object = null;
			if (!Utils.isEmpty(resultList) && resultList.size() > 0) {
				Iterator itr = resultList.listIterator(0);
				while (itr.hasNext()) {
					object = (Object[]) itr.next();
				}
			}
			if (!Utils.isEmpty(flow)) {
				switch (flow) {
				case HOME_SAVE_FOR_LATER:
					String homeSFL = String.valueOf(object[0]);
					emailContent = homeSFL;
					break;
				case HOME_CONVERT_TO_POLICY:
					String homeCTP = String.valueOf(object[1]);
					emailContent = homeCTP;
					break;
				case TRAVEL_SAVE_FOR_LATER:
					String travelSFL = String.valueOf(object[0]);
					emailContent = travelSFL;
					break;
				case TRAVEL_CONVERT_TO_POLICY:
					String travelCTP = String.valueOf(object[1]);
					emailContent = travelCTP;
					break;
				default:
					break;
				}
			}

			if (Utils.isEmpty(flow)) {
				if (policyDataVO.getCommonVO().getLob().equals(LOB.HOME)) {
					if (isTwentyFourHrsInd) {
						String homeTwentyFourHrs = String.valueOf(object[2]);
						emailContent = homeTwentyFourHrs;
					} else {
						String sevenDays = String.valueOf(object[3]);
						emailContent = sevenDays;
					}
				} else {
					if (isTwentyFourHrsInd) {
						String travelTwentyFourHrs = String.valueOf(object[2]);
						emailContent = travelTwentyFourHrs;
					} else {
						String sevenDays = String.valueOf(object[3]);
						emailContent = sevenDays;
					}
				}
			}
			if (Utils.isEmpty(emailContent)) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			logger.debug("Error occured while reading email template for - "
					+ policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName() + " : " + e.getMessage());
			logger.error(e.getStackTrace().toString());
		}
		return emailContent;
	}

	/**
	 * Request 131378 Save online payment Request details to DB
	 * 
	 * @param paymentDetails
	 *            ,policyDataVO
	 */
	public void saveOnlineRequestPaymentDetails(PolicyDataVO policyDataVO, Map<String, String> paymentDetails,
			PaymentDetailsVO paymentDetailsvo) {
		logger.info("Entered CommonHandler.saveOnlineRequestPaymentDetails method - starts.");
		try {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			StringBuffer requestString = new StringBuffer();

			paymentDetailsvo.setTransactionRefNo(paymentDetails.get("reference_number"));
			paymentDetailsvo.setQuoteNo(policyDataVO.getCommonVO().getQuoteNo());
			paymentDetailsvo.setEndtID(policyDataVO.getCommonVO().getEndtId());
			paymentDetailsvo.setPolicyId(policyDataVO.getCommonVO().getPolicyId());
			paymentDetailsvo.setFirstName((policyDataVO.getGeneralInfo().getInsured().getFirstName()));
			paymentDetailsvo.setSurname((policyDataVO.getGeneralInfo().getInsured().getLastName()));
			paymentDetailsvo.setMobileNo(policyDataVO.getGeneralInfo().getInsured().getMobileNo());
			paymentDetailsvo.seteMailId(policyDataVO.getGeneralInfo().getInsured().getEmailId());
			paymentDetailsvo.setRequestedPremiumAmt(policyDataVO.getPremiumVO().getPremiumAmt());
			paymentDetailsvo.setTariffCode(policyDataVO.getScheme().getTariffCode());
			paymentDetailsvo.setDocumentCode(policyDataVO.getCommonVO().getDocCode());
			Date date = formatter.parse(paymentDetails.get("request_date_time")); // 143948
																					// Request
																					// time
																					// in
																					// Dubai
																					// date
																					// time
			paymentDetailsvo.setTransDate(date);
			for (Map.Entry<String, String> temp : paymentDetails.entrySet()) {
				requestString.append(temp.getKey());
				requestString.append("=");
				requestString.append(temp.getValue());
				requestString.append(" , ");
			}
			String requestDetails = requestString.toString();
			paymentDetailsvo.setRequestdeatils(requestDetails);
			paymentDetailsvo.setBrokerCode(policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName());

			logger.info("CommonHandler.saveOnlineRequestPaymentDetails method, QuoteNo: "
					+ paymentDetailsvo.getQuoteNo() + " EndtId: " + paymentDetailsvo.getEndtID() + " policyId:"
					+ paymentDetailsvo.getPolicyId() + " tariffCode:" + paymentDetailsvo.getTariffCode());
			logger.info("CommonHandler.saveOnlineRequestPaymentDetails method, \nrequestDetails: " + requestDetails
					+ " ,  \nBrokerCode: " + policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName());

			TaskExecutor.executeTasks("SAVE_ONLINE_REQUEST_PAYMENT_DETAILS", paymentDetailsvo);
		} catch (BusinessException be) {
			logger.error(
					"CommonHandler.saveOnlineRequestPaymentDetails method, BusinessException : " + be.getMessage());
			throw new BusinessException(be.getErrorKeysList().get(0), be.getCause(), be.getMessage());
		} catch (Exception exp) {
			logger.error("CommonHandler.saveOnlineRequestPaymentDetails method, Exception : " + exp.getMessage());
			exp.printStackTrace();
		}
	}

	public static MailVO populateAndTriggerEmail(PolicyDataVO policyDataVO, String path,
			CreatePolicyRequest createPolicyRequest, B2CEmailTriggers flow) {

		logger.info("calling populateAndTriggerEmail HomeInsuranceSvcHandler class started");
		MailVO mailVO = (MailVO) Utils.getBean(com.Constant.CONST_MAILVO);
		String emailContent = null;
		boolean flag = false;
		List resultList = new ArrayList();

		if (!Utils.isEmpty(policyDataVO) && !Utils.isEmpty(flow)) {

			// Setting the common elements in MailVO
			if (!Utils.isEmpty(createPolicyRequest.getPolicyConfirmationEmail())
					&& createPolicyRequest.getPolicyConfirmationEmail()) {

				mailVO.setSignature("RSA");
				mailVO.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				mailVO.setMailType(SvcConstants.MAIL_TYPE_HTML);
				if (Utils.isEmpty(policyDataVO.getGeneralInfo())
						|| Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus())
						|| Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerId())) {
					String CCAdress[] = AppConstants.B2C_DEFAULT_CC_EMAILID.split(",");
					mailVO.setFromAddress(AppConstants.B2C_DEFAULT_FROM_EMAILID);
					mailVO.setCcAddress(CCAdress);
				} else {
					mailVO.setFromAddress(policyDataVO.getGeneralInfo().getSourceOfBus().getFromEmailID());
					if (!Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getCcEmailId())) {
						String[] ccString = policyDataVO.getGeneralInfo().getSourceOfBus().getCcEmailId().split(";");
						mailVO.setCcAddress(ccString);
					}
					mailVO.setReplyToEmailID(policyDataVO.getGeneralInfo().getSourceOfBus().getReplyToEmailId());
					logger.debug("To Email id: " + policyDataVO.getGeneralInfo().getSourceOfBus().getReplyToEmailId());
				}
			}
			switch (flow) {
			case TRAVEL_CONVERT_TO_POLICY:
				if (!Utils.isEmpty(createPolicyRequest.getPolicyConfirmationEmail())
						&& createPolicyRequest.getPolicyConfirmationEmail()) {
					logger.info("case: TRAVEL_CONVERT_TO_POLICY");
					// emailContent =
					// AppUtils.getTempalteContentAsString(AppConstants.B2C_TRAVEL_SUCCESS_POLICY_PURCHASE_TEMPLATE);
					/* Ticket 165419 */
					if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
						resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_TRAVEL_EMAIL_TEMPLATE_ONLINE);
					} else {
						resultList = DAOUtils.getSqlResultForPasString(
								QueryConstants.B2C_HOME_TRAVEL_EMAIL_TEMPLATE_DIRECT,
								policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName());
					}
					if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
						// Oman D2C Email template change - Start
						emailContent = returnQueryResultList(emailContent, resultList, policyDataVO, flow, flag);
						if (!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && Utils
								.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")) {
							logger.info(" TRAVEL CONVERT TO POLICY for Oman D2C");
							String pmmId = AppConstants.OMAN_D2C_TRAVEL;

							List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.POLICY_OMAN_D2C,
									pmmId);
							if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
								emailContent = resultSet.get(0);
								logger.debug("emailContent CONVERT TO POLICY Oman D2C Travel " + emailContent);
							}
							emailContent = emailContent.replace(com.Constant.CONST_TRAVELPAGE,
									Utils.getSingleValueAppConfig(com.Constant.CONST_D2C_OMAN_TRAVEL_STEP1));
						}
						// Oman D2C Email template change - End
						logger.info(" Travel Convert to policy Direct");
						emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
								Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_RSA_CALLCENTER_NUMBER));
						emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE, " ");
						mailVO.setDirect(true);
					} else {
						int brokerCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
						if (brokerCode == AppConstants.B2C_FGB_BROKER_CODE) {

							List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.FGB_BROKER_CTP,
									policyDataVO.getScheme().getSchemeCode());
							if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
								emailContent = resultSet.get(0);
								logger.debug("TRAVEL_CONVERT_TO_POLICY : CTP Travel emailContent for the scheme  "
										+ policyDataVO.getScheme().getSchemeCode() + "  " + emailContent);
							}
							Integer packCode = policyDataVO.getScheme().getTariffCode();
							List<String> resultSet1 = DAOUtils.getSqlResultForPasString(QueryConstants.PACKAGE_CODE,
									packCode);
							String packName = null;
							if (!Utils.isEmpty(resultSet1) && resultSet1.size() > 0) {
								packName = resultSet1.get(0);
								logger.debug("emailContent package details of Travel Partner_3" + packName);
							}
							if (policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()
									.equals(SvcConstants.DIST_CHANNEL_BROKER)) {
								emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
										policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1,
										Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_1_BROKER));
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2,
										Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_2_BROKER));
								// emailContent =
								// emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE,
								// Utils.getSingleValueAppConfig("EMAIL_SIGNATURE_"+brokerCode));
								mailVO.setDirect(false);
							}
						} else if (brokerCode == AppConstants.B2C_NEX_BROKER_CODE) {
							logger.info("Travel Convert to Policy Nexus");
							// emailContent =
							// AppUtils.getTempalteContentAsString(AppConstants.B2C_TRAVEL_C2P_NEXUS);
							/* Ticket 165419 */
							resultList = DAOUtils.getSqlResultForPasString(QueryConstants.TRAVEL_NEX_BROKER,
									policyDataVO.getScheme().getSchemeCode());
							emailContent = returnQueryResultList(emailContent, resultList, policyDataVO, flow, flag);
							logger.debug("TRAVEL_Convert_To_POlicy :  C2P emailContent for the scheme "
									+ policyDataVO.getScheme().getSchemeCode() + "   " + emailContent);
							emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
									policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
							logger.debug("emailContent CTP Nexus Travel " + emailContent);
							mailVO.setDirect(false);
						} else {
							emailContent = returnQueryResultList(emailContent, resultList, policyDataVO, flow, flag);
							if (policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()
									.equals(SvcConstants.DIST_CHANNEL_BROKER)) {
								logger.info(" Travel Convert to policy Lifecare/Expat");
								List<Object[]> resultSet = DAOUtils.getSqlResultForPas(QueryConstants.BROKER_ADDRESS,
										brokerCode);
								if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
									StringBuffer brokerAddress = new StringBuffer();
									brokerAddress.append(com.Constant.CONST_BR_YOURS_SINCERELY_BR_BR_END);
									brokerAddress.append(com.Constant.CONST_THE_CUSTOMER_SERVICE_TEAM_BR_BR_END);
									for (Object object : resultSet.get(0)) {
										if (!Utils.isEmpty(object)) {
											brokerAddress.append(String.valueOf(object))
													.append(com.Constant.CONST_BR_END);
										}
									}
									emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
											policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
									emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1,
											Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_1_BROKER));
									emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2,
											Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_2_BROKER));
									emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC, "");
									emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE,
											brokerAddress.toString());
								}
								mailVO.setDirect(false);
							} else {
								logger.info("Travel Convert to policy RSA Campaign");
								emailContent = returnQueryResultList(emailContent, resultList, policyDataVO, flow,
										flag);
								emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE, " ");
								emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER, Utils
										.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_RSA_CALLCENTER_NUMBER));
								mailVO.setDirect(true);
							}
						}
					}
					if (!Utils.isEmpty(emailContent)) {
						// Oman D2C Email template change - Start
						if (!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && Utils
								.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")) {
							mailVO.setSubjectText(AppConstants.D2C_OMAN_TRAVEL_POLICY_EMAIL_SUBJECT + " "
									+ policyDataVO.getCommonVO().getPolicyNo());
						} else {
							mailVO.setSubjectText(AppConstants.B2C_TRAVEL_POLICY_EMAIL_SUBJECT + "- #"
									+ policyDataVO.getCommonVO().getPolicyNo());
						}
						// Oman D2C Email template change - End
						if (!Utils.isEmpty(policyDataVO.getGeneralInfo().getInsured().getFirstName())
								&& !Utils.isEmpty(policyDataVO.getGeneralInfo().getInsured().getLastName())) {
							emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
									WordUtils.capitalizeFully(policyDataVO.getGeneralInfo().getInsured().getFirstName()
											+ " " + policyDataVO.getGeneralInfo().getInsured().getLastName()));
						} else {
							emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
									WordUtils.capitalizeFully(policyDataVO.getGeneralInfo().getInsured().getName()));
						}
						// policy wording
						String polWordingTag = null;
						try {
							polWordingTag = AppUtils.constructPolicyWordingURL(path, LOB.TRAVEL,
									policyDataVO.getScheme().getSchemeCode(),
									policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName(), policyDataVO);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						emailContent = emailContent.replace(AppConstants.B2C_POLICY_WORDING_TAG, polWordingTag);
						mailVO.setToAddress(policyDataVO.getGeneralInfo().getInsured().getEmailId());
						mailVO.setMailContent(new StringBuilder(emailContent));

					}
				}
				flag = sendEmail(mailVO, policyDataVO, createPolicyRequest);
				if (!flag) {
					logger.debug("Email trigger failed after TRAVEL convert to policy for policy number -_3"
							+ String.valueOf(policyDataVO.getCommonVO().getPolicyNo()));
				}
				break;

			case HOME_CONVERT_TO_POLICY:
				logger.info("case: HOME_CONVERT_TO_POLICY ");
				if (!Utils.isEmpty(createPolicyRequest.getPolicyConfirmationEmail())
						&& createPolicyRequest.getPolicyConfirmationEmail()) {
					// emailContent =
					// AppUtils.getTempalteContentAsString(AppConstants.B2C_HOME_SUCCESS_POLICY_PURCHASE_TEMPLATE);
					/* Ticket 165419 */
					if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
						resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_HOME_EMAIL_TEMPLATE_ONLINE);
						logger.debug("HOME_CONVERT_TO_POLICY Direct emailContent : " + "   " + emailContent);
					} else {
						resultList = DAOUtils.getSqlResultForPasString(
								QueryConstants.B2C_HOME_TRAVEL_EMAIL_TEMPLATE_DIRECT,
								policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName());
						logger.debug("HOME_CONVERT_TO_POLICY Direct Campaign emailContent : " + "   " + emailContent);
					}
					if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
						logger.info("HOME_CONVERT_TO_POLICY Direct");
						emailContent = returnQueryResultList(emailContent, resultList, policyDataVO, flow, flag);
						emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
								Utils.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_RSA_CALLCENTER_NUMBER));
						emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE, " ");
						mailVO.setDirect(true);
					} else {
						int brokerCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
						if (brokerCode == AppConstants.B2C_FGB_BROKER_CODE) {
							List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.FGB_BROKER_CTP,
									policyDataVO.getScheme().getSchemeCode());
							if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
								emailContent = resultSet.get(0);
								logger.debug("HOME_CONVERT_TO_POLICY : SFL emailContent for the scheme_3"
										+ policyDataVO.getScheme().getSchemeCode() + "   " + emailContent);
							}
							if (policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()
									.equals(SvcConstants.DIST_CHANNEL_BROKER)) {
								emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
										policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1,
										Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_1_BROKER));
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2,
										Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_2_BROKER));
								// emailContent =
								// emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE,
								// Utils.getSingleValueAppConfig("EMAIL_SIGNATURE_"+brokerCode));
								mailVO.setDirect(false);
							}
						} // 156578 : Code change by Pushkar for Nexus Home
							// mirror site to pick email for convert to policy
							// from T_MAS_PARTNER_MGMT table.
						else if (brokerCode == AppConstants.B2C_NEX_BROKER_CODE) {
							List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.NEX_BROKER_CTP,
									policyDataVO.getScheme().getSchemeCode());
							if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
								emailContent = resultSet.get(0);
								logger.debug("HOME_CONVERT_TO_POLICY : SFL emailContent for the scheme_4"
										+ policyDataVO.getScheme().getSchemeCode() + "   " + emailContent);
							}
							if (policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()
									.equals(SvcConstants.DIST_CHANNEL_BROKER)) {
								emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
										policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1,
										Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_1_BROKER));
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2,
										Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_2_BROKER));
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC, "");
								// emailContent =
								// emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE,
								// Utils.getSingleValueAppConfig("EMAIL_SIGNATURE_"+brokerCode));
								mailVO.setDirect(false);
							}
						} else {
							logger.info(" HOME Convert to Policy for Lifecare/Expat");
							// emailContent =
							// AppUtils.getTempalteContentAsString(AppConstants.B2C_HOME_SUCCESS_POLICY_PURCHASE_TEMPLATE);
							if (policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()
									.equals(SvcConstants.DIST_CHANNEL_BROKER)) {
								List<Object[]> resultSet = DAOUtils.getSqlResultForPas(QueryConstants.BROKER_ADDRESS,
										brokerCode);
								if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
									StringBuffer brokerAddress = new StringBuffer();
									brokerAddress.append(com.Constant.CONST_BR_YOURS_SINCERELY_BR_BR_END);
									brokerAddress.append(com.Constant.CONST_THE_CUSTOMER_SERVICE_TEAM_BR_BR_END);
									for (Object object : resultSet.get(0)) {
										if (!Utils.isEmpty(object)) {
											brokerAddress.append(String.valueOf(object))
													.append(com.Constant.CONST_BR_END);
										}
									}
									emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE,
											brokerAddress.toString());
									emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,
											policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
									emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1,
											Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_1_BROKER));
									emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2,
											Utils.getSingleValueAppConfig(com.Constant.CONST_BULLET_POLNT_2_BROKER));
									emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC, "");
								}
								mailVO.setDirect(false);
							} else {
								logger.info(" Home Convert to policy RSA Campaign");
								emailContent = returnQueryResultList(emailContent, resultList, policyDataVO, flow,
										flag);
								emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER, Utils
										.getSingleValueAppConfig(com.Constant.CONST_DEFAULT_RSA_CALLCENTER_NUMBER));
								emailContent = emailContent.replace(com.Constant.CONST_BROKER_SIGNATURE, " ");
								mailVO.setDirect(true);
							}
						}
					}
					if (!Utils.isEmpty(emailContent)) {
						if (!AppConstants.NO_NAME_STRING
								.equalsIgnoreCase(policyDataVO.getGeneralInfo().getInsured().getFirstName())) {
							emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
									WordUtils.capitalizeFully(
											policyDataVO.getGeneralInfo().getInsured().getFirstName()));
						} else {
							emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
									AppConstants.B2C_DEFAULT_CUST_NAME);
						}
						if (!AppConstants.NO_NAME_STRING
								.equalsIgnoreCase(policyDataVO.getGeneralInfo().getInsured().getLastName())) {
							emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_LAST_NAME_IDF, WordUtils
									.capitalizeFully(policyDataVO.getGeneralInfo().getInsured().getLastName()));
						} else {
							emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_LAST_NAME_IDF, "");
						}
						// policy wording
						String polWordingTag = null;
						try {
							polWordingTag = AppUtils.constructPolicyWordingURL(path, LOB.HOME,
									policyDataVO.getScheme().getSchemeCode(),
									policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName(), policyDataVO);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						/*
						 * if(!Utils.isEmpty(
						 * policyDataVO.getGeneralInfo().getSourceOfBus
						 * ().getPartnerName())) { String[] urlArray =
						 * polWordingTag.split("/"); int len =
						 * urlArray[urlArray.length-1].length(); polWordingTag =
						 * polWordingTag.substring(0, polWordingTag.length() -
						 * len); }
						 */
						emailContent = emailContent.replace(AppConstants.B2C_POLICY_WORDING_TAG, polWordingTag);
						mailVO.setSubjectText(AppConstants.B2C_HOME_POLICY_EMAIL_SUBJECT + "- #"
								+ policyDataVO.getCommonVO().getPolicyNo());
						mailVO.setToAddress(policyDataVO.getGeneralInfo().getInsured().getEmailId());
						mailVO.setSignature("RSA");
						// Setting the current time stamp
						mailVO.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
						mailVO.setMailType(SvcConstants.MAIL_TYPE_HTML);
						mailVO.setMailContent(new StringBuilder(emailContent));
						logger.debug("Final Email Content of the Broker / Direct Customers: " + emailContent);

					}
				}
				flag = sendEmail(mailVO, policyDataVO, createPolicyRequest);
				if (!flag) {
					logger.debug("Email trigger failed after Homme convert to policy for policy number - "
							+ String.valueOf(policyDataVO.getCommonVO().getPolicyNo()));
				}
				break;
			// sonar fix
			default:
				break;

			}

		}
		return mailVO;
	}

	// added to trigger mail when async fails
	public static MailVO asyncFailEmail(PolicyVO policyVO) throws Exception {

		logger.info("Async fail email trigger");
		MailVO mailVO = (MailVO) Utils.getBean(com.Constant.CONST_MAILVO);
		String emailContent = null;
		boolean flag = false;
		emailContent = AppUtils.getTempalteContentAsString(AppConstants.B2C_ASYNC_FAIL_TEMPLATE);
		if (!Utils.isEmpty(emailContent)) {
			InetAddress ip;
			ip = InetAddress.getLocalHost();
			mailVO.setSubjectText(
					"Async batch failure notice -" + String.valueOf(policyVO.getQuoteNo()) + "Server IP " + ip);
			if (!Utils.isEmpty(Utils.getMultiValueAppConfig("B2C_TRIGGER_EMAIL_CC_ID_ASYNC", ","))) {
				mailVO.setCcAddress(Utils.getMultiValueAppConfig("B2C_TRIGGER_EMAIL_CC_ID_ASYNC", ","));
			}
			// mailVO.setCcAddress(AppConstants.B2C_TRIGGER_EMAIL_CC_ID_ASYNC);
			mailVO.setToAddress(AppConstants.B2C_ASYNC_FAILURE_TOEMAILID);
			mailVO.setMailContent(new StringBuilder(emailContent));
			mailVO.setFromAddress(AppConstants.B2C_DEFAULT_FROM_EMAILID);
			flag = sendGeneralEmail(mailVO, null);
			if (!flag) {
				logger.debug("Email trigger failed for Async failure for quotation number - "
						+ String.valueOf(policyVO.getQuoteNo()));

			}
		} else {
			logger.debug("Error occured while reading email template -_8" + AppConstants.B2C_ASYNC_FAIL_TEMPLATE);
		}
		return mailVO;
	}

	@SuppressWarnings("unchecked")
	public static boolean sendEmail(MailVO mailVO, PolicyDataVO policyDataVO, CreatePolicyRequest createPolicyRequest) {
		List<String> fileNames = new ArrayList<String>();// Initially assumption
															// is only
															// proposal document
															// will be sent
		boolean mailStatus = false;
		HashMap<String, String> reportParams = new HashMap<String, String>();
		;
		CommonVO commonVO = policyDataVO.getCommonVO();
		DataHolderVO<Boolean> resultVo = null;
		logger.info("Inside sendEmail() of CommonHandler Class");

		if (!Utils.isEmpty(mailVO) && !Utils.isEmpty(policyDataVO) && !Utils.isEmpty(policyDataVO.getCommonVO())) {

			if (commonVO.getLob().equals(LOB.HOME)) {
				CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean(com.Constant.CONST_GECOMSVC);
				BaseVO baseVO = (BaseVO) commonOpSvc.invokeMethod(com.Constant.CONST_CHECKFORMORTGAGEENAME, commonVO);

				resultVo = (DataHolderVO<Boolean>) baseVO;
			} /*
				 * if (!Utils.isEmpty(resultVo) && resultVo.getData()) {
				 * fileNames = new String[4]; } else { fileNames = new
				 * String[3]; }
				 */

			if ((!Utils.isEmpty(createPolicyRequest.getDocuments().getDocsDetails())
					|| !Utils.isEmpty(createPolicyRequest.getPolicyConfirmationEmail()))
					&& (createPolicyRequest.getDocuments().getDocsInResponse()
							|| createPolicyRequest.getPolicyConfirmationEmail())) {

				reportParams.put(com.Constant.CONST_POLICYID, String.valueOf(policyDataVO.getCommonVO().getPolicyId()));
				reportParams.put(com.Constant.CONST_ENDORSEMENTID,
						String.valueOf(policyDataVO.getCommonVO().getEndtId()));
				reportParams.put(com.Constant.CONST_VALIDITYSTARTDATE,
						getFormattedDateAsString(policyDataVO.getCommonVO().getVsd()));
				reportParams.put(com.Constant.CONST_LANGUAGE, "E");
				reportParams.put(com.Constant.CONST_LOCATIONCODE,
						Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));
				/*
				 * reportParams.put("CreditNoteReport",
				 * com.Constant.CONST_FALSE);
				 * reportParams.put("GrossCreditNoteReport",
				 * com.Constant.CONST_FALSE);
				 * 
				 * reportParams.put("GrossDebitNoteReport",
				 * com.Constant.CONST_FALSE);
				 * reportParams.put("EndScheduleReport",
				 * com.Constant.CONST_FALSE);
				 * reportParams.put("policyScheduleClauses",
				 * com.Constant.CONST_FALSE);
				 * reportParams.put(com.Constant.CONST_SHOWBANKLETTER,
				 * com.Constant.CONST_FALSE);
				 */
				if (!Utils.isEmpty(createPolicyRequest.getDocuments().getDocsDetails().getPolicySchedule())
						&& createPolicyRequest.getDocuments().getDocsDetails().getPolicySchedule()) {

					fileNames.add(Utils.getSingleValueAppConfig(com.Constant.CONST_POL_DOC_POL_SCHED_LOC)
							+ policyDataVO.getCommonVO().getPolicyNo() + com.Constant.CONST_POLICYSCHEDULE_PDF);
					reportParams.put(com.Constant.CONST_POLICYSCHEDULE, "true");
				}
				if (!Utils.isEmpty(createPolicyRequest.getDocuments().getDocsDetails().getReceipt())
						&& createPolicyRequest.getDocuments().getDocsDetails().getReceipt()) {

					fileNames.add(Utils.getSingleValueAppConfig(com.Constant.CONST_POL_DOC_POL_RECEIPTS_LOC)
							+ policyDataVO.getCommonVO().getPolicyNo() + com.Constant.CONST_RECEIPT_PDF);
					reportParams.put(com.Constant.CONST_RECEIPT, "true");
					setReceiptParameters(policyDataVO.getCommonVO(), reportParams);
				}
				if (!Utils.isEmpty(createPolicyRequest.getDocuments().getDocsDetails().getDebitNote())
						&& createPolicyRequest.getDocuments().getDocsDetails().getDebitNote()) {
					fileNames.add(Utils.getSingleValueAppConfig(com.Constant.CONST_POL_DOC_DEB_NOTE_LOC)
							+ policyDataVO.getCommonVO().getPolicyNo() + com.Constant.CONST_TAXINVOICE_PDF);
					reportParams.put(com.Constant.CONST_DEBITNOTEREPORT, "true");
					setTaxInvoiceParameters(policyDataVO.getCommonVO(), reportParams);
				}
				if (!Utils.isEmpty(resultVo) && resultVo.getData()
						&& !Utils.isEmpty(createPolicyRequest.getDocuments().getDocsDetails().getLetterToBank())
						&& createPolicyRequest.getDocuments().getDocsDetails().getLetterToBank()) {
					reportParams.put(com.Constant.CONST_SHOWBANKLETTER, "true");
					fileNames.add(Utils.getSingleValueAppConfig("POL_DOC_BANK_LETTER") + commonVO.getPolicyNo()
							+ com.Constant.CONST_BANKLETTER_PDF);
				}
				String[] fileNames1 = new String[fileNames.size()];
				int i = 0;
				for (String string2 : fileNames) {
					fileNames1[i] = string2;
					i++;
				}
				mailVO.setFileNames(fileNames1);
				if (policyDataVO.getCommonVO().getLob().equals(LOB.TRAVEL)) {
					reportParams.put(com.Constant.CONST_REPORTTEMPLATESTYPE, ReportTemplateSet._TRAVEL.toString());
				} else if (policyDataVO.getCommonVO().getLob().equals(LOB.HOME)) {
					reportParams.put(com.Constant.CONST_REPORTTEMPLATESTYPE, ReportTemplateSet._HOME.toString());
				}
				mailVO.setDocParameter(reportParams);
				// mailVO.setCcAddress(AppConstants.B2C_TRIGGER_EMAIL_CC_ID);
				// //TODO
				// if required configure proper email id's in properties file
				try {

					PASDocumentService docCreator = (PASDocumentService) Utils
							.getBean(com.Constant.CONST_DOCSERVICEBEAN);
					mailVO = (MailVO) docCreator.invokeMethod(com.Constant.CONST_CREATEDOCUMENT, mailVO);
					if (!Utils.isEmpty(mailVO) && !mailVO.getDocCreationStatus().equalsIgnoreCase("failure")) {
						PASMailerService mailer = (PASMailerService) Utils.getBean(com.Constant.CONST_EMAILSERVICE);

						String[] documentsList = new String[fileNames.size()];
						documentsList = mailVO.getFileNames();
						/*
						 * if (!Utils.isEmpty(resultVo) && resultVo.getData()) {
						 * documentsList = new String[AppConstants.
						 * B2C_POLICY_ATTACHMENT_SIZE_MORTGAGE];
						 * documentsList[0] = mailVO.getFileNames()[0];
						 * documentsList[1] = mailVO.getFileNames()[1];
						 * documentsList[2] = mailVO.getFileNames()[2];
						 * documentsList[3] = mailVO.getFileNames()[3];
						 * 
						 * if( policyDataVO.getCommonVO().getLob().equals(
						 * LOB.TRAVEL ) ){ documentsList[ 3 ] =
						 * AppConstants.B2C_EMAIL_POLICY_WORDING_TRAVEL; } else{
						 * documentsList[ 3 ] =
						 * AppConstants.B2C_EMAIL_POLICY_WORDING_HOME; }
						 * 
						 * } else { documentsList = new
						 * String[AppConstants.B2C_POLICY_ATTACHMENT_SIZE];
						 * documentsList[0] = mailVO.getFileNames()[0];
						 * documentsList[1] = mailVO.getFileNames()[1];
						 * documentsList[2] = mailVO.getFileNames()[2];
						 * 
						 * if( policyDataVO.getCommonVO().getLob().equals(
						 * LOB.TRAVEL ) ){ documentsList[ 2 ] =
						 * AppConstants.B2C_EMAIL_POLICY_WORDING_TRAVEL; } else{
						 * documentsList[ 2 ] =
						 * AppConstants.B2C_EMAIL_POLICY_WORDING_HOME; }
						 * 
						 * }
						 */

						mailVO.setFileNames(documentsList);
						// Oman D2C Email template change - Start
						if (!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && Utils
								.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")) {
							mailVO = (MailVO) mailer.invokeMethod(com.Constant.CONST_SENDEMAILWITHIMAGE, mailVO,
									com.Constant.CONST_D2C_OMAN);
						} else {

							if (!Utils.isEmpty(createPolicyRequest.getPolicyConfirmationEmail())
									&& createPolicyRequest.getPolicyConfirmationEmail()) {
								mailVO = (MailVO) mailer.invokeMethod(com.Constant.CONST_SENDEMAILWITHIMAGE, mailVO,
										"POLICY");
							}

						}
						// Oman D2C Email template change - End

					} else {
						mailVO.setMailStatus("fail");
					}
				} catch (Exception e) {
					mailVO.setMailStatus("fail");
					logger.error("Exception Occured sending the mail:" + e.getMessage());
				}
				if (!Utils.isEmpty(mailVO) && !Utils.isEmpty(mailVO.getMailStatus())
						&& mailVO.getMailStatus().equalsIgnoreCase(com.Constant.CONST_SUCCESS2)) {
					mailStatus = true;
					logger.info("Email sending Successful for sendEmail() of CommonHandler Class");
				}
			}

		}

		return mailStatus;
	}

	// added to trigger mail when payment failure due to partial payment issue
	public static MailVO paymentFailureEmail(PolicyDataVO policyDataVO) throws Exception {

		logger.info("Payment Failure email trigger");
		MailVO mailVO = (MailVO) Utils.getBean("mailVO");
		boolean flag = false;
		StringBuilder emailContent1 = new StringBuilder();
		String emailContent = "Premium is not in line with B2B. Please call the following customer to convert asap:\n"
				+ "Name of the customer: " + policyDataVO.getGeneralInfo().getInsured().getFirstName() + "\n"
				+ "Quote number: " + policyDataVO.getCommonVO().getQuoteNo() + "\n" + "Mobile number: "
				+ policyDataVO.getGeneralInfo().getInsured().getMobileNo() + "\n" + "Email ID: "
				+ policyDataVO.getGeneralInfo().getInsured().getEmailId() + "\n";
		emailContent1 = emailContent1.append(emailContent);
		mailVO.setSubjectText("B2C Payment Failure due to Premium Mismatch -"
				+ String.valueOf(policyDataVO.getCommonVO().getQuoteNo()));
		if (!Utils.isEmpty(Utils.getMultiValueAppConfig("B2C_TRIGGER_EMAIL_CC_ID_ASYNC", ","))) {
			mailVO.setCcAddress(Utils.getMultiValueAppConfig("B2C_PAYMENT_FAIL_TRIGGER_EMAIL_CC_ID", ","));
		}
		mailVO.setToAddress(AppConstants.B2C_PAYMENT_FAIL_TRIGGER_EMAIL_TO_ID);
		mailVO.setMailContent(emailContent1);
		mailVO.setFromAddress(AppConstants.B2C_DEFAULT_FROM_EMAILID);
		mailVO.setMailType(SvcConstants.MAIL_TYPE_PLAINTXT);
		flag = sendGeneralEmail(mailVO, null);
		if (!flag) {
			logger.debug("Email trigger failed Premium Mismatch B2C - " + String.valueOf(policyDataVO.getQuoteNo()));

		}
		return mailVO;
	}

}
