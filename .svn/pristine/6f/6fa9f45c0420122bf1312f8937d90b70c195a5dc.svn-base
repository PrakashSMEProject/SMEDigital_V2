package com.rsaame.pas.svc.cmn;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.task.ITask;
import com.mindtree.ruc.cmn.task.TaskOutput;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.svc.constants.ServicesErrorKeys;

/**
 * This task executes a service using Kaizen's FGBPM tool.
 */
public class PASFGBPMServiceTask implements ITask{

	@Override
	public TaskOutput execute( String identifier, String[] taskInputConfig, BaseVO input ){
		if( Utils.isEmpty( taskInputConfig ) ){
			throw new SystemException( ServicesErrorKeys.FGBPM_SERVICE_TASK_CONFIG_INCOMPLETE, null, "IPASTaskDelegate implementation not configured" );
		}
		
		/* The configuration is expected to the fully qualified name of the class that implements IPASTaskDelegate. */
		String taskDelegateClassName = taskInputConfig[ 0 ];
		IPASTaskDelegate delegate = (IPASTaskDelegate) Utils.newInstance( Utils.loadClass( taskDelegateClassName ) );
		
		BaseVO returnValue = delegate.execute( identifier, input );
		
		TaskOutput output = new TaskOutput();
		output.setSuccessful( true );
		output.setTaskOutput( returnValue );
		
		return output;
	}

}
