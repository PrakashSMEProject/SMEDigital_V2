/**
 * 
 */
package com.rsaame.pas.git.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.utils.Map;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseSectionLoadDAO;
import com.rsaame.pas.dao.model.TTrnMarineDetailQuo;
import com.rsaame.pas.dao.model.TTrnMarineHeaderQuo;
import com.rsaame.pas.dao.model.TTrnMarineTransitQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.CommodityDetailsVO;
import com.rsaame.pas.vo.bus.GoodsInTransitVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;

/**
 * @author m1016996
 * This Class is used to Populate the data from the database during the Load flow.
 */
public class GoodsInTransitLoadDAO extends BaseSectionLoadDAO{

    
    	private static final Map<String, String> gitVoyageToDescMap = new Map<String, String>( null, null );
	private static final Map<String, String> gitVoyageFromDescMap = new Map<String, String>( null, null );
	
    
	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionLoadDAO#getRiskDetails(com.rsaame.pas.vo.bus.RiskGroup, java.lang.Long, java.lang.Long, com.rsaame.pas.vo.app.LoadExistingInputVO)
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	protected RiskGroupDetails getRiskDetails( RiskGroup riskGroup, Long policyId, Long endId, LoadExistingInputVO lei ){

		GoodsInTransitVO transitVO = new GoodsInTransitVO();
		getGITLookupMAP();
		List<TTrnMarineTransitQuo> marineTransitQuos = null;
		Long bldId = null;
		
		/* 
		 * Changed to pick the risk details based on validity start date - Fix identified for defect
		 * defect nummber 355 - Phase 2 RADAR defect number
		 */
		Date validityStartDate = DAOUtils.getValidityStartDate( getHibernateTemplate(), endId, lei.getPolLinkingId(),lei.isQuote() );
		
		if( ! Utils.isEmpty( ( ( (LocationVO) riskGroup ).getRiskGroupId() ) ) ){
			bldId = Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() );
		}

		if( lei.getAppFlow().equals( Flow.VIEW_POL ) || lei.getAppFlow().equals( Flow.VIEW_QUO ) )
		{
			if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) )
			{
				/*marineTransitQuos = getHibernateTemplate().find( "from TTrnMarineTransitQuo transit where (id.mtEndtId,id.mtDeclarationId,id.mtPolicyId,mtBldId) in "
						+ "(select max(id.mtEndtId),id.mtDeclarationId,id.mtPolicyId,mtBldId from TTrnMarineTransitQuo where id.mtEndtId"+
						"<=? and id.mtPolicyId=? and mtBldId = ? group by id.mtPolicyId,mtBldId,id.mtDeclarationId)",
						endId,policyId,bldId);*/
				
				marineTransitQuos = getHibernateTemplate().find(
						"from TTrnMarineTransitQuo where id.mtPolicyId = ? and id.mtValidityStartDate <= ? and " + " mtValidityExpiryDate > ? and id.mtEndtId = ? and mtBldId=?",
						policyId, validityStartDate, validityStartDate, endId,bldId  );
			}
			else
			{
				 /*marineTransitQuos = getHibernateTemplate().find( "from TTrnMarineTransitQuo transit where (id.mtEndtId,id.mtDeclarationId,id.mtPolicyId,mtBldId) in "
							+ "(select max(id.mtEndtId),id.mtDeclarationId,id.mtPolicyId,mtBldId from TTrnMarineTransitQuo where id.mtEndtId"+
							"<=? and id.mtPolicyId=? and mtBldId = ? group by id.mtPolicyId,mtBldId,id.mtDeclarationId) and transit.mtStatus<>4",
							endId,policyId,bldId);*/
				 
				 marineTransitQuos = getHibernateTemplate().find(
							"from TTrnMarineTransitQuo where id.mtPolicyId = ? and id.mtValidityStartDate <= ? and " + " mtValidityExpiryDate > ? and " +
									" id.mtEndtId <= ? and mtStatus <> 4 and mtBldId=?", policyId,
							validityStartDate, validityStartDate, endId ,bldId );
			}
		}
		else
		{
		    marineTransitQuos = (List<TTrnMarineTransitQuo>) DAOUtils.getTableSnapshotQuery( "T_TRN_MARINE_TRANSIT_LOAD", lei.getAppFlow(), getHibernateTemplate(), false, endId,
				policyId, bldId );
		}
		

		if( !Utils.isEmpty( marineTransitQuos ) ){

		    	TTrnMarineTransitQuo marineTransit = marineTransitQuos.get(0);
		    	transitVO.setDeclarationId(marineTransit.getId().getMtDeclarationId());
		    	
			/*	List<TTrnMarineHeaderQuo> headerQuoList = null;
			  	if( lei.getAppFlow().equals( Flow.VIEW_POL ) || lei.getAppFlow().equals( Flow.VIEW_QUO ) )
				{
				if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) )
				{
				    headerQuoList = getHibernateTemplate().find(  "from TTrnMarineDetailQuo md where (id.mdEndtId, id.mdPolicyId,"
					    +"id.mdDeclarationId) in ( select max ( id.mdEndtId ), id.mdPolicyId, id.mdDeclarationId from TTrnMarineDetailQuo" 
					 +" where id.mdEndtId <= ? and id.mdPolicyId= ? and id.mdDeclarationId= ? group by id.mdPolicyId,id.mdDeclarationId)",endId,policyId,marineTransit.getId().getMtDeclarationId());
				}
				else
				{
				    headerQuoList = getHibernateTemplate().find( "from TTrnMarineDetailQuo md where (id.mdEndtId, id.mdPolicyId,"
					    +"id.mdDeclarationId) in ( select max ( id.mdEndtId ), id.mdPolicyId, id.mdDeclarationId from TTrnMarineDetailQuo " 
					    +"where id.mdEndtId <= ? and id.mdPolicyId= ? and id.mdDeclarationId= ? group by id.mdPolicyId,id.mdDeclarationId) "
					    +" and md.mdStatus <> 4",endId,policyId,marineTransit.getId().getMtDeclarationId());
				}
			}
			else
			{
			    	    headerQuoList =(List<TTrnMarineHeaderQuo>) DAOUtils.getTableSnapshotQuery( "T_TRN_MARINE_HEADER_LOAD", lei.getAppFlow(), getHibernateTemplate(), false,
						endId, policyId, marineTransit.getId().getMtDeclarationId() );
			}*/
			List<TTrnMarineHeaderQuo> headerQuoList =(List<TTrnMarineHeaderQuo>) DAOUtils.getTableSnapshotQuery( "T_TRN_MARINE_HEADER_LOAD", lei.getAppFlow(), getHibernateTemplate(), false,
				endId, policyId, marineTransitQuos.get( 0 ).getId().getMtDeclarationId() );
		
			
			TTrnMarineHeaderQuo headerQuo = headerQuoList.get( 0 );

			if(gitVoyageFromDescMap.containsKey(marineTransit.getMtEStartPlace())){
			    transitVO.setVoyageFrom(gitVoyageFromDescMap.get(marineTransit.getMtEStartPlace()));
			}
			
			if(gitVoyageToDescMap.containsKey(marineTransit.getMtEDestinationPlace())){
			    transitVO.setVoyageTo(gitVoyageToDescMap.get(marineTransit.getMtEDestinationPlace()));
			}
			
			transitVO.setSingleTransitLimit( headerQuo.getMhSingleTransitLimit() );
			//transitVO.setVoyageFrom(marineTransit.getMtEStartPlace());
			//transitVO.setVoyageTo(marineTransit.getMtEDestinationPlace());
			
			List<CommodityDetailsVO> commodityDetailsVOs = new ArrayList<CommodityDetailsVO>();
			TTrnPremiumQuo premiumQuo = null;
			
			
			List<TTrnMarineDetailQuo> detailQuoList = null;		
			
			if( lei.getAppFlow().equals( Flow.VIEW_POL ) || lei.getAppFlow().equals( Flow.VIEW_QUO ) )
			{
				if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) )
				{
				    /*detailQuoList = getHibernateTemplate().find(  "from TTrnMarineDetailQuo md where (id.mdEndtId, id.mdPolicyId,"
					    +"id.mdDeclarationId,id.mdCommodityId) in ( select max ( id.mdEndtId ), id.mdPolicyId, id.mdDeclarationId,id.mdCommodityId from TTrnMarineDetailQuo" 
					 +" where id.mdPolicyId= ? and id.mdDeclarationId= ? group by id.mdPolicyId,id.mdDeclarationId,id.mdCommodityId)",policyId,marineTransit.getId().getMtDeclarationId());*/
				    
				    detailQuoList = getHibernateTemplate().find(
							"from TTrnMarineDetailQuo where id.mdPolicyId = ? and id.mdValidityStartDate <= ? and " + " mdValidityExpiryDate > ? and id.mdEndtId = ? and id.mdDeclarationId=?",
							policyId, validityStartDate, validityStartDate, endId,marineTransit.getId().getMtDeclarationId()  );
				}
				else
				{
				    /*detailQuoList = getHibernateTemplate().find( "from TTrnMarineDetailQuo md where (id.mdEndtId, id.mdPolicyId,"
					    +"id.mdDeclarationId,id.mdCommodityId) in ( select max ( id.mdEndtId ), id.mdPolicyId, id.mdDeclarationId,id.mdCommodityId from TTrnMarineDetailQuo " 
					    +"where id.mdPolicyId= ? and id.mdDeclarationId= ? group by id.mdPolicyId,id.mdDeclarationId,id.mdCommodityId) "
					    +" and md.mdStatus <> 4",policyId,marineTransit.getId().getMtDeclarationId());*/
				    
				    detailQuoList = getHibernateTemplate().find(
							"from TTrnMarineDetailQuo where id.mdPolicyId = ? and id.mdValidityStartDate <= ? and " + " mdValidityExpiryDate > ? and " +
									" id.mdEndtId <= ? and mdStatus <> 4 and id.mdDeclarationId=?", policyId,
							validityStartDate, validityStartDate, endId ,marineTransit.getId().getMtDeclarationId() );
				}
			}
			else
			{
			    		detailQuoList =(List<TTrnMarineDetailQuo>)DAOUtils.getTableSnapshotQuery( "T_TRN_MARINE_DETAIL_LOAD", lei.getAppFlow(), getHibernateTemplate(), false,
					endId, policyId, marineTransit.getId().getMtDeclarationId() );
			}
			
			for( TTrnMarineDetailQuo marineDetailQuo : detailQuoList ){
			 
			    CommodityDetailsVO commodityDetailsVO = new CommodityDetailsVO();
			    
			    commodityDetailsVO.setSerialNo( (long) marineDetailQuo.getId().getMdSerialNo() );
			    commodityDetailsVO.setModeOfTransit( marineTransit.getMtModeOfTransit() );
			    commodityDetailsVO.setCommodityType( marineDetailQuo.getMdCommodityCode() );
			    commodityDetailsVO.setGoodsDescription( marineDetailQuo.getMdECommodityDesc() );
			    commodityDetailsVO.setCommodityId(marineDetailQuo.getId().getMdCommodityId());
			    if(!Utils.isEmpty( marineDetailQuo.getMdSiIndicator() )){
				commodityDetailsVO.setSiBasis( BigDecimal.valueOf( marineDetailQuo.getMdSiIndicator() ));	
			    }
			    
			    
			    List<TTrnPremiumQuo> premiumQuoList =(List<TTrnPremiumQuo>)DAOUtils.getTableSnapshotQuery( "T_TRN_PREMIUM", lei.getAppFlow(), getHibernateTemplate(), false, endId, policyId,
					 BigDecimal.valueOf( marineDetailQuo.getId().getMdCommodityId()),BigDecimal.valueOf( marineTransit.getId().getMtDeclarationId() ), Short.valueOf( Utils.getSingleValueAppConfig( "GIT_COVER" ) ),
					 Short.valueOf( Utils.getSingleValueAppConfig( "GIT_COVER_TYPE" ) ), Short.valueOf( Utils.getSingleValueAppConfig( "GIT_COVER_SUB_TYPE" ) ) );
			   
			    premiumQuo = premiumQuoList.get( 0 );
			    commodityDetailsVOs.add( commodityDetailsVO );
			}
			
			transitVO.setEstimatedAnnualCarryValue( detailQuoList.get(0).getMdEstAnnualSi() );
			
			if( ! Utils.isEmpty( premiumQuo ) ){
			    transitVO.setDeductible( premiumQuo.getPrmCompulsoryExcess().doubleValue() );
			}
			
			transitVO.setCommodityDtls( commodityDetailsVOs );
		}

		return transitVO;
	}
	
	protected void getGITVoyageFromDescMap(){
		LookUpListVO listVO = SvcUtils
				.getLookUpCodesList( "VOYAGE_FROM", "ALL", "ALL" );
		for( LookUpVO luVO : listVO.getLookUpList() ){
			gitVoyageFromDescMap.put( luVO.getDescription(), luVO.getCode().toString() );
		}
	}
	
	protected void getGITVoyageToDescMap(){
		LookUpListVO listVO = SvcUtils
				.getLookUpCodesList( "VOYAGE_TO", "ALL", "ALL" );
		for( LookUpVO luVO : listVO.getLookUpList() ){
			gitVoyageToDescMap.put( luVO.getDescription(), luVO.getCode().toString() );
		}
	}
	
	private void getGITLookupMAP(){
		getGITVoyageFromDescMap();
		getGITVoyageToDescMap();
	}

}
