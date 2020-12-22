/*
 * 
 */
package com.rsaame.pas.referral;

import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.TaskVO;

/*
 * author 1017935
 */
public class ReferralHandler{

	public TaskVO map( HttpServletRequest request, TaskVO taskVO, PolicyVO policyVO, String taskType ){

		Calendar currentDate = Calendar.getInstance();

		//taskVO.setAssignedTo(request.getParameter("referralAssignTo"));
		taskVO.setAssignedTo( request.getParameter( "assignToId" ) );

		//taskVO.setAssignedBy(request.getParameter("assignedBy"));
		taskVO.setAssignedBy( ( (UserProfile) taskVO.getLoggedInUser() ).getRsaUser().getUserId().toString() );
		taskVO.setCategory( Utils.getSingleValueAppConfig( "TASK_REFERRAL_CATEGORY" ) );
		//taskVO.setCreatedBy(policyVO.getCreatedBy());
		//taskVO.setCreatedBy(request.getParameter("assignedBy"));
		taskVO.setCreatedBy( ( (UserProfile) taskVO.getLoggedInUser() ).getRsaUser().getUserId().toString() );
		//Radar fix
		//String comments = request.getParameter( "referralCommentId" );
		taskVO.setCreatedOn( policyVO.getCreatedOn() );
		if( taskType.equalsIgnoreCase( "onDemand" ) ){
			taskVO.setDesc( request.getParameter( "taskDescription" ) );
		}
		else{
			taskVO.setDesc( policyVO.getConCatRefMsgs() );
		}
		taskVO.setCreatedDate( currentDate.getTime() );

		currentDate.add( Calendar.DAY_OF_MONTH, 30 );
		taskVO.setDueDate( currentDate.getTime() );

		taskVO.setLocation( request.getParameter( "referralLocId" ) );
		taskVO.setLoggedInUser( ( policyVO.getLoggedInUser() ) );
		taskVO.setPolicyType( Utils.getSingleValueAppConfig( "POLICY_TYPES" ) );

		taskVO.setPolEndId( AppUtils.getLatestEndtId( policyVO ) );

		taskVO.setPolLinkingId( policyVO.getPolLinkingId() );
		
		if(!Utils.isEmpty(  policyVO.getPolicyNo()  )){
			taskVO.setPolicyNo( policyVO.getPolicyNo() );
		}
		taskVO.setQuoteNo( policyVO.getQuoteNo() );

		taskVO.setPriority( Utils.getSingleValueAppConfig( "TASK_DEFAULT_PRIORITY" ) );
		taskVO.setStatus( Utils.getSingleValueAppConfig( "TASK_DEFAULT_STATUS" ) );

		if( !policyVO.getIsQuote() ){
			taskVO.setTaskName( policyVO.getPolicyNo() + " is referred" );
		}
		else{
			taskVO.setTaskName( policyVO.getQuoteNo() + " is referred" );
		}

		taskVO.setQuote( policyVO.getIsQuote() );

		if( ( policyVO.getAppFlow() == Flow.AMEND_POL ) ){
			taskVO.setTaskType( Utils.getSingleValueAppConfig( "TASK_TRAN_TYPE_ENDORSEMENT" ) );

			/*Below changes has been made to set endorsementId in TaskVO. Earlier we were setting endorsementId as NewEndtId from PolicyVO.
			 * Now , we are checking if there is any record exist for current endorsement, then only set the  endorsementId in TaskVO
			 * as NewEndtId from PolicyVO else set it as EndtId from PolicyVO. It is required if user goes for an endorsement but does not 
			 * change anything   */
			DataHolderVO<Boolean> output = (DataHolderVO<Boolean>) TaskExecutor.executeTasks( "IS_ENDORSEMENT_RECORD_EXIST", policyVO );
			Boolean isEndorsementRecordExist = false;
			if( !Utils.isEmpty( output ) ){
				isEndorsementRecordExist = output.getData();
			}
			if( isEndorsementRecordExist )
				taskVO.setPolEndId( policyVO.getNewEndtId() );
			else
				taskVO.setPolEndId( policyVO.getEndtId() );
		}
		else
			taskVO.setTaskType( Utils.getSingleValueAppConfig( "TASK_TRAN_TYPE_QUOTE" ) );

		return taskVO;
	}

}
