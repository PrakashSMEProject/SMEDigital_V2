package com.rsaame.pas.dao.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;

public class TableSnapshotQueryHelper{
	private static final String HBM_QUO = "Quo";
	private static final String SQL_QUO = "_QUO";
	
	private static final String COMMA = ",";
	private static final String SPACE = " ";
	private static final String CLOSE_BRACE = ")";
	private static final String OPEN_BRACE = "(";
	private static final String IN = "in";
	private static final String SELECT = "select";
	private static final String MAX = "max";
	private static final String WHERE = "where";
	private static final String LEQ = "<=";
	private static final String PARAM_VAL = "?";
	private static final String EQ = "=";
	private static final String AND = "and";
	private static final String GROUP_BY = "group by";
	private static final String FROM = "from";
	private static final String UNDERSCORE = "_";
	private static final String AT_THE_RATE = "@";

	static Object createTableSnapshotQuery( String tableId, TableSnapshotLevel viewType, Flow appFlow, HibernateTemplate ht, boolean sql, /*Object polOrLinkId,*/ Long endId,
			Object... params ){
		StringBuilder queryString = new StringBuilder();
		boolean isQuote = DAOUtils.isQuoteFlow( appFlow );
	
		/* Get the configured values for the table from appconfig.properties. */
		String[] configValues = getConfigKeyForSnapshotTable( tableId, sql );
		/* Get the additional ware clause */
		String[] additionalClause = getAdditionalClause( tableId, sql );
		Object result = null;
		if( Utils.isEmpty( configValues ) ){
			throw new BusinessException( "pas.config.tableConfig", null, "There is no Configaration available for table id [" + tableId + "]" );
		}
		else{
	
			Object[] changedParams = constructSnapshotQueryString( queryString, configValues, sql, viewType, isQuote, additionalClause, endId, params );
			/*
			 * In case of sql query column names are "_" separated where as in
			 * hbm query "_" is not required For that check if the query is sql
			 * or hbm and replace placeHolder "@" to "_" or ""
			 */
			String finalString = sql ? queryString.toString().replaceAll( AT_THE_RATE, UNDERSCORE ) : queryString.toString().replaceAll( AT_THE_RATE, "" );
	
			System.out.println( "Query [" + finalString + "]" );
			System.out.println( "Endt_Id/VED [" + endId + "]" );
			System.out.println( "Other params " + CopyUtils.asList( changedParams ) );
			
			System.out.println("The query:" +finalString);
			/*
			 * Execute the final query
			 */
			Date valExpiryDate = SvcConstants.EXP_DATE;
			result = setQueryParameters( ht, finalString, sql, /*polOrLinkId,*/ getStateParam( viewType, endId, valExpiryDate ), changedParams );
		}
	
		return result;
	}

	/*
	 * The state specifies whether to use VED or Edit ID 
	 */
	private static Object getStateParam( TableSnapshotLevel viewType, Object endId, Object valExpiryDate ){
		return ( viewType.compareTo( TableSnapshotLevel.CURRENT_VALID_STATE ) == 0 ) ? SvcConstants.EXP_DATE : endId;
	}

	/*Get the table snap shot level. which decides weather to use VED or editID */
	public static TableSnapshotLevel getViewTypeForFlow(Flow appFlow) {
		return (appFlow.equals(Flow.VIEW_QUO) || appFlow.equals(Flow.VIEW_POL)) ? TableSnapshotLevel.ENDT_ID : TableSnapshotLevel.CURRENT_VALID_STATE;
	}

	/**
	 * Constructs the table snapshot query based on the TableViewLevel.
	 * 
	 * @param query
	 * @param configValues
	 * @param sql
	 * @param viewType
	 * @param isQuote 
	 * @param params 
	 * @param endId 
	 */
	static Object[] constructSnapshotQueryString( StringBuilder query, String[] configValues, boolean sql, TableSnapshotLevel viewType, boolean isQuote,
			String[] addtionalClause, Long endId, Object[] params ){
		Object[] changedParams = params;
		
		switch( viewType ){
			case CURRENT_ACTIVE_STATE:
				break;
	
			case CURRENT_VALID_STATE:
				if( sql ){
					changedParams =	createSqlQuery( query, configValues, sql, isQuote, viewType, addtionalClause, endId, params );
				}
				else{
					changedParams =	createHbmQuery( query, configValues, sql, isQuote, viewType, addtionalClause, endId, params );
				}
				break;
	
			case ENDT_ID:
				changedParams = createEndtIdQuery( query, configValues, sql, isQuote, viewType, addtionalClause, endId, params );
				break;
				//sonar fix
			default:
				break;
		}
		
		return changedParams;
	
	}

	/**
	 * This method constructs the Endt_Id based snapshot query for both HBM and SQL.
	 * 
	 * @param query
	 * @param configValues
	 * @param sql
	 * @param isQuote
	 * @param viewType
	 * @param addtionalClause
	 * @param endId
	 * @param params
	 * @return
	 */
	private static Object[] createEndtIdQuery( StringBuilder query, String[] configValues, boolean sql, boolean isQuote, TableSnapshotLevel viewType, String[] addtionalClause,
			Long endId, Object[] params ){
		Utils.trimAllEntries( configValues );
		
		String tn = configValues[ 0 ];
		String pf = configValues[ 1 ];
		List<Object> changedParams = new ArrayList<Object>();

		/* Construct the SELECT columns list for SQL queries. */
		if( sql ){
			StringBuilder selectColPhrase = new StringBuilder( SELECT ).append( SPACE );
			
			for( int i = 3; i < configValues.length; i++ ){
				selectColPhrase.append( field( sql, pf, configValues[ i ] ) );
				
				if( configValues.length > i + 1 ){
					selectColPhrase.append( COMMA ).append( SPACE );
				}
				
				selectColPhrase.append(  SPACE );
			}
			
			query.append( selectColPhrase );
		}
		
		/* from <table> where ( */
		query.append( FROM ).append( SPACE ).append( tn ).append( !sql ? HBM_QUO : ( isQuote ? SQL_QUO : "" ) ).append( SPACE ).append( pf ).append( SPACE )
		.append( WHERE ).append( SPACE ).append( OPEN_BRACE );
		
		/* endt_id */
		StringBuilder eIdSB = field( sql, pf, getEndIdColumn( configValues[ 2 ], sql ) );
		query.append( eIdSB );
	
		/* , <additional clause column>, ... */
		if( !Utils.isEmpty( addtionalClause ) ){
			for( String clause : addtionalClause ){
				query.append( COMMA ).append( SPACE ).append( field( sql, pf, clause ) );
			}
		}
		
		/* ") in ( " */
		query.append( CLOSE_BRACE ).append( SPACE ).append( IN ).append( SPACE).append( OPEN_BRACE ).append( SPACE );
		
		/* select max( endt_id ) */
		query.append( SELECT ).append( SPACE ).append( MAX ).append( SPACE ).append( OPEN_BRACE ).append( SPACE ).append( eIdSB ).append( SPACE ).append( CLOSE_BRACE );
		
		/* , <additional clause column>, ... */
		if( !Utils.isEmpty( addtionalClause ) ){
			for( String clause : addtionalClause ){
				query.append( COMMA ).append( SPACE ).append( field( sql, pf, clause ) );
			}
		}
		
		/* from <table> */
		query.append( SPACE ).append( FROM ).append( SPACE ).append( tn ).append( !sql ? HBM_QUO : ( isQuote ? SQL_QUO : "" ) ).append( SPACE );
		
		/* where endt_Id <= <endtId> */
		query.append( WHERE ).append( SPACE ).append( eIdSB ).append( SPACE ).append( LEQ ).append( SPACE ).append( PARAM_VAL );
		
		/* and <additional clause> = <value> and ... */
		if( !Utils.isEmpty( addtionalClause ) ){
			for( int clauseIndex = 0; clauseIndex < addtionalClause.length; clauseIndex++ ){
				String clause = addtionalClause[ clauseIndex ];
				
				/* If there is a param value for this clause, then add this clause to the WHERE clause and the param value to the 
				 * changed params list. */
				if( !Utils.isEmpty( params ) && params.length > clauseIndex && !Utils.isEmpty( params[ clauseIndex ] ) ){
					query.append( SPACE ).append( AND ).append( SPACE ).append( field( sql, pf, clause ) ).append( EQ ).append( SPACE ).append( PARAM_VAL );
					
					changedParams.add( params[ clauseIndex ] );
				}
			}
		}
		
		/* group by <additional clause>, ... */
		if( !Utils.isEmpty( addtionalClause ) ){
			query.append( SPACE ).append( GROUP_BY );
			
			for( int clauseIndex = 0; clauseIndex < addtionalClause.length; clauseIndex++ ){
				String clause = addtionalClause[ clauseIndex ];
				query.append( SPACE ).append( field( sql, pf, clause ) );
				
				if( clauseIndex != addtionalClause.length - 1 ){
					query.append( COMMA ).append( SPACE );
				}
			}
		}

		/* ) */
		query.append( CLOSE_BRACE ).append( SPACE );
	
		/* and status <> 4 */
		excludeDeleted( query, pf, viewType, tn, sql );
		
		return changedParams.toArray();
	}
	
	/**
	 * Constructs the field based on SQL or HBM and returns
	 * 
	 * @param sql
	 * @param pf
	 * @param field
	 * @return
	 */
	private static StringBuilder field( boolean sql, String pf, String field ){
		if( sql ){
			return new StringBuilder().append( pf ).append( field.startsWith( AT_THE_RATE ) ? "" : AT_THE_RATE ).append( field );
		}
		else{
			return toPOJOField( pf, field );
		}
	}

	/**
	 * Constructs the POJO field to be added to the query with "id." (as configured) and with the prefix.
	 * 
	 * @param pf
	 * @param configField
	 * @return
	 */
	private static StringBuilder toPOJOField( String pf, String configField ){
		String idPrefix = "";
		if( configField.contains( "." ) ){
			idPrefix = separateId( configField, 0 );
			configField = separateId( configField, 1 );
		}
		
		StringBuilder sb = new StringBuilder().append( idPrefix ).append( idPrefix.equalsIgnoreCase( "" ) ? "" : "." ).append( pf ).append( configField );
		return sb;
	}

	/*
	 * Creates the sql query based on the configuration in appconfig
	 */
	private static Object[] createSqlQuery( StringBuilder query, String[] configValues, boolean sql, boolean isQuote, 
										TableSnapshotLevel viewType, String[] addtionalClause, Long endId, Object[] params ){
		Utils.trimAllEntries( configValues );
		
		String tn = configValues[ 0 ];
		String pf = configValues[ 1 ];
		//String pId = getPolIdColumn( configValues[ 2 ], sql );
		String eId = getEndIdColumn( configValues[ 2 ], sql );
	
		StringBuilder columnNames = new StringBuilder();
	
		/* Construct the SELECT columns list */
		for( int i = 3; i < configValues.length; i++ ){
			columnNames.append( pf ).append( "_" ).append( configValues[ i ] ).append( "," );
		}
		
		/* Removes the last "," from the string appended in the for-loop */
		String selectColumns = "*";
		if( !Utils.isEmpty( columnNames.toString() ) ) selectColumns = columnNames.substring( 0, columnNames.length() - 1 );
	
		/* Construct the basic query with Endt_Id or VED */
		query.append( com.Constant.CONST_SELECT_END ).append( selectColumns ).append( com.Constant.CONST_FROM_END ).append( tn ).append( isQuote ? "_QUO" : "" )
		.append( com.Constant.CONST_WHERE_END ).append( pf ).append( viewCondition( viewType, eId ) );
		
		List<Object> changedParams = new ArrayList<Object>();
		/*Appends the additional clauses if any to the standard query generated*/
		appendAdditionalClause( query, addtionalClause, pf, true,  endId,  params , changedParams);
	
		/*In case of tables that does not have status column, 
		 * in CURRENT_VALID_STATE mode status <> 4 should not be appended
		 * The following method will take care of that */
		excludeDeleted( query, pf, viewType, tn, true );
		return changedParams.toArray();
	}
	/**
	 * Creates HBM query by using config values and additional clause values passed and updates the same to query passed
	 * (a). Appends validityExpiryDate to query and ignores endorsement id in case of TableSnapshotLevel.CURRENT_VALID_STATE
	 * (b). Returns additional clause PARAM_VALUE array in order of ADD_CLAUSE configuration
	 * (c). Adds status column criteria (<> 4) by checking if status column is available for the table
	 * @param query
	 * @param configValues
	 * @param sql
	 * @param isQuote
	 * @param viewType
	 * @param addtionalClause
	 * @param endId
	 * @param params
	 * @return
	 */
	private static Object[] createHbmQuery( StringBuilder query, String[] configValues, boolean sql, boolean isQuote, TableSnapshotLevel viewType, String[] addtionalClause,
			Long endId, Object[] params ){
		String tn = configValues[ 0 ];
		String pf = configValues[ 1 ];
		String idPrefix = "";

		/*String pId = getPolIdColumn( configValues[ 2 ], sql );
		The default configuration if for policy or linking id, if that changes then handle specifically   
		if( pId.contains( "." ) ){
			idPrefix = separateId( pId, 0 );
			pId = separateId( pId, 1 );
		}*/

		// append(isQuote ? "Quo" : "")
		query.append( "from " ).append( tn ).append( "Quo" ).append( " " ).append( pf ).append( " " ).append( com.Constant.CONST_WHERE_END )/*.append( pf ).append( "." ).append( idPrefix )
																															.append( idPrefix.equalsIgnoreCase( "" ) ? "" : "." ).append( pf ).append( pId ).append( " = ? and " )*/
		.append( pf ).append( "." );

		String eId = getEndIdColumn( configValues[ 2 ], sql );
		idPrefix = "";
		if( eId.contains( "." ) ){
			idPrefix = separateId( eId, 0 );
			eId = separateId( eId, 1 );
		}

		query.append( ( viewCondition( viewType, eId ).equalsIgnoreCase( SvcConstants.VALIDITY_EXP_DATE ) ) ? "" : query.append( idPrefix ).append(
				idPrefix.equalsIgnoreCase( "" ) ? "" : "." ) );
		query.append( pf ).append( viewCondition( viewType, eId ) );

		List<Object> changedParams = new ArrayList<Object>();
		appendAdditionalClause( query, addtionalClause, pf, false, endId, params, changedParams );

		excludeDeleted( query, pf, viewType, tn, false );
		return changedParams.toArray();

	}

	/*
	 * The method decides which tables can have status check.
	 */
	static void excludeDeleted( StringBuilder query, String pf, TableSnapshotLevel viewType, String tn, boolean b ){
		//if( viewType.equals( TableSnapshotLevel.CURRENT_VALID_STATE ) ){
			
			if( !SvcUtils.isPolicyCancelled() ){
		
				String[] nonStaBasedTbl = Utils.getMultiValueAppConfig( "NON_STATUS_BASED_TABLES" );
				List<String> nonStaBasedTblL = CopyUtils.asList( nonStaBasedTbl );
				if(!nonStaBasedTblL.contains(tn))
					query.append(com.Constant.CONST_AND_END).append(b?"":pf).append(b?"":".").append(pf).append(b?"_Status <> 4":"Status <> 4");
			}
		//}
	
	}

	static String separateId( String id, int position ){
		String[] data = id.split( "\\." );
		return data[ position ];
	}

	static void appendAdditionalClause( StringBuilder query, String[] addtionalClause, String prefix, boolean flag, Long endId, Object[] params, List<Object> changedParams ){
		if( !Utils.isEmpty( addtionalClause ) && addtionalClause.length > 0 ){
			for( int clauseIndex = 0; clauseIndex < addtionalClause.length; clauseIndex++ ){
				String clause = addtionalClause[ clauseIndex ];
				if( !Utils.isEmpty( params ) && params.length > clauseIndex && !Utils.isEmpty( params[ clauseIndex ] ) ){
					if( flag ){
						sqlAppendAdditionalClause( query, clause, prefix );
					}
					else{
						hbmAppendAdditionalClause( query, clause, prefix );
					}
					changedParams.add( params[ clauseIndex ] );
				}
			}
		}
	}

	/*Appends the additional clauses if any to the standard query generated*/
	private static void hbmAppendAdditionalClause( StringBuilder query, String clause, String prefix ){
	
		
		String idPrefix = "";
		String pId = clause;
		if( clause.contains( "." ) ){
			idPrefix = separateId( pId, 0 );
			pId = separateId( pId, 1 );
		}
		query.append( com.Constant.CONST_AND_END ).append( prefix ).append( "." ).append( idPrefix ).append( idPrefix.equalsIgnoreCase( "" ) ? "" : "." ).append( prefix ).append( pId )
				.append( " = ?" );
	}

	/*Appends the additional clauses if any to the standard query generated*/
	static void sqlAppendAdditionalClause( StringBuilder query, String clause, String prefix){
		query.append( com.Constant.CONST_AND_END ).append( prefix ).append( "@" ).append( clause ).append( " = ?" );
	}

	/*
	 * Method depending on the state appends editid or VED, the method is common for both sql and hdm query
	 * hence "@" is used as a place holder which will be replace by "_" in sql query and "" in case of hql
	 */
	static String viewCondition(TableSnapshotLevel viewType, String eId) {
	
		return (viewType.compareTo(TableSnapshotLevel.CURRENT_VALID_STATE) == 0) ? SvcConstants.VALIDITY_EXP_DATE:Utils.concat(eId," = ?");
	
	}

	/*
	 * For tables that have end ID column name as end No special handing is required 
	 * the method is common for both sql and hdm query
	 * hence "@" is used as a place holder which will be replace by "_" in sql query and "" in case of hql
	 */
	private static String getEndIdColumn( String endId, boolean sql ){
		String column = null;
		if( endId.equalsIgnoreCase( "Pol_Endt_Id" ) ){
			column = "@Endt@Id";
		}
		else if( endId.equalsIgnoreCase( "Pol_POL_Endt_Id" ) ){
			column = "Pol@Endt@Id";
		}
		else if( endId.equalsIgnoreCase( "Pol_Endt_No" ) ){
			column = "@Pol@Endt@No";
		}
		else{
			column = endId;
		}
	
		return column;
	}
	/*
	 * Depending on the configuration either policy id or policy linking id will be returned for query building
	 *  * the method is common for both sql and hdm query
	 * hence "@" is used as a place holder which will be replace by "_" in sql query and "" in case of hql 
	 */

	static String getPolIdColumn( String polIdCol, boolean sql ){
		String column = null;
		if( polIdCol.equalsIgnoreCase( "Policy_Id" ) ){
			column = "@Policy@Id";
		}
		else if( polIdCol.equalsIgnoreCase( "Linking_Id" ) ){
			column = "@Linking@Id";
		}
		else{
			column = polIdCol;
		}
	
		return column;
	}

	private static String[] getConfigKeyForSnapshotTable(String tableId, boolean sql) {
		return Utils.getMultiValueAppConfig(Utils.concat(com.Constant.CONST_TABLE_END, sql ? "SQL_"
				: "HBM_", tableId));
	}

	/*
	 * Sets the query parameters for the generated query and the passed parameter 
	 */
	private static Object setQueryParameters( HibernateTemplate hibernateTemplate, String queryString, boolean sql, /*Object polOrLinkID,*/ Object stateParam, Object... params ){
		if( sql )
			return setSqlQueryParamater( hibernateTemplate, queryString, /*polOrLinkID,*/ stateParam, params );
		else
			return setHbmQueryParamater( hibernateTemplate, queryString, /*polOrLinkID,*/ stateParam, params );
	}

	/*
	 * For sql query session.createSQLQuery() is used.
	 */
	static Query setSqlQueryParamater( HibernateTemplate hibernateTemplate, String queryString, /*Object polOrLinkID,*/ Object stateParam, Object... params ){
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery( queryString );
		/*if( Utils.isEmpty( polOrLinkID ) ){
			throw new BusinessException( "pas.config.tableConfig", null, "For the query [" + queryString + "] the mandatory params do not match" );
		}*/
		/*query.setParameter( 0, polOrLinkID );*/
		query.setParameter( 0, stateParam );
		if( !Utils.isEmpty( params ) && params.length > 0 ){
			for( int paramCnt = 0; paramCnt < params.length; paramCnt++ ){
				query.setParameter( paramCnt + 1, params[ paramCnt ] );
			}
		}
	
		return query;
	}

	/* 
	 * For hbm hql query hibernateTemplate.find() is used.
	 */
	static Object setHbmQueryParamater( HibernateTemplate hibernateTemplate, String queryString, /*Object polOrLinkID,*/ Object stateParam, Object... params ){
	
		/*if( Utils.isEmpty( polOrLinkID ) ){
			throw new BusinessException( "pas.config.tableConfig", null, "For the query [" + queryString + "] the mandatory params do not match" );
		}*/
		List<Object> param = null;
		if( !Utils.isEmpty( params ) && params.length > 0 ){
			param = CopyUtils.asList( params );
		}
		else{
			param = new ArrayList<Object>();
		}
		/*param.add( 0, polOrLinkID );*/
		param.add( 0, stateParam );
		return hibernateTemplate.find( queryString, param.toArray() );
	}

	static String[] getAdditionalClause( String tableId, boolean sql ){
		return Utils.getMultiValueAppConfig( Utils.concat( com.Constant.CONST_TABLE_END, sql ? "SQL_" : "HBM_" ,tableId, "_ADD_CLAUSE" ) );
	}

	/**
	 * This method reads the configuration from appconfig.properties for TABLE_<sql>_FOR_VSD_<tableId> passed. 
	 * @param tableId
	 * @return
	 */
	static String[] getConfigKeyForVSD( String tableId, boolean sql ){
	
		return Utils.getMultiValueAppConfig( Utils.concat( com.Constant.CONST_TABLE_END, sql ? "SQL_" : "HBM_", "FOR_VSD_", tableId ) );
	}

	/**
	 * 
	 * @param tableId
	 * @param sql
	 * @return
	 */
	static String[] getAdditionalConfigForVSD(String tableId, boolean sql){
		
		return Utils.getMultiValueAppConfig( Utils.concat( com.Constant.CONST_TABLE_END, sql ?"SQL_" : "HBM_","ADD_CLAUSE_FOR_VSD_",tableId ) );
	}

	/**
	 * 
	 * @param configKey
	 * @return
	 *//*
	private static String[] getMultiValuedConfigkey(String configKey){
		return configKey.split( "#" );
	}*/

	/**
	 * 
	 * @param query
	 * @param criteria
	 * @param additionalCriteria
	 * @param isQuote
	 */
	static void createSQLQueryForVSD( StringBuilder query, String[] criteria, String[] additionalCriteria, boolean isQuote ){
	
		String formInKeyVal = null;
		String prefix = criteria[ 2 ];
		query.append( com.Constant.CONST_SELECT_END ).append( "max(" + criteria[ 1 ] + ")" ).append( com.Constant.CONST_FROM_END ).append( criteria[ 0 ] ).append( com.Constant.CONST_WHERE_END ).append( prefix ).append( criteria[3] ).append( " = ?" );
		int cnt = 0;
		String inKey = null;
		for( String configValKey : criteria ){
			
			if( cnt <= 3 ) {
				cnt++;
				continue;
			}
			query.append( com.Constant.CONST_AND_END ).append( configValKey ).append( com.Constant.CONST_QUERY_END );
			
		}
		for( String configKey : additionalCriteria ){
			String[] subKeys = configKey.split( ">" );
	
			if( !Utils.isEmpty( subKeys ) ){
	
				for( String subKey : subKeys ){
					if( subKey.contains( "#" ) ){
						String[] splitStatus = subKey.split( "#" );
						for( String subKey1 : splitStatus ){
							formInKeyVal = Utils.concat( subKey1, "," );
						}
					}else{
						inKey = subKey;
					}
				}
				if( !Utils.isEmpty( formInKeyVal ) ){
					formInKeyVal = formInKeyVal.substring( 0, formInKeyVal.length() - 1 );
					query.append( inKey ).append( " in " ).append( " ( " ).append( formInKeyVal ).append( ")" );
				}
	
			}
	
			query.append( com.Constant.CONST_AND_END ).append( configKey ).append( com.Constant.CONST_QUERY_END );
		}
	
	}

	/**
	 * 
	 * @param query
	 * @param criteria
	 * @param additionalCriteria
	 * @param isQuote
	 */
	static void createHBMQueryForVSD( StringBuilder query, String[] criteria, String[] additionalCriteria, boolean isQuote ){
		String formInKeyVal = "";
		//String prefix = criteria[ 2 ];
		query.append( com.Constant.CONST_SELECT_END ).append( "max(" + criteria[ 1 ] + ")" ).append( com.Constant.CONST_FROM_END ).append( isQuote ? Utils.concat( criteria[ 0 ], "Quo" ) : criteria[ 0 ] )
				.append( com.Constant.CONST_WHERE_END ).append( criteria[ 3 ] ).append( " = ?" );
		int cnt = 0;
		String inKey = null;
		for( String configValKey : criteria ){
			
			if( cnt <= 3 ) {
				cnt++;
				continue;
			}
			query.append( com.Constant.CONST_AND_END ).append( configValKey ).append( com.Constant.CONST_QUERY_END );
			
		}
		for( String configKey : additionalCriteria ){
			String[] subKeys = configKey.split( ">" );
	
			if( !Utils.isEmpty( subKeys ) ){
	
				for( String subKey : subKeys ){
					if( subKey.contains( "#" ) ){
						String[] splitStatus = subKey.split( "#" );
						for( String subKey1 : splitStatus ){
							formInKeyVal = Utils.concat( formInKeyVal,subKey1,"," );
						}
					}else{
						inKey = subKey;
					}
				}
				if( !Utils.isEmpty( formInKeyVal ) ){
					formInKeyVal = formInKeyVal.substring( 0, formInKeyVal.length() - 1 );
					query.append(com.Constant.CONST_AND_END).append( inKey ).append( " in " ).append( " ( " ).append( formInKeyVal ).append( ")" );
				}
	
			}else{
				query.append( com.Constant.CONST_AND_END ).append( configKey ).append( com.Constant.CONST_QUERY_END );
			}
		}
	}

	/**
	 * 
	 * @param params
	 * @return
	 */
	static Object[] getBindingValuesAsObjectArray( Object... params ){
	
		List<Object> param = null;
		if( !Utils.isEmpty( params ) && params.length > 0 ){
			param = CopyUtils.asList( params );
		}
		else{
			param = new ArrayList<Object>();
		}
	
		return param.toArray();
	}

	/**
	 * 
	 * @param tableId
	 * @param sql
	 * @return
	 */
	static String[] getConfigForExistingRecordFetch(String tableId, boolean sql){
		/* ER_TABLE_HBM_KEY_FOR_T_TRN_BUILDING - config key sample */
		return Utils.getMultiValueAppConfig( Utils.concat( "ER_TABLE_", sql ?"SQL_" : "HBM_","KEY_FOR_",tableId ) );
	}

	/**
	 * 
	 * @param query
	 * @param configValues
	 * @param isQuote
	 *//*
	private static void constructSQLQueryForExistingRecordFetch(StringBuilder query, String[] configValues, boolean isQuote){
		
	}
*/
	/**
	 * 
	 * @param query
	 * @param configValues
	 * @param isQuote
	 */
	static void constructHBMQueryForExistingRecordFetch(StringBuilder query, String[] configValues, boolean isQuote){
		int cnt = 0;
		query.append("from ").append(isQuote?Utils.concat( configValues[0],"Quo" ): configValues[0]).append(com.Constant.CONST_WHERE_END).append( configValues[2] ).append(com.Constant.CONST_QUERY_END);
		
		for(String key:configValues){
			if(cnt <= 2){
				cnt++;
				continue;
			}
			query.append( com.Constant.CONST_AND_END ).append( key ).append( com.Constant.CONST_QUERY_END );
		}
	}

}
