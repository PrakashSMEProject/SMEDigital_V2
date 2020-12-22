package com.rsaame.pas.ui.cmn;

import javax.servlet.http.HttpServletRequest;

/**
 * @author m1016996
 *
 */
public class SurveyDetFileUploadRH extends FileUploadRH{

	/*
	 * Get the file name based on the risk group id
	 * and SURVEY_DET_ key word to identify the survey detail file.
	 * 
	 */
	@Override
	protected String getFileName( String fileName, HttpServletRequest request ){
	
		String riskGroupId = request.getParameter( "riskGroupId" );

		String extension = fileName.substring( fileName.lastIndexOf( "." ) + 1 );

		return "SURVEY_DET_"+riskGroupId + "." + extension;
	}

}
