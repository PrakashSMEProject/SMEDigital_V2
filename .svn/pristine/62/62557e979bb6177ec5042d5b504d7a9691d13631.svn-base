package com.rsaame.pas.insured.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.gen.domain.TMasInsured;
import com.rsaame.pas.pojo.mapper.PolicyDataVOToMasInsuredPOJO;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.AccountHistoryListVO;
import com.rsaame.pas.vo.app.AccountHistoryVO;
import com.rsaame.pas.vo.app.ClaimsHistoryListVO;
import com.rsaame.pas.vo.app.ClaimsHistoryVO;
import com.rsaame.pas.vo.app.InsuredCommentListVO;
import com.rsaame.pas.vo.app.InsuredCommentVO;
import com.rsaame.pas.vo.bus.AdditionalInsuredInfoVO;
import com.rsaame.pas.vo.bus.AddressVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.TransactionSummaryVO;
import com.rsaame.pas.vo.bus.TransactionVO;

public class InsuredDetailsDAO extends BaseDBDAO implements IInsuredDetailsDAO{

	private final static Logger logger = Logger.getLogger( InsuredDetailsDAO.class );
	final BigDecimal CONVERSION = new BigDecimal( -1.0 );
	final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	private final static String INSUREDID_CODE_SEQ = "SEQ_INSURED_CODE";

	@Override
	public BaseVO fetchTmasInsured( BaseVO baseVO ){
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof PolicyVO ){

				PolicyVO policyVO = (PolicyVO) baseVO;
				Long InsuredCode = policyVO.getGeneralInfo().getInsured().getInsuredId();
				TMasInsured tMasInsured = DAOUtils.fetchTmasInsured( InsuredCode , getHibernateTemplate() );

				GeneralInfoVO generalInfoVO = BeanMapper.map( tMasInsured, GeneralInfoVO.class );
				InsuredVO insuredVO = generalInfoVO.getInsured();

				AddressVO addressVO = new AddressVO();
				insuredVO.setAddress( addressVO );
				if( !Utils.isEmpty( tMasInsured ) ){
					if( !Utils.isEmpty( tMasInsured.getInsEFirstName() ) ) generalInfoVO.getInsured().setName( tMasInsured.getInsEFirstName() );
					if( !Utils.isEmpty( tMasInsured.getInsEPhoneNo() ) ) generalInfoVO.getInsured().setPhoneNo( tMasInsured.getInsEPhoneNo() );
					if( !Utils.isEmpty( tMasInsured.getInsEMobileNo() ) ) generalInfoVO.getInsured().setMobileNo( tMasInsured.getInsEMobileNo() );
					if( !Utils.isEmpty( tMasInsured.getInsEZipCode() ) ) generalInfoVO.getInsured().getAddress().setPoBox( tMasInsured.getInsEZipCode() );
					if( !Utils.isEmpty( tMasInsured.getInsAFirstName() ) ) insuredVO.setArabicName( tMasInsured.getInsAFirstName() );
					if( !Utils.isEmpty( tMasInsured.getInsEAddress() ) ) insuredVO.getAddress().setAddress( tMasInsured.getInsEAddress() );
					if( !Utils.isEmpty( tMasInsured.getInsCountry() ) ) insuredVO.getAddress().setCountry( tMasInsured.getInsCountry().intValue() );
					if( !Utils.isEmpty( tMasInsured.getInsCity() ) ) insuredVO.setCity( tMasInsured.getInsCity().intValue() );
					if( !Utils.isEmpty( tMasInsured.getInsBusiness() ) ) insuredVO.setBusDescription( tMasInsured.getInsBusiness() );
					if( !Utils.isEmpty( tMasInsured.getInsNationality() ) ) insuredVO.setNationality( tMasInsured.getInsNationality().intValue() );
					if( !Utils.isEmpty( tMasInsured.getInsAffinityRefNo() ) ) generalInfoVO.getAdditionalInfo().setAffinityRefNo( tMasInsured.getInsAffinityRefNo() );
					if( !Utils.isEmpty( tMasInsured.getInsRegulatoryBody() ) ) generalInfoVO.getAdditionalInfo().setRegulatoryBody( tMasInsured.getInsRegulatoryBody() );
					if( !Utils.isEmpty( tMasInsured.getInsDtEstablishment() ) ) generalInfoVO.getAdditionalInfo().setDateOfEst( tMasInsured.getInsDtEstablishment() );
					if( !Utils.isEmpty( tMasInsured.getInsPlaceEstablishment() ) ) generalInfoVO.getAdditionalInfo().setPlaceOfEst( tMasInsured.getInsPlaceEstablishment() );
					if( !Utils.isEmpty( tMasInsured.getInsFaxNo() ) ) generalInfoVO.getAdditionalInfo().setFaxNumber( tMasInsured.getInsFaxNo() );
					if( !Utils.isEmpty( tMasInsured.getInsEEmailId() ) ) generalInfoVO.getInsured().setEmailId( tMasInsured.getInsEEmailId() );

					/*VAT*/
					if( !Utils.isEmpty( tMasInsured.getInsVatRegNo() ) ) generalInfoVO.getInsured().setVatRegNo( tMasInsured.getInsVatRegNo() );
					
					// added
					if( !Utils.isEmpty( tMasInsured.getInsSourceOfCust() ) ) generalInfoVO.getInsured().setBusType( tMasInsured.getInsSourceOfCust().intValue() );
					if( !Utils.isEmpty( tMasInsured.getInsLocCode() ) ) generalInfoVO.getInsured().getAddress().setTerritory( tMasInsured.getInsLocCode() );
					if( !Utils.isEmpty( tMasInsured.getInsECoRegnNo() ) ) generalInfoVO.getInsured().setTradeLicenseNo( tMasInsured.getInsECoRegnNo() );
					if( !Utils.isEmpty( tMasInsured.getInsRegCode() ) ) generalInfoVO.setJurisdiction( tMasInsured.getInsRegCode().toString() );
				}
				/* Setting customer id into policyVo */
       			 if( !Utils.isEmpty( tMasInsured.getInsCustomerId() )){
        			policyVO.setPolCustomerId( Long.valueOf( tMasInsured.getInsCustomerId() ));
       			 }
				policyVO.setGeneralInfo( generalInfoVO );
				return policyVO;
			}
		}
		return baseVO;
	}
	
	/* 
	 * This method is used to fetch the insured details from DB.
	 * This is used for Phase3-Home and Travel
	 */
	@Override
	public BaseVO fetchCommonTmasInsured( BaseVO baseVO ){
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof PolicyDataVO ){

				PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
				Long InsuredCode = policyDataVO.getGeneralInfo().getInsured().getInsuredId();
				TMasInsured tMasInsured = DAOUtils.fetchTmasInsured( InsuredCode , getHibernateTemplate() );

				GeneralInfoVO generalInfoVO = BeanMapper.map( tMasInsured, GeneralInfoVO.class );
				InsuredVO insuredVO = generalInfoVO.getInsured();

				AddressVO addressVO = new AddressVO();
				insuredVO.setAddress( addressVO );
				if( !Utils.isEmpty( tMasInsured ) ){
					if( !Utils.isEmpty( tMasInsured.getInsEFirstName() ) ) generalInfoVO.getInsured().setName( tMasInsured.getInsEFirstName() );
					if( !Utils.isEmpty( tMasInsured.getInsEPhoneNo() ) ) generalInfoVO.getInsured().setPhoneNo( tMasInsured.getInsEPhoneNo() );
					if( !Utils.isEmpty( tMasInsured.getInsEMobileNo() ) ) generalInfoVO.getInsured().setMobileNo( tMasInsured.getInsEMobileNo() );
					if( !Utils.isEmpty( tMasInsured.getInsEZipCode() ) ) generalInfoVO.getInsured().getAddress().setPoBox( tMasInsured.getInsEZipCode() );
					if( !Utils.isEmpty( tMasInsured.getInsAFirstName() ) ) insuredVO.setArabicName( tMasInsured.getInsAFirstName() );
					if( !Utils.isEmpty( tMasInsured.getInsEAddress() ) ) insuredVO.getAddress().setAddress( tMasInsured.getInsEAddress() );
					if( !Utils.isEmpty( tMasInsured.getInsCountry() ) ) insuredVO.getAddress().setCountry( tMasInsured.getInsCountry().intValue() );
					if( !Utils.isEmpty( tMasInsured.getInsCity() ) ) insuredVO.setCity( tMasInsured.getInsCity().intValue() );
					if( !Utils.isEmpty( tMasInsured.getInsBusiness() ) ) insuredVO.setBusDescription( tMasInsured.getInsBusiness() );
					if( !Utils.isEmpty( tMasInsured.getInsNationality() ) ) insuredVO.setNationality( tMasInsured.getInsNationality().intValue() );
					if( !Utils.isEmpty( tMasInsured.getInsAffinityRefNo() ) ) generalInfoVO.getAdditionalInfo().setAffinityRefNo( tMasInsured.getInsAffinityRefNo() );
					if( !Utils.isEmpty( tMasInsured.getInsRegulatoryBody() ) ) generalInfoVO.getAdditionalInfo().setRegulatoryBody( tMasInsured.getInsRegulatoryBody() );
					if( !Utils.isEmpty( tMasInsured.getInsDtEstablishment() ) ) generalInfoVO.getAdditionalInfo().setDateOfEst( tMasInsured.getInsDtEstablishment() );
					if( !Utils.isEmpty( tMasInsured.getInsPlaceEstablishment() ) ) generalInfoVO.getAdditionalInfo().setPlaceOfEst( tMasInsured.getInsPlaceEstablishment() );
					if( !Utils.isEmpty( tMasInsured.getInsFaxNo() ) ) generalInfoVO.getAdditionalInfo().setFaxNumber( tMasInsured.getInsFaxNo() );
					if( !Utils.isEmpty( tMasInsured.getInsEEmailId() ) ) generalInfoVO.getInsured().setEmailId( tMasInsured.getInsEEmailId() );

					// added
					if( !Utils.isEmpty( tMasInsured.getInsSourceOfCust() ) ) generalInfoVO.getInsured().setBusType( tMasInsured.getInsSourceOfCust().intValue() );
					if( !Utils.isEmpty( tMasInsured.getInsLocCode() ) ) generalInfoVO.getInsured().getAddress().setTerritory( tMasInsured.getInsLocCode() );
					if( !Utils.isEmpty( tMasInsured.getInsECoRegnNo() ) ) generalInfoVO.getInsured().setTradeLicenseNo( tMasInsured.getInsECoRegnNo() );
					if( !Utils.isEmpty( tMasInsured.getInsRegCode() ) ) generalInfoVO.setJurisdiction( tMasInsured.getInsRegCode().toString() );
					if( !Utils.isEmpty( tMasInsured.getInsIntAccExecCode() ) ) generalInfoVO.setIntAccExecCode( tMasInsured.getInsIntAccExecCode() );
					if( !Utils.isEmpty( tMasInsured.getInsTurnover() ) ) generalInfoVO.getInsured().setTurnover(tMasInsured.getInsTurnover() );
				}
				/* Setting customer id into policyVo */
       			 if( !Utils.isEmpty( tMasInsured.getInsCustomerId() )){
       				policyDataVO.setPolCustomerId( Long.valueOf( tMasInsured.getInsCustomerId() ));
       			 }
       			policyDataVO.setGeneralInfo( generalInfoVO );
				return policyDataVO;
			}
		}
		return baseVO;
	}

	@Override
	public BaseVO saveTmasInsured( BaseVO baseVO ){
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof PolicyVO ){

				PolicyVO policyVO = (PolicyVO) baseVO;

				GeneralInfoVO generalInfoVO = policyVO.getGeneralInfo();
				InsuredVO insuredVO = generalInfoVO.getInsured();
				AdditionalInsuredInfoVO additionalInsuredInfoVO = generalInfoVO.getAdditionalInfo();
				Long InsuredCode = policyVO.getGeneralInfo().getInsured().getInsuredId();

				TMasInsured tMasInsured = DAOUtils.fetchTmasInsured( InsuredCode , getHibernateTemplate() );
				tMasInsured = BeanMapper.map( policyVO.getGeneralInfo(), tMasInsured );

				if( !Utils.isEmpty( insuredVO ) ){
					if( !Utils.isEmpty( insuredVO.getName() ) ) tMasInsured.setInsEFirstName( insuredVO.getName() );
					if( !Utils.isEmpty( insuredVO.getName() ) ) tMasInsured.setInsEFullName( insuredVO.getName() );
					tMasInsured.setInsEPhoneNo( insuredVO.getPhoneNo() );
					tMasInsured.setInsEMobileNo( insuredVO.getMobileNo() );
					tMasInsured.setInsAFirstName( insuredVO.getArabicName() );
					if( !Utils.isEmpty( insuredVO.getAddress() ) ){
						tMasInsured.setInsEZipCode( insuredVO.getAddress().getPoBox() );
						tMasInsured.setInsEAddress( insuredVO.getAddress().getAddress() );
						if( !Utils.isEmpty( insuredVO.getAddress().getCountry() ) )
							tMasInsured.setInsCountry( insuredVO.getAddress().getCountry().shortValue() );
						else
							tMasInsured.setInsCountry( null );

						// added 
						tMasInsured.setInsLocCode( insuredVO.getAddress().getTerritory() );
					}
					tMasInsured.setInsCity( insuredVO.getCity() );
					tMasInsured.setInsBusiness( insuredVO.getBusDescription() );

					//added 
					if( !Utils.isEmpty( insuredVO.getBusType() ) ){
						tMasInsured.setInsSourceOfCust( insuredVO.getBusType().shortValue() );
					}
					if( !Utils.isEmpty( insuredVO.getNationality() ) )
						tMasInsured.setInsNationality( insuredVO.getNationality().shortValue() );
					else
						tMasInsured.setInsNationality( null );
					if( !Utils.isEmpty( insuredVO.getEmailId() ) ) tMasInsured.setInsEEmailId( insuredVO.getEmailId() );

					// added
					if( !Utils.isEmpty( insuredVO.getTradeLicenseNo() ) ){
						tMasInsured.setInsECoRegnNo( insuredVO.getTradeLicenseNo() );
					}
					
							
				}
				if( !Utils.isEmpty( additionalInsuredInfoVO ) ){
					tMasInsured.setInsAffinityRefNo( additionalInsuredInfoVO.getAffinityRefNo() );
					tMasInsured.setInsRegulatoryBody( additionalInsuredInfoVO.getRegulatoryBody() );
					tMasInsured.setInsDtEstablishment( additionalInsuredInfoVO.getDateOfEst() );
					tMasInsured.setInsPlaceEstablishment( additionalInsuredInfoVO.getPlaceOfEst() );
					tMasInsured.setInsFaxNo( additionalInsuredInfoVO.getFaxNumber() );
				}

				// added 
				if( !Utils.isEmpty( generalInfoVO.getJurisdiction() ) ){
					tMasInsured.setInsRegCode( Integer.valueOf( generalInfoVO.getJurisdiction() ) );
				}
				if( !Utils.isEmpty( generalInfoVO.getInsured().getVatRegNo()) ){
					tMasInsured.setInsVatRegNo(( generalInfoVO.getInsured().getVatRegNo()));//142244
				}
				
				// Setting Status to Active
				if( Utils.isEmpty( tMasInsured.getInsStatus() ) ) tMasInsured.setInsStatus( Integer.valueOf( 1 ) );
				//Setting Date Of Collection Of KYC to System date.
				tMasInsured.setInsDtCollectionKyc( new Date() );
				tMasInsured.setInsModifiedDt( new Date() );
				try{
					saveOrUpdate( tMasInsured );
					//getHibernateTemplate().merge( tMasInsured );
				}
				catch( DataAccessException e ){
					throw new SystemException( "insureddetails.save.fail", e, "Error while updating data to T_MAS_INSURED table" );
				}

				return policyVO;
			}
		}
		return baseVO;
	}

	/* Moving the below method to DAOUtils. */
	/*public TMasInsured fetchTmasInsured( Long InsuredCode ){

		TMasInsured tMasInsured = null;
		try{
			tMasInsured = (TMasInsured) getHibernateTemplate().find( "from TMasInsured tMasInsured where  tMasInsured.insInsuredCode=? ", InsuredCode ).get( 0 );
		}
		catch( HibernateException hibernateException ){
			throw new BusinessException( "pas.gi.couldNotGetCustDetail_1", hibernateException, "Error while trying to SELECT customer details from T_MAS_INSURED" );
		}
		return tMasInsured;
	}*/

	public BaseVO viewInsuredComments( BaseVO baseVO ){
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof InsuredVO ){
				InsuredVO insuredVO = (InsuredVO) baseVO;
				Long insuredCode = insuredVO.getInsuredId();

				InsuredCommentVO commentVO = null;
				InsuredCommentListVO commentListVO = null;
				List<InsuredCommentVO> insuredCommentsList = null;
				Object[] commentDetailsObj;

				Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
				String FETCH_INSURED_COMMENTS_QUERY = "Select pc.POC_POLICY_ID, pol.POL_POLICY_NO, pc.POC_COMMENTS, doc.DOC_E_DESC, to_char(pc.POC_DATE,'dd/MM/yyyy hh:mi:ss AM'), pc.POC_PREPARED_BY ,pc.POC_DATE from T_TRN_POLICY pol, T_TRN_POLICY_COMMENTS pc, T_MAS_DOCUMENT doc "
						+ " where pc.POC_POLICY_ID = pol.POL_POLICY_ID and pc.POC_ENDT_ID = pol.POL_ENDT_ID  and doc.DOC_CODE = pc.POC_DOCUMENT_CODE  and pc.POC_DOCUMENT_CODE in (1,3) and pol.POL_INSURED_CODE =?  "
						+ " union  Select pc.POC_POLICY_ID, pol.POL_QUOTATION_NO, pc.POC_COMMENTS, doc.DOC_E_DESC, to_char(pc.POC_DATE,'dd/MM/yyyy hh:mi:ss AM'), pc.POC_PREPARED_BY , pc.POC_DATE from T_TRN_POLICY_QUO pol, T_TRN_POLICY_COMMENTS pc, T_MAS_DOCUMENT doc "
						+ " where pc.POC_POLICY_ID = pol.POL_POLICY_ID and pc.POC_ENDT_ID = pol.POL_ENDT_ID  and doc.DOC_CODE = pc.POC_DOCUMENT_CODE  and pc.POC_DOCUMENT_CODE in (2,5,6) and pol.POL_INSURED_CODE= ? order by poc_date desc";

				Query query = session.createSQLQuery( FETCH_INSURED_COMMENTS_QUERY );
				query.setLong( 0, insuredCode.longValue() );
				query.setLong( 1, insuredCode.longValue() );

				List<Object> result = query.list();

				if( !Utils.isEmpty( result ) ){
					logger.debug( "Size of comments for Insured Code " + insuredCode + " is:_1" + result.size() );
					Iterator<Object> itr = result.iterator();

					commentListVO = new InsuredCommentListVO();
					insuredCommentsList = new ArrayList<InsuredCommentVO>();

					while( itr.hasNext() ){
						commentVO = new InsuredCommentVO();
						commentDetailsObj = (Object[]) itr.next();

						if( !Utils.isEmpty( commentDetailsObj[ 0 ] ) ) commentVO.setPocPolicyId( new Long( commentDetailsObj[ 0 ].toString() ) );
						if( !Utils.isEmpty( commentDetailsObj[ 1 ] ) ) commentVO.setQuotePolicyNo( commentDetailsObj[ 1 ].toString() );
						if( !Utils.isEmpty( commentDetailsObj[ 2 ] ) ) commentVO.setPocComments( commentDetailsObj[ 2 ].toString() );
						if( !Utils.isEmpty( commentDetailsObj[ 3 ] ) ) commentVO.setDocumentDesc( commentDetailsObj[ 3 ].toString() );
						if( !Utils.isEmpty( commentDetailsObj[ 4 ] ) ) commentVO.setCommentDate( commentDetailsObj[ 4 ].toString() );
						if( !Utils.isEmpty( commentDetailsObj[ 5 ] ) ) commentVO.setPocPreparedBy( new Integer( commentDetailsObj[ 5 ].toString() ) );
						insuredCommentsList.add( commentVO );
					}
					commentListVO.setInsuredComments( insuredCommentsList );
				}
				return commentListVO;
			}
		}
		return baseVO;
	}

	public BaseVO viewInsuredActiveTransactions( BaseVO baseVO ){
		InsuredVO insuredVO = (InsuredVO) baseVO;
		Long insuredCode = insuredVO.getInsuredId();
		Integer ccgCode = insuredVO.getCcgCode();
		
		TransactionVO transactionVO = null;
		TransactionSummaryVO transactionSummaryVO = null;
		TransactionVO[] transactionVOArray = null;
		Object[] activeTransObj;
		int i = 0;
		
		String FETCH_INSURED_ACTIVE_TRANS_QUOTE = "";
		String FETCH_INSURED_ACTIVE_TRANS_POLICY = "";
		
		// based on ccg code different view is getting invoked
		if(!Utils.isEmpty( ccgCode )){
			
			//Home or travel
			if(ccgCode.equals( Integer.valueOf( SvcConstants.CCG_CODE.toString() ) )){
				FETCH_INSURED_ACTIVE_TRANS_QUOTE =  "SELECT * from V_TRN_ACT_TXN_QUO_HT_PAS where POL_INSURED_CODE = " + insuredCode + com.Constant.CONST_AND_POL_ISSUE_HOUR_3;
				FETCH_INSURED_ACTIVE_TRANS_POLICY = "SELECT * from V_TRN_ACT_TXN_POL_HT_PAS where POL_INSURED_CODE = " + insuredCode + com.Constant.CONST_AND_POL_ISSUE_HOUR_3;
			}
			//SBS
			else if(ccgCode.equals( SvcConstants.CCG_CODE_SBS )){
				FETCH_INSURED_ACTIVE_TRANS_QUOTE = "SELECT * from V_TRN_ACT_TXN_QUO_PAS  where POL_INSURED_CODE = " + insuredCode + com.Constant.CONST_AND_POL_ISSUE_HOUR_3;
				FETCH_INSURED_ACTIVE_TRANS_POLICY = "SELECT * from V_TRN_ACT_TXN_POL_PAS  where POL_INSURED_CODE = " + insuredCode + com.Constant.CONST_AND_POL_ISSUE_HOUR_3;
			}
			
		}
		
		
		/*
		 * if broker is logged in then use prepared by check in where clause because
		 * broker can see only customer results which he created.
		 */
		if(( (UserProfile)insuredVO.getLoggedInUser() ).getRsaUser().getProfile().equalsIgnoreCase( "Broker" )){
			FETCH_INSURED_ACTIVE_TRANS_QUOTE  += " AND POL_PREPARED_BY = " + SvcUtils.getUserId(insuredVO);
			FETCH_INSURED_ACTIVE_TRANS_POLICY += " AND POL_PREPARED_BY = " + SvcUtils.getUserId(insuredVO);
		}
		
		
		
		
		String finalQuery = FETCH_INSURED_ACTIVE_TRANS_QUOTE + " UNION " + FETCH_INSURED_ACTIVE_TRANS_POLICY;
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery( finalQuery );
		List<Object> result = query.list();

		if( !Utils.isEmpty( result ) ){
			int size = result.size();
			logger.debug( "Size of active transactions for Insured Code " + insuredCode + " is:_2" + size );
			transactionVOArray = new TransactionVO[ size ];
			Iterator<Object> itr = result.iterator();

			while( itr.hasNext() ){
				transactionVO = new TransactionVO();
				activeTransObj = (Object[]) itr.next();

				if( !Utils.isEmpty( activeTransObj[ 0 ] ) ) transactionVO.setTransactionPolicyNumber(  activeTransObj[ 0 ].toString() );
				if( !Utils.isEmpty( activeTransObj[ 1 ] ) ) transactionVO.setTransactionClass( (String) activeTransObj[ 1 ] );
				if( !Utils.isEmpty( activeTransObj[ 2 ] ) ) transactionVO.setPolicyType( (String) activeTransObj[ 2 ] );
				if( !Utils.isEmpty( activeTransObj[ 3 ] ) ) transactionVO.setTransactionType( (String) activeTransObj[ 3 ] );
				if( !Utils.isEmpty( activeTransObj[ 4 ] ) ) transactionVO.setTransactionDate( (String) activeTransObj[ 4 ] );
				if( !Utils.isEmpty( activeTransObj[ 5 ] ) ) transactionVO.setExpiryDate( (String) activeTransObj[ 5 ] );
				if( !Utils.isEmpty( activeTransObj[ 6 ] ) ) transactionVO.setEffectiveDate( (String) activeTransObj[ 6 ] );
				if( !Utils.isEmpty( activeTransObj[ 8 ] ) && !Utils.isEmpty(activeTransObj[ 7 ])) transactionVO.setTransactionSumInsured( new BigDecimal( activeTransObj[ 7 ].toString() ) );
				if( !Utils.isEmpty( activeTransObj[ 9 ] ) && !Utils.isEmpty(activeTransObj[ 8 ])) transactionVO.setTransactionPremium( activeTransObj[ 8 ].toString() );
				if( !Utils.isEmpty( activeTransObj[ 10 ] ) ) if( !Utils.isEmpty( activeTransObj[ 0 ] ) ) transactionVO.setStatus( (String) activeTransObj[ 9 ] );
				if( !Utils.isEmpty( activeTransObj[ 11 ] ) ) if( !Utils.isEmpty( activeTransObj[ 0 ] ) ) transactionVO.setTransactionNo( activeTransObj[ 10 ].toString() );
				if( !Utils.isEmpty( activeTransObj[ 12 ] ) ) if(!Utils.isEmpty(activeTransObj[ 11 ])) transactionVO.setTransactionEndtId( activeTransObj[ 11 ].toString() );
				if( !Utils.isEmpty( activeTransObj[ 13 ] ) ) if(!Utils.isEmpty(activeTransObj[ 12 ])) transactionVO.setPolicyTariffCode( activeTransObj[ 12 ].toString() );
				/*
				 * Added location check. If Oman then consider Location code while fetching active transactions. 
				 */
				/** Removed the condition to set location code to VO for all the location**/
				/*if(!Utils.isEmpty(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION)) && 
						!Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase(SvcConstants.DUBAI.toString())){ */
				
					if( !Utils.isEmpty( activeTransObj[ 16 ] ) ) transactionVO.setLocationCode(Integer.valueOf(activeTransObj[ 16 ].toString()));
				//}
				transactionVOArray[ i ] = transactionVO;
				i++;
			}
			transactionSummaryVO = new TransactionSummaryVO();
			transactionSummaryVO.setTransactionArray( transactionVOArray );
			transactionSummaryVO.setNumberOfRecords( size );

		}
		return transactionSummaryVO;

	}


	/**
	 * 
	 */
	public BaseVO viewTransAccountHistory( BaseVO baseVO ){
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof TransactionVO ){

				TransactionVO transactionVO = (TransactionVO) baseVO;
				Long policyNo = Long.valueOf( transactionVO.getTransactionPolicyNumber() );

				AccountHistoryVO accountHistoryVO = null;
				AccountHistoryListVO accountHistoryListVO = null;
				List<AccountHistoryVO> accountHistoryList = null;

				Object[] accHistObj;
				int i = 0;

		String FETCH_TRANS_ACCOUNT_HIST = "SELECT tr_e_desc,voucher_no, to_char(voucher_date,'DD-MM-YYYY'), dtl_description, amount, policy_no, (cty_code || '-' || reg_code || '-' || loc_code || '-' || cc_code || '-' || tot_acc_code || '-' || gl_code) "
				+ "FROM st_v_trn_gl, t_mas_transaction WHERE policy_no = " + policyNo + " AND ref_tran_type IN (1,2,17) and tot_acc_code IN (1410,1420,6310) AND transaction_type = tr_code";

		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery( FETCH_TRANS_ACCOUNT_HIST );
		List<Object> result = query.list();

				if( !Utils.isEmpty( result ) ){
					int size = result.size();
					logger.debug( "Size of account history for Policy Id " + policyNo + " is:_3" + size );
					Iterator<Object> itr = result.iterator();

					accountHistoryListVO = new AccountHistoryListVO();
					accountHistoryList = new ArrayList<AccountHistoryVO>();
					while( itr.hasNext() ){
						accountHistoryVO = new AccountHistoryVO();
						accHistObj = (Object[]) itr.next();
						if( !Utils.isEmpty( accHistObj[ 0 ] ) ) accountHistoryVO.setTransactionDesc(  accHistObj[ 0 ].toString() );
						if( !Utils.isEmpty( accHistObj[ 1 ] ) ) accountHistoryVO.setVoucherNo( Long.valueOf( accHistObj[ 1 ].toString() ) );
						if( !Utils.isEmpty( accHistObj[ 2 ] ) ){
							try{
								accountHistoryVO.setVoucherDate( (String) accHistObj[ 2 ] );
							}
							catch( Exception e ){
								logger.error( "Error during parsing date" + accHistObj[ 2 ] );
							}
						}
						if( !Utils.isEmpty( accHistObj[ 3 ] ) ) accountHistoryVO.setDtlDescription(  accHistObj[ 3 ].toString() );
						if( !Utils.isEmpty( accHistObj[ 4 ] ) ){
							Double amount = new Double( accHistObj[ 4 ].toString() );
							if( amount.doubleValue() > 0 ){
								accountHistoryVO.setCredit( Double.valueOf( accHistObj[ 4 ].toString() ) );
							}
							else if( amount.doubleValue() < 0 ){
								BigDecimal debAmt = BigDecimal.valueOf( Double.valueOf( accHistObj[ 4 ].toString() ) ).multiply( CONVERSION );
								accountHistoryVO.setDebit( debAmt.doubleValue() );
							}
						}
						if( !Utils.isEmpty( accHistObj[ 5 ] ) ) accountHistoryVO.setPolicyNo( Long.valueOf( accHistObj[ 5 ].toString() ) );
						if( !Utils.isEmpty( accHistObj[ 6 ] ) ) accountHistoryVO.setTotAccCodeStr( accHistObj[ 6 ].toString() );

						accountHistoryList.add( accountHistoryVO );
					}
					accountHistoryListVO.setAccountHistoryList( accountHistoryList );

				}
				return accountHistoryListVO;
			}
		}
		return baseVO;
	}
	@Override
	public BaseVO viewTransClaimsHistory( BaseVO baseVO ){

		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof TransactionVO ){
				TransactionVO transactionVO = (TransactionVO) baseVO;
				Long policyNo = Long.valueOf( transactionVO.getTransactionPolicyNumber() );

				ClaimsHistoryVO claimsHistoryVO = null;
				ClaimsHistoryListVO claimsHistoryListVO = null;
				ClaimsHistoryVO[] claimsHistoryList = null;

				Object[] claimsHistObj;
				int i = 0;

		String FETCH_TRANS_CLAIMS_HIST = " SELECT claim.POL_POLICY_NO," +
				"  MAX(claim.POL_PT_E_DESC) POL_PT_E_DESC," +
				"  MAX(claim.POL_CL_E_DESC) POL_CL_E_DESC," +
				"  claim.INT_CLAIM_NO," +
				"  NVL((SELECT 'Recoverable'FROM DUAL WHERE EXISTS (SELECT 1 FROM T_TRN_CLM_SALVAGE WHERE SAL_CLAIM_ID = claim.INT_CLAIM_ID AND SAL_SALVAGE_TYPE=7 ) ),'Non Recoverable') claim_type," +
				"  MAX(claim.INT_E_LOSS_DESC)INT_E_LOSS_DESC," +
				"  MAX(PKG_PAS_UTILS.GET_CLAIM_COL_PAS(claim.int_Policy_Id,claim.int_Claim_Id)) CLM_COL," +
				"  MAX(claim.INT_DATE_OF_LOSS)INT_DATE_OF_LOSS," +
				"  MAX(claim.INT_DATE_OF_INTIMATION)INT_DATE_OF_INTIMATION," +
				"  SUM(claim.CLAIM_AMOUNT) CLAIM_AMOUNT," +
				"  SUM(claim.PAID_AMOUNT) PAID_AMOUNT," +
				"  SUM(claim.CLAIM_OS_AMOUNT) CLAIM_OS_AMOUNT," +
				"  MAX(claim.INT_E_REMARKS) INT_E_REMARKS" +
				" FROM St_V_Trn_Claim_Report claim " +
				" WHERE claim.pol_Policy_no= " +policyNo+
				" AND claim.int_Claim_Id   > 0" +
				" GROUP BY claim.POL_POLICY_ID,  claim.POL_POLICY_NO,  claim.INT_CLAIM_ID,  claim.INT_CLAIM_NO ";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery( FETCH_TRANS_CLAIMS_HIST );
		List<Object> result = query.list();

				if( !Utils.isEmpty( result ) ){
					int size = result.size();
					logger.debug( "Size of account history for Policy Id " + policyNo + " is:_4" + size );
					Iterator<Object> itr = result.iterator();

					claimsHistoryListVO = new ClaimsHistoryListVO();
					claimsHistoryList = new ClaimsHistoryVO[ size ];

					while( itr.hasNext() ){
						claimsHistoryVO = new ClaimsHistoryVO();
						claimsHistObj = (Object[]) itr.next();

						claimsHistoryVO.setSrNo( ( i + 1 ) + "" );
						if( !Utils.isEmpty( claimsHistObj[ 0 ] ) ) claimsHistoryVO.setPolPolicyNo( Long.valueOf( claimsHistObj[ 0 ].toString() ) );
						if( !Utils.isEmpty( claimsHistObj[ 1 ] ) ) claimsHistoryVO.setPolPolicyType( String.valueOf( claimsHistObj[ 1 ] ) );
						if( !Utils.isEmpty( claimsHistObj[ 2 ] ) ) claimsHistoryVO.setPolClassCode( String.valueOf( claimsHistObj[ 2 ] ) );
						if( !Utils.isEmpty( claimsHistObj[ 3 ] ) ) claimsHistoryVO.setIntClaimNo( Long.valueOf( claimsHistObj[ 3 ].toString() ) );
						if( !Utils.isEmpty( claimsHistObj[ 4 ] ) ) claimsHistoryVO.setTypeOfClaim( String.valueOf( claimsHistObj[ 4 ] ) );
						if( !Utils.isEmpty( claimsHistObj[ 5 ] ) ) claimsHistoryVO.setIntELossDesc( String.valueOf( claimsHistObj[ 5 ] ) );
						if( !Utils.isEmpty( claimsHistObj[ 6 ] ) ) claimsHistoryVO.setCauseOfLoss( String.valueOf( claimsHistObj[ 6 ] ) );
						if( !Utils.isEmpty( claimsHistObj[ 7 ] ) ) claimsHistoryVO.setIntDateOfLoss( (Date) claimsHistObj[ 7 ] );
						if( !Utils.isEmpty( claimsHistObj[ 8 ] ) ) claimsHistoryVO.setIntDateOfIntimation( (Date) claimsHistObj[ 8 ] );
						if( !Utils.isEmpty( claimsHistObj[ 9 ] ) ) claimsHistoryVO.setClaimAmount( new BigDecimal( claimsHistObj[ 9 ].toString() ) );
						if( !Utils.isEmpty( claimsHistObj[ 10 ] ) ) claimsHistoryVO.setPaidAmount( new BigDecimal( claimsHistObj[ 10 ].toString() ) );
						if( !Utils.isEmpty( claimsHistObj[ 11 ] ) ) claimsHistoryVO.setClaimOsAmount( new BigDecimal( claimsHistObj[ 11 ].toString() ) );
						if( !Utils.isEmpty( claimsHistObj[ 12 ] ) ) claimsHistoryVO.setComments( (String) claimsHistObj[ 12 ] );
						claimsHistoryList[ i ] = claimsHistoryVO;
						i++;
					}
					claimsHistoryListVO.setClaimsHistoryArray( claimsHistoryList );
					claimsHistoryListVO.setNumberOfRecords( size );
				}
				return claimsHistoryListVO;
			}
		}
		return baseVO;
	}
	/* 
	 * This method is used to save/update the insured details used in general info page.
	 * This is used for Phase3-Home and Travel
	 */
	@Override
	public BaseVO saveOrUpdateTmasInsured( BaseVO baseVO ){

		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
		Long insuredCode = policyDataVO.getGeneralInfo().getInsured().getInsuredCode();
		TMasInsured tMasInsured = new TMasInsured();
		/* Set the policyType and classCode for SBS as default.*/
		String policyType = Utils.getSingleValueAppConfig( "SBS_POLICY_TYPE" );
		String classCode = Utils.getSingleValueAppConfig( "SBS_CLASS_CODE" );

		try{
			if( !Utils.isEmpty( insuredCode ) ){
				tMasInsured = (TMasInsured) getHibernateTemplate().find( " from TMasInsured tMasInsured where  tMasInsured.insInsuredCode=? ", insuredCode ).get( 0 );
			}
			updateTmasInsured( tMasInsured, policyDataVO );
			if( Utils.isEmpty( insuredCode ) ){

				/* Get the LOB if present in ThreadLevelContext to fetch the corresponding policyType and classCode.*/
				if( !Utils.isEmpty( ThreadLevelContext.get( SvcConstants.TLC_LOB ) ) ){
					policyType = Utils.getSingleValueAppConfig( ThreadLevelContext.get( SvcConstants.TLC_LOB ).toString() + SvcConstants.POLICY_TYPE );
					classCode = Utils.getSingleValueAppConfig( ThreadLevelContext.get( SvcConstants.TLC_LOB ).toString() + SvcConstants.CLASS_CODE );
				}
				if(!Utils.isEmpty(policyDataVO.getCommonVO())){
					tMasInsured.setInsCcgCode( Short.valueOf( Utils.getSingleValueAppConfig( policyDataVO.getCommonVO().getLob().toString() + SvcConstants.LOB_CCG_CODE )));
				}else{
					tMasInsured.setInsCcgCode( Short.valueOf(  SvcConstants.CCG_CODE ));
				}
				
				tMasInsured.setInsCustomerId( DAOUtils.getCustoemrId( getHibernateTemplate(), policyDataVO.getScheme().getSchemeCode() ) );
				tMasInsured.setInsLocCode( policyDataVO.getGeneralInfo().getAdditionalInfo().getProcessingLoc() );
				tMasInsured.setInsPreparedDt( new Date() );
				
				/*
				 * If Customer source is not present i.e how you hear about RSA i.e in Monoline case then we will use
				 * Source Of Customer in Insured for business type.
				 */
				if( !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured().getBusType() )){
					tMasInsured.setInsSourceOfCust(  policyDataVO.getGeneralInfo().getInsured().getBusType().shortValue() );
				}
				
				if( !Utils.isEmpty( policyDataVO.getGeneralInfo().getSourceOfBus().getCustomerSource() )){
					tMasInsured.setInsSourceOfCust( Short.valueOf( policyDataVO.getGeneralInfo().getSourceOfBus().getCustomerSource() ) );
				}
				if( !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured().getVatRegNo()) ){
					tMasInsured.setInsVatRegNo(( policyDataVO.getGeneralInfo().getInsured().getVatRegNo()));//142244
				}
				
				
				Long insurCd = null;
				if( Utils.isEmpty( tMasInsured.getInsInsuredCode() ) ){
					insurCd = NextSequenceValue.getNextSequence( INSUREDID_CODE_SEQ, Integer.valueOf( policyType ), Integer.valueOf( classCode ), null, null,
							getHibernateTemplate() );
					tMasInsured.setInsInsuredCode( insurCd );
				}
				if(!Utils.isEmpty(policyDataVO.getCommonVO())){
					policyDataVO.getGeneralInfo().getInsured().setCcgCode( Integer.valueOf( Utils.getSingleValueAppConfig( policyDataVO.getCommonVO().getLob().toString().toString() + SvcConstants.LOB_CCG_CODE )) );
				}else{
					policyDataVO.getGeneralInfo().getInsured().setCcgCode( Integer.valueOf( SvcConstants.CCG_CODE) );
				}
				
				policyDataVO.getGeneralInfo().getInsured().setInsuredCode( tMasInsured.getInsInsuredCode() );
			}
			getHibernateTemplate().merge( tMasInsured );
			getHibernateTemplate().flush();
		}
		catch( HibernateException hibernateException ){
			throw new BusinessException( "pas.gi.couldNotGetCustDetails", hibernateException, "Error while trying to SELECT customer details from T_MAS_CASH_CUSTOMER_QU_1" );
		}

		return policyDataVO;

	}

	/**
	 * @param tMasInsured
	 * @param policyVO
	 * This methods maps PolicyDataVo to TmasInsured Pojo
	 */
	private void updateTmasInsured( TMasInsured tMasInsured, PolicyDataVO policyDataVO ){
		if( !( Utils.isEmpty( policyDataVO ) && Utils.isEmpty( policyDataVO.getGeneralInfo() ) && Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured() ) ) ){
			BaseBeanToBeanMapper<PolicyDataVO, TMasInsured> polVOToIns = BeanMapperFactory.getMapperInstance( PolicyDataVOToMasInsuredPOJO.class );
			tMasInsured = polVOToIns.mapBean( policyDataVO, tMasInsured );
			BeanMapper.map( policyDataVO, tMasInsured );

		}
	}
	/**
	 * @param baseVO
	 * This methods compares the tMasInsured Data with existing Data in Home&Travel
	 */
	
	@Override
	public BaseVO compareCommonTmasInsured( BaseVO baseVO ){
		System.out.println("************** In compareCommonTmasInsured ****************");
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
		Long insuredId=policyDataVO.getGeneralInfo().getInsured().getInsuredCode();
		TMasInsured tMasInsured = null;
		try{
			if(!Utils.isEmpty(insuredId)){
				tMasInsured = (TMasInsured) getHibernateTemplate().find(
					" from  TMasInsured tMasInsured where  tMasInsured.insInsuredCode=? ",
					insuredId ).get(0);

				policyDataVO.setInsuredChanged(compareTmasInsured(tMasInsured, policyDataVO));
			}	
		}
		catch( HibernateException hibernateException ){
			throw new BusinessException( "pas.gi.couldNotGetCustDetails", hibernateException, "Error while trying to SELECT customer details from T_MAS_CASH_CUSTOMER_QU_2" );
		}

		return policyDataVO;
	}
	
	private boolean compareTmasInsured( TMasInsured tMasInsured, PolicyDataVO policyDataVO ){

		BaseBeanToBeanMapper<PolicyDataVO,TMasInsured> polVOToIns = BeanMapperFactory.getMapperInstance( PolicyDataVOToMasInsuredPOJO.class );
		TMasInsured tMasInsuredNew = new TMasInsured();
		tMasInsuredNew = polVOToIns.mapBean( policyDataVO, tMasInsuredNew );
		String tMasInsuredCommon = "TMasInsuredCommon";
		if(!policyDataVO.getCommonVO().getLob().toString().equalsIgnoreCase(LOB.HOME.toString()) 
				&& !policyDataVO.getCommonVO().getLob().toString().equalsIgnoreCase(LOB.TRAVEL.toString())){
			tMasInsuredCommon = "TMasInsured";
		}
		boolean hasDataChanged = SvcUtils.hasDataChangedCommon( tMasInsuredNew, tMasInsured, tMasInsuredCommon );
		return hasDataChanged;
	}
	
	
	/**
	 * @param baseVO
	 * This methods compares the tMasInsured Data with existing Data in Home&Travel
	 */
	
	@Override
	public BaseVO compareCommonTmasInsuredForInsuredCheck( BaseVO baseVO ){
		System.out.println("************** In compareCommonTmasInsuredForInsuredCheck ****************");
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
		Long insuredId=policyDataVO.getGeneralInfo().getInsured().getInsuredCode();
		TMasInsured tMasInsured = null;
		try{
			tMasInsured = (TMasInsured) getHibernateTemplate().find(
					" from TMasInsured tMasInsured  where  tMasInsured.insInsuredCode=? ",
					insuredId ).get(0);

			policyDataVO.setInsuredChanged(compareTmasInsuredCheck(tMasInsured, policyDataVO));
		}
		catch( HibernateException hibernateException ){
			throw new BusinessException( "pas.gi.couldNotGetCustDetails", hibernateException, "Error while trying to SELECT customer details from T_MAS_CASH_CUSTOMER_QU_3" );
		}

		return policyDataVO;
	}
	
	private boolean compareTmasInsuredCheck( TMasInsured tMasInsured, PolicyDataVO policyDataVO ){

		BaseBeanToBeanMapper<PolicyDataVO,TMasInsured> polVOToIns = BeanMapperFactory.getMapperInstance( PolicyDataVOToMasInsuredPOJO.class );
		TMasInsured tMasInsuredNew = new TMasInsured();
		tMasInsuredNew = polVOToIns.mapBean( policyDataVO, tMasInsuredNew );
		String tMasInsuredCommonCheck = "TMasInsuredCommonCheck";
		if(!policyDataVO.getCommonVO().getLob().toString().equalsIgnoreCase(LOB.HOME.toString()) 
				&& !policyDataVO.getCommonVO().getLob().toString().equalsIgnoreCase(LOB.TRAVEL.toString())){
			tMasInsuredCommonCheck = "TMasInsuredMonolineCheck";
		}
		boolean hasDataChanged = SvcUtils.hasDataChangedCheck( tMasInsuredNew, tMasInsured, tMasInsuredCommonCheck );
		return hasDataChanged;
	}

}
