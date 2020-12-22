package com.rsaame.pas.tasks.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.model.TMasUser;
import com.rsaame.pas.dao.model.TTrnPasReferralDetails;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnTask;
import com.rsaame.pas.dao.model.TTrnTempPasReferral;
import com.rsaame.pas.dao.model.VTaskListPas;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.app.TaskListVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.TaskVO;

/**
 * 
 * @author m1014644
 *
 *	
 */
public class TaskDAO extends BaseDBDAO implements ITaskDAO{

	private static final String GET_TASK_DETAILS = "from TTrnTask task WHERE  task.tskStatus = ?"
			+ "AND task.tskTaskCategory = ? AND task.tskClass = ? AND ( task.tskCreatedBy = ? OR "
			+ "task.id.tskAssignee = ? OR task.id.tskAssignor = ?) order by task.tskCreatedDate desc";

	//Names parameter Query to fetch the task details with LOB name from DB view. Phase 3 changes
	private static final String GET_REF_TASK_DETAILS = "from VTaskListPas task WHERE task.polPolicyType in (:lobs) and task.category=:category and task.status=:status "
			+ "AND (task.createdBy =:taskCreatedBy OR task.assignedBy =:taskAssignedBy OR " + "task.assignTo in (:taskAssignTo) ) order by task.dateCreated desc";

	private static final String GET_TASK_DETAIL = "from TTrnTask task where task.tskId=?";

	private static final String GET_LOCATION_CODE = "from TMasUser user where user.userId=?";

	private static final String GET_REFERAL_TEXT = "from TTrnTempPasReferral referal WHERE referal.id.tprPolLinkingId=? ";
	
	private static final String GET_REFERAL_INFO = "from TTrnTempPasReferral referral WHERE referral.id.tprPolLinkingId=:tprPolLinkingId and referral.tprUserId!=:userId and  referral.tprUserRole!=:role ";	



	private static final String GET_USER_ROLE = "SELECT ROLE_FK,USER_ID_FK FROM T_TRN_USER_ROLE_MAP WHERE ROLE_FK in (";
	
	private final static Logger logger = Logger.getLogger( TaskDAO.class );

	/* (non-Javadoc)
	 * @see com.rsaame.pas.tasks.dao.ITaskDAO#getViewTaskList(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public BaseVO getViewTaskList( BaseVO baseVO ){

		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof TaskVO ){

				Integer userId = SvcUtils.getUserId( baseVO );

				if( Utils.isEmpty( userId ) ){
					throw new BusinessException( "task.UserID.mandatory", null, "User Id is null in TaskDAO" );
				}
				LOB derivedLob = null;
				Integer lob = null;
				TaskVO taskDetail = (TaskVO) baseVO;
				List<TTrnTask> newTask = null;
				List<VTaskListPas> task = null; //New list for holding the view data related to LOB
				Map<Integer, String> statusMap = null;
				Map<Integer, String> categoryMap = null;
				Map<Integer, String> priorityMap = null;
				Integer ctlBrCode = null;
				Byte statusCode = null;
				Byte category = null;
				List<Integer> otherUserid = new ArrayList<Integer>();
				try{

					if( Utils.isEmpty( taskDetail.getStatus() ) || Utils.isEmpty( taskDetail.getCategory() ) ){
						statusCode = Byte.valueOf( Utils.getSingleValueAppConfig( "POLICY_STATUS" ) );
						category = Byte.valueOf( Utils.getSingleValueAppConfig( "POLICY_CATEGORY" ) );
						lob = Integer.valueOf( Utils.getSingleValueAppConfig( "POLICY_TYPES" ) );
					}
					else{
						statusCode = Byte.valueOf( taskDetail.getStatus() );
						category = Byte.valueOf( taskDetail.getCategory() );
						lob = Integer.valueOf( taskDetail.getLob() );
					}
					List<Integer> lobList = new ArrayList<Integer>();

					if( SvcConstants.TRAVEL_POLICY.equals( lob ) ){
						lobList.add( Integer.parseInt( SvcConstants.SHORT_TRAVEL_POL_TYPE ) );
						lobList.add( Integer.parseInt( SvcConstants.LONG_TRAVEL_POL_TYPE ) );
						derivedLob = LOB.TRAVEL;
					}
					else{
						lobList.add( lob );
						if( Integer.valueOf( Utils.getSingleValueAppConfig( "SBS_POLICY_TYPE" ) ).equals( lob ) ){
							derivedLob = LOB.SBS;
						}else if( Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_POLICY_TYPE" ) ).equals( lob ) ){
							derivedLob = LOB.HOME;
						}else if( Integer.valueOf( Utils.getSingleValueAppConfig( "WC_POLICY_TYPE" ) ).equals( lob ) ){
							derivedLob = LOB.WC;
						}
					}
					

					if( category == 4 ){
						//If refferal, query new DB view for task details. Phase 3 changes
							
						String[] allRasUser =     Utils.getMultiValueAppConfig(  "LOB_"+derivedLob.toString())  ; 
						
/*						List<String> sbsRoles =   SvcUtils.copyAsMutableList( Utils.getMultiValueAppConfig(  "LOB_SBS" )  ) ; 
						allRasUser.removeAll( sbsRoles );
						allRasUser.removeAll( Collections.singleton( null ) );*/
						
						if( !Utils.isEmpty( ( (UserProfile) baseVO.getLoggedInUser() ).getRsaUser().getHighestRole( derivedLob.toString() ) ) ){
							Integer currentUserRank = Integer.valueOf( Utils.getSingleValueAppConfig( ( (UserProfile) baseVO.getLoggedInUser() ).getRsaUser().getHighestRole(
									derivedLob.toString() ) ) );
							StringBuilder query = new StringBuilder( GET_USER_ROLE );
							for( String rsaUser : allRasUser ){
								query.append( "?" ).append( "," );
							}
							query.deleteCharAt( query.length() - 1 );
							query.append( ")" );

							List<Object[]> resultList = DAOUtils.getSqlResult( query.toString(), getHibernateTemplate(), allRasUser );

							if( !Utils.isEmpty( resultList ) && resultList.size() > 0 ){
								for( Object[] result : resultList ){
									String otherUsersRole = (String) result[ 0 ];
									if( currentUserRank <= Integer.valueOf( Utils.getSingleValueAppConfig( otherUsersRole.trim() ) ) ){
										otherUserid.add( ( (BigDecimal) result[ 1 ] ).intValue() );
									}
								}
							}

						}

						Query q = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery( GET_REF_TASK_DETAILS );
						q.setParameterList( "lobs", lobList );
						q.setParameter( "category", category );
						q.setParameter( "status", statusCode );
						q.setParameter( "taskCreatedBy", userId );
						q.setParameter( "taskAssignedBy", userId );
						
						List<Integer> allUserid = new ArrayList<Integer>();
						allUserid.add( userId );
						if( !Utils.isEmpty( otherUserid ) && otherUserid.size() > 0){
							allUserid.addAll( otherUserid );
						}
						q.setParameterList( "taskAssignTo", allUserid );
						
						task = q.list();

					}
					else if( category == 2 ){

						newTask = getHibernateTemplate().find( GET_TASK_DETAILS, statusCode, category,
								Byte.valueOf( Utils.getSingleValueAppConfig( "POLICY_TYPES" ) ), userId, userId, userId );

					}
					ctlBrCode = getCtlBrCode( userId );
				}
				catch( org.springframework.dao.DataAccessException exception ){
					throw new BusinessException( "task.details.noaccess", exception, "Unable to get data from the databas_1" );
				}

				List<TaskVO> taskList = new com.mindtree.ruc.cmn.utils.List<TaskVO>( TaskVO.class );

				if( ( !Utils.isEmpty( task ) && task.size() > 0 ) || ( !Utils.isEmpty( newTask ) && newTask.size() > 0 ) ){
					statusMap = getCodeDescMap( "TASK_STATUS" );
					categoryMap = getCodeDescMap( com.Constant.CONST_TASK_CATEGORY );
					priorityMap = getCodeDescMap( "TASK_PRIORITY" );
				}
				com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
				TaskListVO taskListDetails = new TaskListVO();

				if( category == 4 ){
					for( VTaskListPas taskVO : task ){
						if( !Utils.isEmpty( taskVO ) ){
							TaskVO taskdetails = BeanMapper.map( taskVO, TaskVO.class );
							taskdetails.setCreatedBy( getUserName( taskVO.getCreatedBy() ) );
							taskdetails.setAssignedBy( getUserName( taskVO.getAssignedBy() ) );
							taskdetails.setAssignedTo( getUserName( taskVO.getAssignTo() ) );
							taskdetails.setStatus( statusMap.get( converter.getTypeOfA().cast( converter.getAFromB( taskVO.getStatus() ) ) ) );
							taskdetails.setCategory( categoryMap.get( converter.getTypeOfA().cast( converter.getAFromB( taskVO.getCategory() ) ) ) );
							taskdetails.setPriority( priorityMap.get( converter.getTypeOfA().cast( converter.getAFromB( taskVO.getPriority() ) ) ) );
							taskdetails.setTaskID( taskVO.getTaskId() );
							taskdetails.setTaskName( taskVO.getTaskName() );
							taskdetails.setTranType(taskVO.getTransType());
							taskdetails.setDueDate( taskVO.getDueDate() );
							taskdetails.setCreatedDate( taskVO.getDateCreated() );
							taskdetails.setLob( taskVO.getLob() );
							taskdetails.setLocation( !Utils.isEmpty( taskVO.getTskBrCode() ) ? taskVO.getTskBrCode().toString() : ctlBrCode.toString() );
							taskList.add( taskdetails );
							taskListDetails.setTaskVO( taskList );
						}
					}
				}

				if( category == 2 ){
					for( TTrnTask taskVO : newTask ){
						if( !Utils.isEmpty( taskVO ) ){
							TaskVO taskdetails = BeanMapper.map( taskVO, TaskVO.class );
							/*if(!Utils.isEmpty( taskVO.getCtlBrCode() ))
							{
								String locDesc = getLocationDesc(taskVO.getCtlBrCode());
								taskdetails.setLocation( locDesc );
							}*/
							/*Oman multibranching implementation
							if( !Utils.isEmpty( ctlBrCode ) ){
								//String locDesc = getLocationDesc( ctlBrCode );
								taskdetails.setLocation( locDesc);
							}*/
							// For Existing Dubai tasks branch is not stored in database. Use ctlBrCode in that case
							taskdetails.setLocation( !Utils.isEmpty( taskVO.getTskBrCode() ) ? taskVO.getTskBrCode().toString() : ctlBrCode.toString() );
							taskdetails.setCreatedBy( getUserName( taskVO.getTskCreatedBy() ) );
							taskdetails.setAssignedBy( getUserName( taskVO.getTskAssignor() ) );
							taskdetails.setAssignedTo( getUserName( taskVO.getTskAssignee() ) );
							taskdetails.setStatus( statusMap.get( converter.getTypeOfA().cast( converter.getAFromB( taskVO.getTskStatus() ) ) ) );
							taskdetails.setCategory( categoryMap.get( converter.getTypeOfA().cast( converter.getAFromB( taskVO.getTskTaskCategory() ) ) ) );
							taskdetails.setPriority( priorityMap.get( converter.getTypeOfA().cast( converter.getAFromB( taskVO.getTskPriority() ) ) ) );

							taskList.add( taskdetails );

							taskListDetails.setTaskVO( taskList );
						}
					}
				}

				return taskListDetails;
			}
		}
		return baseVO;
	}
	
	@SuppressWarnings( "unchecked" )
	private Integer getCtlBrCode( Integer userId ){
		List<TMasUser> user = getHibernateTemplate().find( GET_LOCATION_CODE, userId );
		return user.get( 0 ).getBranch();
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.tasks.dao.ITaskDAO#getTaskDet
	 * ails(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public BaseVO getTaskStatusUser( BaseVO baseVO ){
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof TaskVO ){
				TaskVO taskDetail = (TaskVO) baseVO;
				String lob = taskDetail.getLob();
				List<TTrnTask> taskPojo = null;
				try{

					taskPojo = getHibernateTemplate().find( GET_TASK_DETAIL, taskDetail.getTaskID() );
				}
				catch( org.springframework.dao.DataAccessException exception ){
					throw new BusinessException( "task.details.noaccess", exception, "Unable to get data from the databas_2" );
				}
				DataHolderVO<Object[]> holderVO = new DataHolderVO<Object[]>();
				if( !Utils.isEmpty( taskPojo ) && taskPojo.size() > 0 ){
					taskDetail = BeanMapper.map( taskPojo.get( 0 ), TaskVO.class );

					taskDetail.setCreatedBy( !Utils.isEmpty( taskPojo.get( 0 ).getTskCreatedBy() ) ? taskPojo.get( 0 ).getTskCreatedBy().toString() : null );
					taskDetail.setAssignedBy( !Utils.isEmpty( taskPojo.get( 0 ).getTskAssignor() ) ? taskPojo.get( 0 ).getTskAssignor().toString() : null );
					taskDetail.setAssignedTo( !Utils.isEmpty( taskPojo.get( 0 ).getTskAssignee() ) ? taskPojo.get( 0 ).getTskAssignee().toString() : null );
					taskDetail.setLob( lob );
					Map<Integer, String> categoryMap = getCodeDescMap( com.Constant.CONST_TASK_CATEGORY );
					com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
					taskDetail.setCategory( categoryMap.get( converter.getTypeOfA().cast( converter.getAFromB( taskPojo.get( 0 ).getTskTaskCategory() ) ) ) );
					Map<Integer, String> taskTypeMap = getCodeDescMap( "TASK_TYPE" );
					taskDetail.setTaskType( taskTypeMap.get( converter.getTypeOfA().cast( converter.getAFromB( taskPojo.get( 0 ).getTskTranType() ) ) ) );

					if( !Utils.isEmpty( taskPojo.get( 0 ).getTskTaskCategory() ) && taskPojo.get( 0 ).getTskTaskCategory().equals( Byte.valueOf( "4" ) ) ){
						// its a referal task get the pol id and end id
						String[] polEndId = taskPojo.get( 0 ).getTskDocumentId().split( "-" );
						Utils.trimAllEntries( polEndId );
						if( polEndId.length != 3 ){
							throw new BusinessException( "task.polEndID.null", null, "Policy ID or Endorsement ID is not available" );
						}
						taskDetail.setPolLinkingId( Long.valueOf( polEndId[ 0 ] ) );
						taskDetail.setPolEndId( Long.valueOf( polEndId[ 1 ] ) );

						if( !Utils.isEmpty( taskPojo.get( 0 ).getTskTranType() ) ){
							byte tskType = taskPojo.get( 0 ).getTskTranType();

							if( tskType == 6 ){
								taskDetail.setQuoteNo( Long.valueOf( polEndId[ 2 ] ) );
							}
							else if( tskType == 2 ){
								taskDetail.setPolicyNo( Long.valueOf( polEndId[ 2 ] ) );
							}
						}

						List<TTrnPolicyQuo> trnPolicyQuos = null;
						//Phase 3 - policy id to be used instead of linking id
						if( !Utils.isEmpty( taskDetail )
								&& ( taskDetail.getLob().equalsIgnoreCase( LOB.TRAVEL.toString() ) || taskDetail.getLob().equalsIgnoreCase( LOB.HOME.toString() ) || taskDetail.getLob().equalsIgnoreCase( LOB.WC.toString()) ) ){
							trnPolicyQuos = getHibernateTemplate().find( "from TTrnPolicyQuo ttrnPol where ttrnPol.id.polPolicyId = ? and ttrnPol.id.polEndtId=?",
									taskDetail.getPolLinkingId(), taskDetail.getPolEndId() );
						}
						else{
							trnPolicyQuos = getHibernateTemplate().find( "from TTrnPolicyQuo ttrnPol where ttrnPol.polLinkingId=? and ttrnPol.id.polEndtId=?",
									taskDetail.getPolLinkingId(), taskDetail.getPolEndId() );
						}

						// Default status is set pending so that the page is rendered in read only mode in case status are not saved
						Byte status = Utils.isEmpty( trnPolicyQuos ) ? SvcConstants.POL_STATUS_PENDING.byteValue() : trnPolicyQuos.get( 0 ).getPolStatus();
						Object[] objects = { taskDetail, status };
						holderVO.setData( objects );
					}
				}
				else{
					throw new BusinessException( "task.details.nullvalue", null, "No Task available" );
				}

				return holderVO;
			}
		}
		return baseVO;

	}

	@SuppressWarnings( "unchecked" )
	@Override
	public BaseVO getTaskDetails( BaseVO baseVO ){

		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof TaskVO ){

				TaskVO taskDetail = (TaskVO) baseVO;
				String lob = taskDetail.getLob();
				List<TTrnTask> taskPojo = null;
				List<String> comments = null;
				byte tskType = 0;
				Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
				SQLQuery sqlQuery = null;
				try{

					taskPojo = getHibernateTemplate().find( GET_TASK_DETAIL, taskDetail.getTaskID() );
				}
				catch( org.springframework.dao.DataAccessException exception ){
					throw new BusinessException( "task.details.noaccess", exception, "Unable to get data from the databas_3" );
				}
				
				/*
				 * Fix for security issue. checking if the taskid is related to the requested user
				 */
/*				if( !( SvcUtils.getUserId( taskDetail ).equals( taskPojo.get( 0 ).getTskCreatedBy() )
						|| SvcUtils.getUserId( taskDetail ).equals( taskPojo.get( 0 ).getTskAssignor() ) || SvcUtils.getUserId( taskDetail ).equals(
						taskPojo.get( 0 ).getTskAssignee() ) ) ){
					logger.debug( "User: " + SvcUtils.getUserId( taskDetail ) + "trying to access " + taskDetail.getTaskID() );
					throw new BusinessException( "task.details.noaccess", null, "Unauthorized access" );
				}*/

				if( ( !Utils.isEmpty( taskPojo ) && taskPojo.size() > 0 ) ){
					taskDetail = BeanMapper.map( taskPojo.get( 0 ), TaskVO.class );
					taskDetail.setCreatedBy( getUserName( taskPojo.get( 0 ).getTskCreatedBy() ) );
					taskDetail.setAssignedBy( getUserName( taskPojo.get( 0 ).getTskAssignor() ) );
					taskDetail.setAssignedTo( !Utils.isEmpty( taskPojo.get( 0 ).getTskAssignee() ) ? taskPojo.get( 0 ).getTskAssignee().toString() : null );

					Map<Integer, String> categoryMap = getCodeDescMap( com.Constant.CONST_TASK_CATEGORY );
					com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
					taskDetail.setCategory( categoryMap.get( converter.getTypeOfA().cast( converter.getAFromB( taskPojo.get( 0 ).getTskTaskCategory() ) ) ) );
					taskDetail.setTaskType( converter.getTypeOfA().cast( converter.getAFromB( taskPojo.get( 0 ).getTskTranType() ) ).toString() );

					taskDetail.setLob( lob );
					//TODO to validate the changes start
					if( !Utils.isEmpty( taskPojo.get( 0 ).getTskBrCode() ) ){
						taskDetail.setLocation( String.valueOf( taskPojo.get( 0 ).getTskBrCode() ) );
					}
					//TODO to validate the changes end
					if( !Utils.isEmpty( taskPojo.get( 0 ).getTskTaskCategory() ) && taskPojo.get( 0 ).getTskTaskCategory().equals( Byte.valueOf( "4" ) ) ){
						// its a referal task get the pol id and end id						

						String[] polEndId = taskPojo.get( 0 ).getTskDocumentId().split( "-" );

						Utils.trimAllEntries( polEndId );
						if( polEndId.length != 3 ){
							throw new BusinessException( "task.polEndID.null", null, "Policy ID or Endorsement ID is not available" );
						}
						taskDetail.setPolLinkingId( Long.valueOf( polEndId[ 0 ] ) );
						taskDetail.setPolEndId( Long.valueOf( polEndId[ 1 ] ) );

						if( !Utils.isEmpty( taskPojo.get( 0 ).getTskTranType() ) ){
							tskType = taskPojo.get( 0 ).getTskTranType();

							if( tskType == 6 ){
								taskDetail.setQuoteNo( Long.valueOf( polEndId[ 2 ] ) );
							}
							else if( tskType == 2 ){
								taskDetail.setPolicyNo( Long.valueOf( polEndId[ 2 ] ) );
							}
						}
						
						try{
							if( tskType == 6 ){
								//comments = getHibernateTemplate().find( "select pc.pocComments from TTrnPolicyComments pc WHERE pc.pocPolicyId in (select polPolicyId from TTrnPolicyQuo where polQuotationNo =:quoteNo and polEndtId =:endtId and polIssueHour = 3 and polPolicyType= 50) and pc.pocPolicyStatus = 20 and rownum = 1",  taskDetail.getPolEndId(),taskDetail.getQuoteNo()  );
								sqlQuery = session.createSQLQuery("select POC_COMMENTS from t_trn_policy_comments where POC_POLICY_ID in (select pol_Policy_Id from T_Trn_Policy_Quo where pol_Quotation_No =:quoteNo and pol_Endt_Id =:endtId and pol_Issue_Hour = 3 and pol_Policy_Type= 50) and poc_Policy_Status = 20 and rownum = 1");
								sqlQuery.setLong( "quoteNo", taskDetail.getQuoteNo() );
								sqlQuery.setLong( "endtId", taskDetail.getPolEndId() );
							}
							else if( tskType == 2 ){
								sqlQuery = session.createSQLQuery("select POC_COMMENTS from t_trn_policy_comments where POC_POLICY_ID in (select pol_Policy_Id from T_Trn_Policy where pol_policy_No =:quoteNo and pol_Endt_Id =:endtId and pol_Issue_Hour = 3 and pol_Policy_Type= 50) and poc_Policy_Status = 20 and rownum = 1");
								sqlQuery.setLong( "quoteNo", taskDetail.getPolicyNo() );
								sqlQuery.setLong( "endtId", taskDetail.getPolEndId() );
							}
							if(!Utils.isEmpty( sqlQuery ))	/*Added if condition for sqlQuery null check - sonar violation fix */
							comments = (List<String>)sqlQuery.list();
						}
						catch( org.springframework.dao.DataAccessException exception ){
							throw new BusinessException( "task.details.noaccess", exception, "Unable to get data from the databas_4" );
						}
						
						if(!Utils.isEmpty( comments )){
							taskDetail.setDesc(taskDetail.getDesc().concat( "&#13;&#10; User Comments : "+  comments   ));
						}
					}
				}
				else{
					throw new BusinessException( "task.details.nullvalue", null, "No Task available" );
				}

				return taskDetail;
			}
		}
		return baseVO;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public BaseVO saveRefTskDetails( BaseVO baseVO ){

		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof TaskVO ){

				TaskVO taskDetails = (TaskVO) baseVO;

				/*
				 * Check the DB, for the endt id present in TaskVO coz user amends the policy and doesn't change any data the endt id will not be saved in DB
				 */
				if(taskDetails.getPolicyType().equals("31"))
					taskDetails.setPolEndId( getTtrnPolQuoCommon( taskDetails.getQuoteNo(), taskDetails.getPolEndId() ).getId().getPolEndtId() );
				else
				taskDetails.setPolEndId( getTtrnPolQuo( taskDetails.getPolLinkingId(), taskDetails.getPolEndId() ).getId().getPolEndtId() );

				TTrnTask task = getTaskPojo( taskDetails );
				if( Utils.isEmpty( taskDetails.getTaskID() ) ){
					task.setTskId( NextSequenceValue.getNextSequence( com.Constant.CONST_SEQ_TASK_ID, null, null, getHibernateTemplate() ) );
				}

				saveOrUpdate( task );
				taskDetails.setTaskID( task.getTskId() );
				
				if(!taskDetails.getPolicyType().equals("31"))
				{
				/* Below query should fetch records basis linking id and VED  in order to update status of current version which is in pending status */
				/* List<TTrnPolicyQuo> trnPolicyQuos = getHibernateTemplate().find( "from TTrnPolicyQuo ttrnPol where ttrnPol.polLinkingId=?", taskDetails.getPolLinkingId() ); */
				/* Fetch only the latest endorsementId record to update the status. */
					List<TTrnPolicyQuo> polRecList = getHibernateTemplate().find(
							"from TTrnPolicyQuo polTable where polTable.polLinkingId=? and polTable.polValidityExpiryDate=? and polTable.id.polEndtId=?",
							taskDetails.getPolLinkingId(), SvcConstants.EXP_DATE, taskDetails.getPolEndId() );

					for( TTrnPolicyQuo tTrnPolicyQuo : polRecList ){
							tTrnPolicyQuo.setPolStatus( Byte.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_REFERRED ) )  );
							tTrnPolicyQuo.setPolApprovedBy( null );
							tTrnPolicyQuo.setPolApprovalDate( null );
							getHibernateTemplate().merge( tTrnPolicyQuo );
						}

						/* After successfull save of referral details deleting the same from the temp table */
						deleteAll( getHibernateTemplate().find( "from TTrnTempPasReferral tempreferral where tempreferral.id.tprPolLinkingId=?", taskDetails.getPolLinkingId() ) );
				}
				return taskDetails;
			}
		}
		return baseVO;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	/**
	 * Method to get the list of Referal text
	 * 
	 * @param   baseVO
	 * @return  baseVO
	 */
	public BaseVO getReferalList( BaseVO baseVO ){

		List<TTrnTempPasReferral> referalList = null;
		Long tprPolLinkingId = null;
		boolean flag=true;
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof ReferralVO ){

				ReferralVO referalVO = (ReferralVO) baseVO;
				ReferralListVO referrals = new ReferralListVO();
				if( !Utils.isEmpty( referalVO ) ){
					tprPolLinkingId = referalVO.getPolLinkingId();
				}
					try{
					if ((referalVO.getTprUserRole()!=null) && (referalVO.getTprUserRole().equalsIgnoreCase("RSA_USER_3") || referalVO.getTprUserRole().equalsIgnoreCase("RSA_USER_2"))){
						Query q = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery( GET_REFERAL_INFO );
						q.setParameter( "tprPolLinkingId", tprPolLinkingId );
						q.setParameter( "userId", referalVO.getTprUserId() );
						q.setParameter( "role", referalVO.getTprUserRole() );
						referalList = q.list();
						if (!Utils.isEmpty(referalList)) {
							flag = false;
						} else {
							referalList = getHibernateTemplate().find(GET_REFERAL_TEXT, tprPolLinkingId);
							flag = true;
						}
					}else {
						/* Getting the referral text details */
						referalList = getHibernateTemplate().find(GET_REFERAL_TEXT, tprPolLinkingId);
						flag = true;
					}
					
					if (flag) {
						if (!Utils.isEmpty(referalList)) {
							List<ReferralVO> referralVOs = new ArrayList<ReferralVO>();
							for (TTrnTempPasReferral referal : referalList) {
								/*
								 * Preparing VO object for setting the response
								 */
								if (!Utils.isEmpty(referal)) {
									ReferralVO referralVO = new ReferralVO();
									referralVO.setPolLinkingId(tprPolLinkingId);
									// referralVO.setSectionId( new Integer(
									// referal.getId().getTprSecId() ) );
									referralVO.setSectionId(Integer.valueOf(referal.getId().getTprSecId()));
									List<String> referralText = new ArrayList<String>();
									referralText.add(referal.getTprRefText());
									referralVO.setReferralText(referralText);
									referralVO.setConsolidated(true);
									/* For testing purpose */
									String sectionName = getSectionName(Integer.valueOf(referal.getId().getTprSecId()));
									referralVO.setSectionName(sectionName);
									referralVOs.add(referralVO);
								}
							}
							referrals.setReferrals(referralVOs);
						}
					}else {
						
						
						if (!Utils.isEmpty(referalList)) {
							List<ReferralVO> referralVOs = new ArrayList<ReferralVO>();
							for (TTrnTempPasReferral referal : referalList) {
								/*
								 * Preparing VO object for setting the response
								 */
								if (!Utils.isEmpty(referal)) {
									ReferralVO referralVO = new ReferralVO();
									referralVO.setPolLinkingId(tprPolLinkingId);
									// referralVO.setSectionId( new Integer(
									// referal.getId().getTprSecId() ) );
									referralVO.setSectionId(Integer.valueOf(referal.getId().getTprSecId()));
									List<String> referralText = new ArrayList<String>();
									referralText.add(referal.getTprRefText());
									referralVO.setReferralText(referralText);
									referralVO.setConsolidated(true);
									referralVO.setTempReferral(true);
									/* For testing purpose */
									String sectionName = getSectionName(Integer.valueOf(referal.getId().getTprSecId()));
									referralVO.setSectionName(sectionName);
									referralVOs.add(referralVO);
								}
							}
							referrals.setReferrals(referralVOs);
						}
						
					}
					
					
				}			
				
					
				catch( org.springframework.dao.DataAccessException exception ){
					throw new BusinessException( "referal.details.nonaccess", exception, "Unable to get data from the database" );
				}
				return referrals;
			}
		}
		return baseVO;
	}

	private TTrnTask getTaskPojo( TaskVO taskDetails ){

		TTrnTask task = new TTrnTask();

		task.setTskShortDesc( taskDetails.getTaskName() );

		// Bug Fix Start -  referralText in database is 4000 varchar
		String refTxt = taskDetails.getDesc();
		String referralText = "";
		if( refTxt != null && refTxt.length() > 4000 ){
			referralText = refTxt.trim().substring( 0, 4000 );
		}
		else if( refTxt != null){
			referralText = refTxt.trim();
		}

		task.setTskDescription( referralText );
		// Bug Fix End -  referralText in database is 4000 varchar
		if( !Utils.isEmpty( taskDetails.getAssignedTo() ) ){
			task.setTskAssignee( Integer.valueOf( taskDetails.getAssignedTo() ) );
		}
		if( !Utils.isEmpty( taskDetails.getAssignedBy() ) ){
			task.setTskAssignor( Integer.valueOf( taskDetails.getAssignedBy() ) );
		}
		if( !Utils.isEmpty( taskDetails.getTaskType() ) ){
			byte taskType = Byte.valueOf( taskDetails.getTaskType() );
			task.setTskTranType( taskType );
			if( taskType == 6 )
				task.setTskDocumentId( taskDetails.getPolLinkingId() + "-" + taskDetails.getPolEndId() + "-" + taskDetails.getQuoteNo() );
			else if( taskType == 2 ) task.setTskDocumentId( taskDetails.getPolLinkingId() + "-" + taskDetails.getPolEndId() + "-" + taskDetails.getPolicyNo() );
		}

		if( !Utils.isEmpty( taskDetails.getCreatedBy() ) ){
			task.setTskCreatedBy( Integer.valueOf( taskDetails.getCreatedBy() ) );
		}
		task.setTskCreatedDate( taskDetails.getCreatedDate() );
		task.setTskTargetDate( taskDetails.getDueDate() );
		if( !Utils.isEmpty( taskDetails.getPriority() ) ){
			task.setTskPriority( Byte.valueOf( taskDetails.getPriority() ) );
		}
		if( !Utils.isEmpty( taskDetails.getStatus() ) ){
			task.setTskStatus( Byte.valueOf( taskDetails.getStatus() ) );
		}
		if( !Utils.isEmpty( taskDetails.getLoggedInUser() ) ){
			if( !Utils.isEmpty( ( (UserProfile) taskDetails.getLoggedInUser() ).getRsaUser() ) ){
				if( !Utils.isEmpty( ( (UserProfile) taskDetails.getLoggedInUser() ).getRsaUser() ) ){
					if( !Utils.isEmpty( ( (UserProfile) taskDetails.getLoggedInUser() ).getRsaUser().getBrokerId() ) ){
						task.setTskBrCode( Long.valueOf( ( (UserProfile) taskDetails.getLoggedInUser() ).getRsaUser().getBrokerId() ) );
					}
				}
			}
		}
		if( !Utils.isEmpty( taskDetails.getPolicyType() ) ){
			task.setTskClass( Byte.valueOf( taskDetails.getPolicyType() ) );
		}
		if( !Utils.isEmpty( taskDetails.getCategory() ) ){
			task.setTskTaskCategory( Byte.valueOf( taskDetails.getCategory() ) );
		}
		// Oman multibranching. Set the branch code to store in the database
		if(Utils.isEmpty( taskDetails.getLocation() ))
		{
			task.setTskBrCode(Long.valueOf(Utils.getSingleValueAppConfig("DEPLOYED_LOCATION")));
		}
		else
		{
			task.setTskBrCode( Long.valueOf( taskDetails.getLocation() ) );
		}
		return task;
	}

	private String getUserName( Integer userID ){
		return SvcUtils.getLookUpDescription( "USER", "ALL", "ALL", userID );
	}

	private String getLocationDesc( Integer locCode ){
		return SvcUtils.getLookUpDescription( "LOCATION", "ALL", "ALL", locCode );
	}

	public static void main( String[] args ){

		TaskDAO test = new TaskDAO();
		test.getUserName( null );
	}

	private Map<Integer, String> getCodeDescMap( String category ){

		Map<Integer, String> codeMap = new HashMap<Integer, String>();

		if( !Utils.isEmpty( category ) ){
			LookUpListVO luList = SvcUtils.getLookUpCodesList( category, "ALL", "ALL" );

			com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory
					.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );

			List<LookUpVO> luVOList = luList.getLookUpList();
			for( LookUpVO lu : luVOList ){
				if( !Utils.isEmpty( category ) ){
					Integer code = converter.getTypeOfB().cast( converter.getBFromA( lu.getCode() ) );
					if( !Utils.isEmpty( code ) ){
						codeMap.put( code, lu.getDescription() );
					}
				}

			}
		}
		return codeMap;
	}

	private String getSectionName( Integer sectionId ){
		/*
		 * If section is other than General Info or Renewals fetch from lookup.
		 */
		String sectionName = null;
		switch( sectionId ){
			case 1:
				sectionName = "Property All Risk";
				break;
			case 2:
				sectionName = "Business Interruption";
				break;
			case 3:
				sectionName = "Machinery Breakdown";
				break;
			case 4:
				sectionName = "Deterioration Of Stock";
				break;
			case 5:
				sectionName = "Electronic Equipment";
				break;
			case 6:
				sectionName = "Public Liability";
				break;
			case 7:
				sectionName = "Workmen compensation";
				break;
			case 8:
				sectionName = "Money";
				break;
			case 9:
				sectionName = "Fidelity Guarantee";
				break;
			case 10:
				sectionName = "Travel Baggage";
				break;
			case 11:
				sectionName = "Goods in Transit";
				break;
			case 12:
				sectionName = "Group Personal Accident";
				break;
			case 13:
				sectionName = "Motor Fleet";
				break;
			case 103 :
				sectionName = "Renewals";
				break;
			default:
				sectionName = "General Information";
				break;

		}
		return sectionName;
	}

	@Override
	public BaseVO saveOrUpdateTaskDetails( BaseVO baseVO ){

		/* Mapping of the taskVO to TTrnTask task */

		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof TaskVO ){

				TaskVO taskDetails = (TaskVO) baseVO;

				TTrnTask task = null;//getTaskPojo( taskDetails );
				/* If new Task then new Task Id generated */
				if( Utils.isEmpty( taskDetails.getTaskID() ) ){
					task = getTskPojoForNewTask( taskDetails );
					task.setTskId( NextSequenceValue.getNextSequence( com.Constant.CONST_SEQ_TASK_ID, null, null, getHibernateTemplate() ) );
				}
				else{
					/* If task Id present then its an update operation */
					task = (TTrnTask) getHibernateTemplate().find( GET_TASK_DETAIL, taskDetails.getTaskID() ).get( 0 );
					task = getTskPojoForUpdate( taskDetails, task );
				}

				/* save update the details */
				saveOrUpdate( task );

				return taskDetails;
			}
		}
		return baseVO;
	}

	private TTrnTask getTskPojoForUpdate( TaskVO taskDetails, TTrnTask taskPojo ){
		/*
		 * Null check are not added intentionally as all these fields are mandatory and in case of null, 
		 * an exception needs to be thrown
		 */
		taskPojo.setTskAssignee( Integer.valueOf( taskDetails.getAssignedTo() ) );
		taskPojo.setTskAssignor( SvcUtils.getUserId( taskDetails ) );
		taskPojo.setTskTargetDate( taskDetails.getDueDate() );
		taskPojo.setTskStatus( Byte.valueOf( taskDetails.getStatus() ) );
		taskPojo.setTskPriority( Byte.valueOf( taskDetails.getPriority() ) );
		return taskPojo;
	}

	private TTrnTask getTskPojoForNewTask( TaskVO taskDetails ){
		TTrnTask task = new TTrnTask();
		task.setTskShortDesc( taskDetails.getTaskName() );
		task.setTskAssignee( Integer.valueOf( taskDetails.getAssignedTo() ) );
		task.setTskCompletionDate( taskDetails.getDueDate() );
		task.setTskTargetDate( taskDetails.getDueDate() );
		task.setTskPriority( Byte.valueOf( taskDetails.getPriority() ) );
		// Bug Fix Start -  referralText in database is 4000 varchar
		String refTxt = taskDetails.getDesc();
		String referralText = "";
		if( refTxt != null && refTxt.length() > 4000 ){
			referralText = refTxt.trim().substring( 0, 4000 );
		}
		else{
			referralText = refTxt;
		}
		task.setTskDescription( referralText );
		// Bug Fix End -  referralText in database is 4000 varchar

		if( Utils.isEmpty( SvcUtils.getUserId( taskDetails ) ) ){
			throw new BusinessException( "pas.loggedInUser.idNull", null, "Cannot save new Task created because logged in user is null." );
		}

		task.setTskAssignor( SvcUtils.getUserId( taskDetails ) );
		task.setTskCreatedBy( SvcUtils.getUserId( taskDetails ) );
		task.setTskCreatedDate( new Date() );
		task.setTskClass( (byte) 50 );
		task.setTskStatus( (byte) 1 );
		task.setTskTaskCategory( (byte) 2 );
		task.setTskTranType( (byte) 6 );
		task.setTskDocumentId( null );
		// Oman multibranching. Set the branch code to store in the database
		task.setTskBrCode( Long.parseLong( taskDetails.getLocation() ) );

		return task;
	}

	/**
	 * For the passed endt ID get the latest endt present in DB, this is used in case the endt ID passed is not saved in DB but the latest id in DB is required
	 * @param polLinkingId
	 * @param endtId
	 * @return
	 */

	@SuppressWarnings( "unchecked" )
	private TTrnPolicyQuo getTtrnPolQuo( Long polLinkingId, Long endtId ){
		try{

			TTrnPolicyQuo latestPolRecord = null;

			//For the endt ID passed order by desc and get the first value. This ensures the latest record for the passed endt is loaded 
			List<TTrnPolicyQuo> latestPolRecords = getHibernateTemplate().find(
					"from TTrnPolicyQuo polQuo where polQuo.polLinkingId=? and  polQuo.id.polEndtId<=? order by polQuo.id.polEndtId desc ", polLinkingId, endtId );

			if( !Utils.isEmpty( latestPolRecords ) && latestPolRecords.size() > 0 ){
				latestPolRecord = latestPolRecords.get( 0 );
			}

			if( Utils.isEmpty( latestPolRecord ) ){
				throw new SystemException( "pas.cmn.noRecordFound", null, "Policy record not found for the base class code." );
			}

			return latestPolRecord;
		}
		catch( HibernateException e ){
			throw new BusinessException( "pas.cmn.noRecordFound", e, "Error while trying to SELECT policy details from t_trn_policy" );
		}
	}
	private TTrnPolicyQuo getTtrnPolQuoCommon( Long quoteNo, Long endtId ){
		try{

			TTrnPolicyQuo latestPolRecord = null;

			//For the endt ID passed order by desc and get the first value. This ensures the latest record for the passed endt is loaded 
			List<TTrnPolicyQuo> latestPolRecords = getHibernateTemplate().find(
					"from TTrnPolicyQuo polQuo where polQuo.polQuotationNo=? and  polQuo.id.polEndtId<=? order by polQuo.id.polEndtId desc ", quoteNo, endtId );

			if( !Utils.isEmpty( latestPolRecords ) && latestPolRecords.size() > 0 ){
				latestPolRecord = latestPolRecords.get( 0 );
			}

			if( Utils.isEmpty( latestPolRecord ) ){
				throw new SystemException( "pas.cmn.noRecordFound", null, "Policy record not found for the base class code." );
			}

			return latestPolRecord;
		}
		catch( HibernateException e ){
			throw new BusinessException( "pas.cmn.noRecordFound", e, "Error while trying to SELECT policy details from t_trn_policy" );
		}
	}

	/**
	 * PHASE 3 This method will create the referral task for Consolidated referral 
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public BaseVO saveConsolidatedReferral( BaseVO baseVO ){
		TaskVO taskVO = null;
		TTrnTask ttrnTaskPojo = (TTrnTask) Utils.getBean( "ttrnTask" );
		String quoteOrPolicyNo = null;

		StringBuilder documentIdBuilder = new StringBuilder();
		Calendar cal = Calendar.getInstance();

		if( !Utils.isEmpty( baseVO ) ){
			taskVO = (TaskVO) baseVO;

			if( Utils.isEmpty( taskVO.getTaskID() ) ){
				/* For new task generating the sequence id */
				ttrnTaskPojo.setTskId( NextSequenceValue.getNextSequence( com.Constant.CONST_SEQ_TASK_ID, null, null, getHibernateTemplate() ) );
				ttrnTaskPojo.setTskAssignee( Integer.valueOf( taskVO.getAssignedTo() ) );
				ttrnTaskPojo.setTskCreatedBy( Integer.valueOf( taskVO.getCreatedBy() ) );
				ttrnTaskPojo.setTskAssignor( Integer.valueOf( taskVO.getAssignedBy() ) );
				ttrnTaskPojo.setTskBrCode( Long.valueOf( taskVO.getLocation() ) );
				ttrnTaskPojo.setTskCreatedDate( new Date() );
				cal.setTime( new Date() );
				cal.add( Calendar.MONTH, 1 ); /* Extending the target date for referral by one month */
				ttrnTaskPojo.setTskTargetDate( cal.getTime() );
				ttrnTaskPojo.setTskPriority( Byte.valueOf( taskVO.getPriority() ) );
				ttrnTaskPojo.setTskShortDesc( taskVO.getTaskName() );
				ttrnTaskPojo.setTskDescription( taskVO.getDesc() );
				if( taskVO.getTaskType().equals( "6" ) ){
					quoteOrPolicyNo = String.valueOf( taskVO.getQuoteNo() );
				}
				else{
					quoteOrPolicyNo = String.valueOf( taskVO.getPolicyNo() );
				}
				documentIdBuilder = documentIdBuilder.append( taskVO.getPolLinkingId().toString() ).append( "-" ).append( taskVO.getPolEndId().toString() ).append( "-" )
						.append( quoteOrPolicyNo );
				ttrnTaskPojo.setTskDocumentId( documentIdBuilder.toString() ); /* Generating document id */
				ttrnTaskPojo.setTskClass( taskVO.getClassCode() );
				ttrnTaskPojo.setTskStatus( (byte) 1 );
				ttrnTaskPojo.setTskTaskCategory( (byte) 4 ); /* Setting category as referral */
				ttrnTaskPojo.setTskTranType( Byte.valueOf( taskVO.getTaskType() ) );
				ttrnTaskPojo.setTskAssignor( Integer.valueOf( taskVO.getAssignedBy() ) );
				/*polRecList = getHibernateTemplate().find("from TTrnPolicyQuo polTable where polTable.id.polPolicyId=? and polTable.id.polEndtId=?",taskVO.getPolLinkingId(),taskVO.getPolEndId());
				for (TTrnPolicyQuo tTrnPolicyQuo : polRecList) {
					tTrnPolicyQuo.setPolStatus((byte) 20);  Setting the policy status as 20 for referral 
					getHibernateTemplate().saveOrUpdate(tTrnPolicyQuo);
				}*/
				updateTTrnPolicyStatus( taskVO );
				//getHibernateTemplate().flush();// When status is 20 it should be available to PRO_QUO_UPD_STATUS SP to decide if status of other tables to be changed to 1 or not
			}
			else{
				ttrnTaskPojo = (TTrnTask) getHibernateTemplate().find( GET_TASK_DETAIL, taskVO.getTaskID() ).get( 0 );
				if( !Utils.isEmpty( ttrnTaskPojo ) ){
					ttrnTaskPojo = getTskPojoForUpdate( taskVO, ttrnTaskPojo );
					ttrnTaskPojo.setTskDescription( taskVO.getDesc() );
					ttrnTaskPojo.setTskStatus( (byte) 1 );
					updateTTrnPolicyStatus( taskVO );
				}
			}
			getHibernateTemplate().saveOrUpdate( ttrnTaskPojo );
		}
		return taskVO;
	}

	/**
	 * Update the status to as referral(20) in t_trn_policy table
	 * 
	 * @param taskVo
	 */
	@SuppressWarnings( "unchecked" )
	private void updateTTrnPolicyStatus( TaskVO taskVo ){
		//Radar fix
		List<TTrnPolicyQuo> polRecList = null; //new ArrayList<TTrnPolicyQuo>();
		polRecList = getHibernateTemplate().find( "from TTrnPolicyQuo polTable where polTable.id.polPolicyId=? and polTable.id.polEndtId=?", taskVo.getPolLinkingId(),
				taskVo.getPolEndId() );
		for( TTrnPolicyQuo tTrnPolicyQuo : polRecList ){
			tTrnPolicyQuo.setPolStatus( (byte) 20 ); /* Setting the policy status as 20 for referral */
			getHibernateTemplate().saveOrUpdate( tTrnPolicyQuo );
			getHibernateTemplate().flush();// When status is 20 it should be available to PRO_QUO_UPD_STATUS SP to decide if status of other tables to be changed to 1 or not
		}
	}

	@Override
	public BaseVO populateReferralTaskDets(BaseVO baseVO) {
/* To populate the messages corresponding to T_TRN_PAS_REFERRAL_DETAILS */
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
		TaskVO taskVO = policyDataVO.getReferralVOList().getTaskVO();
		
		List<TTrnPasReferralDetails> referralsList = DAOUtils.getReferralDetails( policyDataVO.getCommonVO().getPolicyId(), policyDataVO.getCommonVO().getEndtId() );
		if (!Utils.isEmpty(referralsList)) {
			StringBuilder messageBuilder = new StringBuilder();
			/* Populating the message from T_TRN_PAS_REFERRAL_DETAILS */
			int counter = 0;
			for (TTrnPasReferralDetails pasReferralDetails : referralsList) {
				if (counter == 0) {
					messageBuilder.append(pasReferralDetails.getRefText());
				} else {
					messageBuilder.append("/n").append(pasReferralDetails.getRefText());
				}
			}
			taskVO.setPolicyType(String.valueOf(policyDataVO.getPolicyType()));
			if(!Utils.isEmpty(policyDataVO.getCommonVO().getPolicyId())){
				taskVO.setPolLinkingId(policyDataVO.getCommonVO().getPolicyId());
			}else {
				taskVO.setPolLinkingId(policyDataVO.getPolicyId());
			}
			taskVO.setDesc(messageBuilder.toString());
			if (!Utils.isEmpty(policyDataVO.getCommonVO().getEndtId())) {
				taskVO.setPolEndId(policyDataVO.getCommonVO().getEndtId());
			} else {
				//taskVO.setPolEndId(new Long(0));
				taskVO.setPolEndId(Long.valueOf(0));
			}
			if(!Utils.isEmpty(policyDataVO.getCommonVO()) && policyDataVO.getCommonVO().getIsQuote()){
				taskVO.setTaskType(Utils.getSingleValueAppConfig("QUOTE_REFRRAL_TASK_TYPE"));
				taskVO.setQuote(true);
				taskVO.setQuoteNo(policyDataVO.getCommonVO().getQuoteNo());
				taskVO.setTaskName("Transaction "+policyDataVO.getCommonVO().getQuoteNo()+" is referred.");
			} else {
				taskVO.setTaskType(Utils.getSingleValueAppConfig("POLICY_REFRRAL_TASK_TYPE"));
				taskVO.setQuote(false);
				taskVO.setPolicyNo(policyDataVO.getCommonVO().getPolicyNo());
				taskVO.setTaskName("Transaction "+policyDataVO.getCommonVO().getPolicyNo()+" is referred.");
			}
			taskVO.setPriority(Utils.getSingleValueAppConfig("DEFAULT_TASK_PRIORITY"));
			taskVO.setClassCode(Byte.valueOf(Utils.getSingleValueAppConfig("TRAVEL_CLASS_CODE")));
			taskVO.setCategory(String.valueOf(policyDataVO.getPolicyType()));
			taskVO.setIsOpen(true); /* Default value while assigning */
			taskVO.setLob(policyDataVO.getCommonVO().getLob().toString());
		}
		return taskVO;	
	}
}

