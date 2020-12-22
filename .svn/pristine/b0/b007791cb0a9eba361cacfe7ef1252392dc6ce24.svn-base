package com.rsaame.pas.dairy.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.dao.model.TTrnReminder;
import com.rsaame.pas.dao.model.TTrnReminderId;
import com.rsaame.pas.vo.bus.ReminderListVO;
import com.rsaame.pas.vo.bus.ReminderVO;

public class DairyDAO extends BaseDBDAO implements IDairyDAO{
	private final static Logger logger = Logger.getLogger(DairyDAO.class);

	@Override
	public BaseVO saveDairyItem(BaseVO baseVO) {

		ReminderVO reminderVO = (ReminderVO)baseVO;
		TTrnReminder tTrnReminder = null;
			
		if(!reminderVO.isEdited()){
			tTrnReminder = getRmdrPojoForItem(reminderVO);
			if(!Utils.isEmpty(tTrnReminder)){
				save(tTrnReminder);
			}
		}
		if(reminderVO.isEdited()){
			Date targetDate = reminderVO.getPreparedDate(); // temp date to find record(previous date and time before edited
			Date tempPreparedDate = reminderVO.getTempPreparedDate();
			String dateTemp = getTempDate(targetDate);
			String preparedDateTemp = getTempDate(tempPreparedDate);
			/*String dateTemp = new SimpleDateFormat(com.Constant.CONST_DD_MMM_YY_HH_MM_SS).format(targetDate);
			String  dateTime[] = dateTemp.split(" ");
			String time = dateTime[1];
			String HHMMSS[] = time.split(":");
			String HH = HHMMSS[0];
			int hours = Integer.valueOf(HH);
			if(hours == 0) {
				dateTemp = dateTime[0]+" "+"00"+":"+HHMMSS[1]+":"+HHMMSS[2];
			}else if(hours<10){
				dateTemp = dateTime[0]+" 0"+hours+":"+HHMMSS[1]+":"+HHMMSS[2];
			}else{
				dateTemp = dateTime[0]+" "+hours+":"+HHMMSS[1]+":"+HHMMSS[2];
			}
				
			dateTemp = dateTemp.toUpperCase();*/
			int emailReq = reminderVO.isEmail()?1:0;
			String GET_SECTION_QUERY = "UPDATE T_TRN_REMINDER SET REM_TARGET_DT = to_date('"+reminderVO.getTargetdate()+"', 'YYYY-MM-DD HH24:MI:SS'),rem_description='"+reminderVO.getSubject()+Utils.getSingleValueAppConfig("SUBJECT_SEPERATOR_IN_REMAINDER")+reminderVO.getComments()+"',rem_email_req="+emailReq+" WHERE ROWID=(SELECT ROWID FROM T_TRN_REMINDER WHERE REM_PREPARED_BY = "+Integer.valueOf(reminderVO.getPreparedBy())+" and TO_CHAR(REM_PREPARED_DT, 'dd-MON-yy HH24:MI:SS') = '"+preparedDateTemp+"' and TO_CHAR(REM_TARGET_DT, 'dd-MON-yy HH24:MI:SS') = '"+dateTemp+"')";
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query sqlQuery = session.createSQLQuery(GET_SECTION_QUERY);
			int result = sqlQuery.executeUpdate();
			System.out.print(result);
		}
		return reminderVO;
	}
	
	private String getTempDate(Date targetDate){
		
		String dateTemp = new SimpleDateFormat(com.Constant.CONST_DD_MMM_YY_HH_MM_SS).format(targetDate);
		String  dateTime[] = dateTemp.split(" ");
		String time = dateTime[1];
		String HHMMSS[] = time.split(":");
		String HH = HHMMSS[0];
		int hours = Integer.valueOf(HH);
		if(hours == 0) {
			dateTemp = dateTime[0]+" "+"00"+":"+HHMMSS[1]+":"+HHMMSS[2];
		}else if(hours<10){
			dateTemp = dateTime[0]+" 0"+hours+":"+HHMMSS[1]+":"+HHMMSS[2];
		}else{
			dateTemp = dateTime[0]+" "+hours+":"+HHMMSS[1]+":"+HHMMSS[2];
		}
			
		dateTemp = dateTemp.toUpperCase();
		return dateTemp;
	}

	private TTrnReminder getRmdrPojoForItem(ReminderVO reminderVO) {
		
		TTrnReminder tTrnReminder = new TTrnReminder();
		TTrnReminderId tTrnReminderId = new TTrnReminderId();
		tTrnReminderId.setRemPreparedBy(Integer.valueOf(reminderVO.getPreparedBy()));
		if(Utils.isEmpty( reminderVO.getPreparedDate())){
			tTrnReminderId.setRemPreparedDt(new Date());
		}else{
			tTrnReminderId.setRemPreparedDt(reminderVO.getPreparedDate());
		}
		tTrnReminderId.setRemSrlNo(Short.valueOf((reminderVO.getRemSrlNo())));
		
		if(reminderVO.getRemType() == "1"){
			tTrnReminderId.setRemType(true);
		}else {
			tTrnReminderId.setRemType(false);
		}
		tTrnReminderId.setRemTypeId(Long.valueOf(reminderVO.getRemTypeId()));
		tTrnReminder.setId(tTrnReminderId);
		tTrnReminder.setEmailReq( reminderVO.isEmail() );
		try{
			tTrnReminder.setRemTargetDt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(reminderVO.getTargetdate()));
		}catch(ParseException e)
		{
			e.printStackTrace();
		}
		tTrnReminder.setRemDescription(reminderVO.getSubject()+Utils.getSingleValueAppConfig("SUBJECT_SEPERATOR_IN_REMAINDER")+reminderVO.getComments());
		
		if(reminderVO.getRemStatus() == "1"){
			tTrnReminder.setRemStatus(true);
		}else 
		{
			tTrnReminder.setRemStatus(false);
		}
		
		return tTrnReminder;
	}

	@Override
	public BaseVO getDairyItems(BaseVO baseVO) {

		ReminderVO reminder =(ReminderVO)baseVO;
		
		java.util.List<TTrnReminder> reminderList= new java.util.ArrayList<TTrnReminder>();
		/*java.util.List<TTrnReminder> reminderList= getHibernateTemplate().find("from TTrnReminder trmd where trmd.id.remPreparedBy = ? and trmd.remTargetDt = ?" +
				"and trmd.id.remSrlNo = ? and trmd.id.remTypeId = ?",Integer.valueOf(taskVO.getCreatedBy()),taskVO.getDueDate(),taskVO.getPolEndId().shortValue(),taskVO.getTaskID().longValue());*/
		String GET_SECTION_QUERY ="";
		String fromdate = new Date().toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		fromdate = sdf.format(new Date());
		fromdate = fromdate.toUpperCase();
		if(reminder.isLapsedDairyItems()){	
			 GET_SECTION_QUERY ="select rem_prepared_by,to_char(rem_prepared_dt, 'dd-MON-yy HH24:MI:SS'),to_char(rem_target_dt, 'dd-MON-yy HH24:MI:SS'),rem_description,rem_status,rem_type,rem_type_id,rem_srl_no,rem_email_req from t_trn_reminder where rem_prepared_by="+Integer.valueOf(reminder.getPreparedBy())+" and REM_TARGET_DT >=  CURRENT_DATE-20 AND REM_TARGET_DT <= CURRENT_DATE-1";
		}else{
			/*SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy");*/
			fromdate =(reminder.getTargetdate());
			fromdate = fromdate.toUpperCase()+"%";
			GET_SECTION_QUERY ="select rem_prepared_by,to_char(rem_prepared_dt, 'dd-MON-yy HH24:MI:SS'),to_char(rem_target_dt, 'dd-MON-yy HH24:MI:SS'),rem_description,rem_status,rem_type,rem_type_id,rem_srl_no,rem_email_req from t_trn_reminder where rem_prepared_by="+Integer.valueOf(reminder.getPreparedBy())+" and rem_target_dt Like '"+fromdate+"' and  GREATEST(REM_TARGET_DT,SYSDATE) >= SYSDATE";
		}
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query sqlQuery = session.createSQLQuery(GET_SECTION_QUERY);
		List<Object[]> result = (List<Object[]>)sqlQuery.list();
		Iterator<Object[]> it = result.iterator();
		while(it.hasNext()){	
			Object [] object= it.next();
			TTrnReminder tTrnReminder = new TTrnReminder();
			TTrnReminderId tTrnReminderId = new TTrnReminderId();
			
			
			if(!Utils.isEmpty(object[0])){
				tTrnReminderId.setRemPreparedBy(((BigDecimal)object[0]).intValue());
			}
			if(!Utils.isEmpty(object[1])){
				try{
					tTrnReminderId.setRemPreparedDt(new SimpleDateFormat(com.Constant.CONST_DD_MMM_YY_HH_MM_SS).parse((object[1]).toString()));
				}catch(ParseException e){
					e.printStackTrace();
				}
			}
			if(!Utils.isEmpty(object[2])){
				try{
					tTrnReminder.setRemTargetDt(new SimpleDateFormat(com.Constant.CONST_DD_MMM_YY_HH_MM_SS).parse((object[2]).toString()));
				}catch(ParseException e){
					e.printStackTrace();
				}
			}
			if(!Utils.isEmpty(object[3])){
				tTrnReminder.setRemDescription(((String)object[3]));
			}
			if(!Utils.isEmpty(object[4])){
				tTrnReminder.setRemStatus(intToBoolean(((BigDecimal)object[4]).intValue()));
			}
			if(!Utils.isEmpty(object[5])){
				tTrnReminderId.setRemType(intToBoolean(((BigDecimal)object[5]).intValue()));
			}
			if(!Utils.isEmpty(object[6])){
				tTrnReminderId.setRemTypeId(((BigDecimal)object[6]).longValue());
			}
			if(!Utils.isEmpty(object[7])){
				tTrnReminderId.setRemSrlNo(((BigDecimal)object[7]).shortValue());
			}
			if(!Utils.isEmpty(object[8])){
				tTrnReminder.setEmailReq(intToBoolean(((BigDecimal)object[8]).intValue()));
			}
			tTrnReminder.setId(tTrnReminderId);
			reminderList.add(tTrnReminder);
		}	
		com.mindtree.ruc.cmn.utils.List<ReminderVO> reminderListVOs = new com.mindtree.ruc.cmn.utils.List<ReminderVO>( ReminderVO.class );
		ReminderListVO reminderListVO = new ReminderListVO();
		Integer reminderCount = 0;
		Integer slNumber = 0;
		for(TTrnReminder ttrnReminder :reminderList){
			
			if(!Utils.isEmpty(ttrnReminder)){
				slNumber++;
				ReminderVO reminderVO = new ReminderVO();
				reminderVO.setPreparedBy(String.valueOf(ttrnReminder.getId().getRemPreparedBy()));
				try{
					String date = new SimpleDateFormat("dd-MMM-yy").format(ttrnReminder.getRemTargetDt());
					reminderVO.setTargetdate(date);
				}catch(Exception e){
					e.printStackTrace();
				}
				if(!Utils.isEmpty(ttrnReminder.getRemDescription())){
					String [] subComments = (ttrnReminder.getRemDescription()).split("###");
					if(subComments.length == 2){
					reminderVO.setComments(subComments[1]);
					}
					
					reminderVO.setSubject(subComments[0]);
				}
				String dateTime[] =  ttrnReminder.getRemTargetDt().toString().split(" ");
				if(!Utils.isEmpty(dateTime)){
					String time[] = dateTime[3].split(":");
					if(!Utils.isEmpty(time)){
						reminderVO.setRemTime(time[0]+":"+time[1]);
					}	
				}
				if(!Utils.isEmpty(ttrnReminder.getEmailReq())){
					reminderVO.setEmail(ttrnReminder.getEmailReq());
					}
				reminderVO.setsLNumber(slNumber);
				reminderVO.setPreparedDate( ttrnReminder.getId().getRemPreparedDt() );
				reminderListVOs.add(reminderVO);
				reminderCount++;
			}
			
		}
		reminderListVO.setReminderListVO((reminderListVOs));
		reminderListVO.setReminderCount(reminderCount);
		return reminderListVO;
		
	}

	private boolean intToBoolean(int temp){
		
		if(temp == 1) return true;
		else return false;
	}
	@Override
	public BaseVO deleteDairyItem(BaseVO baseVO) {
		
		ReminderVO reminderVO = (ReminderVO) baseVO;
		String dateTemp = reminderVO.getTargetdate();
		Date tempPreparedDate = reminderVO.getPreparedDate();
		String preparedDate = getTempDate(tempPreparedDate);
		dateTemp = getDelTempDate(dateTemp);
		preparedDate = getTempDate(tempPreparedDate);
		/*String  dateTime[] = dateTemp.split(" ");
		String time = dateTime[1];
		String HHMMSS[] = time.split(":");
		String HH = HHMMSS[0];
		int hours = Integer.valueOf(HH);
		if(hours == 0) {
			dateTemp = dateTime[0]+" "+"00"+":"+HHMMSS[1]+":"+HHMMSS[2];
		}else if(hours<10){
			dateTemp = dateTime[0]+" 0"+hours+":"+HHMMSS[1]+":"+HHMMSS[2];
		}else{
			dateTemp = dateTime[0]+" "+hours+":"+HHMMSS[1]+":"+HHMMSS[2];
		}
		dateTemp = dateTemp.toUpperCase();*/
		String GET_SECTION_QUERY ="delete from t_trn_reminder where rem_prepared_by = "+Integer.valueOf(reminderVO.getPreparedBy())+" and to_char(rem_prepared_dt, 'dd-MON-yy HH24:MI:SS') = '"+preparedDate+"' and to_char(rem_target_dt, 'dd-MON-yy HH24:MI:SS') = '"+dateTemp+"' and rem_description = '"+reminderVO.getSubject()+"###"+reminderVO.getComments()+"'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query sqlQuery = session.createSQLQuery(GET_SECTION_QUERY);
		int result = sqlQuery.executeUpdate();
		System.out.print(result);
	
		return reminderVO;
		
	}
	
	private String getDelTempDate(String dateTemp)
	{
		String  dateTime[] = dateTemp.split(" ");
		String time = dateTime[1];
		String HHMMSS[] = time.split(":");
		String HH = HHMMSS[0];
		int hours = Integer.valueOf(HH);
		if(hours == 0) {
			dateTemp = dateTime[0]+" "+"00"+":"+HHMMSS[1]+":"+HHMMSS[2];
		}else if(hours<10){
			dateTemp = dateTime[0]+" 0"+hours+":"+HHMMSS[1]+":"+HHMMSS[2];
		}else{
			dateTemp = dateTime[0]+" "+hours+":"+HHMMSS[1]+":"+HHMMSS[2];
		}
		dateTemp = dateTemp.toUpperCase();
		
		return dateTemp;
	}
	
	public BaseVO getDiaryItemsForReminder(String userId) {
		DataHolderVO<List <TTrnReminder>> data = new DataHolderVO<List <TTrnReminder>>();
		try{
		java.util.List<TTrnReminder> reminderList= new java.util.ArrayList<TTrnReminder>();
		/*java.util.List<TTrnReminder> reminderList= getHibernateTemplate().find("from TTrnReminder trmd where trmd.id.remPreparedBy = ? and trmd.remTargetDt = ?" +
				"and trmd.id.remSrlNo = ? and trmd.id.remTypeId = ?",Integer.valueOf(taskVO.getCreatedBy()),taskVO.getDueDate(),taskVO.getPolEndId().shortValue(),taskVO.getTaskID().longValue());*/
		String GET_SECTION_QUERY ="";
		//Radar fix 
		/*String fromdate = new Date().toString();
		 * fromdate = sdf.format(new Date());
		fromdate = fromdate.toUpperCase();*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		//GET_SECTION_QUERY ="select rem_prepared_by,to_char(rem_prepared_dt, 'dd-MON-yy HH24:MI:SS'),to_char(rem_target_dt, 'dd-MON-yy HH24:MI:SS'),rem_description,rem_status,rem_type,rem_type_id,rem_srl_no from t_trn_reminder where and rem_target_dt Like '"+fromdate+"' and  GREATEST(REM_TARGET_DT,SYSDATE) >= SYSDATE";
		
		Date currentDate= new Date();
		Date datePlusOneMin=new Date();
		int delayTime = Integer.valueOf(Utils.getSingleValueAppConfig( "PAS_SCHEDULER_DELAY" ));
		datePlusOneMin.setMinutes(new Date().getMinutes()+delayTime);		
		String toDateTimeStr=sdf.format(datePlusOneMin);
		String currentDateStr=sdf.format(currentDate);
		
		
		logger.debug("currentDateStr: "+currentDateStr);
		logger.debug("toDateTimeStr: "+toDateTimeStr);
		if(!Utils.isEmpty( userId )){	
			toDateTimeStr = new SimpleDateFormat("yyyy-MMM-dd HH:mm").format( datePlusOneMin );
			GET_SECTION_QUERY="select rem_prepared_by,to_char(rem_prepared_dt, 'dd-MON-yy HH24:MI:SS')," +
			"to_char(rem_target_dt, 'dd-MON-yy HH24:MI:SS'),rem_description,rem_status,rem_type,rem_type_id,rem_srl_no " +
			"from t_trn_reminder where " +
			"rem_target_dt = TO_DATE('"+toDateTimeStr+"','YYYY-MON-dd HH24:MI:SS') AND REM_STATUS=0 and rem_prepared_by="+userId;
		}else{
			GET_SECTION_QUERY="select rem_prepared_by,to_char(rem_prepared_dt, 'dd-MON-yy HH24:MI:SS')," +
			"to_char(rem_target_dt, 'dd-MON-yy HH24:MI:SS'),rem_description,rem_status,rem_type,rem_type_id,rem_srl_no " +
			"from t_trn_reminder where " +
			"rem_target_dt between TO_DATE('"+currentDateStr+"','YYYY-MON-dd HH24:MI:SS') " +
			"And TO_DATE('"+toDateTimeStr+"','YYYY-MON-dd HH24:MI:SS') AND REM_STATUS=0 and REM_EMAIL_REQ=1";
		}	
				
		logger.debug("Reminder Selection Query:"+GET_SECTION_QUERY)	;
		Query sqlQuery = session.createSQLQuery(GET_SECTION_QUERY);
		List<Object[]> result = (List<Object[]>)sqlQuery.list();
		Iterator<Object[]> it = result.iterator();
		while(it.hasNext()){	
			Object [] object= it.next();
			TTrnReminder tTrnReminder = new TTrnReminder();
			TTrnReminderId tTrnReminderId = new TTrnReminderId();
			
			
			if(!Utils.isEmpty(object[0])){
				tTrnReminderId.setRemPreparedBy(((BigDecimal)object[0]).intValue());
			}
			if(!Utils.isEmpty(object[1])){
				try{
					tTrnReminderId.setRemPreparedDt(new SimpleDateFormat(com.Constant.CONST_DD_MMM_YY_HH_MM_SS).parse((object[1]).toString()));
				}catch(ParseException e){
					e.printStackTrace();
				}
			}
			if(!Utils.isEmpty(object[2])){
				try{
					tTrnReminder.setRemTargetDt(new SimpleDateFormat(com.Constant.CONST_DD_MMM_YY_HH_MM_SS).parse((object[2]).toString()));
				}catch(ParseException e){
					e.printStackTrace();
				}
			}
			if(!Utils.isEmpty(object[3])){
				tTrnReminder.setRemDescription(((String)object[3]));
			}
			if(!Utils.isEmpty(object[4])){
				tTrnReminder.setRemStatus(intToBoolean(((BigDecimal)object[4]).intValue()));
			}
			if(!Utils.isEmpty(object[5])){
				tTrnReminderId.setRemType(intToBoolean(((BigDecimal)object[5]).intValue()));
			}
			if(!Utils.isEmpty(object[6])){
				tTrnReminderId.setRemTypeId(((BigDecimal)object[6]).longValue());
			}
			if(!Utils.isEmpty(object[7])){
				tTrnReminderId.setRemSrlNo(((BigDecimal)object[7]).shortValue());
			}
			tTrnReminder.setId(tTrnReminderId);
			reminderList.add(tTrnReminder);
		}
		
		
		data.setData(reminderList);
		}
		catch(Exception e){
			logger.error("Error in fetcching the reminder details:"+e.getMessage());
		}
		return data;
		
	} 
	public void updateDiaryItemsForReminder(String remSerialNumber,
			String preparedBy, String preprdDate, String typeId){
		try{
		String GET_SECTION_QUERY = "UPDATE T_TRN_REMINDER SET REM_STATUS =1 where REM_SRL_NO =? " +
		"and rem_prepared_by=?  and rem_type_id=? and rem_prepared_dt=TO_DATE('"+preprdDate+"','YYYY-MON-dd HH24:MI:SS')" ;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query sqlQuery = session.createSQLQuery(GET_SECTION_QUERY);
		sqlQuery.setParameter(0, Integer.parseInt(remSerialNumber));
		sqlQuery.setParameter(1, Integer.parseInt(preparedBy));
		sqlQuery.setParameter(2, Integer.parseInt(typeId));
		logger.debug("preprdDate:"+preprdDate);
		//sqlQuery.setParameter(3, "TO_DATE('"+preprdDate+"','YYYY-MON-dd HH24:MI:SS')");
		//sqlQuery.setParameter(3, preprdDate);
		
		logger.debug("sqlQuery.getQueryString()"+sqlQuery.getQueryString());
		int result = sqlQuery.executeUpdate();
		logger.debug("Updated REM_STATUS for REM_SRL_NO:"+ remSerialNumber +"to"+result);
		}catch(Exception e){
			logger.error("Error in updating reminder:"+e.getMessage());
		}
		
	}

	

}
