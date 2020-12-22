/**
 * Helper class created to handle requests from RSA Direct - Drupal Page.
 * All the functionalities that would get migrated to ePlatform from RSA Direct should have the controller handler in this class.
 * From here request can get redirected to appropriate services 
 */
package com.rsaame.pas.b2c.rsaDirect;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.handlers.B2CEmailTriggers;
import com.rsaame.pas.b2c.cmn.handlers.CommonHandler;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.mail.svc.PASMailerService;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.MailVO;
import com.rsaame.pas.vo.bus.ClaimsVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.MotorClaimVO;

/**
 * @author Sarath Varier
 * @since Phase 3 - Make a Claim migration
 */
public class RSADirectHandler {

	private final static Logger LOGGER = Logger
			.getLogger(RSADirectHandler.class);

	public ClaimsVO submitClaim(ClaimsVO claimVO) {

		LOGGER.info("Redirecting to service for submit claim from handler");

		TaskExecutor.executeTasks("RSA_DIRECT_SUBMIT_CLAIM", claimVO);

		/*PolicyDataVO policyDataVO = new PolicyDataVO();
		policyDataVO.setCommonVO(new CommonVO());
		policyDataVO.setGeneralInfo(new GeneralInfoVO());
		policyDataVO.getGeneralInfo().setInsured(
				((MotorClaimVO) claimVO).getInsuredVO());
		policyDataVO.getCommonVO().setPolicyNo(claimVO.getPolicyNo());
		policyDataVO.getCommonVO().setPolicyId(claimVO.getClaimId());

		CommonHandler.populateAndTriggerEmail(policyDataVO, null,
		B2CEmailTriggers.SUBMIT_CLAIM, null);*/
		triggerMail(claimVO, B2CEmailTriggers.SUBMIT_CLAIM);

		LOGGER.info("Exiting from submit claim handler");
		return claimVO;
	}

	/**
	 * Method to handle golf insurance submission. Would trigger mail to insured & RSA
	 * @since Rel 3.4.4
	 * @author Karthik Chandra
	 * @param insuredVO
	 * @return
	 */
	public InsuredVO submitGolfInsurance(InsuredVO insuredVO) {
		LOGGER.info("Redirecting to service for submit Golf Insurance customer contact infromation from handler");

		triggerMail(insuredVO, B2CEmailTriggers.SUBMIT_GOLF_INSURANCE);

		return insuredVO;
	}
	/**
	 * Method to handle Personal Accident insurance submission. Would trigger mail to insured & RSA
	 * @param insuredVO
	 * @return
	 */
	public InsuredVO submitPersonalAccidentInsurance(InsuredVO insuredVO) {
		LOGGER.info("Redirecting to service for submit Golf Insurance customer contact infromation from handler");

		triggerMail(insuredVO, B2CEmailTriggers.SUBMIT_PERSONAL_ACCIDENT_INSURANCE);

		return insuredVO;
	}
	/**
	 * Method to send mails for RSA Direct related screens
	 * 
	 * @param baseVO
	 * @param triggerEvent
	 */
	private void triggerMail(BaseVO baseVO, B2CEmailTriggers triggerEvent) {

		MailVO mailVO = null;
		String emailContent = null;
		boolean flag = false;

		mailVO = (MailVO) Utils.getBean("mailVO");
		PASMailerService mailer = (PASMailerService) Utils
				.getBean("emailService");
		mailVO.setSignature("RSA");
		mailVO.setCreatedOn(new Timestamp(Calendar.getInstance().getTime()
				.getTime()));
		mailVO.setMailType(SvcConstants.MAIL_TYPE_HTML);
		mailVO.setFromAddress(AppConstants.B2C_DEFAULT_FROM_EMAILID);
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat datetimeformat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");

		//Added for D2C Oman
		LoginLocation location = (LoginLocation) Utils.getBean("location");
		String deployedLocation = location.getLocation();
		
		if (!Utils.isEmpty(baseVO) && !Utils.isEmpty(triggerEvent)) {

			switch (triggerEvent) {
			
			case SUBMIT_PERSONAL_ACCIDENT_INSURANCE:
				InsuredVO insuredVO = (InsuredVO) baseVO;
				emailContent = AppUtils
						.getTempalteContentAsString(AppConstants.B2C_PERSONAL_ACCIDENT_INSURANCE_SUCCESS_TEMPLATE);
				if (!Utils.isEmpty(emailContent)) {
					emailContent = emailContent.replace(
							AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
							WordUtils.capitalizeFully(insuredVO.getName()));
					mailVO.setToAddress(insuredVO.getEmailId());
					mailVO.setSubjectText(AppConstants.B2C_PERSONAL_ACCIDENT_INSURANCE_EMAIL_SUBJECT);
					mailVO.setMailContent(new StringBuilder(emailContent));
					mailVO.setFromAddress(AppConstants.B2C_DEFAULT_FROM_EMAILID);
					flag = CommonHandler.sendGeneralEmail(mailVO, null);
					if (!flag) {
						LOGGER.debug("Email trigger failed after submit Golf Insurance Name -_1"
								+ String.valueOf(insuredVO.getName())
								+ com.Constant.CONST_FOR_EMAIL_ID_END
								+ String.valueOf(insuredVO.getEmailId())
								+ com.Constant.CONST_FOR_MOBILE_NUMBER_END
								+ insuredVO.getMobileNo());
					} else {
						LOGGER.debug("Email trigger successfully after submit Golf Insurance Name -_1"
								+ String.valueOf(insuredVO.getName())
								+ com.Constant.CONST_FOR_EMAIL_ID_END
								+ String.valueOf(insuredVO.getEmailId())
								+ com.Constant.CONST_FOR_MOBILE_NUMBER_END
								+ insuredVO.getMobileNo());
					}
				} else {
					LOGGER.debug("Error occured while reading email template -_1"
							+ AppConstants.B2C_PERSONAL_ACCIDENT_INSURANCE_SUCCESS_TEMPLATE);
				}

				// now notification to user
				emailContent = AppUtils
						.getTempalteContentAsString(AppConstants.B2C_PERSONAL_ACCIDENT_INSURANCE_NOTIFICATION_TEMPLATE);

				if (!Utils.isEmpty(emailContent)) {

					emailContent = emailContent.replace(com.Constant.CONST_PERC_INSUREDNAME_PERC_END,
							insuredVO.getName());
					emailContent = emailContent.replace(com.Constant.CONST_PERC_EMAIL_PERC_END,
							insuredVO.getEmailId());
					emailContent = emailContent.replace("%MOBILENUMBER%",
							insuredVO.getMobileNo());
					emailContent = emailContent.replace("%QUOTEREQUESTDATE%",
							dateformat.format(new Date()));

					mailVO.setToAddress(AppConstants.B2C_EMAIL_PERSONAL_ACCIDENT_INSURANCE_EMAILID);
					mailVO.setSubjectText(AppConstants.B2C_PERSONAL_ACCIDENT_INSURANCE_EMAIL_SUBJECT);
					mailVO.setMailContent(new StringBuilder(emailContent));
					mailVO.setFromAddress(AppConstants.B2C_DEFAULT_FROM_EMAILID);
					
					flag = CommonHandler.sendGeneralEmail(mailVO, null);
					
					if (!flag) {
						LOGGER.debug("Email trigger failed after submit Golf Insurance Name -_2"
								+ String.valueOf(insuredVO.getName())
								+ com.Constant.CONST_FOR_EMAIL_ID_END
								+ String.valueOf(insuredVO.getEmailId())
								+ com.Constant.CONST_FOR_MOBILE_NUMBER_END
								+ insuredVO.getMobileNo());
					} else {
						LOGGER.debug("Email trigger successfully after submit Golf Insurance Name -_2"
								+ String.valueOf(insuredVO.getName())
								+ com.Constant.CONST_FOR_EMAIL_ID_END
								+ String.valueOf(insuredVO.getEmailId())
								+ com.Constant.CONST_FOR_MOBILE_NUMBER_END
								+ insuredVO.getMobileNo());
					}
				} else {
					LOGGER.debug("Error occured while reading email template -_2"
							+ AppConstants.B2C_PERSONAL_ACCIDENT_INSURANCE_NOTIFICATION_TEMPLATE);
				}

				
				break;
			
			
			case SUBMIT_GOLF_INSURANCE:
				 insuredVO = (InsuredVO) baseVO;
				//Oman D2C Email template change - Start
				if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) 
						&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){
					LOGGER.info(" TRAVEL HOME LEAD SUBMITTED for Oman D2C");
					String pmmId = AppConstants.OMAN_D2C_TRAVEL;

					List<String> resultSet = DAOUtils
							.getSqlResultForPasString(
									QueryConstants.HOME_LEAD_SUBMIT_OMAN_D2C, pmmId);
					if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
						emailContent = resultSet.get(0);
						LOGGER.debug("emailContent HOME LEAD SUBMITTED Oman D2C Travel "
								+ emailContent);
					}
				}else{
					emailContent = AppUtils
							.getTempalteContentAsString(AppConstants.B2C_GOLF_INSURANCE_SUCCESS_TEMPLATE);
				}
				//Oman D2C Email template change - End
				if (!Utils.isEmpty(emailContent)) {
					emailContent = emailContent.replace(
							AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
							WordUtils.capitalizeFully(insuredVO.getName()));
					mailVO.setToAddress(insuredVO.getEmailId());
					//Oman D2C Email template change - Start
					if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) 
							&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){
						mailVO.setSubjectText(AppConstants.D2C_HOME_INSURANCE_EMAIL_SUBJECT);
						if(null != AppConstants.B2C_GOLF_INSURANCE_SUBMIT_CC_EMAILID && AppConstants.B2C_GOLF_INSURANCE_SUBMIT_CC_EMAILID.length() > 0){
							mailVO.setCcAddress(new String[]{AppConstants.B2C_GOLF_INSURANCE_SUBMIT_CC_EMAILID});	
						}
					}else{
						mailVO.setSubjectText(AppConstants.B2C_GOLF_INSURANCE_EMAIL_SUBJECT);
					}
					//Oman D2C Email template change - End
					mailVO.setMailContent(new StringBuilder(emailContent));
					mailVO.setFromAddress(AppConstants.B2C_DEFAULT_FROM_EMAILID);
					flag = CommonHandler.sendGeneralEmail(mailVO, null);
					if (!flag) {
						LOGGER.debug("Email trigger failed after submit Golf Insurance Name -_3"
								+ String.valueOf(insuredVO.getName())
								+ com.Constant.CONST_FOR_EMAIL_ID_END
								+ String.valueOf(insuredVO.getEmailId())
								+ com.Constant.CONST_FOR_MOBILE_NUMBER_END
								+ insuredVO.getMobileNo());
					} else {
						LOGGER.debug("Email trigger successfully after submit Golf Insurance Name -_3"
								+ String.valueOf(insuredVO.getName())
								+ com.Constant.CONST_FOR_EMAIL_ID_END
								+ String.valueOf(insuredVO.getEmailId())
								+ com.Constant.CONST_FOR_MOBILE_NUMBER_END
								+ insuredVO.getMobileNo());
					}
				} else {
					LOGGER.debug("Error occured while reading email template -_3"
							+ AppConstants.B2C_GOLF_INSURANCE_SUCCESS_TEMPLATE);
				}

				// now notification to user
				//Oman D2C Email template change - Start
				if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) 
						&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){
					LOGGER.info(" TRAVEL HOME LEAD NOTIFICATION for Oman D2C");
					String pmmId = AppConstants.OMAN_D2C_TRAVEL;

					List<String> resultSet = DAOUtils
							.getSqlResultForPasString(
									QueryConstants.HOME_LEAD_NOTIFICATION_OMAN_D2C, pmmId);
					if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
						emailContent = resultSet.get(0);
						LOGGER.debug("emailContent HOME LEAD NOTIFICATION Oman D2C Travel "
								+ emailContent);
					}
				}else{
					emailContent = AppUtils
							.getTempalteContentAsString(AppConstants.B2C_GOLF_INSURANCE_NOTIFICATION_TEMPLATE);
				}
				//Oman D2C Email template change - End
				
				if (!Utils.isEmpty(emailContent)) {

					emailContent = emailContent.replace(com.Constant.CONST_PERC_INSUREDNAME_PERC_END,
							insuredVO.getName());
					emailContent = emailContent.replace(com.Constant.CONST_PERC_EMAIL_PERC_END,
							insuredVO.getEmailId());
					emailContent = emailContent.replace("%MOBILENUMBER%",
							insuredVO.getMobileNo());
					emailContent = emailContent.replace("%QUOTEREQUESTDATE%",
							dateformat.format(new Date()));

					mailVO.setToAddress(AppConstants.B2C_EMAIL_GOLF_INSURANCE_EMAILID);
					//Oman D2C Email template change - Start
					if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) 
							&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){
						if(null != AppConstants.B2C_GOLF_INSURANCE_NOTIFICATION_CC_EMAILID && AppConstants.B2C_GOLF_INSURANCE_NOTIFICATION_CC_EMAILID.length() > 0){
							mailVO.setCcAddress(new String[]{AppConstants.B2C_GOLF_INSURANCE_NOTIFICATION_CC_EMAILID});	
						}
						mailVO.setSubjectText(AppConstants.D2C_HOME_INSURANCE_EMAIL_SUBJECT);
					}else{
						mailVO.setSubjectText(AppConstants.B2C_GOLF_INSURANCE_EMAIL_SUBJECT);
					}
					//Oman D2C Email template change - End
					mailVO.setMailContent(new StringBuilder(emailContent));
					mailVO.setFromAddress(AppConstants.B2C_DEFAULT_FROM_EMAILID);
					
					flag = CommonHandler.sendGeneralEmail(mailVO, null);
					
					if (!flag) {
						LOGGER.debug("Email trigger failed after submit Golf Insurance Name -_4"
								+ String.valueOf(insuredVO.getName())
								+ com.Constant.CONST_FOR_EMAIL_ID_END
								+ String.valueOf(insuredVO.getEmailId())
								+ com.Constant.CONST_FOR_MOBILE_NUMBER_END
								+ insuredVO.getMobileNo());
					} else {
						LOGGER.debug("Email trigger successfully after submit Golf Insurance Name -_4"
								+ String.valueOf(insuredVO.getName())
								+ com.Constant.CONST_FOR_EMAIL_ID_END
								+ String.valueOf(insuredVO.getEmailId())
								+ com.Constant.CONST_FOR_MOBILE_NUMBER_END
								+ insuredVO.getMobileNo());
					}
				} else {
					LOGGER.debug("Error occured while reading email template -_4"
							+ AppConstants.B2C_GOLF_INSURANCE_NOTIFICATION_TEMPLATE);
				}

				break;

			case SUBMIT_CLAIM:

				MotorClaimVO claimVO = (MotorClaimVO) baseVO;
				//Oman D2C Email template change - Start
				if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) 
						&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){
					LOGGER.info(" TRAVEL FNOL SUBMITTED for Oman D2C");
					String pmmId = AppConstants.OMAN_D2C_TRAVEL;

					List<String> resultSet = DAOUtils
							.getSqlResultForPasString(
									QueryConstants.FNOL_SUBMIT_OMAN_D2C, pmmId);
					if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
						emailContent = resultSet.get(0);
						LOGGER.debug("emailContent FNOL SUBMITTED Oman D2C Travel "
								+ emailContent);
					}
				}else{
					emailContent = AppUtils
							.getTempalteContentAsString(AppConstants.B2C_MAKE_CLAIM_SUCCESS_TEMPLATE);
				}
				//Oman D2C Email template change - End
				
				if (!Utils.isEmpty(emailContent)) {
					emailContent = emailContent.replace(
							AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
							WordUtils.capitalizeFully(claimVO.getInsuredVO()
									.getName()));
					mailVO.setToAddress(claimVO.getInsuredVO().getEmailId());
					//Oman D2C Email template change - Start
					if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) 
							&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){
						if(null != AppConstants.B2C_FNOL_CC_EMAILID && AppConstants.B2C_FNOL_CC_EMAILID.length() > 0){
							mailVO.setCcAddress(new String[]{AppConstants.B2C_FNOL_CC_EMAILID});
						}
						mailVO.setSubjectText(AppConstants.D2C_FNOL_EMAIL_SUBJECT + claimVO.getPolicyNo().toString());
					}else{
						mailVO.setSubjectText(AppConstants.B2C_MAKE_CLAIM_EMAIL_SUBJECT);	
					}
					//Oman D2C Email template change - End
					mailVO.setMailContent(new StringBuilder(emailContent));
					mailVO.setFromAddress(AppConstants.B2C_EMAIL_CLAIMS_EMAILID);
					flag = CommonHandler.sendGeneralEmail(mailVO, null);
					if (!flag) {
						LOGGER.debug("Email trigger failed after submit claim for policy no - "
								+ String.valueOf(claimVO.getPolicyNo())
								+ com.Constant.CONST_FOR_CLAIM_ID_END
								+ String.valueOf(claimVO.getClaimId()));
					} else {
						LOGGER.debug("Email triggered after submit claim for policy no - "
								+ String.valueOf(claimVO.getPolicyNo())
								+ com.Constant.CONST_FOR_CLAIM_ID_END
								+ String.valueOf(claimVO.getClaimId()));
					}
				} else {
					LOGGER.debug("Error occured while reading email template -_5"
							+ AppConstants.B2C_MAKE_CLAIM_SUCCESS_TEMPLATE);
				}

				//Oman D2C Email template change - Start
				if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) 
						&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){
					LOGGER.info(" TRAVEL FNOL NOTIFICATION for Oman D2C");
					String pmmId = AppConstants.OMAN_D2C_TRAVEL;

					List<String> resultSet = DAOUtils
							.getSqlResultForPasString(
									QueryConstants.FNOL_NOTIFICATION_OMAN_D2C, pmmId);
					if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
						emailContent = resultSet.get(0);
						LOGGER.debug("emailContent FNOL NOTIFICATION Oman D2C Travel "
								+ emailContent);
					}
				}else{
					emailContent = AppUtils
							.getTempalteContentAsString(AppConstants.B2C_MAKE_CLAIM_NOTIFICATION_TEMPLATE);
				}
				//Oman D2C Email template change - End
				
				if (!Utils.isEmpty(emailContent)) {
					emailContent = emailContent.replace("%POLICYNO%", claimVO
							.getPolicyNo().toString());
					emailContent = emailContent.replace(com.Constant.CONST_PERC_INSUREDNAME_PERC_END,
							claimVO.getInsuredVO().getName().toString());
					emailContent = emailContent.replace("%CONTACTNO%", claimVO
							.getInsuredVO().getMobileNo());
					emailContent = emailContent.replace(com.Constant.CONST_PERC_EMAIL_PERC_END, claimVO
							.getInsuredVO().getEmailId());
					/*emailContent = emailContent.replace("%VEHICLEMAKE%",
							SvcUtils.getLookUpDescription("PAS_VEHICLE_MAKE",
									"ALL", "ALL", claimVO.getVehicleVO()
											.getMakeCode()));*/
					/*emailContent = emailContent.replace("%VEHICLEMODEL%",
							SvcUtils.getLookUpDescription("PAS_VEHICLE_MODEL",
									claimVO.getVehicleVO().getMakeCode()
											.toString(), "ALL", claimVO
											.getVehicleVO().getModelCode()));*/
					/*emailContent = emailContent.replace("%REGISTRATIONNO%",
							claimVO.getVehicleVO().getRegistrationNo());*/
					emailContent = emailContent.replace("%DATEOFACCIDENT%",
							dateformat.format(claimVO.getDateOfAccident()));
					if (null != deployedLocation && !deployedLocation.equals(AppConstants.LOCATION_CODE) && null != claimVO.getVehicleVO() && null != claimVO.getVehicleVO().getLocation()) {
						emailContent = emailContent.replace("%PREFERREDLOC%",
							SvcUtils.getLookUpDescription("CITY", "ALL", "ALL",
									Integer.valueOf(claimVO.getVehicleVO()
											.getLocation())));
					}
					/*emailContent = emailContent.replace("%REMARKS%",
							claimVO.getRemarks());*/
					emailContent = emailContent.replace("%CLAIMSNO%", claimVO
							.getClaimId().toString());
					emailContent = emailContent.replace("%DATEOFCLAIM%",
							datetimeformat.format(claimVO.getPreparedDate()));
					/*emailContent = emailContent.replace("%LOSSDESC%",
							claimVO.getLossDescription());*/
					
					//Attaching file to email for Oman D2C
					boolean isAttach = false;
					if (null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE)) {
						List<String> fileList = new ArrayList<String>();
						if(null != claimVO.getVehicleVO().getDriverLicenceFilePath()) {
							fileList.add(claimVO.getVehicleVO().getDriverLicenceFilePath());
						}
						if(null != claimVO.getVehicleVO().getRegistartionCardFilePath()) {
							fileList.add(claimVO.getVehicleVO().getRegistartionCardFilePath());
						}
						if(null != claimVO.getClaimFormFilePath()) {
							fileList.add(claimVO.getClaimFormFilePath());
						}
						if(null != claimVO.getAccidentType() && claimVO.getAccidentType().equalsIgnoreCase(AppConstants.ACCIDENT_TYPE_POLICE_REPORT)) {
							if(null != claimVO.getPoliceReportFilePath())
								fileList.add(claimVO.getPoliceReportFilePath());
						}else
							if(null != claimVO.getMrtaFormFilePath())
								fileList.add(claimVO.getMrtaFormFilePath());
						String[] fileArrayList = new String[fileList.size()];
						fileArrayList = fileList.toArray(fileArrayList);
						if(fileArrayList.length > 0) 
							isAttach = true;
						mailVO.setFileNames(fileArrayList);
					}else {
						String[] fileNames = {
								claimVO.getVehicleVO().getDriverLicenceFilePath(),
								claimVO.getPoliceReportFilePath(),
								claimVO.getVehicleVO()
								.getRegistartionCardFilePath() };
						if(fileNames.length > 0)
							isAttach = true;
						mailVO.setFileNames(fileNames);
					}
					//Added Driver details for Oman D2C
					if (null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE)) {
						emailContent = emailContent.replace("%DRIVERNAME%",
								claimVO.getVehicleVO().getDriverName());
						emailContent = emailContent.replace("%DRIVERLICENSENUMBER%",
								claimVO.getVehicleVO().getDlNumber());
						if(isAttach)
							emailContent = emailContent.replace("%NOTIFIACATIONMSG%",
								Utils.getSingleValueAppConfig(AppConstants.D2C_NOTIFICATION_MSG));
						else
							emailContent = emailContent.replace("%NOTIFIACATIONMSG%", "");
					}
					if (null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE)) {
						mailVO.setToAddress(AppConstants.B2C_EMAIL_CLAIMS_TO_EMAILID);
					}else {
						mailVO.setToAddress(AppConstants.B2C_EMAIL_CLAIMS_EMAILID);
					}
					mailVO.setSubjectText(AppConstants.B2C_MAKE_CLAIM_NOTIFICATION_EMAIL_SUBJECT
							.replace(
									AppConstants.B2C_EMAIL_POLICY_NO_IDENTIFIER,
									claimVO.getPolicyNo().toString()));
					mailVO.setMailContent(new StringBuilder(emailContent));
					if (null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE)) {
						mailVO.setFromAddress(AppConstants.B2C_EMAIL_CLAIMS_FROM_EMAILID);
					}else {
						mailVO.setFromAddress(AppConstants.B2C_DEFAULT_FROM_EMAILID);
					}
					
					mailVO = (MailVO) mailer.invokeMethod("sendEmailWithImage",
							mailVO, null);
					// flag = CommonHandler.sendGeneralEmail(mailVO, null);

					if (!Boolean.valueOf(mailVO.getMailStatus())) {
						LOGGER.debug("Email trigger failed after submit claim for policy no - "
								+ String.valueOf(claimVO.getPolicyNo())
								+ com.Constant.CONST_FOR_CLAIM_ID_END
								+ String.valueOf(claimVO.getClaimId()));
					} else {
						LOGGER.debug("Email triggered after submit claim for policy no - "
								+ String.valueOf(claimVO.getPolicyNo())
								+ com.Constant.CONST_FOR_CLAIM_ID_END
								+ String.valueOf(claimVO.getClaimId()));
					}
				} else {
					LOGGER.debug("Error occured while reading email template -_6"
							+ AppConstants.B2C_MAKE_CLAIM_NOTIFICATION_TEMPLATE);
				}

				break;
			default:
				break;

			}
		}

	}

	@SuppressWarnings("unchecked")
	public String submitRenewalPolicy(DataHolderVO<Object[]> policyDataHolder)
			throws BusinessException {

		LOGGER.info("Redirecting to service for renewal policy from handler");
		StringBuffer redirectURL = null;

		Object[] data = policyDataHolder.getData();
		Long policyNo = (Long) data[0];
		Date dob = (Date) data[2];
		Integer lob = (Integer) data[3];
		String emailId = (String) data[1];
		// Added equals() instead of == to avoid sonar violation on 25-9-2017
		if (lob.equals(Integer.valueOf(SvcConstants.MOTOR_POL_TYPE))) {
			DataHolderVO<Integer> locCode = (DataHolderVO<Integer>) TaskExecutor
					.executeTasks("RSA_DIRECT_RENEW_POLICY_MOTOR",
							policyDataHolder);
			redirectURL = new StringBuffer(
					Utils.getSingleValueAppConfig("B2C_REQUEST_URL_MOTOR"));
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String formatedDob = format.format(dob);
			redirectURL
					.append(Utils
							.getSingleValueAppConfig("B2C_RENEWAL_MOTOR_CONTROLLER"))
					.append("?renPolicyNum=" + policyNo + "&dob=" + formatedDob
							+ "&loc=" + locCode.getData());
		} else if (lob.equals( Integer.valueOf(SvcConstants.HOME_POL_TYPE))) { // Added equals() instead of == to avoid sonar violation on 26-9-2017
			redirectURL = new StringBuffer(
					Utils.getSingleValueAppConfig("B2C_REQUEST_URL_RENEWAL_HOME"));
			redirectURL
					.append(Utils
							.getSingleValueAppConfig("B2C_FETCH_QUOTE_HOME_RENEWAL_METHOD"))
					.append("?policyNo=").append(policyNo).append("&emailId=")
					.append(emailId);
		} else if (lob .equals(Integer.valueOf(SvcConstants.TRAVEL_POL_TYPE))) { // Added equals() instead of == to avoid sonar violation on 27-9-2017
			redirectURL = new StringBuffer(
					Utils.getSingleValueAppConfig("B2C_REQUEST_URL_RENEWAL_TRAVEL"));
			redirectURL
					.append(Utils
							.getSingleValueAppConfig("B2C_FETCH_QUOTE_TRAVEL_RENEWAL_METHOD"))
					.append("?policyNo=").append(policyNo).append("&dob=")
					.append(AppUtils.datesFormatter((Date) data[2]));
		}

		LOGGER.debug("constructed URL = " + redirectURL);
		LOGGER.info("Exiting from submitRenewalPolicy method");
		return String.valueOf(redirectURL);
	}

}
