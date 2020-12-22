/**
 * Class for implementing the logic of Risk Cover tag. 
 * Risk cover tag helps in generating dynamic risk cover page based on the 
 * configuration done in scheme-tariff table in DB.
 * Created in Phase 3 for Home Insurance
 */
package com.rsaame.pas.taglib.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.taglib.helper.RiskCoverTagRenderer;
import com.rsaame.pas.taglib.svc.LoadCoverSvc;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.BuildingDetailsVO;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author Sarath Varier
 * @since Phase 3
 *
 */
public class RiskCoverTagHandler extends SimpleTagSupport{

	//IRiskCoverTagSvc riskCoverTagSvc;

	private String scheme;
	private String tariff;
	private String coverCode;
	private String policyType;
	private String excludeCoverCode;
	private Long transactionNo;
	private String policyId;
	private String endtId;
	

	private static final Logger LOGGER = Logger.getLogger( RiskCoverTagHandler.class );

	/*
	 * 
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 * 
	 */
	public void doTag() throws IOException{

		LOGGER.info( "Start creating risk cover tag" );

		Object coverDetails = null;
		HashMap<String, Object> inputDto = null;
		LoadCoverSvc loadCoverSvc = null;
		RiskCoverTagRenderer renderer = null;
		PageContext pageContext = null;
		HttpServletRequest request = null;
		CommonVO commonVO = null;
		SchemeVO schemeVO = null;
		List<CoverDetailsVO> covers = null;
		Short ownerShipStatus = null;

		try{
			inputDto = new HashMap<String, Object>();
			schemeVO = new SchemeVO();
			pageContext = (PageContext) getJspContext();
			request = (HttpServletRequest) pageContext.getRequest();
			
			commonVO = PolicyContextUtil.getPolicyContext(request).getCommonDetails();
			UserProfile userProfile = AppUtils.getUserDetailsFromSession( request );
			
			covers = getCoverDetails(request);
			if (!Utils.isEmpty( (BuildingDetailsVO) request.getAttribute( AppConstants.BUILDING ) )){
				ownerShipStatus = ((BuildingDetailsVO) request.getAttribute( AppConstants.BUILDING )).getOwnershipStatus();	
			}
			
			if(!Utils.isEmpty( commonVO.getIsQuote() ) ){
				if(commonVO.getIsQuote()){
					
					loadCoverSvc = (LoadCoverSvc) Utils.getBean( "loadCoverSvc" );
					
				}else{
					
					loadCoverSvc = (LoadCoverSvc) Utils.getBean( "loadCoverSvc_POL" );
				}
			}
			
			renderer = (RiskCoverTagRenderer) Utils.getBean( "riskCoverTagRendererBean" );
			inputDto.put( com.Constant.CONST_SCHEME, scheme );
			inputDto.put( com.Constant.CONST_TARIFF, tariff );
			inputDto.put( "policyType", policyType );
			inputDto.put( "coverCode", coverCode );
			inputDto.put( "excludeCoverCode", excludeCoverCode );
			//Home Revamp requirement 1.1
			inputDto.put( "transactionNo", transactionNo );
			inputDto.put( "policyId", policyId );
			inputDto.put( "endtId", endtId );
			LOGGER.info( "--------" + inputDto.toString() );

			if( Utils.isEmpty( scheme ) && Utils.isEmpty( tariff ) ){
				schemeVO = (SchemeVO) loadCoverSvc.invokeMethod( "getSchemeDetails", commonVO );
				inputDto.put( com.Constant.CONST_SCHEME, schemeVO.getSchemeCode() );
				inputDto.put( com.Constant.CONST_TARIFF, schemeVO.getTariffCode() );
			}
			if( !Utils.isEmpty( inputDto.get( com.Constant.CONST_SCHEME ) ) && !Utils.isEmpty( inputDto.get( com.Constant.CONST_TARIFF ) ) ){
				schemeVO.setSchemeCode( Integer.valueOf( inputDto.get( com.Constant.CONST_SCHEME ).toString() ) );
				schemeVO.setTariffCode( Integer.valueOf( inputDto.get( com.Constant.CONST_TARIFF ).toString() ) );
				schemeVO.setPolicyType( inputDto.get( "policyType" ).toString() );
				coverDetails = loadCoverSvc.invokeMethod( "getRiskCoverDetails", schemeVO );
				inputDto.put( "coverDetails", coverDetails );
				inputDto.put( "pageContext", pageContext );
				inputDto.put( "covers", covers );
				inputDto.put( "ownerShipStatus", ownerShipStatus );
				inputDto.put( "promoCodes", getPromotionalCodes(request) );
				inputDto.put( "profile", userProfile.getRsaUser().getProfile() );
				inputDto.put( "staffDetails", request.getAttribute( "staffDetails" ) );
				inputDto.put( "transactionNo", request.getAttribute( "transactionNo" ) );
				inputDto.put( "oldContentPPFlag", request.getAttribute( "oldContentPPFlag" ) );
				renderer.buildHTMLContent( inputDto );
				request.setAttribute( "schemeCode", inputDto.get( com.Constant.CONST_SCHEME ).toString() );
				request.setAttribute( "tariffCode", inputDto.get( com.Constant.CONST_TARIFF ).toString() );
				//Home Revamp requirement 1.1
				request.setAttribute( "transactionNo", inputDto.get( com.Constant.CONST_TRANSACTIONNO ) );
			}
		}
		catch( Exception exp ){
			exp.printStackTrace();
		}
	}
	
	private List<CoverDetailsVO> getCoverDetails(HttpServletRequest request){
		List<CoverDetailsVO> coverDetails = null;
		List<CoverDetailsVO> covers = null;
		BuildingDetailsVO buildingDetails = null;
		try{
			coverDetails = new ArrayList<CoverDetailsVO>();
			covers = (List<CoverDetailsVO>) request.getAttribute( AppConstants.COVERS );
			buildingDetails = (BuildingDetailsVO) request.getAttribute( AppConstants.BUILDING );
			if(!Utils.isEmpty( covers )){
				coverDetails.addAll( covers );
				
			}
			if(!Utils.isEmpty( buildingDetails )){
				CoverDetailsVO coverVO = new CoverDetailsVO();
				coverVO.setCoverCodes( buildingDetails.getCoverCodes() );
				coverVO.setCoverName( "Building" );
				coverVO.setDiscOrLoadPerc( buildingDetails.getDiscOrLoadPerc() );
				coverVO.setPremiumAmt( buildingDetails.getPremiumAmt() );
				coverVO.setPremiumAmtActual( buildingDetails.getPremiumAmtActual() );
				coverVO.setRiskCodes( buildingDetails.getRiskCodes() );
				coverVO.setSumInsured( buildingDetails.getSumInsured() );
				
				coverVO.setVsd( buildingDetails.getVsd() );
				coverDetails.add( coverVO );
			}
		
		}
		catch(Exception exp){
			exp.printStackTrace();
		}
		
		return coverDetails;
	}
	
	private List<Short> getPromotionalCodes( HttpServletRequest request ){
		@SuppressWarnings( "unchecked" )
		List<Short> promotionalCodes = (List<Short>) request.getAttribute( AppConstants.PROMOTIONAL_CODES );
		return promotionalCodes;
	}

	/**
	 * To set the scheme code
	 * @param scheme
	 */
	public void setScheme( String scheme ){
		this.scheme = scheme;
	}

	/**
	 * returns scheme string
	 * @return scheme
	 */
	public String getScheme(){
		return scheme;
	}

	/**
	 * To set the tariff code
	 * @param tariff
	 */
	public void setTariff( String tariff ){
		this.tariff = tariff;
	}

	/**
	 * returns tariff string
	 * @return tariff
	 */
	public String getTariff(){
		return tariff;
	}

	public void setCoverCode( String coverCode ){
		this.coverCode = coverCode;
	}

	public String getCoverCode(){
		return coverCode;
	}

	public void setPolicyType( String policyType ){
		this.policyType = policyType;
	}

	public String getPolicyType(){
		return policyType;
	}

	public void setExcludeCoverCode( String excludeCoverCode ){
		this.excludeCoverCode = excludeCoverCode;
	}

	public String getExcludeCoverCode(){
		return excludeCoverCode;
	}

	public Long getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(Long transactionNo) {
		this.transactionNo = transactionNo;
	}
	
	public void setPolicyId( String policyId ){
		this.policyId = policyId;
	}

	public String getPolicyId(){
		return policyId;
	}

	public void setEndtId( String endtId ){
		this.endtId = endtId;
	}

	public String getEndtId(){
		return endtId;
	}

	
	
}
