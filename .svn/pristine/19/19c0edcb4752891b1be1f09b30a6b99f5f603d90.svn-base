package com.rsaame.pas.web;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public class XSSRequestWrapper extends HttpServletRequestWrapper {

	public XSSRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	

	private static Pattern[] patterns = new Pattern[]{
        // Script fragments
        Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
        // src='...'
        Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // lonely script tags
        Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
        Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // eval(...)
        Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // expression(...)
        Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // javascript:...
        Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
        // vbscript:...
        Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
        // onload(...)=...
        Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
    };
	 @Override
	public String[] getParameterValues(String parameter) {
	        String[] values = super.getParameterValues(parameter);
	 
	        if (values == null) {
	            return null;
	        }
	 
	        int count = values.length;
	        String[] encodedValues = new String[count];
	        for (int i = 0; i < count; i++) {
	            encodedValues[i] = stripXSS(values[i]);
	        }
	 
	        return encodedValues;
	    }
	 
	    @Override
		public String getParameter(String parameter) {
	        String value = super.getParameter(parameter);
	 
	        return stripXSS(value);
	    }
	 
	    @Override
		public String getHeader(String name) {
	        String value = super.getHeader(name);
	        return stripXSS(value);
	    }
	 
	    private String stripXSS(String value) {
	        if (value != null) {
	            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
	            // avoid encoded attacks.
	            // value = ESAPI.encoder().canonicalize(value);
	 
	            // Avoid null characters
	            value = value.replaceAll("\0", "");
	 
	            // Remove all sections that match a pattern
	            for (Pattern scriptPattern : patterns){
	                value = scriptPattern.matcher(value).replaceAll("");
	            }
	            
//	            System.out.println("Before Replace Html " + value);
	            /*
	             * commented as it was not giving proper output
	             * value=StringEscapeUtils.escapeHtml(value);
	           	*/
	            value = escapeUtil(value);
//	            System.out.println("After replace html " + value);
	        }
	        return value;
	    }
	    private String escapeUtil(String value){
	    	  if (value != null) {
	    		  value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	               //value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
	               //value = value.replaceAll("'", "&#39;");
	               value = value.replaceAll("eval\\((.*)\\)", "");
	               value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
	               //value = value.replaceAll("\\+", "");
	               //value = value.replaceAll("-", "");
	               value = value.replaceAll("script", "");
	               
	               /* VAPT issues fix */
	               value = value.replaceAll("&", "&");
	               value = value.replaceAll("<", "&lt");
	               value = value.replaceAll(">", "&gt");
	               value = value.replaceAll("\"", "&quot");
	               value = value.replaceAll("\'", "&#x27");
	               //value = value.replaceAll("/", "&#x2F");
	               value = value.replaceAll("\\(", "&#40");
	               value = value.replaceAll("\\)", "&#41");
	               value = value.replaceAll("#", "&#35");
	               value = value.replaceAll("\\{", "&#123");
	               value = value.replaceAll("\\}", "&#125");
	    	  }
	    	 return value;
	    }
}
