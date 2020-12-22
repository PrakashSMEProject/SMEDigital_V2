package com.rsaame.pas.underwriterQue.ui;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.cmn.CommonVO;

public class UnderwriterQuestionsTagHandler extends SimpleTagSupport{

	private String type;
	private String noOfCols;
	private String sectId;
	private String disabledFlag;

	/*
	 * In case uw q's needs to be displayed in different section on the same page. The qRange should be set. 
	 * For qRange will be like -> 
	 * 0-3,0 - display q's 1 to 4 with names starting uwA[0]
	 * 4-7,3 display q's 5 to 7 with names starting uwA[3]
	 * qRange is optional paramater
	 */

	private String qRange;

	/*
	 * parameter which defines the LOB currently used
	 */
	private String polType;

	/*
	 * 
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	public void doTag() throws IOException{

		JspWriter out = null;
		UnderWriterQuestionsRenderer uwqRenderer = null;

		Integer tarrif = null;
		
		PageContext pageContext = (PageContext) getJspContext();
		out = pageContext.getOut();
		uwqRenderer = new UnderWriterQuestionsRenderer();
		
		HashMap<String, Object> attributeList = new HashMap<String, Object>();
		/*
		 *  If polType is empty or 50, it belongs to SBS 
		 */
		if( Utils.isEmpty( polType ) || polType.equals(  Utils.getSingleValueAppConfig( "SBS_POLICY_TYPE" ) )){
			
			PolicyContext context = PolicyContextUtil.getPolicyContext( (HttpServletRequest) pageContext.getRequest() );
			PolicyVO policyVO = context.getPolicyDetails();
			tarrif = null;
			if( !Utils.isEmpty( policyVO ) && !Utils.isEmpty( policyVO.getScheme() ) && !Utils.isEmpty( policyVO.getScheme().getTariffCode() ) ){
				tarrif = policyVO.getScheme().getTariffCode();
			}
			if( sectId.equals( String.valueOf( SvcConstants.SECTION_ID_FIDELITY ) ) ){

				SectionVO sectionVO = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_FIDELITY );
				LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( sectionVO );
				FidelityVO fidelityVO = (FidelityVO) PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO );
				if( !Utils.isEmpty( fidelityVO ) && fidelityVO.getUnnammedEmployeeDetails().isEmpty() ){
					disabledFlag = "true";
				}
			}
			if( Utils.isEmpty( tarrif ) || ( !Utils.isEmpty( tarrif ) && sectId.equals( String.valueOf( SvcConstants.SECTION_ID_GEN_INFO ) ) ) ){
				tarrif = 0;
			}
			//Start Added by Mindtree on 01/09/2015 for CR:104256 - Student Liability CR
			
			if (!Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)
					.equalsIgnoreCase("30")) {

				Date polPreparedDate = SvcUtils.getPreparedDate(policyVO
						.getQuoteNo());

				if (polPreparedDate == null) {
					polPreparedDate = new Date();
				}

				attributeList.put(AppConstants.POL_EFFECTIVE_DATE,
						polPreparedDate);
			}
			
			//End Added by Mindtree on 01/09/2015 for CR:104256 - Student Liability CR
		}
		/*
		 * If polType is not empty check for which LOB(TRAVEL, HOME)
		 */
		else{
			//get tariff id from request 
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			String tarCode = null;
			CommonVO commonVO = PolicyContextUtil.getPolicyContext(request).getCommonDetails();
			
			if(!Utils.isEmpty(commonVO.getQuoteNo() )) {
			Date polPreparedDate = SvcUtils.getPreparedDate(commonVO.getQuoteNo() );
			if(polPreparedDate == null) {
				polPreparedDate = new Date();
			}
			attributeList.put(AppConstants.POL_EFFECTIVE_DATE, polPreparedDate);
			}
			// If Policy type is TRAVEL, tariff is 0
			if(polType.equals( Utils.getSingleValueAppConfig( "TRAVEL_LONG_TERM_POLICY_TYPE" )) || (polType.equals( Utils.getSingleValueAppConfig( "TRAVEL_SHORT_TERM_POLICY_TYPE" ) ) )){
				tarrif = 0; 
			}
			// If Policy type is HOME, tariff is retrieved from request parameter
			else {
				// the field 'tarrif_code' needs to be created in common general info page
				tarCode = (String)request.getAttribute( "tariffCode" );
				
				// check for null validation
				if(Utils.isEmpty( tarCode ) || tarCode.equals("null")){
					tarrif = 0;
				}
				else{
					tarrif = Integer.valueOf(tarCode);
				}
				
			}
		}
		
		/*Pending - logging to be included*/
		try{
			if( !Utils.isEmpty( type ) ){
				attributeList.put( AppConstants.INPUTTYPE, type );
				attributeList.put( AppConstants.NUMBEROFCOLS, noOfCols );
				attributeList.put( AppConstants.SECTION_ID, sectId );
				attributeList.put( AppConstants.OUT, out );
				attributeList.put( "tarrif", tarrif );
				attributeList.put( AppConstants.POLICY_TYPE, polType);
				attributeList.put( AppConstants.DISABLEDFLAG, disabledFlag );
				attributeList.put( AppConstants.RANGE, qRange );
				uwqRenderer.buildHTMLContent( attributeList );
			}
		}
		catch( DataAccessException dataAccessException ){
			dataAccessException.printStackTrace();
		}
		catch( Exception exception ){
			uwqRenderer.buildEmptyControl( out );
		}

	}

	/**
	 * @param type
	 */
	public void setType( String type ){
		this.type = type;
	}

	/**
	 * @param noOfCols
	 */
	public void setNoOfCols( String noOfCols ){
		this.noOfCols = noOfCols;
	}

	/**
	 * @param sectId
	 */
	public void setSectId( String sectId ){
		this.sectId = sectId;
	}

	/**
	 * @param disabledFlag
	 *            the disabledFlag to set
	 */
	public void setDisabledFlag( String disabledFlag ){
		this.disabledFlag = disabledFlag;
	}

	/**
	 * @param qRange the qRange to set
	 */
	public void setqRange( String qRange ){
		this.qRange = qRange;
	}

	/**
	 * @param polType
	 */
	public void setPolType( String polType ){
		this.polType = polType;
	}

}
