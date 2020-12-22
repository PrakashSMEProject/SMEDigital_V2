package com.rsaame.pas.b2b.ws.handler;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.b2b.ws.util.WSAppUtils;
import com.rsaame.pas.b2b.ws.vo.CreateSBSPolicyRequest;
import com.rsaame.pas.b2c.WsAuthentication.BasicAuthenticationService;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.model.TTrnPaymentDtl;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;

public class SBSCreatePolicyCommonRH {

	private static final Logger LOGGER = Logger.getLogger(SBSCreatePolicyCommonRH.class);

	public DataHolderVO<List<BaseVO>> excuteSBSCreatePolicyHandler(DataHolderVO<List<BaseVO>> dataHolderVO, CreateSBSPolicyRequest createSBSPolicyRequest) {
        LOGGER.info("Start Calling ConvertToPolicy Service");
        
        RuleHandler ruleHandler = new RuleHandler();
        List inputData = dataHolderVO.getData();
        PolicyVO policyVO = (PolicyVO) inputData.get( 0 );
        boolean rulePassed = ruleHandler.callRuleConvertToPolicy(policyVO);
        LOGGER.debug("Get Policy Rule Passed ?::::" + rulePassed);
        
        TaskExecutor.executeTasks("CONVERT_TO_POLICY", dataHolderVO);
        LOGGER.info("End Calling ConvertToPolicy Service");
        
        if(createSBSPolicyRequest.getPayment().getPaymentMode().equalsIgnoreCase("CARD")){
        	saveOnlinePaymentDetails(policyVO, createSBSPolicyRequest);
        }
       
		return dataHolderVO;
	}

	private void saveOnlinePaymentDetails(PolicyVO policyVO, CreateSBSPolicyRequest createSBSPolicyRequest) {
		TTrnPaymentDtl paymentDtl =new TTrnPaymentDtl();
		paymentDtl.setPdlQutoteNo(policyVO.getQuoteNo());
		paymentDtl.setPdlPolicyId(policyVO.getPolicyNo());

		//String transId = policyVO.getQuoteNo()+""+policyVO.getPolLinkingId()+""+policyVO.getPolicyNo();
		String transId = createSBSPolicyRequest.getPayment().getPaymentRefNumber();
		paymentDtl.setPdlTransId(transId);
		
		paymentDtl.setPdlTransaAmount(new BigDecimal(policyVO.getPremiumVO().getPremiumAmt()));
		paymentDtl.setPdlCurName(createSBSPolicyRequest.getPayment().getPaymentAmount().getCurrencyCode());
		
		if(!Utils.isEmpty(createSBSPolicyRequest.getPayment().getCardType())){
			paymentDtl.setPdlCreditCrdTyp(createSBSPolicyRequest.getPayment().getCardType());
		}
		
		if(!Utils.isEmpty(createSBSPolicyRequest.getPayment().getCardNo())){
			paymentDtl.setPdlCreditCrdNo(createSBSPolicyRequest.getPayment().getCardNo());
		}
		
		if(!Utils.isEmpty(createSBSPolicyRequest.getPayment().getMerchantRefNo())){
			String MID = "050"+ "-"+policyVO.getPolLinkingId()+"-"+createSBSPolicyRequest.getPayment().getMerchantRefNo();
			paymentDtl.setPdlMerchantRefNo(MID);
		}
		
		if(!Utils.isEmpty(createSBSPolicyRequest.getPayment().getCustomerName())){
			paymentDtl.setPdlCustName(createSBSPolicyRequest.getPayment().getCustomerName());
		}
		
		if(!Utils.isEmpty(createSBSPolicyRequest.getPayment().getPaymtTransactionDate())){
			try{
				Date transaDate = new SimpleDateFormat("yyyy-MM-dd").parse(createSBSPolicyRequest.getPayment().getPaymtTransactionDate());
				paymentDtl.setPdlTransaDate(transaDate);
				
				}
			catch (Exception e) {
				LOGGER.info("Exception in parsing Payment Transaction date");
				e.printStackTrace();
			}
		}
		
		try {
			HibernateTemplate ht = (HibernateTemplate) Utils.getBean("hibernateTemplate");
			Session session = ht.getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(paymentDtl);
			session.getTransaction().commit();
			session.evict(paymentDtl);
			session.close();
			
		} catch (BusinessException be) {
			be.printStackTrace();
			throw new BusinessException(be.getErrorKeysList().get(0), be.getCause(), be.getMessage());
		} catch (Exception exp) {
			exp.printStackTrace();
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
			policyVO.getGeneralInfo().getSourceOfBus().setBrokerName(((BigDecimal) values[7]).intValue());
			policyVO.getGeneralInfo().getSourceOfBus().setPartnerId((values[7]).toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Exception while fetching default values :" + e);
		}
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
}
