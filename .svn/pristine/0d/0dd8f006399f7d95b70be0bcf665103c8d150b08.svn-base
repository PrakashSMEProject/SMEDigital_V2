package com.rsaame.pas.svc.cmn;

import com.mindtree.ruc.cmn.task.ITask;
import com.mindtree.ruc.cmn.task.ITaskEnum;
import com.mindtree.ruc.cmn.task.ValidationTask;

public enum PASTask implements ITaskEnum{
	FGBPMSERVICE( PASFGBPMServiceTask.class ),
	SERVICE( PASServiceTask.class ),
	VALIDATION( ValidationTask.class ), RATING ( PASRatingTask.class ),
	RULES ( PASRulesTask.class );
	
	private Class<? extends ITask> taskClass;
	
	private PASTask( Class<? extends ITask> taskClass ){
		this.taskClass = taskClass;
	}

	@Override
	public Class<? extends ITask> getTaskClass(){
		return this.taskClass;
	}

}
