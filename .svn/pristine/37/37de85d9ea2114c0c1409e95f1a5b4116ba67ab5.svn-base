/*
 * Created on Jan 5, 2008
 * Copyright (c) 2007-2012 Royal and Sun Alliance Insurance Group plc.
 * St.Mark’s Court, Chart Way, Horsham, West Sussex RH12 1XL, U.K.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Royal
 * and Sun Alliance Insurance Group plc.("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Royal and Sun Alliance Insurance Group plc.
 *
 */
package com.rsaame.kaizen.authentication;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.event.authentication.AuthenticationFailureBadCredentialsEvent;
import org.acegisecurity.event.authentication.AuthenticationSuccessEvent;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.acegisecurity.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.authorization.model.User;
import com.rsaame.kaizen.framework.constants.PropertyFileConstants;
import com.rsaame.kaizen.framework.logger.AMELogger;
import com.rsaame.kaizen.framework.logger.AMELoggerFactory;
import com.rsaame.kaizen.framework.util.PropertiesUtil;



 
 
public class LoginListener extends JdbcDaoImpl implements ApplicationListener, ApplicationContextAware,HttpSessionListener {

    //logger instance
    private static final AMELogger logger = AMELoggerFactory.getInstance().getLogger(LoginListener.class);
    
    private static final String LOGIN_LIST = "LoginListener";
    
    private static Integer loginId;
    
    private final String LOGIN_ATTEMPTS = "LOGIN_ATTEMPTS";
    
    private final String STATUS_ID = "STATUS_ID";
    
    private final String QUERY_RET_USER_STATUS = "SELECT T_MAS_USER.LOGIN_ATTEMPTS,T_MAS_USER.STATUS_ID,T_MAS_USER.PASSWORD_MODIFIED_DT FROM T_MAS_USER where T_MAS_USER.USER_ID =?";
    
    private final int INACTIVE = 2;
    
    private final int LOCKED = 3;
    
    private final int ACTIVE = 1;
    
	private final String QUERY_UPDATE_USER_LOGIN = "UPDATE T_MAS_USER SET LOGIN_ATTEMPTS = ? WHERE USER_ID= ?";
    
    private final String QUERY_USER_ID = " WHERE USER_ID=";
    
    private final String QUERY_UPDATE_STATUS ="UPDATE T_MAS_USER SET STATUS_ID =";
    
    private static HttpSession session;
    
    private final String LOGIN_ERROR = "LOGINERROR";
    
    private final String LOCKED_MSG = "Account has been locked . Contact the Administrator";
    
    private final String INACTIVE_MSG = "Account is inactive . Contact the Administrator";
    
    private final String NOT_AUTHORIZED = "You do not have required privilege(s) to access this application";
    
   // private final String PWD_MSG = "Invalid Credentials";   //SONARFIX -- changed the variable name from PWD_MSG to P_MSG
    
    private final String P_MSG = "Invalid Credentials";
    
    private final String UID_EXIST = "Invalid Credentials";
    
    private final int loginAttemptQueryCount = 1;
    
    private final int statusIdQueryCount = 2;
    
    private final int DateQueryCount = 3;
    
   // private final String PASSWORD_EXPIRED = "PASSWORD_EXPIRED"; //SONARFIX -- changed the variable name from PASSWORD_EXPIRED to P_EXPIRED
    
    private final String P_EXPIRED = "PASSWORD_EXPIRED";
    
    private final String LOGIN_ATT_CONST = "LOGINATTEMTPS";
    
    private static final Properties fileProperties = PropertiesUtil.loadProperties(PropertyFileConstants.PATH_AME_CONFIG);
    
    private final String QUERY_TO_CHECK_LOB_MAPPING = "SELECT * FROM T_MAS_CODE_MASTER where CDM_ENTITY_TYPE = 'PAS_LOB' AND CDM_ENTITY_DESC =";
    
    /**
     * function to listen to application events
     *  
     */
    @Override
	public void onApplicationEvent(ApplicationEvent event) {
        logger.debug(LOGIN_LIST,"entered with event::::"+event);
       if(event instanceof AuthenticationFailureBadCredentialsEvent){
           logger.debug(LOGIN_LIST,"entered with event AuthenticationFailureBadCredentialsEvent");
           AuthenticationFailureBadCredentialsEvent loginException = 
               												(AuthenticationFailureBadCredentialsEvent) event;
           if(loginException.getException() instanceof UsernameNotFoundException){
               logger.debug(LOGIN_LIST,"UsernameNotFoundException");
           }else if(loginException.getException() instanceof BadCredentialsException){
               logger.debug(LOGIN_LIST,"BadCredentialsException");
               if(getLoginId()!=null){
                       onBadCredentials();
               }else{
                   session.setAttribute(LOGIN_ERROR,UID_EXIST);
               }
           }else{
               logger.debug(LOGIN_LIST,"Authentication failed");
           }
       }else if(event instanceof AuthenticationSuccessEvent){
           logger.debug(LOGIN_LIST,"Login success AuthenticationSuccessEvent");
           // Check if LOB is configured or not
           if(!checkIfLOBisMappedForUser()) {
        	   session.setAttribute(LOGIN_ERROR,NOT_AUTHORIZED);
        	   throw new BadCredentialsException(NOT_AUTHORIZED);
        	  // throw new RuntimeException();
           }
           onAuthenticationSuccess();
       }
    }
    
    /**
     * function to update the Login attempts for the userID
     *  <code>LoginListener</code>
     */
    private boolean checkIfLOBisMappedForUser(){
        logger.debug("checkIfLOBisMappedForUser","checkIfLOBisMappedForUser ");
        Connection conn = null;
        ResultSet rs = null;
        Statement stmt =null;
        try{
	        conn = this.getDataSource().getConnection();
	        stmt = conn.createStatement(); 
	        rs = stmt.executeQuery(QUERY_TO_CHECK_LOB_MAPPING+getLoginId());
	        if(rs!=null && rs.next()){
	            return true;
	        }
        }catch(SQLException e){
            logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED while checkIfLOBisMappedForUser");
        }finally{
        	 if(stmt!=null){ 
 	            try {
 	            	stmt.close();
 	            } catch (SQLException sqlException) {
 	                logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED clossing connectio_1");        
 	            }
             }
        	
            if(conn!=null){ 
	            try {
	                conn.close();
	            } catch (SQLException sqlException) {
	                logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED clossing connectio_2");        
	            }
            }
            if(rs!=null){ //SONARFIX -- 23-apr-2018
	            try {
	                rs.close();
	            } catch (SQLException sqlException) {
	                logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED clossing resultset");        
	            }
            }
            
           
        }
        return false;
    }
    

    /**
     * function to set application context
     *  
     */
    @Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		
        //SONARFIX--26-04-2018---DO NOTHING IN METHOD 
    }
    /**
     * function to perform operations onBadCredentials
     *  <code>LoginListener</code>
     */
    public void onBadCredentials(){
        logger.debug(LOGIN_LIST,"User entered bad Credentials");
        session.setAttribute(LOGIN_ERROR,P_MSG);   //SONARFIX -- variable name changed
        User user = getUserDetails(getLoginId());
        Integer loginAttemptsConstant =  new Integer(fileProperties.getProperty(LOGIN_ATT_CONST));
        if(user.getLoginAttempts().intValue()>=loginAttemptsConstant.intValue()){
            lockUserAccount(user);
        }else{
        	// Added Integer.valueOf() to avoid sonar violation on 18-9-2017
          // updateLoginAttemptsCount(new Integer(user.getLoginAttempts().intValue() + 1 ),user.getLoginId());
        	updateLoginAttemptsCount(Integer.valueOf(user.getLoginAttempts().intValue() + 1 ),user.getLoginId());
        }
    }
    /**
     * function to perform operations onAuthenticationSuccess 
     *  <code>LoginListener</code>
     */
    public void onAuthenticationSuccess(){
        logger.debug(LOGIN_LIST,"User authentication success");
	        User user = getUserDetails(getLoginId());	        
	             if(user!=null && user.getStatusId()!=null){
	              if(user.getStatusId().intValue() == LOCKED){
	                 session.setAttribute(LOGIN_ERROR,LOCKED_MSG);
	                 setLoginId(null);
	                 throw new BadCredentialsException(LOCKED_MSG);
	             }else if(user.getStatusId().intValue()== INACTIVE){
	                 session.setAttribute(LOGIN_ERROR,INACTIVE_MSG);
	                 setLoginId(null);
	                 throw new BadCredentialsException(INACTIVE_MSG);
	             }else if(user.getStatusId().intValue()== ACTIVE){
	                Integer loginAttemptsConstant =  new Integer(fileProperties.getProperty(LOGIN_ATT_CONST));
	                 if(user.getLoginAttempts()!=null && 
	                         user.getLoginAttempts().intValue()>= loginAttemptsConstant.intValue()){
	                     //lock the account
	                     lockUserAccount(user);
	                     setLoginId(null);
	                     throw new BadCredentialsException(LOCKED_MSG);
	                 }else{
	                     if(session.getAttribute(LOGIN_ERROR)!=null)
	                       session.removeAttribute(LOGIN_ERROR);  
	                    setLoginLoginAttemptsCountToZero(user);
	                 }
	             }
	            } 
    }
    /**
     * function to lock the user account 
     *  <code>LoginListener</code>
     */
    private void lockUserAccount(User user){
        logger.debug(LOGIN_LIST,"Locking user account");
        Connection conn = null;
        Statement stmt =null;
        try{
        conn = this.getDataSource().getConnection();
        stmt = conn.createStatement();   
        stmt.executeQuery(QUERY_UPDATE_STATUS+LOCKED+QUERY_USER_ID+ user.getLoginId());
        session.setAttribute(LOGIN_ERROR,LOCKED_MSG);
        }catch(SQLException e){
            logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED when locking user account");
        }finally{
        	 if(stmt!=null){ 
 	            try {
 	            	stmt.close();
 	            } catch (SQLException sqlException) {
 	                logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED clossing connectio_3");        
 	            }
             }
            if(conn!=null){ 
	            try {
	                conn.close();
	            } catch (SQLException sqlException) {
	                logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED clossing connectio_4");        
	            }
            }
        }
    }
    /**
     * function to update the Login attempts for the userID
     *  <code>LoginListener</code>
     */
    private void updateLoginAttemptsCount(Integer count,String loginId){
        logger.debug(LOGIN_LIST,"updating Login Attempt Count ");
        Connection conn = null;
        Statement stmt =null;
        PreparedStatement pstmt = null;
        try{
        conn = this.getDataSource().getConnection();
        stmt = conn.createStatement();   
        pstmt = conn.prepareStatement(QUERY_UPDATE_USER_LOGIN);
        pstmt.setInt(1, count);
        pstmt.setString(2, loginId);
        logger.debug(LOGIN_LIST,"Executing query:::"+QUERY_UPDATE_USER_LOGIN+count+QUERY_USER_ID+ loginId);
        pstmt.executeQuery();
        }catch(SQLException e){
            logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED updating Login Attempt Count");
        }finally{
        	if(pstmt!=null){ 
  	            try {
  	            	pstmt.close();
  	            } catch (SQLException sqlException) {
  	                logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED clossing connectio_5");        
  	            }
              }
        	 if(stmt!=null){ 
  	            try {
  	            	stmt.close();
  	            } catch (SQLException sqlException) {
  	                logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED clossing connectio_6");        
  	            }
              }
            if(conn!=null){ 
	            try {
	                conn.close();
	            } catch (SQLException sqlException) {
	                logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED clossing connectio_7");        
	            }
            }
        }
        logger.debug(LOGIN_LIST,"exiting updateLoginAttemptsCount");
    }
    
    /**
     * function to set Login attempts to zero
     *  <code>LoginListener</code>
     */
    private void setLoginLoginAttemptsCountToZero(User user){
        logger.debug(LOGIN_LIST,"setting LoginLoginAttemptsCountToZero");
        Connection conn = null;
        Statement stmt=null;
        PreparedStatement pstmt = null;
        try{
	        conn = this.getDataSource().getConnection();
	        stmt = conn.createStatement();
	        Integer zero = new Integer("0");
	        pstmt = conn.prepareStatement(QUERY_UPDATE_USER_LOGIN);
	        pstmt.setInt(1, zero);
	        pstmt.setString(2, user.getLoginId());
	        pstmt.executeQuery();
	        //stmt.executeQuery(QUERY_UPDATE_USER_LOGIN+zero+QUERY_USER_ID+ user.getLoginId());
        }catch(SQLException e){
            logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED");
        }finally{
        	if(pstmt!=null){ 
  	            try {
  	            	pstmt.close();
  	            } catch (SQLException sqlException) {
  	                logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED clossing connectio_8");        
  	            }
              }
        	 if(stmt!=null){ 
  	            try {
  	            	stmt.close();
  	            } catch (SQLException sqlException) {
  	                logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED clossing connectio_9");        
  	            }
              }
            if(conn!=null){ 
	            try {
	                conn.close();
	            } catch (SQLException sqlException) {
	                logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED clossing connectio_10");        
	            }
            }
            setLoginId(null);
        }
    }
    
    /**
     * function to get user details
     * @param loginId
     * @return <code>LoginListener</code>
     */
    private User getUserDetails(Integer loginId){
        Connection conn = null;
        ResultSet rs = null;
        Statement stmt =null;
        PreparedStatement pstmt = null;
        User user = new User();
        user.setLoginId(loginId.toString());
        try{
	        conn = this.getDataSource().getConnection();
	        stmt = conn.createStatement(); 
	        pstmt = conn.prepareStatement(QUERY_RET_USER_STATUS);
	        pstmt.setInt(1, loginId);
	        rs = pstmt.executeQuery();
	        if(rs!=null && rs.next()){
	        	// Added Integer.valueOf() to avoid sonar violation on 18-9-2017
	           // user.setLoginAttempts(new Integer(rs.getInt(loginAttemptQueryCount))); 
	        	user.setLoginAttempts(Integer.valueOf(rs.getInt(loginAttemptQueryCount))); 
	        	// Added Integer.valueOf() to avoid sonar violation on 20-9-2017
	          //  user.setStatusId(new Integer(rs.getInt(statusIdQueryCount)));
	        	user.setStatusId(Integer.valueOf(rs.getInt(statusIdQueryCount)));
	            
	            /*Start : Changes for password expiry alert.*/
				Date passwordExpiryDate = null;
				if( !Utils.isEmpty( rs.getDate( DateQueryCount ) ) ){
					passwordExpiryDate = new Date( rs.getDate( DateQueryCount ).getTime() );
				}
				boolean passwordExpired = false;
				if( Utils.isEmpty( passwordExpiryDate ) || ( !Utils.isEmpty( passwordExpiryDate ) && new Date().after( passwordExpiryDate ) ) ){
					passwordExpired = true;	
				}
	            session.setAttribute( P_EXPIRED, passwordExpired );  //SONARFIX -- variable name changed
				/*End : Changes for password expiry alert.*/

	        }
        }catch(SQLException e){
            logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED getting user details");
        }finally{
        	
        	 if(pstmt!=null){ 
 	            try {
 	            	pstmt.close();
 	            } catch (SQLException sqlException) {
 	                logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED clossing connectio_11");        
 	            }
             }
        	
        	 if(stmt!=null){ 
   	            try {
   	            	stmt.close();
   	            } catch (SQLException sqlException) {
   	                logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED clossing connectio_12");        
   	            }
               }
            if(conn!=null){ 
	            try {
	                conn.close();
	            } catch (SQLException sqlException) {
	                logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED clossing connectio_13");        
	            }
            }
            if(rs!=null){ //SONARFIX -- 23-apr-2018
	            try {
	                rs.close();
	            } catch (SQLException sqlException) {
	                logger.debug(LOGIN_LIST,"SQL EXCEPTION OCCURED clossing resultset");        
	            }
            }
        }
        setLoginId(null);
        return user;
    }
    /**
     * Getter method for loginId.
     * @return loginId <code>String</code>
     */
    public static Integer getLoginId() {
        return loginId;
    }
    /**
     * Setter method for loginId
     * @param loginId; <code>String</code>
     */
    public static void setLoginId(Integer loginId) {
        LoginListener.loginId = loginId;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
     */
    @Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        logger.debug(LOGIN_LIST,"sessionCreated(HttpSessionEvent httpSessionEvent)::::"+httpSessionEvent.getSession());
        setSession(httpSessionEvent.getSession());
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
     */
    @Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        logger.debug(LOGIN_LIST,"sessionDestroyed(HttpSessionEvent httpSessionEvent)");
        setSession(null);
    }

    

    /**
     * Getter method for session.
     * @return session <code>HttpSession</code>
     */
    public static HttpSession getSession() {
        return session;
    }
    /**
     * Setter method for session
     * @param session; <code>HttpSession</code>
     */
    public static void setSession(HttpSession session) {
        LoginListener.session = session;
    }
    
   
}
