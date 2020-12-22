package com.rsaame.pas.quote.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.gen.domain.TMasInsured;
import com.rsaame.pas.gen.mapper.PolicyVOToMasInsuredPOJO;
import com.rsaame.pas.pojo.mapper.PolicyDataVOToMasInsuredPOJO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;

public class CheckIfInsuredExistsDAO extends BaseDBDAO implements ICheckIfInsuredExistsDAO{
	
	private static final String CUSTOMER_EXISTS = "customer_exists";
	private final static String QUOTE_SEQ_SBS = "SEQ_SBS_QUO_NO";
	private final static String INSUREDID_SEQ_SBS = "SEQ_INSURED_ID";
	
	public BaseVO checkIfInsuredExists(BaseVO baseVO){
		
		
		PolicyVO policyVO = (PolicyVO) baseVO;
		Integer issueLoc  = policyVO.getGeneralInfo().getAdditionalInfo().getIssueLoc();
		
		
		
		TMasInsured tMasInsured = null;
		
		/* PolicyVO to CashCustomerQuo pojo mapping begins */
		
		BaseBeanToBeanMapper<PolicyVO,TMasInsured> policyVoMasInsuredMapper = (BaseBeanToBeanMapper)BeanMapperFactory.getMapperInstance(PolicyVOToMasInsuredPOJO.class);
		
		tMasInsured = (TMasInsured) policyVoMasInsuredMapper.mapBean(policyVO,tMasInsured);
		
		/* Start : Changes to validate insured name, poBox, distribution channel combination. */
					
		List<TMasInsured> tMasInsList = getHibernateTemplate().find( prepareQueryForInsuredCheck(baseVO,tMasInsured ,"SBS_CHECK_CUST_EXISTS") ) ;
		
		if(!Utils.isEmpty(tMasInsList) && tMasInsList.size() >= 1){
			try {
					if(getInsuredCheckForPLQuote(tMasInsured.getInsEFirstName(),tMasInsured.getInsEZipCode(),"50")){
					     throw new BusinessException(CUSTOMER_EXISTS, null,"Customer already exist_1");
					}
			} catch (IllegalAccessException e) {
				throw new BusinessException( "cmn.compareError", e, "Error in record retriving" );
			}
        }
        /* End : Changes to validate insured name, poBox, distribution channel combination. */
        
        /* Customer already doesn't exist hence fetch quote number sequence which is to be used while saving
         * the record to PolicyQuo table
         * 
         */
        Long quoteNo = NextSequenceValue.getNextSequence( QUOTE_SEQ_SBS, Utils.getSingleValueAppConfig( "TRAN_TYPE_QUO" ),issueLoc,getHibernateTemplate()  );
		if( !Utils.isEmpty( quoteNo ) ){
			policyVO.setQuoteNo( quoteNo );
		}
        /*
         * Setting Insured id from sequence 
         */
        Long insuredId = NextSequenceValue.getNextSequence( INSUREDID_SEQ_SBS,null,null, getHibernateTemplate() );
		if( !Utils.isEmpty( insuredId ) ){
			policyVO.getGeneralInfo().getInsured().setInsuredCode( insuredId );
		}
		return baseVO;
	}

	private String prepareQueryForInsuredCheck(BaseVO baseVO,TMasInsured tMasInsured, String string) {
		
		String query = null;
		String[] custCheckParams = Utils.getMultiValueAppConfig( string );
		List<String> custCheckParamsList = Arrays.asList( custCheckParams );
		String distChannelText = null;
		String[] directChnlCodes = Utils.getMultiValueAppConfig( "DIRECT_CHNL_CODES","," );
		Integer brCode = null;
		
		if(baseVO instanceof PolicyVO) {
			PolicyVO policyVO = (PolicyVO) baseVO;
			UserProfile userProfileVO = (UserProfile) policyVO.getLoggedInUser();
			brCode = userProfileVO.getRsaUser().getBrokerId();
		}
		
		/* If the Distribution channel is selected as Broker/Agent, compare Broker/Agent Name. Else compare using Distribution channel. */
		if( tMasInsured.getInsBrCode() != null  || (!Utils.isEmpty(brCode) && brCode != 0) ){
            distChannelText = " tMasInsured.insBrCode = "+(tMasInsured.getInsBrCode() != null ? tMasInsured.getInsBrCode() : brCode );
        }else if( tMasInsured.getInsAgentCode() != null ){
        	distChannelText = " tMasInsured.insAgentCode = "+tMasInsured.getInsAgentCode();
        }else if( tMasInsured.getInsDistributionChnl() != null && !Utils.isEmpty( directChnlCodes ) 
        		&& CopyUtils.asList( directChnlCodes ).contains( tMasInsured.getInsDistributionChnl().toString() ) ){
        	//distChannelText = " tMasInsured.insDistributionChnl = "+tMasInsured.getInsDistributionChnl();
        	distChannelText = " tMasInsured.insDistributionChnl in ( "+Utils.getSingleValueAppConfig( "DIRECT_CHNL_CODES" )+ " )";
        } 
		// Used Stringbuffer to avoid "+" to fix sonar violation on 18-9-2017
		StringBuffer queryString=new StringBuffer();
		
		query = "from TMasInsured tMasInsured where ";
		
			try{
				for(String param : custCheckParamsList){
					queryString = queryString
											.append("upper("+param + ")=\'")
											.append(String.valueOf(PropertyUtils.getProperty( tMasInsured, param )).toUpperCase())
											.append("\'")
											.append(com.Constant.CONST_AND_END);
					
				   // query = query + "upper("+param + ")=\'" + String.valueOf(PropertyUtils.getProperty( tMasInsured, param )).toUpperCase() + "\'" + com.Constant.CONST_AND_END;
				}
				query=query.concat(queryString.toString());
				
			}
			
			catch( IllegalAccessException e ){
				throw new BusinessException( "cmn.compareError", e, "Error in compare, tried to access private attribute" );
			}
			catch( InvocationTargetException e ){
				throw new BusinessException( "cmn.compareError", e, "Error in compare" );
			}
			catch( NoSuchMethodException e ){
				throw new BusinessException( "cmn.compareError", e, "Error in compare, one of the field is not present in pojo" );
			
		}
		if( !Utils.isEmpty(distChannelText)){
			query = query.concat(distChannelText);
		}
		else{
			if(query.endsWith( com.Constant.CONST_AND_END )){
				int index = query.lastIndexOf( com.Constant.CONST_AND_END );
				query = query.substring( 0, index );
			}
		}
		
		return query;
	}
	
	/**
	 * This method is used to check for existing insured for Home and Travel
	 * @param policyDataVO
	 * @return
	 */
	public BaseVO commonCheckIfInsuredExists(BaseVO policyDataVO){
		  
		PolicyDataVO polDataVO = (PolicyDataVO)policyDataVO;
		TMasInsured tMasInsured = new TMasInsured();
		BaseBeanToBeanMapper<PolicyDataVO, TMasInsured> polVOToIns = BeanMapperFactory.getMapperInstance( PolicyDataVOToMasInsuredPOJO.class );
		tMasInsured = polVOToIns.mapBean( polDataVO, tMasInsured );
		BeanMapper.map( policyDataVO, tMasInsured );
		String checkIfCustParam = "COMMON_CHECK_CUST_EXISTS";
		
		if(!Utils.isEmpty(polDataVO.getCommonVO()) && 
				!polDataVO.getCommonVO().getLob().toString().equalsIgnoreCase(LOB.HOME.toString()) 
				&& !polDataVO.getCommonVO().getLob().toString().equalsIgnoreCase(LOB.TRAVEL.toString())){
			checkIfCustParam = "MONOLINE_CHECK_CUST_EXISTS";
		}
		
		
		List<TMasInsured> tMasInsList = getHibernateTemplate().find( prepareQueryForInsuredCheck(policyDataVO,tMasInsured,checkIfCustParam) ) ;
		
		if(tMasInsList.size() >= 1){
			try { 
				  if(checkIfCustParam.equals("MONOLINE_CHECK_CUST_EXISTS") && getInsuredCheckForPLQuote(tMasInsured.getInsEFirstName(),tMasInsured.getInsEZipCode(), "1" )){
					     throw new BusinessException(CUSTOMER_EXISTS, null,"Customer already exist_2");
				  }else if(checkIfCustParam.equals("COMMON_CHECK_CUST_EXISTS")){
					  	 throw new BusinessException(CUSTOMER_EXISTS, null,"Customer already exist_3");
				  }
			} catch (IllegalAccessException e) {
						throw new BusinessException( "cmn.compareError", e, "Error in record retriving" );
			}
		}
		return polDataVO;
	}
	
	
   /**
	 * This method checks whether insurer having policy for respective policy type(SBS:50,WC:1).
	 * @param insEFirstName
	 * @param insEZipCode
	 * @param polPolicyType
	 * @return Boolean
	 * @throws IllegalAccessException
 */
	public Boolean getInsuredCheckForPLQuote(String insEFirstName,String insEZipCode,String polPolicyType) throws IllegalAccessException {
					
			String sqlQuery = "SELECT PKG_PAS_UTILS.CHECK_IF_USER_HAS_PL_QUOTE('"+ insEFirstName +"','"+insEZipCode + "', "+polPolicyType + ") FROM DUAL"; 
				
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			String isInsuredExists = "";
			Query query = session.createSQLQuery(sqlQuery);
			List<Object> results = query.list();
			if (!Utils.isEmpty(results)) {
				isInsuredExists = results.get(0).toString();
			}
			if (isInsuredExists.equalsIgnoreCase("yes")) {
				return true;
			} else {
				return false;
			}
		}
	}
