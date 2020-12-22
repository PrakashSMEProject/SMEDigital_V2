/**
 * 
 */
package com.rsaame.pas.b2c.productComparison.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.jsp.JspWriter;

import com.mindtree.ruc.cmn.utils.List;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.lookup.ui.IHtmlRenderer;
import com.rsaame.pas.vo.app.FieldType;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;

/**
 * @author m1019193
 *
 */
public class OptionalCoversRenderer implements IHtmlRenderer{

	private static final String ATTR_TEMPLATE = "%s='%s'";	

	@Override
	public void buildHTMLContent(HashMap<String, Object> attributeList)
			throws IOException {
		if( !Utils.isEmpty( attributeList.get( AppConstants.OUT ) ) ){
			/*Preparing HTML content for under writing questions*/
			JspWriter out = (JspWriter) attributeList.get( "Out" );
			StringBuffer responseString = new StringBuffer();

			if( !Utils.isEmpty( attributeList.get( AppConstants.INPUTTYPE ) ) ){
				/*Preparing for a database call to get required values*/
				if( attributeList.get( AppConstants.INPUTTYPE ).toString().equalsIgnoreCase( AppConstants.COVER_RENDERER ) ){
					java.util.List<TravelPackageVO> travelPackageVOs = new List<TravelPackageVO>( TravelPackageVO.class );

					TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) attributeList.get( "TravelInsuranceVO" );

					travelPackageVOs = travelInsuranceVO.getTravelPackageList();
					if( !Utils.isEmpty( travelPackageVOs ) ){
						
						int packageCount = 0;
						String defaultTariff = travelInsuranceVO.getScheme().getTariffCode().toString();
						/*responseString.append( "<section " );
						responseString.append( String.format( ATTR_TEMPLATE, "class", "coverage" ) );
						responseString.append( ">" );
						responseString.append( "<ul " );
						responseString.append( String.format( ATTR_TEMPLATE, "class", "quote" ) );
						responseString.append( ">" );*/
						
						for(TravelPackageVO packageVO : travelPackageVOs){
							if(packageVO.getIsSelected()){
								/*if( packageVO.getIsRecommended() ){
									
									responseString.append( String.format( ATTR_TEMPLATE, "class", "recommended" ) );
									defaultTariff = packageVO.getTariffCode();
								}*/
									/*responseString.append( String.format( ATTR_TEMPLATE, "class", "selected" ) );*/
									responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
									responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
									responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + "].premiumAmt'" );
									responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE,  packageVO.getPremiumAmt() ) );
									responseString.append( com.Constant.CONST_START_INPUT_END );
									
									populateCovers( responseString, packageVO, packageCount );
									packageCount++;
							}							
							
						}
						createSchemeDetails( responseString, travelInsuranceVO.getScheme().getSchemeCode(), defaultTariff );
						/*responseString.append( "</ul>" );						
						responseString.append( "</section>" );*/
					}
				}
			}
			out.print( responseString );
		}
	}
	
	private void createSchemeDetails( StringBuffer responseString, Integer schemeCode, String tariffCode ){

		responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( String.format( ATTR_TEMPLATE, "id", "tariffCode" ) );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, tariffCode ) );
		responseString.append( String.format( ATTR_TEMPLATE, "name", "scheme.tariffCode" ) );
		responseString.append( ">" );
		responseString.append( "</input>" );

		responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, schemeCode.toString() ) );
		responseString.append( String.format( ATTR_TEMPLATE, "id", "schemeCode" ) );
		responseString.append( String.format( ATTR_TEMPLATE, "name", "scheme.schemeCode" ) );
		responseString.append( ">" );
		responseString.append( "</input>" );

	}
	
	private void populateCovers( StringBuffer responseString, TravelPackageVO travelPackageVO, int packageCount ) {

		/* Iterate over CoverDetailsVO to print all the SI values.*/
		int coverCount = 0;
		java.util.List<CoverDetailsVO> additionalCovers = new ArrayList<CoverDetailsVO>();
		//java.util.List<CoverDetailsVO> basicCoverCheckBoxes = new ArrayList<CoverDetailsVO>();
		/*responseString.append( "<div " );
		responseString.append( String.format( ATTR_TEMPLATE, "class", "radio-item checked" ) );
		responseString.append( ">" );*/
		
		if( !Utils.isEmpty( travelPackageVO.getTariffCode() ) ){
			responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
			responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
			responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + "].tariffCode'" );
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, travelPackageVO.getTariffCode() ) );
			responseString.append( com.Constant.CONST_START_INPUT_END );
		}
		
		if( !Utils.isEmpty( travelPackageVO.getPackageName() ) ){
			responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
			responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
			responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + "].packageName'" );
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, travelPackageVO.getPackageName() ) );
			responseString.append( com.Constant.CONST_START_INPUT_END );
		}
		
		for( CoverDetailsVO coverInPackage : travelPackageVO.getCovers() ){

			/*if( !Utils.isEmpty( coverInPackage.getMandatoryIndicator() ) && coverInPackage.getMandatoryIndicator() ){
				if( !Utils.isEmpty( coverInPackage.getCoverName() ) && !Utils.isEmpty( coverInPackage.getFieldType() )
						&& FieldType.LABEL.equals( coverInPackage.getFieldType() ) ){
					responseString.append( com.Constant.CONST_INPUT_END );
					responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
					responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS_END + String.valueOf( coverCount )
							+ "].sumInsured.sumInsured'" );
					responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, coverInPackage.getSumInsured().getSumInsured() ) );
					responseString.append( com.Constant.CONST_INPUT_END );
					createRequiredHiddenFields( responseString, coverCount, packageCount, coverInPackage );
					coverCount++;
				}
				else{
					coverCount++;
				}
			}
			else */
			if( !Utils.isEmpty( coverInPackage.getMandatoryIndicator() ) && !coverInPackage.getMandatoryIndicator() &&
					FieldType.CHECK_BOX.equals( coverInPackage.getFieldType() ) ){
				additionalCovers.add( coverInPackage );
			}
			else if( !Utils.isEmpty( coverInPackage.getMandatoryIndicator() ) && coverInPackage.getMandatoryIndicator() ){
				responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
				responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
				responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount )
						+ "].sumInsured.sumInsured'" );
				responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, coverInPackage.getSumInsured().getSumInsured() ) );
				responseString.append( com.Constant.CONST_START_INPUT_END );
				createRequiredHiddenFields( responseString, coverCount, packageCount, coverInPackage );
				coverCount++;
			}
		}
		responseString.append( "<fieldset " );
		responseString.append( String.format( ATTR_TEMPLATE, " class", "radio-group" ) );
		responseString.append( "><label>" );
		responseString.append( "Optional covers:" );
		responseString.append( "</label>" );
		/*coverCount = */populateCheckBoxes( responseString, additionalCovers, coverCount, packageCount, travelPackageVO.getTariffCode() );		/* Commented the coverCount assignment after method call as assignment is unused - sonar violation fix*/
	}
	
	
	private int populateCheckBoxes( StringBuffer responseString, java.util.List<CoverDetailsVO> covers, int coverCount, int packageCount, String tariff ){

		/*responseString.append( "<div " );
		responseString.append( String.format( ATTR_TEMPLATE, "class", ( "control-group" ) ) );
		responseString.append( ">" );*/
		int i = 1;
		if( !Utils.isEmpty( covers ) && covers.size() > 0 ){

			//if( isAddtlCovers ) responseString.append( "<h6>Add Enhancements:</h6>" );
			//responseString.append( "<h6>Opional Covers:</h6>" );
			//responseString.append( "<fieldset " );
			//if( isAddtlCovers ) responseString.append( String.format( ATTR_TEMPLATE, "class", "controls" ) );
			/*responseString.append( String.format( ATTR_TEMPLATE, "class", "controls" ) );
			responseString.append( ">" );*/

			for( CoverDetailsVO cover : covers ){

				if( !Utils.isEmpty( cover.getCoverName() ) ){
					
					responseString.append( "<div " );
					//responseString.append( String.format( ATTR_TEMPLATE, "class", "radio-item checked" ) );
					responseString.append( String.format( ATTR_TEMPLATE, "class", "radio-item checked" ) );
					
					responseString.append( ">" );
					
					responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
					responseString.append( String.format( ATTR_TEMPLATE, " type", "checkbox" ) );
					responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].isCovered'" );
					responseString.append( " id='optionalCover["+ i++ +"]'" );
					responseString.append( String.format( ATTR_TEMPLATE, " readonly", "true" ) );
					if( !cover.getSumInsured().geteDesc().equals( AppConstants.zeroVal ) || 
							AppConstants.STATUS_ON.equalsIgnoreCase( cover.getIsCovered() ) ){
						responseString.append( String.format( ATTR_TEMPLATE, " checked", "checked" ) );
					}				
						
					responseString.append( com.Constant.CONST_START_INPUT_END );
					responseString.append( "<label>" );
					responseString.append( cover.getCoverName() );
					responseString.append( "</label>" );
					responseString.append( "</div>" );
					
					/*responseString.append( "<div " );
					responseString.append( String.format( ATTR_TEMPLATE, "class", "checkbox" ) );
					responseString.append( "><input " );
					responseString.append( String.format( ATTR_TEMPLATE, " type", "checkbox" ) );
					responseString.append( com.Constant.CONST_ID_TRAVELPACKAGELIST_END+String.valueOf( packageCount )+com.Constant.CONST_COVERS_END+String.valueOf( coverCount )+"].isCovered'" );
					responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS_END + String.valueOf( coverCount ) + "].isCovered'" );
					responseString.append( " onclick=getRevisedPremium.do();" );
					if( !cover.getSumInsured().geteDesc().equals( AppConstants.zeroVal ) || 
							AppConstants.STATUS_ON.equalsIgnoreCase( cover.getIsCovered() ) ){
						responseString.append( String.format( ATTR_TEMPLATE, " checked", "checked" ) );
					}
					responseString.append( "></input><label>" );
					responseString.append( cover.getCoverName() );
					responseString.append( "</label>" );
					responseString.append( "</div>" );*/

					createRequiredHiddenFields( responseString, coverCount, packageCount, cover );
					coverCount++;
				}
			}
			responseString.append( "</fieldset>" );		
		}

		//if( isAddtlCovers ){
			/*responseString.append( "<fieldset " );
			responseString.append( String.format( ATTR_TEMPLATE, " class", "controls" ) );
			responseString.append( "><input " );
			responseString.append( String.format( ATTR_TEMPLATE, " type", "button" ) );
			responseString.append( String.format( ATTR_TEMPLATE, " value", "Select" ) );
			responseString.append( "onClick=populateTariffAndPremium(" + tariff + ")" );
			responseString.append( String.format( ATTR_TEMPLATE, " class", ( "btn-select" ) ) );
			responseString.append( "/>" );
			responseString.append( "</fieldset>" );*/
		//}
		/*responseString.append( "</div>" );*/
		return coverCount;
	}
	
private void createRequiredHiddenFields( StringBuffer responseString, int coverCount, int packageCount, CoverDetailsVO coverInPackage ){

		
		/* Start : Hidden fields for Load.*/

		responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount )
				+ "].mandatoryIndicator'" );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, coverInPackage.getMandatoryIndicator() ) );
		responseString.append( com.Constant.CONST_START_INPUT_END );
		
		
		responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount )
				+ "].coverName'" );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, coverInPackage.getCoverName() ) );
		responseString.append( com.Constant.CONST_START_INPUT_END );
		
		responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount )
				+ "].fieldType'" );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, coverInPackage.getFieldType() ) );
		responseString.append( com.Constant.CONST_START_INPUT_END );
	
		responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount )
				+ "].sumInsured.eDesc'" );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, coverInPackage.getSumInsured().geteDesc() ) );
		responseString.append( com.Constant.CONST_START_INPUT_END );
		
		responseString.append( com.Constant.CONST_INPUT_NONCLOSING);
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount )
				+ "].sumInsured.aDesc'" );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, coverInPackage.getSumInsured().getaDesc() ) );
		responseString.append( com.Constant.CONST_START_INPUT_END );
		
		/* End : Hidden fields for Load.*/
		
		
		/* Start : Hidden fields for save.*/

		responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].coverCodes.covCode'" );
		responseString.append( com.Constant.CONST_ID_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].coverCodes.covCode'" );
		if( !Utils.isEmpty( coverInPackage.getCoverCodes() ) && !Utils.isEmpty( coverInPackage.getCoverCodes().getCovCode() ) ){
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, coverInPackage.getCoverCodes().getCovCode() ) );
		}
		/*else{
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, ZERO ) );
		}*/
		responseString.append( com.Constant.CONST_START_INPUT_END);

		responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].coverCodes.covSubTypeCode'" );
		responseString.append( com.Constant.CONST_ID_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].coverCodes.covSubTypeCode'" );
		if( !Utils.isEmpty( coverInPackage.getCoverCodes() ) && !Utils.isEmpty( coverInPackage.getCoverCodes().getCovSubTypeCode() ) ){
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, coverInPackage.getCoverCodes().getCovSubTypeCode() ) );
		}
		/*else{
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, ZERO ) );
		}*/
		responseString.append( com.Constant.CONST_START_INPUT_END );

		responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].coverCodes.covTypeCode'" );
		responseString.append( com.Constant.CONST_ID_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].coverCodes.covTypeCode'" );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, coverInPackage.getCoverCodes().getCovTypeCode() ) );
		if( !Utils.isEmpty( coverInPackage.getCoverCodes() ) && !Utils.isEmpty( coverInPackage.getCoverCodes().getCovTypeCode() ) ){
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, coverInPackage.getCoverCodes().getCovTypeCode() ) );
		}
		/*else{
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, ZERO ) );
		}*/
		responseString.append( com.Constant.CONST_START_INPUT_END );

		responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].riskCodes.riskCode'" );
		responseString.append( com.Constant.CONST_ID_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].riskCodes.riskCode'" );
		if( !Utils.isEmpty( coverInPackage.getCoverCodes() ) && !Utils.isEmpty( coverInPackage.getCoverCodes().getCovSubTypeCode() ) ){
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, coverInPackage.getRiskCodes().getRiskCode() ) );
		}
		responseString.append( com.Constant.CONST_START_INPUT_END );

		responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].riskCodes.basicRskCode'" );
		responseString.append( com.Constant.CONST_ID_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].riskCodes.basicRskCode'" );
		if( !Utils.isEmpty( coverInPackage.getRiskCodes() ) && !Utils.isEmpty( coverInPackage.getRiskCodes().getBasicRskCode() ) ){
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, coverInPackage.getRiskCodes().getBasicRskCode() ) );
		}
		responseString.append( com.Constant.CONST_START_INPUT_END );

		responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].riskCodes.riskCat'" );
		responseString.append( com.Constant.CONST_ID_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].riskCodes.riskCat'" );
		if( !Utils.isEmpty( coverInPackage.getRiskCodes() ) && !Utils.isEmpty( coverInPackage.getRiskCodes().getRiskCat() ) ){
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, coverInPackage.getRiskCodes().getRiskCat() ) );
		}
		responseString.append( com.Constant.CONST_START_INPUT_END );

		responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].riskCodes.riskType'" );
		responseString.append( com.Constant.CONST_ID_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].riskCodes.riskType'" );
		if( !Utils.isEmpty( coverInPackage.getRiskCodes() ) && !Utils.isEmpty( coverInPackage.getRiskCodes().getRiskType() ) ){
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, coverInPackage.getRiskCodes().getRiskType() ) );
		}
		responseString.append( com.Constant.CONST_START_INPUT_END );

		responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].riskCodes.basicRskId'" );
		responseString.append( com.Constant.CONST_ID_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].riskCodes.basicRskId'" );
		if( !Utils.isEmpty( coverInPackage.getRiskCodes() ) && !Utils.isEmpty( coverInPackage.getRiskCodes().getBasicRskId() ) ){
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, coverInPackage.getRiskCodes().getBasicRskId() ) );
		}
		responseString.append( com.Constant.CONST_START_INPUT_END );

		responseString.append( com.Constant.CONST_INPUT_NONCLOSING );
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].riskCodes.rskId'" );
		responseString.append( com.Constant.CONST_ID_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS + String.valueOf( coverCount ) + "].riskCodes.rskId'" );
		if( !Utils.isEmpty( coverInPackage.getRiskCodes() ) && !Utils.isEmpty( coverInPackage.getRiskCodes().getRskId() ) ){
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, coverInPackage.getRiskCodes().getRskId() ) );
		}
		responseString.append( com.Constant.CONST_START_INPUT_END );

		/*responseString.append( com.Constant.CONST_INPUT_END );
		responseString.append( String.format( ATTR_TEMPLATE, "type", com.Constant.CONST_HIDDEN ) );
		responseString.append( com.Constant.CONST_NAME_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS_END + String.valueOf( coverCount ) + "].vsd'" );
		responseString.append( com.Constant.CONST_ID_TRAVELPACKAGELIST_END + String.valueOf( packageCount ) + com.Constant.CONST_COVERS_END + String.valueOf( coverCount ) + "].vsd'" );
		if( !Utils.isEmpty( coverInPackage.getVsd() ) ){
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, coverInPackage.getVsd() ) );
		}
		responseString.append( com.Constant.CONST_INPUT_END );*/

		/*End : Hidden fields for save.*/
	}	

	@Override
	public void buildEmptyControl(JspWriter out) throws IOException {
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}
}
