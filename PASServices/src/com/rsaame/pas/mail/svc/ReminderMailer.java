package com.rsaame.pas.mail.svc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimerTask;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.dairy.svc.DairySvc;
import com.rsaame.pas.dao.model.TMasUser;
import com.rsaame.pas.dao.model.TTrnReminder;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.MailVO;

public class ReminderMailer extends TimerTask {
	private final static Logger logger = Logger.getLogger(ReminderMailer.class);

	public void run() {
		String location = ServiceContext.getLocation();;
		String locations = Utils.getSingleValueAppConfig("LOCATION_CODE",0);
		List<String> locationsToAppend = CopyUtils.asList(locations.split(","));
		
		for(String loc : locationsToAppend){
			ServiceContext.setLocation(loc);
			sendReminderMail();
		}
		
		ServiceContext.setLocation(location);
	}
	
	private void sendReminderMail() {
		
		try{
		DairySvc diarySvc = (DairySvc) Utils.getBean("dairySvc");
		DataHolderVO<List <TTrnReminder>> data = (DataHolderVO<List <TTrnReminder>>)diarySvc.invokeMethod("getDairyItemsForReminder",null);
		List <TTrnReminder> reminderItemsList= (List <TTrnReminder>)data.getData();
		// Start of For Loop for Each Diary Item Processing
		for(TTrnReminder reminderToSend:reminderItemsList){
			// Start : Check diary Start
			if(!reminderToSend.getRemStatus()){


				int userId=reminderToSend.getId().getRemPreparedBy();
				CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean("geComSvcBean");
				DataHolderVO userDetailsData=(DataHolderVO)commonOpSvc.invokeMethod("getUserDetails",userId);
				TMasUser userDetails=(TMasUser)userDetailsData.getData();
				MailVO mail=new MailVO();
				// Start: Check User Details
				if(!Utils.isEmpty(userDetails)){
					if(!Utils.isEmpty(userDetails.getUserEmailId())){
						mail.setToAddress(userDetails.getUserEmailId());	
					}else{
						//To check whether to throw Exception or not
						//BusinessException businessExcp=new BusinessException( "mail.user.noemail", null, "User does not have mail id updated in user profile" );
						//throw businessExcp;
						logger.error("User does not have mail id updated in user profile, Diary reminder can not send to :" + userDetails.getUserEName());
						continue;
					}

					String subj_desc= reminderToSend.getRemDescription();
					if(Utils.isEmpty(subj_desc)){
						mail.setSubjectText(" ");
						mail.setMailContent(new StringBuilder(" "));
					}
					else{
						if(subj_desc.indexOf(SvcConstants.REMAINDER_SUBJECT_DELIMITER)==-1){
							mail.setSubjectText(" ");
							mail.setMailContent(new StringBuilder(subj_desc));
						}else{
						StringTokenizer st = new StringTokenizer(subj_desc, SvcConstants.REMAINDER_SUBJECT_DELIMITER);
						mail.setSubjectText(Utils.getSingleValueAppConfig( "PAS_REMINDER_SUBJECT")+" "+st.nextToken());
						mail.setMailContent(new StringBuilder(st.nextToken()));
						}
					}
					
					mail.setFromAddress(Utils.getSingleValueAppConfig( "PAS_REMINDER_MAIL_BOX"));
					
					
					mail.setMailType(SvcConstants.MAIL_TYPE_PLAINTXT);
					PASMailerService mailer = (PASMailerService) Utils.getBean("emailService");
					//PASMailerService mailer = new PASMailerService();
					mail=(MailVO)mailer.invokeMethod("sendMail", mail);
					
					if(!Utils.isEmpty(mail.getMailStatus())){
						if(mail.getMailStatus().equals("fail")){
							//TODO: Remove after testing
							/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
							String preparedDateStr = sdf.format(reminderToSend.getId().getRemPreparedDt());
							
							diarySvc.invokeMethod("updateDiaryItemsForReminder",String.valueOf(reminderToSend.getId().getRemSrlNo()),
									String.valueOf(reminderToSend.getId().getRemPreparedBy()),
									preparedDateStr,
									String.valueOf(reminderToSend.getId().getRemTypeId()));*/
							logger.error("Exception in sending mail to " + userDetails.getUserEName()+"regarding diary item with serial number_1"+String.valueOf(reminderToSend.getId().getRemSrlNo()));
							//continue;
						}else{
						
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
							String preparedDateStr = sdf.format(reminderToSend.getId().getRemPreparedDt());
							logger.info("Reminder mail sent to " + userDetails.getUserEName()+
									"regarding diary item with serial number_2"+String.valueOf(reminderToSend.getId().getRemSrlNo()));
							diarySvc.invokeMethod("updateDiaryItemsForReminder",String.valueOf(reminderToSend.getId().getRemSrlNo()),
									String.valueOf(reminderToSend.getId().getRemPreparedBy()),
									preparedDateStr,
									String.valueOf(reminderToSend.getId().getRemTypeId()));
							logger.info("Reminder mail status updated for"+
									"regarding diary item with serial number_3"+String.valueOf(reminderToSend.getId().getRemSrlNo())+
									"creaed by "+ userDetails.getUserEName()+ "on "+preparedDateStr+"of Type" +String.valueOf(reminderToSend.getId().getRemTypeId()) );
							
						}

					}
				}//End: Check User Details

			}//End : Check diary Start
			
		}// End of For Loop for Each Diary Item Processing
		} catch(Exception e){
			logger.error("Error in creating reminder :"+e.getMessage());
		}
		
	

	}

}
