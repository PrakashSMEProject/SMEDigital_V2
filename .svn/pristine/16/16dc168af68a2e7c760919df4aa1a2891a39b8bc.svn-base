/**
 * 
 */
package com.rsaame.pas.money.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.constants.CommonErrorKeys;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.ui.cmn.SaveSectionRH;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.bus.CashResidenceVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SafeVO;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * @author m1014644
 *
 */
public class MoneySaveRH extends SaveSectionRH{
	private void validate( MoneyVO moneyVO, SectionVO sectionVO ){

		TaskExecutor.executeTasks( "MONEY_COMMISSION_VAL", sectionVO );
		TaskExecutor.executeTasks( "MONEY_PAGE_SAVE_VAL", moneyVO );

	}

	@Override
	protected void validate( RiskGroup rg, RiskGroupDetails rgd, SectionVO section,BaseVO baseVo ){
		validate( (MoneyVO) rgd, section );
	}

	@Override
	protected RiskGroup mapRiskGroup( HttpServletRequest request ){
		return BeanMapper.map( request, LocationVO.class );
	}

	@Override
	protected RiskGroupDetails mapRiskGroupDetails( HttpServletRequest request ){
		
		MoneyVO moneyVO = BeanMapper.map( request, MoneyVO.class );

	//	moneyVO.setPremium( getPremiumVO( request ) );
		
		/* Now, we need to set the risk type code, risk category code and risk sub-category code for each
		 * of the cash contents. These are configured in appconfig.propeties against keys in the format
		 * "MONEY_RISK_TYPES_#" where # is the count of the content in the order of appearance on the
		 * UI. */
		PolicyVO policy = PolicyContextUtil.getPolicyContext( request ).getPolicyDetails();
		if( !Utils.isEmpty( policy.getIsPrepackaged() ) && !policy.getIsPrepackaged() &&
			!Utils.isEmpty( moneyVO.getContentsList() ) )
		{
			List<Contents> contents = moneyVO.getContentsList();
			for( int i = 1; i <= contents.size(); i++ ){
				String riskType = "MONEY_RISK_TYPES_" + i;
				
				String[] riskCodes = Utils.getMultiValueAppConfig( riskType, "-" );
				if( Utils.isEmpty( riskCodes ) || riskCodes.length < 3 ){
					throw new BusinessException( CommonErrorKeys.INVALID_CONFIGURATION, null, "Code configuration for cash content not found" );
				}
				
				Contents content = contents.get( i - 1 );
				
				content.setRiskType( Integer.valueOf( riskCodes[ 0 ] ) );
				content.setRiskCat( Integer.valueOf( riskCodes[ 1 ] ) );
				content.setRiskSubCat( Integer.valueOf( riskCodes[ 2 ] ) );
			}
		}
		
		return moneyVO;
	}

	@Override
	protected void sectionLogic( PolicyContext policyContext ){
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void setRowToBeDeletedFlag( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){
		if(rgd instanceof MoneyVO){
			MoneyVO requestMappedMoneyVO = (MoneyVO) rgd;
			MoneyVO contextMoneyVo = (MoneyVO) PolicyUtils.getRiskGroupDetails( rg, currentSection );
			if(com.mindtree.ruc.cmn.utils.Utils.isEmpty( contextMoneyVo )){
				
				return;
			}
		
			for( CashResidenceVO cashDetailsVo : ( (MoneyVO) contextMoneyVo ).getCashResDetails() ){
				if(!requestMappedMoneyVO.getCashResDetails().contains( cashDetailsVo )){
					cashDetailsVo.setToBeDeleted( true );
					requestMappedMoneyVO.setIsToBeDeleted( true );
						requestMappedMoneyVO.getCashResDetails().add( cashDetailsVo );
						}
			}
			for( SafeVO safeDetailsVo : ( (MoneyVO) contextMoneyVo ).getSafeDetails() ){
				if(!requestMappedMoneyVO.getSafeDetails().contains( safeDetailsVo )){
					safeDetailsVo.setToBeDeleted( true );
					requestMappedMoneyVO.setIsToBeDeleted( true );
						requestMappedMoneyVO.getSafeDetails().add( safeDetailsVo );
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
			if( rgd instanceof MoneyVO ){
				MoneyVO contextMoneyVO = null;
				boolean deletionflag = false;
				ArrayList<CashResidenceVO> toBeDeletedCIRVOs = new ArrayList<CashResidenceVO>();
				ArrayList<SafeVO> toBeDeletedSafeVOs = new ArrayList<SafeVO>();
				contextMoneyVO = (MoneyVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( rg, currentSection );
				if( Utils.isEmpty( contextMoneyVO ) ){
					return;
				}
				for( CashResidenceVO crVO : contextMoneyVO.getCashResDetails() ){
					if(!Utils.isEmpty( contextMoneyVO.getCashResDetails() )){
					if( !Utils.isEmpty( crVO.isToBeDeleted() ) && crVO.isToBeDeleted() ){
						toBeDeletedCIRVOs.add( crVO );
						deletionflag = true;
					}
					}
				}
				for( SafeVO safeVO : contextMoneyVO.getSafeDetails() ){
					if(!Utils.isEmpty( contextMoneyVO.getSafeDetails())){
					if( !Utils.isEmpty( safeVO.isToBeDeleted() ) && safeVO.isToBeDeleted() ){
						toBeDeletedSafeVOs.add( safeVO );
						deletionflag = true;
					}
					}
				}

				if( deletionflag ){
					for( CashResidenceVO toBeDeletedVO : toBeDeletedCIRVOs ){

						( (MoneyVO) rgd ).getCashResDetails().remove( toBeDeletedVO );
					}
					for( SafeVO toBeDeletedSafeVO : toBeDeletedSafeVOs ){

						( (MoneyVO) rgd ).getSafeDetails().remove( toBeDeletedSafeVO );
					}
					policyContext.addRiskGroupDetails( currentSection.getSectionId(), rg, rgd );
				}
			}
	}
}
