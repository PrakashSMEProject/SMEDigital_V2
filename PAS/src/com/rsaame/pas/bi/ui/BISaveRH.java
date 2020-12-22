/**
 * 
 */
package com.rsaame.pas.bi.ui;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.ui.cmn.SaveSectionRH;
import com.rsaame.pas.vo.bus.BIUWDetailsVO;
import com.rsaame.pas.vo.bus.BIVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

/**
 * @author m1019703
 * Populates BIVO with values from UI and makes a call to the validation engine and service method. 
 */
public class BISaveRH extends SaveSectionRH {

	public BISaveRH() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.ui.cmn.SaveSectionRH#validate(com.rsaame.pas.vo.bus.RiskGroup, com.rsaame.pas.vo.bus.RiskGroupDetails, com.rsaame.pas.vo.bus.SectionVO)
	 */
	private void validate( BIVO biVo, SectionVO sectionVO ){

		TaskExecutor.executeTasks( "BI_PAGE_SAVE_VAL", biVo ); 

	}
	@Override
	protected void validate(RiskGroup rg, RiskGroupDetails rgd,SectionVO section, BaseVO baseVO ) {
		validate( (BIVO) rgd, section );
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.ui.cmn.SaveSectionRH#mapRiskGroup(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected RiskGroup mapRiskGroup(HttpServletRequest request) {
		return BeanMapper.map( request, LocationVO.class );
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.ui.cmn.SaveSectionRH#mapRiskGroupDetails(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected RiskGroupDetails mapRiskGroupDetails(HttpServletRequest request) {
		BIVO biVO = BeanMapper.map( request, BIVO.class );
		BIUWDetailsVO detailsVO = BeanMapper.map( request, BIUWDetailsVO.class );
		UWQuestionsVO questionsVO = BeanMapper.map( request, UWQuestionsVO.class );
		
		biVO.setUwDetails( detailsVO );
		biVO.setUwQuestions( questionsVO );
		setSumInsured(biVO);
		biVO.setPremium( getPremiumVO( request ) );
		return biVO;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.ui.cmn.SaveSectionRH#sectionLogic(com.rsaame.pas.ui.cmn.PolicyContext)
	 */
	@Override
	protected void sectionLogic(PolicyContext policyContext) {
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}
	
	protected void setSumInsured(BIVO bivo)
	{
		Double sumInsured = 0.0;
		if(!Utils.isEmpty(bivo.getRentRecievable()))
		{
			sumInsured = sumInsured + bivo.getRentRecievable();
		}
		if(!Utils.isEmpty(bivo.getWorkingLimit()))
		{
			sumInsured = sumInsured + bivo.getWorkingLimit();
		}
		if(!Utils.isEmpty(bivo.getEstimatedGrossIncome()))
		{
			sumInsured = sumInsured + bivo.getEstimatedGrossIncome();
		}
		//Added for Adventnet Id:103286;To Move BI Section from PAR to BI
		//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7
		/*if(!Utils.isEmpty(bivo.getAnnualRent()))
		{
			sumInsured = sumInsured + bivo.getAnnualRent();
		}*/
		
		bivo.setSumInsured(sumInsured);
	}
}
