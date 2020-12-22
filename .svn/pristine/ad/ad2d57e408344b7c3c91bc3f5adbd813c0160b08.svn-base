/**
 * 
 */
package com.rsaame.pas.renewals.svc;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnSms;
import com.rsaame.pas.renewals.dao.IRenewalCommonDAO;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author m1019193
 * 
 */
public class RenewalBaseSvc extends BaseService {

	private static final Logger LOGGER = Logger.getLogger(RenewalBaseSvc.class);
	private IRenewalCommonDAO renewalCommonDAO;
	RenewalCommonSvc renewalCommonSvc;
	
	/**
	 * @param renewalCommonDAO
	 */
	public void setRenewalCommonDAO(IRenewalCommonDAO renewalCommonDAO) {
		this.renewalCommonDAO = renewalCommonDAO;
	}

	@Override
	public Object invokeMethod(String methodName, Object... args) {
		if ("generateOnlineRenewal".equals(methodName)) {
			return generateOnlineRenewal((BaseVO) args[0]);
		}else if("processRenewedQuote".equals(methodName)){
			return processRenewedQuote((CommonVO) args[0], (Object[]) args[0]);
		}else if("checkTradeLicenseorRenewal".equals(methodName)){
			return checkTradeLicenseorRenewal((CommonVO) args[0]);
		}
		return null;
	}

	/**
	 * 
	 * @param baseVO
	 * @return
	 */
	private BaseVO generateOnlineRenewal(BaseVO baseVO) {

		@SuppressWarnings("unchecked")
		DataHolderVO<Object[]> inputVO = (DataHolderVO<Object[]>) baseVO;
		
		UserProfile userProfile = null;
		Object[] renInputData = inputVO.getData();
		if (!Utils.isEmpty(renInputData[2])) {
			userProfile = (UserProfile) renInputData[2];
		}
		
		@SuppressWarnings("unchecked")
		DataHolderVO<HashMap<String, Object>> holderVO = (DataHolderVO<HashMap<String, Object>>) renewalCommonDAO
				.generateOnlineRenewalMonoline(baseVO);
		HashMap<String, Object> commonVoMap = holderVO.getData();
		LOGGER.debug("*****renQuotationNo = " + commonVoMap.get("renewalQuoteNo"));
		LOGGER.debug("*****renPolicyId = " + commonVoMap.get("policyId"));

		try {
			CommonVO commonVO = populateCommonVO(commonVoMap, userProfile);
			
			processRenewedQuote(commonVO, renInputData );
			
		} catch (BusinessException be) {
			throw new BusinessException("cmn.unknownError", be,
					"error Occured during Renewal Quote generatation");
		}
		return holderVO;
	}
	
	/**
	 * Once the quote is generated, process it, save & issue
	 * @param commonVO
	 * @param renInputData
	 */
	private BaseVO processRenewedQuote(CommonVO commonVO, Object[] renInputData){
		
		TTrnPolicyQuo toBeRenewedQuoteDetails = renewalCommonDAO.getQuoteDetails(new Object[]{renInputData[0]});
		
		copyTradelicence(commonVO, renInputData, toBeRenewedQuoteDetails.getPolQuotationNo());
		
		PolicyDataVO policyDataVO = (PolicyDataVO) loadHelper(commonVO);
		@SuppressWarnings("unchecked")
		DataHolderVO<HashMap<String, Boolean>> output = (DataHolderVO<HashMap<String, Boolean>>) renewalMatrix(
				policyDataVO, (Long) renInputData[0]);

		saveHelper(policyDataVO);

		if (!Utils.isEmpty(output) && !Utils.isEmpty(output.getData())) {
			HashMap<String, Boolean> renMatrixResult = output.getData();

			if (!Utils.isEmpty(renMatrixResult)
					&& !Utils.isEmpty(renMatrixResult.get("HardStop"))
					&& renMatrixResult.get("HardStop").booleanValue()) {

				// update status in PolicyQuo table - HardStop/SoftStop
				DataHolderVO<Object[]> input = new DataHolderVO<Object[]>();
				Object claimInputData[] = new Object[2];
				claimInputData[0] = policyDataVO;
				claimInputData[1] = Utils.getSingleValueAppConfig("QUOTE_HARD_STOP");
				input.setData(claimInputData);
				renewalCommonSvc.invokeMethod("updateQuotationStatus", input);
				policyDataVO.getCommonVO().setStatus(
								Integer.parseInt(Utils.getSingleValueAppConfig("QUOTE_HARD_STOP")));
			}
		}

		if(SvcConstants.SEND_RENEWAL_SMS_LOBS.contains(commonVO.getLob().toString())){
			sendRenewalSms(policyDataVO);
		}
		return policyDataVO;
	}

	/**
	 * @param commonVO
	 * @return Method to load the PolicyDadaVO data for a renewal quote
	 */
	private BaseVO loadHelper(CommonVO commonVO) {
		String loadClass = "load_" + commonVO.getLob(); // load_HOME
		BaseService load = (BaseService) Utils.getBean(loadClass);
		PolicyDataVO polVO = new PolicyDataVO();
		polVO.setCommonVO(commonVO);
		return (BaseVO) load.invokeMethod(SvcConstants.LOAD_RENEWAL_QUOTE, polVO);
	}

	/**
	 * 
	 * @param polData
	 * @param policyId
	 * @return Method to check Loading/Discount or HardStop based on the
	 *         Specific Renewal Matrix
	 */
	private BaseVO renewalMatrix(PolicyDataVO polData, Long policyId) {
		String matrixClass = "renmatx_" + polData.getCommonVO().getLob(); // renmatx_HOME
		IRenewalMatrix renMatrix = (IRenewalMatrix) Utils.getBean(matrixClass);
		return (BaseVO) renMatrix.executeRenMatrix(polData, policyId);
	}

	/**
	 * 
	 * @param polData
	 * @param status
	 */
	private void saveHelper(PolicyDataVO polData) {
		/*DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();
		Object[] inpObjects = { polData };
		dataHolder.setData(inpObjects);*/
		String saveClass = "save_" + polData.getCommonVO().getLob();
		BaseService save = (BaseService) Utils.getBean(saveClass);
		save.invokeMethod(SvcConstants.SAVE_RENEWAL_QUOTE, polData);
		// change status and call C2P based on <code> status </code>
	}

	/**
	 * 
	 * @param polType
	 * @return
	 */
	private LOB deriveLOBfromPolicyType(String polType) {

		if (polType.equals(SvcConstants.HOME_POL_TYPE)) {
			return LOB.HOME;
		} else if (polType.equals(SvcConstants.SHORT_TRAVEL_POL_TYPE)
				|| polType.equals(SvcConstants.LONG_TRAVEL_POL_TYPE)) {
			return LOB.TRAVEL;
		} else if (polType.equals(SvcConstants.WC_POLICY_TYPE.toString())) {
			return LOB.WC;
		}
		return null;
	}

	/**
	 * @param commonVoMap
	 * @param userProfile
	 * @return Method to populate CommonVO. It fetches data from the CommonVOMap
	 *         and populates the CommonVO.
	 * @param: commonVOMap is populated by querying the TTrnPolicyQuo table in
	 *         DAO layer.
	 */
	private CommonVO populateCommonVO(HashMap<String, Object> commonVoMap,
			UserProfile userProfile) {

		CommonVO commonVO = new CommonVO();
		commonVO.setQuoteNo(Long.valueOf(String.valueOf(commonVoMap
				.get("renewalQuoteNo"))));
		commonVO.setPolicyId((Long) commonVoMap.get("policyId"));
		commonVO.setLocCode(Integer.parseInt(String.valueOf(commonVoMap
				.get("locCode"))));
		commonVO.setPolEffectiveDate((Date) commonVoMap.get("effectiveDate"));
		commonVO.setEndtNo((Long) commonVoMap.get("EndtNo"));
		commonVO.setDocCode((Short) commonVoMap.get("polDocCode"));
		commonVO.setVsd((Date) commonVoMap.get("polVSD"));
		String polType = String.valueOf(commonVoMap.get("policyType"));
		commonVO.setPolicyNo((Long) commonVoMap.get("PolicyNo"));
		commonVO.setConcatPolicyNo((String) commonVoMap.get("cnocPolicyNo"));
		commonVO.setIsQuote(true);
		commonVO.setStatus(Integer.valueOf(Utils
				.getSingleValueAppConfig(SvcConstants.QUOTE_PENDING)));
		commonVO.setEndtId(0L);
		commonVO.setAppFlow(Flow.CREATE_QUO);
		commonVO.setLoggedInUser(userProfile);
		if(!Utils.isEmpty(commonVoMap.get(com.Constant.CONST_POLVATCODE)))
			commonVO.setVatCode((Integer)commonVoMap.get(com.Constant.CONST_POLVATCODE));

		if(!Utils.isEmpty(commonVoMap.get("polVatRegNo")))  
			commonVO.setVatRegNo(commonVoMap.get("polVatRegNo").toString());
		
		BigDecimalDoubleConverter converter = ConverterFactory.getInstance( BigDecimalDoubleConverter.class, "", "" );
		PremiumVO premiumVO = new PremiumVO();
		if(!Utils.isEmpty(commonVoMap.get("polVatTaxPerc")))  premiumVO.setVatTaxPerc(converter.getBFromA( commonVoMap.get("polVatTaxPerc")) );
		if(!Utils.isEmpty(commonVoMap.get("polVatTax")))  premiumVO.setVatTax(converter.getBFromA(commonVoMap.get("polVatTax")) );
		if(!Utils.isEmpty(commonVoMap.get(com.Constant.CONST_POLVATCODE)))  premiumVO.setVatCode((Integer) commonVoMap.get(com.Constant.CONST_POLVATCODE));
		LOB lob = deriveLOBfromPolicyType(polType);
		commonVO.setLob(lob);
		commonVO.setPremiumVO( premiumVO );
		return commonVO;
	}

	/**
	 * @param policyDataVO
	 *            Method to trigger SMS in the last step of Renewal Process SMS
	 *            will be triggered once the Renewal Quotation has been
	 *            generated.
	 */
	void sendRenewalSms(PolicyDataVO policyDataVO) {

		DataHolderVO<Object[]> input = new DataHolderVO<Object[]>();
		TTrnSms tTrnSms = new TTrnSms();
		tTrnSms.setTrnSmsPolicyId(policyDataVO.getCommonVO().getPolicyId());
		tTrnSms.setTrnSmsEndtId(policyDataVO.getCommonVO().getEndtId());
		tTrnSms.setTrnSmsLocCode(policyDataVO.getCommonVO().getLocCode()
				.shortValue());
		tTrnSms.setTrnSmsGsmNo(policyDataVO.getGeneralInfo().getInsured()
				.getMobileNo());
		tTrnSms.setTrnSmsStatus(Byte.valueOf(Utils
				.getSingleValueAppConfig("SUBMITTED")));
		tTrnSms.setTrnSmsLangType(SvcConstants.SMS_LANGUAGE);
		tTrnSms.setTrnSmsMode(SvcConstants.SMS_AUTO);
		tTrnSms.setTrnSmsLevel((byte) 1);
		// tTrnSms.setTrnSmsSubmittedBy(ServiceContext.getUser().getUserId().toString());
		tTrnSms.setTrnSmsSubmittedBy(policyDataVO.getCommonVO()
				.getLoggedInUser().getUserId());
		tTrnSms.setTrnSmsSentDate(new Date());
		tTrnSms.setTrnSmsSubDate(new Date());
		Object renewalSmsData[] = new Object[2];
		renewalSmsData[0] = tTrnSms;
		renewalSmsData[1] = policyDataVO.getCommonVO();
		input.setData(renewalSmsData);
		renewalCommonDAO.sendRenewalMessage(input);
	}
	
	private void copyTradelicence(CommonVO commonVO, Object[] renInputData, Long toBeRenewedQuoteNo) {
		
		try {
			if (SvcConstants.COPY_TL_RENEWAL_LOBS.contains(commonVO.getLob().toString())) {
				LOGGER.debug("------- Copy Trade License for renewed quote --------"
						+ commonVO.getQuoteNo());
				File orginalTLfile = new File(
						Utils.getSingleValueAppConfig(com.Constant.CONST_FILE_UPLOAD_ROOT) + "//"
								+ Utils.getSingleValueAppConfig(com.Constant.CONST_FILE_UPLOAD_TRADE_LICENCE_FOLDER) + "//"
								+ toBeRenewedQuoteNo );
				File renewedFile = new File(
						Utils.getSingleValueAppConfig(com.Constant.CONST_FILE_UPLOAD_ROOT) + "//"
								+ Utils.getSingleValueAppConfig(com.Constant.CONST_FILE_UPLOAD_TRADE_LICENCE_FOLDER)
								+ "//" + commonVO.getQuoteNo());

				LOGGER.debug("-------Old file-----------" + orginalTLfile.getPath());
				LOGGER.debug("---New file is writable---" + renewedFile.canWrite()
						+ "--------path of new file-------" + renewedFile.getPath());
				SvcUtils.copyTradeLicense(orginalTLfile, renewedFile);
				
				//String oldFilename = Utils.getSingleValueAppConfig( "TRADE_LIC_DOWNLOAD_FILE_NAME" )+ "_" + toBeRenewedQuoteNo;
				//String newFilename = Utils.getSingleValueAppConfig( "TRADE_LIC_DOWNLOAD_FILE_NAME" )+ "_" + commonVO.getQuoteNo();
				//SvcUtils.renameFile(renewedFile, oldFilename, newFilename);
				
				
				// Request Id: 153337 : Enhance the functionality of Upload/Download of Trade Licence Certificates
				String renewedFilePath = Utils.getSingleValueAppConfig(com.Constant.CONST_FILE_UPLOAD_ROOT) + "//"
						+ Utils.getSingleValueAppConfig(com.Constant.CONST_FILE_UPLOAD_TRADE_LICENCE_FOLDER)
						+ "//" + commonVO.getQuoteNo();
				
				// Get list of files from renewed file path
				File folder = new File(renewedFilePath);
				File listOfFiles[] = folder.listFiles();
				String oldFilename = null, newFilename = null;
					
				if(!Utils.isEmpty(listOfFiles)) {
					for(int cnt=0; cnt < listOfFiles.length; cnt++) {
							
						if (listOfFiles[cnt].isFile()) {
							System.out.println("The file name is:"+ listOfFiles[cnt].getName());
							
							oldFilename = listOfFiles[cnt].getName();
							oldFilename = oldFilename.substring(0, (oldFilename.lastIndexOf(".")));
							
							if(oldFilename.contains(toBeRenewedQuoteNo.toString())) {
								newFilename = oldFilename.substring(0, oldFilename.lastIndexOf("_") + 1) + commonVO.getQuoteNo();
								SvcUtils.renameFile(renewedFile, oldFilename, newFilename);
							}
						}
							
					}
				}
				
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			LOGGER.debug("-------Trade license Files Copied failure------");
			LOGGER.trace("Copying of trade licence failed.");
		}
	}
	
	private BaseVO checkTradeLicenseorRenewal(CommonVO commonVO){
		
		String path = 	Utils.concat( Utils.getSingleValueAppConfig( SvcConstants.FILE_UPLOAD_ROOT ) , "/" , 
						Utils.getSingleValueAppConfig( SvcConstants.FILE_UPLOAD_TRADE_LICENCE_FOLDER ) , "/", 
						commonVO.getQuoteNo().toString() );
		
		File theFile = new File( path );
		
		if( Utils.isEmpty( theFile.list() )){
			Long originalLinkingId = null;
			DataHolderVO<Long> result =  (DataHolderVO<Long>) renewalCommonDAO.getPreviousYearQuoteNo(commonVO);
			Long quoteNo = result.getData();
			if(!Utils.isEmpty(quoteNo)){
				copyTradelicence(commonVO, null, quoteNo);
			}		
		}		
		 
		return null;
	}
	

	public RenewalCommonSvc getRenewalCommonSvc() {
		return renewalCommonSvc;
	}

	public void setRenewalCommonSvc(RenewalCommonSvc renewalCommonSvc) {
		this.renewalCommonSvc = renewalCommonSvc;
	}

}
