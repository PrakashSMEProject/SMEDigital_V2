package com.rsaame.pas.b2b.ws.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.kaizen.framework.model.RSAUser;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.b2b.ws.util.WSAppUtils;
import com.rsaame.pas.b2b.ws.vo.CreateSBSPolicyRequest;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.vo.bus.AuthenticationInfoVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PaymentVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/* This mapper is to map the SBS CreatePolicy Request to existing object of PolicyVo.
 * All fields in PolicyVO, PaymentVO and CommonVO needs to be identified and mapped here.
 * Any default value setting should be mapped here.
 * Any value from DB needs to be queried here. 
*/

public class SBSCreatePolicyRequestMapper {

	private final static Logger LOGGER = Logger.getLogger(SBSCreatePolicyRequestMapper.class);
	private static final String SESSION_USER = "USER";

	public void mapRequestToPolicyVO(Object source1, Object target, HttpServletRequest request, HttpSession session, PolicyVO policyVO) {

		LOGGER.info("Entered mapRequestToVO() method");

		if (source1 != null) {

			LOGGER.info("requestObj is NOT null ");
		}
		if (target != null) {

			LOGGER.info("valueObj is NOT null ");
		}

		CreateSBSPolicyRequest createSBSPolicyRequest = (CreateSBSPolicyRequest) source1;
		DataHolderVO<List<BaseVO>> dataHolderVO = (DataHolderVO<List<BaseVO>>) target;
		List<BaseVO> inputVoList = new ArrayList<BaseVO>();

		PaymentVO paymentvo = new PaymentVO();
		CommonVO commonVO = (CommonVO) Utils.getBean("VO_COMMON");

		mappolicyVO(createSBSPolicyRequest, policyVO, request, session);
		inputVoList.add(policyVO);

		mapPaymentVO(createSBSPolicyRequest, paymentvo);
		inputVoList.add(paymentvo);

		PolicyDataVO policyDataVO = new PolicyDataVO();
		mapCommonVO(createSBSPolicyRequest, commonVO, policyDataVO);
		inputVoList.add(policyDataVO.getCommonVO());

		dataHolderVO.setData(inputVoList);
	}

	private void mapCommonVO(CreateSBSPolicyRequest createSBSPolicyRequest, CommonVO commonVO,
			PolicyDataVO policyDataVO) {
		commonVO.setLob(LOB.SBS);
		policyDataVO.setCommonVO(commonVO);
	}

	private void mapPaymentVO(CreateSBSPolicyRequest createSBSPolicyRequest, PaymentVO paymentvo) {
		paymentvo.setPaymentMode(createSBSPolicyRequest.getPayment().getPaymentMode());
		paymentvo.setAmount(createSBSPolicyRequest.getPayment().getPaymentAmount().getAmount());
		paymentvo.setBankCode(23);
		
		if(createSBSPolicyRequest.getPayment().getPaymentMode().equals("CARD")){
			paymentvo.setPaymentDone(true);
			paymentvo.setPayModeCode((byte) 7); //Code=7 for Pas Payment Gateway 
		}
	}

	private void mappolicyVO(CreateSBSPolicyRequest createSBSPolicyRequest, PolicyVO policyVO,
			HttpServletRequest request, HttpSession session) {
		policyVO.setQuoteNo(Long.parseLong(createSBSPolicyRequest.getQuoteId()));

		HibernateTemplate ht = (HibernateTemplate) Utils.getBean("hibernateTemplate");
		Session session1 = ht.getSessionFactory().openSession();
		//Changes-Adv#-10698 -JLT adding pol_issue_hour
		Query query = session1.createSQLQuery(
				"Select distinct Pol_Expiry_Date, Pol_Linking_Id, pol_effective_date, Pol_Customer_id,pol_document_code,POL_EFFECTIVE_DATE,pol_policy_no From T_Trn_Policy_Quo Where  Pol_Quotation_No=:quotationNo and pol_validity_expiry_date = '31-DEC-49' and pol_status <> 4 AND pol_issue_hour = 3 and POL_PREPARED_BY=:preparedBy");
		query.setParameter("quotationNo", policyVO.getQuoteNo());
		query.setParameter("preparedBy", Integer.parseInt(policyVO.getLoggedInUser().getUserId()));
		List<Object[]> resultList = query.list();
		Date effDate = null;
		for (Object[] results : resultList) {
			try {
				Date polExpiryDate = (Date) results[0];
				policyVO.setPolExpiryDate(polExpiryDate);
			} catch (Exception e) {
				LOGGER.info("Exception in parsing expiryDate date");
				e.printStackTrace();
			}

			policyVO.setPolLinkingId(((BigDecimal) results[1]).longValue());

			try {
				effDate = (Date) results[2];
			} catch (Exception e) {
				LOGGER.info("Exception in parsing effective date");
				e.printStackTrace();
			}

			policyVO.setPolCustomerId(((BigDecimal) results[3]).longValue());
			
			/*
			 * Added for JLT Renewal Scope #11424
			 */
			if(!Utils.isEmpty(policyVO.getAuthInfoVO())) {
				policyVO.getAuthInfoVO().setTxnType(((BigDecimal)results[4]).intValue());
			}else {
				policyVO.setAuthInfoVO(new AuthenticationInfoVO());
				policyVO.getAuthInfoVO().setTxnType(((BigDecimal)results[4]).intValue());
			}
			policyVO.setPolEffectiveDate((Date) (results[5]));
			
			if(!Utils.isEmpty( results[6])) {
				policyVO.getAuthInfoVO().setRefPolicyNo( ((BigDecimal) results[6]).longValue());
			}
				
			LOGGER.info(
					" Pol_Expiry_Date:" + policyVO.getPolExpiryDate() + " Pol_Linking_Id" + policyVO.getPolLinkingId()
							+ " Pol_Effective_Date" + effDate + " Pol_Customer_ID" + policyVO.getPolCustomerId());
		}

		UserProfile userProfile = WSAppUtils.getWSUserProfileVo(policyVO.getLoggedInUser().getUserId());
		request.getSession(false).setAttribute(AppConstants.SESSION_USER_PROFILE_VO, userProfile);
		session = request.getSession(false);
		session.setAttribute(SESSION_USER, userProfile);
		RSAUser user = (RSAUser) userProfile.getRsaUser();

		ServiceContext.setUser(user);
		if (ServiceContext.getMessage() != null) {
			ServiceContext.setMessage(null);
		}
		policyVO.setLoggedInUser(userProfile);

		/*// GeneralInfoVO input for Rules call
		GeneralInfoVO generalInfo = new GeneralInfoVO();
		policyVO.setGeneralInfo(generalInfo);*/

		// SchemeVO input for Rules call
//		SchemeVO scheme = new SchemeVO();
//		scheme.setTariffCode(204);
		policyVO.getScheme().setPolicyType("50");
		policyVO.getScheme().setPolicyCode(50);
		if (!Utils.isEmpty(effDate)) {
			policyVO.getScheme().setEffDate(effDate);
		}
//		policyVO.setScheme(scheme);

		// PremiumVO input for Rules call
		if (!Utils.isEmpty(createSBSPolicyRequest.getPayment().getPaymentAmount().getAmount())) {
			PremiumVO premium = new PremiumVO();
			premium.setPremiumAmt(createSBSPolicyRequest.getPayment().getPaymentAmount().getAmount());
			policyVO.setPremiumVO(premium);
		}

		// SourceOfBusinessVO input for Rules call
	/*	SourceOfBusinessVO sourceOfBusiness = new SourceOfBusinessVO();
		sourceOfBusiness.setBrokerName(83);
		policyVO.getGeneralInfo().setSourceOfBus(sourceOfBusiness);*/
	}

}
