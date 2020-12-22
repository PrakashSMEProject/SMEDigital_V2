/**
 * 
 */
package com.rsaame.pas.rating.svc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import com.cts.writeRate.Policy;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.constants.AMEConstants;
import com.rsaame.kaizen.framework.constants.AMEPolicyConstants;
import com.rsaame.kaizen.framework.constants.PropertyFileConstants;
import com.rsaame.kaizen.framework.exception.ErateException;
import com.rsaame.kaizen.framework.util.PropertiesUtil;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;

/**
 * @author M1014644
 *
 */
public abstract class RatingServiceInvoker {

	private final static Logger logger = Logger
			.getLogger(RatingServiceInvoker.class);
	protected IRatingInvoker ratingInvoker;

	/**
	 * Properties file containing the parameters required for erater properties.
	 */
	private final Properties actions = PropertiesUtil
			.loadProperties(PropertyFileConstants.PATH_MAPPING_CLSPE_MAPPING);
	/**
	 * Properties file containing Config Properties.
	 */
	private final Properties ameConfigActions = PropertiesUtil
			.loadProperties(PropertyFileConstants.PATH_AME_CONFIG);

	/*
	 * This Method invokes the rating engine to get the premium for a particular
	 * product
	 */
	public BaseVO invokeRating(BaseVO baseVo) {

		logger.info("Entering invokePremium");
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVo;
		logger.debug("Data in rating:" + policyDataVO);
		long startTime = System.currentTimeMillis();
		logger.debug("Calling Rating Start time:" + new Date(startTime));
		/*
		 * Prepare the key for the first rating invocation and get the details
		 * of all the cover details that needs to be mapped. This will be a
		 * concrete implementation
		 */
		Policy[] coverDetails = invokeRatingForCoverDetails(policyDataVO);

		/*
		 * Set the factor values for the cover details from the previous
		 * invocation from the respective VO's.
		 */
		Policy[] premiumDetails = invokeRatingForPremium(coverDetails,
				policyDataVO);

		/*
		 * Map the final result to the respective vo
		 */
		mapPremiumToVo(premiumDetails, policyDataVO);

		long endTime = System.currentTimeMillis();
		logger.debug("Calling Rating End time:" + new Date(endTime));
		logger.debug("Time taken for rating is " + (endTime - startTime) / 1000
				+ "sec");

		logger.info("Exiting invokePremium");
		return baseVo;
	}

	private Policy[] invokeRatingForCoverDetails(PolicyDataVO policyDataVO) {

		// List<FactorBO> factorBo = getCLSPEKey( policyDataVO );
		List<Policy> policyList = new ArrayList<Policy>(0);
		Policy[] policyArray = null;

		/*
		 * Get the policy object from policy data vo to send as first request to
		 * rating engine
		 */
		Policy policy = getPolicyDetail(policyDataVO);
		policyList.add(policy);

		policyArray = new Policy[policyList.size()];

		policyList.toArray(policyArray);

		try {
			policyArray = ratingInvoker.getDetailsForPolicy(policyArray);
		} catch (ErateException e) {
			logger.error("Rating Engine ErateException: riskGroupDetailsMap.entrySet() is null");

			BusinessException businessExcp = new BusinessException(
					"rating.invocation.no.loc", null,
					"riskGroupDetailsMap.entrySet() is null");
			throw businessExcp;

		}

		return policyArray;
	}

	private Policy getPolicyDetail(PolicyDataVO policyDataVO) {
		Policy policy = null;

		if (!Utils.isEmpty(policyDataVO)) {

			policy = new Policy();
			String lob = null;
			String product = null;
			String locationCode = null;
			String plan = null;

			Integer tariffCode = null;

			Date effectiveDate = null;

			/* Prepare the policy object from policy data vo */
			String service = actions
					.getProperty(AMEConstants.QUOTE_GET_SERVICE_NAME);
			String callerId = actions
					.getProperty(AMEConstants.QUOTE_CALLER_ID_VALUE);
			String password = actions
					.getProperty(AMEConstants.QUOTE_ERATER_PASS);
			String sourceRegion = ameConfigActions
					.getProperty(AMEConstants.QUOTE_GET_SOURCE_REGION);
			String transType = actions
					.getProperty(AMEConstants.QUOTE_ERATER_TRANSACTION_VALUE_NEW_BUSINESS);
			String debugIndexValue = actions
					.getProperty(AMEConstants.QUOTE_DEBUG_INDEX_VALUE);
			String company = SvcConstants.COMPANY;
			String ABUDHABI_LOC = "21";

			Integer classCode = policyDataVO.getPolicyClassCode();
			Integer policyType = policyDataVO.getPolicyType();

			if (policyType.equals(Integer.valueOf(Utils
					.getSingleValueAppConfig("TRAVEL_LONG_TERM_POLICY_TYPE")))) {
				policyType = Integer
						.valueOf(Utils
								.getSingleValueAppConfig("TRAVEL_SHORT_TERM_POLICY_TYPE"));
			}

			/* get the class code and policy type and form the product */
			if (!Utils.isEmpty(classCode) && !Utils.isEmpty(policyType)) {
				lob = String.valueOf(classCode);
				String policyTypeVaue = String.valueOf(policyType);
				String classValue = (lob.length() == 1) ? AMEPolicyConstants.APPEND_ZERO
						+ lob
						: lob;

				// classValue = "02"; // TODO -- Hard coding to be removed

				String policyTypeValue = (policyTypeVaue.length() == 1) ? AMEPolicyConstants.APPEND_ZERO
						+ policyTypeVaue
						: policyTypeVaue;
				product = String.valueOf(classValue)
						+ String.valueOf(policyTypeValue);
			}

			/* get the tariff code and effective date */
			if (!Utils.isEmpty(policyDataVO.getScheme())) {
				tariffCode = getTariffCode(policyDataVO);// policyDataVO.getScheme().getTariffCode();
				logger.debug("RSI getPolicyDetail() tariffCode: " + tariffCode);
				if (!Utils.isEmpty(policyDataVO.getScheme().getEffDate()))
					effectiveDate = policyDataVO.getScheme().getEffDate();
			}

			/* get location code and form plan */
			if (!Utils.isEmpty(policyDataVO.getCommonVO())
					&& !Utils.isEmpty(policyDataVO.getCommonVO().getLocCode())) {
				locationCode = String.valueOf(policyDataVO.getCommonVO()
						.getLocCode());
				logger.debug("RSI getPolicyDetail() locationCode: "
						+ locationCode);

				// Pass Abu dhabi location code as 20 as the rating
				// configuration is same for dubai as well as abu dhabi.
				if (ABUDHABI_LOC.equalsIgnoreCase(locationCode)) {
					locationCode = "20";
				} else if (!Utils
						.isEmpty(Utils
								.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION))
						&& Utils.getSingleValueAppConfig(
								SvcConstants.DEPLOYED_LOCATION)
								.equalsIgnoreCase("30")) {
					locationCode = "30";
				}

				String location = (locationCode.length() == 2) ? AMEPolicyConstants.APPEND_ZERO
						+ locationCode
						: locationCode;
				String tariff = null;
				if (!Utils.isEmpty(tariffCode))
					tariff = String.valueOf(tariffCode);
				tariffCode = mapTariff(tariffCode);
				logger.debug("RSI getPolicyDetail() mapTariff: " + tariffCode);
				if (!Utils.isEmpty(location) && !Utils.isEmpty(tariff))
					plan = location + tariff;
			}

			/* Set the values to policy object */
			if (!Utils.isEmpty(service)) {
				policy.setService(service);
				logger.debug("RSI getPolicyDetail() policy-service: " + service);
			}
			if (!Utils.isEmpty(callerId)) {
				policy.setCallerId(callerId);
				logger.debug("RSI getPolicyDetail() policy-callerId: "
						+ callerId);
			}
			if (!Utils.isEmpty(password))
				policy.setPassword(password);
			if (!Utils.isEmpty(sourceRegion))
				policy.setSourceRegion(sourceRegion);

			if (!Utils.isEmpty(locationCode))
				policy.setState(locationCode); // Location code - 20 30

			if (!Utils.isEmpty(lob))
				policy.setLob(lob); // classCode - concat 00
			if (!Utils.isEmpty(product)) {
				policy.setProduct(product); // class code + Policy type
				logger.debug("RSI getPolicyDetail() policy-product: " + product);
			}

			if (!Utils.isEmpty(plan)) {
				policy.setPlan(plan); // tariff code
				logger.debug("RSI getPolicyDetail() policy-plan: " + plan);
			}
			if (!Utils.isEmpty(company)) {
				policy.setCompany(company); // Company code
				logger.debug("RSI getPolicyDetail() policy-company: " + company);
			}

			if (!Utils.isEmpty(effectiveDate))
				policy.setEffectiveDate(effectiveDate);
			if (!Utils.isEmpty(effectiveDate))
				policy.setAvailableDate(effectiveDate);
			if (!Utils.isEmpty(transType)) {
				policy.setTransType(transType);
				logger.debug("RSI getPolicyDetail() policy-transType: "
						+ transType);
			}
			policy.setTermsInMonth(SvcConstants.TERM); // set to 12
			if (!Utils.isEmpty(debugIndexValue))
				policy.setDebugInd(Boolean.valueOf(debugIndexValue));
		}

		return policy;
	}

	/**
	 * Method to get the tariff code
	 * 
	 * @param policyDataVO
	 * @return
	 */
	private Integer getTariffCode(PolicyDataVO policyDataVO) {
		Integer tariffCode = null;
		/*Change scheme code type to Interger - Ticket Id 145686*/
		Integer schemeCode = null;
		BigDecimal schCode = null;
		/*
		 * FGB Staff Scheme Code for TRavel Added by Vishwa for the Advenet
		 * id:129135
		 */
		if (!Utils.isEmpty(policyDataVO)) {

			if (policyDataVO instanceof TravelInsuranceVO) {
			/*Change this block to make Travel rating configurable - Ticket Id 145686 */
				schemeCode = policyDataVO.getScheme().getSchemeCode();
				tariffCode = mapSchemeTariffForTravelRating(schemeCode);
				/*List<Object> resultSet = DAOUtils
						.getSqlResultForPas(QueryConstants.FGB_STAFF_SCHEME_CODE);
				if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
					schCode = (BigDecimal) resultSet.get(0);
					logger.debug("RSI getTariffCode(): " + schCode);
				}
				if (null != schCode && schCode.toString().equals(schemeCode)) {
					tariffCode = SvcConstants.TRAVEL_TARIFF_CODE_RATING_FGB_SCHEME;
				} else {
					tariffCode = SvcConstants.TRAVEL_TARIFF_CODE_RATING;
				}*/
			} else if (!Utils.isEmpty(policyDataVO.getScheme().getTariffCode())) {
				tariffCode = policyDataVO.getScheme().getTariffCode();
			}
		}
		return tariffCode;
	}

	/*
	 * The concrete implementation is specific for a particular product and will
	 * be implemented by the child class
	 */
	protected abstract Policy[] invokeRatingForPremium(Policy[] coverDetails,
			PolicyDataVO policyDataVO);

	/*
	 * The concrete implementation is specific for a particular product and will
	 * be implemented by the child class
	 */
	protected abstract void mapPremiumToVo(Policy[] premiumDetails,
			PolicyDataVO policyDataVO);

	public void setRatingInvoker(IRatingInvoker ratingInvoker) {
		this.ratingInvoker = ratingInvoker;
	}

	private int mapTariff(int tariff) {
		String RULE_TARIFF_MAP = Utils
				.getSingleValueAppConfig("RULE_TARIFF_MAP");
		if (RULE_TARIFF_MAP.contains("$" + tariff)) {
			StringTokenizer tok = new StringTokenizer(RULE_TARIFF_MAP, ";");
			while (tok.hasMoreTokens()) {
				String str = tok.nextToken();
				if (str.contains("$" + tariff)) {
					tariff = Integer
							.valueOf(str.substring(1, str.indexOf("-")));
					break;
				}
			}
		}
		return tariff;
	}
	
	/*Added this function to make Travel rating configurable - Ticket Id 145686 */
	private int mapSchemeTariffForTravelRating(Integer schemeCode) {
		
		Integer tariffCode = SvcConstants.TRAVEL_TARIFF_CODE_RATING;
		
		String SCHEME_TARIFF_MAP_TRAVEL = Utils
				.getSingleValueAppConfig("SCHEME_TARIFF_MAP_TRAVEL");
		if (SCHEME_TARIFF_MAP_TRAVEL.contains("$" + schemeCode)) {
			StringTokenizer tok = new StringTokenizer(SCHEME_TARIFF_MAP_TRAVEL, ";");
			while (tok.hasMoreTokens()) {
				String str = tok.nextToken();
				if (str.contains("$" + schemeCode)) {
					tariffCode = Integer
							.valueOf(str.substring(1, str.indexOf("-")));
					break;
				}
			}
		}
		return tariffCode;
		
	}
}
