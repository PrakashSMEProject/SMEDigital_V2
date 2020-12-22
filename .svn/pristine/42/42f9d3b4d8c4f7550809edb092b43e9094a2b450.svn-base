package com.rsaame.pas.endorse.dao;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.model.TMasPartnerMgmt;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.vo.svc.TMasPartnerMgmtVOHolder;

/**
 * @author M1020859
 * 
 * DAO class for saving the partner details from
 * Partner Management screen
 *
 */
public class PartnerManagementDAO extends BaseDBDAO implements IPartnerMgmtDAO{

	/** Logger instance for server logging */
	private final static Logger logger = Logger.getLogger( PartnerManagementDAO.class );
	/** Sequence name declaration to generate sequence */
	private final static String GEN_SEQ_PMM_ID = "SEQ_PMM_ID";

	/**
	 * Method to call hibernate instance for saving the entered data
	 */
	@Override
	public BaseVO savePartnerDets( BaseVO baseVO ){

		logger.debug( "PartnerManagementDAO ------> Entered into savePartnerDets method" );
		TMasPartnerMgmtVOHolder tmasPartnerMgmtVOHolder = (TMasPartnerMgmtVOHolder) baseVO;
		/** Fetching the details from T_MAS_CODE_MASTER against the promotional code */
		logger.debug( "PartnerManagementDAO ------> Start fetching the values from TMasCodeMaster" );
		
		

		TMasPartnerMgmt tmasPartnerMgmt = BeanMapper.map( tmasPartnerMgmtVOHolder, TMasPartnerMgmt.class );
		
		if( !Utils.isEmpty( tmasPartnerMgmt ) ){
			/** Generating explicitly next sequence for persisting the data */
			logger.debug( "PartnerManagementDAO ------> Making call to NextSequenceValue.java to get the next sequence" );
			tmasPartnerMgmt.setPmmPartnerCode( new Integer( NextSequenceValue.getNextSequence( GEN_SEQ_PMM_ID, null, null, getHibernateTemplate() ) .toString()) );
			logger.debug( "PartnerManagementDAO ---------> Going to call saveData method to invoke hibernate template" );
			saveData( tmasPartnerMgmt );
		}

		return baseVO;
	}

	/**
	 * Method to pass the POJO object for persistence
	 * @param tMasPromotionalCode
	 */
	private void saveData( TMasPartnerMgmt tMasPromotionalCode ){
		logger.debug( "PartnerManagementDAO ------> Going to invoke hibernate template to persist the data" );
		getHibernateTemplate().save( tMasPromotionalCode );
	}

}
