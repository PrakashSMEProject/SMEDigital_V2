package com.rsaame.pas.dao.cmn;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtls;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtlsId;
import com.rsaame.pas.dao.model.VMasPasFetchBasicInfo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.PPPCriteriaVO;
import com.rsaame.pas.vo.app.SectionPPPDataHolder;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.PolicyVO;


public class BaseFetchPPPValDAO extends BaseDBDAO implements IFetchPPPValDAO{
	
	private final static Logger LOGGER = Logger.getLogger( BaseFetchPPPValDAO.class );
	
	/*
	 * (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.IFetchPPPValDAO#fetchPPPVal(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	
	@Override
	public SectionPPPDataHolder fetchPPPVal(BaseVO input) {
		/*(a).  First Check for the input passed , if null throw a System Exception
		 * (b). Fetch basic info from VMasPasFetchBasicInfo view which basically 
		 * consists of trade group, hazard level, RI category and commission value for 
		 * a section, class and tariff combination
		 * (c). Check if basic info obtained is null, then throw a System Exception as
		 * user cannot proceed with the process if the pre-filled values are null
		 * (d). Fetch Details for Basic Cover from VMasPasFetchBasicDtls view which 
		 * contains details configured for different risk types if applicable
		 * ex : For PAR contents are configured as Different risk types
		 * (e). Check if basic details obtained is null, then throw a System Exception as
		 * user cannot proceed with the process if the pre-filled values are null
		 * 
		 */
		
		if( LOGGER.isInfo() ) LOGGER.info( "Entering fetchPPPVal menthod" );
		
		/* 
		 * (a).
		 */
		DataHolderVO<List> pppInput = (DataHolderVO<List>) input;		
		List inputList = pppInput.getData();		
		PPPCriteriaVO pPPCriteriaVO = (PPPCriteriaVO)inputList.get(0);		
		PolicyVO policyVO = ( PolicyVO ) inputList.get(1);
		
		if(Utils.isEmpty(pPPCriteriaVO)){
			if(LOGGER.isError()) LOGGER.error("PPPCriteriaVO passed is null");
			throw new SystemException("",null,"");
		}
		
		/*
		 * (b).
		 */
		List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfo = getHibernateTemplate().find("from VMasPasFetchBasicInfo vbif  where vbif.id.secId = ? and vbif.id.classCode = ? and vbif.id.tariffCode = ?",Short.valueOf(String.valueOf(pPPCriteriaVO.getSectionId())),pPPCriteriaVO.getClassCode(),pPPCriteriaVO.getTariffCode());
		
		/*
		 * (c).
		 */
		
		if(Utils.isEmpty(vMasPasFetchBasicInfo)){
			if (LOGGER.isError()) LOGGER.error("Basic Info is not configured for ["+ pPPCriteriaVO.getSectionId() +"] section id and ["+pPPCriteriaVO.getClassCode() +"] class code and ["+pPPCriteriaVO.getTariffCode()+"] tariff code");
			throw new SystemException("",null,"");
		}
		
		/* Fetch base cover i.e. cover code 1  details from V_MAS_PAS_FETCH_BASIC_DTLS .
		 * Note - Content Risk Code is passed as null since it is not required for prepackaged tariff configuration */
		List<VMasPasFetchBasicDtls> NULL_BASIC_DTLS_LIST = null;
		Integer NULL_CNT_RSK_CODE = null; 
		
		List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsList = DAOUtils.getDataFromVMasPasFetchBasicDtls( getHibernateTemplate(), pPPCriteriaVO , NULL_BASIC_DTLS_LIST , pPPCriteriaVO, true , NULL_CNT_RSK_CODE );
		
		/* Fetch additional cover details from V_MAS_PAS_FETCH_ADDTL_DTLS and update it to base cover list obtained */
		
		vMasPasFetchBasicDtlsList = DAOUtils.getDataFromVMasPasFetchBasicDtls( getHibernateTemplate(), pPPCriteriaVO , vMasPasFetchBasicDtlsList , pPPCriteriaVO, false , NULL_CNT_RSK_CODE );
		
		SectionVO sectionVO = null;
		
		sectionLoadPreProcessing(policyVO);
		
		/*
		 *  Construct SectionVO by using getSectionPPPDetails method which will in turn 
		 *  invoke corresponding DAO based on the section id implementing risk details 
		 *  and risk group methods
		 */
		sectionVO = getSectionPPPDetails(pPPCriteriaVO,vMasPasFetchBasicDtlsList,vMasPasFetchBasicInfo);
		SectionPPPDataHolder secPPPDataHolder = new SectionPPPDataHolder();
		secPPPDataHolder.setSectionVO(sectionVO);
		
		/*
		 * Construct Contents List which will be set to SectionPPPDataHolder to use 
		 * it for Display on the screen
		 */
		List<Contents> contentsList = DAOUtils.getContentsListForSection(  pPPCriteriaVO, vMasPasFetchBasicDtlsList, vMasPasFetchBasicInfo);
		secPPPDataHolder.setContentsList(contentsList);
		
		sectionLoadPostProcessing(policyVO);
		return secPPPDataHolder;
	}
	
	
	/**
	 * Obtain the PPPFetchVal bean name corresponding to the section id passed
	 * from appconfig.properties
	 */
	public String getSectionDAOBeanName(Integer sectionId){
		
		String opTypeKey=Utils.concat( "OPTYPE_PPPVALBEAN_",sectionId.toString());
		String beanName=Utils.getSingleValueAppConfig( opTypeKey );
		return beanName;
	}
	
	
	/*
	 *  Constructing SectionVO by obtaining the bean corresponding to section id
	 *  and invoking getRiskDetails and getRiskGroup methods on the bean obtained
	 */
	
	public SectionVO getSectionPPPDetails(BaseVO input,List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsList,List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList){
		
		PPPCriteriaVO pppCriVO = (PPPCriteriaVO)input;
		String beanName = getSectionDAOBeanName(pppCriVO.getSectionId());
		if(Utils.isEmpty(beanName)){
			if(LOGGER.isError()) LOGGER.error("PPPFetchValDAO appconfig for ["+pppCriVO.getSectionId()+"] section id doesn't exist ");
			throw new SystemException("",null,"");
		}
		
		/*
		 * Obtain the bean implementing IConstructPPPRiskDetailsDAO to construct risk group details
		 */
		IConstructPPPRiskDetailsDAO iConstructPPPRskDetsDAO = (IConstructPPPRiskDetailsDAO)Utils.getBean(beanName);
		
		RiskGroup riskGroup = (RiskGroup)iConstructPPPRskDetsDAO.getRiskGroup(vMasPasFetchBasicInfoList);
		RiskGroupDetails riskGroupDetails = iConstructPPPRskDetsDAO.getRiskDetails(vMasPasFetchBasicDtlsList, vMasPasFetchBasicInfoList);
		SectionVO sectionVO = new SectionVO(RiskGroupingLevel.LOCATION);
		if(!Utils.isEmpty(riskGroup)){
			if(!Utils.isEmpty(riskGroupDetails)){
				Map rgMap = new com.mindtree.ruc.cmn.utils.Map( riskGroup.getClass(), riskGroupDetails.getClass() );
				rgMap.put(riskGroup, riskGroupDetails);
				sectionVO.setRiskGroupDetails( rgMap );
			}
		}
		if(!Utils.isEmpty(vMasPasFetchBasicInfoList.get(0).getId().getCommission())){
			sectionVO.setCommission(vMasPasFetchBasicInfoList.get(0).getId().getCommission().doubleValue());
		}
		
		sectionVO.setSectionId(pppCriVO.getSectionId());
		
		return sectionVO;
		
	}
	
	
	/*
	 * Constructing List<Contents> i.e. risk types which is configured for base cover 
	 */
	public List<Contents> getSectionPPPContentsList(BaseVO input,List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsList,List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList){
		
		List<Contents> contentsList = new com.mindtree.ruc.cmn.utils.List<Contents>(Contents.class);
		
		/*
		 * Use BeanMapper to form different Contents which is finally set to List of contents
		 */
		for(VMasPasFetchBasicDtls vMasPasFetchBasicDtls : vMasPasFetchBasicDtlsList){
			
			Contents content ;
			content = BeanMapper.map(vMasPasFetchBasicDtls,Contents.class);
			contentsList.add(content);
		}
		
		return contentsList;
	}
	
	@Override
	/**
	 * This method is used to fetch configured section list for a given tariff and PolicyType
	 */
	public BaseVO getSectionListForPPP( BaseVO baseVO ){
		
		if(Utils.isEmpty(baseVO)){
			if(LOGGER.isError()) LOGGER.error("PPPCriteriaVO passed is null");
			}
		List<BigDecimal> sectionListTemp;
		List<Short> sectionList =new ArrayList<Short>();
		PPPCriteriaVO pppCriteriaVO =(PPPCriteriaVO)baseVO;
		if(!Utils.isEmpty( pppCriteriaVO )){
			Integer tariffCode=pppCriteriaVO.getTariffCode();

			sectionListTemp = DAOUtils.getSqlResult( "select distinct SEC_ID from V_MAS_PAS_FETCH_BASIC_INFO where TARIFF_CODE = ? ", getHibernateTemplate(), tariffCode );
			
			for( BigDecimal secId : sectionListTemp){
				sectionList.add( Short.valueOf( secId.toString() ) );
			}
			
			pppCriteriaVO.setSectionList( sectionList );
		}
		return pppCriteriaVO;
	}	
	
	/**
	 * This method is for some pre-processing like construction of some VOs, setting flags, etc.
	 * @param policyVO
	 */
	protected void sectionLoadPreProcessing( PolicyVO policyVO ){
		/* This is getting used in WCFetchPPPValDAO to fetch the employee type for selected business type */
		ThreadLevelContext.set(SvcConstants.PPP_BUSSTYPE_WC_THREADVAR, policyVO.getGeneralInfo().getInsured().getBusType());
		if(!Utils.isEmpty(policyVO.getGeneralInfo()) && !Utils.isEmpty(policyVO.getGeneralInfo().getInsured()) &&   
				!Utils.isEmpty(policyVO.getGeneralInfo().getInsured().getTurnover()))
		{
			ThreadLevelContext.set(SvcConstants.ANNUAL_TURN_OVER, policyVO.getGeneralInfo().getInsured().getTurnover().toString());
			
		}
	}
	
	/**
	 * This method is for some post-processing like clearing ThreadLevelContext, setting flags, etc.
	 * @param policyVO
	 */
	protected void sectionLoadPostProcessing( PolicyVO policyVO ){
		/* This is used in WCFetchPPPValDAO to fetch the employee type for selected business type , clearing here after its usage */
		ThreadLevelContext.clear( SvcConstants.PPP_BUSSTYPE_WC_THREADVAR );
	}
	
}
