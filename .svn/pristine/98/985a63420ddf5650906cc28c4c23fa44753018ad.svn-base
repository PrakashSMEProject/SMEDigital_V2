/**
 * LookUpDAO.java
 * Copyright (c) 2011 MindTree Ltd.
 */
package com.rsaame.pas.lookup.dao;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.cache.CacheManagerFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.kaizen.framework.model.SecurityContext;
import com.rsaame.pas.cmn.cache.PASCache;
import com.rsaame.pas.dao.model.LookUpView;
import com.rsaame.pas.dao.model.SsVMasLookup;
import com.rsaame.pas.dao.model.SsVMasLookupId;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;


/**
 * Class LookUpDAO is a dao class which extends BaseDAO
 * 
 * @version 1.0  Jan 2012
 * @author 	M1014594
 */
public class LookUpDAO extends BaseDBDAO implements ILookUpDAO{
	Logger logger = Logger.getLogger( LookUpDAO.class );
	/**
	 * Method to get the list of values for an identifier
	 * 
	 * @param 
	 * @return  
	 */

	public BaseVO getListOfDescription(BaseVO baseVO) throws DataAccessException
	{	
		
		
		List<LookUpView> lookUpList;
		LookUpListVO lookUpListVO=new LookUpListVO();
		LookUpVO inputVO=(LookUpVO)baseVO;
		String key = Utils.concat( inputVO.getCategory(), "-", inputVO.getLevel1(), "-", inputVO.getLevel2() );
		
		Object cachedObject = CacheManagerFactory.getCacheManager().get( PASCache.LOOKUP, key );
		if( !Utils.isEmpty( cachedObject ) ){
			lookUpListVO = (LookUpListVO) cachedObject;
			
		
			
			// Printing Logs
	/*		if(!Utils.isEmpty(lookUpListVO.getLookUpList()))
			{
				
				for(LookUpVO lookUpVOs:lookUpListVO.getLookUpList() )
				{	
					Preparing VO object for setting the response
					if(!Utils.isEmpty(lookUpVOs))
					{
						if(lookUpVOs.getCategory().equalsIgnoreCase("CITY")){
							logger.debug("*******PASCache.LOOKUP*********"+PASCache.LOOKUP);
							logger.debug("**********key******"+key);
							logger.debug("**********Inside getListOfDescription()************");
							logger.debug("**********In cache Block************");

						logger.debug("***in  getListOfDescription()****"+lookUpVOs.getCategory() + lookUpVOs.getCode() + lookUpVOs.getDescription());
						}
					}
				}
			}*/
			
		}
			
		else{
			
			
			
			/*To get the list of values for a giver identifier*/
			String[] paramNames={"identifier","level1","level2"};
			Object[] values={inputVO.getCategory(),inputVO.getLevel1(),inputVO.getLevel2()};
			//lookUpList=getHibernateTemplateMislive().findByNamedQueryAndNamedParam("getDescrptionList", paramNames,values);
			org.springframework.orm.hibernate3.HibernateTemplate ht = getHibernateTemplate();
			lookUpList=ht.findByNamedQueryAndNamedParam("getDescrptionList", paramNames,values);
			if(!Utils.isEmpty(lookUpList))
			{
				List<LookUpVO> lookUpVOList= new ArrayList<LookUpVO>();
				for(LookUpView lookUpView:lookUpList )
				{	
					/*Preparing VO object for setting the response*/
					if(!Utils.isEmpty(lookUpView))
					{
						LookUpVO lookUpVO=new LookUpVO();
						if(!Utils.isEmpty(lookUpView.getCategory()))
							lookUpVO.setCategory(lookUpView.getCategory());
						if(!Utils.isEmpty(lookUpView.getCode()))
							lookUpVO.setCode(lookUpView.getCode());
						if(!Utils.isEmpty(lookUpView.getDescription()))
							lookUpVO.setDescription(lookUpView.getDescription());
				/*		if(lookUpView.getCategory().equalsIgnoreCase("CITY")){
							logger.debug("**********Inside getListOfDescription()************");
							logger.debug("********In Else Block*********");
						logger.debug("***in getListOfDescription()****"+lookUpView.getCategory() + lookUpView.getCode() + lookUpView.getDescription());
						}*/
						lookUpVOList.add(lookUpVO);
					}
				}
				lookUpVOList.removeAll(Collections.singletonList(null));
				lookUpListVO.setLookUpList(lookUpVOList);
				
			}			
			if(!Utils.isEmpty( lookUpListVO.getLookUpList() ) && lookUpListVO.getLookUpList().size() > SvcConstants.zeroVal){
			CacheManagerFactory.getCacheManager().put( PASCache.LOOKUP, key, lookUpListVO );
			}
		}
		
		return lookUpListVO;

	}
	
	/**
	 * Method to get the description for an identifier and code
	 * 
	 * @param 
	 * @return  
	 */
	public BaseVO getDescription( BaseVO input ) throws DataAccessException{
		
	//	logger.debug("**********Inside getDescription()************");
		if( Utils.isEmpty( input ) ) return null;

		LookUpVO luInputVO = (LookUpVO) input;
		if( Utils.isEmpty( luInputVO ) || Utils.isEmpty( luInputVO.getCategory() ) || Utils.isEmpty( luInputVO.getCode() ) ) return null;

		LookUpVO lookUpVO = null;

		/* Get list codes for the category-level1-level2 combination. This will check even in the cache. */
		LookUpListVO luList = (LookUpListVO) getListOfDescription( luInputVO );

		/* Iterate through the list of codes to find the one we need. */
		List<LookUpVO> luVOList = luList.getLookUpList();
		if( Utils.isEmpty( luVOList ) ) return null;
		for( LookUpVO lu : luVOList ){
			if( !Utils.isEmpty( lu ) && lu.getCode().equals( luInputVO.getCode() ) ){
				lookUpVO = constructLookUpVO( lu.getCategory(), lu.getLevel1(), lu.getLevel2(), lu.getCode(), lu.getDescription() );
				break;
			}
		}
	
		return lookUpVO;
	}
	
	/**
	 * Returns the LookUpVO instance that has the code that applies for the category-level1-level2-description combination 
	 * passed in <code>input</code>.
	 * 
	 * @param input A LookUpVO instance that contains <code>category</code>, <code>level1</code>, <code>level2</code> and 
	 * <code>description</code> fields set
	 * @return The LookUpVO instance that has the code that applies for the category-level1-level2-description passed
	 */
	public BaseVO getCode( BaseVO input ) throws DataAccessException{
		
		//logger.debug("**********Inside getCode()************");
		if( Utils.isEmpty( input ) ) return null;

		LookUpVO luInputVO = (LookUpVO) input;
		
		/* Return null if category and description are not passed. Empty description values (empty string) can be a valid
		 * value and hence, is being allowed here. */
		if( Utils.isEmpty( luInputVO.getCategory() ) || luInputVO.getDescription() == null ) return null;

		LookUpVO lookUpVO = null;

		/* Get list codes for the category-level1-level2 combination. This will check even in the cache. */
		LookUpListVO luList = (LookUpListVO) getListOfDescription( luInputVO );

		/* Iterate through the list of codes to find the one we need. */
		List<LookUpVO> luVOList = luList.getLookUpList();
		for( LookUpVO lu : luVOList ){
			if( lu.getDescription() != null && lu.getDescription().trim().equals( luInputVO.getDescription().trim() ) ){
				lookUpVO = constructLookUpVO( lu.getCategory(), lu.getLevel1(), lu.getLevel2(), lu.getCode(), lu.getDescription() );
				break;
			}
		}

		return lookUpVO;
	}

	private LookUpVO constructLookUpVO( String category, String level1, String level2, BigDecimal code, String description ){
		LookUpVO lu = new LookUpVO();
	/*	if(category!=null && !Utils.isEmpty(category) && category.equalsIgnoreCase("CITY")){
		logger.debug("**********Inside constructLookUpVO()************");
		
		logger.debug("*************category*************"+category);
		logger.debug("*************level1*************"+level1);
		logger.debug("*************level2*************"+level2);
		logger.debug("*************code*************"+code);
		logger.debug("*************code*************"+description);
		}*/
		
		lu.setCategory( category );
		lu.setLevel1( level1 );
		lu.setLevel2( level2 );
		lu.setCode( code );
		lu.setDescription( description );
		
		return lu;
	}
	
	
	public void refreshCache(BaseVO baseVO) throws DataAccessException
	{	
		
		//logger.debug("**********Inside refreshCache()************");
		
		boolean aclRefresh = false;
		DataHolderVO<Object[]> input = (DataHolderVO<Object[]>) baseVO;
		Object keyData[] = input.getData();
		String lookUpKey = (String) keyData[0];  //LOGIN_LOCATIONS-ALL-ALL
		Map<String, Map<String,String>> roleFuntionMap = null;
		
		String[] str = lookUpKey.split("-");
		String identifier = str[0];
		String level1 = str[1];
		String level2 = str[2];
		
		List<LookUpView> lookUpList ;
		LookUpListVO lookUpListVO=new LookUpListVO();
		LookUpVO lookUpVO=new LookUpVO();
		lookUpVO.setCategory(identifier);
		lookUpVO.setLevel1(level1);
		lookUpVO.setLevel2(level2);
		
		String[] paramNames={"identifier","level1","level2"};
		Object[] values={lookUpVO.getCategory(),lookUpVO.getLevel1(),lookUpVO.getLevel2()};
		org.springframework.orm.hibernate3.HibernateTemplate ht = getHibernateTemplate();
		lookUpList=ht.findByNamedQueryAndNamedParam("getDescrptionList", paramNames,values);
		
		if(!Utils.isEmpty(lookUpList))
		{
			List<LookUpVO> lookUpVOList= new ArrayList<LookUpVO>();
			for(LookUpView lookUpView:lookUpList )
			{	
				if(!Utils.isEmpty(lookUpView))
				{
					LookUpVO lookUp=new LookUpVO();
					if(!Utils.isEmpty(lookUpView.getCategory()))
						lookUp.setCategory(lookUpView.getCategory());
					if(!Utils.isEmpty(lookUpView.getCode()))
						lookUp.setCode(lookUpView.getCode());
					if(!Utils.isEmpty(lookUpView.getDescription()))
						lookUp.setDescription(lookUpView.getDescription());
					//logger.debug("***In refreshCache()****"+lookUpView.getCategory() + lookUpView.getCode() + lookUpView.getDescription());
					
					lookUpVOList.add(lookUp);
				}
			}
			lookUpVOList.removeAll(Collections.singletonList(null));
			lookUpListVO.setLookUpList(lookUpVOList);
			
		}
		if(!Utils.isEmpty( lookUpListVO.getLookUpList() ) && lookUpListVO.getLookUpList().size() > SvcConstants.zeroVal)
		{
			CacheManagerFactory.getCacheManager().put( PASCache.LOOKUP, lookUpKey, lookUpListVO );
		}
		if("ACL-ACL-ACL".equals(lookUpKey))
		{
			aclRefresh = true;
		}
		
		if(aclRefresh)
		{
			
			roleFuntionMap = SecurityContext.getRoleFunctionMap();
			
			List<String> roleList = Arrays.asList( Utils.getMultiValueAppConfig( "ALL_ROLES" ));
			List<String> lobList = Arrays.asList( Utils.getMultiValueAppConfig( "LOB_LIST" ));
			
			SsVMasLookup lookup;SsVMasLookupId id;
				List<SsVMasLookup> lookupList = ht.find( "from SsVMasLookup where id.category = 'PAS_LOB' and id.level1='ALL' " );
				List<String> userIdList = ht.find( "select distinct(id.level2) from SsVMasLookup where id.category = 'PAS_LOB' and id.level1='ALL'" );
			for( String role : roleList ){
				for( String userId : userIdList ){
					//String roleFunction = Utils.concat( role, SvcConstants.DELIMITER, "CREATE_QUO" );
					String roleFunction = Utils.concat( role, SvcConstants.DELIMITER, "CREATE_QUO",SvcConstants.DELIMITER,
                            Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ));
					
					for( String lob : lobList ){
						String screenSection = Utils.concat( "HOME_PAGE", SvcConstants.DELIMITER, "LOB_LINKS_", lob, "_", userId );
						String privilege = "EDIT";
						lookup = new SsVMasLookup();
						id = new SsVMasLookupId();
						id.setCategory( "PAS_LOB" );
						id.setLevel1( "ALL" );
						id.setLevel2( userId );
						id.setDescription( lob );
						int code = Integer.parseInt( Utils.getSingleValueAppConfig( lob+"_POLICY_TYPE" ));
						
						id.setCode( BigDecimal.valueOf( code ));
						lookup.setId( id );
						if( !lookupList.contains( lookup ) ){
							privilege = "HIDE";
						}
						//if(privilege!="EDIT"){
						if( !roleFuntionMap.containsKey( roleFunction ) ){
							Map<String, String> screenSectionppriv = new HashMap<String, String>();
							screenSectionppriv.put( screenSection, privilege );
							roleFuntionMap.put( roleFunction, screenSectionppriv );
						}
						else{
							Map<String, String> screenSectionppriv = roleFuntionMap.get( roleFunction );
							screenSectionppriv.put( screenSection, privilege );
							roleFuntionMap.put( roleFunction, screenSectionppriv );
						}
						//}
					}
				}
			}
			SecurityContext.setRoleFunctionMap( roleFuntionMap );
	
			
		}
		
	}
		
}