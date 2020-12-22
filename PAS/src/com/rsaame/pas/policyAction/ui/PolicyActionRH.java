/**
 * 
 */
package com.rsaame.pas.policyAction.ui;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.rules.mapper.RulesConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.app.RuleContext;
import com.rsaame.pas.vo.bus.CommentsVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;

/**
 * @author m1014644
 * 
 */
public class PolicyActionRH implements IRequestHandler {

	private static final String SPACE = " ";
	DecimalFormat decForm = new DecimalFormat(RulesConstants.DECIMAL_FORMAT);


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) {
		Response responseObj = new Response();
		String action = request.getParameter("action");
		CommentsVO comments = (CommentsVO) request.getSession(false)
				.getAttribute(AppConstants.GET_COMMENTS);
		PolicyCommentsHolder polComHolder = new PolicyCommentsHolder();
		PolicyContext policyContext = PolicyContextUtil
				.getPolicyContext(request);
		PolicyVO policyVO = policyContext.getPolicyDetails();

		Integer licensedBy = null;

		String filterReferral = request.getParameter("filterRef");

		// TODO: Need to confirm from DB whether has to be based on Policy id or
		// Linking Id

		/*
		 * Map values from premium page like govt tax, policy fees etc to
		 * PremiumVO.
		 */
		if (!Utils.isEmpty(policyVO.getPremiumVO())) {
			BeanMapper.map(request, policyVO.getPremiumVO());
		}

		if (action.equals(com.Constant.CONST_ISSUE_QUOTE) || action.equals(com.Constant.CONST_APPROVE_QUOTE)
				|| action.equals(com.Constant.CONST_APPROVE_POLICY)) {
			mapTradeLicNo(request, policyVO);
		}

		UserProfile userProfile = (UserProfile) request.getSession()
				.getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
		if (!Utils.isEmpty(userProfile)) {
			policyVO.setLoggedInUser(userProfile);
		}
		
		
		//added to fix ticket #129210
	   TaskExecutor.executeTasks("BROKER_ACC_STATUS_VAL", policyVO);
		

		String message = "";
	/*Added appflow condition VIEW_QUO, to call the rule when the quote is in view mode ticket 158315*/
	if (policyVO.getIsQuote()
				&& (policyVO.getAppFlow().equals(Flow.CREATE_QUO)
						|| policyVO.getAppFlow().equals(Flow.EDIT_QUO) || policyVO
						.getAppFlow().equals(Flow.RESOLVE_REFERAL) || policyVO
                        .getAppFlow().equals(Flow.VIEW_QUO) )) {
		    
			policyVO.setRuleContext(null);
			if (!SectionRHUtils.executeReferralTask(responseObj,
					"DISCOUNT_LOADING", policyVO, "DISCOUNT_LOADING")) {
				if (AppUtils.isDiscountRuleRequired(policyVO)) {
					message = Utils.getSingleValueAppConfig(
							"discountOrLoadPercentage",
							"Your role does not allow a Discount/Loading of ")
							+ SPACE
							+ policyVO.getPremiumVO().getDiscOrLoadPerc() + "%";
					AppUtils.addToRequestErrorMessagesMap(request,
							"pas.discFail", message);
					return responseObj;
				}
			}
			
			
			/*
			 * Ticket : 164420 Start Added condition for JLT for cumulative loss
			 * quantum
			 */
			if (!policyVO.getAppFlow().equals(Flow.RESOLVE_REFERAL)) {
				if (!userProfile.getRsaUser().getHighestRole().equalsIgnoreCase("RSA_USER_3")
						&& !userProfile.getRsaUser().getHighestRole().equalsIgnoreCase("RSA_USER_2")) {
					String schemeCode = "";
					Date preparedDate = new Date();
					if (!Utils.isEmpty(policyVO.getScheme())) {
						schemeCode = policyVO.getScheme().getSchemeCode().toString();
					}
					if (!Utils.isEmpty(policyVO.getCreated())) {
						preparedDate = policyVO.getCreated();
					}
					SimpleDateFormat s2 = new SimpleDateFormat(com.Constant.CONST_DD_MM_YYYY);
					String d2 = Utils.getSingleValueAppConfig("JLT_LiveDate");
					Date JLTLiveDate = null;
					try {
						JLTLiveDate = s2.parse(d2);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Date date = new Date();
					SimpleDateFormat s3 = new SimpleDateFormat(com.Constant.CONST_DD_MM_YYYY);
					Date modifiedDate = new Date();

					String strDateFormat = com.Constant.CONST_DD_MM_YYYY;
					DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
					try {
						modifiedDate = s3.parse(dateFormat.format(date));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if (Utils.getSingleValueAppConfig("JLT_SchemeCode").equals(schemeCode)
							&& (JLTLiveDate.compareTo(preparedDate) <= 0 || JLTLiveDate.compareTo(modifiedDate) <= 0)
							&& policyVO.getIsQuote() && SvcConstants.DUBAI == Integer
									.parseInt(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION))) {

						BigDecimal lossExpQuantum = policyVO.getGeneralInfo().getClaimsHistory().getLossExpQuantum();
						double premiumAmt = SvcUtils.getTotalPremium(policyVO) * 100.0;
						double premiumAmount = 0.0;
						double lossExpQuantumValue = ((lossExpQuantum.doubleValue() / 3) * 0.25);

						Double payablePrm = (Double) request.getSession().getAttribute("PREMIUM_TOTAL");
						StringBuffer stringBuffer = new StringBuffer();

						if (payablePrm > 0) {
							premiumAmount = payablePrm;
						} else {
							premiumAmount = Double.valueOf(decForm.format(Math.round(premiumAmt) / 100.0));
						}

						if (lossExpQuantumValue > premiumAmount) {
							policyVO.setRuleContext(RuleContext.CUMULATIVE_LOSS_QUANTUM);

							if (!SectionRHUtils.executeReferralTask(responseObj, "CUMULATIVE_lOSS_QUANTUM", policyVO,
									"CUMULATIVE_lOSS_QUANTUM")) {
								ReferralListVO referralListVO = (ReferralListVO) responseObj.getData();
								if (!Utils.isEmpty(referralListVO) && !Utils.isEmpty(referralListVO.getReferrals())) {
									String consolidatedReferralMessage = "";
									List<ReferralVO> referralVOs = referralListVO.getReferrals();
									for (ReferralVO voTemp : referralVOs) {
										if (!Utils.isEmpty(voTemp)) {
											voTemp.getReferralText().clear();
											voTemp.getReferralText()
													.add("Your role does not allow to have cumulative loss of quantum "
															+ (Currency.getFormattedCurrency(lossExpQuantumValue,
																	"SBS"))
															+ " is greather than premium " + premiumAmount);
											consolidatedReferralMessage = stringBuffer.append(voTemp.getReferralText())
													.append("\n").toString();

										}
									}

									policyContext.getPolicyDetails().setConCatRefMsgs(consolidatedReferralMessage);
									boolean isMessage = true;

									if (isMessage) {
										response.setHeader("isMessage", "true");
									}

									response.setHeader("referral", "true");
								}

								return responseObj;

							}

							if (!Utils.isEmpty(comments)) {
								polComHolder.setComments(comments);
							}
							polComHolder.setPolicyDetails(policyVO);
							polComHolder = (PolicyCommentsHolder) TaskExecutor.executeTasks(action, polComHolder);
							request.getSession(false).removeAttribute(AppConstants.GET_COMMENTS);

							if (action.equals(com.Constant.CONST_ISSUE_QUOTE)) {
								request.getSession().removeAttribute("premiumSectionId");
							}

						}
					}
				}
			}
			/*
			 * Ticket : 164420 Start Added condition for JLT for cumulative loss
			 * quantum
			 */
			
			

			policyVO.setRuleContext(RuleContext.QUOTE_PREMIUM);
			if (!SectionRHUtils.executeReferralTask(responseObj,
					"QUOTE_PREMIUM", policyVO, "QUOTE_PREMIUM")) {

				message = Utils
						.getSingleValueAppConfig("quotePremium",
								"Your role does not allowed to have Quote Premium of %VAR%");
				message = message.replaceAll("%VAR%", Currency
						.getFormattedCurrency(
								SvcUtils.getTotalPremium(policyVO),
								policyContext.getLOB().name()));

				AppUtils.addToRequestErrorMessagesMap(request,
						"pas.quotePremiumFail", message);
				return responseObj;
			}

		}

		/* code to trigger the credit limit rule during the approve quote */
		if ((action.equals(com.Constant.CONST_APPROVE_POLICY) || action.equals(com.Constant.CONST_APPROVE_QUOTE))
				&& Utils.isEmpty(filterReferral)) {

			if (!Utils.isEmpty(policyVO)
					&& !Utils.isEmpty(policyVO.getAuthInfoVO())) {
				licensedBy = policyVO.getAuthInfoVO().getLicensedBy();
			}
			/*
			 * Filter the rule if the licensed by selected is the CREDIT_MANAGER
			 * role
			 */
			if (!AppUtils.checkCreditLimitRule(licensedBy)) {
				if (Utils.getSingleValueAppConfig("ISCREDITCHKRULEREQ")
						.equalsIgnoreCase("YES")
						&& !SectionRHUtils.executeReferralTask(responseObj,
								"CONV_TO_POLICY_REF", policyVO,
								"CONV_TO_POLICY_REF")) {
					ReferralListVO referralListVO = (ReferralListVO) responseObj
							.getData();
					if (!Utils.isEmpty(referralListVO)
							&& !Utils.isEmpty(referralListVO.getReferrals())) {

						/*
						 * Added string buffer to avoid "+" for sonar violation
						 * on 13-9-2017
						 */

						 String consolidatedReferralMessage = "";
						StringBuffer stringBuffer = new StringBuffer();
						// Iterating all the referrals to get consolidated
						// message
						List<ReferralVO> referralVOs = referralListVO
								.getReferrals();
						for (ReferralVO voTemp : referralVOs) {
							if (!Utils.isEmpty(voTemp)) {
								// consolidatedReferralMessage +=
								// voTemp.getReferralText() + "\n";
								consolidatedReferralMessage = stringBuffer.append(voTemp
										.getReferralText()).append("\n").toString();

							}
						}
						policyContext.getPolicyDetails().setConCatRefMsgs(
								consolidatedReferralMessage);
						boolean isMessage = referralListVO.getReferrals()
								.get(0).isMessage();

						if (isMessage) {
							response.setHeader("isMessage", "true");
						}

						response.setHeader("referral", "true");
					}

					return responseObj;
				}
			}
		}

		if (!Utils.isEmpty(comments)) {
			polComHolder.setComments(comments);
		}
		polComHolder.setPolicyDetails(policyVO);
		polComHolder = (PolicyCommentsHolder) TaskExecutor.executeTasks(action,
				polComHolder);
		request.getSession(false).removeAttribute(AppConstants.GET_COMMENTS);

		if (action.equals(com.Constant.CONST_ISSUE_QUOTE)) {
			request.getSession().removeAttribute("premiumSectionId");
		}

		if (action.equals(com.Constant.CONST_APPROVE_QUOTE)) {
			message = "pas.approveQuoteSuccessful";
			policyContext.getPolicyDetails().setStatus(
					AppConstants.QUOTE_ACCEPT);
			TaskExecutor.executeTasks("UPDATE_REFERRAL_DETAILS_DISC", policyVO);

			AppUtils.sendMail(policyVO, "APPROVE");

			if (!Utils.isEmpty(policyVO.getTaskDetails())
					&& !Utils.isEmpty(policyVO.getTaskDetails().getDesc())
					&& policyVO
							.getTaskDetails()
							.getDesc()
							.contains(
									Utils.getSingleValueAppConfig(com.Constant.CONST_BROKER_CREDIT_LIMIT_APPROVAL))) {
				AppUtils.sendCreditLimitMail(policyVO, "APPROVE_CREDIT_LIMIT",
						request);
			}
		} else if (action.equals(com.Constant.CONST_ISSUE_QUOTE)) {
			// message="pas.issueQuoteSuccessful";
			if (!Utils.isEmpty(policyVO.getQuoteNo())) {
				String quoteIssued = "Quote number "
						+ policyVO.getQuoteNo().toString()
						+ " issued successfully.";
				AppUtils.addToRequestErrorMessagesMap(request,
						"pas.QuoteIssued", quoteIssued);
			} else {
				message = "pas.issueQuoteSuccessful";
			}

			Double payablePrm = (Double) request.getSession().getAttribute(
					"PAYABLE_PREMIUM_TOTAL");
			// payablePrm = payablePrm - (payablePrm *
			// policyVO.getPremiumVO().getDiscOrLoadPerc() /100);
			request.getSession(false).setAttribute("PAYABLE_PREMIUM",
					payablePrm);

			/*
			 * set attribute to know where the convert to policy button to be
			 * enabled.
			 */

			request.setAttribute("CONV_POL_ENABLE", "true");

			policyContext.getPolicyDetails().setStatus(
					AppConstants.QUOTE_ACTIVE);
			policyContext.getPolicyDetails().setAppFlow(Flow.VIEW_QUO);
			policyContext.getPolicyDetails().setEndtId(
					polComHolder.getPolicyDetails().getEndtId());
			policyContext.getPolicyDetails().setNewEndtId(
					polComHolder.getPolicyDetails().getNewEndtId());
			policyContext
					.getPolicyDetails()
					.getAuthInfoVO()
					.setAccountingDate(
							polComHolder.getPolicyDetails().getAuthInfoVO()
									.getAccountingDate());
			/*
			 * After the quote is issued, the endt id's are swapped.
			 */
			if (!Utils.isEmpty(policyContext.getPolicyDetails().getEndtId())
					&& !Utils.isEmpty(policyContext.getPolicyDetails()
							.getNewEndtId())) {
				policyContext.getPolicyDetails().setEndtId(
						policyContext.getPolicyDetails().getNewEndtId());
			}
			policyContext.getPolicyDetails().setNewEndtId(null);

		} else if (action.equals("REJECT_QUOTE")) {
			policyContext.getPolicyDetails().setStatus(
					AppConstants.QUOTE_REJECT);
			message = "pas.rejectQuoteSuccessful";
		} else if (action.equals("DECLINE_QUOTE")) {
			policyContext.getPolicyDetails().setStatus(
					AppConstants.QUOTE_DECLINED);
			message = "pas.declineQuoteSuccessful";
			AppUtils.sendMail(policyVO, "DECLINE");
		} else if (action.equals("SAVE_ENDORSMENT_TEXT")) {
			message = "pas.endorsementTextSuccessful";
		} else if (action.equals("CANCEL_ENDORSEMENT")) {
			message = "pas.cancelEndorsement";
		} else if (action.equals(com.Constant.CONST_APPROVE_POLICY)) {
			/*
			 * set appflow to view policy after approve policy is done and set
			 * status to quote accepted.
			 */
			policyContext.getPolicyDetails().setStatus(
					AppConstants.QUOTE_ACCEPT);
			policyContext.getPolicyDetails().setAppFlow(Flow.VIEW_POL);
			AppUtils.sendMail(policyVO, "APPROVE");

			if (!Utils.isEmpty(policyVO.getTaskDetails())
					&& !Utils.isEmpty(policyVO.getTaskDetails().getDesc())
					&& policyVO
							.getTaskDetails()
							.getDesc()
							.contains(
									Utils.getSingleValueAppConfig(com.Constant.CONST_BROKER_CREDIT_LIMIT_APPROVAL))) {
				AppUtils.sendCreditLimitMail(policyVO, "APPROVE_CREDIT_LIMIT",
						request);
			}

			message = "pas.approvePolicySuccessful";
		} else if (action.equals("DECLINE_POLICY")) {
			policyContext.getPolicyDetails().setStatus(
					AppConstants.QUOTE_DECLINED);
			policyContext.getPolicyDetails().setAppFlow(Flow.VIEW_POL);
			AppUtils.sendMail(policyVO, "DECLINE");
			message = "pas.declinePolicySuccessful";
		} else if (action.equals("CANCEL_POLICY")) {
			policyContext.getPolicyDetails().setAppFlow(Flow.VIEW_POL);
			responseObj.setData(policyVO);
		} else if (action.equals("ENDT_CONFIRMED")) {
			/*
			 * In case of endorsement confirmation, make the page read only and
			 * sent the status as active in PolicyVO.
			 */
			policyContext.getPolicyDetails().setStatus(
					AppConstants.QUOTE_ACTIVE);
			policyContext.getPolicyDetails().setAppFlow(Flow.VIEW_POL);
			message = "pas.endorsementSuccessMsg";
		}

		if (!Utils.isEmpty(filterReferral)) {
			if (!Utils.isEmpty(policyVO.getTaskDetails())
					&& !Utils.isEmpty(policyVO.getTaskDetails().getDesc())
					&& policyVO
							.getTaskDetails()
							.getDesc()
							.contains(
									Utils.getSingleValueAppConfig(com.Constant.CONST_BROKER_CREDIT_LIMIT_APPROVAL))) {
				AppUtils.sendCreditLimitMail(policyVO, "MESSAGE_CREDIT_LIMIT",
						request);
			}
		}

		AppUtils.addErrorMessage(request, message);
		return responseObj;
	}

	private void mapTradeLicNo(HttpServletRequest request, PolicyVO policyVO) {

		/*
		 * Mapping: "quote_name_tradelicno" ->
		 * "generalInfo.insured.tradeLicenseNo"
		 */
		if (!Utils.isEmpty(request.getParameter("quote_name_tradelicno"))) {
			policyVO.getGeneralInfo()
					.getInsured()
					.setTradeLicenseNo(
							request.getParameter("quote_name_tradelicno"));
		} else {
			policyVO.getGeneralInfo().getInsured().setTradeLicenseNo(null);
		}

	}

}
