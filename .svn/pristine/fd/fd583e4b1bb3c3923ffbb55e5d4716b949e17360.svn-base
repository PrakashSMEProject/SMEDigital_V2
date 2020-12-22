package com.rsaame.pas.tb.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.ui.cmn.SaveSectionRH;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.TravelBaggageVO;
import com.rsaame.pas.vo.bus.TravellingEmployeeVO;

/*
 * author m1017935
 * 
 */
public class TBContentSaveRH extends SaveSectionRH{

	private static final Double DOUBLE_CONSTANT=0.00d;
	
	private void validate( TravelBaggageVO travelBaggageVO ){
		
		for(TravellingEmployeeVO travellingEmployeeVO : travelBaggageVO.getTravellingEmpDets()){		
			if(!Utils.isEmpty( travelBaggageVO.getTravellingEmpDets().get( 0 ).getSumInsuredDtl().getDeductible())){
				travellingEmployeeVO.getSumInsuredDtl().setDeductible( travelBaggageVO.getTravellingEmpDets().get( 0 ).getSumInsuredDtl().getDeductible() );
			}
			TaskExecutor.executeTasks( "TB_PAGE_SAVE_VAL", travellingEmployeeVO );
		}	
	}
	
	@Override
	protected void validate( RiskGroup rg, RiskGroupDetails rgd, SectionVO section, BaseVO baseVO ){
		
		validate( (TravelBaggageVO)rgd );

	}

	@Override
	protected RiskGroup mapRiskGroup( HttpServletRequest request ){
		return BeanMapper.map( request, LocationVO.class );
	}

	@Override
	protected RiskGroupDetails mapRiskGroupDetails( HttpServletRequest request ){
		TravelBaggageVO travelBaggageVO;
		
		travelBaggageVO = BeanMapper.map( request, TravelBaggageVO.class );
		
		/* Map and Set UW Questions and Details if any */ 
		
		return travelBaggageVO;
	}

	@Override
	protected void sectionLogic( PolicyContext policyContext ){
		// TODO Auto-generated method stub

	}
	
	protected  boolean isDataChanged( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){
		Boolean isDataChanged = true;
		
			/*if( rgd instanceof TravelBaggageVO ){

				// request Mapped rgd(TravelBaggage) 
				TravelBaggageVO requestMappedTbVO = (TravelBaggageVO) rgd;
				// This deductible mapping is only specific to TravelBaggage 
				for( TravellingEmployeeVO travellingEmployeeVO : requestMappedTbVO.getTravellingEmpDets() ){
					if( !Utils.isEmpty( requestMappedTbVO.getTravellingEmpDets().get( 0 ).getSumInsuredDtl().getDeductible() ) ){
						travellingEmployeeVO.getSumInsuredDtl().setDeductible( requestMappedTbVO.getTravellingEmpDets().get( 0 ).getSumInsuredDtl().getDeductible() );
					}
				}
				// The TravelBaggageVO from context 
				TravelBaggageVO contextTbVO = null;
				// for first save section details will be empty 
				contextTbVO = (TravelBaggageVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( contextTbVO ) ){
					return true;
				}
				// compare requestMapped and context TRavelBaggageVO 
				if( requestMappedTbVO.toString().equals( contextTbVO.toString() ) ){
					isDataChanged = false;
				}
			}*/
		return isDataChanged;
	}

	/* This method is used to identify the rows removed from the UI page and the corresponding
	* table entries to be deleted for these records.The Logic will be specific to each table
	* 
	* @return rgd (This will be modified based on whether insertion or deletion
	*/

	protected void setRowToBeDeletedFlag( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){

			if( rgd instanceof TravelBaggageVO ){

				TravelBaggageVO requestMappedTbVO = (TravelBaggageVO) rgd;
				// The TravelBaggageVO from context 
				TravelBaggageVO contextTbVO = null;
				// for first save section details will be empty 
				contextTbVO = (TravelBaggageVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( contextTbVO ) ){
					return ;
				}

				List<TravellingEmployeeVO> toBeDeletedVOs = new ArrayList<TravellingEmployeeVO>();
				boolean deletionFlag = false;
				for( TravellingEmployeeVO travellingEmployeeVO : ( (TravelBaggageVO) rgd ).getTravellingEmpDets() ){
					if( Utils.isEmpty( travellingEmployeeVO.getName() ) && Utils.isEmpty( travellingEmployeeVO.getGprId() )
							&& ( DOUBLE_CONSTANT ).equals( travellingEmployeeVO.getSumInsuredDtl().getSumInsured() ) ){
						toBeDeletedVOs.add( travellingEmployeeVO );
						deletionFlag = true;
					}
				}
				if( deletionFlag ){
					for( TravellingEmployeeVO toBeDeletedVO : toBeDeletedVOs ){

						( (TravelBaggageVO) rgd ).getTravellingEmpDets().remove( toBeDeletedVO );
					}
				}
				/* adding to the request the records in context with deletion flag set as these will not be 
				 * available as part of request mapping(in rgd)*/

				for( TravellingEmployeeVO travellingEmployeeVO : contextTbVO.getTravellingEmpDets() ){
					if( !requestMappedTbVO.getTravellingEmpDets().contains( travellingEmployeeVO ) ){
						travellingEmployeeVO.setIsToBeDeleted( true );
						requestMappedTbVO.setIsToBeDeleted( true );
						requestMappedTbVO.getTravellingEmpDets().add( travellingEmployeeVO );
					}
				}
			}
	}

	
	/* This method is used to remove the delted rows from context
	* 
	* @return rgd (This will be modified based on whether insertion or deletion
	*/
	protected void removeDeletedRowsFromContext( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){
		
			if( Utils.isEmpty( rgd.getIsToBeDeleted() ) ){
				return;
			}
			if( rgd instanceof TravelBaggageVO ){
				TravelBaggageVO contextTbVO = null;
				boolean deletionflag = false;
				ArrayList<TravellingEmployeeVO> toBeDeletedVOs = new ArrayList<TravellingEmployeeVO>();
				contextTbVO = (TravelBaggageVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( contextTbVO ) ){
					return;
				}
				for( TravellingEmployeeVO teVO : contextTbVO.getTravellingEmpDets() ){
					if( !Utils.isEmpty( teVO.getIsToBeDeleted() ) && teVO.getIsToBeDeleted() ){
						toBeDeletedVOs.add( teVO );
						deletionflag = true;
					}
				}
				if( deletionflag ){
					for( TravellingEmployeeVO toBeDeletedVO : toBeDeletedVOs ){

						( (TravelBaggageVO) rgd ).getTravellingEmpDets().remove( toBeDeletedVO );
					}
					policyContext.addRiskGroupDetails( currentSection.getSectionId(), rg, rgd );
				}
			}
	}

}
