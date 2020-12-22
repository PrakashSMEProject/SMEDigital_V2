package com.rsaame.pas.b2b.ws.handler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.b2b.ws.constant.WSAppContants;
import com.rsaame.pas.b2b.ws.util.WSBusinessValidatorUtils;
import com.rsaame.pas.b2b.ws.util.WSDAOUtils;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.dao.cmn.TempPasReferralDAO;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.app.PremiumSummary;
import com.rsaame.pas.vo.bus.CommentsVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralLocKey;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.TaskVO;

public class SaveQuoteHandler {

	private final static Logger LOGGER = Logger.getLogger(SaveQuoteHandler.class);

	public PolicyVO checkIfInsuredExists(PolicyVO policyVo) {
		LOGGER.info("Entered checkIfInsuredExists method");
		try {

			policyVo = WSDAOUtils.checkIfInsuredExists(policyVo, policyVo.getGeneralInfo().getInsured().getName(),
					policyVo.getGeneralInfo().getInsured().getAddress().getPoBox(),
					Integer.valueOf(policyVo.getGeneralInfo().getSourceOfBus().getPartnerId()));

		} catch (Exception e) {
			LOGGER.info("Exception in checkIfInsuredExists method");
			e.printStackTrace();
		}

		return policyVo;
	}

	public PolicyVO calculateVatAndGovtTaxAmount(PolicyVO policyVO) {
		LOGGER.info("Entered calculateVatAndGovtTaxAmount method");
		try {
			double premiumAmount = 0.0;
			BigDecimal govtTax = null;
			double totalPremium = 0.0;
			double vatAmount = 0.0;
			policyVO.setPolVatRate(DAOUtils.getVATRateSBS(policyVO.getPolVATCode(), null));
			Map<String, String> vatAmtAndDays = new HashMap<String, String>();

			if (!Utils.isEmpty(policyVO) && !Utils.isEmpty(policyVO.getPremiumVO())) {

				vatAmtAndDays = SvcUtils.calculateSBSVatTaxAmountJSP(policyVO, policyVO.getPremiumVO().getPremiumAmt());

				String vatableAMt = vatAmtAndDays.get("vatbleAmt");
				String vatAmt = vatAmtAndDays.get("vatTax");

				vatAmount = Double.parseDouble(vatAmt);
				policyVO.getPremiumVO().setVatTax(vatAmount);
				policyVO.getPremiumVO().setVatablePrm(Double.parseDouble(vatableAMt));

				PremiumSummary prmSummary = new PremiumSummary();
				prmSummary.getGovtTaxAmt();
				policyVO.getPremiumVO().setPolicyFees(0);
				policyVO.setPolVattableAmt(new BigDecimal(vatableAMt));
				policyVO.setPolVatAmt(new BigDecimal(vatAmt));
				LOGGER.info("Govt Tax : " + policyVO.getPremiumVO().getGovtTax());
				LOGGER.info("Policy Fees : " + policyVO.getPremiumVO().getPolicyFees()) ;

				//Adding two decimal values to the premium
				//premiumAmount = policyVO.getPremiumVO().getPremiumAmt();
				premiumAmount =WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(policyVO.getPremiumVO().getPremiumAmt());
				govtTax = prmSummary.getGovtTaxAmt();
				totalPremium = premiumAmount + vatAmount + govtTax.doubleValue()+prmSummary.getPolicyFee();
				//Handled while setting the value no need to convert
				//policyVO.getPremiumVO().setPremiumAmt( Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(totalPremium), "SBS")));
				policyVO.getPremiumVO().setPremiumAmt( WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(totalPremium));
				LOGGER.info("Payable Premium amount after adding vat and govt tax :" + totalPremium);
			} else {
				return policyVO;
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Exception in calculateVatAndGovtTaxAmount method" + e);

		}

		return policyVO;

	}

	public void insertReferal(PolicyVO policyVo) {
		try {
			if (!Utils.isEmpty(policyVo.getMapReferralVO())) {

				ReferralVO referralVO;
				TempPasReferralDAO insertTempPasReferalDao = (TempPasReferralDAO) Utils.getBean("tempPasReferralDAO");
				for (Entry<ReferralLocKey, ReferralVO> mapRefEntry : policyVo.getMapReferralVO().entrySet()) {
					referralVO = mapRefEntry.getValue();
					referralVO.setPolLinkingId(policyVo.getPolLinkingId());
					referralVO.setRiskGroupId("0");
					insertTempPasReferalDao.insertReferal(referralVO);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Exception in insertReferal method" + e);

		}
	}

	public  PolicyVO saveRefTskDetails(PolicyVO policyVo) {
		// Saving in T_TRN_TASK table
		LOGGER.info("Entered saveRefTskDetails method");
		try {
			Integer sectionId = Integer.valueOf(WSAppContants.SECTION_ID_PREMIUM);
			String svcIdentifier = (String) Utils
					.getSingleValueAppConfig(Utils.concat("SVC_IDENTIFIER_", sectionId.toString()));
			TaskVO taskVO = new TaskVO();

			if (!Utils.isEmpty(policyVo.getLoggedInUser())) {
				taskVO.setLoggedInUser(policyVo.getLoggedInUser());
			}
			taskVO = new WSReferralHandler().map(taskVO, policyVo, "allReferrals");
			TaskExecutor.executeTasks(svcIdentifier, taskVO);
			DataHolderVO<Long> polLinkingId = new DataHolderVO<Long>();
			polLinkingId.setData(policyVo.getPolLinkingId());
			TaskExecutor.executeTasks("UPDATE_REFERRAL_DETAILS", DAOUtils.getRenewalReferralVO(policyVo));

			// Storing comments
			CommentsVO commentsVO = new CommentsVO();
			if (policyVo.getIsQuote())
				commentsVO.setIsQuote(true);

			String comments=null;
			comments = (String) DAOUtils.fetchComments(policyVo);
			commentsVO.setComment(comments +" referrals ");
			
			taskVO.setDesc(taskVO.getDesc().concat("  " + commentsVO.getComment()));

			if (!Utils.isEmpty(policyVo.getLoggedInUser())) {
				commentsVO.setLoggedInUser((policyVo.getLoggedInUser()));
			}
			if (!Utils.isEmpty(policyVo.getPolLinkingId()) && !Utils.isEmpty(policyVo.getEndtId())) {
				commentsVO.setPocPolicyId(policyVo.getPolLinkingId()); // TODO:
																		// need
																		// to
																		// check
																		// with
																		// DB
				commentsVO.setPocEndtId(policyVo.getEndtId());
			}
			commentsVO.setPolicyStatus(Byte.valueOf(Utils.getSingleValueAppConfig("POLICY_REFERRED")));
			PolicyCommentsHolder polComHolder = new PolicyCommentsHolder();
			polComHolder.setComments(commentsVO);
			polComHolder.setPolicyDetails(policyVo);
			polComHolder = (PolicyCommentsHolder) TaskExecutor.executeTasks("STORE_POL_COMMENTS", polComHolder);

			taskVO.setDesc(taskVO.getDesc().concat("&#13;&#10; User Comments : " + commentsVO.getComment()));
			policyVo.setTaskDetails(taskVO);

			/* Commented by Supreetha */
			// WSAppUtils.sendMail( policyVo, "REFERRAL" );

			if (!Utils.isEmpty(policyVo.getTaskDetails()) && !Utils.isEmpty(policyVo.getTaskDetails().getDesc())
					&& policyVo.getTaskDetails().getDesc()
							.contains(Utils.getSingleValueAppConfig("BROKER_CREDIT_LIMIT_APPROVAL"))) {
				// Commented by Supreetha
	//			WSAppUtils.sendCreditLimitMail( policyVo,
	//			 "REFERRAL_CREDIT_LIMIT", request );
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Exception in saveRefTskDetails method" + e);

		}
		return policyVo;
	}


	public void updateRefStatusInTransQuo(PolicyVO policyVO) {
		WSDAOUtils.updateReferralStatus(policyVO);
	}

}
