package com.rsaame.pas.b2c.cmn.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.rsaame.pas.b2c.exception.SystemException;

/**
 * @author m1020637
 *
 */
public class ApplicationContextUtils implements ApplicationContextAware {
 
  private static ApplicationContext ctx;
 
  @Override
  public void setApplicationContext(ApplicationContext appContext)
      throws BeansException {
    ctx = appContext;
 
  }
 
  public static ApplicationContext getApplicationContext() {
    return ctx;
  }
  
  /**
	 * This method returns the bean instance based on the bean name passed.
	 * @return
	 */
	public static Object getBean( String beanName ){
		Object beanInstance = null;
		if( CommonUtils.isEmpty( beanName ) ){
			throw new SystemException( null, "BeanName should not be empty" );
		}
		
		if( CommonUtils.isEmpty( ctx ) ){
			throw new SystemException( null, "applicationContext.xml is not loaded" );
		}
		
		beanInstance = ctx.getBean( beanName );

		return beanInstance;
	}
  
}