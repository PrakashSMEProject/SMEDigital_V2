/**
 * 
 */
package com.rsaame.pas.b2c.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;

/**
 * Filter for payment gateway
 * @author Sarath
 * @since Phase 3
 *
 */
public class PaymentResponseFilter extends HttpServlet implements Filter{


	private static final long serialVersionUID = 1459394703754987949L;
	private final static Logger LOGGER = Logger.getLogger(PaymentResponseFilter.class);

	@Override
	public void destroy(){
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}

	@Override
	public void doFilter( ServletRequest req, ServletResponse res, FilterChain filterChain ) throws IOException, ServletException{
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
        HashMap<String, String> params = new HashMap<String, String>();
        @SuppressWarnings( "rawtypes" )
		Enumeration paramsEnum = request.getParameterNames();
        
        while (paramsEnum.hasMoreElements()) {
            String paramName = (String) paramsEnum.nextElement();
            String paramValue = request.getParameter(paramName);
            params.put(paramName, paramValue);
        }
        String responseCode = params.get("reason_code");
        if( Arrays.asList( AppConstants.PAYMENT_SUCCESS_CODES ).contains( responseCode ) ){
        	LOGGER.info( "Payment Success for Ref No " + params.get( "req_reference_number" ) +". Redirecting to Convert to policy" );
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/convertToPolicy.do");
            requestDispatcher.forward(request, response);
        }
        else{
        	if(Utils.isEmpty( params )){
        		LOGGER.info( "Payment cancelled - No response from Gateway. Redirecting to blank page" );
            	RequestDispatcher requestDispatcher = request.getRequestDispatcher("/paymentCancelled.do");
                requestDispatcher.forward(request, response);
        	}
        	else{
        		LOGGER.info( "Payment Failure for Ref No " + params.get( "req_reference_number" ) +". Redirecting to error page" );
        		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/paymentError.do");
        		requestDispatcher.forward(request, response);
        	}
        }
	}

	@Override
	public void init( FilterConfig arg0 ) throws ServletException{
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}

}
