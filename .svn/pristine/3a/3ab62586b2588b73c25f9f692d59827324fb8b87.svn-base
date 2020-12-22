/**
 * Since Phase 3.
 * Added for Travel Page. 
 */
package com.rsaame.pas.productComparison.ui;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.jsp.JspWriter;

import com.mindtree.ruc.cmn.utils.List;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.lookup.ui.IHtmlRenderer;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.taglib.svc.LoadCoverSvc;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.app.FieldType;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author M1016284
 *
 */
public class ProductsRenderer implements IHtmlRenderer{

	private static final String ATTR_TEMPLATE = "%s='%s'";
	private static final String CROSSMARK_IMG_PATH = "/pas/static/img/cross.jpg";
	private static final String SPACE = " ";
	
	@SuppressWarnings( "unchecked" )
	@Override
	public void buildHTMLContent( HashMap<String, Object> attributeList ) throws IOException{

		Integer schemeCode = null;
		String tariffCode = "";
		
		if( !Utils.isEmpty( attributeList.get( AppConstants.OUT ) ) ){
			/*Preparing HTML content for under writing questions*/
			JspWriter out = (JspWriter) attributeList.get( "Out" );
			StringBuffer responseString = new StringBuffer();
			SchemeVO schemeVO = null;
			CommonVO commonVO = null;

			if( !Utils.isEmpty( attributeList.get( AppConstants.INPUTTYPE ) ) ){
				/*Preparing for a database call to get required values*/
				if( attributeList.get( AppConstants.INPUTTYPE ).toString().equalsIgnoreCase( AppConstants.PRODUCT_RENDERER ) ){

					java.util.List<TravelPackageVO> travelPackageVOs ;
					List<CoverDetailsVO> covers ;

					/*Put the inputs required for service into HashMap from attributeList and pass the same to service.*/
					if( !Utils.isEmpty( attributeList ) && !Utils.isEmpty( attributeList.get( AppConstants.COMMON_VO ) ) ){
						commonVO = (CommonVO)attributeList.get( AppConstants.COMMON_VO );
					}
					
					LoadCoverSvc coverSvc = null;
					
					if(!Utils.isEmpty(  commonVO ) && commonVO.getIsQuote()){
						coverSvc = (LoadCoverSvc)Utils.getBean( "loadCoverSvc" );
					}else{
						coverSvc = (LoadCoverSvc) Utils.getBean( "loadCoverSvc_POL" );
					}
			
					//TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) coverSvc.invokeMethod( "getPackages", commonVO );
					TravelInsuranceVO travelInsuranceVO  = (TravelInsuranceVO) attributeList.get( "TravelInsuranceVO" );
					schemeCode = travelInsuranceVO.getScheme().getSchemeCode();
					schemeVO = travelInsuranceVO.getScheme();
					if(!Utils.isEmpty(  commonVO ) &&!Utils.isEmpty( commonVO.getQuoteNo())){  // Travel Scope 131378
						schemeVO.setQuoteNo(commonVO.getQuoteNo());
					}
					//schemeVO.setSchemeCode( 1072 );
					travelPackageVOs = travelInsuranceVO.getTravelPackageList();
					
					if( !Utils.isEmpty( travelPackageVOs ) ){

						int noOfProducts = travelPackageVOs.size();
						int maxNoOfCols = noOfProducts + 1;
						int columnNumber = 1;
						List<CoverDetailsVO> addlCovers = new List<CoverDetailsVO>( CoverDetailsVO.class );

						covers = (List<CoverDetailsVO>)coverSvc.invokeMethod( "getTravelCovers", schemeVO  );
						
						/*if( !Utils.isEmpty( attributeList.get( AppConstants.NUMBEROFCOLS ) ) )
							maxNoOfCols = Integer.parseInt( (String) attributeList.get( AppConstants.NUMBEROFCOLS ) );*/

						/* Create the column for cover names.*/
						responseString.append( createCoversColumn( covers, addlCovers ));

						for( TravelPackageVO travelPackageVO : travelPackageVOs ){
							
							if( maxNoOfCols >= columnNumber ){
								/* Create column for each package.*/
								tariffCode = createPackageColumn( responseString , travelPackageVO, covers, tariffCode, schemeCode ,travelInsuranceVO);
								columnNumber++;
							}
							
						}
						createHiddenSchemeDetails( responseString , tariffCode, schemeCode );
					}
				}
			}
			out.print(responseString);
		}
	}

	/**
	 * @param responseString
	 * Render hidden schemeCode and tariffCode fields.
	 * @param schemeCode 
	 * @param tariffCode 
	 * @param setHiddenCodes 
	 */
	private void createHiddenSchemeDetails( StringBuffer responseString, String tariffCode, Integer schemeCode ){
		
		responseString.append( com.Constant.CONST_INPUT_END );
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, tariffCode ) );
		responseString.append( String.format( ATTR_TEMPLATE, "id", "tariffCode" ) );
		responseString.append( String.format( ATTR_TEMPLATE, "name", "tariffCode" ) );
		responseString.append( ">");
		responseString.append( "</input>");
		
		responseString.append( com.Constant.CONST_INPUT_END );
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, schemeCode.toString() ) );
		responseString.append( String.format( ATTR_TEMPLATE, "id", "schemeCode" ) );
		responseString.append( String.format( ATTR_TEMPLATE, "name", "schemeCode" ) );
		responseString.append( ">");
		responseString.append( "</input>");
		
	}

	/**
	 * @param covers  
	 * @param addlCovers 
	 * @return StringBuffer
	 * Creates the column containing cover names in the travel page.
	 */
	private StringBuffer createCoversColumn( List<CoverDetailsVO> covers, List<CoverDetailsVO> addlCovers ){

		StringBuffer responseString = new StringBuffer();
		
		createPackageHeader( responseString, AppConstants.COVERS.toString(),null,null,null );
		displayCoverNames( responseString, covers );
		responseString.append( "<tr><td style='line-height : 25px !important; padding: 1px 3px; ' class='leftAlignText'><b>Select Product</b></td></tr>" );
		responseString.append("</table>" );
		
		return responseString;
	}

	/**
	 * @param responseString
	 * @param covers
	 * This function creates the (left most) column with Cover names. 
	 */
	private void displayCoverNames( StringBuffer responseString, List<CoverDetailsVO> covers ){
		
		Boolean isAddlCoverLabelDisplayed = false;
		
		/* Render the cover names one by one in a loop.*/
		for( CoverDetailsVO cover : covers ){
			
			/* Create the 'Additional Covers' heading only once.*/
			if( !cover.getMandatoryIndicator() && !isAddlCoverLabelDisplayed ){
				
				responseString.append( "<tr " );
				responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, "summaryheadergeneral leftAlignText" ) );
				responseString.append( "><td>" );
				responseString.append( "<b>" );
				responseString.append( AppConstants.ADDITIONAL_COVERS.toString() );
				responseString.append( "</b>" );
				responseString.append( com.Constant.CONST_TD_END );
				responseString.append( com.Constant.CONST_TR_END );
			
				isAddlCoverLabelDisplayed = Boolean.TRUE;
			
			}
				
			if(cover.getPrcDisplayInd()==0){ //Travel Scope 131378 : Shortened covers
				
				System.out.println(" *********Hidden Cover Details  **********"+cover.getCoverName());
				responseString.append( "<tr>" );
				responseString.append( "<td>" );
				responseString.append( com.Constant.CONST_TD_END );
				responseString.append( com.Constant.CONST_TR_END );
			}
			
			else{
			responseString.append( "<tr>" );
			responseString.append( "<td " );
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS,com.Constant.CONST_ROWSTYLE_LEFTALIGNTEXT ) );
			responseString.append( ">" );
			responseString.append( "<label>" );
			responseString.append( cover.getCoverName() );
			responseString.append( com.Constant.CONST_LABEL_CLOSING );
			responseString.append( com.Constant.CONST_TD_END );
			responseString.append( com.Constant.CONST_TR_END );
			
				
				
			}
			
		}
		
	}

	/**
	 * @param travelPackageVO
	 * @param addlCovers 
	 * @param schemeCode 
	 * @param tariffCode 
	 * @param travelInsuranceVO 
	 * @param setHiddenCodes 
	 * @return StringBuffer
	 * Creates the column with Sum Insured values for each package.
	 */
	private String createPackageColumn( StringBuffer responseString, TravelPackageVO travelPackageVO, List<CoverDetailsVO> covers, String tariffCode, Integer schemeCode, TravelInsuranceVO travelInsuranceVO ){

		
		Boolean isSchengenAllowed=false;
		if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && 
				Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")) {
			
			isSchengenAllowed = Integer.parseInt(travelPackageVO.getTariffCode()) == Integer.parseInt(Utils.getSingleValueAppConfig("OMAN_TRAVEL_SCHENGEN_TARIFF")) &&
												(SvcConstants.WORLDWIDE.equalsIgnoreCase(travelInsuranceVO.getTravelDetailsVO().getTravelLocation()) || 
														travelInsuranceVO.getPolicyType().equals( Integer.valueOf( Utils.getSingleValueAppConfig( "TRAVEL_LONG_TERM_POLICY_TYPE" ) ) ));

		}
		createPackageHeader( responseString, travelPackageVO.getPackageName() ,travelPackageVO.getPremiumAmtActual(),travelPackageVO.getTariffCode(),isSchengenAllowed);
		populateCovers( responseString, travelPackageVO.getCovers(), covers ) ;
		
		/*Create the row with radio button.*/
		responseString.append( "<tr>" );
		responseString.append( "<td style='line-height : 25px !important; padding: 1px 3px; padding-top: 6px;' class='centerAlignText'>" );
		responseString.append( com.Constant.CONST_INPUT_END );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_TYPE, com.Constant.CONST_RADIO ) );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DOJOTYPE, com.Constant.CONST_DIJIT_FORM_RADIOBUTTON ) ) ;
		responseString.append( "id = 'tariff_"+travelPackageVO.getCovers().get( 0 ).getTariffCode()+"'" );
		responseString.append( String.format( ATTR_TEMPLATE, " name", "tariffSelect" ) );
		responseString.append( String.format( ATTR_TEMPLATE ,com.Constant.CONST_STYLE, " margin-left : 30px;" ) );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_ONCLICK, 
				("populateSelectedTariff("+travelPackageVO.getCovers().get( 0 ).getTariffCode().toString()+","+schemeCode+");") ) );
		
		responseString.append( String.format( ATTR_TEMPLATE, " onselect", 
				("populateSelectedTariff("+travelPackageVO.getCovers().get( 0 ).getTariffCode().toString()+","+schemeCode.toString()+");") ) );
		
		Boolean isEnabled=true;
		
		if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && 
				Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")) {
			
			
			if(isSchengenAllowed) {
				responseString.append( String.format( ATTR_TEMPLATE, " disabled", "disabled") );
				isEnabled=false;
			}
			
			
		}
		
		if( !Utils.isEmpty( travelPackageVO.getIsSelected() ) && travelPackageVO.getIsSelected() && isEnabled){
			tariffCode = travelPackageVO.getCovers().get( 0 ).getTariffCode().toString();
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CHECKED, com.Constant.CONST_CHECKED) );
		}
		
		responseString.append( "/>" );
		responseString.append( com.Constant.CONST_TD_END );
		responseString.append( com.Constant.CONST_TR_END );
		responseString.append( "</table>" );
		
		return tariffCode;
	}


	/**
	 * @param responseString
	 * @param heading
	 * Creates the First row with Package Name.
	 */
	private void createPackageHeader(  StringBuffer responseString, String heading, Double pkgPremium ,String tariffCode,Boolean isSchengenAllowed ){
		
		responseString.append( "<table " );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, "displayInline" ) );
		
		responseString.append( "><tr " );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, "summaryheadergeneral" ) );
		responseString.append( "><td " );
		if(heading.equalsIgnoreCase(AppConstants.COVERS.toString())){
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_ROWSTYLE_LEFTALIGNTEXT ) );
		} else {
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_ROWSTYLE_CENTERALIGNTEXT ) );
		}
		responseString.append( "><b><div id='identifier_"+tariffCode+"'>" );
		if(Utils.isEmpty( pkgPremium )){
			responseString.append( AppConstants.PREMIUM );
		}else{
			if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && 
					Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")) {
			if(!Utils.isEmpty(isSchengenAllowed) && isSchengenAllowed)
			{
				responseString.append( Currency.getUnitName()).append( SPACE ).append( Currency.getFormattedCurrency( 0, LOB.TRAVEL.name() ) );
			}
			
			else
			{
				responseString.append( Currency.getUnitName()).append( SPACE ).append( Currency.getFormattedCurrency( pkgPremium, LOB.TRAVEL.name() ) );
			}
			}
			else
			{
				responseString.append( Currency.getUnitName()).append( SPACE ).append( Currency.getFormattedCurrency( pkgPremium, LOB.TRAVEL.name() ) );
			}
			
		}
		
		responseString.append( "</div></b>" );
		responseString.append( com.Constant.CONST_TD_END );
		responseString.append( com.Constant.CONST_TR_END );
		
		/* First row with Package Name. */
		responseString.append( "<tr " );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, "summaryheadergeneral" ) );
		responseString.append( "><td " );
		if(heading.equalsIgnoreCase(AppConstants.COVERS.toString())){
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_ROWSTYLE_LEFTALIGNTEXT ) );
		} else {
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS, com.Constant.CONST_ROWSTYLE_CENTERALIGNTEXT ) );
		}
		responseString.append( "><b>" );
		responseString.append( heading );
		responseString.append( "</b>" );
		responseString.append( com.Constant.CONST_TD_END );
		responseString.append( com.Constant.CONST_TR_END );
		
	}

	/**
	 * @param responseString 
	 * @param addtlCoversInPackage 
	 * @param covers
	 * This method populates the Cover SI values on screen. 
	 */
	private void populateCovers( StringBuffer responseString, java.util.List<CoverDetailsVO> coversInPackage, java.util.List<CoverDetailsVO> coverNames ){

		/* Iterate over CoverDetailsVO to print all the SI values.*/
		Boolean matchExists = Boolean.FALSE;
		Boolean isAddlCoverLabelAdded = Boolean.FALSE;
		int i = 0; 
		Integer tariffCode = null;
		
		for( CoverDetailsVO coverUsedForName : coverNames ){
			
			if( !coverUsedForName.getMandatoryIndicator() && !isAddlCoverLabelAdded ){
				isAddlCoverLabelAdded = Boolean.TRUE;
				responseString.append( "<tr><td style='line-height : 16px; padding: 1.5px 3px; '>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>" );
			}
			
			matchExists = Boolean.FALSE;
			for( CoverDetailsVO coverInPackage : coversInPackage ){

				tariffCode = coverInPackage.getTariffCode();
				
				if( coverUsedForName.equals( coverInPackage ) ){
					
					
					if(coverUsedForName.getPrcDisplayInd()==0){  // Travel Scope 131378 : Shortened covers
						matchExists = Boolean.TRUE;
						System.out.println(" ****************** Hidden for Cover Codes************"+ coverUsedForName.getCoverCodes().getCovCode());
						responseString.append( "<tr>" );
						responseString.append( "<td> " );
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_COVERSI_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_COVERSI_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getSumInsured().getSumInsured().toString()));
						responseString.append("/>");
						
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_COVERDEDUCTIBLE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_COVERDEDUCTIBLE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getSumInsured().getDeductible().toString()));
						responseString.append("/>");
						
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_COVERCODE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_COVERCODE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getCoverCodes().getCovCode() ));
						responseString.append("/>");
						
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_COVERSUBTYPECODE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_COVERSUBTYPECODE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getCoverCodes().getCovSubTypeCode() ));
						responseString.append("/>");
						
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_COVERTYPECODE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_COVERTYPECODE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getCoverCodes().getCovTypeCode() ));
						responseString.append("/>");
						
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_RISKCODE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_RISKCODE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getRiskCodes().getRiskCode() ));
						responseString.append("/>");
						
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_BASICRSKCODE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_BASICRSKCODE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getRiskCodes().getBasicRskCode() ));
						responseString.append("/>");
						
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_RISKCAT_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_RISKCAT_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getRiskCodes().getRiskCat() ));
						responseString.append("/>");
						
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_RISKTYPE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_RISKTYPE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getRiskCodes().getRiskType() ));
						responseString.append("/>");
						
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_BASICRISKID_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_BASICRISKID_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getRiskCodes().getBasicRskId() ));
						responseString.append("/>");
						
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_RISKID_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_RISKID_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getRiskCodes().getRskId() ));
						responseString.append("/>");
						
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",("vsd_"+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",("vsd_"+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getVsd() ));
						responseString.append("/>");
						
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_ADESC_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_ADESC_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE, coverInPackage.getSumInsured().getaDesc()));
						responseString.append("/>");
						
						responseString.append( com.Constant.CONST_TD_END );
						responseString.append( com.Constant.CONST_TR_END );
						i++;
						break;
					}
						
					else{
					
					matchExists = Boolean.TRUE;
					
					responseString.append( "<tr>" );
					responseString.append( "<td " );
					responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS,com.Constant.CONST_ROWSTYLE_CENTERALIGNTEXT ) );
					responseString.append( ">" );
					
					if( FieldType.LABEL.equals( coverInPackage.getFieldType() )){
						/* render the SI.*/
						responseString.append( com.Constant.CONST_LABEL_NOTCLOSING );
					//	responseString.append( String.format( ATTR_TEMPLATE ," class", "leftAlignText" ) );
						responseString.append( ">" );
						responseString.append( coverInPackage.getSumInsured().geteDesc() );
						responseString.append( com.Constant.CONST_LABEL_CLOSING );
						
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_COVERSI_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_COVERSI_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getSumInsured().getSumInsured().toString()));
						responseString.append("/>");
						
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_COVERDEDUCTIBLE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_COVERDEDUCTIBLE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getSumInsured().getDeductible().toString()));
						responseString.append("/>");
						
					}else if( FieldType.CHECK_BOX.equals( coverInPackage.getFieldType() )){
					
						
						if(!Utils.isEmpty(coverUsedForName.getCoverDesc()) ){
							if(coverUsedForName.getCoverDesc().equalsIgnoreCase("Personal Belongings and Baggage (including valuables)") ||
								coverUsedForName.getCoverDesc().equalsIgnoreCase("Emergency Medical Expenses including emergency dental treatment")){
							
							responseString.append( "<br>" );
						    }
						}
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",("checkBoxSIVal_"+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",("checkBoxSIVal_"+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE, coverInPackage.getSumInsured().getSumInsured().toString() ) );
						responseString.append("/>");
						
						responseString.append( com.Constant.CONST_INPUT_END );
						responseString.append( String.format( ATTR_TEMPLATE, "type", "checkbox" ) ) ;
						responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DOJOTYPE, "dijit.form.CheckBox" ) ) ;
						responseString.append( String.format( ATTR_TEMPLATE, " id", (com.Constant.CONST_COVERSI_END+tariffCode.toString()+"["+i+"]")));
						responseString.append( String.format( ATTR_TEMPLATE, " name",(com.Constant.CONST_COVERSI_END+tariffCode.toString()+"["+i+"]")));
						
						if( !Utils.isEmpty( coverInPackage.getSumInsured() ) && !Utils.isEmpty( coverInPackage.getSumInsured().geteDesc() ) ){
							
							/* If there's a value not equal to zero, render it as checked.*/
							if( !coverInPackage.getSumInsured().geteDesc().equals( AppConstants.zeroVal ) || 
										SvcConstants.STATUS_ON.equalsIgnoreCase( coverInPackage.getIsCovered() ) ){
								responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CHECKED, com.Constant.CONST_CHECKED ));
							}
							
							/* make the check box checked and readonly if the cover is selected in promo code*/
							if(coverInPackage.getSumInsured().isPromoCover()){
								responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CHECKED, com.Constant.CONST_CHECKED ));
								responseString.append( String.format( ATTR_TEMPLATE, " readonly", "readonly" ));
							}
						responseString.append( String.format( ATTR_TEMPLATE ,com.Constant.CONST_STYLE, " margin-left : 27px; !important" ) );
						
						}
						responseString.append(" onchange=\"onCheck("+tariffCode.toString()+","+i+")\"></input>");
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_COVERDEDUCTIBLE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_COVERDEDUCTIBLE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getSumInsured().getDeductible().toString()));
						responseString.append("/>");
					
					}else if( FieldType.RADIO.equals( coverInPackage.getFieldType() )){
						
						responseString.append( com.Constant.CONST_INPUT_END );
						responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_TYPE, com.Constant.CONST_RADIO ) );
						responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DOJOTYPE, com.Constant.CONST_DIJIT_FORM_RADIOBUTTON ) ) ;
						responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_COVERSI_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",("coverSIId1_"+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getSumInsured().getSumInsured().toString()));
						
						if(!SvcConstants.STATUS_ON.equalsIgnoreCase( coverInPackage.getIsCovered() )){
							responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CHECKED, com.Constant.CONST_CHECKED ));
						}
						
						responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_ONCLICK,("populatePASIValue("+i+");onSelect("+tariffCode.toString()+","+i+")") ) );
						responseString.append("/>");


						responseString.append( com.Constant.CONST_LABEL_NOTCLOSING );
						responseString.append( String.format( ATTR_TEMPLATE ," class", "leftAlignText" ) );
						responseString.append( ">" );
						responseString.append( coverInPackage.getSumInsured().geteDesc() );
						responseString.append( com.Constant.CONST_LABEL_CLOSING );
						responseString.append( com.Constant.CONST_INPUT_END );
						responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_TYPE, com.Constant.CONST_RADIO ) );
						responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DOJOTYPE, com.Constant.CONST_DIJIT_FORM_RADIOBUTTON ) ) ;
						responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_COVERSI_END+tariffCode.toString()+"["+i+"]")));
						
					
						if(SvcConstants.STATUS_ON.equalsIgnoreCase( coverInPackage.getIsCovered() )){
							responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CHECKED, com.Constant.CONST_CHECKED ));
						}
						
						responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_ONCLICK,("populatePASIValue("+i+");onSelect("+tariffCode.toString()+","+i+")") ) );

						responseString.append(String.format(ATTR_TEMPLATE,"id",("coverSIId2_"+tariffCode.toString()+"["+i+"]")));
						
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getPrLimit().toString()));
						responseString.append("/>");
						
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_COVERDEDUCTIBLE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_COVERDEDUCTIBLE_END+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getSumInsured().getDeductible().toString()));
						responseString.append("/>");
						
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",("personalAcc_"+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",("personalAcc_"+tariffCode.toString()+"["+i+"]")));
						
						if(!SvcConstants.STATUS_ON.equalsIgnoreCase( coverInPackage.getIsCovered() )){
							responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getPrLimit().toString()));
						}else{
							responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getSumInsured().getSumInsured().toString()));
						}

						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append("/>");
						
						responseString.append( com.Constant.CONST_LABEL_NOTCLOSING );
						responseString.append( String.format( ATTR_TEMPLATE ," class", "leftAlignText" ) );
						responseString.append( ">" );
						responseString.append( coverInPackage.getHelpMessage());
						responseString.append( com.Constant.CONST_LABEL_CLOSING );
					}
					
					if( coverInPackage.getSumInsured().isPromoCover() ){
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",("promoCover_"+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"id",("promoCover_"+tariffCode.toString()+"["+i+"]")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE, "true" ));
						responseString.append("/>");
					}
					
					/* Create the hidden fields required for save operation.*/
					responseString.append(com.Constant.CONST_INPUT_END);
					responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_COVERCODE_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_COVERCODE_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
					responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getCoverCodes().getCovCode() ));
					responseString.append("/>");
					
					responseString.append(com.Constant.CONST_INPUT_END);
					responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_COVERSUBTYPECODE_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_COVERSUBTYPECODE_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
					responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getCoverCodes().getCovSubTypeCode() ));
					responseString.append("/>");
					
					responseString.append(com.Constant.CONST_INPUT_END);
					responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_COVERTYPECODE_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_COVERTYPECODE_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
					responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getCoverCodes().getCovTypeCode() ));
					responseString.append("/>");
					
					responseString.append(com.Constant.CONST_INPUT_END);
					responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_RISKCODE_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_RISKCODE_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
					responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getRiskCodes().getRiskCode() ));
					responseString.append("/>");
					
					responseString.append(com.Constant.CONST_INPUT_END);
					responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_BASICRSKCODE_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_BASICRSKCODE_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
					responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getRiskCodes().getBasicRskCode() ));
					responseString.append("/>");
					
					responseString.append(com.Constant.CONST_INPUT_END);
					responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_RISKCAT_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_RISKCAT_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
					responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getRiskCodes().getRiskCat() ));
					responseString.append("/>");
					
					responseString.append(com.Constant.CONST_INPUT_END);
					responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_RISKTYPE_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_RISKTYPE_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
					responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getRiskCodes().getRiskType() ));
					responseString.append("/>");
					
					responseString.append(com.Constant.CONST_INPUT_END);
					responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_BASICRISKID_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_BASICRISKID_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
					responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getRiskCodes().getBasicRskId() ));
					responseString.append("/>");
					
					responseString.append(com.Constant.CONST_INPUT_END);
					responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_RISKID_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_RISKID_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
					responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getRiskCodes().getRskId() ));
					responseString.append("/>");
					
					responseString.append(com.Constant.CONST_INPUT_END);
					responseString.append(String.format(ATTR_TEMPLATE,"name",("vsd_"+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"id",("vsd_"+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
					responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,coverInPackage.getVsd() ));
					responseString.append("/>");
					
					responseString.append(com.Constant.CONST_INPUT_END);
					responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_ADESC_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_ADESC_END+tariffCode.toString()+"["+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
					responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE, coverInPackage.getSumInsured().getaDesc()));
					responseString.append("/>");
					
					
					responseString.append( com.Constant.CONST_TD_END );
					responseString.append( com.Constant.CONST_TR_END );
					i++;
					break;
					
					}
				}
			}

			
			if( !matchExists ){
				
				/* Insert cross mark image if the cover is not available for a package.*/
				responseString.append( "<tr>" );
				responseString.append( "<td " );
				responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CLASS,com.Constant.CONST_ROWSTYLE_CENTERALIGNTEXT ) );
				responseString.append( ">" );
				if(!Utils.isEmpty(coverUsedForName.getCoverDesc()) ){ //Request ## 131378 New Covers
					if(coverUsedForName.getCoverDesc().equalsIgnoreCase("Personal Belongings and Baggage (including valuables)") ||
						coverUsedForName.getCoverDesc().equalsIgnoreCase("Cancelling your trip/Cutting your trip short(including missed events)(each)")	||
						coverUsedForName.getCoverDesc().equalsIgnoreCase("Bail Bond Facility (including advance of bail bond)")){
					
					responseString.append( "<br>" );
				    }
				}
				responseString.append( "<img src='"+CROSSMARK_IMG_PATH+"'" );
				responseString.append( String.format( ATTR_TEMPLATE ,com.Constant.CONST_STYLE, " margin-left : 27px; height : 16px;" ) );
				responseString.append( "/>" );
				
				if(!Utils.isEmpty(coverUsedForName.getCoverDesc()) ){ //Request ## 145494
					if(coverUsedForName.getCoverDesc().equalsIgnoreCase("Delayed Baggage (Reimbursement of actual costs incurred)")){
						responseString.append( "<br><br>" );
					}
				}
				
			}
			responseString.append( com.Constant.CONST_TD_END );
			responseString.append( com.Constant.CONST_TR_END );
		}

	}

	
	@Override
	public void buildEmptyControl( JspWriter out ) throws IOException{
		//TODO : exception handling.
	}

}
