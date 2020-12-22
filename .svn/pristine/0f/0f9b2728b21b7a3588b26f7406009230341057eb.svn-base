package com.rsaame.pas.svc.cmn;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.ITask;
import com.mindtree.ruc.cmn.task.TaskOutput;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.svc.constants.ServicesErrorKeys;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.TaskVO;

public class PASServiceTask implements ITask{
	private static final Logger logger = Logger.getLogger( PASServiceTask.class );

	@Override
	public TaskOutput execute( String identifier, String[] taskInputConfig, BaseVO input ){
		TaskOutput to = new TaskOutput();

		preProcessing( input );

		taskExecution: {
			/* If there is no configuration passed or has been set as "NOT_REQUIRED", consider the scenario successful. */
			if( Utils.isEmpty( taskInputConfig ) || SvcConstants.APP_CONFIG_SERVICE_NOT_REQ.equalsIgnoreCase( taskInputConfig[ 0 ] ) ){
				to.setSuccessful( true );
				break taskExecution;
			}

			/* There are two configurations expected in the */
			if( taskInputConfig.length < 2 ){
				String message = Utils.concat( "Configuration for SERVICE task is incomplete for identifier [", identifier, "]" );

				if( logger.isError() ) logger.error( message );
				throw new SystemException( ServicesErrorKeys.SERVICE_TASK_CONFIG_INCOMPLETE, null, message );
			}

			/* (i)   The first item in the array should be the Spring bean name for the service.
			 * (ii)  The second item in the array will be the method to be invoked in the service class. 
			 * 
			 * Invoke the service method and set the output to the TaskOutput instance. */
			BaseVO output = invokeMethod( taskInputConfig[ 0 ], taskInputConfig[ 1 ], input );
			to.setTaskOutput( output );
		}

		postProcessing( input );

		return to;
	}

	/**
	 * Any pre-processing
	 * @param input
	 */
	private void preProcessing( BaseVO input ){
		if( input instanceof PolicyVO ){
			PolicyVO p = (PolicyVO) input;
			TaskVO taskDetails = p.getTaskDetails();

			ThreadLevelContext.set( com.Constant.CONST_APP_FLOW_PRE_SVC, p.getAppFlow() );
			
			/* In the case of referrals, we need to send one of EDIT_QUO, VIEW_QUO, AMEND_POL or VIEW_POL to the service layer based
			 * on whether the logged in user is the initiator of the */
			if( !Utils.isEmpty(p.getAppFlow()) && p.getAppFlow().equals( Flow.RESOLVE_REFERAL ) ){
				UserProfile user = (UserProfile) p.getLoggedInUser();

				if( p.getIsQuote() ){
					if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getAssignedTo() ) ) ){
						p.setAppFlow( Flow.EDIT_QUO );
					}
					else{
						p.setAppFlow( Flow.VIEW_QUO );
					}
				}
				else{
					p.setAppFlow( Flow.VIEW_POL );
					if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getAssignedTo() ) )
							&& p.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( "POLICY_REFERRED" ) ) ) ){
						p.setAppFlow( Flow.AMEND_POL );
					}
					else if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getAssignedTo() ) )
							&& p.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( "POLICY_ACCEPT" ) ) ) ){
						p.setAppFlow( Flow.VIEW_POL );
					}
					else if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getCreatedBy() ) )
							&& p.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( "POLICY_ACCEPT" ) ) ) ){
						p.setAppFlow( Flow.AMEND_POL );
					}
					else if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getCreatedBy() ) )
							&& p.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( "POLICY_REFERRED" ) ) ) ){
						p.setAppFlow( Flow.VIEW_POL );
					}
				}
			}
			/*
			 * Policy status is added to ThreadLevelContext and is mainly used in DAO's to check whether database records with status 4 (Policy Cancelled/ Location deleted) are fetched or not.
			 */			
			ThreadLevelContext.set( SvcConstants.POLICY_STATUS_CODE , p.getStatus() );			
			
		}
		else if( input instanceof LoadExistingInputVO ){
			LoadExistingInputVO loadExistingInputVO = (LoadExistingInputVO) input;
			if( loadExistingInputVO.getAppFlow().equals( Flow.RESOLVE_REFERAL ) ){	
				ThreadLevelContext.set( com.Constant.CONST_APP_FLOW_PRE_SVC, loadExistingInputVO.getAppFlow() );
				if( !loadExistingInputVO.isQuote() ){
					loadExistingInputVO.setAppFlow( Flow.VIEW_POL);
				}
				else{
					loadExistingInputVO.setAppFlow( Flow.VIEW_QUO );
				}
			}
			/*
			 * Policy status is added to ThreadLevelContext and is mainly used in DAO's to check whether database records with status 4 (Policy Cancelled/ Location deleted) are fetched or not.
			 */	
			ThreadLevelContext.set( SvcConstants.POLICY_STATUS_CODE , loadExistingInputVO.getPolicyStatus() );			
			
			}
	}

	private void postProcessing( BaseVO input ){
		if( input instanceof PolicyVO ){
			PolicyVO p = (PolicyVO) input;

			if( !Utils.isEmpty( ThreadLevelContext.get( com.Constant.CONST_APP_FLOW_PRE_SVC ) ) ){
				p.setAppFlow( (Flow) ThreadLevelContext.get( com.Constant.CONST_APP_FLOW_PRE_SVC ) );
				ThreadLevelContext.clear( com.Constant.CONST_APP_FLOW_PRE_SVC );				
			}
		}
		else if( input instanceof LoadExistingInputVO ){
			LoadExistingInputVO loadExistingInputVO = (LoadExistingInputVO) input;
			if( !Utils.isEmpty( ThreadLevelContext.get( com.Constant.CONST_APP_FLOW_PRE_SVC ) ) ){
				loadExistingInputVO.setAppFlow( (Flow) ThreadLevelContext.get( com.Constant.CONST_APP_FLOW_PRE_SVC ) );
				ThreadLevelContext.clear( com.Constant.CONST_APP_FLOW_PRE_SVC );
			}
		}
		
		if( !Utils.isEmpty( ThreadLevelContext.get( "POLICY_STATUS" ) ) ){
			ThreadLevelContext.clear( "POLICY_STATUS" );				
		}
		
	}

	/**
	 * This method invokes the service method based on the configuration.
	 * @param serviceName
	 * @param methodName
	 * @param input
	 * @return svcResponse
	 */
	private BaseVO invokeMethod( String serviceName, String methodName, BaseVO input ){
		logger.debug( "Invoking service " + serviceName + "method " + methodName );
		String temp1 = serviceName + SvcUtils.getBeanNameSuffix( input );
		/* Based on the serviceName passed, get an instance of the service bean from the application context */
		BaseService svc = (BaseService) Utils.getBean( temp1 );
		logger.debug( "Service class name: " + svc.getClass().getSimpleName());
		return (BaseVO) svc.invokeMethod( methodName, input );
	}
	
	public BaseVO invokeMethodFromBatch( String serviceName, String methodName, BaseVO input ) {
		return invokeMethod(serviceName,methodName,input);
	}

}
