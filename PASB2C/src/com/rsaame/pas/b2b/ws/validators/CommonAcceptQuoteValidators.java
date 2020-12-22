package com.rsaame.pas.b2b.ws.validators;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.b2b.ws.handler.DocumentHandler;
import com.rsaame.pas.b2b.ws.util.WSBusinessValidatorUtils;
import com.rsaame.pas.b2b.ws.util.WSDAOUtils;
import com.rsaame.pas.b2b.ws.util.WSBusinessValidatorUtils.SBSErrorCodes;
import com.rsaame.pas.b2b.ws.vo.CreateSBSPolicyRequest;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.PolicyVO;

public class CommonAcceptQuoteValidators {

	private final static Logger LOGGER = Logger.getLogger(CommonAcceptQuoteValidators.class);
	public List<SBSWSValidators> validate(PolicyVO policyVO,CreateSBSPolicyRequest createSBSPolicyRequest, List<SBSWSValidators> sbsWSValidatorsList) {

		/* Start DB fields validation*/
		WSDAOUtils util =new WSDAOUtils();
		
		if(util.checkQuotaionNo(policyVO)){
			SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_QUOTATION_NUMBER, "SBSWS_ERR_096",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
			sbsWSValidatorsList.add(SBSbusinessValidators);
		}else{
			util.getPolicyREquiredData(policyVO);
			if(!Utils.isEmpty(createSBSPolicyRequest.getPayment().getPaymentAmount().getAmount())){
				Double premiumAmt = createSBSPolicyRequest.getPayment().getPaymentAmount().getAmount();
				Double premiumFromDB = util.getPremiumWithVat(policyVO.getQuoteNo(), premiumAmt);
				
				if((Math.floor(premiumAmt) == Math.floor(premiumFromDB)) || (AppUtils.getFormattedDoubleWithTwoDecimals(Math.abs(Math.abs(premiumFromDB) - Math.abs(premiumAmt))) <= Double.parseDouble(Utils.getSingleValueAppConfig("B2B_JLT_PREMIUM_DIFF")))){
					LOGGER.debug( "The Premium difference is less than equal to B2B_JLT_PREMIUM_DIFF");
				}else{
					SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_QUOTATION_NUMBER, "SBSWS_ERR_097",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
			}
			if(policyVO.getStatus()==7) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_QUOTATION_NUMBER, "SBSWS_ERR_100",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
				return sbsWSValidatorsList;
			}
		    int polStatusFromTrans = policyVO.getStatus();
		    Long linkingId = policyVO.getPolLinkingId();
		    if(polStatusFromTrans == Integer.parseInt(Utils.getSingleValueAppConfig("QUOTE_ACCEPT"))) {
		    	policyVO.setEndtId(policyVO.getEndtId());
		    	policyVO.setIsQuote(true);
		    	policyVO.setPolLinkingId(linkingId);
		    	WSDAOUtils.updateStatus(policyVO);
				
				policyVO.setStatus(1);
				polStatusFromTrans =1;
		    }
			if(polStatusFromTrans != 1){
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_QUOTATION_NUMBER, "SBSWS_ERR_095",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbsWSValidatorsList.add(SBSbusinessValidators);
			}
		}
		/* End DB fields validation*/
		
		return sbsWSValidatorsList;

	}

	public List<SBSWSValidators> ValidateTradeLicenceDoc(DataHolderVO<List<BaseVO>> dataHolderVO,
			List<SBSWSValidators> sbsWSValidatorsList) {
		List inputData = dataHolderVO.getData();
		PolicyVO policyVO = (PolicyVO) inputData.get(0);

		Boolean isQuote = true;
		Long quoLinkingId = 0L;
		DocumentHandler documentHandler = new DocumentHandler();
		HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean("hibernateTemplate");
		quoLinkingId = DocumentHandler.getLinkingIdOfQuo(policyVO, hibernateTemplate, isQuote);
		
		/**
		 * Added for JLT Renewal Scope #11424
		 */
		if (policyVO.getAuthInfoVO().getTxnType() == Integer
				.parseInt(Utils.getSingleValueAppConfig("REN_QUO_DOC_CODE"))) {
				// Getting Original quotation number from policy no
				List<TTrnPolicyQuo> trnPoliciesQuos = WSDAOUtils.getPolicyDetails(policyVO);
				
				Long originalQuoteNo = null;
				Long originalLinkingId = null;	
				if(!Utils.isEmpty(trnPoliciesQuos.get(0))) {
					originalQuoteNo = trnPoliciesQuos.get(0).getPolQuotationNo();
				}
				// Getting Original Linking Id from quote no
				PolicyVO policyVO2 = new PolicyVO();
				policyVO2.setQuoteNo(originalQuoteNo);
				policyVO2.setLoggedInUser(policyVO.getLoggedInUser());
				List<TTrnPolicyQuo> trnPolicies = WSDAOUtils.getPolicyRecord(policyVO2);
				
				if(!Utils.isEmpty(trnPolicies.get(0))) {
					originalLinkingId = trnPolicies.get(0).getPolLinkingId();
				}
				/*
				 * Copying trade license  from original quotation to renewed  quote
				 */
				File orginalTLfile = new File(Utils.getSingleValueAppConfig("FILE_UPLOAD_ROOT")+"//"+Utils.getSingleValueAppConfig("FILE_UPLOAD_TRADE_LICENCE_FOLDER")+"//"+originalLinkingId);
				File renewedFile = new File(Utils.getSingleValueAppConfig("FILE_UPLOAD_ROOT")+"//"+Utils.getSingleValueAppConfig("FILE_UPLOAD_TRADE_LICENCE_FOLDER")+"//"+quoLinkingId);
				LOGGER.debug("--------------- Copy Trade License for renewed quote --------------------"+policyVO.getQuoteNo());
				LOGGER.debug("---------------------------------------Old file------------------------"+orginalTLfile.getPath());
				LOGGER.debug("---New file is writable---"+renewedFile.canWrite()+"--------path of new file-------"+renewedFile.getPath());
				/*
				 * Added Java IO copy method instead of FileUtils API
				 */
				try{
					SvcUtils.copyTradeLicense(orginalTLfile,renewedFile);
				}catch (IOException e) {
					e.printStackTrace();
					LOGGER.debug("--------------------------------------Files Copied failure------------------------");
		        }
		} else {

			String rootPath = Utils.getSingleValueAppConfig("FILE_UPLOAD_ROOT");
			String modulePath = Utils
					.getSingleValueAppConfig(Utils.concat("FILE_UPLOAD_" + "TRADE_LICENCE" + "_FOLDER"));
			String path = Utils.concat(rootPath, "/", Utils.isEmpty(modulePath) ? "" : modulePath);

			path = path + "/" + quoLinkingId;
			LOGGER.debug("PATH " + path);

			File directory = new File(path);
			boolean isFlag = true;
			if (!directory.exists()) {
				if (isFlag) {
					isFlag = false;
					SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(
							com.Constant.CONST_QUOTATION_NUMBER, com.Constant.CONST_SBSWS_ERR_094,
							SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					;
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
			}

			List<Long> quoEndtList = null;
			quoEndtList = documentHandler.getEndtIdOfQuoTL(policyVO, hibernateTemplate, isQuote);
			String reqFileName = "TRADE_LICENCE_" + quoEndtList.get(0);

			File[] files = new File(path).listFiles();
			if (null != files && !Utils.isEmpty(files)) {
				for (File file : files) {
					if (file.isFile()) {
						if (file.getName().contains(reqFileName)) {
							if (file.getName().length() > 0) {
								LOGGER.debug("Size of File is greater than zero : " + file.getName().length());
							} else {
								if (isFlag) {
									isFlag = false;
									SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils
											.businessErrorMapping(com.Constant.CONST_QUOTATION_NUMBER,
													com.Constant.CONST_SBSWS_ERR_094, SBSErrorCodes.ERROR.name(),
													com.Constant.CONST_BUSINESS);
									;
									sbsWSValidatorsList.add(SBSbusinessValidators);
								}
							}
						} else {
							String fileName = file.getName();
							String endtNo = fileName.substring(14, fileName.length() - 4);
							List<String> endtList = new ArrayList<String>();
							for (int i = 0; i < quoEndtList.size(); i++) {
								endtList.add(String.valueOf(quoEndtList.get(i)));
							}

							if (endtList.contains(endtNo)) {
								LOGGER.debug("Trade Licence endtNo : " + endtNo);
							} else {
								if (isFlag) {
									isFlag = false;
									SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils
											.businessErrorMapping(com.Constant.CONST_QUOTATION_NUMBER,
													com.Constant.CONST_SBSWS_ERR_094, SBSErrorCodes.ERROR.name(),
													com.Constant.CONST_BUSINESS);
									;
									sbsWSValidatorsList.add(SBSbusinessValidators);
								}
							}
						}
					} else {
						if (isFlag) {
							isFlag = false;
							SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(
									com.Constant.CONST_QUOTATION_NUMBER, com.Constant.CONST_SBSWS_ERR_094,
									SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
							;
							sbsWSValidatorsList.add(SBSbusinessValidators);
						}
					}
				}
			} else {
				if (isFlag) {
					isFlag = false;
					SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(
							com.Constant.CONST_QUOTATION_NUMBER, com.Constant.CONST_SBSWS_ERR_094,
							SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					;
					sbsWSValidatorsList.add(SBSbusinessValidators);
				}
			}
		}
		return sbsWSValidatorsList;
	}
	
}
