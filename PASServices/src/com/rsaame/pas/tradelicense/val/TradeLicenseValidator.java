/**
 * 
 */
package com.rsaame.pas.tradelicense.val;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.validation.IBeanValidator;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.PolicyVO;

/**
 * @author M1016284
 * 	
 * 	This is validator class, Used to check whether a user has 
 * 	uploaded trade license file before converting a quote into policy.
 *
 */
public class TradeLicenseValidator implements IBeanValidator{

	@Override
	public boolean validate( Object bean, Map<String, String> parameters, List<String> errorKeys ){

		boolean filePresent = false;
		if( bean instanceof PolicyVO ){
			PolicyVO policyVO = (PolicyVO) bean;
			String path = Utils.concat( Utils.getSingleValueAppConfig( SvcConstants.FILE_UPLOAD_ROOT ) , "/" , Utils.getSingleValueAppConfig( SvcConstants.FILE_UPLOAD_TRADE_LICENCE_FOLDER ) , "/"
					, policyVO.getPolLinkingId().toString() );
			
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
