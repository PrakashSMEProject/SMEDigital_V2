package com.rsaame.pas.ui.cmn.fileupload;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
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
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EquipmentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;

/**
 * 
 * uploads the EE section
 * 
 * @author m1016996
 * 
 */
public class EEFileUploadRH extends GenericFileUploadRH{

	private static final int CIR_EQUIP_TYPE_COL = 0;
	private static final int CIR_DESCRIPTION_COL = 1;
	private static final int CIR_DEDUCTIBLE_COL = 3;
	private static final int CIR_YROFMAKE_COL = 4;
	private static final int CIR_SERIALNUM_COL = 5;
	private static final int CIR_QUANTITY_COL = 6;
	private static final int CIR_NEWREPLACEMENTVAL_COL = 2;
	private static final Map eeCodeDescMap = new Map<String, String>( null, null );
	private static final String ELECTRONIC_EQUIPMENT_CATEGORY = "ELECTRONIC_EQUIPMENT";

	/**
	 * Here the money section related changes can be done, here we populate the
	 * moneyVO for cash in residence details and return it.
	 * 
	 */
	@Override
	protected BaseVO sectionRelatedChanges( HttpServletRequest request, Response response, String fileName ){

		/*
		 * 1. Get PolicyVO from PolicyContext 2. Get SectionVO from PolicyVO 3.
		 * Get LocationVO from SectionVO 4. Get MoneyVO for the locationVO
		 * obtained in step 3 5. SetPageData i.e.
		 * AppUtils.setSectionPageDataForJSON 6. It will always be case of
		 * reload hence implement the logic of
		 * SectionRHUtils.getLocationReloadJSP blindly
		 */
		EEVO eevo = new EEVO();

		PolicyVO policyVO = PolicyContextUtil.getPolicyContext( request ).getPolicyDetails();

		SectionVO sectionVO = PolicyUtils.getSectionVO( policyVO, AppConstants.SECTION_ID_EE );
		LocationVO locationVO = (LocationVO) PolicyUtils.getRiskGroupForProcessing( sectionVO );

		/*
		 * eevo = (EEVO) PolicyUtils.getRiskGroupDetails( locationVO, sectionVO
		 * ); if( Utils.isEmpty( eevo ) ){ eevo = new EEVO(); }
		 */
		// eeVO.setCashInResidence( true );

		if( !Utils.isEmpty( fileName ) ){

			File file = null;
			file = new File( fileName );
			// MSExcelFileHandler readingExcelFiles = new MSExcelFileHandler();

			if( file.exists() ){
				getEETypeDescription( request, policyVO );
				eevo = readFileForEquipmentDetails( fileName, eevo );
			}

			AppUtils.setSectionPageDataForJSON( request, sectionVO, locationVO, eevo, policyVO );
			setRedirectionIfAny( response );

		}
		return eevo;
	}

	/**
	 * Get the file name based on the risk group id and CIR_ key word to
	 * identify the Cash in residence file.
	 * 
	 */
	@Override
	protected String getFileName( String fileName, HttpServletRequest request ){

		String riskGroupId = request.getParameter( "riskGroupId" );

		String extension = fileName.substring( fileName.lastIndexOf( "." ) + 1 );

		return "CIR_" + riskGroupId + "." + extension;
	}

	/**
	 * Set the redirection for the Employee detail file upload. (CASH IN
	 * RESIDENCE)
	 * 
	 */
	@Override
	protected void setRedirectionIfAny( Response responseObj ){

		Redirection redirection = new Redirection();
		String redirectionURL = SectionRHUtils.getLocationReloadJSP( Integer.valueOf( Utils.getSingleValueAppConfig( "EE_SECTION" ) ), true );
		redirection.setUrl( redirectionURL );
		redirection.setType( Type.TO_JSP );
		responseObj.setRedirection( redirection );
	}

	/**
	 * Populates EEVO from the data whatever is read from the upload EXcel
	 */
	public EEVO readFileForEquipmentDetails( String fileName, EEVO eeVO ){

		List<Map<Integer, Cell>> listOfRows = getFileData( fileName, null );
		int noOfRows = listOfRows.size();
		boolean isValidationPass = false;

		/**
		 * Looping starts from the second row because, the first row we defined
		 * the headings for columns
		 * 
		 */
		for( int i = 1; i < noOfRows; i++ ){
			Map<Integer, Cell> row = listOfRows.get( i );
			EquipmentVO equipmentVO = new EquipmentVO();
			SumInsuredVO sumInsuredVO = new SumInsuredVO();
			equipmentVO.setSumInsuredDetails( sumInsuredVO );
			Cell myCell = null;

			Double tempVar;
			if( row.containsKey( CIR_EQUIP_TYPE_COL ) ){
				myCell = row.get( CIR_EQUIP_TYPE_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
					String eeTypeCode = null;
					if( eeCodeDescMap.containsKey( myCell.getStringCellValue() ) ){
						eeTypeCode = (String) eeCodeDescMap.get( myCell.getStringCellValue() );
						equipmentVO.setEquipmentType( eeTypeCode );
					}
				}
			}
			else{
				equipmentVO.setEquipmentType( null );
			}
			if( row.containsKey( CIR_DESCRIPTION_COL ) ){
				myCell = row.get( CIR_DESCRIPTION_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
					equipmentVO.setEquipmentDesc( myCell.getStringCellValue() );
				}
				else if(myCell.getCellType() != HSSFCell.CELL_TYPE_BLANK){
					equipmentVO.setEquipmentDesc( String.valueOf( myCell.getNumericCellValue() ) );
				}

			}
			else{
				equipmentVO.setEquipmentDesc( null );
			}
			if( row.containsKey( CIR_NEWREPLACEMENTVAL_COL ) ){
				myCell = row.get( CIR_NEWREPLACEMENTVAL_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC ){
					sumInsuredVO.setSumInsured( myCell.getNumericCellValue() );
				}

			}
			else{
				sumInsuredVO.setSumInsured( null );
			}

			if( row.containsKey( CIR_DEDUCTIBLE_COL ) ){
				myCell = row.get( CIR_DEDUCTIBLE_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC ){
					sumInsuredVO.setDeductible( myCell.getNumericCellValue() );
					tempVar = myCell.getNumericCellValue();
					sumInsuredVO.setDeductible( myCell.getNumericCellValue() );
				}
			}
			else{
				sumInsuredVO.setDeductible( null );
			}

			if( row.containsKey( CIR_YROFMAKE_COL ) ){
				myCell = row.get( CIR_YROFMAKE_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC ){
					tempVar = myCell.getNumericCellValue();
					equipmentVO.setYearOfMake( String.valueOf( tempVar.longValue() ) );
				}
			}
			else{
				equipmentVO.setYearOfMake( null );
			}

			if( row.containsKey( CIR_SERIALNUM_COL ) ){
				myCell = row.get( CIR_SERIALNUM_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC ){
					tempVar = myCell.getNumericCellValue();
					equipmentVO.setSerialNumber( String.valueOf( tempVar.longValue() ) );
				}
			}
			else{
				equipmentVO.setSerialNumber( null );
			}

			if( row.containsKey( CIR_QUANTITY_COL ) ){
				myCell = row.get( CIR_QUANTITY_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC ){
					tempVar = myCell.getNumericCellValue();
					equipmentVO.setQuantity( tempVar.intValue() );
				}
			}
			else{
				equipmentVO.setQuantity( null );
			}

			eeVO.getEquipmentDtls().add( equipmentVO );
		}
		if( isValidationPass ){
			throw new BusinessException( "pas.cmn.incorrectData", null, "File has incorrect data" );
		}

		return eeVO;
	}

	/**
	 * Loads the EE look up (code , description)fields and stored it in map
	 * 
	 * @param request
	 * @param policyVO
	 */
	protected void getEETypeDescription( HttpServletRequest request, PolicyVO policyVO ){
		LookUpListVO listVO = SvcUtils.getLookUpCodesList( ELECTRONIC_EQUIPMENT_CATEGORY, policyVO.getScheme().getSchemeCode().toString(), policyVO.getScheme().getTariffCode()
				.toString() );
		for( LookUpVO luVO : listVO.getLookUpList() ){
			eeCodeDescMap.put( luVO.getDescription(), luVO.getCode().toString() );
		}
	}

}
