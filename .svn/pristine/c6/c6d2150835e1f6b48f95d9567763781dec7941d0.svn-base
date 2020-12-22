package com.rsaame.pas.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mindtree.ruc.cmn.base.BaseException;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.cache.CacheManagerFactory;
import com.mindtree.ruc.cmn.constants.CommonErrorKeys;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.FormattedResponse;
import com.mindtree.ruc.mvc.IOpType;
import com.mindtree.ruc.mvc.IResponseFormatter;
import com.mindtree.ruc.mvc.MVCUtils;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.constants.Constants;
import com.mindtree.ruc.mvc.init.RequestInitExecutor;
import com.mindtree.ruc.mvc.init.WebInitExecutor;
import com.mindtree.ruc.mvc.servlet.BaseServlet;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.ForgotPwdDetailsVO;
import com.rsaame.pas.vo.app.NoticeBoardVO;
import com.rsaame.pas.vo.bus.PARUWDetailsVO;

/**
 * Servlet implementation class PASServlet
 */
public class ForgotPasswordServlet extends BaseServlet{
	private static final Logger logger = Logger.getLogger( ForgotPasswordServlet.class );
	private static final long serialVersionUID = 1L;
	private static boolean initialized = false;
	//private static final String ForgotPasswordServlet = "ForgotPasswordServlet";  //SONARFIX -- 25-apr-2018 -- commented as this stmmt is not useful
	
	//protected final int REDIRECTION = 1; 			//SONARFIX -- 08-04-2019 -- commented as this stmmt is not useful
	//protected final int CONTENT_TYPE = 2;
    private static HttpSession session;
    
    private final String FORGOT_ERROR = "ForgotPwdERROR";


	/**
	 * Used to initialize the application using web-init tasks configured in the application configuration.
	 * 
	 * @see WebInitExecutor
	 */
	@Override
	public void init( ServletConfig config ) throws ServletException{
		super.init( config );
		
		/* Commented by Anveshan: No need to initialize application context again. This is creating an exception when initializing scoped proxy bean*/
		//initialize( config );
	}

	private void initialize( ServletConfig config ){
		if( !initialized ){
			WebInitExecutor.execute( config );
			initialized = true;
		}
	}
	
	/**
	 * This method checks for the operation type and calls the right request handler.
	 * 
	 * @param request
	 * @param response
	 */
	protected void handleRequest( HttpServletRequest request, HttpServletResponse response ){
	/**
	 * Calls {@link PolicyContext}.endTransaction() if the request handler execution didn't throw any exception.
	 */
		/* The following is the request handling process:
		 * (a) Initialise request specific parameters/attributes like character encoding, remote user agent, etc.
		 * (b) Retrieve the operation type from the HTTP request.
		 * (c) Figure out the request handler (IRequestHandler implementation class) for the operation and invoke it.
		 * (d) Handle the redirection, if the request handler has set any. If there is no redirection, the servlet
		 *     directly responds. The response data in the Response object returned by the request handler is used in 
		 *     the formation of the response.    
		 */
		FormattedResponse formattedResponse = null;
		Response handlerResponse = null;
		IOpType operationType = null;
		boolean rhThrewException = false;
		ForgotPwdDetailsVO detailsVO =null;
		try{
			/*
			 * (a) Initialise request specific parameters/attributes. It is
			 * important that this succeeds because the value of the
			 * initialisation parameters may affect the request handling. Hence,
			 * this has been added to the try-catch block.
			 */
			RequestInitExecutor.execute(request);

			// call service DAO for forgot password -- handlerResponse =

			if (request.getParameter("j_username") != null) {
				System.out.println(request.getParameter("j_username") + " "
						+ request.getParameter("LOCATION") + " "
						+ request.getParameter("forgotPwdDOB") + " "
						+ request.getParameter("lastName"));
			}
			session = request.getSession(false);

			detailsVO = BeanMapper.map(request,
					ForgotPwdDetailsVO.class);

			detailsVO = (ForgotPwdDetailsVO) TaskExecutor.executeTasks(
					"FORGOT_PASSWORD", detailsVO);

			AppUtils.sendMailForPasswordChange(detailsVO);
			
			//logger.debug(ForgotPasswordServlet + "handleRequest()","Password generated");  //SONARFIX -- 25-apr-2018 -- commented as this stmmt is not useful

		}
		catch( BaseException be ){
			if( logger.isError() ){
				logger.error( be, "Exception during request processing" );
			}
			
			/* Set the exception flag to true. This may be required for post-processing by the implementing servlet. */
			rhThrewException = true;

			/* Set the error messages for display. */
			resolveErrors( request, response, be );
		}
		/*catch( Throwable anyOtherThrowable ){*/	/* replaced Throwable catch with Exception catch - sonar violation fix */
		catch( Exception anyOtherThrowable ){
			if( logger.isError() ){
				logger.error( anyOtherThrowable, "Unknown error during request processing" );
			}

			/* Set the exception flag to true. This may be required for post-processing by the implementing servlet. */
			rhThrewException = true;

			/* Should resolve to "Unknown error". */
			resolveErrors( request, response, anyOtherThrowable );
		}
		finally{
			/* In this finally block, we will handle 2 things:
			 * (a) Any request-level pre-closure activities
			 * (b) The response creation and writing */
			try{
				/* Call request-level pre-closure activities. */
				postProcessing( request, response, rhThrewException );
				
				if(!rhThrewException){
					String userEmailId = detailsVO.getEmailAddress()!=null?detailsVO.getEmailAddress():" "; // Adv#ticket:128666
					request.setAttribute("forgotSuccessMsg","\"Password Reset Successfully & the new password is sent to your email " + userEmailId+"\".");
				}
					Redirection redirection = new Redirection(Utils.getSingleValueAppConfig("FORGOT_PASSWORD_UPD"),Redirection.Type.TO_JSP);
					handlerResponse = new Response();
					handlerResponse.setRedirection(redirection);
					handleRedirection( handlerResponse, request, response );
				
				
				/*processOutput: {
					if( Utils.isEmpty( handlerResponse ) ){
						 TODO Need to bring in a default output here. 
						handlerResponse = new Response();
						
						 Since handlerResponse was null, it can be assumed that it was an error scenario. Otherwise, we would
						 * have got an instance of Response. Hence, set Response.success to "false". 
						handlerResponse.setSuccess( Boolean.FALSE );
					}

					else if( !Utils.isEmpty( handlerResponse ) && !Utils.isEmpty( handlerResponse.getRedirection() ) ){
						 Check if there is redirection requested. If yes, then redirect and stop processing
						 * here. 
						handleRedirection( handlerResponse, request, response );
						break processOutput;
					}
					//else{
						String[] resNavVal = Utils.getMultiValueAppConfig( Constants.APP_CONFIG_RES_NAV_PREFIX
								+ operationType.name() );
	
						//Step2: see any redirection specified
						Redirection redirection = MVCUtils.getConfiguredRedirection( operationType, request );
					

					Step3: If both contentType and configured redirection are there, contentTypeSettingTakesPriority() decide
						     which takes priority 
					if( !Utils.isEmpty( redirection ) && responsePriority( request, response, handlerResponse, redirection ) == REDIRECTION )
					{
						handlerResponse.setRedirection( redirection );
						handleRedirection( handlerResponse, request, response );
					}
					else{
						if( Utils.isEmpty( handlerResponse.getResponseType() ) ){
							//get from properties file
							handlerResponse.setResponseType( MVCUtils.getResType( resNavVal ) );
							
							if(logger.isDebug()){
								logger.debug( "[[From Config::]["+operationType + " : " + handlerResponse.getResponseType()+"]]" );
							}
						}

						 If handleRedirection() has returned, then process the output to the browser. 
						formattedResponse = processOutput( handlerResponse, request, response );

						if( !Utils.isEmpty( formattedResponse ) ){
							 (1) Set content type for the response 
							response.setContentType( formattedResponse.getContentType() );

							 (2) If the response formatter or the request handler have set any response headers, set them to 
							 *     the response. 
							Map<String, String> responseHeader = formattedResponse.getResponseHeader();

							if( !Utils.isEmpty( responseHeader ) ){

								for( String key : responseHeader.keySet() ){
									response.setHeader( key, responseHeader.get( key ) );
								}
							}

							 (3) Write the data to output response:
							 *     - If the content is binary (a file, may be) use OutputStream.write().
							 *     - If the content is character-based, use OutputStream.print().
							 
							ServletOutputStream op = response.getOutputStream();
							if( formattedResponse.isBinaryContent() ){
								op.write( formattedResponse.getBinaryData() );
							}
							else{
								op.print( formattedResponse.getFormattedData() );
							}

							op.flush();
							op.close();
						}
					}
				}*/
			}
			catch( BaseException e ){
				/* TODO Figure out the response in this case. */
				logger.error( e, "Got application exception while processing output" );
				resolveErrors( request, response, e );
			}
		}	
		
	}
	private FormattedResponse processOutput( Response handlerResponse, HttpServletRequest request, HttpServletResponse response ){
		/* First check if there are error messages to be shown. */
		resolveErrors( request, response, handlerResponse.getErrorKeys() );
		
		String rhBeanName = Utils.getSingleValueAppConfig( 
								Utils.concat( Constants.APP_CONFIG_RES_FORMATTER_PREFIX, handlerResponse.getResponseType().name() ) 
							);
		
		FormattedResponse formattedResponse = null;
		if( !Utils.isEmpty( rhBeanName ) ){
			IResponseFormatter iResponseFormatter = (IResponseFormatter) Utils.getBean( rhBeanName );
			formattedResponse = iResponseFormatter.execute( handlerResponse, request, response );
		}
		
		return formattedResponse;
	}

	
	/**
	 * This method takes all the error codes in the exception and adds it to the request for display.
	 * 
	 * @param request
	 * @param response
	 */
	private void resolveErrors( HttpServletRequest request, HttpServletResponse response, Throwable t ){
		/* Get the error code list from the exception, iterate through it and set the message
		 * for each code in the request. */
		String errorKey = null;
		
		resolveErrorsBlock: {
			if( t instanceof BaseException ){
				BaseException be = (BaseException) t;
	
				java.util.List<String> errorKeys = be.getErrorKeysList();
				
				/* If there are no error keys set, set UNKNOWN_ERROR. */
				if( Utils.isEmpty( errorKeys ) ){
					resolveError( request, response, CommonErrorKeys.UNKNOWN_ERROR );
					break resolveErrorsBlock;
				}
	
				/* There are error keys set. Set all of them to "errorMessages" request parameter. */
				resolveErrors( request, response, errorKeys );
	
			}
			else{
				/* If it is any other type of exception that has reached the servlet (ideally it shouldn't have),
				 * set UNKNOWN_ERROR. */
				resolveError( request, response, CommonErrorKeys.UNKNOWN_ERROR );
			}
		}
	}

		/**
		 * This method gets the handlerResponse object and based on redirection type, redirects accordingly.
		 * @param handlerResponse
		 * @param request
		 * @param response
		 */
		private void handleRedirection( Response handlerResponse, HttpServletRequest request, HttpServletResponse response ){
			/* The request handler may have set some messages. These must be set to the request before the request is forwarded to
			 * another JSP or operation. */
			resolveErrors( request, response, handlerResponse.getErrorKeys() );

			String url = null;
			switch( handlerResponse.getRedirection().getType() ){
				case TO_JSP:
					url = handlerResponse.getRedirection().getUrl();
					break;
				case TO_NEW_OPERATION:
					url = Utils.concat( request.getServletPath(), "?opType=", handlerResponse.getRedirection().getUrl() );
					break;
				default:
			}
			
			if( !Utils.isEmpty( url ) ){
				forwardRequest( url, request, response );
			}
		}
		private void forwardRequest( String url, HttpServletRequest request, HttpServletResponse response ){
			try{
				RequestDispatcher dispatcher = request.getRequestDispatcher( url );
				dispatcher.forward( request, response );
			}
			catch( ServletException e ){
				throw new SystemException( e, "ServletException when trying to forward request" );
			}
			catch( IOException e ){
				throw new SystemException( e, "IOException when trying to forward request" );
			}
		}
		/**
		 * This method takes all the error codes passed and adds them to the request.
		 * 
		 * @param request
		 * @param response
		 */
		private void resolveErrors( HttpServletRequest request, HttpServletResponse response, java.util.List<String> errorKeys ){
			if( Utils.isEmpty( errorKeys ) ){
				return;
			}

			for( String errorKey : errorKeys ){
				resolveError( request, response, errorKey );
			}
			
		}
		/**
		 * This method adds a single error message to the message map in the request.
		 * 
		 * @param request
		 * @param response
		 */
		private void resolveError( HttpServletRequest request, HttpServletResponse response, String errorKey ){
			if( Utils.isEmpty( errorKey ) ){
				return;
			}

			String errorMessage = Utils.getAppErrorMessage( errorKey );
			addToRequestErrorMessagesMap( request, response, errorKey, errorMessage );
		}
		private void addToRequestErrorMessagesMap( HttpServletRequest request, HttpServletResponse response, String errorKey, String errorMessage ){
			@SuppressWarnings( "unchecked" )
			java.util.Map<String, String> errorMessagesMap = (java.util.Map<String, String>) request.getAttribute( Constants.REQ_PARAM_GLOBAL_ERROR_MESSAGES );
			if( Utils.isEmpty( errorMessagesMap ) ){
				errorMessagesMap = new java.util.HashMap<String, String>();
			}

			errorMessagesMap.put( errorKey, errorMessage );
			request.setAttribute(FORGOT_ERROR,errorMessage);
			request.setAttribute( Constants.REQ_PARAM_GLOBAL_ERROR_MESSAGES, errorMessagesMap );
		}
	@Override
	protected void postProcessing( HttpServletRequest request, HttpServletResponse response, boolean rhThrewException ){
		/* End PolicyContext transaction here. This is being done here to handle cases of exceptions being thrown after the transaction
		 * was started but before it could be committed. However, if there was no exception from the request handler, then we should 
		 * keep the transaction open, so that the next call can take the saved data. */
		if( rhThrewException ){
			PolicyContext pc = PolicyContextUtil.getPolicyContext( request );
			if( !Utils.isEmpty( pc ) ) pc.endTransaction();
		}
		
		/* Clear ThreadLevelContext completely. The purpose of ThreadLevelContext is for communication within a request. */
		ThreadLevelContext.clearAll();
	}
}
