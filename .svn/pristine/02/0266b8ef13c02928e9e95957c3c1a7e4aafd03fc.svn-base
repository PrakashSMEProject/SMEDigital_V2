package com.rsaame.pas.ui.cmn.fileupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Map;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author m1016996
 * 
 */
public abstract class GenericFileUploadRH implements IRequestHandler{

	private static final Logger LOGGER = Logger.getLogger(DAOUtils.class);

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){

		Response responseObj = new Response();
		try{

			boolean isMultipart = ServletFileUpload.isMultipartContent( request );
			String param = request.getParameter( "param" );

			/* Set default case as "OTHERS" */
			if( Utils.isEmpty( param ) ){
				param = "OTHERS";
			}

			/*
			 * Get the root path for uploading a file, throw an exception if root 
			 * path is not set in the Server area.
			 */
			String rootPath = Utils.getSingleValueAppConfig( "FILE_UPLOAD_ROOT" );
			if( Utils.isEmpty( rootPath ) ){
				throw new SystemException( "pas.upload.rootFolderNotSet", null, "Root path for file uploads has not been set." );
			}

			/*
			 * Get the path for uploading the file. its actually folder where you want to save 
			 * the file under above found root path.
			 */
			String path = getUploadingPath( rootPath, request );
			if( isMultipart ){
				request.setAttribute( "responseType", "DOJO_IFRAME" );

				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload( factory );

				List items = upload.parseRequest( request );
				Iterator<FileItem> iter = items.iterator();
				while( iter.hasNext() ){
					FileItem item = (FileItem) iter.next();
					if( !item.isFormField() ){
						String fileName = item.getName();
						double size = (double) item.getSize() / ( 1024 * 1024 );
						String extension = null;

						/*
						 * Get the extension of the file. 
						 */
						if(!Utils.isEmpty( fileName )){
							if( fileName.lastIndexOf( "." ) > 0 ){
								extension = fileName.substring( fileName.lastIndexOf( "." ) + 1 );
							}
						}

						boolean noError = true;
						boolean success = true;
						if(!Utils.isEmpty( extension )){
							if( !isAllowedExtn( extension.toLowerCase(), param ) ){
								noError = false;
								if( param.equals( "SURVEY_DETAILS" ) || param.equals( "TRADE_LICENCE" ) ){

									/*
									 * Type1 files--> .doc, .docx, .pdf and .jpeg
									 */
									responseObj.addErrorKey( "pas.upload.fileTypeNotSupportedType1" );

								}
								else{

									/*
									 * Type2 files--> .xls or .xlsx.
									 */
									responseObj.addErrorKey( "pas.upload.fileTypeNotSupportedType2" );
								
								}
							}
							else if( size > getUploadableFileSize( param ) ){
								noError = false;
								responseObj.addErrorKey( "pas.upload.fileSizeTooHigh" );

							}
						}	
							
						if( noError && success ){

							int i = fileName.lastIndexOf( "\\" );
							if( i != -1 ){
								fileName = fileName.substring( i + 1 );
							}
							fileName = getFileName( fileName, request );
							File uploadedFile = new File( path, fileName );
							item.write( uploadedFile );

							sectionRelatedChanges( request, responseObj, path + "/" + fileName );

							responseObj.addErrorKey( "pas.upload.successful" );							
							responseObj.setResponseType( Response.Type.DOJO_IFRAME );
							responseObj.setSuccess( true );

						}
						else{
							responseObj.addErrorKey( com.Constant.CONST_PAS_UPLOAD_UNSUCCESSFUL );
							responseObj.setResponseType( Response.Type.DOJO_IFRAME );
							responseObj.setSuccess( false );
						}

					}
				}
			}

		}
		catch( BusinessException be ){

			List<String> errorKeys = be.getErrorKeysList();
			responseObj.addErrorKey( com.Constant.CONST_PAS_UPLOAD_UNSUCCESSFUL );
			for( String errorKey : errorKeys ){
				responseObj.addErrorKey( errorKey );
			}
			responseObj.setSuccess( false );
			responseObj.setResponseType( Response.Type.DOJO_IFRAME );
		}
		catch( Exception e ){
			responseObj.addErrorKey( com.Constant.CONST_PAS_UPLOAD_UNSUCCESSFUL );
			responseObj.setSuccess( false );
			responseObj.setResponseType( Response.Type.DOJO_IFRAME );
		}

		return responseObj;
	}

	/**
	 * * This method returns true if the particular file type can be uploaded for particular section.
	 * 
	 * @param extension
	 * @param param
	 * @return 
	 */
	private boolean isAllowedExtn( String extension, String param ){
		boolean isAllowed = false;
		if( Utils.isEmpty( extension ) ){
			return isAllowed;
		}

		String[] extns = Utils.getMultiValueAppConfig( param + "_" + "FILE_UPLOAD_ALLOWED_EXTNS" );

		if( !Utils.isEmpty( extns ) ){
			Utils.trimAllEntries( extns );
			List<String> extnsList = CopyUtils.asList( extns );

			if( extnsList.contains( extension ) ) isAllowed = true;
		}

		return isAllowed;
	}

	/**
	 * * This method will return the allowed size in MB which is configured in the appconfig property
	 * 	 file
	 * 
	 * @param section
	 * @return
	 */
	protected Double getUploadableFileSize( String section ){

		return Double.valueOf( Utils.getSingleValueAppConfig( "FILE_SIZE_LIMIT" ) );
	}

	/**
	 * This method returns the uploading path for the file, based on the Section specific parameter.
	 * 
	 * @param rootPath
	 * @param request
	 * @return
	 */

	protected String getUploadingPath( String rootPath, HttpServletRequest request ){

		String modulePath = Utils.getSingleValueAppConfig( Utils.concat( "FILE_UPLOAD_" + request.getParameter( "param" ) + "_FOLDER" ) );
		String path = Utils.concat( rootPath, "/", Utils.isEmpty( modulePath ) ? "" : modulePath );

		PolicyVO policyDetails = PolicyContextUtil.getPolicyContext( request ).getPolicyDetails();
		if(!Utils.isEmpty(policyDetails))
		{
			Long newEndtId = policyDetails.getNewEndtId();

			if( Utils.isEmpty( newEndtId ) ){
				path = path + "/" + policyDetails.getPolLinkingId();
				LOGGER.debug( "PATH "+path );
			}
			else{
				path = path + "/" + policyDetails.getPolLinkingId();

			}
		}
		else
		{
			CommonVO commonVO = PolicyContextUtil.getPolicyContext( request ).getCommonDetails();
			path = path +commonVO.getPolicyId();
		}

		File directory = new File( path );
		if( !directory.exists() ){
			boolean success = ( new File( path ) ).mkdirs();
			if(success){
				LOGGER.debug( "Directory created sucessfully"+ path );
			}
		}

		return path;
	}

	/**
	 * This method can be overridden by any other section for section related functionalities.
	 * 
	 * @param request
	 * @param response
	 * @param fileName
	 * @return
	 */
	protected BaseVO sectionRelatedChanges( HttpServletRequest request, Response response, String fileName ){
		return null;
	}

	/**
	 * This abstract method gives the file name, this function is made abstract because
	 * every section which uploads the file should have particular format for file Name.
	 * 
	 * @param fileName
	 * @param request
	 * @return
	 */
	protected abstract String getFileName( String fileName, HttpServletRequest request );

	/**
	 * This method can be overridden by any other sections which perform the file operation,
	 * only if they have particular form of re direction other than normal Success/error message
	 * display.
	 * 
	 * @param responseObj
	 */
	protected void setRedirectionIfAny( Response responseObj ){
		//override this method if any specific redirection is required for a particular file upload
	}

	public List<List<Cell>> getFileData( String fileName ){
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

	public List<Map<Integer, Cell>> getFileData( String fileName, String section ){
		FileInputStream xlsIn = null;

		HSSFWorkbook wb= null;
		try{
			xlsIn = new FileInputStream( fileName );
			wb = new HSSFWorkbook(xlsIn); 
			Biff8EncryptionKey.setCurrentUserPassword(Utils.getSingleValueAppConfig( "FILE_UPLOAD_EXCEL_PASSWORD" ));
			//wb = WorkbookFactory.create( xlsIn );
		}
		catch( FileNotFoundException e ){
			throw new BusinessException( "pas.cmn.fileNotFound", e, "Error : Could not find the file _2", fileName, "]" );
		}		
		catch( IOException e ){
			throw new BusinessException( "pas.cmn.fileReadFailed", e, "Error in reading the data from fil_2" );
		}

		HSSFSheet  sheet = wb.getSheetAt(0);
		Iterator<Row> rowIter = sheet.rowIterator();
		List<Map<Integer, Cell>> listOfRows = new ArrayList<Map<Integer, Cell>>();
		List<Cell> cellsList = null;
		while( rowIter.hasNext() ){
			HSSFRow row =  (HSSFRow) rowIter.next();
			Iterator<Cell> cellIter = row.cellIterator();
			//List<Cell> listOfCells = new ArrayList<Cell>();
			Map<Integer, Cell> listOfCells = new Map<Integer,Cell>( null, null );
			cellsList = new ArrayList<Cell>();
			while( cellIter.hasNext() ){
				HSSFCell myCell = (HSSFCell) cellIter.next();
				listOfCells.put( myCell.getColumnIndex(), myCell );
				if( myCell.getCellType() != HSSFCell.CELL_TYPE_BLANK ){
					cellsList.add( myCell );
				}
			}
			if( !cellsList.isEmpty() ){
				listOfRows.add( listOfCells );
			}
		}
		if( listOfRows.size() <= 1 ){
			throw new BusinessException( "pas.upload.fileIsEmpty", null, "Uploaded file is empty" );
		}
		Biff8EncryptionKey.setCurrentUserPassword(null);
		return listOfRows;
	}
	
	public List<Map<Integer, Cell>> getFileDataMonoline( String fileName, String section ){
		FileInputStream xlsIn = null;

		Workbook workbook =null;
		Sheet  sheet = null;
		try{  			
			xlsIn = new FileInputStream( fileName );
			if(fileName.toLowerCase().endsWith("xlsx")){
                workbook = WorkbookFactory.create(xlsIn);
            }else if(fileName.toLowerCase().endsWith("xls")){
                workbook = new HSSFWorkbook(xlsIn);
            }
			Biff8EncryptionKey.setCurrentUserPassword(Utils.getSingleValueAppConfig( "FILE_UPLOAD_EXCEL_PASSWORD" ));
		}
		catch( FileNotFoundException e ){
			throw new BusinessException( "pas.cmn.fileNotFound", e, "Error : Could not find the file _3", fileName, "]" );
		}		
		catch( IOException e ){
			throw new BusinessException( "pas.cmn.fileReadFailed", e, "Error in reading the data from fil_3" );
		} 
		catch (InvalidFormatException e) {
			throw new BusinessException( "pas.cmn.fileReadFailed", e, "Error in reading the data from fil_4" );
		}		
			if(!Utils.isEmpty( workbook ))
			 sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIter = sheet.rowIterator();
			List<Map<Integer, Cell>> listOfRows = new ArrayList<Map<Integer, Cell>>();
			List<Cell> cellsList = null;
			while( rowIter.hasNext() ){
				Row row =  (Row) rowIter.next();
				Iterator<Cell> cellIter = row.cellIterator();
				Map<Integer, Cell> listOfCells = new Map<Integer,Cell>( null, null );
				cellsList = new ArrayList<Cell>();
				while( cellIter.hasNext() ){
					Cell myCell = (Cell) cellIter.next();
					listOfCells.put( myCell.getColumnIndex(), myCell );
					if( myCell.getCellType() != Cell.CELL_TYPE_BLANK ){
						cellsList.add( myCell );
					}
				}
				if( !cellsList.isEmpty() ){
					listOfRows.add( listOfCells );
				}
			}
			if( listOfRows.size() <= 1 ){
				throw new BusinessException( "pas.upload.fileIsEmpty", null, "Uploaded file is empty" );
			}
			Biff8EncryptionKey.setCurrentUserPassword(null);
		return listOfRows;
	}
}
