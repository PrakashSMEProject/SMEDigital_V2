/**
 * 
 */
package com.rsaame.pas.fidelity.ui;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.ui.cmn.SaveSectionRH;
import com.rsaame.pas.vo.bus.FidelityNammedEmployeeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityUnnammedEmployeeVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.GroupPersonalAccidentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

/**
 * @author m1016303
 *
 */
public class FidelitySaveRH extends SaveSectionRH{

	/* (non-Javadoc)
	 * @see com.rsaame.pas.ui.cmn.SaveSectionRH#validate(com.rsaame.pas.vo.bus.RiskGroup, com.rsaame.pas.vo.bus.RiskGroupDetails, com.rsaame.pas.vo.bus.SectionVO)
	 */
	@Override
	protected void validate( RiskGroup rg, RiskGroupDetails rgd, SectionVO section, BaseVO baseVO ){
		validate( (FidelityVO) rgd, section );

	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.ui.cmn.SaveSectionRH#mapRiskGroup(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected RiskGroup mapRiskGroup( HttpServletRequest request ){
		return BeanMapper.map( request, LocationVO.class );
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.ui.cmn.SaveSectionRH#mapRiskGroupDetails(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected RiskGroupDetails mapRiskGroupDetails( HttpServletRequest request ){
		FidelityVO fidelityVO = BeanMapper.map( request, FidelityVO.class );
		UWQuestionsVO questionsVO = BeanMapper.map( request, UWQuestionsVO.class );
		fidelityVO.setUwQuestions( questionsVO );

		return fidelityVO;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.ui.cmn.SaveSectionRH#sectionLogic(com.rsaame.pas.ui.cmn.PolicyContext)
	 */
	@Override
	protected void sectionLogic( PolicyContext policyContext ){
		// TODO Auto-generated method stub

	}

	private void validate( FidelityVO fidelityVO, SectionVO sectionVO ){

		TaskExecutor.executeTasks( "FIDELITY_PAGE_VAL", fidelityVO );
		for( FidelityNammedEmployeeDetailsVO employeeDetailsVO : fidelityVO.getFidelityEmployeeDetails() ){
			TaskExecutor.executeTasks( "FIDELITY_NAMED_EMP_PAGE", employeeDetailsVO );
		}
		for( FidelityUnnammedEmployeeVO unnammedEmployeeVO : fidelityVO.getUnnammedEmployeeDetails() ){
			TaskExecutor.executeTasks( "FIDELITY_UNNAMED_EMP_PAGE", unnammedEmployeeVO );
		}

	}

	protected boolean isDataChanged( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){
		Boolean isDataChanged = true;
		/*try{
			if( rgd instanceof FidelityVO ){

				FidelityVO requestMappedFidelityVO = (FidelityVO) rgd;
				// The GroupPersonalAccidentVO from context 
				FidelityVO contextFidelityVO = null;
				// for first save section details will be empty 
				contextFidelityVO = (FidelityVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( contextFidelityVO ) ){
					return true;
				}
				// compare requestMapped and context GPAVO 
				if( requestMappedFidelityVO.toString().equals( contextFidelityVO.toString() ) ){
					isDataChanged = false;
				}
			}

		}
		catch( Exception e ){
			throw new BusinessException( "cmn.compareError", null, "Error in compare" );
		}*/

		return isDataChanged;
	}

	/* This method is used to identify the rows removed from the UI page and the corresponding
	* table entries to be deleted for these records.The Logic will be specific to each table
	* 
	* @return rgd (This will be modified based on whether insertion or deletion
	*/

	protected void setRowToBeDeletedFlag( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){
		try{

			if( rgd instanceof FidelityVO ){

				FidelityVO requestMappedFidelityVO = (FidelityVO) rgd;
				// The GroupPersonalAccidentVO from context 
				FidelityVO contextFidelityVO = null;
				// for first save section details will be empty 
				contextFidelityVO = (FidelityVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( contextFidelityVO ) ){
					return;
				}
				/* adding to the request the records in context with deletion flag set as these will not be 
					 * available as part of request mapping(in rgd)*/
				for( FidelityNammedEmployeeDetailsVO nammedEmployee : contextFidelityVO.getFidelityEmployeeDetails() ){
					if( !requestMappedFidelityVO.getFidelityEmployeeDetails().contains( nammedEmployee ) ){
						nammedEmployee.setIsToBeDeleted( true );
						requestMappedFidelityVO.setIsToBeDeleted( true );
						requestMappedFidelityVO.getFidelityEmployeeDetails().add( nammedEmployee );
					}
				}

				for( FidelityUnnammedEmployeeVO unnammedEmployeeVO : contextFidelityVO.getUnnammedEmployeeDetails() ){
					if( !requestMappedFidelityVO.getUnnammedEmployeeDetails().contains( unnammedEmployeeVO ) ){
						unnammedEmployeeVO.setIsToBeDeleted( true );
						requestMappedFidelityVO.setIsToBeDeleted( true );
						requestMappedFidelityVO.getUnnammedEmployeeDetails().add( unnammedEmployeeVO );
					}
				}

			}
		}
		catch( Exception e ){
			throw new BusinessException( "cmn.compareError", null, "Error in compare" );
		}
	}

	/* This method is used to remove the delted rows from context
	* 
	* @return rgd (This will be modified based on whether insertion or deletion
	*/
	protected void removeDeletedRowsFromContext( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){
		try{
			if( Utils.isEmpty( rgd.getIsToBeDeleted() ) ) return;

			if( rgd instanceof FidelityVO ){
				FidelityVO contextFidelityVO = null;
				boolean deletionflagNamed = false;
				boolean deletionflagUnnamed = false;
				ArrayList<FidelityNammedEmployeeDetailsVO> toBeDeletedVOs = new ArrayList<FidelityNammedEmployeeDetailsVO>();
				ArrayList<FidelityUnnammedEmployeeVO> toBeDeletedVOs1 = new ArrayList<FidelityUnnammedEmployeeVO>();
				contextFidelityVO = (FidelityVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( contextFidelityVO ) ) return;
				for( FidelityNammedEmployeeDetailsVO nammedEmpVO : contextFidelityVO.getFidelityEmployeeDetails() ){
					if( !Utils.isEmpty( nammedEmpVO.getIsToBeDeleted() ) && nammedEmpVO.getIsToBeDeleted() ){
						toBeDeletedVOs.add( nammedEmpVO );
						deletionflagNamed = true;
					}
				}
				for( FidelityUnnammedEmployeeVO unnammedEmpVO : contextFidelityVO.getUnnammedEmployeeDetails() ){
					if( !Utils.isEmpty( unnammedEmpVO.getIsToBeDeleted() ) && unnammedEmpVO.getIsToBeDeleted() ){
						toBeDeletedVOs1.add( unnammedEmpVO );
						deletionflagUnnamed = true;
					}
				}
				if( deletionflagNamed ){
					for( FidelityNammedEmployeeDetailsVO toBeDeletedVO : toBeDeletedVOs ){

						( (FidelityVO) rgd ).getFidelityEmployeeDetails().remove( toBeDeletedVO );
					}
				}
				if( deletionflagUnnamed ){
					for( FidelityUnnammedEmployeeVO toBeDeletedVO1 : toBeDeletedVOs1 ){

						( (FidelityVO) rgd ).getUnnammedEmployeeDetails().remove( toBeDeletedVO1 );
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
