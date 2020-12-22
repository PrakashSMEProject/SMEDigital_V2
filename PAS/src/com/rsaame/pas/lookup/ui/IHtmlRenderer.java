/**
 * IHtmlRenderer.java
 * Copyright (c) 2011 MindTree Ltd.
 */
package com.rsaame.pas.lookup.ui;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.jsp.JspWriter;


/**
 * @author m1014594
 *
 */
public interface IHtmlRenderer {
		
	/**
	 * This method is used to generate html content for a given input type
	 * @param 
	 * @throws IOException
	 */
	public abstract void buildHTMLContent(HashMap<String,Object> attributeList) throws IOException;
	
	/**
	 * This method is used to generate default tag for a given input type
	 * @param 
	 * @throws IOException
	 */
	public abstract void buildEmptyControl(JspWriter out) throws IOException;
}
