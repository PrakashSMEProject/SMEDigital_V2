package com.rsaame.pas.mb.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.ui.cmn.SaveSectionRH;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MBUWDetailsVO;
import com.rsaame.pas.vo.bus.MBVO;
import com.rsaame.pas.vo.bus.MachineDetailsVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

/**
 * 
 * @author M1017029
 *
 * Populates WCVO with values from UI and makes a call to the validation engine and service method. 
 */

public class MBSaveRH extends SaveSectionRH{
	private static final String MB_PAGE_SAVE_VAL = "MB_PAGE_SAVE_VAL";
	private static final String MB_BASIC_RISK_CODE = "MB_BASIC_RISK_CODE";
	private static final String MB_CLASS = "MB_CLASS";
	private static final String MB_SECTION = "MB_SECTION";
	private static final String MB_RISK_TYPE = "MB_RISK_TYPE";
	private static final String MB_CONTENT_RISK = "MB_CONTENT_RISK";
	private static final String MB_RISK_DTL = "MB_RISK_DTL";

	private void validate( MBVO mb ){

		for( MachineDetailsVO machineDetails : mb.getMachineryDetails() ){

			TaskExecutor.executeTasks( MB_PAGE_SAVE_VAL, machineDetails );
		}
	}

	@Override
	protected void validate( RiskGroup rg, RiskGroupDetails rgd, SectionVO section, BaseVO baseVO ){
		validate( (MBVO) rgd );
	}

	@Override
	protected RiskGroup mapRiskGroup( HttpServletRequest request ){
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request ) ;
		PolicyVO policyVO = policyContext.getPolicyDetails();
		LookUpListVO listVO = SvcUtils.getLookUpCodesList( "MACHINERY_TYPE",policyVO.getScheme().getSchemeCode().toString(),policyVO.getScheme().getTariffCode().toString() );
		if(!Utils.isEmpty( listVO ) &&  !Utils.isEmpty( listVO.getLookUpList() )){
			java.util.List<LookUpVO> list = listVO.getLookUpList();
			request.setAttribute( "machineryType",list );
		}
		
	
		return BeanMapper.map( request, LocationVO.class );
	}

	/**
	 * This method does the mapping from request to corresponding POJOs
	 */
	@Override
	protected RiskGroupDetails mapRiskGroupDetails( HttpServletRequest request ){
		MBVO mbvo = BeanMapper.map( request, MBVO.class );
		UWQuestionsVO questionsVO = BeanMapper.map( request, UWQuestionsVO.class );
		MBUWDetailsVO detailsVO = BeanMapper.map( request, MBUWDetailsVO.class );
		mbvo.setUwQuestions( questionsVO );
		mbvo.setUwDetails( detailsVO );

		for( MachineDetailsVO machineDetailsVO : mbvo.getMachineryDetails() ){
			machineDetailsVO.setContents( setContentValuesForMB( machineDetailsVO.getContents() ) );
		}

		mbvo = setSumInsured( mbvo );
		return mbvo;
	}

	/**
	 * This method is used to set the default values for contents for MB section
	 * @param contents 
	 * @return Contents
	 * @param 
	 */
	private Contents setContentValuesForMB( Contents contents ){

		contents.setBasicRiskCode( new Integer( Utils.getSingleValueAppConfig( MB_BASIC_RISK_CODE ) ) );
		contents.setClassCode( new Short( Utils.getSingleValueAppConfig( MB_CLASS ) ) );
		contents.setSecId( new Short( Utils.getSingleValueAppConfig( MB_SECTION ) ) );
		contents.setRiskType( new Integer( Utils.getSingleValueAppConfig( MB_RISK_TYPE ) ) );
		contents.setRiskCode( Integer.valueOf( Utils.getSingleValueAppConfig( MB_CONTENT_RISK ) ) );
		contents.setRiskDtl( Long.valueOf( Integer.valueOf( Utils.getSingleValueAppConfig( MB_RISK_DTL ) ) ) );

		return contents;
	}

	/**
	 * This method is used to set the consolidate sum insured to MBVO . 
	 * @param mbvo
	 * @return
	 */
	private MBVO setSumInsured( MBVO mbvo ){
		List<MachineDetailsVO> machineList = null;
		double sumInsured = 0.0;
		if( !Utils.isEmpty( mbvo ) ){
			machineList = mbvo.getMachineryDetails();
			if( !Utils.isEmpty( machineList ) ){
				for( MachineDetailsVO detailsVO : machineList ){
					if( !Utils.isEmpty( detailsVO ) ){
						if( !Utils.isEmpty( detailsVO.getSumInsuredVO() ) ){
							if( !Utils.isEmpty( detailsVO.getSumInsuredVO().getSumInsured() ) ) sumInsured += detailsVO.getSumInsuredVO().getSumInsured();
						}
					}
				}
			}
		}
		mbvo.setSumInsured( sumInsured );
		return mbvo;
	}

	@Override
	protected void sectionLogic( PolicyContext policyContext ){
		// TODO Auto-generated method stub

	}

	/**
	 * This method check if the data (whatever entered already) is changed or not . 
	 */
	protected boolean isDataChanged( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){
		Boolean isDataChanged = true;
		/*try{
			if( rgd instanceof MBVO ){

				MBVO requestMappedMBVO = (MBVO) rgd;
				// The MBVO from context 
				MBVO contextMBVO = null;
				// for first save section details will be empty 
				contextMBVO = (MBVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( contextMBVO ) ){
					return true;
				}
				// compare requestMapped and context MBVO 
				if( requestMappedMBVO.toString().equals( contextMBVO.toString() ) ){
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
		log.debug( "setRowToBeDeletedFlag in MB" );
		try{
			if( rgd instanceof MBVO ){

				MBVO requestMappedMBVO = (MBVO) rgd;
				// The MBVO from context 
				MBVO contextMBVO = null;
				// for first save section details will be empty 
				contextMBVO = (MBVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( contextMBVO ) ){
					return;
				}
				/* adding to the request the records in context with deletion flag set as these will not be 
					 * available as part of request mapping(in rgd)*/

				for( MachineDetailsVO machineDetailsVO : contextMBVO.getMachineryDetails() ){
					if( !requestMappedMBVO.getMachineryDetails().contains( machineDetailsVO ) ){
						machineDetailsVO.setIsToBeDeleted( true );
						requestMappedMBVO.setIsToBeDeleted( true );
						requestMappedMBVO.getMachineryDetails().add( machineDetailsVO );
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

			if( rgd instanceof MBVO ){
				MBVO contextMBVO = null;
				boolean deletionflag = false;
				ArrayList<MachineDetailsVO> toBeDeletedVOs = new ArrayList<MachineDetailsVO>();
				contextMBVO = (MBVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( contextMBVO ) ) return;
				for( MachineDetailsVO machineDetailsVO : contextMBVO.getMachineryDetails() ){
					if( !Utils.isEmpty( machineDetailsVO.getIsToBeDeleted() ) && machineDetailsVO.getIsToBeDeleted() ){
						toBeDeletedVOs.add( machineDetailsVO );
						deletionflag = true;
					}
				}
				//Deleting the removed rows MachineryDetails list
				if( deletionflag ){
					for( MachineDetailsVO toBeDeletedVO : toBeDeletedVOs ){

						( (MBVO) rgd ).getMachineryDetails().remove( toBeDeletedVO );
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
