package com.rsaame.pas.svc.cmn;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.ISectionDAO;
import com.rsaame.pas.dao.cmn.ISectionLoadDAO;
import com.rsaame.pas.vo.app.LoadExistingInputVO;

public class SectionSvc extends BaseService implements ISectionSvc{
	private ISectionDAO sectionDAO;
	
	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;
		if( "deleteLocation".equals( methodName ) ){
 			returnValue = deleteLocation( (BaseVO) args[ 0 ] );
		}
		if( "loadSectionData".equals( methodName ) ){
 			returnValue = loadSectionData( (BaseVO) args[ 0 ] );
		}
		if( "getSectionListForPolicy".equals( methodName ) ){
 			returnValue = getSectionListForPolicy( (BaseVO) args[ 0 ] );
		}
		if( "fetchPolicyInfoFromPolicyNo".equals( methodName ) ){
 			returnValue = fetchPolicyInfoFromPolicyNo((BaseVO) args[ 0 ]);
		}
		if( "fetchPolicyInfoFromPolicyNoHomeTravel".equals( methodName ) ){
			returnValue = fetchPolicyInfoFromPolicyNoHomeTravel((BaseVO) args[ 0 ]);
		}
		if( "fetchQuotationInfoFromQuoteNo".equals( methodName ) ){
 			returnValue = fetchQuotationInfoFromQuoteNo((BaseVO) args[ 0 ]);
		}
		if( "saveSelectedRiskSections".equals( methodName ) ){
 			returnValue = saveSelectedRiskSections((BaseVO) args[ 0 ]);
		}
		if( "getPremiumSummaryDetails".equals( methodName ) ){
 			returnValue = getPremiumSummaryDetails((BaseVO) args[ 0 ]);
		}
		if("deleteSelectedRiskSection".equals(methodName)){
			returnValue = deleteSelectedRiskSection((BaseVO) args[ 0 ]);
		}
		if("updateSplCovers".equals(methodName)){
			returnValue = updateSplCovers((BaseVO) args[ 0 ]);
		}
		if("handleRulesForAllSections".equals(methodName)){
			returnValue = handleRulesForAllSections((BaseVO) args[ 0 ]);
		}
		if("getSavedSectionListForQuote".equals(methodName)){
			returnValue = getSavedSectionListForQuote((BaseVO) args[ 0 ]);
		}
		return returnValue;
	}

	private BaseVO handleRulesForAllSections(BaseVO baseVO) {
		return sectionDAO.handleRulesForAllSections(baseVO);
	}

	private BaseVO updateSplCovers( BaseVO baseVO ){
		return baseVO = sectionDAO.updateSpecialPremium(baseVO);
	}

	@Override
	public BaseVO saveSelectedRiskSections( BaseVO baseVO ){
		return sectionDAO.saveSelectedRiskSections(baseVO);
	}

	@Override
	public BaseVO getSavedSectionListForQuote( BaseVO input ){
		return sectionDAO.getSavedSectionListForQuote( input );
	}
	
	@Override
	public BaseVO deleteSelectedRiskSection( BaseVO baseVO ){
		return sectionDAO.deleteSelectedRiskSection(baseVO);
	}
	
	@Override
	public BaseVO deleteLocation( BaseVO input ){
		
		BaseVO baseVOOutput = sectionDAO.deleteLocation(input);
		return baseVOOutput;
	}

	@Override
	public BaseVO loadSectionData( BaseVO input ){
		String daoBeanName = getSectionDAOBeanName( input );
		ISectionLoadDAO dao = (ISectionLoadDAO) Utils.getBean( daoBeanName );
		return dao.loadExistingData( input );
	}

	@Override
	public BaseVO getSectionListForPolicy( BaseVO input ){
		return sectionDAO.getSectionListForPolicy( input );
	}
	
	@Override
	public BaseVO fetchPolicyInfoFromPolicyNo(BaseVO baseVO){
		return sectionDAO.fetchPolicyInfoFromPolicyNo(baseVO);
	}

	@Override
	public BaseVO fetchPolicyInfoFromPolicyNoHomeTravel(BaseVO baseVO){
		return sectionDAO.fetchPolicyInfoFromPolicyNoHomeTravel(baseVO);
	}
	
	@Override
	public BaseVO fetchQuotationInfoFromQuoteNo(BaseVO baseVO){
		return sectionDAO.fetchQuotationInfoFromQuoteNo(baseVO);
	}
	@Override
	public BaseVO getPremiumSummaryDetails(BaseVO baseVO){
		baseVO = sectionDAO.updateSpecialPremium(baseVO);
		return sectionDAO.getPremiumSummaryDetails(baseVO);
	}

	private String getSectionDAOBeanName( BaseVO input ){
		LoadExistingInputVO inputVO = (LoadExistingInputVO) input;
		String daoBeanKey = Utils.concat( "SECTION_LOAD_DAOBEAN_", inputVO.getSectionId().toString() );

		String beanName = Utils.getSingleValueAppConfig( daoBeanKey );

		if( !inputVO.isQuote() ){
			beanName = beanName + "_POL";
		}

		return beanName;
	}

	public ISectionDAO getSectionDAO(){
		return sectionDAO;
	}

	public void setSectionDAO( ISectionDAO sectionDAO ){
		this.sectionDAO = sectionDAO;
	}
}
