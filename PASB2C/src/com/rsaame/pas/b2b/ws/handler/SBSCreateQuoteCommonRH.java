package com.rsaame.pas.b2b.ws.handler;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2b.ws.dao.vo.EplatformWsStaging;
import com.rsaame.pas.b2b.ws.dao.vo.EplatformWsStagingId;
import com.rsaame.pas.b2b.ws.util.WSAppUtils;
import com.rsaame.pas.b2b.ws.util.WSDAOUtils;
import com.rsaame.pas.b2b.ws.vo.CreateSBSQuoteRequest;
import com.rsaame.pas.b2b.ws.vo.CreateSBSQuoteResponse;
import com.rsaame.pas.b2b.ws.vo.RetrieveSBSQuoteResponse;
import com.rsaame.pas.b2c.WsAuthentication.BasicAuthenticationService;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.AuthenticationInfoVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;

public class SBSCreateQuoteCommonRH {

	private static final Logger LOGGER = Logger.getLogger(SBSCreateQuoteCommonRH.class);
	private static String SAVE_OPERATION_OP_TYPE = "QUOTE_SAVE_INVSVC";

	public PolicyVO executeCommonHandler(PolicyVO policyVo) {

		LOGGER.info("Entered executeCommonHandler() method");
		SaveQuoteHandler saveQuoteHandler = new SaveQuoteHandler();
		if (policyVo.getAppFlow().equals(Flow.CREATE_QUO) || policyVo.getAppFlow().equals(Flow.EDIT_QUO)) {
			policyVo = saveQuoteHandler.checkIfInsuredExists(policyVo);

			if (policyVo.getQuoteNo() != null) {

				if (!Utils.isEmpty(policyVo) && Utils.isEmpty(policyVo.getPolicyNo())) {
					policyVo.setPolicyNo(Long.valueOf(0));
				}

				/*
				 * To check if EndtId is populated from screen if not then
				 * default populate it to 0 for quote creation process
				 */
				if (Utils.isEmpty(policyVo.getEndtId())) {
					policyVo.setEndtId(Long.valueOf("0"));

				}

				if (!policyVo.getGeneralInfo().getInsured().getUpdateMaster()) {
					policyVo.getGeneralInfo().getInsured().setUpdateMaster(true);

				}

				if (policyVo.getAppFlow().equals(Flow.CREATE_QUO)) {
					policyVo = (PolicyVO) TaskExecutor.executeTasks(SAVE_OPERATION_OP_TYPE, policyVo);
				}
				
				//Added for JLT
	              String comment=null;
	              boolean isValidSceheme= SvcUtils.isValidSchemeCode(policyVo);
	              if (isValidSceheme
	                           && SvcConstants.DUBAI == Integer
	                                         .parseInt(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION))) {
	                   //  logger.info("Enter Saving remarks for JLT");
	                     if(!Utils.isEmpty( policyVo) && !Utils.isEmpty( policyVo.getGeneralInfo().getAdditionalInfo().getRemarks()) ) {
	                           comment = policyVo.getGeneralInfo().getAdditionalInfo().getRemarks();
	                           DAOUtils.storeComments(policyVo,comment);
	                     }
	                     
	                  //   logger.info("Exit Saving remarks for JLT");
	                     
	              }


				RuleHandler ruleHandler = new RuleHandler();
				RatingHandler ratingHandler = new RatingHandler();

				boolean rulePassed = ruleHandler.callRulesForAllSection(policyVo);
				LOGGER.debug("General page and All Section rules passed ?::::" + rulePassed);

				policyVo = (PolicyVO) ratingHandler.invokeRating(policyVo);

				rulePassed = ruleHandler.callRuleForIssueQuote(policyVo);
				LOGGER.debug("Issue quote Rule Passed ?::::" + rulePassed);

				ruleHandler.checkReferrals(policyVo);
//				if (!Utils.isEmpty(policyVo) && !Utils.isEmpty(policyVo.getPremiumVO())) {
//					saveQuoteHandler.calculateVatAndGovtTaxAmount(policyVo);
//				}

				if (!Utils.isEmpty(policyVo.getMapReferralVO())) {
					// Saving in T_TRN_TEMP_PAS_REFERRAL
					saveQuoteHandler.insertReferal(policyVo);
					policyVo = saveQuoteHandler.saveRefTskDetails(policyVo);
					policyVo.setStatus(Integer.parseInt(Utils.getSingleValueAppConfig("POLICY_REFERRED")));
				}
			}
		}
		return policyVo;

	}
	
	public void executeWSDBHandler(PolicyVO policyVO,CreateSBSQuoteRequest createSBSQuoteRequest, CreateSBSQuoteResponse createSBSQuoteResponse , long twa_id) {
		
		StagingTableDBHandler stagingTableDBHandler = new StagingTableDBHandler();
		stagingTableDBHandler.executeStagingTableHandler(policyVO,createSBSQuoteRequest,createSBSQuoteResponse , twa_id);
		//stagingTableDBHandler.getSBSQuoteFromStagingTable("2448093");
        
	}

	
	
	
	public PolicyVO getQuoteDeatils(PolicyVO policyVO) {
		try {

			List<TTrnPolicyQuo> policy = WSDAOUtils.getPolicyRecord(policyVO);

			int polStatus = policy.get(0).getPolStatus();
			// To check quote status
			if (!Utils.isEmpty(policy)
					&& (polStatus != Integer.parseInt(Utils.getSingleValueAppConfig("POLICY_REFERRED")))) {

				List<EplatformWsStaging> stagings = WSDAOUtils.getPolicyRecordFromStaging(policyVO.getQuoteNo());

				if (stagings.size() > 0) {
					if (policy.get(0).getId().getPolEndtId() > stagings.get(0).getId().getPolEndtId()) {
						// Eplatfrom trans table
						policyVO.setPolLinkingId(policy.get(0).getPolLinkingId());
						policyVO.setEndtId(policy.get(0).getId().getPolEndtId());
						policyVO.setEndtNo(policy.get(0).getPolEndtNo());
						if (policy.get(0).getPolStatus() == 6) {
							if (!Utils.isEmpty(policy.get(0).getId().getPolEndtId())) {
								policyVO.setNewEndtId(policy.get(0).getId().getPolEndtId());
							}
							if (policy.get(0).getPolValidityStartDate() != null) {
								policyVO.setNewValidityStartDate(policy.get(0).getPolValidityStartDate());
							}
						}
					} else {
						// staging table

						policyVO.setPolLinkingId(stagings.get(0).getPolLinkingId());
						policyVO.setEndtId(stagings.get(0).getId().getPolEndtId());
						policyVO.setEndtNo(stagings.get(0).getPolEndtNo());
						if (stagings.get(0).getPolStatus() == 6) {
							if (!Utils.isEmpty(stagings.get(0).getId().getPolEndtId())) {
								policyVO.setNewEndtId(stagings.get(0).getId().getPolEndtId());
							}
							if (policy.get(0).getPolValidityStartDate() != null) {
								policyVO.setNewValidityStartDate(policy.get(0).getPolValidityStartDate());
							}
						}
					}

				} else {
					policyVO.setPolLinkingId(policy.get(0).getPolLinkingId());
					policyVO.setEndtId(policy.get(0).getId().getPolEndtId());
					policyVO.setEndtNo(policy.get(0).getPolEndtNo());
					if (policy.get(0).getPolStatus() == 6) {
						if (!Utils.isEmpty(policy.get(0).getId().getPolEndtId())) {
							policyVO.setNewEndtId(policy.get(0).getId().getPolEndtId());
						}
						if (policy.get(0).getPolValidityStartDate() != null) {
							policyVO.setNewValidityStartDate(policy.get(0).getPolValidityStartDate());
						}
					}
				}

				policyVO = WSDAOUtils.getBaseSecPolicyId(policyVO);
				policyVO.setIsQuote(true);
				policyVO.setStatus(Integer.parseInt(Utils.getSingleValueAppConfig("POLICY_PENDING")));

			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("getQuoteDeatils :_1" + e);
		}
		return policyVO;

	}

	public PolicyVO getQuoteStatus(PolicyVO policyVO) {
		try {
			List<TTrnPolicyQuo> policy = WSDAOUtils.getPolicyRecord(policyVO);
			if(!Utils.isEmpty(policy)) {
				policyVO.setStatus((int) policy.get(0).getPolStatus());
				policyVO.setCreatedBy(String.valueOf(policy.get(0).getPreparedBy())); 
				policyVO.setPolLinkingId((long) policy.get(0).getPolLinkingId()); //added to provosion quote edit for referred quotes.
				policyVO.setEndtId((long) policy.get(0).getId().getPolEndtId());//added to provosion quote edit for referred quotes.
				policyVO.setBasePolicyId((long) policy.get(0).getId().getPolPolicyId());
				
				/*
				 * Modified for JLT API Renewal scope #11424
				 */
				if(!Utils.isEmpty(policyVO.getAuthInfoVO())) {
					if(!Utils.isEmpty(policy.get(0).getPolDocumentCode()))
						policyVO.getAuthInfoVO().setTxnType((int) policy.get(0).getPolDocumentCode());
				}else {
					policyVO.setAuthInfoVO(new AuthenticationInfoVO());
					if(!Utils.isEmpty(policy.get(0).getPolDocumentCode()))
						policyVO.getAuthInfoVO().setTxnType((int) policy.get(0).getPolDocumentCode());
				}
				if(!Utils.isEmpty(policy.get(0).getPolRefPolicyNo()))
					policyVO.getAuthInfoVO().setRefPolicyNo(policy.get(0).getPolRefPolicyNo());
				if(!Utils.isEmpty(policy.get(0).getPolRenTermTxt()))
					policyVO.getAuthInfoVO().setRenewalTerms(policy.get(0).getPolRenTermTxt());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("getQuoteDeatils :_2" + e);
		}
		return policyVO;

	}

	public PolicyVO getBaseSecPolicyId(PolicyVO policyVO) {

		try {
			policyVO = WSDAOUtils.getBaseSecPolicyId(policyVO);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("getQuoteDeatils :_3" + e);
		}
		return policyVO;
	}

	public List<TTrnPolicyQuo> getPolicyRecord(PolicyVO policyVO) {
		List<TTrnPolicyQuo> policy = null;
		try {
			policy = WSDAOUtils.getPolicyRecord(policyVO);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("getQuoteDeatils :_4" + e);
		}
		return policy;
	}

	public List<EplatformWsStaging> getPolicyRecordFromStaging(Long quoteNo) {
		List<EplatformWsStaging> staging = null;
		try {
			staging = WSDAOUtils.getPolicyRecordFromStaging(quoteNo);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("getQuoteDeatils :_5" + e);
		}

		return staging;
	}
	public TTrnPolicyQuo getRenewalQuoteByPolicyNo(PolicyVO policyVO) {
		List<TTrnPolicyQuo> policyQuos = null;
		TTrnPolicyQuo policyQuo = new TTrnPolicyQuo();
		try {
			policyQuos = WSDAOUtils.getRenewalQuoteFromPolicyNo(policyVO);
			if(!Utils.isEmpty(policyQuos)) {
				policyQuo=policyQuos.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Exception Occured While Getting Renewal quote" + e);
		}
		return policyQuo;
	}
	public void fectchUserId(PolicyVO policyVO, HttpServletRequest request) { 
		// To fetch userId from authentication
		String authorization = request.getHeader(Utils.getSingleValueAppConfig("AUTHORIZATION"));
		BasicAuthenticationService authenticationService = new BasicAuthenticationService();
		String userId = null;
	
		try {
			String s = authenticationService.decodeText(authorization);
			String[] credentials = authenticationService.getUserIdAndPassword(s);
			userId = credentials[0];
			
			if (Utils.isEmpty(policyVO.getLoggedInUser())) {
				UserProfile userProfile = WSAppUtils.getWSUserProfileVo(userId);
				request.getSession().setAttribute(AppConstants.SESSION_USER_PROFILE_VO, userProfile);
				policyVO.setLoggedInUser(userProfile);
				policyVO.getLoggedInUser().setUserId(userId);
			}
			
			policyVO.getLoggedInUser().setUserId(userId);
			LOGGER.info(credentials[0]);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Exception while decoding authentication :" + e);
		}
	}

	public void fectchDefaultValues(PolicyVO policyVO, HttpServletRequest request) {
		// To fetch default values from table
		String partnerName = request.getHeader(Utils.getSingleValueAppConfig("PARTNERID"));
		if(Utils.isEmpty(policyVO.getScheme())) {
			policyVO.setScheme(new SchemeVO());
		}
		
		if(Utils.isEmpty(policyVO.getGeneralInfo())) {
			policyVO.setGeneralInfo(new GeneralInfoVO());
		}
		if(Utils.isEmpty(policyVO.getGeneralInfo().getSourceOfBus())) {
			policyVO.getGeneralInfo().setSourceOfBus(new SourceOfBusinessVO());
		}
		try {
			Object[] values = (Object[]) DAOUtils.fetchDefaultValues(partnerName);
			policyVO.getScheme().setSchemeCode(((BigDecimal) values[3]).intValue());
			policyVO.getScheme().setId(((BigDecimal) values[3]).intValue());
			policyVO.getScheme().setTariffCode(((BigDecimal) values[4]).intValue());
			policyVO.getGeneralInfo().getSourceOfBus().setPartnerId((values[7]).toString());
			policyVO.getGeneralInfo().getSourceOfBus().setBrokerName(((BigDecimal) values[7]).intValue());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Exception while fetching default values :" + e);
		}
	}
}
