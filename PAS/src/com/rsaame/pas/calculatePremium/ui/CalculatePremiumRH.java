/**
 * 
 */
package com.rsaame.pas.calculatePremium.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.constants.CommonErrorKeys;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.bus.BIUWDetailsVO;
import com.rsaame.pas.vo.bus.BIVO;
import com.rsaame.pas.vo.bus.CommodityDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockVO;
import com.rsaame.pas.vo.bus.EEUWDetailsVO;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EquipmentVO;
import com.rsaame.pas.vo.bus.FidelityNammedEmployeeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityUnnammedEmployeeVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.GPANammedEmpVO;
import com.rsaame.pas.vo.bus.GPAUnnammedEmpVO;
import com.rsaame.pas.vo.bus.GoodsInTransitVO;
import com.rsaame.pas.vo.bus.GroupPersonalAccidentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MBUWDetailsVO;
import com.rsaame.pas.vo.bus.MBVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.PARUWDetailsVO;
import com.rsaame.pas.vo.bus.PLUWDetails;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.TravelBaggageVO;
import com.rsaame.pas.vo.bus.TravellingEmployeeVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;
import com.rsaame.pas.vo.bus.WCVO;

/**
 * @author M1016284
 *
 */
public class CalculatePremiumRH implements IRequestHandler{

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){

		Response response = new Response();
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		policyContext.startTransaction();

		PolicyVO policyDetails = policyContext.getPolicyDetails();
		int currentSectionId = policyContext.getCurrentSectionId();
		SectionVO sectionVO = policyContext.getSectionDetails( currentSectionId );
		RiskGroup rg = null;
		RiskGroupDetails rgd = null;
		//String identifier = request.getParameter( "opType" );
		String iden = (String) request.getAttribute( "opType" );

		rg = BeanMapper.map( request, LocationVO.class );
		rg.setToSave( true );
		if( currentSectionId == AppConstants.SECTION_ID_PAR ){

			/* mapping all the details into diffrent VOs for PAR page */

			ParVO parVO = BeanMapper.map( request, ParVO.class );
			PARUWDetailsVO detailsVO = BeanMapper.map( request, PARUWDetailsVO.class );
			UWQuestionsVO questionsVO = BeanMapper.map( request, UWQuestionsVO.class );

			parVO.setUwDetails( detailsVO );
			parVO.setUwQuestions( questionsVO );
			rgd = parVO;

			 /*We need to validate all the input because rating engine will need the correct values. */
			TaskExecutor.executeTasks( "PAR_PAGE_LOCATION", (LocationVO) rg );
			TaskExecutor.executeTasks( "PAR_PAGE_UWDETAILS", detailsVO );
			TaskExecutor.executeTasks( "PAR_PAGE_SI_VAL", sectionVO );

		}
		else if( currentSectionId == AppConstants.SECTION_ID_PL ){

			PublicLiabilityVO publicLiabilityVO = BeanMapper.map( request, PublicLiabilityVO.class );
			PLUWDetails detailsVO = BeanMapper.map( request, PLUWDetails.class );
			UWQuestionsVO questionsVO = BeanMapper.map( request, UWQuestionsVO.class );

			publicLiabilityVO.setUwDetails( detailsVO );
			publicLiabilityVO.setUwQuestions( questionsVO );

			rgd = publicLiabilityVO;

			 /*We need to validate all the input because rating engine will need the correct values.*/ 
			TaskExecutor.executeTasks( "PL_PAGE_LOCATION", (LocationVO) rg );
			TaskExecutor.executeTasks( "PL_PAGE_CORE", publicLiabilityVO );
			TaskExecutor.executeTasks( "PL_PAGE_COMMISION", sectionVO );
		}
		else if( currentSectionId == AppConstants.SECTION_ID_WC ){

			WCVO wcVO = BeanMapper.map( request, WCVO.class );
			String pAcoverField = null;
			
				pAcoverField = request.getParameter( "24HourPACoverHiddenValue" );
				
				if(Utils.isEmpty( pAcoverField ) ){
					throw new BusinessException( "pas.wc.24HourPACover",null, "24 Hour pa cover null from form submit" );
				}
				if( pAcoverField.equalsIgnoreCase("true")){
					wcVO.getWcCovers().setPACover(true);
				} else if((!Utils.isEmpty( pAcoverField )) && pAcoverField.equalsIgnoreCase("false")){
	        	  wcVO.getWcCovers().setPACover(false);
				}
			rgd = wcVO;

			 /*We need to validate all the input because rating engine will need the correct values.*/ 
			TaskExecutor.executeTasks( "WC_PAGE_VAL", wcVO );

		}
		else if( currentSectionId == AppConstants.SECTION_ID_MONEY ){
			MoneyVO moneyVO = BeanMapper.map( request, MoneyVO.class );
			
			if( !Utils.isEmpty( policyDetails.getIsPrepackaged() ) && !policyDetails.getIsPrepackaged() &&
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

			rgd = moneyVO;

			 /*We need to validate all the input because rating engine will need the correct values.*/ 
			TaskExecutor.executeTasks( "MONEY_COMMISSION_VAL", sectionVO );
			TaskExecutor.executeTasks( "MONEY_PAGE_SAVE_VAL", moneyVO );
		}
		else if( currentSectionId == AppConstants.SECTION_ID_BI ){

			BIVO biVO = BeanMapper.map( request, BIVO.class );
			BIUWDetailsVO detailsVO = BeanMapper.map( request, BIUWDetailsVO.class );
			UWQuestionsVO questionsVO = BeanMapper.map( request, UWQuestionsVO.class );
			
			biVO.setUwDetails( detailsVO );
			biVO.setUwQuestions( questionsVO );
			setSumInsured(biVO);

			 /*We need to validate all the input because rating engine will need the correct values.*/ 
			TaskExecutor.executeTasks( "BI_PAGE_SAVE_VAL", biVO );
			rgd = biVO;
		}
		else if( currentSectionId == AppConstants.SECTION_ID_MB ){

			MBVO mbvo=BeanMapper.map(request, MBVO.class);
			UWQuestionsVO questionsVO=BeanMapper.map(request, UWQuestionsVO.class);
			MBUWDetailsVO detailsVO=BeanMapper.map(request, MBUWDetailsVO.class);
			mbvo.setUwQuestions(questionsVO);
			mbvo.setUwDetails(detailsVO);
			/*Setting sum insured to MBVO-sumInsured field */
			mbvo=AppUtils.setSumInsured(mbvo);
			
			rgd=mbvo;
			
			 /*We need to validate all the input because rating engine will need the correct values.*/ 
			TaskExecutor.executeTasks( "MB_PAGE_SAVE_VAL", mbvo );
			TaskExecutor.executeTasks( "MB_COMMISSION_VAL", sectionVO );

		}
		else if( currentSectionId == AppConstants.SECTION_ID_EE ){

			EEVO eeVO = BeanMapper.map( request, EEVO.class );
			EEUWDetailsVO detailsVO = BeanMapper.map( request, EEUWDetailsVO.class );
			eeVO.setUwDetails( detailsVO );
			setSumInsuredEE(eeVO);

			 /*We need to validate all the input because rating engine will need the correct values.*/ 
			for (EquipmentVO equipmentVO : eeVO.getEquipmentDtls()){
				TaskExecutor.executeTasks("EE_PAGE", equipmentVO);
				}
			rgd = eeVO;
		}
		else if(currentSectionId == AppConstants.SECTION_ID_TB){
			
			TravelBaggageVO travelVO = BeanMapper.map(  request, TravelBaggageVO.class );
			setSumInsured(travelVO);
			
			/*We need to validate all the input because rating engine will need the correct values.*/ 
			for (TravellingEmployeeVO travellingEmployeeVO : travelVO.getTravellingEmpDets()){
				if(!Utils.isEmpty( travelVO.getTravellingEmpDets().get( 0 ).getSumInsuredDtl().getDeductible())){
					travellingEmployeeVO.getSumInsuredDtl().setDeductible( travelVO.getTravellingEmpDets().get( 0 ).getSumInsuredDtl().getDeductible() );
				}
				TaskExecutor.executeTasks("TB_PAGE_SAVE_VAL", travellingEmployeeVO);
				}
			rgd = travelVO;
		}
		else if(currentSectionId == AppConstants.SECTION_ID_GIT){
			
			GoodsInTransitVO gitTransitVO = BeanMapper.map(  request, GoodsInTransitVO.class );
			
			TaskExecutor.executeTasks( "GOODS_IN_TRANSIT_PAGE_SAVE_VAL", gitTransitVO );

			for(CommodityDetailsVO commodityDetailsVO : gitTransitVO.getCommodityDtls()){
				TaskExecutor.executeTasks( "COMMODITY_DETAILS_PAGE_SAVE_VAL", commodityDetailsVO );
			}
			
			setSumInsured(gitTransitVO);
			
	    	/*TaskExecutor.executeTasks( "GOODS_IN_TRANSIT_PAGE_SAVE_VAL", gitTransitVO );

			for(CommodityDetailsVO commodityDetailsVO : gitTransitVO.getCommodityDtls()){
				TaskExecutor.executeTasks( "COMMODITY_DETAILS_PAGE_SAVE_VAL", commodityDetailsVO );
			}*/

			rgd = gitTransitVO;
		}else if(currentSectionId == AppConstants.SECTION_ID_GROUP_PERSONAL_ACCIDENT){
			
			GroupPersonalAccidentVO groupPersonalAccidentVO = BeanMapper.map(  request, GroupPersonalAccidentVO.class );
			
			TaskExecutor.executeTasks( "GROUP_PERSONAL_ACCIDENT_PAGE", groupPersonalAccidentVO );
			
			setSumInsuredGPA(groupPersonalAccidentVO);
			

			rgd = groupPersonalAccidentVO;
		}else if(currentSectionId == AppConstants.SECTION_ID_FIDELITY){
			FidelityVO fidelityVO = BeanMapper.map(  request, FidelityVO.class );
			
			TaskExecutor.executeTasks( "FIDELITY_PAGE_LOAD", fidelityVO );
			
			setSumInsuredFID(fidelityVO);
			

			rgd = fidelityVO;
		}
		else if(currentSectionId == AppConstants.SECTION_ID_DETERIORATION_OF_STOCK){
			
			DeteriorationOfStockVO deteriorationOfStockVO = BeanMapper.map(  request, DeteriorationOfStockVO.class );
			
			for(DeteriorationOfStockDetailsVO detailsVO : deteriorationOfStockVO.getDeteriorationOfStockDetails()){
				TaskExecutor.executeTasks( "DOS_PAGE", detailsVO );
				}
			
			getSectionLevelSumInsured(deteriorationOfStockVO);
			rgd = deteriorationOfStockVO;
		}

		policyContext.addRiskGroupDetails( currentSectionId, rg, rgd );
		policyDetails = (PolicyVO) TaskExecutor.executeTasks( iden, policyDetails );

		if( !Utils.isEmpty( policyDetails ) && !Utils.isEmpty( policyDetails.getPremiumVO() ) && !Utils.isEmpty( policyDetails.getPremiumVO().getPremiumAmt() ) ){
			response.setSuccess( true );
			response.setData( policyDetails.getPremiumVO() );
		}

		policyContext.endTransaction();
		return response;
	}
	
	private double getSectionLevelSumInsured( DeteriorationOfStockVO stockDetails ){
		return PremiumHelper.getSectionLevelSumInsured( stockDetails );
	} 
	
	private void setSumInsuredFID(FidelityVO fidelityVO) {
		
		double sumInsured=0.0;
		if(!Utils.isEmpty(fidelityVO)){
			
			List<FidelityUnnammedEmployeeVO> unnammedEmployeeDetails =fidelityVO.getUnnammedEmployeeDetails();
			 if(!Utils.isEmpty(unnammedEmployeeDetails)){
				 for(FidelityUnnammedEmployeeVO fidUnnammedEmpVO1 : unnammedEmployeeDetails){
					 if(!Utils.isEmpty(fidUnnammedEmpVO1)){
						 if(!Utils.isEmpty(fidUnnammedEmpVO1.getLimitPerPerson())){
							if(!Utils.isEmpty(fidUnnammedEmpVO1.getLimitPerPerson())) 
								sumInsured+=fidUnnammedEmpVO1.getLimitPerPerson();
						 }
					 }
				 }
			 }
			 List<FidelityNammedEmployeeDetailsVO> fidelityEmployeeDetails =fidelityVO.getFidelityEmployeeDetails();
			 if(!Utils.isEmpty(fidelityEmployeeDetails)){
				 for(FidelityNammedEmployeeDetailsVO fidNammedEmpVO1 : fidelityEmployeeDetails){
					 if(!Utils.isEmpty(fidNammedEmpVO1)){
						 if(!Utils.isEmpty(fidNammedEmpVO1.getLimitPerPerson())){
							if(!Utils.isEmpty(fidNammedEmpVO1.getLimitPerPerson())) 
								sumInsured+=fidNammedEmpVO1.getLimitPerPerson();
						 }
					 }
				 }
			 }
		}
		
		fidelityVO.setSumInsured(sumInsured);
		
	}

	private void setSumInsuredGPA(
			GroupPersonalAccidentVO groupPersonalAccidentVO) {
		
		double sumInsured=0.0;
		if(!Utils.isEmpty(groupPersonalAccidentVO)){
			
			List<GPAUnnammedEmpVO> gpaUnnammedEmpVO =groupPersonalAccidentVO.getGpaUnnammedEmpVO();
			 if(!Utils.isEmpty(gpaUnnammedEmpVO)){
				 for(GPAUnnammedEmpVO gpaUnnammedEmpVO1 : gpaUnnammedEmpVO){
					 if(!Utils.isEmpty(gpaUnnammedEmpVO1)){
						 if(!Utils.isEmpty(gpaUnnammedEmpVO1.getSumInsuredDetails())){
							if(!Utils.isEmpty(gpaUnnammedEmpVO1.getSumInsuredDetails().getSumInsured())) 
								sumInsured+=gpaUnnammedEmpVO1.getSumInsuredDetails().getSumInsured();
						 }
					 }
				 }
			 }
			 List<GPANammedEmpVO> gpaNammedEmpVO =groupPersonalAccidentVO.getGpaNammedEmpVO();
			 if(!Utils.isEmpty(gpaNammedEmpVO)){
				 for(GPANammedEmpVO gpaNammedEmpVO1 : gpaNammedEmpVO){
					 if(!Utils.isEmpty(gpaNammedEmpVO1)){
						 if(!Utils.isEmpty(gpaNammedEmpVO1.getSumInsuredDetails())){
							if(!Utils.isEmpty(gpaNammedEmpVO1.getSumInsuredDetails().getSumInsured())) 
								sumInsured+=gpaNammedEmpVO1.getSumInsuredDetails().getSumInsured();
						 }
					 }
				 }
			 }
		}
		
		groupPersonalAccidentVO.setSumInsured(sumInsured);
		
	}
	private void setSumInsured( GoodsInTransitVO gitTransitVO ){
	
		gitTransitVO.setSumInsured( gitTransitVO.getEstimatedAnnualCarryValue() );
		
	}

	private void setSumInsuredEE(EEVO eeVO) {
		Double sumInsured = 0.0;
	for (EquipmentVO equipmentVO : eeVO.getEquipmentDtls()){
		sumInsured = sumInsured + equipmentVO.getSumInsuredDetails().getSumInsured();
	}
	eeVO.setSumInsured(sumInsured);
	}
	
	private void setSumInsured(TravelBaggageVO travelVO) {
		Double sumInsured = 0.0;
	for (TravellingEmployeeVO travellingEmployeeVO : travelVO.getTravellingEmpDets()){
		sumInsured = sumInsured + travellingEmployeeVO.getSumInsuredDtl().getSumInsured();
	}
	travelVO.setSumInsured(sumInsured);
	}

	private void setSumInsured(BIVO bivo)
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
