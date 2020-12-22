package com.rsaame.pas.kaizen.bpm;

import java.util.HashMap;
import java.util.Map;

import com.cognizant.businessprocess.framework.engine.BusinessProcessEngine;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.constants.CommonErrorKeys;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.exception.InsufficientAuthorizationException;
import com.rsaame.kaizen.framework.exception.ServiceException;
import com.rsaame.kaizen.framework.model.BaseAMEBO;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.kaizen.framework.servicewrapper.ServiceWrapper;
import com.rsaame.kaizen.framework.util.AuthorizationUtil;
import com.rsaame.kaizen.framework.util.BeanFactory;
import com.rsaame.pas.svc.cmn.IPASTaskDelegate;
import com.rsaame.pas.svc.constants.ServicesErrorKeys;
import com.rsaame.pas.svc.constants.SvcConstants;

/**
 * This class makes a call to Kaizen's FGBPM to invoke a business function or service.
 */
public class FGBPMCaller implements IPASTaskDelegate{
	private static final Logger logger = Logger.getLogger( FGBPMCaller.class );
	private static final String LOG_DETAIL_METHOD_NAME = "IPASTaskDelegate.execute()";
	private static final String SERVICE_CTX_USER = "USER";
	private static final String OP_INDICATOR_INPUT = "InputAsOutput";
	private static final String OP_INDICATOR_OUTPUT = "NewOutput";
	private static final String ACTION_IS = " Action is ";
	public BaseVO execute( String identifier, BaseVO input ){
		
		BaseVO response = null;

		try{
			System.out.println("logger -->"+logger);
			response = executeFGBPMCall( identifier, input );
		}
		catch( ServiceException e ){
			throw new SystemException( "", e, "" );
		}

		return response;
	}

	private BaseVO executeFGBPMCall( String identifier, BaseVO input ) throws ServiceException{
		long startTime = System.currentTimeMillis();

		Object responseObject = null;
		BaseVO output = null;
		try{
			// 1. Call OXM to convert the xml to the object /* ** Not required for PAS. ** */
			
			int userId = 0;
			String userToStringValue = null;

			if( ServiceContext.getUser() != null && ServiceContext.getUser().getUserId() != null ){
				userId = ServiceContext.getUser().getUserId().intValue();
				userToStringValue = ServiceContext.getUser().toString();
			}

			/* Check from appconfig.properties if service method authorization check is required. If yes, authorize, else skip. */
			boolean methodAuthorizationIsRequired = Utils.toDefaultFalseBoolean( Utils.getSingleValueAppConfig( SvcConstants.APP_CONFIG_KEY_METHOD_AUTHORIZATION_ENABLED ) );
			if( methodAuthorizationIsRequired ){
				AuthorizationUtil auth = new AuthorizationUtil();
				if( !auth.isUserAuthorizedForFunction( identifier, input ) ){
					throw new InsufficientAuthorizationException();
				}
				else if( ServiceContext.getUser().getProfile().equalsIgnoreCase( "broker" ) && !auth.matchBrokerIdInRequest( identifier, input ) ){
					throw new InsufficientAuthorizationException();

				}
			}
			
			/* Get the FGBPM invocation configuration. If not found, consider fatal. */
			String[] fgbpmConfig = Utils.getMultiValueAppConfig( Utils.concat( identifier, "_FGBPM_PROPS" ) );
			if( Utils.isEmpty( fgbpmConfig ) || fgbpmConfig.length != 7 ){
				String message = Utils.concat( 
					"The configurations for FGBPM calls for identifer [", identifier, 
					"] were not found. The expected configuration is: <Product_Name>,<Version>,<Process_Name>,<RequestBOKey>,<Bus_Func_Input_Class_Name>,<Bus_Func_Output_Class_Name>,<Output Indicator Input|NewOutput>" );
				if(!Utils.isEmpty(logger)){
					if( logger.isError() ) logger.error( message );
					throw new SystemException( ServicesErrorKeys.FGBPM_CONFIG_NOT_FOUND, null, message );
				}	
				if(!fgbpmConfig [ 6 ].equalsIgnoreCase(OP_INDICATOR_INPUT) || fgbpmConfig [ 6 ].equalsIgnoreCase(OP_INDICATOR_OUTPUT) )
				{
					message = "Output Indicator must be one of these value <Input|NewOutput>";
				}
				if(!Utils.isEmpty(logger)){
					if( logger.isError() ) logger.error( message );
				}
				throw new SystemException( ServicesErrorKeys.FGBPM_CONFIG_NOT_FOUND, null, message );
			}
			
			String productName = fgbpmConfig[ 0 ];
			String version = fgbpmConfig[ 1 ];
			String processName = fgbpmConfig[ 2 ];
			String requestBOKey = fgbpmConfig[ 3 ];
			String fgbpmBFInputClassName = fgbpmConfig[ 4 ];
			String fgbpmBFOutputClassName = fgbpmConfig [ 5 ];
			String  opIndicator = fgbpmConfig [ 6 ];
			
			/* Map and create the input for the FGBPM call. */
			Object fgbpmInput = BeanMapper.map( input, Utils.loadClass( fgbpmBFInputClassName ) );

			// 2. Invoke FineGrainedBPM engine
			Map inputParamsForFGBPM = new HashMap();
			if( input != null ){
				inputParamsForFGBPM.put( requestBOKey, fgbpmInput );
			}
			
			
			if(!Utils.isEmpty(logger)){
					if( logger.isInfo() ) logger.info( LOG_DETAIL_METHOD_NAME, String.valueOf( startTime ), ACTION_IS, identifier, " Current user is ::", userToStringValue );
			}
			
			
			if( ServiceContext.getUser() != null ){
				inputParamsForFGBPM.put( SERVICE_CTX_USER, ServiceContext.getUser() );
			}

			BeanFactory factory = BeanFactory.getInstance();

			BusinessProcessEngine businessProcessEngine = (BusinessProcessEngine) factory.getBean( "businessProcessEngineImpl" );
			responseObject = businessProcessEngine.execute( productName, processName, version,
					(HashMap) inputParamsForFGBPM );

			if(!Utils.isEmpty(logger)){
				logger.info( LOG_DETAIL_METHOD_NAME, startTime + ACTION_IS + identifier + " ResponseObject ::" + responseObject );
			}
			
			
			if( responseObject != null ){
				
				/*if( ServiceContext.getUser().getProfile().equalsIgnoreCase( "broker" ) && !auth.matchBrokerIdInResponse( identifier, responseObject ) ){
					throw new InsufficientAuthorizationException();
				}*/ /* ** Not required for PAS. This has already been done above. ** */
				
				if( responseObject instanceof BaseAMEBO ){
					
					if(!Utils.isEmpty(logger)){
						logger.info( LOG_DETAIL_METHOD_NAME, startTime + ACTION_IS + identifier + " Instance of AMEBaseBO.." );
					}
					BaseAMEBO responseObj = (BaseAMEBO) responseObject;

					if(!Utils.isEmpty(logger)){
						logger.info( LOG_DETAIL_METHOD_NAME, startTime + ACTION_IS + identifier  );
					}
					if( responseObj.getMessages() != null && responseObj.getMessages().length > 0 ){
						/* TODO PAS: Throw BusinessException with the error code list. */
						// FLAG THE MESSAGES
						if(!Utils.isEmpty(logger)){
							logger.info( LOG_DETAIL_METHOD_NAME, startTime + ACTION_IS + identifier + " Setting the flag.." );
						}
						if( ServiceContext.getUser() != null ){
							ServiceContext.setMessage( ServiceContext.MSG_EXCEPTION );
						}
					}
				}
				
				/* Commented for Testing on 23/01/2012 to be uncommented once the reverse mapping is ready - begins */
				/* Map responseObj to a BaseVO instance. */
				
				if(opIndicator.equalsIgnoreCase(OP_INDICATOR_INPUT))
				{
					output = (BaseVO) BeanMapper.map( responseObject,input);
				}
				if(opIndicator.equalsIgnoreCase(OP_INDICATOR_OUTPUT))
				{
					output = (BaseVO) BeanMapper.map( responseObject, Utils.loadClass( fgbpmBFOutputClassName ) );
				}
				
				
				/* Commented for Testing on 23/01/2012 to be uncommented once the reverse mapping is ready - ends */
				
				// 3. Call XML to return the xml response /* ** Not required for PAS. ** */
				if(!Utils.isEmpty(logger)){
					logger.info( LOG_DETAIL_METHOD_NAME, "Performance LOG:::: " + startTime + " Action :: " + identifier + "| Current User :: " + userId + " | Time Taken :: "
						+ ( ( System.currentTimeMillis() - startTime ) ) + " MilliSec " /*| Response XML :: " + responseStr */ );
				}
			}

		}
		catch( Throwable e ){
			if(!Utils.isEmpty(logger)){
				logger.info( LOG_DETAIL_METHOD_NAME, startTime + " Process failed :: Time taken for " + identifier + " ::" + ( ( System.currentTimeMillis() - startTime ) )
					+ " MilliSec" );
			}
			e.printStackTrace();

			if( e instanceof ServiceException ){
				/* TODO 
				 * (a) Throw SystemException. 
				 * (b) Figure out a way to map (or recognize) Kaizen error codes in PAS. */
				throw new SystemException( CommonErrorKeys.UNKNOWN_ERROR, e, "ServiceException from FGBPM call." );
			}
			else{
				throw new SystemException( CommonErrorKeys.UNKNOWN_ERROR, e, "Throwable from FGBPM call." );
			}
		}
		
		return (BaseVO) output;
	}
}
