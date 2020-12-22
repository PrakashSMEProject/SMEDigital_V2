package com.rsaame.pas.print.svc;

import java.io.File;
import java.io.FileInputStream;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.vo.app.PrintDocVO;

public class PASPrinterService {
	private final static Logger logger = Logger.getLogger(PASPrinterService.class);
	public PrintDocVO sendingPrintDoc(PrintDocVO printDocVo ){
		
		//Sonar Fix to use Try with Resources 
		//FileInputStream fis = null; //SONARFIX
		
		try {
			
		if(!Utils.isEmpty(printDocVo.getFileNames())) {

				String fileName=printDocVo.getFileNames();
				
				String renewalFileName = fileName.substring(fileName.lastIndexOf("/")+1,fileName.length());
				System.out.println("Filename is ::::::::::::: "+ renewalFileName);
				File file = new File(fileName);
					if(file.isFile()){
						
						//Sonar Fix to use Try with Resources 
						try(FileInputStream fis = new FileInputStream(file)){
							//fis = new FileInputStream(file);
							PrintPdf printPDFFile = new PrintPdf(fis, renewalFileName, printDocVo.getLocation());
							printPDFFile.print();
							}
							catch(Exception e){
								printDocVo.setDocCreationStatus("fail");
								printDocVo.setError(com.Constant.CONST_FILE_NOT_FOUND_END+ file );
								logger.error("PAS Printer file.isFile() Service Error :"+"File Not Found_1"+ file);
								//Sonar Fix to use Try with Resources 
								//fis.close();  //Uncommented this line for SONARFIX -- 23-apr-2018
							}
						//Sonar Fix to use Try with Resources 
						/*finally{  //SONARFIX -- added finally block to close fis stream - 23-apr-2018
							
							if(fis!=null){
								
								try{
									fis.close();
								}
								catch(Exception e){
									e.getMessage();
								}
							}
						}*/
					}/*else{
						//Start: Check if it is testing or not
						if(Utils.getSingleValueAppConfig( "DOC_PRINT_TESTING").equals("Y")){
							// Yes this is testing
							// Create a PDFFile from a File reference
							try{
							FileInputStream fis = new FileInputStream("C:/PASDocuments/Sample.pdf");
							PrintPdf printPDFFile = new PrintPdf(fis, renewalFileName);
							printPDFFile.print();
							}
							catch(Exception e){
								printDocVo.setDocCreationStatus("fail");
								printDocVo.setError(com.Constant.CONST_FILE_NOT_FOUND_END+"C:/PASDocuments/Sample.pdf" );
								logger.error("PAS Printer file.isFile() else Service Error :"+"File Not Found_2"+"C:/PASDocuments/Sample.pdf");
								//fis.close();
							}
						
						}*/else{
							// Not testing, production
							printDocVo.setDocCreationStatus("fail");
							printDocVo.setError(com.Constant.CONST_FILE_NOT_FOUND_END+fileName );
							logger.error("PAS Printer file.isFile() else Service Error :"+"File Not Found_3"+fileName);

						}
						//End: Check if it is testing or not


					}
		//}
		
		} catch (Exception e) {

			printDocVo.setDocCreationStatus("fail");
			printDocVo.setError("Error occured during Sending Print");
			logger.error("PAS Printer Service Error:"+e.getMessage());
			
		}
		//Sonar Fix to use Try with Resources 
		/*finally{  //SONARFIX -- added finally block to close fis stream - 23-apr-2018
			
			if(fis!=null){
				
				try{
					fis.close();
				}
				catch(Exception e){
					e.getMessage();
				}
			}
		}*/
		return printDocVo;

	}
	
	public Object invokeMethod( String methodName, Object... args ){
		Object returnValue = null;
		if( methodName.equals( "sendingPrintDoc" ) ){
			returnValue = sendingPrintDoc( (PrintDocVO) args[ 0 ] );
		}

		return returnValue;
	}

	
}
