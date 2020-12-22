package com.rsaame.pas.motor.ui;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.ui.cmn.SaveSectionRH;
import com.rsaame.pas.vo.bus.EEUWDetailsVO;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MotorFleetVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * 
 * @author m1014438
 *
 */

public class MotorFleetSaveRH extends SaveSectionRH{

	@Override
	protected void validate( RiskGroup rg, RiskGroupDetails rgd, SectionVO section, BaseVO baseVO ){
	validate((MotorFleetVO)rgd);

	}

	private void validate(MotorFleetVO motorFleet) {
		
		TaskExecutor.executeTasks( "MOTORFLEET_PAGE_SAVE_VAL", motorFleet );
		TaskExecutor.executeTasks( "COVERAGE_DETAILS_PAGE_SAVE_VAL", motorFleet );
	}

	@Override
	protected RiskGroup mapRiskGroup( HttpServletRequest request ){
		return BeanMapper.map( request, LocationVO.class );
	}

	@Override
	protected RiskGroupDetails mapRiskGroupDetails( HttpServletRequest request ){
		MotorFleetVO motorFleet = BeanMapper.map( request, MotorFleetVO.class );		
		
		return motorFleet;
	}

	@Override
	protected void sectionLogic( PolicyContext policyContext ){
		// TODO Auto-generated method stub

	}

}
