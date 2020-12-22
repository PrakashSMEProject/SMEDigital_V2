/**
 * Class for rendering the data in Risk Cover tag. 
 * Risk cover tag helps in generating dynamic risk cover page based on the 
 * configuration done in scheme-tariff table in DB.
 * Render is a helper class to generate the form elements
 * Created in Phase 3 for Home Insurance
 */
package com.rsaame.pas.taglib.helper;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.lookup.ui.DropDownHTMLRenderer;
import com.rsaame.pas.lookup.ui.IHtmlRenderer;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.app.FieldType;
import com.rsaame.pas.vo.bus.CoverDetails;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.StaffDetailsVO;


/**
 * @author Sarath Varier
 * @since Phase 3
 *
 */
public class RiskCoverTagRenderer implements IHtmlRenderer{

	private static final String ATTR_TEMPLATE = "%s='%s'";
	private static final String DISABLED = "disabled";
	private static final String READONLY = "readonly";
	private static final String OPTION_TEMPLATE_SELECTED = "<option value='%s' selected=\"selected\">%s</option>";
	private static final String DOJOTYPESELECT = "dijit.form.FilteringSelect";
	private static final String OPTION_TEMPLATE = "<option value='%s' >%s</option>";
	PageContext pageContext = null;
	String scheme = null;
	String tariff = null;
	Long transactionNo = null;
	Boolean autoPopulateRiskType32Flag;
	Boolean oldContentPPFlag;
	String DUBAI_LOC = "20";
	String ABU_DHABI_LOC = "21";
	String location =  Utils.getSingleValueAppConfig("DEPLOYED_LOCATION");
	//List<StaffDetailsVO> staffDetailsVO = null;

	@Override
	public void buildHTMLContent( HashMap<String, Object> attributeList ) throws IOException{

		JspWriter jspWriter = null;
		StringBuffer responseString = null;
		List<CoverDetailsVO> coverDetailsList = null;
		Short ownerShipStatus = null;
		
		try{
			pageContext = (PageContext) attributeList.get( "pageContext" );
			jspWriter = pageContext.getOut();
			scheme = attributeList.get( "scheme" ).toString();
			tariff = attributeList.get( "tariff" ).toString();
			oldContentPPFlag = new Boolean(attributeList.get( "oldContentPPFlag" ).toString());
			transactionNo = (Long)attributeList.get( "transactionNo" );

			if (!Utils.isEmpty( attributeList.get( "ownerShipStatus" ) )){
				ownerShipStatus = (Short)attributeList.get( "ownerShipStatus" );
			}
			responseString = new StringBuffer();
			coverDetailsList = ( (CoverDetails) attributeList.get( com.Constant.CONST_COVERDETAILS ) ).getCoverDetails();
			Short coverCode = ( ( attributeList.get( com.Constant.CONST_COVERCODE ) == null ) || ( ( attributeList.get( com.Constant.CONST_COVERCODE ).toString() == "" ) ) ) ? 0 : Short.valueOf( attributeList.get(
					com.Constant.CONST_COVERCODE ).toString() );
			//create table
			if( coverDetailsList.size() > 0 ){
				responseString.append( "<table " );
				responseString.append( String.format( ATTR_TEMPLATE, "border", "0" ) );
				responseString.append( String.format( ATTR_TEMPLATE, "cellpadding", "0" ) );
				responseString.append( String.format( ATTR_TEMPLATE, "cellspacing", "0" ) );
				responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, "partableie7 rsaCommonTable" ) );
				responseString.append( " >" );
				if( coverCode == 1 ){
					responseString.append( "<tr>" );
					responseString.append( createOwnershipStatus(attributeList, ownerShipStatus));
					responseString.append( "<td " );
					responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, "sum_title_head1" ) );
					responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "width: 125px;" ) );
					responseString.append( String.format( ATTR_TEMPLATE, "align", "center" ) );
					responseString.append( "><label><b>Coverage Premium</b></label></td>" );
					if( !attributeList.get( "profile" ).toString().equalsIgnoreCase( com.Constant.CONST_BROKER ) ){
						responseString.append( "<td " );
						responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, "sum_title_head1" ) );
						responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "width: 125px; align: centre;" ) );
						responseString.append( String.format( ATTR_TEMPLATE, "align", "center" ) );
						responseString.append( ">" );
						responseString.append( "<label><b>Apply Discount/Loading %</b></label></td> " );
					}
					responseString.append( "<td></td>" );
					responseString.append( "</tr>" );
				}
				responseString.append( createRows( attributeList ) );

				responseString.append( "</table>" );
			}
			else{
				responseString.append( "There are no covers available for this scheme" );
			}
			jspWriter.print( responseString );
		}
		catch( Exception exp ){
			exp.printStackTrace();
		}

	}

	private StringBuffer createRows( HashMap<String, Object> attributeList ){

		StringBuffer tableRow = null;
		List<CoverDetailsVO> coverDetailsList = null;
		int counter = -1;
		List<CoverDetailsVO> savedCovers = null;
		HashMap <String, CoverDetailsVO> coverMap = null;
		List<Short> promoCodes = null;
		
		Boolean freeCover = false;
		autoPopulateRiskType32Flag = false;
		List<Object> columnDetails = null;
		List<StaffDetailsVO> staffDetailsVO = new ArrayList<StaffDetailsVO>(0);
		
		
		try{

			tableRow = new StringBuffer();
			coverDetailsList = ( (CoverDetails) attributeList.get( com.Constant.CONST_COVERDETAILS ) ).getCoverDetails();
			Short coverCode = ( ( attributeList.get( com.Constant.CONST_COVERCODE ) == null ) || ( attributeList.get( com.Constant.CONST_COVERCODE ).toString() == "" ) ) ? 0 : Short.valueOf( attributeList.get(
					com.Constant.CONST_COVERCODE ).toString() );
			Short excludeCoverCode = ( ( attributeList.get( com.Constant.CONST_EXCLUDECOVERCODE ) == null ) || ( attributeList.get( com.Constant.CONST_EXCLUDECOVERCODE ).toString() == "" ) ) ? 0 : Short
					.valueOf( attributeList.get( com.Constant.CONST_EXCLUDECOVERCODE ).toString() );
			

			if (!Utils.isEmpty( (List<CoverDetailsVO>) attributeList.get( "covers" ) )){
				savedCovers = (List<CoverDetailsVO>) attributeList.get( "covers" );
			}
			if (!Utils.isEmpty( (List<Short>) attributeList.get( "promoCodes" ) )){
				promoCodes = (List<Short>) attributeList.get( "promoCodes" );
			}
			if (!Utils.isEmpty( (List<StaffDetailsVO>) attributeList.get( "staffDetails" ) )){
				staffDetailsVO = (List<StaffDetailsVO>) attributeList.get( "staffDetails" );
			}
			
			transactionNo =  (Long)attributeList.get( "transactionNo" );
			
			String profile = attributeList.get( "profile" ).toString();
			
			
			
			if(!Utils.isEmpty( savedCovers ))
			
				coverMap = (HashMap<String, CoverDetailsVO>) getCoversMap(savedCovers);
			
						
			for( int i = 0; i < coverDetailsList.size(); i++ ){
				counter++;
				
					if( ( ( ( coverDetailsList.get( i ).getCoverCodes().getCovCode() ) == coverCode ) || ( coverCode == 0 ) )
											&& ( ( coverDetailsList.get( i ).getCoverCodes().getCovCode() ) != excludeCoverCode ) ){
					tableRow.append( "<tr>" );
					
					int j = 0;
					for( j = i; j < coverDetailsList.size(); j++ ){
						
						
						if(coverDetailsList.get( j ).getCoverCodes().getCovCode() == coverDetailsList.get( i ).getCoverCodes().getCovCode() )
							{
								CoverDetailsVO coverVO = null;
								 if(!Utils.isEmpty( coverMap ) )
								coverVO = getCoverFromMap(coverMap, coverDetailsList.get( j ));
								
								
								
								freeCover = false;
								if( (!Utils.isEmpty( promoCodes )) && (promoCodes.contains( coverDetailsList.get( j ).getCoverCodes().getCovCode() ) ) ){
									freeCover = true;
								}
								columnDetails =  new ArrayList<Object>();
								columnDetails.add( coverDetailsList.get( j ) );
								columnDetails.add( counter );
								columnDetails.add( coverVO );
								columnDetails.add( freeCover );
								columnDetails.add( profile );
								columnDetails.add( staffDetailsVO );
								tableRow.append( createColumn( columnDetails ));
							}
						else{
							break;
						}
						if( ( j < ( coverDetailsList.size() - 1 ) )
								&& ( coverDetailsList.get( j + 1 ).getCoverCodes().getCovTypeCode() == coverDetailsList.get( j ).getCoverCodes().getCovTypeCode() )
								&& ( coverDetailsList.get( j + 1 ).getCoverCodes().getCovCode() == coverDetailsList.get( j ).getCoverCodes().getCovCode() ) ){
							break;
						}

					}
					if( i != j ){
						i = j - 1;
					}
					else
						i = j;
					tableRow.append( "</tr>" );
	//	}
					}
			}
			tableRow.append( createElement(com.Constant.CONST_HIDDEN, ("autoPopulateRiskType32Flag"), null, null, autoPopulateRiskType32Flag.toString() ));
		
		}
		catch( Exception exp ){
			exp.printStackTrace();
		}

		return tableRow;

}

	private StringBuffer createColumn( List<Object> columnDetails ){

		StringBuffer column = null;
		String fieldId = null;
		Map<String, Object> resultMap = null;
		String savedSIValue = null;
		CoverDetailsVO inputDto = null;
		int rowNum = 0;
		CoverDetailsVO coverVO = null;
		Boolean freeCover = null;
		String profile = null;
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		List<StaffDetailsVO> staffDetailsVO = null;
		try{

			column = new StringBuffer();
			inputDto = (CoverDetailsVO) columnDetails.get( 0 );
			rowNum = Integer.parseInt( columnDetails.get( 1 ).toString() );
			coverVO = (CoverDetailsVO) columnDetails.get( 2 );
			freeCover = (Boolean) columnDetails.get( 3 );
			profile = (String) columnDetails.get( 4 );
			staffDetailsVO = (List<StaffDetailsVO>) columnDetails.get( 5 );
			String defaultDateFormat = Utils.getSingleValueAppConfig("DEFAULT_DATE_FORMAT");
			Date covEffDate = null;
			try {
				
				covEffDate = new SimpleDateFormat(defaultDateFormat).parse(SvcUtils.populateCovRemovalDt());
				
			} catch (ParseException e) {
				e.printStackTrace();
			}  
			
			
			Date polPreparedDt = null;
			try {
				polPreparedDt = new SimpleDateFormat(defaultDateFormat).parse(SvcUtils.populatePolPreparedDt(transactionNo));
				
			} catch (ParseException e) {
				e.printStackTrace();
			}  
			
			if(inputDto.getRiskCodes().getRiskCode() == 1 && inputDto.getCoverCodes().getCovCode() != 6 ){
				fieldId = "building_";
			}
			else {
				fieldId = "cover_";
			}
			resultMap = getSelectedValues(coverVO, inputDto.getMappingField());
			//Home Revamp requirement 1.1
			if(inputDto.getMandatoryIndicator() && 
					(DUBAI_LOC.equalsIgnoreCase(location) || ABU_DHABI_LOC.equalsIgnoreCase(location)) && 
					polPreparedDt.compareTo(covEffDate)>0)
			{  
				//Home Revamp 174639 6.1 changes start
				if(inputDto.getCoverCodes().getCovCode() != 4 &&
						inputDto.getCoverCodes().getCovCode() != 5 && 
						inputDto.getCoverCodes().getCovCode() != 6 && 
						inputDto.getCoverCodes().getCovCode() != 7 && 
						inputDto.getCoverCodes().getCovCode() != 8 && 
						inputDto.getCoverCodes().getCovCode() != 9)
		        
				/*if(inputDto.getCoverCodes().getCovCode() != 4 && 
						inputDto.getCoverCodes().getCovCode() != 5 && 
						inputDto.getCoverCodes().getCovCode() != 6 && 
						inputDto.getCoverCodes().getCovCode() != 7 && 
						inputDto.getCoverCodes().getCovCode() != 8 && 
						inputDto.getCoverCodes().getCovCode() != 9)*/
				//Home Revamp 174639 6.1 changes end
				{
					column.append( "<td " );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_TEXTR_RSACOMMONTDWIDTH145 ) );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "width: 175px;" ) );
					column.append( ">" );
					column.append( "<label " );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_RSATABLELINEHEIGHT ) );
					column.append( ">" );
					column.append( inputDto.getCoverName() );
					
					
					if(inputDto.getCoverDesc() != null ){
									column.append("&nbsp;&nbsp;");
									column.append("<img ");
									column.append(String.format( ATTR_TEMPLATE, "alt", "Help image" ));
									column.append(String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "height: 14px;" ));
									column.append(String.format( ATTR_TEMPLATE, " src", "/pas/static/img/help.jpg" ));
									column.append(String.format( ATTR_TEMPLATE, " title", inputDto.getCoverDesc() ));
									column.append(">");
					}
					column.append( "</label>" );
				}
				else if (inputDto.getCoverCodes().getCovCode() == 4){
					column.append( "<td " );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_TEXTR_RSACOMMONTDWIDTH145 ) );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "width: 175px;" ) );
					column.append( ">" );
					column.append( "<label " );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_RSATABLELINEHEIGHT ) );
					column.append( ">" );
					column.append( "</label>" );
					
					
					}	
				}
			//Home Revamp 174639 1.1 changes start
		/*	else if(!inputDto.getMandatoryIndicator() && 
					(DUBAI_LOC.equalsIgnoreCase(location) || ABU_DHABI_LOC.equalsIgnoreCase(location)) && 
					polPreparedDt.compareTo(covEffDate)>0 && inputDto.getCoverCodes().getCovCode() == 4)
			{
				column.append( "<td " );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_TEXTR_RSACOMMONTDWIDTH145 ) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "width: 175px;" ) );
				column.append( ">" );
				column.append( "<label " );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_RSATABLELINEHEIGHT ) );
				column.append( ">" );
				column.append( "</label>" );
			}*/
			else if(!inputDto.getMandatoryIndicator() && 
					(DUBAI_LOC.equalsIgnoreCase(location) || ABU_DHABI_LOC.equalsIgnoreCase(location)) && 
					polPreparedDt.compareTo(covEffDate)>0 && inputDto.getCoverCodes().getCovCode() == 4)
			{
				column.append( "<td " );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_TEXTR_RSACOMMONTDWIDTH145 ) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "width: 175px;" ) );
				column.append( ">" );
				column.append( "<label " );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_RSATABLELINEHEIGHT ) );
				column.append( ">" );
				column.append( "</label>" );
			}
			//Home Revamp 174639 1.1 changes ends

			else
			{
				//Home Revamp 174639 6.1 changes start
				if(inputDto.getCoverCodes().getCovCode() != 5 && inputDto.getCoverCodes().getCovCode() != 6 &&
						inputDto.getCoverCodes().getCovCode() != 7 && 
						inputDto.getCoverCodes().getCovCode() != 8 && 
						inputDto.getCoverCodes().getCovCode() != 9)
					//Home Revamp 174639 6.1 changes end
				{
				column.append( "<td " );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_TEXTR_RSACOMMONTDWIDTH145 ) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "width: 175px;" ) );
				column.append( ">" );
				column.append( "<label " );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_RSATABLELINEHEIGHT ) );
				column.append( ">" );
				column.append( inputDto.getCoverName() );
				
				
				if(inputDto.getCoverDesc() != null ){
								column.append("&nbsp;&nbsp;");
								column.append("<img ");
								column.append(String.format( ATTR_TEMPLATE, "alt", "Help image" ));
								column.append(String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "height: 14px;" ));
								column.append(String.format( ATTR_TEMPLATE, " src", "/pas/static/img/help.jpg" ));
								column.append(String.format( ATTR_TEMPLATE, " title", inputDto.getCoverDesc() ));
								column.append(">");
				}
				column.append( "</label>" );
				}
			}
			
			String location =  Utils.getSingleValueAppConfig("DEPLOYED_LOCATION");
			
				column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ "coverName["+rowNum+"]"), null, null, inputDto.getCoverName() ));
				column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ "riskCode["+rowNum+"]"), null, null, inputDto.getRiskCodes().getRiskCode().toString() ));
				column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ "riskCat["+rowNum+"]"), null, null, inputDto.getRiskCodes().getRiskCat().toString() ));
				column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ "riskType["+rowNum+"]"), null, null, inputDto.getRiskCodes().getRiskType().toString() ));
				column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ "covCode["+rowNum+"]"), null, null, String.valueOf( inputDto.getCoverCodes().getCovCode())));
				column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ "covTypeCode["+rowNum+"]"), null, null, String.valueOf( inputDto.getCoverCodes().getCovTypeCode()) ));
				column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ "basicRskCode["+rowNum+"]"), null, null, inputDto.getRiskCodes().getBasicRskCode().toString() ));
				column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ "isFreeCover["+rowNum+"]"), null, null, freeCover.toString() ));
				if(!Utils.isEmpty( coverVO)){
				column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ "id["+rowNum+"]"), null, null, coverVO.getRiskCodes().getRskId().toString() ));
				column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ "vsd["+rowNum+"]"), null, null, coverVO.getVsd().toString() ));
				}	
			
			
			column.append( com.Constant.CONST_TD_END );

			if( inputDto.getFieldType() == FieldType.DROP_DOWN ){
				String disabledFlag = "false";
				savedSIValue = null;
				column.append( "<td " );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_TEXTL_RSACOMMONTDWIDTH145 ) );
				column.append( ">" );
				if(resultMap.get( "SI" ) != null){
					savedSIValue = resultMap.get( "SI" ).toString();
				}
				if( ( ( inputDto.getCoverCodes().getCovTypeCode() != 0 ) ||
						((inputDto.getCoverCodes().getCovCode() == 1) && 
								(inputDto.getRiskCodes().getRiskType().equals( AppConstants.HOME_PERSONAL_POSSESSION_RISK_TYPE ) ) ) )
									&& Utils.isEmpty( savedSIValue )){
					disabledFlag = "true";
				}
			
				String[] specialTariffCodes = Utils.getMultiValueAppConfig( AppConstants.HOME_PERSONAL_POSSESSION_SI_TARIFF, "," );
				
				if (Arrays.asList(specialTariffCodes).contains( tariff )){
					if (!Utils.isEmpty( savedSIValue )){
						savedSIValue = (SvcUtils.getLookUpCode( "PAS_HOME_" + inputDto.getRiskCodes().getRiskType() +
							"_" + inputDto.getCoverCodes().getCovCode() + "_" + inputDto.getCoverCodes().getCovTypeCode(), scheme, tariff,decimalFormat.format( Long.valueOf( resultMap.get( "SI" ).toString() ) ) ) ).toString();
					}
					if((inputDto.getRiskCodes().getRiskType().equals( AppConstants.HOME_PERSONAL_POSSESSION_RISK_TYPE )) && (!autoPopulateRiskType32Flag)){
						autoPopulateRiskType32Flag = true;
					}
					
				}
				//Home Revamp requirement 1.1
				if(inputDto.getMandatoryIndicator() && 
						(DUBAI_LOC.equalsIgnoreCase(location) || ABU_DHABI_LOC.equalsIgnoreCase(location)) 
						&& polPreparedDt.compareTo(covEffDate)>0)
				{ 
					if(inputDto.getCoverCodes().getCovCode() == 4 )
					{
				
					column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ inputDto.getMappingField()+ "["+rowNum+"]"), null, null, String.valueOf(inputDto.getPrLimit())));
					}
					
				
				else
				{
					column.append( createDropdown((fieldId+ inputDto.getMappingField()+ "["+rowNum+"]"), "PAS_HOME_" + inputDto.getRiskCodes().getRiskType() +
							"_" + inputDto.getCoverCodes().getCovCode() + "_" + inputDto.getCoverCodes().getCovTypeCode(), 
							scheme, tariff, savedSIValue, null, ("onChangeDropdownAction(this, "+rowNum+")"), disabledFlag));
				}
				}
				else if(!inputDto.getMandatoryIndicator() && 
						(DUBAI_LOC.equalsIgnoreCase(location) || ABU_DHABI_LOC.equalsIgnoreCase(location)) 
						&& polPreparedDt.compareTo(covEffDate)>0)
				{
					/*if(inputDto.getCoverCodes().getCovCode() == 4 )
					{
				
					column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ inputDto.getMappingField()+ "["+rowNum+"]"), null, null, String.valueOf(inputDto.getPrLimit())));
					}
					
				
				else
				{
					column.append( createDropdown((fieldId+ inputDto.getMappingField()+ "["+rowNum+"]"), "PAS_HOME_" + inputDto.getRiskCodes().getRiskType() +
							"_" + inputDto.getCoverCodes().getCovCode() + "_" + inputDto.getCoverCodes().getCovTypeCode(), 
							scheme, tariff, savedSIValue, null, ("onChangeDropdownAction(this, "+rowNum+")"), disabledFlag));
				}	*/
					//Home Revamp 174639 1.1 changes start
					if(inputDto.getCoverCodes().getCovCode() == 4 )
					{
				
					column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ inputDto.getMappingField()+ "["+rowNum+"]"), null, null, String.valueOf(inputDto.getPrLimit())));
					}
					
				
				else
				{
					column.append( createDropdown((fieldId+ inputDto.getMappingField()+ "["+rowNum+"]"), "PAS_HOME_" + inputDto.getRiskCodes().getRiskType() +
							"_" + inputDto.getCoverCodes().getCovCode() + "_" + inputDto.getCoverCodes().getCovTypeCode(), 
							scheme, tariff, savedSIValue, null, ("onChangeDropdownAction(this, "+rowNum+")"), disabledFlag));
				}	
					//Home Revamp 174639 1.1 changes ends
				}
				
				else{
						column.append( createDropdown((fieldId+ inputDto.getMappingField()+ "["+rowNum+"]"), "PAS_HOME_" + inputDto.getRiskCodes().getRiskType() +
								"_" + inputDto.getCoverCodes().getCovCode() + "_" + inputDto.getCoverCodes().getCovTypeCode(), 
								scheme, tariff, savedSIValue, null, ("onChangeDropdownAction(this, "+rowNum+")"), disabledFlag));
						
				}
				
				column.append( com.Constant.CONST_TD_END );
				if ( inputDto.getCoverCodes().getCovCode()  == 3  ){
					
					String btnLabel = Utils.isEmpty(staffDetailsVO) || (!Utils.isEmpty( staffDetailsVO ) && !Utils.isEmpty( staffDetailsVO.get( 0 ) ) && Utils.isEmpty( staffDetailsVO.get( 0 ).getEmpName()))? "Add Staff Details" : "Edit Staff Details";
					column.append( "<td " );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, "genconrolbtn" ) );
					column.append( ">" );

					column.append( "<button " );
					column.append( String.format( ATTR_TEMPLATE, "type", "button" ) );				
					column.append( String.format( ATTR_TEMPLATE, " id", ("staffDetailsBtn" )) );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DOJOTYPE, "dijit.form.Button" ) );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_NAME, ("staffDetailsBtn")) );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, "staffDetailsClass") );
					if( disabledFlag.equalsIgnoreCase( "true" ) ){
						column.append( " disabled='disabled' " );
					}
					//Create method arguments of format method(rowNum, covCode~basicRskCode~riskCode~riskType~riskCat)
					
					String onClickMethod = ("staffDetails("+rowNum+");");

					column.append( String.format( ATTR_TEMPLATE, " onclick", onClickMethod ) );
					column.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_STYLE,	" width:100px; background: url("
									+ "/pas/static/img/buttonBg.jpg) repeat-x; color:white; width:80px;"));
					column.append( com.Constant.CONST_START_LABEL_END );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_RSATABLELINEHEIGHT ) );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "color:white" ) );
					column.append( ">"+ btnLabel +"</label></button></td>" );
				}
			}
			else if( inputDto.getFieldType() == FieldType.CHECK_BOX ){
                //Home Revamp requirement 1.1
				if(inputDto.getMandatoryIndicator() && (DUBAI_LOC.equalsIgnoreCase(location) || ABU_DHABI_LOC.equalsIgnoreCase(location)) && polPreparedDt.compareTo(covEffDate)>0)
				{ 
				if(inputDto.getCoverCodes().getCovCode() == 4)
				{
					column.append( "<td " );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "width: 10px;" ) );
					column.append( ">" );
					
					column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ "checkBox["+rowNum+"]"), null, null, "on" ));
					//String.format( ATTR_TEMPLATE, " checked", "checked" );
					column.append( com.Constant.CONST_TD_END );
					
					
				}
				else
				{
					column.append( "<td " );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "width: 10px;" ) );
					column.append( ">" );
					
					column.append( com.Constant.CONST_INPUT_END );
					column.append( String.format( ATTR_TEMPLATE, "id", (fieldId+ "checkBox["+rowNum+"]" )) );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_NAME, (fieldId+ "checkBox["+rowNum+"]") ) );
					column.append( String.format( ATTR_TEMPLATE, "type", "checkbox" ) );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DOJOTYPE, "dijit.form.CheckBox" ) );
					if (!Utils.isEmpty( resultMap.get( com.Constant.CONST_CHKBOX ) )){
						column.append( String.format( ATTR_TEMPLATE, " checked", "checked" ) );	
					}
					else{
						column.append( String.format( ATTR_TEMPLATE, "disabled", DISABLED ) );	
					}
					column.append( String.format( ATTR_TEMPLATE, " onClick", ("onCheckAction(this, "+rowNum+")" ) ));
					column.append( "/>" );
					column.append( com.Constant.CONST_TD_END );
				}
				}
				else{
				column.append( "<td " );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "width: 10px;" ) );
				column.append( ">" );
				
				column.append( com.Constant.CONST_INPUT_END );
				column.append( String.format( ATTR_TEMPLATE, "id", (fieldId+ "checkBox["+rowNum+"]" )) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_NAME, (fieldId+ "checkBox["+rowNum+"]") ) );
				column.append( String.format( ATTR_TEMPLATE, "type", "checkbox" ) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DOJOTYPE, "dijit.form.CheckBox" ) );
				if (!Utils.isEmpty( resultMap.get( com.Constant.CONST_CHKBOX ) )){
					column.append( String.format( ATTR_TEMPLATE, " checked", "checked" ) );	
				}
				else{
					column.append( String.format( ATTR_TEMPLATE, "disabled", DISABLED ) );	
				}
				column.append( String.format( ATTR_TEMPLATE, " onClick", ("onCheckAction(this, "+rowNum+")" ) ));
				column.append( "/>" );
				column.append( com.Constant.CONST_TD_END );
				}
			}
			else{
				savedSIValue = resultMap.get( "SI" ) != null? Long.valueOf( resultMap.get( "SI" ).toString() ).toString() : "";
				//Home Revamp 174639 6.1 changes start
				if(inputDto.getCoverCodes().getCovCode() == 5 )
				{
					column.append( "<td " );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "width: 10px;" ) );
					column.append( ">" );
					
					column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ inputDto.getMappingField()+ "["+rowNum+"]"), null, null,String.valueOf(inputDto.getPrLimit())));
					//String.format( ATTR_TEMPLATE, " checked", "checked" );
					column.append( com.Constant.CONST_TD_END );
					
				
				}
				else if(inputDto.getCoverCodes().getCovCode() == 6 )
				{
					column.append( "<td " );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "width: 10px;" ) );
					column.append( ">" );
					
					column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ inputDto.getMappingField()+ "["+rowNum+"]"), null, null,String.valueOf(inputDto.getPrLimit())));
					//String.format( ATTR_TEMPLATE, " checked", "checked" );
					column.append( com.Constant.CONST_TD_END );
					
				
				}
				
				else if(inputDto.getCoverCodes().getCovCode() == 7 )
				{
					column.append( "<td " );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "width: 10px;" ) );
					column.append( ">" );
					
					column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ inputDto.getMappingField()+ "["+rowNum+"]"), null, null,String.valueOf(inputDto.getPrLimit())));
					//String.format( ATTR_TEMPLATE, " checked", "checked" );
					column.append( com.Constant.CONST_TD_END );
					
				
				}
				
				else if(inputDto.getCoverCodes().getCovCode() == 8 )
				{
					column.append( "<td " );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "width: 10px;" ) );
					column.append( ">" );
					
					column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ inputDto.getMappingField()+ "["+rowNum+"]"), null, null,String.valueOf(inputDto.getPrLimit())));
					//String.format( ATTR_TEMPLATE, " checked", "checked" );
					column.append( com.Constant.CONST_TD_END );
					
				
				}
				
				else if(inputDto.getCoverCodes().getCovCode() == 9 )
				{
					column.append( "<td " );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "width: 10px;" ) );
					column.append( ">" );
					
					column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ inputDto.getMappingField()+ "["+rowNum+"]"), null, null,String.valueOf(inputDto.getPrLimit())));
					//String.format( ATTR_TEMPLATE, " checked", "checked" );
					column.append( com.Constant.CONST_TD_END );
					
				
				}
				else
				{ //Home Revamp 174639 6.1 changes  end
				column.append( "<td " );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_TEXTL_RSACOMMONTDWIDTH145 ) );
				column.append( ">" );

				column.append( com.Constant.CONST_INPUT_END );
				column.append( String.format( ATTR_TEMPLATE, "type", "text" ) );
				column.append( String.format( ATTR_TEMPLATE, " id", (fieldId+ inputDto.getMappingField()+ "["+rowNum+"]") ) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DOJOTYPE, "dijit.form.NumberTextBox" ) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_NAME, (fieldId+ inputDto.getMappingField()+"["+rowNum+"]") ) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_MAXLENGTH, "20" ) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, savedSIValue ) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_ONCHANGE, ("onChangeTextAction(this, "+rowNum+")") ) );
				if((((inputDto.getRiskCodes().getRiskCode().equals( AppConstants.HOME_CONTENTS_RISK_CODE )) && (inputDto.getRiskCodes().getRiskType().equals( AppConstants.HOME_PERSONAL_POSSESSION_RISK_TYPE ))) ||
						(((inputDto.getRiskCodes().getRiskCode().equals( AppConstants.HOME_BUILDING_RISK_CODE )) && (inputDto.getRiskCodes().getRiskType().equals( AppConstants.HOME_BUILDING_RISK_TYPE ))))) &&
						(Utils.isEmpty( savedSIValue ))){
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DISABLED, DISABLED ) );
				}
				column.append( "/>" );
				column.append( com.Constant.CONST_TD_END );
				}
			}
			
			
			if(inputDto.getCoverCodes().getCovCode() == 1 ){
				// Add premium & discount columns

				column.append( "<td " );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_TEXTR_RSACOMMONTDWIDTH145 ) );
				column.append( ">" );
				
				column.append( com.Constant.CONST_INPUT_END );
				column.append( String.format( ATTR_TEMPLATE, "type", "text") );
				column.append( String.format( ATTR_TEMPLATE, " id", (fieldId+ "premium["+rowNum+"]") ) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DOJOTYPE, com.Constant.CONST_DIJIT_FORM_VALIDATIONTEXTBOX ) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_NAME, (fieldId+ "premium["+rowNum+"]" )) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_MAXLENGTH, "20" ) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, resultMap.get( com.Constant.CONST_PREMIUM ) ) );
				column.append( String.format( ATTR_TEMPLATE, " readonly", READONLY ) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, "rsaRightAlign") );
				column.append( "/>" );
				
				column.append( createElement(com.Constant.CONST_HIDDEN, (fieldId+ "actualPremium["+rowNum+"]"), null, null, resultMap.get( com.Constant.CONST_ACTUALPREMIUM ).toString() ));
				// 147075 : When discount/loading is given at the Cover level and the quote is edited by Broker, the discount is clearing out
				// 152584 : Claim loading not reflecting in the renewal quote. Even when there is claim reported prior to renewal generation  
				if( profile.equalsIgnoreCase(com.Constant.CONST_BROKER ) ){
					column.append( "</td><td " );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_TEXTR_RSACOMMONTDWIDTH145 ) );
					column.append( ">" );

					column.append( com.Constant.CONST_INPUT_END );
					column.append( String.format( ATTR_TEMPLATE, "type", "text" ) );
					column.append( String.format( ATTR_TEMPLATE, " readonly", READONLY ) );
					column.append( String.format( ATTR_TEMPLATE, " id", (fieldId+ com.Constant.CONST_DISCOUNT_END+rowNum+"]" ) ));
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DOJOTYPE, com.Constant.CONST_DIJIT_FORM_VALIDATIONTEXTBOX ) );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_NAME, (fieldId+ com.Constant.CONST_DISCOUNT_END+rowNum+"]" )) );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_MAXLENGTH, "10" ) );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, resultMap.get( "disc" ) ) );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, "homeDiscount rsaRightAlign" ) );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_ONCHANGE, ("onChangeDiscountActionField(this)") ) );
					if((((inputDto.getRiskCodes().getRiskCode().equals( AppConstants.HOME_CONTENTS_RISK_CODE)) && (inputDto.getRiskCodes().getRiskType().equals( AppConstants.HOME_PERSONAL_POSSESSION_RISK_TYPE ))) ||
							(((inputDto.getRiskCodes().getRiskCode().equals( AppConstants.HOME_BUILDING_RISK_CODE )) && (inputDto.getRiskCodes().getRiskType().equals( AppConstants.HOME_BUILDING_RISK_TYPE ))))) &&
							(Utils.isEmpty( savedSIValue ))){
						column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DISABLED, DISABLED ) );
					}
					column.append( "/>" );
				}
				else if( !profile.equalsIgnoreCase( com.Constant.CONST_BROKER ) ){
					column.append( "</td><td " );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_TEXTR_RSACOMMONTDWIDTH145 ) );
					column.append( ">" );

					column.append( com.Constant.CONST_INPUT_END );
					column.append( String.format( ATTR_TEMPLATE, "type", "text" ) );
					column.append( String.format( ATTR_TEMPLATE, " id", (fieldId+ com.Constant.CONST_DISCOUNT_END+rowNum+"]" ) ));
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DOJOTYPE, com.Constant.CONST_DIJIT_FORM_VALIDATIONTEXTBOX ) );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_NAME, (fieldId+ com.Constant.CONST_DISCOUNT_END+rowNum+"]" )));
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_MAXLENGTH, "10" ) );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, resultMap.get( "disc" ) ) );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, "homeDiscount rsaRightAlign" ) );
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_ONCHANGE, ("onChangeDiscountActionField(this)") ) );
					if((((inputDto.getRiskCodes().getRiskCode().equals( AppConstants.HOME_CONTENTS_RISK_CODE)) && (inputDto.getRiskCodes().getRiskType().equals( AppConstants.HOME_PERSONAL_POSSESSION_RISK_TYPE ))) ||
							(((inputDto.getRiskCodes().getRiskCode().equals( AppConstants.HOME_BUILDING_RISK_CODE )) && (inputDto.getRiskCodes().getRiskType().equals( AppConstants.HOME_BUILDING_RISK_TYPE ))))) &&
							(Utils.isEmpty( savedSIValue ))){
						column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DISABLED, DISABLED ) );
					}
					column.append( "/>" );
				}
				column.append( com.Constant.CONST_TD_END );

			}
			if((inputDto.getCoverCodes().getCovCode() == 1) && (inputDto.getRiskCodes().getRiskCode().equals( AppConstants.HOME_CONTENTS_RISK_CODE ) )){
				//add list of item button
				
				String btnLabel = null;
				String BAHRAIN_LOC="50";
				
				if (BAHRAIN_LOC.equalsIgnoreCase(location)) {
					btnLabel = inputDto.getRiskCodes().getRiskType().equals( AppConstants.HOME_PERSONAL_POSSESSION_RISK_TYPE ) ? com.Constant.CONST_LIST_ITEM_END + Currency.getUnitName() + " 250 &nbsp;" : com.Constant.CONST_LIST_ITEM_END + Currency.getUnitName() + " 500";
				}else {
					if(AppConstants.EMIRATES_HOME_TARIFF.toString().equals(scheme) || oldContentPPFlag)
					{
						btnLabel = inputDto.getRiskCodes().getRiskType().equals( AppConstants.HOME_PERSONAL_POSSESSION_RISK_TYPE ) ? com.Constant.CONST_LIST_ITEM_END + Currency.getUnitName() + " 5,000 &nbsp;" : com.Constant.CONST_LIST_ITEM_END + Currency.getUnitName() + " 20,000";
					}
					else
					{
						btnLabel = inputDto.getRiskCodes().getRiskType().equals( AppConstants.HOME_PERSONAL_POSSESSION_RISK_TYPE ) ? com.Constant.CONST_LIST_ITEM_END + Currency.getUnitName() + " 10,000 &nbsp;" : com.Constant.CONST_LIST_ITEM_END + Currency.getUnitName() + " 40,000";
					}
				}
				
				column.append( "<td " );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, "genconrolbtn" ) );
				column.append( ">" );

				column.append( "<button " );
				column.append( String.format( ATTR_TEMPLATE, "type", "button" ) );				
				column.append( String.format( ATTR_TEMPLATE, " id", (fieldId+ "listItemBtn["+rowNum+"]" )) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DOJOTYPE, "dijit.form.Button" ) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_NAME, (fieldId+ "listItemBtn["+rowNum+"]" )) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, "listItemClass") );
				if((inputDto.getCoverCodes().getCovCode() == 1) && (inputDto.getRiskCodes().getRiskType().equals( AppConstants.HOME_PERSONAL_POSSESSION_RISK_TYPE )) && (Utils.isEmpty( savedSIValue ))){
					column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DISABLED, DISABLED ) );
				}
				//Create method arguments of format method(rowNum, covCode~basicRskCode~riskCode~riskType~riskCat)
				
				String onClickMethod = ("\"listitem(\'"+rowNum+"\', \'"
					+ inputDto.getCoverCodes().getCovCode() + "~"
					+ inputDto.getRiskCodes().getBasicRskCode() + "~" 
					+ inputDto.getRiskCodes().getRiskCode() + "~"
					+ inputDto.getRiskCodes().getRiskType() + "~"
					+ inputDto.getRiskCodes().getRiskCat()+"\');\"");
				onClickMethod = ("listitem("+rowNum+");");

				column.append( String.format( ATTR_TEMPLATE, " onclick", onClickMethod ) );
				column.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_STYLE,	" width:100px; background: url("
								+ "/pas/static/img/buttonBg.jpg) repeat-x; color:white; width:130px;"));
				column.append( com.Constant.CONST_START_LABEL_END );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_RSATABLELINEHEIGHT ) );
				column.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE, "color:white" ) );
				column.append( ">"+ btnLabel +"</label></button></td>" );
			}
			else
				column.append( "<td></td>" );
		}
		catch( Exception exp ){
			exp.printStackTrace();
		}
		return column;
	}
	
	private StringBuffer createElement(String type, String id, String dojoType, String len, String value){
		StringBuffer element = new StringBuffer(); 
		element.append( com.Constant.CONST_INPUT_END );
		element.append( String.format( ATTR_TEMPLATE, "type", type ) );
		element.append( String.format( ATTR_TEMPLATE, " id", id ) );
		if(dojoType != null)
			element.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DOJOTYPE, dojoType ) );
		element.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_NAME, id ));
		if(len != null)
			element.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_MAXLENGTH, len ) );
		element.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, value ) );
		element.append( "/>" );
		return element;
	}

	private StringBuffer createOwnershipStatus( HashMap<String, Object> attributeList, Short value){
		
		List<CoverDetailsVO> coverDetailsList = null;
		StringBuffer element = new StringBuffer();
		boolean ownerShipFlag = false;
		//String selectedValue = null;
		String lookupCode = "0";
		
		coverDetailsList = ( (CoverDetails) attributeList.get( com.Constant.CONST_COVERDETAILS ) ).getCoverDetails();
		
		for( int i = 0; i < coverDetailsList.size(); i++ ){
			if((coverDetailsList.get( i ).getCoverCodes().getCovCode() == 1) && (coverDetailsList.get( i ).getRiskCodes().getRiskCode().equals( AppConstants.HOME_BUILDING_RISK_CODE ))){
				if (value != null){
					lookupCode = value.toString();
				}
				element.append( "<td " );
				element.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_TEXTR_RSACOMMONTDWIDTH145 ) );
				element.append( com.Constant.CONST_START_LABEL_END );
				element.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_RSATABLELINEHEIGHT ) );
				element.append( ">Ownership status" );
				element.append( "</label>" );
				element.append( com.Constant.CONST_TD_END );
				element.append( "<td " );
				element.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_TEXTL_RSACOMMONTDWIDTH145 ) );
				element.append( ">" );
				element.append( createDropdown("ownershipStatus", "PAS_OWNRST","ALL","ALL", lookupCode, null, "onChangeOwnershipStatus()", "false"));
				element.append( com.Constant.CONST_TD_END );
				ownerShipFlag = true;
				break;
			}
				
			}
		if(!ownerShipFlag){
			element.append( "<td colspan='2'></td>" );
		}
		return element;
	}
	
	private StringBuffer createDropdown(String tagId, String lookUpIdentifier, String level1, String level2, String lookUpCode, String value, String onChange, String disabledFlag){
		
		StringBuffer element = new StringBuffer();
		
		DropDownHTMLRenderer dropDownRenderer = new DropDownHTMLRenderer();
		HashMap<String,Object> attributeList = new HashMap<String, Object>();
		//Radar fix
		HashMap<String,Object> responseAttributeList = null; //new HashMap<String, Object>();
		
		attributeList.put(AppConstants.INPUTTYPE, "dropdown");
		attributeList.put(AppConstants.TAGNAME, tagId);
		attributeList.put(AppConstants.TAGID, tagId);
		attributeList.put(AppConstants.IDENTIFIER, lookUpIdentifier);	//HOME_DRPDWN
		attributeList.put("level1",level1);
		attributeList.put("level2", level2);
		attributeList.put(AppConstants.CODE,lookUpCode);
		attributeList.put(AppConstants.VALUE,value);
		attributeList.put(AppConstants.ON_CHANGE_EVENT, onChange);
		attributeList.put(AppConstants.DISABLEDFLAG,disabledFlag);
		attributeList.put(AppConstants.SESSIONDATA, pageContext.getSession());
		
		responseAttributeList = dropDownRenderer.getDropdownValues( attributeList );
		if(responseAttributeList.get( "status" ).toString().equals( AppConstants.TRUE )){
			element.append( (StringBuffer)responseAttributeList.get( "responseString" ));
		}
		else{
			element.append("<select ");  
			element.append(String.format(ATTR_TEMPLATE,"name","emptyList"));  
			element.append(String.format(ATTR_TEMPLATE,"dojoType",DOJOTYPESELECT));
			
			element.append(String.format(OPTION_TEMPLATE,"","Select"));
			element.append('>');
		}
		return element;
	}
	


	private Map<String, CoverDetailsVO> getCoversMap(List<CoverDetailsVO> coverDetails){
		Map<String, CoverDetailsVO> coverMap = new HashMap<String, CoverDetailsVO>();
		for (CoverDetailsVO coverDetailsVO : coverDetails){
			
			String riskCat = !Utils.isEmpty( coverDetailsVO.getRiskCodes().getRiskCat() )? coverDetailsVO.getRiskCodes().getRiskCat().toString() : "0";
			if(!Utils.isEmpty( coverDetailsVO.getRiskCodes() ) && !Utils.isEmpty( coverDetailsVO.getCoverCodes() )){
			String key = coverDetailsVO.getRiskCodes().getRiskCode().toString() + 
							coverDetailsVO.getRiskCodes().getRiskType().toString() + riskCat + 
								String.valueOf( coverDetailsVO.getCoverCodes().getCovCode()) /*+
									String.valueOf( coverDetailsVO.getCoverCodes().getCovTypeCode())*/;
				coverMap.put( key, coverDetailsVO );
			}
		}
		return coverMap;
	}
	
	
	private CoverDetailsVO getCoverFromMap (HashMap<String, CoverDetailsVO> coverMap, CoverDetailsVO coverVO){
		CoverDetailsVO matchingObject = null;
		String key = coverVO.getRiskCodes().getRiskCode().toString() +
						coverVO.getRiskCodes().getRiskType().toString() + 
						coverVO.getRiskCodes().getRiskCat().toString() +
							String.valueOf( coverVO.getCoverCodes().getCovCode()) /*+
								String.valueOf( coverVO.getCoverCodes().getCovTypeCode())*/;
		matchingObject = coverMap.get( key );
		return matchingObject;
	}
	
	

	private Map<String, Object> getSelectedValues(CoverDetailsVO coverVO, String field){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if(!Utils.isEmpty( coverVO )){
			if (((field.equals( "SI" ))) && (!Utils.isEmpty( coverVO.getSumInsured())) && (!Utils.isEmpty( coverVO.getSumInsured().getSumInsured() ))){
				String SI = (BigDecimal.valueOf( coverVO.getSumInsured().getSumInsured().longValue() ) ).toString();
				resultMap.put( "SI", (SI.split(  "\\."  ))[0]);
			}	
			else if ((!Utils.isEmpty( coverVO.getSumInsured())) && (!Utils.isEmpty( coverVO.getSumInsured().geteDesc()))) {
				resultMap.put( "SI", coverVO.getSumInsured().geteDesc() );
			}
			resultMap.put( com.Constant.CONST_CHKBOX, "checked" );
			if (!Utils.isEmpty( coverVO.getPremiumAmt())) {
				resultMap.put( com.Constant.CONST_PREMIUM, Currency.getFormattedCurrency( coverVO.getPremiumAmt(),LOB.HOME.name() ));
			}
			if (!Utils.isEmpty( coverVO.getPremiumAmtActual())) {
				resultMap.put( com.Constant.CONST_ACTUALPREMIUM, Currency.getFormattedCurrency( coverVO.getPremiumAmtActual(),LOB.HOME.name() ));
			}
			if (!Utils.isEmpty( coverVO.getDiscOrLoadPerc())) {
				resultMap.put( "disc", coverVO.getDiscOrLoadPerc());
			}
		}
		else{
			resultMap.put( "SI", null);
			resultMap.put( com.Constant.CONST_CHKBOX, null );
			resultMap.put( com.Constant.CONST_PREMIUM, "");
			resultMap.put( "disc", "");
			resultMap.put( com.Constant.CONST_ACTUALPREMIUM, "" );
		}
		return resultMap;

	}
	@Override
	public void buildEmptyControl( JspWriter out ) throws IOException{
		//No implementation required.
	}

}
