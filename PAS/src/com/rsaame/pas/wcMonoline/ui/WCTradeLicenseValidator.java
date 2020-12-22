package com.rsaame.pas.wcMonoline.ui;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.validation.IBeanValidator;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * This validator class to check trade license is uploaded before quote is converted to policy
 * @author M1024781
 *
 */
public class WCTradeLicenseValidator implements IBeanValidator{

	/**
	 * Overridden method to check if the trade license is uploaded.
	 */
	@Override
	public boolean validate(Object bean, Map<String, String> parameters,
			List<String> errorKeys) {
		boolean filePresent = false;
		if( bean instanceof CommonVO ){
			CommonVO commonVO = (CommonVO) bean;
			String path = Utils.concat( Utils.getSingleValueAppConfig( SvcConstants.FILE_UPLOAD_ROOT ) , "/" , Utils.getSingleValueAppConfig( SvcConstants.FILE_UPLOAD_TRADE_LICENCE_FOLDER ) , "/"
					, commonVO.getQuoteNo().toString() );
			
			/*
			 * Get the list of files under the directory "path".
			 * If the list is not empty, that means the user has 
			 * uploaded at least one trade license file already. 
			 * */
			File theFile = new File( path );
			
			if( !Utils.isEmpty( theFile.list() )){
				filePresent = true;
			}
			else{
				errorKeys.add( "pas.tradeLicense.notUploaded" );
				filePresent = false;
			}
			
		}
		return filePresent;
	}

}
