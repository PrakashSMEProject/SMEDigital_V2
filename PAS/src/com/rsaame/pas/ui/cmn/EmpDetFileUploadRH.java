package com.rsaame.pas.ui.cmn;

import java.io.File;
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
import com.rsaame.pas.ui.cmn.fileupload.GenericFileUploadRH;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.bus.CashResidenceVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;

/**
 * @author m1016996
 *
 */
public class EmpDetFileUploadRH extends GenericFileUploadRH{
	private static final int CIR_EMP_NAME_COL = 0;
	private static final int CIR_OCCUPATION_COL = 1;
	private static final int CIR_AMOUNT_COL = 2;
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
		MoneyVO moneyVO = new MoneyVO();

		PolicyVO policyVO = PolicyContextUtil.getPolicyContext( request ).getPolicyDetails();

		SectionVO sectionVO = PolicyUtils.getSectionVO( policyVO, AppConstants.SECTION_ID_MONEY );
		LocationVO locationVO = (LocationVO) PolicyUtils.getRiskGroupForProcessing( sectionVO );

		/*moneyVO = (MoneyVO) PolicyUtils.getRiskGroupDetails( locationVO, sectionVO );
		if( Utils.isEmpty( moneyVO ) ){
			moneyVO = new MoneyVO();
		}*/
		moneyVO.setCashInResidence( true );

		if( !Utils.isEmpty( fileName ) ){

			File file = null;
			file = new File( fileName );
			
			if( file.exists() ){
				moneyVO = readFileForCashInResidence( fileName, moneyVO );
			}

			AppUtils.setSectionPageDataForJSON( request, sectionVO, locationVO, moneyVO, policyVO );
			setRedirectionIfAny( response );

		}
		return moneyVO;
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

		return "CIR_"+riskGroupId + "." + extension;
	}

	/**
	 * Set the redirection for the Employee detail file upload. (CASH IN RESIDENCE)
	 * 
	 */
	@Override
	protected void setRedirectionIfAny( Response responseObj ){

		Redirection redirection = new Redirection();
		String redirectionURL = SectionRHUtils.getLocationReloadJSP( Integer.valueOf( Utils.getSingleValueAppConfig( "MONEY_SECTION" ) ), true );
		redirection.setUrl( redirectionURL );
		redirection.setType( Type.TO_JSP );
		responseObj.setRedirection( redirection );
	}
	/**
	 * 
	 * * This function is for Money section. 
	 * * This function will populate moneyVO using the data from the Excel file.
	 * 
	 * * Important : Like this we can add a new functions for other sections
	 * 			 	 Which uses the Above method  getFileData() to read the file.
	 * 			   	 for this fileName is mandatory, because we need it in getFileData().
	 * 		         and other parameter is depends on the Section.
	 * 
	 * @param fileName
	 * @param moneyVO 
	 * @return moneyVO
	 */

	public MoneyVO readFileForCashInResidence( String fileName, MoneyVO moneyVO ){

		List<Map<Integer, Cell>> listOfRows = getFileData( fileName, null );
		int noOfRows = listOfRows.size();

		/**
		 *  Looping starts from the second row because, the first row we defined the headings for columns 
		 * 
		 */
		for( int i = 1; i < noOfRows; i++ ){
			Map<Integer,Cell> row = listOfRows.get( i );
			CashResidenceVO cashResidenceVO = new CashResidenceVO();

			int rowSize = row.size();
			for( int j = 0; j < rowSize; j++ ){
				Cell myCell = row.get( j );
				Integer cellType = myCell.getCellType();

				/**
				 * The below "if" condition validates the data. if it has the invalidate data
				 * it throws the business Exception. 
				 */
				/*if( ( cellType != HSSFCell.CELL_TYPE_STRING && j == 0 ) || ( cellType != HSSFCell.CELL_TYPE_STRING && j == 1 )
						|| ( cellType == HSSFCell.CELL_TYPE_BLANK && j == 2 )|| ( cellType != HSSFCell.CELL_TYPE_NUMERIC && j == 2 )  ){

					throw new BusinessException( "pas.cmn.incorrectData", null, "File has incorrect data" );
				}

				if( j == CIR_EMP_NAME_COL ){
					cashResidenceVO.setEmpName( myCell.getStringCellValue() );
				}
				else if( j == CIR_OCCUPATION_COL ){
					cashResidenceVO.setOccupation( myCell.getStringCellValue() );
				}
				else if( j == CIR_AMOUNT_COL ){
					SumInsuredVO sumInsuredVO = new SumInsuredVO();
					sumInsuredVO.setSumInsured( myCell.getNumericCellValue() );
					cashResidenceVO.setSumInsuredDets( sumInsuredVO );
				}*/
				
				if(row.containsKey( CIR_EMP_NAME_COL )){
					myCell = row.get( CIR_EMP_NAME_COL );
					if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
						cashResidenceVO.setEmpName( myCell.getStringCellValue() );
					}
					else if(myCell.getCellType() != HSSFCell.CELL_TYPE_BLANK){
						String nameVal = String.valueOf(myCell.getNumericCellValue());
						if(nameVal.endsWith(".0")){
							cashResidenceVO.setEmpName( nameVal.substring(0, nameVal.length()-2) );
						}else{
							cashResidenceVO.setEmpName(nameVal);
						}
						
						
					}					
				}else{
					cashResidenceVO.setEmpName( null );
				}
				if(row.containsKey( CIR_OCCUPATION_COL )){
					myCell = row.get( CIR_OCCUPATION_COL );
					if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
						cashResidenceVO.setOccupation( myCell.getStringCellValue() );
					}
					else if(myCell.getCellType() != HSSFCell.CELL_TYPE_BLANK){
						String nameVal = String.valueOf(myCell.getNumericCellValue());
						if(nameVal.endsWith(".0")){
							cashResidenceVO.setOccupation( nameVal.substring(0, nameVal.length()-2) );
						}else{
							cashResidenceVO.setOccupation(nameVal);
						}
						
						
					}
					
				}else{
					cashResidenceVO.setOccupation( null );
				}
				if(row.containsKey( CIR_AMOUNT_COL )){
					myCell = row.get( CIR_AMOUNT_COL );
					if( myCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC ){
						SumInsuredVO sumInsuredVO = new SumInsuredVO();
						sumInsuredVO.setSumInsured( myCell.getNumericCellValue() );
						cashResidenceVO.setSumInsuredDets( sumInsuredVO );
					}
					
				}else{
					cashResidenceVO.setSumInsuredDets( null );
				}
			}

			/**
			 * MoneyVO.getCashResDetails() will never return null because we are populating in the EmpDetFileUploadRH. 
			 */
			moneyVO.getCashResDetails().add( cashResidenceVO );
		}

		return moneyVO;
	}

	

}

