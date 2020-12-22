/*
 * 
 */
package com.rsaame.pas.b2b.ws.handler;

import java.util.Calendar;
import java.util.List;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.b2b.ws.util.WSDAOUtils;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.TaskVO;

public class WSReferralHandler {
	private static final Logger LOGGER = Logger.getLogger(WSReferralHandler.class);

	public TaskVO map(TaskVO taskVO, PolicyVO policyVO, String taskType) {
		LOGGER.info("Enter in Task Map method");
		Calendar currentDate = Calendar.getInstance();
		try {
			taskVO.setAssignedTo(String.valueOf(WSDAOUtils.defaultAppover(policyVO)));
			policyVO = referralDescription(policyVO);
			taskVO.setAssignedBy(((UserProfile) policyVO.getLoggedInUser()).getRsaUser().getUserId().toString());
			taskVO.setCategory(Utils.getSingleValueAppConfig("TASK_REFERRAL_CATEGORY"));
			taskVO.setCreatedBy(((UserProfile) policyVO.getLoggedInUser()).getRsaUser().getUserId().toString());
			taskVO.setCreatedOn(policyVO.getCreatedOn());
			if (taskType.equalsIgnoreCase("onDemand")) {
				// Commented by Supreetha
				// taskVO.setDesc(request.getParameter("taskDescription"));
			} else {
				taskVO.setDesc(policyVO.getConCatRefMsgs());
			}
			taskVO.setCreatedDate(currentDate.getTime());

			currentDate.add(Calendar.DAY_OF_MONTH, 30);
			taskVO.setDueDate(currentDate.getTime());

			taskVO.setLocation(String.valueOf(policyVO.getGeneralInfo().getAdditionalInfo().getIssueLoc()));
			taskVO.setLoggedInUser((policyVO.getLoggedInUser()));
			taskVO.setPolicyType(Utils.getSingleValueAppConfig("POLICY_TYPES"));

			taskVO.setPolEndId(WSDAOUtils.getLatestEndtId(policyVO));

			taskVO.setPolLinkingId(policyVO.getPolLinkingId());

			if (!Utils.isEmpty(policyVO.getPolicyNo())) {
				taskVO.setPolicyNo(policyVO.getPolicyNo());
			}
			taskVO.setQuoteNo(policyVO.getQuoteNo());

			taskVO.setPriority(Utils.getSingleValueAppConfig("TASK_DEFAULT_PRIORITY"));
			taskVO.setStatus(Utils.getSingleValueAppConfig("TASK_DEFAULT_STATUS"));

			if (!policyVO.getIsQuote()) {
				taskVO.setTaskName(policyVO.getPolicyNo() + " is referred");
			} else {
				taskVO.setTaskName(policyVO.getQuoteNo() + " is referred");
			}

			taskVO.setQuote(policyVO.getIsQuote());

			if ((policyVO.getAppFlow() == Flow.AMEND_POL)) {
				taskVO.setTaskType(Utils.getSingleValueAppConfig("TASK_TRAN_TYPE_ENDORSEMENT"));

				/*
				 * Below changes has been made to set endorsementId in TaskVO.
				 * Earlier we were setting endorsementId as NewEndtId from
				 * PolicyVO. Now , we are checking if there is any record exist
				 * for current endorsement, then only set the endorsementId in
				 * TaskVO as NewEndtId from PolicyVO else set it as EndtId from
				 * PolicyVO. It is required if user goes for an endorsement but
				 * does not change anything
				 */
				DataHolderVO<Boolean> output = (DataHolderVO<Boolean>) TaskExecutor
						.executeTasks("IS_ENDORSEMENT_RECORD_EXIST", policyVO);
				Boolean isEndorsementRecordExist = false;
				if (!Utils.isEmpty(output)) {
					isEndorsementRecordExist = output.getData();
				}
				if (isEndorsementRecordExist)
					taskVO.setPolEndId(policyVO.getNewEndtId());
				else
					taskVO.setPolEndId(policyVO.getEndtId());
			} else
				taskVO.setTaskType(Utils.getSingleValueAppConfig("TASK_TRAN_TYPE_QUOTE"));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Exception in Task Map method" + e);
		}
		return taskVO;
	}

	public PolicyVO referralDescription(PolicyVO policyVO) {
		LOGGER.info("Enter  referralDescription method");
		ReferralVO referalVO = new ReferralVO();
		referalVO.setPolLinkingId(policyVO.getPolLinkingId());

		ReferralListVO referralListVO = (ReferralListVO) TaskExecutor.executeTasks("PREMIUM_PAGE", referalVO);
		try {
			if (!Utils.isEmpty(referralListVO) && !Utils.isEmpty(referralListVO.getReferrals())) {
				String consolidatedReferralMessage = "";

				StringBuffer consolidatedReferralMessageBuffer = new StringBuffer();
				// Iterating all the referrals to get consolidated message
				List<ReferralVO> referralVOs = referralListVO.getReferrals();
				for (ReferralVO voTemp : referralVOs) {
					if (!Utils.isEmpty(voTemp)) {
						consolidatedReferralMessage = consolidatedReferralMessageBuffer.append(voTemp.getSectionName())
								.append(" : ").append(voTemp.getReferralText()).append("\n").toString();
					}
				}
				policyVO.setConCatRefMsgs(consolidatedReferralMessage);

			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Enter  referralDescription method" + e);
		}
		return policyVO;
	}

}
