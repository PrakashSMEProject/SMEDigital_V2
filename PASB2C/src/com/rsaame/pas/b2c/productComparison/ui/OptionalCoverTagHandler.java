/**
 * 
 */
package com.rsaame.pas.b2c.productComparison.ui;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;

/**
 * @author m1019193
 *
 */
public class OptionalCoverTagHandler extends SimpleTagSupport{
	
	private String type;
	private String noOfColumns;
	private String disabledFlag;
	
	public void doTag() throws IOException {

		PageContext pageContext=(PageContext)getJspContext();  
		JspWriter out = pageContext.getOut();
		OptionalCoversRenderer coversRenderer = new OptionalCoversRenderer();
		HashMap<String,Object> attributeList = new HashMap<String,Object>(); 
		try {
			if ( !Utils.isEmpty(type) ) {
				attributeList.put( AppConstants.INPUTTYPE, type );
				attributeList.put( AppConstants.NUMBEROFCOLS, noOfColumns );
				attributeList.put( AppConstants.OUT, out );
				attributeList.put( AppConstants.DISABLEDFLAG, disabledFlag );
				
				TravelInsuranceVO travellerTravelInsuranceVO = (TravelInsuranceVO) pageContext.getRequest().getAttribute( "travelInsuranceVO" );
				attributeList.put( "TravelInsuranceVO", travellerTravelInsuranceVO );
				coversRenderer.buildHTMLContent( attributeList );
			}
		} catch (DataAccessException dataAccessException) {
			dataAccessException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
			coversRenderer.buildEmptyControl(out);
		}	
		
}
	
	/**
	 * @param type
	 * Field Type. (Text box, check box etc..)
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param noOfCols
	 */
	public void setNoOfCols(String noOfColumns) {
		this.noOfColumns = noOfColumns;
	}

	/**
	 * @param disabledFlag
	 * the disabledFlag to set
	 */
	public void setDisabledFlag( String disabledFlag ){
		this.disabledFlag = disabledFlag;
	}

}
