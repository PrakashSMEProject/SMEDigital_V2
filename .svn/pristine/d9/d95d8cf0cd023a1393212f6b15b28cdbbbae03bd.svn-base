/**
 * 
 */
package com.rsaame.pas.dao.cmn;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author M1014644
 *	This DAO loads all the data from the input Map<String ,Class<? extends BaseVO> > for a particular class
 */
public class BaseLoadOperation extends BaseDBDAO implements IBaseLoadOperation{

	private static final String COMMA = ",";
	private static final String FETCH_REC_FOR_QUOTE = " from TTrnPolicyQuo polQuo where polQuo.polQuotationNo = ? and polQuo.polLocationCode = ? and  polQuo.id.polEndtId =? and polQuo.polIssueHour = ?";
	private static final String FETCH_REC_FOR_POLICY = " from TTrnPolicyQuo polQuo where polQuo.polPolicyNo = ? and polQuo.polLocationCode = ? and polQuo.id.polEndtId =? and polQuo.polIssueHour = ?";
	private static final String FETCH_REC_DATA_EXTENDED_DOC_CODE = " and polQuo.polDocumentCode=?";
	//private static final String FETCH_REC_DATA_EXTENDED_EFF_DATE = " and to_char(pol_effective_date, 'dd/mm/yyyy') = ?";
	private static final String FETCH_REC_DATA_EXTENDED_EFF_DATE = " and pol_effective_date = ?";
	private final static com.mindtree.ruc.cmn.log.Logger LOGGER = com.mindtree.ruc.cmn.log.Logger.getLogger( BaseLoadOperation.class );	/* Added LOGGER - sonar violation fix */

	@Override
	@SuppressWarnings( "rawtypes" )
	public final DataHolderVO<LinkedHashMap<String, List<TableData>>> loadData( LoadDataInputVO loadInput, Map<String, Class<? extends BaseVO>> dataToLoad ){

		/*
		 * Get the policy id based on the quote no or policy no for a class code, and set the VSD for that endorsement in TLC
		 */
		getVSDPolicyIdForClass( loadInput );

		LinkedHashMap<String, List<TableData>> dataMap = new LinkedHashMap<String, List<TableData>>();

		for( Map.Entry<String, Class<? extends BaseVO>> tablesSet : dataToLoad.entrySet() ){

			loadData( loadInput, tablesSet, dataMap );
		}

		DataHolderVO<LinkedHashMap<String, List<TableData>>> loadedDataHolder = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();
		loadedDataHolder.setData( dataMap );

		return loadedDataHolder;
	}

	private void getVSDPolicyIdForClass( LoadDataInputVO loadInput ){

		//Radar fix
		Long policyId = Long.valueOf( 0 );
		Date vsd = getSysDate();

		Long polQuoteNo = loadInput.getIsQuote() ? loadInput.getQuoteNo() : loadInput.getPolicyNo();
		getVSDPolicyId( polQuoteNo, loadInput.getEndtId(), loadInput.getLocCode(), loadInput.getIsQuote(), policyId, vsd, loadInput.getDocCode() , loadInput.getPolEffectiveDate() , getHibernateTemplate() );

	}

	/**
	 * @param loadInput 
	 * @param tablesSet
	 * @param dataMap
	 */
	@SuppressWarnings( "rawtypes" )
	private void loadData( LoadDataInputVO loadInput, Entry<String, Class<? extends BaseVO>> tablesSet, LinkedHashMap<String, List<TableData>> dataMap ){

		// Get the alias table name in execution
		String tableInExecution = tablesSet.getKey();
		// Get the actual table name in execution from the alias configured in property file
		String tableName = Utils.getSingleValueAppConfig( tableInExecution );

		List<POJOWrapper> tableDataList = getPojosList( loadInput, tableInExecution, tableName );

		dataMap.put( tableInExecution, null );

		List<TableData> mappedTableData = new ArrayList<TableData>();

		for( POJOWrapper pojo : tableDataList ){

			try{

				//POJO premiumPojo = getPrmRecForRec( pojo, loadInput );

				BaseVO mappedVO = getVoFromPojo( pojo, tableInExecution, loadInput, tablesSet.getValue().newInstance() );

				//mappedVO = populatePremium( mappedVO, loadInput , pojo, premiumPojo );

				TableData<BaseVO> tableData = createTableData( mappedVO, pojo, loadInput );

				mappedTableData.add( tableData );
			}
			catch( InstantiationException e ){
				// TODO Auto-generated catch block
				LOGGER.error("InstantiationException occurred in the method:loadData :"+e.getMessage()); /* Added LOGGER - sonar violation fix */
			}
			catch( IllegalAccessException e ){
				// TODO Auto-generated catch block
				LOGGER.error("IllegalAccessException occurred in the method:loadData :"+e.getMessage()); /* Added LOGGER - sonar violation fix */
			}

		}

		if( mappedTableData.size() > SvcConstants.zeroVal ){
			dataMap.put( tableInExecution, mappedTableData );
		}

	}

	/**
	 * @param mappedVO
	 * @param pojo
	 * @param premiumPojo
	 * @param loadInput
	 * @return
	 */
	private TableData<BaseVO> createTableData( BaseVO mappedVO, POJOWrapper pojo, /*POJO premiumPojo,*/LoadDataInputVO loadInput ){

		TableData<BaseVO> tableData = new TableData<BaseVO>();

		if( !Utils.isEmpty( pojo.getPOJOWrapperId() ) && !Utils.isEmpty( pojo.getPOJOWrapperId().getId() ) ){

			tableData.setContentID( pojo.getPOJOWrapperId().getId() );
		}

		if( !Utils.isEmpty( pojo.getPOJOWrapperId() ) && !Utils.isEmpty( pojo.getPOJOWrapperId().getVSD() ) ){

			tableData.setContentVsd( pojo.getPOJOWrapperId().getVSD() );
		}

		tableData.setTableData( mappedVO );

		return tableData;
	}

	/**
	 * @param mappedVO
	 * @param loadInput 
	 * @param pojo
	 * @param premiumPojo
	 * @return
	 */
	@SuppressWarnings( "unused" )
	private BaseVO populatePremium( BaseVO mappedVO, LoadDataInputVO loadInput, POJO pojo, POJO premiumPojo ){
		// TODO need to decide about the implementation
		return null;
	}

	/**
	 * @param pojo
	 * @param loadInput
	 * @return
	 */
	@SuppressWarnings( "unused" )
	private POJO getPrmRecForRec( POJO pojo, LoadDataInputVO loadInput ){
		// TODO need to decide about the implementation
		return null;
	}

	/**
	 * @param pojo
	 * @param newInstance
	 * @param baseVO 
	 * @return
	 */
	@SuppressWarnings( "unchecked" )
	private BaseVO getVoFromPojo( POJO pojo, String tableInExecution, LoadDataInputVO base, BaseVO baseVO ){

		//POJOWrapper pojoTemp = (POJOWrapper)pojo;

		//POJOWrapperId id = (POJOWrapperId)Utils.getBean( tableInExecution+"_ID" );

		BaseBeanToBeanMapper<POJOWrapper, BaseVO> mapperClass = (BaseBeanToBeanMapper<POJOWrapper, BaseVO>) Utils.getBean( tableInExecution + "_Rev_Mapper" );

		baseVO = mapperClass.mapBean( (POJOWrapper) pojo, baseVO );

		return baseVO;

	}

	/**
	 * @param loadInput
	 * @param tableInExecution
	 * @param tableName
	 * @return
	 */
	private List<POJOWrapper> getPojosList( LoadDataInputVO loadInput, String tableInExecution, String tableName ){

		/*
		 * Get mandatory configured query parameter. 
		 */
		String[] queryParamsStrings = Utils.getMultiValueAppConfig( tableName + "_QUERY_PARMS", COMMA );

		Criteria c = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria( Utils.getBean( tableName ).getClass() );
		
		/*Added the restriction for policy*/
		c.add( Restrictions.eq( queryParamsStrings[ 0 ], ThreadLevelContext.get( SvcConstants.TLC_KEY_POLICY_ID ) ) );
		
		/*Added the restriction for VSD less than or equal*/
		c.add( Restrictions.le( queryParamsStrings[ 1 ], ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) ) );
		
		/*Added the restriction for VED less than or equal*/
		c.add( Restrictions.gt( queryParamsStrings[ 2 ], ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) ) );
		
		Byte polStatus = (Byte)ThreadLevelContext.get( SvcConstants.TCL_POL_STATUS );
		/*Added the restriction for endt id less than or equal*/
		if(polStatus!=SvcConstants.DEL_SEC_LOC_STATUS ){
			c.add( Restrictions.le( queryParamsStrings[ 3 ], loadInput.getEndtId() ) );
		}else{
			c.add( Restrictions.eq( queryParamsStrings[ 3 ], loadInput.getEndtId() ) );
		}
		
		if( queryParamsStrings.length > 4 ){
			/*Added the restriction for status not equal to 4*/
			if(polStatus!=SvcConstants.DEL_SEC_LOC_STATUS ){
				if (!Utils.isEmpty(loadInput.getStatus())){
					c.add( Restrictions.eq( queryParamsStrings[ 4 ], loadInput.getStatus().byteValue() ) );
				}
				else{
					c.add( Restrictions.ne( queryParamsStrings[ 4 ], SvcConstants.DEL_SEC_LOC_STATUS ) );
				}
			}
		}

		return c.list();
	}

	/**
	 * @param loadInput
	 * @return
	 */
	private Date getVSDForVersion( LoadDataInputVO loadInput ){
		// TODO decision is pending about the implementation
		return null;
	}

	/**
	 * To fetch PolicyId and Validity Start Date for a particular Quote / Policy.
	 * @param polQuoteNo
	 * @param endtId
	 * @param locCode
	 * @param isQuote
	 * @param policyId
	 * @param vsd
	 * @param docCode
	 * @param effDate
	 * @param ht
	 */
	public void getVSDPolicyId( Long polQuoteNo, Long endtId, Integer locCode, Boolean isQuote, Long policyId, Date vsd, Short docCode, Date effDate, HibernateTemplate ht ){

		List<Object> queryData = new ArrayList<Object>(); 
		String query = isQuote ? FETCH_REC_FOR_QUOTE : FETCH_REC_FOR_POLICY;
		queryData.add( polQuoteNo);
		queryData.add( locCode.shortValue() );
		queryData.add( endtId );
		queryData.add( SvcConstants.POL_ISSUE_HOUR );
		// Check if document code available(Transaction search/Recent activity flow)
		if(!Utils.isEmpty( docCode )){
			query = query+FETCH_REC_DATA_EXTENDED_DOC_CODE;
			queryData.add( docCode );
		}
		// Check if policy effective date available(Transaction search/Recent activity flow)
		if(!Utils.isEmpty( effDate )){
			query =query+FETCH_REC_DATA_EXTENDED_EFF_DATE;
			//SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
			queryData.add( effDate);
		}
	
		List<TTrnPolicyQuo> polRecords = ht.find( query,queryData.toArray());
		
		if( !Utils.isEmpty( polRecords ) && !Utils.isEmpty( polRecords.get( SvcConstants.zeroVal ) ) && !Utils.isEmpty( polRecords.get( SvcConstants.zeroVal ).getId() ) ){
			policyId = polRecords.get( SvcConstants.zeroVal ).getId().getPolicyId();
			vsd = polRecords.get( SvcConstants.zeroVal ).getPolValidityStartDate();
		}
		else{
			throw new BusinessException( null, "No Policy records exists!" );
		}

		ThreadLevelContext.set( SvcConstants.TLC_KEY_POLICY_ID, policyId );
		ThreadLevelContext.set( SvcConstants.TLC_KEY_VSD, vsd );
		ThreadLevelContext.set( SvcConstants.TCL_POL_STATUS, polRecords.get( SvcConstants.zeroVal ).getPolStatus() );
	}

	public static void main( String[] args ){

		Map<String, Class<? extends BaseVO>> dataToLoad = new LinkedHashMap<String, Class<? extends BaseVO>>();

		dataToLoad.put( "POL_DATA", PolicyDataVO.class );
		dataToLoad.put( "WORK_MEN_COMP", EmpTypeDetailsVO.class );

		try{
			BaseVO wc = dataToLoad.get( "WORK_MEN_COMP" ).newInstance();
			System.out.println( wc );
		}
		catch( InstantiationException e ){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch( IllegalAccessException e ){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
