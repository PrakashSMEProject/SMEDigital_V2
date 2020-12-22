package com.rsaame.pas.ui.cmn.fileupload;

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
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.bus.WCNammedEmployeeVO;
import com.rsaame.pas.vo.bus.WorkmenCompVO;

public class MonolineWCFileUploadRH extends GenericFileUploadRH{

	private static final int EMP_NAME_COL = 0;
	
	
	@Override
	protected BaseVO sectionRelatedChanges( HttpServletRequest request, Response response, String fileName ){

		WorkmenCompVO wcompVO = new WorkmenCompVO();
		List<WCNammedEmployeeVO> wcNammedEmployeeVOList = null;
		

		if( !Utils.isEmpty( fileName ) ){

			File file = null;
			file = new File( fileName );
			
			if( file.exists() ){
				wcNammedEmployeeVOList = readFileForWCDetails( fileName );
			}
			
			response.setData(wcNammedEmployeeVOList);

		}
		wcompVO.setWcEmployeeDetails(wcNammedEmployeeVOList);
		return wcompVO;
	}

	private List<WCNammedEmployeeVO> readFileForWCDetails( String fileName){

		List<Map<Integer, Cell>> listOfRows = getFileDataMonoline( fileName, null );
		int noOfRows = listOfRows.size();
		java.util.List<WCNammedEmployeeVO> wcEmployeeDetails = new com.mindtree.ruc.cmn.utils.List<WCNammedEmployeeVO>(
				WCNammedEmployeeVO.class );
		/**
		 * Looping starts from the second row because, the first row we defined
		 * the headings for columns
		 * 
		 */
		for( int i = 1; i < noOfRows; i++ ){
			Map<Integer, Cell> row = listOfRows.get( i );
			WCNammedEmployeeVO namedEmployee = new WCNammedEmployeeVO();

			Cell myCell = null;

			if( row.containsKey( EMP_NAME_COL ) ){
				myCell = row.get( EMP_NAME_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
					namedEmployee.setEmpName( myCell.getStringCellValue() );
				}else{
					namedEmployee.setEmpName( String.valueOf( myCell.getNumericCellValue()) );
				}
				namedEmployee.setIndex( i-1 );
			}
			wcEmployeeDetails.add( namedEmployee );
		}
		return wcEmployeeDetails;
	}

	/**
	 * Get the file name based on the risk group id and CIR_ key word to
	 * identify the Cash in residence file.
	 * 
	 */
	@Override
	protected String getFileName( String fileName, HttpServletRequest request ){

		String riskGroupId = request.getParameter( "riskGroupId" );

		String extension = fileName.substring( fileName.lastIndexOf( "." ) + 1 );

		return "CIR_" + riskGroupId + "." + extension;
	}

	@Override
	protected void setRedirectionIfAny( Response responseObj ){

	/*	Redirection redirection = new Redirection();
		String redirectionURL = SectionRHUtils.getLocationReloadJSP( Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_SECTION" ) ), true );
		redirection.setUrl( redirectionURL );
		redirection.setType( Type.TO_JSP );
		responseObj.setRedirection( redirection );*/
		
		Redirection redirection = new Redirection();
		String redirectionURL = SectionRHUtils.getLocationReloadJSP( Integer.valueOf( Utils.getSingleValueAppConfig( "WC_FILE_UPLOAD_ID" ) ), true );
		redirection.setUrl( redirectionURL );
		redirection.setType( Type.TO_JSP );
		responseObj.setRedirection( redirection );
	}

}
