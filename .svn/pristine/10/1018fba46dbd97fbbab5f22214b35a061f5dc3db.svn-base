package com.rsaame.pas.ui.cmn;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
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
public abstract class FileUploadRH implements IRequestHandler{

	private static final Logger LOGGER = Logger.getLogger(FileUploadRH.class);
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
								//CTS 06.08.2020 CR#613 -Document attachment change
								if( param.equals( "SURVEY_DETAILS" ) || param.equals( "TRADE_LICENCE" ) || param.equals("PAS_HOME_DOC") || param.equals("PAS_Travel_DOC")){
									
									//CTS 06.08.2020 CR#613 -Document attachment change
									/*
									 * Type1 files--> .doc, .docx, .pdf and .jpeg
									 */
									responseObj.addErrorKey( "pas.upload.fileTypeNotSupportedType1" );
							
								}
								else{
								
									/*
									 * Type2 files--> .xls or .xlsx.
									 */
									responseObj.addErrorKey( "pas.upload.fileTypeNotSupportedType2");
							
								}
							} else if(param.equals("TRADE_LICENCE")) {
								 if(size > Double.valueOf( Utils.getSingleValueAppConfig( "TRADE_LICENCE_FILE_SIZE" )) ) {
									noError = false;
									responseObj.addErrorKey( "pas.upload.tlc.fileSizeTooHigh" );
								 }//CTS 06.08.2020 CR#613 -Document attachment change
							} else if(param.equals("PAS_HOME_DOC") || param.equals("PAS_Travel_DOC")){
								if(size > Double.valueOf( Utils.getSingleValueAppConfig( param+"_FILE_SIZE" )) ) {
									noError = false;
									responseObj.addErrorKey( "pas.upload.tlc.fileSizeTooHigh" );
								 }
							}//CTS 06.08.2020 CR#613 -Document attachment change
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
							//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
							if(param.equals("PAS_HOME_DOC") || param.equals("PAS_Travel_DOC")){
								String licCertType=request.getParameter( "licCertType" );
								if(licCertType.startsWith(Utils.getSingleValueAppConfig("EMIRATE_DEFAULT_DOCID"))){
								CommonVO commonVO = PolicyContextUtil.getPolicyContext( request ).getCommonDetails();
								int startpos=path.lastIndexOf("/")+ 1;
								String InsuredCode=path.substring(startpos);
								fileName=fileName.replace(commonVO.getQuoteNo().toString(),InsuredCode );  
								}}
								//CTS - 21.10.2020 - CR#16903 IA Emirates CR - End
							File uploadedFile = new File( path, fileName );
							item.write( uploadedFile );

							sectionRelatedChanges( request, responseObj, path + "/" + fileName );

							responseObj.addErrorKey( "pas.upload.successful" );
							responseObj.setResponseType( Response.Type.DOJO_IFRAME );
							responseObj.setSuccess( true );

						}
						else{
							responseObj.addErrorKey( com.Constant.CONST_PAS_UPLOAD_UNSUCCESSFUL );
							LOGGER.debug("************Upload unsuccessful**********");
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
			LOGGER.debug("************Upload unsuccessful**********");
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
		//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
		       String param= request.getParameter( "param");
        
        if(param.equals("PAS_HOME_DOC") || param.equals("PAS_Travel_DOC")){
			String licenceType = request.getParameter( "licCertType" );
			if(licenceType.startsWith(Utils.getSingleValueAppConfig("EMIRATE_DEFAULT_DOCID"))){
				param=Utils.getSingleValueAppConfig("EMIRATE_DEFAULT_DOCID");
			}
		}
	
        
		String modulePath = Utils.getSingleValueAppConfig( Utils.concat( "FILE_UPLOAD_" + param + "_FOLDER" ) );
		//CTS - 21.10.2020 - CR#16903 IA Emirates CR - End
		//String modulePath = Utils.getSingleValueAppConfig( Utils.concat( "FILE_UPLOAD_" + request.getParameter( "param" ) + "_FOLDER" ) );
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
			//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
			CommonVO commonVO = PolicyContextUtil.getPolicyContext( request ).getCommonDetails();
			//path = path + "/" +commonVO.getQuoteNo();
							  if(param.startsWith(Utils.getSingleValueAppConfig("EMIRATE_DEFAULT_DOCID"))){
					
						String InsuredCode=DAOUtils.FetchInsuredCode(commonVO.getQuoteNo().toString());
								//fetchOwnershipInfo(commonVO.getQuoteNo().toString());
						path=path+ "/" +InsuredCode;
					
				}else{
			  path = path + "/" +commonVO.getQuoteNo();
				}
//CTS - 21.10.2020 - CR#16903 IA Emirates CR - END
			
			
		}

		File directory = new File( path );
		if( !directory.exists() ){
			boolean success = ( new File( path ) ).mkdirs();
			if(success){
				LOGGER.debug( "Directory created successfully "+ path);
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
}
