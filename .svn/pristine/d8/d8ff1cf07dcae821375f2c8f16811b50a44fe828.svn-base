package com.rsaame.pas.wc.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.ui.cmn.SaveSectionRH;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.WCNammedEmployeeVO;
import com.rsaame.pas.vo.bus.WCVO;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * 
 * @author M1017029
 *
 * Populates WCVO with values from UI and makes a call to the validation engine and service method. 
 */

public class WCSaveRH extends SaveSectionRH{
	private void validate( WCVO wc,PolicyVO policyVO ){
		
		TaskExecutor.executeTasks( "WC_PAGE_VAL", wc );
		
		/*
		 * Oman Consolidation : Validation for WC no of emp > GI no of emp is required only in Oman 
		 */
		if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.WC_NO_EMP_VAL)) && (Utils.getSingleValueAppConfig(AppConstants.WC_NO_EMP_VAL).equalsIgnoreCase( "true" ))){ 
			validateNumberOfEmployees(wc,policyVO);
		}
		
	}

	@Override
	protected void validate( RiskGroup rg, RiskGroupDetails rgd, SectionVO section, BaseVO baseVO ){
		
		PolicyVO policyVO = (PolicyVO) baseVO;
		WCVO wcvo = (WCVO) rgd;
		validate( wcvo,policyVO );
		
	}
	
	private void validateNumberOfEmployees(WCVO wcvo, PolicyVO policyVO) {
		
		Integer noOfEmpFromGI = policyVO.getGeneralInfo().getInsured().getNoOfEmployees();
		Integer noOfEmpFromWC = 0;
		
		for(EmpTypeDetailsVO  empDetail : wcvo.getEmpTypeDetails() ){
			noOfEmpFromWC += empDetail.getNoOfEmp();
		}
		
		java.util.List<Integer> noEmpList = new  ArrayList<Integer>();
		
		noEmpList.add(noOfEmpFromGI);
		noEmpList.add(noOfEmpFromWC);
		
		DataHolderVO<List<Integer>> dataHolderVO = new DataHolderVO();
		
		dataHolderVO.setData(noEmpList);
	
		TaskExecutor.executeTasks("WC_NO_EMP_VAL", dataHolderVO);
		
	}

	@Override
	protected RiskGroup mapRiskGroup( HttpServletRequest request ){
		return BeanMapper.map( request, LocationVO.class );
	}

	@Override
	protected RiskGroupDetails mapRiskGroupDetails( HttpServletRequest request ){
		WCVO wc = BeanMapper.map( request, WCVO.class );
		
		//Map the employee details from request.
		if( !Utils.isEmpty( request.getAttribute( "namedEmpList" ) ) ){
			List<WCNammedEmployeeVO> namedEmpList = (com.mindtree.ruc.cmn.utils.List<WCNammedEmployeeVO>) request.getAttribute( "namedEmpList" );
			wc.setWcEmployeeDetails( namedEmpList );
		}
		
		if(request.getParameter( "24HourPACoverHiddenValue" ).equalsIgnoreCase("true")){
			wc.getWcCovers().setPACover(true);
		} else if(request.getParameter( "24HourPACoverHiddenValue" ).equalsIgnoreCase("false")){
			wc.getWcCovers().setPACover(false);
		}
		
		//wc.setPremium( getPremiumVO( request ) );
		
		return wc;
	}

	@Override
	protected void sectionLogic( PolicyContext policyContext ){
		// TODO Auto-generated method stub
		
	}
	
	/* This method is used to identify the rows removed from the UI page and the corresponding
	* table entries to be deleted for these records.The Logic will be specific to each table
	* @return rgd (This will be modified based on whether insertion or deletion
	*/
	@Override
	protected void setRowToBeDeletedFlag( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){

			if( rgd instanceof WCVO ){

				WCVO requestMappedWCVO = (WCVO) rgd;
				// The TravelBaggageVO from context 
				WCVO wcVOFromPolContext = null;
				// for first save section details will be empty 
				wcVOFromPolContext = (WCVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( wcVOFromPolContext ) ){
					return ;
				}
				
				List<WCNammedEmployeeVO> toBeDeletedVOs = new com.mindtree.ruc.cmn.utils.List<WCNammedEmployeeVO>( WCNammedEmployeeVO.class );
				boolean deletionFlag = false;
				for( WCNammedEmployeeVO namedEmployee : ( (WCVO) rgd ).getWcEmployeeDetails() ){
					if( Utils.isEmpty( namedEmployee.getEmpName() ) ){
						toBeDeletedVOs.add( namedEmployee );
						deletionFlag = true;
					}
				}
				if( deletionFlag ){
					for( WCNammedEmployeeVO toBeDeletedVO : toBeDeletedVOs ){

						( (WCVO) rgd ).getWcEmployeeDetails().remove( toBeDeletedVO );
					}
				}
				/* adding to the request the records in context with deletion flag set as these will not be 
				 * available as part of request mapping(in rgd)*/
				/*Modified for Request ID :117466*/
				for( WCNammedEmployeeVO namedEmp : wcVOFromPolContext.getWcEmployeeDetails() ){
					if( !requestMappedWCVO.getWcEmployeeDetails().contains( namedEmp ) ){
						namedEmp.setIsToBeDeleted( true );
						requestMappedWCVO.setIsToBeDeleted( true );
						requestMappedWCVO.getWcEmployeeDetails().add( namedEmp );
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
			if( rgd instanceof WCVO ){
				WCVO wcVOFromPolContext = null;
				boolean deletionflag = false;
				List<WCNammedEmployeeVO> toBeDeletedVOs = new com.mindtree.ruc.cmn.utils.List<WCNammedEmployeeVO>( WCNammedEmployeeVO.class );
				wcVOFromPolContext = (WCVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( wcVOFromPolContext ) ){
					return;
				}
				for( WCNammedEmployeeVO namedEmpVO : wcVOFromPolContext.getWcEmployeeDetails() ){
					if( !Utils.isEmpty( namedEmpVO.getIsToBeDeleted() ) && namedEmpVO.getIsToBeDeleted() ){
						toBeDeletedVOs.add( namedEmpVO );
						deletionflag = true;
					}
				}
				if( deletionflag ){
					for( WCNammedEmployeeVO toBeDeletedVO : toBeDeletedVOs ){

						( (WCVO) rgd ).getWcEmployeeDetails().remove( toBeDeletedVO );
					}
					policyContext.addRiskGroupDetails( currentSection.getSectionId(), rg, rgd );
				}
			}
	}
	
}
