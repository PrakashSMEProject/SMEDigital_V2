/**
 * Tag handler for product comparison tag.
 */
package com.rsaame.pas.productComparison.ui;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.cmn.CommonVO;

public class ProductComparisonTagHandler extends SimpleTagSupport{
	
	private String type;
	private String noOfColumns;
	private String disabledFlag;
	private Long policyId;
	/*
	 * 
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	public void doTag() throws IOException {

		PageContext pageContext=(PageContext)getJspContext();  
		JspWriter out = pageContext.getOut();
		
		/*Fetch the details from policy context and forward the same through attribute list to the renderer.*/
		PolicyContext context = PolicyContextUtil.getPolicyContext((HttpServletRequest) pageContext.getRequest());
		CommonVO commonVO = context.getCommonDetails();
		
		ProductsRenderer productsRenderer = new ProductsRenderer();
		
		HashMap<String,Object> attributeList = new HashMap<String,Object>(); 
		try {
			if ( !Utils.isEmpty(type) ) {
				
				attributeList.put( AppConstants.INPUTTYPE, type );
				attributeList.put( AppConstants.NUMBEROFCOLS, noOfColumns );
				attributeList.put( AppConstants.OUT, out );
				attributeList.put( AppConstants.DISABLEDFLAG, disabledFlag );
				attributeList.put( AppConstants.COMMON_VO, commonVO );
				
				TravelInsuranceVO travellerTravelInsuranceVO = (TravelInsuranceVO) pageContext.getRequest().getAttribute( "travelInsuranceVO" );
				attributeList.put( "TravelInsuranceVO", travellerTravelInsuranceVO );
				productsRenderer.buildHTMLContent( attributeList );
				
			}
		} catch (DataAccessException dataAccessException) {
			dataAccessException.printStackTrace();
		} catch (Exception exception) {
			productsRenderer.buildEmptyControl(out);
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
