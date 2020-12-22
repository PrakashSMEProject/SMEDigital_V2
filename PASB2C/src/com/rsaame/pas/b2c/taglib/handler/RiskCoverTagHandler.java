/**
 * Class for implementing the logic of Risk Cover tag. 
 * Risk cover tag helps in generating dynamic risk cover page based on the 
 * configuration done in scheme-tariff table in DB.
 * Created in Phase 3 for Home Insurance
 */
package com.rsaame.pas.b2c.taglib.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.swing.text.StyledEditorKit.BoldAction;
import org.springframework.web.servlet.tags.form.InputTag;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.taglib.svc.LoadCoverSvc;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.taglib.helper.RiskCoverTagRenderer;
//import com.rsaame.pas.util.PolicyContextUtil;
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
	private String policyId;
	private String endtId;
	private Short docCode;
	private Long quotationNo;

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
		CommonVO commonVO = new CommonVO();
		SchemeVO schemeVO = null;
		List<CoverDetailsVO> covers = null;
		Short ownerShipStatus = null;

		try{
			inputDto = new HashMap<String, Object>();
			schemeVO = new SchemeVO();
			pageContext = (PageContext) getJspContext();
			request = (HttpServletRequest) pageContext.getRequest();
			
			//commonVO = PolicyContextUtil.getPolicyContext(request).getCommonDetails();
			
			covers = getCoverDetails(request);
			if (!Utils.isEmpty( (BuildingDetailsVO) request.getAttribute( AppConstants.BUILDING ) )){
				ownerShipStatus = ((BuildingDetailsVO) request.getAttribute( AppConstants.BUILDING )).getOwnershipStatus();	
			}
			commonVO.setIsQuote( Boolean.TRUE );
			if(!Utils.isEmpty( commonVO.getIsQuote() ) ){
				if(commonVO.getIsQuote()){
					
					loadCoverSvc = (LoadCoverSvc) Utils.getBean( "loadCoverSvc" );
					
				}else{
					
					loadCoverSvc = (LoadCoverSvc) Utils.getBean( "loadCoverSvc_POL" );
				}
			}
			
			renderer = (RiskCoverTagRenderer) Utils.getBean( "riskCoverTagRendererBean" );
			inputDto.put( "scheme", scheme );
			inputDto.put( "tariff", tariff );
			inputDto.put( "policyType", policyType );
			inputDto.put( "coverCode", coverCode );
			inputDto.put( "excludeCoverCode", excludeCoverCode );
			inputDto.put( "policyId", policyId );
			inputDto.put( "endtId", endtId );
			inputDto.put( "docCode", docCode );
			// CTS 21/07/2020 Home Revamp CR 174639 requirement 1.1
			inputDto.put( "quotationNo", quotationNo );
			LOGGER.info( "--------" + inputDto.toString() );

			if( Utils.isEmpty( scheme ) && Utils.isEmpty( tariff ) ){
				schemeVO = (SchemeVO) loadCoverSvc.invokeMethod( "getSchemeDetails", commonVO );
				inputDto.put( "scheme", schemeVO.getSchemeCode() );
				inputDto.put( "tariff", schemeVO.getTariffCode() );
			}
			if( !Utils.isEmpty( inputDto.get( "scheme" ) ) && !Utils.isEmpty( inputDto.get( "tariff" ) ) ){
				schemeVO.setSchemeCode( Integer.valueOf( inputDto.get( "scheme" ).toString() ) );
				schemeVO.setTariffCode( Integer.valueOf( inputDto.get( "tariff" ).toString() ) );
				schemeVO.setPolicyType( inputDto.get( "policyType" ).toString() );
				coverDetails = loadCoverSvc.invokeMethod( "getRiskCoverDetails", schemeVO );
				inputDto.put( "coverDetails", coverDetails );
				inputDto.put( "pageContext", pageContext );
				inputDto.put( "covers", covers );
				inputDto.put( "ownerShipStatus", ownerShipStatus );
				inputDto.put( "promoCodes", getPromotionalCodes(request) );
				inputDto.put( "oldContentPPFlag", request.getAttribute( "oldContentPPFlag" ) );
				inputDto.put( "quotationNo", request.getAttribute( "quotationNo" ) );
				renderer.buildHTMLContent( inputDto );
				request.setAttribute( "schemeCode", inputDto.get( "scheme" ).toString() );
				request.setAttribute( "tariffCode", inputDto.get( "tariff" ).toString() );
				request.setAttribute( "quotationNo", inputDto.get( com.Constant.CONST_QUOTATIONNO ));
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

	/**
	 * @return the docCode
	 */
	public Short getDocCode(){
		return docCode;
	}

	/**
	 * @param docCode the docCode to set
	 */
	public void setDocCode( Short docCode ){
		this.docCode = docCode;
	}	
	
}
