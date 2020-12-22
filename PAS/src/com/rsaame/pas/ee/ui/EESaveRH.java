package com.rsaame.pas.ee.ui;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.ui.cmn.SaveSectionRH;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.EEUWDetailsVO;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EquipmentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;

public class EESaveRH extends SaveSectionRH{
	public static final Logger log = Logger.getLogger( EESaveRH.class );

	@Override
	protected void validate( RiskGroup rg, RiskGroupDetails rgd, SectionVO section, BaseVO baseVO ){
		validate( (EEVO) rgd );

	}

	/**
	 * Call the Validator to validate all mandatory fields
	 * @param eeVO
	 */
	private void validate( EEVO eeVO ){
		for( EquipmentVO equipmentVO : eeVO.getEquipmentDtls() ){
			TaskExecutor.executeTasks( "EE_PAGE", equipmentVO );
		}
	}

	@Override
	protected RiskGroup mapRiskGroup( HttpServletRequest request ){
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request ) ;
		PolicyVO policyVO = policyContext.getPolicyDetails();
		LookUpListVO listVO = SvcUtils.getLookUpCodesList( "ELECTRONIC_EQUIPMENT",policyVO.getScheme().getSchemeCode().toString(),policyVO.getScheme().getTariffCode().toString() );
		
		if(!com.mindtree.ruc.cmn.utils.Utils.isEmpty( listVO ) &&  !com.mindtree.ruc.cmn.utils.Utils.isEmpty( listVO.getLookUpList() )){
			java.util.List<LookUpVO> list = listVO.getLookUpList();
			request.setAttribute( "equipmentType",list );
		}
		return BeanMapper.map( request, LocationVO.class );
	}

	/**
	 * Populates all required object from the request
	 */
	@Override
	protected RiskGroupDetails mapRiskGroupDetails( HttpServletRequest request ){
		EEVO eevo = BeanMapper.map( request, EEVO.class );
		EEUWDetailsVO eeuwDetailsVO = BeanMapper.map( request, EEUWDetailsVO.class );
		eevo.setUwDetails( eeuwDetailsVO );
		return eevo;
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
			if( rgd instanceof EEVO ){

				EEVO requestMappedEEVO = (EEVO) rgd;
				// The EEVO from context 
				EEVO contextEEVO = null;
				// for first save section details will be empty 
				contextEEVO = (EEVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( contextEEVO ) ){
					return true;
				}
				// compare requestMapped and context EEVO 
				if( requestMappedEEVO.toString().equals( contextEEVO.toString() ) ){
					isDataChanged = false;
				}
			}

		}
		catch( Exception e ){
			throw new BusinessException( "cmn.compareError", null, "Error in compare" );
		}
*/
		return isDataChanged;
	}

	/* This method is used to identify the rows removed from the UI page and the corresponding
	* table entries to be deleted for these records.The Logic will be specific to each table
	* 
	* @return rgd (This will be modified based on whether insertion or deletion
	*/

	protected void setRowToBeDeletedFlag( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){
		log.debug( "setRowToBeDeletedFlag in EE" );
		try{
			if( rgd instanceof EEVO ){

				EEVO requestMappedEEVO = (EEVO) rgd;
				// The EEVO from context 
				EEVO contextEEVO = null;
				// for first save section details will be empty 
				contextEEVO = (EEVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( contextEEVO ) ){
					return;
				}
				/* adding to the request the records in context with deletion flag set as these will not be 
					 * available as part of request mapping(in rgd)*/

				for( EquipmentVO equipmentVO : contextEEVO.getEquipmentDtls() ){
					if( !requestMappedEEVO.getEquipmentDtls().contains( equipmentVO ) ){
						equipmentVO.setIsToBeDeleted( true );
						requestMappedEEVO.setIsToBeDeleted( true );
						requestMappedEEVO.getEquipmentDtls().add( equipmentVO );
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

			if( rgd instanceof EEVO ){
				EEVO contextEEVO = null;
				boolean deletionflag = false;
				ArrayList<EquipmentVO> toBeDeletedVOs = new ArrayList<EquipmentVO>();
				contextEEVO = (EEVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( contextEEVO ) ) return;
				for( EquipmentVO equipmentVO : contextEEVO.getEquipmentDtls() ){
					if( !Utils.isEmpty( equipmentVO.getIsToBeDeleted() ) && equipmentVO.getIsToBeDeleted() ){
						toBeDeletedVOs.add( equipmentVO );
						deletionflag = true;
					}
				}
				if( deletionflag ){
					for( EquipmentVO toBeDeletedVO : toBeDeletedVOs ){

						( (EEVO) rgd ).getEquipmentDtls().remove( toBeDeletedVO );
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
