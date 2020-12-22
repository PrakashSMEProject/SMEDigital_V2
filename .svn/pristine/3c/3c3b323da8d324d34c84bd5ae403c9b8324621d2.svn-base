/**
 * 
 */
package com.rsaame.pas.par.svc;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.cmn.BaseSectionSaveDAO;
import com.rsaame.pas.par.dao.IParDAO;
import com.rsaame.pas.par.dao.ParDAO;

/**
 * @author m1014644
 *
 */
public class ParSvc extends BaseService {

	IParDAO parDAO;

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		BaseVO returnValue = null;
		if( "parLoadService".equals( methodName ) ){
			returnValue = parLoadService( (BaseVO) args[ 0 ] );
		}
		if( "parSaveService".equals( methodName ) ){
			returnValue = parSaveService( (BaseVO) args[ 0 ] );
		}
		if( "newparSaveService".equals( methodName ) ){
			returnValue = newparSaveService( (BaseVO) args[ 0 ] );
		}
		if( "parBldService".equals( methodName ) ){
			returnValue = parBldService( (BaseVO) args[ 0 ] );
		}
		if( "getSysDate".equals( methodName ) ){
			returnValue = parBldService( (BaseVO) args[ 0 ] );
		}

		return returnValue;
	}
	
	/*private java.sql.Timestamp getSysdate(){
		return ( (BaseDBDAO) parDAO ).getSysDate();
	}*/
	
	private BaseVO parLoadService(BaseVO baseVO) {
		
		return parDAO.parLoad(baseVO);
	}

	private BaseVO parSaveService(BaseVO baseVO) {
		
		return parDAO.parSaveDAO(baseVO);
				
	}
	
private BaseVO newparSaveService(BaseVO baseVO) {
		
		//return parDAO.parSaveDAO(baseVO);
		//return ( (BaseSectionSaveDAO) parDAO ).save(baseVO);
		return ( (BaseSectionSaveDAO) parDAO ).save(baseVO);
		
	}

	private  BaseVO parBldService(BaseVO baseVO) {
		 
		 return parDAO.parLoadBlds(baseVO);
	}
	


	/**
	 * @param parDAO the parDAO to set
	 */
	public void setParDAO(IParDAO parDAO) {
		this.parDAO = parDAO;
	}

	
	
	
	/*public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
		ParSvc pasSvcS = (ParSvc) applicationContext.getBean("parSaveService");
		//ParSvc pasSvcL = (ParSvc) applicationContext.getBean("parLoadService");
		PolicyVO policyVO = (PolicyVO) readObjectFromFile("D:/PolicyVO");
		
		pasSvcS.invokeMethod("parSaveService", policyVO);
		
	}
	
	private static Object readObjectFromFile( String fileName ){
        Object o = null;
        try{
              ObjectInputStream oos = new ObjectInputStream( new FileInputStream( fileName ) );
              o = oos.readObject();
              oos.close();
              System.out.println( o );
        }
        catch( FileNotFoundException e ){
              // TODO Auto-generated catch block
              e.printStackTrace();
        }
        catch( IOException e ){
              // TODO Auto-generated catch block
              e.printStackTrace();
        }
        catch( ClassNotFoundException e ){
              // TODO Auto-generated catch block
              e.printStackTrace();
        }
        
        return o;
  }*/

}
