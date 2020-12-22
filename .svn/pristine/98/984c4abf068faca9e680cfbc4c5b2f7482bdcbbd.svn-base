/**
 * 
 */
package com.rsaame.pas.tradeLicense.ui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.rsaame.pas.dao.utils.DAOUtils;
import java.util.ArrayList;	
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author M1016303
 *
 */
public class TradeLicenceDownloadRH implements IRequestHandler {

	/* (non-Javadoc)2428445
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) {
		CommonVO commonVO;
		String action=request.getParameter( "action" );
		//String homeparam=request.getParameter("param");
		Response resp = new Response();
		PolicyVO policyVO = PolicyContextUtil.getPolicyContext( request ).getPolicyDetails();
		if(Utils.isEmpty(policyVO))
		{
			commonVO = PolicyContextUtil.getPolicyContext( request ).getCommonDetails();
			if(!Utils.isEmpty(commonVO))
			{
				try {
					writeContentToStreamMonoline(commonVO,response);
					resp.setSuccess(true);
				}
				catch( Exception e ){
					resp.setData( "File could not be downloaded." );
					resp.setSuccess(false);
					//throw new BusinessException( "pas.renewal.noRecordsFound", null, "No records found for given search criteria" );
					}
			}
		}
		
		else
		{

			if(action.equalsIgnoreCase("TRADE_LICENCE")){
			try {
				System.out.println("before download");
				downloadGeneratedDocument(policyVO,response,action);
				System.out.println("after download");
				resp.setSuccess(true);
			}catch( Exception e ){
				resp.setData( "File could not be downloaded." );
				resp.setSuccess(false);
				//throw new BusinessException( "pas.renewal.noRecordsFound", null, "No records found for given search criteria" );
				}
			}
		}
		
		return resp;
	}

	
	public static void downloadGeneratedDocument( PolicyVO polDataVO, HttpServletResponse response,String actionType ) throws IOException, FileNotFoundException{
		
		
		//Radar fix
		DataHolderVO<Object[]> holder = null; // new DataHolderVO<Object[]>();
		Boolean isQuote = false;
		try{
		
		holder = (DataHolderVO<Object[]>) TaskExecutor.executeTasks(  "GET_LINKING_ID_QUO" ,  polDataVO );
		
		Object holderData[] = holder.getData();
		
		Long quoLinkingId = (Long) holderData[0];
		List<BigDecimal> quoEndtList = (List<BigDecimal>) holderData[1];
		Long polLinkingId = (Long) holderData[2];
		List<BigDecimal> polEndtList = (List<BigDecimal>) holderData[3];
		
		
		System.out.println("fetch linking id and endt id : " + quoLinkingId + " " + polLinkingId );
		System.out.println("fetch linking id and endt id : " + quoEndtList + " " + polEndtList );
		
		Boolean areFilesZipped = false;
		
		if(polEndtList.size() != AppConstants.ZERO){
			areFilesZipped = writeContentToStream(polEndtList, polLinkingId,response,isQuote, polDataVO, areFilesZipped);
		}
		
		// if Policy folder path does not exists then check for Quote path folder
		isQuote = true;
		if(!areFilesZipped) {
			areFilesZipped = writeContentToStream(quoEndtList, quoLinkingId,response,isQuote, polDataVO, areFilesZipped);
		}
		
		
	} catch(Exception e){
		throw new SystemException( "cmn.unknownError", null, "No file found" );
	}

		
	}

	
	private static Boolean writeContentToStream (List<BigDecimal> endtList,Long linkingId,HttpServletResponse response, Boolean isQuote, PolicyVO policyVO, Boolean isZipped){
	 
	try{
		
		
		Boolean fileStreamed = false;
		
		String filepath = Utils.getSingleValueAppConfig( "FILE_UPLOAD_ROOT" )+"/"+ Utils.getSingleValueAppConfig( "FILE_UPLOAD_TRADE_LICENCE_FOLDER" )+"/"+linkingId+"/";
		
		System.out.println("filepath: " + filepath );
		
		
		/*String filename = Utils.getSingleValueAppConfig( "TRADE_LIC_DOWNLOAD_FILE_NAME" )+"_";
		
		System.out.println("filename: " + filename );
		
		List<String> extensionsList = AppUtils.getExtensionSupported();*/
		
		String filename = AppConstants.PRODUCT + "_" + policyVO.getPolicyNo();
		DataHolderVO<Object[]> boolHolder = null;
		// Below method is used to prepare a zip to be downloaded, by adding the uploaded files. 
		boolHolder = (DataHolderVO<Object[]>)prepareZipFileToDownload(response, filepath, fileStreamed, filename, isZipped);
		Object boolHolderData[] = boolHolder.getData();
		fileStreamed = (Boolean) boolHolderData[0];
		isZipped = (Boolean) boolHolderData[1];
		
		/*for ( BigDecimal endIt :  endtList){
			String filenameWithEndtId = filename + endIt;
			File lastModifiedFile = null;// = new File(filepath + filenameWithEndtId + "." + extensionsList.get(0));
			String extn = null; //extensionsList.get(0);
			for (String extension : extensionsList) {
				File file = new File(filepath + filenameWithEndtId + "." + extension);
				System.out.println(filepath + "" +filenameWithEndtId +"" +extension);
				System.out.println(file.getCanonicalPath());
				if (file.isFile()) {
				   if ( Utils.isEmpty(lastModifiedFile) || lastModifiedFile.lastModified() < file.lastModified()) {
				       lastModifiedFile = file;
				       extn = extension;
				   }
				}
			}
			if ( !Utils.isEmpty(lastModifiedFile) && lastModifiedFile.isFile()) {
				response.setHeader("Content-disposition","attachment;filename=" + filenameWithEndtId + "." + extn);
				response.setContentType("application/binary");
				System.out.println(lastModifiedFile.getCanonicalPath());
				OutputStream out = response.getOutputStream();
				FileInputStream inputStream = new FileInputStream(filepath + filenameWithEndtId + "." + extn);
	
				int bufferSize = Integer.valueOf(Utils.getSingleValueAppConfig("TRADE_LICENCE_FILE_SIZE"));
				byte[] buf = new byte[bufferSize];
				int bytesRead = 0;
				while ((bytesRead = inputStream.read(buf)) != -1) {
					out.write(buf, 0, bytesRead);
				}
				fileStreamed = true;
				inputStream.close();
			out.close();
			}
		
		}*/
		/*
		 * Trade license may or may not present for policy but it has to be uploaded for quote at least.  
		 */
		// Check if its quote and file not fond then only throw error.
		if(!fileStreamed && isQuote ){
			
			throw new SystemException( "cmn.unknownError", null, "No file foun_2" );
		}
		
		return isZipped;
		
	}  catch(Exception e){
		throw new SystemException( "cmn.unknownError", null, "File cannot be downloaded" );
	}
}
	
	private static void writeContentToStreamMonoline (CommonVO commonVO, HttpServletResponse response ){
		 
		try{			
			
			Boolean fileStreamed = false;
			//CTS 06.08.2020 CR#613 -Document attachment change
			String filepath=null;
			String filename="";
			if((!Utils.isEmpty(commonVO.getLob())) && (commonVO.getLob().equals(LOB.HOME)||commonVO.getLob().equals(LOB.TRAVEL))){
				filepath = Utils.getSingleValueAppConfig( "FILE_UPLOAD_ROOT" )+"/"+ Utils.getSingleValueAppConfig( "FILE_UPLOAD_"+Utils.getSingleValueAppConfig(commonVO.getLob()+"_DEFAULT_SCREENID")+"_FOLDER" )+"/"+commonVO.getQuoteNo()+"/";
				 filename=commonVO.getLob()+"_"+commonVO.getQuoteNo();
			}else{	
				 filepath = Utils.getSingleValueAppConfig( "FILE_UPLOAD_ROOT" )+"/"+ Utils.getSingleValueAppConfig( "FILE_UPLOAD_TRADE_LICENCE_FOLDER" )+"/"+commonVO.getQuoteNo()+"/";
				 filename = Utils.getSingleValueAppConfig( "MONOLINE_LOB" ) + "_" + commonVO.getPolicyNo();
			}
					
			/*String filename = Utils.getSingleValueAppConfig( "TRADE_LIC_DOWNLOAD_FILE_NAME" )+"_"+commonVO.getQuoteNo();
			//CTS 06.08.2020 CR#613 -Document attachment change
			List<String> extensionsList = AppUtils.getExtensionSupported();*/
			
			 //filename = Utils.getSingleValueAppConfig( "MONOLINE_LOB" ) + "_" + commonVO.getPolicyNo();
			
			Boolean isMonoZipped = false;
			DataHolderVO<Object[]> boolHolder = null;
			// Request ID: 153337 : Below method is used to prepare a zip to be downloaded, by adding the uploaded files.
			boolHolder = (DataHolderVO<Object[]>)prepareZipFileToDownload(response, filepath, fileStreamed, filename, isMonoZipped);
			Object boolHolderData[] = boolHolder.getData();
			fileStreamed = (Boolean) boolHolderData[0];
			isMonoZipped = (Boolean) boolHolderData[1];
			
				/*for (String extension : extensionsList) {
					
					File file = new File(filepath + filename + "." + extension);
					if (file.isFile()) {
						response.setHeader("Content-disposition","attachment;filename=" + filename + "." + extension);
						response.setContentType("application/binary");

						OutputStream out = response.getOutputStream();
						FileInputStream inputStream = new FileInputStream(filepath + filename + "." + extension);

						int bufferSize = Integer.valueOf(Utils.getSingleValueAppConfig("TRADE_LICENCE_FILE_SIZE"));
						byte[] buf = new byte[bufferSize];
						int bytesRead = 0;
						while ((bytesRead = inputStream.read(buf)) != -1) {
							out.write(buf, 0, bytesRead);
						}
						fileStreamed = true;
						inputStream.close();
						out.close();
					}
				}*/
			if(!fileStreamed){				
				throw new SystemException( "cmn.unknownError", null, "No file foun_3" );
			}
		}  catch(Exception e){
			throw new SystemException( "cmn.unknownError", null, "File cannot be downloaded" );
		}
	}
/*
	private static List<String> getExtensionSupported() {
		List<String> extnsList = new ArrayList<String>();
		String[] extns = Utils.getMultiValueAppConfig( "TRADE_LICENCE_FILE_UPLOAD_ALLOWED_EXTNS" );

		if( !Utils.isEmpty( extns ) ){
			Utils.trimAllEntries( extns );
		    extnsList = CopyUtils.asList( extns );
		}
		
		return extnsList;
	}*/
	
	/**
	 * Request ID: 153337 : Method to prepare a zip file to be downloaded
	 * @param response
	 * @param filepath
	 * @param fileStreamed
	 * @throws Exception
	 */
	private static DataHolderVO<Object[]> prepareZipFileToDownload(HttpServletResponse response, String filepath, Boolean fileStreamed, String fileName, Boolean isZipped) throws Exception {
		
		List<String> extensionsList = AppUtils.getExtensionSupported();
		
		Set<File> files = new HashSet<File>();
		File folder = new File(filepath);
		//File listOfFiles[] = folder.listFiles();
				//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
		List<File> Filelist = new ArrayList<File>();	
		      if(folder.exists()&&!Utils.isEmpty(folder.listFiles())){	
		    	  Filelist.addAll(Arrays.asList(folder.listFiles()));	
		      }	
		
			if(fileName.startsWith(LOB.HOME.toString()) || fileName.startsWith(LOB.TRAVEL.toString())){	
					
				String InsuredCode=DAOUtils.FetchInsuredCode(fileName.split("_")[1]);	
		        String Insuredpath = Utils.getSingleValueAppConfig( "FILE_UPLOAD_ROOT" )+"/"+Utils.getSingleValueAppConfig( Utils.concat( "FILE_UPLOAD_" + Utils.getSingleValueAppConfig("EMIRATE_DEFAULT_DOCID") + "_FOLDER" ) )+"/"+InsuredCode;	
					
				File Insuredfolder = new File(Insuredpath);	
				//File InsuredlistOfFiles[] = Insuredfolder.listFiles();	
					
				if(Insuredfolder.exists() && !Utils.isEmpty(Insuredfolder.listFiles())){	
		
					Filelist.addAll(Arrays.asList(Insuredfolder.listFiles()));	
			      	
				}	
			}	
			System.out.println(Filelist.toArray().toString());	
			File listOfFiles[]=new File[Filelist.size()];	
			 listOfFiles=Filelist.toArray(listOfFiles);
			
			//CTS - 21.10.2020 - CR#16903 IA Emirates CR - End
			
			
		if(!Utils.isEmpty(listOfFiles) && !Utils.isEmpty(extensionsList)) {
			for (String extension : extensionsList) {
				for(int cnt=0; cnt < listOfFiles.length; cnt++) {
					
					if (listOfFiles[cnt].isFile()) {
						System.out.println("The file name is:"+ listOfFiles[cnt].getName());
							
						if(listOfFiles[cnt].getName().endsWith("." + extension)) {
							files.add(new File(listOfFiles[cnt].getCanonicalPath() ));
						}
					}
					
				}
			}
		}
		
if(!Utils.isEmpty(files)) {
			
			// Get a ZipOutputStream, so we can zip our files together
			//Sonar Fix to use Try with Resources
			//ZipOutputStream zipOutStream = new ZipOutputStream( response.getOutputStream() );
			//BufferedInputStream buffInpStream =null;
			//FileInputStream fis = null;
			try(ZipOutputStream zipOutStream = new ZipOutputStream(response.getOutputStream())) {
			
			// Set the content type and the filename
			response.setContentType( "application/zip" ) ;
			response.setHeader( "Content-Disposition", "attachment; filename=" + fileName + ".zip" ) ;
			
			for (File file : files) {
				System.out.println("Adding " + file.getName());
				
				zipOutStream.putNextEntry(new ZipEntry(file.getName()));

				// Get the file
				 //fis = null;
				try (FileInputStream fis = new FileInputStream(file);
					BufferedInputStream buffInpStream = new BufferedInputStream(fis)){
					//fis = new FileInputStream(file);
					 //buffInpStream = new BufferedInputStream(fis);
						
						// Write the contents of the file
						int bytesRead = 0;
						while ((bytesRead = buffInpStream.read()) != -1) {
							zipOutStream.write(bytesRead);
						}

						isZipped = true;
						fileStreamed = true;
						zipOutStream.closeEntry();
						System.out.println("Finished adding and writing the file " + file.getName());
				} catch (FileNotFoundException fnfe) {
					// If the file does not exists, write an error entry instead of file contents
					zipOutStream.write(("File not found: " + file.getName()).getBytes());
					zipOutStream.closeEntry();
					System.out.println("Could find file " + file.getAbsolutePath());
					continue;
				}
				/*finally{  //SONARFIX - 20-apr-2018 -- added finally block
					try{
					if(fis!=null){
						fis.close();
					  }
					}
					catch(Exception e){
						e.getMessage();
					 }
					try{
						if(zipOutStream!=null){
							zipOutStream.close();
						  }
						}
						catch(Exception e){
							e.getMessage();
						 }
				}*/
				//Sonar Fix to use Try with Resources
				/* buffInpStream = new BufferedInputStream(fis);
				
				// Write the contents of the file
				int bytesRead = 0;
				while ((bytesRead = buffInpStream.read()) != -1) {
					zipOutStream.write(bytesRead);
				}

				isZipped = true;
				fileStreamed = true;
				
				zipOutStream.closeEntry();
				System.out.println("Finished adding and writing the file " + file.getName());*/
			}
			}
			
		}
		
		DataHolderVO<Object[]> boolHolder = new DataHolderVO<Object[]>();
		Object boolDetails[] = new Object[2];
		
		boolDetails[0] = fileStreamed;
		boolDetails[1] = isZipped;
		
		boolHolder.setData(boolDetails);
		
		return boolHolder;
	}
}





