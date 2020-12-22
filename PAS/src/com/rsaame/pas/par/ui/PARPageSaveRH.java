package com.rsaame.pas.par.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.kaizen.vo.PASServiceContext;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.ui.cmn.SaveSectionRH;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PARUWDetailsVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.UWDetails;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

/**
 * @author m1014644
 *
 */
public class PARPageSaveRH extends SaveSectionRH{
	public static final Logger log = Logger.getLogger( PARPageSaveRH.class );
	
	private void validate( RiskGroup rg, ParVO parVO, UWDetails uwDetails, UWQuestionsVO questionsVO, SectionVO sectionVO ,PolicyVO policyVO ){
		/* TODO Finish all validations. */
		TaskExecutor.executeTasks( "PAR_PAGE_LOCATION", (LocationVO) rg );
		//Its removed as there is new class to add
		//TaskExecutor.executeTasks("PAR_PAGE_PAR_SAVE", parVO);
		TaskExecutor.executeTasks( "PAR_PAGE_UWDETAILS", uwDetails );
		//Since UW Questions is radio button validation not required 
		//TaskExecutor.executeTasks("PAR_PAGE_UWQUESTIONS", questionsVO);
		TaskExecutor.executeTasks("PAR_PAGE_SI_VAL", sectionVO);
		
		if( !AppUtils.isPropertyDisableForLocation( "SI_PER_LOC_VALIDATION_DISABLED_LOCNS", PASServiceContext.getLocation() ) ){
			validatesumInsuredPerLoc(parVO,policyVO);
		}
		
	}
	
	@Override
	protected void validate( RiskGroup rg, RiskGroupDetails rgd, SectionVO section,BaseVO baseVO ){
		ParVO parVO = (ParVO) rgd;
		PolicyVO policyVO = (PolicyVO)baseVO;
		validate( rg, parVO, parVO.getUwDetails(), parVO.getUwQuestions(), section, policyVO );
	}

	/**
	 * @param parVO
	 * @param policyVO
	 */
	private void validatesumInsuredPerLoc(ParVO parVO , PolicyVO policyVO) {
		
		Long sumInsuredPerLocGI = policyVO.getGeneralInfo().getInsured().getTurnover();
		Double sumInsuredPerLocPAR = null;
		
		if(!Utils.isEmpty( parVO ) && !Utils.isEmpty(parVO.getCovers()) && !Utils.isEmpty(parVO.getCovers().getPropertyCoversDetails()))
		{
			java.util.List<PropertyRiskDetails> propertyCoversDetails = parVO.getCovers().getPropertyCoversDetails();
			sumInsuredPerLocPAR = 0.0;
			for(PropertyRiskDetails propertyRiskDetails: propertyCoversDetails){
				if(!Utils.isEmpty(propertyRiskDetails.getCover()))
				{
					sumInsuredPerLocPAR += propertyRiskDetails.getCover();
				}						
			}
		}
		
		java.util.List<Double> sumInsuredList = new  ArrayList<Double>();
		
		sumInsuredList.add(sumInsuredPerLocGI.doubleValue());
		sumInsuredList.add(sumInsuredPerLocPAR);
		
		DataHolderVO<List<Double>> dataHolderVO = new DataHolderVO();
		
		dataHolderVO.setData(sumInsuredList);
		TaskExecutor.executeTasks("PAR_PAGE_MAXSI_PERLOC_VAL", dataHolderVO);
		
	}

	@Override
	protected RiskGroup mapRiskGroup( HttpServletRequest request ){
		return BeanMapper.map( request, LocationVO.class );
	}

	@Override
	protected RiskGroupDetails mapRiskGroupDetails( HttpServletRequest request ){
		ParVO parVO = BeanMapper.map( request, ParVO.class );
		PARUWDetailsVO detailsVO = BeanMapper.map( request, PARUWDetailsVO.class );
		UWQuestionsVO questionsVO = BeanMapper.map( request, UWQuestionsVO.class );
		
		parVO.setUwDetails( detailsVO );
		parVO.setUwQuestions( questionsVO );
		
	//	parVO.setPremium( getPremiumVO( request ) );
		return parVO;
	}

	@Override
	protected void sectionLogic( PolicyContext policyContext ){
		
		/*PolicyVO policyVO = policyContext.getPolicyDetails();
		UWQuestionsVO uwQuestionsVO = new UWQuestionsVO();
		
		SectionVO parSectionVO = com.rsaame.pas.svc.utils.PolicyUtils.getSectionVO( policyVO, Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) ) );
		RiskGroup currRG = com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupForProcessing( parSectionVO );
		ParVO currRGD = (ParVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( currRG, parSectionVO );
		java.util.Map<? extends RiskGroup, ? extends RiskGroupDetails> rgdMap = parSectionVO.getRiskGroupDetails();
		
		UWQuestionsVO currentUWQ = currRGD.getUwQuestions();
		
		if(currentUWQ.isCascaded()){
		   for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : rgdMap.entrySet() ){
				ParVO parVO = (ParVO) locationEntry.getValue();
				if( !locationEntry.getKey().equals( (LocationVO) currRG )){
					if( !Utils.isEmpty( parVO )){
						uwQuestionsVO  = CopyUtils.copySerializableObject( currentUWQ );
						uwQuestionsVO.setCascaded( false );
						parVO.setUwQuestions( uwQuestionsVO );
					}
					
				}
			}
		}*/
	}
	
	/*private UWQuestionsVO getCurrentUWQ(SectionVO parSection){
		java.util.Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = parSection.getRiskGroupDetails();
		
		for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : riskGroupDetails.entrySet() ){
					ParVO parVO = (ParVO) locationEntry.getValue();
					UWQuestionsVO questionsVO = parVO.getUwQuestions();
					if( questionsVO.isCascaded()){
						return questionsVO;
					}
		}
		return null;
	}*/
}
