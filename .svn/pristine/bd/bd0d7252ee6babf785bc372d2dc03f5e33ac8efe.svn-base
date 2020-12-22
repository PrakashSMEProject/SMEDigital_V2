package com.rsaame.pas.wc.ui;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.List;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.LoadSectionRH;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.WCVO;

public class WCLoadRH extends LoadSectionRH implements IRequestHandler{
	private final static Logger LOGGER = Logger.getLogger( WCLoadRH.class );
	public static final String[] FGB_SCHEME_CODE = Utils.getMultiValueAppConfig( "FGB_SCHEME_CODE" );

	@Override
	protected int getSectionClassCode( Integer sectionId ){
		return Integer.valueOf( Utils.getSingleValueAppConfig( Utils.concat( "SEC_", String.valueOf( sectionId ) ) ) );
	}

	@Override
	protected RiskGroup setSectionLevelRiskGroupDetailsToRequest( HttpServletRequest request, PolicyContext policyContext ){

		LocationVO locationVO = null;
		SectionVO sectionVO = policyContext.getSectionDetails( AppConstants.SECTION_ID_WC );
		java.util.Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = sectionVO.getRiskGroupDetails();
		if( !Utils.isEmpty( riskGroupDetails ) ){
			for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : riskGroupDetails.entrySet() ){

				locationVO = (LocationVO) locationEntry.getKey();

				if( Utils.isEmpty( locationVO ) ){
					continue;
				}
				else{
					break;
				}
			}
		}
		return locationVO;
	}

	@Override
	protected void setContentsListToRequest( HttpServletRequest request, PolicyContext policyContext ){
		
		LOGGER.info( "Executing serivce to fetch contents list from VMasPasFetchBasicDtls fo WC section" );
		PolicyVO policyVO = policyContext.getPolicyDetails();
		/* Fetch the contents from configuration table and set the list of contents to render the WC contents page for multiple contents.*/
		BaseVO baseVO = TaskExecutor.executeTasks( request.getAttribute( AppConstants.OPERATIONTYPE ).toString(), policyVO );
		@SuppressWarnings( "unchecked" )
		DataHolderVO<List<EmpTypeDetailsVO>> empTypeDets = (DataHolderVO<List<EmpTypeDetailsVO>>) baseVO;
		request.setAttribute( "wcEmpTypeDetsList", empTypeDets );
		LOGGER.info( "Completed serivce to fetch contents list from VMasPasFetchBasicDtls fo WC section" );
		/*
		 * 81123 code to make the employee type dropdown as editable
		 * for JAFZA scheme
		 */
		Boolean combinedTariff=false;
		if(!Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){
		/*	if(policyVO.getScheme().getTariffCode().toString().equalsIgnoreCase(Utils.getSingleValueAppConfig(AppConstants.COMBINED_TARIFF_CODE)));
			combinedTariff=true; */
			combinedTariff=SvcUtils.isCombinedTariff(policyVO.getScheme().getTariffCode().toString());
			
			
		}
		
		if( (!Utils.isEmpty( policyVO.getIsPrepackaged() ) && (!policyVO.getIsPrepackaged())) || (combinedTariff ) ){
			
			if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){	
				request.setAttribute(com.Constant.CONST_EMPTYPELEVEL1, policyContext.getPolicyDetails().getGeneralInfo().getInsured().getBusType().toString() ) ;
			} else {
				request.setAttribute(com.Constant.CONST_EMPTYPELEVEL1,  policyVO.getScheme().getTariffCode().toString());
			}
			System.out.println("---------------------check request attribute for fgb tariff------------"+request.getAttribute("empTypeLevel1"));
			request.setAttribute("empTypeLevel2",  AppConstants.LOOKUP_LEVEL2 );
		}
		if( !Utils.isEmpty( policyVO.getIsPrepackaged() ) && policyVO.getIsPrepackaged() && !combinedTariff){
			java.util.List<RiskGroup> riskGroups =(java.util.List<RiskGroup>)policyContext.getRiskGroups( AppConstants.SECTION_ID_WC );
			for(RiskGroup riskGroup : riskGroups){
				WCVO wcVo = (WCVO)policyContext.getRiskGroupDetails( AppConstants.SECTION_ID_WC ,riskGroup);
				short code = 0;
				if(isPolicyFGB(policyVO))
				{
					code = SvcUtils.getLookUpCodeForLOneLTwo( SvcConstants.WC_EMP_TYPE_LKP_CATEGORY,policyVO.getScheme().getTariffCode().toString(),SvcConstants.LOOKUP_LEVEL2);
				}
				else
				{
					code = SvcUtils.getLookUpCodeForLOneLTwo( SvcConstants.WC_EMP_TYPE_LKP_CATEGORY,policyVO.getGeneralInfo().getInsured().getBusType().toString(),SvcConstants.LOOKUP_LEVEL2);
				}
				/* In case of change in business type in General Info page during edit quote in pre-pack only,
				 * setting the type of emp to updated emp type in rgd.As the same is not set in rgd in LoadDao 
				 */
				if(!Utils.isEmpty( wcVo )&& !Utils.isEmpty(wcVo.getEmpTypeDetails() )){
					for(int i=0;i<wcVo.getEmpTypeDetails().size();i++){
						if(!Utils.isEmpty( wcVo.getEmpTypeDetails().get( i ) )){
							wcVo.getEmpTypeDetails().get( i ).setEmpType(Integer.valueOf( code )); // Radar fix
						}					
					}						
				}
			}
			
		}
		
		//To set value for number of employees.
		if( !Utils.isEmpty( policyVO ) ){
			java.util.List<RiskGroup> riskGroups =(java.util.List<RiskGroup>)policyContext.getRiskGroups( AppConstants.SECTION_ID_WC );
			for(RiskGroup riskGroup : riskGroups){
				WCVO wcVo = (WCVO)policyContext.getRiskGroupDetails( AppConstants.SECTION_ID_WC ,riskGroup);
				if( !Utils.isEmpty( wcVo ) && !Utils.isEmpty( wcVo.getWcEmployeeDetails() ) ){
					request.setAttribute( "empCount",  wcVo.getWcEmployeeDetails().size() );
				}
			}
		}
		
	}

	private boolean isPolicyFGB(PolicyVO polVO)
	{
		boolean isPolicyFGB = false;
		
		if(!Utils.isEmpty(polVO.getScheme()))
		{
			for(int i = 0; i<FGB_SCHEME_CODE.length;i++)
			{
				if(polVO.getScheme().getSchemeCode().toString().equals(FGB_SCHEME_CODE[i]))
				{
					isPolicyFGB = true;
					break;
				}
			}
		}
		
		return isPolicyFGB;
	}
	@Override
	protected void getDefaultValues(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}
}
