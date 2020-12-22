package com.rsaame.pas.mail.svc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;

public class PASMailSender {


    /** Logger instance */
	private final static Logger logger = Logger.getLogger(PASMailSender.class);

    /** Context constants */
    private static final String CREATE_NEW_MSG = "createNewMessage";

    private static final String ADD_ATTACHMENT = "addFileAttachment";

    private static final String SEND = "send";

    private static final String CANNOT_ADD_ATTACHMENT = "Cannot add attachment to EmailMessages.";

    private static final String CANNOT_ADD_TEXT_PLAIN_CONTENT = "Cannot add text/plain content to EmailMessages";

    private static final String CANNOT_SET_SUBJECT = "Cannot set subject to EmailSender. ";

    @SuppressWarnings( "unused" )
	private static final String CANNOT_SET_CC_RECIPIENTS = "Cannot set cc recipients in EmailSender. ";

    private static final String CANNOT_SET_RECIPIENTS = "Cannot set recipients in EmailSender. ";

    private static final String CANNOT_SET_SENDER = "Cannot set sender in EmailSender. ";

    private static final String CANNOT_CREATE_MESSAGE = "Cannot create new message with EmailSender";

    private static final String MAIL_SMTP_PORT = "mail.smtp.port";

    private static final String MAIL_SMTP_HOST = "mail.smtp.host";


    /** email session. */
    private Session session = null;

    /** mime message */
    private MimeMessageHelper msgHelper = null;

    private MimeMessage mimeMsg = null;

    /** The spring mail sender implementation */
    private JavaMailSenderImpl mailSender = null;

    @SuppressWarnings( "unused" )
	private Properties properties = null;

   // private static final Properties mailProps = PropertiesUtil.loadProperties(PATH_AME_CONFIG);

    /**
     * This is constructor of EmailSender
     * @throws Exception
     */
    public PASMailSender() throws MessagingException {
        Properties argMailServerProperties = new Properties();
        String smtpHost = Utils.getSingleValueAppConfig( "MAIL_SMTP_HOST");
        String smtpPort = Utils.getSingleValueAppConfig( "MAIL_SMTP_PORT");
        argMailServerProperties.put(MAIL_SMTP_HOST, smtpHost);
        argMailServerProperties.put(MAIL_SMTP_PORT, smtpPort);
        this.session = Session.getInstance(argMailServerProperties);
        
/*        this.session = Session.getDefaultInstance(argMailServerProperties,
        	    new javax.mail.Authenticator() {
        	      protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication("M1017952@mindtree.com","N152mokkat");
        	      }
        	    });*/
        createNewMessage();
    }

    /**
     * This method is used for constructing message
     * @throws Exception
     */
    private void createNewMessage() throws MessagingException {

        logger.debug(CREATE_NEW_MSG, "entering metho_1");
        try {
            mailSender = new JavaMailSenderImpl();
            mailSender.setSession(this.session);
            this.mimeMsg = mailSender.createMimeMessage();
            this.msgHelper = new MimeMessageHelper(mimeMsg, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);

        } catch (Exception me) {
            throw new MessagingException(CANNOT_CREATE_MESSAGE + me.getMessage());
        }
        logger.debug(CREATE_NEW_MSG, "exiting metho_1");
    }

    /**
     * This method is used to set the sender of the mail
     * 
     * @param argAddress
     * @throws Exception
     */
    public void setFrom(String argAddress) throws Exception {
        try {
            this.msgHelper.setFrom(argAddress);

        } catch (Exception me) {
            throw new Exception(CANNOT_SET_SENDER + me.getMessage());
        }
    }

    /**
     * This method is used to set recipient of the mail
     * 
     * @param argAddress
     * @throws Exception
     */
    public void setRecipient(String argAddress) throws Exception {
        String[] temp = new String[1];
        temp[0] = argAddress;
        setRecipients(temp);
    }

    /**
     * This method is used to set multiple receipients of the mail
     * 
     * @param argAddress
     * @throws Exception
     */
    public void setRecipients(String[] argAddress) throws Exception {
        InternetAddress[] addr = new InternetAddress[argAddress.length];

        try {
            for (int i = 0; i < argAddress.length; i++) {
                addr[i] = new InternetAddress(argAddress[i]);

            }

            this.msgHelper.setTo(addr);

        } catch (Exception me) {
            throw new Exception(CANNOT_SET_RECIPIENTS + me.getMessage());
        }
    }
    
    /**
     * This method is used to set multiple cc receipients of the mail
     * 
     * @param argAddress
     * @throws Exception
     */
    public void setCCRecipients(String[] argAddress) throws Exception {
        InternetAddress[] addr = new InternetAddress[argAddress.length];

        try {
            for (int i = 0; i < argAddress.length; i++) {
                logger.debug( "CC Recipients:"+argAddress[i] );
            	addr[i] = new InternetAddress(argAddress[i]);

            }

            this.msgHelper.setCc(addr);

        } catch (Exception me) {
            throw new Exception(CANNOT_SET_RECIPIENTS + me.getMessage());
        }
    }

    /**
     * This method is used to set the subject of the mail
     * 
     * @param argSubject
     * @throws Exception
     */
    public void setSubject(String argSubject) throws Exception {
        try {
            msgHelper.setSubject(argSubject);
        } catch (Exception me) {
            throw new Exception(CANNOT_SET_SUBJECT + me.getMessage());
        }
    }

    /**
     * This method is used to set the text content of the mail
     * 
     * @param argText
     * @throws Exception
     */
    public void addTextContent(String argText) throws Exception {

        try {

            msgHelper.setText(argText);
        } catch (Exception me) {
            throw new Exception(CANNOT_ADD_TEXT_PLAIN_CONTENT + me.getMessage());
        }
    }

    /**
     * This method is used to set the html content of the mail
     * 
     * @param argText
     * @throws Exception
     */
    public void addHtmlContent(String argText) throws Exception {

        try {
            msgHelper.setText(argText, true);
        } catch (Exception me) {
            throw new Exception(CANNOT_ADD_TEXT_PLAIN_CONTENT + me.getMessage());
        }
    }

    /**
     * This method is used to add the attachment of the mail
     * 
     * @param inputStream
     * @param fileName
     * @throws Exception
     */
    public void addFileAttachment(InputStream inputStream, String fileName) throws Exception {
        logger.debug(ADD_ATTACHMENT, "entering metho_2");
        int ch = 0;
        byte[] fileData;

        try {
            ByteArrayOutputStream opStream = new ByteArrayOutputStream();
            while ((ch = inputStream.read()) != -1)
                opStream.write(ch);
            fileData = opStream.toByteArray();
            ByteArrayResource byteArrayResource = new ByteArrayResource(fileData);
            this.msgHelper.addAttachment(fileName, byteArrayResource);
        } catch (Exception me) {
            throw new Exception(CANNOT_ADD_ATTACHMENT + me.getMessage());
        }
        logger.debug(ADD_ATTACHMENT, "exiting metho_2");
    }

    /**
     * This method is used to send the mail
     * 
     * @throws Exception
     */
    public void send()  {
        logger.debug(SEND, "entering metho_3");
        mailSender.send(this.mimeMsg);
        logger.debug(SEND, "exiting metho_3");
    }
   
    /**
     * Get the image content
     * 
     * @param pathRsa
     * @param id
     * @throws MessagingException
     * @throws URISyntaxException 
     */
    public void getImageContent(String pathRsa,String id) throws MessagingException, URISyntaxException {
		java.net.URL urlToFile = Thread.currentThread().getContextClassLoader().getResource( pathRsa );
		File file = new File( urlToFile.toURI() );
		
		FileSystemResource resource = new FileSystemResource( file );
		
		msgHelper.addInline( id, resource );
    }
    
    /**
     * This method is used to setsetReplyTo of the mail
     * 
     * @param argAddress
     * @throws Exception
     */
    public void setReplyTo(String argAddress) throws Exception 
    {
        InternetAddress addr = new InternetAddress(argAddress);        
        this.msgHelper.setReplyTo(addr);        
    }

}

