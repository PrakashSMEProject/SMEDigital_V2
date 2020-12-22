package com.rsaame.pas.git.ui;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.ui.cmn.SaveSectionRH;
import com.rsaame.pas.vo.bus.CommodityDetailsVO;
import com.rsaame.pas.vo.bus.GoodsInTransitVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * @author m1016996
 * 
 * Request handler for Goods in transit SAVE flow.
 *
 */
public class GoodsInTransitPageSaveRH extends SaveSectionRH{

    @Override
    protected void validate(RiskGroup rg, RiskGroupDetails rgd,
	    SectionVO section, BaseVO baseVO) {

    	GoodsInTransitVO goodsInTransitVO = (GoodsInTransitVO) rgd;
		
    	TaskExecutor.executeTasks( "GOODS_IN_TRANSIT_PAGE_SAVE_VAL", goodsInTransitVO );

		for(CommodityDetailsVO commodityDetailsVO : goodsInTransitVO.getCommodityDtls()){
			TaskExecutor.executeTasks( "COMMODITY_DETAILS_PAGE_SAVE_VAL", commodityDetailsVO );
		}
		
    }

    @Override
    protected RiskGroup mapRiskGroup(HttpServletRequest request) {
	return BeanMapper.map( request, LocationVO.class );
    }

    @Override
    protected RiskGroupDetails mapRiskGroupDetails(HttpServletRequest request) {
	GoodsInTransitVO goodsInTransitVO = BeanMapper.map( request, GoodsInTransitVO.class );
	return goodsInTransitVO;
    }

    @Override
    protected void sectionLogic(PolicyContext policyContext) {
	// TODO Auto-generated method stub
	
    }
    
    
    /**
     * return true if the Data has been changed by comparing Context and request VO Details. 
     */
    protected boolean isDataChanged( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){
		Boolean isDataChanged = true;
		/*try{
			if( rgd instanceof GoodsInTransitVO ){

				GoodsInTransitVO requestMappedGITVO = (GoodsInTransitVO) rgd;
				// The GoodsInTransitVO from context 
				GoodsInTransitVO contextGITVO = null;
				// for first save section details will be empty 
				contextGITVO = (GoodsInTransitVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( contextGITVO ) ){
					return true;
				}
				// compare requestMapped and context GoodsInTransitVO 
				if( requestMappedGITVO.toString().equals( contextGITVO.toString() ) ){
					isDataChanged = false;
				}
			}

		}
		catch( Exception e ){
			throw new BusinessException( "cmn.compareError", null, "Error in compare" );
		}*/

		return isDataChanged;
	}

	/**
	 * This method is used to identify the rows removed from the UI page and the corresponding
	 * table entries to be deleted for these records.The Logic will be specific to each table
	 */
	protected void setRowToBeDeletedFlag( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){
		try{
			if( rgd instanceof GoodsInTransitVO ){

				GoodsInTransitVO requestMappedGITVO = (GoodsInTransitVO) rgd;
				// The GoodsInTransitVO from context 
				GoodsInTransitVO contextGITVO = null;
				// for first save section details will be empty 
				contextGITVO = (GoodsInTransitVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( contextGITVO ) ){
					return;
				}
				
				for( CommodityDetailsVO commodityDetailsVO : contextGITVO.getCommodityDtls() ){
					if( !requestMappedGITVO.getCommodityDtls().contains( commodityDetailsVO ) ){
						commodityDetailsVO.setIsToBeDeleted( true );
						requestMappedGITVO.setIsToBeDeleted( true );
						requestMappedGITVO.getCommodityDtls().add( commodityDetailsVO );
					}
				}

			}
		}
		catch( Exception e ){
			throw new BusinessException( "cmn.compareError", null, "Error in compare" );
		}
	}

	/**
	 *  Used to remove the deleted rows from the Context. to avoid the duplicate calculation for Premium.
	 */
	protected void removeDeletedRowsFromContext( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){
		try{
			if( Utils.isEmpty( rgd.getIsToBeDeleted() ) ) return;

			if( rgd instanceof GoodsInTransitVO ){
				GoodsInTransitVO contextGITVO = null;
				boolean deletionflag = false;
				ArrayList<CommodityDetailsVO> toBeDeletedVOs = new ArrayList<CommodityDetailsVO>();
				contextGITVO = (GoodsInTransitVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( contextGITVO ) ) return;
				for( CommodityDetailsVO commodityDetailsVO : contextGITVO.getCommodityDtls()){
					if( !Utils.isEmpty( commodityDetailsVO.getIsToBeDeleted() ) && commodityDetailsVO.getIsToBeDeleted() ){
						toBeDeletedVOs.add( commodityDetailsVO );
						deletionflag = true;
					}
				}
				if( deletionflag ){
					for( CommodityDetailsVO toBeDeletedVO : toBeDeletedVOs ){

						( (GoodsInTransitVO) rgd ).getCommodityDtls().remove( toBeDeletedVO );
					}
					policyContext.addRiskGroupDetails( currentSection.getSectionId(), rg, rgd );
				}
			}
		}
		catch( Exception e ){
			throw new BusinessException( "cmn.compareError", null, "Error in compare" );
		}
	}

}
