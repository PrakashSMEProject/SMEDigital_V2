package com.rsaame.pas.dao.utils;

import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;

public class NextSequenceValue extends BaseDBDAO{

	private final static Logger logger = Logger.getLogger( NextSequenceValue.class );

	//private static  HibernateTemplate hibernateTemplate = (HibernateTemplate)Utils.getBean("hibernateTemplate");;

	private final static String SQL_BUILD_SEL = "select ";

	private final static String SQL_BUILD_FRM = ".NEXTVAL as id  from dual";

	/**
	 * @param sequenceName
	 * @param polType
	 * @param classCode
	 * @param seqTrnType
	 * @param seqBranch
	 * @param hibernateTemplate
	 * To be used by home\travel
	 */
	public final static Long getNextSequence( String sequenceName, Integer polType, Integer classCode, String seqTrnType, Integer seqBranch, HibernateTemplate hibernateTemplate ){
		Long data = null;
		if( logger.isInfo() ) logger.info( "Enteing getNextSequence() to get next sequence for " + sequenceName );
		PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean( "getNextSequence" );
		Map results = sp.call( sequenceName, polType, classCode, seqTrnType, seqBranch );
		data = Long.valueOf( results.get( "NEW_NEXT_NUMBER" ).toString() );
		if( logger.isDebug() ) logger.debug( "Sequence generated for " + sequenceName + " is " + data );

		if( logger.isInfo() ) logger.info( "Exiting getNextSequence()" );

		return data;
	}

	/**
	 * @param sequenceName
	 * @param seqTrnType
	 * @param seqBranch
	 * @param hibernateTemplate
	 * SBS calls always use policy type 50 and class code 2. Below method can be used by SBS to get the sequence
	 */
	public final static Long getNextSequence( String sequenceName, String seqTrnType, Integer seqBranch, HibernateTemplate hibernateTemplate ){
		return getNextSequence( sequenceName, Integer.valueOf( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) ),
				Integer.valueOf( Utils.getSingleValueAppConfig( "SBS_Class_code" ) ), seqTrnType, seqBranch, hibernateTemplate );
	}

}
