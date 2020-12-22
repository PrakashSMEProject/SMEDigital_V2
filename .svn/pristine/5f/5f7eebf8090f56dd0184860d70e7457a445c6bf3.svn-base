package com.rsaame.pas.gpa.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.ui.cmn.SaveSectionRH;
import com.rsaame.pas.vo.bus.GPANammedEmpVO;
import com.rsaame.pas.vo.bus.GPAUnnammedEmpVO;
import com.rsaame.pas.vo.bus.GroupPersonalAccidentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;

public class GroupPersonalAccidentSaveRH extends SaveSectionRH {

public static final Logger LOGGER = Logger.getLogger(GroupPersonalAccidentSaveRH.class);

@Override
protected void validate(RiskGroup rg, RiskGroupDetails rgd,	SectionVO section, BaseVO baseVO) {
LOGGER.info("GroupPersonalAccidentSaveRH : Inside Validator");		
	validate( (GroupPersonalAccidentVO) rgd );
}
		
private void validate( GroupPersonalAccidentVO groupPersonalAccident ){
LOGGER.info("GroupPersonalAccidentSaveRH : Starting validate");		
TaskExecutor.executeTasks("GROUP_PERSONAL_ACCIDENT_PAGE", groupPersonalAccident);
	 for(GPAUnnammedEmpVO  unnamedEmpVO : groupPersonalAccident.getGpaUnnammedEmpVO()){

		TaskExecutor.executeTasks( "GROUP_PERSONAL_ACCIDENT_UNNAMED_PAGE", unnamedEmpVO );
	 }
	 for(GPANammedEmpVO  namedEmpVO : groupPersonalAccident.getGpaNammedEmpVO()){

		TaskExecutor.executeTasks( "GROUP_PERSONAL_ACCIDENT_NAMED_PAGE", namedEmpVO );
	 }
}

@Override
protected RiskGroup mapRiskGroup(HttpServletRequest request) {
		return BeanMapper.map(request, LocationVO.class);
}

@Override
protected RiskGroupDetails mapRiskGroupDetails(HttpServletRequest request) {
	LOGGER.info("GroupPersonalAccidentSaveRH : Inside mapRiskGroupDetails -> Mapping request to VO");
	GroupPersonalAccidentVO groupPersonalAccidentVO = BeanMapper.map(request, GroupPersonalAccidentVO.class);
		groupPersonalAccidentVO = setSumInsured(groupPersonalAccidentVO);
		
		return groupPersonalAccidentVO;
}

private GroupPersonalAccidentVO setSumInsured(GroupPersonalAccidentVO groupPersonalAccidentVO) {
	List<GPAUnnammedEmpVO> gpaUnnammedEmpVO=null;
	List<GPANammedEmpVO> gpaNammedEmpVO=null;
	double sumInsured=0.0;
	if(!Utils.isEmpty(groupPersonalAccidentVO)){
	gpaUnnammedEmpVO =groupPersonalAccidentVO.getGpaUnnammedEmpVO();
	if(!Utils.isEmpty(gpaUnnammedEmpVO)){
		for(GPAUnnammedEmpVO gpaUnnammedEmpVO1 : gpaUnnammedEmpVO){
			  if(!Utils.isEmpty(gpaUnnammedEmpVO1.getSumInsuredDetails())&&(!Utils.isEmpty(gpaUnnammedEmpVO1.getSumInsuredDetails().getSumInsured()))){ 
					sumInsured+=gpaUnnammedEmpVO1.getSumInsuredDetails().getSumInsured();
		      }
		}
	}
	gpaNammedEmpVO =groupPersonalAccidentVO.getGpaNammedEmpVO();
	if(!Utils.isEmpty(gpaNammedEmpVO)){
		for(GPANammedEmpVO gpaNammedEmpVO1 : gpaNammedEmpVO){
		  if(!Utils.isEmpty(gpaNammedEmpVO1.getSumInsuredDetails())&&(!Utils.isEmpty(gpaNammedEmpVO1.getSumInsuredDetails().getSumInsured()))){ 
			sumInsured+=gpaNammedEmpVO1.getSumInsuredDetails().getSumInsured();
		  }
		}
	}
}
groupPersonalAccidentVO.setSumInsured(sumInsured);
return groupPersonalAccidentVO;
}

@Override
protected void sectionLogic(PolicyContext policyContext) {
	//SONARFIX--26-04-2018---DO NOTHING IN METHOD
}
/**
	* @param rg to receive RiskGroup values
	* @param rgd to receive RiskGroupDetails values
	* @param currentSection to receive SectionVO values
	* @param policyContext to receive PolicyContext values
 */
/*protected boolean isDataChanged( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){
Boolean isDataChanged = true;
	try{
		if( rgd instanceof GroupPersonalAccidentVO ){
			GroupPersonalAccidentVO requestMappedGroupPersonalAccidentVO = (GroupPersonalAccidentVO) rgd;
			// The GroupPersonalAccidentVO from context 
			GroupPersonalAccidentVO contextGroupPersonalAccidentVO = null;
			// for first save section details will be empty 
			contextGroupPersonalAccidentVO = (GroupPersonalAccidentVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
			if( Utils.isEmpty( contextGroupPersonalAccidentVO ) ){
				return true;
			}
			// compare requestMapped and context GPAVO 
			if( requestMappedGroupPersonalAccidentVO.toString().equals( contextGroupPersonalAccidentVO.toString() ) ){
				isDataChanged = false;
			}
		}

	}
	catch( Exception e ){
		throw new BusinessException( "cmn.compareError", null, "Error in compare" );
	}

		return isDataChanged;
}*/

	/** This method is used to identify the rows removed from the UI page and the corresponding
	* table entries to be deleted for these records.The Logic will be specific to each table.
	* @param rg to receive RiskGroup values
	* @param rgd to receive RiskGroupDetails values
	* @param currentSection to receive SectionVO values
	* @param policyContext to receive PolicyContext values
	* 
	*/
protected void setRowToBeDeletedFlag( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){
		log.debug( "setRowToBeDeletedFlag in GroupPersonalAccidentVO" );
		try{
											
			if( rgd instanceof GroupPersonalAccidentVO ){

				
				GroupPersonalAccidentVO requestMappedGroupPersonalAccidentVO = (GroupPersonalAccidentVO) rgd;
				// The GroupPersonalAccidentVO from context 
				GroupPersonalAccidentVO contextGroupPersonalAccidentVO = null;
				// for first save section details will be empty 
				contextGroupPersonalAccidentVO = (GroupPersonalAccidentVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( contextGroupPersonalAccidentVO ) ){
					return;
				}
				/* adding to the request the records in context with deletion flag set as these will not be 
					 * available as part of request mapping(in rgd)*/
				for( GPANammedEmpVO gpaNammedEmpVO : contextGroupPersonalAccidentVO.getGpaNammedEmpVO() ){
					if( !requestMappedGroupPersonalAccidentVO.getGpaNammedEmpVO().contains( gpaNammedEmpVO )){
						gpaNammedEmpVO.setIsToBeDeleted( true );
						requestMappedGroupPersonalAccidentVO.setIsToBeDeleted( true );
						requestMappedGroupPersonalAccidentVO.getGpaNammedEmpVO().add( gpaNammedEmpVO );
					}
				}
				
				for( GPAUnnammedEmpVO gpaUnnammedEmpVO : contextGroupPersonalAccidentVO.getGpaUnnammedEmpVO() ){
					if( !requestMappedGroupPersonalAccidentVO.getGpaUnnammedEmpVO().contains( gpaUnnammedEmpVO ) ){
						gpaUnnammedEmpVO.setIsToBeDeleted( true );
						requestMappedGroupPersonalAccidentVO.setIsToBeDeleted( true );
						requestMappedGroupPersonalAccidentVO.getGpaUnnammedEmpVO().add( gpaUnnammedEmpVO );
					}
				}
				


			}
		}
		catch( Exception e ){
			throw new BusinessException( "cmn.compareError", null, "Error in compare" );
		}
	}

	/** This method is used to remove the delted rows from context.
	* @param rg to receive RiskGroup values
	* @param rgd to receive RiskGroupDetails values
	* @param currentSection to receive SectionVO values
	* @param policyContext to receive PolicyContext values
	*/
	protected void removeDeletedRowsFromContext( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){
		try{
			if( Utils.isEmpty( rgd.getIsToBeDeleted() ) ) return;

			if( rgd instanceof GroupPersonalAccidentVO ){
				GroupPersonalAccidentVO contextGroupPersonalAccidentVO = null;
				boolean deletionflagNamed = false;
				boolean deletionflagUnnamed = false;
				ArrayList<GPANammedEmpVO> toBeDeletedVOs = new ArrayList<GPANammedEmpVO>();
				ArrayList<GPAUnnammedEmpVO> toBeDeletedVOs1 = new ArrayList<GPAUnnammedEmpVO>();
				contextGroupPersonalAccidentVO = (GroupPersonalAccidentVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( contextGroupPersonalAccidentVO ) ) return;
				for( GPANammedEmpVO gpaNammedEmpVO : contextGroupPersonalAccidentVO.getGpaNammedEmpVO()){
					if( !Utils.isEmpty( gpaNammedEmpVO.getIsToBeDeleted() ) && gpaNammedEmpVO.getIsToBeDeleted() ){
						toBeDeletedVOs.add( gpaNammedEmpVO );
						deletionflagNamed = true;
					}
				}
				for( GPAUnnammedEmpVO gpaUnnammedEmpVO : contextGroupPersonalAccidentVO.getGpaUnnammedEmpVO()){
					if( !Utils.isEmpty( gpaUnnammedEmpVO.getIsToBeDeleted() ) && gpaUnnammedEmpVO.getIsToBeDeleted() ){
						toBeDeletedVOs1.add( gpaUnnammedEmpVO );
						deletionflagUnnamed = true;
					}
				}
				if( deletionflagNamed ){
					for( GPANammedEmpVO toBeDeletedVO : toBeDeletedVOs ){

						( (GroupPersonalAccidentVO) rgd ).getGpaNammedEmpVO().remove( toBeDeletedVO );
					}
				}
				if( deletionflagUnnamed ){
					for( GPAUnnammedEmpVO toBeDeletedVO1 : toBeDeletedVOs1 ){

						( (GroupPersonalAccidentVO) rgd ).getGpaUnnammedEmpVO().remove( toBeDeletedVO1 );
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
