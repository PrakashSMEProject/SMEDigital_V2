/**
 * 
 */
package com.rsaame.pas.dao.cmn;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;




import org.apache.commons.beanutils.BeanUtilsBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.constants.CommonConstants;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapperId;
import com.rsaame.pas.cmn.vo.IRSAUser;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.com.helper.IUpdateDerivedData;
import com.rsaame.pas.dao.model.TTrnGaccPersonQuo;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnPolicyQuoId;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;


/**
 * @author M1014644
 *
 */
public class BaseSaveOperation extends BaseDBDAO implements IBaseSaveOperation{

	private static final List<String> NON_VERSION_STATUS = Arrays.asList( Utils.getMultiValueAppConfig( SvcConstants.NON_VERSION_STATUS ) );
	private static final List<String> VERSION_STATUS = Arrays.asList( Utils.getMultiValueAppConfig( SvcConstants.VERSION_STATUS ) );
	private static final BeanUtilsBean BEANUTIL = SvcUtils.getBeanUtilBean();
	private final static Logger logger = Logger.getLogger( BaseSaveOperation.class );
	private static final Integer QUOTE_LAPSED = Integer.parseInt(Utils.getSingleValueAppConfig( "QUOTE_LAPSED_STATUS" ));
	
	

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.IBaseSaveOperation#executeSave(com.mindtree.ruc.cmn.vo.DataHolderVO, com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@Override
	public final BaseVO executeSave( DataHolderVO<LinkedHashMap<String, List<TableData>>> toBeSaved, BaseVO commonBaseVO ){

		logger.info("Entered BaseSaveOperation.executeSave() method");
		ThreadLevelContext.clearAll();

		LinkedHashMap<String, List<TableData>> dataToSave = toBeSaved.getData();

		/*
		 * Check if the first  record in the dataToSave list is t_trn_policy
		 */
		checkPolicyRec( dataToSave );

		CommonVO commonVO = (CommonVO) commonBaseVO;

		/*
		 * The system date is set in the start so that the same date can be used through out all to update audit fields
		 */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, DAOUtils.getValidityStartDate( getHibernateTemplate(), getSysDate() ) ); // Home and Travel so that month end closing date will effect in quote as well. 
		ThreadLevelContext.set( SvcConstants.TLC_KEY_VSD, DAOUtils.getValidityStartDate( getHibernateTemplate(), getSysDate() ) ); // Home and Travel so that month end closing date will effect in quote as well.
		ThreadLevelContext.set( SvcConstants.TLC_CURRENT_DATE, getSysDate() );
		/*
		 * Thread level context elements to track if Data has changed
		 */
		ThreadLevelContext.set( SvcConstants.TCL_KEY_HAS_DATA_CHANGED, false);
		
		/*
		 * Endorsement text generation table list tracker
		 */
		
		ThreadLevelContext.set( SvcConstants.TCL_KEY_ENDT_TABLE_LIST, new ArrayList<String>() );

		/* Set the copy commonvo to thread level context which will be available throughout the thread of execution
		 * a copy is set since in case of exception exception original VO is not changed
		*/
		ThreadLevelContext.set( SvcConstants.COMMONDATA, CopyUtils.copySerializableObject( commonVO ) );
		ThreadLevelContext.set( SvcConstants.TABLE_DATA, CopyUtils.copySerializableObject( toBeSaved ) );

		/*
		 * For each table get the list of table data to be saved
		 */
		try{
			
			for( Map.Entry<String, List<TableData>> tablesSet : dataToSave.entrySet() ){

				saveTableLevelRecs( tablesSet );
			}
			
			
			//generateEndtText();

			updatePoldata( (PolicyDataVO) ThreadLevelContext.get( SvcConstants.POLDATA ) );
			updateCommonVO( (CommonVO) ThreadLevelContext.get( SvcConstants.COMMONDATA ) );
		}
		catch( BusinessException e ){
			
			//In case of exception. The changes done on the value objects are reverted to the original state
			rollBackVOModification( toBeSaved, commonVO );
			logger.error("BusinessException : "+e);
			throw e;
		}
		catch (Exception unhandledExp){
			//In case of exception. The changes done on the value objects are reverted to the original state
			rollBackVOModification( toBeSaved, commonVO );
			throw new BusinessException( "", unhandledExp, "An exception occured while saving data");
		}

		/*if( updateOrRollBackTransaction( toBeSaved, commonVO ) ) return toBeSaved;*/
		 
		// The changes the the value objects is finalized and the original object is updated
		commitVOModification( commonVO );
		
		ThreadLevelContext.clearAll();
		logger.info("Exiting from BaseSaveOperation.executeSave() method");
		return toBeSaved;
	}





	/*
	 * If no data is changed, then roll back the record inserted into t_trn_policy(quo).
	 * This is required because we are inserting data into t_trn_policy at the beginning of the transaction to avoid foreign key
	 * Violation with content tables 
	 */
	private boolean updateOrRollBackTransaction( DataHolderVO<LinkedHashMap<String, List<TableData>>> toBeSaved, CommonVO commonVO ){
		logger.info("Entered BaseSaveOperation.updateOrRollBackTransaction() method");
		if(  !( (Boolean) ThreadLevelContext.get( SvcConstants.TCL_KEY_HAS_DATA_CHANGED ) ) ){
			rollBackVOModification( toBeSaved, commonVO );
			TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
			return true;
		}
		logger.info("Exiting from BaseSaveOperation.updateOrRollBackTransaction() method");
		return false;
	}



	/**
	 * @param tablesSet
	 */
	private void saveTableLevelRecs( Map.Entry<String, List<TableData>> tablesSet ){
		logger.info("Entered BaseSaveOperation.saveTableLevelRecs() method");
		final String tableInExecution = tablesSet.getKey();

		logger.debug( "Table in execution: " + tableInExecution);
		/*
		 * Set the table name in thread level context. 
		 */
		ThreadLevelContext.set( SvcConstants.TABLE_NAME, Utils.getSingleValueAppConfig( tableInExecution ) );

		/*
		 * For each table save the list of table data table data 
		 */
		for( TableData<BaseVO> tableData : tablesSet.getValue() ){
			saveIndividualRec( tableInExecution, tableData );
		}

		ThreadLevelContext.clear( SvcConstants.TABLE_NAME );
		logger.info("Exiting from BaseSaveOperation.saveTableLevelRecs() method");
	}

	/**
	 * @param tableInExecution
	 * @param tableData
	 */
	private void saveIndividualRec( final String tableInExecution, TableData<BaseVO> tableData ){
		logger.info("Entered BaseSaveOperation.saveIndividualRec() method");
		try{
			/*
			 * Based on tableInExecution we check if the table to be saved is T_TRN_POLICY or other tables
			 */
			logger.info("tableInExecution ="+tableInExecution);
			switch( resolveTableForExe( tableInExecution ) ){
				case T_TRN_POLICY:
					logger.debug( "Saving T_TRN_POLICY rec" );
					logger.info("Enters to T_TRN_POLICY(tableInExecution)");
					/*
					 * The save operation for t_trn_policy is to be handled here.
					 */
					saveOrUpdatePolicy( tableInExecution, tableData );

					/*
					 * The updated policy data will be saved in the thread context so that the data is available for transaction
					 */
					ThreadLevelContext.set( SvcConstants.POLDATA, tableData.getTableData() );
					logger.info("Exiting from T_TRN_POLICY(tableInExecution)");
					break;
				case OTHERS:
					logger.info("Enters to OTHERS(tableInExecution)");
					/*
					 * All other transaction tables are to be handled by saveOrUpdateTtrnTables
					 */
					//SaveCase ttrnSaveCase = saveOrUpdateTtrnTables( tableInExecution, tableData );
					saveOrUpdateTtrnTables( tableInExecution, tableData );
					logger.info("Exiting from OTHERS(tableInExecution)");
					break;

				default:
					break;
			}
		}
		catch( DataAccessException e ){
			throw new BusinessException( "", e, "An exception occured while saving data into: " + tableInExecution );
		}
		logger.info("Exiting from BaseSaveOperation.saveIndividualRec() method");
	}
	
	



	/**
	 * @param tableInExecution
	 * @param ttrnSaveCase 
	 * @param tableData
	 */
	private void saveOrUpdateTtrnPrmTable( String tableInExecution, SaveCase ttrnSaveCase, TableData<BaseVO> tableData ){
		logger.info("Entered BaseSaveOperation.saveOrUpdateTtrnPrmTable() method");
		POJOWrapper mappedPojo = SvcUtils.mapBean( tableInExecution, tableData, (CommonVO) ThreadLevelContext.get( SvcConstants.COMMONDATA ) );

		boolean isPrmRecRequired = checkIfPrmRecRequired( mappedPojo );

		if( !isPrmRecRequired ){
			return;
		}
		/*
		 * Derived attributes specific to tariff/scheme/user is to be mapped here 
		 */
		updateDerivedTtrnAttributes( tableInExecution, mappedPojo, tableData );

		POJOWrapper existingRecord = getIdBasedRec( SvcConstants.PREMIUM_INFO, mappedPojo );

		saveUpdateOrDeleteRec( SvcConstants.PREMIUM_INFO, tableData, mappedPojo, ttrnSaveCase, existingRecord );
		logger.info("Exiting from BaseSaveOperation.saveOrUpdateTtrnPrmTable() method");
	}



	/**
	 * @param policyDataVO
	 */
	private void updatePoldata( PolicyDataVO policyDataVO ){
		policyDataVO.setPolicyId(  (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_POLICY_ID ) );
		
	}

	/**
	 * This method is excepted to save data 
	 * @param tableInExecution
	 * @param tableData
	 */
	private SaveCase saveOrUpdateTtrnTables( String tableInExecution, TableData<BaseVO> tableData ){
		logger.info("Entered BaseSaveOperation.saveOrUpdateTtrnTables() method");
		/*
		 * Base on tableInExecution/ the instance of tableData the respective POJOWrapper is generated and mapped from VO.
		 */
		POJOWrapper mappedPojo = (POJOWrapper)Utils.getBean(tableInExecution);
		mappedPojo = SvcUtils.mapBean( tableInExecution, tableData, (CommonVO) ThreadLevelContext.get( SvcConstants.COMMONDATA ) );
		/*
		 * Derived attributes specific to tariff/scheme/user is to be mapped here 
		 */


		updateDerivedTtrnAttributes( tableInExecution, mappedPojo, tableData );
		
		/*
		 * If a new record has to be created for the first time isToBeCreated will return "SaveCase.CREATE"
		 */
		SaveCase saveCase = isToBeCreated( tableInExecution, mappedPojo );
		/*
		 * If the current record is to be deleted isToBeDeleted will return "SaveCase.CREATE"
		 */
		if( Utils.isEmpty( saveCase ) ){
			saveCase = isToBeDeleted( tableInExecution, mappedPojo, tableData );
		}
		
		/*
		 * Holder for the existing record
		 */
		PojoInstance pojoHolder = new PojoInstance();
		

		/*
		 * If SaveCase is empty or if the SaveCase is not create then try to get existing record and decide weather the savecase is
		 * 	SaveCase.CHANGE_WITH_NEW_REC;
		 *	SaveCase.CHANGE_WITH_EXISTING_REC;
		 *	SaveCase.DELETE_PENDING_REC;
		 */
		if( Utils.isEmpty( saveCase ) || ( !Utils.isEmpty( saveCase ) && !saveCase.equals( SaveCase.CREATE ) ) ){
			saveCase = saveCaseForExstingRec( tableInExecution, saveCase, mappedPojo, pojoHolder );
		}
		
		POJOWrapper existingRecord = pojoHolder.getPojo();
		
		if( Utils.isEmpty( saveCase ) ){
			setKeysToVO( tableInExecution, mappedPojo, tableData );
			return null;
		}

		saveUpdateOrDeleteRec( tableInExecution, tableData, mappedPojo, saveCase, existingRecord );
		logger.info("Exiting from BaseSaveOperation.saveOrUpdateTtrnTables() method");
		return saveCase;

	}


	/**
	 * @param tableInExecution
	 * @param tableData
	 * @param mappedPojo
	 * @param saveCase
	 * @param existingRecord
	 */
	private void saveUpdateOrDeleteRec( String tableInExecution, TableData<BaseVO> tableData, POJOWrapper mappedPojo, SaveCase saveCase, POJOWrapper existingRecord ){
		logger.info("Entered BaseSaveOperation.saveUpdateOrDeleteRec() method");
		mappedPojo.setEndtId((Long) ThreadLevelContext
				.get(SvcConstants.TLC_KEY_ENDT_ID));
		switch( saveCase ){
			case CREATE:
				logger.info("Enters to CREATE(saveCase)");
				mappedPojo.setPOJOWrapperId( setId( tableInExecution,mappedPojo.getPOJOWrapperId() , saveCase ) );
				setDefaultStatusVed( mappedPojo );
				setAuditDetailsForCreation( mappedPojo );
				preprocessBeforeSave(tableInExecution,mappedPojo,tableData,existingRecord , saveCase);
				saveData( mappedPojo );
				
				setKeysToVO( tableInExecution, mappedPojo, tableData );
				logger.info("Exits from CREATE(saveCase)");
				break;
			case DELETE:
				logger.info("Enters to DELETE(saveCase)");
				/*
				 * In case of delete, new record will be created with same data as the existing record and the existing record status
				 * will be updated with status 4 and VED will be updated with current versions VSD 
				 */

				// Create a copy of the existing record, the status will be updated to "Deleted Status" and updated to DB
				POJOWrapper previousRec = CopyUtils.copySerializableObject( existingRecord );
				getHibernateTemplate().evict( existingRecord );
				existingRecord.setPOJOWrapperId( setId( tableInExecution,existingRecord.getPOJOWrapperId(), saveCase ) );
				existingRecord.setStatus( SvcConstants.DEL_SEC_LOC_STATUS );
				existingRecord.setEndtId((Long) ThreadLevelContext.get(SvcConstants.TLC_KEY_ENDT_ID));
				setAuditDetailsForCreation( existingRecord );
				//preprocessBeforeSave(tableInExecution,mappedPojo,tableData,existingRecord , saveCase);
				preprocessBeforeSave(tableInExecution,existingRecord,tableData,previousRec , saveCase);
				saveData( existingRecord );

				previousRec.setValidityExpiryDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
				setAuditDetailsForUpdate( previousRec );
				updateData( previousRec );
				deleteKeysFromVO( tableInExecution, mappedPojo, tableData );
				logger.info("Exits from DELETE(saveCase)");
				break;

			case DELETE_PENDING_REC:
				logger.info("Enters to DELETE_PENDING_REC(saveCase)");
				deleteData( existingRecord );
				deleteKeysFromVO( tableInExecution, mappedPojo, tableData );
				logger.info("Exits from DELETE_PENDING_REC(saveCase)");
				break;
			case CHANGE_WITH_NEW_REC:
				logger.info("Enters to CHANGE_WITH_NEW_REC(saveCase)");
				mappedPojo.setPOJOWrapperId( setId( tableInExecution,mappedPojo.getPOJOWrapperId(), saveCase ) );
				setDefaultStatusVed( mappedPojo );
				setAuditDetailsForCreation( mappedPojo );
				
				preprocessBeforeSave(tableInExecution,mappedPojo,tableData,existingRecord, saveCase);
				if(!Utils.isEmpty( getHibernateTemplate().get( mappedPojo.getClass(), mappedPojo.getPOJOId() ) )){
					getHibernateTemplate().evict( mappedPojo );
					getHibernateTemplate().merge( mappedPojo );
					dataChanged();
				} else {
					saveData( mappedPojo );
				}
				//logger.info("*** Policy ID ="+mappedPojo.getPOJOWrapperId().getPolicyId()+",ENDT ID ="+mappedPojo.getPOJOWrapperId().getEndtId()+"****");
				logger.info("Validity Expiry Date :"+(Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ));
				existingRecord.setValidityExpiryDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
				
				update( existingRecord );
				setKeysToVO( tableInExecution, mappedPojo, tableData );
				logger.info("Exits from CHANGE_WITH_NEW_REC(saveCase)");
				break;
			case CHANGE_WITH_EXISTING_REC:
				logger.info("Enters to CHANGE_WITH_EXISTING_REC(saveCase)");
				setAuditDetailsForUpdate( mappedPojo );
				setDefaultStatusVed( mappedPojo );
				preprocessBeforeSave(tableInExecution,mappedPojo,tableData,existingRecord , saveCase);
				updateData( mappedPojo );
				setKeysToVO( tableInExecution, mappedPojo, tableData );
				logger.info("Exits from CHANGE_WITH_EXISTING_REC(saveCase)");
				break;

			default:
				break;
		}
		
		getHibernateTemplate().flush();
		logger.info("Exiting from BaseSaveOperation.saveUpdateOrDeleteRec() method");
	}


	private void deleteData( POJOWrapper dataToBeDeleted ){
		logger.info("Entered BaseSaveOperation.deleteData() method");
		delete( dataToBeDeleted );
		dataChanged();
		logger.info("Exiting from BaseSaveOperation.deleteData() method");
	}

	private void updateData( POJOWrapper dataToBeUpdated ){
		logger.info("Entered BaseSaveOperation.updateData() method");
		update( dataToBeUpdated );
		dataChanged();
		logger.info("Exiting from BaseSaveOperation.updateData() method");
	}

	private void saveData( POJOWrapper dataToBeSaved ){
		logger.info("Entered BaseSaveOperation.saveData() method");
		save( dataToBeSaved );
		dataChanged();
		logger.info("Exiting from BaseSaveOperation.saveData() method");
	}

	private void dataChanged(){
		logger.info("Entered BaseSaveOperation.dataChanged() method");
		if( !Utils.isEmpty( ThreadLevelContext.get( SvcConstants.TLC_KEY_TABLE_DATA_CHANGED ) ) && (Boolean) ThreadLevelContext.get( SvcConstants.TLC_KEY_TABLE_DATA_CHANGED ) ){
			List<String> tableList = (List<String>) ThreadLevelContext.get( SvcConstants.TCL_KEY_ENDT_TABLE_LIST );
			if( !Utils.isEmpty( tableList ) ){
				String tableName = ( (String) ThreadLevelContext.get( SvcConstants.TABLE_NAME ) ).toUpperCase();
				tableName = tableName.replace( "_QUO", "" );
				tableList.add( tableName );
			}
			else{
				tableList = new ArrayList<String>();
				//Table name should not be null here.
				String tableName = ( (String) ThreadLevelContext.get( SvcConstants.TABLE_NAME ) ).toUpperCase();
				tableName = tableName.replace( "_QUO", "" );
				tableList.add( tableName );
				ThreadLevelContext.set( SvcConstants.TCL_KEY_ENDT_TABLE_LIST, tableList );
			}
			
			ThreadLevelContext.set( SvcConstants.TLC_KEY_TABLE_DATA_CHANGED , false) ;
		}
		logger.info("Exiting from BaseSaveOperation.dataChanged() method");
		//ThreadLevelContext.set( SvcConstants.TCL_KEY_HAS_DATA_CHANGED, true );

	}


	/**
	 * @param tableInExecution
	 * @param mappedPojo
	 * @param tableData
	 * 
	 * This method is used to update changes before data getting saved and after sequence is generated
	 * @param existingRecord 
	 * @param saveCase 
	 */
	private void preprocessBeforeSave( String tableInExecution, POJOWrapper mappedPojo, TableData<BaseVO> tableData, POJOWrapper existingRecord, SaveCase saveCase ){
		
		String beanName = Utils.getSingleValueAppConfig( tableInExecution + "_DV" );
		if( !Utils.isEmpty( beanName )){
			IUpdateDerivedData updateDerivedData = (IUpdateDerivedData) Utils.getBean( beanName );
			updateDerivedData.preprocessBeforeSave( tableInExecution, mappedPojo, existingRecord , tableData, saveCase , getHibernateTemplate());
		}
		
	}



	/**
	 * @param mappedPojo
	 */
	private void setDefaultStatusVed( POJOWrapper mappedPojo ){
		mappedPojo.setValidityExpiryDate( SvcConstants.EXP_DATE );
		mappedPojo.setStatus( Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_PENDING" )  ) );
	}

	/**
	 * @param tableInExecution
	 * @param saveCase 
	 * @param mappedPojo
	 * @param existingRecord
	 * @return
	 */
	private SaveCase saveCaseForExstingRec( String tableInExecution, SaveCase saveCase, POJOWrapper mappedPojo, PojoInstance pojoHolder ){
		logger.info("Entered BaseSaveOperation.saveCaseForExstingRec() method");
		SaveCase resolvedSaveCase = null;

		if( !Utils.isEmpty( saveCase ) && saveCase.equals( SaveCase.CREATE ) ){
			resolvedSaveCase = SaveCase.CREATE;
			ThreadLevelContext.set( SvcConstants.TLC_KEY_TABLE_DATA_CHANGED, true);
		}
		else{

			pojoHolder.setPojo( getIdBasedRec( tableInExecution, mappedPojo ) );

			if( Utils.isEmpty( pojoHolder.getPojo() ) ){
				throw new BusinessException( "", null, "Unable to get the previous rec. Pls check the data validity: " );
			}

		}

		if( !Utils.isEmpty( saveCase ) && saveCase.equals( SaveCase.DELETE ) ){

			if( !Utils.isEmpty( pojoHolder.getPojo() ) && pojoHolder.getPojo().getStatus() == SvcConstants.POL_STATUS_PENDING ){

				resolvedSaveCase = SaveCase.DELETE_PENDING_REC;
				ThreadLevelContext.set( SvcConstants.TLC_KEY_TABLE_DATA_CHANGED, true);

			}
			else{

				resolvedSaveCase = SaveCase.DELETE;
				ThreadLevelContext.set( SvcConstants.TLC_KEY_TABLE_DATA_CHANGED, true);
			}

		}
		else{

			/* Figure out if the values have changed. If they haven't, then we will not take any action on this table. */
			boolean hasDataChanged = hasDataChanged( tableInExecution, mappedPojo, pojoHolder.getPojo() );
			ThreadLevelContext.set( SvcConstants.TLC_KEY_TABLE_DATA_CHANGED, hasDataChanged);

			/*
			 * Handling for tables that doesn't have status column. In this case we follow the save case of the policy record
			 */
			if( !Utils.isEmpty( pojoHolder.getPojo() )
					&& ( Utils.isEmpty( pojoHolder.getPojo().getStatus() ) || ( !Utils.isEmpty( pojoHolder.getPojo().getStatus() ) && pojoHolder.getPojo().getStatus() == CommonConstants.DEFAULT_LOW_INTEGER ) )
					&& ( hasDataChanged || ( (String) ThreadLevelContext.get( SvcConstants.TABLE_NAME ) ).equalsIgnoreCase( Utils.getSingleValueAppConfig( "GENERAL_INFORMATION" ) ) ) ){
				resolvedSaveCase = (SaveCase) ThreadLevelContext.get( SvcConstants.POL_SAVE_CASE );
			}

			if( hasDataChanged && !Utils.isEmpty( pojoHolder.getPojo() ) && !Utils.isEmpty( pojoHolder.getPojo().getStatus() )
					&& SvcConstants.POL_STATUS_ACTIVE == pojoHolder.getPojo().getStatus() ){
				resolvedSaveCase = SaveCase.CHANGE_WITH_NEW_REC;
			}
			else if( hasDataChanged && !Utils.isEmpty( pojoHolder.getPojo() ) && !Utils.isEmpty( pojoHolder.getPojo().getStatus() )
					&& SvcConstants.POL_STATUS_PENDING == pojoHolder.getPojo().getStatus() ){
				resolvedSaveCase = SaveCase.CHANGE_WITH_EXISTING_REC;
			}

		}
		
		/* Set the required data from pojoHolder if the save case is change with existing record.*/
		if(SaveCase.CHANGE_WITH_EXISTING_REC.equals( resolvedSaveCase )){
			mappedPojo.setPreparedBy( pojoHolder.getPojo().getPreparedBy() );
			mappedPojo.setPreparedDt( pojoHolder.getPojo().getPreparedDt() );
		}
		logger.info("Exiting from BaseSaveOperation.saveCaseForExstingRec() method");
		return resolvedSaveCase;
	}

	/**
	 * @param tableInExecution
	 * @param mappedPojo
	 * @return
	 */
	private POJOWrapper getIdBasedRec( String tableInExecution, POJOWrapper mappedPojo ){
		logger.info("Entered BaseSaveOperation.getIdBasedRec() method");
		try{
			
			POJOWrapper pojoWrapper=null;
			pojoWrapper= (POJOWrapper) getHibernateTemplate().get( Class.forName( Utils.getBean( tableInExecution ).getClass().getName() ),
					mappedPojo.getPOJOId() );
			if(tableInExecution.trim().equals("GACC_PERSON") && pojoWrapper==null){		                	  
          	  
				pojoWrapper=isGaccPerson(mappedPojo);
          		
            }
			logger.info("Exiting from BaseSaveOperation.getIdBasedRec() method");
			return pojoWrapper;
		}
		catch( ClassNotFoundException e ){

			throw new BusinessException( "", null, "Error while getting the intance of table for tableInExecution: " + tableInExecution );
		}
	}

	/**
	 * @param mappedPojo
	 */
	private void setAuditDetailsForUpdate( POJOWrapper mappedPojo ){
		if(getUserId() == SvcConstants.zeroVal){
			mappedPojo.setModifiedBy( getUserDetails().getUserId() );
		}else{	
			mappedPojo.setModifiedBy(getUserId());
		}		
		
		mappedPojo.setModifiedDt( (Date)ThreadLevelContext.get(SvcConstants.TLC_CURRENT_DATE) );

	}

	/**
	 * @param mappedPojo
	 */
	private void setAuditDetailsForCreation( POJOWrapper mappedPojo ){	
		if(getUserId() == SvcConstants.zeroVal){
			mappedPojo.setPreparedBy( getUserDetails().getUserId() );
		}else{	
			mappedPojo.setPreparedBy( getUserId() );
		}					
		mappedPojo.setPreparedDt( (Date)ThreadLevelContext.get(SvcConstants.TLC_CURRENT_DATE) );
		mappedPojo.setModifiedBy( null );
		mappedPojo.setModifiedDt( null );
	}

	/**
	 * @return
	 */
	//changed because If Scheduler is running, IRSAUser getRSAUser() was returning null
	private Integer getUserId(){
		if(Utils.isEmpty(( (CommonVO)ThreadLevelContext.get( SvcConstants.COMMONDATA )).getLoggedInUser().getUserId())){
			return SvcConstants.zeroVal;
		}
		return Integer.parseInt(( (CommonVO)ThreadLevelContext.get( SvcConstants.COMMONDATA )).getLoggedInUser().getUserId());
	}
	
	private IRSAUser getUserDetails(){
		return ( (UserProfile) ( (CommonVO) ThreadLevelContext.get( SvcConstants.COMMONDATA ) ).getLoggedInUser() ).getRsaUser();
	}

	/**
	 * In case of delete, the ID's are deleted from the table data so that, no the next save the 
	 * @param tableInExecution
	 * @param mappedPojo
	 * @param tableData
	 */
	private void deleteKeysFromVO( String tableInExecution, POJOWrapper mappedPojo, TableData<BaseVO> tableData ){
		tableData.setContentID( null );
		tableData.setContentVsd( null );
	}

	/**
	 * @param tableInExecution
	 * @param mappedPojo
	 * @param tableData
	 */
	private void setKeysToVO( String tableInExecution, POJOWrapper mappedPojo, TableData<BaseVO> tableData ){
		tableData.setContentID( mappedPojo.getPOJOWrapperId().getId() );
		tableData.setContentVsd( mappedPojo.getPOJOWrapperId().getVSD() );
	}

	/** This is a generic method to set the ID's for any pojo that extends class POJO and the id implements the POJOId interface
	 * @param tableInExecution
	 * @param pojoId 
	 * @param saveCase 
	 * @param mappedPojo 
	 * @return
	 */
	protected POJOWrapperId setId( String tableInExecution, POJOWrapperId pojoId, SaveCase saveCase ){

		String tableIdName = (String) ThreadLevelContext.get( SvcConstants.TABLE_NAME );
		String[] idConfigaration = Utils.getMultiValueAppConfig( tableIdName + "_IDS" );
		String tableSequence = Utils.getSingleValueAppConfig( tableIdName + "_SEQUENCE" );
		//POJOWrapperId tableID = (POJOWrapperId) Utils.getBean( tableIdName + "_ID" );
		POJOWrapperId tableID = (POJOWrapperId) Utils.getBean( tableInExecution + "_ID" );
		PolicyDataVO polData = (PolicyDataVO) ThreadLevelContext.get( SvcConstants.POLDATA );

		if( !Utils.isEmpty( pojoId ) ){
			tableID =  CopyUtils.copySerializableObject( pojoId )    ;
		}

		if( !Utils.isEmpty( idConfigaration ) ){

			for( String keyValue : idConfigaration ){

				switch( ID.valueOf( keyValue ) ){
					case SEQUENCE_ID:
						if( !( saveCase.equals( SaveCase.DELETE ) || saveCase.equals( SaveCase.CHANGE_WITH_NEW_REC ) ) ){
							tableID.setId( !Utils.isEmpty( tableSequence ) ? NextSequenceValue.getNextSequence( tableSequence, Integer.valueOf( polData.getPolicyType() ),
							polData.getPolicyClassCode(), null, null, getHibernateTemplate() ) : null );
						}

						break;

					case ENDT_ID:
						tableID.setEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
						break;

					case POLICY_ID:
						tableID.setPolicyId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_POLICY_ID ) );
						break;

					default:
						break;
				}
			}

		}

		if( saveCase.equals( SaveCase.CREATE ) || saveCase.equals( SaveCase.DELETE ) || saveCase.equals( SaveCase.CHANGE_WITH_NEW_REC ) ){
			tableID.setVSD( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		}
		
		logger.debug( "Id Generated for " + tableIdName + " is " +  tableID.toStringPojoId() );

		return tableID;
	}

	/**Based on the flag  <code>toBeDeleted</code> set in the in tableData to be deleted case is decided
	 * @param tableInExecution 
	 * @param mappedPojo
	 * @param tableData 
	 * @return
	 */
	protected SaveCase isToBeDeleted( String tableInExecution, POJOWrapper mappedPojo, TableData<BaseVO> tableData ){

		if( tableData.isToBeDeleted() ){
			mappedPojo.setStatus( SvcConstants.DEL_SEC_LOC_STATUS );
			return SaveCase.DELETE;
		}

		return null;
	}

	/**
	 * Based on the mappedPojo SaveCase.CREATE is derived, if id is null then the record has to be created.
	 * @param tableInExecution 
	 * @param mappedPojo
	 * @return
	 */
	private SaveCase isToBeCreated( String tableInExecution, POJOWrapper mappedPojo ){

		/*if( Utils.isEmpty( mappedPojo.getId() ) || ( !Utils.isEmpty( mappedPojo.getPOJOWrapperId() ) && Utils.isEmpty( mappedPojo.getPOJOWrapperId().getId() ) )){

		                  return SaveCase.CREATE;
		            }*/
					/* Code given by Vikas*/
					if(Utils.isEmpty( mappedPojo.getPOJOWrapperId()) || ( !Utils.isEmpty( mappedPojo.getPOJOWrapperId()) && mappedPojo.getPOJOWrapperId().getId()==0 )){
						ThreadLevelContext.set( SvcConstants.TLC_KEY_TABLE_DATA_CHANGED, true);
		                  return SaveCase.CREATE;
		            }
		            
		            POJOWrapper pojoWrapper;
		            try {

		                  pojoWrapper = (POJOWrapper) getHibernateTemplate().get( Class.forName( com.mindtree.ruc.cmn.utils.Utils.getBean(tableInExecution.trim()).getClass().getName() ),
		                              mappedPojo.getPOJOWrapperId());
		                  if(tableInExecution.trim().equals("GACC_PERSON") && pojoWrapper==null){		                	  
		                	  pojoWrapper=isGaccPerson(mappedPojo);
		                		
		                  }
		            } catch( ClassNotFoundException e ){
		                  throw new BusinessException( "", null, "Error while getting the intance of table for tableInExecution: " + tableInExecution );
		            }
		            
		            if(Utils.isEmpty(pojoWrapper)){
		            	ThreadLevelContext.set( SvcConstants.TLC_KEY_TABLE_DATA_CHANGED, true);
		                return SaveCase.CREATE;
		            }
		            return null;
		      }
	
	/*Added new method to handle multiple travellers issue during Endorsement*/
	private POJOWrapper isGaccPerson( POJOWrapper mappedPojo){
		  POJOWrapper pojoWrapper = null;
		  Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();	
		  Query query = session.createQuery("FROM TTrnGaccPersonQuo where id.gprId=:gprId");
		  query.setLong("gprId",mappedPojo.getPOJOWrapperId().getId());
		  List<TTrnGaccPersonQuo> results = query.list();		
		  System.out.println("results :"+results+"Size :"+results.size());
		  Iterator<TTrnGaccPersonQuo> itr=results.iterator();  
			 while(itr.hasNext()){  
				 pojoWrapper=itr.next();  
		  }
			return pojoWrapper;    	  
		
	}


	/**
	 * @param tableInExecution 
	 * @param mappedPojo
	 * @param tableData 
	 */
	protected void updateDerivedTtrnAttributes( String tableInExecution, POJOWrapper mappedPojo, TableData<BaseVO> tableData ){

		String beanName = Utils.getSingleValueAppConfig( tableInExecution + "_DV" );
		if( !Utils.isEmpty( beanName )){
			IUpdateDerivedData updateDerivedData = (IUpdateDerivedData) Utils.getBean( beanName );
			updateDerivedData.updateDerivedValues( tableInExecution, mappedPojo, tableData, getHibernateTemplate() );
		}
		
	}

	/**
	 * @param policy
	 * @param polData
	 * @param object
	 */
	protected void updateDerivedPolicyAttributes( TTrnPolicyQuo policy, PolicyDataVO polData, CommonVO commonVO ){
		
		TableData<BaseVO> polTableData = new TableData<BaseVO>();
		polTableData.setTableData( polData );
		updateDerivedTtrnAttributes(SvcConstants.T_TRN_POLICY, policy, polTableData);
	}

	/**
	 * @param tableInExecution
	 * @param tableData
	 * @return 
	 */
	private void saveOrUpdatePolicy( String tableInExecution, TableData<BaseVO> tableData ){
		logger.info("Entered BaseSaveOperation.saveOrUpdatePolicy() method");
		/*
		 * a, Check if its create quote flow - First time save . This can be identified if Quotation no is null.
		 * 	  if true set policyid, endt id, endt no, VSD 
		 * b, If policy id and and endt id is present, then check status to set new endt id and vsd
		 */
		PolicyDataVO polData = (PolicyDataVO) tableData.getTableData();

		TTrnPolicyQuo policy = (TTrnPolicyQuo) SvcUtils.mapBean( tableInExecution, tableData, (CommonVO) ThreadLevelContext.get( SvcConstants.COMMONDATA ) );
		
		//TODO: Logic to be moved to respective service
		policy.setEndtId( ( (CommonVO) ThreadLevelContext.get( SvcConstants.COMMONDATA )  ).getEndtId() );

		/*
		 * Try to get the existing policy record, in case a new record is created the ved of the existing record has to be updated.
		 */
		
		TTrnPolicyQuo existingPolicyData = null;
		
		if( !Utils.isEmpty( policy.getId() ) && !Utils.isEmpty( policy.getId().getPolicyId() ) && !Utils.isEmpty( policy.getId().getEndtId() ) ){
			if(policy.getId().getPolicyId()!=0){//TODO It should not be mapped as 0. Check this.
				existingPolicyData = getHibernateTemplate().get( TTrnPolicyQuo.class, policy.getId() );
			}
		}
		
				
		/*Added to check if user is operating on the latest record. Added the logic to address issues with concurrent Transaction - AdventId 119366 */
		
		if(!Utils.isEmpty(polData.getCommonVO()) && !Utils.isEmpty(polData.getCommonVO().getVsd()) && !Utils.isEmpty(existingPolicyData) 
				&& ((!Utils.isEmpty(((CommonVO) ThreadLevelContext.get( SvcConstants.COMMONDATA )).getChannel()) && ((CommonVO) ThreadLevelContext.get( SvcConstants.COMMONDATA )).getChannel().getId() != 2 )
				|| Utils.isEmpty(((CommonVO) ThreadLevelContext.get( SvcConstants.COMMONDATA )).getChannel())))
		{
			Boolean hasStatusChanged = polData.getCommonVO().getStatus().byteValue() != existingPolicyData.getPolStatus();
			
			CommonVO comVO = (CommonVO) ThreadLevelContext.get(SvcConstants.COMMONDATA);
			if (hasStatusChanged && !Utils.isEmpty(comVO.getAppFlow()) && (comVO.getAppFlow().equals(Flow.RESOLVE_REFERAL) 
					|| comVO.getAppFlow().equals(Flow.EDIT_QUO) )) {
				hasStatusChanged = polData.getStatus().byteValue() != existingPolicyData.getPolStatus();
			}
			SimpleDateFormat sd = new SimpleDateFormat("dd-MMM-yy");
			String expDate = sd.format(existingPolicyData.getPolValidityExpiryDate());
			if (!polData.getCommonVO().getVsd().equals(existingPolicyData.getPolValidityStartDate())
					|| hasStatusChanged || !(expDate).equals(SvcConstants.DFAULT_VED)) { // 143627
				
				
				if(!Utils.isEmpty(SvcConstants.QUOTE_ACCEPT)) { //152534
					byte quoteAccStat = Integer.valueOf(SvcConstants.QUOTE_ACCEPT).byteValue(); //23
					if(!(existingPolicyData.getPolStatus() == quoteAccStat)) {
						throw new BusinessException(
								"pas.concurrent.transaction.by.multiple.users",null,
								"The same quote is edited by Another User please do transaction search once again to have the latest update.");
					}
				}
				
				
			}
		}
		
			
		
		/*Check for policy data change*/
		boolean hasDataChanged = hasDataChanged( tableInExecution, policy, existingPolicyData );
		ThreadLevelContext.set( SvcConstants.TLC_KEY_TABLE_DATA_CHANGED, hasDataChanged);
		if(hasDataChanged){
			dataChanged();
			
		}
		
		SaveCase polSaveCase = resolvePolIdEndtId( polData, policy, existingPolicyData );

		/* Set preparedby and preparedDt in case of change with existing record.*/
		if( SaveCase.CHANGE_WITH_EXISTING_REC.equals( polSaveCase ) || SaveCase.CHANGE_WITH_NEW_REC.equals( polSaveCase )){
			if( Utils.isEmpty( existingPolicyData ) ){
				throw new BusinessException( "", null, "Existing record not found for the transaction." );
			}
			policy.setPolPreparedBy( existingPolicyData.getPolPreparedBy() );
			policy.setPolPreparedDt( existingPolicyData.getPolPreparedDt() );
			// Added by Anveshan. Yes. it is by Anveshan
			if(Utils.isEmpty(policy.getPolBusinessType()) && !Utils.isEmpty(existingPolicyData.getPolBusinessType()))
			{
				policy.setPolBusinessType(existingPolicyData.getPolBusinessType());
			}
		}
		
		/*
		 * Update the attributes that are specific for tariff/scheme/user 
		 */
		updateDerivedPolicyAttributes( ( policy ), polData, (CommonVO) ThreadLevelContext.get( SvcConstants.COMMONDATA ) );

		logger.debug( "Id Generated for " + tableInExecution + " is " +  policy.getId().toStringPojoId() );
		
		preprocessBeforeSave( tableInExecution, policy, tableData, existingPolicyData, polSaveCase );
		
		if( !Utils.isEmpty( existingPolicyData ) ){
			policy.setPolModifiedBy( SvcUtils.getUserId( (CommonVO) ThreadLevelContext.get( SvcConstants.COMMONDATA ) ) );
			policy.setPolModifiedDt( (Date) ThreadLevelContext.get( SvcConstants.TLC_CURRENT_DATE ) );
		}

		
		Boolean vatResult = DAOUtils.isVatEnabled();
		// condition check when, POL_VATABLE_PRM(VAT Premium field) column should only be updated when VAT is enabled.
		//System.out.println("before Value in BaseSaveOperation.java--" +vatResult+":: Vatable Amount :"+policy.getPolVattableAmt());
		if(!Utils.isEmpty(policy.getPolVattableAmt()) && vatResult.equals(Boolean.TRUE)){
			policy.setPolVattableAmt(BigDecimal.valueOf(SvcConstants.zeroVal));
		}
		//System.out.println("after Value in BaseSaveOperation.java--" +vatResult+":: Vatable Amount :"+policy.getPolVattableAmt());
		
		if(!Utils.isEmpty( getHibernateTemplate().get( TTrnPolicyQuo.class, policy.getId() ) )){
			getHibernateTemplate().merge( policy );
		}else{
			saveOrUpdate( policy );
		}
		
		ThreadLevelContext.set( SvcConstants.TLC_KEY_POLICY_REC, policy );

		getHibernateTemplate().flush();
		if( SaveCase.CHANGE_WITH_EXISTING_REC.equals( polSaveCase ) ){
			( (CommonVO) ThreadLevelContext.get( SvcConstants.COMMONDATA ) ).setStatus( existingPolicyData.getStatus() );
		} else {
			( (CommonVO) ThreadLevelContext.get( SvcConstants.COMMONDATA ) ).setStatus( SvcConstants.POL_STATUS_PENDING );
		}

		if( !Utils.isEmpty( polSaveCase ) && polSaveCase.equals( SaveCase.CHANGE_WITH_NEW_REC ) && !Utils.isEmpty( existingPolicyData )
				&& !existingPolicyData.getId().equals( policy.getId() ) ){

			existingPolicyData.setValidityExpiryDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			update( existingPolicyData );
		}

		/*
		 * The save case of the policy is set in the context so that the any record that is processed further which doesn't have status column will
		 * inherit the save case of policy record
		 */
		ThreadLevelContext.set( SvcConstants.POL_SAVE_CASE, polSaveCase );
		logger.info("Exiting from BaseSaveOperation.saveOrUpdatePolicy() method");
	}



	/**
	 * This method checks if the data has changed for a particular record with comparison to the previous record 
	 * @param tableInExecution
	 * @param newVal
	 * @param oldVal
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private boolean hasDataChanged( String tableInExecution, POJOWrapper newVal, POJOWrapper oldVal ){
		boolean hasDataChaged = false;
		
		if(Utils.isEmpty( oldVal )){
			return  true;
		}
		try{
			hasDataChaged = SvcUtils.compareBeans( newVal, oldVal, getFields( tableInExecution ) );
		}
		catch( IllegalAccessException e ){
			throw new BusinessException( "cmn.unknownError", e, "Error while comparing fields for_1", tableInExecution );
		}
		catch( InvocationTargetException e ){
			throw new BusinessException( "cmn.unknownError", e, "Error while comparing fields for_2", tableInExecution );
		}
		catch( NoSuchMethodException e ){
			throw new BusinessException( "cmn.unknownError", e, "Error while comparing fields for_3", tableInExecution );
		}

		return hasDataChaged;
	}

	/*private void getPolicyId(PolicyDataVO polData){
		CommonVO commonVO = (CommonVO) ThreadLevelContext.get( SvcConstants.COMMONDATA ); 
		TTrnPolicyQuo latestPolRecord = null;
		List<TTrnPolicyQuo> polQuoRecordList ; 
		if(commonVO.getIsQuote()){ 
			polQuoRecordList = (List<TTrnPolicyQuo>) getHibernateTemplate() .find("from TTrnPolicyQuo polQuo where polQuo.polQuotationNo=? and polQuo.id.polEndtId=? ", commonVO.getQuoteNo(), commonVO.getEndtId()); 
			} else { 
				polQuoRecordList = (List<TTrnPolicyQuo>) getHibernateTemplate() .find("from TTrnPolicyQuo polQuo where polQuo.polQuotationNo=? and polQuo.id.polEndtId=? ", commonVO.getPolicyNo(), commonVO.getEndtId()); 
				} 
		if( !Utils.isEmpty( polQuoRecordList ) ) latestPolRecord = polQuoRecordList.get( SvcConstants.zeroVal ); 
	    polData.setPolicyId(latestPolRecord.getId().getPolPolicyId());
	}*/


	private String[] getFields( String tableInExecution ){
		return Utils.getMultiValueAppConfig( "COMPARE_"+tableInExecution);
	}



	/**
	 * @param polData
	 * @param policy 
	 * @param existingPolicyData 
	 * @return 
	 */
	private SaveCase resolvePolIdEndtId( PolicyDataVO polData, TTrnPolicyQuo policy, TTrnPolicyQuo existingPolicyData ){
		logger.info("Entered BaseSaveOperation.resolvePolIdEndtId() method");
		/*
		 * Will check if the status is 1 set new endt id and new VSD
		 * For other status set the old endt id and VSD
		 */

		CommonVO commonVO = (CommonVO) ThreadLevelContext.get( SvcConstants.COMMONDATA );
		SaveCase polSaveCase = null;

		if( Utils.isEmpty( commonVO.getPolicyId() ) ){
			ThreadLevelContext.set( SvcConstants.TLC_KEY_POLICY_ID, NextSequenceValue.getNextSequence( SvcConstants.SEQ_QUOTATION_ID,
					Integer.valueOf( polData.getPolicyType() ), polData.getPolicyClassCode(), null, null, getHibernateTemplate() ) );
		}
		ThreadLevelContext.set( SvcConstants.TLC_KEY_ENDT_ID, Long.valueOf( SvcConstants.zeroVal ) );
		ThreadLevelContext.set( SvcConstants.TLC_KEY_ENDT_NO, Long.valueOf( SvcConstants.zeroVal ) );
		ThreadLevelContext.set( SvcConstants.TLC_KEY_VSD, ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
		
		/*
		 * If the transaction is quotation and the quotation no is null, then the quotation is being saved for the first time.
		 * Generate the quotation no and policy id from sequence
		 * set endorsement id to 0
		 * set endorsement no to 0
		 * 
		 * If Quotation no is not null, check the status and generate new endorsement 
		 * Set the new endorsement id and endorsement no
		 */		
		if( commonVO.getIsQuote() ){ 

			if( Utils.isEmpty( commonVO.getQuoteNo() ) ){
				/*String quoteSeq = null;
				if(polData.getPolicyType().equals(Integer.valueOf(SvcConstants.HOME_POL_TYPE))){
					quoteSeq = SvcConstants.SEQ_HOME_QUOTATION_NO;
				} else if ( (polData.getPolicyType().equals(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE ))) || 
						(polData.getPolicyType().equals(Integer.valueOf(SvcConstants.LONG_TRAVEL_POL_TYPE)))){
					quoteSeq = SvcConstants.SEQ_TRAVEL_QUOTATION_NO;
				}else if(polData.getPolicyType().equals(Integer.valueOf(SvcConstants.WC_POLICY_TYPE))){
					quoteSeq = SvcConstants.SEQ_WC_QUO_NO;
				}*/
				String quoteSeq= Utils.getSingleValueAppConfig( "SEQ_QUO_NO_"+commonVO.getLob());
				
				/* Added by Anveshan. In Oman for both long term and short term travel, we need to send policy type a 6*/
				if((SvcConstants.OMAN.toString().equalsIgnoreCase(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION))) &&
						polData.getPolicyType().equals(Integer.valueOf(SvcConstants.LONG_TRAVEL_POL_TYPE )) )
				{
					commonVO.setQuoteNo( NextSequenceValue.getNextSequence( quoteSeq, Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE ), polData.getPolicyClassCode(),
							Utils.getSingleValueAppConfig( "TRAN_TYPE_QUO" ), commonVO.getLocCode(), getHibernateTemplate() ) );
				}
				else
				{
					commonVO.setQuoteNo( NextSequenceValue.getNextSequence( quoteSeq, Integer.valueOf( polData.getPolicyType() ), polData.getPolicyClassCode(),
							Utils.getSingleValueAppConfig( "TRAN_TYPE_QUO" ), commonVO.getLocCode(), getHibernateTemplate() ) );
				}
				

			//	polData.setPolicyId( NextSequenceValue.getNextSequence( SvcConstants.SEQ_POLICY_ID, null, null, getHibernateTemplate() ) );
				setAuditDetailsForCreation( policy );
				polSaveCase = SaveCase.CREATE;

			}
			else{
				polSaveCase = resolveVersioningForPolicy( polData, policy, commonVO, polSaveCase );
			}
			
			if(Utils.isEmpty( policy.getPolDocumentCode() ) || policy.getPolDocumentCode() == SvcConstants.zeroVal){
				if(!Utils.isEmpty( existingPolicyData ) && !Utils.isEmpty( existingPolicyData.getPolDocumentCode() )){
					policy.setPolDocumentCode( existingPolicyData.getPolDocumentCode() );
				}else{
					policy.setPolDocumentCode(Short.valueOf(  SvcConstants.QUOTE_DOC_ID ));
				}
			}
			
		}
		else{

			polSaveCase = resolveVersioningForPolicy( polData, policy, commonVO, polSaveCase );
			policy.setPolDocumentCode(Short.valueOf(  SvcConstants.ENDT_DOC_ID ));
			//ENDT_DOC_ID
			
		}


		if( Utils.isEmpty( policy.getId() ) || SaveCase.CREATE.equals( polSaveCase ) || SaveCase.CHANGE_WITH_NEW_REC.equals( polSaveCase ) ){
			policy.setId( (TTrnPolicyQuoId) Utils.getBean( "T_TRN_POLICY_ID" ) );
		}
		policy.setPolQuotationNo( commonVO.getQuoteNo() );
		policy.setPolPolicyNo( commonVO.getPolicyNo() );
		policy.setPolPolicyYear(Short.valueOf( String.valueOf(SvcUtils.getYearFromDate(policy.getPolEffectiveDate()))));
		policy.getId().setEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		policy.getId().setPolicyId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_POLICY_ID ) );
		policy.setEndtNo( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_NO ) );
		policy.setValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		policy.setValidityExpiryDate( SvcConstants.EXP_DATE );
		
		commonVO.setDocCode( policy.getPolDocumentCode() );

		logger.debug( "Policy Save case is :" + polSaveCase);
		logger.info("Exiting from BaseSaveOperation.resolvePolIdEndtId() method");
		return polSaveCase;
	}



	/**
	 * @param polData
	 * @param policy
	 * @param commonVO
	 * @param polSaveCase
	 * @return
	 */
	private SaveCase resolveVersioningForPolicy( PolicyDataVO polData, TTrnPolicyQuo policy, CommonVO commonVO, SaveCase polSaveCase ){
		logger.info("Entered BaseSaveOperation.resolveVersioningForPolicy() method");
		/*
		 * If the status is Declined then the quote should not be activated.
		 */
		TTrnPolicyQuo existingPolicyData = getHibernateTemplate().get( TTrnPolicyQuo.class, policy.getId() ) ;
		/*
		 * If the status is converted to policy then the quote should not be editable
		 * 
		 */
		logger.info("CommonVO Status = "+commonVO.getStatus().toString());
		if( Utils.getSingleValueAppConfig( SvcConstants.CONV_TO_POL ).equalsIgnoreCase( commonVO.getStatus().toString() ) ){
			throw new BusinessException( "", null, "Trying to edit a quote that is converted to policy " );
		}
		else if(!Utils.isEmpty( existingPolicyData )&& (existingPolicyData.getStatus()==21 || existingPolicyData.getStatus()==22))
		{
			throw new BusinessException( "pas.quote.declined", null, "Trying to save a quote that is declined " );
		}
		else if( NON_VERSION_STATUS.contains( commonVO.getStatus().toString() ) ){

			/*
			 * If Policy Id is null that means new class code LOB is added for an existing quote
			 */
			if( Utils.isEmpty( commonVO.getPolicyId() ) ){
					//polData.setPolicyId( NextSequenceValue.getNextSequence( SvcConstants.SEQ_POLICY_ID, null, null, getHibernateTemplate() ) );

				ThreadLevelContext.set( SvcConstants.TLC_KEY_POLICY_ID, NextSequenceValue.getNextSequence( SvcConstants.SEQ_QUOTATION_ID, Integer.valueOf(polData.getPolicyType()),polData.getPolicyClassCode(), null, null, getHibernateTemplate() ));
				setAuditDetailsForCreation( policy );
				polSaveCase = SaveCase.CHANGE_WITH_NEW_REC;
			}
			else{
				setAuditDetailsForUpdate( policy );
				polSaveCase = SaveCase.CHANGE_WITH_EXISTING_REC;
				ThreadLevelContext.set( SvcConstants.TLC_KEY_POLICY_ID, policy.getId().getPolicyId() );
			}
			
			if(Flow.EDIT_QUO.equals(commonVO.getAppFlow()) && QUOTE_LAPSED.equals( commonVO.getStatus())){
				policy.setPolStatus(SvcConstants.POL_STATUS_PENDING.byteValue());
				polData.setStatus(SvcConstants.POL_STATUS_PENDING.intValue());
			}

			/*
			 * If the status is not version able then the vsd in thread context is set to the vsd loaded in common VO 
			 */
			ThreadLevelContext.set( SvcConstants.TLC_KEY_VSD, commonVO.getVsd() );
			ThreadLevelContext.set( SvcConstants.TLC_KEY_ENDT_NO, commonVO.getEndtNo() );
			ThreadLevelContext.set( SvcConstants.TLC_KEY_ENDT_ID, commonVO.getEndtId() );

		}
		else if( VERSION_STATUS.contains( commonVO.getStatus().toString() ) ){

			/*
			 * If Policy Id is null that means new class code LOB is added for an existing quote
			 */
			if( Utils.isEmpty( commonVO.getPolicyId()  ) ){
				//polData.setPolicyId( NextSequenceValue.getNextSequence( SvcConstants.SEQ_POLICY_ID, null, null, getHibernateTemplate() ) );
				ThreadLevelContext.set( SvcConstants.TLC_KEY_POLICY_ID, NextSequenceValue.getNextSequence( SvcConstants.SEQ_QUOTATION_ID, Integer.valueOf(polData.getPolicyType()),polData.getPolicyClassCode(),null, null, getHibernateTemplate() ));
			}else{
				ThreadLevelContext.set( SvcConstants.TLC_KEY_POLICY_ID, commonVO.getPolicyId() );
			}
				
			/*
			 * If the status is version able then set the new endt id 
			 */
			if(commonVO.getIsQuote()){
				ThreadLevelContext.set( SvcConstants.TLC_KEY_ENDT_ID,NextSequenceValue.getNextSequence( SvcConstants.SEQ_QUO_ENDORSEMENT_ID, Integer.valueOf(polData.getPolicyType()),polData.getPolicyClassCode() ,null, null, getHibernateTemplate() ) );
			} else {
				ThreadLevelContext.set( SvcConstants.TLC_KEY_ENDT_ID,NextSequenceValue.getNextSequence( SvcConstants.SEQ_ENDORSEMENT_ID, Integer.valueOf(polData.getPolicyType()),polData.getPolicyClassCode() ,null, null, getHibernateTemplate() ) );
			}
			ThreadLevelContext.set( SvcConstants.TLC_KEY_ENDT_NO, Long.valueOf( commonVO.getEndtNo() + 1 ) );
			
			if( DAOUtils.validateVSD( commonVO, getHibernateTemplate() ) ){
				ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, SvcUtils.incrementVSD( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) ) );
				ThreadLevelContext.set( SvcConstants.TLC_KEY_VSD, ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
			}
			else{
				ThreadLevelContext.set( SvcConstants.TLC_KEY_VSD, ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
			}
			
			setAuditDetailsForCreation( policy );
			if(!Flow.RESOLVE_REFERAL.equals(commonVO.getAppFlow())){
				policy.setPolStatus(SvcConstants.POL_STATUS_PENDING.byteValue());
				polData.setStatus(SvcConstants.POL_STATUS_PENDING.intValue());
			}

			polSaveCase = SaveCase.CHANGE_WITH_NEW_REC;
		}
		else{
			throw new BusinessException( "", null, "Trying to edit a quote whose status is neither version able or non version able" );
		}
		logger.info("Exiting from BaseSaveOperation.resolveVersioningForPolicy() method");
		return polSaveCase;
	}

	/** If the first table to be saved is not policy table throw an exception. In case of any save operation the first table to be processed will be t_trn_policy.
	 * 
	 * @param dataToSave
	 */
	private void checkPolicyRec( LinkedHashMap<String, List<TableData>> dataToSave ){
		logger.info("Entered BaseSaveOperation.checkPolicyRec() method");
		if( Utils.isEmpty( dataToSave ) ) throw new BusinessException( "", null, "Save data is null" );
		for( Map.Entry<String, List<TableData>> tablesSet : dataToSave.entrySet() ){
			switch( resolveTableForExe( tablesSet.getKey() ) ){
				case T_TRN_POLICY:
					break;
				default: throw new BusinessException( "", null, "First entry in toBeSaved should be policyData for any case" );
			}
			break;
		}
		logger.info("Exiting from BaseSaveOperation.checkPolicyRec() method");
	}

	/**
	 * @param commonVO
	 */
	private void updateCommonVO( CommonVO commonVO ){
		commonVO.setEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		commonVO.setEndtNo( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_NO ) );
		commonVO.setVsd( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );

		// Update the policy Id also back to the commonVO. 
		commonVO.setPolicyId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_POLICY_ID ) );
		commonVO.setPolEffectiveDate( ((TTrnPolicyQuo)ThreadLevelContext.get( SvcConstants.TLC_KEY_POLICY_REC )).getPolEffectiveDate() );
		commonVO.setCreatedOn( (Timestamp) ((TTrnPolicyQuo)ThreadLevelContext.get( SvcConstants.TLC_KEY_POLICY_REC )).getPolPreparedDt() );

	}
	
	/**
	 * @param mappedPojo
	 * @return 
	 */
	private boolean checkIfPrmRecRequired( POJOWrapper mappedPojo ){
		
		return false;
	}

	/**
	 * @param tableInExecution
	 * @return
	 */
	private BASETABLE resolveTableForExe( String tableInExecution ){

		BASETABLE[] tables = BASETABLE.values();
		for( BASETABLE table : tables ){
			if( table.toString().equalsIgnoreCase( tableInExecution ) ) return BASETABLE.valueOf( tableInExecution );
		}
		return BASETABLE.OTHERS;
	}

	public enum BASETABLE{
		T_TRN_POLICY, OTHERS
	};

	public enum ID{
		SEQUENCE_ID, POLICY_ID, ENDT_ID
	};
	
	
	/*
	 * Since pojo is a abstract call an instance cannot be created
	 */
	private class PojoInstance{
		private POJOWrapper pojo;

		/**
		 * @return the pojo
		 */
		public POJOWrapper getPojo(){
			return pojo;
		}

		/**
		 * @param pojo the pojo to set
		 */
		public void setPojo( POJOWrapper pojo ){
			this.pojo = pojo;
		}

	}
	
	/**
	 * @param toBeSaved
	 * @param commonVO
	 */
	private void rollBackVOModification( DataHolderVO<LinkedHashMap<String, List<TableData>>> toBeSaved, CommonVO commonVO ){
		logger.info("Entered BaseSaveOperation.rollBackVOModification() method");
		try{
			BEANUTIL.getConvertUtils().register( new org.apache.commons.beanutils.converters.BigDecimalConverter( null ), BigDecimal.class );
			BEANUTIL.getConvertUtils().register( new org.apache.commons.beanutils.converters.SqlTimestampConverter( null ), Timestamp.class );
			BEANUTIL.copyProperties( toBeSaved, ThreadLevelContext.get( SvcConstants.TABLE_DATA ) );
		}
		catch( IllegalAccessException e1 ){
			throw new BusinessException( "", e1, "An IllegalAccessException exception occured while committing the VO:  " + commonVO.getClass().getSimpleName() );
		}
		catch( InvocationTargetException e2 ){
			throw new BusinessException( "", e2, "An InvocationTargetException exception occured while committing the VO: " + commonVO.getClass().getSimpleName() );
		}
		logger.info("Exiting from BaseSaveOperation.rollBackVOModification() method");
	}
	
	
	/**
	 * @param commonVO
	 */
	private void commitVOModification( CommonVO commonVO ){
		logger.info("Entered BaseSaveOperation.commitVOModification() method");
		try{
			BEANUTIL.getConvertUtils().register( new org.apache.commons.beanutils.converters.BigDecimalConverter( null ), BigDecimal.class );
			BEANUTIL.getConvertUtils().register( new org.apache.commons.beanutils.converters.SqlTimestampConverter( null ), Timestamp.class );
			BEANUTIL.getConvertUtils().register( new org.apache.commons.beanutils.converters.DateConverter( null ), Date.class );
			BEANUTIL.copyProperties( commonVO, ThreadLevelContext.get( SvcConstants.COMMONDATA) );
		}
	
		catch( IllegalAccessException e1 ){
			throw new BusinessException( "", e1, "An IllegalAccessException exception occured while committing the VO:  " + commonVO.getClass().getSimpleName() );
		}
		catch( InvocationTargetException e2 ){
			throw new BusinessException( "", e2, "An InvocationTargetException exception occured while committing the VO: " + commonVO.getClass().getSimpleName() );
		}
		logger.info("Exiting from BaseSaveOperation.commitVOModification() method");
	}



	/**
	 * This method will generate the endorsement text for policy
	 */
	private void generateEndtText(){

		CommonVO commonVO = (CommonVO) ThreadLevelContext.get( SvcConstants.COMMONDATA );
		if( !commonVO.getIsQuote() && commonVO.getAppFlow().equals( Flow.AMEND_POL ) ){
			List<String> tableList = (List<String>) ThreadLevelContext.get( SvcConstants.TCL_KEY_ENDT_TABLE_LIST );
			if( !Utils.isEmpty( tableList ) && tableList.size() > 0 && !Utils.isEmpty( tableList.get( 0 ) ) ){
				// Only in case of policy and amend flow call the procedure
				getHibernateTemplate().flush();
				Integer userId = ( (UserProfile) ( commonVO ).getLoggedInUser() ).getRsaUser().getUserId();
				PolicyDataVO polData = (PolicyDataVO) ThreadLevelContext.get( SvcConstants.POLDATA );
				if( polData.getPolicyType() == Integer.valueOf( SvcConstants.HOME_POL_TYPE ) ){
					polData.setSectionId( (short) 14 );
				}
				else if( ( polData.getPolicyType().equals( Integer.valueOf( SvcConstants.SHORT_TRAVEL_POL_TYPE ) ) )
						|| (polData.getPolicyType().equals(  Integer.valueOf( SvcConstants.LONG_TRAVEL_POL_TYPE )) ) ){
					polData.setSectionId( (short) 15 );
				}
				PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean( "commonEndtTxtGen" );
				tableList =  CopyUtils.asList( CopyUtils.asSet( tableList ) );
				String tableNames = "";
				StringBuilder strBuilder = new StringBuilder();			/* Declared variable for replacing '+' concat of string - sonar violation fix */
				for( String tables : tableList ){
					tableNames = strBuilder.append(tables).append(",").toString();		/* Replaced '+' concat of string with String builder - sonar violation fix */
					/*tableNames += tables;
					tableNames += ",";*/
				}
				PASStoredProcedure spForPrmUpdate = commonVO.getIsQuote() ? (PASStoredProcedure) Utils.getBean( "commonUpdPrmQuoEndt" ) : (PASStoredProcedure) Utils
						.getBean( "commonUpdPrmPolEndt" );
				TTrnPolicyQuo policyQuo = (TTrnPolicyQuo) ThreadLevelContext.get( SvcConstants.TLC_KEY_POLICY_REC );
				spForPrmUpdate.call( policyQuo.getId().getPolicyId(), policyQuo.getId().getEndtId(), Integer.valueOf( policyQuo.getPolClassCode() ) );
				tableNames = tableNames.substring( 0, tableNames.lastIndexOf( "," ) );
				sp.call( ThreadLevelContext.get( SvcConstants.TLC_KEY_POLICY_ID ), ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ), tableNames, userId,
						Integer.valueOf( polData.getSectionId().toString() ), commonVO.getLocCode() );

			}

		}

	}
}
