/**
 * 
 */
package com.rsaame.pas.ui.cmn.fileupload;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Map;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.CommodityDetailsVO;
import com.rsaame.pas.vo.bus.GoodsInTransitVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * @author m1016996
 *
 */
public class CommodityDetailsFileUploadRH extends GenericFileUploadRH{
	
	private static final int MODE_OF_TRANSIT_COL = 0;
	//private static final int VOYAGE_FROM_COL = 1;
	//private static final int VOYAGE_TO_COL = 2;
	private static final int COMMODITY_TYPE_COL = 1;
	private static final int DESC_GOODS_COL = 2;
	private static final int SI_BASIS_COL = 3;
	//private static final int DEDUCTIBLE_COL = 6;
	
	private static final Map<String, String> gitMTDescMap = new Map<String, String>( null, null );
	//private static final Map<String, String> gitVoyageToDescMap = new Map<String, String>( null, null );
	//private static final Map<String, String> gitVoyageFromDescMap = new Map<String, String>( null, null );
	private static final Map<String, String> gitCommodityDescMap = new Map<String, String>( null, null );
	private static final Map<String, String> gitSIBasisDescMap = new Map<String, String>( null, null );

	/**
	 * Here the money section related changes can be done,
	 * here we populate the moneyVO for cash in residence details and return it.
	 *  
	 */
	@Override
	protected BaseVO sectionRelatedChanges( HttpServletRequest request, Response response, String fileName ){
		
		
		

		/*
		 * 1. Get PolicyVO from PolicyContext
		 * 2. Get SectionVO from PolicyVO
		 * 3. Get LocationVO from SectionVO
		 * 4. Get MoneyVO for the locationVO obtained in step 3
		 * 5. SetPageData i.e. AppUtils.setSectionPageDataForJSON
		 * 6. It will always be case of reload hence implement the logic of SectionRHUtils.getLocationReloadJSP blindly
		 * 
		 */
		GoodsInTransitVO goodsInTransitVO = new GoodsInTransitVO();
		PolicyVO policyVO = PolicyContextUtil.getPolicyContext( request ).getPolicyDetails();

		SectionVO sectionVO = PolicyUtils.getSectionVO( policyVO, AppConstants.SECTION_ID_GIT );
		LocationVO locationVO = (LocationVO) PolicyUtils.getRiskGroupForProcessing( sectionVO );

		/*goodsInTransitVO = (GoodsInTransitVO) PolicyUtils.getRiskGroupDetails( locationVO, sectionVO );
		if( Utils.isEmpty( goodsInTransitVO ) ){
			goodsInTransitVO = new GoodsInTransitVO();
		}*/

		if( !Utils.isEmpty( fileName ) ){

			File file = null;
			file = new File( fileName );

			if( file.exists() ){
				getGITLookupMAP( request, policyVO );
				goodsInTransitVO = readFileForCommodityDetails( fileName, goodsInTransitVO );
			}

			AppUtils.setSectionPageDataForJSON( request, sectionVO, locationVO, goodsInTransitVO, policyVO );
			setRedirectionIfAny( response );

		}
		return goodsInTransitVO;
	}
	
	

	/**
	 * Get the file name based on the risk group id
	 * and CIR_ key word to identify the Cash in residence file.
	 * 
	 */
	@Override
	protected String getFileName( String fileName, HttpServletRequest request ){

		String riskGroupId = request.getParameter( "riskGroupId" );

		String extension = fileName.substring( fileName.lastIndexOf( "." ) + 1 );

		return "GIT_COMM_"+riskGroupId + "." + extension;
	}

	/**
	 * Set the redirection for the Employee detail file upload. (CASH IN RESIDENCE)
	 * 
	 */
	@Override
	protected void setRedirectionIfAny( Response responseObj ){

		Redirection redirection = new Redirection();
		String redirectionURL = SectionRHUtils.getLocationReloadJSP( Integer.valueOf( Utils.getSingleValueAppConfig( "GOODS_IN_TRANSIT_SECTION" ) ), true );
		redirection.setUrl( redirectionURL );
		redirection.setType( Type.TO_JSP );
		responseObj.setRedirection( redirection );
	}
	
	public GoodsInTransitVO readFileForCommodityDetails( String fileName, GoodsInTransitVO goodsInTransitVO ){

		List<Map<Integer, Cell>> listOfRows = getFileData( fileName, null );
		int noOfRows = listOfRows.size();
				
		for( int i = 1; i < noOfRows; i++ ){
			Map<Integer, Cell> row = listOfRows.get( i );
			CommodityDetailsVO commodityDetailsVO = new CommodityDetailsVO();
			Cell myCell = null;

			if( row.containsKey( MODE_OF_TRANSIT_COL ) ){
				myCell = row.get( MODE_OF_TRANSIT_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
					String eeTypeCode = null;
					if( gitMTDescMap.containsKey( myCell.getStringCellValue().toUpperCase() ) ){
						eeTypeCode = (String) gitMTDescMap.get( myCell.getStringCellValue().toUpperCase() );
						commodityDetailsVO.setModeOfTransit( Short.valueOf( eeTypeCode ));
					}
				}
			}
			else{
				commodityDetailsVO.setModeOfTransit( null );
			}
			
			/*if( row.containsKey( VOYAGE_FROM_COL ) ){
				myCell = row.get( VOYAGE_FROM_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
					String eeTypeCode = null;
					if( gitVoyageFromDescMap.containsKey( myCell.getStringCellValue() ) ){
						eeTypeCode = (String) gitVoyageFromDescMap.get( myCell.getStringCellValue() );
						commodityDetailsVO.setVoyageFrom( eeTypeCode );
					}
				}
			}
			else{
				commodityDetailsVO.setVoyageFrom( null );
			}
			
			if( row.containsKey( VOYAGE_TO_COL) ){
				myCell = row.get( VOYAGE_TO_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
					String eeTypeCode = null;
					if( gitVoyageToDescMap.containsKey( myCell.getStringCellValue() ) ){
						eeTypeCode = (String) gitVoyageToDescMap.get( myCell.getStringCellValue() );
						commodityDetailsVO.setVoyageTo( eeTypeCode );
					}
				}
			}
			else{
				commodityDetailsVO.setVoyageTo( null );
			}*/
			
			if( row.containsKey( COMMODITY_TYPE_COL) ){
				myCell = row.get( COMMODITY_TYPE_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
					String eeTypeCode = null;
					if( gitCommodityDescMap.containsKey( myCell.getStringCellValue().toUpperCase() ) ){
						eeTypeCode = (String) gitCommodityDescMap.get( myCell.getStringCellValue().toUpperCase() );
						commodityDetailsVO.setCommodityType( Integer.valueOf( eeTypeCode ));
					}
				}
			}
			else{
				commodityDetailsVO.setCommodityType( null );
			}
						
			if( row.containsKey( DESC_GOODS_COL ) ){
				myCell = row.get( DESC_GOODS_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
					commodityDetailsVO.setGoodsDescription( myCell.getStringCellValue() );
				}
				else if(myCell.getCellType() != HSSFCell.CELL_TYPE_BLANK){
					commodityDetailsVO.setGoodsDescription( String.valueOf( myCell.getNumericCellValue() ) );
				}

			}
			else{
				commodityDetailsVO.setGoodsDescription(null );
			}
			
			if( row.containsKey( SI_BASIS_COL) ){
				myCell = row.get( SI_BASIS_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
					String eeTypeCode = null;
					if( gitSIBasisDescMap.containsKey( myCell.getStringCellValue().toUpperCase() ) ){
						eeTypeCode = (String) gitSIBasisDescMap.get( myCell.getStringCellValue().toUpperCase() );
						commodityDetailsVO.setSiBasis( BigDecimal.valueOf( Double.valueOf( eeTypeCode )));
					}
				}
			}
			else{
				commodityDetailsVO.setSiBasis( null );
			}

			/*if( row.containsKey( DEDUCTIBLE_COL ) ){
				myCell = row.get( DEDUCTIBLE_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC ){
					commodityDetailsVO.setDeductible( myCell.getNumericCellValue() );
				}
			}
			else{
				commodityDetailsVO.setDeductible( null );
			}*/

			

			goodsInTransitVO.getCommodityDtls().add( commodityDetailsVO );
		}
		
		return goodsInTransitVO;
	}
	
	private void getGITLookupMAP( HttpServletRequest request, PolicyVO policyVO ){
			
			getGITMTDescription(request,policyVO);
			//getGITVoyageFromDescMap(request,policyVO);
			//getGITVoyageToDescMap(request,policyVO);
			getGITCommodityTypeDescMap(request,policyVO);
			getGITSIBasisDescMap(request,policyVO);
			
	}
	
	protected void getGITMTDescription( HttpServletRequest request, PolicyVO policyVO ){
		LookUpListVO listVO = SvcUtils
				.getLookUpCodesList( "MODE_OF_TRANSIT", "ALL", "ALL" );
		for( LookUpVO luVO : listVO.getLookUpList() ){
			gitMTDescMap.put( luVO.getDescription().toUpperCase(), luVO.getCode().toString() );
		}
	}
	
	/*protected void getGITVoyageFromDescMap( HttpServletRequest request, PolicyVO policyVO ){
		LookUpListVO listVO = SvcUtils
				.getLookUpCodesList( "VOYAGE_FROM", "ALL", "ALL" );
		for( LookUpVO luVO : listVO.getLookUpList() ){
			gitVoyageFromDescMap.put( luVO.getDescription(), luVO.getCode().toString() );
		}
	}
	
	protected void getGITVoyageToDescMap( HttpServletRequest request, PolicyVO policyVO ){
		LookUpListVO listVO = SvcUtils
				.getLookUpCodesList( "VOYAGE_TO", "ALL", "ALL" );
		for( LookUpVO luVO : listVO.getLookUpList() ){
			gitVoyageToDescMap.put( luVO.getDescription(), luVO.getCode().toString() );
		}
	}*/
	
	protected void getGITCommodityTypeDescMap( HttpServletRequest request, PolicyVO policyVO ){
		LookUpListVO listVO = SvcUtils
				.getLookUpCodesList( "COMMODITY_TYPE", policyVO.getScheme().getSchemeCode().toString(), policyVO.getScheme().getTariffCode().toString() );
		for( LookUpVO luVO : listVO.getLookUpList() ){
			gitCommodityDescMap.put( luVO.getDescription().toUpperCase(), luVO.getCode().toString() );
		}
	}
	
	protected void getGITSIBasisDescMap( HttpServletRequest request, PolicyVO policyVO ){
		LookUpListVO listVO = SvcUtils
				.getLookUpCodesList( "SI_BASIS", "ALL", "ALL");
		for( LookUpVO luVO : listVO.getLookUpList() ){
			gitSIBasisDescMap.put( luVO.getDescription().toUpperCase(), luVO.getCode().toString() );
		}
	}
}
