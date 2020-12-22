package com.rsaame.pas.dos.ui;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.ui.cmn.SaveSectionRH;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

public class DeteriorationOfStockSaveRH extends SaveSectionRH{

	static final int DOS_SECTION = Integer.parseInt( com.mindtree.ruc.cmn.utils.Utils.getSingleValueAppConfig( "DETERIORATION_OF_STOCK_SECTION" ));

	@Override
	protected void validate( RiskGroup rg, RiskGroupDetails rgd, SectionVO section, BaseVO baseVO ){
		validate((DeteriorationOfStockVO)rgd);
		
	}

	private void validate( DeteriorationOfStockVO rgd ){
		for(DeteriorationOfStockDetailsVO detailsVO : rgd.getDeteriorationOfStockDetails()){
			if(!(detailsVO.getSumInsuredDetails().getSumInsured() == 0.0 && 
					
					detailsVO.getSumInsuredDetails().getDeductible()==0.0))
					TaskExecutor.executeTasks( "DOS_PAGE", detailsVO );
		}
	}

	@Override
	protected RiskGroup mapRiskGroup( HttpServletRequest request ){
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request ) ;
		PolicyVO policyVO = policyContext.getPolicyDetails();
		LookUpListVO listVO = SvcUtils.getLookUpCodesList( "STOCK_TYPE",policyVO.getScheme().getSchemeCode().toString(),policyVO.getScheme().getTariffCode().toString() );
		
		if(!com.mindtree.ruc.cmn.utils.Utils.isEmpty( listVO ) &&  !com.mindtree.ruc.cmn.utils.Utils.isEmpty( listVO.getLookUpList() )){
			java.util.List<LookUpVO> list = listVO.getLookUpList();
			request.setAttribute( "dosStockType",list );
		}
		
		return BeanMapper.map( request, LocationVO.class );
	}

	@Override
	protected RiskGroupDetails mapRiskGroupDetails( HttpServletRequest request ){
		DeteriorationOfStockVO dosVo = BeanMapper.map( request, DeteriorationOfStockVO.class );
		UWQuestionsVO uwQuestionsVO = BeanMapper.map( request, UWQuestionsVO.class );
		dosVo.setUwQuestions( uwQuestionsVO );
		return dosVo;
	}

	@Override
	protected void sectionLogic( PolicyContext policyContext ){
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}
	
	@Override
	protected void setRowToBeDeletedFlag( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){
		if(rgd instanceof DeteriorationOfStockVO){
			DeteriorationOfStockVO requestMappedDOSVO = (DeteriorationOfStockVO) rgd;
			DeteriorationOfStockVO contextDOSVo = (DeteriorationOfStockVO) PolicyUtils.getRiskGroupDetails( rg, currentSection );
			if(com.mindtree.ruc.cmn.utils.Utils.isEmpty( contextDOSVo )){
				
				return;
			}
		
			for( DeteriorationOfStockDetailsVO deteriorationOfStockDetailsVO : ( (DeteriorationOfStockVO) contextDOSVo ).getDeteriorationOfStockDetails() ){
				if(!requestMappedDOSVO.getDeteriorationOfStockDetails().contains( deteriorationOfStockDetailsVO )){
						deteriorationOfStockDetailsVO.setIsToBeDeleted( true );
						requestMappedDOSVO.setIsToBeDeleted( true );
						requestMappedDOSVO.getDeteriorationOfStockDetails().add( deteriorationOfStockDetailsVO );
						}
			}
			
		}
	}
	
	/* This method is used to remove the delted rows from context
	* 
	* @return rgd (This will be modified based on whether insertion or deletion
	*/
	protected void removeDeletedRowsFromContext( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){
		
			if( com.mindtree.ruc.cmn.utils.Utils.isEmpty( rgd.getIsToBeDeleted() ) ){
				return;
			}
			if( rgd instanceof DeteriorationOfStockVO ){
				DeteriorationOfStockVO contextDOSVO = null;
				boolean deletionflag = false;
				ArrayList<DeteriorationOfStockDetailsVO> toBeDeletedVOs = new ArrayList<DeteriorationOfStockDetailsVO>();
				contextDOSVO = (DeteriorationOfStockVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( com.mindtree.ruc.cmn.utils.Utils.isEmpty( contextDOSVO ) ){
					return;
				}
				for( DeteriorationOfStockDetailsVO deteriorationOfStockDetailsVO : contextDOSVO.getDeteriorationOfStockDetails() ){
					if( !com.mindtree.ruc.cmn.utils.Utils.isEmpty( deteriorationOfStockDetailsVO.getIsToBeDeleted() ) && deteriorationOfStockDetailsVO.getIsToBeDeleted() ){
						toBeDeletedVOs.add( deteriorationOfStockDetailsVO );
						deletionflag = true;
					}
				}
				if( deletionflag ){
					for( DeteriorationOfStockDetailsVO toBeDeletedVO : toBeDeletedVOs ){

						( (DeteriorationOfStockVO) rgd ).getDeteriorationOfStockDetails().remove( toBeDeletedVO );
					}
					policyContext.addRiskGroupDetails( currentSection.getSectionId(), rg, rgd );
				}
			}
	}

}
