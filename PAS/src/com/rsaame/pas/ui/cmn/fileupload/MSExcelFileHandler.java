package com.rsaame.pas.ui.cmn.fileupload;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.print.attribute.standard.Fidelity;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.rsaame.pas.vo.bus.CashResidenceVO;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EquipmentVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;

/**
 * @author m1016996
 * 
 * This Class represents the Excel file operation, corresponding to the Sections.
 *
 */
public class MSExcelFileHandler{

	private static final int CIR_EMP_NAME_COL = 0;
	private static final int CIR_OCCUPATION_COL = 1;
	private static final int CIR_AMOUNT_COL = 2;

	
	private static final int FIRST_COLUMN = 0;
	private static final int SECOND_COLUMN = 1;
	private static final int THIRD_COLUMN = 2;
	private static final int FORTH_COLUMN = 3;
	private static final int FIFTH_COLUMN = 4;
	private static final int SIXTH_COLUMN = 5;
	private static final int SEVENTH_COLUMN = 6;
	
	
	
	
	/**
	 * This Function can be used by any sections to read the Data from the excel file,
	 * it will return the Combinations of rows and columns. each row will be having the list of
	 * cells.
	 * 
	 * And this function will read the data irrespective of Excel file type. ( xls or xlsx ).
	 * 
	 * @param fileName
	 * @return list of rows
	 */

	private List<List<Cell>> getFileData( String fileName ){
		FileInputStream xlsIn = null;

		Workbook wb = null;
		try{
			xlsIn = new FileInputStream( fileName );

			wb = WorkbookFactory.create( xlsIn );
		}
		catch( FileNotFoundException e ){
			throw new BusinessException( "pas.cmn.fileNotFound", e, "Error : Could not find the file [", fileName, "]" );
		}
		catch( InvalidFormatException e ){
			throw new BusinessException( "pas.cmn.fileReadFailed", e, "Excel file is not of the right format" );
		}
		catch( IOException e ){
			throw new BusinessException( "pas.cmn.fileReadFailed", e, "Error in reading the data from file" );
		}

		Sheet sheet = wb.getSheetAt( 0 );
		Iterator<Row> rowIter = sheet.rowIterator();
		List<List<Cell>> listOfRows = new ArrayList<List<Cell>>();

		while( rowIter.hasNext() ){
			Row row = (Row) rowIter.next();
			Iterator<Cell> cellIter = row.cellIterator();
			List<Cell> listOfCells = new ArrayList<Cell>();
			while( cellIter.hasNext() ){
				Cell cell = (Cell) cellIter.next();
				listOfCells.add( cell );
			}

			listOfRows.add( listOfCells );
		}

		return listOfRows;
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

		List<List<Cell>> listOfRows = getFileData( fileName );
		int noOfRows = listOfRows.size();

		/**
		 *  Looping starts from the second row because, the first row we defined the headings for columns 
		 * 
		 */
		for( int i = 1; i < noOfRows; i++ ){
			List<Cell> row = listOfRows.get( i );
			CashResidenceVO cashResidenceVO = new CashResidenceVO();

			int rowSize = row.size();
			for( int j = 0; j < rowSize; j++ ){
				Cell myCell = row.get( j );
				Integer cellType = myCell.getCellType();

				/**
				 * The below "if" condition validates the data. if it has the invalidate data
				 * it throws the business Exception. 
				 */
				if( ( cellType != HSSFCell.CELL_TYPE_STRING && j == 0 ) || ( cellType != HSSFCell.CELL_TYPE_STRING && j == 1 )
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
				}
			}

			/**
			 * MoneyVO.getCashResDetails() will never return null because we are populating in the EmpDetFileUploadRH. 
			 */
			moneyVO.getCashResDetails().add( cashResidenceVO );
		}

		return moneyVO;
	}
//	public EEVO readFileForEE( String fileName, EEVO eeVO ){
//
//		List<List<Cell>> listOfRows = getFileData( fileName );
//		int noOfRows = listOfRows.size();
//
//		/**
//		 *  Looping starts from the second row because, the first row we defined the headings for columns 
//		 * 
//		 */
//		for( int i = 1; i < noOfRows; i++ ){
//			List<Cell> row = listOfRows.get( i );
//			EquipmentVO equipmentVO = new EquipmentVO();
//
//			int rowSize = row.size();
//			for( int j = 0; j < rowSize; j++ ){
//				Cell myCell = row.get( j );
//				Integer cellType = myCell.getCellType();
//
//				/**
//				 * The below "if" condition validates the data. if it has the invalidate data
//				 * it throws the business Exception. 
//				 */
//				if( ( cellType != HSSFCell.CELL_TYPE_STRING && j == 0 ) ||
//						( cellType != HSSFCell.CELL_TYPE_STRING && j == 1 )	|| 
//						( cellType == HSSFCell.CELL_TYPE_NUMERIC && j == 2 )|| 
//						( cellType != HSSFCell.CELL_TYPE_NUMERIC && j == 3 ))
//				{
//					throw new BusinessException( "pas.cmn.incorrectData", null, "File has incorrect data" );
//				}
//
//				if( j == CIR_EMP_NAME_COL ){
//					equipmentVO.setEquipmentType( myCell.getStringCellValue());
//				}
//				else if( j == CIR_OCCUPATION_COL ){
//					cashResidenceVO.setOccupation( myCell.getStringCellValue() );
//				}
//				else if( j == CIR_AMOUNT_COL ){
//					SumInsuredVO sumInsuredVO = new SumInsuredVO();
//					sumInsuredVO.setSumInsured( myCell.getNumericCellValue() );
//					cashResidenceVO.setSumInsuredDets( sumInsuredVO );
//				}
//			}
//
//			/**
//			 * MoneyVO.getCashResDetails() will never return null because we are populating in the EmpDetFileUploadRH. 
//			 */
//			moneyVO.getCashResDetails().add( cashResidenceVO );
//		}
//
//		return moneyVO;
//	}
	

}
