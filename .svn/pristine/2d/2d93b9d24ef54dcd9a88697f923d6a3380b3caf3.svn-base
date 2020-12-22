package com.rsaame.pas.ui.cmn;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.util.PolicyContextUtil;

/**
 * @author M1016996
 *
 */
public class TradeLiceFileUploadRH extends FileUploadRH{

	/**
	 * Get the file name based on the risk group id
	 * and TRADE_LIC_ key word to identify the trade license file.
	 * 
	 */
	@Override
	protected String getFileName( String fileName, HttpServletRequest request ){

		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		String extension = fileName.substring( fileName.lastIndexOf( "." ) + 1 );
		//CTS 06.08.2020 CR#613 -Document attachment change
		String param = request.getParameter( "param" );
		String fileNamePrefix = "";
		String licenceType = request.getParameter( "licCertType" );
		if(param.equals("PAS_HOME_DOC") || param.equals("PAS_Travel_DOC")){
			fileNamePrefix=licenceType;
		}else{
			//CTS 06.08.2020 CR#613 -Document attachment change
	    if("TLC".equalsIgnoreCase(licenceType)) fileNamePrefix = Utils.getSingleValueAppConfig( "TRADE_LICENSE_FILE" );
	    if("VRC".equalsIgnoreCase(licenceType)) fileNamePrefix = Utils.getSingleValueAppConfig( "VAT_REG_FILE" );
	    if("TLVR".equalsIgnoreCase(licenceType)) fileNamePrefix = Utils.getSingleValueAppConfig( "TRADE_VAT_LICENSE_FILE" );
	    if("OTH".equalsIgnoreCase(licenceType)) fileNamePrefix = Utils.getSingleValueAppConfig( "OTHER_FILE" );
}
		if(!Utils.isEmpty(policyContext.getPolicyDetails()))
		fileName = fileNamePrefix + "_" + policyContext.getPolicyDetails().getEndtId() + "." + extension;
		else
			fileName = fileNamePrefix + "_" + policyContext.getCommonDetails().getQuoteNo() + "." + extension;
		return fileName;
	}

}  
