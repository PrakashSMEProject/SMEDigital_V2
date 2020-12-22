package com.rsaame.pas.mail.svc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.ArrayUtils;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.MailVO;

public class PASMailerService {
	private final static Logger logger = Logger.getLogger(PASMailerService.class);
	
	@SuppressWarnings( "unchecked" )
	public Object invokeMethod( String methodName, Object... args ){
		Object returnValue = null;
		if( methodName.equals( "sendMail" ) ){
			returnValue = sendMail( (MailVO) args[ 0 ] );
		} else if(methodName.equals( "sendMailImage" )){
			returnValue = sendMail((MailVO) args[0],(String)args[1]);
		} else if(methodName.equals("sendEmailWithImage")) {
			returnValue = sendEmailWithImage((MailVO) args[0],(String)args[1]); //For B2C emails
		} /*else if(methodName.equalsIgnoreCase( "sendReferralMail" )){
			returnValue = sendReferralMail((MailVO) args[0],(String)args[1]); //For Referral Mail
		}*/
		return returnValue;
	}
	
	
	public MailVO sendMail(MailVO mailVO ){
		InputStream input= null;
		InputStream in = null; //SONARFIX
		OutputStream out = null; //SONARFIX
		
		
		try {
			PASMailSender mailSender= new PASMailSender();
			mailSender.setFrom(mailVO.getFromAddress());
			logger.debug( "From Id:"+mailVO.getFromAddress() );
			
			if(!Utils.isEmpty( mailVO.getToAddress() )) {
				mailSender.setRecipient(mailVO.getToAddress());
				logger.debug( "To Id:"+mailVO.getToAddress() );
			} else if(!Utils.isEmpty( mailVO.getToAddresses() )){
				mailSender.setRecipients(mailVO.getToAddresses());
				//Added ArrayUtils.toString() for Invocation of toString on an array, sonar violation fix on 22-9-2017
				logger.debug( "To Id's:"+ ArrayUtils.toString(mailVO.getToAddresses()));
			}
			
			if(!Utils.isEmpty( mailVO.getReplyToEmailID())){
				mailSender.setReplyTo(mailVO.getReplyToEmailID());
				logger.debug(new String[] { "REPLY TO Id:" + mailVO.getReplyToEmailID() });
			}
			else{
				mailSender.setReplyTo(mailVO.getFromAddress());
				logger.debug(new String[] { "REPLY TO Id in else :" + mailVO.getFromAddress() });
			}
							
			mailSender.setSubject(mailVO.getSubjectText());
			logger.debug( "Subject:"+mailVO.getSubjectText() );

			if(!Utils.isEmpty( mailVO.getCcAddress() )){
				mailSender.setCCRecipients( mailVO.getCcAddress() );
			}
			
			logger.debug( "Content:"+mailVO.getMailContent() );
			
			if(!Utils.isEmpty(mailVO.getMailType())){
				if(mailVO.getMailType().equals(SvcConstants.MAIL_TYPE_HTML)){
					mailSender.addHtmlContent(mailVO.getMailContent().toString());	
				}else if(mailVO.getMailType().equals(SvcConstants.MAIL_TYPE_PLAINTXT)){
					mailSender.addTextContent(mailVO.getMailContent().toString());	
				}
			}


			
			if(!Utils.isEmpty(mailVO.getFileNames())) {

				String [] fileNames;
				fileNames=mailVO.getFileNames();
				for(String fileName:fileNames){
					File file = new File(fileName);
					if(file.isFile()){
						logger.debug("PASMailer:File Name_1"+ fileName);
						input = new FileInputStream(file);
						mailSender.addFileAttachment(input, file.getName());
					}else{
						//Start: Check if it is testing or not
						if(Utils.getSingleValueAppConfig( com.Constant.CONST_DOC_EMAIL_TESTING).equals("Y")){
							// Yes this is testing
							File smpleFile= new File(com.Constant.CONST_C_PASDOCUMENTS_SAMPLE_PDF);
							File tempFileToSend= new File(fileName);
							 in = new FileInputStream(smpleFile);  //SONARFIX
							 out = new FileOutputStream(tempFileToSend); //SONARFIX

							try{
								byte[] buf = new byte[1024];
								int len;
								while ((len = in.read(buf)) > 0){
									out.write(buf, 0, len);
								}
								input = new FileInputStream(file);
								mailSender.addFileAttachment(input, file.getName());

							}
							catch(Exception e){
								mailVO.setMailStatus("fail");
								mailVO.setError(com.Constant.CONST_FILE_NOT_FOUND_END+fileName );
								logger.error("PAS Mailer Service Error _1"+"File Not Found_1"+fileName);

								in.close();
								out.close();
							}
							
							finally{ //SONARFIX -- updated the finally block 23-apr-2018
								
								try{
									if(in != null){ //!Utils.isEmpty(in)
									in.close();
									} 
								}
								catch(Exception e){
									e.getMessage();
								}
								
								try{
									if(out != null){  //!Utils.isEmpty(out)
									out.close();
									}
								}
								catch(Exception e){
									e.getMessage();
								}
							}

						}else{
							// Not testing, production
							mailVO.setMailStatus("fail");
							mailVO.setError(com.Constant.CONST_FILE_NOT_FOUND_END+fileName );
							logger.error("PAS Mailer Service Error _2"+"File Not Found_2"+fileName);

						}
						//End: Check if it is testing or not


					}
				}


			}
			
				
				
			mailSender.send();
			mailVO.setMailStatus(com.Constant.CONST_SUCCESS);

		} catch (Exception e) {

			mailVO.setMailStatus("fail");
			mailVO.setError(com.Constant.CONST_ERROR_OCCURED_DURING_SENDING_MAIL);
			logger.error("PAS Mailer Service Error_1"+e.getMessage());
			
		}
		finally{
			try{
			if(!Utils.isEmpty(input)){
				input.close();
				}
			}
			catch (Exception e){
				
				mailVO.setMailStatus("fail");
				mailVO.setError(com.Constant.CONST_ERROR_OCCURED_DURING_SENDING_MAIL);
				logger.error("PAS Mailer Service Error_2"+e.getMessage());
			}
			try{
				if(in != null){ //!Utils.isEmpty(in)
				in.close();
				} 
			}
			catch(Exception e){
				e.getMessage();
			}
			
			try{
				if(out != null){  //!Utils.isEmpty(out)
				out.close();
				}
			}
			catch(Exception e){
				e.getMessage();
			}
		}
		

		return mailVO;

	}
	
	/**
	 * Method to send mail with image as attachment
	 * @param mailVO
	 * @param lob
	 * @return
	 */
	public MailVO sendMail(MailVO mailVO,String flow){
		InputStream input= null;
		InputStream in = null; //SONARFIX
		OutputStream out = null;//SONARFIX
		
		try {
			PASMailSender mailSender= new PASMailSender();
			mailSender.setFrom(mailVO.getFromAddress());
			logger.debug( "From Id:"+mailVO.getFromAddress() );
			//changed for CR - 94168
			if(!Utils.isEmpty( mailVO.getToAddress() )) {
				mailSender.setRecipient(mailVO.getToAddress());
				logger.debug( "To Id:"+mailVO.getToAddress() );
			} else if(!Utils.isEmpty( mailVO.getToAddresses() )){
				mailSender.setRecipients(mailVO.getToAddresses());
				//Added ArrayUtils.toString() for Invocation of toString on an array, sonar violation fix on 22-9-2017
				logger.debug( "To Id's:"+ ArrayUtils.toString(mailVO.getToAddresses()));
			}
			if(!Utils.isEmpty( mailVO.getReplyToEmailID())){
				mailSender.setReplyTo(mailVO.getReplyToEmailID());
				logger.debug(new String[] { "REPLY TO Id:" + mailVO.getReplyToEmailID() });
			}
			else{
				mailSender.setReplyTo(mailVO.getFromAddress());
				logger.debug(new String[] { "REPLY TO Id in else :" + mailVO.getFromAddress() });
			}

			mailSender.setSubject(mailVO.getSubjectText());
			logger.debug( "Subject:"+mailVO.getSubjectText() );
			
			/** Get the image signature only if the distribution channel is direct */
			if( mailVO.isDirect() ){
				if(mailVO.getPolicyType().equalsIgnoreCase(SvcConstants.SHORT_TRAVEL_POL_TYPE) || 
						mailVO.getPolicyType().equalsIgnoreCase(SvcConstants.LONG_TRAVEL_POL_TYPE)) {
					mailVO.getMailContent().append( Utils.getSingleValueAppConfig( "TRAVEL_EMAIL_SIGNATURE_" + flow ) );
				} else {
					mailVO.getMailContent().append( Utils.getSingleValueAppConfig( "EMAIL_SIGNATURE_" + flow ) );
				}
			}
			logger.debug( "Content:"+mailVO.getMailContent() );
			
			
			if(!Utils.isEmpty( mailVO.getCcAddress() )){
				mailSender.setCCRecipients( mailVO.getCcAddress() );
			}
			
			if(!Utils.isEmpty(mailVO.getMailType())){
				if(mailVO.getMailType().equals(SvcConstants.MAIL_TYPE_HTML)){
					mailSender.addHtmlContent(mailVO.getMailContent().toString());
				}else if(mailVO.getMailType().equals(SvcConstants.MAIL_TYPE_PLAINTXT)){
					mailSender.addTextContent(mailVO.getMailContent().toString());	
				}
			}
			
			/** Get the image signature only if the distribution channel is direct */
			if(mailVO.isDirect()){
				String[] imageIds = null;
				if((mailVO.getPolicyType().equalsIgnoreCase(SvcConstants.SHORT_TRAVEL_POL_TYPE) || 
						mailVO.getPolicyType().equalsIgnoreCase(SvcConstants.LONG_TRAVEL_POL_TYPE)) && 
						(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("20") || Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("21") || Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30"))) {
					imageIds = Utils.getMultiValueAppConfig( "TRAVEL_IMAGE_ID_"+flow );;
				} 
				if(mailVO.getPolicyType().equalsIgnoreCase(SvcConstants.SHORT_TRAVEL_POL_TYPE) || 
						mailVO.getPolicyType().equalsIgnoreCase(SvcConstants.LONG_TRAVEL_POL_TYPE) &&
						Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("50")) {
					imageIds = Utils.getMultiValueAppConfig( "BAH_TRAVEL_IMAGE_ID_"+flow );;
				} else {
					imageIds = Utils.getMultiValueAppConfig( "IMAGE_ID_"+flow );;
				}
				
				
				for(String imageId:imageIds){
					String[] imageContent = Utils.getMultiValueAppConfig( imageId+"_"+flow );
					mailSender.getImageContent( imageContent[1], imageContent[0] );
				}
			}
			
			if(!Utils.isEmpty(mailVO.getFileNames())) {

				String [] fileNames= new String [mailVO.getFileNames().length];
				fileNames=mailVO.getFileNames();
				for(String fileName:fileNames){
					File file = new File(fileName);
					if(file.isFile()){
						logger.debug("PASMailer:File Name_2"+ fileName);
						input = new FileInputStream(file);
						mailSender.addFileAttachment(input, file.getName());
					}else{
						//Start: Check if it is testing or not
						if(Utils.getSingleValueAppConfig( com.Constant.CONST_DOC_EMAIL_TESTING).equals("Y")){
							// Yes this is testing
							File smpleFile= new File(com.Constant.CONST_C_PASDOCUMENTS_SAMPLE_PDF);
							File tempFileToSend= new File(fileName);
							 in = new FileInputStream(smpleFile); //SONARFIX
							 out = new FileOutputStream(tempFileToSend); //SONARFIX

							try{
								byte[] buf = new byte[1024];
								int len;
								while ((len = in.read(buf)) > 0){
									out.write(buf, 0, len);
								}
								input = new FileInputStream(file);
								mailSender.addFileAttachment(input, file.getName());

							}
							catch(Exception e){
								mailVO.setMailStatus("fail");
								mailVO.setError(com.Constant.CONST_FILE_NOT_FOUND_END+fileName );
								logger.error("PAS Mailer Service Error _3"+"File Not Found_3"+fileName);
								in.close();
								out.close();
							}
							finally{   //SONARFIX
								
								try{
									if(!Utils.isEmpty(in)){
									in.close();
									} 
								}
								catch(Exception e){
									e.getMessage();
								}
								
								try{
									if(!Utils.isEmpty(out)){
									out.close();
									} 
								}
								catch(Exception e){
									e.getMessage();
								}
							}

						}else{
							// Not testing, production
							mailVO.setMailStatus("fail");
							mailVO.setError(com.Constant.CONST_FILE_NOT_FOUND_END+fileName );
							logger.error("PAS Mailer Service Error _4"+"File Not Found_4"+fileName);

						}
						//End: Check if it is testing or not


					}
				}


			}
			
				
				
			mailSender.send();
			mailVO.setMailStatus(com.Constant.CONST_SUCCESS);

		} catch (Exception e) {

			mailVO.setMailStatus("fail");
			mailVO.setError(com.Constant.CONST_ERROR_OCCURED_DURING_SENDING_MAIL);
			logger.error("PAS Mailer Service Error_3"+e.getMessage());
			
		}
		finally{
			try{
			if(!Utils.isEmpty(input)){
				input.close();
				}
			}
			catch (Exception e){
				
				mailVO.setMailStatus("fail");
				mailVO.setError(com.Constant.CONST_ERROR_OCCURED_DURING_SENDING_MAIL);
				logger.error("PAS Mailer Service Error_4"+e.getMessage());
			}
			//SONARFIX -- 23-apr-2018
			try{
				if(!Utils.isEmpty(in)){
				in.close();
				} 
			}
			catch(Exception e){
				e.getMessage();
			}
			
			try{
				if(!Utils.isEmpty(out)){
				out.close();
				} 
			}
			catch(Exception e){
				e.getMessage();
			}
		}
		

		return mailVO;

	}
	
	/**
	 * This method is for B2C related email triggers with images
	 * 
	 * @param mailVO
	 * @param flow
	 * @return
	 */
	public MailVO sendEmailWithImage(MailVO mailVO, String flow) {

		InputStream input= null;
		InputStream in = null;  //SONARFIX
		OutputStream out = null; //SONARFIX
		
		try {
			PASMailSender mailSender= new PASMailSender();
			mailSender.setFrom(mailVO.getFromAddress());
			mailSender.setRecipient(mailVO.getToAddress());
			mailSender.setSubject(mailVO.getSubjectText());
			if(!Utils.isEmpty(mailVO.getReplyToEmailID()))
			{
				mailSender.setReplyTo(mailVO.getReplyToEmailID());
			}
			else
			{
				mailSender.setReplyTo(mailVO.getFromAddress());
			}
			
			if(mailVO.isDirect()){
			if(!Utils.isEmpty(flow)){
				mailVO.getMailContent().append( Utils.getSingleValueAppConfig( "EMAIL_SIGNATURE_" + flow ) );
			}
			}
			if(!Utils.isEmpty( mailVO.getCcAddress() )){
				mailSender.setCCRecipients( mailVO.getCcAddress() );
			}
			if(!Utils.isEmpty(mailVO.getMailType())){
				if(mailVO.getMailType().equals(SvcConstants.MAIL_TYPE_HTML)){
					mailSender.addHtmlContent(mailVO.getMailContent().toString());
				}else if(mailVO.getMailType().equals(SvcConstants.MAIL_TYPE_PLAINTXT)){
					mailSender.addTextContent(mailVO.getMailContent().toString());	
				}
			}
			if(mailVO.isDirect()){
			String[] imageIds = Utils.getMultiValueAppConfig( "IMAGE_ID_"+flow );
			if (!Utils.isEmpty(imageIds)){
				for(String imageId:imageIds){
					String[] imageContent = Utils.getMultiValueAppConfig( imageId+"_"+flow );
					mailSender.getImageContent( imageContent[1], imageContent[0] );
				}
			}
			}
			if(!Utils.isEmpty(mailVO.getFileNames())) {
				String [] fileNames;
				fileNames=mailVO.getFileNames();
				for(String fileName:fileNames){
					File file = new File(fileName);
					if(file.isFile()){
						logger.debug("PASMailer:File Name_3"+ fileName);
						input = new FileInputStream(file);
						mailSender.addFileAttachment(input, file.getName());
					}else{
						//Start: Check if it is testing or not
						if(Utils.getSingleValueAppConfig( com.Constant.CONST_DOC_EMAIL_TESTING).equals("Y")){
							// Yes this is testing
							File smpleFile= new File(com.Constant.CONST_C_PASDOCUMENTS_SAMPLE_PDF);
							File tempFileToSend= new File(fileName);
							 in = new FileInputStream(smpleFile); //SONARFIX
							 out = new FileOutputStream(tempFileToSend);  //SONARFIX
							try{
								byte[] buf = new byte[1024];
								int len;
								while ((len = in.read(buf)) > 0){
									out.write(buf, 0, len);
								}
								input = new FileInputStream(file);
								mailSender.addFileAttachment(input, file.getName());
							}
							catch(Exception e){
								mailVO.setMailStatus("fail");
								mailVO.setError(com.Constant.CONST_FILE_NOT_FOUND_END+fileName );
								logger.error("PAS Mailer Service Error _5"+"File Not Found_5"+fileName);
								in.close();
								out.close();
							}
							finally{  //SONARFIX
								
								try{
									if(!Utils.isEmpty(in)){
									in.close();
									} 
								}
								catch(Exception e){
									e.getMessage();
								}
								
								try{
									if(!Utils.isEmpty(out)){
									out.close();
									} 
								}
								catch(Exception e){
									e.getMessage();
								}
								
							
							}
						}else{
							// Not testing, production
							mailVO.setMailStatus("fail");
							mailVO.setError(com.Constant.CONST_FILE_NOT_FOUND_END+fileName );
							logger.error("PAS Mailer Service Error for B2C:"+"File Not Found_6"+fileName);
						}
						//End: Check if it is testing or not
					}
				}
			}
			mailSender.send();
			mailVO.setMailStatus(com.Constant.CONST_SUCCESS);
		} catch (Exception e) {
			mailVO.setMailStatus("fail");
			mailVO.setError("Error occured during Sending B2C emails");
			logger.error("Error occured while sending B2C emails - "+e.getMessage());
		}
		finally{
			try{
			if(!Utils.isEmpty(input)){
				input.close();
				}
			}
			catch (Exception e){
				mailVO.setMailStatus("fail");
				mailVO.setError(com.Constant.CONST_ERROR_OCCURED_DURING_SENDING_MAIL);
				logger.error("PAS Mailer Service Error_5"+e.getMessage());
			}
			//SONARFIX
			try{
				if(!Utils.isEmpty(in)){
				in.close();
				} 
			}
			catch(Exception e){
				e.getMessage();
			}
			
			try{
				if(!Utils.isEmpty(out)){
				out.close();
				} 
			}
			catch(Exception e){
				e.getMessage();
			}
		}
		return mailVO;
	}
	
	


	
}
