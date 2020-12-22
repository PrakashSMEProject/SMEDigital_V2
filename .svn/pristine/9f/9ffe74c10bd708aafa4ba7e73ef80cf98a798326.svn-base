/**
 * Class for rendering the data in Risk Cover tag. 
 * Risk cover tag helps in generating dynamic risk cover page based on the 
 * configuration done in scheme-tariff table in DB.
 * Render is a helper class to generate the form elements
 * Created in Phase 3 for Home Insurance
 */
package com.rsaame.pas.b2c.taglib.helper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.lookup.ui.DropDownHTMLRenderer;
import com.rsaame.pas.b2c.lookup.ui.IHtmlRenderer;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.FieldType;
import com.rsaame.pas.vo.bus.CoverDetails;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.LOB;

/**
 * @author Sarath Varier
 * @since Phase 3
 *
 */
public class RiskCoverTagRenderer implements IHtmlRenderer{
	private final static Logger LOGGER = Logger.getLogger( RiskCoverTagRenderer.class);
	private static final String ATTR_TEMPLATE = "%s='%s'";
	private static final String DISABLED = "disabled";
	private static final String READONLY = "readonly";
	private static final String OPTION_TEMPLATE_SELECTED = "<option value='%s' selected=\"selected\">%s</option>";
	private static final String DOJOTYPESELECT = "dijit.form.FilteringSelect";
	private static final String OPTION_TEMPLATE = "<option value='%s' >%s</option>";
	PageContext pageContext = null;
	String scheme = null;
	String tariff = null;
	short docCode = 0;
	Boolean autoPopulateRiskType32Flag;
	HashMap<String, Object> attributeGlobalList; 
	Boolean oldContentPPFlag;

	@Override
	public void buildHTMLContent( HashMap<String, Object> attributeList ) throws IOException{

		JspWriter jspWriter = null;
		StringBuffer responseString = null;
		List<CoverDetailsVO> coverDetailsList = null;
		//Short ownerShipStatus = null;		/* commented unused variable and initialisation in if condition for this variable - sonar violation fix */

		try{
			attributeGlobalList = attributeList;
			pageContext = (PageContext) attributeList.get( "pageContext" );
			jspWriter = pageContext.getOut();
			scheme = attributeList.get( "scheme" ).toString();
			tariff = attributeList.get( "tariff" ).toString();
			oldContentPPFlag = new Boolean(attributeList.get( "oldContentPPFlag" ).toString());

			if(!Utils.isEmpty( attributeList.get( "docCode" )  ) ){ 
				docCode = (Short) attributeList.get( "docCode" );//added
			}
			else{
				docCode = 0;
			}
			
			/*if( !Utils.isEmpty( attributeList.get( "ownerShipStatus" ) ) ){		// commented unused variable and initialisation in if condition for this variable - sonar violation fix
				ownerShipStatus = (Short) attributeList.get( "ownerShipStatus" );
			}*/
			responseString = new StringBuffer();
			coverDetailsList = ( (CoverDetails) attributeList.get( "coverDetails" ) ).getCoverDetails();
			Short coverCode = ( ( attributeList.get( "coverCode" ) == null ) || ( ( attributeList.get( "coverCode" ).toString() == "" ) ) ) ? 0 : Short.valueOf( attributeList.get(
					"coverCode" ).toString() );

			if( coverDetailsList.size() > 0 ){
				responseString.append( "<ul " );
				responseString.append( String.format( ATTR_TEMPLATE, "class", "home-type" ) );
				responseString.append( " >" );
				responseString.append( createDynamicLi( attributeList ) );
				responseString.append( " </ul>" );
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

	private StringBuffer createDynamicLi( HashMap<String, Object> attributeList ){

		StringBuffer tableRow = null;
		List<CoverDetailsVO> coverDetailsList = null;
		int counter = -1;
		List<CoverDetailsVO> savedCovers = null;
		HashMap<String, CoverDetailsVO> coverMap = null;
		List<Short> promoCodes = null;
		Boolean freeCover = false;
		autoPopulateRiskType32Flag = false;
		List<Object> columnDetails = null;
		try{

			tableRow = new StringBuffer();
			coverDetailsList = ( (CoverDetails) attributeList.get( "coverDetails" ) ).getCoverDetails();
			Short coverCode = ( ( attributeList.get( "coverCode" ) == null ) || ( attributeList.get( "coverCode" ).toString() == "" ) ) ? 0 : Short.valueOf( attributeList.get(
					"coverCode" ).toString() );
			Short excludeCoverCode = ( ( attributeList.get( "excludeCoverCode" ) == null ) || ( attributeList.get( "excludeCoverCode" ).toString() == "" ) ) ? 0 : Short
					.valueOf( attributeList.get( "excludeCoverCode" ).toString() );

			if( !Utils.isEmpty( (List<CoverDetailsVO>) attributeList.get( "covers" ) ) ){
				savedCovers = (List<CoverDetailsVO>) attributeList.get( "covers" );
			}
			if( !Utils.isEmpty( (List<Short>) attributeList.get( "promoCodes" ) ) ){
				promoCodes = (List<Short>) attributeList.get( "promoCodes" );
			}

			if( !Utils.isEmpty( savedCovers ) ) coverMap = (HashMap<String, CoverDetailsVO>) getCoversMap( savedCovers );
			
			boolean isBuildingSelected = false;
			boolean isContentSelected = false;
			boolean isPersPossSelected = false;
			boolean isAdditCovSelected = false;
			boolean isLossOdDoc = false;
			boolean isEmpLiab = false;
			boolean isTenantLiab = false;
			
			if(!Utils.isEmpty(coverMap)){
				isBuildingSelected = coverMap.containsKey("1101")? true:false;
				isContentSelected = coverMap.containsKey("23111")? true:false;
				isPersPossSelected = coverMap.containsKey("23211")? true:false;
				isLossOdDoc = coverMap.containsKey("23112")? true:false;
				isEmpLiab= coverMap.containsKey("23113")? true:false;
				isTenantLiab = coverMap.containsKey("23114")? true:false;
				
				if(isLossOdDoc || isEmpLiab || isTenantLiab){
					isAdditCovSelected = true;
				}
			}
			
			//Map<String, Boolean> toBeCheckedCoversMap = new HashMap<String, Boolean>();		/* Commented unused variable - sonar violation fix */
			List<String> toBeCheckedCovers = new ArrayList<String>();
			int listCount = 0;
			if( !Utils.isEmpty( savedCovers ) ){
				for (CoverDetailsVO coverDetailsVO : savedCovers) {
					if(!Utils.isEmpty(coverDetailsVO.getRiskCodes().getRiskType()) && !Utils.isEmpty(coverDetailsVO.getCoverCodes()) && !Utils.isEmpty(coverDetailsVO.getCoverCodes().getCovCode())){
						toBeCheckedCovers.add(coverDetailsVO.getRiskCodes().getRiskType()+"~"+coverDetailsVO.getCoverCodes().getCovCode());
					}
					
					if(!Utils.isEmpty(coverDetailsVO.getRiskCodes().getRiskCat()) && coverDetailsVO.getRiskCodes().getRiskCat()==2){
						listCount++;
					}
				}
			}
			
			if( excludeCoverCode == 0 ){
				for( int i = 0; i < coverDetailsList.size(); i++ ){

					if( ( ( ( coverDetailsList.get( i ).getCoverCodes().getCovCode() ) == coverCode ) || ( coverCode == 0 ) )
							&& ( ( coverDetailsList.get( i ).getCoverCodes().getCovCode() ) != excludeCoverCode ) ){
						
						if(coverDetailsList.get( i ).getRiskCodes().getRiskType() != 1){
							counter++;
						}

						int j = 0;
						if( coverDetailsList.get( i ).getRiskCodes().getRiskType().equals( AppConstants.HOME_BUILDING_RISK_TYPE ) ){
							tableRow.append( "<li " );

							if((!Utils.isEmpty(toBeCheckedCovers) && toBeCheckedCovers.contains(coverDetailsList.get( i ).getRiskCodes().getRiskType()+"~"+coverDetailsList.get( i ).getCoverCodes().getCovCode()))){
								tableRow.append( String.format( ATTR_TEMPLATE, "class", "ownership-building active" ) );
							}else{
								tableRow.append( String.format( ATTR_TEMPLATE, "class", "ownership-building" ) );
							}
											
							tableRow.append( ">" );
							tableRow.append( "<fieldset> " );
							tableRow.append( "<input " );

							tableRow.append( String.format( ATTR_TEMPLATE, "type", "checkbox" ) );
							
							//TODO Test for all the scenarios Start - ONLY FOR BUILDING CHECK BOX
							if (i==0 && tableRow.toString().contains("checkbox")) {
								tableRow.append(String.format(ATTR_TEMPLATE, "id", "building_checkBox"));
								//tableRow.append("id='building_checkBox'");
							}
							//TODO Test for all the scenarios End
							
							if(!Utils.isEmpty(toBeCheckedCovers) && toBeCheckedCovers.contains(coverDetailsList.get( i ).getRiskCodes().getRiskType()+"~"+coverDetailsList.get( i ).getCoverCodes().getCovCode())){
								tableRow.append( " checked");
							}

							tableRow.append( " /> " );
							tableRow.append( "<label >" );
							tableRow.append( "Building" );
							tableRow.append( "</label>" );
							tableRow.append( "</fieldset> " );
							tableRow.append( "<div " );
							tableRow.append( String.format( ATTR_TEMPLATE, "class", "box-content" ) );
							tableRow.append( ">" );
						}
						for( j = i; j < coverDetailsList.size(); j++ ){
							if( coverDetailsList.get( j ).getCoverCodes().getCovCode() == coverDetailsList.get( i ).getCoverCodes().getCovCode() ){
								CoverDetailsVO coverVO = null;
								if( !Utils.isEmpty( coverMap ) ){
									coverVO = getCoverFromMap( coverMap, coverDetailsList.get( j ) );
								}
								freeCover = false;
								if( ( !Utils.isEmpty( promoCodes ) ) && ( promoCodes.contains( coverDetailsList.get( j ).getCoverCodes().getCovCode() ) ) ){
									freeCover = true;
								}
								columnDetails = new ArrayList<Object>();
								columnDetails.add( coverDetailsList.get( j ) );
								columnDetails.add( counter );
								columnDetails.add( coverVO );
								columnDetails.add( freeCover );
								columnDetails.add( savedCovers );

								counter = createDynamicLiData( columnDetails,tableRow );

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
						else{
							i = j;
						}

						if( coverDetailsList.get( i ).getRiskCodes().getRiskType().equals( AppConstants.HOME_BUILDING_RISK_TYPE ) ){
							tableRow.append( " </li>" );
							tableRow.append( "<li " );
							
							/*if(!Utils.isEmpty(toBeCheckedCovers) && toBeCheckedCovers.contains(coverDetailsList.get( i ).getRiskCodes().getRiskType()+"~"+coverDetailsList.get( i ).getCoverCodes().getCovCode())){
								tableRow.append( String.format( ATTR_TEMPLATE, "class", "ownership-content active" ) );
							}else{
								tableRow.append( String.format( ATTR_TEMPLATE, "class", "ownership-content" ) );
							}*/
							
							if(isPersPossSelected || isContentSelected ){
								tableRow.append( String.format( ATTR_TEMPLATE, "class", "ownership-content active" ) );
							}else{
								tableRow.append( String.format( ATTR_TEMPLATE, "class", "ownership-content" ) );
							}
							
							tableRow.append( ">" );
							tableRow.append( "<fieldset> " );
							tableRow.append( "<input " );
							tableRow.append( String.format( ATTR_TEMPLATE, "type", "checkbox" ) );
							
							if(coverDetailsList.get( i ).getRiskCodes().getRiskType().equals( AppConstants.HOME_PERSONAL_POSSESSION_RISK_TYPE )){
								if(isPersPossSelected){
									tableRow.append( " checked");
								}
							}else{
								if(isContentSelected){
									tableRow.append( " checked");
								}
								tableRow.append(String.format(ATTR_TEMPLATE, " id", "content_checkBox"));
							}

							tableRow.append( " /> " );
							tableRow.append( "<label >" );
							tableRow.append( "Content" );
							tableRow.append( "</label>" );
							tableRow.append( "</fieldset> " );
							tableRow.append( "<div " );
							tableRow.append( String.format( ATTR_TEMPLATE, "class", "box-content" ) );
							tableRow.append( ">" );
						}
					}
				}

			}
			else{

				tableRow.append( "<li " );
				if(isAdditCovSelected){
					tableRow.append( String.format( ATTR_TEMPLATE, "class", "active" ) );

				}
				tableRow.append( ">" );
				tableRow.append( "<fieldset> " );
				tableRow.append( "<input " );
				tableRow.append( String.format( ATTR_TEMPLATE, "type", "checkbox" ) );
				if(isAdditCovSelected){
					tableRow.append( " checked");
				}
				tableRow.append( " /> " );
				tableRow.append( "<label >" );
				tableRow.append( "Optional Extras:" );
				tableRow.append( "</label>" );
				tableRow.append( "</fieldset> " );
				tableRow.append( "<div " );
				tableRow.append( String.format( ATTR_TEMPLATE, "class", "box-content" ) );
				tableRow.append( ">" );
				tableRow.append( "<fieldset " );
				tableRow.append( String.format( ATTR_TEMPLATE, "class", "radio-group optional" ) );
				tableRow.append( " >" );
				
				/*if(listCount!=0){
					counter=listCount;
				}*/
				counter = -1;
				for( int i = 0; i < coverDetailsList.size(); i++ ){
					Boolean createHiddenField = true;
					Boolean isCovered = Boolean.FALSE;
					if(coverDetailsList.get( i ).getRiskCodes().getRiskType() != 1 && coverDetailsList.get( i ).getRiskCodes().getRiskCat() != 2){
						counter++;
					}
					
					if( ( ( ( coverDetailsList.get( i ).getCoverCodes().getCovCode() ) == coverCode ) || ( coverCode == 0 ) )
							&& ( ( coverDetailsList.get( i ).getCoverCodes().getCovCode() ) != excludeCoverCode ) ){
						int j = 0;

						tableRow.append( "<div " );
						//added checked
						if(docCode == 6 || docCode == 2){
							tableRow.append( String.format( ATTR_TEMPLATE, "class", "radio-item checked" ) );
						}
						else{
							tableRow.append( String.format( ATTR_TEMPLATE, "class", "radio-item" ) );
						}						
						tableRow.append( " >" );

						tableRow.append( "<input " );
						tableRow.append( String.format( ATTR_TEMPLATE, "type", "checkbox" ) );
						tableRow.append( String.format( ATTR_TEMPLATE, "name", "covers[" + counter + "].isCovered" ) );
						if(!Utils.isEmpty(toBeCheckedCovers) && toBeCheckedCovers.contains(coverDetailsList.get( i ).getRiskCodes().getRiskType()+"~"+coverDetailsList.get( i ).getCoverCodes().getCovCode())){
							tableRow.append( " checked");
							isCovered = Boolean.TRUE;
						}
						tableRow.append( " />" );

						tableRow.append( "<label>" );
						tableRow.append( coverDetailsList.get( i ).getCoverName() );
						tableRow.append( "</label>" );

						tableRow.append( "<p class=\"smallipopWhite\">?<span class=\"smallipopHint\"><span>" );

						tableRow.append(coverDetailsList.get(i).getCoverDesc());

						tableRow.append( "</span><em>x</em></span>" + "</p>" );
						
						//Added here
						if(docCode == 6 || docCode == 2){
							if( coverDetailsList.get( i ).getCoverCodes().getCovCode() != 1 ){
								//tableRow.append("<label ");
								/*tableRow.append( String.format( ATTR_TEMPLATE, "class", "extras-label" ) );
								tableRow.append("> ");*/
								if(isCovered ){
									tableRow.append("<label ");
									tableRow.append( String.format( ATTR_TEMPLATE, "class", "extras-label" ) );
									tableRow.append("> ");
									if(coverDetailsList.get( i ).getCoverCodes().getCovCode() == 3){
										tableRow.append( "Number Of Staff:" );
									}
									else if(coverDetailsList.get( i ).getCoverCodes().getCovCode() == 4){
										tableRow.append( "Limit: ");
									}
									tableRow.append( "</label>" );
								}
								//tableRow.append( "</label>" );
							}
						}
						//

						for( j = i+1; j < coverDetailsList.size(); j++ ){
							if( coverDetailsList.get( j ).getCoverCodes().getCovCode() == coverDetailsList.get( i ).getCoverCodes().getCovCode() ){
								CoverDetailsVO coverVO = null;
								if( !Utils.isEmpty( coverMap ) ){
									coverVO = getCoverFromMap( coverMap, coverDetailsList.get( j ) );
								}
								freeCover = false;
								if( ( !Utils.isEmpty( promoCodes ) ) && ( promoCodes.contains( coverDetailsList.get( j ).getCoverCodes().getCovCode() ) ) ){
									freeCover = true;
								}
								columnDetails = new ArrayList<Object>();
								columnDetails.add( coverDetailsList.get( i ) );
								columnDetails.add( counter );
								columnDetails.add( coverVO );
								columnDetails.add( freeCover );
								
								columnDetails.add( coverDetailsList.get( j ) );
								columnDetails.add(toBeCheckedCovers);

								tableRow.append( createHtmlElements( columnDetails ));
								createHiddenField = false;
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
						else{
							i = j;
						}
						
						if(createHiddenField){
							
							String fieldId = "covers[" + counter + "].sumInsured.sumInsured";
							String fieldPath = "covers[" + counter + "].";
							CoverDetailsVO coverVO = null;
							if( !Utils.isEmpty( coverMap ) ){
								coverVO = getCoverFromMap( coverMap, coverDetailsList.get( i ) );
							}
								
							tableRow.append( createElement( "hidden","covers_" + counter + "_coverName", "covers[" + counter + "].coverName", null, null, coverDetailsList.get( i ).getCoverName() ) );
							tableRow.append( createElement( "hidden","covers_" +counter + "_riskCode", fieldPath + "riskCodes.riskCode", null, null, coverDetailsList.get( i ).getRiskCodes().getRiskCode().toString() ) );
							tableRow.append( createElement( "hidden","covers_" +counter + "_riskCat", fieldPath + "riskCodes.riskCat", null, null, coverDetailsList.get( i ).getRiskCodes().getRiskCat().toString() ) );
							tableRow.append( createElement( "hidden","covers_" +counter + "_riskType",fieldPath + "riskCodes.riskType", null, null, coverDetailsList.get( i ).getRiskCodes().getRiskType().toString() ) );
							tableRow.append( createElement( "hidden","covers_" +counter + "_basicRskCode", fieldPath + "riskCodes.basicRskCode", null, null, coverDetailsList.get( i ).getRiskCodes().getBasicRskCode().toString() ) );
							tableRow.append( createElement( "hidden","covers_" +counter + "_covCode", fieldPath + "coverCodes.covCode", null, null, String.valueOf( coverDetailsList.get( i ).getCoverCodes().getCovCode() ) ) );
							tableRow.append( createElement( "hidden","covers_" +counter + "_covTypeCode", fieldPath + "coverCodes.covTypeCode", null, null, String.valueOf( coverDetailsList.get( i ).getCoverCodes().getCovTypeCode() ) ) );
							tableRow.append( createElement( "hidden","covers_" +counter + "_promoCover", fieldPath + "sumInsured.promoCover", null, null, freeCover.toString() ) );
							

							if( !Utils.isEmpty( coverVO ) ){
								
								tableRow.append( createElement( "hidden","covers_" +counter + "_rskId", fieldPath + "riskCodes.rskId", null, null, !Utils.isEmpty(coverVO.getRiskCodes().getRskId())?coverVO.getRiskCodes().getRskId().toString():"" ) );
								tableRow.append( createElement( "hidden","covers_" +counter + "_vsd", fieldPath + "vsd", null, null, !Utils.isEmpty(coverVO.getVsd())?coverVO.getVsd().toString():"") );
								tableRow.append( createElement( "hidden","covers_" +counter + "_discOrLoadPerc", fieldPath + "discOrLoadPerc", null, null, String.valueOf(coverDetailsList.get( i ).getDiscOrLoadPerc() )) );
							}
						}
						tableRow.append( "</div>" );
						
					}
				}
				tableRow.append( "</fieldset>" );

			}
			tableRow.append( "</div></li>" );
		}
		catch( Exception exp ){
			exp.printStackTrace();
		}

		return tableRow;

	}

	private int createDynamicLiData( List<Object> columnDetails, StringBuffer tableRow ){

		
		String fieldId = null;
		Map<String, Object> resultMap = null;
		String savedSIValue = null;
		CoverDetailsVO inputDto = null;
		int rowNum = 0;
		CoverDetailsVO coverVO = null;
		//Boolean freeCover = null;		/* commented unused variable and its initialization - sonar violation fix */
		String profile = null;
		//List<CoverDetailsVO> savedCovers = null;		/* commented unused variable and its initialization - sonar violation fix */
		try{

			inputDto = (CoverDetailsVO) columnDetails.get( 0 );
			rowNum = Integer.parseInt( columnDetails.get( 1 ).toString() ); 
			coverVO = (CoverDetailsVO) columnDetails.get( 2 );
			//freeCover = (Boolean) columnDetails.get( 3 );		/* commented unused variable and its initialsation - sonar violation fix */
			//savedCovers = (List<CoverDetailsVO> )columnDetails.get(4);	/* commented unused variable and its initialsation - sonar violation fix */
			//profile = (String) columnDetails.get( 4 );

			resultMap = getSelectedValues( coverVO, inputDto.getMappingField() );
			tableRow.append( "<fieldset " );
			tableRow.append( String.format( ATTR_TEMPLATE, "class",
					Utils.getSingleValueAppConfig( inputDto.getRiskCodes().getRiskType() + "_" + inputDto.getCoverCodes().getCovCode() + "_fieldset_dyn_class_tag" ) ) );
			tableRow.append( " >" );
			tableRow.append( "<label>" );
			tableRow.append( inputDto.getCoverName() + " Value:" );
			tableRow.append( "</label>" );

			savedSIValue = resultMap.get( "SI" ) != null ? resultMap.get( "SI" ).toString() : "";

			/**
			 * Dynamic one, it can be text box,drop down and check box.
			 */

			tableRow.append( createHtmlElements( columnDetails ) );

			tableRow.append( "<p class=\"smallipopWhite\">?<span class=\"smallipopHint\"><span>" );
			tableRow.append( inputDto.getCoverDesc() );
			tableRow.append( "</span><br><span></span><em>x</em></span>" + "</p>" );
			String listId = null;
			if(!inputDto.getRiskCodes().getRiskType().equals( AppConstants.HOME_BUILDING_RISK_TYPE )){
				if(inputDto.getRiskCodes().getRiskType().equals( 31 )){
					listId = "sheepItForm1";
				}else{
					listId = "sheepItForm2";
				}
			}

			if( !inputDto.getRiskCodes().getRiskType().equals( AppConstants.HOME_BUILDING_RISK_TYPE ) ){
				
								
					tableRow.append( "<a " );
					tableRow.append( String.format( ATTR_TEMPLATE, " href", "#" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " class", "action-add" ) );
					tableRow.append( ">List Item</a>" );
					
					if(docCode == 6 || docCode == 2){
						tableRow.append( "<a " );
						tableRow.append( String.format( ATTR_TEMPLATE, " href", "#" ) );
						tableRow.append( String.format( ATTR_TEMPLATE, " class", "action-view" ) );
						tableRow.append( ">View List</a>" );
					}

					tableRow.append( "<div " );
					tableRow.append( String.format( ATTR_TEMPLATE, "class", "item-wrapper" ) );
					tableRow.append( ">" );
					
					if(inputDto.getRiskCodes().getRiskType().equals( 31 )){
						if(AppConstants.EMIRATES_HOME_TARIFF.toString().equals(scheme) || oldContentPPFlag)
						{
							tableRow.append( "<h6>List Single Items >= AED 20,000</h6>" );
						}
						else
						{
							tableRow.append( "<h6>List Single Items >= AED 40,000</h6>" );
						}
					}else{
						if(AppConstants.EMIRATES_HOME_TARIFF.toString().equals(scheme)|| oldContentPPFlag)
						{
							tableRow.append( "<h6>List Single Items >= AED 5,000</h6>" );
						}
						else
						{
							tableRow.append( "<h6>List Single Items >= AED 10,000</h6>" );
						}
					}
					
					tableRow.append( "<div " );
                    tableRow.append( String.format( ATTR_TEMPLATE, "class", "list-error-message" ) );
                    tableRow.append( "></div>" );
                    
					tableRow.append( "<div " );
					tableRow.append( String.format( ATTR_TEMPLATE, "class", "scroll-list" ) );
					tableRow.append( ">" );

					tableRow.append( "<ul " );
					tableRow.append( String.format( ATTR_TEMPLATE, "id", listId ) );
					tableRow.append( ">" );
					
					tableRow.append( "<li " );
					tableRow.append( String.format( ATTR_TEMPLATE, "id", listId+"_template" ) );
					tableRow.append( ">" );

					tableRow.append( "<input " );
					tableRow.append( String.format( ATTR_TEMPLATE, "type", "text" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " id", listId+"_#index#_itemname" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " name", listId+"_Desc[#index#]" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " placeholder", "Item name" ) );
					tableRow.append( " />" );

					tableRow.append( "<input " );
					tableRow.append( String.format( ATTR_TEMPLATE, "type", "text" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " class", "numbersonly" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " id",listId+"_#index#_itemvalue" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " name", listId+"_sumInsured[#index#]" ) );
				
					tableRow.append( String.format( ATTR_TEMPLATE, " placeholder", "Item value in AED" ) );
					tableRow.append( " />" );
					
					
						
					/*tableRow.append( createElement( "hidden","covers_#index#_coverName", listId+"_coverName[#index#]", null, null, inputDto.getCoverName() ) );
					tableRow.append( createElement( "hidden","covers_#index#_promoCover", listId+"_promoCover[#index#]", null, null, freeCover.toString() ) );*/

				
						tableRow.append( createElement( "hidden",listId+"_#index#_rskId",listId+"_rskId[#index#]", null, null, "" ));
						tableRow.append( createElement( "hidden",listId+"_#index#_vsd", listId+"_vsd[#index#]", null, null, "" ) );
					

					tableRow.append( "<div " );
					tableRow.append( String.format( ATTR_TEMPLATE, "class", "action-button" ) );
					tableRow.append( ">" );

					tableRow.append( "<a " );
					tableRow.append( String.format( ATTR_TEMPLATE, "href", "#" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " class", "delete remove-btn" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " id", listId+"_remove_current" ) );
					tableRow.append( ">remove</a>" );

					tableRow.append( "<a " );
					tableRow.append( String.format( ATTR_TEMPLATE, " href", "#" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " class", "add-btn" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " id", listId+"_add" ) );
					tableRow.append( ">add</a>" );

					tableRow.append( "</div>" );

					tableRow.append( "</li>" );
					
					//}
					
					tableRow.append( "<li " );
					tableRow.append( String.format( ATTR_TEMPLATE, " id", listId+"_noforms_template" ) );
					tableRow.append( ">No Item</li>" );
					tableRow.append( "<li " );
					tableRow.append( String.format( ATTR_TEMPLATE, " id", listId+"_controls" ) );
					tableRow.append( " />" );
					
					
					tableRow.append( "</ul>" );

					tableRow.append( "</div>" );
					
					/*if(!Utils.isEmpty(savedCovers)){
						CoverDetailsVO coverDetailsVO = savedCovers.get(0);
						//tableRow.append( createElement("hidden",listId+"_coverName", listId+"_coverName", null, null, coverDetailsVO.getCoverName() ));
						tableRow.append( createElement("hidden",listId+"_riskCode",listId+"_riskCode", null, null, coverDetailsVO.getRiskCodes().getRiskCode().toString() ));
						tableRow.append( createElement("hidden",listId+"_riskCat",listId+"_riskCat", null, null, AppConstants.HOME_LIST_ITEM_RISK_CATEGORY.toString() ));
						tableRow.append( createElement("hidden",listId+"_riskType",listId+"_riskType", null, null, coverDetailsVO.getRiskCodes().getRiskType().toString() ));
						tableRow.append( createElement("hidden",listId+"_basicRskCode",listId+"_basicRskCode", null, null, coverDetailsVO.getRiskCodes().getBasicRskCode().toString() ));
						tableRow.append( createElement("hidden",listId+"_covCode",listId+"_covCode", null, null, String.valueOf( coverDetailsVO.getCoverCodes().getCovCode())));
						tableRow.append( createElement("hidden",listId+"_covTypeCode",listId+"_covTypeCode", null, null, String.valueOf( coverDetailsVO.getCoverCodes().getCovTypeCode()) ));
					}else{*/
						tableRow.append( createElement("hidden",listId+"_riskCode",listId+"_riskCode", null, null, inputDto.getRiskCodes().getRiskCode().toString() ));
						tableRow.append( createElement("hidden",listId+"_riskCat",listId+"_riskCat", null, null, AppConstants.HOME_LIST_ITEM_RISK_CATEGORY.toString() ));
						tableRow.append( createElement("hidden",listId+"_riskType",listId+"_riskType", null, null, inputDto.getRiskCodes().getRiskType().toString() ));
						tableRow.append( createElement("hidden",listId+"_basicRskCode",listId+"_basicRskCode", null, null, inputDto.getRiskCodes().getBasicRskCode().toString() ));
						tableRow.append( createElement("hidden",listId+"_covCode",listId+"_covCode", null, null, String.valueOf( inputDto.getCoverCodes().getCovCode())));
						tableRow.append( createElement("hidden",listId+"_covTypeCode",listId+"_covTypeCode", null, null, String.valueOf( inputDto.getCoverCodes().getCovTypeCode()) ));
					/*}*/

					tableRow.append( "<div " );
					tableRow.append( String.format( ATTR_TEMPLATE, " class", "action-row" ) );
					tableRow.append( ">" );

					tableRow.append( "<input " );
					tableRow.append( String.format( ATTR_TEMPLATE, "type", "button" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " value", "Save" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " class", "btn-save btn-secondary" ) );
					tableRow.append( "/>" );

					tableRow.append( "<input " );
					tableRow.append( String.format( ATTR_TEMPLATE, "type", "button" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " value", "Cancel" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " class", "btn-cancel btn-secondary" ) );
					tableRow.append( "/>" );

					tableRow.append( "</div>" );

					tableRow.append( "</div>" );

			}

			tableRow.append( "</fieldset>" );

		}
		catch( Exception exp ){
			exp.printStackTrace();
		}
		return rowNum;
	}

	private StringBuffer createHtmlElements( List<Object> columnDetails ){

		StringBuffer column = null;
		String savedSIValue = null;
		CoverDetailsVO inputDto = null;
		CoverDetailsVO coverVO = null;
		//Commented the variable to avoid Dead store to local variable , sonar violation on 20-9-2017
	//	column = new StringBuffer();
		Boolean freeCover = null;
		String fieldId = null;
		String fieldName = null;
		String fieldPath = null;
		String fieldIdPart = null;
		CoverDetailsVO additCoverVO = null;
		Map<String, Object> resultMap = null;
		List<String> toBeCheckedCovers = null;

		int rowNum;
		
		

		column = new StringBuffer();
		inputDto = (CoverDetailsVO) columnDetails.get( 0 );
		rowNum = Integer.parseInt( columnDetails.get( 1 ).toString() );
		coverVO = (CoverDetailsVO) columnDetails.get( 2 );
		freeCover = (Boolean) columnDetails.get( 3 );
		//String profile = (String) columnDetails.get( 4 );
		String CATEGORY = null;
		FieldType inputFieldType = null;
		Boolean toDisplay = true;
		String defaultOption = "Select in AED";
		boolean isPrmValue = false;
		if(columnDetails.size() == 6 && !Utils.isEmpty(columnDetails.get( 4 ))){
			additCoverVO = (CoverDetailsVO) columnDetails.get( 4 );
			inputFieldType = additCoverVO.getFieldType();
			CATEGORY = "PAS_HOME_" + additCoverVO.getRiskCodes().getRiskType() + "_" + additCoverVO.getCoverCodes().getCovCode() + "_"
						+ additCoverVO.getCoverCodes().getCovTypeCode();
			resultMap = getSelectedValues( coverVO, additCoverVO.getMappingField() );
			toBeCheckedCovers = (List<String>) columnDetails.get( 5 );
			toDisplay = !toBeCheckedCovers.contains(additCoverVO.getRiskCodes().getRiskType()+"~"+additCoverVO.getCoverCodes().getCovCode())?false:true;
			defaultOption = additCoverVO.getCoverName();
			if (additCoverVO.getMappingField().equals("prmvalue")){
				isPrmValue = true;
			}
			
		}else{
			inputFieldType = inputDto.getFieldType();
			CATEGORY = "PAS_HOME_" + inputDto.getRiskCodes().getRiskType() + "_" + inputDto.getCoverCodes().getCovCode() + "_"
					+ inputDto.getCoverCodes().getCovTypeCode();
			resultMap = getSelectedValues( coverVO, inputDto.getMappingField() );
		}
		

		if( inputDto.getRiskCodes().getRiskType() == 1 ){
			fieldName = "buildingDetails.sumInsured.sumInsured";
			fieldId = "building_si";
			fieldPath = "buildingDetails.";
			fieldIdPart = "building";

		}
		else{
			if(!isPrmValue){
				fieldName = "covers[" + rowNum + "].sumInsured.sumInsured";
			}else{
				fieldName = "covers[" + rowNum + "].sumInsured.eDesc";
			}
			fieldId = "covers_si["+rowNum+"]";
			fieldPath = "covers[" + rowNum + "].";
			fieldIdPart = "covers_" + rowNum;
		}

	
		if( inputFieldType == FieldType.DROP_DOWN ){
			if(inputDto.getRiskCodes().getRiskType()!=1){
				
				column.append( createElement( "hidden",fieldIdPart+ "_coverName", "covers[" + rowNum + "].coverName", null, null, inputDto.getCoverName() ) );
			}
			column.append( createElement( "hidden",fieldIdPart+ "_riskCode", fieldPath + "riskCodes.riskCode", null, null, inputDto.getRiskCodes().getRiskCode().toString() ) );
			column.append( createElement( "hidden",fieldIdPart+ "_riskCat", fieldPath + "riskCodes.riskCat", null, null, inputDto.getRiskCodes().getRiskCat().toString() ) );
			column.append( createElement( "hidden",fieldIdPart+ "_riskType",fieldPath + "riskCodes.riskType", null, null, inputDto.getRiskCodes().getRiskType().toString() ) );
			column.append( createElement( "hidden",fieldIdPart+ "_basicRskCode", fieldPath + "riskCodes.basicRskCode", null, null, inputDto.getRiskCodes().getBasicRskCode().toString() ) );
			column.append( createElement( "hidden",fieldIdPart+ "_covCode", fieldPath + "coverCodes.covCode", null, null, String.valueOf( inputDto.getCoverCodes().getCovCode() ) ) );
			column.append( createElement( "hidden",fieldIdPart+ "_covTypeCode", fieldPath + "coverCodes.covTypeCode", null, null, String.valueOf( inputDto.getCoverCodes().getCovTypeCode() ) ) );
			column.append( createElement( "hidden",fieldIdPart + "_promoCover", fieldPath + "sumInsured.promoCover", null, null, freeCover.toString() ) );


			if( !Utils.isEmpty( coverVO ) ){
					column.append( createElement( "hidden",fieldIdPart+ "_rskId", fieldPath + "riskCodes.rskId", null, null, !Utils.isEmpty(coverVO.getRiskCodes().getRskId())?coverVO.getRiskCodes().getRskId().toString():"" ));					
					column.append( createElement( "hidden",fieldIdPart+ "_vsd", fieldPath + "vsd", null, null, !Utils.isEmpty(coverVO.getVsd())?coverVO.getVsd().toString():"" ) );
					column.append( createElement( "hidden",fieldIdPart + "_discOrLoadPerc", fieldPath + "discOrLoadPerc", null, null, String.valueOf(coverVO.getDiscOrLoadPerc() )) );
			}
			
			String disabledFlag = "false";
			savedSIValue = null;

			if( resultMap.get( "SI" ) != null ){
				savedSIValue = resultMap.get( "SI" ).toString();
			}
			if( ( inputDto.getCoverCodes().getCovTypeCode() != 0 ) && Utils.isEmpty( savedSIValue ) ){
				disabledFlag = freeCover ? "false" : "true";
			}
			else if( ( inputDto.getCoverCodes().getCovCode() == 1 ) && ( inputDto.getRiskCodes().getRiskType().equals( AppConstants.HOME_PERSONAL_POSSESSION_RISK_TYPE ) )
					&& Utils.isEmpty( savedSIValue ) ){
				disabledFlag = "true";
			}

			String[] specialTariffCodes = Utils.getMultiValueAppConfig( AppConstants.HOME_PERSONAL_POSSESSION_SI_TARIFF, "," );

			if( Arrays.asList( specialTariffCodes ).contains( tariff ) ){
				if( !Utils.isEmpty( savedSIValue ) ){
					
					savedSIValue = ( SvcUtils.getLookUpCode( CATEGORY, scheme, tariff, resultMap.get( "SI" ).toString() ) ).toString();
				}
				if( ( inputDto.getRiskCodes().getRiskType().equals( AppConstants.HOME_PERSONAL_POSSESSION_RISK_TYPE ) ) && ( !autoPopulateRiskType32Flag ) ){
					autoPopulateRiskType32Flag = true;
				}

			}

			column.append( createDropdown( fieldId,fieldName, CATEGORY , scheme, tariff, savedSIValue,savedSIValue,  "", "false",toDisplay, defaultOption ) );

		}
		else if( inputFieldType == FieldType.CHECK_BOX ){
			// Added logger to avoid empty else if block (Sonar defect) 12-9-2017
			LOGGER.debug("Empty else if block");
			/*

																	column.append( "<td " );
																	column.append( String.format( ATTR_TEMPLATE, "style", "width: 10px;" ) );
																	column.append( ">" );

																	column.append( "<input " );
																	column.append( String.format( ATTR_TEMPLATE, "id", ( fieldId + "checkBox[" + rowNum + "]" ) ) );
																	column.append( String.format( ATTR_TEMPLATE, " name", ( fieldId + "checkBox[" + rowNum + "]" ) ) );
																	column.append( String.format( ATTR_TEMPLATE, "type", "checkbox" ) );
																	column.append( String.format( ATTR_TEMPLATE, " dojoType", "dijit.form.CheckBox" ) );
																	if( !Utils.isEmpty( resultMap.get( "chkBox" ) ) ){
																	column.append( String.format( ATTR_TEMPLATE, " checked", "checked" ) );
																	if( freeCover ){
																	column.append( String.format( ATTR_TEMPLATE, "readonly", READONLY ) );
																	}
																	}
																	else if( freeCover && Utils.isEmpty( resultMap.get( "chkBox" ) ) ){
																	column.append( String.format( ATTR_TEMPLATE, " checked", "checked" ) );
																	column.append( String.format( ATTR_TEMPLATE, "readonly", READONLY ) );
																	}
																	else{
																	column.append( String.format( ATTR_TEMPLATE, "disabled", DISABLED ) );
																	}
																	column.append( String.format( ATTR_TEMPLATE, " onClick", ( "onCheckAction(this, " + rowNum + ")" ) ) );
																	column.append( "/>" );
																	column.append( "</td>" );
																	*/
		}
		else{
			
			if(inputDto.getRiskCodes().getRiskType()!=1){
				
				column.append( createElement( "hidden",fieldIdPart+ "_coverName", "covers[" + rowNum + "].coverName", null, null, inputDto.getCoverName() ) );
			}
			column.append( createElement( "hidden",fieldIdPart+ "_riskCode", fieldPath + "riskCodes.riskCode", null, null, inputDto.getRiskCodes().getRiskCode().toString() ) );
			column.append( createElement( "hidden",fieldIdPart+ "_riskCat", fieldPath + "riskCodes.riskCat", null, null, inputDto.getRiskCodes().getRiskCat().toString() ) );
			column.append( createElement( "hidden",fieldIdPart+ "_riskType",fieldPath + "riskCodes.riskType", null, null, inputDto.getRiskCodes().getRiskType().toString() ) );
			column.append( createElement( "hidden",fieldIdPart+ "_basicRskCode", fieldPath + "riskCodes.basicRskCode", null, null, inputDto.getRiskCodes().getBasicRskCode().toString() ) );
			column.append( createElement( "hidden",fieldIdPart+ "_covCode", fieldPath + "coverCodes.covCode", null, null, String.valueOf( inputDto.getCoverCodes().getCovCode() ) ) );
			column.append( createElement( "hidden",fieldIdPart+ "_covTypeCode", fieldPath + "coverCodes.covTypeCode", null, null, String.valueOf( inputDto.getCoverCodes().getCovTypeCode() ) ) );
			column.append( createElement( "hidden",fieldIdPart+ "_promoCover", fieldPath + "sumInsured.promoCover", null, null, freeCover.toString() ) );
			

			if( !Utils.isEmpty( coverVO ) ){
				column.append( createElement( "hidden",fieldIdPart+ "_rskId", fieldPath + "riskCodes.rskId", null, null, !Utils.isEmpty(coverVO.getRiskCodes().getRskId())?coverVO.getRiskCodes().getRskId().toString():"" ) );
				column.append( createElement( "hidden",fieldIdPart+ "_vsd", fieldPath + "vsd", null, null, !Utils.isEmpty(coverVO.getVsd())?coverVO.getVsd().toString():"" ));
				column.append( createElement( "hidden",fieldIdPart + "_discOrLoadPerc", fieldPath + "discOrLoadPerc", null, null, String.valueOf(coverVO.getDiscOrLoadPerc() )) );
			}

			savedSIValue = resultMap.get( "SI" ) != null ? resultMap.get( "SI" ).toString() : "";

			column.append( "<input " );
			column.append( String.format( ATTR_TEMPLATE, "type", "text" ) );
			if( inputDto.getRiskCodes().getRiskType() == 1 ){
				column.append( String.format( ATTR_TEMPLATE, " class", "numbers" ) );
			}
			column.append( String.format( ATTR_TEMPLATE, " id", fieldId ) );
			column.append( String.format( ATTR_TEMPLATE, " name", fieldName ) );
			column.append( String.format( ATTR_TEMPLATE, " maxLength", "13" ) );
			column.append( String.format( ATTR_TEMPLATE, " value", Currency.getFormattedCurrency( savedSIValue , LOB.HOME.toString() ) ) );
			
			if(inputDto.getRiskCodes().getRiskType() == 1){
				column.append( String.format( ATTR_TEMPLATE, " placeholder", "Building value in AED" ) );
			} else if(inputDto.getRiskCodes().getRiskType() == 31){
				column.append( String.format( ATTR_TEMPLATE, " placeholder", "Content value in AED" ) );
			}else if(inputDto.getRiskCodes().getRiskType() == 32){
				column.append( String.format( ATTR_TEMPLATE, " placeholder", "Personal Possession value in AED" ) );
			}
			column.append( String.format( ATTR_TEMPLATE, " style", "margin-right : 0px !important; margin-left: 5px;" ) );
			
			if (docCode == 6 || docCode == 2){
				column.append( String.format( ATTR_TEMPLATE, " readonly", "true" ) );
			}
			
			column.append( " />" );
		}
		return column;
	}

	private StringBuffer createElement( String type, String id,String name, String dojoType, String len, String value ){
		StringBuffer element = new StringBuffer();
		element.append( "<input " );
		element.append( String.format( ATTR_TEMPLATE, "type", type ) );
		element.append( String.format( ATTR_TEMPLATE, " id", id ) );
		if( dojoType != null ) element.append( String.format( ATTR_TEMPLATE, " dojoType", dojoType ) );
		element.append( String.format( ATTR_TEMPLATE, " name", name ) );
		if( len != null ) element.append( String.format( ATTR_TEMPLATE, " maxLength", len ) );
		element.append( String.format( ATTR_TEMPLATE, " value", value ) );
		element.append( "/>" );
		return element;
	}

	private StringBuffer createDropdown( String tagId,String tagName, String lookUpIdentifier, String level1, String level2, String lookUpCode, String value, String onChange, String disabledFlag, Boolean toDisplay, String defaultOption ){

		StringBuffer element = new StringBuffer();

		DropDownHTMLRenderer dropDownRenderer = new DropDownHTMLRenderer();
		HashMap<String, Object> attributeList = new HashMap<String, Object>();
		HashMap<String, Object> responseAttributeList = new HashMap<String, Object>();

		attributeList.put( AppConstants.INPUTTYPE, "dropdown" );
		attributeList.put( AppConstants.TAGNAME, tagName );
		attributeList.put( AppConstants.TAGID, tagId );
		attributeList.put( AppConstants.IDENTIFIER, lookUpIdentifier ); //HOME_DRPDWN
		attributeList.put( "level1", level1 );
		attributeList.put( "level2", level2 );
		attributeList.put( AppConstants.CODE, lookUpCode );
		attributeList.put( AppConstants.VALUE, value );
		attributeList.put( AppConstants.ON_CHANGE_EVENT, onChange );
		attributeList.put( AppConstants.DISABLEDFLAG, disabledFlag );
		attributeList.put( AppConstants.SESSIONDATA, pageContext.getSession() );
		attributeList.put( "defaultOption", defaultOption );

		responseAttributeList = dropDownRenderer.getDropdownValues( attributeList );
		if( responseAttributeList.get( "status" ).toString().equals( AppConstants.TRUE ) ){
			element.append( "<div " );
			element.append( String.format( ATTR_TEMPLATE, "class", "custom-select" ) );
			
			if(docCode != 2 && docCode != 6){
				if(toDisplay){
					element.append( String.format( ATTR_TEMPLATE, "style", "display: block;" ) );
				}else{
					element.append( String.format( ATTR_TEMPLATE, "style", "display: none;" ) );
				}
			}
			element.append( " >" );

			element.append( (StringBuffer) responseAttributeList.get( "responseString" ) );
			element.append( "</div>" );
		}
		else{
			element.append( "<select " );
			element.append( String.format( ATTR_TEMPLATE, "name", "emptyList" ) );
			element.append( String.format( ATTR_TEMPLATE, "dojoType", DOJOTYPESELECT ) );

			element.append( String.format( OPTION_TEMPLATE, "", "Select" ) );
			element.append( '>' );
		}
		if( tagId.equals( "covers_si[3]" ) ){
			CoverDetailsVO coverVO = null;
			List<Object> columnDetails = null;
			List<CoverDetailsVO> coverDetailsList = null;
			coverDetailsList = ( (CoverDetails) attributeGlobalList.get( "coverDetails" ) ).getCoverDetails();
			columnDetails = new ArrayList<Object>();
			int rowNum = 0;
			for( int i = 0; i < coverDetailsList.size(); i++ ){
				if(coverDetailsList.get( i ).getCoverCodes().getCovCode() == 3){
					columnDetails.add( coverDetailsList.get( i ) );
					rowNum = i;
					break;
				}
			}
			
			columnDetails.add( rowNum );
			createStaffDetails( columnDetails,element );
		}
		return element;
	}

	private Map<String, CoverDetailsVO> getCoversMap( List<CoverDetailsVO> coverDetails ){
		Map<String, CoverDetailsVO> coverMap = new HashMap<String, CoverDetailsVO>();
		for( CoverDetailsVO coverDetailsVO : coverDetails ){

			String riskCat = !Utils.isEmpty( coverDetailsVO.getRiskCodes().getRiskCat() ) ? coverDetailsVO.getRiskCodes().getRiskCat().toString() : "0";
			if( !Utils.isEmpty( coverDetailsVO.getRiskCodes() ) && !Utils.isEmpty( coverDetailsVO.getCoverCodes() ) ){
				String key = coverDetailsVO.getRiskCodes().getRiskCode().toString() + coverDetailsVO.getRiskCodes().getRiskType().toString() + riskCat
						+ String.valueOf( coverDetailsVO.getCoverCodes().getCovCode() ) /*+
																						String.valueOf( coverDetailsVO.getCoverCodes().getCovTypeCode())*/;
				coverMap.put( key, coverDetailsVO );
			}
		}
		return coverMap;
	}

	private CoverDetailsVO getCoverFromMap( HashMap<String, CoverDetailsVO> coverMap, CoverDetailsVO coverVO ){
		CoverDetailsVO matchingObject = null;
		String key = coverVO.getRiskCodes().getRiskCode().toString() + coverVO.getRiskCodes().getRiskType().toString() + coverVO.getRiskCodes().getRiskCat().toString()
				+ String.valueOf( coverVO.getCoverCodes().getCovCode() ) /*+
																			String.valueOf( coverVO.getCoverCodes().getCovTypeCode())*/;
		matchingObject = coverMap.get( key );
		return matchingObject;
	}

	private Map<String, Object> getSelectedValues( CoverDetailsVO coverVO, String field ){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if( !Utils.isEmpty( coverVO ) ){
			if( ( ( field.equals( "SI" ) ) ) && ( !Utils.isEmpty( coverVO.getSumInsured() ) ) && ( !Utils.isEmpty( coverVO.getSumInsured().getSumInsured() ) ) ){
				String SI = ( BigDecimal.valueOf( coverVO.getSumInsured().getSumInsured().longValue() ) ).toString();
				resultMap.put( "SI", ( SI.split( "\\." ) )[ 0 ] );
			}
			else if( ( !Utils.isEmpty( coverVO.getSumInsured() ) ) && ( !Utils.isEmpty( coverVO.getSumInsured().geteDesc() ) ) ){
				resultMap.put( "SI", coverVO.getSumInsured().geteDesc() );
			}
			resultMap.put( "chkBox", "checked" );
			if( !Utils.isEmpty( coverVO.getPremiumAmt() ) ){
				resultMap.put( "premium", Currency.getFormattedCurrency( coverVO.getPremiumAmt() , LOB.HOME.toString() ) );
			}
			if( !Utils.isEmpty( coverVO.getPremiumAmtActual() ) ){
				resultMap.put( "actualPremium", Currency.getFormattedCurrency( coverVO.getPremiumAmtActual() , LOB.HOME.toString() ) );
			}
			if( !Utils.isEmpty( coverVO.getDiscOrLoadPerc() ) ){
				resultMap.put( "disc", coverVO.getDiscOrLoadPerc() );
			}
		}
		else{
			resultMap.put( "SI", null );
			resultMap.put( "chkBox", null );
			resultMap.put( "premium", "" );
			resultMap.put( "disc", "" );
			resultMap.put( "actualPremium", "" );
		}
		return resultMap;

	}

	@Override
	public void buildEmptyControl( JspWriter out ) throws IOException{
		//No implementation required.
	}
	
private int createStaffDetails( List<Object> columnDetails, StringBuffer tableRow ){

		
		CoverDetailsVO inputDto = null;
		int rowNum = 0;
		try{

			inputDto = (CoverDetailsVO) columnDetails.get( 0 );
			rowNum = Integer.parseInt( columnDetails.get( 1 ).toString() );
			String listId = "sheepItForm3";
			if( !inputDto.getRiskCodes().getRiskType().equals( AppConstants.HOME_BUILDING_RISK_TYPE ) ){
				
								
					tableRow.append( "<a " );
					tableRow.append( String.format( ATTR_TEMPLATE, " href", "#" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " class", "action-add staffDetailsLink" ) );
					tableRow.append( ">Staff Details</a>" );						
					if(docCode == 6 || docCode == 2){
						tableRow.append( "<a " );
						tableRow.append( String.format( ATTR_TEMPLATE, " href", "#" ) );
						tableRow.append( String.format( ATTR_TEMPLATE, " class", "action-view" ) );
						tableRow.append( ">Staff Details</a>" );						
					}
					tableRow.append( "<div></br></br></div>");
					tableRow.append( "<div " );
					tableRow.append( String.format( ATTR_TEMPLATE, "class", "item-wrapper" ) );
					//tableRow.append( String.format( ATTR_TEMPLATE, " style", "height:240px;" ) );
					tableRow.append( ">" );
					tableRow.append( "<h6>Domestic Staff Details</h6>" );
					String placeHolder1 = "Staff name";
					String placeHolder2 = "Date of Birth";
					
					tableRow.append( "<div " );
                    tableRow.append( String.format( ATTR_TEMPLATE, "class", "list-error-message" ) );
                    tableRow.append( "></div>" );
                    
					tableRow.append( "<div " );
					tableRow.append( String.format( ATTR_TEMPLATE, "class", "scroll-list" ) );
					tableRow.append( ">" );

					tableRow.append( "<ul " );
					tableRow.append( String.format( ATTR_TEMPLATE, "id", listId ) );
					tableRow.append( ">" );
					
					tableRow.append( "<li " );
					tableRow.append( String.format( ATTR_TEMPLATE, "id", listId+"_template" ) );
					tableRow.append( ">" );

					tableRow.append( "<input " );
					tableRow.append( String.format( ATTR_TEMPLATE, "type", "text" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " id", listId+"_#index#_itemname" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " name", listId+"_Desc[#index#]" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " placeholder", placeHolder1 ) );
					tableRow.append( " />" );

					tableRow.append( "<input " );
					tableRow.append( String.format( ATTR_TEMPLATE, " class", "dob-picker" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, "type", "text" ) );
					
					tableRow.append( String.format( ATTR_TEMPLATE, " id",listId+"_#index#_itemvalue" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " name", listId+"_sumInsured[#index#]" ) );
				
					tableRow.append( String.format( ATTR_TEMPLATE, " placeholder", placeHolder2 ) );
					tableRow.append( " />" );
					
					
						
					/*tableRow.append( createElement( "hidden","covers_#index#_coverName", listId+"_coverName[#index#]", null, null, inputDto.getCoverName() ) );
					tableRow.append( createElement( "hidden","covers_#index#_promoCover", listId+"_promoCover[#index#]", null, null, freeCover.toString() ) );*/

				
						tableRow.append( createElement( "hidden",listId+"_#index#_rskId",listId+"_rskId[#index#]", null, null, "" ));
						tableRow.append( createElement( "hidden",listId+"_#index#_vsd", listId+"_vsd[#index#]", null, null, "" ) );
					

					tableRow.append( "<div " );
					tableRow.append( String.format( ATTR_TEMPLATE, "class", "action-button" ) );
					tableRow.append( ">" );

					tableRow.append( "<a " );
					tableRow.append( String.format( ATTR_TEMPLATE, "href", "#" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " class", "delete remove-btn" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " id", listId+"_remove_current" ) );
					tableRow.append( ">remove</a>" );

					tableRow.append( "<a " );
					tableRow.append( String.format( ATTR_TEMPLATE, " href", "#" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " class", "add-btn" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " id", listId+"_add" ) );
					tableRow.append( ">add</a>" );

					tableRow.append( "</div>" );

					tableRow.append( "</li>" );
					
					//}
					
					tableRow.append( "<li " );
					tableRow.append( String.format( ATTR_TEMPLATE, " id", listId+"_noforms_template" ) );
					tableRow.append( ">No Item</li>" );
					tableRow.append( "<li " );
					tableRow.append( String.format( ATTR_TEMPLATE, " id", listId+"_controls" ) );
					tableRow.append( " />" );
					
					
					tableRow.append( "</ul>" );

					tableRow.append( "</div>" );
					
					/*if(!Utils.isEmpty(savedCovers)){
						CoverDetailsVO coverDetailsVO = savedCovers.get(0);
						//tableRow.append( createElement("hidden",listId+"_coverName", listId+"_coverName", null, null, coverDetailsVO.getCoverName() ));
						tableRow.append( createElement("hidden",listId+"_riskCode",listId+"_riskCode", null, null, coverDetailsVO.getRiskCodes().getRiskCode().toString() ));
						tableRow.append( createElement("hidden",listId+"_riskCat",listId+"_riskCat", null, null, AppConstants.HOME_LIST_ITEM_RISK_CATEGORY.toString() ));
						tableRow.append( createElement("hidden",listId+"_riskType",listId+"_riskType", null, null, coverDetailsVO.getRiskCodes().getRiskType().toString() ));
						tableRow.append( createElement("hidden",listId+"_basicRskCode",listId+"_basicRskCode", null, null, coverDetailsVO.getRiskCodes().getBasicRskCode().toString() ));
						tableRow.append( createElement("hidden",listId+"_covCode",listId+"_covCode", null, null, String.valueOf( coverDetailsVO.getCoverCodes().getCovCode())));
						tableRow.append( createElement("hidden",listId+"_covTypeCode",listId+"_covTypeCode", null, null, String.valueOf( coverDetailsVO.getCoverCodes().getCovTypeCode()) ));
					}else{*/
						tableRow.append( createElement("hidden",listId+"_riskCode",listId+"_riskCode", null, null, inputDto.getRiskCodes().getRiskCode().toString() ));
						tableRow.append( createElement("hidden",listId+"_riskCat",listId+"_riskCat", null, null, AppConstants.HOME_LIST_ITEM_RISK_CATEGORY.toString() ));
						tableRow.append( createElement("hidden",listId+"_riskType",listId+"_riskType", null, null, inputDto.getRiskCodes().getRiskType().toString() ));
						tableRow.append( createElement("hidden",listId+"_basicRskCode",listId+"_basicRskCode", null, null, inputDto.getRiskCodes().getBasicRskCode().toString() ));
						tableRow.append( createElement("hidden",listId+"_covCode",listId+"_covCode", null, null, String.valueOf( inputDto.getCoverCodes().getCovCode())));
						tableRow.append( createElement("hidden",listId+"_covTypeCode",listId+"_covTypeCode", null, null, String.valueOf( inputDto.getCoverCodes().getCovTypeCode()) ));
					/*}*/

					tableRow.append( "<div " );
					tableRow.append( String.format( ATTR_TEMPLATE, " class", "action-row" ) );
					tableRow.append( ">" );

					tableRow.append( "<input " );
					tableRow.append( String.format( ATTR_TEMPLATE, "type", "button" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " value", "Save" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " class", "btn-save btn-secondary" ) );
					tableRow.append( "/>" );

					tableRow.append( "<input " );
					tableRow.append( String.format( ATTR_TEMPLATE, "type", "button" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " value", "Cancel" ) );
					tableRow.append( String.format( ATTR_TEMPLATE, " class", "btn-cancel btn-secondary" ) );
					tableRow.append( "/>" );

					tableRow.append( "</div>" );

					tableRow.append( "</div>" );

			}

		}
		catch( Exception exp ){
			exp.printStackTrace();
		}
		return rowNum;
	}

}
